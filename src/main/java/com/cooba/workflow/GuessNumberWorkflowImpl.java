package com.cooba.workflow;

import com.cooba.dto.GuessRequest;
import com.cooba.entity.Order;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class GuessNumberWorkflowImpl implements GuessNumberWorkflow {
    private GuessRequest request;

    private final GuessNumberActivity activities = Workflow.newActivityStub(
            GuessNumberActivity.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(5))
                    .build()
    );

    @Override
    public String guessNumber(GuessRequest request) {
        Order order = activities.generateOrderActivity(request.getGuessNumber(), request.getBetAmount());
        activities.subtractBalanceActivity(order);
        activities.settleOrderActivity(order);
        activities.addBalanceActivity(order);
        return "";
    }

    @Override
    public void drawNumber(int resultNumber) {

    }

    @Override
    public boolean isOrderCompleted() {
        return false;
    }
}
