package com.example.demo.controller.jms;

import com.example.demo.service.jms.InternalQueueMessage;
import com.example.demo.service.jms.JmsService;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private JmsService jmsService;

  //  @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        jmsService.sendMessage(message);
        return "Message sent: " + message;
    }

   // @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/messages")
    public List<InternalQueueMessage> viewMessages() {
        List<InternalQueueMessage> messages = jmsService.getReceivedMessages();
        System.out.println("Messages received " + messages.toString());
        return messages;
    }
}