package com.web3.marketplace.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String walletAddress;
    private Integer status;
    private String role;  // 角色: USER-普通用户(买家), MERCHANT-商户, ADMIN-管理员
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
