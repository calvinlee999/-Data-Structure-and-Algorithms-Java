package com.calvin.fintech.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Functional Gateway Routes Configuration
 * 
 * This configuration uses functional routing (similar to RouterFunction from Phase 6)
 * instead of traditional annotation-based routing.
 * 
 * Benefits:
 * - Type-safe: Compile-time validation
 * - Composable: Easy to add filters
 * - Performance: No reflection overhead
 * - Testable: Can test routes in isolation
 * 
 * Key Patterns:
 * - lb://service-name: Load-balanced routing via Eureka
 * - Circuit breaker: Fails fast when service is down
 * - Retry: Automatically retry transient failures
 * - Filters: Add headers, strip prefix, etc.
 */
@Configuration
public class GatewayRoutesConfig {
    
    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            
            // ========================================
            // PAYMENT SERVICE ROUTES
            // ========================================
            .route("payment-service", r -> r
                .path("/api/payments/**")
                .filters(f -> f
                    // Remove /api prefix before forwarding
                    .stripPrefix(1)
                    
                    // Add custom header for tracking
                    .addRequestHeader("X-Gateway", "ApiGateway")
                    .addRequestHeader("X-Gateway-Version", "1.0.0")
                    
                    // Circuit breaker: Fail fast if service is down
                    .circuitBreaker(config -> config
                        .setName("paymentCircuitBreaker")
                        .setFallbackUri("forward:/fallback/payments")
                    )
                    
                    // Retry: 3 attempts with exponential backoff
                    .retry(config -> config
                        .setRetries(3)
                        .setBackoff(
                            Duration.ofMillis(100),  // Initial delay
                            Duration.ofMillis(1000),  // Max delay
                            2,  // Multiplier
                            true  // basedOnPreviousValue
                        )
                    )
                    
                    // Request timeout
                    .requestRateLimiter(config -> config
                        .setRateLimiter(null)  // Configure Redis rate limiter
                    )
                )
                // Load-balanced routing via Eureka
                .uri("lb://payment-service")
            )
            
            // ========================================
            // USER SERVICE ROUTES
            // ========================================
            .route("user-service", r -> r
                .path("/api/users/**")
                .filters(f -> f
                    .stripPrefix(1)
                    .addRequestHeader("X-Gateway", "ApiGateway")
                    .circuitBreaker(config -> config
                        .setName("userCircuitBreaker")
                        .setFallbackUri("forward:/fallback/users")
                    )
                    .retry(config -> config
                        .setRetries(3)
                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)
                    )
                )
                .uri("lb://user-service")
            )
            
            // ========================================
            // ACCOUNT SERVICE ROUTES
            // ========================================
            .route("account-service", r -> r
                .path("/api/accounts/**")
                .filters(f -> f
                    .stripPrefix(1)
                    .addRequestHeader("X-Gateway", "ApiGateway")
                    .circuitBreaker(config -> config
                        .setName("accountCircuitBreaker")
                        .setFallbackUri("forward:/fallback/accounts")
                    )
                    .retry(config -> config
                        .setRetries(3)
                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)
                    )
                )
                .uri("lb://account-service")
            )
            
            // ========================================
            // ADMIN ROUTES (secured)
            // ========================================
            .route("admin-routes", r -> r
                .path("/api/admin/**")
                .filters(f -> f
                    .stripPrefix(2)
                    .addRequestHeader("X-Requires-Auth", "true")
                )
                .uri("lb://admin-service")
            )
            
            .build();
    }
}
