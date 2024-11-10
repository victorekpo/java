package com.example.demo.controller;

import com.example.demo.service.JmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private JmsService jmsService;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        jmsService.sendMessage(message);
        return "Message sent: " + message;
    }

    @GetMapping("/messages")
    public List<String> viewMessages() {
        List<String> messages = jmsService.getReceivedMessages();
        System.out.println("Messages received " + messages.toString());
        return messages;
    }
}