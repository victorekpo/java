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
import java.util.concurrent.Future;
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
        String threadName = Thread.currentThread().getName();
        System.out.println("Received message from " + queueName + ": " + message + " on thread: " + threadName);

        String messageId = UUID.randomUUID().toString();
        InternalQueueMessage internalQueueMessage = new InternalQueueMessage(messageId, message, Thread.currentThread().getName(), InternalQueueMessage.Status.PENDING);
        internalQueue.add(internalQueueMessage);
        // System.out.println("Internal queue : " + getReceivedMessages());

        try {
            processMessageWithRetry(message, internalQueueMessage);
        } catch (Exception e) {
            System.out.println("Sending message to DLQ: " + message);
            System.out.println("Retry attempts exhausted. Sending message to DLQ: " + message + " errorMessage: " + e.getMessage() + " errorCause: " + e.getCause());
            internalQueueMessage.setStatus(InternalQueueMessage.Status.FAILURE);
            sendToDlq(message);
        }
    }

    private void processMessageWithRetry(String message, InternalQueueMessage internalQueueMessage) {
        try {
            processMessage(message);
            internalQueueMessage.setStatus(InternalQueueMessage.Status.SUCCESS);
        } catch (Exception e) {
            System.out.println("Exception occurred while processing the first time: " + e.getMessage());
            CompletableFuture.runAsync(() -> {
                retryTemplate.execute(context -> {
                    internalQueueMessage.setStatus(InternalQueueMessage.Status.PROCESSING);
                    try {
                        String threadName = Thread.currentThread().getName();
                        System.out.println("Retrying message: " + message + " on thread: " + threadName);
                        processMessage(message);
                    } catch (Exception ex) {
                        // Handle the exception here
                        System.out.println("Exception occurred while retrying: " + ex.getMessage());
                        // You can add additional logic here, such as logging or updating the message status
                        throw new RuntimeException(ex);
                    }
                    return null;
                });
            }, executorService).exceptionally(ex -> {
                // Handle the exception from the async task
                System.out.println("Retry attempts exhausted. Sending message to DLQ: " + message + " errorMessage: " + ex.getMessage() + " errorCause: " + ex.getCause());
                internalQueueMessage.setStatus(InternalQueueMessage.Status.FAILURE);
                sendToDlq(message);
                return null;
            });
        }
    }

    public void processMessage(String message) {
        try {
            System.out.println("Processing message: " + message);
            Thread.sleep(10000); // Simulate delay of 5 seconds
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