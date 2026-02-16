package com.calvin.fintech.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Account Entity - MANY-TO-MANY Relationship
 * 
 * Demonstrates MANY-TO-MANY relationship with Customer.
 * 
 * A customer can have MANY accounts (checking, savings, investment).
 * An account can belong to MANY customers (joint accounts).
 * 
 * This is common in banking:
 * - Joint checking account for married couples
 * - Business accounts with multiple authorized users
 * - Trust accounts with multiple beneficiaries
 */
@Entity
@Table(
    name = "accounts",
    indexes = {
        @Index(name = "idx_account_number", columnList = "account_number", unique = true),
        @Index(name = "idx_account_type", columnList = "account_type"),
        @Index(name = "idx_account_status", columnList = "status")
    }
)
@EntityListeners(AuditingEntityListener.class)
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 20)
    private AccountType accountType;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
    
    @Column(nullable = false, length = 3)
    private String currency = "USD";
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status = AccountStatus.ACTIVE;
    
    /**
     * MANY-TO-MANY relationship
     * 
     * mappedBy: The "accounts" field in Customer owns the relationship
     * This side does NOT define @JoinTable
     */
    @ManyToMany(mappedBy = "accounts", fetch = FetchType.LAZY)
    private Set<Customer> customers = new HashSet<>();
    
    /**
     * Interest rate for savings accounts
     */
    @Column(name = "interest_rate", precision = 5, scale = 4)
    private BigDecimal interestRate;
    
    /**
     * Overdraft limit for checking accounts
     */
    @Column(name = "overdraft_limit", precision = 19, scale = 2)
    private BigDecimal overdraftLimit;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * Optimistic locking
     */
    @Version
    private Long version;
    
    // Constructors
    
    public Account() {
    }
    
    public Account(String accountNumber, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }
    
    // Business methods
    
    /**
     * Deposit money
     */
    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        if (status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        this.balance = this.balance.add(amount);
    }
    
    /**
     * Withdraw money
     */
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
        
        // Check if sufficient funds (considering overdraft if checking account)
        BigDecimal availableBalance = balance;
        if (accountType == AccountType.CHECKING && overdraftLimit != null) {
            availableBalance = availableBalance.add(overdraftLimit);
        }
        
        if (availableBalance.compareTo(amount) < 0) {
            throw new InsufficientFundsException(
                String.format("Insufficient funds. Available: %s, Requested: %s", 
                    availableBalance, amount)
            );
        }
        
        this.balance = this.balance.subtract(amount);
    }
    
    /**
     * Check if account is joint (has multiple customers)
     */
    public boolean isJointAccount() {
        return customers.size() > 1;
    }
    
    /**
     * Get primary customer (first one added)
     */
    public Customer getPrimaryCustomer() {
        return customers.stream().findFirst().orElse(null);
    }
    
    /**
     * Freeze account
     */
    public void freeze() {
        this.status = AccountStatus.FROZEN;
    }
    
    /**
     * Close account
     */
    public void close() {
        if (balance.compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException("Cannot close account with non-zero balance");
        }
        this.status = AccountStatus.CLOSED;
    }
    
    // Getters and setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public AccountType getAccountType() {
        return accountType;
    }
    
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public AccountStatus getStatus() {
        return status;
    }
    
    public void setStatus(AccountStatus status) {
        this.status = status;
    }
    
    public Set<Customer> getCustomers() {
        return customers;
    }
    
    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
    
    public BigDecimal getInterestRate() {
        return interestRate;
    }
    
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
    
    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }
    
    public void setOverdraftLimit(BigDecimal overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public Long getVersion() {
        return version;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return accountNumber != null && accountNumber.equals(account.accountNumber);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", type=" + accountType +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", status=" + status +
                '}';
    }
}

/**
 * AccountType Enum
 */
enum AccountType {
    CHECKING,      // Regular checking account
    SAVINGS,       // Savings account with interest
    INVESTMENT,    // Investment/brokerage account
    CREDIT_CARD,   // Credit card account
    LOAN          // Loan account
}

/**
 * AccountStatus Enum
 */
enum AccountStatus {
    ACTIVE,
    INACTIVE,
    FROZEN,
    CLOSED,
    PENDING_APPROVAL
}

/**
 * InsufficientFundsException
 */
class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
