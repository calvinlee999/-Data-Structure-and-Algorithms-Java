package com.calvin.java17.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

/**
 * Immutable Transaction Record - Java 17 Data-Oriented Programming
 * 
 * Records provide a concise syntax for creating classes that are transparent
 * holders for shallowly immutable data. This eliminates 90% of DTO boilerplate.
 * 
 * Automatically provides:
 * - Constructor
 * - Getters (id(), amount(), currency(), timestamp())
 * - equals(), hashCode(), toString()
 * - Immutability (all fields are final)
 * 
 * FinTech Use Case: Audit logs, transaction history, compliance reports
 */
public record Transaction(
    String id,
    BigDecimal amount,
    Currency currency,
    Instant timestamp,
    String accountId,
    String description,
    TransactionType type
) {
    /**
     * Compact Constructor - Custom validation
     * Runs before the canonical constructor assigns fields
     */
    public Transaction {
        // Validation: Amount must be positive
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive, got: " + amount);
        }
        
        // Validation: Required fields
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or empty");
        }
        
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        
        if (timestamp == null) {
            timestamp = Instant.now();  // Default to current time
        }
    }
    
    /**
     * Custom method: Check if transaction is international
     */
    public boolean isInternational() {
        return !currency.getCurrencyCode().equals("USD");
    }
    
    /**
     * Custom method: Check if transaction is high-value
     */
    public boolean isHighValue() {
        return amount.compareTo(new BigDecimal("10000")) > 0;
    }
    
    /**
     * Custom method: Get transaction category for reporting
     */
    public String getCategory() {
        return switch (type) {
            case DEBIT -> "OUTGOING";
            case CREDIT, DEPOSIT -> "INCOMING";
            case TRANSFER -> amount.compareTo(BigDecimal.ZERO) > 0 ? "INCOMING" : "OUTGOING";
            case WITHDRAWAL -> "CASH_OUT";
            case PAYMENT -> "MERCHANT_PAYMENT";
            case REFUND -> "REVERSAL";
        };
    }
    
    /**
     * Transaction Type Enum
     */
    public enum TransactionType {
        DEBIT,
        CREDIT,
        TRANSFER,
        WITHDRAWAL,
        DEPOSIT,
        PAYMENT,
        REFUND
    }
}
