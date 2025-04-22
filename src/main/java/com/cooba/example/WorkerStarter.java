package com.cooba.example;

import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.stereotype.Component;

@Component
public class WorkerStarter {

    public WorkerStarter(WorkerFactory factory) {
        Worker worker = factory.newWorker("demo-task-queue");
        worker.registerWorkflowImplementationTypes(SimpleWorkflowImpl.class);
        worker.registerActivitiesImplementations(new GreetingActivitiesImpl()); // 加這行
        factory.start();
    }
}

