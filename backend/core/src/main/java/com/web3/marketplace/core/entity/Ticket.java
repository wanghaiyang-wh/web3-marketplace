package com.web3.marketplace.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_ticket")
public class Ticket {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String ticketNo;
    private Long userId;
    private Long merchantId;
    private String type;        // 咨询,投诉,售后
    private String title;
    private String content;
    private Integer status;    // 0-待处理 1-处理中 2-已完成 3-已关闭
    private Long adminId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
