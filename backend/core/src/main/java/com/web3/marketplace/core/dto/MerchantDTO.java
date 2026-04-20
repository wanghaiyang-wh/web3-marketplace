package com.web3.marketplace.core.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MerchantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String username;
    private String shopName;
    private String description;
    private String contact;
    private BigDecimal balance;
    private BigDecimal pendingSettlement;
    private Integer productCount;
    private Integer status;
}
