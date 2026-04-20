package com.web3.marketplace.core.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GameProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long merchantId;
    private String merchantName;
    private String name;
    private String description;
    private String image;
    private String category;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
}
