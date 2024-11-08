package com.example;

import java.util.concurrent.TimeUnit;

public class CustomWorker implements Runnable {
    private final int id;
    private final Queue queue;
    private final Processor processor;
    private final int maxRetries;

    public CustomWorker(int id, Queue queue, Processor processor, int maxRetries) {
        this.id = id;
        this.queue = queue;
        this.processor = processor;
        this.maxRetries = maxRetries;
    }

    @Override
    public void run() {
        try {
            while (true) {
                QueueItem item = queue.take();
                processWithRetry(item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void processWithRetry(QueueItem item) {
        for (int retries = 0; retries < maxRetries; retries++) {
            try {
                processor.processItem(item);
                return;
            } catch (Exception e) {
                System.out.println("Error processing item " + item.getData() + ", attempt " + (retries + 1));
                try {
                    TimeUnit.SECONDS.sleep((long) Math.pow(2, retries));
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println("Failed to process item " + item.getData() + " after " + maxRetries + " attempts, sending to DLQ");
        try {
            queue.addDLQ(item);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}