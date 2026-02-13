package com.calvin.functional.patterns;

import java.util.*;
import java.util.function.*;

/**
 * FUNCTION COMPOSITION PATTERN
 * 
 * Think of function composition like building with LEGO blocks!
 * Small functions snap together to build complex workflows.
 * 
 * Real-world analogy: Like an assembly line - each station does one job,
 * and the product moves through all stations to become complete.
 * 
 * @author FinTech Principal Software Engineer
 */
public class FunctionCompositionPattern {

    record Payment(double amount, String currency) {}
    record Transaction(String id, double amount, String status) {}

    /**
     * PATTERN 1: andThen - Chain functions left to right
     */
    static class AndThenExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: andThen (Left to Right) ===");
            System.out.println("Goal: Process payment through multiple steps\n");
            
            // Individual functions (single responsibility)
            Function<Double, Double> addTax = amount -> amount * 1.08;
            Function<Double, Double> addFee = amount -> amount + 2.50;
            Function<Double, String> formatCurrency = amount -> 
                String.format("$%.2f", amount);
            
            // Compose: tax → fee → format
            Function<Double, String> processPayment = addTax
                .andThen(addFee)
                .andThen(formatCurrency);
            
            double originalAmount = 100.0;
            String finalAmount = processPayment.apply(originalAmount);
            
            System.out.println("  Original: $" + originalAmount);
            System.out.println("  After processing: " + finalAmount);
            System.out.println("\n  Benefits: Clear pipeline, testable steps!");
        }
    }

    /**
     * PATTERN 2: compose - Chain functions right to left
     */
    static class ComposeExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: compose (Right to Left) ===");
            System.out.println("Goal: Data validation pipeline\n");
            
            Function<String, String> trim = String::trim;
            Function<String, String> uppercase = String::toUpperCase;
            Function<String, String> removeSpaces = s -> s.replaceAll("\\s+", "");
            
            // compose: applied right to left
            Function<String, String> cleanInput = removeSpaces
                .compose(uppercase)
                .compose(trim);
            
            String input = "  account 123  ";
            String cleaned = cleanInput.apply(input);
            
            System.out.println("  Input:   '" + input + "'");
            System.out.println("  Output:  '" + cleaned + "'");
            System.out.println("\n  Order: trim → uppercase → removeSpaces");
        }
    }

    /**
     * PATTERN 3: Complex Composition - Multiple steps
     */
    static class ComplexCompositionExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Complex Composition ===");
            System.out.println(" Goal: Full transaction processing pipeline\n");
            
            // Step 1: Validate
            Function<Transaction, Transaction> validate = tx -> {
                if (tx.amount <= 0) throw new IllegalArgumentException("Invalid amount");
                return tx;
            };
            
            // Step 2: Apply discount
            Function<Transaction, Transaction> applyDiscount = tx ->
                new Transaction(tx.id, tx.amount * 0.95, tx.status);
            
            // Step 3: Update status
            Function<Transaction, Transaction> approve = tx ->
                new Transaction(tx.id, tx.amount, "APPROVED");
            
            // Step 4: Log
            Function<Transaction, Transaction> log = tx -> {
                System.out.println("  📝 Processed: " + tx);
                return tx;
            };
            
            // Compose full pipeline
            Function<Transaction, Transaction> processPipeline = validate
                .andThen(applyDiscount)
                .andThen(approve)
                .andThen(log);
            
            Transaction input = new Transaction("TX001", 100.0, "PENDING");
            Transaction result = processPipeline.apply(input);
            
            System.out.println("\n  Benefits: Each step is independent and testable!");
        }
    }

    /**
     * PATTERN 4: BiFunction Composition
     */
    static class BiFunctionCompositionExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: BiFunction Composition ===");
            System.out.println("Goal: Combine two inputs, then transform\n");
            
            // BiFunction: two inputs → one output
            BiFunction<Double, Double, Double> multiply = (a, b) -> a * b;
            
            // Then transform the result
            Function<Double, String> formatResult = amount ->
                "Result: $" + String.format("%.2f", amount);
            
            // Compose: multiply then format
            BiFunction<Double, Double, String> calculateAndFormat =
                multiply.andThen(formatResult);
            
            String result = calculateAndFormat.apply(25.0, 4.0);
            System.out.println("  " + result);
            
            System.out.println("\n  Benefits: Combine multi-input functions!");
        }
    }

    /**
     * PATTERN 5: Predicate Composition
     */
    static class PredicateCompositionExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Predicate Composition ===");
            System.out.println("Goal: Build complex validation rules\n");
            
            record User(String name, int age, double balance) {}
            
            // Simple predicates
            Predicate<User> isAdult = u -> u.age >= 18;
            Predicate<User> hasBalance = u -> u.balance > 0;
            Predicate<User> hasName = u -> u.name != null && !u.name.isEmpty();
            
            // Compose with and()
            Predicate<User> isValidUser = isAdult.and(hasBalance).and(hasName);
            
            List<User> users = List.of(
                new User("Alice", 25, 100.0),
                new User("Bob", 16, 50.0),
                new User("", 30, 200.0)
            );
            
            System.out.println("Valid users:");
            users.stream()
                .filter(isValidUser)
                .forEach(u -> System.out.println("  ✅ " + u.name));
            
            System.out.println("\n  Benefits: Reusable validation logic!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║           FUNCTION COMPOSITION PATTERN                         ║");
        System.out.println("║  Build complex workflows from simple functions                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        AndThenExample.demonstrate();
        ComposeExample.demonstrate();
        ComplexCompositionExample.demonstrate();
        BiFunctionCompositionExample.demonstrate();
        PredicateCompositionExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • andThen(): execute functions left to right (f then g)       ║");
        System.out.println("║  • compose(): execute functions right to left (g then f)       ║");
        System.out.println("║  • Build complex logic from simple, testable pieces            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
