package com.web3.marketplace.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.marketplace.core.entity.Message;

public interface MessageService extends IService<Message> {

    /**
     * 发送消息
     */
    Message sendMessage(Long senderId, Long receiverId, Long productId, String productType, String content);

    /**
     * 发送系统消息
     */
    Message sendSystemMessage(Long receiverId, String title, String content, String messageType, Long relatedId);

    /**
     * 查询收到的消息
     */
    IPage<Message> getReceivedMessages(Page<Message> page, Long userId);

    /**
     * 按类型查询消息
     */
    IPage<Message> getMessagesByType(Page<Message> page, Long userId, String messageType);

    /**
     * 查询发送的消息
     */
    IPage<Message> getSentMessages(Page<Message> page, Long userId);

    /**
     * 标记消息为已读
     */
    void markAsRead(Long messageId);

    /**
     * 批量标记为已读
     */
    void batchMarkAsRead(Long userId, String messageType);

    /**
     * 获取未读消息数量
     */
    long getUnreadCount(Long userId);

    /**
     * 按类型获取未读数量
     */
    long getUnreadCountByType(Long userId, String messageType);

    /**
     * 删除消息
     */
    void deleteMessage(Long messageId);
}
