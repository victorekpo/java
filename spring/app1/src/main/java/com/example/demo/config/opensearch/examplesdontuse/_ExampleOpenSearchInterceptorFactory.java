package com.example.demo.config.opensearch.examplesdontuse;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.apache.hc.core5.http.message.BasicHttpRequest;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.http.HttpEntityEnclosingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _ExampleOpenSearchInterceptorFactory {

    private static final Logger logger = LoggerFactory.getLogger(_ExampleOpenSearchInterceptorFactory.class);

    public static HttpRequestInterceptor createInterceptor() {
        return ((HttpRequest request, EntityDetails entity, HttpContext context) -> {
            // Add custom headers or log the request
            request.addHeader("Custom-Header", "HeaderValue");
            System.out.println("Request: " + request);
            logger.info("Request interceptor: {}", request);
            System.out.println("Request class: " + request.getClass().getName());
            System.out.println(request instanceof HttpPost);
            System.out.println(request instanceof ClassicHttpRequest);
            System.out.println(request instanceof BasicClassicHttpRequest);
            System.out.println(request instanceof HttpGet);
            System.out.println(request instanceof HttpDelete);
            System.out.println(request instanceof HttpEntityEnclosingRequest);
            System.out.println(request instanceof BasicHttpRequest);
            System.out.println("In ClassicHttpRequest");
//            if (request.getEntity() != null) {
//                String requestBody = EntityUtils.toString(request.getEntity(), StandardCharsets.UTF_8);
//                System.out.println("Request Body: " + requestBody);
//                logger.info("Request Body: {}", requestBody);
//            }
        });
    }
}