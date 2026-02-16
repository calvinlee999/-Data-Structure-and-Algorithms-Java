# REST API + Microservices Guide - Spring Boot 3.2 + Java 21

**Production-Ready REST API Development for FinTech Applications**

This comprehensive guide covers building modern REST APIs and microservices using Spring Boot 3.2, Java 21, Docker, and Kubernetes - designed for junior to senior developers in FinTech environments.

---

## Table of Contents

1. [REST API Fundamentals](#rest-api-fundamentals)
2. [Spring Boot 3.2 REST Controllers](#spring-boot-32-rest-controllers)
3. [Request/Response Handling](#requestresponse-handling)
4. [Validation & Error Handling](#validation--error-handling)
5. [Microservices Patterns](#microservices-patterns)
6. [Service Communication](#service-communication)
7. [Docker Containerization](#docker-containerization)
8. [Docker Compose Setup](#docker-compose-setup)
9. [Kubernetes Deployment](#kubernetes-deployment)
10. [Production Best Practices](#production-best-practices)

---

## REST API Fundamentals

### What is REST?

**REST (Representational State Transfer)** is an architectural style for building web services that use HTTP methods to perform CRUD operations.

**Key Principles:**
1. **Stateless**: Each request contains all information needed
2. **Client-Server**: Separation of concerns
3. **Cacheable**: Responses can be cached for performance
4. **Uniform Interface**: Consistent API design
5. **Layered System**: Architecture can be composed of layers

### HTTP Methods (CRUD Operations)

| HTTP Method | CRUD Operation | Description | Idempotent? | Safe? |
|-------------|----------------|-------------|-------------|-------|
| GET | Read | Retrieve resources | ✅ Yes | ✅ Yes |
| POST | Create | Create new resource | ❌ No | ❌ No |
| PUT | Update | Replace entire resource | ✅ Yes | ❌ No |
| PATCH | Update | Partial update | ❌ No | ❌ No |
| DELETE | Delete | Remove resource | ✅ Yes | ❌ No |

**Idempotent:** Multiple identical requests have the same effect as a single request.  
**Safe:** Does not modify server state.

### HTTP Status Codes

**Success Codes (2xx):**
- `200 OK` - Request succeeded
- `201 Created` - Resource created successfully
- `204 No Content` - Success, but no content to return

**Client Error Codes (4xx):**
- `400 Bad Request` - Invalid request syntax
- `401 Unauthorized` - Authentication required
- `403 Forbidden` - Authenticated but not authorized
- `404 Not Found` - Resource doesn't exist
- `422 Unprocessable Entity` - Validation failed

**Server Error Codes (5xx):**
- `500 Internal Server Error` - Server-side error
- `503 Service Unavailable` - Server temporarily unavailable

---

## Spring Boot 3.2 REST Controllers

### Basic REST Controller

```java
package com.calvin.fintech.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

/**
 * Transaction REST Controller
 * 
 * Handles HTTP requests for transaction management.
 * Think of this as a "reception desk" - it receives requests
 * and directs them to the right service to handle.
 */
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;
    
    // Constructor injection (recommended for testability)
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    /**
     * GET /api/v1/transactions
     * Retrieves all transactions
     * 
     * @return List of all transactions
     */
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * GET /api/v1/transactions/{id}
     * Retrieves a single transaction by ID
     * 
     * @param id Transaction ID (path variable)
     * @return Transaction if found, 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST /api/v1/transactions
     * Creates a new transaction
     * 
     * @param request Transaction creation request
     * @return Created transaction with 201 status
     */
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request) {
        Transaction created = transactionService.create(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(created);
    }
    
    /**
     * PUT /api/v1/transactions/{id}
     * Updates an existing transaction (full replacement)
     * 
     * @param id Transaction ID
     * @param request Updated transaction data
     * @return Updated transaction
     */
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTransactionRequest request) {
        Transaction updated = transactionService.update(id, request);
        return ResponseEntity.ok(updated);
    }
    
    /**
     * DELETE /api/v1/transactions/{id}
     * Deletes a transaction
     * 
     * @param id Transaction ID
     * @return 204 No Content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Key Annotations Explained

**@RestController:**
- Combines `@Controller` + `@ResponseBody`
- Automatically converts return values to JSON
- Think: "This class handles web requests and returns data"

**@RequestMapping("/api/v1/transactions"):**
- Base path for all endpoints in this controller
- Best practice: Include API version (v1, v2, etc.)

**@GetMapping, @PostMapping, @PutMapping, @DeleteMapping:**
- Shorthand for `@RequestMapping(method = RequestMethod.GET)`, etc.
- Maps HTTP methods to Java methods

**@PathVariable:**
- Extracts value from URL path
- Example: `/transactions/123` → `id = 123`

**@RequestBody:**
- Converts JSON request body to Java object
- Spring automatically deserializes JSON

**@Valid:**
- Triggers validation on the request object
- Works with Jakarta Bean Validation annotations

---

## Request/Response Handling

### Request DTOs (Data Transfer Objects)

```java
package com.calvin.fintech.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Request DTO for creating a new transaction
 * 
 * DTO = Data Transfer Object
 * Think of it as a "form" that must be filled out correctly
 * before we can process the request.
 */
public record CreateTransactionRequest(
    
    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be positive")
    Long customerId,
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    @Digits(integer = 17, fraction = 2, message = "Invalid amount format")
    BigDecimal amount,
    
    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters (e.g., USD)")
    String currency,
    
    @NotNull(message = "Transaction type is required")
    TransactionType type,
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description
) {}
```

### Response DTOs

```java
package com.calvin.fintech.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO for transaction data
 * 
 * This is what we send back to the client.
 * Records are perfect for DTOs because they're immutable
 * (data can't be changed after creation).
 */
public record TransactionResponse(
    Long id,
    Long customerId,
    BigDecimal amount,
    String currency,
    TransactionType type,
    TransactionStatus status,
    String description,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    /**
     * Factory method to create response from entity
     * 
     * This converts our database entity to a response DTO.
     * Think: "Translate internal data format to external format"
     */
    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
            transaction.getId(),
            transaction.getCustomerId(),
            transaction.getAmount(),
            transaction.getCurrency(),
            transaction.getType(),
            transaction.getStatus(),
            transaction.getDescription(),
            transaction.getCreatedAt(),
            transaction.getUpdatedAt()
        );
    }
}
```

### Pagination & Filtering

```java
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    
    /**
     * GET /api/v1/transactions?page=0&size=20&sort=createdAt,desc
     * 
     * Pagination prevents loading too much data at once.
     * Think of it like browsing pages in a book instead of
     * reading the whole book at once.
     */
    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<TransactionResponse> transactions = transactionService
            .findAll(pageable)
            .map(TransactionResponse::from);
        
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * GET /api/v1/transactions/search?status=COMPLETED&minAmount=100
     * 
     * Filtering allows clients to find specific transactions.
     */
    @GetMapping("/search")
    public ResponseEntity<List<TransactionResponse>> searchTransactions(
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        
        List<TransactionResponse> results = transactionService
            .search(status, minAmount, maxAmount, startDate, endDate)
            .stream()
            .map(TransactionResponse::from)
            .toList();
        
        return ResponseEntity.ok(results);
    }
}
```

---

## Validation & Error Handling

### Bean Validation Annotations

```java
package com.calvin.fintech.dto;

import jakarta.validation.constraints.*;

/**
 * Common validation annotations for request DTOs
 */
public record CustomerRequest(
    
    // String validations
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be 2-100 characters")
    String name,
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    String email,
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    String phone,
    
    // Number validations
    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Must be at least 18 years old")
    @Max(value = 150, message = "Age must be realistic")
    Integer age,
    
    @DecimalMin(value = "0.00", message = "Balance cannot be negative")
    BigDecimal balance,
    
    // Collection validations
    @NotEmpty(message = "At least one address is required")
    @Size(max = 5, message = "Maximum 5 addresses allowed")
    List<AddressDTO> addresses
) {}
```

### Global Exception Handler

```java
package com.calvin.fintech.exception;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global Exception Handler
 * 
 * This catches all errors in our application and converts them
 * to consistent, user-friendly error responses.
 * Think of it as a "customer service" layer that translates
 * technical errors into helpful messages.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Handles validation errors (400 Bad Request)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        
        // Collect all validation errors
        Map<String, String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                error -> error.getDefaultMessage() != null 
                    ? error.getDefaultMessage() 
                    : "Invalid value"
            ));
        
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Validation failed for one or more fields"
        );
        problem.setProperty("errors", errors);
        problem.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(problem);
    }
    
    /**
     * Handles resource not found (404 Not Found)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFound(
            ResourceNotFoundException ex) {
        
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            ex.getMessage()
        );
        problem.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(problem);
    }
    
    /**
     * Handles business logic errors (422 Unprocessable Entity)
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> handleBusinessException(
            BusinessException ex) {
        
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.UNPROCESSABLE_ENTITY,
            ex.getMessage()
        );
        problem.setProperty("errorCode", ex.getErrorCode());
        problem.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(problem);
    }
    
    /**
     * Handles all other unexpected errors (500 Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericError(Exception ex) {
        
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred. Please try again later."
        );
        problem.setProperty("timestamp", LocalDateTime.now());
        
        // Log the full exception for debugging
        // DO NOT expose internal error details to clients!
        
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(problem);
    }
}
```

### Custom Exceptions

```java
package com.calvin.fintech.exception;

/**
 * Thrown when a requested resource doesn't exist
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceType, Long id) {
        super(String.format("%s with ID %d not found", resourceType, id));
    }
}

/**
 * Thrown when business rules are violated
 */
public class BusinessException extends RuntimeException {
    private final String errorCode;
    
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}

// Usage example in service:
public Transaction withdraw(Long accountId, BigDecimal amount) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new ResourceNotFoundException("Account", accountId));
    
    if (account.getBalance().compareTo(amount) < 0) {
        throw new BusinessException(
            "INSUFFICIENT_FUNDS",
            "Account balance is insufficient for this withdrawal"
        );
    }
    
    // Process withdrawal...
}
```

---

## Microservices Patterns

### Microservices Architecture Overview

```
┌─────────────────────────────────────────────────────────┐
│                    API Gateway                           │
│         (Route requests to appropriate service)          │
└────────┬────────────┬─────────────┬─────────────────────┘
         │            │             │
    ┌────▼────┐  ┌───▼────┐   ┌────▼──────┐
    │Customer │  │Account │   │Transaction│
    │Service  │  │Service │   │Service    │
    └────┬────┘  └───┬────┘   └────┬──────┘
         │            │             │
    ┌────▼────────────▼─────────────▼──────┐
    │        Service Discovery               │
    │           (Eureka Server)              │
    └───────────────────────────────────────┘
```

### Service Discovery with Eureka

**1. Eureka Server (Discovery Service)**

```java
package com.calvin.fintech.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Discovery Server
 * 
 * This service keeps track of all other services (like a phone book).
 * When Service A wants to call Service B, it asks Eureka:
 * "Where is Service B?" and Eureka provides the location.
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
```

**application.yml (Eureka Server):**
```yaml
server:
  port: 8761

eureka:
  client:
    registerWithEureka: false  # Server doesn't register with itself
    fetchRegistry: false
  server:
    enableSelfPreservation: false  # Disable in development
```

**2. Microservice Registration (Client)**

```yaml
# application.yml for each microservice
spring:
  application:
    name: transaction-service  # Service name

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
    fetchRegistry: true
    registerWithEureka: true
  instance:
    preferIpAddress: true
    instanceId: ${spring.application.name}:${random.value}
```

### Service-to-Service Communication

```java
package com.calvin.fintech.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Transaction Service
 * 
 * Calls other microservices to complete transactions.
 */
@Service
public class TransactionService {
    
    private final RestClient restClient;
    
    public TransactionService(RestClient.Builder restClientBuilder) {
        // Spring Boot 3.2+ RestClient (modern, functional API)
        this.restClient = restClientBuilder
            .baseUrl("http://customer-service")  // Service name from Eureka
            .build();
    }
    
    /**
     * Verify customer exists before processing transaction
     */
    public void processTransaction(CreateTransactionRequest request) {
        // Call customer-service to verify customer
        CustomerDTO customer = restClient.get()
            .uri("/api/v1/customers/{id}", request.customerId())
            .retrieve()
            .body(CustomerDTO.class);
        
        if (customer == null) {
            throw new BusinessException(
                "CUSTOMER_NOT_FOUND",
                "Customer does not exist"
            );
        }
        
        // Process transaction...
    }
}
```

---

## Docker Containerization

### Dockerfile (Multi-stage Build)

```dockerfile
# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create runtime image
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", \
  "-XX:+UseZGC", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-jar", \
  "app.jar"]
```

### Build and Run Docker Image

```bash
# Build image
docker build -t fintech/transaction-service:1.0.0 .

# Run container
docker run -d \
  --name transaction-service \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/fintech \
  fintech/transaction-service:1.0.0

# View logs
docker logs -f transaction-service

# Stop container
docker stop transaction-service

# Remove container
docker rm transaction-service
```

---

## Docker Compose Setup

### docker-compose.yml

```yaml
version: '3.8'

services:
  # PostgreSQL Database
  postgres:
    image: postgres:16-alpine
    container_name: fintech-postgres
    environment:
      POSTGRES_DB: fintech
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: secret123
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U admin"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - fintech-network

  # Eureka Discovery Server
  discovery-server:
    build:
      context: ./discovery-server
      dockerfile: Dockerfile
    container_name: eureka-server
    ports:
      - "8761:8761"
    environment:
      SPRING_PROFILES_ACTIVE: docker
    healthcheck:
      test: ["CMD", "wget", "--spider", "http://localhost:8761/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
    networks:
      - fintech-network

  # Transaction Service
  transaction-service:
    build:
      context: ./transaction-service
      dockerfile: Dockerfile
    container_name: transaction-service
    ports:
      - "8081:8080"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/fintech
      SPRING_DATASOURCE_USERNAME: admin
      SPRING_DATASOURCE_PASSWORD: secret123
      EUREKA_CLIENT_SERVICEURL_DEFAULTZONE: http://discovery-server:8761/eureka/
      SPRING_THREADS_VIRTUAL_ENABLED: true
    depends_on:
      postgres:
        condition: service_healthy
      discovery-server:
        condition: service_healthy
    networks:
      - fintech-network

  # Customer Service
  customer-service:
    build:
      context: ./customer-service
      dockerfile: Dockerfile
    container_name: customer-service
    ports:
      - "8082:8080"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/fintech
      EUREKA_CLIENT_SERVICEURL_DEFAULTZONE: http://discovery-server:8761/eureka/
    depends_on:
      postgres:
        condition: service_healthy
      discovery-server:
        condition: service_healthy
    networks:
      - fintech-network

  # Redis Cache
  redis:
    image: redis:7-alpine
    container_name: fintech-redis
    ports:
      - "6379:6379"
    command: redis-server --appendonly yes
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 10s
      timeout: 5s
      retries: 3
    volumes:
      - redis_data:/data
    networks:
      - fintech-network

volumes:
  postgres_data:
  redis_data:

networks:
  fintech-network:
    driver: bridge
```

### Run with Docker Compose

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# View specific service logs
docker-compose logs -f transaction-service

# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v

# Rebuild services
docker-compose up -d --build
```

---

## Kubernetes Deployment

### Deployment Manifest

```yaml
# transaction-service-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: transaction-service
  namespace: fintech
  labels:
    app: transaction-service
    version: v1
spec:
  replicas: 3
  selector:
    matchLabels:
      app: transaction-service
  template:
    metadata:
      labels:
        app: transaction-service
        version: v1
    spec:
      containers:
      - name: transaction-service
        image: fintech/transaction-service:1.0.0
        imagePullPolicy: IfNotPresent
        ports:
        - containerPort: 8080
          name: http
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "k8s"
        - name: SPRING_DATASOURCE_URL
          valueFrom:
            secretKeyRef:
              name: database-secret
              key: url
        - name: SPRING_DATASOURCE_USERNAME
          valueFrom:
            secretKeyRef:
              name: database-secret
              key: username
        - name: SPRING_DATASOURCE_PASSWORD
          valueFrom:
            secretKeyRef:
              name: database-secret
              key: password
        - name: SPRING_THREADS_VIRTUAL_ENABLED
          value: "true"
        - name: JAVA_OPTS
          value: "-XX:+UseZGC -XX:MaxRAMPercentage=75.0"
        resources:
          requests:
            memory: "512Mi"
            cpu: "500m"
          limits:
            memory: "1Gi"
            cpu: "1000m"
        livenessProbe:
          httpGet:
            path: /actuator/health/liveness
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 10
          timeoutSeconds: 5
          failureThreshold: 3
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 5
          timeoutSeconds: 3
          failureThreshold: 3
        volumeMounts:
        - name: config
          mountPath: /app/config
          readOnly: true
      volumes:
      - name: config
        configMap:
          name: transaction-service-config
```

### Service Manifest

```yaml
# transaction-service-service.yaml
apiVersion: v1
kind: Service
metadata:
  name: transaction-service
  namespace: fintech
  labels:
    app: transaction-service
spec:
  type: ClusterIP
  selector:
    app: transaction-service
  ports:
  - name: http
    port: 80
    targetPort: 8080
    protocol: TCP
---
# External LoadBalancer (optional)
apiVersion: v1
kind: Service
metadata:
  name: transaction-service-external
  namespace: fintech
spec:
  type: LoadBalancer
  selector:
    app: transaction-service
  ports:
  - port: 80
    targetPort: 8080
```

### ConfigMap

```yaml
# transaction-service-configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: transaction-service-config
  namespace: fintech
data:
  application.yaml: |
    spring:
      application:
        name: transaction-service
      threads:
        virtual:
          enabled: true
      jpa:
        show-sql: false
        hibernate:
          ddl-auto: validate
    
    management:
      endpoints:
        web:
          exposure:
            include: health,info,metrics,prometheus
      health:
        probes:
          enabled: true
      metrics:
        export:
          prometheus:
            enabled: true
```

### Secret

```bash
# Create secret from literal values
kubectl create secret generic database-secret \
  --from-literal=url='jdbc:postgresql://postgres:5432/fintech' \
  --from-literal=username='admin' \
  --from-literal=password='secret123' \
  --namespace=fintech
```

### Deploy to Kubernetes

```bash
# Create namespace
kubectl create namespace fintech

# Apply configurations
kubectl apply -f transaction-service-configmap.yaml
kubectl apply -f transaction-service-deployment.yaml
kubectl apply -f transaction-service-service.yaml

# Check deployment status
kubectl get deployments -n fintech
kubectl get pods -n fintech
kubectl get services -n fintech

# View logs
kubectl logs -f deployment/transaction-service -n fintech

# Scale deployment
kubectl scale deployment transaction-service --replicas=5 -n fintech

# Delete resources
kubectl delete -f transaction-service-deployment.yaml
```

---

## Production Best Practices

### 1. API Versioning

```java
// URL versioning (recommended)
@RequestMapping("/api/v1/transactions")
@RequestMapping("/api/v2/transactions")

// Header versioning
@GetMapping(headers = "X-API-Version=1")

// Media type versioning
@GetMapping(produces = "application/vnd.fintech.v1+json")
```

### 2. Rate Limiting

```java
@Configuration
public class RateLimitConfig {
    
    @Bean
    public RateLimiter rateLimiter() {
return RateLimiter.of("api", RateLimiterConfig.custom()
            .limitForPeriod(100)  // 100 requests
            .limitRefreshPeriod(Duration.ofMinutes(1))  // per minute
            .timeoutDuration(Duration.ofSeconds(5))
            .build());
    }
}
```

### 3. Security Headers

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .headers(headers -> headers
                .contentSecurityPolicy("default-src 'self'")
                .xssProtection()
                .and()
                .frameOptions().deny()
            )
            .build();
    }
}
```

### 4. Monitoring & Observability

```properties
# application.properties
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.metrics.export.prometheus.enabled=true
management.health.probes.enabled=true
```

### 5. Graceful Shutdown

```properties
# application.properties
server.shutdown=graceful
spring.lifecycle.timeout-per-shutdown-phase=30s
```

---

## Summary

**You've learned:**
- ✅ REST API fundamentals and HTTP methods
- ✅ Spring Boot 3.2 REST controller patterns
- ✅ Request/response handling with DTOs
- ✅ Validation and error handling
- ✅ Microservices architecture patterns
- ✅ Service discovery with Eureka
- ✅ Docker containerization
- ✅ Docker Compose for local development
- ✅ Kubernetes deployment
- ✅ Production best practices

**Next Steps:**
- Practice building REST APIs
- Experiment with Docker and Docker Compose
- Deploy to Kubernetes cluster
- Implement monitoring and observability
- Add security (OAuth2, JWT)

**Resources:**
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- Docker Documentation: https://docs.docker.com/
- Kubernetes Documentation: https://kubernetes.io/docs/

---

**Happy Coding! 🚀**
