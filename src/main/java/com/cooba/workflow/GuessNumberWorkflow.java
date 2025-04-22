package com.cooba.workflow;

import com.cooba.dto.GuessRequest;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface GuessNumberWorkflow {

    @WorkflowMethod
    String guessNumber(GuessRequest request);

    @SignalMethod
    void drawNumber(int resultNumber);

    @QueryMethod
    boolean isOrderCompleted();
}
