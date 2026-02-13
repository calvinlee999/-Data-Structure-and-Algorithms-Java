package com.calvin.functional.patterns;

import java.util.*;
import java.util.stream.*;

/**
 * PREDICATE STREAM PATTERN (Java 9+)
 * 
 * Think of takeWhile/dropWhile like eating candies from a jar!
 * takeWhile: Keep eating until you find one you don't like - then stop!
 * dropWhile: Skip candies you don't like until you find one you do - then take the rest!
 * 
 * Real-world analogy: Reading a book - takeWhile reads until boring chapter,
 * dropWhile skips boring intro and starts when interesting!
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 9
 */
public class PredicateStreamPattern {

    /**
     * PATTERN 1: takeWhile() - Take Until Condition Fails
     * Process ordered data until a condition becomes false
     */
    static class TakeWhileExample {
        
        record Transaction(String id, double amount, String status, long timestamp) {}
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: takeWhile() ===");
            System.out.println("Goal: Take elements until condition fails\n");
            
            // Sorted transactions by amount
            List<Transaction> transactions = List.of(
                new Transaction("TX001", 100.0, "COMPLETED", 1000),
                new Transaction("TX002", 250.0, "COMPLETED", 2000),
                new Transaction("TX003", 500.0, "COMPLETED", 3000),
                new Transaction("TX004", 1500.0, "PENDING", 4000),   // Stops here!
                new Transaction("TX005", 2000.0, "COMPLETED", 5000)
            );
            
            System.out.println("All transactions:");
            transactions.forEach(tx -> 
                System.out.println("  " + tx.id + ": $" + tx.amount + " [" + tx.status + "]"));
            
            // OLD WAY: filter() - checks EVERY element
            System.out.println("\n❌ OLD WAY (filter - checks all):");
            List<Transaction> filtered = transactions.stream()
                .filter(tx -> tx.amount < 1000.0)
                .toList();
            System.out.println("  Results: " + filtered.size() + " transactions");
            filtered.forEach(tx -> System.out.println("  " + tx.id + ": $" + tx.amount));
            
            // NEW WAY: takeWhile() - stops when condition fails
            System.out.println("\n✅ NEW WAY (takeWhile - stops early):");
            List<Transaction> taken = transactions.stream()
                .takeWhile(tx -> tx.amount < 1000.0)
                .toList();
            System.out.println("  Results: " + taken.size() + " transactions");
            taken.forEach(tx -> System.out.println("  " + tx.id + ": $" + tx.amount));
            
            System.out.println("\n  Benefits: Stops processing when condition fails!");
            System.out.println("  Use case: Batch processing with limits");
        }
    }

    /**
     * PATTERN 2: dropWhile() - Drop Until Condition Fails
     * Skip elements until a condition becomes false
     */
    static class DropWhileExample {
        
        record Payment(String id, double amount, String status) {}
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: dropWhile() ===");
            System.out.println("Goal: Skip elements until condition fails\n");
            
            // Payments sorted by status (PENDING first, then COMPLETED)
            List<Payment> payments = List.of(
                new Payment("PAY001", 100.0, "PENDING"),
                new Payment("PAY002", 200.0, "PENDING"),
                new Payment("PAY003", 300.0, "PENDING"),
                new Payment("PAY004", 400.0, "COMPLETED"),  // Starts here!
                new Payment("PAY005", 500.0, "COMPLETED")
            );
            
            System.out.println("All payments:");
            payments.forEach(p -> 
                System.out.println("  " + p.id + ": $" + p.amount + " [" + p.status + "]"));
            
            // OLD WAY: filter() - checks EVERY element
            System.out.println("\n❌ OLD WAY (filter - checks all):");
            List<Payment> filtered = payments.stream()
                .filter(p -> !p.status.equals("PENDING"))
                .toList();
            System.out.println("  Results: " + filtered.size() + " payments");
            filtered.forEach(p -> System.out.println("  " + p.id + ": " + p.status));
            
            // NEW WAY: dropWhile() - skips until condition fails
            System.out.println("\n✅ NEW WAY (dropWhile - skips then takes all):");
            List<Payment> dropped = payments.stream()
                .dropWhile(p -> p.status.equals("PENDING"))
                .toList();
            System.out.println("  Results: " + dropped.size() + " payments");
            dropped.forEach(p -> System.out.println("  " + p.id + ": " + p.status));
            
            System.out.println("\n  Benefits: Skip uninteresting data efficiently!");
            System.out.println("  Use case: Skip header rows, process from marker");
        }
    }

    /**
     * PATTERN 3: Combined takeWhile() and dropWhile()
     * Extract a specific window of data
     */
    static class  CombinedExample {
        
        record Transaction(String id, long timestamp, double amount) {}
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Combined takeWhile + dropWhile ===");
            System.out.println("Goal: Extract data window from ordered stream\n");
            
            // Transactions sorted by timestamp
            List<Transaction> transactions = List.of(
                new Transaction("TX001", 1000, 100.0),
                new Transaction("TX002", 2000, 200.0),
                new Transaction("TX003", 3000, 300.0),  // Start window
                new Transaction("TX004", 4000, 400.0),  // In window
                new Transaction("TX005", 5000, 500.0),  // In window
                new Transaction("TX006", 6000, 600.0),  // End window
                new Transaction("TX007", 7000, 700.0)
            );
            
            System.out.println("Get transactions between timestamps 3000-6000:");
            
            List<Transaction> window = transactions.stream()
                .dropWhile(tx -> tx.timestamp < 3000)  // Skip until >= 3000
                .takeWhile(tx -> tx.timestamp <= 6000) // Take until > 6000
                .toList();
            
            System.out.println("\nWindow results:");
            window.forEach(tx -> 
                System.out.println("  " + tx.id + " @ " + tx.timestamp + ": $" + tx.amount));
            
            System.out.println("\n  Benefits: Extract ranges from sorted data!");
            System.out.println("  Use case: Time-window analysis, pagination");
        }
    }

    /**
     * PATTERN 4: Performance Comparison
     * Show when takeWhile/dropWhile vs filter
     */
    static class PerformanceExample {
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Performance Comparison ===");
            System.out.println("Goal: Understand when to use takeWhile/dropWhile\n");
            
            // Large sorted list
            List<Integer> numbers = IntStream.rangeClosed(1, 10000).boxed().toList();
            
            // Scenario: Get first 100 numbers
            System.out.println("Scenario: Get first 100 numbers from sorted list");
            
            // Using filter (checks all 10,000!)
            long start1 = System.nanoTime();
            List<Integer> filtered = numbers.stream()
                .filter(n -> n <= 100)
                .toList();
            long time1 = System.nanoTime() - start1;
            System.out.println("\n  filter(): Checked all " + numbers.size() + " elements");
            System.out.println("  Time: " + time1 / 1000 + " microseconds");
            
            // Using takeWhile (stops after 100!)
            long start2 = System.nanoTime();
            List<Integer> taken = numbers.stream()
                .takeWhile(n -> n <= 100)
                .toList();
            long time2 = System.nanoTime() - start2;
            System.out.println("\n  takeWhile(): Stopped after checking " + taken.size() + " elements");
            System.out.println("  Time: " + time2 / 1000 + " microseconds");
            
            long improvement = ((time1 - time2) * 100 / time1);
            System.out.println("\n  Performance gain: " + improvement + "% faster!");
            
            System.out.println("\n  Rule: Use takeWhile/dropWhile on SORTED data!");
            System.out.println("  Rule: Use filter() on UNSORTED data!");
        }
    }

    /**
     * PATTERN 5: Batch Processing Pattern
     * Process data in batches until threshold
     */
    static class BatchProcessingExample {
        
        record Order(String id, double amount, String priority) {}
        
        static double processBatch(List<Order> batch) {
            double total = batch.stream().mapToDouble(Order::amount).sum();
            System.out.println("  Processed batch of " + batch.size() + " orders: $" + total);
            return total;
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Batch Processing with Threshold ===");
            System.out.println("Goal: Process batches until limit reached\n");
            
            // Orders sorted by priority
            List<Order> orders = List.of(
                new Order("ORD001", 100.0, "HIGH"),
                new Order("ORD002", 200.0, "HIGH"),
                new Order("ORD003", 300.0, "HIGH"),
                new Order("ORD004", 400.0, "MEDIUM"),  // Stops here
                new Order("ORD005", 500.0, "MEDIUM"),
                new Order("ORD006", 600.0, "LOW")
            );
            
            System.out.println("Processing HIGH priority orders only:");
            List<Order> highPriorityBatch = orders.stream()
                .takeWhile(order -> order.priority.equals("HIGH"))
                .toList();
            
            processBatch(highPriorityBatch);
            
            System.out.println("\nProcessing from first MEDIUM priority:");
            List<Order> mediumOnwardBatch = orders.stream()
                .dropWhile(order -> order.priority.equals("HIGH"))
                .toList();
            
            processBatch(mediumOnwardBatch);
            
            System.out.println("\n  Benefits: Clean batch processing logic!");
        }
    }

    /**
     * PATTERN 6: Pagination Pattern
     * Skip to page and take page size
     */
    static class PaginationExample {
        
        record Customer(String id, String name, double totalSpent) {}
        
        static List<Customer> getPage(List<Customer> customers, int page, int pageSize) {
            return customers.stream()
                .dropWhile(c -> customers.indexOf(c) < (page * pageSize))
                .takeWhile(c -> customers.indexOf(c) < ((page + 1) * pageSize))
                .toList();
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Pagination Pattern ===");
            System.out.println("Goal: Implement pagination efficiently\n");
            
            List<Customer> customers = IntStream.range(0, 25)
                .mapToObj(i -> new Customer("CUST" + String.format("%03d", i), 
                    "Customer" + i, Math.random() * 10000))
                .toList();
            
            int pageSize = 5;
            
            System.out.println("Total customers: " + customers.size());
            System.out.println("Page size: " + pageSize);
            
            // Get page 2 (0-indexed)
            System.out.println("\nPage 2 (customers 10-14):");
            List<Customer> page2 = customers.stream()
                .skip(10)  // Better than dropWhile for pagination
                .limit(5)  // Better than takeWhile for pagination
                .toList();
            
            page2.forEach(c -> System.out.println("  " + c.id + ": " + c.name));
            
            System.out.println("\n  Note: For pagination, skip()/limit() are better!");
            System.out.println("  Use takeWhile/dropWhile for CONDITION-based slicing!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║         PREDICATE STREAM PATTERN (Java 9+)                    ║");
        System.out.println("║  Efficient conditional slicing of streams                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        TakeWhileExample.demonstrate();
        DropWhileExample.demonstrate();
        CombinedExample.demonstrate();
        PerformanceExample.demonstrate();
        BatchProcessingExample.demonstrate();
        PaginationExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • takeWhile(): Take until condition fails (short-circuit)     ║");
        System.out.println("║  • dropWhile(): Skip until condition fails, then take all      ║");
        System.out.println("║  • REQUIRES sorted/ordered data for predictable behavior       ║");
        System.out.println("║  • More efficient than filter() on large sorted datasets       ║");
        System.out.println("║  • Use case: Batch processing, windowing, thresholds           ║");
        System.out.println("║  • Introduced in: Java 9                                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
