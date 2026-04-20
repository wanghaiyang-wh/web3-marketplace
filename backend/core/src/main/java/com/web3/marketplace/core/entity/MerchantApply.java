package com.web3.marketplace.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_merchant_apply")
public class MerchantApply {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String shopName;
    private String description;
    private String contact;
    private String licenseUrl;
    private String idCardUrl;
    private Integer status;        // 0-待审核 1-通过 2-拒绝
    private String rejectReason;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
