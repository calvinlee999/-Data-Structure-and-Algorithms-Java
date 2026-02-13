package com.calvin.java17.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * Immutable Account Record - Java 17 Data-Oriented Programming
 * 
 * Records eliminate 90% of boilerplate code for DTOs and Value Objects.
 * Perfect for domain models that represent immutable state.
 * 
 * FinTech Use Case: Account snapshots, read-only views, audit trails
 */
public record Account(
    String id,
    String customerId,
    String accountNumber,
    AccountType type,
    BigDecimal balance,
    Currency currency,
    AccountStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    /**
     * Compact Constructor - Custom validation
     */
    public Account {
        // Validation: Required fields
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        
        if (accountNumber == null || accountNumber.length() < 10) {
            throw new IllegalArgumentException("Invalid account number");
        }
        
        if (balance == null) {
            throw new IllegalArgumentException("Balance cannot be null");
        }
        
        // Default values
        if (currency == null) {
            currency = Currency.getInstance("USD");
        }
        
        if (status == null) {
            status = AccountStatus.ACTIVE;
        }
        
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }
    
    /**
     * Alternative constructor for new accounts
     */
    public Account(String customerId, String accountNumber, AccountType type) {
        this(
            generateId(),
            customerId,
            accountNumber,
            type,
            BigDecimal.ZERO,
            Currency.getInstance("USD"),
            AccountStatus.ACTIVE,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }
    
    /**
     * Custom method: Check if account has sufficient balance
     */
    public boolean hasSufficientBalance(BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }
    
    /**
     * Custom method: Check if account has minimum balance
     */
    public boolean hasMinimumBalance(BigDecimal minimumBalance) {
        return balance.compareTo(minimumBalance) >= 0;
    }
    
    /**
     * Custom method: Check if account is active
     */
    public boolean isActive() {
        return status == AccountStatus.ACTIVE;
    }
    
    /**
     * Custom method: Check if account is premium (based on balance)
     */
    public boolean isPremium() {
        return balance.compareTo(new BigDecimal("100000")) >= 0;
    }
    
    /**
     * Custom method: Get account tier based on balance
     */
    public String getTier() {
        return switch (this) {
            case Account a when a.balance().compareTo(new BigDecimal("1000000")) >= 0 
                -> "PLATINUM";
            case Account a when a.balance().compareTo(new BigDecimal("100000")) >= 0 
                -> "GOLD";
            case Account a when a.balance().compareTo(new BigDecimal("10000")) >= 0 
                -> "SILVER";
            default 
                -> "STANDARD";
        };
    }
    
    /**
     * Immutable update: Create new account with updated balance
     * Records are immutable, so we create a new instance
     */
    public Account withBalance(BigDecimal newBalance) {
        return new Account(
            id,
            customerId,
            accountNumber,
            type,
            newBalance,
            currency,
            status,
            createdAt,
            LocalDateTime.now()  // Update timestamp
        );
    }
    
    /**
     * Immutable update: Create new account with updated status
     */
    public Account withStatus(AccountStatus newStatus) {
        return new Account(
            id,
            customerId,
            accountNumber,
            type,
            balance,
            currency,
            newStatus,
            createdAt,
            LocalDateTime.now()  // Update timestamp
        );
    }
    
    /**
     * Generate unique account ID
     */
    private static String generateId() {
        return "ACC-" + System.nanoTime();
    }
    
    /**
     * Account Type Enum
     */
    public enum AccountType {
        CHECKING("Checking Account"),
        SAVINGS("Savings Account"),
        MONEY_MARKET("Money Market Account"),
        CD("Certificate of Deposit"),
        INVESTMENT("Investment Account"),
        CREDIT_CARD("Credit Card Account");
        
        private final String displayName;
        
        AccountType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Account Status Enum
     */
    public enum AccountStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        CLOSED,
        PENDING_APPROVAL,
        FROZEN
    }
}
