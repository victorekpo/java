package com.example.demo.service.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

@Service
public class JmsService {
    private static final Logger logger = LoggerFactory.getLogger(JmsService.class);

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
        logger.info("Received message from {}: {} on thread: {}", queueName, message, threadName);

        String messageId = UUID.randomUUID().toString();
        InternalQueueMessage internalQueueMessage = new InternalQueueMessage(messageId, message, Thread.currentThread().getName(), InternalQueueMessage.Status.PENDING);
        internalQueue.add(internalQueueMessage);
        // System.out.println("Internal queue : " + getReceivedMessages());

        processMessageWithRetry(message, internalQueueMessage);
    }

    private void processMessageWithRetry(String message, InternalQueueMessage internalQueueMessage) {
        try {
            processMessage(message);
            internalQueueMessage.setStatus(InternalQueueMessage.Status.SUCCESS);
        } catch (Exception e) {
            logger.error("Exception occurred while processing the first time: {}", e.getMessage());
            CompletableFuture.runAsync(() -> {
                retryTemplate.execute(context -> {
                    internalQueueMessage.setStatus(InternalQueueMessage.Status.PROCESSING);
                    try {
                        String threadName = Thread.currentThread().getName();
                        logger.info("Retrying message: {} on thread: {}", message, threadName);
                        processMessage(message);
                    } catch (Exception ex) {
                        logger.error("Exception occurred while retrying: {}", ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                    return null;
                });
            }, executorService).exceptionally(ex -> {
                // Handle the exception from the async task
                logger.warn("Retry attempts exhausted. Sending message to DLQ: {} errorMessage: {} stack:{}", message, ex.getMessage(), ex.getStackTrace());
                internalQueueMessage.setStatus(InternalQueueMessage.Status.FAILURE);
                sendToDlq(message);
                return null;
            });
        }
    }

    public void processMessage(String message) {
        try {
            logger.info("Processing message: {}", message);
            Thread.sleep(10000); // Simulate delay of 5 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
        throw new RuntimeException("exception thrown test");
    }

    public List<InternalQueueMessage> getReceivedMessages() {
        return new ArrayList<>(internalQueue);
    }

    public InternalQueueMessage getMessageById(String id) {
        return internalQueue.stream()
                .filter(message -> message.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}