package com.cooba.workflow;

import com.cooba.entity.Order;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface GuessNumberWorkflow {

    @WorkflowMethod
    void guessNumber(Order order);

    @SignalMethod
    void drawNumber(int resultNumber);
}
