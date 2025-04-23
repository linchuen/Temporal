package com.cooba.controller;

import com.cooba.dto.GuessRequest;
import com.cooba.entity.Order;
import com.cooba.service.OrderService;
import com.cooba.workflow.GuessNumberWorkflow;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flow")
@RequiredArgsConstructor
public class GuessFlowController {
    private final OrderService orderService;
    private final WorkflowClient workflowClient;

    @PostMapping("/guess")
    public String startWorkflow(@RequestBody GuessRequest request) {
        Order order = orderService.generateOrder(request.getGuessNumber(), request.getBetAmount());

        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue("guess-task-queue")
                .build();

        GuessNumberWorkflow workflow = workflowClient.newWorkflowStub(GuessNumberWorkflow.class, options);
        WorkflowExecution execution = WorkflowClient.start(workflow::guessNumber, order);

        return "Get your guess request orderNo:" + order.getOrderNo()
                + "\nWorkflow started with id:" + execution.getWorkflowId();
    }
}
