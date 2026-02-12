package com.calvin.functional;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Functional Interfaces Demonstration - The Tools of Functional Programming
 * 
 * Java provides 40+ built-in functional interfaces. This class demonstrates the most
 * important ones: Predicate, Consumer, Supplier, Function, BiFunction, and comparators.
 * 
 * Each functional interface represents a contract: a function with specific input/output.
 */
public class FunctionalInterfacesDemonstration {

    // ==============================================================================
    // FUNCTIONAL INTERFACES 101
    // ==============================================================================
    
    // Data models
    record Payment(String id, double amount, String account, boolean verified) {}
    record Account(String name, double balance, boolean active) {}
    record User(String id, String name, String role) {}

    // ==============================================================================
    // 1. PREDICATE<T> - Tests if something is true or false (like a filter)
    // ==============================================================================
    
    static class PredicateExample {
        public static void demonstrate() {
            System.out.println("\n=== FUNCTIONAL INTERFACE 1: Predicate<T> ===");
            System.out.println("Purpose: Test if something matches a condition (returns boolean)");
            System.out.println("Analogy: Like a security guard checking if you're on the approved list\n");
            
            // Define predicates
            Predicate<Payment> isHighAmount = p -> p.amount > 10000;
            Predicate<Payment> isVerified = p -> p.verified;
            Predicate<Payment> isValidPayment = isHighAmount.and(isVerified);
            
            // The .and() method chains predicates together
            List<Payment> payments = List.of(
                new Payment("P1", 5000, "ACC001", true),
                new Payment("P2", 15000, "ACC002", true),
                new Payment("P3", 20000, "ACC003", false),
                new Payment("P4", 8000, "ACC004", true)
            );
            
            System.out.println("All payments:");
            payments.forEach(p -> System.out.println("  " + p));
            
            System.out.println("\nFiltered (amount > 10000 AND verified):");
            payments.stream()
                .filter(isValidPayment)
                .forEach(p -> System.out.println("  ✅ " + p));
            
            System.out.println("\nNegation example (NOT verified):");
            payments.stream()
                .filter(isVerified.negate())
                .forEach(p -> System.out.println("  ❌ " + p));
        }
    }

    // ==============================================================================
    // 2. CONSUMER<T> - Does something with data (no return value)
    // ==============================================================================
    
    static class ConsumerExample {
        public static void demonstrate() {
            System.out.println("\n=== FUNCTIONAL INTERFACE 2: Consumer<T> ===");
            System.out.println("Purpose: Perform an action on data (takes input, returns nothing)");
            System.out.println("Analogy: Like a mail carrier delivering a package (takes action)\n");
            
            Consumer<Payment> logPayment = p -> 
                System.out.println("  LOG: Payment " + p.id + " for $" + p.amount);
            
            Consumer<Payment> auditPayment = p ->
                System.out.println("  AUDIT: Verified=" + p.verified + ", Amount=" + p.amount);
            
            // Chain consumers with .andThen()
            Consumer<Payment> completeProcess = logPayment.andThen(auditPayment);
            
            List<Payment> payments = List.of(
                new Payment("P1", 1500, "ACC001", true),
                new Payment("P2", 2500, "ACC002", false)
            );
            
            System.out.println("Processing payments:");
            payments.forEach(completeProcess);
        }
    }

    // ==============================================================================
    // 3. SUPPLIER<T> - Generates or provides data (no input, returns value)
    // ==============================================================================
    
    static class SupplierExample {
        public static void demonstrate() {
            System.out.println("\n=== FUNCTIONAL INTERFACE 3: Supplier<T> ===");
            System.out.println("Purpose: Provide/generate data on demand (no input required)");
            System.out.println("Analogy: Like a vending machine - you press a button, it gives you something\n");
            
            // Supplier for random amounts
            Supplier<Double> randomAmount = () -> Math.random() * 10000;
            
            // Supplier for session IDs
            Supplier<String> sessionId = () -> UUID.randomUUID().toString();
            
            System.out.println("Generated random amounts:");
            for (int i = 0; i < 3; i++) {
                System.out.println("  Amount " + (i + 1) + ": $" + randomAmount.get());
            }
            
            System.out.println("\nGenerated session IDs:");
            for (int i = 0; i < 2; i++) {
                System.out.println("  Session: " + sessionId.get());
            }
            
            // Lazy evaluation example
            System.out.println("\nLazy evaluation (suppliers used with Optional):");
            Optional.empty().orElseSupply(() -> {
                System.out.println("  This only runs if Optional is empty");
                return "Default value";
            });
        }
    }

    // ==============================================================================
    // 4. FUNCTION<T, R> - Transforms data from one type to another
    // ==============================================================================
    
    static class FunctionExample {
        public static void demonstrate() {
            System.out.println("\n=== FUNCTIONAL INTERFACE 4: Function<T, R> ===");
            System.out.println("Purpose: Transform input to output (map data from type A to type B)");
            System.out.println("Analogy: Like a currency converter - takes dollars, gives euros\n");
            
            // Simple transformation functions
            Function<Double, Double> applyDiscount = amount -> amount * 0.9;
            Function<Double, Double> applyTax = amount -> amount * 1.08;
            Function<Double, String> formatCurrency = amount -> "$" + String.format("%.2f", amount);
            
            // Compose functions: discount then tax
            Function<Double, Double> discountThenTax = applyDiscount.andThen(applyTax);
            
            // Or reverse: tax then discount
            Function<Double, Double> taxThenDiscount = applyTax.compose(applyDiscount);
            
            double original = 1000.0;
            System.out.println("Original amount: $" + original);
            System.out.println("Discount then Tax: " + formatCurrency.apply(discountThenTax.apply(original)));
            System.out.println("Tax then Discount: " + formatCurrency.apply(taxThenDiscount.apply(original)));
            
            // Practical example: processing payments
            Function<Payment, Double> calculateFee = p -> p.amount * 0.02;
            List<Payment> payments = List.of(
                new Payment("P1", 1000, "ACC001", true),
                new Payment("P2", 5000, "ACC002", true)
            );
            
            System.out.println("\nCalculated fees:");
            payments.stream()
                .forEach(p -> System.out.println("  Payment " + p.id + ": fee = $" + 
                    calculateFee.apply(p)));
        }
    }

    // ==============================================================================
    // 5. BI-FUNCTION & UNARY OPERATOR - Multiple inputs or same type operations
    // ==============================================================================
    
    static class BiFunctionExample {
        public static void demonstrate() {
            System.out.println("\n=== FUNCTIONAL INTERFACE 5: BiFunction<T, U, R> ===");
            System.out.println("Purpose: Combine TWO inputs to produce one output");
            System.out.println("Analogy: Like a recipe - takes two ingredients, makes one dish\n");
            
            // BiFunction: Two inputs, one output
            BiFunction<Double, Double, Double> calculateLoan = (principal, rate) -> {
                return principal * (1 + rate);
            };
            
            System.out.println("Loan calculations:");
            System.out.println("  $10000 at 5% = $" + calculateLoan.apply(10000.0, 0.05));
            System.out.println("  $50000 at 3% = $" + calculateLoan.apply(50000.0, 0.03));
            
            // UnaryOperator: Same type input and output
            UnaryOperator<String> toUpperCase = String::toUpperCase;
            UnaryOperator<Double> roundToTwo = amount -> 
                Math.round(amount * 100.0) / 100.0;
            
            System.out.println("\nUnaryOperator examples:");
            System.out.println("  Uppercase: " + toUpperCase.apply("hello"));
            System.out.println("  Round $123.456 to: $" + roundToTwo.apply(123.456));
            
            // BinaryOperator: Two inputs of same type
            BinaryOperator<Double> maxAmount = (a, b) -> Math.max(a, b);
            BinaryOperator<String> concatenate = (a, b) -> a + " | " + b;
            
            System.out.println("\nBinaryOperator examples:");
            System.out.println("  Max(1500, 2300) = " + maxAmount.apply(1500.0, 2300.0));
            System.out.println("  Concat: " + concatenate.apply("Account1", "Account2"));
        }
    }

    // ==============================================================================
    // 6. COMPARATOR<T> - Determining order and sorting
    // ==============================================================================
    
    static class ComparatorExample {
        public static void demonstrate() {
            System.out.println("\n=== FUNCTIONAL INTERFACE 6: Comparator<T> & Sorting ===");
            System.out.println("Purpose: Define custom sorting logic");
            System.out.println("Analogy: Like a judging criteria for a competition\n");
            
            List<Payment> payments = List.of(
                new Payment("P1", 5000, "ACC001", true),
                new Payment("P2", 2000, "ACC002", true),
                new Payment("P3", 8000, "ACC003", false),
                new Payment("P4", 1500, "ACC004", true)
            );
            
            // Sort by amount (descending)
            System.out.println("Sorted by amount (highest first):");
            payments.stream()
                .sorted(Comparator.comparingDouble(Payment::amount).reversed())
                .forEach(p -> System.out.println("  $" + p.amount + " - " + p.id));
            
            // Sort by verification status, then by amount
            System.out.println("\nSorted by: verified first, then amount:");
            payments.stream()
                .sorted(Comparator
                    .comparing(Payment::verified).reversed()
                    .thenComparingDouble(Payment::amount).reversed())
                .forEach(p -> System.out.println("  Verified=" + p.verified + ", $" + 
                    p.amount + " - " + p.id));
            
            // Custom comparator
            Comparator<String> byLength = Comparator.comparingInt(String::length);
            System.out.println("\nCustom sort by string length:");
            List<String> words = List.of("Transaction", "Pay", "International", "Fee");
            words.stream()
                .sorted(byLength)
                .forEach(w -> System.out.println("  " + w + " (length: " + w.length() + ")"));
        }
    }

    // ==============================================================================
    // 7. RUNNABLE & CALLABLE - Running tasks
    // ==============================================================================
    
    static class RunnableCallableExample {
        public static void demonstrate() {
            System.out.println("\n=== FUNCTIONAL INTERFACES 7: Runnable<T> & Callable<T> ===");
            System.out.println("Purpose: Define tasks to run (in threads or async contexts)");
            System.out.println("Analogy: Like assigning a task to a worker\n");
            
            // Runnable: No return value
            Runnable processPayment = () -> 
                System.out.println("  Processing payment... (no return)");
            
            // Callable: Returns a value
            Callable<String> fetchBalance = () -> {
                Thread.sleep(100); // Simulate network call
                return "Account Balance: $10,000";
            };
            
            System.out.println("Runnable execution:");
            processPayment.run();
            
            System.out.println("\nCallable execution:");
            try {
                String result = fetchBalance.call();
                System.out.println("  " + result);
            } catch (Exception e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
    }

    // ==============================================================================
    // MAIN: Run all demonstrations
    // ==============================================================================
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  FUNCTIONAL INTERFACES: The Building Blocks of FP             ║");
        System.out.println("║  7 Key Interfaces Every Java Developer Should Know            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        PredicateExample.demonstrate();
        ConsumerExample.demonstrate();
        SupplierExample.demonstrate();
        FunctionExample.demonstrate();
        BiFunctionExample.demonstrate();
        ComparatorExample.demonstrate();
        RunnableCallableExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  SUMMARY: 40+ Functional interfaces exist in java.util.function ║");
        System.out.println("║  These 7 are the most commonly used for business applications   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
