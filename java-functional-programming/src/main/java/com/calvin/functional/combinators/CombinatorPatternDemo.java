package com.calvin.functional;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Combinator Pattern - Building Complex Logic from Simple Functions
 * 
 * The combinator pattern builds complex validators and operations by combining
 * simple, reusable functions. Think of it like LEGO blocks - small pieces combine
 * to build something complex.
 */
public class CombinatorPatternDemo {

    record Payment(String id, double amount, String account, String userType) {}
    record ValidationResult(boolean valid, String message) {}

    // ==============================================================================
    // 1. BASIC PREDICATE COMBINATION - Chaining validators
    // ==============================================================================
    
    static class BasicCombinatorExample {
        public static void demonstrate() {
            System.out.println("\n=== BASIC COMBINATOR: Predicate Chaining ===");
            System.out.println("Combine simple checks into complex validators\n");
            
            // Simple predicates
            Predicate<Payment> amountValid = p -> p.amount > 0 && p.amount < 100000;
            Predicate<Payment> accountValid = p -> p.account != null && !p.account.isEmpty();
            Predicate<Payment> userTypeValid = p -> 
                List.of("RETAIL", "BUSINESS", "PREMIUM").contains(p.userType);
            
            // Combine them
            Predicate<Payment> isValidPayment = amountValid
                .and(accountValid)
                .and(userTypeValid);
            
            List<Payment> payments = List.of(
                new Payment("P1", 500, "ACC001", "RETAIL"),
                new Payment("P2", 150000, "ACC002", "BUSINESS"),      // Amount too high
                new Payment("P3", 1000, null, "PREMIUM"),             // No account
                new Payment("P4", -500, "ACC004", "RETAIL"),          // Negative
                new Payment("P5", 2000, "ACC005", "VIP")              // Unknown type
            );
            
            System.out.println("Payment Validation:");
            payments.stream()
                .map(p -> new Object() {
                    String status = isValidPayment.test(p) ? "✅ VALID" : "❌ INVALID";
                })
                .forEach(obj -> System.out.println("  " + obj.status));
        }
    }

    // ==============================================================================
    // 2. FUNCTION COMBINATOR - Chaining transformations
    // ==============================================================================
    
    static class FunctionComboExample {
        public static void demonstrate() {
            System.out.println("\n=== FUNCTION COMBINATOR: Transformation Pipeline ===");
            
            // Simple transformation functions
            Function<Double, Double> applyDiscount = amount -> amount * 0.95;
            Function<Double, Double> applyTax = amount -> amount * 1.08;
            Function<Double, Double> roundToTwo = amount -> 
                Math.round(amount * 100.0) / 100.0;
            
            // Build a price calculator
            Function<Double, Double> priceCalculator = applyDiscount
                .andThen(applyTax)
                .andThen(roundToTwo);
            
            double[] originalPrices = {100.0, 500.0, 1000.0};
            System.out.println("Price Transformation Pipeline:");
            System.out.println("Step 1: Apply 5% discount");
            System.out.println("Step 2: Apply 8% tax");
            System.out.println("Step 3: Round to 2 decimals\n");
            
            for (double price : originalPrices) {
                double finalPrice = priceCalculator.apply(price);
                System.out.println("$" + price + " → $" + finalPrice);
            }
        }
    }

    // ==============================================================================
    // 3. RESULT MONAD - Safe error handling with functional style
    // ==============================================================================
    
    static class ResultComboExample {
        
        // Result type: Success<T> or Failure<T>
        abstract static class Result<T> {
            abstract <R> Result<R> map(Function<T, R> f);
            abstract <R> Result<R> flatMap(Function<T, Result<R>> f);
            abstract T orElse(T defaultValue);
            abstract void ifPresent(Consumer<T> consumer);
        }
        
        static class Success<T> extends Result<T> {
            private final T value;
            
            Success(T value) { this.value = value; }
            
            @Override
            <R> Result<R> map(Function<T, R> f) {
                return new Success<>(f.apply(value));
            }
            
            @Override
            <R> Result<R> flatMap(Function<T, Result<R>> f) {
                return f.apply(value);
            }
            
            @Override
            T orElse(T defaultValue) {
                return value;
            }
            
            @Override
            void ifPresent(Consumer<T> consumer) {
                consumer.accept(value);
            }
            
            @Override
            public String toString() { return "✅ " + value; }
        }
        
        static class Failure<T> extends Result<T> {
            private final String error;
            
            Failure(String error) { this.error = error; }
            
            @Override
            <R> Result<R> map(Function<T, R> f) {
                return new Failure<>(error);
            }
            
            @Override
            <R> Result<R> flatMap(Function<T, Result<R>> f) {
                return new Failure<>(error);
            }
            
            @Override
            T orElse(T defaultValue) {
                return defaultValue;
            }
            
            @Override
            void ifPresent(Consumer<T> consumer) {
                // Do nothing, error case
            }
            
            @Override
            public String toString() { return "❌ " + error; }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== RESULT COMBINATOR: Error Handling ===");
            
            // Validation functions returning Result
            Function<Payment, Result<String>> validateAmount = p -> {
                if (p.amount <= 0 || p.amount > 100000) {
                    return new Failure<>("Amount must be between $0 and $100,000");
                }
                return new Success<>("Amount valid");
            };
            
            Function<Payment, Result<String>> validateAccount = p -> {
                if (p.account == null || p.account.isEmpty()) {
                    return new Failure<>("Account required");
                }
                return new Success<>("Account valid");
            };
            
            Function<Payment, Result<String>> validateType = p -> {
                if (!List.of("RETAIL", "BUSINESS").contains(p.userType)) {
                    return new Failure<>("Unknown user type");
                }
                return new Success<>("Type valid");
            };
            
            // Validation pipeline
            System.out.println("Payment Validation with Error Messages:");
            List<Payment> payments = List.of(
                new Payment("P1", 500, "ACC001", "RETAIL"),
                new Payment("P2", 150000, "ACC002", "BUSINESS"),
                new Payment("P3", 1000, null, "PREMIUM")
            );
            
            payments.forEach(p -> {
                Result<String> result = validateAmount.apply(p)
                    .flatMap(_ -> validateAccount.apply(p))
                    .flatMap(_ -> validateType.apply(p));
                
                System.out.println("  Payment " + p.id + ": " + result);
            });
        }
    }

    // ==============================================================================
    // 4. TRANSACTION PROCESSOR - Real-world combinator
    // ==============================================================================
    
    static class TransactionProcessorExample {
        public static void demonstrate() {
            System.out.println("\n=== TRANSACTION PROCESSOR: Real-World Combinator ===");
            
            // Simple checker functions
            Predicate<Payment> checkAmount = p -> p.amount > 0 && p.amount < 100000;
            Predicate<Payment> checkAccount = p -> p.account != null && p.account.startsWith("ACC");
            Predicate<Payment> checkUserType = p -> 
                List.of("RETAIL", "BUSINESS", "PREMIUM").contains(p.userType);
            
            // Combine into transaction processor
            Predicate<Payment> processTransaction = checkAmount
                .and(checkAccount)
                .and(checkUserType)
                .and(p -> {
                    // Additional business logic
                    if (p.userType.equals("PREMIUM") && p.amount > 10000) {
                        System.out.println("    [COMPLIANCE] High-value premium transaction flagged");
                    }
                    return true;
                });
            
            List<Payment> transactions = List.of(
                new Payment("P1", 500, "ACC001", "RETAIL"),
                new Payment("P2", 50000, "ACC002", "PREMIUM"),
                new Payment("P3", 1000, "INVALID", "BUSINESS"),
                new Payment("P4", 200000, "ACC004", "RETAIL")
            );
            
            System.out.println("Processing " + transactions.size() + " transactions:\n");
            transactions.parallelStream()
                .forEach(p -> {
                    String result = processTransaction.test(p) ? "✅ APPROVED" : "❌ REJECTED";
                    System.out.println("  " + p.id + " ($" + p.amount + "): " + result);
                });
        }
    }

    // ==============================================================================
    // MAIN: Run all demonstrations
    // ==============================================================================
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  COMBINATOR PATTERN - Building Complex from Simple            ║");
        System.out.println("║  Compose Reusable Functions into Powerful Validators          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        BasicCombinatorExample.demonstrate();
        FunctionComboExample.demonstrate();
        ResultComboExample.demonstrate();
        TransactionProcessorExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY INSIGHT: Small, composable functions are powerful         ║");
        System.out.println("║  They're easy to test, reuse, and understand individually      ║");
        System.out.println("║  Together they create complex, flexible business logic         ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
