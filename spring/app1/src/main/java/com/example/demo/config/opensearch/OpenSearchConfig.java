package com.example.demo.config.opensearch;

import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.TaskScheduler;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.opensearch.model.DomainInfo;

import javax.net.ssl.SSLContext;
import java.util.Arrays;

@Configuration
@EnableScheduling
public class OpenSearchConfig {

    public static volatile DomainInfo[] openSearchCluster = new DomainInfo[] {
            DomainInfo.builder().domainName("localhost:9200").build(),
         //   DomainInfo.builder().domainName("anotherdomain.com:9200").build()
    };

    @Bean
    public OpenSearchClient openSearchClient() throws Exception {
        String domainName = openSearchCluster[0].domainName();
        System.out.println("Domain Name: " + domainName);
        boolean isLocal = "localhost:9200".equals(domainName);

        String[] domainParts = domainName.split(":");
        String hostName = domainParts[0];
        int port = domainParts.length > 1 ? Integer.parseInt(domainParts[1]) : 443;

        HttpHost host = new HttpHost(isLocal ? "http" : "https", hostName, port);

        ApacheHttpClient5TransportBuilder builder = ApacheHttpClient5TransportBuilder.builder(host);

//        if (!isLocal) {

        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(null, (chains, authType) -> true)
                .build();

        TlsStrategy tlsStrategy = ClientTlsStrategyBuilder.create()
                .setSslContext(sslContext)
                .build();

        PoolingAsyncClientConnectionManager connectionManager = PoolingAsyncClientConnectionManagerBuilder.create()
                .setTlsStrategy(tlsStrategy)
                .build();

        builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                .setConnectionManager(connectionManager)
                .addRequestInterceptorFirst((request, entity, context) -> {
                    // Add custom headers
                    request.addHeader("app-name", "your-app-name");
                })
                .addRequestInterceptorFirst((request, entity, context) -> {
                    System.out.println("Request: " + request.toString());
                })
                .addResponseInterceptorFirst((response, entity, context) -> {
                    System.out.println("Response: " + response);
                })
        );

//        }

        OpenSearchTransport transport = builder.build();
        System.out.println("OpenSearch client created for " + domainName);
        System.out.println("OpenSearch host " + host.toString());

        // Additional logging for debugging
        System.out.println("Host: " + hostName);
        System.out.println("Port: " + port);
        System.out.println("Is Local: " + isLocal);

        return new OpenSearchClient(transport);
    }

    @Bean
    public software.amazon.awssdk.services.opensearch.OpenSearchClient awsOpenSearchClient() {
        return software.amazon.awssdk.services.opensearch.OpenSearchClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .httpClient(ApacheHttpClient.builder().build())
                .build();
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("dns-resolver-");
        return scheduler;
    }
}