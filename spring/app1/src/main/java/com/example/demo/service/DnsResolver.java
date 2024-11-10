package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.opensearch.OpenSearchClient;
import software.amazon.awssdk.services.opensearch.model.ListDomainNamesRequest;
import software.amazon.awssdk.services.opensearch.model.ListDomainNamesResponse;

@Component
public class DnsResolver {

    @Autowired
    private OpenSearchClient awsOpenSearchClient;

    @Scheduled(fixedRate = 5000)
    public void resolveDns() {
        try {
            ListDomainNamesRequest request = ListDomainNamesRequest.builder().build();
            ListDomainNamesResponse response = awsOpenSearchClient.listDomainNames(request);
            System.out.println("Resolved DNS: " + response.domainNames());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}