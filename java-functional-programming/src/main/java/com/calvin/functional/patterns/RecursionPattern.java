package com.calvin.functional.patterns;

import java.util.*;
import java.util.stream.*;

/**
 * RECURSION PATTERN
 * 
 * Think of recursion like Russian nesting dolls! Each doll contains a smaller doll,
 * until you reach the tiniest one. A recursive function calls itself with simpler
 * versions of the problem until reaching the simplest case.
 * 
 * Real-world analogy: Like looking for a book in a stack. Check the top book.
 * If it's not the right one, check the rest of the stack (same task, smaller stack).
 * Stop when the stack is empty or you find the book!
 * 
 * @author FinTech Principal Software Engineer
 */
public class RecursionPattern {

    record Transaction(String id, double amount, List<Transaction> subTransactions) {
        // Leaf transaction (no children)
        public Transaction(String id, double amount) {
            this(id, amount, List.of());
        }
    }

    /**
     * PATTERN 1: Basic Recursion - Factorial
     */
    static class BasicRecursionExample {
        
        // OLD WAY: Iterative
        static long factorialIterative(int n) {
            long result = 1;
            for (int i = 1; i <= n; i++) {
                result *= i;
            }
            return result;
        }
        
        // NEW WAY: Recursive
        static long factorialRecursive(int n) {
            // Base case: stop condition
            if (n <= 1) {
                return 1;
            }
            // Recursive case: call itself with simpler problem
            return n * factorialRecursive(n - 1);
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Basic Recursion ===");
            System.out.println("Goal: Calculate factorial\n");
            
            int n = 5;
            
            System.out.println("❌ OLD WAY (Iterative):");
            long result1 = factorialIterative(n);
            System.out.println("  " + n + "! = " + result1);
            
            System.out.println("\n✅ NEW WAY (Recursive):");
            long result2 = factorialRecursive(n);
            System.out.println("  " + n + "! = " + result2);
            
            System.out.println("\n  How it works:");
            System.out.println("  5! = 5 × 4!");
            System.out.println("  4! = 4 × 3!");
            System.out.println("  3! = 3 × 2!");
            System.out.println("  2! = 2 × 1!");
            System.out.println("  1! = 1 (base case)");
            
            System.out.println("\n  Benefits: Elegant for mathematical problems!");
        }
    }

    /**
     * PATTERN 2: Tail Recursion (Optimizable)
     */
    static class TailRecursionExample {
        
        // Regular recursion (not tail-recursive)
        static long sumRecursive(int n) {
            if (n <= 0) return 0;
            return n + sumRecursive(n - 1);  // Addition AFTER recursive call
        }
        
        // Tail recursion (last operation is recursive call)
        static long sumTailRecursive(int n, long accumulator) {
            if (n <= 0) return accumulator;
            return sumTailRecursive(n - 1, accumulator + n);  // No operation after call
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Tail Recursion ===");
            System.out.println("Goal: Recursion that can be optimized\n");
            
            int n = 10;
            
            System.out.println("Regular recursion (sum 1 to " + n + "):");
            long result1 = sumRecursive(n);
            System.out.println("  Result: " + result1);
            
            System.out.println("\nTail recursion (optimizable):");
            long result2 = sumTailRecursive(n, 0);
            System.out.println("  Result: " + result2);
            
            System.out.println("\n  How tail recursion works:");
            System.out.println("  sum(10, 0) → sum(9, 10) → sum(8, 19) → ...");
            System.out.println("  Carries result forward, no stack buildup!");
            
            System.out.println("\n  Benefits: Can be converted to loop by compiler!");
        }
    }

    /**
     * PATTERN 3: Tree Recursion - Hierarchical Data
     */
    static class TreeRecursionExample {
        
        // Recursive: Calculate total including nested transactions
        static double calculateTotalRecursive(Transaction tx) {
            // Base case: current transaction amount
            double total = tx.amount;
            
            // Recursive case: add all sub-transactions
            for (Transaction sub : tx.subTransactions) {
                total += calculateTotalRecursive(sub);
            }
            
            return total;
        }
        
        // Functional: Using streams
        static double calculateTotalFunctional(Transaction tx) {
            return tx.amount + tx.subTransactions.stream()
                .mapToDouble(TreeRecursionExample::calculateTotalFunctional)
                .sum();
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Tree Recursion ===");
            System.out.println("Goal: Process hierarchical data\n");
            
            // Build transaction tree
            Transaction parent = new Transaction("TX001", 100.0, List.of(
                new Transaction("TX002", 50.0, List.of(
                    new Transaction("TX003", 10.0),
                    new Transaction("TX004", 5.0)
                )),
                new Transaction("TX005", 25.0)
            ));
            
            System.out.println("Transaction hierarchy:");
            System.out.println("  TX001: $100");
            System.out.println("    TX002: $50");
            System.out.println("      TX003: $10");
            System.out.println("      TX004: $5");
            System.out.println("    TX005: $25");
            
            double total1 = calculateTotalRecursive(parent);
            System.out.println("\n  Recursive total: $" + total1);
            
            double total2 = calculateTotalFunctional(parent);
            System.out.println("  Functional total: $" + total2);
            
            System.out.println("\n  Benefits: Natural fit for tree structures!");
        }
    }

    /**
     * PATTERN 4: Fibonacci - Classic Recursion Problem
     */
    static class FibonacciExample {
        
        // Naive recursion (slow - exponential time!)
        static long fibonacciNaive(int n) {
            if (n <= 1) return n;
            return fibonacciNaive(n - 1) + fibonacciNaive(n - 2);
        }
        
        // Memoized recursion (fast - linear time!)
        static long fibonacciMemoized(int n, Map<Integer, Long> memo) {
            if (n <= 1) return n;
            
            if (memo.containsKey(n)) {
                return memo.get(n);
            }
            
            long result = fibonacciMemoized(n - 1, memo) + fibonacciMemoized(n - 2, memo);
            memo.put(n, result);
            return result;
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Fibonacci ===");
            System.out.println("Goal: Optimize recursive solutions\n");
            
            int n = 40;
            
            System.out.println("❌ Naive recursion (slow):");
            long start1 = System.currentTimeMillis();
            long result1 = fibonacciNaive(n);
            long elapsed1 = System.currentTimeMillis() - start1;
            System.out.println("  fib(" + n + ") = " + result1);
            System.out.println("  Time: " + elapsed1 + "ms");
            
            System.out.println("\n✅ Memoized recursion (fast):");
            long start2 = System.currentTimeMillis();
            long result2 = fibonacciMemoized(n, new HashMap<>());
            long elapsed2 = System.currentTimeMillis() - start2;
            System.out.println("  fib(" + n + ") = " + result2);
            System.out.println("  Time: " + elapsed2 + "ms");
            
            System.out.println("\n  Speedup: " + (elapsed1 / Math.max(elapsed2, 1)) + "x faster!");
            System.out.println("  Benefits: Memoization prevents duplicate work!");
        }
    }

    /**
     * PATTERN 5: Recursive Stream Processing
     */
    static class RecursiveStreamExample {
        
        record Directory(String name, List<Directory> subdirectories, List<String> files) {
            public Directory(String name, List<String> files) {
                this(name, List.of(), files);
            }
        }
        
        // Recursively count all files
        static int countFilesRecursive(Directory dir) {
            int count = dir.files.size();
            for (Directory sub : dir.subdirectories) {
                count += countFilesRecursive(sub);
            }
            return count;
        }
        
        // Functional approach with streams
        static int countFilesFunctional(Directory dir) {
            return dir.files.size() + 
                dir.subdirectories.stream()
                    .mapToInt(RecursiveStreamExample::countFilesFunctional)
                    .sum();
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Recursive Streams ===");
            System.out.println("Goal: Combine recursion with streams\n");
            
            Directory root = new Directory("root", 
                List.of(
                    new Directory("src", List.of("Main.java", "Utils.java")),
                    new Directory("test", List.of("TestMain.java"))
                ),
                List.of("README.md")
            );
            
            System.out.println("Directory structure:");
            System.out.println("  root/");
            System.out.println("    README.md");
            System.out.println("    src/");
            System.out.println("      Main.java");
            System.out.println("      Utils.java");
            System.out.println("    test/");
            System.out.println("      TestMain.java");
            
            int count1 = countFilesRecursive(root);
            System.out.println("\n  Recursive count: " + count1 + " files");
            
            int count2 = countFilesFunctional(root);
            System.out.println("  Functional count: " + count2 + " files");
            
            System.out.println("\n  Benefits: Clean, declarative code!");
        }
    }

    /**
     * PATTERN 6: Replacing Loops with Recursion
     */
    static class ReplacingLoopsExample {
        
        // OLD WAY: Loop
        static List<Integer> reverseListLoop(List<Integer> list) {
            List<Integer> result = new ArrayList<>();
            for (int i = list.size() - 1; i >= 0; i--) {
                result.add(list.get(i));
            }
            return result;
        }
        
        // NEW WAY: Recursion
        static List<Integer> reverseListRecursive(List<Integer> list) {
            if (list.isEmpty()) {
                return List.of();
            }
            
            List<Integer> rest = list.subList(1, list.size());
            List<Integer> reversedRest = reverseListRecursive(rest);
            
            List<Integer> result = new ArrayList<>(reversedRest);
            result.add(list.get(0));
            return result;
        }
        
        // EVEN BETTER: Stream
        static List<Integer> reverseListStream(List<Integer> list) {
            return IntStream.range(0, list.size())
                .map(i -> list.size() - 1 - i)
                .mapToObj(list::get)
                .collect(Collectors.toList());
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Replacing Loops ===");
            System.out.println("Goal: Functional alternatives to loops\n");
            
            List<Integer> numbers = List.of(1, 2, 3, 4, 5);
            
            System.out.println("Original: " + numbers);
            
            List<Integer> reversed1 = reverseListLoop(numbers);
            System.out.println("\n❌ Loop: " + reversed1);
            
            List<Integer> reversed2 = reverseListRecursive(numbers);
            System.out.println("✅ Recursion: " + reversed2);
            
            List<Integer> reversed3 = reverseListStream(numbers);
            System.out.println("✅ Stream: " + reversed3);
            
            System.out.println("\n  Benefits: No mutable state!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                   RECURSION PATTERN                            ║");
        System.out.println("║  Functions that call themselves - elegant problem solving      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        BasicRecursionExample.demonstrate();
        TailRecursionExample.demonstrate();
        TreeRecursionExample.demonstrate();
        FibonacciExample.demonstrate();
        RecursiveStreamExample.demonstrate();
        ReplacingLoopsExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • Recursion solves problems by breaking them into smaller     ║");
        System.out.println("║    versions of the same problem                                ║");
        System.out.println("║  • Always have a base case (stop condition)                    ║");
        System.out.println("║  • Use memoization to avoid duplicate calculations             ║");
        System.out.println("║  • Perfect for tree/hierarchical data structures               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
