package com.calvin.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Transaction Entity - JPA/Hibernate Domain Model
 * 
 * <p>FinTech Principal Engineer's Guide to Modern JPA with Java 21</p>
 * 
 * <h2>Design Principles</h2>
 * <ul>
 *   <li><b>Immutability:</b> All fields are final (except JPA-required setters)</li>
 *   <li><b>Domain-Driven Design:</b> Rich domain model with business logic</li>
 *   <li><b>Virtual Thread Compatible:</b> No synchronized blocks, stateless methods</li>
 * </ul>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
@Entity
@Table(name = "transactions", indexes = {
    @Index(name = "idx_customer_id", columnList = "customer_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false, unique = true, length = 50)
    private String transactionId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionStatus status;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(length = 500)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version; // Optimistic locking

    // JPA requires no-arg constructor
    protected Transaction() {
    }

    // Builder pattern for immutability
    public Transaction(String transactionId, Long customerId, BigDecimal amount, 
                      TransactionType type, String paymentMethod, String description) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.amount = amount;
        this.type = type;
        this.status = TransactionStatus.PENDING;
        this.paymentMethod = paymentMethod;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic methods (Domain-Driven Design)
    public Transaction approve() {
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException("Can only approve PENDING transactions");
        }
        this.status = TransactionStatus.APPROVED;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    public Transaction reject(String reason) {
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException("Can only reject PENDING transactions");
        }
        this.status = TransactionStatus.REJECTED;
        this.description = (description != null ? description + " | " : "") + "Rejected: " + reason;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    public boolean isHighValue() {
        return amount.compareTo(BigDecimal.valueOf(10000)) > 0;
    }

    // Getters
    public Long getId() { return id; }
    public String getTransactionId() { return transactionId; }
    public Long getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }
    public TransactionStatus getStatus() { return status; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Long getVersion() { return version; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", transactionId='" + transactionId + "', amount=" + amount + "}";
    }
}

/**
 * Transaction Type Enum
 */
enum TransactionType {
    PAYMENT, REFUND, TRANSFER, WITHDRAWAL, DEPOSIT
}

/**
 * Transaction Status Enum
 */
enum TransactionStatus {
    PENDING, APPROVED, REJECTED, COMPLETED, FAILED
}
