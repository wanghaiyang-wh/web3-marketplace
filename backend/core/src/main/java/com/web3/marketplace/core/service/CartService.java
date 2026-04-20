package com.web3.marketplace.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.marketplace.core.dto.CartDTO;
import com.web3.marketplace.core.entity.Cart;

import java.util.List;

public interface CartService extends IService<Cart> {

    /**
     * 添加商品到购物车
     */
    Cart addToCart(Long userId, Long productId, String productType, Integer quantity);

    /**
     * 更新购物车商品数量
     */
    void updateCartQuantity(Long cartId, Integer quantity);

    /**
     * 从购物车删除
     */
    void removeFromCart(Long cartId);

    /**
     * 批量删除购物车商品
     */
    void batchRemoveFromCart(List<Long> cartIds);

    /**
     * 获取用户购物车列表
     */
    List<CartDTO> getUserCart(Long userId);

    /**
     * 获取用户购物车分页列表
     */
    IPage<CartDTO> getUserCartPage(Page<Cart> page, Long userId);

    /**
     * 清空用户购物车
     */
    void clearUserCart(Long userId);

    /**
     * 检查商品是否在购物车中
     */
    boolean checkProductInCart(Long userId, Long productId, String productType);
}
