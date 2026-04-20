package com.web3.marketplace.client.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.CartDTO;
import com.web3.marketplace.core.entity.Cart;
import com.web3.marketplace.core.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 添加商品到购物车
     */
    @PostMapping("/add")
    public Result<Cart> addToCart(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long productId = Long.valueOf(params.get("productId").toString());
        String productType = params.get("productType").toString();
        Integer quantity = params.get("quantity") != null ?
            Integer.valueOf(params.get("quantity").toString()) : 1;

        Cart cart = cartService.addToCart(userId, productId, productType, quantity);
        return Result.success(cart);
    }

    /**
     * 更新购物车商品数量
     */
    @PutMapping("/update/{cartId}")
    public Result<Void> updateCartQuantity(
            @PathVariable Long cartId,
            @RequestParam Integer quantity) {
        cartService.updateCartQuantity(cartId, quantity);
        return Result.success();
    }

    /**
     * 从购物车删除
     */
    @DeleteMapping("/remove/{cartId}")
    public Result<Void> removeFromCart(@PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
        return Result.success();
    }

    /**
     * 批量删除购物车商品
     */
    @PostMapping("/batchRemove")
    public Result<Void> batchRemoveFromCart(@RequestBody List<Long> cartIds) {
        cartService.batchRemoveFromCart(cartIds);
        return Result.success();
    }

    /**
     * 获取用户购物车列表
     */
    @GetMapping("/list")
    public Result<List<CartDTO>> getUserCart(@RequestParam Long userId) {
        List<CartDTO> cartList = cartService.getUserCart(userId);
        return Result.success(cartList);
    }

    /**
     * 获取用户购物车分页列表
     */
    @GetMapping("/page")
    public Result<IPage<CartDTO>> getUserCartPage(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<Cart> page = new Page<>(current, size);
        IPage<CartDTO> cartPage = cartService.getUserCartPage(page, userId);
        return Result.success(cartPage);
    }

    /**
     * 清空用户购物车
     */
    @DeleteMapping("/clear")
    public Result<Void> clearUserCart(@RequestParam Long userId) {
        cartService.clearUserCart(userId);
        return Result.success();
    }

    /**
     * 检查商品是否在购物车中
     */
    @GetMapping("/check")
    public Result<Boolean> checkProductInCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam String productType) {
        boolean exists = cartService.checkProductInCart(userId, productId, productType);
        return Result.success(exists);
    }
}
