package com.example;

public class Processor {
    public void processItem(QueueItem item) throws Exception {
        // Simulate processing
        System.out.println("Processing item: " + item.getData());
        // Allow some time for processing
        Thread.sleep(1000);
        item.setStatus(QueueItem.ItemStatus.PROCESSED);
    }
}