package com.web3.marketplace.client.controller;

import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.UserRegisterDTO;
import com.web3.marketplace.core.entity.User;
import com.web3.marketplace.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<Map<String, Object>> register(@RequestBody UserRegisterDTO dto) {
        User user = userService.register(dto);
        String token = userService.login(user.getUsername(), dto.getPassword());

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        data.put("token", token);
        return Result.success(data);
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody UserRegisterDTO dto) {
        String token = userService.login(dto.getUsername(), dto.getPassword());
        User user = userService.getByUsername(dto.getUsername());

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        data.put("role", user.getRole());
        data.put("token", token);
        return Result.success(data);
    }

    @PostMapping("/wallet/login")
    public Result<Map<String, Object>> walletLogin(@RequestBody Map<String, String> request) {
        String walletAddress = request.get("walletAddress");
        if (walletAddress == null || walletAddress.isEmpty()) {
            return Result.error(400, "钱包地址不能为空");
        }
        User user = userService.walletLogin(walletAddress);
        String token = userService.generateToken(user);

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        data.put("role", user.getRole());
        data.put("walletAddress", user.getWalletAddress());
        data.put("token", token);
        return Result.success(data);
    }

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestParam Long userId) {
        return Result.success(userService.getById(userId));
    }
}
