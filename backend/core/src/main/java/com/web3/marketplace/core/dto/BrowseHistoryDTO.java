package com.web3.marketplace.core.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BrowseHistoryDTO implements Serializable {
    private Long id;
    private Long userId;
    private Long productId;
    private String productType;
    private LocalDateTime createTime;

    // 商品信息
    private String productName;
    private String productImage;
    private BigDecimal productPrice;
}
