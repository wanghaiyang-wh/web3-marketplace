package com.web3.marketplace.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_ticket_reply")
public class TicketReply {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long ticketId;
    private Long senderId;
    private String senderType;  // USER,MERCHANT,ADMIN
    private String content;
    private LocalDateTime createTime;
}
