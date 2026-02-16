package com.calvin.fintech.entity;

/**
 * Transaction Type Enum
 * 
 * Defines the types of transactions supported in our system.
 * Using an enum ensures only valid types are used.
 */
public enum TransactionType {
    
    /**
     * Money deposited into account
     * Example: Customer deposits cash into their account
     */
    DEPOSIT("Deposit", "Money added to account"),
    
    /**
     * Money withdrawn from account
     * Example: Customer withdraws cash from ATM
     */
    WITHDRAWAL("Withdrawal", "Money removed from account"),
    
    /**
     * Money transferred between accounts
     * Example: Transfer from checking to savings
     */
    TRANSFER("Transfer", "Money moved between accounts"),
    
    /**
     * Payment to external party
     * Example: Bill payment, merchant payment
     */
    PAYMENT("Payment", "Payment to external party"),
    
    /**
     * Refund of previous transaction
     * Example: Return of goods, service cancellation
     */
    REFUND("Refund", "Return of previous payment"),
    
    /**
     * Fee charged by the bank
     * Example: Monthly maintenance fee, overdraft fee
     */
    FEE("Fee", "Service fee charged");
    
    private final String displayName;
    private final String description;
    
    TransactionType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
}
