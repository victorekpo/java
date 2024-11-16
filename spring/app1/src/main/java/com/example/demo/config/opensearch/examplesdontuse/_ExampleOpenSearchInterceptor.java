package com.example.demo.config.opensearch.examplesdontuse;

import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class _ExampleOpenSearchInterceptor implements HttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(_ExampleOpenSearchInterceptor.class);

    // @Override
    public void process(HttpRequest request, EntityDetails entity, HttpContext context) throws HttpException, IOException {
        logger.info("Intercepting request: " + request);

        if (entity != null) {
            String body = EntityUtils.toString((HttpEntity) entity, StandardCharsets.UTF_8);
            logger.info("Request body: " + body);
        }

    }
}