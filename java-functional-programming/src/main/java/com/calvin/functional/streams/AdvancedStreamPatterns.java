package com.calvin.functional;

import java.util.*;
import java.util.stream.*;

/**
 * Advanced Stream Patterns - Mastering Complex Data Processing
 * 
 * This class demonstrates 8+ advanced patterns for stream processing that are
 * essential for real-world data operations in enterprise applications.
 */
public class AdvancedStreamPatterns {

    record Transaction(String id, double amount, String type, String account) {}
    record Account(String name, double balance, boolean active) {}

    // ==============================================================================
    // 1. FILTER-MAP-REDUCE PATTERN - The fundamental stream pattern
    // ==============================================================================
    
    static class FilterMapReduceExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Filter-Map-Reduce ===");
            System.out.println("Filter: Keep what we want");
            System.out.println("Map: Transform to what we need");
            System.out.println("Reduce: Combine into result\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T1", 500, "DEPOSIT", "ACC001"),
                new Transaction("T2", -200, "WITHDRAWAL", "ACC001"),
                new Transaction("T3", 1000, "TRANSFER", "ACC002"),
                new Transaction("T4", -100, "FEE", "ACC001"),
                new Transaction("T5", 750, "DEPOSIT", "ACC002")
            );
            
            // Total of all deposits
            double totalDeposits = transactions.stream()
                .filter(t -> t.type.equals("DEPOSIT"))          // Filter: Only deposits
                .mapToDouble(Transaction::amount)                // Map: Extract amounts
                .sum();                                           // Reduce: Sum them
            
            System.out.println("Total deposits: $" + totalDeposits);
            
            // Average withdrawal amount
            double avgWithdrawal = transactions.stream()
                .filter(t -> t.type.equals("WITHDRAWAL"))
                .mapToDouble(t -> Math.abs(t.amount))
                .average()
                .orElse(0.0);
            
            System.out.println("Average withdrawal: $" + avgWithdrawal);
            
            // Count by type with reduce
            long typeCount = transactions.stream()
                .filter(t -> t.type.equals("DEPOSIT"))
                .count();
            
            System.out.println("Number of deposits: " + typeCount);
        }
    }

    // ==============================================================================
    // 2. PARTITION PATTERN - Split into true/false groups
    // ==============================================================================
    
    static class PartitionPatternExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Partition Pattern ===");
            System.out.println("Split data into two groups: true and false categories\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T1", 500, "DEPOSIT", "ACC001"),
                new Transaction("T2", 200, "WITHDRAWAL", "ACC001"),
                new Transaction("T3", 1000, "TRANSFER", "ACC002"),
                new Transaction("T4", 100, "FEE", "ACC001"),
                new Transaction("T5", 750, "DEPOSIT", "ACC002")
            );
            
            // Partition by positive/negative
            Map<Boolean, List<Transaction>> bySign = transactions.stream()
                .collect(Collectors.partitioningBy(t -> t.amount >= 0));
            
            System.out.println("Credits (positive amounts):");
            bySign.get(true).forEach(t -> System.out.println("  +" + t.amount + " " + t.type));
            
            System.out.println("\nDebits (negative amounts):");
            bySign.get(false).forEach(t -> System.out.println("  " + t.amount + " " + t.type));
            
            // Partition by type
            Map<Boolean, Double> byLarge = transactions.stream()
                .collect(Collectors.partitioningBy(
                    t -> Math.abs(t.amount) > 500,
                    Collectors.summingDouble(Transaction::amount)
                ));
            
            System.out.println("\nLarge transactions (>$500): $" + byLarge.get(true));
            System.out.println("Small transactions (≤$500): $" + byLarge.get(false));
        }
    }

    // ==============================================================================
    // 3. GROUP-BY PATTERN - Organize by category
    // ==============================================================================
    
    static class GroupByPatternExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Group-By Pattern ===");
            System.out.println("Organize data by a common attribute\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T1", 500, "DEPOSIT", "ACC001"),
                new Transaction("T2", -200, "WITHDRAWAL", "ACC001"),
                new Transaction("T3", 1000, "TRANSFER", "ACC002"),
                new Transaction("T4", -100, "FEE", "ACC001"),
                new Transaction("T5", 750, "DEPOSIT", "ACC002")
            );
            
            // Group by type
            System.out.println("Transactions grouped by type:");
            Map<String, List<Transaction>> byType = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::type));
            
            byType.forEach((type, list) -> 
                System.out.println("  " + type + ": " + list.size() + " transactions")
            );
            
            // Group by account with sum
            System.out.println("\nAccount balances:");
            Map<String, Double> accountBalance = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::account,
                    Collectors.summingDouble(Transaction::amount)
                ));
            
            accountBalance.forEach((account, balance) ->
                System.out.println("  " + account + ": $" + balance)
            );
            
            // Complex grouping: multiple levels
            System.out.println("\nTransactions by type then by account:");
            Map<String, Map<String, List<Transaction>>> nested = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::type,
                    Collectors.groupingBy(Transaction::account)
                ));
            
            nested.forEach((type, accountGroup) -> {
                System.out.println("  " + type + ":");
                accountGroup.forEach((account, list) ->
                    System.out.println("    " + account + ": " + list.size())
                );
            });
        }
    }

    // ==============================================================================
    // 4. FLATMAP PATTERN - Flatten nested structures
    // ==============================================================================
    
    static class FlatMapPatternExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: FlatMap Pattern ===");
            System.out.println("Flatten nested collections into single stream\n");
            
            // Accounts, each with multiple transactions
            Map<String, List<Transaction>> accountTransactions = Map.of(
                "ACC001", List.of(
                    new Transaction("T1", 500, "DEPOSIT", "ACC001"),
                    new Transaction("T2", -200, "WITHDRAWAL", "ACC001")
                ),
                "ACC002", List.of(
                    new Transaction("T3", 1000, "TRANSFER", "ACC002"),
                    new Transaction("T4", 750, "DEPOSIT", "ACC002")
                )
            );
            
            // Flatten all transactions from all accounts
            System.out.println("All transactions across all accounts:");
            List<Transaction> allTransactions = accountTransactions.values().stream()
                .flatMap(List::stream)  // Flatten: list of lists -> single stream
                .collect(Collectors.toList());
            
            allTransactions.forEach(t ->
                System.out.println("  " + t.id + ": " + t.type + " $" + t.amount)
            );
            
            // Total across all accounts
            double grandTotal = accountTransactions.values().stream()
                .flatMap(List::stream)
                .mapToDouble(Transaction::amount)
                .sum();
            
            System.out.println("\nTotal across all accounts: $" + grandTotal);
            
            // String example: flatten array of words
            System.out.println("\nFlatMap with strings:");
            String[] sentences = {"Hello World", "Functional Programming"};
            List<String> words = Arrays.stream(sentences)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .collect(Collectors.toList());
            
            System.out.println("Words: " + words);
        }
    }

    // ==============================================================================
    // 5. LAZY EVALUATION PATTERN - Processing only what's needed
    // ==============================================================================
    
    static class LazyEvaluationExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Lazy Evaluation ===");
            System.out.println("Streams only process data when terminal operation is called\n");
            
            List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            
            System.out.println("Processing first 3 even numbers:");
            var stream = numbers.stream()
                .peek(n -> System.out.println("  [peek] Processing " + n))
                .filter(n -> n % 2 == 0)
                .peek(n -> System.out.println("  [filtered] " + n))
                .limit(3);
            
            System.out.println("(Stream created, but nothing processed yet!)");
            System.out.println("\nNow calling terminal operation (collect):");
            List<Integer> result = stream.collect(Collectors.toList());
            
            System.out.println("Result: " + result);
            System.out.println("\nKey insight: Without limit(3), all 10 numbers would be processed.");
            System.out.println("With limit: only as many as needed were actually processed!");
        }
    }

    // ==============================================================================
    // 6. PARALLEL STREAMS - Process data with multiple threads
    // ==============================================================================
    
    static class ParallelStreamsExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Parallel Streams ===");
            System.out.println("Process large datasets using multiple CPU cores\n");
            
            // Create large dataset
            List<Integer> largeDataset = IntStream.rangeClosed(1, 1000000)
                .boxed()
                .collect(Collectors.toList());
            
            // Sequential processing
            long start1 = System.nanoTime();
            long seqSum = largeDataset.stream()
                .filter(n -> n % 2 == 0)
                .mapToLong(Long::valueOf)
                .sum();
            long duration1 = System.nanoTime() - start1;
            
            // Parallel processing
            long start2 = System.nanoTime();
            long parSum = largeDataset.parallelStream()
                .filter(n -> n % 2 == 0)
                .mapToLong(Long::valueOf)
                .sum();
            long duration2 = System.nanoTime() - start2;
            
            System.out.println("Processing 1,000,000 numbers:");
            System.out.println("  Sequential: " + (duration1 / 1_000_000) + "ms");
            System.out.println("  Parallel:   " + (duration2 / 1_000_000) + "ms");
            System.out.println("  Results equal: " + (seqSum == parSum));
            System.out.println("\nBenefit: Parallel often faster for large datasets, but be careful!");
        }
    }

    // ==============================================================================
    // 7. OPERATION TYPES PATTERN - Intermediate vs Terminal
    // ==============================================================================
    
    static class OperationTypesExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 7: Operation Types ===");
            System.out.println("Intermediate operations: return Stream (lazy)");
            System.out.println("Terminal operations: return final result (triggers execution)\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T1", 500, "DEPOSIT", "ACC001"),
                new Transaction("T2", -200, "WITHDRAWAL", "ACC001"),
                new Transaction("T3", 1000, "TRANSFER", "ACC002"),
                new Transaction("T4", -100, "FEE", "ACC001"),
                new Transaction("T5", 750, "DEPOSIT", "ACC002")
            );
            
            System.out.println("Intermediate operations (lazy, returns Stream):");
            System.out.println("  - filter(), map(), flatMap()");
            System.out.println("  - distinct(), sorted(), limit(), skip()");
            System.out.println("  - peek()");
            
            System.out.println("\nTerminal operations (eager, triggers execution):");
            System.out.println("  - collect(), forEach()");
            System.out.println("  - count(), sum(), average()");
            System.out.println("  - min(), max()");
            System.out.println("  - findFirst(), anyMatch(), allMatch()");
            
            // Practical example
            System.out.println("\nExample pipeline:");
            long count = transactions.stream()         // Create stream
                .filter(t -> t.amount > 0)             // Intermediate
                .map(t -> t.amount * 0.02)             // Intermediate
                .collect(Collectors.summingDouble(Double::doubleValue))  // Terminal
                ;
            
            System.out.println("Total fees on deposits: $" + count);
        }
    }

    // ==============================================================================
    // 8. CUSTOM COLLECTORS PATTERN - Advanced collection strategies
    // ==============================================================================
    
    static class CustomCollectorsExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 8: Custom Collectors ===");
            System.out.println("Use Collectors to produce custom outputs from streams\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T1", 500, "DEPOSIT", "ACC001"),
                new Transaction("T2", -200, "WITHDRAWAL", "ACC001"),
                new Transaction("T3", 1000, "TRANSFER", "ACC002"),
                new Transaction("T4", 750, "DEPOSIT", "ACC002")
            );
            
            // Group to map with statistics
            System.out.println("Statistics by account:");
            Map<String, DoubleSummaryStatistics> stats = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::account,
                    Collectors.summarizingDouble(Transaction::amount)
                ));
            
            stats.forEach((account, stat) ->
                System.out.println("  " + account + ": avg=$" + stat.getAverage() + 
                    ", total=$" + stat.getSum() + ", count=" + stat.getCount())
            );
            
            // Join strings
            System.out.println("\nTransaction IDs joined:");
            String ids = transactions.stream()
                .map(Transaction::id)
                .collect(Collectors.joining(", ", "[", "]"));
            System.out.println("  " + ids);
            
            // Mapping and then collecting
            System.out.println("\nAmounts only (mapped then collected):");
            List<Double> amounts = transactions.stream()
                .collect(Collectors.mapping(
                    Transaction::amount,
                    Collectors.toList()
                ));
            System.out.println("  " + amounts);
        }
    }

    // ==============================================================================
    // MAIN: Run all demonstrations
    // ==============================================================================
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  ADVANCED STREAM PATTERNS - Production Data Processing         ║");
        System.out.println("║  Master 8+ Patterns for Complex Real-World Scenarios           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
       FilterMapReduceExample.demonstrate();
        PartitionPatternExample.demonstrate();
        GroupByPatternExample.demonstrate();
        FlatMapPatternExample.demonstrate();
        LazyEvaluationExample.demonstrate();
        ParallelStreamsExample.demonstrate();
        OperationTypesExample.demonstrate();
        CustomCollectorsExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  These patterns solve 80% of real-world stream processing      ║");
        System.out.println("║  Combine them to build powerful, declarative data pipelines    ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
