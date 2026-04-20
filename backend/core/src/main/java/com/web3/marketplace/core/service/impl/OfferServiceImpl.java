package com.web3.marketplace.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.marketplace.common.exception.BusinessException;
import com.web3.marketplace.core.entity.Offer;
import com.web3.marketplace.core.mapper.OfferMapper;
import com.web3.marketplace.core.service.OfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferServiceImpl extends ServiceImpl<OfferMapper, Offer> implements OfferService {

    @Override
    public Offer createOffer(Long productId, String productType, Long buyerId, Long merchantId, BigDecimal price, String message) {
        Offer offer = new Offer();
        offer.setProductId(productId);
        offer.setProductType(productType);
        offer.setBuyerId(buyerId);
        offer.setMerchantId(merchantId);
        offer.setPrice(price);
        offer.setMessage(message);
        offer.setStatus("PENDING");
        offer.setCreateTime(LocalDateTime.now());
        offer.setUpdateTime(LocalDateTime.now());

        save(offer);
        log.info("创建出价: buyer={}, product={}, price={}", buyerId, productId, price);
        return offer;
    }

    @Override
    public IPage<Offer> getBuyerOffers(Page<Offer> page, Long buyerId) {
        LambdaQueryWrapper<Offer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Offer::getBuyerId, buyerId);
        wrapper.orderByDesc(Offer::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public IPage<Offer> getMerchantOffers(Page<Offer> page, Long merchantId) {
        LambdaQueryWrapper<Offer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Offer::getMerchantId, merchantId);
        wrapper.orderByDesc(Offer::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public void respondOffer(Long offerId, String status) {
        Offer offer = getById(offerId);
        if (offer == null) {
            throw new BusinessException("出价不存在");
        }
        if (!"PENDING".equals(offer.getStatus())) {
            throw new BusinessException("出价状态不正确");
        }
        if (!"ACCEPTED".equals(status) && !"REJECTED".equals(status)) {
            throw new BusinessException("无效的状态");
        }

        offer.setStatus(status);
        offer.setUpdateTime(LocalDateTime.now());
        updateById(offer);
        log.info("出价响应: id={}, status={}", offerId, status);
    }

    @Override
    public IPage<Offer> getProductOffers(Page<Offer> page, Long productId) {
        LambdaQueryWrapper<Offer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Offer::getProductId, productId);
        wrapper.orderByDesc(Offer::getCreateTime);
        return page(page, wrapper);
    }
}
