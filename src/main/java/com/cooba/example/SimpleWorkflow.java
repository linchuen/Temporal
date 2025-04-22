package com.cooba.example;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

// SimpleWorkflow.java
@WorkflowInterface
public interface SimpleWorkflow {
    @WorkflowMethod
    String greet(String name);

    @SignalMethod
    void updateName(String newName);
}

