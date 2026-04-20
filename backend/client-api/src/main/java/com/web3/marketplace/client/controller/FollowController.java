package com.web3.marketplace.client.controller;

import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.FollowDTO;
import com.web3.marketplace.core.entity.Follow;
import com.web3.marketplace.core.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/client/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    /**
     * 关注店铺
     */
    @PostMapping("/add")
    public Result<Follow> addFollow(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long merchantId = Long.valueOf(params.get("merchantId").toString());

        Follow follow = followService.addFollow(userId, merchantId);
        return Result.success(follow);
    }

    /**
     * 取消关注
     */
    @DeleteMapping("/remove/{followId}")
    public Result<Void> removeFollow(@PathVariable Long followId) {
        followService.removeFollow(followId);
        return Result.success();
    }

    /**
     * 根据商户取消关注
     */
    @DeleteMapping("/remove")
    public Result<Void> removeFollowByMerchant(
            @RequestParam Long userId,
            @RequestParam Long merchantId) {
        followService.removeFollowByUserAndMerchant(userId, merchantId);
        return Result.success();
    }

    /**
     * 获取用户关注列表
     */
    @GetMapping("/list")
    public Result<List<FollowDTO>> getUserFollows(@RequestParam Long userId) {
        List<FollowDTO> follows = followService.getUserFollows(userId);
        return Result.success(follows);
    }

    /**
     * 检查是否已关注
     */
    @GetMapping("/check")
    public Result<Boolean> checkFollow(
            @RequestParam Long userId,
            @RequestParam Long merchantId) {
        boolean following = followService.isFollowing(userId, merchantId);
        return Result.success(following);
    }
}
