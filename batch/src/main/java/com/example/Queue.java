package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Queue {
    private final BlockingQueue<QueueItem> queue;
    private final BlockingQueue<QueueItem> dlq;

    public Queue(int bufferSize) {
        this.queue = new LinkedBlockingQueue<>(bufferSize);
        this.dlq = new LinkedBlockingQueue<>(bufferSize);
    }

    public void add(QueueItem item) throws InterruptedException {
        queue.put(item);
    }

    public QueueItem take() throws InterruptedException {
        return queue.take();
    }

    public void addDLQ(QueueItem item) throws InterruptedException {
        dlq.put(item);
    }

    public int getQueueSize() {
        return queue.size();
    }

    public int getDLQSize() {
        return dlq.size();
    }
}