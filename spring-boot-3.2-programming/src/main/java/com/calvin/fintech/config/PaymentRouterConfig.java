package com.calvin.fintech.config;

import com.calvin.fintech.domain.PaymentRequest;
import com.calvin.fintech.domain.PaymentResponse;
import com.calvin.fintech.handler.PaymentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * Functional Router Configuration (Java 21 + Spring Boot 3.2)
 * 
 * Replaces traditional @RestController approach with functional routing
 * 
 * Benefits:
 * - Reduces reflection overhead
 * - Centralizes routing logic
 * - Better for GraalVM native compilation
 * - Easier to test
 * - Type-safe routing
 * 
 * Shift: @Controller + @RequestMapping → RouterFunction
 */
@Configuration
public class PaymentRouterConfig {
    
    /**
     * Main payment router
     * 
     * Functional DSL for routing:
     * - No @RequestMapping annotations
     * - Centralized in one bean
     * - Composable routes
     */
    @Bean
    public RouterFunction<ServerResponse> paymentRoutes(PaymentHandler handler) {
        return RouterFunctions
            .route()
            
            // POST /api/payments/process
            .POST("/api/payments/process",
                  accept(MediaType.APPLICATION_JSON),
                  handler::processPayment)
            
            // POST /api/payments/validate
            .POST("/api/payments/validate",
                  accept(MediaType.APPLICATION_JSON),
                  handler::validatePayment)
            
            // GET /api/payments/{id}
            .GET("/api/payments/{id}",
                 handler::getPayment)
            
            // GET /api/payments/status/{id}
            .GET("/api/payments/status/{id}",
                 handler::getPaymentStatus)
            
            // Health check
            .GET("/api/payments/health",
                 request -> ServerResponse.ok().bodyValue("OK"))
            
            .build();
    }
    
    /**
     * Alternative: Nested routes for organization
     */
    @Bean
    public RouterFunction<ServerResponse> adminRoutes(PaymentHandler handler) {
        return RouterFunctions
            .nest(path("/api/admin/payments"),
                RouterFunctions.route()
                    .GET("/stats", handler::getStatistics)
                    .GET("/failed", handler::getFailedPayments)
                    .POST("/retry/{id}", handler::retryPayment)
                    .build()
            );
    }
    
    /**
     * Alternative: Route with predicates
     */
    @Bean
    public RouterFunction<ServerResponse> secureRoutes(PaymentHandler handler) {
        return RouterFunctions
            .route()
            // Only accept requests with API key header
            .POST("/api/payments/priority",
                  accept(MediaType.APPLICATION_JSON)
                      .and(headers(h -> h.containsKey("X-API-Key"))),
                  handler::processPriorityPayment)
            .build();
    }
}
