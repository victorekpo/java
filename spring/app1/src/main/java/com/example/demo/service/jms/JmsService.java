package com.example.demo.service.jms;

import jakarta.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class JmsService {
    private static final Logger logger = LoggerFactory.getLogger(JmsService.class);

    @Value("${jms.sqs.queue.name}")
    private String queueName;

    @Autowired
    private JmsTemplate jmsTemplate;

    private final ConcurrentLinkedQueue<InternalQueueMessage> internalQueue = new ConcurrentLinkedQueue<>();

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(queueName, message);
    }

    @JmsListener(destination = "${jms.sqs.queue.name}")
    public void receiveMessage(String message, Session session) throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        logger.info("Received message from {}: {} on thread: {}", queueName, message, threadName);

        String messageId = UUID.randomUUID().toString();
        InternalQueueMessage internalQueueMessage = new InternalQueueMessage(messageId, message, Thread.currentThread().getName(), InternalQueueMessage.Status.PENDING);
        internalQueue.add(internalQueueMessage);
        // System.out.println("Internal queue : " + getReceivedMessages());

        try {
            processMessage(message);
        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage());
            throw e;
        }
    }

    public void processMessage(String message) throws InterruptedException {
        logger.info("Processing message: {}", message);
        Thread.sleep(10000); // Simulate delay of 5 seconds

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

// The processMessageWithRetry method is designed to process a message and handle any exceptions that occur during processing. If an exception occurs, it retries the message processing asynchronously using a RetryTemplate and an ExecutorService. Here is a detailed breakdown of the code:
// Initial Message Processing:
// The method processMessageWithRetry is called with a message and an InternalQueueMessage object.
// It first attempts to process the message by calling processMessage(message).
// If the message is processed successfully, it sets the status of internalQueueMessage to SUCCESS.
// Exception Handling:
// If an exception occurs during the initial processing, it logs the error message.
// It then uses CompletableFuture.runAsync to retry the message processing asynchronously.
// Asynchronous Retry:
// CompletableFuture.runAsync takes two arguments:
// A Runnable task that contains the retry logic.
// An ExecutorService to manage the asynchronous execution of the task.
// The retryTemplate.execute method is used to retry the message processing.
// It sets the status of internalQueueMessage to PROCESSING.
// It logs the retry attempt and calls processMessage(message) again.
// If an exception occurs during the retry, it logs the error and throws a RuntimeException.
// Handling Retry Exhaustion:
// The exceptionally method of CompletableFuture is used to handle any exceptions that occur during the asynchronous task.
// If all retry attempts are exhausted, it logs the error message, sets the status of internalQueueMessage to FAILURE, and sends the message to the Dead Letter Queue (DLQ) using sendToDlq(message).