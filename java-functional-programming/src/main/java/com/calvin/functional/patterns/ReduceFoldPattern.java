package com.calvin.functional.patterns;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * REDUCE/FOLD PATTERN
 * 
 * Think of reduce like squishing many things into one!
 * You start with a collection and collapse it into a single value.
 * 
 * Real-world analogy: Like making a smoothie - you put in many fruits,
 * blend them together, and get one delicious drink!
 * 
 * @author FinTech Principal Software Engineer
 */
public class ReduceFoldPattern {

    // Data models for financial scenarios
    record Transaction(String id, double amount, String type) {}
    record Product(String name, double price, int quantity) {}
    record Account(String id, double balance) {}

    /**
     * PATTERN 1: Simple Reduce - Sum, Max, Min
     * BinaryOperator: Takes two T, returns one T
     */
    static class SimpleReduceExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Simple Reduce (Sum/Max/Min) ===");
            System.out.println("Goal: Calculate total, maximum, and minimum\n");
            
            List<Double> amounts = List.of(100.0, 250.0, 75.0, 500.0, 150.0);
            
            // OLD WAY: Using loops with accumulator
            System.out.println("❌ OLD WAY (Imperative):");
            double sum_old = 0.0;
            for (Double amount : amounts) {
                sum_old += amount;
            }
            System.out.println("  Total: $" + sum_old);
            System.out.println("  Problem: Manual accumulation, error-prone\n");
            
            // NEW WAY: Using reduce
            System.out.println("✅ NEW WAY (Functional with reduce):");
            
            // Sum using reduce
            double total = amounts.stream()
                .reduce(0.0, (a, b) -> a + b);    // Start at 0, add each element
            
            // Or more simply:
            double total2 = amounts.stream()
                .reduce(0.0, Double::sum);         // Method reference
            
            System.out.println("  Total (reduce): $" + total);
            
            // Maximum value
            double max = amounts.stream()
                .reduce(Double.MIN_VALUE, Math::max);
            
            System.out.println("  Maximum: $" + max);
            
            // Minimum value
            double min = amounts.stream()
                .reduce(Double.MAX_VALUE, Math::min);
            
            System.out.println("  Minimum: $" + min);
            
            System.out.println("\nBenefit: Declarative, clearer intent!");
        }
    }

    /**
     * PATTERN 2: Reduce with Optional
     * Safer when stream might be empty
     */
    static class OptionalReduceExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Reduce with Optional ===");
            System.out.println("Goal: Handle empty collections safely\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T001", 100.0, "DEPOSIT"),
                new Transaction("T002", 250.0, "WITHDRAWAL"),
                new Transaction("T003", 500.0, "DEPOSIT")
            );
            
            // Reduce without identity (returns Optional)
            Optional<Transaction> largest = transactions.stream()
                .reduce((t1, t2) -> t1.amount > t2.amount ? t1 : t2);
            
            System.out.println("Largest transaction:");
            largest.ifPresentOrElse(
                tx -> System.out.println("  " + tx.id + ": $" + tx.amount),
                () -> System.out.println("  No transactions found")
            );
            
            // What happens with empty stream?
            System.out.println("\nWith empty stream:");
            List<Transaction> empty = List.of();
            Optional<Transaction> result = empty.stream()
                .reduce((t1, t2) -> t1.amount > t2.amount ? t1 : t2);
            
            result.ifPresentOrElse(
                tx -> System.out.println("  Found: " + tx),
                () -> System.out.println("  ✅ Safely handled empty case!")
            );
            
            System.out.println("\nBenefit: No NullPointerException, safe code!");
        }
    }

    /**
     * PATTERN 3: String Concatenation
     * Building strings from collections
     */
    static class StringConcatenationExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: String Concatenation ===");
            System.out.println("Goal: Build formatted strings from data\n");
            
            List<String> accounts = List.of("ACC001", "ACC002", "ACC003", "ACC004");
            
            // Reduce to concatenate
            String accountList = accounts.stream()
                .reduce("", (acc, id) -> acc + id + ", ");
            
            System.out.println("Basic concatenation:");
            System.out.println("  " + accountList);
            System.out.println("  Problem: Extra comma at end!\n");
            
            // Better: Use Collectors.joining()
            String formatted = accounts.stream()
                .collect(Collectors.joining(", ", "[", "]"));
            
            System.out.println("Using Collectors.joining:");
            System.out.println("  " + formatted);
            System.out.println("  Benefit: Proper formatting automatically!\n");
            
            // Custom formatting
            List<Transaction> transactions = List.of(
                new Transaction("T001", 100.0, "DEPOSIT"),
                new Transaction("T002", 250.0, "WITHDRAWAL")
            );
            
            String summary = transactions.stream()
                .map(tx -> tx.id + "($" + tx.amount + ")")
                .collect(Collectors.joining(" → ", "Flow: ", " → END"));
            
            System.out.println("Transaction flow:");
            System.out.println("  " + summary);
        }
    }

    /**
     * PATTERN 4: Complex Reduce - Building Objects
     * Accumulate into complex structures
     */
    static class ComplexReduceExample {
        
        record OrderSummary(int itemCount, double totalPrice, List<String> items) {
            OrderSummary addItem(Product p) {
                List<String> newItems = new ArrayList<>(items);
                newItems.add(p.name);
                return new OrderSummary(
                    itemCount + p.quantity,
                    totalPrice + (p.price * p.quantity),
                    newItems
                );
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Complex Reduce (Build Objects) ===");
            System.out.println("Goal: Create order summary from products\n");
            
            List<Product> cart = List.of(
                new Product("Laptop", 999.99, 1),
                new Product("Mouse", 29.99, 2),
                new Product("Keyboard", 79.99, 1),
                new Product("Monitor", 299.99, 2)
            );
            
            // Reduce into custom object
            OrderSummary summary = cart.stream()
                .reduce(
                    new OrderSummary(0, 0.0, new ArrayList<>()),  // Identity
                    (acc, product) -> acc.addItem(product),        // Accumulator
                    (s1, s2) -> s1                                  // Combiner (for parallel)
                );
            
            System.out.println("Order Summary:");
            System.out.println("  Total items: " + summary.itemCount);
            System.out.println("  Total price: $" + String.format("%.2f", summary.totalPrice));
            System.out.println("  Products: " + summary.items);
            
            System.out.println("\nBenefit: Build any structure you need!");
        }
    }

    /**
     * PATTERN 5: Parallel Reduce
     * Efficient reduce for large datasets
     */
    static class ParallelReduceExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Parallel Reduce ===");
            System.out.println("Goal: Sum large dataset using multiple cores\n");
            
            // Create large dataset
            List<Integer> numbers = IntStream.rangeClosed(1, 10_000_000)
                .boxed()
                .collect(Collectors.toList());
            
            // Sequential reduce
            long start1 = System.nanoTime();
            long sum1 = numbers.stream()
                .reduce(0, Integer::sum);
            long time1 = (System.nanoTime() - start1) / 1_000_000;
            
            // Parallel reduce
            long start2 = System.nanoTime();
            long sum2 = numbers.parallelStream()
                .reduce(0, Integer::sum, Integer::sum);  // Note: 3 parameters
            long time2 = (System.nanoTime() - start2) / 1_000_000;
            
            System.out.println("Summing 10 million numbers:");
            System.out.println("  Sequential: " + time1 + " ms (sum = " + sum1 + ")");
            System.out.println("  Parallel:   " + time2 + " ms (sum = " + sum2 + ")");
            System.out.println("  Speedup:    " + (time1 / (double) time2) + "x");
            
            System.out.println("\nBenefit: Automatic parallelization for free!");
        }
    }

    /**
     * PATTERN 6: Statistical Reduce
     * Calculate multiple statistics in one pass
     */
    static class StatisticalReduceExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Statistical Reduce ===");
            System.out.println("Goal: Calculate count, sum, avg, min, max in one pass\n");
            
            List<Account> accounts = List.of(
                new Account("A001", 1000.0),
                new Account("A002", 2500.0),
                new Account("A003", 500.0),
                new Account("A004", 1500.0),
                new Account("A005", 3000.0)
            );
            
            // Single pass for all statistics
            DoubleSummaryStatistics stats = accounts.stream()
                .collect(Collectors.summarizingDouble(Account::balance));
            
            System.out.println("Account Balance Statistics:");
            System.out.println("  Count:   " + stats.getCount());
            System.out.println("  Sum:     $" + stats.getSum());
            System.out.println("  Average: $" + stats.getAverage());
            System.out.println("  Min:     $" + stats.getMin());
            System.out.println("  Max:     $" + stats.getMax());
            
            System.out.println("\nBenefit: All stats in ONE iteration!");
        }
    }

    /**
     * PATTERN 7: Grouping Reduce
     * Reduce within groups
     */
    static class GroupingReduceExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 7: Grouping + Reduce ===");
            System.out.println("Goal: Total amount by transaction type\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T001", 100.0, "DEPOSIT"),
                new Transaction("T002", 250.0, "WITHDRAWAL"),
                new Transaction("T003", 500.0, "DEPOSIT"),
                new Transaction("T004", 150.0, "WITHDRAWAL"),
                new Transaction("T005", 300.0, "DEPOSIT")
            );
            
            // Group by type, then reduce to sum
            Map<String, Double> totalByType = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::type,
                    Collectors.reducing(0.0, Transaction::amount, Double::sum)
                ));
            
            System.out.println("Total by transaction type:");
            totalByType.forEach((type, total) ->
                System.out.println("  " + type + ": $" + total));
            
            // Or simpler with summingDouble
            Map<String, Double> totalByType2 = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::type,
                    Collectors.summingDouble(Transaction::amount)
                ));
            
            System.out.println("\nBenefit: Group and aggregate in one pass!");
        }
    }

    // Main demonstration
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                 REDUCE/FOLD PATTERN                            ║");
        System.out.println("║  Collapse collections into single values                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        SimpleReduceExample.demonstrate();
        OptionalReduceExample.demonstrate();
        StringConcatenationExample.demonstrate();
        ComplexReduceExample.demonstrate();
        ParallelReduceExample.demonstrate();
        StatisticalReduceExample.demonstrate();
        GroupingReduceExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • reduce() collapses many elements into one                   ║");
        System.out.println("║  • Use Optional version when stream might be empty             ║");
        System.out.println("║  • Parallel reduce for large datasets                          ║");
        System.out.println("║  • Combine with grouping for powerful aggregations             ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
