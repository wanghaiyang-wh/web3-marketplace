package com.web3.marketplace.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.marketplace.core.dto.FollowDTO;
import com.web3.marketplace.core.entity.Follow;

import java.util.List;

public interface FollowService extends IService<Follow> {

    /**
     * 关注店铺
     */
    Follow addFollow(Long userId, Long merchantId);

    /**
     * 取消关注
     */
    void removeFollow(Long followId);

    /**
     * 取消关注(根据用户和商户)
     */
    void removeFollowByUserAndMerchant(Long userId, Long merchantId);

    /**
     * 获取用户关注列表
     */
    List<FollowDTO> getUserFollows(Long userId);

    /**
     * 检查是否已关注
     */
    boolean isFollowing(Long userId, Long merchantId);
}
