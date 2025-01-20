package com.example.demo.config.metrics;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.influx.InfluxConfig;
import io.micrometer.influx.InfluxMeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("region", "us-east-1");
    }

    @Bean
    public InfluxMeterRegistry influxMeterRegistry(InfluxConfig influxConfig) {
        return new InfluxMeterRegistry(influxConfig, Clock.SYSTEM);
    }

    @Bean
    public Counter openCounter(MeterRegistry registry) {
        System.out.println("Creating counter");
        return registry.counter("received.messages");
    }

//    @Bean
//    public InfluxConfig influxConfig() {
//        return new InfluxConfig() {
//            @Override
//            public String get(String key) {
//                return null; // accept the default values for all properties
//            }
//
//            @Override
//            public String uri() {
//                return "http://localhost:8086";
//            }
//
//            @Override
//            public String db() {
//                return "metricsdb";
//            }
//
//            @Override
//            public String userName() {
//                return "vic";
//            }
//
//            @Override
//            public String password() {
//                return "vic12345";
//            }
//
//            @Override
//            public String token() {
//                return "secret-token";
//            }
//        };
//    }
}