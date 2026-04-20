package com.web3.marketplace.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.marketplace.core.entity.MerchantApply;

import java.util.Map;

public interface MerchantApplyService extends IService<MerchantApply> {

    /**
     * 提交入驻申请
     */
    MerchantApply submitApply(Map<String, Object> params);

    /**
     * 查询用户申请状态
     */
    MerchantApply getUserApply(Long userId);

    /**
     * 审核申请
     */
    void reviewApply(Long applyId, Integer status, String rejectReason);

    /**
     * 分页查询申请列表
     */
    IPage<MerchantApply> getApplyPage(Page<MerchantApply> page, Integer status);
}
