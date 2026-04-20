package com.web3.marketplace.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.marketplace.core.dto.OrderDTO;
import com.web3.marketplace.core.entity.Order;

public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     */
    Order createOrder(Long userId, Long merchantId, Long productId, String productType, String payType);

    /**
     * 支付订单
     */
    void payOrder(Long orderId, String txHash);

    /**
     * 完成订单
     */
    void completeOrder(Long orderId);

    /**
     * 取消订单
     */
    void cancelOrder(Long orderId);

    /**
     * 用户订单列表
     */
    IPage<OrderDTO> getUserOrderPage(Page<Order> page, Long userId, Integer status);

    /**
     * 商家订单列表
     */
    IPage<OrderDTO> getMerchantOrderPage(Page<Order> page, Long merchantId, Integer status);

    /**
     * 根据ID查询订单
     */
    OrderDTO getOrderById(Long id);

    /**
     * 根据订单号查询
     */
    OrderDTO getOrderByNo(String orderNo);
}
