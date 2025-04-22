package com.cooba.workflow;

import com.cooba.service.OrderService;
import com.cooba.service.WalletService;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.stereotype.Component;

@Component
public class GuessNumberWorker {

    public GuessNumberWorker(WorkerFactory factory, OrderService orderService, WalletService walletService) {
        Worker worker = factory.newWorker("demo-task-queue");
        worker.registerWorkflowImplementationTypes(GuessNumberWorkflowImpl.class);
        worker.registerActivitiesImplementations(new GuessNumberActivityImpl(orderService, walletService)); // 加這行
        factory.start();
    }
}
