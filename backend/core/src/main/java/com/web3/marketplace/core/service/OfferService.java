package com.web3.marketplace.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.marketplace.core.entity.Offer;

import java.math.BigDecimal;

public interface OfferService extends IService<Offer> {

    /**
     * 创建出价
     */
    Offer createOffer(Long productId, String productType, Long buyerId, Long merchantId, BigDecimal price, String message);

    /**
     * 买家查询自己的出价
     */
    IPage<Offer> getBuyerOffers(Page<Offer> page, Long buyerId);

    /**
     * 商家查询收到的出价
     */
    IPage<Offer> getMerchantOffers(Page<Offer> page, Long merchantId);

    /**
     * 商家响应出价
     */
    void respondOffer(Long offerId, String status);

    /**
     * 查询商品的所有出价
     */
    IPage<Offer> getProductOffers(Page<Offer> page, Long productId);
}
