package com.calvin.fintech.domain;

/**
 * Sealed Payment Request Interface
 * 
 * Java 21 Feature: Sealed Types
 * - Only specified subtypes are allowed
 * - Creates a closed-world security model
 * - Enables exhaustive pattern matching
 * - Prevents unauthorized subtype injection
 * 
 * Security Impact: Reduces "Logic Injection" vulnerabilities
 */
public sealed interface PaymentRequest 
    permits CreditCardRequest, CryptoRequest, BankTransferRequest {
    
    /**
     * Get payment amount (common to all payment types)
     */
    double amount();
    
    /**
     * Get payment currency
     */
    String currency();
    
    /**
     * Validate the payment request
     * Each implementation defines its own validation rules
     */
    default boolean isValid() {
        return amount() > 0 && currency() != null;
    }
}
