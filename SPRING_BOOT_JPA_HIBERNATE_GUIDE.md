# Spring Boot 3.2 + JPA/Hibernate Deep Dive Guide

**Complete guide for FinTech Principal Engineers**

**Target Audience:** Senior developers who need to master JPA/Hibernate for production systems  
**Difficulty Level:** Intermediate to Advanced  
**Prerequisites:** Java 21, Spring Boot 3.2, SQL fundamentals

---

## Table of Contents

1. [JPA Fundamentals](#1-jpa-fundamentals)
2. [Entity Relationships](#2-entity-relationships)
3. [Advanced Mapping](#3-advanced-mapping)
4. [Query Methods](#4-query-methods)
5. [Transaction Management](#5-transaction-management)
6. [Performance Optimization](#6-performance-optimization)
7. [Caching Strategies](#7-caching-strategies)
8. [Auditing](#8-auditing)
9. [Database Migrations](#9-database-migrations)
10. [Production Best Practices](#10-production-best-practices)

---

## 1. JPA Fundamentals

### What is JPA?

**JPA (Java Persistence API)** is a specification that defines how Java objects should be persisted to relational databases. Think of it as a "contract" that says: "If you follow these rules, your objects can be saved to databases automatically."

**Hibernate** is the most popular **implementation** of the JPA specification. It's like saying JPA is the blueprint, and Hibernate is the actual building.

### Key Concepts

```java
// An "Entity" is a Java object that gets saved to a database table
@Entity  // This annotation tells JPA: "Save this class to a database"
@Table(name = "customers")  // Optional: specify exact table name
public class Customer {
    
    // Every entity needs a primary key
    @Id  // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment
    private Long id;
    
    // Regular fields become columns
    @Column(name = "full_name", nullable = false, length = 100)
    private String name;
    
    @Column(unique = true)  // No two customers can have same email
    private String email;
    
    // Getters and setters...
}
```

### Entity Lifecycle States

An entity can be in one of 4 states:

```java
// 1. TRANSIENT (NEW) - Not connected to database yet
Customer customer = new Customer();
customer.setName("John Doe");
// Entity exists ONLY in memory, database knows nothing about it

// 2. MANAGED (PERSISTENT) - Connected to database, changes tracked
entityManager.persist(customer);  // Now JPA tracks this object
customer.setEmail("john@example.com");  // JPA sees this change!
// When transaction commits, changes automatically saved

// 3. DETACHED - Was managed, but not anymore
entityManager.detach(customer);  // Disconnect from JPA
customer.setEmail("new@example.com");  // JPA doesn't see this change
// Changes won't be saved unless you re-attach it

// 4. REMOVED - Marked for deletion
entityManager.remove(customer);  // Will be deleted when transaction commits
```

### Basic CRUD Operations

```java
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // JpaRepository provides these methods automatically:
    
    // CREATE
    Customer save(Customer customer);  // Insert or update
    
    // READ
    Optional<Customer> findById(Long id);  // Find by ID
    List<Customer> findAll();  // Get all records
    
    // UPDATE
    // Just modify a managed entity, changes auto-saved:
    Customer customer = customerRepository.findById(1L).orElseThrow();
    customer.setName("New Name");  // Auto-saved when transaction commits!
    
    // DELETE
    void deleteById(Long id);  // Delete by ID
    void delete(Customer customer);  // Delete entity
    
    // COUNT & EXISTS
    long count();  // Total records
    boolean existsById(Long id);  // Check if exists
}
```

---

## 2. Entity Relationships

Understanding relationships is **critical** for FinTech applications. Wrong relationships = slow queries = angry customers.

### @OneToOne - One-to-One Relationship

Used when **one entity is directly related to exactly one other entity**.

**Example:** A customer has exactly one profile, and a profile belongs to exactly one customer.

```java
// Customer.java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    // One customer has one profile
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private CustomerProfile profile;
    
    // Helper method to maintain both sides of relationship
    public void setProfile(CustomerProfile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setCustomer(this);  // Keep both sides in sync!
        }
    }
}

// CustomerProfile.java
@Entity
public class CustomerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    
    // Profile belongs to one customer
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)  // Foreign key column
    private Customer customer;
}
```

**Key Concepts:**
- `mappedBy = "customer"`: Tells JPA that the `customer` field in `CustomerProfile` owns the relationship
- `cascade = CascadeType.ALL`: When you save/delete customer, profile is saved/deleted too
- `orphanRemoval = true`: If profile is removed from customer, delete it from database
- Always update **both sides** of the relationship to avoid bugs!

### @OneToMany & @ManyToOne - Parent-Child Relationship

Used when **one entity has many related entities**.

**Example:** A customer has many transactions.

```java
// Customer.java (PARENT)
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // One customer has MANY transactions
    @OneToMany(
        mappedBy = "customer",  // Field name in Transaction class
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY  // Don't load transactions until accessed
    )
    private List<Transaction> transactions = new ArrayList<>();
    
    // Helper method - ALWAYS use this instead of direct list manipulation!
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setCustomer(this);  // Maintain both sides
    }
    
    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        transaction.setCustomer(null);  // Maintain both sides
    }
}

// Transaction.java (CHILD)
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private BigDecimal amount;
    
    // MANY transactions belong to ONE customer
    @ManyToOne(fetch = FetchType.LAZY)  // LAZY is critical for performance!
    @JoinColumn(name = "customer_id", nullable = false)  // Foreign key
    private Customer customer;
}
```

**Database Schema:**
```sql
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(19,2),
    customer_id BIGINT NOT NULL,  -- Foreign key to customers
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);
```

**Critical Rules:**
1. **Always use `FetchType.LAZY`** on `@ManyToOne` and `@OneToMany`
2. **The "many" side (`@ManyToOne`) owns the relationship** (has the foreign key)
3. **Use helper methods** to maintain both sides of the relationship

### @ManyToMany - Many-to-Many Relationship

Used when **many entities are related to many other entities**.

**Example:** A customer can have many accounts, and an account can have many customers (joint accounts).

```java
// Customer.java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    // MANY customers can have MANY accounts
    @ManyToMany
    @JoinTable(
        name = "customer_accounts",  // Join table name
        joinColumns = @JoinColumn(name = "customer_id"),  // FK to customers
        inverseJoinColumns = @JoinColumn(name = "account_id")  // FK to accounts
    )
    private Set<Account> accounts = new HashSet<>();
    
    // Helper methods
    public void addAccount(Account account) {
        accounts.add(account);
        account.getCustomers().add(this);  // Maintain both sides
    }
    
    public void removeAccount(Account account) {
        accounts.remove(account);
        account.getCustomers().remove(this);  // Maintain both sides
    }
}

// Account.java
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String accountNumber;
    private BigDecimal balance;
    
    // MANY accounts can have MANY customers
    @ManyToMany(mappedBy = "accounts")
    private Set<Customer> customers = new HashSet<>();
}
```

**Database Schema:**
```sql
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20),
    balance DECIMAL(19,2)
);

-- Join table (created automatically by JPA)
CREATE TABLE customer_accounts (
    customer_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    PRIMARY KEY (customer_id, account_id),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);
```

**Important:**
- Use `Set` instead of `List` for `@ManyToMany` (better performance)
- One side must use `mappedBy`, the other defines `@JoinTable`
- Consider creating an explicit join entity if you need extra data (e.g., join date)

### Advanced: Join Entity Pattern

Sometimes you need extra data in the join table. Instead of `@ManyToMany`, create an explicit entity:

```java
// CustomerAccount.java - Explicit join entity
@Entity
public class CustomerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    
    // Extra data that belongs to the relationship!
    private LocalDateTime joinedDate;
    private String role;  // OWNER, CO_OWNER, AUTHORIZED_USER
    private boolean isPrimary;
    
    // Composite key could also be used
}

// Now Customer has:
@OneToMany(mappedBy = "customer")
private List<CustomerAccount> customerAccounts;
```

---

## 3. Advanced Mapping

### @Embeddable - Reusable Value Objects

Used for **grouping related fields** that don't need their own table.

```java
// Address.java - Not an entity, just a value object
@Embeddable
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    
    // No @Id needed - this is not a table!
}

// Customer.java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    // Embed address fields directly into customers table
    @Embedded
    private Address billingAddress;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
        @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
        @AttributeOverride(name = "state", column = @Column(name = "shipping_state")),
        @AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zip")),
        @AttributeOverride(name = "country", column = @Column(name = "shipping_country"))
    })
    private Address shippingAddress;
}
```

**Database Schema:**
```sql
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    -- Billing address fields
    street VARCHAR(200),
    city VARCHAR(100),
    state VARCHAR(50),
    zip_code VARCHAR(20),
    country VARCHAR(50),
    -- Shipping address fields (with prefix)
    shipping_street VARCHAR(200),
    shipping_city VARCHAR(100),
    shipping_state VARCHAR(50),
    shipping_zip VARCHAR(20),
    shipping_country VARCHAR(50)
);
```

### @ElementCollection - Collections of Simple Types

For storing **lists/sets of simple values** (not entities).

```java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Store list of phone numbers in separate table
    @ElementCollection
    @CollectionTable(
        name = "customer_phone_numbers",
        joinColumns = @JoinColumn(name = "customer_id")
    )
    @Column(name = "phone_number")
    private List<String> phoneNumbers = new ArrayList<>();
    
    // Store set of email addresses
    @ElementCollection
    @CollectionTable(name = "customer_emails")
    @Column(name = "email")
    private Set<String> emails = new HashSet<>();
}
```

**Database Schema:**
```sql
CREATE TABLE customer_phone_numbers (
    customer_id BIGINT NOT NULL,
    phone_number VARCHAR(20),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);
```

### Inheritance Strategies

JPA supports 3 strategies for mapping class hierarchies to tables.

#### Strategy 1: SINGLE_TABLE (Default)

**All classes in one table** with a discriminator column.

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String accountNumber;
    protected BigDecimal balance;
}

@Entity
@DiscriminatorValue("SAVINGS")
public class SavingsAccount extends Account {
    private BigDecimal interestRate;
}

@Entity
@DiscriminatorValue("CHECKING")
public class CheckingAccount extends Account {
    private BigDecimal overdraftLimit;
}
```

**Database Schema:**
```sql
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    account_type VARCHAR(31),  -- Discriminator
    account_number VARCHAR(20),
    balance DECIMAL(19,2),
    interest_rate DECIMAL(5,2),  -- Only for SAVINGS
    overdraft_limit DECIMAL(19,2)  -- Only for CHECKING
);
```

**Pros:** Fast queries, simple  
**Cons:** Lots of NULL columns, no NOT NULL constraints on subclass fields

#### Strategy 2: JOINED

**Each class gets its own table**, joined by foreign key.

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String accountNumber;
    protected BigDecimal balance;
}

@Entity
public class SavingsAccount extends Account {
    private BigDecimal interestRate;
}

@Entity
public class CheckingAccount extends Account {
    private BigDecimal overdraftLimit;
}
```

**Database Schema:**
```sql
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20),
    balance DECIMAL(19,2)
);

CREATE TABLE savings_account (
    id BIGINT PRIMARY KEY,
    interest_rate DECIMAL(5,2),
    FOREIGN KEY (id) REFERENCES account(id)
);

CREATE TABLE checking_account (
    id BIGINT PRIMARY KEY,
    overdraft_limit DECIMAL(19,2),
    FOREIGN KEY (id) REFERENCES account(id)
);
```

**Pros:** Normalized, no wasted space, proper constraints  
**Cons:** Requires JOINs (slower queries)

#### Strategy 3: TABLE_PER_CLASS

**Each concrete class gets its own complete table**.

```java
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)  // Must use TABLE strategy!
    private Long id;
    
    private String accountNumber;
    protected BigDecimal balance;
}
```

**Database Schema:**
```sql
-- No account table!

CREATE TABLE savings_account (
    id BIGINT PRIMARY KEY,
    account_number VARCHAR(20),
    balance DECIMAL(19,2),
    interest_rate DECIMAL(5,2)
);

CREATE TABLE checking_account (
    id BIGINT PRIMARY KEY,
    account_number VARCHAR(20),
    balance DECIMAL(19,2),
    overdraft_limit DECIMAL(19,2)
);
```

**Pros:** No joins, no NULLs  
**Cons:** Data duplication, can't use polymorphic queries efficiently

**Recommendation:** Use **JOINED** for FinTech (data integrity > performance)

---

## 4. Query Methods

### Method Naming Conventions

Spring Data JPA creates queries automatically based on method names!

```java
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // Find by single field
    List<Transaction> findByStatus(TransactionStatus status);
    // SQL: SELECT * FROM transactions WHERE status = ?
    
    // Find by multiple fields (AND)
    List<Transaction> findByCustomerIdAndStatus(Long customerId, TransactionStatus status);
    // SQL: SELECT * FROM transactions WHERE customer_id = ? AND status = ?
    
    // Find by multiple fields (OR)
    List<Transaction> findByStatusOrType(TransactionStatus status, TransactionType type);
    // SQL: SELECT * FROM transactions WHERE status = ? OR type = ?
    
    // Greater than / Less than
    List<Transaction> findByAmountGreaterThan(BigDecimal amount);
    List<Transaction> findByAmountLessThanEqual(BigDecimal amount);
    
    // Between
    List<Transaction> findByAmountBetween(BigDecimal min, BigDecimal max);
    List<Transaction> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    // Like / Contains
    List<Transaction> findByDescriptionContaining(String keyword);
    List<Transaction> findByDescriptionStartingWith(String prefix);
    List<Transaction> findByDescriptionEndingWith(String suffix);
    
    // Null checks
    List<Transaction> findByDescriptionIsNull();
    List<Transaction> findByDescriptionIsNotNull();
    
    // Collection operations
    List<Transaction> findByStatusIn(List<TransactionStatus> statuses);
    List<Transaction> findByStatusNotIn(List<TransactionStatus> statuses);
    
    // Ordering
    List<Transaction> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    List<Transaction> findByStatusOrderByAmountAscCreatedAtDesc(TransactionStatus status);
    
    // Limiting results
    List<Transaction> findTop10ByOrderByCreatedAtDesc();
    List<Transaction> findFirst5ByCustomerId(Long customerId);
    
    // Pagination
    Page<Transaction> findByStatus(TransactionStatus status, Pageable pageable);
    
    // Distinct
    List<String> findDistinctCurrencyBy();
    
    // Count
    long countByStatus(TransactionStatus status);
    long countByCustomerIdAndStatus(Long customerId, TransactionStatus status);
    
    // Exists
    boolean existsByReferenceNumber(String referenceNumber);
    
    // Delete
    long deleteByStatus(TransactionStatus status);
    
    // Aggregations (custom queries needed - see below)
}
```

### @Query - JPQL (Java Persistence Query Language)

JPQL is **object-oriented SQL**. You query entities, not tables.

```java
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // Basic JPQL
    @Query("SELECT t FROM Transaction t WHERE t.customer.id = :customerId")
    List<Transaction> findByCustomerId(@Param("customerId") Long customerId);
    
    // Join fetch (solve N+1 problem!)
    @Query("SELECT t FROM Transaction t JOIN FETCH t.customer WHERE t.status = :status")
    List<Transaction> findByStatusWithCustomer(@Param("status") TransactionStatus status);
    
    // Aggregations
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.customer.id = :customerId AND t.status = 'COMPLETED'")
    BigDecimal calculateTotalAmount(@Param("customerId") Long customerId);
    
    @Query("SELECT t.currency, SUM(t.amount) FROM Transaction t GROUP BY t.currency")
    List<Object[]> sumByGroupByCurrency();
    
    // Better: Use projection
    @Query("SELECT new com.calvin.fintech.dto.CurrencyTotal(t.currency, SUM(t.amount)) " +
           "FROM Transaction t GROUP BY t.currency")
    List<CurrencyTotal> getCurrencyTotals();
    
    // Subqueries
    @Query("SELECT t FROM Transaction t WHERE t.amount > " +
           "(SELECT AVG(t2.amount) FROM Transaction t2)")
    List<Transaction> findAboveAverage();
    
    // IN clause with collection
    @Query("SELECT t FROM Transaction t WHERE t.status IN :statuses")
    List<Transaction> findByStatuses(@Param("statuses") List<TransactionStatus> statuses);
    
    // LIKE with pattern
    @Query("SELECT t FROM Transaction t WHERE LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Transaction> searchByDescription(@Param("keyword") String keyword);
    
    // Date range
    @Query("SELECT t FROM Transaction t WHERE t.createdAt BETWEEN :start AND :end")
    List<Transaction> findByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    // Update query
    @Modifying
    @Query("UPDATE Transaction t SET t.status = :newStatus WHERE t.status = :oldStatus")
    int bulkUpdateStatus(@Param("oldStatus") TransactionStatus oldStatus, 
                         @Param("newStatus") TransactionStatus newStatus);
    
    // Delete query
    @Modifying
    @Query("DELETE FROM Transaction t WHERE t.createdAt < :cutoffDate AND t.status = 'COMPLETED'")
    int archiveOldTransactions(@Param("cutoffDate") LocalDateTime cutoffDate);
}
```

### Native SQL Queries

Use when JPQL can't express your query (window functions, CTEs, etc.).

```java
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // Basic native query
    @Query(value = "SELECT * FROM transactions WHERE amount > :amount", nativeQuery = true)
    List<Transaction> findHighValueTransactions(@Param("amount") BigDecimal amount);
    
    // With pagination
    @Query(value = "SELECT * FROM transactions WHERE customer_id = :customerId ORDER BY created_at DESC",
           countQuery = "SELECT COUNT(*) FROM transactions WHERE customer_id = :customerId",
           nativeQuery = true)
    Page<Transaction> findByCustomerIdNative(@Param("customerId") Long customerId, Pageable pageable);
    
    // Window functions (not possible in JPQL!)
    @Query(value = """
        SELECT *, 
               ROW_NUMBER() OVER (PARTITION BY customer_id ORDER BY created_at DESC) as row_num
        FROM transactions
        WHERE customer_id = :customerId
        """, nativeQuery = true)
    List<Transaction> findRecentTransactionsPerCustomer(@Param("customerId") Long customerId);
    
    // CTE (Common Table Expression)
    @Query(value = """
        WITH monthly_totals AS (
            SELECT 
                customer_id,
                DATE_TRUNC('month', created_at) as month,
                SUM(amount) as total
            FROM transactions
            WHERE status = 'COMPLETED'
            GROUP BY customer_id, DATE_TRUNC('month', created_at)
        )
        SELECT * FROM monthly_totals WHERE customer_id = :customerId
        """, nativeQuery = true)
    List<Object[]> getMonthlyTotals(@Param("customerId") Long customerId);
    
    // Projection to DTO
    @Query(value = "SELECT currency as currency, SUM(amount) as total " +
                   "FROM transactions GROUP BY currency", nativeQuery = true)
    List<CurrencyTotalProjection> getCurrencyTotalsNative();
}

// Projection interface
public interface CurrencyTotalProjection {
    String getCurrency();
    BigDecimal getTotal();
}
```

### Specifications (Dynamic Queries)

Build queries dynamically at runtime. Perfect for search forms!

```java
// TransactionSpecification.java
public class TransactionSpecification {
    
    public static Specification<Transaction> hasCustomerId(Long customerId) {
        return (root, query, cb) -> customerId == null ? 
            null : cb.equal(root.get("customer").get("id"), customerId);
    }
    
    public static Specification<Transaction> hasStatus(TransactionStatus status) {
        return (root, query, cb) -> status == null ? 
            null : cb.equal(root.get("status"), status);
    }
    
    public static Specification<Transaction> hasType(TransactionType type) {
        return (root, query, cb) -> type == null ? 
            null : cb.equal(root.get("type"), type);
    }
    
    public static Specification<Transaction> amountBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) {
                return cb.between(root.get("amount"), min, max);
            }
            if (min != null) {
                return cb.greaterThanOrEqualTo(root.get("amount"), min);
            }
            return cb.lessThanOrEqualTo(root.get("amount"), max);
        };
    }
    
    public static Specification<Transaction> createdBetween(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;
            if (start != null && end != null) {
                return cb.between(root.get("createdAt"), start, end);
            }
            if (start != null) {
                return cb.greaterThanOrEqualTo(root.get("createdAt"), start);
            }
            return cb.lessThanOrEqualTo(root.get("createdAt"), end);
        };
    }
    
    public static Specification<Transaction> descriptionContains(String keyword) {
        return (root, query, cb) -> keyword == null ? 
            null : cb.like(cb.lower(root.get("description")), "%" + keyword.toLowerCase() + "%");
    }
}

// Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, 
                                               JpaSpecificationExecutor<Transaction> {
    // JpaSpecificationExecutor provides:
    // - Optional<T> findOne(Specification<T> spec)
    // - List<T> findAll(Specification<T> spec)
    // - Page<T> findAll(Specification<T> spec, Pageable pageable)
    // - long count(Specification<T> spec)
}

// Service usage
@Service
public class TransactionService {
    
    public Page<Transaction> searchTransactions(
            Long customerId,
            TransactionStatus status,
            TransactionType type,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String keyword,
            Pageable pageable) {
        
        // Combine specifications dynamically!
        Specification<Transaction> spec = Specification.where(null);
        
        spec = spec.and(TransactionSpecification.hasCustomerId(customerId));
        spec = spec.and(TransactionSpecification.hasStatus(status));
        spec = spec.and(TransactionSpecification.hasType(type));
        spec = spec.and(TransactionSpecification.amountBetween(minAmount, maxAmount));
        spec = spec.and(TransactionSpecification.createdBetween(startDate, endDate));
        spec = spec.and(TransactionSpecification.descriptionContains(keyword));
        
        return transactionRepository.findAll(spec, pageable);
    }
}
```

---

## 5. Transaction Management

### @Transactional Fundamentals

```java
@Service
public class TransactionService {
    
    // Default: Read-write transaction, rollback on RuntimeException
    @Transactional
    public void transferMoney(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Account from = accountRepository.findById(fromAccountId).orElseThrow();
        Account to = accountRepository.findById(toAccountId).orElseThrow();
        
        from.withdraw(amount);  // If this throws exception...
        to.deposit(amount);     // ...this won't execute, and withdraw is rolled back!
        
        // Both saves happen together or not at all (ACID)
        accountRepository.save(from);
        accountRepository.save(to);
    }
    
    // Read-only optimization (no flush, no dirty checking)
    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    
    // Rollback on checked exceptions too
    @Transactional(rollbackFor = Exception.class)
    public void processPayment(Payment payment) throws PaymentException {
        // Will rollback even for checked exceptions
    }
    
    // Don't rollback on specific exceptions
    @Transactional(noRollbackFor = InsufficientFundsException.class)
    public void attemptPayment(Payment payment) {
        // Won't rollback if InsufficientFundsException thrown
    }
    
    // Custom timeout (in seconds)
    @Transactional(timeout = 30)
    public void longRunningOperation() {
        // Will rollback if takes > 30 seconds
    }
    
    // Isolation levels
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void readCommittedOperation() {
        // Prevents dirty reads
    }
    
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void serializableOperation() {
        // Highest isolation, slowest performance
    }
}
```

### Propagation Levels

```java
@Service
public class OrderService {
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private NotificationService notificationService;
    
    // REQUIRED (default): Join existing transaction or create new one
    @Transactional
    public void processOrder(Order order) {
        saveOrder(order);
        paymentService.processPayment(order);  // Joins this transaction
        // If payment fails, order is rolled back too
    }
    
    // REQUIRES_NEW: Always create new transaction, suspend current one
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void auditLog(String message) {
        // Always commits, even if calling transaction rolls back!
        // Use for audit logs that should persist even on errors
    }
    
    // SUPPORTS: Join transaction if exists, otherwise execute non-transactionally
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Order> findOrders() {
        // If called from @Transactional method: joins transaction
        // If called standalone: no transaction
    }
    
    // NOT_SUPPORTED: Execute non-transactionally, suspend current transaction
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendEmailNotification(String email) {
        // Email sending doesn't need transaction
    }
    
    // MANDATORY: Must be called within existing transaction
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        // Throws exception if not called from @Transactional method
    }
    
    // NEVER: Must not be called within transaction
    @Transactional(propagation = Propagation.NEVER)
    public void externalApiCall() {
        // Throws exception if called from @Transactional method
    }
    
    // NESTED: Execute within nested transaction if supported
    @Transactional(propagation = Propagation.NESTED)
    public void saveOrderItems(List<OrderItem> items) {
        // Can rollback independently from parent transaction
        // Requires JDBC 3.0 savepoints
    }
}

// Example: Order with independent audit log
@Service
public class OrderService {
    
    @Autowired
    private AuditService auditService;
    
    @Transactional
    public void processOrder(Order order) {
        try {
            saveOrder(order);
            processPayment(order);
            // ... other operations
            
            auditService.logSuccess(order);  // REQUIRES_NEW - commits even if order fails later
            
        } catch (Exception e) {
            auditService.logFailure(order, e);  // REQUIRES_NEW - always commits
            throw e;  // Re-throw to rollback order
        }
    }
}
```

### Common Transaction Pitfalls

```java
@Service
public class TransactionPitfalls {
    
    // ❌ WRONG: Self-invocation doesn't work!
    @Transactional
    public void methodA() {
        // This call bypasses Spring proxy - no transaction!
        methodB();
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void methodB() {
        // Expected new transaction, but won't get one due to self-invocation
    }
    
    // ✅ CORRECT: Inject self or use separate service
    @Autowired
    private TransactionService self;  // Inject proxy
    
    @Transactional
    public void methodACorrect() {
        self.methodB();  // Now REQUIRES_NEW works!
    }
    
    // ❌ WRONG: Catching exception without re-throwing
    @Transactional
    public void wrongExceptionHandling() {
        try {
            dangerousOperation();
        } catch (RuntimeException e) {
            log.error("Error", e);
            // Transaction won't rollback!
        }
    }
    
    // ✅ CORRECT: Re-throw or mark for rollback
    @Transactional
    public void correctExceptionHandling() {
        try {
            dangerousOperation();
        } catch (RuntimeException e) {
            log.error("Error", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // Now transaction will rollback
        }
    }
}
```

---

## 6. Performance Optimization

### The N+1 Query Problem

**Most common JPA performance issue!**

```java
// ❌ WRONG: N+1 queries
@Transactional(readOnly = true)
public List<Transaction> getAllWithCustomers() {
    List<Transaction> transactions = transactionRepository.findAll();  // 1 query
    
    for (Transaction tx : transactions) {
        String name = tx.getCustomer().getName();  // N additional queries!
        // Each access to customer triggers SELECT
    }
    
    return transactions;
}
// Total: 1 + N queries (if 100 transactions = 101 queries!)
```

**Solution 1: JOIN FETCH**

```java
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // ✅ CORRECT: Single query with JOIN
    @Query("SELECT t FROM Transaction t JOIN FETCH t.customer")
    List<Transaction> findAllWithCustomers();
    
    // For collections, use DISTINCT
    @Query("SELECT DISTINCT c FROM Customer c JOIN FETCH c.transactions")
    List<Customer> findAllWithTransactions();
}
```

**Solution 2: Entity Graph**

```java
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    @EntityGraph(attributePaths = {"customer"})
    List<Transaction> findAll();
    
    // Multiple relationships
    @EntityGraph(attributePaths = {"customer", "account"})
    List<Transaction> findByStatus(TransactionStatus status);
}

// Or define named entity graph on entity
@Entity
@NamedEntityGraph(
    name = "Transaction.detailed",
    attributeNodes = {
        @NamedAttributeNode("customer"),
        @NamedAttributeNode("account")
    }
)
public class Transaction {
    // ...
}

// Use it
@EntityGraph("Transaction.detailed")
List<Transaction> findAll();
```

**Solution 3: Batch Fetching**

```java
// In application.yml
spring:
  jpa:
    properties:
      hibernate:
        default_batch_fetch_size: 10  # Fetch in batches of 10

// Or per entity
@Entity
public class Customer {
    @OneToMany(mappedBy = "customer")
    @BatchSize(size = 10)  // Fetch transactions in batches
    private List<Transaction> transactions;
}
```

### Fetch Strategies

```java
@Entity
public class Customer {
    
    // LAZY: Load only when accessed (default for @OneToMany, @ManyToMany)
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Transaction> transactions;  // Not loaded unless you call getTransactions()
    
    // EAGER: Load immediately (default for @ManyToOne, @OneToOne)
    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;  // Always loaded with customer
}

// ⚠️ WARNING: Don't use EAGER unless absolutely necessary!
// It can cause performance issues and unexpected queries

// Better: Use JOIN FETCH when you need eager loading
@Query("SELECT c FROM Customer c JOIN FETCH c.transactions WHERE c.id = :id")
Optional<Customer> findByIdWithTransactions(@Param("id") Long id);
```

### Projection - Load Only What You Need

```java
// Instead of loading full entity, load only needed fields

// Interface-based projection
public interface TransactionSummary {
    Long getId();
    BigDecimal getAmount();
    String getStatus();
    
    // Nested projection
    CustomerInfo getCustomer();
    
    interface CustomerInfo {
        String getName();
        String getEmail();
    }
}

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<TransactionSummary> findAllProjectedBy();
}

// Class-based projection (DTO)
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private String status;
    
    public TransactionDTO(Long id, BigDecimal amount, String status) {
        this.id = id;
        this.amount = amount;
        this.status = status;
    }
}

@Query("SELECT new com.calvin.fintech.dto.TransactionDTO(t.id, t.amount, t.status) FROM Transaction t")
List<TransactionDTO> findAllDTOs();

// Dynamic projection
<T> List<T> findByCustomerId(Long customerId, Class<T> type);

// Usage
List<TransactionSummary> summaries = repo.findByCustomerId(1L, TransactionSummary.class);
List<TransactionDTO> dtos = repo.findByCustomerId(1L, TransactionDTO.class);
```

### Pagination Best Practices

```java
@Service
public class TransactionService {
    
    // ✅ CORRECT: Use Pageable
    @Transactional(readOnly = true)
    public Page<Transaction> getTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by(Sort.Direction.DESC, "createdAt"));
        return transactionRepository.findAll(pageable);
    }
    
    // For large datasets, use Slice instead of Page (doesn't count total)
    @Transactional(readOnly = true)
    public Slice<Transaction> getTransactionsSlice(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findAll(pageable);
    }
    
    // For infinite scroll, use keyset pagination (fastest!)
    @Query("SELECT t FROM Transaction t WHERE t.id < :lastId ORDER BY t.id DESC")
    List<Transaction> findNext(@Param("lastId") Long lastId, Pageable pageable);
}
```

### Bulk Operations

```java
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // ❌ WRONG: Loads all entities, updates one by one
    default void updateStatusWrong(TransactionStatus oldStatus, TransactionStatus newStatus) {
        List<Transaction> transactions = findByStatus(oldStatus);
        transactions.forEach(t -> t.setStatus(newStatus));
        saveAll(transactions);  // N UPDATE queries!
    }
    
    // ✅ CORRECT: Single bulk UPDATE
    @Modifying
    @Transactional
    @Query("UPDATE Transaction t SET t.status = :newStatus WHERE t.status = :oldStatus")
    int bulkUpdateStatus(@Param("oldStatus") TransactionStatus oldStatus,
                        @Param("newStatus") TransactionStatus newStatus);
    
    // Bulk delete
    @Modifying
    @Transactional
    @Query("DELETE FROM Transaction t WHERE t.createdAt < :cutoffDate")
    int deleteOldTransactions(@Param("cutoffDate") LocalDateTime cutoffDate);
}

// ⚠️ WARNING: Bulk operations bypass Hibernate cache and lifecycle callbacks!
// After bulk operation, refresh entities:
@Transactional
public void updateAndRefresh() {
    int updated = transactionRepository.bulkUpdateStatus(OLD, NEW);
    entityManager.clear();  // Clear persistence context
}
```

---

## 7. Caching Strategies

### First-Level Cache (Persistence Context)

Enabled automatically, scoped to transaction.

```java
@Transactional
public void demonstrateFirstLevelCache() {
    // First access - hits database
    Transaction tx1 = transactionRepository.findById(1L).orElseThrow();
    
    // Second access - from cache, no query!
    Transaction tx2 = transactionRepository.findById(1L).orElseThrow();
    
    // tx1 == tx2 (same instance!)
    System.out.println(tx1 == tx2);  // true
}
```

### Second-Level Cache (Hibernate Cache)

Shared across transactions/sessions.

```java
// application.yml
spring:
  jpa:
    properties:
      hibernate:
        cache:
          use_second_level_cache: true
          region:
            factory_class: org.hibernate.cache.jcache.JCacheRegionFactory
        javax:
          cache:
            provider: org.ehcache.jsr107.EhcacheCachingProvider
            uri: classpath:ehcache.xml

// Entity caching
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer {
    // Customer entities will be cached
}

// Query caching
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    @Query("SELECT t FROM Transaction t WHERE t.status = :status")
    List<Transaction> findCachedByStatus(@Param("status") TransactionStatus status);
}

// ehcache.xml
<config xmlns="http://www.ehcache.org/v3">
    <cache alias="com.calvin.fintech.entity.Customer">
        <expiry>
            <ttl unit="minutes">10</ttl>
        </expiry>
        <heap unit="entries">1000</heap>
    </cache>
</config>
```

### Spring Cache Abstraction

```java
// Enable caching
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("transactions", "customers");
    }
}

@Service
public class TransactionService {
    
    // Cache result
    @Cacheable(value = "transactions", key = "#id")
    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id).orElseThrow();
        // Subsequent calls with same ID return cached value
    }
    
    // Update cache
    @CachePut(value = "transactions", key = "#transaction.id")
    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    
    // Remove from cache
    @CacheEvict(value = "transactions", key = "#id")
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
    
    // Clear entire cache
    @CacheEvict(value = "transactions", allEntries = true)
    public void clearCache() {
        // Cache cleared
    }
    
    // Conditional caching
    @Cacheable(value = "transactions", condition = "#amount > 1000")
    public List<Transaction> findHighValue(BigDecimal amount) {
        // Only cache if amount > 1000
    }
}
```

---

## 8. Auditing

Track who created/updated entities and when.

```java
// Enable JPA auditing
@Configuration
@EnableJpaAuditing
public class JpaConfig {
    
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            // Get current user from SecurityContext
            return Optional.of(SecurityContextHolder.getContext()
                .getAuthentication()
                .getName());
        };
    }
}

// Base auditable entity
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;
    
    @LastModifiedBy
    @Column(nullable = false)
    private String updatedBy;
    
    // Getters...
}

// Use in entities
@Entity
public class Transaction extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private BigDecimal amount;
    
    // createdAt, updatedAt, createdBy, updatedBy automatically populated!
}
```

---

## 9. Database Migrations

### Flyway

```java
// pom.xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>

// application.yml
spring:
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true

// src/main/resources/db/migration/V1__initial_schema.sql
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    updated_by VARCHAR(50) NOT NULL
);

// src/main/resources/db/migration/V2__add_transactions_table.sql
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE INDEX idx_transactions_customer_id ON transactions(customer_id);
CREATE INDEX idx_transactions_status ON transactions(status);

// Flyway naming convention:
// V{version}__{description}.sql
// V1__initial_schema.sql
// V2__add_transactions.sql
// V3__add_accounts.sql
```

---

## 10. Production Best Practices

### Connection Pooling

```yaml
spring:
  datasource:
    hikari:
      # Pool sizing
      maximum-pool-size: 20        # Max connections
      minimum-idle: 5              # Min idle connections
      
      # Timeouts
      connection-timeout: 30000    # 30 seconds
      idle-timeout: 600000         # 10 minutes
      max-lifetime: 1800000        # 30 minutes
      
      # Health checks
      connection-test-query: SELECT 1
      
      # Leak detection
      leak-detection-threshold: 60000  # 1 minute
      
      # Performance
      auto-commit: false
      
  jpa:
    properties:
      hibernate:
        # Connection handling
        connection:
          provider_disables_autocommit: true
        
        # Statement batching
        jdbc:
          batch_size: 20
          fetch_size: 50
        order_inserts: true
        order_updates: true
        
        # Query optimizations
        query:
          in_clause_parameter_padding: true
          fail_on_pagination_over_collection_fetch: true
```

### Logging & Monitoring

```yaml
logging:
  level:
    # SQL logging
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
    
    # Show slow queries
    org.hibernate.stat: DEBUG

spring:
  jpa:
    properties:
      hibernate:
        # Statistics
        generate_statistics: true
        
        # Slow query logging
        session:
          events:
            log:
              LOG_QUERIES_SLOWER_THAN_MS: 100
```

### Production Configuration

```java
// application-prod.yml
spring:
  jpa:
    hibernate:
      ddl-auto: validate  # NEVER use create/update in production!
    
    show-sql: false  # Disable in production
    
    properties:
      hibernate:
        format_sql: false
        use_sql_comments: false
        
        # Enable caching
        cache:
          use_second_level_cache: true
          use_query_cache: true
        
        # Batch processing
        jdbc:
          batch_size: 50
        order_inserts: true
        order_updates: true
        
        # Performance
        query:
          plan_cache_max_size: 2048
```

### Health Checks

```java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Health health() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return Health.up()
                .withDetail("database", "reachable")
                .build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}
```

---

## Complete FinTech Example

```java
// Account entity with all best practices
@Entity
@Table(
    name = "accounts",
    indexes = {
        @Index(name = "idx_account_number", columnList = "account_number", unique = true),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_customer_id", columnList = "customer_id")
    }
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
public class Account extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
    
    @Column(nullable = false, length = 3)
    private String currency = "USD";
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status = AccountStatus.ACTIVE;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 25)
    private List<Transaction> transactions = new ArrayList<>();
    
    @Version
    private Long version;  // Optimistic locking
    
    // Business methods
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        this.balance = this.balance.add(amount);
    }
    
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
    }
}

// Repository with all patterns
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, 
                                          JpaSpecificationExecutor<Account> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    @EntityGraph(attributePaths = {"customer", "transactions"})
    Optional<Account> findDetailedById(Long id);
    
    @Query("SELECT a FROM Account a JOIN FETCH a.customer WHERE a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumberWithCustomer(@Param("accountNumber") String accountNumber);
    
    @Query("SELECT a.currency, SUM(a.balance) FROM Account a GROUP BY a.currency")
    List<Object[]> getTotalBalanceByCurrency();
    
    @Modifying
    @Query("UPDATE Account a SET a.status = :status WHERE a.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") AccountStatus status);
}

// Service with transaction management
@Service
@Transactional(readOnly = true)
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    
    @Transactional
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        Account from = accountRepository.findByAccountNumber(fromAccountNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + fromAccountNumber));
        
        Account to = accountRepository.findByAccountNumber(toAccountNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + toAccountNumber));
        
        // Business logic
        from.withdraw(amount);
        to.deposit(amount);
        
        // Create transaction records
        Transaction debit = new Transaction();
        debit.setAccount(from);
        debit.setAmount(amount.negate());
        debit.setType(TransactionType.TRANSFER);
        debit.setStatus(TransactionStatus.COMPLETED);
        
        Transaction credit = new Transaction();
        credit.setAccount(to);
        credit.setAmount(amount);
        credit.setType(TransactionType.TRANSFER);
        credit.setStatus(TransactionStatus.COMPLETED);
        
        transactionRepository.saveAll(List.of(debit, credit));
        
        // Changes auto-saved when transaction commits!
    }
}
```

---

## Summary Checklist

✅ **Entities**
- [ ] Use appropriate ID generation strategy
- [ ] Add indexes on frequently queried columns
- [ ] Use `@Version` for optimistic locking
- [ ] Set realistic string lengths
- [ ] Use `@Enumerated(EnumType.STRING)` for enums
- [ ] Use BigDecimal for money
- [ ] Add `@Table(name = "...")` for consistent naming

✅ **Relationships**
- [ ] Always use `LAZY` fetching
- [ ] Use `@JoinColumn` on the owning side
- [ ] Use helper methods to maintain bidirectional relationships
- [ ] Add `cascade` and `orphanRemoval` where appropriate
- [ ] Use `@BatchSize` for collections

✅ **Queries**
- [ ] Use method naming for simple queries
- [ ] Use `@Query` (JPQL) for complex queries
- [ ] Use JOIN FETCH to avoid N+1 problems
- [ ] Use Specifications for dynamic queries
- [ ] Use projections to load only needed fields
- [ ] Use pagination for large result sets

✅ **Transactions**
- [ ] Mark service methods with `@Transactional`
- [ ] Use `readOnly = true` for read operations
- [ ] Choose appropriate propagation level
- [ ] Handle exceptions correctly
- [ ] Avoid self-invocation

✅ **Performance**
- [ ] Enable second-level cache for read-heavy entities
- [ ] Configure connection pooling properly
- [ ] Use batch processing for bulk operations
- [ ] Monitor slow queries
- [ ] Use database migrations (Flyway/Liquibase)

✅ **Production**
- [ ] Use `hibernate.ddl-auto: validate` in production
- [ ] Enable auditing (`@CreatedDate`, `@CreatedBy`)
- [ ] Set up proper logging
- [ ] Configure health checks
- [ ] Test with production-like data volume

---

## Next Steps

1. Review existing Transaction service implementation
2. Add unit tests for repositories (see testing guide)
3. Add integration tests with Testcontainers
4. Implement caching strategy
5. Set up database migrations with Flyway
6. Add performance monitoring
7. Create EvaluationIteration 2 to assess improvements

**Est. Time to Master:** 2-3 weeks of hands-on practice

**Recommended Reading:**
- Spring Data JPA Documentation
- Hibernate User Guide
- High-Performance Java Persistence (Vlad Mihalcea)

---

*This guide demonstrates production-grade JPA/Hibernate usage for FinTech applications. All examples follow industry best practices for security, performance, and maintainability.*
