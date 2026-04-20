package com.web3.marketplace.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.marketplace.common.constant.StatusConstants;
import com.web3.marketplace.common.exception.BusinessException;
import com.web3.marketplace.core.dto.FavoriteDTO;
import com.web3.marketplace.core.dto.GameProductDTO;
import com.web3.marketplace.core.dto.NftProductDTO;
import com.web3.marketplace.core.entity.Favorite;
import com.web3.marketplace.core.mapper.FavoriteMapper;
import com.web3.marketplace.core.service.FavoriteService;
import com.web3.marketplace.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

    private final ProductService productService;

    @Override
    public Favorite addFavorite(Long userId, Long productId, String productType) {
        // 检查是否已收藏
        if (isFavorited(userId, productId, productType)) {
            throw new BusinessException("您已经收藏过该商品");
        }

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        favorite.setProductType(productType);
        favorite.setCreateTime(LocalDateTime.now());

        save(favorite);
        log.info("添加收藏: userId={}, productId={}, type={}", userId, productId, productType);
        return favorite;
    }

    @Override
    public void removeFavorite(Long favoriteId) {
        removeById(favoriteId);
        log.info("取消收藏: favoriteId={}", favoriteId);
    }

    @Override
    public void removeFavoriteByUserAndProduct(Long userId, Long productId, String productType) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId)
               .eq(Favorite::getProductId, productId)
               .eq(Favorite::getProductType, productType);
        remove(wrapper);
        log.info("取消收藏: userId={}, productId={}", userId, productId);
    }

    @Override
    public List<FavoriteDTO> getUserFavorites(Long userId) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId);
        wrapper.orderByDesc(Favorite::getCreateTime);
        List<Favorite> favorites = list(wrapper);

        List<FavoriteDTO> dtoList = new ArrayList<>();
        for (Favorite favorite : favorites) {
            FavoriteDTO dto = BeanUtil.copyProperties(favorite, FavoriteDTO.class);

            try {
                if (StatusConstants.PRODUCT_TYPE_NFT.equals(favorite.getProductType())) {
                    NftProductDTO product = productService.getNftProductById(favorite.getProductId());
                    if (product != null) {
                        dto.setProductName(product.getName());
                        dto.setProductImage(product.getImage());
                        dto.setProductPrice(product.getPrice());
                        dto.setProductStock(product.getStock());
                    }
                } else if (StatusConstants.PRODUCT_TYPE_GAME.equals(favorite.getProductType())) {
                    GameProductDTO product = productService.getGameProductById(favorite.getProductId());
                    if (product != null) {
                        dto.setProductName(product.getName());
                        dto.setProductImage(product.getImage());
                        dto.setProductPrice(product.getPrice());
                        dto.setProductStock(product.getStock());
                    }
                }
            } catch (Exception e) {
                log.error("获取收藏商品信息失败: favoriteId={}", favorite.getId(), e);
            }

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public boolean isFavorited(Long userId, Long productId, String productType) {
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getUserId, userId)
               .eq(Favorite::getProductId, productId)
               .eq(Favorite::getProductType, productType);
        return count(wrapper) > 0;
    }
}
