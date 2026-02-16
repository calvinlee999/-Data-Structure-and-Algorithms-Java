package com.calvin.fintech.entity;

/**
 * Transaction Status Enum
 * 
 * Tracks the lifecycle of a transaction.
 */
public enum TransactionStatus {
    
    /**
     * Transaction created but not yet processed
     * This is the initial state for all transactions
     */
    PENDING("Pending", "Awaiting processing"),
    
    /**
     * Transaction is being processed
     * Used for long-running operations (like ACH transfers)
     */
    PROCESSING("Processing", "Currently being processed"),
    
    /**
     * Transaction completed successfully
     * Money has been transferred
     */
    COMPLETED("Completed", "Successfully completed"),
    
    /**
     * Transaction failed
     * Could be due to insufficient funds, invalid account, etc.
     */
    FAILED("Failed", "Processing failed"),
    
    /**
     * Transaction cancelled by user or system
     * Cancelled before completion
     */
    CANCELLED("Cancelled", "Cancelled before completion"),
    
    /**
     * Transaction reversed after completion
     * Example: Chargeback, fraud reversal
     */
    REVERSED("Reversed", "Reversed after completion");
    
    private final String displayName;
    private final String description;
    
    TransactionStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Check if transaction is in a final state (won't change)
     */
    public boolean isFinal() {
        return this == COMPLETED || this == FAILED || 
               this == CANCELLED || this == REVERSED;
    }
    
    /**
     * Check if transaction is still active
     */
    public boolean isActive() {
        return this == PENDING || this == PROCESSING;
    }
}
