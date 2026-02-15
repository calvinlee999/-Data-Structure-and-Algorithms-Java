package com.calvin.streams;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream API in Spring Boot 3.2 + Java 21
 * 
 * <p>FinTech Principal Engineer's Guide to Declarative Data Processing</p>
 * 
 * <h2>What is the Stream API?</h2>
 * <p>
 * The Stream API enables <b>declarative data processing</b> using functional pipelines.
 * Instead of telling the computer HOW to do something (imperative loops), you tell it
 * WHAT you want (declarative transformations).
 * </p>
 * 
 * <h2>The Paradigm Shift: External vs Internal Iteration</h2>
 * <pre>
 * // ❌ Old Way: Internal Iteration (Imperative)
 * List&lt;Transaction&gt; highValue = new ArrayList&lt;&gt;();
 * for (Transaction tx : transactions) {
 *     if (tx.amount().compareTo(BigDecimal.valueOf(1000)) &gt; 0) {
 *         highValue.add(tx);
 *     }
 * }
 * 
 * // ✅ New Way: External Iteration (Declarative)
 * List&lt;Transaction&gt; highValue = transactions.stream()
 *     .filter(tx -&gt; tx.amount().compareTo(BigDecimal.valueOf(1000)) &gt; 0)
 *     .collect(Collectors.toList());
 * </pre>
 * 
 * <h2>Core Stream Operations</h2>
 * <ul>
 *   <li><b>filter()</b> - Keep elements that match a condition (Predicate)</li>
 *   <li><b>map()</b> - Transform each element (Function)</li>
 *   <li><b>flatMap()</b> - Flatten nested structures</li>
 *   <li><b>reduce()</b> - Combine elements into a single result</li>
 *   <li><b>collect()</b> - Gather results into a collection</li>
 *   <li><b>sorted()</b> - Order elements</li>
 *   <li><b>distinct()</b> - Remove duplicates</li>
 *   <li><b>limit()/skip()</b> - Pagination</li>
 * </ul>
 * 
 * <h2>Strategic Benefits for FinTech</h2>
 * <ul>
 *   <li><b>Readability</b> - Code reads like business requirements</li>
 *   <li><b>Maintainability</b> - Less code = fewer bugs</li>
 *   <li><b>Parallelism</b> - .parallelStream() for free concurrency</li>
 *   <li><b>Lazy Evaluation</b> - Only process what's needed</li>
 * </ul>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 * @version 1.0.0
 * @since 2026-02-15
 */
@Component
public class StreamAPIDemo implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║  Stream API in Spring Boot 3.2 + Java 21                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        demonstrateFilter();
        demonstrateMap();
        demonstrateReduce();
        demonstrateFlatMap();
        demonstrateCollectors();
        demonstrateParallelStreams();
        demonstrateLazyEvaluation();
    }

    /**
     * filter() - Keep Only High-Value Transactions
     * 
     * <p><b>FinTech Use Case:</b> Fraud detection by filtering high-value transactions</p>
     * <pre>
     * Imperative: Loop through all transactions, if amount > $1000, add to list
     * Declarative: Filter transactions where amount > $1000
     * </pre>
     */
    private void demonstrateFilter() {
        System.out.println("1️⃣  filter() - Fraud Detection: High-Value Transactions");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        List<Transaction> transactions = List.of(
            new Transaction("TX001", new BigDecimal("450.00"), "PURCHASE"),
            new Transaction("TX002", new BigDecimal("1200.00"), "PURCHASE"),
            new Transaction("TX003", new BigDecimal("75.50"), "REFUND"),
            new Transaction("TX004", new BigDecimal("2500.00"), "PURCHASE"),
            new Transaction("TX005", new BigDecimal("320.00"), "PURCHASE")
        );

        List<Transaction> highValue = transactions.stream()
            .filter(tx -> tx.amount().compareTo(BigDecimal.valueOf(1000)) > 0)
            .toList(); // Java 16+ shorthand for collect(Collectors.toList())

        System.out.println("📊 Total Transactions: " + transactions.size());
        System.out.println("🚨 High-Value Transactions (> $1000): " + highValue.size());
        highValue.forEach(tx -> System.out.println("  ├─ " + tx.id() + ": $" + tx.amount()));
        System.out.println("✅ Declarative filtering - no loops, no if statements\n");
    }

    /**
     * map() - Transform Transactions to Amount Summary
     * 
     * <p><b>FinTech Use Case:</b> Daily reconciliation report</p>
     * <pre>
     * Transform: List&lt;Transaction&gt; → List&lt;BigDecimal&gt; (amounts only)
     * </pre>
     */
    private void demonstrateMap() {
        System.out.println("2️⃣  map() - Daily Reconciliation: Extract Amounts");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        List<Transaction> transactions = List.of(
            new Transaction("TX001", new BigDecimal("450.00"), "PURCHASE"),
            new Transaction("TX002", new BigDecimal("1200.00"), "PURCHASE"),
            new Transaction("TX003", new BigDecimal("75.50"), "REFUND")
        );

        // Extract amounts
        List<BigDecimal> amounts = transactions.stream()
            .map(Transaction::amount) // Method reference: shorter than tx -> tx.amount()
            .toList();

        // Transform to formatted strings
        List<String> formattedAmounts = transactions.stream()
            .map(tx -> String.format("%s: $%,.2f", tx.id(), tx.amount()))
            .toList();

        System.out.println("💵 Extracted Amounts:");
        amounts.forEach(amt -> System.out.println("  ├─ $" + amt));
        
        System.out.println("\n📋 Formatted Summary:");
        formattedAmounts.forEach(formatted -> System.out.println("  ├─ " + formatted));
        System.out.println("✅ map() transforms each element to a new form\n");
    }

    /**
     * reduce() - Calculate Total Transaction Volume
     * 
     * <p><b>FinTech Use Case:</b> End-of-day settlement total</p>
     * <pre>
     * Combine all transaction amounts into a single total
     * reduce(identity, accumulator) → BigDecimal
     * </pre>
     */
    private void demonstrateReduce() {
        System.out.println("3️⃣  reduce() - End-of-Day Settlement: Total Volume");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        List<Transaction> transactions = List.of(
            new Transaction("TX001", new BigDecimal("450.00"), "PURCHASE"),
            new Transaction("TX002", new BigDecimal("1200.00"), "PURCHASE"),
            new Transaction("TX003", new BigDecimal("75.50"), "REFUND"),
            new Transaction("TX004", new BigDecimal("2500.00"), "PURCHASE")
        );

        // reduce() - combines all elements into a single result
        BigDecimal totalVolume = transactions.stream()
            .map(Transaction::amount)
            .reduce(BigDecimal.ZERO, BigDecimal::add); // identity=0, accumulator=add

        // Alternative: Using sum() for specialized streams
        double totalAsDouble = transactions.stream()
            .mapToDouble(tx -> tx.amount().doubleValue())
            .sum();

        System.out.println("📊 Transactions Processed: " + transactions.size());
        System.out.println("💰 Total Volume (reduce): $" + totalVolume);
        System.out.println("💰 Total Volume (sum): $" + String.format("%.2f", totalAsDouble));
        System.out.println("✅ reduce() combines all elements into a single value\n");
    }

    /**
     * flatMap() - Flatten Nested Account Structures
     * 
     * <p><b>FinTech Use Case:</b> Aggregate all transactions across multiple accounts</p>
     * <pre>
     * Customer → List&lt;Account&gt; → List&lt;Transaction&gt;
     * flatMap() flattens the nested structure into a single stream
     * </pre>
     */
    private void demonstrateFlatMap() {
        System.out.println("4️⃣  flatMap() - Multi-Account Transaction Aggregation");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        Account checking = new Account("CHECKING", List.of(
            new Transaction("TX001", new BigDecimal("100.00"), "PURCHASE"),
            new Transaction("TX002", new BigDecimal("250.00"), "PURCHASE")
        ));

        Account savings = new Account("SAVINGS", List.of(
            new Transaction("TX003", new BigDecimal("500.00"), "DEPOSIT"),
            new Transaction("TX004", new BigDecimal("75.00"), "WITHDRAWAL")
        ));

        List<Account> accounts = List.of(checking, savings);

        // Without flatMap (nested structure)
        List<List<Transaction>> nested = accounts.stream()
            .map(Account::transactions)
            .toList();

        // With flatMap (flattened structure)
        List<Transaction> allTransactions = accounts.stream()
            .flatMap(account -> account.transactions().stream())
            .toList();

        System.out.println("📊 Accounts: " + accounts.size());
        System.out.println("📊 Total Transactions (across all accounts): " + allTransactions.size());
        allTransactions.forEach(tx -> System.out.println("  ├─ " + tx.id() + ": $" + tx.amount()));
        System.out.println("✅ flatMap() flattens nested structures into a single stream\n");
    }

    /**
     * Collectors - Advanced Data Aggregation
     * 
     * <p><b>FinTech Use Case:</b> Transaction reporting and grouping</p>
     * <ul>
     *   <li>toList() - Collect into list</li>
     *   <li>toSet() - Remove duplicates</li>
     *   <li>toMap() - Create lookup tables</li>
     *   <li>groupingBy() - Group transactions by category</li>
     *   <li>partitioningBy() - Split into two groups (true/false)</li>
     * </ul>
     */
    private void demonstrateCollectors() {
        System.out.println("5️⃣  Collectors - Advanced Transaction Reporting");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        List<Transaction> transactions = List.of(
            new Transaction("TX001", new BigDecimal("450.00"), "PURCHASE"),
            new Transaction("TX002", new BigDecimal("1200.00"), "PURCHASE"),
            new Transaction("TX003", new BigDecimal("75.50"), "REFUND"),
            new Transaction("TX004", new BigDecimal("2500.00"), "PURCHASE"),
            new Transaction("TX005", new BigDecimal("320.00"), "REFUND")
        );

        // groupingBy - Group transactions by type
        Map<String, List<Transaction>> byType = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::type));

        System.out.println("📊 Transactions Grouped by Type:");
        byType.forEach((type, txList) -> {
            System.out.println("  ├─ " + type + ": " + txList.size() + " transactions");
            txList.forEach(tx -> System.out.println("      └─ " + tx.id() + ": $" + tx.amount()));
        });

        // partitioningBy - Split into high-value vs low-value
        Map<Boolean, List<Transaction>> partitioned = transactions.stream()
            .collect(Collectors.partitioningBy(tx -> tx.amount().compareTo(BigDecimal.valueOf(1000)) > 0));

        System.out.println("\n📊 Transactions Partitioned by Value:");
        System.out.println("  ├─ High-Value (> $1000): " + partitioned.get(true).size());
        System.out.println("  └─ Low-Value (≤ $1000): " + partitioned.get(false).size());
        System.out.println("✅ Collectors provide powerful data aggregation\n");
    }

    /**
     * Parallel Streams - Leverage Multi-Core CPUs
     * 
     * <p><b>FinTech Use Case:</b> Batch processing millions of transactions</p>
     * <p><b>⚠️ Warning:</b> Only use parallel streams for CPU-intensive operations, not I/O</p>
     */
    private void demonstrateParallelStreams() {
        System.out.println("6️⃣  Parallel Streams - Batch Processing at Scale");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Generate 1 million transactions
        List<Integer> transactionIds = IntStream.range(1, 1_000_001)
            .boxed()
            .toList();

        // Sequential processing
        long startSeq = System.currentTimeMillis();
        long countSeq = transactionIds.stream()
            .filter(id -> id % 2 == 0)
            .count();
        long endSeq = System.currentTimeMillis();

        // Parallel processing
        long startPar = System.currentTimeMillis();
        long countPar = transactionIds.parallelStream()
            .filter(id -> id % 2 == 0)
            .count();
        long endPar = System.currentTimeMillis();

        System.out.println("📊 Processing 1,000,000 transactions");
        System.out.println("  ├─ Sequential: " + (endSeq - startSeq) + "ms → " + countSeq + " even IDs");
        System.out.println("  └─ Parallel: " + (endPar - startPar) + "ms → " + countPar + " even IDs");
        System.out.println("💡 Parallel streams leverage multiple CPU cores");
        System.out.println("⚠️  Only use for CPU-intensive tasks, not I/O operations\n");
    }

    /**
     * Lazy Evaluation - Process Only What's Needed
     * 
     * <p><b>FinTech Use Case:</b> Early termination in fraud detection</p>
     * <p>Streams are <b>lazy</b> - they only execute when a terminal operation is called</p>
     */
    private void demonstrateLazyEvaluation() {
        System.out.println("7️⃣  Lazy Evaluation - Efficient Processing");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        List<Transaction> transactions = List.of(
            new Transaction("TX001", new BigDecimal("450.00"), "PURCHASE"),
            new Transaction("TX002", new BigDecimal("1200.00"), "PURCHASE"),
            new Transaction("TX003", new BigDecimal("2500.00"), "PURCHASE"),
            new Transaction("TX004", new BigDecimal("75.50"), "REFUND")
        );

        System.out.println("🔍 Finding first high-value transaction:");
        
        Optional<Transaction> firstHighValue = transactions.stream()
            .peek(tx -> System.out.println("  ├─ Processing: " + tx.id()))
            .filter(tx -> tx.amount().compareTo(BigDecimal.valueOf(1000)) > 0)
            .findFirst();

        firstHighValue.ifPresent(tx -> 
            System.out.println("\n✅ Found: " + tx.id() + " ($" + tx.amount() + ")")
        );
        
        System.out.println("💡 Stream stopped after finding first match (lazy evaluation)");
        System.out.println("📊 Processed only 2 transactions instead of all 4\n");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // Domain Models (Records)
    // ═══════════════════════════════════════════════════════════════════════════

    private record Transaction(String id, BigDecimal amount, String type) {}
    private record Account(String type, List<Transaction> transactions) {}
}
