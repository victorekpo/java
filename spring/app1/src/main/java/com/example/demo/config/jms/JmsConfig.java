package com.example.demo.config.jms;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Session;
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
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    // Spring will automatically inject the SqsClient bean when it is passed as a parameter to the connectionFactory method.
    // This is known as method injection. Here is the relevant part of the JmsConfig class:
    @Bean
    public ConnectionFactory connectionFactory(SqsClient sqsClient) {
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
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(messageConverter());
        factory.setErrorHandler(errorHandler());
        factory.setConcurrency("1-2"); // Set concurrency range
        return factory;
    }

    @Bean
    public JmsErrorHandler errorHandler() {
        return new JmsErrorHandler();
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}

// The JmsConfig class is annotated with @Configuration to indicate that it is a configuration class.
// The @EnableJms annotation enables JMS features in the application.
// This configuration class sets up JMS (Java Message Service) with Amazon SQS (Simple Queue Service) in a Spring Boot application. Here's a breakdown of what each part does:
// SqsClient Bean:
// Creates an SqsClient bean configured to connect to a local SQS endpoint with specific region and credentials.
// ConnectionFactory Bean:
// Uses the SqsClient to create an SQSConnectionFactory.
// Ensures the specified queues (my-queue and my-queue-dlq) exist, creating them if necessary.
// Prints the URLs of the created queues.
// JmsTemplate Bean:
// Creates a JmsTemplate bean using the ConnectionFactory.
// Sets a custom message converter (MappingJackson2MessageConverter) to convert messages to JSON format.
// DefaultJmsListenerContainerFactory Bean:
// Configures a DefaultJmsListenerContainerFactory with the ConnectionFactory and message converter.
// Sets an error handler to log errors and send problematic messages to a dead-letter queue (my-queue-dlq).
// MessageConverter Bean:
// Configures a MappingJackson2MessageConverter to convert messages to JSON format, setting the target type to TEXT and specifying a type ID property name.

// https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/sqs/package-summary.html
// https://docs.spring.io/spring-framework/reference/integration/jms.html
