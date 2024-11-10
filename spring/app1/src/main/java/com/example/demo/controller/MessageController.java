package com.example.demo.controller;

import com.example.demo.service.JmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private JmsService jmsService;

    @Value("${jms.sqs.queue.name}")
    private String queueName;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        jmsService.sendMessage(queueName, message);
        return "Message sent to " + queueName + ": " + message;
    }

    @GetMapping("/messages")
    public List<String> viewMessages() {
        return jmsService.receiveMessages(queueName);
    }
}