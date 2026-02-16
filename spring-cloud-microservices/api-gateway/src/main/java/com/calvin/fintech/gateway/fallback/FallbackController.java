package com.calvin.fintech.gateway.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

/**
 * Circuit Breaker Fallback Controller
 * 
 * When a service is down or circuit breaker is OPEN, requests are routed here.
 * 
 * Why Fallback?
 * - Without fallback: 500 Internal Server Error (bad user experience)
 * - With fallback: Graceful degradation message (better UX)
 * 
 * Example:
 * - Payment service is down
 * - Circuit breaker opens
 * - Requests return friendly message instead of timeout/error
 * 
 * Production Tip:
 * - Return cached data if available
 * - Return default values
 * - Return "try again later" message
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {
    
    /**
     * Fallback for payment service
     */
    @GetMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<Map<String, Object>> paymentsFallback() {
        return Mono.just(Map.of(
            "status", "SERVICE_UNAVAILABLE",
            "message", "Payment service is temporarily unavailable. Please try again later.",
            "timestamp", Instant.now().toString(),
            "service", "payment-service",
            "circuitBreaker", "OPEN"
        ));
    }
    
    /**
     * Fallback for user service
     */
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<Map<String, Object>> usersFallback() {
        return Mono.just(Map.of(
            "status", "SERVICE_UNAVAILABLE",
            "message", "User service is temporarily unavailable. Please try again later.",
            "timestamp", Instant.now().toString(),
            "service", "user-service",
            "circuitBreaker", "OPEN"
        ));
    }
    
    /**
     * Fallback for account service
     */
    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<Map<String, Object>> accountsFallback() {
        return Mono.just(Map.of(
            "status", "SERVICE_UNAVAILABLE",
            "message", "Account service is temporarily unavailable. Please try again later.",
            "timestamp", Instant.now().toString(),
            "service", "account-service",
            "circuitBreaker", "OPEN"
        ));
    }
}
