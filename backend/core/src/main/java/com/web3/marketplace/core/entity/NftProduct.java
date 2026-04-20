package com.web3.marketplace.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_nft_product")
public class NftProduct {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long merchantId;
    private String name;
    private String description;
    private String image;
    private String contractAddress;
    private String tokenId;
    private String chain;
    private Integer rarity;          // 稀有度: 1-普通 2-稀有 3-史诗 4-传说 5-神话
    private BigDecimal wearValue;   // 磨损值: 0.0-1.0
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
