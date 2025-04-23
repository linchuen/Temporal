package com.cooba.workflow;

import com.cooba.entity.Order;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class GuessNumberWorkflowImpl implements GuessNumberWorkflow {
    private Integer resultNumber;
    private final GuessNumberActivity activities = Workflow.newActivityStub(
            GuessNumberActivity.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(5))
                    .build()
    );

    @Override
    public void guessNumber(Order order) {
        String orderNo = order.getOrderNo();
        int betAmount = order.getBetAmount();
        boolean isSuccess = activities.subtractBalanceActivity(orderNo, betAmount);
        if (!isSuccess) return;

        Workflow.await(() -> this.resultNumber != null); // 等待 signal

        activities.settleOrderActivity(orderNo, this.resultNumber);

        activities.addBalanceActivity(orderNo);

        activities.finalizeOrderActivity(orderNo);
    }

    @Override
    public void drawNumber(int resultNumber) {
        this.resultNumber = resultNumber;
    }

    @Override
    public boolean isOrderCompleted() {
        return false;
    }
}
