package com.web3.marketplace.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_message")
public class Message {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long senderId;
    private Long receiverId;
    private Long productId;
    private String productType;
    private String content;
    private Integer status;
    private LocalDateTime createTime;

    // 扩展字段
    private String messageType;      // 消息类型: SYSTEM-系统通知 ORDER-订单通知 OFFER-议价通知
    private Long relatedId;          // 关联ID (订单ID/商品ID等)
    private Integer isRead;          // 是否已读: 0-未读 1-已读
    private String title;           // 消息标题
}
