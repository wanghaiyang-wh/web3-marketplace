package com.web3.marketplace.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long merchantId;
    private Long productId;
    private String productType;
    private BigDecimal amount;
    private String payType;
    private String txHash;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
