package com.example.demo.service.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
public class JmsService {

    @Value("${jms.sqs.queue.name}")
    private String queueName;

    @Value("${jms.sqs.dlq.name}")
    private String dlqName;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private RetryTemplate retryTemplate;

    @Autowired
    private ExecutorService executorService;

    private final ConcurrentLinkedQueue<InternalQueueMessage> internalQueue = new ConcurrentLinkedQueue<>();

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(queueName, message);
    }

    public void sendToDlq(String message) {
        jmsTemplate.convertAndSend(dlqName, message);
    }

    @JmsListener(destination = "${jms.sqs.queue.name}")
    public void receiveMessage(String message, Session session) throws JMSException {
        String messageId = UUID.randomUUID().toString();
        InternalQueueMessage internalQueueMessage = new InternalQueueMessage(messageId, message, Thread.currentThread().getName(), InternalQueueMessage.Status.PENDING);
        internalQueue.add(internalQueueMessage);
        System.out.println("Internal queue : " + getReceivedMessages());

        processMessageWithRetry(message, internalQueueMessage);
    }

    private void processMessageWithRetry(String message, InternalQueueMessage internalQueueMessage) {
        try {
            processMessage(message);
            internalQueueMessage.setStatus(InternalQueueMessage.Status.SUCCESS);
        } catch (Exception e) {
            executorService.submit(() -> {
                retryTemplate.execute(context -> {
                    internalQueueMessage.setStatus(InternalQueueMessage.Status.PROCESSING);
                    CompletableFuture.runAsync(() -> processMessage(message))
                            .exceptionally(ex -> {
                                throw new RuntimeException(ex);
                            }).join();
                    return null;
                });
            });
        }
    }

    public void processMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.println("Received message from " + queueName + ": " + message + " on thread: " + threadName);
        try {
            Thread.sleep(5000); // Simulate delay of 5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
        throw new RuntimeException("exception thrown test");
    }

    public List<InternalQueueMessage> getReceivedMessages() {
        return internalQueue.stream().collect(Collectors.toList());
    }

    public InternalQueueMessage getMessageById(String id) {
        return internalQueue.stream()
                .filter(message -> message.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}