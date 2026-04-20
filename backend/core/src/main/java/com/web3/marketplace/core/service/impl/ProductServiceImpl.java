package com.web3.marketplace.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.marketplace.common.constant.StatusConstants;
import com.web3.marketplace.common.exception.BusinessException;
import com.web3.marketplace.core.dto.GameProductDTO;
import com.web3.marketplace.core.dto.NftProductDTO;
import com.web3.marketplace.core.entity.GameProduct;
import com.web3.marketplace.core.entity.Merchant;
import com.web3.marketplace.core.entity.NftProduct;
import com.web3.marketplace.core.mapper.GameProductMapper;
import com.web3.marketplace.core.mapper.MerchantMapper;
import com.web3.marketplace.core.mapper.NftProductMapper;
import com.web3.marketplace.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final NftProductMapper nftProductMapper;
    private final GameProductMapper gameProductMapper;
    private final MerchantMapper merchantMapper;

    @Override
    public IPage<NftProductDTO> getNftProductPage(Page<NftProduct> page, String keyword, String chain,
                                                  Integer rarity, BigDecimal minPrice, BigDecimal maxPrice, Integer status) {
        LambdaQueryWrapper<NftProduct> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(keyword)) {
            wrapper.like(NftProduct::getName, keyword);
        }
        if (StringUtils.hasText(chain)) {
            wrapper.eq(NftProduct::getChain, chain);
        }
        if (rarity != null) {
            wrapper.eq(NftProduct::getRarity, rarity);
        }
        if (minPrice != null) {
            wrapper.ge(NftProduct::getPrice, minPrice);
        }
        if (maxPrice != null) {
            wrapper.le(NftProduct::getPrice, maxPrice);
        }
        if (status != null) {
            wrapper.eq(NftProduct::getStatus, status);
        }
        wrapper.orderByDesc(NftProduct::getCreateTime);

        IPage<NftProduct> productPage = nftProductMapper.selectPage(page, wrapper);

        return productPage.convert(this::convertToNftProductDTO);
    }

    @Override
    public IPage<NftProductDTO> getNftProductPageByMerchant(Page<NftProduct> page, Long merchantId,
                                                            String keyword, String chain, Integer rarity, Integer status) {
        LambdaQueryWrapper<NftProduct> wrapper = new LambdaQueryWrapper<>();

        if (merchantId != null) {
            wrapper.eq(NftProduct::getMerchantId, merchantId);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.like(NftProduct::getName, keyword);
        }
        if (StringUtils.hasText(chain)) {
            wrapper.eq(NftProduct::getChain, chain);
        }
        if (rarity != null) {
            wrapper.eq(NftProduct::getRarity, rarity);
        }
        if (status != null) {
            wrapper.eq(NftProduct::getStatus, status);
        }
        wrapper.orderByDesc(NftProduct::getCreateTime);

        IPage<NftProduct> productPage = nftProductMapper.selectPage(page, wrapper);

        return productPage.convert(this::convertToNftProductDTO);
    }

    @Override
    public NftProductDTO getNftProductById(Long id) {
        NftProduct product = nftProductMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("NFT商品不存在");
        }
        return convertToNftProductDTO(product);
    }

    @Override
    public NftProduct createNftProduct(NftProduct product) {
        // 新商品默认待审核
        product.setStatus(StatusConstants.PRODUCT_STATUS_PENDING);
        if (product.getStock() == null) {
            product.setStock(1);
        }
        nftProductMapper.insert(product);
        log.info("创建NFT商品: {}", product.getName());
        return product;
    }

    @Override
    public NftProduct approveNftProduct(Long id) {
        NftProduct product = nftProductMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("NFT商品不存在");
        }
        product.setStatus(StatusConstants.PRODUCT_STATUS_APPROVED);
        nftProductMapper.updateById(product);
        log.info("审核通过NFT商品: {}", id);
        return product;
    }

    @Override
    public NftProduct rejectNftProduct(Long id, String reason) {
        NftProduct product = nftProductMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("NFT商品不存在");
        }
        product.setStatus(StatusConstants.PRODUCT_STATUS_REJECTED);
        nftProductMapper.updateById(product);
        log.info("审核拒绝NFT商品: {}, 原因: {}", id, reason);
        return product;
    }

    @Override
    public NftProduct reshelfNftProduct(Long id) {
        NftProduct product = nftProductMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("NFT商品不存在");
        }
        // 重新上架需要待审核状态，由平台管理员审核
        product.setStatus(StatusConstants.PRODUCT_STATUS_PENDING);
        nftProductMapper.updateById(product);
        log.info("申请重新上架NFT商品: {}", id);
        return product;
    }

    @Override
    public NftProduct updateNftProduct(NftProduct product) {
        if (nftProductMapper.selectById(product.getId()) == null) {
            throw new BusinessException("NFT商品不存在");
        }
        nftProductMapper.updateById(product);
        log.info("更新NFT商品: {}", product.getName());
        return product;
    }

    @Override
    public void deleteNftProduct(Long id) {
        nftProductMapper.deleteById(id);
        log.info("删除NFT商品: {}", id);
    }

    @Override
    public IPage<GameProductDTO> getGameProductPage(Page<GameProduct> page, String keyword, String category,
                                                    BigDecimal minPrice, BigDecimal maxPrice, Integer status) {
        LambdaQueryWrapper<GameProduct> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(keyword)) {
            wrapper.like(GameProduct::getName, keyword);
        }
        if (StringUtils.hasText(category)) {
            wrapper.eq(GameProduct::getCategory, category);
        }
        if (minPrice != null) {
            wrapper.ge(GameProduct::getPrice, minPrice);
        }
        if (maxPrice != null) {
            wrapper.le(GameProduct::getPrice, maxPrice);
        }
        if (status != null) {
            wrapper.eq(GameProduct::getStatus, status);
        }
        wrapper.orderByDesc(GameProduct::getCreateTime);

        IPage<GameProduct> productPage = gameProductMapper.selectPage(page, wrapper);

        return productPage.convert(this::convertToGameProductDTO);
    }

    @Override
    public IPage<GameProductDTO> getGameProductPageByMerchant(Page<GameProduct> page, Long merchantId,
                                                               String keyword, String category, Integer status) {
        LambdaQueryWrapper<GameProduct> wrapper = new LambdaQueryWrapper<>();

        if (merchantId != null) {
            wrapper.eq(GameProduct::getMerchantId, merchantId);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.like(GameProduct::getName, keyword);
        }
        if (StringUtils.hasText(category)) {
            wrapper.eq(GameProduct::getCategory, category);
        }
        if (status != null) {
            wrapper.eq(GameProduct::getStatus, status);
        }
        wrapper.orderByDesc(GameProduct::getCreateTime);

        IPage<GameProduct> productPage = gameProductMapper.selectPage(page, wrapper);

        return productPage.convert(this::convertToGameProductDTO);
    }

    @Override
    public GameProductDTO getGameProductById(Long id) {
        GameProduct product = gameProductMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("游戏商品不存在");
        }
        return convertToGameProductDTO(product);
    }

    @Override
    public GameProduct createGameProduct(GameProduct product) {
        // 新商品默认待审核
        product.setStatus(StatusConstants.PRODUCT_STATUS_PENDING);
        if (product.getStock() == null) {
            product.setStock(1);
        }
        gameProductMapper.insert(product);
        log.info("创建游戏商品: {}", product.getName());
        return product;
    }

    @Override
    public GameProduct approveGameProduct(Long id) {
        GameProduct product = gameProductMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("游戏商品不存在");
        }
        product.setStatus(StatusConstants.PRODUCT_STATUS_APPROVED);
        gameProductMapper.updateById(product);
        log.info("审核通过游戏商品: {}", id);
        return product;
    }

    @Override
    public GameProduct rejectGameProduct(Long id, String reason) {
        GameProduct product = gameProductMapper.selectById(id);
        if (product == null) {
            throw new BusinessException("游戏商品不存在");
        }
        product.setStatus(StatusConstants.PRODUCT_STATUS_REJECTED);
        gameProductMapper.updateById(product);
        log.info("审核拒绝游戏商品: {}, 原因: {}", id, reason);
        return product;
    }

    @Override
    public GameProduct updateGameProduct(GameProduct product) {
        if (gameProductMapper.selectById(product.getId()) == null) {
            throw new BusinessException("游戏商品不存在");
        }
        gameProductMapper.updateById(product);
        log.info("更新游戏商品: {}", product.getName());
        return product;
    }

    @Override
    public void deleteGameProduct(Long id) {
        gameProductMapper.deleteById(id);
        log.info("删除游戏商品: {}", id);
    }

    private NftProductDTO convertToNftProductDTO(NftProduct product) {
        NftProductDTO dto = BeanUtil.copyProperties(product, NftProductDTO.class);

        Merchant merchant = merchantMapper.selectById(product.getMerchantId());
        if (merchant != null) {
            dto.setMerchantName(merchant.getShopName());
        }
        return dto;
    }

    private GameProductDTO convertToGameProductDTO(GameProduct product) {
        GameProductDTO dto = BeanUtil.copyProperties(product, GameProductDTO.class);

        Merchant merchant = merchantMapper.selectById(product.getMerchantId());
        if (merchant != null) {
            dto.setMerchantName(merchant.getShopName());
        }
        return dto;
    }
}
