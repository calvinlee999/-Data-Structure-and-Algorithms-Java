package com.calvin.fintech.domain;

import java.time.Instant;

/**
 * Payment Response (Record)
 * 
 * Immutable response object for all payment types
 * - Transparent data carrier
 * - No getters/setters boilerplate
 * - Thread-safe (immutable)
 */
public record PaymentResponse(
    String transactionId,
    PaymentStatus status,
    String message,
    Instant timestamp,
    double amount,
    String currency,
    PaymentMetadata metadata
) {
    
    public enum PaymentStatus {
        SUCCESS,
        PENDING,
        DECLINED,
        FAILED,
        REQUIRES_VERIFICATION
    }
    
    /**
     * Factory method for successful payment
     */
    public static PaymentResponse success(
            String transactionId, 
            double amount, 
            String currency,
            PaymentMetadata metadata) {
        return new PaymentResponse(
            transactionId,
            PaymentStatus.SUCCESS,
            "Payment processed successfully",
            Instant.now(),
            amount,
            currency,
            metadata
        );
    }
    
    /**
     * Factory method for pending payment
     */
    public static PaymentResponse pending(
            String transactionId, 
            double amount, 
            String currency,
            String reason) {
        return new PaymentResponse(
            transactionId,
            PaymentStatus.PENDING,
            reason,
            Instant.now(),
            amount,
            currency,
            new PaymentMetadata(null, null, null)
        );
    }
    
    /**
     * Factory method for declined payment
     */
    public static PaymentResponse declined(
            String transactionId, 
            double amount, 
            String currency,
            String reason) {
        return new PaymentResponse(
            transactionId,
            PaymentStatus.DECLINED,
            reason,
            Instant.now(),
            amount,
            currency,
            new PaymentMetadata(null, null, null)
        );
    }
    
    /**
     * Factory method for failed payment
     */
    public static PaymentResponse failed(
            String transactionId, 
            double amount, 
            String currency,
            String errorMessage) {
        return new PaymentResponse(
            transactionId,
            PaymentStatus.FAILED,
            errorMessage,
            Instant.now(),
            amount,
            currency,
            new PaymentMetadata(null, null, null)
        );
    }
    
    /**
     * Business method: Check if payment requires manual review
     */
    public boolean requiresManualReview() {
        return status == PaymentStatus.REQUIRES_VERIFICATION
            || (status == PaymentStatus.PENDING && amount > 100000);
    }
}

/**
 * Payment Metadata (Nested Record)
 * 
 * Contains additional information about the payment
 */
record PaymentMetadata(
    String gatewayReference,
    String authorizationCode,
    String processorResponse
) {}
