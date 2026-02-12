package com.calvin.fp.streams;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * ADVANCED STREAM API & FUNCTIONAL PATTERNS
 * ==========================================
 * 
 * The Stream API is Java's implementation of functional data pipelines.
 * It enables declarative data processing:
 * - Intermediate operations: filter, map, flatMap (lazy evaluation)
 * - Terminal operations: forEach, collect, reduce (triggers computation)
 * 
 * Key insight: Streams don't do work until a terminal operation is called.
 * This lazy evaluation enables optimizations and parallel processing.
 * 
 * @author Principal Software Engineer - FinTech Team
 */
public class AdvancedStreamPatterns {
	
	record Transaction(String id, double amount, String type, String account) {}
	record Account(String id, String owner, double balance) {}
	
	/**
	 * PATTERN 1: FILTER-MAP-REDUCE
	 * =============================
	 * The holy trinity of functional data processing.
	 */
	static class FilterMapReduce {
		static void demonstrate() {
			System.out.println("\n=== FILTER-MAP-REDUCE PATTERN ===");
			
			List<Transaction> txns = Arrays.asList(
				new Transaction("T1", 100, "DEBIT", "ACC1"),
				new Transaction("T2", 250, "CREDIT", "ACC1"),
				new Transaction("T3", 75, "DEBIT", "ACC2"),
				new Transaction("T4", 500, "CREDIT", "ACC1")
			);
			
			// Filter: Keep only debits over $100
			// Map: Extract amounts
			// Reduce: Sum them up
			double result = txns.stream()
				.filter(t -> t.type().equals("DEBIT"))
				.filter(t -> t.amount() > 100)
				.map(t -> t.amount())
				.reduce(0.0, Double::sum);
			
			System.out.println("Total debits > $100: $" + result);
		}
	}
	
	/**
	 * PATTERN 2: PARTITION
	 * =====================
	 * Split data into two groups based on a condition.
	 * Useful for: separating valid/invalid, approved/rejected, etc.
	 */
	static class PartitionPattern {
		static void demonstrate() {
			System.out.println("\n=== PARTITION PATTERN ===");
			
			List<Transaction> txns = Arrays.asList(
				new Transaction("T1", 100, "DEBIT", "ACC1"),
				new Transaction("T2", 5000, "CREDIT", "ACC1"),
				new Transaction("T3", 75, "DEBIT", "ACC2"),
				new Transaction("T4", 500, "CREDIT", "ACC1")
			);
			
			// Partition into large (>200) and small
			Map<Boolean, List<Transaction>> partition = txns.stream()
				.collect(Collectors.partitioningBy(t -> t.amount() > 200));
			
			System.out.println("Large transactions (>$200):");
			partition.get(true).forEach(t -> 
				System.out.println("  " + t.id() + ": $" + t.amount()));
			System.out.println("Small transactions (≤$200):");
			partition.get(false).forEach(t -> 
				System.out.println("  " + t.id() + ": $" + t.amount()));
		}
	}
	
	/**
	 * PATTERN 3: GROUP BY
	 * ====================
	 * Organize data into categories.
	 * Real-world: Group transactions by account, by date, by type, etc.
	 */
	static class GroupByPattern {
		static void demonstrate() {
			System.out.println("\n=== GROUP BY PATTERN ===");
			
			List<Transaction> txns = Arrays.asList(
				new Transaction("T1", 100, "DEBIT", "ACC1"),
				new Transaction("T2", 250, "CREDIT", "ACC1"),
				new Transaction("T3", 75, "DEBIT", "ACC2"),
				new Transaction("T4", 500, "CREDIT", "ACC1"),
				new Transaction("T5", 200, "DEBIT", "ACC2")
			);
			
			// Group by account
			Map<String, List<Transaction>> byAccount = txns.stream()
				.collect(Collectors.groupingBy(t -> t.account()));
			
			System.out.println("Transactions by account:");
			byAccount.forEach((account, transactions) -> {
				double total = transactions.stream()
					.mapToDouble(t -> t.amount())
					.sum();
				System.out.println("  " + account + ": " + transactions.size() + 
					" transactions, total: $" + total);
			});
			
			// Group by type and count
			Map<String, Long> countByType = txns.stream()
				.collect(Collectors.groupingBy(
					t -> t.type(),
					Collectors.counting()
				));
			
			System.out.println("\nCount by type:");
			countByType.forEach((type, count) -> 
				System.out.println("  " + type + ": " + count));
		}
	}
	
	/**
	 * PATTERN 4: FLATMAP - "Flatten" nested structures
	 * ==================================================
	 * Transform each item into a stream, then flatten all results.
	 * Use when: One item expands into multiple items.
	 */
	static class FlatMapPattern {
		static void demonstrate() {
			System.out.println("\n=== FLATMAP PATTERN ===");
			
			// Scenario: Multiple accounts, each with transactions
			Map<String, List<Transaction>> accountTxns = new HashMap<>();
			accountTxns.put("ACC1", Arrays.asList(
				new Transaction("T1", 100, "DEBIT", "ACC1"),
				new Transaction("T2", 250, "CREDIT", "ACC1")
			));
			accountTxns.put("ACC2", Arrays.asList(
				new Transaction("T3", 75, "DEBIT", "ACC2")
			));
			
			// FlatMap: Convert each account's list into a stream, then flatten
			System.out.println("All transactions (flattened):");
			accountTxns.values().stream()
				.flatMap(List::stream)  // Flatten List<List<T>> to List<T>
				.forEach(t -> System.out.println("  " + t.id() + ": $" + t.amount()));
			
			// Calculate total across all accounts
			double grandTotal = accountTxns.values().stream()
				.flatMap(List::stream)
				.mapToDouble(t -> t.amount())
				.sum();
			System.out.println("Grand total: $" + grandTotal);
		}
	}
	
	/**
	 * PATTERN 5: LAZY EVALUATION
	 * ===========================
	 * Streams don't execute until a terminal operation is called.
	 * This allows optimizations and short-circuiting.
	 */
	static class LazyEvaluationPattern {
		static void demonstrate() {
			System.out.println("\n=== LAZY EVALUATION PATTERN ===");
			
			List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			
			System.out.println("Processing with lazy evaluation:");
			
			// This doesn't execute anything yet - just sets up the pipeline
			Stream<Integer> lazy = numbers.stream()
				.filter(n -> {
					System.out.println("    Filter called on: " + n);
					return n > 3;
				})
				.map(n -> {
					System.out.println("    Map called on: " + n);
					return n * 2;
				});
			
			System.out.println("(Notice: nothing printed above - pipeline not executed yet)\n");
			
			System.out.println("Now calling terminal operation (limit(2)):");
			lazy
				.limit(2)  // Terminal operation!
				.forEach(n -> System.out.println("      Result: " + n));
			
			System.out.println("\n(Notice: only processed first 3 numbers - short-circuit optimization!)");
		}
	}
	
	/**
	 * PATTERN 6: PARALLEL STREAMS
	 * =============================
	 * Process data in parallel for CPU-bound tasks.
	 * Warning: Use only when processing is expensive; overhead isn't worth it for simple operations.
	 */
	static class ParallelStreamsPattern {
		static void demonstrate() {
			System.out.println("\n=== PARALLEL STREAMS PATTERN ===");
			
			List<Integer> numbers = new ArrayList<>();
			for (int i = 1; i <= 1_000_000; i++) {
				numbers.add(i);
			}
			
			// Sequential sum
			long start = System.currentTimeMillis();
			long sum1 = numbers.stream().mapToLong(n -> n).sum();
			long seq = System.currentTimeMillis() - start;
			
			// Parallel sum
			start = System.currentTimeMillis();
			long sum2 = numbers.parallelStream().mapToLong(n -> n).sum();
			long par = System.currentTimeMillis() - start;
			
			System.out.println("Sequential (1M items): " + seq + "ms");
			System.out.println("Parallel (1M items): " + par + "ms");
			System.out.println("Speedup: " + (seq > 0 ? String.format("%.2f", (double)seq/par) : "N/A") + "x");
			System.out.println("(Note: parallel might be slower for small data due to overhead)");
		}
	}
	
	/**
	 * PATTERN 7: INTERMEDIATE vs TERMINAL OPERATIONS
	 * ================================================
	 * Understanding what triggers computation.
	 */
	static class OperationTypesPattern {
		static void demonstrate() {
			System.out.println("\n=== INTERMEDIATE VS TERMINAL OPERATIONS ===");
			
			List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
			
			// Intermediate operations: filter, map, flatMap, limit, etc.
			// They return a Stream and DON'T execute immediately
			System.out.println("Intermediate (don't execute):");
			Stream<Integer> intermediate = numbers.stream()
				.filter(n -> n > 2)  // intermediate
				.map(n -> n * 2);    // intermediate
			System.out.println("  (Pipeline created but not executed yet)");
			
			// Terminal operations: forEach, collect, reduce, count, etc.
			// They return a non-Stream result and TRIGGER execution
			System.out.println("\nTerminal (execute the pipeline):");
			List<Integer> result = intermediate.collect(Collectors.toList());
			System.out.println("  Result: " + result);
		}
	}
	
	/**
	 * PATTERN 8: CUSTOM COLLECTORS
	 * =============================
	 * Create custom collectors for specialized aggregations.
	 */
	static class CustomCollectorsPattern {
		static void demonstrate() {
			System.out.println("\n=== CUSTOM COLLECTORS ===");
			
			List<Transaction> txns = Arrays.asList(
				new Transaction("T1", 100, "DEBIT", "ACC1"),
				new Transaction("T2", 250, "CREDIT", "ACC1"),
				new Transaction("T3", 75, "DEBIT", "ACC2"),
				new Transaction("T4", 500, "CREDIT", "ACC1")
			);
			
			// Using built-in collectors
			Map<String, Long> countByType = txns.stream()
				.collect(Collectors.groupingBy(
					t -> t.type(),
					Collectors.counting()
				));
			
			System.out.println("Count by type: " + countByType);
			
			// Using joining collector
			String transactionIds = txns.stream()
				.map(t -> t.id())
				.collect(Collectors.joining(", "));
			
			System.out.println("All IDs: " + transactionIds);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("╔═══════════════════════════════════════════════════════╗");
		System.out.println("║   ADVANCED STREAM API & FUNCTIONAL PATTERNS           ║");
		System.out.println("║   Mastering Java's Declarative Data Processing        ║");
		System.out.println("╚═══════════════════════════════════════════════════════╝");
		
		FilterMapReduce.demonstrate();
		PartitionPattern.demonstrate();
		GroupByPattern.demonstrate();
		FlatMapPattern.demonstrate();
		LazyEvaluationPattern.demonstrate();
		ParallelStreamsPattern.demonstrate();
		OperationTypesPattern.demonstrate();
		CustomCollectorsPattern.demonstrate();
		
		System.out.println("\n╔═══════════════════════════════════════════════════════╗");
		System.out.println("║   All advanced patterns demonstrated!                ║");
		System.out.println("╚═══════════════════════════════════════════════════════╝");
	}
}
