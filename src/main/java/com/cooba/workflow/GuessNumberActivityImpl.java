package com.cooba.workflow;

import com.cooba.constant.Status;
import com.cooba.dto.GuessRequest;
import com.cooba.entity.Order;
import com.cooba.service.OrderService;
import com.cooba.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuessNumberActivityImpl implements GuessNumberActivity {
    private final OrderService orderService;
    private final WalletService walletService;

    @Override
    public Order generateOrderActivity(GuessRequest request) {
        return orderService.generateOrder(request.getGuessNumber(), request.getBetAmount());
    }

    @Override
    public void subtractBalanceActivity(Order order) {
        if (order.getStatus() != Status.INIT ){
            throw new RuntimeException("Status Error");
        }

        try {
            walletService.subtract(order.getBetAmount());
        } catch (Exception e) {
            orderService.updateOrderStatus(order.getOrderNo(), Status.FAILED);
        }
        orderService.updateOrderStatus(order.getOrderNo(), Status.PENDING);
    }

    @Override
    public void updateOrderFailedActivity(Order order) {
        if (order.getStatus() == Status.PENDING) return;

        orderService.updateOrderStatus(order.getOrderNo(), Status.FAILED);
    }

    @Override
    public void settleOrderActivity(Order order, int resultNumber) {
        if (order.getStatus() != Status.PENDING ){
            throw new RuntimeException("Status Error");
        }

        orderService.settleOrder(order, resultNumber);
    }

    @Override
    public void addBalanceActivity(Order order) {
        if (order.getStatus() != Status.SETTLE ){
            throw new RuntimeException("Status Error");
        }
        if(order.getWinAmount() > 0) {
            walletService.add(order.getWinAmount());
        }
        orderService.updateOrderStatus(order.getOrderNo(), Status.AWARD);
    }

    @Override
    public void finalizeOrderActivity(Order order) {
        if (order.getStatus() != Status.AWARD ){
            throw new RuntimeException("Status Error");
        }
        orderService.updateOrderStatus(order.getOrderNo(), Status.DONE);

    }
}
