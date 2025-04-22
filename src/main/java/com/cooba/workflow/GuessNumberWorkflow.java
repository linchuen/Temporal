package com.cooba.workflow;

import com.cooba.dto.GuessRequest;
import com.cooba.entity.Order;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface GuessNumberWorkflow {

    @WorkflowMethod
    Order guessNumber(GuessRequest request);

    @SignalMethod
    void drawNumber(int resultNumber);

    @QueryMethod
    boolean isOrderCompleted();
}
