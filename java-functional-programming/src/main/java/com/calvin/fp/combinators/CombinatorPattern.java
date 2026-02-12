package com.calvin.fp.combinators;

import java.util.*;
import java.util.function.*;

/**
 * COMBINATOR PATTERN IN FUNCTIONAL PROGRAMMING
 * =============================================
 * 
 * The Combinator Pattern is like LEGO blocks: you create small, pure functions
 * that do ONE thing well, then combine them to build complex behaviors.
 * 
 * Think of it like building a financial transaction validator:
 * - Small combinators: Check amount, check country, check user
 * - Combined: Check all to approve/reject a transaction
 * 
 * Benefits:
 * 1. Reusable: Use each validator independently
 * 2. Testable: Test each validator in isolation
 * 3. Composable: Combine validators in any way
 * 4. Maintainable: Changing a validator doesn't break others
 * 
 * @author Principal Software Engineer - FinTech Team
 */
public class CombinatorPattern {
	
	/**
	 * BASIC COMBINATOR EXAMPLE
	 * ========================
	 * Create small validators (combinators) and combine them.
	 */
	static class BasicCombinator {
		record Order(String id, double amount, String country, String paymentMethod) {}
		
		// Individual validator functions (small, pure, focused)
		static Predicate<Order> validateAmount = order -> 
			order.amount() > 0 && order.amount() < 1_000_000;
		
		static Predicate<Order> validateCountry = order -> 
			!List.of("SanctionedA", "SanctionedB").contains(order.country());
		
		static Predicate<Order> validatePayment = order -> 
			List.of("CARD", "TRANSFER", "CRYPTO").contains(order.paymentMethod());
		
		// Combine validators using 'and'
		static Predicate<Order> isValidOrder = validateAmount
			.and(validateCountry)
			.and(validatePayment);
		
		static void demonstrate() {
			System.out.println("\n=== BASIC COMBINATOR (Validation Chain) ===");
			
			Order goodOrder = new Order("O1", 5000, "USA", "CARD");
			Order badOrder1 = new Order("O2", 0, "USA", "CARD");
			Order badOrder2 = new Order("O3", 1_500_000, "USA", "CARD");
			Order badOrder3 = new Order("O4", 500, "SanctionedA", "CARD");
			
			System.out.println("Good order: " + goodOrder.id() + " -> " + 
				isValidOrder.test(goodOrder));
			System.out.println("Bad order (zero amount): " + badOrder1.id() + " -> " + 
				isValidOrder.test(badOrder1));
			System.out.println("Bad order (too much): " + badOrder2.id() + " -> " + 
				isValidOrder.test(badOrder2));
			System.out.println("Bad order (sanctioned): " + badOrder3.id() + " -> " + 
				isValidOrder.test(badOrder3));
		}
	}
	
	/**
	 * ADVANCED COMBINATOR: Function Combinators
	 * ===========================================
	 * Compose functions to build complex data transformation pipelines.
	 */
	static class FunctionCombinator {
		
		// Domain class
		record Price(double value) {
			Price apply(Function<Double, Double> transform) {
				return new Price(transform.apply(this.value));
			}
		}
		
		// Combinator: Create transformation pipelines
		static class PriceCalculator {
			Function<Double, Double> applyDiscount;
			Function<Double, Double> applyTax;
			Function<Double, Double> roundToNearest;
			
			PriceCalculator(double discountPercent, double taxPercent) {
				this.applyDiscount = price -> price * (1 - discountPercent/100);
				this.applyTax = price -> price * (1 + taxPercent/100);
				this.roundToNearest = price -> Math.round(price * 100);
			}
			
			// Combine functions into a pipeline
			Function<Double, Double> buildPipeline() {
				return applyDiscount
					.andThen(applyTax)
					.andThen(roundToNearest);
			}
		}
		
		static void demonstrate() {
			System.out.println("\n=== FUNCTION COMBINATOR (Transformation Pipeline) ===");
			
			PriceCalculator calc = new PriceCalculator(10, 8);  // 10% discount, 8% tax
			Function<Double, Double> pipeline = calc.buildPipeline();
			
			double originalPrice = 100.0;
			double finalPrice = pipeline.apply(originalPrice) / 100;
			
			System.out.println("Original: $" + originalPrice);
			System.out.println("After discount, tax, rounding: $" + 
				String.format("%.2f", finalPrice));
		}
	}
	
	/**
	 * RESULT COMBINATOR PATTERN
	 * =========================
	 * Create a Result type that combines success/failure logic.
	 * Inspired by functional languages, but implemented in Java.
	 */
	static class ResultCombinator {
		
		// A Result that can be Success or Failure
		abstract static class Result<T> {
			abstract <R> Result<R> map(Function<T, R> fn);
			abstract <R> Result<R> flatMap(Function<T, Result<R>> fn);
			abstract T getOrElse(T defaultValue);
			
			static <T> Result<T> success(T value) {
				return new Success<>(value);
			}
			
			static <T> Result<T> failure(Exception e) {
				return new Failure<>(e);
			}
			
			static class Success<T> extends Result<T> {
				final T value;
				Success(T value) { this.value = value; }
				
				@Override
				<R> Result<R> map(Function<T, R> fn) {
					try {
						return success(fn.apply(value));
					} catch (Exception e) {
						return failure(e);
					}
				}
				
				@Override
				<R> Result<R> flatMap(Function<T, Result<R>> fn) {
					try {
						return fn.apply(value);
					} catch (Exception e) {
						return failure(e);
					}
				}
				
				@Override
				T getOrElse(T defaultValue) { return value; }
				
				@Override
				public String toString() { return "Success(" + value + ")"; }
			}
			
			static class Failure<T> extends Result<T> {
				final Exception error;
				Failure(Exception error) { this.error = error; }
				
				@Override
				<R> Result<R> map(Function<T, R> fn) {
					return new Failure<>(error);
				}
				
				@Override
				<R> Result<R> flatMap(Function<T, Result<R>> fn) {
					return new Failure<>(error);
				}
				
				@Override
				T getOrElse(T defaultValue) { return defaultValue; }
				
				@Override
				public String toString() { return "Failure(" + error.getMessage() + ")"; }
			}
		}
		
		// Use Result combinator for safe processing
		static Result<Double> parseAmount(String str) {
			try {
				return Result.success(Double.parseDouble(str));
			} catch (NumberFormatException e) {
				return Result.failure(e);
			}
		}
		
		static Result<Double> applyDiscount(Double amount) {
			return amount < 0 
				? Result.failure(new IllegalArgumentException("Negative amount"))
				: Result.success(amount * 0.9);
		}
		
		static void demonstrate() {
			System.out.println("\n=== RESULT COMBINATOR (Success/Failure Chain) ===");
			
			// Chain operations safely
			Result<Double> result1 = parseAmount("100")
				.flatMap(CombinatorPattern.ResultCombinator::applyDiscount);
			
			Result<Double> result2 = parseAmount("invalid")
				.flatMap(CombinatorPattern.ResultCombinator::applyDiscount);
			
			Result<Double> result3 = parseAmount("-50")
				.flatMap(CombinatorPattern.ResultCombinator::applyDiscount);
			
			System.out.println("Valid amount (100) after discount: " + result1);
			System.out.println("Invalid amount: " + result2);
			System.out.println("Negative amount: " + result3);
		}
	}
	
	/**
	 * REAL-WORLD COMBINATOR: Transaction Processor
	 * ==============================================
	 * Build a financial transaction processor using combinators.
	 */
	static class TransactionProcessor {
		record Transaction(String id, double amount, String country, String userId) {}
		
		// Small checker functions (combinators)
		static Predicate<Transaction> checkAmount = tx -> 
			tx.amount > 0 && tx.amount < 1_000_000;
		
		static Predicate<Transaction> checkSanctions = tx -> 
			!List.of("SANCTIONED_COUNTRY").contains(tx.country);
		
		static Predicate<Transaction> checkUser = tx -> 
			!tx.userId.isEmpty();
		
		// Combine checkers
		static Predicate<Transaction> completeCheck = checkAmount
			.and(checkSanctions)
			.and(checkUser);
		
		// Transformer functions
		static Function<Transaction, Transaction> addTimestamp = tx -> {
			long timestamp = System.currentTimeMillis();
			return new Transaction(tx.id + "-" + timestamp, tx.amount, 
				tx.country, tx.userId);
		};
		
		static Function<Transaction, String> formatForLogging = tx -> 
			String.format("TXN[%s] $%.2f from %s", tx.id, tx.amount, tx.country);
		
		static void demonstrate() {
			System.out.println("\n=== REAL-WORLD COMBINATOR (Transaction Processor) ===");
			
			List<Transaction> transactions = Arrays.asList(
				new Transaction("T1", 5000, "USA", "user1"),
				new Transaction("T2", 0, "USA", "user2"),
				new Transaction("T3", 50000, "SANCTIONED_COUNTRY", "user3"),
				new Transaction("T4", 1000, "UK", "user4")
			);
			
			System.out.println("Processing transactions:");
			transactions.stream()
				.peek(tx -> System.out.print("  " + tx.id + ": "))
				.filter(completeCheck)
				.map(addTimestamp)
				.map(formatForLogging)
				.forEach(System.out::println);
				
			System.out.println("\nTotal valid transactions: " + 
				transactions.stream().filter(completeCheck).count());
		}
	}
	
	public static void main(String[] args) {
		System.out.println("╔═══════════════════════════════════════════════════════╗");
		System.out.println("║   COMBINATOR PATTERN IN FUNCTIONAL PROGRAMMING        ║");
		System.out.println("║   Building Complex Behaviors from Simple Functions    ║");
		System.out.println("╚═══════════════════════════════════════════════════════╝");
		
		BasicCombinator.demonstrate();
		FunctionCombinator.demonstrate();
		ResultCombinator.demonstrate();
		TransactionProcessor.demonstrate();
		
		System.out.println("\n╔═══════════════════════════════════════════════════════╗");
		System.out.println("║   All combinator examples completed!                 ║");
		System.out.println("╚═══════════════════════════════════════════════════════╝");
	}
}
