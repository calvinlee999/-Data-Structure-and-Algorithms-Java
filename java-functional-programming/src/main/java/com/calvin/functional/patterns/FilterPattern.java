package com.calvin.functional.patterns;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * FILTER PATTERN
 * 
 * Think of filter like a security guard checking IDs at a club!
 * Only elements that pass the test (Predicate) get through.
 * 
 * Real-world analogy: Like sorting apples - you inspect each one,
 * and only the good ones go into the basket. Bad ones stay out.
 * 
 * @author FinTech Principal Software Engineer
 */
public class FilterPattern {

    // Data models for financial scenarios
    record Transaction(String id, double amount, String type, boolean verified, String account) {}
    record Customer(String id, String name, int age, String membershipLevel, double creditScore) {}
    record Payment(String id, double amount, String status, long timestamp) {}

    /**
     * PATTERN 1: Simple Filter - Keep elements that match condition
     * Predicate: Takes T, returns boolean
     */
    static class SimpleFilterExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Simple Filter ===");
            System.out.println("Goal: Find all large transactions (amount > $1000)\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T001", 500.0, "DEPOSIT", true, "ACC001"),
                new Transaction("T002", 1500.0, "WITHDRAWAL", true, "ACC002"),
                new Transaction("T003", 250.0, "TRANSFER", false, "ACC001"),
                new Transaction("T004", 5000.0, "DEPOSIT", true, "ACC003"),
                new Transaction("T005", 750.0, "PAYMENT", true, "ACC002")
            );
            
            // OLD WAY: Using if statements in loops
            System.out.println("❌ OLD WAY (Imperative):");
            List<Transaction> large_old = new ArrayList<>();
            for (Transaction tx : transactions) {
                if (tx.amount > 1000.0) {
                    large_old.add(tx);
                }
            }
            System.out.println("  Found " + large_old.size() + " large transactions");
            System.out.println("  Problem: Loop boilerplate, error-prone\n");
            
            // NEW WAY: Using filter with Predicate
            System.out.println("✅ NEW WAY (Functional):");
            Predicate<Transaction> isLarge = tx -> tx.amount > 1000.0;
            
            List<Transaction> largeTransactions = transactions.stream()
                .filter(isLarge)                  // Keep only large ones
                .collect(Collectors.toList());
            
            System.out.println("  Large transactions:");
            largeTransactions.forEach(tx -> 
                System.out.println("    " + tx.id + ": $" + tx.amount));
            
            System.out.println("\n  Benefits: Clear intent, reusable predicate!");
        }
    }

    /**
     * PATTERN 2: Predicate Chaining - Combine multiple conditions
     * Using and(), or(), negate()
     */
    static class PredicateChainingExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Predicate Chaining ===");
            System.out.println("Goal: Find verified AND large transactions\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T001", 500.0, "DEPOSIT", true, "ACC001"),
                new Transaction("T002", 1500.0, "WITHDRAWAL", true, "ACC002"),
                new Transaction("T003", 2500.0, "TRANSFER", false, "ACC001"),
                new Transaction("T004", 5000.0, "DEPOSIT", true, "ACC003")
            );
            
            // Define individual predicates (reusable)
            Predicate<Transaction> isLarge = tx -> tx.amount > 1000.0;
            Predicate<Transaction> isVerified = Transaction::verified;
            Predicate<Transaction> isDeposit = tx -> tx.type.equals("DEPOSIT");
            
            // Chain with .and()
            System.out.println("Filter 1: Large AND Verified:");
            Predicate<Transaction> largeAndVerified = isLarge.and(isVerified);
            
            transactions.stream()
                .filter(largeAndVerified)
                .forEach(tx -> System.out.println("  ✅ " + tx.id + ": $" + tx.amount));
            
            // Chain with .or()
            System.out.println("\nFilter 2: Large OR Unverified:");
            Predicate<Transaction> largeOrUnverified = isLarge.or(isVerified.negate());
            
            transactions.stream()
                .filter(largeOrUnverified)
                .forEach(tx -> System.out.println("  ⚠️  " + tx.id + ": $" + tx.amount));
            
            // Complex: (Large AND Verified) OR Deposit
            System.out.println("\nFilter 3: (Large AND Verified) OR Deposit:");
            Predicate<Transaction> complex = isLarge.and(isVerified).or(isDeposit);
            
            transactions.stream()
                .filter(complex)
                .forEach(tx -> System.out.println("  💰 " + tx.id + ": $" + tx.amount));
            
            System.out.println("\nBenefit: Build complex rules from simple ones!");
        }
    }

    /**
     * PATTERN 3: Filter with Multiple Criteria
     * Real-world customer segmentation
     */
    static class CustomerSegmentationExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Customer Segmentation ===");
            System.out.println("Goal: Find premium customers for special offers\n");
            
            List<Customer> customers = List.of(
                new Customer("C001", "Alice", 35, "GOLD", 750.0),
                new Customer("C002", "Bob", 28, "SILVER", 680.0),
                new Customer("C003", "Carol", 42, "PLATINUM", 820.0),
                new Customer("C004", "Dave", 55, "GOLD", 710.0),
                new Customer("C005", "Eve", 31, "BRONZE", 620.0)
            );
            
            // Business rule: Premium = (Age > 30 AND CreditScore > 700) OR Platinum
            Predicate<Customer> isPremium = c -> 
                (c.age > 30 && c.creditScore > 700) || 
                c.membershipLevel.equals("PLATINUM");
            
            System.out.println("Premium Customers:");
            List<Customer> premiumCustomers = customers.stream()
                .filter(isPremium)
                .collect(Collectors.toList());
            
            premiumCustomers.forEach(c -> 
                System.out.println("  👤 " + c.name + " (" + c.membershipLevel + 
                    ", Score: " + c.creditScore + ")"));
            
            // Count by membership level
            System.out.println("\nPremium customers by level:");
            Map<String, Long> byLevel = premiumCustomers.stream()
                .collect(Collectors.groupingBy(
                    Customer::membershipLevel,
                    Collectors.counting()
                ));
            
            byLevel.forEach((level, count) -> 
                System.out.println("  " + level + ": " + count));
            
            System.out.println("\nBenefit: Complex business rules in one place!");
        }
    }

    /**
     * PATTERN 4: Filter with Method References
     * Cleaner syntax for simple checks
     */
    static class MethodReferenceFilterExample {
        
        // Helper methods for predicates
        static boolean isHighValue(Payment p) {
            return p.amount > 500.0;
        }
        
        static boolean isPending(Payment p) {
            return p.status.equals("PENDING");
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Filter with Method References ===");
            System.out.println("Goal: Use existing methods as filters\n");
            
            List<Payment> payments = List.of(
                new Payment("P001", 250.0, "COMPLETED", System.currentTimeMillis()),
                new Payment("P002", 750.0, "PENDING", System.currentTimeMillis()),
                new Payment("P003", 1200.0, "PENDING", System.currentTimeMillis()),
                new Payment("P004", 100.0, "FAILED", System.currentTimeMillis())
            );
            
            // Using method references (cleaner than lambdas)
            System.out.println("High-value pending payments:");
            payments.stream()
                .filter(MethodReferenceFilterExample::isHighValue)
                .filter(MethodReferenceFilterExample::isPending)
                .forEach(p -> System.out.println("  💵 " + p.id + ": $" + p.amount));
            
            System.out.println("\nBenefit: Reuse existing validation methods!");
        }
    }

    /**
     * PATTERN 5: Filter with Count and Statistics
     * Not just collecting, but analyzing
     */
    static class FilterStatisticsExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Filter + Statistics ===");
            System.out.println("Goal: Analyze filtered data\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T001", 500.0, "DEPOSIT", true, "ACC001"),
                new Transaction("T002", 1500.0, "WITHDRAWAL", true, "ACC002"),
                new Transaction("T003", 2500.0, "TRANSFER", false, "ACC001"),
                new Transaction("T004", 5000.0, "DEPOSIT", true, "ACC003"),
                new Transaction("T005", 750.0, "PAYMENT", true, "ACC002"),
                new Transaction("T006", 3000.0, "WITHDRAWAL", false, "ACC003")
            );
            
            // Filter and count
            long verifiedCount = transactions.stream()
                .filter(Transaction::verified)
                .count();
            
            // Filter and sum
            double verifiedTotal = transactions.stream()
                .filter(Transaction::verified)
                .mapToDouble(Transaction::amount)
                .sum();
            
            // Filter and average
            double verifiedAvg = transactions.stream()
                .filter(Transaction::verified)
                .mapToDouble(Transaction::amount)
                .average()
                .orElse(0.0);
            
            // Filter and group
            Map<String, Double> totalByType = transactions.stream()
                .filter(Transaction::verified)
                .collect(Collectors.groupingBy(
                    Transaction::type,
                    Collectors.summingDouble(Transaction::amount)
                ));
            
            System.out.println("Verified Transactions Analysis:");
            System.out.println("  Count: " + verifiedCount);
            System.out.println("  Total: $" + verifiedTotal);
            System.out.println("  Average: $" + verifiedAvg);
            System.out.println("\n  By Type:");
            totalByType.forEach((type, total) -> 
                System.out.println("    " + type + ": $" + total));
            
            System.out.println("\nBenefit: Filter once, analyze many ways!");
        }
    }

    /**
     * PATTERN 6: Filter with Distinct and Limit
     * Combining filter with other stream operations
     */
    static class CombinedFilterExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Combined Filter Operations ===");
            System.out.println("Goal: Top 3 unique high-value accounts\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("T001", 500.0, "DEPOSIT", true, "ACC001"),
                new Transaction("T002", 1500.0, "WITHDRAWAL", true, "ACC002"),
                new Transaction("T003", 2500.0, "TRANSFER", false, "ACC001"),
                new Transaction("T004", 5000.0, "DEPOSIT", true, "ACC003"),
                new Transaction("T005", 750.0, "PAYMENT", true, "ACC002"),
                new Transaction("T006", 3000.0, "WITHDRAWAL", false, "ACC001")
            );
            
            // Filter > 1000, get unique accounts, take first 3
            System.out.println("Top 3 accounts with transactions > $1000:");
            List<String> topAccounts = transactions.stream()
                .filter(tx -> tx.amount > 1000.0)     // Only large transactions
                .map(Transaction::account)             // Extract account IDs
                .distinct()                            // Remove duplicates
                .limit(3)                              // Take only 3
                .collect(Collectors.toList());
            
            topAccounts.forEach(acc -> System.out.println("  🏦 " + acc));
            
            System.out.println("\nBenefit: Combine operations for powerful queries!");
        }
    }

    // Main demonstration
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    FILTER PATTERN                              ║");
        System.out.println("║  Select elements that match conditions using Predicates        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        SimpleFilterExample.demonstrate();
        PredicateChainingExample.demonstrate();
        CustomerSegmentationExample.demonstrate();
        MethodReferenceFilterExample.demonstrate();
        FilterStatisticsExample.demonstrate();
        CombinedFilterExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • filter() keeps elements that pass a test (Predicate)        ║");
        System.out.println("║  • Chain predicates with and(), or(), negate()                 ║");
        System.out.println("║  • Reusable predicates = cleaner, testable code                ║");
        System.out.println("║  • Combine with map, distinct, limit for power queries         ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
