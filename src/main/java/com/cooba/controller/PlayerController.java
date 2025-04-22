package com.cooba.controller;

import com.cooba.constant.Status;
import com.cooba.dto.GuessRequest;
import com.cooba.dto.GuessResponse;
import com.cooba.dto.SettleResponse;
import com.cooba.entity.Order;
import com.cooba.entity.Wallet;
import com.cooba.service.OrderService;
import com.cooba.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlayerController {
    private final OrderService orderService;
    private final WalletService walletService;

    @PostMapping("/guess")
    @Transactional
    public ResponseEntity<GuessResponse> guess(@RequestBody GuessRequest request) {
        GuessResponse response = bet(request);
        if (response.getStatus() == Status.FAILED) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    private GuessResponse bet(GuessRequest request) {
        Order newOrder;
        try {
            newOrder = orderService.generateOrder(request.getGuessNumber(), request.getBetAmount());
        } catch (Exception e) {
            return new GuessResponse(null, 0, Status.FAILED, 0, null, "訂單生成失敗", walletService.getWallet().getBalance());
        }

        try {
            Wallet wallet = walletService.subtract(request.getBetAmount());
            orderService.updateOrderStatus(newOrder.getOrderNo(), Status.PENDING);
            return new GuessResponse(
                    newOrder.getOrderNo(),
                    newOrder.getOdds(),
                    Status.PENDING,
                    newOrder.getRound(),
                    newOrder.getCreatedTime(),
                    null,
                    wallet.getBalance()
            );
        } catch (Exception e) {
            orderService.updateOrderStatus(newOrder.getOrderNo(), Status.FAILED);
            return new GuessResponse(
                    newOrder.getOrderNo(),
                    newOrder.getOdds(),
                    Status.FAILED,
                    newOrder.getRound(),
                    newOrder.getCreatedTime(),
                    "餘額不足",
                    walletService.getWallet().getBalance()
            );
        }
    }

    @PostMapping("/settle")
    @Transactional
    public ResponseEntity<SettleResponse> settle() {
        SettleResponse response = settleInternal();
        if (response.getMessage() != null) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    private SettleResponse settleInternal() {
        int resultNumber = new Random().nextInt(10) + 1;

        List<Order> orders;
        try {
            orders = orderService.settleOrders(resultNumber);
        } catch (Exception e) {
            return new SettleResponse(0, 0, 0, e.getMessage());
        }

        int totalWinAmount = 0;

        for (Order order : orders) {
            if (order.getStatus() == Status.SETTLE && order.getWinAmount() > 0) {
                walletService.add(order.getWinAmount());
            }

            if (order.getStatus() == Status.SETTLE || order.getStatus() == Status.AWARD) {
                orderService.updateOrderStatus(order.getOrderNo(), Status.AWARD);
            }

            if (order.getStatus() == Status.AWARD) {
                orderService.updateOrderStatus(order.getOrderNo(), Status.DONE);
                totalWinAmount += order.getWinAmount();
            }
        }

        orderService.generateNextRound();

        return new SettleResponse(
                orders.isEmpty() ? 0 : orders.get(0).getRound(),
                orders.isEmpty() ? 0 : orders.get(0).getResultNumber(),
                totalWinAmount,
                null
        );
    }
}

