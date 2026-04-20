package com.web3.marketplace.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.marketplace.core.dto.GameProductDTO;
import com.web3.marketplace.core.dto.NftProductDTO;
import com.web3.marketplace.core.entity.GameProduct;
import com.web3.marketplace.core.entity.NftProduct;

import java.math.BigDecimal;

public interface ProductService {

    // NFT商品相关
    IPage<NftProductDTO> getNftProductPage(Page<NftProduct> page, String keyword, String chain,
                                            Integer rarity, BigDecimal minPrice, BigDecimal maxPrice, Integer status);

    IPage<NftProductDTO> getNftProductPageByMerchant(Page<NftProduct> page, Long merchantId,
                                                       String keyword, String chain, Integer rarity, Integer status);

    NftProductDTO getNftProductById(Long id);

    NftProduct createNftProduct(NftProduct product);

    NftProduct updateNftProduct(NftProduct product);

    void deleteNftProduct(Long id);

    // NFT审核
    NftProduct approveNftProduct(Long id);

    NftProduct rejectNftProduct(Long id, String reason);

    // NFT重新上架
    NftProduct reshelfNftProduct(Long id);

    // 游戏商品相关
    IPage<GameProductDTO> getGameProductPage(Page<GameProduct> page, String keyword, String category,
                                             BigDecimal minPrice, BigDecimal maxPrice, Integer status);

    IPage<GameProductDTO> getGameProductPageByMerchant(Page<GameProduct> page, Long merchantId,
                                                        String keyword, String category, Integer status);

    GameProductDTO getGameProductById(Long id);

    GameProduct createGameProduct(GameProduct product);

    GameProduct updateGameProduct(GameProduct product);

    void deleteGameProduct(Long id);

    // 游戏审核
    GameProduct approveGameProduct(Long id);

    GameProduct rejectGameProduct(Long id, String reason);
}
