package com.web3.marketplace.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web3.marketplace.common.result.PageResult;
import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.entity.User;
import com.web3.marketplace.core.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class UserManageController {

    private final UserMapper userMapper;

    @GetMapping("/list")
    public Result<PageResult<User>> getUserList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status) {

        Page<User> page = new Page<>(current, size);
        IPage<User> result = userMapper.selectPage(page, null);

        return Result.success(PageResult.of(
                result.getRecords(),
                result.getTotal(),
                result.getCurrent(),
                result.getSize()
        ));
    }

    @GetMapping("/{id}")
    public Result<User> getUserDetail(@PathVariable Long id) {
        return Result.success(userMapper.selectById(id));
    }

    @PutMapping("/status/{id}")
    public Result<Void> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userMapper.updateById(user);
        return Result.success();
    }
}
