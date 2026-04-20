package com.web3.marketplace.client.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web3.marketplace.common.result.PageResult;
import com.web3.marketplace.common.result.Result;
import com.web3.marketplace.core.dto.OrderDTO;
import com.web3.marketplace.core.entity.Order;
import com.web3.marketplace.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/client/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public Result<Order> createOrder(@RequestBody Map<String, Object> request) {
        if (request.get("userId") == null || request.get("merchantId") == null ||
            request.get("productId") == null || request.get("productType") == null ||
            request.get("payType") == null) {
            return Result.error(400, "缺少必要参数");
        }
        Long userId = Long.valueOf(request.get("userId").toString());
        Long merchantId = Long.valueOf(request.get("merchantId").toString());
        Long productId = Long.valueOf(request.get("productId").toString());
        String productType = request.get("productType").toString();
        String payType = request.get("payType").toString();

        Order order = orderService.createOrder(userId, merchantId, productId, productType, payType);
        return Result.success(order);
    }

    @PostMapping("/pay")
    public Result<Void> payOrder(@RequestBody Map<String, Object> request) {
        if (request.get("orderId") == null) {
            return Result.error(400, "缺少订单ID");
        }
        Long orderId = Long.valueOf(request.get("orderId").toString());
        String txHash = request.get("txHash") != null ? request.get("txHash").toString() : null;
        orderService.payOrder(orderId, txHash);
        return Result.success();
    }

    @PostMapping("/cancel/{id}")
    public Result<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<PageResult<OrderDTO>> getOrderList(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) Integer status) {

        Page<Order> page = new Page<>(current, size);
        IPage<OrderDTO> result = orderService.getUserOrderPage(page, userId, status);

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
}
