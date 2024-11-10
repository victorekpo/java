package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

@Service
public class JmsService {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String destination, String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    public List<String> receiveMessages(String destination) {
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = jmsTemplate.receive(destination);
            if (message instanceof TextMessage) {
                try {
                    messages.add(((TextMessage) message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
        return messages;
    }
}