package com.web3.marketplace.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.marketplace.common.exception.BusinessException;
import com.web3.marketplace.core.dto.FollowDTO;
import com.web3.marketplace.core.dto.MerchantDTO;
import com.web3.marketplace.core.entity.Follow;
import com.web3.marketplace.core.mapper.FollowMapper;
import com.web3.marketplace.core.service.FollowService;
import com.web3.marketplace.core.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    private final MerchantService merchantService;

    @Override
    public Follow addFollow(Long userId, Long merchantId) {
        // 检查是否已关注
        if (isFollowing(userId, merchantId)) {
            throw new BusinessException("您已经关注过该店铺");
        }

        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setMerchantId(merchantId);
        follow.setCreateTime(LocalDateTime.now());

        save(follow);
        log.info("关注店铺: userId={}, merchantId={}", userId, merchantId);
        return follow;
    }

    @Override
    public void removeFollow(Long followId) {
        removeById(followId);
        log.info("取消关注: followId={}", followId);
    }

    @Override
    public void removeFollowByUserAndMerchant(Long userId, Long merchantId) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getUserId, userId)
               .eq(Follow::getMerchantId, merchantId);
        remove(wrapper);
        log.info("取消关注: userId={}, merchantId={}", userId, merchantId);
    }

    @Override
    public List<FollowDTO> getUserFollows(Long userId) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getUserId, userId);
        wrapper.orderByDesc(Follow::getCreateTime);
        List<Follow> follows = list(wrapper);

        List<FollowDTO> dtoList = new ArrayList<>();
        for (Follow follow : follows) {
            FollowDTO dto = BeanUtil.copyProperties(follow, FollowDTO.class);

            try {
                MerchantDTO merchant = merchantService.getMerchantById(follow.getMerchantId());
                if (merchant != null) {
                    dto.setMerchantName(merchant.getShopName());
                    dto.setMerchantDescription(merchant.getDescription());
                }
            } catch (Exception e) {
                log.error("获取关注店铺信息失败: followId={}", follow.getId(), e);
            }

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public boolean isFollowing(Long userId, Long merchantId) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getUserId, userId)
               .eq(Follow::getMerchantId, merchantId);
        return count(wrapper) > 0;
    }
}
