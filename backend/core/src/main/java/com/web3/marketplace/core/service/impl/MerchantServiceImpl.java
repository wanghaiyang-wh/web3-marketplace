package com.web3.marketplace.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.marketplace.common.constant.StatusConstants;
import com.web3.marketplace.common.exception.BusinessException;
import com.web3.marketplace.core.dto.MerchantDTO;
import com.web3.marketplace.core.entity.Merchant;
import com.web3.marketplace.core.entity.User;
import com.web3.marketplace.core.mapper.MerchantMapper;
import com.web3.marketplace.core.mapper.UserMapper;
import com.web3.marketplace.core.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements MerchantService {

    private final UserMapper userMapper;

    @Override
    public Merchant register(Long userId, String shopName, String description, String contact) {
        // 检查用户是否已经是商家
        Merchant existingMerchant = getByUserIdEntity(userId);
        if (existingMerchant != null) {
            throw new BusinessException("您已经是商家");
        }

        // 检查店铺名是否已存在
        long count = count(new LambdaQueryWrapper<Merchant>().eq(Merchant::getShopName, shopName));
        if (count > 0) {
            throw new BusinessException("店铺名称已存在");
        }

        Merchant merchant = new Merchant();
        merchant.setUserId(userId);
        merchant.setShopName(shopName);
        merchant.setDescription(description);
        merchant.setContact(contact);
        merchant.setBalance(BigDecimal.ZERO);
        merchant.setPendingSettlement(BigDecimal.ZERO);
        merchant.setStatus(StatusConstants.MERCHANT_STATUS_NORMAL);

        save(merchant);
        log.info("商家注册成功: {}", shopName);
        return merchant;
    }

    @Override
    public MerchantDTO getByUserId(Long userId) {
        Merchant merchant = getByUserIdEntity(userId);
        if (merchant == null) {
            return null;
        }
        return convertToMerchantDTO(merchant);
    }

    private Merchant getByUserIdEntity(Long userId) {
        return getOne(new LambdaQueryWrapper<Merchant>().eq(Merchant::getUserId, userId));
    }

    @Override
    public MerchantDTO getMerchantById(Long id) {
        Merchant merchant = getById(id);
        if (merchant == null) {
            return null;
        }
        return convertToMerchantDTO(merchant);
    }

    @Override
    public IPage<MerchantDTO> getMerchantPage(Page<Merchant> page, String keyword, Integer status) {
        LambdaQueryWrapper<Merchant> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Merchant::getShopName, keyword);
        }
        if (status != null) {
            wrapper.eq(Merchant::getStatus, status);
        }
        wrapper.orderByDesc(Merchant::getCreateTime);

        IPage<Merchant> merchantPage = page(page, wrapper);
        return merchantPage.convert(this::convertToMerchantDTO);
    }

    @Override
    public Merchant updateMerchant(Merchant merchant) {
        if (getById(merchant.getId()) == null) {
            throw new BusinessException("商家不存在");
        }
        updateById(merchant);
        log.info("更新商家信息: {}", merchant.getShopName());
        return merchant;
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Merchant merchant = getById(id);
        if (merchant == null) {
            throw new BusinessException("商家不存在");
        }
        merchant.setStatus(status);
        updateById(merchant);
        log.info("更新商家状态: {} -> {}", merchant.getShopName(), status);
    }

    private MerchantDTO convertToMerchantDTO(Merchant merchant) {
        MerchantDTO dto = BeanUtil.copyProperties(merchant, MerchantDTO.class);

        User user = userMapper.selectById(merchant.getUserId());
        if (user != null) {
            dto.setUsername(user.getUsername());
        }
        return dto;
    }
}
