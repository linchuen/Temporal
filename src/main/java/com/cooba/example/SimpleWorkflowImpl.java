package com.cooba.example;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class SimpleWorkflowImpl implements SimpleWorkflow {

    private String name;

    private final GreetingActivities activities = Workflow.newActivityStub(
            GreetingActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(5))
                    .build()
    );

    @Override
    public String greet(String name) {
        this.name = name;
        return activities.composeGreeting(this.name); // 呼叫 Activity
    }

    @Override
    public void updateName(String newName) {
        this.name = newName;
    }
}


