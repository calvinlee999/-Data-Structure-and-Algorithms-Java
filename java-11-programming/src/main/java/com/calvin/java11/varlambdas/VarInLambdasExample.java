package com.calvin.java11.varlambdas;

import com.calvin.java11.models.Payment;
import com.calvin.java11.models.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Demonstrates Java 11 var in Lambda Parameters (JEP 323).
 * 
 * Key Features:
 * - Use var in lambda parameters to apply annotations
 * - Enables @Nonnull, @Nullable for compile-time safety
 * - Critical for Security & Compliance (Principle 5)
 * 
 * FinTech Use Cases:
 * - Type-safe payment processing with @Nonnull validation
 * - Audit logging with @Audit annotations
 * - Compliance-driven null-safety checks
 * 
 * @author Calvin Lee
 * @since Java 11
 */
public class VarInLambdasExample {
    
    public static void main(String[] args) {
        System.out.println("=== Java 11: var in Lambda Parameters ===\n");
        
        VarInLambdasExample example = new VarInLambdasExample();
        
        // 1. Basic var syntax in lambdas
        example.demonstrateBasicVarSyntax();
        
        // 2. Annotations with var (Security & Compliance)
        example.demonstrateAnnotationsWithVar();
        
        // 3. FinTech use case: Type-safe payment processing
        example.demonstratePaymentProcessing();
        
        // 4. Comparison: With/Without var
        example.demonstrateComparison();
    }
    
    /**
     * Demonstrates basic var syntax in lambda parameters.
     */
    public void demonstrateBasicVarSyntax() {
        System.out.println("1. Basic var Syntax in Lambdas:\n");
        
        // Without var (Java 8)
        BiFunction<String, String, String> concat1 = (String x, String y) -> x + y;
        BiFunction<String, String, String> concat2 = (x, y) -> x + y;  // Type inference
        
        // With var (Java 11) - same as type inference but allows annotations
        BiFunction<String, String, String> concat3 = (var x, var y) -> x + y;
        
        System.out.println("   Result: " + concat3.apply("Hello, ", "World!"));
        System.out.println("   ✅ var syntax works in lambdas\n");
    }
    
    /**
     * Demonstrates annotations with var for compile-time safety.
     * This is the PRIMARY reason to use var in lambdas.
     */
    public void demonstrateAnnotationsWithVar() {
        System.out.println("2. Annotations with var (Security & Compliance):\n");
        
        // Simulate @Nonnull validation (in production, use javax.annotation.Nonnull)
        List<Payment> payments = Arrays.asList(
            createPayment("PAY-001", "CUST-123", "5000.00"),
            createPayment("PAY-002", "CUST-456", "3000.00"),
            null  // Null payment (should be caught by @Nonnull)
        );
        
        System.out.println("   Processing payments with @Nonnull validation:\n");
        
        // With var, you can apply annotations like @Nonnull
        // In real code: payments.forEach((@Nonnull var payment) -> processPayment(payment));
        
        payments.forEach((var payment) -> {
            if (payment != null) {  // Manual null check (simulating @Nonnull)
                System.out.println("   ✅ Processing: " + payment.getId() + " - $" + payment.getAmount());
            } else {
                System.out.println("   ❌ Null payment detected (would be caught by @Nonnull at compile time)");
            }
        });
        
        System.out.println("\n   💡 In production: @Nonnull annotation enforces null-safety at compile time\n");
    }
    
    /**
     * FinTech use case: Type-safe payment processing with audit logging.
     */
    public void demonstratePaymentProcessing() {
        System.out.println("3. FinTech Use Case: Type-Safe Payment Processing:\n");
        
        List<Payment> payments = Arrays.asList(
            createPayment("PAY-001", "CUST-123", "5000.00"),
            createPayment("PAY-002", "CUST-456", "3000.00"),
            createPayment("PAY-003", "CUST-789", "7500.00")
        );
        
        // Validator with var and simulated @Nonnull
        BiFunction<Payment, BigDecimal, Boolean> validatePayment = 
            (var payment, var threshold) -> {
                // payment and threshold are guaranteed non-null (with @Nonnull in production)
                return payment.getAmount().compareTo(threshold) <= 0;
            };
        
        BigDecimal threshold = new BigDecimal("6000.00");
        
        System.out.println("   Validating payments against threshold: $" + threshold + "\n");
        
        payments.forEach((var payment) -> {
            boolean isValid = validatePayment.apply(payment, threshold);
            System.out.println("   " + (isValid ? "✅" : "❌") + " " + 
                payment.getId() + ": $" + payment.getAmount() + 
                (isValid ? " (within threshold)" : " (exceeds threshold)"));
        });
        
        System.out.println("\n   📊 Impact: Type-safe validation prevents runtime errors\n");
    }
    
    /**
     * Comparison: Lambda with/without var annotations.
     */
    public void demonstrateComparison() {
        System.out.println("4. Comparison: With/Without var:\n");
        
        List<Transaction> transactions = Arrays.asList(
            createTransaction("TXN-001", "ACC-123", "1000.00"),
            createTransaction("TXN-002", "ACC-456", "2000.00")
        );
        
        System.out.println("   ❌ Without var (Java 8-10): Cannot use annotations");
        Consumer<Transaction> processor1 = (transaction) -> {
            // Cannot apply @Audit annotation here
            System.out.println("      Processing: " + transaction.getId());
        };
        
        System.out.println("\n   ✅ With var (Java 11+): Can apply annotations");
        Consumer<Transaction> processor2 = (var transaction) -> {
            // Can apply @Audit annotation: (var @Audit transaction)
            System.out.println("      [AUDIT] Processing: " + transaction.getId() + " - $" + transaction.getAmount());
        };
        
        System.out.println();
        transactions.forEach(processor2);
        
        System.out.println("\n   🎯 Key Benefit: Security & Compliance through compile-time validation\n");
    }
    
    /**
     * Create sample payment for demonstrations.
     */
    private Payment createPayment(String id, String customerId, String amount) {
        return new Payment(
            id,
            customerId,
            new BigDecimal(amount),
            "USD",
            Payment.PaymentStatus.PENDING,
            Instant.now(),
            "MERCHANT-789",
            "Sample payment"
        );
    }
    
    /**
     * Create sample transaction for demonstrations.
     */
    private Transaction createTransaction(String id, String accountId, String amount) {
        return new Transaction(
            id,
            accountId,
            new BigDecimal(amount),
            "USD",
            Transaction.TransactionType.PAYMENT,
            LocalDateTime.now(),
            "Sample transaction",
            "US"
        );
    }
}
