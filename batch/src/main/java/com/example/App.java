package com.example;

public class App {
    public static void main(String[] args) throws InterruptedException {
        int numberOfWorkers = 100;
        int bufferSize = 1000;
        int maxRetries = 3;

        Queue queue = new Queue(bufferSize);
        Processor processor = new Processor();
        WorkerPool workerPool = new WorkerPool(numberOfWorkers, queue, processor, maxRetries);

        // Add items to the queue
        for (int i = 0; i < 500; i++) {
            queue.add(new QueueItem("Item " + i));
        }

        // Shutdown the worker pool
        workerPool.shutdown();
    }

}
