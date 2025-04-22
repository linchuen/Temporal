package com.cooba.workflow;

import com.cooba.dto.GuessRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public  class GuessNumberWorkflowImpl implements GuessNumberWorkflow{
    private GuessRequest request;

    private final GuessNumberWorkflow activities = Workflow.newActivityStub(
            GuessNumberWorkflow.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(5))
                    .build()
    );

    @Override
    public String guessNumber(GuessRequest request) {
        return "";
    }

    @Override
    public void drawNumber(int resultNumber) {

    }
}
