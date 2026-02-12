package com.calvin.fp.principles;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * FUNCTIONAL PROGRAMMING PRINCIPLES IN JAVA
 * ==========================================
 * 
 * This class demonstrates the 5 core principles of functional programming:
 * 1. Immutability - Don't change data; create new versions instead
 * 2. Statelessness - Functions don't rely on external state
 * 3. Declarative Pipelines - Describe WHAT, not HOW
 * 4. Concurrency-Safe - No mutable shared state = thread-safe by default
 * 5. Security Integration - Business rules as pure function chains
 * 
 * Think of it this way: Functional programming is like following a recipe precisely
 * (given the same ingredients, you always get the same result), versus imperative
 * programming which is like giving someone instructions to "find something to eat"
 * (the result depends on what's in the kitchen at that moment).
 * 
 * @author Principal Software Engineer - FinTech Team
 */
public class FunctionalPrinciples {

	/**
	 * PRINCIPLE 1: IMMUTABILITY
	 * ========================
	 * Rule: Never modify existing data. Instead, create new copies with changes.
	 * 
	 * Real-world analogy: In banking, you don't erase transaction history and rewrite it.
	 * You create a NEW ledger entry for each transaction. This maintains an audit trail
	 * and prevents corruption.
	 */
	static class ImmutabilityExample {
		
		// Imperative (BAD - mutates the list)
		static List<Integer> addTransactionImperative(List<Integer> transactions, int newAmount) {
			transactions.add(newAmount);  // MUTATES original! Bad for concurrency, bad for debugging
			return transactions;
		}
		
		// Functional (GOOD - creates new list)
		static List<Integer> addTransactionFunctional(List<Integer> transactions, int newAmount) {
			// Create a new list without mutating the original
			return transactions.stream()
				.collect(Collectors.toList());  // Creates new list
			// Would add: return Stream.concat(...) for appending
		}
	}
	
	/**
	 * PRINCIPLE 2: STATELESSNESS
	 * ==========================
	 * Rule: Pure functions don't depend on external variables or change them.
	 * Given the same input, a pure function ALWAYS returns the same output.
	 * 
	 * Benefit: Predictable behavior, easier testing, safe for multi-threading.
	 */
	static class StatelessnessExample {
		
		// Stateful (BAD - depends on external state)
		private static double exchangeRate = 1.1;  // Sneaky global state!
		
		static double convertCurrencyStateful(double amount) {
			return amount * exchangeRate;  // Result depends on when you call it
		}
		
		// Stateless (GOOD - pure function)
		static double convertCurrencyPure(double amount, double rate) {
			return amount * rate;  // Same input = same output, always!
		}
	}
	
	/**
	 * PRINCIPLE 3: DECLARATIVE PIPELINES
	 * ==================================
	 * Rule: Describe WHAT you want to happen, not HOW to do it.
	 * 
	 * The Stream API lets you chain operations: filter → map → reduce
	 * You don't worry about loops; you describe transformations.
	 */
	static class DeclarativePipelinesExample {
		
		// Imperative (tells the computer HOW)
		static Double calculateTotalImperative(List<Integer> amounts, double taxRate) {
			Double total = 0.0;
			for (Integer amount : amounts) {  // HOW: iterate with a loop
				if (amount > 100) {  // HOW: check condition
					total += amount * (1 + taxRate);  // HOW: multiply and add
				}
			}
			return total;
		}
		
		// Functional (describes WHAT we want)
		static Double calculateTotalFunctional(List<Integer> amounts, double taxRate) {
			return amounts.stream()
				.filter(amt -> amt > 100)  // WHAT: keep large amounts
				.map(amt -> amt * (1 + taxRate))  // WHAT: apply tax
				.reduce(0.0, Double::sum);  // WHAT: sum them up
			// The Stream API handles the HOW for us!
		}
	}
	
	/**
	 * PRINCIPLE 4: CONCURRENCY-SAFE
	 * =============================
	 * Rule: No mutable shared state = safe for parallel processing.
	 * 
	 * If you don't change data and don't use external state, threads can't
	 * interfere with each other. Each thread gets its own data flow.
	 */
	static class ConcurrencySafeExample {
		
		// Dangerous (BAD - shared mutable state)
		static class SharedCounter {
			private int count = 0;  // Shared! Thread-unsafe!
			
			void increment() {
				count++;  // Race condition! Multiple threads corrupt this
			}
		}
		
		// Safe (GOOD - functional stream approach)
		static long countParallel(List<Integer> amounts, int threshold) {
			return amounts.parallelStream()  // Safe parallel processing
				.filter(amt -> amt > threshold)  // No shared state
				.count();  // Thread-safe aggregation
		}
	}
	
	/**
	 * PRINCIPLE 5: SECURITY & COMPLIANCE
	 * ===================================
	 * Rule: Embed business rules (compliance checks) as pure function chains.
	 * 
	 * Each function is a validation step: does this transaction comply?
	 * Easier to audit, easier to change rules, easier to test compliance.
	 */
	static class SecurityComplianceExample {
		
		// A transaction that needs compliance checks
		record Transaction(String id, double amount, String country, String userId) {}
		
		// Compliance checks as pure functions (easy to test, easy to audit)
		static Predicate<Transaction> isAmountValid = t -> t.amount() > 0 && t.amount() < 1_000_000;
		static Predicate<Transaction> isSanctionedCountry = t -> 
			!List.of("BadCountry1", "BadCountry2").contains(t.country());
		static Predicate<Transaction> isKycVerified = t -> true;  // Placeholder
		
		// Combine all checks into a pipeline
		static boolean isComplianceApproved(Transaction t) {
			return isAmountValid
				.and(isSanctionedCountry)
				.and(isKycVerified)
				.test(t);
		}
	}
	
	// Demonstrate all principles
	public static void main(String[] args) {
		System.out.println("=== FUNCTIONAL PROGRAMMING PRINCIPLES ===\n");
		
		// 1. Immutability
		List<Integer> transactions = new ArrayList<>(List.of(50, 200, 75));
		System.out.println("1. IMMUTABILITY");
		System.out.println("Original: " + transactions);
		List<Integer> newTransactions = transactions.stream()
			.map(t -> t * 2)
			.collect(Collectors.toList());
		System.out.println("After doubling (functional): " + newTransactions);
		System.out.println("Original unchanged: " + transactions + "\n");
		
		// 2. Statelessness
		System.out.println("2. STATELESSNESS (Pure Functions)");
		double pure1 = StatelessnessExample.convertCurrencyPure(100, 1.1);
		double pure2 = StatelessnessExample.convertCurrencyPure(100, 1.1);
		System.out.println("Same input, same output (pure): " + pure1 + " == " + pure2 + "\n");
		
		// 3. Declarative
		System.out.println("3. DECLARATIVE PIPELINES");
		List<Integer> amounts = Arrays.asList(50, 150, 75, 200, 100);
		Double total = DeclarativePipelinesExample.calculateTotalFunctional(amounts, 0.1);
		System.out.println("Total with tax (declarative): $" + total + "\n");
		
		// 4. Concurrency-Safe
		System.out.println("4. CONCURRENCY-SAFE");
		long largeTransactions = ConcurrencySafeExample.countParallel(amounts, 100);
		System.out.println("Transactions > $100 (thread-safe parallel): " + largeTransactions + "\n");
		
		// 5. Security & Compliance
		System.out.println("5. SECURITY & COMPLIANCE");
		var tx = new SecurityComplianceExample.Transaction("TX001", 50_000, "USA", "user123");
		System.out.println("Transaction compliant? " + 
			SecurityComplianceExample.isComplianceApproved(tx) + "\n");
	}
}
