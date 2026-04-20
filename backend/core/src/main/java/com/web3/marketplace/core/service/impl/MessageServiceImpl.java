package com.web3.marketplace.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.marketplace.core.entity.Message;
import com.web3.marketplace.core.mapper.MessageMapper;
import com.web3.marketplace.core.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public Message sendMessage(Long senderId, Long receiverId, Long productId, String productType, String content) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setProductId(productId);
        message.setProductType(productType);
        message.setContent(content);
        message.setStatus(0);
        message.setIsRead(0);
        message.setCreateTime(LocalDateTime.now());

        save(message);
        log.info("发送消息: from={}, to={}", senderId, receiverId);
        return message;
    }

    @Override
    public Message sendSystemMessage(Long receiverId, String title, String content, String messageType, Long relatedId) {
        Message message = new Message();
        message.setReceiverId(receiverId);
        message.setTitle(title);
        message.setContent(content);
        message.setMessageType(messageType);
        message.setRelatedId(relatedId);
        message.setStatus(0);
        message.setIsRead(0);
        message.setCreateTime(LocalDateTime.now());

        save(message);
        log.info("发送系统消息: to={}, type={}", receiverId, messageType);
        return message;
    }

    @Override
    public IPage<Message> getReceivedMessages(Page<Message> page, Long userId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getReceiverId, userId);
        wrapper.orderByDesc(Message::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public IPage<Message> getMessagesByType(Page<Message> page, Long userId, String messageType) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getReceiverId, userId);
        if (messageType != null && !messageType.isEmpty()) {
            wrapper.eq(Message::getMessageType, messageType);
        }
        wrapper.orderByDesc(Message::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public IPage<Message> getSentMessages(Page<Message> page, Long userId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getSenderId, userId);
        wrapper.orderByDesc(Message::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public void markAsRead(Long messageId) {
        Message message = getById(messageId);
        if (message != null && message.getIsRead() == 0) {
            message.setIsRead(1);
            message.setStatus(1);
            updateById(message);
            log.info("标记消息已读: messageId={}", messageId);
        }
    }

    @Override
    public void batchMarkAsRead(Long userId, String messageType) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getReceiverId, userId);
        wrapper.eq(Message::getIsRead, 0);
        if (messageType != null && !messageType.isEmpty()) {
            wrapper.eq(Message::getMessageType, messageType);
        }

        // 批量更新
        list(wrapper).forEach(message -> {
            message.setIsRead(1);
            message.setStatus(1);
            updateById(message);
        });

        log.info("批量标记消息已读: userId={}, type={}", userId, messageType);
    }

    @Override
    public long getUnreadCount(Long userId) {
        return count(new LambdaQueryWrapper<Message>()
                .eq(Message::getReceiverId, userId)
                .eq(Message::getIsRead, 0));
    }

    @Override
    public long getUnreadCountByType(Long userId, String messageType) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getReceiverId, userId);
        wrapper.eq(Message::getIsRead, 0);
        if (messageType != null && !messageType.isEmpty()) {
            wrapper.eq(Message::getMessageType, messageType);
        }
        return count(wrapper);
    }

    @Override
    public void deleteMessage(Long messageId) {
        removeById(messageId);
        log.info("删除消息: messageId={}", messageId);
    }
}
