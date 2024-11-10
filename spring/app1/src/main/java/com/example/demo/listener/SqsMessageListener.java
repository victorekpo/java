package com.example.demo.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SqsMessageListener {

    @Value("${jms.sqs.queue.name}")
    private String queueName;

    @JmsListener(destination = "${jms.sqs.queue.name}")
    public void receiveMessage(String message) {
        System.out.println("Received message from " + queueName + ": " + message);
    }
}