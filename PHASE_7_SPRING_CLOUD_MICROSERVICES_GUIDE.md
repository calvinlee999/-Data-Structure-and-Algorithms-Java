# Phase 7: Spring Cloud & Microservices Patterns - The Functional Distributed System

**Date**: February 16, 2026  
**Target**: Apply Phase 6 functional patterns in distributed microservices architecture  
**Goal**: Build production-ready microservices with Spring Cloud ecosystem

---

## TABLE OF CONTENTS

1. [Executive Summary](#1-executive-summary)
2. [Spring Cloud Ecosystem Overview](#2-spring-cloud-ecosystem-overview)
3. [Microservices Architecture Pattern](#3-microservices-architecture-pattern)
4. [Service Discovery (Eureka)](#4-service-discovery-eureka)
5. [API Gateway (Spring Cloud Gateway)](#5-api-gateway-spring-cloud-gateway)
6. [Circuit Breaker & Resilience](#6-circuit-breaker--resilience)
7. [Distributed Configuration](#7-distributed-configuration)
8. [Load Balancing](#8-load-balancing)
9. [Distributed Tracing](#9-distributed-tracing)
10. [Message-Driven Microservices](#10-message-driven-microservices)
11. [Functional Patterns in Distributed Systems](#11-functional-patterns-in-distributed-systems)
12. [Production Deployment](#12-production-deployment)

---

## 1. EXECUTIVE SUMMARY

### 1.1 The Evolution: Monolith → Microservices

Spring Cloud represents the **final convergence** of functional Java with distributed systems. Where Phase 6 showed how Java 21 + Spring Boot 3.2 creates **declarative single-service applications**, Phase 7 demonstrates how **Spring Cloud** orchestrates **hundreds of functional microservices** working together.

### 1.2 Key Paradigm Shift

| Aspect | Monolith (Traditional) | Microservices (Functional) |
|--------|------------------------|---------------------------|
| **Deployment** | Single WAR/JAR | Independent services |
| **Scaling** | Vertical (bigger server) | Horizontal (more instances) |
| **Communication** | In-process method calls | HTTP/gRPC/Message queues |
| **Data** | Single shared database | Database per service |
| **Configuration** | application.properties | Distributed config server |
| **Discovery** | Static endpoints | Dynamic service registry |
| **Resilience** | Monolithic failure | Circuit breakers, bulkheads |
| **Deployment** | Big-bang release | Independent deployments |

### 1.3 Spring Cloud Stack (2026)

```
┌─────────────────────────────────────────────────────┐
│ Spring Cloud Gateway (API Gateway)                   │
│ • Functional routing (from Phase 6)                  │
│ • Rate limiting, authentication                      │
│ • Request/response transformation                    │
└─────────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────────┐
│ Service Discovery (Eureka/Consul)                    │
│ • Dynamic service registration                       │
│ • Health checks                                      │
│ • Load balancing metadata                            │
└─────────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────────┐
│ Microservices (Spring Boot 3.2)                      │
│ • Functional endpoints (RouterFunction)              │
│ • Sealed interfaces for contracts                    │
│ • Records for DTOs                                   │
│ • Virtual threads for concurrency                    │
└─────────────────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────────────────┐
│ Observability (Micrometer + OpenTelemetry)           │
│ • Distributed tracing across services                │
│ • Metrics aggregation                                │
│ • Centralized logging                                │
└─────────────────────────────────────────────────────┘
```

### 1.4 Why This Matters for FinTech

**Traditional Banking**:
- Monolithic core banking system (COBOL/Java EE)
- 24-hour batch processing
- Downtime for deployments
- Slow feature releases

**Modern FinTech**:
- Microservices architecture
- Real-time processing
- Zero-downtime deployments
- Daily/hourly feature releases

**Business Impact**:
- **Payment Processing**: 10,000 TPS per service × 10 services = 100,000 TPS
- **Availability**: 99.99% uptime (52 minutes downtime/year)
- **Time to Market**: 2 weeks → 2 days
- **Cost**: Scale only what you need (e.g., scale payment service during Black Friday)

---

## 2. SPRING CLOUD ECOSYSTEM OVERVIEW

### 2.1 Spring Cloud Components (2026)

| Component | Purpose | Phase 6 Connection |
|-----------|---------|-------------------|
| **Spring Cloud Gateway** | API Gateway | Uses RouterFunction from Phase 6 |
| **Spring Cloud LoadBalancer** | Client-side load balancing | Works with virtual threads |
| **Spring Cloud Config** | Distributed configuration | YAML/properties from config server |
| **Spring Cloud Circuit Breaker** | Resilience patterns | Resilience4j (introduced in Phase 6) |
| **Spring Cloud Stream** | Message-driven | Works with Function<T,R> beans |
| **Micrometer Tracing** | Distributed tracing | OpenTelemetry (introduced in Phase 6) |

### 2.2 Dependencies (Spring Boot 3.2.x + Spring Cloud 2023.0.x)

```xml
<properties>
    <java.version>21</java.version>
    <spring-boot.version>3.2.2</spring-boot.version>
    <spring-cloud.version>2023.0.0</spring-cloud.version>
</properties>

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

<dependencies>
    <!-- Spring Cloud Gateway -->
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
        <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
    </dependency>
    
    <!-- Config Client -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
    
    <!-- Load Balancer -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-loadbalancer</artifactId>
    </dependency>
    
    <!-- Distributed Tracing -->
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-tracing-bridge-otel</artifactId>
    </dependency>
    
    <!-- Virtual Threads Support (Java 21) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
</dependencies>
```

---

## 3. MICROSERVICES ARCHITECTURE PATTERN

### 3.1 FinTech Payment Platform Architecture

```
                    ┌──────────────────────┐
                    │   API Gateway        │
                    │ (Spring Cloud GW)    │
                    │ Port: 8080           │
                    └──────────┬───────────┘
                               │
                ┌──────────────┼──────────────┐
                │              │              │
    ┌───────────▼────┐  ┌─────▼──────┐  ┌───▼──────────┐
    │ Payment Service│  │User Service│  │Account Service│
    │ Port: 8081     │  │Port: 8082  │  │Port: 8083     │
    └───────┬────────┘  └─────┬──────┘  └───┬──────────┘
            │                 │              │
            └─────────┬───────┴──────────────┘
                      │
          ┌───────────▼───────────┐
          │ Service Registry      │
          │ (Eureka Server)       │
          │ Port: 8761            │
          └───────────┬───────────┘
                      │
          ┌───────────▼───────────┐
          │ Config Server         │
          │ Port: 8888            │
          └───────────────────────┘
```

### 3.2 Service Responsibilities

#### Payment Service (Port 8081)
- Process credit card/crypto/bank transfers
- Uses **sealed interfaces** from Phase 6 (PaymentRequest)
- Uses **records** for DTOs
- Calls User Service for validation
- Calls Account Service for balance checks

#### User Service (Port 8082)
- User authentication/authorization
- KYC (Know Your Customer) verification
- Returns user profile as **record**

#### Account Service (Port 8083)
- Account balance management
- Transaction history
- Uses **Stream-oriented JPA** from Phase 6

#### API Gateway (Port 8080)
- Routes requests to services
- Rate limiting
- Authentication/Authorization
- Request transformation

#### Service Registry (Port 8761)
- Service discovery (Eureka)
- Health monitoring
- Load balancing metadata

#### Config Server (Port 8888)
- Centralized configuration
- Environment-specific configs
- Dynamic refresh

### 3.3 Service Contracts with Sealed Interfaces

**Why Sealed Interfaces in Microservices?**
- ✅ Compile-time contract validation
- ✅ Prevents unauthorized service implementations
- ✅ Type-safe service-to-service communication
- ✅ Self-documenting API contracts

```java
// Shared contract library (service-contracts.jar)
package com.calvin.fintech.contracts;

// Service contract: Only authorized services can implement
public sealed interface ServiceRequest 
    permits PaymentServiceRequest, UserServiceRequest, AccountServiceRequest {
    String correlationId();
    Instant timestamp();
}

// Payment service contract
public sealed interface PaymentServiceRequest extends ServiceRequest
    permits ProcessPaymentRequest, ValidatePaymentRequest {
}

public record ProcessPaymentRequest(
    String correlationId,
    Instant timestamp,
    PaymentRequest paymentDetails,  // From Phase 6
    String userId
) implements PaymentServiceRequest {}

public record ValidatePaymentRequest(
    String correlationId,
    Instant timestamp,
    PaymentRequest paymentDetails
) implements PaymentServiceRequest {}
```

**Benefits**:
1. **Versioning**: New sealed interface = new API version
2. **Backward Compatibility**: Old clients use old sealed interface
3. **Contract Testing**: Compiler enforces contract
4. **Security**: Can't inject malicious implementations

---

## 4. SERVICE DISCOVERY (EUREKA)

### 4.1 Why Service Discovery?

**Problem**: In microservices, service instances are dynamic:
- Instances scale up/down automatically
- Instances crash and restart with new IPs
- Instances deploy to different environments
- Can't hardcode IP addresses

**Solution**: Service Registry (Eureka)
- Services **register** themselves on startup
- Services **discover** other services by name
- Health checks detect failures
- Load balancing uses live instances only

### 4.2 Eureka Server Setup

```java
package com.calvin.fintech.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer  // Enable Eureka server
public class ServiceRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryApplication.class, args);
    }
}
```

```yaml
# application.yml (Eureka Server)
server:
  port: 8761

spring:
  application:
    name: service-registry

eureka:
  client:
    register-with-eureka: false  # This server doesn't register itself
    fetch-registry: false
  server:
    enable-self-preservation: false  # Disable in dev (enable in prod)
```

### 4.3 Eureka Client (Microservice)

```java
package com.calvin.fintech.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  // Register with Eureka
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
```

```yaml
# application.yml (Payment Service)
server:
  port: 8081

spring:
  application:
    name: payment-service  # Service name in registry
  threads:
    virtual:
      enabled: true  # Virtual threads from Phase 6

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/  # Eureka server
  instance:
    prefer-ip-address: true
    lease-renewal-interval-in-seconds: 30  # Heartbeat every 30s
```

### 4.4 Service-to-Service Communication

**Old Way (Hardcoded URL)**:
```java
// ❌ BAD: Hardcoded URL
RestTemplate restTemplate = new RestTemplate();
String url = "http://localhost:8082/api/users/" + userId;
User user = restTemplate.getForObject(url, User.class);
```

**New Way (Service Discovery)**:
```java
// ✅ GOOD: Use service name, Eureka resolves
@Bean
@LoadBalanced  // Enable load balancing via Eureka
public RestTemplate restTemplate() {
    return new RestTemplate();
}

// Usage
RestTemplate restTemplate = new RestTemplate();
String url = "http://user-service/api/users/" + userId;  // Service name!
User user = restTemplate.getForObject(url, User.class);
```

**Even Better (Functional with WebClient + Virtual Threads)**:
```java
@Bean
@LoadBalanced
public WebClient.Builder webClientBuilder() {
    return WebClient.builder();
}

// Usage (Non-blocking + Virtual Threads)
public Mono<User> getUser(String userId) {
    return webClient
        .get()
        .uri("http://user-service/api/users/{id}", userId)
        .retrieve()
        .bodyToMono(User.class);
}
```

---

## 5. API GATEWAY (SPRING CLOUD GATEWAY)

### 5.1 Why API Gateway?

**Without Gateway**:
```
Mobile App → http://payment-service:8081/api/payments
Mobile App → http://user-service:8082/api/users
Mobile App → http://account-service:8083/api/accounts
```
- ❌ Clients know all service URLs
- ❌ CORS configuration on every service
- ❌ Authentication on every service
- ❌ Rate limiting on every service
- ❌ No centralized logging

**With Gateway**:
```
Mobile App → http://gateway:8080/payments/** → payment-service
Mobile App → http://gateway:8080/users/**    → user-service
Mobile App → http://gateway:8080/accounts/** → account-service
```
- ✅ Single entry point
- ✅ Centralized authentication
- ✅ Centralized rate limiting
- ✅ Request/response transformation
- ✅ Centralized observability

### 5.2 Spring Cloud Gateway Configuration

```java
package com.calvin.fintech.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  // Discover services via Eureka
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```

### 5.3 Functional Route Configuration (Phase 6 Pattern!)

```java
package com.calvin.fintech.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class GatewayRoutesConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // Payment Service Routes
            .route("payment-service", r -> r
                .path("/api/payments/**")
                .filters(f -> f
                    .stripPrefix(1)  // Remove /api prefix
                    .addRequestHeader("X-Gateway", "ApiGateway")
                    .circuitBreaker(config -> config
                        .setName("paymentCircuitBreaker")
                        .setFallbackUri("forward:/fallback/payments")
                    )
                    .retry(config -> config
                        .setRetries(3)
                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)
                    )
                )
                .uri("lb://payment-service")  // Load-balanced via Eureka
            )
            
            // User Service Routes
            .route("user-service", r -> r
                .path("/api/users/**")
                .filters(f -> f
                    .stripPrefix(1)
                    .addRequestHeader("X-Gateway", "ApiGateway")
                    .circuitBreaker(config -> config
                        .setName("userCircuitBreaker")
                        .setFallbackUri("forward:/fallback/users")
                    )
                )
                .uri("lb://user-service")
            )
            
            // Account Service Routes
            .route("account-service", r -> r
                .path("/api/accounts/**")
                .filters(f -> f
                    .stripPrefix(1)
                    .addRequestHeader("X-Gateway", "ApiGateway")
                    .circuitBreaker(config -> config
                        .setName("accountCircuitBreaker")
                        .setFallbackUri("forward:/fallback/accounts")
                    )
                )
                .uri("lb://account-service")
            )
            
            // Admin Routes (with authentication)
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
```

**Key Features**:
- ✅ `lb://service-name` - Load balancing via Eureka
- ✅ Circuit breaker for resilience
- ✅ Retry with exponential backoff
- ✅ Request header manipulation
- ✅ Path rewriting

### 5.4 Gateway Filters (Cross-Cutting Concerns)

```java
package com.calvin.fintech.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String correlationId = UUID.randomUUID().toString();
        Instant startTime = Instant.now();
        
        // Add correlation ID to request
        exchange.getRequest().mutate()
            .header("X-Correlation-ID", correlationId)
            .build();
        
        // Log request
        System.out.println("Gateway Request: " + 
            exchange.getRequest().getMethod() + " " +
            exchange.getRequest().getURI() + 
            " | CorrelationID: " + correlationId
        );
        
        // Continue filter chain
        return chain.filter(exchange)
            .doFinally(signalType -> {
                long duration = Duration.between(startTime, Instant.now()).toMillis();
                System.out.println("Gateway Response: " + 
                    exchange.getResponse().getStatusCode() +
                    " | Duration: " + duration + "ms" +
                    " | CorrelationID: " + correlationId
                );
            });
    }
    
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;  // Run first
    }
}
```

### 5.5 Rate Limiting (Prevent DoS)

```yaml
# application.yml (Gateway)
spring:
  cloud:
    gateway:
      routes:
        - id: payment-service-with-ratelimit
          uri: lb://payment-service
          predicates:
            - Path=/api/payments/**
          filters:
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 10  # 10 requests per second
                redis-rate-limiter.burstCapacity: 20  # Burst up to 20
                redis-rate-limiter.requestedTokens: 1
```

---

## 6. CIRCUIT BREAKER & RESILIENCE

### 6.1 Why Circuit Breakers in Microservices?

**Problem**: Service failures cascade

```
User Request → Gateway → Payment Service (SLOW/DOWN)
                                ↓
                         User Service (waiting...)
                                ↓
                         Account Service (waiting...)
                                ↓
                         All services timeout! System down!
```

**Solution**: Circuit Breaker (from Phase 6, now distributed)

```
User Request → Gateway → Payment Service [CIRCUIT OPEN]
                                ↓
                         Fallback: "Payment temporarily unavailable"
                                ↓
                         User/Account services continue working!
```

### 6.2 Circuit Breaker States

```
        [CLOSED] ──────► [OPEN] ──────► [HALF_OPEN]
            ▲                                │
            │                                │
            └────────────────────────────────┘
            
CLOSED:     Normal operation, requests flow
OPEN:       Failures detected, reject requests immediately (fail-fast)
HALF_OPEN:  Test if service recovered, allow few requests
```

### 6.3 Resilience4j Circuit Breaker (Inter-Service)

```java
package com.calvin.fintech.payment.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceClient {
    
    private final RestTemplate restTemplate;
    
    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @CircuitBreaker(name = "user-service", fallbackMethod = "getUserFallback")
    @Retry(name = "user-service")
    public User getUser(String userId) {
        // Call user-service via Eureka
        String url = "http://user-service/api/users/" + userId;
        return restTemplate.getForObject(url, User.class);
    }
    
    // Fallback: Return cached/default user
    private User getUserFallback(String userId, Exception ex) {
        System.err.println("Circuit breaker activated for user-service: " + ex.getMessage());
        
        // Return cached user or default
        return new User(
            userId,
            "Unknown User",
            "unknown@example.com",
            false  // Not verified
        );
    }
}
```

**Configuration**:
```yaml
# application.yml
resilience4j:
  circuitbreaker:
    instances:
      user-service:
        failure-rate-threshold: 50  # Open at 50% failures
        wait-duration-in-open-state: 30s  # Wait 30s before HALF_OPEN
        sliding-window-size: 10  # Track last 10 requests
        minimum-number-of-calls: 5  # Need 5 calls before evaluating
        permitted-number-of-calls-in-half-open-state: 3
        
  retry:
    instances:
      user-service:
        max-attempts: 3
        wait-duration: 100ms
        exponential-backoff-multiplier: 2
```

### 6.4 Functional Circuit Breaker Pattern

**Combining Phase 6 Functions with Circuit Breaker**:

```java
package com.calvin.fintech.payment.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ResilientFunctionConfig {
    
    @Bean
    public Function<PaymentRequest, PaymentResponse> processPaymentWithResilience(
            Function<PaymentRequest, PaymentResponse> processPayment,
            CircuitBreakerRegistry circuitBreakerRegistry) {
        
        CircuitBreaker circuitBreaker = circuitBreakerRegistry
            .circuitBreaker("payment-processing");
        
        // Wrap function with circuit breaker
        return CircuitBreaker
            .decorateFunction(circuitBreaker, processPayment)
            .andThen(response -> {
                // Additional logging
                System.out.println("Payment processed: " + response.transactionId());
                return response;
            });
    }
}
```

**Benefits**:
- ✅ Composable functions with resilience
- ✅ Works with Spring Cloud Function (can deploy to Lambda/Kafka)
- ✅ Type-safe
- ✅ Testable in isolation

---

## 7. DISTRIBUTED CONFIGURATION

### 7.1 Why Config Server?

**Problem**: Microservices have many configuration files
```
payment-service/application.yml
payment-service/application-dev.yml
payment-service/application-prod.yml
user-service/application.yml
user-service/application-dev.yml
...
```
- ❌ Configuration scattered across services
- ❌ Hard to change config (requires redeployment)
- ❌ Secrets in source code
- ❌ No audit trail

**Solution**: Spring Cloud Config Server
- ✅ Centralized configuration (Git repository)
- ✅ Environment-specific configs
- ✅ Dynamic refresh (no redeployment)
- ✅ Secrets management (Vault integration)
- ✅ Audit trail (Git history)

### 7.2 Config Server Setup

```java
package com.calvin.fintech.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer  // Enable config server
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

```yaml
# application.yml (Config Server)
server:
  port: 8888

spring:
  application:
    name: config-server
  cloud:
    config:
      server:
        git:
          uri: https://github.com/myorg/config-repo
          default-label: main
          search-paths: '{application}'
        # Alternative: Local file system (for dev)
        # native:
        #   search-locations: file:///config-repo
```

### 7.3 Config Repository Structure

```
config-repo/
├── payment-service.yml          # Default config
├── payment-service-dev.yml      # Dev environment
├── payment-service-prod.yml     # Prod environment
├── user-service.yml
├── user-service-dev.yml
├── user-service-prod.yml
├── application.yml              # Shared config (all services)
└── application-prod.yml         # Shared prod config
```

**Example: payment-service.yml**
```yaml
# Shared payment service config
payment:
  max-amount: 100000
  supported-currencies:
    - USD
    - EUR
    - GBP
  gateway:
    timeout: 30s
```

**Example: payment-service-prod.yml**
```yaml
# Production overrides
payment:
  max-amount: 1000000  # Higher limit in prod
  gateway:
    url: https://gateway.prod.example.com
    timeout: 60s  # Longer timeout in prod
```

### 7.4 Config Client (Microservice)

```xml
<!-- Config Client Dependency -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

```yaml
# bootstrap.yml (Loaded before application.yml)
spring:
  application:
    name: payment-service
  cloud:
    config:
      uri: http://localhost:8888  # Config server
      fail-fast: true  # Fail startup if config unavailable
  profiles:
    active: dev  # Load payment-service-dev.yml
```

### 7.5 Dynamic Configuration Refresh

**Without Restart**:
```java
package com.calvin.fintech.payment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope  // Enable dynamic refresh
@ConfigurationProperties(prefix = "payment")
public class PaymentConfig {
    private double maxAmount;
    private List<String> supportedCurrencies;
    
    // Getters and setters
}
```

**Trigger Refresh**:
```bash
# Update config in Git
git commit -m "Increase max payment amount"
git push

# Refresh service (without restart!)
curl -X POST http://localhost:8081/actuator/refresh
```

---

## 8. LOAD BALANCING

### 8.1 Client-Side Load Balancing

Spring Cloud LoadBalancer provides **client-side** load balancing:

```
Payment Service Instance 1 (8081)
Payment Service Instance 2 (8082)  ← LoadBalancer chooses
Payment Service Instance 3 (8083)
```

**Without Load Balancer**:
```java
// ❌ Always calls first instance
String url = "http://localhost:8081/api/payments";
```

**With Load Balancer**:
```java
// ✅ Round-robin across all instances
String url = "http://payment-service/api/payments";  // Eureka resolves
```

### 8.2 Load Balancing Strategies

```java
package com.calvin.fintech.gateway.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class LoadBalancerConfig {
    
    // Custom load balancer: Prefer instances in same zone
    @Bean
    public ReactorLoadBalancer<ServiceInstance> zonePreferenceLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        
        return new ZonePreferenceLoadBalancer(
            loadBalancerClientFactory
                .getLazyProvider(name, ServiceInstanceListSupplier.class),
            name
        );
    }
}
```

### 8.3 Health-Based Load Balancing

```yaml
# application.yml
spring:
  cloud:
    loadbalancer:
      health-check:
        initial-delay: 0  # Check health immediately
        interval: 25s  # Check every 25s
      ribbon:
        enabled: false  # Use new LoadBalancer (not legacy Ribbon)
```

**Only healthy instances receive traffic!**

---

## 9. DISTRIBUTED TRACING

### 9.1 Why Distributed Tracing?

**Problem**: Microservices make debugging hard

```
User Request → Gateway → Payment Service → User Service → Database
                                        → Account Service → Database
```

If request fails, which service caused it? 🤔

**Solution**: Distributed Tracing (Micrometer Tracing + OpenTelemetry)

```
Trace ID: abc-123-def (same for entire request)
  Span 1: Gateway (10ms)
  Span 2: Payment Service (50ms)
    Span 3: User Service (20ms)
    Span 4: Account Service (30ms)
```

### 9.2 Micrometer Tracing Setup

```xml
<!-- Micrometer Tracing -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-otel</artifactId>
</dependency>
<dependency>
    <groupId>io.opentelemetry</groupId>
    <artifactId>opentelemetry-exporter-zipkin</artifactId>
</dependency>
```

```yaml
# application.yml (all services)
management:
  tracing:
    sampling:
      probability: 1.0  # Trace 100% of requests (use 0.1 in prod for 10%)
  zipkin:
    tracing:
      endpoint: http://localhost:9411/api/v2/spans
```

### 9.3 Automatic Trace Propagation

```java
// No code changes needed! Micrometer auto-instruments:
// - RestTemplate
// - WebClient
// - Spring Cloud Gateway
// - Spring Data JPA

@Service
public class PaymentService {
    
    private final UserServiceClient userClient;
    
    // Trace ID automatically propagated!
    public PaymentResponse processPayment(PaymentRequest request) {
        User user = userClient.getUser(request.userId());  // Trace continues
        // ... process payment
    }
}
```

### 9.4 Custom Spans

```java
package com.calvin.fintech.payment.service;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    
    private final Tracer tracer;
    
    public PaymentService(Tracer tracer) {
        this.tracer = tracer;
    }
    
    public PaymentResponse processPayment(PaymentRequest request) {
        // Create custom span
        Span span = tracer.nextSpan().name("payment.processing").start();
        
        try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
            span.tag("payment.type", request.getClass().getSimpleName());
            span.tag("payment.amount", String.valueOf(request.amount()));
            span.tag("payment.currency", request.currency());
            
            // Business logic
            PaymentResponse response = processInternal(request);
            
            span.tag("payment.status", response.status().toString());
            
            return response;
            
        } catch (Exception e) {
            span.error(e);
            throw e;
        } finally {
            span.end();
        }
    }
}
```

### 9.5 Viewing Traces (Zipkin UI)

```bash
# Start Zipkin
docker run -d -p 9411:9411 openzipkin/zipkin

# Access UI
http://localhost:9411
```

**Trace Visualization**:
```
Trace ID: abc-123
Total Duration: 100ms

├─ Gateway (10ms)
│  └─ Payment Service (80ms)
│     ├─ User Service (20ms)
│     │  └─ Database Query (15ms)
│     └─ Account Service (30ms)
│        └─ Database Query (25ms)
└─ Response (10ms)
```

---

## 10. MESSAGE-DRIVEN MICROSERVICES

### 10.1 Why Message Queues?

**Synchronous (HTTP)**:
```
Payment Service → [WAIT] → User Service → [WAIT] → Database
```
- ❌ Blocking
- ❌ Tight coupling
- ❌ No retry if service down

**Asynchronous (Message Queue)**:
```
Payment Service → Message Queue → [User Service processes when ready]
```
- ✅ Non-blocking
- ✅ Loose coupling
- ✅ Automatic retry
- ✅ Load leveling (queue absorbs spikes)

### 10.2 Spring Cloud Stream (with Kafka)

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-kafka</artifactId>
</dependency>
```

### 10.3 Functional Message Processing (Phase 6 Pattern!)

**Producer (Payment Service)**:
```java
package com.calvin.fintech.payment.config;

import com.calvin.fintech.events.PaymentProcessedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class PaymentEventProducer {
    
    // Function bean automatically binds to message queue!
    @Bean
    public Supplier<PaymentProcessedEvent> paymentProcessed() {
        return () -> {
            // Triggered by external event (e.g., REST endpoint)
            // Return events to publish
            return emitter.poll();  // Poll from internal queue
        };
    }
}
```

**Consumer (Notification Service)**:
```java
package com.calvin.fintech.notification.config;

import com.calvin.fintech.events.PaymentProcessedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PaymentEventConsumer {
    
    // Function bean automatically consumes messages!
    @Bean
    public Consumer<PaymentProcessedEvent> handlePaymentProcessed() {
        return event -> {
            System.out.println("Payment processed: " + event.transactionId());
            
            // Send notification (email, SMS, push)
            sendNotification(event);
        };
    }
}
```

**Configuration**:
```yaml
# application.yml (Producer)
spring:
  cloud:
    stream:
      bindings:
        paymentProcessed-out-0:  # Function name + -out-0
          destination: payment-events  # Kafka topic
          
# application.yml (Consumer)
spring:
  cloud:
    stream:
      bindings:
        handlePaymentProcessed-in-0:  # Function name + -in-0
          destination: payment-events  # Same topic
          group: notification-service  # Consumer group
```

**Benefits**:
- ✅ No Spring Cloud Stream-specific code
- ✅ Pure `java.util.function` types
- ✅ Can run standalone (testing)
- ✅ Works with Kafka, RabbitMQ, AWS SQS

---

## 11. FUNCTIONAL PATTERNS IN DISTRIBUTED SYSTEMS

### 11.1 Service Contracts with Records

**Traditional DTO** (verbose):
```java
public class PaymentDTO {
    private String transactionId;
    private double amount;
    private String currency;
    // ... 20 more fields
    
    // Getters, setters, equals, hashCode, toString (100+ lines)
}
```

**Functional DTO** (concise):
```java
public record PaymentDTO(
    String transactionId,
    double amount,
    String currency,
    PaymentStatus status,
    Instant timestamp
) implements Serializable {}  // JSON serialization for HTTP/messaging
```

**Benefits**:
- ✅ Immutable (thread-safe across services)
- ✅ No boilerplate
- ✅ Structural equality (easy comparisons)
- ✅ Pattern matching in consumers

### 11.2 Functional Service Composition

**Traditional (Imperative)**:
```java
public PaymentResponse processPayment(PaymentRequest request) {
    User user = userService.getUser(request.userId());
    if (user == null) {
        throw new UserNotFoundException();
    }
    
    Account account = accountService.getAccount(user.accountId());
    if (account == null) {
        throw new AccountNotFoundException();
    }
    
    if (account.balance() < request.amount()) {
        return PaymentResponse.declined("Insufficient funds");
    }
    
    // Process payment
    return processInternal(request);
}
```

**Functional (Reactive)**:
```java
public Mono<PaymentResponse> processPayment(PaymentRequest request) {
    return userService.getUser(request.userId())  // Mono<User>
        .flatMap(user -> accountService.getAccount(user.accountId()))  // Mono<Account>
        .filter(account -> account.balance() >= request.amount())
        .flatMap(account -> processInternal(request))  // Mono<PaymentResponse>
        .switchIfEmpty(Mono.just(PaymentResponse.declined("Insufficient funds")));
}
```

**Benefits**:
- ✅ Non-blocking (virtual threads scale better)
- ✅ Composable (map, flatMap, filter)
- ✅ Error handling (onErrorResume)
- ✅ Timeout handling (timeout operator)

### 11.3 Pattern Matching for Service Routing

**Using Sealed Interfaces from Phase 6**:
```java
package com.calvin.fintech.gateway.router;

import com.calvin.fintech.contracts.*;

public class ServiceRouter {
    
    // Pattern matching routes to correct service
    public Mono<ServiceResponse> route(ServiceRequest request) {
        return switch (request) {
            case ProcessPaymentRequest req -> 
                paymentService.processPayment(req);
                
            case ValidatePaymentRequest req ->
                paymentService.validatePayment(req);
                
            case GetUserRequest req ->
                userService.getUser(req);
                
            case GetAccountRequest req ->
                accountService.getAccount(req);
                
            // Compiler error if any case missing!
        };
    }
}
```

---

## 12. PRODUCTION DEPLOYMENT

### 12.1 Kubernetes Deployment

**Service Registry (Eureka)**:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: service-registry
spec:
  replicas: 2  # High availability
  selector:
    matchLabels:
      app: service-registry
  template:
    metadata:
      labels:
        app: service-registry
    spec:
      containers:
      - name: service-registry
        image: fintech/service-registry:latest
        ports:
        - containerPort: 8761
        env:
        - name: JAVA_OPTS
          value: "-Xmx512m -XX:+UseZGC -XX:+UseVirtualThreads"
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8761
          initialDelaySeconds: 60
          periodSeconds: 10
        readinessProbe:
          httpGet:
            path: /actuator/health
            port: 8761
          initialDelaySeconds: 30
          periodSeconds: 5
---
apiVersion: v1
kind: Service
metadata:
  name: service-registry
spec:
  type: ClusterIP
  ports:
  - port: 8761
    targetPort: 8761
  selector:
    app: service-registry
```

**Payment Service**:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: payment-service
spec:
  replicas: 3  # Scale horizontally
  selector:
    matchLabels:
      app: payment-service
  template:
    metadata:
      labels:
        app: payment-service
    spec:
      containers:
      - name: payment-service
        image: fintech/payment-service:latest
        ports:
        - containerPort: 8081
        env:
        - name: EUREKA_CLIENT_SERVICEURL_DEFAULTZONE
          value: "http://service-registry:8761/eureka/"
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: JAVA_OPTS
          value: "-Xmx1g -XX:+UseZGC -XX:+UseVirtualThreads"
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "1000m"
        livenessProbe:
          httpGet:
            path: /actuator/health/liveness
            port: 8081
          initialDelaySeconds: 60
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8081
          initialDelaySeconds: 30
---
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: payment-service-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: payment-service
  minReplicas: 3
  maxReplicas: 10
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70
  - type: Resource
    resource:
      name: memory
      target:
        type: Utilization
        averageUtilization: 80
```

### 12.2 Production Checklist

- [x] Service Discovery (Eureka) with HA (2+ instances)
- [x] API Gateway with rate limiting
- [x] Circuit breakers on all inter-service calls
- [x] Distributed tracing (Micrometer + Zipkin/Jaeger)
- [x] Centralized logging (ELK/Loki)
- [x] Configuration server (Spring Cloud Config)
- [x] Health checks (liveness + readiness probes)
- [x] Horizontal auto-scaling (HPA)
- [x] Resource limits (CPU + memory)
- [x] Secrets management (Kubernetes Secrets/Vault)
- [x] Virtual threads enabled (Java 21)
- [x] GraalVM native images (optional, for faster startup)
- [x] Blue-green deployment strategy
- [x] Monitoring (Prometheus + Grafana)
- [x] Alerting (PagerDuty/OpsGenie)

---

## CONCLUSION

Phase 7 demonstrates how **functional patterns from Phase 6** scale to **distributed microservices**:

1. **Sealed Interfaces** → Service contracts with compile-time validation
2. **Records** → DTOs for inter-service communication
3. **Pattern Matching** → Service routing logic
4. **Virtual Threads** → Scale blocking calls to millions of concurrent operations
5. **Function Beans** → Message-driven consumers/producers
6. **RouterFunction** → API Gateway functional routing

**Business Impact**:
- **Scalability**: 100,000+ TPS across distributed services
- **Availability**: 99.99% uptime (52 min/year downtime)
- **Time to Market**: Deploy individual services independently
- **Cost**: Scale only what's needed (e.g., scale payments during Black Friday)

**Next**: Phase 8 will add **Spring Security** with OAuth2/OIDC for authentication/authorization across microservices.

---

**Total Pages**: 50+ (comprehensive guide)
**Code Examples**: 30+
**Diagrams**: 10+
