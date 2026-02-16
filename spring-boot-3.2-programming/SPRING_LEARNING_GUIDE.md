# Spring Framework Learning Guide for Junior Developers

**Using Java 21 LTS + Spring Boot 3.2 + Spring Framework 6.0**

*A FinTech Principal Software Engineer's Guide to Modern Spring Development*

---

## 🎯 Executive Summary

Welcome to the modern Spring ecosystem! This guide will take you from zero to production-ready Spring developer using the latest technologies:

- **Java 21 LTS** (September 2023) - Latest long-term support version with Virtual Threads
- **Spring Boot 3.2** (November 2023) - Modern auto-configuration and production-ready features
- **Spring Framework 6.0** - Full Java 21 support with native compilation
- **Spring Data JPA** - Zero-boilerplate database access
- **Hibernate 6.4+** - Latest ORM with performance improvements
- **Functional Programming** - Lambda expressions, Stream API, and modern Java patterns

### Why This Stack?

| Older Approach | Modern Java 21 + Spring Boot 3.2 | Improvement |
|----------------|-----------------------------------|-------------|
| Manual configuration (XML, JavaConfig) | Auto-configuration with sensible defaults | 80% less boilerplate |
| For-loops and imperative code | Functional streams and lambda expressions | 60% more readable |
| Thread pools with blocking I/O | Virtual Threads (Project Loom) | 10x better concurrency |
| Verbose POJOs with getters/setters | Records (zero-boilerplate DTOs) | 90% less code |
| Manual SQL writing | Spring Data JPA derived queries | 70% faster development |
| Complex null handling | Optional and pattern matching | 50% fewer NullPointerExceptions |

---

## 📚 Table of Contents

1. [What is Spring?](#what-is-spring)
2. [Technology Stack Overview](#technology-stack-overview)
3. [Five Key Areas of Spring Development](#five-key-areas)
   - [Core Spring Concepts](#1-core-spring-concepts)
   - [Rapid Development with Spring Boot](#2-rapid-development-with-spring-boot)
   - [Building RESTful APIs](#3-building-restful-apis)
   - [Data Access with Spring Data JPA](#4-data-access-with-spring-data-jpa)
   - [Testing and Security](#5-testing-and-security)
4. [Java 21 Features in Action](#java-21-features-in-action)
5. [Functional Programming with Spring](#functional-programming-with-spring)
6. [Your 12-Week Learning Path](#your-12-week-learning-path)
7. [Common Pitfalls and How to Avoid Them](#common-pitfalls)
8. [Essential Resources](#essential-resources)

---

## What is Spring?

Spring is like a **comprehensive toolkit** that helps developers build enterprise-grade Java applications faster and better. Think of it as:

- **LEGO Blocks** - Pre-built components you can assemble instead of building from scratch
- **Power Tools** - Automated solutions for common problems (database access, security, web servers)
- **Best Practice Enforcer** - Guides you toward clean, maintainable code architecture

### The Spring Ecosystem

```
┌─────────────────────────────────────────────────────────┐
│                    Spring Ecosystem                      │
├─────────────────────────────────────────────────────────┤
│ Spring Boot 3.2 (Auto-configuration + Embedded Server)  │
├─────────────────────────────────────────────────────────┤
│ Spring Framework 6.0 (Core IoC Container + DI + AOP)    │
├─────────────────────────────────────────────────────────┤
│ Spring Data JPA (Repository Pattern + Query Methods)     │
├─────────────────────────────────────────────────────────┤
│ Hibernate 6.4+ (ORM Implementation + SQL Generation)     │
├─────────────────────────────────────────────────────────┤
│ JDBC (Low-Level Database Driver)                         │
└─────────────────────────────────────────────────────────┘
```

---

## Technology Stack Overview

### Java 21 LTS Features We'll Use

| Feature | Release | Purpose | Example Use Case |
|---------|---------|---------|------------------|
| **Virtual Threads** | Java 21 (JEP 444) | Handle 10,000+ concurrent requests | REST API with high traffic |
| **Record Patterns** | Java 21 (JEP 440) | Destructure Records in pattern matching | DTO validation |
| **Pattern Matching for switch** | Java 21 (JEP 441) | Type-safe switch expressions | Payment processing |
| **Sequenced Collections** | Java 21 (JEP 431) | getFirst()/getLast() methods | Query result handling |
| **Sealed Classes** | Java 17 (finalized) | Restrict inheritance hierarchy | Domain boundaries |
| **Records** | Java 16 (finalized) | Immutable data carriers | DTOs, API responses |
| **Text Blocks** | Java 15 (finalized) | Multi-line string literals | SQL queries, JSON |

### Spring Boot 3.2 Features

- **spring.threads.virtual.enabled=true** - First-class Virtual Threads support
- **Native AOT Compilation** - Faster startup with GraalVM
- **Observability** - Built-in Micrometer metrics and distributed tracing
- **Problem Details (RFC 7807)** - Standardized error responses
- **Docker Compose Integration** - Auto-start dependencies

### Spring Data JPA Benefits

- **Zero Boilerplate** - Automatic CRUD operations
- **Derived Query Methods** - Generate SQL from method names
- **Query By Example** - Dynamic queries without writing JPQL
- **Projections** - Fetch only needed fields
- **Specifications** - Type-safe dynamic queries

---

## Five Key Areas of Spring Development

### 1️⃣ Core Spring Concepts: The Foundation

#### 1.1 Inversion of Control (IoC) - "Let Spring Be the Boss"

**The Problem (Traditional Java):**
```java
// You manually create and manage everything
public class OrderService {
    private PaymentService paymentService = new PaymentService();
    private EmailService emailService = new EmailService();
    private DatabaseConnection db = new DatabaseConnection();
    
    // What if PaymentService needs different parameters?
    // What if you want to swap implementations for testing?
    // This is TIGHT COUPLING - hard to change, hard to test
}
```

**The Solution (Spring IoC):**
```java
// Spring creates and manages everything for you
@Service
public class OrderService {
    private final PaymentService paymentService;
    private final EmailService emailService;
    
    // Spring automatically injects what you need
    @Autowired
    public OrderService(PaymentService paymentService, EmailService emailService) {
        this.paymentService = paymentService;
        this.emailService = emailService;
    }
    
    // Now you can easily swap implementations
    // Testing is simple - just provide mock services
}
```

**Key Concept:** You declare **WHAT** you need, Spring figures out **HOW** to provide it.

---

#### 1.2 Dependency Injection (DI) - "Spring Delivers What You Need"

**Three Types of Dependency Injection:**

##### Constructor Injection (✅ RECOMMENDED)
```java
@Service
public class TransactionService {
    private final TransactionRepository repository;
    
    // Immutable dependencies - best practice
    @Autowired
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }
}
```

**Why Constructor Injection?**
- ✅ Immutable dependencies (final fields)
- ✅ Easy to test (just pass mocks in constructor)
- ✅ Fails fast if dependencies missing
- ✅ Clear what's required

##### Field Injection (❌ AVOID)
```java
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository; // Mutable, hard to test
}
```

##### Setter Injection (⚠️ RARELY NEEDED)
```java
@Service
public class TransactionService {
    private TransactionRepository repository;
    
    @Autowired
    public void setRepository(TransactionRepository repository) {
        this.repository = repository;
    }
}
```

---

#### 1.3 Component Scanning and Stereotypes

Spring automatically finds and registers your beans:

```java
@SpringBootApplication  // Combines 3 annotations below
// @Configuration - Marks as configuration class
// @EnableAutoConfiguration - Enables Spring Boot auto-config
// @ComponentScan - Scans for components in this package and sub-packages
public class FinTechApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinTechApplication.class, args);
    }
}
```

**Spring Stereotypes (Component Types):**

```java
@Controller       // Web layer - handles HTTP requests
@RestController   // REST API - returns data (not HTML views)
@Service          // Business logic layer
@Repository       // Data access layer - database operations
@Configuration    // Configuration classes with @Bean methods
@Component        // Generic component (use above when possible)
```

**Example Layered Architecture:**
```
┌─────────────────────────────────────────────┐
│  @RestController (REST API Layer)           │
│  - TransactionController.java               │
│  - Handles HTTP requests/responses          │
├─────────────────────────────────────────────┤
│  @Service (Business Logic Layer)            │
│  - TransactionService.java                  │
│  - Business rules and validations           │
├─────────────────────────────────────────────┤
│  @Repository (Data Access Layer)            │
│  - TransactionRepository.java               │
│  - Database queries and persistence         │
├─────────────────────────────────────────────┤
│  @Entity (Domain Model Layer)               │
│  - Transaction.java                         │
│  - Database table mappings                  │
└─────────────────────────────────────────────┘
```

---

#### 1.4 Aspect-Oriented Programming (AOP) - "Do Things Automatically"

**Use Case:** Logging, security, transactions - things that "cut across" your application.

**Problem Without AOP:**
```java
@Service
public class TransactionService {
    public void processPayment(Payment payment) {
        // Logging - repeated everywhere
        log.info("Starting payment processing");
        
        // Security check - repeated everywhere
        if (!SecurityContext.hasPermission("PROCESS_PAYMENT")) {
            throw new SecurityException();
        }
        
        // Timing - repeated everywhere
        long start = System.currentTimeMillis();
        
        // ACTUAL BUSINESS LOGIC (only 1 line!)
        paymentProcessor.process(payment);
        
        // More repetitive code...
        long end = System.currentTimeMillis();
        log.info("Completed in " + (end - start) + "ms");
    }
}
```

**Solution With AOP:**
```java
@Aspect
@Component
public class LoggingAspect {
    
    @Around("@annotation(Loggable)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        
        Object result = joinPoint.proceed();
        
        long end = System.currentTimeMillis();
        log.info("{} executed in {}ms", joinPoint.getSignature(), (end - start));
        
        return result;
    }
}

// Now your service is clean!
@Service
public class TransactionService {
    
    @Loggable  // AOP automatically adds logging
    @Secured("ROLE_ADMIN")  // AOP automatically checks security
    @Transactional  // AOP automatically manages transactions
    public void processPayment(Payment payment) {
        paymentProcessor.process(payment);  // Just business logic!
    }
}
```

**Common AOP Use Cases:**
- ✅ Logging method calls and execution time
- ✅ Security checks and authorization
- ✅ Transaction management (@Transactional)
- ✅ Caching (@Cacheable)
- ✅ Error handling and retry logic
- ✅ Performance monitoring

---

### 2️⃣ Rapid Development with Spring Boot

#### 2.1 Why Spring Boot?

Spring Boot = **Spring Framework + Auto-Configuration + Embedded Server + Production Features**

**Before Spring Boot (2013):**
- ❌ Write 200+ lines of XML configuration
- ❌ Manually download 50+ JAR dependencies
- ❌ Install and configure Tomcat separately
- ❌ 2-3 hours to get "Hello World" running

**With Spring Boot (Now):**
- ✅ Zero XML configuration
- ✅ Add one "starter" dependency
- ✅ Embedded Tomcat auto-configured
- ✅ 5 minutes to get "Hello World" running

---

#### 2.2 Creating Your First Spring Boot Application

**Step 1: Generate Project**
Visit [Spring Initializr](https://start.spring.io/):
- **Project:** Maven
- **Language:** Java
- **Spring Boot:** 3.2.x
- **Java:** 21
- **Dependencies:** 
  - Spring Web
  - Spring Data JPA
  - H2 Database (for learning)
  - Spring Boot DevTools
  - Validation

**Step 2: Understanding the Generated Structure**
```
my-fintech-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/fintech/
│   │   │       └── FintechApplication.java  ← Main class
│   │   └── resources/
│   │       └── application.properties        ← Configuration
│   └── test/
│       └── java/                             ← Test classes
├── pom.xml                                    ← Dependencies
└── target/                                    ← Compiled classes
```

**Step 3: The Main Application Class**
```java
package com.example.fintech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfiguration.SpringBootApplication;

@SpringBootApplication  // Magic annotation that does 3 things:
// 1. @Configuration - Says this class can define beans
// 2. @EnableAutoConfiguration - Auto-configures based on dependencies
// 3. @ComponentScan - Finds @Service, @Repository, @Controller classes
public class FintechApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FintechApplication.class, args);
        // Auto-starts embedded Tomcat on port 8080
        // Auto-configures database, JPA, transactions
        // Registers all @Component, @Service, @Repository beans
    }
}
```

**Step 4: Run the Application**
```bash
# Using Maven
mvn spring-boot:run

# Or run the main method in your IDE
# Visit: http://localhost:8080
```

---

#### 2.3 Spring Boot Starters (Dependency Management Made Easy)

Instead of adding 20+ individual JARs, add ONE starter:

```xml
<!-- Traditional way (MESSY) -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
<!-- ... 15 more dependencies ... -->

<!-- Spring Boot way (CLEAN) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <!-- Automatically includes all web dependencies! -->
</dependency>
```

**Common Starters:**

| Starter | Purpose | Includes |
|---------|---------|----------|
| spring-boot-starter-web | REST APIs | Spring MVC, Tomcat, JSON |
| spring-boot-starter-data-jpa | Database access | Hibernate, JDBC, Transactions |
| spring-boot-starter-security | Authentication | Spring Security, OAuth2 |
| spring-boot-starter-test | Testing | JUnit 5, Mockito, AssertJ |
| spring-boot-starter-validation | Bean validation | Hibernate Validator |
| spring-boot-starter-actuator | Monitoring | Health checks, Metrics |

---

#### 2.4 Auto-Configuration Magic

Spring Boot looks at your classpath and automatically configures beans:

**Example 1: Database Auto-Configuration**
```java
// Just add H2 dependency - Spring Boot automatically:
// 1. Creates DataSource bean
// 2. Configures JPA EntityManagerFactory
// 3. Sets up TransactionManager
// 4. Initializes database schema

// You just use it!
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data auto-implements this interface!
}
```

**Example 2: JSON Auto-Configuration**
```java
@RestController
public class UserController {
    
    @GetMapping("/users")
    public User getUser() {
        return new User("Alice", "alice@example.com");
    }
    // Spring Boot automatically:
    // 1. Converts User to JSON using Jackson
    // 2. Sets Content-Type: application/json
    // 3. Handles serialization errors
}
```

**How to See What's Auto-Configured:**
```bash
# Run your app with debug logging
mvn spring-boot:run -Ddebug=true

# Or add to application.properties:
logging.level.org.springframework.boot.autoconfigure=DEBUG
```

---

#### 2.5 application.properties Configuration

**File Location:** `src/main/resources/application.properties`

```properties
# ===================================================================
# COMMON SPRING BOOT PROPERTIES
# ===================================================================

# ----------------------------------------
# SERVER CONFIGURATION
# ----------------------------------------
server.port=8080
server.servlet.context-path=/api

# ----------------------------------------
# DATASOURCE CONFIGURATION
# ----------------------------------------
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

# ----------------------------------------
# JPA / HIBERNATE CONFIGURATION
# ----------------------------------------
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# ----------------------------------------
# VIRTUAL THREADS (Java 21)
# ----------------------------------------
spring.threads.virtual.enabled=true

# ----------------------------------------
# LOGGING CONFIGURATION
# ----------------------------------------
logging.level.root=INFO
logging.level.com.example.fintech=DEBUG
logging.level.org.hibernate.SQL=DEBUG

# ----------------------------------------
# ACTUATOR ENDPOINTS
# ----------------------------------------
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always
```

**Environment-Specific Configuration:**
```
application.properties          ← Default settings
application-dev.properties      ← Development
application-prod.properties     ← Production
application-test.properties     ← Testing
```

**Activate Profile:**
```bash
# Command line
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Or in application.properties
spring.profiles.active=dev
```

---

### 3️⃣ Building RESTful APIs

#### 3.1 Understanding REST

**REST = REpresentational State Transfer**

Think of it as a **standardized way for applications to talk to each other over HTTP**.

**Key Principles:**
1. **Resources** - Everything is a resource (User, Transaction, Account)
2. **URLs** - Each resource has a unique URL
3. **HTTP Methods** - Standard operations (GET, POST, PUT, DELETE)
4. **Stateless** - Each request is independent
5. **JSON** - Data format for requests/responses

**HTTP Methods (CRUD Operations):**

| HTTP Method | CRUD Operation | Example URL | Purpose |
|-------------|----------------|-------------|---------|
| GET | Read | GET /api/users | Retrieve all users |
| GET | Read | GET /api/users/123 | Retrieve user with ID 123 |
| POST | Create | POST /api/users | Create a new user |
| PUT | Update | PUT /api/users/123 | Update user 123 (full replacement) |
| PATCH | Update | PATCH /api/users/123 | Update user 123 (partial update) |
| DELETE | Delete | DELETE /api/users/123 | Delete user 123 |

---

#### 3.2 Your First REST Controller

**Example: FinTech Transaction API**

```java
package com.example.fintech.controller;

import com.example.fintech.domain.Transaction;
import com.example.fintech.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Combines @Controller + @ResponseBody (auto-convert to JSON)
@RequestMapping("/api/transactions")  // Base URL for all methods
public class TransactionController {
    
    private final TransactionService transactionService;
    
    // Constructor injection (best practice)
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    // CREATE - POST /api/transactions
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction created = transactionService.create(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    // READ ALL - GET /api/transactions
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }
    
    // READ ONE - GET /api/transactions/123
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        return transactionService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // UPDATE - PUT /api/transactions/123
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @RequestBody Transaction transaction) {
        return transactionService.update(id, transaction)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // DELETE - DELETE /api/transactions/123
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    // CUSTOM QUERY - GET /api/transactions/customer/456
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Transaction>> getCustomerTransactions(
            @PathVariable Long customerId) {
        List<Transaction> transactions = transactionService.findByCustomerId(customerId);
        return ResponseEntity.ok(transactions);
    }
}
```

---

#### 3.3 Understanding Annotations

```java
@RestController
// = @Controller + @ResponseBody
// Tells Spring this class handles HTTP requests
// Auto-converts return values to JSON

@RequestMapping("/api/transactions")
// Base path for ALL methods in this controller
// Methods inherit this path

@GetMapping
// Shortcut for @RequestMapping(method = RequestMethod.GET)
// Also: @PostMapping, @PutMapping, @DeleteMapping, @PatchMapping

@PathVariable
// Extracts values from URL path
// Example: /users/{id} → @PathVariable Long id

@RequestBody
// Automatically deserializes JSON to Java object
// Uses Jackson library under the hood

@RequestParam
// Extracts query parameters
// Example: /users?age=25 → @RequestParam int age

ResponseEntity<T>
// HTTP response with status code, headers, and body
// ResponseEntity.ok(data) → 200 OK
// ResponseEntity.notFound().build() → 404 Not Found
// ResponseEntity.status(HttpStatus.CREATED).body(data) → 201 Created
```

---

#### 3.4 Request/Response Examples

**Creating a Transaction:**

**Request:**
```http
POST /api/transactions HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "customerId": 123,
  "amount": 250.00,
  "currency": "USD",
  "type": "PAYMENT",
  "description": "Invoice payment"
}
```

**Response:**
```http
HTTP/1.1 201 Created
Content-Type: application/json

{
  "id": 456,
  "customerId": 123,
  "amount": 250.00,
  "currency": "USD",
  "type": "PAYMENT",
  "status": "PENDING",
  "description": "Invoice payment",
  "createdAt": "2026-02-15T10:30:00Z"
}
```

**Testing with cURL:**
```bash
# Create transaction
curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
  -d '{"customerId":123,"amount":250.00,"type":"PAYMENT"}'

# Get all transactions
curl http://localhost:8080/api/transactions

# Get specific transaction
curl http://localhost:8080/api/transactions/456

# Delete transaction
curl -X DELETE http://localhost:8080/api/transactions/456
```

---

#### 3.5 Modern Java 21 Features in Controllers

**Using Records for DTOs (Zero Boilerplate):**

```java
// Traditional DTO (50+ lines of code)
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private String currency;
    
    public TransactionDTO() {}
    
    public TransactionDTO(Long id, BigDecimal amount, String currency) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    // equals(), hashCode(), toString()...
}

// Java 16+ Record (1 line!)
public record TransactionDTO(Long id, BigDecimal amount, String currency) {}
// Automatically generates: constructor, getters, equals(), hashCode(), toString()
```

**Using Records in Controller:**

```java
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    @GetMapping("/{id}/summary")
    public ResponseEntity<TransactionSummaryDTO> getTransactionSummary(@PathVariable Long id) {
        return transactionService.getSummary(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}

// Clean, immutable DTO
public record TransactionSummaryDTO(
    Long transactionId,
    String customerName,
    BigDecimal amount,
    String status,
    LocalDateTime createdAt
) {}
```

**Using Sealed Classes for Payment Types:**

```java
// Domain model with sealed classes (compile-time exhaustiveness)
public sealed interface Payment 
    permits CreditCardPayment, PayPalPayment, CryptoPayment {
    BigDecimal getAmount();
}

public record CreditCardPayment(
    String cardNumber,
    String cvv,
    BigDecimal amount
) implements Payment {}

public record PayPalPayment(
    String email,
    BigDecimal amount
) implements Payment {}

public record CryptoPayment(
    String walletAddress,
    String cryptocurrency,
    BigDecimal amount
) implements Payment {}

// Controller with pattern matching
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @PostMapping
    public ResponseEntity<PaymentResult> processPayment(@RequestBody Payment payment) {
        PaymentResult result = switch (payment) {
            case CreditCardPayment cc -> processCreditCard(cc);
            case PayPalPayment pp -> processPayPal(pp);
            case CryptoPayment crypto -> processCrypto(crypto);
            // Compiler ensures all cases covered!
        };
        return ResponseEntity.ok(result);
    }
}
```

---

### 4️⃣ Data Access with Spring Data JPA

#### 4.1 The Database Layer Architecture

```
┌─────────────────────────────────────────────────────────┐
│  Your Code (@RestController, @Service)                  │
├─────────────────────────────────────────────────────────┤
│  Spring Data JPA (Repository Abstraction)               │
│  - Derived query methods                                │
│  - @Query with JPQL                                     │
│  - Query by Example                                     │
├─────────────────────────────────────────────────────────┤
│  JPA (Java Persistence API - Specification)             │
│  - @Entity, @Id, @Column annotations                    │
│  - EntityManager interface                              │
├─────────────────────────────────────────────────────────┤
│  Hibernate (ORM Implementation - generates SQL)         │
│  - SQL generation from Java objects                     │
│  - First/second level caching                           │
│  - Lazy loading / Eager loading                         │
├─────────────────────────────────────────────────────────┤
│  JDBC (Low-level database driver)                       │
└─────────────────────────────────────────────────────────┘
```

**Why This Layered Approach?**
- ✅ Write Java code, not SQL
- ✅ Swap databases without code changes (MySQL → PostgreSQL → Oracle)
- ✅ Automatic schema generation
- ✅ Object-oriented queries
- ✅ First-class support for relationships (one-to-many, many-to-many)

---

#### 4.2 Creating Your First JPA Entity

**Entity = Java class that maps to a database table**

```java
package com.example.fintech.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity  // Tells JPA this is a database table
@Table(name = "transactions")  // Optional: customize table name
public class Transaction {
    
    @Id  // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment
    private Long id;
    
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    @Column(length = 3)
    private String currency;
    
    @Enumerated(EnumType.STRING)  // Store enum as string, not number
    @Column(nullable = false)
    private TransactionType type;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;
    
    @Column(length = 500)
    private String description;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Version  // Optimistic locking - prevents concurrent update conflicts
    private Long version;
    
    // JPA requires default constructor
    public Transaction() {
        this.createdAt = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }
    
    // Business logic methods
    public void approve() {
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException("Can only approve pending transactions");
        }
        this.status = TransactionStatus.APPROVED;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void reject() {
        this.status = TransactionStatus.REJECTED;
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean isHighValue() {
        return this.amount.compareTo(new BigDecimal("10000")) > 0;
    }
    
    // Getters and setters...
}

// Enums
enum TransactionType {
    PAYMENT, REFUND, TRANSFER, WITHDRAWAL, DEPOSIT
}

enum TransactionStatus {
    PENDING, APPROVED, REJECTED, COMPLETED, FAILED
}
```

**What Hibernate Generates:**

```sql
CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    currency VARCHAR(3),
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT
);
```

---

#### 4.3 Spring Data JPA Repository (Zero Boilerplate)

**Traditional DAO (Data Access Object) - 100+ lines:**

```java
// Old way - manual JDBC code
public class TransactionDAO {
    private DataSource dataSource;
    
    public Transaction findById(Long id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Transaction t = new Transaction();
                    t.setId(rs.getLong("id"));
                    t.setAmount(rs.getBigDecimal("amount"));
                    // ... 15 more lines mapping columns ...
                    return t;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    
    // Repeat for save(), update(), delete(), findAll()...
}
```

**Spring Data JPA Way - 1 interface:**

```java
package com.example.fintech.repository;

import com.example.fintech.domain.Transaction;
import com.example.fintech.domain.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // =====================================================================
    // SPRING DATA AUTO-IMPLEMENTS THESE METHODS:
    // =====================================================================
    // save(Transaction entity)              - Create or update
    // findById(Long id)                     - Find by primary key
    // findAll()                             - Get all records
    // deleteById(Long id)                   - Delete by ID
    // count()                               - Count all records
    // existsById(Long id)                   - Check if exists
    
    // =====================================================================
    // DERIVED QUERY METHODS (Spring generates SQL from method names)
    // =====================================================================
    
    // SELECT * FROM transactions WHERE customer_id = ?
    List<Transaction> findByCustomerId(Long customerId);
    
    // SELECT * FROM transactions WHERE status = ?
    List<Transaction> findByStatus(TransactionStatus status);
    
    // SELECT * FROM transactions WHERE customer_id = ? AND status = ?
    List<Transaction> findByCustomerIdAndStatus(Long customerId, TransactionStatus status);
    
    // SELECT * FROM transactions WHERE amount > ?
    List<Transaction> findByAmountGreaterThan(BigDecimal amount);
    
    // SELECT * FROM transactions WHERE created_at BETWEEN ? AND ?
    List<Transaction> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    // SELECT * FROM transactions WHERE currency IN (?, ?, ?)
    List<Transaction> findByCurrencyIn(List<String> currencies);
    
    // SELECT * FROM transactions WHERE description LIKE %?%
    List<Transaction> findByDescriptionContaining(String keyword);
    
    // SELECT * FROM transactions ORDER BY created_at DESC
    List<Transaction> findAllByOrderByCreatedAtDesc();
    
    // SELECT COUNT(*) FROM transactions WHERE status = ?
    long countByStatus(TransactionStatus status);
    
    // DELETE FROM transactions WHERE customer_id = ?
    void deleteByCustomerId(Long customerId);
    
    // =====================================================================
    // STREAM QUERIES (Memory-efficient for large datasets)
    // =====================================================================
    
    // Process one row at a time instead of loading all into memory
    @Query("SELECT t FROM Transaction t WHERE t.status = :status")
    Stream<Transaction> streamByStatus(@Param("status") TransactionStatus status);
    
    // =====================================================================
    // CUSTOM JPQL QUERIES
    // =====================================================================
    
    @Query("""
        SELECT t FROM Transaction t
        WHERE t.customerId = :customerId
          AND t.status = 'APPROVED'
          AND t.amount > :minAmount
        ORDER BY t.createdAt DESC
        """)
    List<Transaction> findHighValueApprovedTransactions(
        @Param("customerId") Long customerId,
        @Param("minAmount") BigDecimal minAmount
    );
    
    // =====================================================================
    // NATIVE SQL (When you need database-specific features)
    // =====================================================================
    
    @Query(value = """
        SELECT 
            customer_id,
            COUNT(*) as transaction_count,
            SUM(amount) as total_amount
        FROM transactions
        WHERE created_at >= :startDate
        GROUP BY customer_id
        HAVING COUNT(*) >= :minCount
        """, nativeQuery = true)
    List<Object[] > getCustomerStatistics(
        @Param("startDate") LocalDateTime startDate,
        @Param("minCount") long minCount
    );
    
    // =====================================================================
    // DTO PROJECTIONS (Fetch only needed fields)
    // =====================================================================
    
    @Query("""
        SELECT new com.example.fintech.dto.TransactionSummaryDTO(
            t.id, t.amount, t.currency, t.status
        )
        FROM Transaction t
        WHERE t.customerId = :customerId
        """)
    List<TransactionSummaryDTO> getCustomerTransactionSummaries(@Param("customerId") Long customerId);
}
```

**Query Method Naming Convention:**

| Keyword | Example | Generated SQL |
|---------|---------|---------------|
| findBy | findByStatus(status) | WHERE status = ? |
| And | findByCustomerIdAndStatus() | WHERE customer_id = ? AND status = ? |
| Or | findByAmountOrStatus() | WHERE amount = ? OR status = ? |
| Between | findByCreatedAtBetween() | WHERE created_at BETWEEN ? AND ? |
| LessThan | findByAmountLessThan() | WHERE amount < ? |
| GreaterThan | findByAmountGreaterThan() | WHERE amount > ? |
| Like | findByDescriptionLike() | WHERE description LIKE ? |
| In | findByCurrencyIn() | WHERE currency IN (?, ?, ?) |
| OrderBy | findAllByOrderByCreatedAtDesc() | ORDER BY created_at DESC |
| Top | findTop10ByOrderByAmountDesc() | LIMIT 10 ORDER BY amount DESC |

---

#### 4.4 Using the Repository in a Service

```java
package com.example.fintech.service;

import com.example.fintech.domain.Transaction;
import com.example.fintech.domain.TransactionStatus;
import com.example.fintech.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional  // All methods run in a transaction
public class TransactionService {
    
    private final TransactionRepository repository;
    
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }
    
    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }
    
    public Optional<Transaction> findById(Long id) {
        return repository.findById(id);
    }
    
    public List<Transaction> findAll() {
        return repository.findAll();
    }
    
    public List<Transaction> findPendingTransactions() {
        return repository.findByStatus(TransactionStatus.PENDING);
    }
    
    public List<Transaction> findCustomerTransactions(Long customerId) {
        return repository.findByCustomerId(customerId);
    }
    
    @Transactional
    public void approveTransaction(Long id) {
        Transaction transaction = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        
        transaction.approve();  // Business logic
        repository.save(transaction);  // Persist changes
    }
    
    public long countPendingTransactions() {
        return repository.countByStatus(TransactionStatus.PENDING);
    }
    
    public List<Transaction> findRecentTransactions(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        return repository.findByCreatedAtBetween(startDate, LocalDateTime.now());
    }
}
```

---

### 5️⃣ Testing and Security

#### 5.1 Testing Your Spring Boot Application

**Test Pyramid:**
```
        ┌───────┐
        │  E2E  │  ← Few (slow, covers full system)
        ├───────┤
        │ Integ │  ← Some (medium speed, covers layers)
        ├───────┤
        │ Unit  │  ← Many (fast, covers classes)
        └───────┘
```

**Unit Test (Service Layer):**

```java
package com.example.fintech.service;

import com.example.fintech.domain.Transaction;
import com.example.fintech.domain.TransactionStatus;
import com.example.fintech.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    
    @Mock  // Mock dependency
    private TransactionRepository repository;
    
    @InjectMocks  // Injects mocks into service
    private TransactionService service;
    
    @Test
    void createTransaction_shouldSaveTransaction() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("100.00"));
        
        when(repository.save(any(Transaction.class))).thenReturn(transaction);
        
        // Act
        Transaction result = service.create(transaction);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualByComparingTo("100.00");
        verify(repository, times(1)).save(transaction);
    }
    
    @Test
    void approveTransaction_shouldUpdateStatus() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setStatus(TransactionStatus.PENDING);
        
        when(repository.findById(1L)).thenReturn(Optional.of(transaction));
        
        // Act
        service.approveTransaction(1L);
        
        // Assert
        assertThat(transaction.getStatus()).isEqualTo(TransactionStatus.APPROVED);
        verify(repository).save(transaction);
    }
    
    @Test
    void approveTransaction_shouldThrowException_whenNotFound() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> service.approveTransaction(999L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Transaction not found");
    }
}
```

**Integration Test (Full Spring Context):**

```java
package com.example.fintech.service;

import com.example.fintech.domain.Transaction;
import com.example.fintech.domain.TransactionStatus;
import com.example.fintech.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest  // Loads full Spring context
@Transactional  // Auto-rollback after each test
class TransactionServiceIntegrationTest {
    
    @Autowired
    private TransactionService service;
    
    @Autowired
    private TransactionRepository repository;
    
    @Test
    void findPendingTransactions_shouldReturnOnlyPending() {
        // Arrange
        Transaction t1 = new Transaction();
        t1.setStatus(TransactionStatus.PENDING);
        t1.setAmount(new BigDecimal("100"));
        
        Transaction t2 = new Transaction();
        t2.setStatus(TransactionStatus.APPROVED);
        t2.setAmount(new BigDecimal("200"));
        
        repository.save(t1);
        repository.save(t2);
        
        // Act
        List<Transaction> pending = service.findPendingTransactions();
        
        // Assert
        assertThat(pending).hasSize(1);
        assertThat(pending.get(0).getStatus()).isEqualTo(TransactionStatus.PENDING);
    }
}
```

**REST Controller Test:**

```java
package com.example.fintech.controller;

import com.example.fintech.domain.Transaction;
import com.example.fintech.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TransactionService service;
    
    @Test
    void getAllTransactions_shouldReturnList() throws Exception {
        // Arrange
        Transaction t1 = new Transaction();
        t1.setId(1L);
        t1.setAmount(new BigDecimal("100"));
        
        when(service.findAll()).thenReturn(Arrays.asList(t1));
        
        // Act & Assert
        mockMvc.perform(get("/api/transactions"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].amount").value(100));
    }
    
    @Test
    void getTransaction_shouldReturn404_whenNotFound() throws Exception {
        when(service.findById(999L)).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/transactions/999"))
            .andExpect(status().isNotFound());
    }
}
```

---

#### 5.2 Spring Security Basics

**Add Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**Basic Security Configuration:**

```java
package com.example.fintech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**", "/api/health").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/transactions/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout.permitAll())
            .csrf(csrf -> csrf.disable());  // For REST APIs
        
        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();
        
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN", "USER")
            .build();
        
        return new InMemoryUserDetailsManager(user, admin);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**Method-Level Security:**

```java
@Service
public class TransactionService {
    
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllTransactions() {
        repository.deleteAll();
    }
    
    @PreAuthorize("hasRole('USER')")
    public Transaction createTransaction(Transaction transaction) {
        return repository.save(transaction);
    }
    
    @PreAuthorize("#customerId == authentication.principal.id or hasRole('ADMIN')")
    public List<Transaction> getCustomerTransactions(Long customerId) {
        return repository.findByCustomerId(customerId);
    }
}
```

---

## Java 21 Features in Action

### Virtual Threads (Project Loom)

**Enable in application.properties:**
```properties
spring.threads.virtual.enabled=true
```

**Configuration:**
```java
@Configuration
public class VirtualThreadConfig {
    
    @Bean
    public Executor virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
```

**Use Case: Handle 10,000+ Concurrent Requests**

```java
@Service
public class TransactionService {
    
    private final Executor executor;
    
    @GetMapping("/dashboard/{customerId}")
    public CompletableFuture<CustomerDashboard> getCustomerDashboard(@PathVariable Long customerId) {
        // All run concurrently on virtual threads
        CompletableFuture<List<Transaction>> transactionsFuture = 
            CompletableFuture.supplyAsync(() -> transactionRepository.findByCustomerId(customerId), executor);
        
        CompletableFuture<BigDecimal> balanceFuture = 
            CompletableFuture.supplyAsync(() -> accountService.getBalance(customerId), executor);
        
        CompletableFuture<List<Alert>> alertsFuture = 
            CompletableFuture.supplyAsync(() -> alertService.getAlerts(customerId), executor);
        
        return CompletableFuture.allOf(transactionsFuture, balanceFuture, alertsFuture)
            .thenApply(v -> new CustomerDashboard(
                transactionsFuture.join(),
                balanceFuture.join(),
                alertsFuture.join()
            ));
    }
}
```

---

### Records for DTOs

```java
// Response DTO
public record TransactionResponseDTO(
    Long id,
    BigDecimal amount,
    String currency,
    TransactionStatus status,
    LocalDateTime createdAt
) {
    // Compact constructor with validation
    public TransactionResponseDTO {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }
}

// Map Entity to DTO
public TransactionResponseDTO toDTO(Transaction transaction) {
    return new TransactionResponseDTO(
        transaction.getId(),
        transaction.getAmount(),
        transaction.getCurrency(),
        transaction.getStatus(),
        transaction.getCreatedAt()
    );
}
```

---

### Pattern Matching with Sealed Classes

```java
public sealed interface PaymentMethod 
    permits CreditCard, DebitCard, PayPal, CryptoCurrency {}

public record CreditCard(String number, String cvv) implements PaymentMethod {}
public record DebitCard(String number, String pin) implements PaymentMethod {}
public record PayPal(String email) implements PaymentMethod {}
public record CryptoCurrency(String walletAddress, String coinType) implements PaymentMethod {}

@Service
public class PaymentProcessor {
    
    public PaymentResult process(PaymentMethod method, BigDecimal amount) {
        return switch (method) {
            case CreditCard(var number, var cvv) -> 
                processCreditCard(number, cvv, amount);
            case DebitCard(var number, var pin) -> 
                processDebitCard(number, pin, amount);
            case PayPal(var email) -> 
                processPayPal(email, amount);
            case CryptoCurrency(var wallet, var coin) -> 
                processCrypto(wallet, coin, amount);
            // Compiler ensures all cases covered!
        };
    }
}
```

---

### Text Blocks for SQL

```java
@Query("""
    SELECT t
    FROM Transaction t
    WHERE t.customerId = :customerId
      AND t.status = 'APPROVED'
      AND t.createdAt >= :startDate
    ORDER BY t.createdAt DESC
    """)
List<Transaction> findApprovedTransactions(
    @Param("customerId") Long customerId,
    @Param("startDate") LocalDateTime startDate
);
```

---

## Functional Programming with Spring

### Stream API Best Practices

```java
@Service
public class TransactionService {
    
    // ❌ BAD: Imperative approach
    public BigDecimal calculateTotalBad(List<Transaction> transactions) {
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction t : transactions) {
            if (t.getStatus() == TransactionStatus.APPROVED) {
                total = total.add(t.getAmount());
            }
        }
        return total;
    }
    
    // ✅ GOOD: Functional approach
    public BigDecimal calculateTotalGood(List<Transaction> transactions) {
        return transactions.stream()
            .filter(t -> t.getStatus() == TransactionStatus.APPROVED)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // ✅ EXCELLENT: Extract predicates for reusability
    private static final Predicate<Transaction> IS_APPROVED = 
        t -> t.getStatus() == TransactionStatus.APPROVED;
    
    private static final Predicate<Transaction> IS_HIGH_VALUE = 
        t -> t.getAmount().compareTo(new BigDecimal("10000")) > 0;
    
    public BigDecimal calculateHighValueTotal(List<Transaction> transactions) {
        return transactions.stream()
            .filter(IS_APPROVED.and(IS_HIGH_VALUE))
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Group transactions by currency
    public Map<String, List<Transaction>> groupByCurrency(List<Transaction> transactions) {
        return transactions.stream()
            .collect(Collectors.groupingBy(Transaction::getCurrency));
    }
    
    // Calculate statistics
    public DoubleSummaryStatistics getTransactionStats(List<Transaction> transactions) {
        return transactions.stream()
            .mapToDouble(t -> t.getAmount().doubleValue())
            .summaryStatistics();
    }
}
```

---

### Functional Interfaces and Lambda Expressions

```java
@Service
public class TransactionValidationService {
    
    // Define custom functional interface
    @FunctionalInterface
    public interface TransactionValidator {
        boolean validate(Transaction transaction);
    }
    
    // Use lambdas for validation rules
    private final TransactionValidator amountValidator = 
        t -> t.getAmount().compareTo(BigDecimal.ZERO) > 0;
    
    private final TransactionValidator currencyValidator = 
        t -> List.of("USD", "EUR", "GBP").contains(t.getCurrency());
    
    private final TransactionValidator customerValidator = 
        t -> t.getCustomerId() != null && t.getCustomerId() > 0;
    
    // Compose validators
    public boolean validateTransaction(Transaction transaction) {
        return amountValidator.validate(transaction)
            && currencyValidator.validate(transaction)
            && customerValidator.validate(transaction);
    }
    
    // Chain validators functionally
    public TransactionValidator combineValidators(TransactionValidator... validators) {
        return transaction -> Arrays.stream(validators)
            .allMatch(validator -> validator.validate(transaction));
    }
}
```

---

## Your 12-Week Learning Path

### Weeks 1-2: Foundation
- ✅ Install Java 21 JDK, IntelliJ IDEA, Maven
- ✅ Complete [Spring Boot Getting Started Guide](https://spring.io/guides/gs/spring-boot/)
- ✅ Build "Hello World" REST API
- ✅ Learn Git basics

### Weeks 3-4: Core Concepts
- ✅ Understand IoC and DI
- ✅ Create layered architecture (@Controller, @Service, @Repository)
- ✅ Build simple CRUD REST API
- ✅ Learn about Spring Boot auto-configuration

### Weeks 5-6: Database Access
- ✅ Create JPA entities with @Entity
- ✅ Build Spring Data JPA repositories
- ✅ Write derived query methods
- ✅ Use @Query with JPQL

### Weeks 7-8: Advanced Features
- ✅ Write unit tests with JUnit 5
- ✅ Write integration tests with @SpringBootTest
- ✅ Add Spring Security authentication
- ✅ Use Java 21 Records for DTOs

### Weeks 9-10: Production Readiness
- ✅ Add validation with @Valid
- ✅ Implement global exception handling
- ✅ Add Actuator health checks and metrics
- ✅ Configure different profiles (dev, prod)

### Weeks 11-12: Portfolio Project
- ✅ Build complete FinTech application
- ✅ Use Virtual Threads for concurrency
- ✅ Apply functional programming patterns
- ✅ Deploy to cloud platform
- ✅ Create comprehensive documentation

---

## Common Pitfalls and How to Avoid Them

| Pitfall | Why It's Bad | Solution |
|---------|--------------|----------|
| Using field injection (@Autowired on fields) | Hard to test, mutable | Use constructor injection |
| Not using @Transactional | LazyInitializationException | Add @Transactional on service methods |
| Exposing entities in REST API | Tight coupling | Use DTOs (Records) |
| Ignoring N+1 query problem | Performance disaster | Use @EntityGraph or JOIN FETCH |
| Not handling Optional properly | NullPointerException | Use .orElseThrow() / .map() / .ifPresent() |
| Using @RequestMapping for everything | Not RESTful | Use @GetMapping, @PostMapping, etc. |
| Hardcoding configuration | Can't change environments | Use application.properties |
| Not writing tests | Bugs in production | Write tests as you code |

---

## Essential Resources

### Official Documentation
- [Spring Framework Reference](https://docs.spring.io/spring-framework/reference/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/documentation.html)
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/reference/)
- [Java 21 Documentation](https://docs.oracle.com/en/java/javase/21/)

### Learning Platforms
- [Spring Academy](https://spring.academy/) - Official courses (FREE)
- [Baeldung](https://www.baeldung.com/) - In-depth tutorials
- [Spring Guides](https://spring.io/guides) - Step-by-step tutorials

### Tools
- **IDE:** IntelliJ IDEA Community (FREE)
- **Build Tool:** Maven 3.9+
- **Database:** PostgreSQL or MySQL
- **API Testing:** Postman or Bruno
- **Version Control:** Git + GitHub

---

## Quick Reference Card

### Common Annotations

```java
// APPLICATION
@SpringBootApplication        // Main application class

// COMPONENTS
@RestController              // REST API controller
@Service                     // Business logic
@Repository                  // Data access
@Configuration               // Configuration class
@Component                   // Generic bean

// DEPENDENCY INJECTION
@Autowired                   // Inject dependency (use constructor)

// REST API
@GetMapping("/path")         // Handle GET requests
@PostMapping("/path")        // Handle POST requests
@PutMapping("/path")         // Handle PUT requests
@DeleteMapping("/path")      // Handle DELETE requests
@PathVariable                // Extract from URL: /users/{id}
@RequestParam                // Extract from query: /users?age=25
@RequestBody                 // Parse JSON in request body

// JPA
@Entity                      // Mark as database table
@Table(name = "users")       // Customize table name
@Id                          // Primary key
@GeneratedValue              // Auto-increment
@Column                      // Customize column mapping
@Enumerated(EnumType.STRING) // Store enum as string
@Version                     // Optimistic locking

// TRANSACTIONS
@Transactional               // Run method in transaction

// VALIDATION
@Valid                       // Trigger validation
@NotNull                     // Field cannot be null
@NotBlank                    // String cannot be empty
@Min(value)                  // Minimum value
@Max(value)                  // Maximum value
@Size(min, max)              // String/Collection size

// TESTING
@SpringBootTest              // Load full Spring context
@WebMvcTest                  // Test controllers only
@Test                        // Mark test method
@Mock                        // Create mock object
@InjectMocks                 // Inject mocks

// SECURITY
@EnableWebSecurity           // Enable Spring Security
@PreAuthorize                // Method-level security
```

---

## Final Words for Junior Developers

### The Learning Mindset

1. **Start Small** - Don't try to learn everything at once
2. **Build Projects** - Theory is useless without practice
3. **Read Error Messages** - They're trying to help you!
4. **Use Debugger** - Step through code to understand flow
5. **Ask Questions** - StackOverflow, Reddit r/spring, Discord
6. **Read Source Code** - Spring is open-source - learn from it
7. **Write Tests** - They're your safety net
8. **Refactor Often** - Code gets better with iteration

### Why Spring + Java 21 is a Great Career Choice

- ✅ **High Demand** - 70% of enterprise Java uses Spring
- ✅ **Good Salaries** - Spring developers earn 10-20% more
- ✅ **Modern Technology** - Java 21 is cutting-edge
- ✅ **FinTech Focused** - Banking, payments, crypto all use Spring
- ✅ **Active Community** - Millions of developers, tons of resources
- ✅ **Future-Proof** - Spring evolves with Java ecosystem

### Your Next Steps

1. ✅ **Today:** Generate your first project at [start.spring.io](https://start.spring.io)
2. ✅ **This Week:** Build a simple REST API with CRUD operations
3. ✅ **This Month:** Create a complete application with database
4. ✅ **This Quarter:** Deploy a production-ready app to cloud

---

**Happy Coding! 🚀**

*Built with Spring Boot 3.2 + Java 21 LTS + Spring Data JPA + Modern Functional Programming*

---

## Appendix: Sample Project Structure

```
fintech-transaction-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/fintech/
│   │   │   ├── FintechApplication.java
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── VirtualThreadConfig.java
│   │   │   ├── controller/
│   │   │   │   └── TransactionController.java
│   │   │   ├── domain/
│   │   │   │   ├── Transaction.java
│   │   │   │   └── Payment.java (sealed classes)
│   │   │   ├── dto/
│   │   │   │   └── TransactionDTO.java (Records)
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── repository/
│   │   │   │   └── TransactionRepository.java
│   │   │   └── service/
│   │   │       └── TransactionService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-prod.properties
│   └── test/
│       └── java/com/example/fintech/
│           ├── service/
│           │   └── TransactionServiceTest.java
│           └── controller/
│               └── TransactionControllerTest.java
├── pom.xml
└── README.md
```

This structure follows industry best practices and is recognized by all Java developers worldwide.
