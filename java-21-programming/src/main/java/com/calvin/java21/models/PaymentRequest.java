package com.calvin.java21.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Payment Request - Immutable record for payment data
 * 
 * Demonstrates Java 21/17 Records with:
 * - Immutability by default
 * - Compact constructor for validation
 * - Derived methods (getters, equals, hashCode, toString)
 * - Pattern matching support (can be deconstructed)
 * 
 * Used with:
 * - Virtual Threads: Process thousands of payment requests concurrently
 * - Record Patterns: Deconstruct for validation logic
 * - Sequenced Collections: Ordered payment history
 * 
 * @author Calvin Lee (FinTech Principal Software Engineer)
 * @since Java 21 (LTS) - September 2023
 */
public record PaymentRequest(
    String paymentId,
    String customerId,
    BigDecimal amount,
    Currency currency,
    PaymentMethod method,
    LocalDateTime timestamp
) {
    
    /**
     * Compact constructor with validation.
     * Executes before field assignment.
     */
    public PaymentRequest {
        Objects.requireNonNull(paymentId, "Payment ID cannot be null");
        Objects.requireNonNull(customerId, "Customer ID cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");
        Objects.requireNonNull(currency, "Currency cannot be null");
        Objects.requireNonNull(method, "Payment method cannot be null");
        Objects.requireNonNull(timestamp, "Timestamp cannot be null");
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        
        if (paymentId.isBlank()) {
            throw new IllegalArgumentException("Payment ID cannot be blank");
        }
        
        if (customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be blank");
        }
    }
    
    /**
     * Named constructor for high-value payments (> $10,000).
     */
    public static PaymentRequest highValue(
        String paymentId,
        String customerId,
        BigDecimal amount,
        Currency currency,
        PaymentMethod method
    ) {
        if (amount.compareTo(new BigDecimal("10000")) <= 0) {
            throw new IllegalArgumentException("High value payments must exceed $10,000");
        }
        return new PaymentRequest(paymentId, customerId, amount, currency, method, LocalDateTime.now());
    }
    
    /**
     * Named constructor for standard payments (≤ $10,000).
     */
    public static PaymentRequest standard(
        String paymentId,
        String customerId,
        BigDecimal amount,
        Currency currency,
        PaymentMethod method
    ) {
        if (amount.compareTo(new BigDecimal("10000")) > 0) {
            throw new IllegalArgumentException("Standard payments cannot exceed $10,000");
        }
        return new PaymentRequest(paymentId, customerId, amount, currency, method, LocalDateTime.now());
    }
    
    /**
     * Check if payment is high-value (> $10,000).
     */
    public boolean isHighValue() {
        return amount.compareTo(new BigDecimal("10000")) > 0;
    }
    
    /**
     * Check if payment is domestic (USD).
     */
    public boolean isDomestic() {
        return currency == Currency.USD;
    }
    
    /**
     * Get payment category based on amount.
     */
    public String getCategory() {
        return switch (this) {
            case PaymentRequest p when p.amount().compareTo(new BigDecimal("10000")) > 0 ->
                "HIGH_VALUE";
            case PaymentRequest p when p.amount().compareTo(new BigDecimal("1000")) > 0 ->
                "MEDIUM_VALUE";
            case PaymentRequest p ->
                "STANDARD_VALUE";
        };
    }
    
    /**
     * Currency enum for payment requests.
     */
    public enum Currency {
        USD("US Dollar"),
        EUR("Euro"),
        GBP("British Pound"),
        JPY("Japanese Yen");
        
        private final String displayName;
        
        Currency(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Payment method enum.
     */
    public enum PaymentMethod {
        CREDIT_CARD,
        DEBIT_CARD,
        BANK_TRANSFER,
        CRYPTO,
        WIRE_TRANSFER
    }
    
    /**
     * Example usage demonstrating Java 21 features.
     */
    public static void main(String[] args) {
        System.out.println("=== PaymentRequest Model (Java 21) ===\n");
        
        // Create payment requests
        PaymentRequest payment1 = new PaymentRequest(
            "PAY-001",
            "CUST-123",
            new BigDecimal("5000.00"),
            Currency.USD,
            PaymentMethod.CREDIT_CARD,
            LocalDateTime.now()
        );
        
        PaymentRequest payment2 = PaymentRequest.highValue(
            "PAY-002",
            "CUST-456",
            new BigDecimal("25000.00"),
            Currency.EUR,
            PaymentMethod.WIRE_TRANSFER
        );
        
        // Demonstrate record features
        System.out.println("Payment 1: " + payment1);
        System.out.println("  High Value: " + payment1.isHighValue());
        System.out.println("  Category: " + payment1.getCategory());
        System.out.println("  Domestic: " + payment1.isDomestic());
        
        System.out.println("\nPayment 2: " + payment2);
        System.out.println("  High Value: " + payment2.isHighValue());
        System.out.println("  Category: " + payment2.getCategory());
        System.out.println("  Domestic: " + payment2.isDomestic());
        
        // Demonstrate pattern matching with records
        System.out.println("\nPattern Matching Demonstration:");
        processPayment(payment1);
        processPayment(payment2);
    }
    
    /**
     * Process payment using pattern matching.
     */
    private static void processPayment(PaymentRequest payment) {
        // Record pattern deconstruction
        String message = switch (payment) {
            case PaymentRequest(var id, _, BigDecimal amt, Currency.USD, _, _) 
                when amt.compareTo(new BigDecimal("10000")) > 0 ->
                "High-value domestic payment: " + id + " ($" + amt + ")";
            
            case PaymentRequest(var id, _, BigDecimal amt, Currency.USD, _, _) ->
                "Standard domestic payment: " + id + " ($" + amt + ")";
            
            case PaymentRequest(var id, _, BigDecimal amt, var cur, _, _) ->
                "International payment: " + id + " (" + amt + " " + cur + ")";
        };
        
        System.out.println("  → " + message);
    }
}
