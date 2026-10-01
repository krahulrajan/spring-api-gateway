package com.example.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();

        String method = exchange.getRequest()
                .getMethod()
                .name();

        String path = exchange.getRequest()
                .getURI()
                .getPath();

        logger.info("Incoming Request: {} {}", method, path);

        return chain.filter(exchange)
                .doFinally(signal -> {

                    long duration =
                            System.currentTimeMillis() - startTime;

                    var statusCode =
                            exchange.getResponse().getStatusCode();

                    logger.info(
                            "Completed Request: {} {} | Status: {} | Time: {} ms",
                            method,
                            path,
                            statusCode,
                            duration
                    );
                });
    }
}