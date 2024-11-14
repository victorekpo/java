package com.example.demo.service.opensearch;

import com.example.demo.config.opensearch.OpenSearchConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.opensearch.OpenSearchClient;
import software.amazon.awssdk.services.opensearch.model.DomainInfo;
import software.amazon.awssdk.services.opensearch.model.ListDomainNamesRequest;
import software.amazon.awssdk.services.opensearch.model.ListDomainNamesResponse;

import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DnsResolver {

    private static final Logger logger = LoggerFactory.getLogger(DnsResolver.class);
    private static final int MAX_EXECUTIONS = 2;

    @Autowired
    private OpenSearchClient awsOpenSearchClient;

    @Autowired
    private TaskScheduler taskScheduler;

    private final AtomicInteger executionCount = new AtomicInteger(0);
    private ScheduledFuture<?> scheduledTask;

    @PostConstruct
    public void scheduleDnsResolution() {
        scheduledTask = taskScheduler.scheduleAtFixedRate(this::resolveDns, Instant.now(), Duration.ofSeconds(5));
    }

    public void resolveDns() {
        try {
            if (executionCount.incrementAndGet() > MAX_EXECUTIONS) {
                scheduledTask.cancel(false);
                logger.info("Task cancelled after {} executions", MAX_EXECUTIONS);
                return;
            }

            ListDomainNamesRequest request = ListDomainNamesRequest.builder().build();
            ListDomainNamesResponse response = awsOpenSearchClient.listDomainNames(request);
            List<DomainInfo> resolvedDnsList = response.domainNames();

            if (resolvedDnsList.size() > 1) {
                OpenSearchConfig.openSearchCluster = resolvedDnsList.toArray(new DomainInfo[0]);
                logger.info("Resolved DNS: {} from {}", (Object) resolvedDnsList, Arrays.toString(OpenSearchConfig.openSearchCluster));
            } else {
                OpenSearchConfig.openSearchCluster = new DomainInfo[] { DomainInfo.builder().domainName("localhost:9200").build() };
                logger.info("Resolved DNS to localhost:9200; cluster={}", OpenSearchConfig.openSearchCluster[0].toString());
            }
        } catch (Exception e) {
            logger.error("Error resolving DNS", e);
            e.printStackTrace();
        }
    }
}

// The `@PostConstruct` annotation is used on a method that needs to be executed after dependency injection is done to perform any initialization. It is part of the `jakarta.annotation` package.
//
// Yes, the method annotated with `@PostConstruct` runs after the class is created and all dependencies are injected. This typically happens at runtime when the Spring container initializes the bean.
//
// In the context of a Spring application, the class gets created during the application startup when the Spring container scans and instantiates the beans defined in the application context.