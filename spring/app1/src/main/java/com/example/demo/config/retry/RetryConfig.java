package com.example.demo.config.retry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate(CustomRetryListener customRetryListener) {
        RetryTemplate retryTemplate = new RetryTemplate();

        // Configure backoff policy
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(15000); // 15 seconds // 5 minutes // 100 minutes
        backOffPolicy.setMultiplier(20.0);
        backOffPolicy.setMaxInterval(6000000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        // Configure retry policy
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.registerListener(customRetryListener);

        return retryTemplate;
    }
}