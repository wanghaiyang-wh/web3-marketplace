package com.web3.marketplace.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.marketplace.common.constant.StatusConstants;
import com.web3.marketplace.core.dto.BrowseHistoryDTO;
import com.web3.marketplace.core.dto.GameProductDTO;
import com.web3.marketplace.core.dto.NftProductDTO;
import com.web3.marketplace.core.entity.BrowseHistory;
import com.web3.marketplace.core.mapper.BrowseHistoryMapper;
import com.web3.marketplace.core.service.BrowseHistoryService;
import com.web3.marketplace.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrowseHistoryServiceImpl extends ServiceImpl<BrowseHistoryMapper, BrowseHistory> implements BrowseHistoryService {

    private final ProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BrowseHistory addHistory(Long userId, Long productId, String productType) {
        // 检查是否已存在，存在则更新时间
        LambdaQueryWrapper<BrowseHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BrowseHistory::getUserId, userId)
               .eq(BrowseHistory::getProductId, productId)
               .eq(BrowseHistory::getProductType, productType);
        BrowseHistory existing = getOne(wrapper);

        if (existing != null) {
            existing.setCreateTime(LocalDateTime.now());
            updateById(existing);
            return existing;
        }

        // 新增记录
        BrowseHistory history = new BrowseHistory();
        history.setUserId(userId);
        history.setProductId(productId);
        history.setProductType(productType);
        history.setCreateTime(LocalDateTime.now());

        save(history);
        log.info("添加浏览记录: userId={}, productId={}, type={}", userId, productId, productType);
        return history;
    }

    @Override
    public List<BrowseHistoryDTO> getUserHistory(Long userId) {
        LambdaQueryWrapper<BrowseHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BrowseHistory::getUserId, userId);
        wrapper.orderByDesc(BrowseHistory::getCreateTime);
        wrapper.last("LIMIT 50"); // 最多显示50条
        List<BrowseHistory> histories = list(wrapper);

        List<BrowseHistoryDTO> dtoList = new ArrayList<>();
        for (BrowseHistory history : histories) {
            BrowseHistoryDTO dto = BeanUtil.copyProperties(history, BrowseHistoryDTO.class);

            try {
                if (StatusConstants.PRODUCT_TYPE_NFT.equals(history.getProductType())) {
                    NftProductDTO product = productService.getNftProductById(history.getProductId());
                    if (product != null) {
                        dto.setProductName(product.getName());
                        dto.setProductImage(product.getImage());
                        dto.setProductPrice(product.getPrice());
                    }
                } else if (StatusConstants.PRODUCT_TYPE_GAME.equals(history.getProductType())) {
                    GameProductDTO product = productService.getGameProductById(history.getProductId());
                    if (product != null) {
                        dto.setProductName(product.getName());
                        dto.setProductImage(product.getImage());
                        dto.setProductPrice(product.getPrice());
                    }
                }
            } catch (Exception e) {
                log.error("获取浏览历史商品信息失败: historyId={}", history.getId(), e);
            }

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearUserHistory(Long userId) {
        LambdaQueryWrapper<BrowseHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BrowseHistory::getUserId, userId);
        remove(wrapper);
        log.info("清空浏览历史: userId={}", userId);
    }
}
