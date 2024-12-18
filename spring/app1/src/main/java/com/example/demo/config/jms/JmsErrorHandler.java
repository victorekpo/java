package com.example.demo.config.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class JmsErrorHandler implements ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(JmsErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        // Log the error details
        logger.error("Error in JMS processing: {}", t.getMessage(), t);
        // Add additional error handling logic here if needed (e.g., notifying monitoring systems)
    }
}
