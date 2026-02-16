package com.calvin.fintech.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API Gateway
 * 
 * Purpose: Single entry point for all client requests, routes to microservices
 * 
 * Features:
 * - Request routing: /api/payments/** → payment-service
 * - Load balancing: Distributes requests across instances
 * - Circuit breaker: Fails fast when services are down
 * - Rate limiting: Prevents DoS attacks
 * - Authentication: Validates JWT tokens
 * - Request transformation: Add headers, modify requests
 * - Response aggregation: Combine multiple service calls
 * 
 * Why Needed:
 * - Without gateway: Clients call services directly (tight coupling)
 * - With gateway: Single endpoint, centralized security/monitoring
 * 
 * Functional Routing (from Phase 6):
 * - Uses RouterFunction (no @Controller)
 * - Type-safe routing logic
 * - Better performance (no reflection)
 * 
 * Port: 8080 (main entry point)
 * Routes:
 * - /api/payments/** → payment-service
 * - /api/users/** → user-service
 * - /api/accounts/** → account-service
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        
        System.out.println("""
            
            ╔════════════════════════════════════════════════════════════════╗
            ║                   API GATEWAY STARTED                           ║
            ╠════════════════════════════════════════════════════════════════╣
            ║  Gateway URL: http://localhost:8080                            ║
            ║                                                                 ║
            ║  Routes:                                                        ║
            ║    POST /api/payments/process    → payment-service             ║
            ║    GET  /api/payments/{id}       → payment-service             ║
            ║    GET  /api/users/{id}          → user-service                ║
            ║    GET  /api/accounts/{id}       → account-service             ║
            ║                                                                 ║
            ║  Features:                                                      ║
            ║    ✓ Load balancing via Eureka                                 ║
            ║    ✓ Circuit breaker (fail-fast)                               ║
            ║    ✓ Retry with exponential backoff                            ║
            ║    ✓ Distributed tracing                                       ║
            ╚════════════════════════════════════════════════════════════════╝
            """);
    }
}
