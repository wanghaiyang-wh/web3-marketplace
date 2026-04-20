package com.web3.marketplace.core.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String email;
    private String phone;
    private String walletAddress;
    private String role;  // USER-买家, MERCHANT-商户, ADMIN-管理员
}
