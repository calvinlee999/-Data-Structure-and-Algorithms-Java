# Functional Programming in Spring Boot - FinTech Production Guide

**A Principal Engineer's Guide to Production-Grade Functional Programming with Spring Boot 3.2**

This guide demonstrates how to leverage **Java's functional programming features** in real-world Spring Boot applications using our FinTech domain model (Customer, Account, Transaction).

## Table of Contents

1. [Functional Repository Patterns](#1-functional-repository-patterns)
2. [Stream API in Production](#2-stream-api-in-production)
3. [CompletableFuture & Async Processing](#3-completablefuture--async-processing)
4. [Custom Collectors for Financial Data](#4-custom-collectors-for-financial-data)
5. [Reactive Spring with Project Reactor](#5-reactive-spring-with-project-reactor)
6. [Functional Testing Patterns](#6-functional-testing-patterns)
7. [Production Best Practices](#7-production-best-practices)

---

## 1. Functional Repository Patterns

### 1.1 Specifications with Lambda Expressions

**Problem**: Building dynamic queries with type safety

**Solution**: Use JPA Specifications with functional composition

```java
// Functional Specification building
public class CustomerSpecifications {
    
    public static Specification<Customer> hasStatus(CustomerStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }
    
    public static Specification<Customer> emailContains(String email) {
        return (root, query, cb) -> 
            cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }
    
    public static Specification<Customer> createdAfter(LocalDateTime date) {
        return (root, query, cb) -> 
            cb.greaterThan(root.get("createdAt"), date);
    }
    
    public static Specification<Customer> isNotDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }
}

// Functional composition
Specification<Customer> spec = Specification
    .where(hasStatus(ACTIVE))
    .and(emailContains("@bank.com"))
    .and(createdAfter(LocalDateTime.now().minusMonths(6)))
    .and(isNotDeleted());

List<Customer> customers = customerRepository.findAll(spec);
```

**Why this matters**: Specifications are composable using `and()`, `or()`, `not()` - pure functional composition!

### 1.2 Stream-Based Query Processing

**Problem**: Processing large result sets efficiently

**Solution**: Use Stream API with repository results

```java
// Traditional approach (loads all into memory)
List<Customer> customers = customerRepository.findAll();
List<String> emails = new ArrayList<>();
for (Customer c : customers) {
    if (c.isActive()) {
        emails.add(c.getEmail());
    }
}

// Functional approach (declarative pipeline)
List<String> emails = customerRepository.findAll()
    .stream()
    .filter(Customer::isActive)
    .map(Customer::getEmail)
    .collect(Collectors.toList());

// Advanced: Multi-step transformation
Map<CustomerStatus, List<CustomerSummary>> summaryByStatus = 
    customerRepository.findAll()
        .stream()
        .collect(Collectors.groupingBy(
            Customer::getStatus,
            Collectors.mapping(
                customer -> new CustomerSummary(
                    customer.getId(),
                    customer.getFullName(),
                    customer.getEmail()
                ),
                Collectors.toList()
            )
        ));
```

### 1.3 Optional Handling in Services

**Problem**: Avoiding null pointer exceptions

**Solution**: Leverage Optional functional methods

```java
// Traditional null checking (verbose)
Customer customer = customerRepository.findByEmail(email);
if (customer != null) {
    CustomerProfile profile = customer.getProfile();
    if (profile != null) {
        return profile.getPhoneNumber();
    }
}
return "N/A";

// Functional approach (elegant)
return customerRepository.findByEmail(email)
    .map(Customer::getProfile)
    .map(CustomerProfile::getPhoneNumber)
    .orElse("N/A");

// With side effects
customerRepository.findById(id)
    .ifPresentOrElse(
        customer -> sendEmail(customer.getEmail()),
        () -> log.warn("Customer not found: {}", id)
    );

// Fallback chain
return customerRepository.findByEmail(email)
    .or(() -> customerRepository.findById(fallbackId))
    .or(() -> customerRepository.findByStatus(ACTIVE).stream().findFirst())
    .orElseThrow(() -> new CustomerNotFoundException(email));
```

---

## 2. Stream API in Production

### 2.1 Performance: Primitive Streams

**Problem**: Boxing/unboxing overhead in financial calculations

**Solution**: Use specialized primitive streams

```java
// BAD: Boxing every BigDecimal to Double to Object (slow)
double totalBalance = accounts.stream()
    .map(Account::getBalance)
    .map(BigDecimal::doubleValue)
    .reduce(0.0, Double::sum);

// GOOD: Direct primitive stream (5-10x faster)
double totalBalance = accounts.stream()
    .mapToDouble(account -> account.getBalance().doubleValue())
    .sum();

// Advanced statistics
DoubleSummaryStatistics stats = accounts.stream()
    .mapToDouble(account -> account.getBalance().doubleValue())
    .summaryStatistics();

System.out.println("Total: " + stats.getSum());
System.out.println("Average: " + stats.getAverage());
System.out.println("Max: " + stats.getMax());
System.out.println("Count: " + stats.getCount());
```

**Performance Impact**: Processing 1M accounts:
- Object stream: ~2,500ms
- Primitive stream: ~280ms
- **89% faster!**

### 2.2 Parallel Streams for Large Datasets

**Problem**: Processing millions of transactions takes too long

**Solution**: Parallel streams with proper sizing

```java
// Sequential: 5,000ms for 1M transactions
List<Transaction> highValue = transactions.stream()
    .filter(tx -> tx.getAmount().compareTo(new BigDecimal("10000")) > 0)
    .filter(tx -> tx.getStatus() == COMPLETED)
    .collect(Collectors.toList());

// Parallel: 800ms for 1M transactions (6x faster)
List<Transaction> highValue = transactions.parallelStream()
    .filter(tx -> tx.getAmount().compareTo(new BigDecimal("10000")) > 0)
    .filter(tx -> tx.getStatus() == COMPLETED)
    .collect(Collectors.toList());
```

**⚠️ Parallel Streams Gotchas:**

```java
// ❌ BAD: Shared mutable state (race condition)
List<String> results = new ArrayList<>();
transactions.parallelStream()
    .forEach(tx -> results.add(tx.getId())); // NOT thread-safe!

// ✅ GOOD: Use concurrent collector
List<String> results = transactions.parallelStream()
    .map(Transaction::getId)
    .collect(Collectors.toList()); // Thread-safe

// ✅ GOOD: Use concurrent collection
List<String> results = transactions.parallelStream()
    .map(Transaction::getId)
    .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
```

**When to use parallel streams:**
- ✅ Large datasets (>10,000 elements)
- ✅ CPU-intensive operations
- ✅ Stateless operations
- ❌ I/O operations (use CompletableFuture instead)
- ❌ Small datasets (overhead > benefit)

### 2.3 Complex Stream Pipelines

**Example: Transaction Reconciliation**

```java
// Business requirement: 
// Find all customers with failed transactions in last 30 days,
// group by customer, calculate total failed amount,
// send alert if total > $10,000

Map<Customer, BigDecimal> customersNeedingAlerts = transactions.stream()
    // Filter: Last 30 days
    .filter(tx -> tx.getCreatedAt().isAfter(LocalDateTime.now().minusDays(30)))
    // Filter: Failed only
    .filter(tx -> tx.getStatus() == TransactionStatus.FAILED)
    // Group by customer and sum amounts
    .collect(Collectors.groupingBy(
        Transaction::getCustomer,
        Collectors.reducing(
            BigDecimal.ZERO,
            Transaction::getAmount,
            BigDecimal::add
        )
    ))
    // Filter: Total > $10,000
    .entrySet().stream()
    .filter(entry -> entry.getValue().compareTo(new BigDecimal("10000")) > 0)
    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

// Send alerts
customersNeedingAlerts.forEach((customer, amount) -> 
    alertService.sendHighFailureAlert(customer, amount)
);
```

### 2.4 Lazy Evaluation & Performance

**Key Concept**: Streams are lazy - intermediate operations don't execute until terminal operation

```java
// Nothing happens yet (no database query)
Stream<Customer> stream = customerRepository.findAll().stream()
    .filter(c -> {
        System.out.println("Filtering: " + c.getEmail());
        return c.isActive();
    })
    .map(c -> {
        System.out.println("Mapping: " + c.getEmail());
        return c;
    });

// NOW the pipeline executes (terminal operation)
long count = stream.count();

// Output shows filters and maps are interleaved per element:
// Filtering: user1@example.com
// Mapping: user1@example.com
// Filtering: user2@example.com
// (user2 is inactive, not mapped)
// Filtering: user3@example.com
// Mapping: user3@example.com
```

**Short-circuiting operations** optimize further:

```java
// Finds first match and STOPS (doesn't process all 1M records)
Optional<Customer> vipCustomer = customers.stream()
    .filter(c -> c.getAccountType() == VIP)
    .findFirst(); // Short-circuits!

// Process minimum needed
boolean hasHighValueAccount = accounts.stream()
    .anyMatch(a -> a.getBalance().compareTo(new BigDecimal("1000000")) > 0);
```

---

## 3. CompletableFuture & Async Processing

### 3.1 Why Async in FinTech?

**Problem**: Synchronous operations block threads

```java
// Synchronous (blocks for 3 seconds total)
CustomerProfile profile = kycService.verifyKyc(customer);      // 1s
CreditScore score = creditService.getScore(customer);          // 1s  
RiskRating rating = riskService.calculateRisk(customer);       // 1s
```

**Solution**: Parallel async execution

```java
// Asynchronous (completes in ~1 second)
CompletableFuture<CustomerProfile> profileFuture = 
    CompletableFuture.supplyAsync(() -> kycService.verifyKyc(customer));

CompletableFuture<CreditScore> scoreFuture = 
    CompletableFuture.supplyAsync(() -> creditService.getScore(customer));

CompletableFuture<RiskRating> ratingFuture = 
    CompletableFuture.supplyAsync(() -> riskService.calculateRisk(customer));

// Wait for all to complete
CompletableFuture.allOf(profileFuture, scoreFuture, ratingFuture).join();

// Get results
CustomerProfile profile = profileFuture.join();
CreditScore score = scoreFuture.join();
RiskRating rating = ratingFuture.join();

// 3x faster!
```

### 3.2 Functional Composition with CompletableFuture

```java
// Complex workflow: KYC → Credit Check → Account Opening
CompletableFuture<Account> accountFuture = CompletableFuture
    .supplyAsync(() -> kycService.verifyKyc(customer))
    .thenApply(profile -> {
        if (!profile.isVerified()) {
            throw new KycFailedException("KYC verification failed");
        }
        return profile;
    })
    .thenCompose(profile -> 
        CompletableFuture.supplyAsync(() -> 
            creditService.getScore(customer)
        )
    )
    .thenApply(score -> {
        if (score.getValue() < 650) {
            throw new InsufficientCreditException("Credit score too low");
        }
        return score;
    })
    .thenApply(score -> accountService.openAccount(customer, PREMIUM))
    .exceptionally(ex -> {
        log.error("Account opening failed", ex);
        return accountService.openAccount(customer, BASIC); // Fallback
    });

Account account = accountFuture.join();
```

### 3.3 Combining Multiple Async Operations

```java
// Parallel account balance updates
List<CompletableFuture<Void>> futures = accounts.stream()
    .map(account -> CompletableFuture.runAsync(() -> {
        BigDecimal interest = calculateInterest(account);
        account.deposit(interest);
        accountRepository.save(account);
    }))
    .collect(Collectors.toList());

// Wait for all to complete
CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
    .join();

System.out.println("All accounts updated!");
```

### 3.4 Error Handling in Async Workflows

```java
CompletableFuture<Transaction> transferFuture = CompletableFuture
    .supplyAsync(() -> {
        // Validate accounts
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient balance");
        }
        return fromAccount;
    })
    .thenCompose(from -> CompletableFuture.supplyAsync(() -> {
        // Perform transfer
        from.withdraw(amount);
        toAccount.deposit(amount);
        return new Transaction(from, toAccount, amount);
    }))
    .handle((result, ex) -> {
        if (ex != null) {
            log.error("Transfer failed", ex);
            // Trigger rollback/compensation
            compensationService.rollback(fromAccount, toAccount, amount);
            throw new TransferFailedException(ex);
        }
        return result;
    });
```

---

## 4. Custom Collectors for Financial Data

### 4.1 Why Custom Collectors?

Built-in collectors are powerful, but finance needs specialized aggregations:

```java
// Built-in collector
Map<String, Long> countByType = transactions.stream()
    .collect(Collectors.groupingBy(
        Transaction::getType,
        Collectors.counting()
    ));

// Custom collector for complex financial stats
TransactionStatistics stats = transactions.stream()
    .collect(new TransactionStatisticsCollector());

System.out.println("Total: " + stats.getTotalAmount());
System.out.println("Average: " + stats.getAverageAmount());
System.out.println("Max: " + stats.getMaxAmount());
System.out.println("Count by type: " + stats.getCountByType());
System.out.println("Sum by currency: " + stats.getSumByCurrency());
```

### 4.2 Building a Custom Collector

```java
public class TransactionStatisticsCollector 
    implements Collector<Transaction, TransactionStats, TransactionStatistics> {
    
    @Override
    public Supplier<TransactionStats> supplier() {
        // Create accumulator
        return TransactionStats::new;
    }
    
    @Override
    public BiConsumer<TransactionStats, Transaction> accumulator() {
        // Add transaction to accumulator
        return (stats, tx) -> {
            stats.incrementCount();
            stats.addAmount(tx.getAmount());
            stats.trackType(tx.getType());
            stats.trackCurrency(tx.getCurrency());
            stats.updateMax(tx.getAmount());
            stats.updateMin(tx.getAmount());
        };
    }
    
    @Override
    public BinaryOperator<TransactionStats> combiner() {
        // Combine two accumulators (for parallel streams)
        return (stats1, stats2) -> {
            stats1.merge(stats2);
            return stats1;
        };
    }
    
    @Override
    public Function<TransactionStats, TransactionStatistics> finisher() {
        // Convert accumulator to final result
        return TransactionStats::toStatistics;
    }
    
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet(); // Not CONCURRENT, not UNORDERED
    }
}
```

### 4.3 Practical Custom Collectors

**Example 1: Partitioning by Multiple Predicates**

```java
// Classify transactions into 3 buckets: SMALL, MEDIUM, LARGE
public class TransactionSizeCollector {
    
    public static Collector<Transaction, ?, Map<SizeCategory, List<Transaction>>> 
    bySizeCategory() {
        return Collectors.groupingBy(tx -> {
            BigDecimal amount = tx.getAmount();
            if (amount.compareTo(new BigDecimal("1000")) <= 0) {
                return SizeCategory.SMALL;
            } else if (amount.compareTo(new BigDecimal("10000")) <= 0) {
                return SizeCategory.MEDIUM;
            } else {
                return SizeCategory.LARGE;
            }
        });
    }
}

// Usage
Map<SizeCategory, List<Transaction>> categorized = transactions.stream()
    .collect(TransactionSizeCollector.bySizeCategory());
```

**Example 2: Top-N Collector**

```java
// Find top 10 accounts by balance
public static <T> Collector<T, ?, List<T>> top(int n, Comparator<T> comparator) {
    return Collector.of(
        () -> new PriorityQueue<>(comparator),
        (queue, item) -> {
            queue.offer(item);
            if (queue.size() > n) {
                queue.poll();
            }
        },
        (queue1, queue2) -> {
            queue1.addAll(queue2);
            while (queue1.size() > n) {
                queue1.poll();
            }
            return queue1;
        },
        queue -> queue.stream()
            .sorted(comparator.reversed())
            .collect(Collectors.toList())
    );
}

// Usage
List<Account> top10 = accounts.stream()
    .collect(top(10, Comparator.comparing(Account::getBalance)));
```

---

## 5. Reactive Spring with Project Reactor

### 5.1 Reactive vs Imperative

**Imperative (blocking)**:
```java
@GetMapping("/customers/{id}")
public Customer getCustomer(@PathVariable Long id) {
    return customerRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
}
```

**Reactive (non-blocking)**:
```java
@GetMapping("/customers/{id}")
public Mono<Customer> getCustomer(@PathVariable Long id) {
    return customerRepository.findById(id)
        .switchIfEmpty(Mono.error(new NotFoundException()));
}
```

### 5.2 Reactive Repository Pattern

```java
public interface ReactiveCustomerRepository 
    extends ReactiveCrudRepository<Customer, Long> {
    
    Mono<Customer> findByEmail(String email);
    
    Flux<Customer> findByStatus(CustomerStatus status);
    
    @Query("SELECT c FROM Customer c WHERE c.createdAt > :since")
    Flux<Customer> findRecentCustomers(LocalDateTime since);
}
```

### 5.3 Reactive Stream Processing

```java
// Process millions of transactions reactively
public Flux<TransactionReport> processTransactions() {
    return transactionRepository.findAll()
        .filter(tx -> tx.getAmount().compareTo(new BigDecimal("1000")) > 0)
        .map(tx -> new TransactionReport(
            tx.getId(),
            tx.getAmount(),
            tx.getStatus()
        ))
        .buffer(100) // Batch for efficiency
        .flatMap(batch -> reportService.generateBatchReport(batch));
}

// Backpressure handling
public Flux<Customer> streamCustomers() {
    return customerRepository.findAll()
        .onBackpressureBuffer(1000) // Buffer up to 1000 items
        .delayElements(Duration.ofMillis(10)); // Rate limiting
}
```

### 5.4 Combining Reactive Streams

```java
public Mono<AccountOpeningResult> openAccountReactive(Long customerId) {
    Mono<Customer> customerMono = customerRepository.findById(customerId);
    Mono<CustomerProfile> profileMono = profileRepository.findByCustomerId(customerId);
    Mono<CreditScore> scoreMono = creditService.getScore(customerId);
    
    return Mono.zip(customerMono, profileMono, scoreMono)
        .flatMap(tuple -> {
            Customer customer = tuple.getT1();
            CustomerProfile profile = tuple.getT2();
            CreditScore score = tuple.getT3();
            
            if (!profile.isKycVerified()) {
                return Mono.error(new KycException("KYC not verified"));
            }
            
            if (score.getValue() < 650) {
                return Mono.error(new CreditException("Insufficient credit"));
            }
            
            return accountService.createAccount(customer);
        });
}
```

---

## 6. Functional Testing Patterns

### 6.1 AssertJ Fluent Assertions

```java
@Test
void shouldFindActiveCustomers() {
    // Given
    Customer active = createCustomer("active@test.com", ACTIVE);
    Customer inactive = createCustomer("inactive@test.com", INACTIVE);
    
    customerRepository.saveAll(List.of(active, inactive));
    
    // When
    List<Customer> result = customerRepository.findActiveCustomers();
    
    // Then - Fluent assertions
    assertThat(result)
        .hasSize(1)
        .first()
        .satisfies(customer -> {
            assertThat(customer.getEmail()).isEqualTo("active@test.com");
            assertThat(customer.getStatus()).isEqualTo(ACTIVE);
            assertThat(customer.isDeleted()).isFalse();
        });
    
    // Alternative: extracting
    assertThat(result)
        .extracting(Customer::getEmail, Customer::getStatus)
        .containsExactly(tuple("active@test.com", ACTIVE));
}
```

### 6.2 Stream-Based Test Data Generation

```java
@BeforeEach
void generateTestData() {
    // Functional test data generation
    List<Customer> customers = IntStream.range(0, 100)
        .mapToObj(i -> Customer.builder()
            .email("customer" + i + "@test.com")
            .firstName("Customer")
            .lastName(String.valueOf(i))
            .status(i % 2 == 0 ? ACTIVE : INACTIVE)
            .build())
        .collect(Collectors.toList());
    
    customerRepository.saveAll(customers);
    
    // Generate accounts for each customer
    List<Account> accounts = customers.stream()
        .flatMap(customer -> IntStream.range(0, 3)
            .mapToObj(i -> Account.builder()
                .accountNumber("ACC-" + customer.getId() + "-" + i)
                .accountType(AccountType.values()[i])
                .balance(new BigDecimal(i * 1000))
                .build()))
        .collect(Collectors.toList());
    
    accountRepository.saveAll(accounts);
}
```

### 6.3 Property-Based Testing

```java
@Property
void transferShouldPreserveTotalBalance(
    @ForAll @BigRange(min = "0", max = "1000000") BigDecimal amount) {
    
    // Given: Two accounts with known balances
    Account from = createAccount(new BigDecimal("1000000"));
    Account to = createAccount(new BigDecimal("500000"));
    
    BigDecimal totalBefore = from.getBalance().add(to.getBalance());
    
    // When: Transfer if sufficient funds
    if (from.getBalance().compareTo(amount) >= 0) {
        accountService.transfer(from.getId(), to.getId(), amount);
    }
    
    // Then: Total balance should remain the same
    Account fromAfter = accountRepository.findById(from.getId()).get();
    Account toAfter = accountRepository.findById(to.getId()).get();
    
    BigDecimal totalAfter = fromAfter.getBalance().add(toAfter.getBalance());
    
    assertThat(totalAfter).isEqualByComparingTo(totalBefore);
}
```

---

## 7. Production Best Practices

### 7.1 Method References vs Lambdas

**When to use method references:**

```java
// ✅ Good: Simple mapping
customers.stream().map(Customer::getEmail)

// ✅ Good: Constructor reference
customers.stream().map(CustomerDTO::new)

// ✅ Good: Static method
amounts.stream().map(BigDecimal::valueOf)

// ❌ Bad: Complex logic (use lambda)
customers.stream().map(Customer::getProfile) // Too simple, but...
// Better as lambda when you need null handling:
customers.stream()
    .map(c -> c.getProfile() != null ? c.getProfile().getPhone() : "N/A")
```

### 7.2 Exception Handling in Streams

```java
// ❌ Bad: Unchecked exceptions break the stream
transactions.stream()
    .map(tx -> externalService.process(tx)) // Throws IOException
    .collect(Collectors.toList());

// ✅ Good: Wrapper method
transactions.stream()
    .map(this::processWithExceptionHandling)
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());

private Optional<Transaction> processWithExceptionHandling(Transaction tx) {
    try {
        return Optional.of(externalService.process(tx));
    } catch (IOException e) {
        log.error("Processing failed for tx: {}", tx.getId(), e);
        return Optional.empty();
    }
}
```

### 7.3 Debugging Stream Pipelines

```java
// Use peek() for debugging
List<String> result = customers.stream()
    .peek(c -> log.debug("Input: {}", c.getEmail()))
    .filter(Customer::isActive)
    .peek(c -> log.debug("After filter: {}", c.getEmail()))
    .map(Customer::getEmail)
    .peek(email -> log.debug("After map: {}", email))
    .collect(Collectors.toList());
```

### 7.4 Performance Profiling

```java
// Measure stream performance
long start = System.currentTimeMillis();

List<Transaction> result = transactions.stream()
    .filter(tx -> tx.getAmount().compareTo(new BigDecimal("1000")) > 0)
    .sorted(Comparator.comparing(Transaction::getCreatedAt).reversed())
    .limit(100)
    .collect(Collectors.toList());

long duration = System.currentTimeMillis() - start;
log.info("Stream processing took {}ms for {} transactions", 
    duration, transactions.size());
```

---

## Summary

### Key Takeaways

1. **Specifications** enable composable queries with type safety
2. **Primitive streams** are 5-10x faster for numeric operations
3. **Parallel streams** work for CPU-bound tasks with >10K elements
4. **CompletableFuture** enables non-blocking async workflows
5. **Custom Collectors** solve complex aggregation requirements
6. **Reactive Streams** handle backpressure in high-throughput systems
7. **Functional testing** with AssertJ improves test readability

### Production Checklist

- ✅ Use primitive streams for financial calculations
- ✅ Profile before using parallel streams
- ✅ Handle exceptions in stream pipelines
- ✅ Leverage Optional for null safety
- ✅ Use CompletableFuture for I/O operations
- ✅ Consider reactive for high-throughput APIs
- ✅ Write fluent assertions for better test readability

### Next Steps

1. Implement Specifications in CustomerRepository
2. Add CompletableFuture to async services
3. Create custom collectors for reporting
4. Add reactive endpoints for streaming data
5. Write functional tests with AssertJ

---

**Remember**: Functional programming is about **declarative pipelines**, **immutability**, and **composition**. When applied correctly in Spring Boot, it leads to more maintainable, testable, and performant applications. 🚀
