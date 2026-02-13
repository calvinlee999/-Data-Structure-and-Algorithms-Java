package com.calvin.functional.patterns;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * MAP TRANSFORMATION PATTERN
 * 
 * Think of "map" like a magic wand that transforms every item in a list!
 * Instead of using old-fashioned loops, we use map() to change each element.
 * 
 * Real-world analogy: Like a candy factory - you put in raw ingredients,
 * and the machine transforms each one into a finished candy. The original
 * ingredients don't change; you get new candies!
 * 
 * @author FinTech Principal Software Engineer
 */
public class MapTransformationPattern {

    // Data models for banking scenario
    record Transaction(String id, double amount, String currency) {}
    record Account(String id, String name, double balance) {}
    record TransactionSummary(String id, double amountUSD, String status) {}

    /**
     * PATTERN 1: Simple Map - Transform each element
     * Function: Takes T, returns R
     */
    static class SimpleMapExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Simple Map Transformation ===");
            System.out.println("Goal: Convert prices from dollars to euros\n");
            
            // Original prices in USD
            List<Double> pricesUSD = List.of(10.0, 25.5, 100.0, 50.75);
            
            // OLD WAY: Using loops (mutates or creates temporary variables)
            System.out.println("❌ OLD WAY (Imperative with loops):");
            List<Double> pricesEUR_old = new ArrayList<>();
            for (Double price : pricesUSD) {
                pricesEUR_old.add(price * 0.85);  // Convert to EUR
            }
            System.out.println("  Prices in EUR: " + pricesEUR_old);
            System.out.println("  Problem: Verbose, harder to read, more room for bugs\n");
            
            // NEW WAY: Using map() (declarative, clear intent)
            System.out.println("✅ NEW WAY (Functional with map):");
            Function<Double, Double> usdToEur = price -> price * 0.85;
            List<Double> pricesEUR = pricesUSD.stream()
                .map(usdToEur)                    // Transform each price
                .collect(Collectors.toList());
            
            System.out.println("  Original USD: " + pricesUSD);
            System.out.println("  Converted EUR: " + pricesEUR);
            System.out.println("  Benefits: Clear intent, immutable, no side effects!");
        }
    }

    /**
     * PATTERN 2: Map with Method References
     * Using :: notation for cleaner code
     */
    static class MapWithMethodReferences {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Map with Method References ===");
            System.out.println("Goal: Extract account names from account objects\n");
            
            List<Account> accounts = List.of(
                new Account("A001", "Alice Smith", 1000.0),
                new Account("A002", "Bob Johnson", 2500.0),
                new Account("A003", "Carol White", 500.0)
            );
            
            // Using method reference (cleaner than lambda)
            System.out.println("Using method reference Account::name:");
            List<String> names = accounts.stream()
                .map(Account::name)               // Same as: acc -> acc.name()
                .collect(Collectors.toList());
            
            System.out.println("  Account names: " + names);
            
            // Chain multiple maps
            System.out.println("\nChaining maps (extract name, then uppercase):");
            List<String> upperNames = accounts.stream()
                .map(Account::name)               // Extract name
                .map(String::toUpperCase)         // Convert to uppercase
                .collect(Collectors.toList());
            
            System.out.println("  Uppercase names: " + upperNames);
            System.out.println("  Benefit: Read like a sentence - very clear!");
        }
    }

    /**
     * PATTERN 3: Map with Complex Transformations
     * Transform objects to different types
     */
    static class ComplexMapTransformation {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Complex Map Transformation ===");
            System.out.println("Goal: Convert transactions to summaries\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T001", 100.0, "USD"),
                new Transaction("T002", 85.0, "EUR"),
                new Transaction("T003", 120.0, "GBP")
            );
            
            // Function to convert to USD and create summary
            Function<Transaction, TransactionSummary> toSummary = tx -> {
                double amountUSD = switch (tx.currency) {
                    case "EUR" -> tx.amount * 1.18;
                    case "GBP" -> tx.amount * 1.27;
                    default -> tx.amount;
                };
                String status = amountUSD > 100 ? "HIGH_VALUE" : "NORMAL";
                return new TransactionSummary(tx.id, amountUSD, status);
            };
            
            List<TransactionSummary> summaries = transactions.stream()
                .map(toSummary)
                .collect(Collectors.toList());
            
            System.out.println("Transaction Summaries:");
            summaries.forEach(s -> System.out.println("  " + s));
            
            System.out.println("\nBenefit: Complex logic hidden in function, stream stays clean!");
        }
    }

    /**
     * PATTERN 4: FlatMap - Map + Flatten
     * When each element maps to multiple elements
     */
    static class FlatMapExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: FlatMap (Map + Flatten) ===");
            System.out.println("Goal: Get all transactions from multiple accounts\n");
            
            // Each account has multiple transactions
            Map<String, List<String>> accountTransactions = Map.of(
                "Alice", List.of("T001", "T002", "T003"),
                "Bob", List.of("T004", "T005"),
                "Carol", List.of("T006")
            );
            
            // Regular map would give us List<List<String>> - nested!
            System.out.println("Problem with regular map:");
            List<List<String>> nested = accountTransactions.values().stream()
                .collect(Collectors.toList());
            System.out.println("  Nested structure: " + nested);
            
            // FlatMap flattens it automatically
            System.out.println("\nSolution with flatMap:");
            List<String> allTransactions = accountTransactions.values().stream()
                .flatMap(List::stream)            // Flatten the lists
                .collect(Collectors.toList());
            
            System.out.println("  All transactions (flat): " + allTransactions);
            System.out.println("  Benefit: No nested collections, easy to work with!");
        }
    }

    /**
     * PATTERN 5: MapToInt/MapToDouble/MapToLong
     * Specialized maps for primitive types (more efficient)
     */
    static class PrimitiveMapExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Primitive Type Maps ===");
            System.out.println("Goal: Calculate statistics efficiently\n");
            
            List<Account> accounts = List.of(
                new Account("A001", "Alice", 1000.0),
                new Account("A002", "Bob", 2500.0),
                new Account("A003", "Carol", 500.0),
                new Account("A004", "Dave", 1500.0)
            );
            
            // mapToDouble - specialized for double primitives
            double totalBalance = accounts.stream()
                .mapToDouble(Account::balance)    // Extract balances as double
                .sum();                            // Sum them up
            
            double avgBalance = accounts.stream()
                .mapToDouble(Account::balance)
                .average()
                .orElse(0.0);
            
            double maxBalance = accounts.stream()
                .mapToDouble(Account::balance)
                .max()
                .orElse(0.0);
            
            System.out.println("Account Statistics:");
            System.out.println("  Total balance: $" + totalBalance);
            System.out.println("  Average balance: $" + avgBalance);
            System.out.println("  Maximum balance: $" + maxBalance);
            System.out.println("\nBenefit: Faster than boxing/unboxing, cleaner code!");
        }
    }

    /**
     * PATTERN 6: Parallel Map - Use multiple CPU cores
     * For large datasets
     */
    static class ParallelMapExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Parallel Map (Multi-threading) ===");
            System.out.println("Goal: Process large dataset faster\n");
            
            // Create large dataset
            List<Integer> largeDataset = IntStream.rangeClosed(1, 1_000_000)
                .boxed()
                .collect(Collectors.toList());
            
            // Sequential processing
            long start1 = System.nanoTime();
            List<Integer> sequential = largeDataset.stream()
                .map(n -> n * 2)                  // Double each number
                .collect(Collectors.toList());
            long time1 = (System.nanoTime() - start1) / 1_000_000;
            
            // Parallel processing
            long start2 = System.nanoTime();
            List<Integer> parallel = largeDataset.parallelStream()
                .map(n -> n * 2)                  // Same operation, but parallel
                .collect(Collectors.toList());
            long time2 = (System.nanoTime() - start2) / 1_000_000;
            
            System.out.println("Processing 1 million numbers:");
            System.out.println("  Sequential: " + time1 + " ms");
            System.out.println("  Parallel:   " + time2 + " ms");
            System.out.println("  Speedup:    " + (time1 / (double) time2) + "x");
            System.out.println("\nBenefit: Use all CPU cores for big data!");
        }
    }

    // Main demonstration
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║          MAP TRANSFORMATION PATTERN                            ║");
        System.out.println("║  Transform collections without loops or mutation               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        SimpleMapExample.demonstrate();
        MapWithMethodReferences.demonstrate();
        ComplexMapTransformation.demonstrate();
        FlatMapExample.demonstrate();
        PrimitiveMapExample.demonstrate();
        ParallelMapExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • map() transforms each element to something new              ║");
        System.out.println("║  • Original collection stays unchanged (immutable)             ║");
        System.out.println("║  • Chain maps for complex transformations                      ║");
        System.out.println("║  • Use parallelStream() for large datasets                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
