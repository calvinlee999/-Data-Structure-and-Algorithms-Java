package com.calvin.java21.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction - Record for payment transactions with nested components
 * 
 * Demonstrates Java 21 Record Patterns with:
 * - Nested record deconstruction
 * - Multi-level pattern matching
 * - DDD State & Identity Mesh extraction
 * 
 * Used with:
 * - Record Patterns: Single-step multi-level deconstruction
 * - Pattern Matching: Complex filtering with nested patterns
 * - Sequenced Collections: Ordered transaction history
 * 
 * @author Calvin Lee (FinTech Principal Software Engineer)
 * @since Java 21 (LTS) - September 2023
 */
public record Transaction(
    String id,
    Amount amount,
    Customer customer,
    LocalDateTime timestamp,
    Status status
) {
    
    /**
     * Amount record (value object).
     */
    public record Amount(BigDecimal value, String currency) {
        public Amount {
            if (value.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
            if (currency == null || currency.isBlank()) {
                throw new IllegalArgumentException("Currency cannot be null or blank");
            }
        }
    }
    
    /**
     * Customer record with address.
     */
    public record Customer(String id, String email, Address address) {
        public Customer {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("Customer ID cannot be null or blank");
            }
        }
    }
    
    /**
     * Address record.
     */
    public record Address(String city, String country) {
        public Address {
            if (country == null || country.isBlank()) {
                throw new IllegalArgumentException("Country cannot be null or blank");
            }
        }
    }
    
    /**
     * Transaction status enum.
     */
    public enum Status {
        INITIATED,
        PROCESSING,
        COMPLETED,
        FAILED,
        REFUNDED
    }
    
    /**
     * Compact constructor with validation.
     */
    public Transaction {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or blank");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
    }
    
    /**
     * Check if transaction is high-value (> $10,000).
     */
    public boolean isHighValue() {
        return amount.value().compareTo(new BigDecimal("10000")) > 0;
    }
    
    /**
     * Check if transaction is domestic (USA).
     */
    public boolean isDomestic() {
        return "USA".equals(customer.address().country());
    }
    
    /**
     * Get transaction category.
     */
    public String getCategory() {
        // Record pattern matching with nested deconstruction
        return switch (this) {
            case Transaction(_, Amount(BigDecimal val, _), Customer(_, _, Address(_, "USA")), _, _)
                when val.compareTo(new BigDecimal("10000")) > 0 ->
                "HIGH_VALUE_DOMESTIC";
            
            case Transaction(_, Amount(_, _), Customer(_, _, Address(_, "USA")), _, _) ->
                "STANDARD_DOMESTIC";
            
            case Transaction(_, Amount(BigDecimal val, _), Customer(_, _, Address(_, var country)), _, _)
                when val.compareTo(new BigDecimal("10000")) > 0 && !"USA".equals(country) ->
                "HIGH_VALUE_INTERNATIONAL";
            
            case Transaction(_, _, Customer(_, _, Address(_, var country)), _, _)
                when !"USA".equals(country) ->
                "STANDARD_INTERNATIONAL";
            
            default -> "UNKNOWN";
        };
    }
    
    /**
     * Example usage demonstrating Java 21 record patterns.
     */
    public static void main(String[] args) {
        System.out.println("=== Transaction Model (Java 21 Record Patterns) ===\n");
        
        // Create transactions with nested records
        Transaction txn1 = new Transaction(
            "TXN-001",
            new Amount(new BigDecimal("5000.00"), "USD"),
            new Customer("CUST-123", "john@example.com", new Address("New York", "USA")),
            LocalDateTime.now(),
            Status.COMPLETED
        );
        
        Transaction txn2 = new Transaction(
            "TXN-002",
            new Amount(new BigDecimal("25000.00"), "EUR"),
            new Customer("CUST-456", "alice@example.com", new Address("London", "UK")),
            LocalDateTime.now(),
            Status.PROCESSING
        );
        
        Transaction txn3 = new Transaction(
            "TXN-003",
            new Amount(new BigDecimal("15000.00"), "USD"),
            new Customer("CUST-789", "bob@example.com", new Address("San Francisco", "USA")),
            LocalDateTime.now(),
            Status.COMPLETED
        );
        
        Transaction[] transactions = {txn1, txn2, txn3};
        
        // Demonstrate record pattern deconstruction
        System.out.println("Transaction Analysis:");
        for (Transaction txn : transactions) {
            // Single-step nested deconstruction!
            if (txn instanceof Transaction(
                String id,
                Amount(BigDecimal val, String cur),
                Customer(String custId, String email, Address(String city, String country)),
                LocalDateTime ts,
                Status status
            )) {
                System.out.printf("\nTransaction %s (%s):%n", id, status);
                System.out.printf("  Amount: %s %s%n", val, cur);
                System.out.printf("  Customer: %s (%s)%n", custId, email);
                System.out.printf("  Location: %s, %s%n", city, country);
                System.out.printf("  Category: %s%n", txn.getCategory());
                System.out.printf("  High Value: %s%n", txn.isHighValue());
                System.out.printf("  Domestic: %s%n", txn.isDomestic());
            }
        }
        
        // Demonstrate pattern matching in switch
        System.out.println("\nRoute Determination:");
        for (Transaction txn : transactions) {
            String route = switch (txn) {
                case Transaction(var id, Amount(BigDecimal val, var cur), 
                                Customer(_, _, Address(_, "USA")), _, _)
                    when val.compareTo(new BigDecimal("10000")) > 0 ->
                    "HIGH_VALUE_ROUTE_DOMESTIC";
                
                case Transaction(var id, _, Customer(_, _, Address(_, "USA")), _, _) ->
                    "STANDARD_ROUTE_DOMESTIC";
                
                case Transaction(var id, _, Customer(_, _, Address(_, var country)), _, _) ->
                    "INTERNATIONAL_ROUTE_" + country;
            };
            
            System.out.printf("  %s → %s%n", txn.id(), route);
        }
        
        System.out.println("\n✓ Record Patterns: 90% reduction in intermediate variables");
        System.out.println("✓ Single-step extraction of deeply nested data");
        System.out.println("✓ Self-documenting code structure");
    }
}
