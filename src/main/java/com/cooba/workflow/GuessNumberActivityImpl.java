package com.cooba.workflow;

import com.cooba.constant.Status;
import com.cooba.entity.Order;
import com.cooba.service.OrderService;
import com.cooba.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GuessNumberActivityImpl implements GuessNumberActivity {
    private final OrderService orderService;
    private final WalletService walletService;

    @Override
    public boolean subtractBalanceActivity(String orderNo, int betAmount) {
        Order order = orderService.findOrder(orderNo);
        if (order.getStatus() == Status.FAILED) return false;
        if (order.getStatus() == Status.PENDING) return true;

        if (order.getStatus() != Status.INIT) {
            log.warn("{} status is not init", order);
            throw new RuntimeException("Status Error");
        }

        try {
            walletService.subtract(betAmount);
        } catch (Exception e) {
            if (e.getMessage().equals("insufficient balance")) {
                orderService.updateOrderStatus(orderNo, Status.FAILED);
                return false;
            }
            throw new RuntimeException(e);
        }
        orderService.updateOrderStatus(orderNo, Status.PENDING);
        return true;
    }

    @Override
    public void settleOrderActivity(String orderNo, int resultNumber) {
        Order order = orderService.findOrder(orderNo);
        if (order.getStatus() == Status.SETTLE) return;

        if (order.getStatus() != Status.PENDING) {
            log.warn("{} status is not pending", order);
            throw new RuntimeException("Status Error");
        }

        orderService.settleOrder(order, resultNumber);
    }

    @Override
    public void addBalanceActivity(String orderNo) {
        Order order = orderService.findOrder(orderNo);
        if (order.getStatus() == Status.AWARD) return;

        if (order.getStatus() != Status.SETTLE) {
            log.warn("{} status is not settle", order);
            throw new RuntimeException("Status Error");
        }
        if (order.getWinAmount() > 0) {
            walletService.add(order.getWinAmount());
        }
        orderService.updateOrderStatus(order.getOrderNo(), Status.AWARD);
        order.setStatus(Status.FAILED);
    }

    @Override
    public void finalizeOrderActivity(String orderNo) {
        Order order = orderService.findOrder(orderNo);
        if (order.getStatus() == Status.DONE) return;

        if (order.getStatus() != Status.AWARD) {
            log.warn("{} status is not award", order);
            throw new RuntimeException("Status Error");
        }
        orderService.updateOrderStatus(order.getOrderNo(), Status.DONE);
        order.setStatus(Status.DONE);

    }
}
