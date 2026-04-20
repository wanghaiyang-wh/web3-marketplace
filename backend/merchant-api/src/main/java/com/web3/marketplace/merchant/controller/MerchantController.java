package com.web3.marketplace.merchant.controller;

import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.MerchantDTO;
import com.web3.marketplace.core.entity.Merchant;
import com.web3.marketplace.core.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping("/register")
    public Result<Merchant> register(@RequestBody Map<String, String> request) {
        Long userId = Long.valueOf(request.get("userId"));
        String shopName = request.get("shopName");
        String description = request.get("description");
        String contact = request.get("contact");

        Merchant merchant = merchantService.register(userId, shopName, description, contact);
        return Result.success(merchant);
    }

    @GetMapping("/info")
    public Result<MerchantDTO> getMerchantInfo(@RequestParam Long userId) {
        return Result.success(merchantService.getByUserId(userId));
    }

    @PutMapping("/update")
    public Result<Merchant> updateMerchant(@RequestBody Merchant merchant) {
        return Result.success(merchantService.updateMerchant(merchant));
    }
}
