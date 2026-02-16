package com.calvin.fintech.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Transaction Entity
 * 
 * Represents a financial transaction in the database.
 * This is the "blueprint" for how transaction data is stored.
 * 
 * Think of an entity as a row in a database table:
 * - Each field is a column
 * - Each instance is a row
 * 
 * @Entity tells Spring this should be saved to database
 * @Table specifies the table name
 */
@Entity
@Table(name = "transactions", indexes = {
    @Index(name = "idx_customer_id", columnList = "customer_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class Transaction {
    
    /**
     * Primary key - unique ID for each transaction
     * 
     * @Id marks this as the primary key
     * @GeneratedValue means database auto-generates this value
     * IDENTITY strategy: database handles ID generation
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Customer who initiated this transaction
     * 
     * @Column specifies database column details
     * nullable = false means this field is required
     */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    /**
     * Transaction amount
     * 
     * BigDecimal is used for money (NEVER use float/double for money!)
     * precision = 19: total digits (e.g., 99999999999999999.99)
     * scale = 2: digits after decimal point (cents)
     */
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    /**
     * Currency code (USD, EUR, GBP, etc.)
     * 
     * @Column length = 3 for ISO currency codes
     */
    @Column(nullable = false, length = 3)
    private String currency;
    
    /**
     * Type of transaction (DEPOSIT, WITHDRAWAL, TRANSFER, PAYMENT)
     * 
     * @Enumerated(STRING) stores enum name in database (not ordinal number)
     * This is safer if you add more enum values later
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;
    
    /**
     * Current status (PENDING, COMPLETED, FAILED, CANCELLED)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionStatus status;
    
    /**
     * Optional description/memo for the transaction
     */
    @Column(length = 500)
    private String description;
    
    /**
     * Reference number for external systems
     * Used when integrating with payment gateways
     */
    @Column(name = "reference_number", length = 100)
    private String referenceNumber;
    
    /**
     * When this transaction was created
     * 
     * @Column(updatable = false) prevents accidental changes
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    /**
     * When this transaction was last updated
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Soft delete flag (don't actually delete financial records!)
     * In FinTech, we NEVER delete transaction records for audit compliance.
     */
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
    
    /**
     * Version for optimistic locking
     * 
     * Prevents lost updates when multiple users edit same record.
     * Version increments automatically on each update.
     */
    @Version
    private Long version;
    
    /**
     * Default constructor (required by JPA)
     */
    public Transaction() {
    }
    
    /**
     * Constructor with required fields
     */
    public Transaction(Long customerId, BigDecimal amount, String currency, 
                      TransactionType type) {
        this.customerId = customerId;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.status = TransactionStatus.PENDING;  // Default status
        this.deleted = false;
    }
    
    /**
     * Called automatically before inserting to database
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = TransactionStatus.PENDING;
        }
        if (deleted == null) {
            deleted = false;
        }
    }
    
    /**
     * Called automatically before updating in database
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public TransactionStatus getStatus() {
        return status;
    }
    
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getReferenceNumber() {
        return referenceNumber;
    }
    
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Boolean getDeleted() {
        return deleted;
    }
    
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    
    public Long getVersion() {
        return version;
    }
    
    public void setVersion(Long version) {
        this.version = version;
    }
    
    /**
     * Business method: Mark transaction as completed
     */
    public void complete() {
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException(
                "Only PENDING transactions can be completed. Current status: " + this.status
            );
        }
        this.status = TransactionStatus.COMPLETED;
    }
    
    /**
     * Business method: Mark transaction as failed
     */
    public void fail(String reason) {
        if (this.status == TransactionStatus.COMPLETED) {
            throw new IllegalStateException("Cannot fail a COMPLETED transaction");
        }
        this.status = TransactionStatus.FAILED;
        this.description = (this.description != null ? this.description + ". " : "") 
            + "Failure reason: " + reason;
    }
    
    /**
     * Business method: Cancel transaction
     */
    public void cancel() {
        if (this.status == TransactionStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a COMPLETED transaction");
        }
        this.status = TransactionStatus.CANCELLED;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + id +
            ", customerId=" + customerId +
            ", amount=" + amount +
            ", currency='" + currency + '\'' +
            ", type=" + type +
            ", status=" + status +
            ", createdAt=" + createdAt +
            '}';
    }
}
