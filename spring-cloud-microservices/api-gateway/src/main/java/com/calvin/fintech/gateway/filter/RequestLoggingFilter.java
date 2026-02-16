package com.calvin.fintech.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * Global Request Logging and Correlation ID Filter
 * 
 * This filter runs for ALL requests through the gateway.
 * 
 * Features:
 * - Generates correlation ID for distributed tracing
 * - Logs all requests and responses
 * - Measures request duration
 * - Adds correlation ID to request headers
 * 
 * Correlation ID propagates through:
 * Gateway → Payment Service → User Service → Database
 * 
 * All services log the same correlation ID for easier debugging.
 * 
 * Example:
 * Request:  POST /api/payments/process | CorrelationID: abc-123
 * Payment:  Processing payment | CorrelationID: abc-123
 * User:     Validating user | CorrelationID: abc-123
 * Response: 200 OK | Duration: 120ms | CorrelationID: abc-123
 */
@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String correlationId = UUID.randomUUID().toString();
        Instant startTime = Instant.now();
        
        ServerHttpRequest request = exchange.getRequest();
        
        // Add correlation ID to request headers
        ServerHttpRequest modifiedRequest = request.mutate()
            .header("X-Correlation-ID", correlationId)
            .header("X-Gateway-Start-Time", startTime.toString())
            .build();
        
        // Update exchange with modified request
        ServerWebExchange modifiedExchange = exchange.mutate()
            .request(modifiedRequest)
            .build();
        
        // Log incoming request
        System.out.printf("""
            ┌──────────────────────────────────────────────────────────────┐
            │ GATEWAY REQUEST                                               │
            ├──────────────────────────────────────────────────────────────┤
            │ Method:         %s
            │ URI:            %s
            │ Correlation ID: %s
            │ Timestamp:      %s
            └──────────────────────────────────────────────────────────────┘
            """,
            request.getMethod(),
            request.getURI(),
            correlationId,
            startTime
        );
        
        // Continue filter chain
        return chain.filter(modifiedExchange)
            .doFinally(signalType -> {
                long durationMs = Duration.between(startTime, Instant.now()).toMillis();
                int statusCode = exchange.getResponse().getStatusCode() != null
                    ? exchange.getResponse().getStatusCode().value()
                    : -1;
                
                // Log response
                System.out.printf("""
                    ┌──────────────────────────────────────────────────────────────┐
                    │ GATEWAY RESPONSE                                              │
                    ├──────────────────────────────────────────────────────────────┤
                    │ Status:         %d
                    │ Duration:       %dms
                    │ Correlation ID: %s
                    │ Signal:         %s
                    └──────────────────────────────────────────────────────────────┘
                    """,
                    statusCode,
                    durationMs,
                    correlationId,
                    signalType
                );
            });
    }
    
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;  // Run this filter first
    }
}
