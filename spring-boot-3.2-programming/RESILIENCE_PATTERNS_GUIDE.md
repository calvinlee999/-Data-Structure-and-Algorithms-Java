# Microservices Resilience & Production Patterns
# Circuit Breakers, Retries, Fallbacks, Timeout, Bulkhead

**Spring Boot 3.2 + Resilience4j - Production-Ready Patterns for FinTech**

This guide covers how to make your microservices resilient to failures using industry-standard patterns.

---

## Table of Contents

1. [Why Resilience Matters](#why-resilience-matters)
2. [Circuit Breaker Pattern](#circuit-breaker-pattern)
3. [Retry Pattern](#retry-pattern)
4. [Timeout Pattern](#timeout-pattern)
5. [Rate Limiter Pattern](#rate-limiter-pattern)
6. [Bulkhead Pattern](#bulkhead-pattern)
7. [Combining Patterns](#combining-patterns)
8. [Monitoring & Metrics](#monitoring--metrics)

---

## Why Resilience Matters

In a microservices architecture, services depend on each other. When one service fails, it can cause a **cascading failure** that brings down the entire system.

**Common Failure Scenarios:**
- Service is slow or unresponsive (latency)
- Service is completely down (unavailable)
- Network issues between services
- Database connection pool exhausted
- Third-party API rate limits exceeded

**Resilience patterns protect against these failures.**

---

## Circuit Breaker Pattern

### What is a Circuit Breaker?

Think of it like an **electrical circuit breaker** in your home:
- When too many failures occur, the circuit "opens" (breaks)
- Further requests **fast-fail** without calling the service
- After some time, the circuit "half-opens" to test if service recovered
- If successful, circuit "closes" and normal operation resumes

### States

```
┌─────────────────────────────────────────────────────────┐
│                    Circuit Breaker States                │
└─────────────────────────────────────────────────────────┘

        Requests succeed normally
              ↓
        ┌── CLOSED ──┐
        │  (Normal)   │
        └─────────────┘
              │
              │ Failure threshold exceeded
              ↓
        ┌────OPEN────┐
        │   (Trip)    │  ← Requests fast-fail immediately
        └─────────────┘
              │
              │ Wait duration passes
              ↓
      ┌──HALF_OPEN───┐
      │ (Testing)     │  ← Send test request
      └───────────────┘
         │          │
 Success │          │ Failure
         ↓          ↓
     CLOSED       OPEN
```

### Dependencies

```xml
<!-- pom.xml -->
<dependencies>
    <!-- Resilience4j for Spring Boot 3 -->
    <dependency>
        <groupId>io.github.resilience4j</groupId>
        <artifactId>resilience4j-spring-boot3</artifactId>
        <version>2.1.0</version>
    </dependency>
    
    <!-- AOP support -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
    
    <!-- Actuator for metrics -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

### Configuration

```yaml
# application.yml
resilience4j:
  circuitbreaker:
    instances:
      customerService:
        # How many calls we track (sliding window)
        slidingWindowSize: 10
        
        # Minimum calls needed before we can calculate failure rate
        minimumNumberOfCalls: 5
        
        # When to open circuit (50% failures)
        failureRateThreshold: 50
        
        # How long to wait before trying again (half-open)
        waitDurationInOpenState: 10s
        
        # How many test calls in half-open state
        permittedNumberOfCallsInHalfOpenState: 3
        
        # Automatically transition from open to half-open
        automaticTransitionFromOpenToHalfOpenEnabled: true
        
        # Which exceptions count as failures
        recordExceptions:
          - org.springframework.web.client.HttpServerErrorException
          - java.util.concurrent.TimeoutException
          - java.io.IOException
```

### Implementation

```java
package com.calvin.fintech.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Transaction Service with Circuit Breaker
 * 
 * When calling other services, we protect against failures
 * using circuit breaker pattern.
 */
@Service
public class TransactionService {
    
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final RestClient restClient;
    
    public TransactionService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
            .baseUrl("http://customer-service")
            .build();
    }
    
    /**
     * Create transaction with circuit breaker protection
     * 
     * @CircuitBreaker annotation provides automatic protection
     * - name: matches configuration in application.yml
     * - fallbackMethod: called when circuit is open or service fails
     */
    @CircuitBreaker(name = "customerService", fallbackMethod = "createTransactionFallback")
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        log.info("Creating transaction for customer: {}", request.customerId());
        
        // Call customer service (this call is protected)
        CustomerDTO customer = restClient.get()
            .uri("/api/v1/customers/{id}", request.customerId())
            .retrieve()
            .body(CustomerDTO.class);
        
        if (customer == null) {
            throw new BusinessException("CUSTOMER_NOT_FOUND", "Customer not found");
        }
        
        // Verify customer is active
        if (!customer.isActive()) {
            throw new BusinessException("CUSTOMER_INACTIVE", "Customer account is inactive");
        }
        
        // Create transaction (business logic here)
        Transaction transaction = new Transaction();
        transaction.setCustomerId(request.customerId());
        transaction.setAmount(request.amount());
        transaction.setCurrency(request.currency());
        transaction.setType(request.type());
        transaction.setStatus(TransactionStatus.PENDING);
        
        // Save to database
        transaction = transactionRepository.save(transaction);
        
        return TransactionResponse.from(transaction);
    }
    
    /**
     * Fallback method called when circuit is open or service fails
     * 
     * This method MUST have:
     * - Same return type as original method
     * - Same parameters PLUS exception parameter
     * 
     * Think of this as a "backup plan" when primary service is down.
     */
    private TransactionResponse createTransactionFallback(
            CreateTransactionRequest request,
            Exception exception) {
        
        log.error("Circuit breaker activated for customer service. Using fallback.", exception);
        
        // Option 1: Return cached data
        // CustomerDTO cached = customerCache.get(request.customerId());
        
        // Option 2: Return default/degraded response
        // For financial transactions, we typically DON'T create transaction
        // when we can't verify customer
        
        throw new ServiceUnavailableException(
            "Customer service is temporarily unavailable. Please try again later.",
            exception
        );
        
        // Alternative: Queue for later processing
        // transactionQueue.add(request);
        // return pendingResponse("Transaction queued for processing");
    }
}
```

### Testing Circuit Breaker

```java
package com.calvin.fintech.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClient;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Circuit Breaker Integration Test
 * 
 * Verifies that circuit breaker opens after threshold failures.
 */
@SpringBootTest
class CircuitBreakerTest {
    
    @Autowired
    private TransactionService transactionService;
    
    @MockBean
    private RestClient restClient;
    
    @Test
    void shouldOpenCircuitAfterFailureThreshold() {
        // Simulate service failures
        when(restClient.get()).thenThrow(new RuntimeException("Service down"));
        
        // Call service multiple times until circuit opens
        for (int i = 0; i < 10; i++) {
            try {
                transactionService.createTransaction(createRequest());
            } catch (Exception e) {
                // Expected failures
            }
        }
        
        // Circuit should now be OPEN
        // Next call should fail immediately (fast-fail)
        long startTime = System.currentTimeMillis();
        
        assertThatThrownBy(() -> transactionService.createTransaction(createRequest()))
            .isInstanceOf(ServiceUnavailableException.class);
        
        long duration = System.currentTimeMillis() - startTime;
        
        // Fast-fail should be very quick (< 100ms)
        assertThat(duration).isLessThan(100);
    }
}
```

---

## Retry Pattern

### What is Retry?

Automatically retry failed requests after a delay. Useful for **transient failures** (temporary network issues, service temporarily busy).

### Configuration

```yaml
resilience4j:
  retry:
    instances:
      paymentGateway:
        # Maximum retry attempts
        maxAttempts: 3
        
        # Wait between retries (exponential backoff)
        waitDuration: 1s
        
        # Enable exponential backoff (1s, 2s, 4s)
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
        
        # Which exceptions trigger retry
        retryExceptions:
          - java.net.SocketTimeoutException
          - org.springframework.web.client.ResourceAccessException
        
        # Which exceptions should NOT retry
        ignoreExceptions:
          - com.calvin.fintech.exception.BusinessException
```

### Implementation

```java
@Service
public class PaymentService {
    
    private final RestClient paymentGatewayClient;
    
    /**
     * Process payment with automatic retries
     * 
     * Retries are useful for payment gateways that might have
     * temporary network issues.
     */
    @Retry(name = "paymentGateway", fallbackMethod = "processPaymentFallback")
    @CircuitBreaker(name = "paymentGateway")
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Attempting payment processing: {}", request.transactionId());
        
        // Call external payment gateway
        PaymentResponse response = paymentGatewayClient.post()
            .uri("/api/v1/payments")
            .body(request)
            .retrieve()
            .body(PaymentResponse.class);
        
        return response;
    }
    
    /**
     * Fallback when all retries fail
     */
    private PaymentResponse processPaymentFallback(
            PaymentRequest request,
            Exception exception) {
        
        log.error("Payment processing failed after retries", exception);
        
        // In FinTech, failed payments should be logged for manual review
        paymentAuditService.logFailedPayment(request, exception);
        
        throw new PaymentProcessingException(
            "Payment gateway unavailable. Payment has been logged for manual processing.",
            exception
        );
    }
}
```

---

## Timeout Pattern

### What is Timeout?

Prevent requests from waiting indefinitely. Set maximum time to wait for response.

### Configuration

```yaml
resilience4j:
  timelimiter:
    instances:
      slowService:
        timeoutDuration: 5s  # Max 5 seconds per call
        cancelRunningFuture: true  # Cancel if timeout
```

### Implementation

```java
@Service
public class ReportService {
    
    /**
     * Generate report with timeout protection
     * 
     * If report generation takes > 5 seconds, timeout occurs.
     */
    @TimeLimiter(name = "slowService", fallbackMethod = "generateReportFallback")
    public CompletableFuture<ReportDTO> generateReportAsync(Long customerId) {
        return CompletableFuture.supplyAsync(() -> {
            // Long-running report generation
            return reportGenerator.generate(customerId);
        });
    }
    
    private CompletableFuture<ReportDTO> generateReportFallback(
            Long customerId,
            Exception exception) {
        
        log.warn("Report generation timed out for customer: {}", customerId);
        
        // Queue for background processing
        reportQueueService.queueReport(customerId);
        
        return CompletableFuture.completedFuture(
            ReportDTO.pending("Report is being generated. Check back later.")
        );
    }
}
```

---

## Rate Limiter Pattern

### What is Rate Limiter?

Control how many requests can be made in a time period. Prevents overloading services.

### Configuration

```yaml
resilience4j:
  ratelimiter:
    instances:
      apiRateLimit:
        # Max 100 requests per minute
        limitForPeriod: 100
        limitRefreshPeriod: 1m
        
        # Max time to wait for permission
        timeoutDuration: 5s
```

### Implementation

```java
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    
    /**
     * Rate-limited endpoint
     * 
     * Protects against excessive requests from single client.
     */
    @RateLimiter(name = "apiRateLimit")
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        // This endpoint can only be called 100 times per minute
        List<TransactionResponse> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }
}
```

---

## Bulkhead Pattern

### What is Bulkhead?

Isolate resources to prevent one failing component from taking down entire system.

Think of a ship with compartments (bulkheads) - if one compartment floods, others stay dry.

### Configuration

```yaml
resilience4j:
  bulkhead:
    instances:
      accountService:
        # Maximum concurrent calls
        maxConcurrentCalls: 10
        
        # Maximum wait time for permission
        maxWaitDuration: 500ms
```

### Implementation

```java
@Service
public class AccountService {
    
    /**
     * Get account with bulkhead protection
     * 
     * Only 10 concurrent calls allowed.
     * If 11th request comes, it waits up to 500ms.
     * If still no slot available, request fails fast.
     */
    @Bulkhead(name = "accountService", fallbackMethod = "getAccountFallback")
    public AccountDTO getAccount(Long accountId) {
        return accountRepository.findById(accountId)
            .map(AccountDTO::from)
            .orElseThrow(() -> new ResourceNotFoundException("Account", accountId));
    }
    
    private AccountDTO getAccountFallback(Long accountId, BulkheadFullException exception) {
        log.warn("Bulkhead full for account service");
        throw new ServiceOverloadedException("Service is busy. Please try again.");
    }
}
```

---

## Combining Patterns

In production, combine multiple patterns for robust resilience:

```java
@Service
public class CustomerService {
    
    /**
     * Combined resilience patterns
     * 
     * Order matters! Applied from bottom to top:
     * 1. Retry (innermost) - retry transient failures
     * 2. CircuitBreaker - prevent cascading failures
     * 3. RateLimiter - control request rate
     * 4. Bulkhead (outermost) - isolate resources
     */
    @Bulkhead(name = "customerService")
    @RateLimiter(name = "customerService")
    @CircuitBreaker(name = "customerService", fallbackMethod = "getCustomerFallback")
    @Retry(name = "customerService")
    @TimeLimiter(name = "customerService")
    public CompletableFuture<CustomerDTO> getCustomerAsync(Long customerId) {
        return CompletableFuture.supplyAsync(() -> {
            // Call customer service
            return restClient.get()
                .uri("/api/v1/customers/{id}", customerId)
                .retrieve()
                .body(CustomerDTO.class);
        });
    }
    
    private CompletableFuture<CustomerDTO> getCustomerFallback(
            Long customerId,
            Exception exception) {
        
        // Try cache first
        CustomerDTO cached = customerCache.get(customerId);
        if (cached != null) {
            return CompletableFuture.completedFuture(cached);
        }
        
        // No cache available
        throw new ServiceUnavailableException(
            "Customer service unavailable and no cached data found",
            exception
        );
    }
}
```

---

## Monitoring & Metrics

### Actuator Endpoints

```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,metrics,circuitbreakers,ratelimiters
  health:
    circuitbreakers:
      enabled: true
  metrics:
    export:
      prometheus:
        enabled: true
```

### Check Circuit Breaker Status

```bash
# View all circuit breakers
curl http://localhost:8080/actuator/circuitbreakers

# View specific circuit breaker events
curl http://localhost:8080/actuator/circuitbreakerevents/customerService
```

### Prometheus Metrics

Resilience4j automatically exports metrics:

```promql
# Circuit breaker state (0=CLOSED, 1=OPEN, 2=HALF_OPEN)
resilience4j_circuitbreaker_state{name="customerService"}

# Failure rate
resilience4j_circuitbreaker_failure_rate{name="customerService"}

# Retry attempts
resilience4j_retry_calls{kind="successful|failed_with_retry|failed_without_retry"}

# Rate limiter available permissions
resilience4j_ratelimiter_available_permissions{name="apiRateLimit"}
```

---

## Best Practices

1. **Always provide fallback methods** - never leave users with generic error
2. **Monitor circuit breaker states** - alert when circuits open
3. **Use appropriate timeouts** - don't wait forever, but don't be too aggressive
4. **Implement idempotency** - retries should be safe (especially for payments)
5. **Cache when possible** - use fallback cache data when service unavailable
6. **Log circuit breaker events** - understand failure patterns
7. **Test resilience** - simulate failures in testing
8. **Different policies per service** - critical services get more retries

---

## Summary

**Resilience Patterns:**
- ✅ **Circuit Breaker**: Prevent cascading failures
- ✅ **Retry**: Handle transient failures
- ✅ **Timeout**: Don't wait forever
- ✅ **Rate Limiter**: Control request rate
- ✅ **Bulkhead**: Isolate resources

**Key Takeaway:** In microservices, assume failures WILL happen. Design for resilience from day one.

---

**Next:** [API Gateway Implementation](./API_GATEWAY_GUIDE.md)
