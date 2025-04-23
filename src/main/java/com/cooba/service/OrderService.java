package com.cooba.service;

import com.cooba.constant.Status;
import com.cooba.entity.Order;
import com.cooba.mapper.OrderMapper;
import com.cooba.mapper.RoundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final RoundMapper roundMapper;


    public Order generateOrder(int guessNumber, int betAmount) {
        int currentRound = getRound();

        String orderNo = String.format("ORD-%d-%d", currentRound, System.nanoTime());

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setRound(currentRound);
        order.setGuessNumber(guessNumber);
        order.setBetAmount(betAmount);
        order.setOdds(10);
        order.setStatus(Status.INIT);
        order.setCreatedTime(LocalDateTime.now());
        order.setUpdatedTime(LocalDateTime.now());

        orderMapper.insert(order);
        return order;
    }

    public void updateOrderStatus(String orderNo, Status status) {
        orderMapper.updateStatusByOrderNo(orderNo, status);
    }

    public List<Order> settleOrders(int resultNumber) {
        int currentRound = roundMapper.getLatestRound();
        System.out.printf("SettleOrder: currentRound=%d, resultNumber=%d%n", currentRound, resultNumber);

        List<Order> orders = orderMapper.getOrdersByRound(currentRound);
        System.out.println("SettleOrder: orders=" + orders);

        for (Order order : orders) {
            settleOrder(order, resultNumber);
        }

        return orders;
    }

    public Order findOrder(String orderNo) {
        return orderMapper.getOrderByOrderNo(orderNo);
    }

    public void settleOrder(Order order, int resultNumber) {
        if (order.getStatus() != Status.PENDING) {
            return;
        }

        order.setResultNumber(resultNumber);
        order.setUpdatedTime(LocalDateTime.now());

        if (order.getGuessNumber() == resultNumber) {
            order.setOdds(10);
            order.setWinAmount(order.getBetAmount() * 10);
            order.setStatus(Status.SETTLE);
            order.setResult("WIN");
        } else {
            order.setOdds(0);
            order.setWinAmount(0);
            order.setStatus(Status.SETTLE);
            order.setResult("LOSE");
        }

        orderMapper.updateById(order);
    }

    public int getRound() {
        Integer currentRound = roundMapper.getLatestRound();
        return currentRound == null ? 0 : currentRound;
    }

    public void generateNextRound() {
        roundMapper.generateNextRound();
    }
}
