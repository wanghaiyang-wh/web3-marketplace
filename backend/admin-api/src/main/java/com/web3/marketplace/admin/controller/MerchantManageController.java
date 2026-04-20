package com.web3.marketplace.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web3.marketplace.common.result.PageResult;
import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.MerchantDTO;
import com.web3.marketplace.core.entity.Merchant;
import com.web3.marketplace.core.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/merchant")
@RequiredArgsConstructor
public class MerchantManageController {

    private final MerchantService merchantService;

    @GetMapping("/list")
    public Result<PageResult<MerchantDTO>> getMerchantList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status) {

        Page<Merchant> page = new Page<>(current, size);
        IPage<MerchantDTO> result = merchantService.getMerchantPage(page, keyword, status);

        return Result.success(PageResult.of(
                result.getRecords(),
                result.getTotal(),
                result.getCurrent(),
                result.getSize()
        ));
    }

    @GetMapping("/{id}")
    public Result<MerchantDTO> getMerchantDetail(@PathVariable Long id) {
        return Result.success(merchantService.getMerchantById(id));
    }

    @PutMapping("/status/{id}")
    public Result<Void> updateMerchantStatus(@PathVariable Long id, @RequestParam Integer status) {
        merchantService.updateStatus(id, status);
        return Result.success();
    }
}
