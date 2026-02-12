package com.calvin.functional;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Lambda Expressions & Method References - The Modern Java Syntax
 * 
 * Lambda expressions allow us to write very concise anonymous functions.
 * Method references (::) reference existing methods instead of writing new lambdas.
 * 
 * Together they make functional programming readable and elegant.
 */
public class LambdaExpressionsMastery {

    record Transaction(String id, double amount, String type, String account) {}

    // ==============================================================================
    // 1. LAMBDA EXPRESSIONS - Concise anonymous functions
    // ==============================================================================
    
    static class LambdaSyntaxExample {
        public static void demonstrate() {
            System.out.println("\n=== LAMBDA EXPRESSION SYNTAX ===");
            
            // Lambda syntax: (parameters) -> expression or { statements }
            
            // 1. No parameters
            Supplier<String> greeting = () -> "Hello, Banking System!";
            System.out.println("No params: " + greeting.get());
            
            // 2. One parameter (parentheses optional if one param)
            Consumer<String> print = msg -> System.out.println("  Message: " + msg);
            print.accept("Processing payment");
            
            // 3. Multiple parameters
            BiFunction<Double, Double, Double> add = (a, b) -> a + b;
            System.out.println("Multiple params (5 + 3): " + add.apply(5.0, 3.0));
            
            // 4. Multi-line lambda (use curly braces)
            Function<Double, String> formatAmount = amount -> {
                double rounded = Math.round(amount * 100.0) / 100.0;
                return String.format("$%.2f", rounded);
            };
            System.out.println("Multi-line lambda: " + formatAmount.apply(123.456));
        }
    }

    // ==============================================================================
    // 2. STREAM API - Declarative data processing
    // ==============================================================================
    
    static class StreamAPIExample {
        public static void demonstrate() {
            System.out.println("\n=== STREAM API PATTERNS ===");
            
            List<Transaction> transactions = List.of(
                new Transaction("T1", 5000, "DEPOSIT", "ACC001"),
                new Transaction("T2", -500, "WITHDRAWAL", "ACC001"),
                new Transaction("T3", 250, "TRANSFER", "ACC002"),
                new Transaction("T4", -100, "FEE", "ACC001"),
                new Transaction("T5", 2000, "DEPOSIT", "ACC002")
            );
            
            System.out.println("All transactions: " + transactions.size());
            
            // Filter -> Map -> Reduce pattern
            System.out.println("\nFilter-Map-Reduce Pattern:");
            System.out.println("Question: Total value deposited?");
            double totalDeposits = transactions.stream()
                .filter(t -> t.type.equals("DEPOSIT"))      // Filter: Only deposits
                .mapToDouble(Transaction::amount)             // Map: Extract amounts
                .sum();                                        // Reduce: Sum them
            System.out.println("Answer: $" + totalDeposits);
            
            // Count by type
            System.out.println("\nGrouping Example:");
            Map<String, Long> byType = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::type,
                    Collectors.counting()
                ));
            System.out.println("Transactions by type: " + byType);
            
            // Complex aggregation
            System.out.println("\nComplex Aggregation:");
            Map<String, Double> totalByAccount = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::account,
                    Collectors.summingDouble(Transaction::amount)
                ));
            System.out.println("Balance by account: " + totalByAccount);
        }
    }

    // ==============================================================================
    // 3. OPTIONAL - Handling null safely with functional style
    // ==============================================================================
    
    static class OptionalExample {
        
        static Optional<Transaction> findTransaction(String id) {
            List<Transaction> transactions = List.of(
                new Transaction("T1", 5000, "DEPOSIT", "ACC001"),
                new Transaction("T2", -500, "WITHDRAWAL", "ACC001")
            );
            return transactions.stream()
                .filter(t -> t.id.equals(id))
                .findFirst();
        }
        
        public static void demonstrate() {
            System.out.println("\n=== OPTIONAL - Null Safety ===");
            
            System.out.println("Old way (null checks everywhere):");
            Transaction t = null;
            if (t != null && t.amount > 0) {
                System.out.println("  Amount: " + t.amount);
            }
            
            System.out.println("\nFunctional way (Optional):");
            Optional<Transaction> transaction = findTransaction("T1");
            
            // map() transforms if present
            transaction
                .filter(t2 -> t2.amount > 0)
                .map(t2 -> "Amount: " + t2.amount)
                .ifPresentOrElse(
                    msg -> System.out.println("  " + msg),
                    () -> System.out.println("  Transaction not found or invalid")
                );
            
            // orElse provides default
            System.out.println("\nUsing orElse:");
            String account = findTransaction("T999")
                .map(Transaction::account)
                .orElse("DEFAULT_ACCOUNT");
            System.out.println("  Account: " + account);
            
            // Chain operations
            System.out.println("\nChaining Optional operations:");
            findTransaction("T2")
                .map(t2 -> t2.amount * 0.02)
                .map(fee -> String.format("Fee: $%.2f", fee))
                .ifPresent(fee -> System.out.println("  " + fee));
        }
    }

    // ==============================================================================
    // 4. FUNCTION COMPOSITION - Combining functions
    // ==============================================================================
    
    static class FunctionCompositionExample {
        public static void demonstrate() {
            System.out.println("\n=== FUNCTION COMPOSITION ===");
            
            // Define simple functions
            Function<String, String> trim = String::trim;
            Function<String, String> uppercase = String::toUpperCase;
            Function<String, String> addPrefix = s -> "ACC-" + s;
            
            // Compose: apply trim, then uppercase
            System.out.println("Using andThen (left to right):");
            String input = "  account123  ";
            String result1 = trim.andThen(uppercase).andThen(addPrefix).apply(input);
            System.out.println("  Input: '" + input + "'");
            System.out.println("  Result: " + result1);
            
            // Compose: apply uppercase, then trim (opposite order)
            System.out.println("\nUsing compose (right to left):");
            String result2 = addPrefix.compose(uppercase).compose(trim).apply(input);
            System.out.println("  Result: " + result2);
            
            // Practical: Payment processing pipeline
            System.out.println("\nPractical Example - Payment Pipeline:");
            Function<Double, Double> applyDiscount = amount -> amount * 0.95;
            Function<Double, Double> applyTax = amount -> amount * 1.08;
            Function<Double, String> format = amount -> 
                String.format("$%.2f", Math.round(amount * 100.0) / 100.0);
            
            double originalAmount = 1000.0;
            String finalPrice = applyDiscount
                .andThen(applyTax)
                .andThen(format)
                .apply(originalAmount);
            
            System.out.println("  Original: $" + originalAmount);
            System.out.println("  After discount & tax: " + finalPrice);
        }
    }

    // ==============================================================================
    // 5. METHOD REFERENCES (::) - Reference existing methods
    // ==============================================================================
    
    static class MethodReferencesExample {
        public static void demonstrate() {
            System.out.println("\n=== METHOD REFERENCES (::) ===");
            
            List<String> accounts = List.of("ACC001", "ACC002", "ACC003");
            
            // 1. Reference to static method: Class::staticMethod
            System.out.println("Static method reference (String::valueOf):");
            List<String> stringified = accounts.stream()
                .map(String::valueOf)  // Instead of: a -> String.valueOf(a)
                .collect(Collectors.toList());
            System.out.println("  Done: " + stringified.size() + " items");
            
            // 2. Reference to instance method: object::method
            System.out.println("\nInstance method reference (String::toUpperCase):");
            accounts.stream()
                .map(String::toUpperCase)  // Instead of: a -> a.toUpperCase()
                .forEach(acc -> System.out.println("  " + acc));
            
            // 3. Reference to constructor: Class::new
            System.out.println("\nConstructor reference (ArrayList::new):");
            Supplier<List<Integer>> listSupplier = ArrayList::new;  // Instead of: () -> new ArrayList<>()
            List<Integer> newList = listSupplier.get();
            System.out.println("  Created empty list: " + newList);
            
            // Practical use with streams
            System.out.println("\nPractical - Collecting to different types:");
            List<String> original = List.of("value1", "value2", "value3");
            List<String> collected = original.stream()
                .collect(Collectors.toCollection(LinkedList::new));  // Collect to LinkedList
            System.out.println("  Collected into LinkedList: " + collected.getClass().getSimpleName());
        }
    }

    // ==============================================================================
    // 6. INTERMEDIATE vs TERMINAL OPERATIONS
    // ==============================================================================
    
    static class IntermediateVsTerminalExample {
        public static void demonstrate() {
            System.out.println("\n=== INTERMEDIATE vs TERMINAL OPERATIONS ===");
            
            List<Transaction> transactions = List.of(
                new Transaction("T1", 5000, "DEPOSIT", "ACC001"),
                new Transaction("T2", -500, "WITHDRAWAL", "ACC001"),
                new Transaction("T3", 250, "TRANSFER", "ACC002"),
                new Transaction("T4", -100, "FEE", "ACC001")
            );
            
            System.out.println("Lazy evaluation (nothing happens until terminal operation):");
            var lazyStream = transactions.stream()
                .filter(t -> {
                    System.out.println("    [filter] " + t.id);
                    return t.amount != 0;
                })
                .map(t -> {
                    System.out.println("    [map] " + t.id);
                    return t.amount;
                });
            
            System.out.println("Stream created but nothing printed yet!");
            System.out.println("Now applying terminal operation (collect):");
            List<Double> amounts = lazyStream.collect(Collectors.toList());
            
            System.out.println("\nKey insight:");
            System.out.println("  - Intermediate: filter, map, flatMap, distinct, sorted");
            System.out.println("  - Terminal: collect, forEach, count, sum, findFirst");
            System.out.println("  - Streams are LAZY: nothing happens until you call a terminal operation");
        }
    }

    // ==============================================================================
    // 7. IMMUTABLE TRANSFORMATIONS
    // ==============================================================================
    
    static class ImmutableTransformationsExample {
        public static void demonstrate() {
            System.out.println("\n=== IMMUTABLE TRANSFORMATIONS ===");
            
            List<Transaction> original = List.of(
                new Transaction("T1", 5000, "DEPOSIT", "ACC001"),
                new Transaction("T2", -500, "WITHDRAWAL", "ACC001")
            );
            
            System.out.println("Original list: " + original.size() + " transactions");
            
            // Transformations create NEW lists, don't modify original
            List<Double> amounts = original.stream()
                .filter(t -> t.amount > 0)
                .map(Transaction::amount)
                .collect(Collectors.toList());
            
            System.out.println("After transformations:");
            System.out.println("  Original still has: " + original.size() + " transactions");
            System.out.println("  Filtered amounts: " + amounts);
            System.out.println("\nBenefit: Original data untouched, safe for concurrent use!");
        }
    }

    // ==============================================================================
    // MAIN: Run all demonstrations
    // ==============================================================================
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  LAMBDA EXPRESSIONS & METHOD REFERENCES - Modern Java Syntax  ║");
        System.out.println("║  Write Concise, Readable Functional Code                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        LambdaSyntaxExample.demonstrate();
        StreamAPIExample.demonstrate();
        OptionalExample.demonstrate();
        FunctionCompositionExample.demonstrate();
        MethodReferencesExample.demonstrate();
        IntermediateVsTerminalExample.demonstrate();
        ImmutableTransformationsExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  Lambda expressions make functional programming concise        ║");
        System.out.println("║  Method references reduce code even further                   ║");
        System.out.println("║  Combined with Streams: powerful declarative pipelines        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
