package com.web3.marketplace.client.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.web3.marketplace.common.constant.StatusConstants;
import com.web3.marketplace.core.entity.Order;
import com.web3.marketplace.core.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// @Slf4j
// @Component
public class OrderCancelTask {

    // private final OrderMapper orderMapper;

    /**
     * 每分钟检查并取消超时未支付的订单（30分钟超时）
     */
    // @Scheduled(cron = "0 * * * * ?")
    // @Transactional(rollbackFor = Exception.class)
    public void cancelExpiredOrders() {
        LocalDateTime expireTime = LocalDateTime.now().minusMinutes(30);

        // 查询超时未支付的订单
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getStatus, StatusConstants.ORDER_STATUS_PENDING)
               .lt(Order::getCreateTime, expireTime);

        List<Order> expiredOrders = orderMapper.selectList(wrapper);

        if (!expiredOrders.isEmpty()) {
            log.info("开始取消 {} 个超时订单", expiredOrders.size());
            for (Order order : expiredOrders) {
                order.setStatus(StatusConstants.ORDER_STATUS_CANCELLED);
                orderMapper.updateById(order);
                log.info("订单 {} 已自动取消", order.getOrderNo());
            }
            log.info("自动取消超时订单完成，共取消 {} 个", expiredOrders.size());
        }
    }
}
