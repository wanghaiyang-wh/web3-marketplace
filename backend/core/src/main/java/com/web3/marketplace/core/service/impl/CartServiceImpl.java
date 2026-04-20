package com.web3.marketplace.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.marketplace.common.constant.StatusConstants;
import com.web3.marketplace.common.exception.BusinessException;
import com.web3.marketplace.core.dto.CartDTO;
import com.web3.marketplace.core.dto.GameProductDTO;
import com.web3.marketplace.core.dto.MerchantDTO;
import com.web3.marketplace.core.dto.NftProductDTO;
import com.web3.marketplace.core.entity.Cart;
import com.web3.marketplace.core.mapper.CartMapper;
import com.web3.marketplace.core.service.CartService;
import com.web3.marketplace.core.service.MerchantService;
import com.web3.marketplace.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    private final ProductService productService;
    private final MerchantService merchantService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Cart addToCart(Long userId, Long productId, String productType, Integer quantity) {
        // 检查商品是否存在
        if (StatusConstants.PRODUCT_TYPE_NFT.equals(productType)) {
            NftProductDTO product = productService.getNftProductById(productId);
            if (product == null) {
                throw new BusinessException("NFT商品不存在");
            }
            if (product.getStock() < quantity) {
                throw new BusinessException("商品库存不足");
            }
        } else if (StatusConstants.PRODUCT_TYPE_GAME.equals(productType)) {
            GameProductDTO product = productService.getGameProductById(productId);
            if (product == null) {
                throw new BusinessException("游戏商品不存在");
            }
            if (product.getStock() < quantity) {
                throw new BusinessException("商品库存不足");
            }
        }

        // 检查是否已在购物车中
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId)
               .eq(Cart::getProductId, productId)
               .eq(Cart::getProductType, productType);
        Cart existingCart = getOne(wrapper);

        if (existingCart != null) {
            // 更新数量
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            updateById(existingCart);
            log.info("更新购物车数量: userId={}, productId={}, quantity={}", userId, productId, existingCart.getQuantity());
            return existingCart;
        }

        // 新增购物车记录
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setProductType(productType);
        cart.setQuantity(quantity);
        save(cart);
        log.info("添加商品到购物车: userId={}, productId={}, quantity={}", userId, productId, quantity);
        return cart;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCartQuantity(Long cartId, Integer quantity) {
        Cart cart = getById(cartId);
        if (cart == null) {
            throw new BusinessException("购物车记录不存在");
        }
        if (quantity <= 0) {
            removeById(cartId);
            log.info("从购物车删除: cartId={}", cartId);
            return;
        }
        cart.setQuantity(quantity);
        updateById(cart);
        log.info("更新购物车数量: cartId={}, quantity={}", cartId, quantity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFromCart(Long cartId) {
        removeById(cartId);
        log.info("从购物车删除: cartId={}", cartId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchRemoveFromCart(List<Long> cartIds) {
        removeByIds(cartIds);
        log.info("批量从购物车删除: cartIds={}", cartIds);
    }

    @Override
    public List<CartDTO> getUserCart(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        wrapper.orderByDesc(Cart::getCreateTime);
        List<Cart> carts = list(wrapper);
        return convertToCartDTOList(carts);
    }

    @Override
    public IPage<CartDTO> getUserCartPage(Page<Cart> page, Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        wrapper.orderByDesc(Cart::getCreateTime);
        IPage<Cart> cartPage = page(page, wrapper);
        return cartPage.convert(this::convertToCartDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearUserCart(Long userId) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId);
        remove(wrapper);
        log.info("清空用户购物车: userId={}", userId);
    }

    @Override
    public boolean checkProductInCart(Long userId, Long productId, String productType) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId, userId)
               .eq(Cart::getProductId, productId)
               .eq(Cart::getProductType, productType);
        return count(wrapper) > 0;
    }

    private CartDTO convertToCartDTO(Cart cart) {
        CartDTO dto = BeanUtil.copyProperties(cart, CartDTO.class);

        try {
            if (StatusConstants.PRODUCT_TYPE_NFT.equals(cart.getProductType())) {
                NftProductDTO product = productService.getNftProductById(cart.getProductId());
                if (product != null) {
                    dto.setProductName(product.getName());
                    dto.setProductImage(product.getImage());
                    dto.setProductPrice(product.getPrice());
                    dto.setProductStock(product.getStock());
                    dto.setMerchantId(product.getMerchantId());

                    MerchantDTO merchant = merchantService.getMerchantById(product.getMerchantId());
                    if (merchant != null) {
                        dto.setMerchantName(merchant.getShopName());
                    }
                }
            } else if (StatusConstants.PRODUCT_TYPE_GAME.equals(cart.getProductType())) {
                GameProductDTO product = productService.getGameProductById(cart.getProductId());
                if (product != null) {
                    dto.setProductName(product.getName());
                    dto.setProductImage(product.getImage());
                    dto.setProductPrice(product.getPrice());
                    dto.setProductStock(product.getStock());
                    dto.setMerchantId(product.getMerchantId());

                    MerchantDTO merchant = merchantService.getMerchantById(product.getMerchantId());
                    if (merchant != null) {
                        dto.setMerchantName(merchant.getShopName());
                    }
                }
            }
        } catch (Exception e) {
            log.error("转换购物车DTO失败: cartId={}", cart.getId(), e);
        }

        return dto;
    }

    private List<CartDTO> convertToCartDTOList(List<Cart> carts) {
        if (carts == null || carts.isEmpty()) {
            return new ArrayList<>();
        }
        return carts.stream()
                .map(this::convertToCartDTO)
                .collect(Collectors.toList());
    }
}
