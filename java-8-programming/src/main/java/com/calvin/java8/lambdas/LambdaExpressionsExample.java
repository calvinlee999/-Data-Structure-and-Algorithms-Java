package com.calvin.java8.lambdas;

import com.calvin.java8.models.Payment;
import com.calvin.java8.models.Transaction;
import com.calvin.java8.models.Customer;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.*;

/**
 * Lambda Expressions comprehensive demonstration for Java 8.
 * 
 * Lambdas revolutionize Java by enabling functional programming:
 * - 85% code reduction vs. anonymous inner classes
 * - Behavior as first-class citizens (pass functions as arguments)
 * - Foundation for Stream API, CompletableFuture, and async programming
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
public class LambdaExpressionsExample {

    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("JAVA 8 LAMBDA EXPRESSIONS - COMPREHENSIVE DEMONSTRATION");
        System.out.println("=".repeat(80));
        System.out.println();

        demonstrateBasicLambdas();
        demonstrateFunctionalInterfaces();
        demonstrateFinTechUseCases();
        demonstrateLambdaVsAnonymousClass();
        demonstrateMethodReferencesPreview();
    }

    /**
     * 1. Basic Lambda Syntax
     */
    private static void demonstrateBasicLambdas() {
        System.out.println("1. BASIC LAMBDA SYNTAX");
        System.out.println("-".repeat(80));

        // No parameters
        Runnable noParams = () -> System.out.println("   No parameters lambda");
        noParams.run();

        // One parameter (parentheses optional)
        Consumer<String> oneParam = message -> System.out.println("   One parameter: " + message);
        oneParam.accept("Hello Lambda!");

        // Multiple parameters
        BiFunction<Integer, Integer, Integer> twoParams = (a, b) -> a + b;
        System.out.println("   Two parameters (5 + 3): " + twoParams.apply(5, 3));

        // Block body (multiple statements)
        Function<Integer, String> blockBody = x -> {
            int square = x * x;
            return "Square of " + x + " is " + square;
        };
        System.out.println("   Block body: " + blockBody.apply(7));

        System.out.println();
    }

    /**
     * 2. Core Functional Interfaces from java.util.function
     */
    private static void demonstrateFunctionalInterfaces() {
        System.out.println("2. CORE FUNCTIONAL INTERFACES");
        System.out.println("-".repeat(80));

        // Predicate<T>: boolean test(T t) - filtering, validation
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("   Predicate - isEven(4): " + isEven.test(4));
        System.out.println("   Predicate - isEven(7): " + isEven.test(7));

        // Function<T,R>: R apply(T t) - transformation
        Function<String, Integer> stringLength = s -> s.length();
        System.out.println("   Function - length('Lambda'): " + stringLength.apply("Lambda"));

        // Consumer<T>: void accept(T t) - side effects
        Consumer<String> printer = s -> System.out.println("   Consumer: " + s);
        printer.accept("Printing a message");

        // Supplier<T>: T get() - lazy initialization, factories
        Supplier<String> uuidGenerator = () -> "TXN-" + UUID.randomUUID().toString().substring(0, 8);
        System.out.println("   Supplier - Generated ID: " + uuidGenerator.get());

        // BiFunction<T,U,R>: R apply(T t, U u) - two parameters
        BiFunction<Integer, Integer, String> divideOrError = (a, b) -> 
            b == 0 ? "Division by zero!" : "Result: " + (a / b);
        System.out.println("   BiFunction - divide(10, 2): " + divideOrError.apply(10, 2));
        System.out.println("   BiFunction - divide(10, 0): " + divideOrError.apply(10, 0));

        System.out.println();
    }

    /**
     * 3. Real-World FinTech Use Cases
     */
    private static void demonstrateFinTechUseCases() {
        System.out.println("3. FINTECH USE CASES");
        System.out.println("-".repeat(80));

        // Create sample payments
        List<Payment> payments = createSamplePayments();

        // Use Case 1: Payment filtering (Predicate)
        System.out.println("   Use Case 1: Filter High-Value Payments (>$5000)");
        Predicate<Payment> isHighValue = p -> p.getAmount().compareTo(new BigDecimal("5000")) > 0;
        
        long highValueCount = payments.stream()
                .filter(isHighValue)
                .count();
        System.out.println("   High-value payments: " + highValueCount + " out of " + payments.size());

        // Use Case 2: Payment transformation (Function)
        System.out.println("\n   Use Case 2: Extract Payment Amounts");
        Function<Payment, BigDecimal> extractAmount = Payment::getAmount;
        
        BigDecimal totalAmount = payments.stream()
                .map(extractAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("   Total payment amount: $" + totalAmount);

        // Use Case 3: Payment notification (Consumer)
        System.out.println("\n   Use Case 3: Send Payment Notifications");
        Consumer<Payment> notifyCustomer = payment -> 
            System.out.println("   [NOTIFICATION] Payment " + payment.getId() + 
                               " of $" + payment.getAmount() + " - Status: " + payment.getStatus());
        
        payments.stream()
                .filter(Payment::isApproved)
                .limit(3)
                .forEach(notifyCustomer);

        // Use Case 4: Risk scoring (BiFunction)
        System.out.println("\n   Use Case 4: Risk Assessment");
        BiFunction<Integer, Double, String> calculateRisk = (creditScore, income) -> {
            double ratio = creditScore / income;
            if (ratio > 0.015) return "LOW_RISK";
            else if (ratio > 0.010) return "MEDIUM_RISK";
            else return "HIGH_RISK";
        };
        
        System.out.println("   Customer A (Credit: 750, Income: 50000): " + 
                           calculateRisk.apply(750, 50000.0));
        System.out.println("   Customer B (Credit: 600, Income: 80000): " + 
                           calculateRisk.apply(600, 80000.0));
        System.out.println("   Customer C (Credit: 500, Income: 35000): " + 
                           calculateRisk.apply(500, 35000.0));

        System.out.println();
    }

    /**
     * 4. Lambda vs. Anonymous Inner Class Comparison
     */
    private static void demonstrateLambdaVsAnonymousClass() {
        System.out.println("4. LAMBDA VS. ANONYMOUS INNER CLASS");
        System.out.println("-".repeat(80));

        // Scenario: Payment processor

        // BEFORE JAVA 8: Anonymous Inner Class (8 lines + verbose)
        System.out.println("   Before Java 8 (Anonymous Inner Class - 8 lines):");
        Consumer<Payment> oldStyle = new Consumer<Payment>() {
            @Override
            public void accept(Payment payment) {
                System.out.println("      Processing: " + payment.getId());
            }
        };
        
        // AFTER JAVA 8: Lambda Expression (1 line!)
        System.out.println("\n   After Java 8 (Lambda Expression - 1 line):");
        Consumer<Payment> newStyle = payment -> 
            System.out.println("      Processing: " + payment.getId());

        // Both do the same thing
        Payment samplePayment = createSamplePayments().get(0);
        oldStyle.accept(samplePayment);
        newStyle.accept(samplePayment);

        System.out.println("\n   Code Reduction: 85% (8 lines → 1 line)");
        System.out.println("   Performance: Lambda ~7.5x faster instantiation (invokedynamic)");
        System.out.println("   Memory: Lambda uses 10x less heap (no .class file created)");

        System.out.println();
    }

    /**
     * 5. Method References Preview (related to lambdas)
     */
    private static void demonstrateMethodReferencesPreview() {
        System.out.println("5. METHOD REFERENCES PREVIEW (Shorthand for Lambdas)");
        System.out.println("-".repeat(80));

        List<Payment> payments = createSamplePayments();

        // Lambda version
        System.out.println("   Lambda version:");
        System.out.println("   payments.stream().filter(p -> p.isApproved()).count()");
        long count1 = payments.stream().filter(p -> p.isApproved()).count();
        System.out.println("   Result: " + count1);

        // Method reference version (cleaner!)
        System.out.println("\n   Method reference version (cleaner):");
        System.out.println("   payments.stream().filter(Payment::isApproved).count()");
        long count2 = payments.stream().filter(Payment::isApproved).count();
        System.out.println("   Result: " + count2);

        System.out.println("\n   Both produce same result, but method reference is more readable!");
        System.out.println("   See MethodReferencesExample.java for full coverage.");

        System.out.println();
    }

    /**
     * Helper: Create sample payments for demonstrations
     */
    private static List<Payment> createSamplePayments() {
        List<Payment> payments = new ArrayList<>();
        
        payments.add(new Payment("PAY-001", "CUST-123", new BigDecimal("2500.00"), "USD",
                Payment.PaymentStatus.APPROVED, Instant.now(), "MERCH-A"));
        payments.add(new Payment("PAY-002", "CUST-456", new BigDecimal("15000.00"), "USD",
                Payment.PaymentStatus.APPROVED, Instant.now(), "MERCH-B"));
        payments.add(new Payment("PAY-003", "CUST-789", new BigDecimal("750.50"), "EUR",
                Payment.PaymentStatus.PENDING, Instant.now(), "MERCH-C"));
        payments.add(new Payment("PAY-004", "CUST-234", new BigDecimal("8500.00"), "USD",
                Payment.PaymentStatus.APPROVED, Instant.now(), "MERCH-D"));
        payments.add(new Payment("PAY-005", "CUST-567", new BigDecimal("3200.00"), "GBP",
                Payment.PaymentStatus.DECLINED, Instant.now(), "MERCH-E"));
        
        return payments;
    }
}
