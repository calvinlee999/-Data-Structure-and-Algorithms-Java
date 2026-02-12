package com.calvin.fp.lambdas;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * LAMBDA EXPRESSIONS & FUNCTIONAL PROGRAMMING PATTERNS
 * ===================================================
 * 
 * A lambda expression is a compact way to write an anonymous function.
 * Instead of writing:
 *   new DataProcessor() { public String process(String data) { return data.toUpperCase(); } }
 * 
 * You can write:
 *   data -> data.toUpperCase()
 * 
 * Lambdas are the "syntax sugar" that makes functional programming practical in Java.
 * 
 * Pattern: (parameters) -> { body }
 * 
 * @author Principal Software Engineer - FinTech Team
 */
public class LambdaExpressions {
	
	/**
	 * STREAM API PATTERNS
	 * ===================
	 * The Stream API is Java's gateway to functional programming.
	 * Master these 4 patterns: Filter, Map, Reduce, and Terminal Operations.
	 */
	static class StreamPatterns {
		record Transaction(String id, double amount, String category, boolean verified) {}
		
		static void demonstrateStreamPatterns() {
			System.out.println("\n=== STREAM API PATTERNS ===");
			
			List<Transaction> transactions = Arrays.asList(
				new Transaction("T1", 1500, "TRANSFER", true),
				new Transaction("T2", 500, "PAYMENT", true),
				new Transaction("T3", 2000, "DEPOSIT", false),
				new Transaction("T4", 300, "WITHDRAWAL", true),
				new Transaction("T5", 5000, "TRANSFER", true)
			);
			
			// Pattern 1: FILTER - Select items matching a condition
			System.out.println("\n1. FILTER Pattern (Select verified, > $1000):");
			transactions.stream()
				.filter(t -> t.verified() && t.amount() > 1000)
				.forEach(t -> System.out.println("   " + t.id() + ": $" + t.amount()));
			
			// Pattern 2: MAP - Transform each item
			System.out.println("\n2. MAP Pattern (Extract IDs and amounts as text):");
			List<String> mapped = transactions.stream()
				.map(t -> t.id() + " ($" + t.amount() + ")")
				.collect(Collectors.toList());
			mapped.forEach(s -> System.out.println("   " + s));
			
			// Pattern 3: REDUCE - Combine all items into one result
			System.out.println("\n3. REDUCE Pattern (Calculate total):");
			double total = transactions.stream()
				.map(t -> t.amount())
				.reduce(0.0, (a, b) -> a + b);
			System.out.println("   Total: $" + total);
			
			// Pattern 4: COLLECT - Gather results into a collection
			System.out.println("\n4. COLLECT Pattern (Group by category):");
			Map<String, List<Transaction>> byCategory = transactions.stream()
				.collect(Collectors.groupingBy(t -> t.category()));
			byCategory.forEach((cat, txns) -> 
				System.out.println("   " + cat + ": " + txns.size() + " transactions"));
		}
	}
	
	/**
	 * OPTIONAL - Handling NULL values functionally
	 * =============================================
	 * Instead of: if (user != null && user.getAccount() != null) ...
	 * Use: Optional.ofNullable(user).map(User::getAccount)...
	 * 
	 * Optional prevents NullPointerExceptions in a functional way.
	 */
	static class OptionalPatterns {
		record User(String id, String name, String email) {}
		
		static void demonstrateOptional() {
			System.out.println("\n=== OPTIONAL PATTERNS ===");
			
			// Old way (imperative, verbose)
			User user = new User("U1", "John", null);
			if (user != null && user.email() != null) {
				System.out.println("Email: " + user.email());
			} else {
				System.out.println("Email not available");
			}
			
			// New way (functional, clean)
			System.out.println("Functional approach:");
			Optional.ofNullable(user)
				.map(u -> u.email())
				.ifPresentOrElse(
					email -> System.out.println("Email: " + email),
					() -> System.out.println("Email not available")
				);
			
			// More advanced: filter and chain
			Optional.ofNullable(user)
				.filter(u -> u.email() != null && u.email().contains("@"))
				.map(u -> "Valid email for " + u.name())
				.ifPresent(System.out::println);
		}
	}
	
	/**
	 * FUNCTION COMPOSITION - Combining functions
	 * ===========================================
	 * Instead of: result = f(g(h(x)))
	 * Use: f.compose(g).compose(h)
	 * 
	 * Makes complex transformations readable as pipelines.
	 */
	static class FunctionComposition {
		static void demonstrateComposition() {
			System.out.println("\n=== FUNCTION COMPOSITION ===");
			
			// Define individual transformation functions
			Function<String, String> trim = String::strip;
			Function<String, String> uppercase = String::toUpperCase;
			Function<String, String> addPrefix = s -> "PROCESSED: " + s;
			
			// Method 1: compose (right to left)
			Function<String, String> pipeline1 = addPrefix
				.compose(uppercase)
				.compose(trim);
			
			String input = "  hello world  ";
			System.out.println("Input: '" + input + "'");
			System.out.println("After pipeline: " + pipeline1.apply(input));
			
			// Method 2: andThen (left to right) - Often more intuitive
			Function<String, String> pipeline2 = 
				((Function<String, String>) String::strip)
				.andThen(String::toUpperCase)
				.andThen(s -> "PROCESSED: " + s);
			
			System.out.println("Using andThen (more intuitive): " + pipeline2.apply(input));
		}
	}
	
	/**
	 * METHOD REFERENCES - Even more concise!
	 * ======================================
	 * Instead of: str -> str.toUpperCase()
	 * Use: String::toUpperCase
	 * 
	 * Method references are lambdas with fewer characters.
	 */
	static class MethodReferences {
		record Account(String id, double balance) {}
		
		static void demonstrateMethodReferences() {
			System.out.println("\n=== METHOD REFERENCES ===");
			
			List<String> names = Arrays.asList("alice", "bob", "charlie");
			List<Account> accounts = Arrays.asList(
				new Account("A1", 1000),
				new Account("A2", 2000),
				new Account("A3", 500)
			);
			
			// Lambda versions
			System.out.println("Lambda: names.forEach(name -> System.out.println(name))");
			System.out.println("Method reference version:");
			names.forEach(System::out::println);
			
			// Sort using method reference
			System.out.println("\nSort accounts by balance (using method reference):");
			accounts.stream()
				.sorted(Comparator.comparingDouble(Account::balance))
				.forEach(a -> System.out.println("  " + a.id() + ": $" + a.balance()));
			
			// Transform to strings using method reference
			System.out.println("\nTransform to strings:");
			names.stream()
				.map(String::toUpperCase)  // Method reference!
				.forEach(s -> System.out.println("  " + s));
		}
	}
	
	/**
	 * CUSTOM FUNCTIONAL INTERFACES
	 * =============================
	 * Create your own functional interfaces for domain logic.
	 */
	static class CustomFunctionalInterfaces {
		
		@FunctionalInterface
		interface ComplianceCheck {
			/**
			 * Validates if a transaction meets compliance requirements.
			 * @param amount - Transaction amount
			 * @param country - Customer's country
			 * @return true if compliant, false otherwise
			 */
			boolean validate(double amount, String country);
		}
		
		static void demonstrateCustom() {
			System.out.println("\n=== CUSTOM FUNCTIONAL INTERFACES ===");
			
			// Create custom compliance checks as lambdas
			ComplianceCheck suspiciousAmount = (amt, country) -> amt < 10_000;
			ComplianceCheck notSanctioned = (amt, country) -> 
				!country.equals("SanctionedCountry");
			
			// Use them
			double testAmount = 5000;
			String testCountry = "USA";
			
			System.out.println("Amount: $" + testAmount + ", Country: " + testCountry);
			System.out.println("Passes suspicious check: " + 
				suspiciousAmount.validate(testAmount, testCountry));
			System.out.println("Passes sanctions check: " + 
				notSanctioned.validate(testAmount, testCountry));
		}
	}
	
	/**
	 * IMMUTABILITY & DATA TRANSFORMATION
	 * ===================================
	 * Functional style: Create new data instead of modifying existing data.
	 */
	static class ImmutableTransformations {
		static void demonstrateImmutability() {
			System.out.println("\n=== IMMUTABILITY & TRANSFORMATIONS ===");
			
			List<Integer> original = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
			System.out.println("Original list: " + original);
			
			// WRONG: Modify in place
			// original.replaceAll(n -> n * 2);
			
			// RIGHT: Create new list with transformation
			List<Integer> transformed = original.stream()
				.map(n -> n * 2)
				.collect(Collectors.toList());
			
			System.out.println("After map (new list): " + transformed);
			System.out.println("Original unchanged: " + original);
			
			// Chain multiple transformations without mutation
			List<Integer> complex = original.stream()
				.filter(n -> n > 2)
				.map(n -> n * 2)
				.map(n -> n + 10)
				.collect(Collectors.toList());
			
			System.out.println("Complex pipeline result: " + complex);
			System.out.println("Original still unchanged: " + original);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("╔═════════════════════════════════════════════════════════╗");
		System.out.println("║   LAMBDA EXPRESSIONS & FUNCTIONAL PATTERNS             ║");
		System.out.println("║   Modern Java Functional Programming Techniques        ║");
		System.out.println("╚═════════════════════════════════════════════════════════╝");
		
		StreamPatterns.demonstrateStreamPatterns();
		OptionalPatterns.demonstrateOptional();
		FunctionComposition.demonstrateComposition();
		MethodReferences.demonstrateMethodReferences();
		CustomFunctionalInterfaces.demonstrateCustom();
		ImmutableTransformations.demonstrateImmutability();
		
		System.out.println("\n╔═════════════════════════════════════════════════════════╗");
		System.out.println("║   All examples completed successfully!                 ║");
		System.out.println("╚═════════════════════════════════════════════════════════╝");
	}
}
