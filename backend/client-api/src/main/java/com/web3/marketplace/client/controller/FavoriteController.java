package com.web3.marketplace.client.controller;

import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.FavoriteDTO;
import com.web3.marketplace.core.entity.Favorite;
import com.web3.marketplace.core.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    /**
     * 添加收藏
     */
    @PostMapping("/add")
    public Result<Favorite> addFavorite(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long productId = Long.valueOf(params.get("productId").toString());
        String productType = params.get("productType").toString();

        Favorite favorite = favoriteService.addFavorite(userId, productId, productType);
        return Result.success(favorite);
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/remove/{favoriteId}")
    public Result<Void> removeFavorite(@PathVariable Long favoriteId) {
        favoriteService.removeFavorite(favoriteId);
        return Result.success();
    }

    /**
     * 根据商品取消收藏
     */
    @DeleteMapping("/remove")
    public Result<Void> removeFavoriteByProduct(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam String productType) {
        favoriteService.removeFavoriteByUserAndProduct(userId, productId, productType);
        return Result.success();
    }

    /**
     * 获取用户收藏列表
     */
    @GetMapping("/list")
    public Result<List<FavoriteDTO>> getUserFavorites(@RequestParam Long userId) {
        List<FavoriteDTO> favorites = favoriteService.getUserFavorites(userId);
        return Result.success(favorites);
    }

    /**
     * 检查是否已收藏
     */
    @GetMapping("/check")
    public Result<Boolean> checkFavorite(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam String productType) {
        boolean favorited = favoriteService.isFavorited(userId, productId, productType);
        return Result.success(favorited);
    }
}
