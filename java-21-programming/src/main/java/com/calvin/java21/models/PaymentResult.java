package com.calvin.java21.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Payment Result - Sealed interface for payment outcomes
 * 
 * Demonstrates Java 21/17 Sealed Types with:
 * - Exhaustive pattern matching (compiler ensures all cases handled)
 * - Type-safe payment statuses
 * - Discriminated unions for payment outcomes
 * 
 * Used with:
 * - Pattern Matching: Switch expressions with exhaustiveness checking
 * - Record Patterns: Deconstruct payment results
 * - Virtual Threads: Process results concurrently
 * 
 * @author Calvin Lee (FinTech Principal Software Engineer)
 * @since Java 21 (LTS) - September 2023
 */
public sealed interface PaymentResult 
    permits PaymentResult.Approved, PaymentResult.Rejected, PaymentResult.Pending {
    
    String paymentId();
    LocalDateTime timestamp();
    
    /**
     * Approved payment with settlement details.
     */
    record Approved(
        String paymentId,
        BigDecimal amount,
        String transactionId,
        LocalDateTime timestamp
    ) implements PaymentResult {
        
        public Approved {
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Approved amount must be positive");
            }
        }
    }
    
    /**
     * Rejected payment with reason.
     */
    record Rejected(
        String paymentId,
        String reason,
        RejectionCode code,
        LocalDateTime timestamp
    ) implements PaymentResult {
        
        public enum RejectionCode {
            INSUFFICIENT_FUNDS,
            FRAUD_DETECTED,
            INVALID_ACCOUNT,
            COMPLIANCE_VIOLATION,
            SYSTEM_ERROR
        }
    }
    
    /**
     * Pending payment awaiting review.
     */
    record Pending(
        String paymentId,
        String status,
        String reviewReason,
        LocalDateTime timestamp,
        LocalDateTime expectedResolution
    ) implements PaymentResult {}
    
    /**
     * Get human-readable status message.
     */
    default String getStatusMessage() {
        // Exhaustive pattern matching - compiler ensures all cases handled!
        return switch (this) {
            case Approved(var id, var amt, var txnId, _) ->
                String.format("✓ Approved: %s ($%s) - Transaction: %s", id, amt, txnId);
            
            case Rejected(var id, var reason, var code, _) ->
                String.format("✗ Rejected: %s - %s (%s)", id, reason, code);
            
            case Pending(var id, var status, var reviewReason, _, var expectedTime) ->
                String.format("⏳ Pending: %s - %s (Expected: %s)", id, reviewReason, expectedTime);
        };
    }
    
    /**
     * Check if payment is successful.
     */
    default boolean isSuccessful() {
        return this instanceof Approved;
    }
    
    /**
     * Example usage demonstrating Java 21 features.
     */
    static void main(String[] args) {
        System.out.println("=== PaymentResult Model (Java 21 Sealed Types) ===\n");
        
        // Create different payment results
        PaymentResult[] results = {
            new Approved(
                "PAY-001",
                new BigDecimal("5000.00"),
                "TXN-ABC123",
                LocalDateTime.now()
            ),
            new Rejected(
                "PAY-002",
                "Insufficient funds",
                Rejected.RejectionCode.INSUFFICIENT_FUNDS,
                LocalDateTime.now()
            ),
            new Pending(
                "PAY-003",
                "Under review",
                "High-value transaction requires compliance check",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2)
            ),
            new Rejected(
                "PAY-004",
                "Suspected fraud",
                Rejected.RejectionCode.FRAUD_DETECTED,
                LocalDateTime.now()
            )
        };
        
        // Process results with pattern matching
        System.out.println("Processing payment results:");
        for (PaymentResult result : results) {
            System.out.println("  " + result.getStatusMessage());
            
            // Pattern matching with when guards
            String action = switch (result) {
                case Approved a when a.amount().compareTo(new BigDecimal("10000")) > 0 ->
                    "NOTIFY_COMPLIANCE_HIGH_VALUE";
                
                case Approved _ ->
                    "STANDARD_SETTLEMENT";
                
                case Rejected r when r.code() == Rejected.RejectionCode.FRAUD_DETECTED ->
                    "ESCALATE_TO_SECURITY";
                
                case Rejected _ ->
                    "NOTIFY_CUSTOMER";
                
                case Pending p when p.expectedResolution().isBefore(LocalDateTime.now().plusHours(1)) ->
                    "PRIORITY_REVIEW";
                
                case Pending _ ->
                    "STANDARD_REVIEW_QUEUE";
            };
            
            System.out.println("    → Action: " + action);
        }
        
        // Demonstrate exhaustiveness checking
        System.out.println("\n✓ Compiler ensures all sealed types are handled!");
        System.out.println("✓ No default case needed - exhaustive pattern matching");
        
        // Statistics
        long approved = java.util.Arrays.stream(results)
            .filter(PaymentResult::isSuccessful)
            .count();
        long rejected = java.util.Arrays.stream(results)
            .filter(r -> r instanceof Rejected)
            .count();
        long pending = java.util.Arrays.stream(results)
            .filter(r -> r instanceof Pending)
            .count();
        
        System.out.println("\nResults Summary:");
        System.out.printf("  Approved: %d%n", approved);
        System.out.printf("  Rejected: %d%n", rejected);
        System.out.printf("  Pending: %d%n", pending);
    }
}
