package com.web3.marketplace.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.marketplace.core.dto.FavoriteDTO;
import com.web3.marketplace.core.entity.Favorite;

import java.util.List;

public interface FavoriteService extends IService<Favorite> {

    /**
     * 添加收藏
     */
    Favorite addFavorite(Long userId, Long productId, String productType);

    /**
     * 取消收藏
     */
    void removeFavorite(Long favoriteId);

    /**
     * 取消收藏(根据用户和商品)
     */
    void removeFavoriteByUserAndProduct(Long userId, Long productId, String productType);

    /**
     * 获取用户收藏列表
     */
    List<FavoriteDTO> getUserFavorites(Long userId);

    /**
     * 检查是否已收藏
     */
    boolean isFavorited(Long userId, Long productId, String productType);
}
