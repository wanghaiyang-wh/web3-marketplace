package com.web3.marketplace.client.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web3.marketplace.common.result.PageResult;
import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.entity.Message;
import com.web3.marketplace.core.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/client/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public Result<Message> sendMessage(@RequestBody Map<String, Object> request) {
        if (request.get("senderId") == null || request.get("receiverId") == null ||
            request.get("productId") == null || request.get("productType") == null ||
            request.get("content") == null) {
            return Result.error(400, "缺少必要参数");
        }
        Long senderId = Long.valueOf(request.get("senderId").toString());
        Long receiverId = Long.valueOf(request.get("receiverId").toString());
        Long productId = Long.valueOf(request.get("productId").toString());
        String productType = request.get("productType").toString();
        String content = request.get("content").toString();

        Message message = messageService.sendMessage(senderId, receiverId, productId, productType, content);
        return Result.success(message);
    }

    @GetMapping("/received")
    public Result<PageResult<Message>> getReceivedMessages(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {

        Page<Message> page = new Page<>(current, size);
        IPage<Message> result = messageService.getReceivedMessages(page, userId);

        return Result.success(PageResult.of(
                result.getRecords(),
                result.getTotal(),
                result.getCurrent(),
                result.getSize()
        ));
    }

    @GetMapping("/sent")
    public Result<PageResult<Message>> getSentMessages(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {

        Page<Message> page = new Page<>(current, size);
        IPage<Message> result = messageService.getSentMessages(page, userId);

        return Result.success(PageResult.of(
                result.getRecords(),
                result.getTotal(),
                result.getCurrent(),
                result.getSize()
        ));
    }

    @GetMapping("/unread")
    public Result<Long> getUnreadCount(@RequestParam Long userId) {
        return Result.success(messageService.getUnreadCount(userId));
    }

    @PostMapping("/read/{id}")
    public Result<Void> markAsRead(@PathVariable Long id) {
        messageService.markAsRead(id);
        return Result.success();
    }
}
