package com.cooba.config;

import com.cooba.example.GreetingActivitiesImpl;
import com.cooba.example.SimpleWorkflowImpl;
import com.cooba.service.OrderService;
import com.cooba.service.WalletService;
import com.cooba.workflow.GuessNumberActivityImpl;
import com.cooba.workflow.GuessNumberWorkflowImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.stereotype.Component;

@Component
public class WorkerStarter {

    public WorkerStarter(WorkerFactory factory, OrderService orderService, WalletService walletService) {
        Worker worker = factory.newWorker("demo-task-queue");
        worker.registerWorkflowImplementationTypes(SimpleWorkflowImpl.class);
        worker.registerActivitiesImplementations(new GreetingActivitiesImpl());

        Worker guessWorker = factory.newWorker("guess-task-queue");
        guessWorker.registerWorkflowImplementationTypes(GuessNumberWorkflowImpl.class);
        guessWorker.registerActivitiesImplementations(new GuessNumberActivityImpl(orderService, walletService)); // 加這行
        factory.start();
    }
}

