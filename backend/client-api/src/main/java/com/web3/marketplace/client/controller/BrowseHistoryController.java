package com.web3.marketplace.client.controller;

import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.BrowseHistoryDTO;
import com.web3.marketplace.core.entity.BrowseHistory;
import com.web3.marketplace.core.service.BrowseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client/history")
@RequiredArgsConstructor
public class BrowseHistoryController {

    private final BrowseHistoryService browseHistoryService;

    /**
     * 添加浏览记录
     */
    @PostMapping("/add")
    public Result<BrowseHistory> addHistory(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long productId = Long.valueOf(params.get("productId").toString());
        String productType = params.get("productType").toString();

        BrowseHistory history = browseHistoryService.addHistory(userId, productId, productType);
        return Result.success(history);
    }

    /**
     * 获取用户浏览历史
     */
    @GetMapping("/list")
    public Result<List<BrowseHistoryDTO>> getUserHistory(@RequestParam Long userId) {
        List<BrowseHistoryDTO> history = browseHistoryService.getUserHistory(userId);
        return Result.success(history);
    }

    /**
     * 清空用户浏览历史
     */
    @DeleteMapping("/clear")
    public Result<Void> clearUserHistory(@RequestParam Long userId) {
        browseHistoryService.clearUserHistory(userId);
        return Result.success();
    }
}
