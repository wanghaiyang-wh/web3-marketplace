package com.web3.marketplace.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web3.marketplace.common.result.PageResult;
import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.OrderDTO;
import com.web3.marketplace.core.entity.Order;
import com.web3.marketplace.core.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/order")
@RequiredArgsConstructor
public class OrderManageController {

    private final OrderMapper orderMapper;

    @GetMapping("/list")
    public Result<PageResult<Order>> getOrderList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) Integer status) {

        Page<Order> page = new Page<>(current, size);
        IPage<Order> result = orderMapper.selectPage(page, null);

        return Result.success(PageResult.of(
                result.getRecords(),
                result.getTotal(),
                result.getCurrent(),
                result.getSize()
        ));
    }

    @GetMapping("/{id}")
    public Result<Order> getOrderDetail(@PathVariable Long id) {
        return Result.success(orderMapper.selectById(id));
    }
}
