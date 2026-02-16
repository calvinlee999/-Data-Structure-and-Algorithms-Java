# API Gateway with Spring Cloud Gateway
# Routing, Load Balancing, Security, Rate Limiting

**Spring Boot 3.2 + Spring Cloud Gateway - Production-Ready API Gateway for Microservices**

An API Gateway is the single entry point for all client requests in a microservices architecture.

---

## Table of Contents

1. [Why API Gateway?](#why-api-gateway)
2. [Setup & Dependencies](#setup--dependencies)
3. [Basic Routing](#basic-routing)
4. [Load Balancing](#load-balancing)
5. [Request/Response Filters](#requestresponse-filters)
6. [Security Integration](#security-integration)
7. [Rate Limiting](#rate-limiting)
8. [Circuit Breaker Integration](#circuit-breaker-integration)
9. [Distributed Tracing](#distributed-tracing)
10. [Production Configuration](#production-configuration)

---

## Why API Gateway?

### Traditional Approach (Without Gateway)
```
Client App → Customer Service (port 8081)
Client App → Account Service (port 8082)
Client App → Transaction Service (port 8083)
Client App → Payment Service (port 8084)
```

**Problems:**
- Client needs to know all service locations
- No centralized authentication
- No centralized rate limiting
- CORS configuration duplicated
- Difficult to version APIs

### With API Gateway
```
Client App → API Gateway → Customer Service
                         → Account Service
                         → Transaction Service
                         → Payment Service
```

**Benefits:**
- ✅ Single entry point
- ✅ Centralized authentication/authorization
- ✅ Centralized rate limiting
- ✅ Request/response transformation
- ✅ Load balancing
- ✅ Circuit breaker integration
- ✅ Monitoring & logging
- ✅ API versioning

---

## Setup & Dependencies

### Maven Dependencies

```xml
<!-- pom.xml -->
<properties>
    <java.version>21</java.version>
    <spring-cloud.version>2023.0.0</spring-cloud.version>
</properties>

<dependencies>
    <!-- Spring Cloud Gateway (Reactive) -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    
    <!-- Service Discovery -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    
    <!-- Circuit Breaker -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-circuitbreaker-reactor-resilience4j</artifactId>
    </dependency>
    
    <!-- Security (OAuth2/JWT) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>
    
    <!-- Redis for Rate Limiting -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
    </dependency>
    
    <!-- Actuator -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Application Class

```java
package com.calvin.fintech.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API Gateway Application
 * 
 * Single entry point for all microservices.
 * Think of this as the "front desk" of a building - all visitors
 * must check in here before accessing any office.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```

---

## Basic Routing

### Configuration-Based Routing (application.yml)

```yaml
# application.yml
server:
  port: 8080

spring:
  application:
    name: api-gateway
  
  cloud:
    gateway:
      # Global CORS configuration
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins: "http://localhost:3000"
            allowedHeaders: "*"
            allowedMethods:
              - GET
              - POST
              - PUT
              - DELETE
              - OPTIONS
            allowCredentials: true
      
      # Route definitions
      routes:
        # Customer Service Routes
        - id: customer-service
          uri: lb://CUSTOMER-SERVICE  # lb = load-balanced via Eureka
          predicates:
            - Path=/api/v1/customers/**
          filters:
            - StripPrefix=0  # Don't modify path
            - name: CircuitBreaker
              args:
                name: customerServiceCB
                fallbackUri: forward:/fallback/customers
        
        # Account Service Routes
        - id: account-service
          uri: lb://ACCOUNT-SERVICE
          predicates:
            - Path=/api/v1/accounts/**
          filters:
            - AddRequestHeader=X-Request-Source, API-Gateway
            - AddResponseHeader=X-Response-Time, ${timestamp}
        
        # Transaction Service Routes
        - id: transaction-service
          uri: lb://TRANSACTION-SERVICE
          predicates:
            - Path=/api/v1/transactions/**
          filters:
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 10
                redis-rate-limiter.burstCapacity: 20
                redis-rate-limiter.requestedTokens: 1
        
        # Payment Service Routes (Secured)
        - id: payment-service
          uri: lb://PAYMENT-SERVICE
          predicates:
            - Path=/api/v1/payments/**
            - Method=POST,PUT,DELETE
          filters:
            - name: CircuitBreaker
              args:
                name: paymentServiceCB
                fallbackUri: forward:/fallback/payments

# Eureka Configuration
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
    fetchRegistry: true
    registerWithEureka: true
```

### Java-Based Routing (More Flexible)

```java
package com.calvin.fintech.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Gateway Route Configuration
 * 
 * Defines how requests are routed to backend services.
 */
@Configuration
public class GatewayRoutesConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            
            // Customer Service - Simple routing
            .route("customer-service", r -> r
                .path("/api/v1/customers/**")
                .uri("lb://CUSTOMER-SERVICE"))
            
            // Account Service - With filters
            .route("account-service", r -> r
                .path("/api/v1/accounts/**")
                .filters(f -> f
                    .addRequestHeader("X-Gateway-Request", "true")
                    .addResponseHeader("X-Gateway-Response", "true")
                    .retry(config -> config
                        .setRetries(3)
                        .setMethods(HttpMethod.GET)
                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
                .uri("lb://ACCOUNT-SERVICE"))
            
            // Transaction Service - With circuit breaker
            .route("transaction-service", r -> r
                .path("/api/v1/transactions/**")
                .filters(f -> f
                    .circuitBreaker(config -> config
                        .setName("transactionServiceCB")
                        .setFallbackUri("forward:/fallback/transactions")))
                .uri("lb://TRANSACTION-SERVICE"))
            
            // Admin endpoints - Restricted by header
            .route("admin-service", r -> r
                .path("/api/v1/admin/**")
                .and()
                .header("X-Admin-Token")  // Only requests with this header
                .filters(f -> f
                    .stripPrefix(2))  // Remove /api/v1 from path
                .uri("lb://ADMIN-SERVICE"))
            
            // Version 2 API - Path rewrite
            .route("transaction-service-v2", r -> r
                .path("/api/v2/transactions/**")
                .filters(f -> f
                    .rewritePath("/api/v2/(?<segment>.*)", "/api/v1/${segment}"))
                .uri("lb://TRANSACTION-SERVICE-V2"))
            
            .build();
    }
}
```

---

## Load Balancing

Spring Cloud Gateway automatically load balances requests when using `lb://` URI scheme with Eureka.

### Load Balancing Strategies

```java
package com.calvin.fintech.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Load Balancer Configuration
 */
@Configuration
public class LoadBalancerConfig {
    
    /**
     * Use Round Robin load balancing (default)
     * Requests distributed evenly across all instances.
     */
    @Bean
    public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
            ConfigurableApplicationContext context) {
        return ServiceInstanceListSupplier.builder()
            .withDiscoveryClient()
            .withHealthChecks()  // Only route to healthy instances
            .build(context);
    }
}
```

---

## Request/Response Filters

### Global Filters (Apply to All Routes)

```java
package com.calvin.fintech.gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

/**
 * Global Logging Filter
 * 
 * Adds correlation ID to all requests for distributed tracing.
 */
@Component
public class LoggingFilter implements GlobalFilter, Ordered {
    
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Generate or extract correlation ID
        String correlationId = exchange.getRequest()
            .getHeaders()
            .getFirst(CORRELATION_ID_HEADER);
        
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }
        
        // Add correlation ID to request
        ServerHttpRequest request = exchange.getRequest()
            .mutate()
            .header(CORRELATION_ID_HEADER, correlationId)
            .build();
        
        ServerWebExchange modifiedExchange = exchange.mutate()
            .request(request)
            .build();
        
        // Log request
        log.info("Request: {} {} [{}]",
            request.getMethod(),
            request.getPath(),
            correlationId);
        
        long startTime = System.currentTimeMillis();
        
        // Continue filter chain
        return chain.filter(modifiedExchange)
            .doFinally(signalType -> {
                // Log response
                long duration = System.currentTimeMillis() - startTime;
                log.info("Response: {} {} [{}] - {}ms",
                    request.getMethod(),
                    request.getPath(),
                    correlationId,
                    duration);
            });
    }
    
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;  // Run first
    }
}
```

### Authentication Filter

```java
package com.calvin.fintech.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * JWT Authentication Filter
 * 
 * Validates JWT token in Authorization header.
 */
@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    
    private final JwtTokenProvider jwtTokenProvider;
    
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");
            
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return unauthorized(exchange);
            }
            
            String token = authHeader.substring(7);
            
            if (!jwtTokenProvider.validateToken(token)) {
                return unauthorized(exchange);
            }
            
            // Add user info to request headers
            String username = jwtTokenProvider.getUsername(token);
            ServerHttpRequest modifiedRequest = exchange.getRequest()
                .mutate()
                .header("X-User-Name", username)
                .build();
            
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        };
    }
    
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
    
    public static class Config {
        // Configuration properties
    }
}
```

### Usage in Routes

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: secured-transaction-service
          uri: lb://TRANSACTION-SERVICE
          predicates:
            - Path=/api/v1/transactions/**
          filters:
            - JwtAuthenticationFilter  # Custom filter
            - AddRequestHeader=X-Authenticated, true
```

---

## Security Integration

### OAuth2 Resource Server Configuration

```java
package com.calvin.fintech.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Security Configuration for API Gateway
 * 
 * Validates JWT tokens from OAuth2 authorization server.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange(exchanges -> exchanges
                // Public endpoints
                .pathMatchers("/api/v1/auth/**").permitAll()
                .pathMatchers("/actuator/health").permitAll()
                
                // Admin endpoints (requires ADMIN role)
                .pathMatchers("/api/v1/admin/**").hasRole("ADMIN")
                
                // All other endpoints require authentication
                .anyExchange().authenticated()
            )
            
            // OAuth2 Resource Server (JWT validation)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            )
            
            // CSRF protection (disabled for API)
            .csrf(ServerHttpSecurity.CsrfSpec::disable);
        
        return http.build();
    }
    
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
```

---

## Rate Limiting

### Redis-Based Rate Limiting

```yaml
# application.yml
spring:
  redis:
    host: localhost
    port: 6379
  
  cloud:
    gateway:
      routes:
        - id: rate-limited-route
          uri: lb://TRANSACTION-SERVICE
          predicates:
            - Path=/api/v1/transactions/**
          filters:
            - name: RequestRateLimiter
              args:
                # Tokens per second
                redis-rate-limiter.replenishRate: 10
                
                # Burst capacity (max tokens)
                redis-rate-limiter.burstCapacity: 20
                
                # Tokens per request
                redis-rate-limiter.requestedTokens: 1
                
                # Key resolver (rate limit per user)
                key-resolver: "#{@userKeyResolver}"
```

### Custom Key Resolver

```java
package com.calvin.fintech.gateway.ratelimit;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Rate limit by authenticated user
 */
@Component
public class UserKeyResolver implements KeyResolver {
    
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // Rate limit per user
        String username = exchange.getRequest()
            .getHeaders()
            .getFirst("X-User-Name");
        
        return Mono.just(username != null ? username : "anonymous");
    }
}
```

---

## Circuit Breaker Integration

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: transaction-service-with-cb
          uri: lb://TRANSACTION-SERVICE
          predicates:
            - Path=/api/v1/transactions/**
          filters:
            - name: CircuitBreaker
              args:
                name: transactionServiceCB
                fallbackUri: forward:/fallback/transactions

# Resilience4j Circuit Breaker Configuration
resilience4j:
  circuitbreaker:
    instances:
      transactionServiceCB:
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
        permittedNumberOfCallsInHalfOpenState: 3
```

### Fallback Controller

```java
package com.calvin.fintech.gateway.fallback;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

/**
 * Fallback endpoints when services are unavailable
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {
    
    @GetMapping("/transactions")
    @PostMapping("/transactions")
    public ResponseEntity<ErrorResponse> transactionFallback() {
        ErrorResponse error = new ErrorResponse(
            "SERVICE_UNAVAILABLE",
            "Transaction service is temporarily unavailable. Please try again later."
        );
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(error);
    }
    
    @GetMapping("/customers")
    public ResponseEntity<ErrorResponse> customerFallback() {
        ErrorResponse error = new ErrorResponse(
            "SERVICE_UNAVAILABLE",
            "Customer service is temporarily unavailable."
        );
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(error);
    }
}
```

---

## Distributed Tracing

### Add Headers for Tracing

```java
package com.calvin.fintech.gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;

/**
 * Distributed Tracing Filter
 * 
 * Adds trace IDs to all requests for end-to-end tracing.
 */
@Component
public class TracingFilter implements GlobalFilter {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = UUID.randomUUID().toString();
        String spanId = UUID.randomUUID().toString();
        
        ServerHttpRequest request = exchange.getRequest()
            .mutate()
            .header("X-Trace-ID", traceId)
            .header("X-Span-ID", spanId)
            .build();
        
        return chain.filter(exchange.mutate().request(request).build());
    }
}
```

---

## Production Configuration

```yaml
# application-production.yml
server:
  port: 8080
  netty:
    connection-timeout: 30s
  compression:
    enabled: true

spring:
  threads:
    virtual:
      enabled: true  # Java 21 virtual threads
  
  cloud:
    gateway:
      httpclient:
        connect-timeout: 5000
        response-timeout: 30s
        pool:
          type: elastic
          max-idle-time: 10s
          eviction-interval: 30s

# Actuator
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus,gateway
  metrics:
    export:
      prometheus:
        enabled: true

# Logging
logging:
  level:
    org.springframework.cloud.gateway: INFO
    reactor.netty: INFO
    com.calvin.fintech: DEBUG
```

---

## Summary

**API Gateway provides:**
- ✅ Single entry point for all services
- ✅ Centralized authentication/authorization
- ✅ Load balancing across service instances
- ✅ Rate limiting per user/IP
- ✅ Circuit breaker for resilience
- ✅ Request/response transformation
- ✅ Distributed tracing support
- ✅ CORS handling
- ✅ Monitoring & metrics

**Key Benefits:**
- Simplified client integration
- Centralized cross-cutting concerns
- Better security posture
- Easier to evolve APIs (versioning)
- Performance optimization (caching, compression)

---

**Next:** [Distributed Tracing with OpenTelemetry & Zipkin](./DISTRIBUTED_TRACING_GUIDE.md)
