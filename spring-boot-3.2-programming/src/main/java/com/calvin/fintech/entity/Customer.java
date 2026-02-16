package com.calvin.fintech.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Customer Entity - Demonstrates Advanced JPA Patterns
 * 
 * This entity shows:
 * - Auditing (@CreatedDate, @LastModifiedDate)
 * - Soft delete pattern
 * - One-to-Many relationship with transactions
 * - One-to-One relationship with profile
 * - Many-to-Many relationship with accounts
 * - Embedded value objects (Address)
 * - Element collections (phone numbers, emails)
 * - Optimistic locking with @Version
 * - Indexes for performance
 * - Caching configuration
 */
@Entity
@Table(name = "customers", indexes = {
    @Index(name = "idx_customer_email", columnList = "email", unique = true),
    @Index(name = "idx_customer_status", columnList = "status"),
    @Index(name = "idx_customer_created_at", columnList = "created_at"),
    @Index(name = "idx_customer_deleted", columnList = "deleted")
})
@EntityListeners(AuditingEntityListener.class)
@Cacheable
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CustomerStatus status = CustomerStatus.ACTIVE;
    
    /**
     * ONE-TO-ONE Relationship
     * 
     * A customer has exactly ONE profile.
     * mappedBy: Indicates the "profile" side owns the relationship
     * cascade: Operations on customer cascade to profile
     * orphanRemoval: Delete profile if removed from customer
     */
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private CustomerProfile profile;
    
    /**
     * ONE-TO-MANY Relationship
     * 
     * A customer has MANY transactions.
     * LAZY fetching prevents loading ALL transactions when loading customer
     * BatchSize: When transactions ARE loaded, load in batches of 25
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @org.hibernate.annotations.BatchSize(size = 25)
    private List<Transaction> transactions = new ArrayList<>();
    
    /**
     * MANY-TO-MANY Relationship
     * 
     * A customer can have MANY accounts (e.g., checking, savings, investment)
     * An account can belong to MANY customers (joint accounts)
     * 
     * @JoinTable creates the join table "customer_accounts"
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "customer_accounts",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    @org.hibernate.annotations.BatchSize(size = 10)
    private Set<Account> accounts = new HashSet<>();
    
    /**
     * EMBEDDED Value Object
     * 
     * Address is not an entity, just a group of related fields.
     * These fields are stored directly in the customers table.
     */
    @Embedded
    private Address billingAddress;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
        @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
        @AttributeOverride(name = "state", column = @Column(name = "shipping_state")),
        @AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zip_code")),
        @AttributeOverride(name = "country", column = @Column(name = "shipping_country"))
    })
    private Address shippingAddress;
    
    /**
     * ELEMENT COLLECTION - Collection of Simple Values
     * 
     * Store list of phone numbers in separate table "customer_phone_numbers"
     * Not entities, just strings stored in their own table
     */
    @ElementCollection
    @CollectionTable(
        name = "customer_phone_numbers",
        joinColumns = @JoinColumn(name = "customer_id")
    )
    @Column(name = "phone_number", length = 20)
    private List<String> phoneNumbers = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(
        name = "customer_emails",
        joinColumns = @JoinColumn(name = "customer_id")
    )
    @Column(name = "email_address", length = 100)
    private Set<String> additionalEmails = new HashSet<>();
    
    /**
     * AUDITING Fields
     * 
     * Automatically populated by Spring Data JPA Auditing
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false, length = 50)
    private String createdBy;
    
    @LastModifiedBy
    @Column(name = "updated_by", nullable = false, length = 50)
    private String updatedBy;
    
    /**
     * SOFT DELETE Pattern
     * 
     * Never physically delete customer records (regulatory requirement)
     * Instead, mark as deleted
     */
    @Column(nullable = false)
    private boolean deleted = false;
    
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    /**
     * OPTIMISTIC LOCKING
     * 
     * Prevents lost updates in concurrent environment
     * Hibernate increments version on each update
     * If version doesn't match, throws OptimisticLockException
     */
    @Version
    private Long version;
    
    // ========================================
    // Helper Methods for Bidirectional Relationships
    // ========================================
    
    /**
     * Add transaction to customer
     * 
     * CRITICAL: Maintain both sides of the relationship!
     * This prevents bugs where one side is set but not the other
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setCustomer(this);
    }
    
    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        transaction.setCustomer(null);
    }
    
    /**
     * Set profile
     * 
     * Also sets the customer reference in the profile
     */
    public void setProfile(CustomerProfile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setCustomer(this);
        }
    }
    
    /**
     * Add account to customer
     * 
     * For many-to-many, update BOTH sides
     */
    public void addAccount(Account account) {
        accounts.add(account);
        account.getCustomers().add(this);
    }
    
    public void removeAccount(Account account) {
        accounts.remove(account);
        account.getCustomers().remove(this);
    }
    
    // ========================================
    // Business Methods
    // ========================================
    
    /**
     * Soft delete customer
     * 
     * Don't use repository.delete() - use this instead
     */
    public void markAsDeleted() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
        this.status = CustomerStatus.INACTIVE;
    }
    
    /**
     * Restore deleted customer
     */
    public void restore() {
        this.deleted = false;
        this.deletedAt = null;
        this.status = CustomerStatus.ACTIVE;
    }
    
    /**
     * Calculate total transaction volume
     */
    public BigDecimal getTotalTransactionVolume() {
        return transactions.stream()
            .filter(t -> t.getStatus() == TransactionStatus.COMPLETED)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Get full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Check if customer is active
     */
    public boolean isActive() {
        return status == CustomerStatus.ACTIVE && !deleted;
    }
    
    // ========================================
    // Getters and Setters
    // ========================================
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public CustomerStatus getStatus() {
        return status;
    }
    
    public void setStatus(CustomerStatus status) {
        this.status = status;
    }
    
    public CustomerProfile getProfile() {
        return profile;
    }
    
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    public Set<Account> getAccounts() {
        return accounts;
    }
    
    public Address getBillingAddress() {
        return billingAddress;
    }
    
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    public Address getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }
    
    public Set<String> getAdditionalEmails() {
        return additionalEmails;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public String getUpdatedBy() {
        return updatedBy;
    }
    
    public boolean isDeleted() {
        return deleted;
    }
    
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
    
    public Long getVersion() {
        return version;
    }
    
    // ========================================
    // equals() and hashCode()
    // ========================================
    
    /**
     * IMPORTANT: For entities, base equals/hashCode on business key (email)
     * NOT on ID (ID is null for new entities)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return email != null && email.equals(customer.email);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + getFullName() + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", deleted=" + deleted +
                '}';
    }
}

/**
 * CustomerStatus Enum
 */
enum CustomerStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    PENDING_VERIFICATION
}
