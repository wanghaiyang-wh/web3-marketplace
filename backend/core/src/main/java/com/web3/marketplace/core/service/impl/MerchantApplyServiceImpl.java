package com.web3.marketplace.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.marketplace.common.exception.BusinessException;
import com.web3.marketplace.core.entity.MerchantApply;
import com.web3.marketplace.core.mapper.MerchantApplyMapper;
import com.web3.marketplace.core.service.MerchantApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantApplyServiceImpl extends ServiceImpl<MerchantApplyMapper, MerchantApply> implements MerchantApplyService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MerchantApply submitApply(Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        String shopName = params.get("shopName").toString();

        // 检查是否有待处理的申请
        MerchantApply existing = getUserApply(userId);
        if (existing != null && existing.getStatus() == 0) {
            throw new BusinessException("您有待审核的申请，请勿重复提交");
        }

        MerchantApply apply = new MerchantApply();
        apply.setUserId(userId);
        apply.setShopName(shopName);
        apply.setDescription((String) params.get("description"));
        apply.setContact((String) params.get("contact"));
        apply.setLicenseUrl((String) params.get("licenseUrl"));
        apply.setIdCardUrl((String) params.get("idCardUrl"));
        apply.setStatus(0);
        apply.setCreateTime(LocalDateTime.now());

        save(apply);
        log.info("提交入驻申请: userId={}, shopName={}", userId, shopName);
        return apply;
    }

    @Override
    public MerchantApply getUserApply(Long userId) {
        LambdaQueryWrapper<MerchantApply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MerchantApply::getUserId, userId);
        wrapper.orderByDesc(MerchantApply::getCreateTime);
        wrapper.last("LIMIT 1");
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reviewApply(Long applyId, Integer status, String rejectReason) {
        MerchantApply apply = getById(applyId);
        if (apply == null) {
            throw new BusinessException("申请不存在");
        }
        if (apply.getStatus() != 0) {
            throw new BusinessException("申请已处理");
        }

        apply.setStatus(status);
        apply.setRejectReason(rejectReason);
        apply.setUpdateTime(LocalDateTime.now());
        updateById(apply);

        log.info("审核入驻申请: applyId={}, status={}", applyId, status);
    }

    @Override
    public IPage<MerchantApply> getApplyPage(Page<MerchantApply> page, Integer status) {
        LambdaQueryWrapper<MerchantApply> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(MerchantApply::getStatus, status);
        }
        wrapper.orderByDesc(MerchantApply::getCreateTime);
        return page(page, wrapper);
    }
}
