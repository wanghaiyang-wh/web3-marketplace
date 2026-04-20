package com.web3.marketplace.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_offer")
public class Offer {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId;
    private String productType;
    private Long buyerId;
    private Long merchantId;
    private BigDecimal price;
    private String status;
    private String message;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
