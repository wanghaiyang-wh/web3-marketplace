package com.web3.marketplace.merchant.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web3.marketplace.common.result.PageResult;
import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.OrderDTO;
import com.web3.marketplace.core.entity.Order;
import com.web3.marketplace.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merchant/order")
@RequiredArgsConstructor
public class OrderManageController {

    private final OrderService orderService;

    @GetMapping("/list")
    public Result<PageResult<OrderDTO>> getOrderList(
            @RequestParam Long merchantId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) Integer status) {

        Page<Order> page = new Page<>(current, size);
        IPage<OrderDTO> result = orderService.getMerchantOrderPage(page, merchantId, status);

        return Result.success(PageResult.of(
                result.getRecords(),
                result.getTotal(),
                result.getCurrent(),
                result.getSize()
        ));
    }

    @GetMapping("/{id}")
    public Result<OrderDTO> getOrderDetail(@PathVariable Long id) {
        return Result.success(orderService.getOrderById(id));
    }

    @PostMapping("/complete/{id}")
    public Result<Void> completeOrder(@PathVariable Long id) {
        orderService.completeOrder(id);
        return Result.success();
    }
}
