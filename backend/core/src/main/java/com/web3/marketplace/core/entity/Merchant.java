package com.web3.marketplace.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_merchant")
public class Merchant {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String shopName;
    private String description;
    private String contact;
    private BigDecimal balance;
    private BigDecimal pendingSettlement;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
