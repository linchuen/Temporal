package com.cooba.example;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

    private final WorkflowClient workflowClient;

    public WorkflowController(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @PostMapping("/start")
    public String startWorkflow(@RequestParam String name) {
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue("demo-task-queue")
                .build();

        SimpleWorkflow workflow = workflowClient.newWorkflowStub(SimpleWorkflow.class, options);

        WorkflowClient.start(workflow::greet, name);

        return "Workflow started with name: " + name;
    }
}
