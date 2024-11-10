package com.example.demo.config;

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
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;

import javax.net.ssl.SSLContext;

@Configuration
@EnableScheduling
public class OpenSearchConfig {

    @Bean
    public OpenSearchClient openSearchClient() throws Exception {
        final HttpHost host = new HttpHost("https", "your-opensearch-domain", 443);

        final SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial(null, (chains, authType) -> true)
                .build();

        final TlsStrategy tlsStrategy = ClientTlsStrategyBuilder.create()
                .setSslContext(sslContext)
                .build();

        final PoolingAsyncClientConnectionManager connectionManager = PoolingAsyncClientConnectionManagerBuilder.create()
                .setTlsStrategy(tlsStrategy)
                .build();


        final DefaultCredentialsProvider credentialsProvider = DefaultCredentialsProvider.create();



        final ApacheHttpClient5TransportBuilder builder = ApacheHttpClient5TransportBuilder.builder(host);
        builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                .setConnectionManager(connectionManager)
                .addRequestInterceptorFirst((request, entity, context) -> {
                    // Add custom headers
                    request.addHeader("app-name", "your-app-name");
                })
        );

        final OpenSearchTransport transport = builder.build();
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
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("dns-resolver-");
        return scheduler;
    }
}