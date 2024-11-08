package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerPool {
    private final ExecutorService executor;

    public WorkerPool(int numberOfWorkers, Queue queue, Processor processor, int maxRetries) {
        this.executor = Executors.newFixedThreadPool(numberOfWorkers);
        for (int i = 0; i < numberOfWorkers; i++) {
            executor.submit(new CustomWorker(i, queue, processor, maxRetries));
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}