package com.web3.marketplace.client.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web3.marketplace.common.result.PageResult;
import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.entity.Offer;
import com.web3.marketplace.core.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/client/offer")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PostMapping("/create")
    public Result<Offer> createOffer(@RequestBody Map<String, Object> request) {
        if (request.get("productId") == null || request.get("productType") == null ||
            request.get("buyerId") == null || request.get("merchantId") == null ||
            request.get("price") == null) {
            return Result.error(400, "缺少必要参数");
        }
        Long productId = Long.valueOf(request.get("productId").toString());
        String productType = request.get("productType").toString();
        Long buyerId = Long.valueOf(request.get("buyerId").toString());
        Long merchantId = Long.valueOf(request.get("merchantId").toString());
        BigDecimal price = new BigDecimal(request.get("price").toString());
        String message = (String) request.getOrDefault("message", "");

        Offer offer = offerService.createOffer(productId, productType, buyerId, merchantId, price, message);
        return Result.success(offer);
    }

    @GetMapping("/my")
    public Result<PageResult<Offer>> getMyOffers(
            @RequestParam Long buyerId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {

        Page<Offer> page = new Page<>(current, size);
        IPage<Offer> result = offerService.getBuyerOffers(page, buyerId);

        return Result.success(PageResult.of(
                result.getRecords(),
                result.getTotal(),
                result.getCurrent(),
                result.getSize()
        ));
    }

    @PostMapping("/respond")
    public Result<Void> respondOffer(@RequestBody Map<String, Object> request) {
        if (request.get("offerId") == null || request.get("status") == null) {
            return Result.error(400, "缺少必要参数");
        }
        Long offerId = Long.valueOf(request.get("offerId").toString());
        String status = request.get("status").toString();

        offerService.respondOffer(offerId, status);
        return Result.success();
    }
}
