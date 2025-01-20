package com.example.demo.config.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class SampleMetrics {

    private final Counter counter;

    public SampleMetrics(MeterRegistry registry) {
        this.counter = registry.counter("received.messages");
    }

    public void handleMessage(String message) {
        this.counter.increment();
        System.out.println("Message received: " + message);
        // handle message implementation
    }

}
