package com.web3.marketplace.core.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class FollowDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long merchantId;
    private LocalDateTime createTime;

    // 商户信息
    private String merchantName;
    private String merchantImage;
    private String merchantDescription;
}
