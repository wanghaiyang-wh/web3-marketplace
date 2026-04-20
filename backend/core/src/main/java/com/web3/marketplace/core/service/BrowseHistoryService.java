package com.web3.marketplace.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.marketplace.core.dto.BrowseHistoryDTO;
import com.web3.marketplace.core.entity.BrowseHistory;

import java.util.List;

public interface BrowseHistoryService extends IService<BrowseHistory> {

    /**
     * 添加浏览记录
     */
    BrowseHistory addHistory(Long userId, Long productId, String productType);

    /**
     * 获取用户浏览历史
     */
    List<BrowseHistoryDTO> getUserHistory(Long userId);

    /**
     * 清空用户浏览历史
     */
    void clearUserHistory(Long userId);
}
