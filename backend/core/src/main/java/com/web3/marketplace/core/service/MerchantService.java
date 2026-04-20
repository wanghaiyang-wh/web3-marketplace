package com.web3.marketplace.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.marketplace.core.dto.MerchantDTO;
import com.web3.marketplace.core.entity.Merchant;

public interface MerchantService extends IService<Merchant> {

    /**
     * 商家注册
     */
    Merchant register(Long userId, String shopName, String description, String contact);

    /**
     * 根据用户ID查询商家
     */
    MerchantDTO getByUserId(Long userId);

    /**
     * 根据ID查询商家详情
     */
    MerchantDTO getMerchantById(Long id);

    /**
     * 商家列表（管理员）
     */
    IPage<MerchantDTO> getMerchantPage(Page<Merchant> page, String keyword, Integer status);

    /**
     * 更新商家信息
     */
    Merchant updateMerchant(Merchant merchant);

    /**
     * 冻结/解冻商家
     */
    void updateStatus(Long id, Integer status);
}
