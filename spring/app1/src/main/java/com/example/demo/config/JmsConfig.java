package com.example.demo.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJms
public class JmsConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        SqsClient sqsClient = SqsClient.builder()
                .endpointOverride(URI.create("http://localhost:9324"))
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        // Create queues if they do not exist
        String[] queues = {"my-queue", "my-queue-dlq"};
        Map<String, String> queueUrls = new HashMap<>();
        for (String queue : queues) {
            CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                    .queueName(queue)
                    .build();
            CreateQueueResponse createQueueResponse = sqsClient.createQueue(createQueueRequest);
            queueUrls.put(queue, createQueueResponse.queueUrl());
        }

        System.out.println("Queue URLs: " + queueUrls);

        return new SQSConnectionFactory(new ProviderConfiguration(), sqsClient);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(messageConverter());
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        factory.setErrorHandler(t -> {
            System.err.println("An error has occurred in the transaction: " + t.getMessage());
            // Send the message to the DLQ
            jmsTemplate(connectionFactory).convertAndSend("my-queue-dlq", t.getMessage());
        });
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}