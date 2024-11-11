package com.example.demo.service.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
public class JmsService {

    @Value("${jms.sqs.queue.name}")
    private String queueName;

    @Autowired
    private JmsTemplate jmsTemplate;

    private ConcurrentLinkedQueue<String> internalQueue = new ConcurrentLinkedQueue<>();

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(queueName, message);
    }

    @JmsListener(destination = "${jms.sqs.queue.name}")
    public void receiveMessage(String message) {
        System.out.println("Received message from " + queueName + ": " + message);
        internalQueue.add(message);
    }

    public List<String> getReceivedMessages() {
        return internalQueue.stream().collect(Collectors.toList());
    }
}

// https://docs.spring.io/spring-framework/reference/integration/jms/sending.html
// https://docs.spring.io/spring-framework/reference/integration/jms/receiving.html