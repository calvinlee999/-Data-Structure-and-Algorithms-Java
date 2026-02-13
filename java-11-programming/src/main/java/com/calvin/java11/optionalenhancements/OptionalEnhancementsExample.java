package com.calvin.java11.optionalenhancements;

import com.calvin.java11.models.Account;
import com.calvin.java11.models.Payment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

/**
 * Demonstrates Java 11 Optional Enhancements.
 * 
 * New Method:
 * - Optional.isEmpty(): Inverse of isPresent() for cleaner null-safe code
 * 
 * Why it matters:
 * - More readable null-checking logic
 * - Complements isPresent() for symmetry
 * - Improves functional programming style
 * 
 * FinTech Use Cases:
 * - Account validation
 * - Payment lookup
 * - Customer data retrieval
 * - API response handling
 * 
 * @author Calvin Lee
 * @since Java 11
 */
public class OptionalEnhancementsExample {
    
    public static void main(String[] args) {
        System.out.println("=== Java 11: Optional Enhancements ===\n");
        
        OptionalEnhancementsExample example = new OptionalEnhancementsExample();
        
        // 1. isEmpty() vs isPresent()
        example.demonstrateIsEmpty();
        
        // 2. Account validation use case
        example.demonstrateAccountValidation();
        
        // 3. Payment lookup use case        example.demonstratePaymentLookup();
        
        // 4. Comparison: Java 8 vs Java 11
        example.demonstrateComparison();
    }
    
    /**
     * Demonstrates Optional.isEmpty() vs isPresent().
     */
    public void demonstrateIsEmpty() {
        System.out.println("1. Optional.isEmpty() - Basic Usage:\n");
        
        Optional<String> presentValue = Optional.of("Payment Approved");
        Optional<String> emptyValue = Optional.empty();
        
        // Java 8: isPresent()
        System.out.println("   Java 8 (isPresent):");
        System.out.println("   presentValue.isPresent() = " + presentValue.isPresent());  // true
        System.out.println("   emptyValue.isPresent() = " + emptyValue.isPresent());      // false
        
        // Java 11: isEmpty()
        System.out.println("\n   Java 11 (isEmpty):");
        System.out.println("   presentValue.isEmpty() = " + presentValue.isEmpty());      // false
        System.out.println("   emptyValue.isEmpty() = " + emptyValue.isEmpty());          // true
        
        System.out.println("\n   ✅ isEmpty() is the inverse of isPresent()");
        System.out.println("   📊 Benefit: More readable negative checks\n");
    }
    
    /**
     * Account validation use case with isEmpty().
     */
    public void demonstrateAccountValidation() {
        System.out.println("2. Account Validation Use Case:\n");
        
        String[] customerIds = {"CUST-123", "CUST-999"};  // 999 doesn't exist
        
        for (String customerId : customerIds) {
            Optional<Account> account = findAccount(customerId);
            
            // Java 8 way (less readable)
            System.out.println("   ❌ Java 8 way:");
            if (!account.isPresent()) {
                System.out.println("      Account not found for customer: " + customerId);
            } else {
                System.out.println("      Account found: " + account.get().getId());
            }
            
            // Java 11 way (more readable)
            System.out.println("\n   ✅ Java 11 way:");
            if (account.isEmpty()) {  // Reads naturally: "if account is empty"
                System.out.println("      Account not found for customer: " + customerId);
            } else {
                System.out.println("      Account found: " + account.get().getId());
            }
            
            System.out.println();
        }
        
        System.out.println("   📊 Impact: isEmpty() reads more naturally than !isPresent()\n");
    }
    
    /**
     * Payment lookup use case with isEmpty().
     */
    public void demonstratePaymentLookup() {
        System.out.println("3. Payment Lookup Use Case:\n");
        
        String[] paymentIds = {"PAY-001", "PAY-999"};
        
        for (String paymentId : paymentIds) {
            Optional<Payment> payment = findPayment(paymentId);
            
            // Functional style with isEmpty()
            String result = payment
                .map(p -> "Payment found: " + p.getId() + " - $" + p.getAmount())
                .orElse("Payment not found: " + paymentId);
            
            System.out.println("   " + result);
            
            // Validation with isEmpty()
            if (payment.isEmpty()) {
                System.out.println("      ⚠️  Action required: Investigate missing payment");
            } else {
                System.out.println("      ✅ Payment validated successfully");
            }
            
            System.out.println();
        }
        
        System.out.println("   📊 Impact: Cleaner validation logic\n");
    }
    
    /**
     * Comparison: Java 8 vs Java 11 for negative checks.
     */
    public void demonstrateComparison() {
        System.out.println("4. Comparison: Java 8 vs Java 11:\n");
        
        Optional<Account> account = findAccount("CUST-999");  // Empty
        
        System.out.println("   Scenario: Check if account does NOT exist\n");
        
        System.out.println("   ❌ Java 8 (less readable):");
        System.out.println("       if (!account.isPresent()) {");
        System.out.println("           throw new AccountNotFoundException();");
        System.out.println("       }");
        
        System.out.println("\n   ✅ Java 11 (more readable):");
        System.out.println("       if (account.isEmpty()) {  // Reads naturally");
        System.out.println("           throw new AccountNotFoundException();");
        System.out.println("       }");
        
        System.out.println("\n   📊 Comparison:");
        System.out.println("      - Java 8: Double negation (!isPresent) - cognitive overhead");
        System.out.println("      - Java 11: Direct check (isEmpty) - clear intent");
        System.out.println("\n   🎯 Best Practice: Use isEmpty() for negative checks\n");
    }
    
    /**
     * Find account by customer ID (simulated database lookup).
     */
    private Optional<Account> findAccount(String customerId) {
        if ("CUST-123".equals(customerId)) {
            return Optional.of(new Account(
                "ACC-001",
                customerId,
                "1234567890",
                Account.AccountType.CHECKING,
                new BigDecimal("15000.00"),
                "USD",
                true
            ));
        }
        return Optional.empty();
    }
    
    /**
     * Find payment by ID (simulated database lookup).
     */
    private Optional<Payment> findPayment(String paymentId) {
        if ("PAY-001".equals(paymentId)) {
            return Optional.of(new Payment(
                paymentId,
                "CUST-123",
                new BigDecimal("5000.00"),
                "USD",
                Payment.PaymentStatus.COMPLETED,
                Instant.now(),
                "MERCHANT-789",
                "Payment for invoice #12345"
            ));
        }
        return Optional.empty();
    }
}
