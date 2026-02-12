package com.calvin.fp.interfaces;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * FUNCTIONAL INTERFACES IN JAVA
 * =============================
 * 
 * A functional interface has ONE abstract method. It's like a contract that says:
 * "I do one job, and here's what that job looks like."
 * 
 * Think of it like a vending machine: you insert money (input) and get a snack (output).
 * The machine doesn't care which snack button you press; it just follows the rule
 * programmed for that button.
 * 
 * Java provides 40+ built-in functional interfaces. We cover the essential ones here.
 * 
 * @author Principal Software Engineer - FinTech Team
 */
public class FunctionalInterfacesGuide {
	
	/**
	 * PREDICATE<T> - "Does this pass the test?"
	 * ==========================================
	 * Single method: boolean test(T t)
	 * 
	 * Use when: You need a YES/NO question (filtering, validation)
	 * Real analogy: Security guard checking if someone is on the VIP list
	 */
	static class PredicateExample {
		record Account(String id, double balance, boolean verified) {}
		
		static void demonstratePredicate() {
			System.out.println("\n=== PREDICATE<T> ===");
			
			// Create predicates (YES/NO questions)
			Predicate<Account> hasHighBalance = acc -> acc.balance() > 10_000;
			Predicate<Account> isVerified = acc -> acc.verified();
			
			// Combine predicates
			Predicate<Account> isPremiumAccount = hasHighBalance.and(isVerified);
			
			// Use with data
			List<Account> accounts = Arrays.asList(
				new Account("ACC001", 50_000, true),
				new Account("ACC002", 5_000, true),
				new Account("ACC003", 100_000, false)
			);
			
			System.out.println("Premium accounts:");
			accounts.stream()
				.filter(isPremiumAccount)
				.forEach(acc -> System.out.println("  " + acc.id() + ": $" + acc.balance()));
		}
	}
	
	/**
	 * CONSUMER<T> - "Do something with this"
	 * =======================================
	 * Single method: void accept(T t)
	 * 
	 * Use when: You need to perform an action (side effect) on each item
	 * Real analogy: A postal worker delivering a package (they receive it, do something with it)
	 */
	static class ConsumerExample {
		record Payment(String id, double amount, String status) {}
		
		static void demonstrateConsumer() {
			System.out.println("\n=== CONSUMER<T> ===");
			
			// Create consumers (action performers)
			Consumer<Payment> logPayment = p -> 
				System.out.println("  LOG: Payment " + p.id() + " for $" + p.amount());
			
			Consumer<Payment> auditPayment = p -> 
				System.out.println("  AUDIT: " + p.id() + " -> " + p.status());
			
			// Chain consumers
			Consumer<Payment> processPayment = logPayment.andThen(auditPayment);
			
			List<Payment> payments = Arrays.asList(
				new Payment("PAY001", 100.00, "APPROVED"),
				new Payment("PAY002", 250.50, "APPROVED"),
				new Payment("PAY003", 50.00, "PENDING")
			);
			
			System.out.println("Processing payments:");
			payments.forEach(processPayment);
		}
	}
	
	/**
	 * SUPPLIER<T> - "Give me something"
	 * ==================================
	 * Single method: T get()
	 * 
	 * Use when: You need to generate or provide values lazily
	 * Real analogy: A vending machine dispensing items (you don't tell it what; it decides)
	 */
	static class SupplierExample {
		static void demonstrateSupplier() {
			System.out.println("\n=== SUPPLIER<T> ===");
			
			// Create suppliers (value generators)
			Supplier<String> generateSessionId = () -> UUID.randomUUID().toString();
			Supplier<Integer> generateOTP = () -> (int)(Math.random() * 9000) + 1000;
			Supplier<Long> generateTimestamp = System::currentTimeMillis;
			
			System.out.println("Generated values:");
			System.out.println("  Session ID: " + generateSessionId.get());
			System.out.println("  OTP: " + generateOTP.get());
			System.out.println("  Timestamp: " + generateTimestamp.get());
		}
	}
	
	/**
	 * FUNCTION<T, R> - "Transform T into R"
	 * ======================================
	 * Single method: R apply(T t)
	 * 
	 * Use when: You need to convert/transform input into output
	 * Real analogy: Currency converter (EUR -> USD conversion)
	 */
	static class FunctionExample {
		static void demonstrateFunction() {
			System.out.println("\n=== FUNCTION<T, R> ===");
			
			// Create functions (transformers)
			Function<Double, Double> applyDiscount = price -> price * 0.9;  // 10% off
			Function<Double, Double> applyTax = price -> price * 1.08;  // 8% tax
			Function<Integer, String> formatCurrency = amt -> "$" + String.format("%.2f", amt.doubleValue());
			
			// Chain functions (composition)
			Function<Double, Double> totalPrice = applyDiscount.andThen(applyTax);
			
			List<Double> prices = Arrays.asList(100.0, 50.0, 200.0);
			
			System.out.println("Price transformations:");
			prices.forEach(p -> {
				double discounted = applyDiscount.apply(p);
				double withTax = totalPrice.apply(p);
				System.out.println("  Original: $" + p + " -> After discount & tax: $" + 
					String.format("%.2f", withTax));
			});
		}
	}
	
	/**
	 * UNARY_OPERATOR<T> - "Transform T into another T"
	 * ================================================
	 * Single method: T apply(T t)
	 * 
	 * Use when: Transformation keeps the type the same (Integer -> Integer, String -> String)
	 * Real analogy: A photo filter that keeps it as a photo but changes its appearance
	 */
	static class UnaryOperatorExample {
		static void demonstrateUnaryOperator() {
			System.out.println("\n=== UNARY_OPERATOR<T> ===");
			
			// Create unary operators (same type in and out)
			UnaryOperator<Integer> square = n -> n * n;
			UnaryOperator<String> toUpperCase = String::toUpperCase;
			UnaryOperator<Double> percentage = n -> n * 100;
			
			System.out.println("Unary operations (same type in/out):");
			System.out.println("  5 squared: " + square.apply(5));
			System.out.println("  'hello' uppercase: " + toUpperCase.apply("hello"));
			System.out.println("  0.85 as percentage: " + percentage.apply(0.85) + "%");
		}
	}
	
	/**
	 * BINARY_OPERATOR<T> - "Combine two T's into one T"
	 * ================================================
	 * Single method: T apply(T t1, T t2)
	 * 
	 * Use when: You need to combine two values of the same type into one result
	 * Real analogy: Merging two bank accounts into one balance
	 */
	static class BinaryOperatorExample {
		static void demonstrateBinaryOperator() {
			System.out.println("\n=== BINARY_OPERATOR<T> ===");
			
			// Create binary operators (combine two values)
			BinaryOperator<Integer> add = (a, b) -> a + b;
			BinaryOperator<Double> maxAmount = (a, b) -> Math.max(a, b);
			BinaryOperator<String> merge = (a, b) -> a + " AND " + b;
			
			System.out.println("Binary operations (two inputs, one output):");
			System.out.println("  Add: " + add.apply(10, 20));
			System.out.println("  Max of $100 and $250: " + maxAmount.apply(100.0, 250.0));
			System.out.println("  Merge: " + merge.apply("John", "Jane"));
		}
	}
	
	/**
	 * BI_FUNCTION<T, U, R> - "Transform two different types into a third"
	 * ===================================================================
	 * Single method: R apply(T t, U u)
	 * 
	 * Use when: You need to combine two different types into a result
	 * Real analogy: Calculating a loan (amount + interest_rate = monthly_payment)
	 */
	static class BiFunctionExample {
		static void demonstrateBiFunction() {
			System.out.println("\n=== BI_FUNCTION<T, U, R> ===");
			
			// Create bi-functions (two different inputs)
			BiFunction<Double, Double, Double> calculateLoan = (principal, rate) -> 
				principal * (1 + rate);
			
			BiFunction<String, Integer, String> repeatString = (str, count) -> 
				String.join("", Collections.nCopies(count, str));
			
			System.out.println("Bi-function operations (different types):");
			System.out.println("  Loan calc: $1000 at 5% = " + 
				calculateLoan.apply(1000.0, 0.05));
			System.out.println("  Repeat 'Ha' 3 times: " + 
				repeatString.apply("Ha", 3));
		}
	}
	
	/**
	 * COMPARATOR<T> - "Which one is 'greater'?"
	 * ==========================================
	 * Single method: int compare(T o1, T o2)
	 * 
	 * Use when: You need to sort or compare values
	 * Real analogy: Judging in a competition (which score is higher?)
	 */
	static class ComparatorExample {
		record Transaction(String id, double amount) {}
		
		static void demonstrateComparator() {
			System.out.println("\n=== COMPARATOR<T> ===");
			
			Comparator<Transaction> byAmount = (t1, t2) -> 
				Double.compare(t1.amount(), t2.amount());
			
			List<Transaction> transactions = Arrays.asList(
				new Transaction("T1", 500),
				new Transaction("T2", 100),
				new Transaction("T3", 300)
			);
			
			System.out.println("Transactions sorted by amount:");
			transactions.stream()
				.sorted(byAmount)
				.forEach(t -> System.out.println("  " + t.id() + ": $" + t.amount()));
		}
	}
	
	/**
	 * CALLABLE<V> & RUNNABLE - "Perform work"
	 * ========================================
	 * Runnable: void run() - Does work, returns nothing
	 * Callable: V call() throws Exception - Does work, returns a value
	 * 
	 * Use when: You need to execute code in threads or async contexts
	 */
	static class RunnableCallableExample {
		static void demonstrateRunnableCallable() throws Exception {
			System.out.println("\n=== RUNNABLE & CALLABLE ===");
			
			// Runnable (no return value)
			Runnable task1 = () -> System.out.println("  Task 1 executed");
			task1.run();
			
			// Callable (returns a value)
			Callable<String> task2 = () -> {
				Thread.sleep(100);
				return "Task 2 result after delay";
			};
			System.out.println("  " + task2.call());
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("╔════════════════════════════════════════════════════╗");
		System.out.println("║   COMPREHENSIVE FUNCTIONAL INTERFACES GUIDE        ║");
		System.out.println("║   Understanding Java's Built-in Functional Types   ║");
		System.out.println("╚════════════════════════════════════════════════════╝");
		
		PredicateExample.demonstratePredicate();
		ConsumerExample.demonstrateConsumer();
		SupplierExample.demonstrateSupplier();
		FunctionExample.demonstrateFunction();
		UnaryOperatorExample.demonstrateUnaryOperator();
		BinaryOperatorExample.demonstrateBinaryOperator();
		BiFunctionExample.demonstrateBiFunction();
		ComparatorExample.demonstrateComparator();
		RunnableCallableExample.demonstrateRunnableCallable();
		
		System.out.println("\n╔════════════════════════════════════════════════════╗");
		System.out.println("║   All examples completed successfully!             ║");
		System.out.println("╚════════════════════════════════════════════════════╝");
	}
}
