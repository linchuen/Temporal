package com.cooba.controller;

import com.cooba.dto.GuessRequest;
import com.cooba.workflow.GuessNumberWorkflow;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flow")
public class GuessFlowController {

    private final WorkflowClient workflowClient;

    public GuessFlowController(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @PostMapping("/guess")
    public String startWorkflow(@RequestBody GuessRequest request) {
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue("guess-task-queue")
                .build();

        GuessNumberWorkflow workflow = workflowClient.newWorkflowStub(GuessNumberWorkflow.class, options);

        WorkflowExecution execution = WorkflowClient.start(workflow::guessNumber, request);

        return "Workflow started with name: " + execution.getWorkflowId();
    }
}
