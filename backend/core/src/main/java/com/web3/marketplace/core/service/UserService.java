package com.web3.marketplace.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web3.marketplace.core.dto.UserRegisterDTO;
import com.web3.marketplace.core.entity.User;

public interface UserService extends IService<User> {

    /**
     * 用户注册
     */
    User register(UserRegisterDTO dto);

    /**
     * 用户登录
     */
    String login(String username, String password);

    /**
     * 钱包登录/注册
     */
    User walletLogin(String walletAddress);

    /**
     * 根据用户名查询
     */
    User getByUsername(String username);

    /**
     * 根据钱包地址查询
     */
    User getByWalletAddress(String walletAddress);

    /**
     * 生成用户token
     */
    String generateToken(User user);
}
