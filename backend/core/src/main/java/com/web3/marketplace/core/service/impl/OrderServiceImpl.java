package com.web3.marketplace.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.marketplace.common.constant.StatusConstants;
import com.web3.marketplace.common.exception.BusinessException;
import com.web3.marketplace.core.dto.GameProductDTO;
import com.web3.marketplace.core.dto.NftProductDTO;
import com.web3.marketplace.core.dto.OrderDTO;
import com.web3.marketplace.core.entity.*;
import com.web3.marketplace.core.mapper.OrderMapper;
import com.web3.marketplace.core.service.OrderService;
import com.web3.marketplace.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final ProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Long userId, Long merchantId, Long productId, String productType, String payType) {
        BigDecimal price = BigDecimal.ZERO;

        if (StatusConstants.PRODUCT_TYPE_NFT.equals(productType)) {
            NftProductDTO product = productService.getNftProductById(productId);
            if (product.getStock() < 1) {
                throw new BusinessException("商品已售罄");
            }
            price = product.getPrice();
        } else if (StatusConstants.PRODUCT_TYPE_GAME.equals(productType)) {
            GameProductDTO product = productService.getGameProductById(productId);
            if (product.getStock() < 1) {
                throw new BusinessException("商品已售罄");
            }
            price = product.getPrice();
        }

        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setMerchantId(merchantId);
        order.setProductId(productId);
        order.setProductType(productType);
        order.setAmount(price);
        order.setPayType(payType);
        order.setStatus(StatusConstants.ORDER_STATUS_PENDING);

        save(order);
        log.info("创建订单: {}", order.getOrderNo());
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(Long orderId, String txHash) {
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != StatusConstants.ORDER_STATUS_PENDING) {
            throw new BusinessException("订单状态不正确");
        }

        order.setStatus(StatusConstants.ORDER_STATUS_PAID);
        order.setTxHash(txHash);
        updateById(order);
        log.info("订单支付成功: {}", order.getOrderNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeOrder(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != StatusConstants.ORDER_STATUS_PAID) {
            throw new BusinessException("订单状态不正确");
        }

        order.setStatus(StatusConstants.ORDER_STATUS_COMPLETED);
        updateById(order);
        log.info("订单完成: {}", order.getOrderNo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != StatusConstants.ORDER_STATUS_PENDING) {
            throw new BusinessException("只有待支付的订单才能取消");
        }

        order.setStatus(StatusConstants.ORDER_STATUS_CANCELLED);
        updateById(order);
        log.info("订单已取消: {}", order.getOrderNo());
    }

    @Override
    public IPage<OrderDTO> getUserOrderPage(Page<Order> page, Long userId, Integer status) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId);
        if (status != null) {
            wrapper.eq(Order::getStatus, status);
        }
        wrapper.orderByDesc(Order::getCreateTime);

        IPage<Order> orderPage = page(page, wrapper);
        return orderPage.convert(this::convertToOrderDTO);
    }

    @Override
    public IPage<OrderDTO> getMerchantOrderPage(Page<Order> page, Long merchantId, Integer status) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getMerchantId, merchantId);
        if (status != null) {
            wrapper.eq(Order::getStatus, status);
        }
        wrapper.orderByDesc(Order::getCreateTime);

        IPage<Order> orderPage = page(page, wrapper);
        return orderPage.convert(this::convertToOrderDTO);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = getById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        return convertToOrderDTO(order);
    }

    @Override
    public OrderDTO getOrderByNo(String orderNo) {
        Order order = getOne(new LambdaQueryWrapper<Order>().eq(Order::getOrderNo, orderNo));
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        return convertToOrderDTO(order);
    }

    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + (int)(Math.random() * 10000);
    }

    private OrderDTO convertToOrderDTO(Order order) {
        OrderDTO dto = BeanUtil.copyProperties(order, OrderDTO.class);
        // TODO: 完善用户和商家名称查询
        return dto;
    }
}
