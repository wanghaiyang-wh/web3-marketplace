package com.web3.marketplace.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web3.marketplace.common.constant.StatusConstants;
import com.web3.marketplace.common.exception.BusinessException;
import com.web3.marketplace.core.dto.UserRegisterDTO;
import com.web3.marketplace.core.entity.User;
import com.web3.marketplace.core.mapper.UserMapper;
import com.web3.marketplace.core.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User register(UserRegisterDTO dto) {
        // 检查用户名是否存在
        if (getByUsername(dto.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }

        // 检查邮箱是否存在
        if (dto.getEmail() != null && getByEmail(dto.getEmail()) != null) {
            throw new BusinessException("邮箱已被注册");
        }

        // 检查钱包地址是否存在
        if (dto.getWalletAddress() != null && getByWalletAddress(dto.getWalletAddress()) != null) {
            throw new BusinessException("钱包地址已被注册");
        }

        // 创建用户
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encryptPassword(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setWalletAddress(dto.getWalletAddress());
        user.setStatus(StatusConstants.USER_STATUS_NORMAL);
        // 设置角色，默认是普通用户(买家)
        user.setRole(dto.getRole() != null ? dto.getRole() : "USER");

        save(user);
        log.info("用户注册成功: {}", user.getUsername());
        return user;
    }

    @Override
    public String login(String username, String password) {
        User user = getByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        if (user.getStatus() == StatusConstants.USER_STATUS_DISABLED) {
            throw new BusinessException("账户已被禁用");
        }

        String encryptedPassword = encryptPassword(password);
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 生成token
        String token = generateToken(user);
        log.info("用户登录成功: {}", username);
        return token;
    }

    @Override
    public User walletLogin(String walletAddress) {
        User user = getByWalletAddress(walletAddress);
        if (user == null) {
            // 钱包登录自动注册
            user = new User();
            // 使用钱包地址的哈希码生成唯一用户名，避免重复
            String baseUsername = "wallet_" + Math.abs(walletAddress.hashCode());
            String username = baseUsername;
            int suffix = 1;
            while (getByUsername(username) != null) {
                username = baseUsername + "_" + suffix++;
            }
            user.setUsername(username);
            user.setWalletAddress(walletAddress);
            user.setPassword(encryptPassword(IdUtil.fastSimpleUUID()));
            user.setStatus(StatusConstants.USER_STATUS_NORMAL);
            save(user);
            log.info("钱包用户自动注册: {}", walletAddress);
        }
        return user;
    }

    @Override
    public User getByUsername(String username) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public User getByWalletAddress(String walletAddress) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getWalletAddress, walletAddress));
    }

    private User getByEmail(String email) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
    }

    private String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(("web3_marketplace_" + password).getBytes());
    }

    @Override
    public String generateToken(User user) {
        return IdUtil.fastSimpleUUID();
    }
}
