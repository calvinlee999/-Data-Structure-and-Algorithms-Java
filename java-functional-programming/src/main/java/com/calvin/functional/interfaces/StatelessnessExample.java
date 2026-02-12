package com.calvin.functional.interfaces;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Principle 2: Statelessness
 * 
 * Pure functions don't rely on or change external variables.
 * They only depend on their inputs and always produce the same output for the same input.
 * This enhances resiliency and makes code predictable and testable.
 */
public class StatelessnessExample {

    // ❌ Stateful class (BAD - relies on mutable state)
    static class StatefulCalculator {
        private int total = 0; // Mutable state!

        public int add(int value) {
            total += value; // Modifies state!
            return total;
        }

        public int getTotal() {
            return total;
        }
    }

    // ✅ Stateless class (GOOD - pure functions only)
    static class StatelessCalculator {
        // Pure function: same input always produces same output
        public static int add(int a, int b) {
            return a + b; // No state modification
        }

        public static int sum(List<Integer> numbers) {
            return numbers.stream()
                    .mapToInt(Integer::intValue)
                    .sum();
        }

        public static double average(List<Integer> numbers) {
            return numbers.stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0.0);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== PRINCIPLE 2: STATELESSNESS ===\n");

        demonstrateStatefulProblems();
        demonstrateStatelessBenefits();
        demonstratePureFunctions();
        demonstrateStatelessStreams();
    }

    private static void demonstrateStatefulProblems() {
        System.out.println("❌ STATEFUL APPROACH (PROBLEMS):");
        
        StatefulCalculator calc = new StatefulCalculator();
        System.out.println("Add 5: " + calc.add(5));  // 5
        System.out.println("Add 10: " + calc.add(10)); // 15
        System.out.println("Add 3: " + calc.add(3));   // 18
        
        System.out.println("⚠️  State is mutable - hard to predict!");
        System.out.println("⚠️  Not thread-safe!");
        System.out.println("⚠️  Difficult to test!\n");
    }

    private static void demonstrateStatelessBenefits() {
        System.out.println("✅ STATELESS APPROACH (BENEFITS):");
        
        int result1 = StatelessCalculator.add(5, 0);
        int result2 = StatelessCalculator.add(result1, 10);
        int result3 = StatelessCalculator.add(result2, 3);
        
        System.out.println("5 + 0 = " + result1);
        System.out.println("5 + 10 = " + result2);
        System.out.println("15 + 3 = " + result3);
        
        System.out.println("✅ Predictable - same input, same output!");
        System.out.println("✅ Thread-safe by default!");
        System.out.println("✅ Easy to test!\n");
    }

    private static void demonstratePureFunctions() {
        System.out.println("=== PURE vs IMPURE FUNCTIONS ===\n");

        // ❌ Impure function (has side effects)
        class ImpureExample {
            private List<String> log = new ArrayList<>();

            public String processImpure(String input) {
                log.add("Processed: " + input); // Side effect!
                return input.toUpperCase();
            }
        }

        // ✅ Pure function (no side effects)
        Function<String, String> processPure = String::toUpperCase;

        ImpureExample impure = new ImpureExample();
        System.out.println("Impure result: " + impure.processImpure("hello"));
        System.out.println("⚠️  Modified external state: " + impure.log);
        
        System.out.println("\nPure result: " + processPure.apply("hello"));
        System.out.println("✅ No side effects, no state modification!\n");

        // Pure functions are referentially transparent
        demonstrateReferentialTransparency();
    }

    private static void demonstrateReferentialTransparency() {
        System.out.println("=== REFERENTIAL TRANSPARENCY ===\n");

        // Pure function - can replace call with result
        Function<Integer, Integer> square = x -> x * x;
        
        int x = 5;
        int result1 = square.apply(x) + square.apply(x);
        int result2 = 25 + 25; // Can replace square.apply(5) with 25
        
        System.out.println("square(5) + square(5) = " + result1);
        System.out.println("25 + 25 = " + result2);
        System.out.println("✅ Both are equal - referentially transparent!\n");
    }

    private static void demonstrateStatelessStreams() {
        System.out.println("=== STATELESS STREAM OPERATIONS ===\n");

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // ✅ Stateless transformation
        List<String> uppercased = names.stream()
                .map(String::toUpperCase)  // Pure function
                .collect(Collectors.toList());

        System.out.println("Original: " + names);
        System.out.println("Uppercased: " + uppercased);
        System.out.println("✅ Original unchanged, no state modified!\n");

        // ✅ Multiple stateless operations
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        int result = numbers.stream()
                .filter(n -> n % 2 == 0)    // Pure: depends only on input
                .map(n -> n * 2)             // Pure: depends only on input
                .reduce(0, Integer::sum);    // Pure: combines values

        System.out.println("Even numbers doubled and summed: " + result);
        System.out.println("✅ Entire pipeline is stateless!\n");

        // Demonstrating thread safety of stateless operations
        demonstrateThreadSafety(numbers);
    }

    private static void demonstrateThreadSafety(List<Integer> numbers) {
        System.out.println("=== THREAD SAFETY WITH STATELESS OPERATIONS ===\n");

        // ✅ Safe to parallelize stateless operations
        long sum = numbers.parallelStream()
                .filter(n -> n > 5)
                .map(n -> n * 2)
                .mapToLong(Integer::longValue)
                .sum();

        System.out.println("Parallel stream result: " + sum);
        System.out.println("✅ Stateless = Safe for parallel execution!\n");
    }

    /**
     * KEY TAKEAWAYS:
     * 
     * 1. Pure functions:
     *    - Depend only on inputs
     *    - Same input always produces same output
     *    - No side effects (no state modification)
     * 
     * 2. Benefits of statelessness:
     *    - Predictable behavior
     *    - Thread-safe by default
     *    - Easy to test
     *    - Can be safely parallelized
     *    - Referentially transparent
     * 
     * 3. Avoid:
     *    - Modifying external variables
     *    - Relying on mutable state
     *    - Side effects in stream operations
     * 
     * 4. Prefer:
     *    - Pure functions
     *    - Immutable data
     *    - Functional transformations
     */

    // Additional Examples: Pure Functions
    static class PureFunctionExamples {
        // ✅ Pure: only depends on input
        public static int multiply(int a, int b) {
            return a * b;
        }

        // ✅ Pure: no side effects
        public static String formatName(String first, String last) {
            return first + " " + last;
        }

        // ✅ Pure: creates new list, doesn't modify input
        public static List<Integer> doubleAll(List<Integer> numbers) {
            return numbers.stream()
                    .map(n -> n * 2)
                    .collect(Collectors.toList());
        }

        // ✅ Pure: depends only on inputs, no state
        public static boolean isValid(String email) {
            return email != null && email.contains("@");
        }
    }
}
