# Spring Framework Advanced Topics (Iteration 2 Enhancements)

**Building Production-Ready Spring Boot Applications**

This document extends the main SPRING_LEARNING_GUIDE.md with advanced topics based on peer review feedback.

---

## Table of Contents

1. [Development Environment Setup](#development-environment-setup)
2. [Error Handling and Problem Details](#error-handling-and-problem-details)
3. [Observability and Monitoring](#observability-and-monitoring)
4. [Database Migrations with Flyway](#database-migrations-with-flyway)
5. [Advanced Testing Strategies](#advanced-testing-strategies)
6. [Performance Optimization](#performance-optimization)
7. [Docker Containerization](#docker-containerization)
8. [Production Troubleshooting](#production-troubleshooting)
9. [Team Collaboration Best Practices](#team-collaboration-best-practices)
10. [Common Pitfalls and Anti-Patterns](#common-pitfalls-and-anti-patterns)

---

## Development Environment Setup

### Prerequisites Checklist

#### 1. Install Java 21 JDK

**macOS:**
```bash
# Using SDKMAN (recommended)
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 21.0.2-tem

# Verify installation
java -version
# Should show: openjdk version "21.0.2"
```

**Windows:**
```powershell
# Download from https://adoptium.net/
# Install Eclipse Temurin 21 LTS
# Add to PATH: C:\Program Files\Eclipse Adoptium\jdk-21.0.2-hotspot\bin

# Verify
java -version
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-21-jdk
java -version
```

---

#### 2. Install IntelliJ IDEA

**Download:** https://www.jetbrains.com/idea/download/

**Essential Plugins:**
1. Spring Boot
2. Spring Data
3. Maven Helper
4. GitToolBox
5. Rainbow Brackets
6. Key Promoter X (learn shortcuts)

**IntelliJ Settings for Spring Boot:**

```
File → Settings (Ctrl+Alt+S)

1. Build, Execution, Deployment → Build Tools → Maven
   ✅ Auto-import

2. Build, Execution, Deployment → Compiler
   ✅ Build project automatically
   ✅ Compile independent modules in parallel

3. Advanced Settings → Compiler
   ✅ Allow auto-make to start even if developed application is currently running

4. Keymap → Main menu → Code
   Set Ctrl+Space for "Code Completion"
```

---

#### 3. Install Maven

**macOS:**
```bash
brew install maven
mvn -version
```

**Windows:**
```powershell
choco install maven
mvn -version
```

**Linux:**
```bash
sudo apt install maven
mvn -version
```

---

#### 4. Install Docker Desktop

**Download:** https://www.docker.com/products/docker-desktop

**Verify:**
```bash
docker --version
docker-compose --version
```

---

#### 5. Install Database Tools

**Option A: DBeaver (Free, cross-platform)**
```bash
# Download from https://dbeaver.io/download/
```

**Option B: DataGrip (JetBrains, paid)**
```bash
# Download from https://www.jetbrains.com/datagrip/
```

---

#### 6. Install API Testing Tool

**Postman:**
```bash
# Download from https://www.postman.com/downloads/
```

**Bruno (Open Source Alternative):**
```bash
# Download from https://www.usebruno.com/
```

---

### IntelliJ Keyboard Shortcuts (Save Hours!)

| Shortcut | Action | Use Case |
|----------|--------|----------|
| Ctrl+Space | Code completion | Auto-complete code |
| Ctrl+Shift+Space | Smart type completion | Suggests variables of expected type |
| Alt+Enter | Quick fix | Fix errors, import classes |
| Ctrl+Alt+L | Reformat code | Clean up formatting |
| Ctrl+B | Go to declaration | Jump to class/method definition |
| Ctrl+Alt+B | Go to implementation | Jump to interface implementation |
| Shift+Shift | Search everywhere | Find files, classes, symbols |
| Ctrl+Shift+F | Find in files | Search across entire project |
| Ctrl+Alt+V | Extract variable | Refactor expression to variable |
| Ctrl+Alt+M | Extract method | Refactor code to method |
| Alt+Insert | Generate code | Generate getters, constructors, etc. |
| F2 | Next error | Navigate to next error |
| Ctrl+F9 | Build project | Compile changes |
| Shift+F10 | Run | Run application |
| Shift+F9 | Debug | Debug application |

---

## Error Handling and Problem Details

### Problem: Inconsistent Error Responses

**Without Error Handling:**
```json
// Different errors return different formats
{
  "timestamp": "2026-02-15T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Cannot invoke String.toLowerCase() on null object",
  "path": "/api/transactions/123"
}
```

### Solution: Global Exception Handler with RFC 7807 Problem Details

**1. Add Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

**2. Create Exception Classes:**
```java
package com.example.fintech.exception;

// Base exception
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

// Specific exceptions
public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String resource, Long id) {
        super("RESOURCE_NOT_FOUND", 
              String.format("%s with ID %d not found", resource, id));
    }
}

public class InvalidTransactionException extends BusinessException {
    public InvalidTransactionException(String message) {
        super("INVALID_TRANSACTION", message);
    }
}

public class InsufficientFundsException extends BusinessException {
    public InsufficientFundsException(BigDecimal required, BigDecimal available) {
        super("INSUFFICIENT_FUNDS",
              String.format("Required: %s, Available: %s", required, available));
    }
}
```

**3. Problem Details Response (RFC 7807):**
```java
package com.example.fintech.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProblemDetail(
    String type,           // URI reference that identifies the problem type
    String title,          // Short, human-readable summary
    int status,            // HTTP status code
    String detail,         // Human-readable explanation
    String instance,       // URI reference that identifies specific occurrence
    LocalDateTime timestamp,
    String errorCode,      // Application-specific error code
    Map<String, Object> additionalProperties  // Extra debugging info
) {
    public static ProblemDetail of(String type, String title, int status, String detail, String instance, String errorCode) {
        return new ProblemDetail(type, title, status, detail, instance, LocalDateTime.now(), errorCode, null);
    }
}
```

**4. Global Exception Handler:**
```java
package com.example.fintech.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice  // Applies to ALL @RestController classes
public class GlobalExceptionHandler {
    
    private static final String PROBLEM_BASE_URI = "https://api.fintech.com/problems/";
    
    // Handle custom business exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {
        
        ProblemDetail problem = ProblemDetail.of(
            PROBLEM_BASE_URI + "resource-not-found",
            "Resource Not Found",
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            request.getRequestURI(),
            ex.getErrorCode()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }
    
    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ProblemDetail> handleInvalidTransaction(
            InvalidTransactionException ex,
            HttpServletRequest request) {
        
        ProblemDetail problem = ProblemDetail.of(
            PROBLEM_BASE_URI + "invalid-transaction",
            "Invalid Transaction",
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            request.getRequestURI(),
            ex.getErrorCode()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
    
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ProblemDetail> handleInsufficientFunds(
            InsufficientFundsException ex,
            HttpServletRequest request) {
        
        ProblemDetail problem = ProblemDetail.of(
            PROBLEM_BASE_URI + "insufficient-funds",
            "Insufficient Funds",
            HttpStatus.PAYMENT_REQUIRED.value(),
            ex.getMessage(),
            request.getRequestURI(),
            ex.getErrorCode()
        );
        
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(problem);
    }
    
    // Handle validation errors (from @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ProblemDetail problem = new ProblemDetail(
            PROBLEM_BASE_URI + "validation-failed",
            "Validation Failed",
            HttpStatus.BAD_REQUEST.value(),
            "Request validation failed. See 'errors' for details.",
            request.getRequestURI(),
            LocalDateTime.now(),
            "VALIDATION_FAILED",
            errors
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
    }
    
    // Handle general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneralException(
            Exception ex,
            HttpServletRequest request) {
        
        // Log exception for debugging (don't expose stack trace to client)
        ex.printStackTrace();
        
        ProblemDetail problem = ProblemDetail.of(
            PROBLEM_BASE_URI + "internal-error",
            "Internal Server Error",
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred. Please contact support.",
            request.getRequestURI(),
            "INTERNAL_ERROR"
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }
}
```

**5. Using Exceptions in Service:**
```java
@Service
@Transactional
public class TransactionService {
    
    private final TransactionRepository repository;
    
    public Transaction findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));
    }
    
    public Transaction createTransaction(CreateTransactionRequest request) {
        // Validation
        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Amount must be positive");
        }
        
        // Business logic
        BigDecimal balance = accountService.getBalance(request.customerId());
        if (balance.compareTo(request.amount()) < 0) {
            throw new InsufficientFundsException(request.amount(), balance);
        }
        
        Transaction transaction = new Transaction();
        transaction.setAmount(request.amount());
        transaction.setCustomerId(request.customerId());
        return repository.save(transaction);
    }
}
```

**6. Error Response Examples:**

```http
HTTP/1.1 404 Not Found
Content-Type: application/problem+json

{
  "type": "https://api.fintech.com/problems/resource-not-found",
  "title": "Resource Not Found",
  "status": 404,
  "detail": "Transaction with ID 999 not found",
  "instance": "/api/transactions/999",
  "timestamp": "2026-02-15T10:30:00",
  "errorCode": "RESOURCE_NOT_FOUND"
}
```

```http
HTTP/1.1 402 Payment Required
Content-Type: application/problem+json

{
  "type": "https://api.fintech.com/problems/insufficient-funds",
  "title": "Insufficient Funds",
  "status": 402,
  "detail": "Required: 500.00, Available: 250.00",
  "instance": "/api/transactions",
  "timestamp": "2026-02-15T10:30:00",
  "errorCode": "INSUFFICIENT_FUNDS"
}
```

```http
HTTP/1.1 400 Bad Request
Content-Type: application/problem+json

{
  "type": "https://api.fintech.com/problems/validation-failed",
  "title": "Validation Failed",
  "status": 400,
  "detail": "Request validation failed. See 'errors' for details.",
  "instance": "/api/transactions",
  "timestamp": "2026-02-15T10:30:00",
  "errorCode": "VALIDATION_FAILED",
  "additionalProperties": {
    "amount": "must be greater than 0",
    "currency": "must not be blank"
  }
}
```

---

## Observability and Monitoring

### Spring Boot Actuator

**1. Add Dependencies:**
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
</dependencies>
```

**2. Configure Actuator:**
```properties
# application.properties

# Enable all actuator endpoints
management.endpoints.web.exposure.include=*

# Show detailed health information
management.endpoint.health.show-details=always
management.endpoint.health.show-components=always

# Custom application info
info.app.name=FinTech Transaction API
info.app.version=@project.version@
info.app.description=Modern Spring Boot 3.2 + Java 21 application

# Enable Prometheus metrics
management.metrics.export.prometheus.enabled=true

# Customize base path
management.endpoints.web.base-path=/actuator

# Enable specific endpoints
management.endpoint.health.enabled=true
management.endpoint.info.enabled=true
management.endpoint.metrics.enabled=true
management.endpoint.prometheus.enabled=true
```

**3. Custom Health Indicators:**
```java
package com.example.fintech.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    
    private final TransactionRepository repository;
    
    public DatabaseHealthIndicator(TransactionRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Health health() {
        try {
            long count = repository.count();
            return Health.up()
                .withDetail("database", "PostgreSQL")
                .withDetail("transactionCount", count)
                .withDetail("status", "Connected")
                .build();
        } catch (Exception ex) {
            return Health.down()
                .withDetail("database", "PostgreSQL")
                .withDetail("error", ex.getMessage())
                .build();
        }
    }
}

@Component
public class ExternalApiHealthIndicator implements HealthIndicator {
    
    private final RestTemplate restTemplate;
    
    @Override
    public Health health() {
        try {
            String response = restTemplate.getForObject(
                "https://api.payment-gateway.com/health", 
                String.class
            );
            return Health.up()
                .withDetail("paymentGateway", "Available")
                .build();
        } catch (Exception ex) {
            return Health.down()
                .withDetail("paymentGateway", "Unavailable")
                .withDetail("error", ex.getMessage())
                .build();
        }
    }
}
```

**4. Custom Metrics with Micrometer:**
```java
package com.example.fintech.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceWithMetrics {
    
    private final TransactionRepository repository;
    private final MeterRegistry meterRegistry;
    
    // Business metrics
    private final Counter transactionCreatedCounter;
    private final Counter transactionApprovedCounter;
    private final Counter transactionRejectedCounter;
    private final Timer transactionProcessingTimer;
    
    public TransactionServiceWithMetrics(
            TransactionRepository repository,
            MeterRegistry meterRegistry) {
        this.repository = repository;
        this.meterRegistry = meterRegistry;
        
        // Initialize counters
        this.transactionCreatedCounter = Counter.builder("transaction.created")
            .description("Total transactions created")
            .tag("app", "fintech-api")
            .register(meterRegistry);
        
        this.transactionApprovedCounter = Counter.builder("transaction.approved")
            .description("Total transactions approved")
            .register(meterRegistry);
        
        this.transactionRejectedCounter = Counter.builder("transaction.rejected")
            .description("Total transactions rejected")
            .register(meterRegistry);
        
        this.transactionProcessingTimer = Timer.builder("transaction.processing.time")
            .description("Time taken to process transaction")
            .register(meterRegistry);
    }
    
    public Transaction createTransaction(Transaction transaction) {
        return transactionProcessingTimer.record(() -> {
            Transaction saved = repository.save(transaction);
            transactionCreatedCounter.increment();
            
            // Tag by transaction type
            meterRegistry.counter("transaction.created.by.type",
                "type", transaction.getType().toString()
            ).increment();
            
            return saved;
        });
    }
    
    public void approveTransaction(Long id) {
        Transaction transaction = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));
        
        transaction.approve();
        repository.save(transaction);
        
        transactionApprovedCounter.increment();
        
        // Record high-value approvals
        if (transaction.isHighValue()) {
            meterRegistry.counter("transaction.high.value.approved").increment();
        }
    }
    
    // Gauge example: Current balance
    public void registerBalanceGauge(Long customerId) {
        meterRegistry.gauge("customer.balance",
            List.of(Tag.of("customerId", String.valueOf(customerId))),
            customerId,
            id -> accountService.getBalance(id).doubleValue()
        );
    }
}
```

**5. Accessing Actuator Endpoints:**

```bash
# Health check
curl http://localhost:8080/actuator/health

# Detailed health
curl http://localhost:8080/actuator/health | jq

# Metrics
curl http://localhost:8080/actuator/metrics

# Specific metric
curl http://localhost:8080/actuator/metrics/transaction.created

# Prometheus format (for Grafana)
curl http://localhost:8080/actuator/prometheus

# Application info
curl http://localhost:8080/actuator/info

# Environment variables
curl http://localhost:8080/actuator/env

# All beans
curl http://localhost:8080/actuator/beans

# HTTP trace
curl http://localhost:8080/actuator/httptrace
```

**6. Securing Actuator Endpoints:**
```java
@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                .requestMatchers("/actuator/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
```

---

## Database Migrations with Flyway

**Why Flyway?**
- ✅ Version control for database schema
- ✅ Automated migrations in CI/CD
- ✅ Rollback capabilities
- ✅ Team-wide schema consistency

**1. Add Dependency:**
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

**2. Configure Flyway:**
```properties
# application.properties

# Disable Hibernate auto-DDL (Flyway takes over)
spring.jpa.hibernate.ddl-auto=validate

# Flyway configuration
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-version=0
```

**3. Create Migration Scripts:**

**File naming:** `V{version}__{description}.sql`

```
src/main/resources/db/migration/
├── V1__create_transactions_table.sql
├── V2__add_transaction_status_column.sql
├── V3__create_customers_table.sql
└── V4__add_foreign_key_to_transactions.sql
```

**V1__create_transactions_table.sql:**
```sql
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    description VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT DEFAULT 0,
    CONSTRAINT positive_amount CHECK (amount > 0)
);

CREATE INDEX idx_transactions_customer_id ON transactions(customer_id);
CREATE INDEX idx_transactions_status ON transactions(status);
CREATE INDEX idx_transactions_created_at ON transactions(created_at);
```

**V2__add_transaction_status_column.sql:**
```sql
-- Add new column
ALTER TABLE transactions 
ADD COLUMN payment_method VARCHAR(50);

-- Backfill existing data
UPDATE transactions 
SET payment_method = 'UNKNOWN' 
WHERE payment_method IS NULL;
```

**V3__create_customers_table.sql:**
```sql
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO customers (name, email, balance) VALUES
('John Doe', 'john@example.com', 1000.00),
('Jane Smith', 'jane@example.com', 5000.00);
```

**V4__add_foreign_key_to_transactions.sql:**
```sql
ALTER TABLE transactions
ADD CONSTRAINT fk_customer
FOREIGN KEY (customer_id) REFERENCES customers(id);
```

**4. Verify Migrations:**
```bash
# Flyway creates a schema_version table
SELECT * FROM flyway_schema_history ORDER BY installed_rank;
```

**5. Rollback (Flyway Teams/Enterprise only):**
```sql
-- Create undo migration: U4__add_foreign_key_to_transactions.sql
ALTER TABLE transactions
DROP CONSTRAINT fk_customer;
```

---

## Advanced Testing Strategies

### Testcontainers for Real Database Tests

**1. Add Dependencies:**
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>testcontainers</artifactId>
        <version>1.19.3</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>postgresql</artifactId>
        <version>1.19.3</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>1.19.3</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

**2. Integration Test with Testcontainers:**
```java
package com.example.fintech.repository;

import com.example.fintech.domain.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest  // Only loads JPA components
@Testcontainers  // Enables Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // Use real DB
class TransactionRepositoryIntegrationTest {
    
    @Container  // Automatically starts/stops PostgreSQL container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
    
    @DynamicPropertySource  // Override datasource properties
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    
    @Autowired
    private TransactionRepository repository;
    
    @Test
    void shouldSaveAndFindTransaction() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setCustomerId(1L);
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setCurrency("USD");
        
        // Act
        Transaction saved = repository.save(transaction);
        Transaction found = repository.findById(saved.getId()).orElseThrow();
        
        // Assert
        assertThat(found.getId()).isNotNull();
        assertThat(found.getCustomerId()).isEqualTo(1L);
        assertThat(found.getAmount()).isEqualByComparingTo("100.00");
    }
    
    @Test
    void shouldFindByCustomerId() {
        // Arrange
        Transaction t1 = new Transaction();
        t1.setCustomerId(123L);
        t1.setAmount(new BigDecimal("100.00"));
        
        Transaction t2 = new Transaction();
        t2.setCustomerId(123L);
        t2.setAmount(new BigDecimal("200.00"));
        
        repository.save(t1);
        repository.save(t2);
        
        // Act
        List<Transaction> transactions = repository.findByCustomerId(123L);
        
        // Assert
        assertThat(transactions).hasSize(2);
        assertThat(transactions).extracting("customerId").containsOnly(123L);
    }
}
```

**3. Test Slices (Faster Tests):**

**@WebMvcTest (Test Controllers Only):**
```java
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean  // Mock the service
    private TransactionService service;
    
    @Test
    void shouldReturnTransactionById() throws Exception {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(new BigDecimal("100.00"));
        
        when(service.findById(1L)).thenReturn(Optional.of(transaction));
        
        // Act & Assert
        mockMvc.perform(get("/api/transactions/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.amount").value(100.00));
    }
}
```

**@DataJpaTest (Test Repository Only):**
```java
@DataJpaTest
class TransactionRepositoryTest {
    
    @Autowired
    private TransactionRepository repository;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    void shouldFindHighValueTransactions() {
        // Use TestEntityManager for setup
        Transaction t1 = new Transaction();
        t1.setAmount(new BigDecimal("15000.00"));
        entityManager.persist(t1);
        
        Transaction t2 = new Transaction();
        t2.setAmount(new BigDecimal("500.00"));
        entityManager.persist(t2);
        
        entityManager.flush();
        
        // Act
        List<Transaction> highValue = repository.findByAmountGreaterThan(new BigDecimal("10000"));
        
        // Assert
        assertThat(highValue).hasSize(1);
        assertThat(highValue.get(0).getAmount()).isEqualByComparingTo("15000.00");
    }
}
```

---

## Performance Optimization

### N+1 Query Problem (Critical!)

**❌ BAD: N+1 Queries (1 query for customers + N queries for transactions)**
```java
@Entity
public class Customer {
    @Id
    private Long id;
    
    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;
}

// This code generates N+1 queries!
List<Customer> customers = customerRepository.findAll();  // 1 query
for (Customer customer : customers) {
    System.out.println(customer.getTransactions().size());  // N queries!
}
```

**✅ GOOD: Use JOIN FETCH**
```java
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.transactions")
    List<Customer> findAllWithTransactions();
}

// Now only 1 query!
List<Customer> customers = customerRepository.findAllWithTransactions();
for (Customer customer : customers) {
    System.out.println(customer.getTransactions().size());  // No additional queries!
}
```

**✅ BETTER: Use @EntityGraph**
```java
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    @EntityGraph(attributePaths = {"transactions"})
    @Query("SELECT c FROM Customer c")
    List<Customer> findAllWithTransactions();
}
```

### Connection Pool Tuning (HikariCP)

```properties
# application.properties

# Connection pool size (formula: cores * 2 + spindles)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5

# Connection timeout
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000

# Performance tuning
spring.datasource.hikari.leak-detection-threshold=60000
spring.datasource.hikari.pool-name=FinTechHikariPool

# Connection test query
spring.datasource.hikari.connection-test-query=SELECT 1
```

### JPA Caching

```properties
# Second-level cache with Ehcache
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.jcache.JCacheRegionFactory

# Query cache
spring.jpa.properties.hibernate.cache.use_query_cache=true
```

```java
@Entity
@Cacheable  // Enable second-level cache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transaction {
    // ...
}
```

---

## Docker Containerization

**1. Create Dockerfile:**
```dockerfile
# Multi-stage build for smaller image

# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/fintech-api-*.jar app.jar

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Expose port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**2. Build and Run:**
```bash
# Build image
docker build -t fintech-api:latest .

# Run container
docker run -p 8080:8080 fintech-api:latest

# Run with environment variables
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/fintech \
  fintech-api:latest
```

**3. Docker Compose:**
```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/fintech
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=secret
    depends_on:
      - db
      
  db:
    image: postgres:16-alpine
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_DB=fintech
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=secret
    volumes:
      - postgres-data:/var/lib/postgresql/data

volumes:
  postgres-data:
```

**4. Run with Docker Compose:**
```bash
docker-compose up -d
docker-compose logs -f
docker-compose down
```

---

## Production Troubleshooting

### Common Error Scenarios

**1. OutOfMemoryError**
```bash
# Check heap usage
curl http://localhost:8080/actuator/metrics/jvm.memory.used

# Increase heap size
java -Xmx2g -Xms1g -jar app.jar
```

**2. Slow Queries**
```properties
# Enable slow query logging
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true

# Log slow queries (over 1 second)
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

**3. LazyInitializationException**
```java
// ❌ BAD: Accessing lazy collection outside transaction
@GetMapping("/customers/{id}")
public Customer getCustomer(@PathVariable Long id) {
    Customer customer = customerRepository.findById(id).orElseThrow();
    customer.getTransactions().size();  // LazyInitializationException!
    return customer;
}

// ✅ GOOD: Use @Transactional or JOIN FETCH
@GetMapping("/customers/{id}")
@Transactional(readOnly = true)
public Customer getCustomer(@PathVariable Long id) {
    Customer customer = customerRepository.findById(id).orElseThrow();
    customer.getTransactions().size();  // Works!
    return customer;
}
```

---

## Team Collaboration Best Practices

### Git Commit Message Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation only
- `style:` Formatting, missing semicolons, etc.
- `refactor:` Code restructuring
- `test:` Adding tests
- `chore:` Build process, dependencies

**Example:**
```
feat(transaction): Add bulk approval endpoint

- Implement POST /api/transactions/batch-approve
- Add validation for batch size (max 100)
- Return detailed success/failure report
- Add integration tests

Closes #JIRA-123
```

### Code Review Checklist

**Before Submitting PR:**
- [ ] All tests passing
- [ ] No compiler warnings
- [ ] Code formatted (Ctrl+Alt+L)
- [ ] Added/updated tests for changes
- [ ] Updated documentation
- [ ] No sensitive data committed
- [ ] No commented-out code

**Reviewing PR:**
- [ ] Constructor injection used (not field injection)
- [ ] @Transactional on appropriate methods
- [ ] Proper error handling
- [ ] Validation with @Valid
- [ ] No N+1 query problems
- [ ] Meaningful variable names
- [ ] No magic numbers

---

## Common Pitfalls and Anti-Patterns

| Anti-Pattern | Why Bad | Solution |
|--------------|---------|----------|
| Field injection | Hard to test, mutable | Constructor injection |
| Missing @Transactional | LazyInitializationException | Add @Transactional |
| Exposing entities in REST | Tight coupling | Use DTOs (Records) |
| N+1 queries | Performance disaster | @EntityGraph, JOIN FETCH |
| Ignoring Optional | NullPointerException | Use .orElseThrow() |
| Poor exception handling | Inconsistent errors | @ControllerAdvice |
| No database migrations | Schema chaos | Use Flyway |
| Hardcoded values | Can't change config | Use properties |
| No integration tests | False confidence | Use Testcontainers |
| Large transactions | Database locks | Keep transactions small |

---

## Next Steps

With these enhancements, you now have:
- ✅ Production-grade error handling
- ✅ Comprehensive monitoring with Actuator
- ✅ Database version control with Flyway
- ✅ Real integration tests with Testcontainers
- ✅ Performance optimization patterns
- ✅ Docker containerization
- ✅ Troubleshooting guide
- ✅ Team collaboration best practices

**Continue to:**
- Microservices patterns
- Event-driven architecture
- Advanced security (OAuth2, JWT)
- API documentation (OpenAPI/Swagger)
- Cloud deployment (AWS, Azure, GCP)

**Happy Coding! 🚀**
