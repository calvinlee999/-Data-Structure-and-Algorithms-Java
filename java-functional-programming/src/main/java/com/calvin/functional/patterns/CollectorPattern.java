package com.calvin.functional.patterns;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * COLLECTOR PATTERN (Java 8+)
 * 
 * Think of Collectors like different containers at a recycling center!
 * You sort items (stream elements) into different bins (collections) based on rules.
 * Collectors help you GROUP, COUNT, SUM, and organize data efficiently.
 * 
 * Real-world analogy: Like sorting coins into different jars - pennies in one jar,
 * nickels in another, dimes in a third. Collectors do this automatically!
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 8
 */
public class CollectorPattern {

    record Transaction(String id, double amount, String type, String account, String status) {}
    record Customer(String id, String name, String tier, double totalSpent) {}
    record Payment(String id, double amount, String currency, long timestamp) {}

    /**
     * PATTERN 1: Grouping Collectors
     * Group elements by a classifier function
     */
    static class GroupingCollectorExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Grouping Collectors ===");
            System.out.println("Goal: Group transactions by type\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("TX001", 1000.0, "DEBIT", "ACC001", "COMPLETED"),
                new Transaction("TX002", 500.0, "CREDIT", "ACC002", "COMPLETED"),
                new Transaction("TX003", 1500.0, "DEBIT", "ACC001", "PENDING"),
                new Transaction("TX004", 2000.0, "CREDIT", "ACC003", "COMPLETED"),
                new Transaction("TX005", 750.0, "DEBIT", "ACC002", "FAILED")
            );
            
            // OLD WAY: Manual grouping with loops
            System.out.println("❌ OLD WAY (Imperative):");
            Map<String, List<Transaction>> manualGroups = new HashMap<>();
            for (Transaction tx : transactions) {
                manualGroups.computeIfAbsent(tx.type, k -> new ArrayList<>()).add(tx);
            }
            System.out.println("  Groups: " + manualGroups.keySet());
            System.out.println("  Problem: Verbose, error-prone\n");
            
            // NEW WAY: groupingBy collector
            System.out.println("✅ NEW WAY (Functional with Collectors):");
            Map<String, List<Transaction>> grouped = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::type));
            
            grouped.forEach((type, txList) -> 
                System.out.println("  " + type + ": " + txList.size() + " transactions"));
            
            // Multi-level grouping: by type, then by status
            System.out.println("\nMulti-level grouping (type → status):");
            Map<String, Map<String, List<Transaction>>> multiLevel = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::type,
                    Collectors.groupingBy(Transaction::status)
                ));
            
            multiLevel.forEach((type, statusMap) -> {
                System.out.println("  " + type + ":");
                statusMap.forEach((status, txList) ->
                    System.out.println("    " + status + ": " + txList.size()));
            });
            
            System.out.println("\n  Benefits: Clear, declarative, less code!");
        }
    }

    /**
     * PATTERN 2: Downstream Collectors
     * Combine grouping with aggregation
     */
    static class DownstreamCollectorExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Downstream Collectors ===");
            System.out.println("Goal: Group and calculate totals\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("TX001", 1000.0, "DEBIT", "ACC001", "COMPLETED"),
                new Transaction("TX002", 500.0, "CREDIT", "ACC002", "COMPLETED"),
                new Transaction("TX003", 1500.0, "DEBIT", "ACC001", "PENDING"),
                new Transaction("TX004", 2000.0, "CREDIT", "ACC003", "COMPLETED"),
                new Transaction("TX005", 750.0, "DEBIT", "ACC002", "FAILED")
            );
            
            // Group by type and sum amounts
            Map<String, Double> totalByType = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::type,
                    Collectors.summingDouble(Transaction::amount)
                ));
            
            System.out.println("Total amount by type:");
            totalByType.forEach((type, total) ->
                System.out.println("  " + type + ": $" + total));
            
            // Group by account and count transactions
            Map<String, Long> countByAccount = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::account,
                    Collectors.counting()
                ));
            
            System.out.println("\nTransaction count by account:");
            countByAccount.forEach((account, count) ->
                System.out.println("  " + account + ": " + count + " transactions"));
            
            // Group by status and get average amount
            Map<String, Double> avgByStatus = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::status,
                    Collectors.averagingDouble(Transaction::amount)
                ));
            
            System.out.println("\nAverage amount by status:");
            avgByStatus.forEach((status, avg) ->
                System.out.println("  " + status + ": $" + String.format("%.2f", avg)));
            
            System.out.println("\n  Benefits: Group + aggregate in one pass!");
        }
    }

    /**
     * PATTERN 3: Partitioning Collectors
     * Split data into two groups (true/false)
     */
    static class PartitioningCollectorExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Partitioning Collectors ===");
            System.out.println("Goal: Split into high-value and regular transactions\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("TX001", 1000.0, "DEBIT", "ACC001", "COMPLETED"),
                new Transaction("TX002", 500.0, "CREDIT", "ACC002", "COMPLETED"),
                new Transaction("TX003", 1500.0, "DEBIT", "ACC001", "PENDING"),
                new Transaction("TX004", 200.0, "CREDIT", "ACC003", "COMPLETED"),
                new Transaction("TX005", 750.0, "DEBIT", "ACC002", "FAILED")
            );
            
            // Partition by amount threshold
            Map<Boolean, List<Transaction>> partitioned = transactions.stream()
                .collect(Collectors.partitioningBy(tx -> tx.amount > 1000.0));
            
            System.out.println("High-value transactions (>$1000):");
            partitioned.get(true).forEach(tx ->
                System.out.println("  " + tx.id + ": $" + tx.amount));
            
            System.out.println("\nRegular transactions (≤$1000):");
            partitioned.get(false).forEach(tx ->
                System.out.println("  " + tx.id + ": $" + tx.amount));
            
            // Partition with downstream collector
            Map<Boolean, Double> totalPartitioned = transactions.stream()
                .collect(Collectors.partitioningBy(
                    tx -> tx.amount > 1000.0,
                    Collectors.summingDouble(Transaction::amount)
                ));
            
            System.out.println("\nTotal amounts:");
            System.out.println("  High-value: $" + totalPartitioned.get(true));
            System.out.println("  Regular: $" + totalPartitioned.get(false));
            
            System.out.println("\n  Benefits: Binary split with statistics!");
        }
    }

    /**
     * PATTERN 4: Custom Collectors
     * Create your own collector for complex logic
     */
    static class CustomCollectorExample {
        
        // Custom result container
        static class TransactionSummary {
            int count = 0;
            double totalAmount = 0.0;
            double maxAmount = Double.MIN_VALUE;
            double minAmount = Double.MAX_VALUE;
            
            void add(Transaction tx) {
                count++;
                totalAmount += tx.amount;
                maxAmount = Math.max(maxAmount, tx.amount);
                minAmount = Math.min(minAmount, tx.amount);
            }
            
            TransactionSummary combine(TransactionSummary other) {
                count += other.count;
                totalAmount += other.totalAmount;
                maxAmount = Math.max(maxAmount, other.maxAmount);
                minAmount = Math.min(minAmount, other.minAmount);
                return this;
            }
            
            double getAverage() {
                return count > 0 ? totalAmount / count : 0.0;
            }
            
            @Override
            public String toString() {
                return String.format("Count: %d, Total: $%.2f, Avg: $%.2f, Max: $%.2f, Min: $%.2f",
                    count, totalAmount, getAverage(), maxAmount, minAmount);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Custom Collectors ===");
            System.out.println("Goal: Create custom aggregation logic\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("TX001", 1000.0, "DEBIT", "ACC001", "COMPLETED"),
                new Transaction("TX002", 500.0, "CREDIT", "ACC002", "COMPLETED"),
                new Transaction("TX003", 1500.0, "DEBIT", "ACC001", "PENDING"),
                new Transaction("TX004", 200.0, "CREDIT", "ACC003", "COMPLETED"),
                new Transaction("TX005", 750.0, "DEBIT", "ACC002", "FAILED")
            );
            
            // Custom collector for comprehensive statistics
            Collector<Transaction, TransactionSummary, TransactionSummary> summaryCollector = 
                Collector.of(
                    TransactionSummary::new,              // Supplier: create container
                    TransactionSummary::add,              // Accumulator: add element
                    TransactionSummary::combine,          // Combiner: merge containers
                    Function.identity()                    // Finisher: transform result
                );
            
            TransactionSummary summary = transactions.stream()
                .collect(summaryCollector);
            
            System.out.println("Transaction Summary:");
            System.out.println("  " + summary);
            
            // Group by type and apply custom collector
            Map<String, TransactionSummary> summaryByType = transactions.stream()
                .collect(Collectors.groupingBy(
                    Transaction::type,
                    summaryCollector
                ));
            
            System.out.println("\nSummary by type:");
            summaryByType.forEach((type, sum) ->
                System.out.println("  " + type + ": " + sum));
            
            System.out.println("\n  Benefits: Reusable, complex aggregation logic!");
        }
    }

    /**
     * PATTERN 5: Collectors.toMap Pattern
     * Build maps with custom key/value extractors
     */
    static class ToMapCollectorExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Collectors.toMap ===");
            System.out.println("Goal: Create ID-to-object lookup maps\n");
            
            List<Customer> customers = List.of(
                new Customer("C001", "Alice", "GOLD", 5000.0),
                new Customer("C002", "Bob", "SILVER", 2500.0),
                new Customer("C003", "Carol", "PLATINUM", 10000.0),
                new Customer("C004", "Dave", "BRONZE", 500.0)
            );
            
            // Simple ID to customer map
            Map<String, Customer> customerMap = customers.stream()
                .collect(Collectors.toMap(
                    Customer::id,              // Key extractor
                    Function.identity()        // Value extractor (the customer itself)
                ));
            
            System.out.println("Customer lookup:");
            System.out.println("  " + customerMap.get("C001").name);
            
            // Map with transformed values
            Map<String, String> tierMap = customers.stream()
                .collect(Collectors.toMap(
                    Customer::name,
                    Customer::tier
                ));
            
            System.out.println("\nName to tier mapping:");
            tierMap.forEach((name, tier) ->
                System.out.println("  " + name + " → " + tier));
            
            // Handle duplicate keys (merge function)
            Map<String, Double> tierTotals = customers.stream()
                .collect(Collectors.toMap(
                    Customer::tier,
                    Customer::totalSpent,
                    Double::sum              // Merge duplicate keys by summing
                ));
            
            System.out.println("\nTotal spent by tier:");
            tierTotals.forEach((tier, total) ->
                System.out.println("  " + tier + ": $" + total));
            
            System.out.println("\n  Benefits: Fast lookups, custom maps!");
        }
    }

    /**
     * PATTERN 6: Collectors.joining Pattern
     * Concatenate strings with delimiter
     */
    static class JoiningCollectorExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Collectors.joining ===");
            System.out.println("Goal: Format data as delimited strings\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("TX001", 1000.0, "DEBIT", "ACC001", "COMPLETED"),
                new Transaction("TX002", 500.0, "CREDIT", "ACC002", "COMPLETED"),
                new Transaction("TX003", 1500.0, "DEBIT", "ACC001", "PENDING")
            );
            
            // Simple joining
            String ids = transactions.stream()
                .map(Transaction::id)
                .collect(Collectors.joining(", "));
            
            System.out.println("Transaction IDs:");
            System.out.println("  " + ids);
            
            // Joining with prefix and suffix
            String formatted = transactions.stream()
                .map(tx -> tx.id + "($" + tx.amount + ")")
                .collect(Collectors.joining(" → ", "Flow: ", " [END]"));
            
            System.out.println("\nFormatted transaction flow:");
            System.out.println("  " + formatted);
            
            // Build CSV report
            String csvHeader = "ID,Amount,Type,Account,Status";
            String csvData = transactions.stream()
                .map(tx -> String.format("%s,%.2f,%s,%s,%s",
                    tx.id, tx.amount, tx.type, tx.account, tx.status))
                .collect(Collectors.joining("\n", csvHeader + "\n", ""));
            
            System.out.println("\nCSV Report:");
            System.out.println(csvData);
            
            System.out.println("  Benefits: Professional formatting in one line!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              COLLECTOR PATTERN (Java 8+)                       ║");
        System.out.println("║  Powerful data aggregation and transformation                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        GroupingCollectorExample.demonstrate();
        DownstreamCollectorExample.demonstrate();
        PartitioningCollectorExample.demonstrate();
        CustomCollectorExample.demonstrate();
        ToMapCollectorExample.demonstrate();
        JoiningCollectorExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • groupingBy(): Group elements by classifier                  ║");
        System.out.println("║  • partitioningBy(): Split into true/false groups              ║");
        System.out.println("║  • toMap(): Build custom maps                                  ║");
        System.out.println("║  • Custom collectors: Reusable aggregation logic               ║");
        System.out.println("║  • Introduced in: Java 8                                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
