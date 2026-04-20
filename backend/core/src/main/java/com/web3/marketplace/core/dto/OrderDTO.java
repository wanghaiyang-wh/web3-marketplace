package com.web3.marketplace.core.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String orderNo;
    private Long userId;
    private String username;
    private Long merchantId;
    private String merchantName;
    private Long productId;
    private String productName;
    private String productType;
    private BigDecimal amount;
    private String payType;
    private String txHash;
    private Integer status;
    private LocalDateTime createTime;
}
