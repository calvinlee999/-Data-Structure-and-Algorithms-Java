package com.calvin.functional.patterns;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * LAZY EVALUATION PATTERN
 * 
 * Think of lazy evaluation like a lazy person who only does work when absolutely necessary!
 * Instead of doing all the work upfront, lazy evaluation waits until results are needed.
 * 
 * Real-world analogy: Like ordering food delivery. The restaurant doesn't cook
 * your meal when you browse the menu - only when you actually place the order!
 * This saves time and resources.
 * 
 * @author FinTech Principal Software Engineer
 */
public class LazyEvaluationPattern {

    record Transaction(String id, double amount, String type) {}

    /**
     * PATTERN 1: Stream Lazy Evaluation Basics
     */
    static class StreamLazyEvaluationExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Stream Lazy Evaluation ===");
            System.out.println("Goal: Understand intermediate vs terminal operations\n");
            
            List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            
            System.out.println("Creating stream with intermediate operations...");
            
            // Intermediate operations (lazy - not executed yet!)
            Stream<Integer> stream = numbers.stream()
                .peek(n -> System.out.println("  Filtering: " + n))
                .filter(n -> n % 2 == 0)
                .peek(n -> System.out.println("  Mapping: " + n))
                .map(n -> n * 2);
            
            System.out.println("Stream created, but NO work done yet!\n");
            
            // Terminal operation (triggers execution!)
            System.out.println("Now collecting (terminal operation)...\n");
            List<Integer> result = stream.collect(Collectors.toList());
            
            System.out.println("\nResult: " + result);
            System.out.println("\n  Benefits: Only process what's needed!");
        }
    }

    /**
     * PATTERN 2: Short-Circuit Operations
     */
    static class ShortCircuitExample {
        
        static boolean expensiveCheck(int n) {
            System.out.println("  Expensive check for: " + n);
            try {
                Thread.sleep(100);  // Simulate expensive operation
            } catch (InterruptedException e) {}
            return n > 5;
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Short-Circuit Operations ===");
            System.out.println("Goal: Stop processing early when possible\n");
            
            List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            
            System.out.println("Finding first number > 5 (stops early):");
            long start = System.currentTimeMillis();
            
            Optional<Integer> first = numbers.stream()
                .filter(ShortCircuitExample::expensiveCheck)
                .findFirst();  // Short-circuits!
            
            long elapsed = System.currentTimeMillis() - start;
            
            System.out.println("\n  Result: " + first.get());
            System.out.println("  Time: " + elapsed + "ms");
            System.out.println("  Notice: Only checked until found!\n");
            
            // Comparison: findAny, anyMatch, allMatch also short-circuit
            boolean anyMatch = numbers.stream()
                .anyMatch(n -> n > 5);  // Stops at first match
            
            System.out.println("  anyMatch > 5: " + anyMatch);
            
            System.out.println("\n  Benefits: Huge performance savings!");
        }
    }

    /**
     * PATTERN 3: Supplier Pattern for Lazy Initialization
     */
    static class SupplierPatternExample {
        
        static class ExpensiveResource {
            ExpensiveResource() {
                System.out.println("  Creating expensive resource...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
            
            public String getData() {
                return "Important data";
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Supplier for Lazy Initialization ===");
            System.out.println("Goal: Delay creation until needed\n");
            
            // EAGER (wasteful if not used!)
            System.out.println("❌ EAGER creation:");
            ExpensiveResource eager = new ExpensiveResource();
            System.out.println("Resource created immediately\n");
            
            // LAZY with Supplier
            System.out.println("✅ LAZY with Supplier:");
            Supplier<ExpensiveResource> lazy = ExpensiveResource::new;
            System.out.println("Supplier created (no resource yet!)");
            
            System.out.println("\nDoing other work...");
            System.out.println("Still no resource created!\n");
            
            System.out.println("Now we actually need it:");
            ExpensiveResource resource = lazy.get();  // Created here!
            System.out.println("Data: " + resource.getData());
            
            System.out.println("\n  Benefits: Don't pay for what you don't use!");
        }
    }

    /**
     * PATTERN 4: Lazy Collections with Stream Generation
     */
    static class LazyCollectionsExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Lazy Collections ===");
            System.out.println("Goal: Generate infinite streams lazily\n");
            
            // Infinite stream of transaction IDs
            Stream<String> transactionIds = Stream.iterate(1, n -> n + 1)
                .map(n -> String.format("TX%05d", n));
            
            System.out.println("First 5 transaction IDs:");
            transactionIds.limit(5)  // Only generate 5!
                .forEach(id -> System.out.println("  " + id));
            
            // Infinite stream with condition (Java 9+)
            System.out.println("\nGenerate until amount > 1000:");
            Stream.iterate(100.0, amount -> amount < 1000.0, amount -> amount * 1.1)
                .forEach(amount -> System.out.println("  $" + String.format("%.2f", amount)));
            
            // Generate random transactions lazily
            System.out.println("\nRandom transactions (generated on demand):");
            Stream.generate(() -> new Transaction(
                    UUID.randomUUID().toString().substring(0, 8),
                    Math.random() * 1000,
                    Math.random() > 0.5 ? "DEBIT" : "CREDIT"
                ))
                .limit(3)
                .forEach(tx -> System.out.println("  " + tx));
            
            System.out.println("\n  Benefits: Can work with infinite data!");
        }
    }

    /**
     * PATTERN 5: Lazy Evaluation for Performance
     */
    static class LazyPerformanceExample {
        
        static boolean isPrime(int n) {
            if (n < 2) return false;
            for (int i = 2; i <= Math.sqrt(n); i++) {
                if (n % i == 0) return false;
            }
            return true;
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Lazy Performance Benefits ===");
            System.out.println("Goal: Process only what's needed\n");
            
            int limit = 100000;
            
            // EAGER: Process everything first (slow!)
            System.out.println("❌ EAGER: Find first 5 primes");
            long start1 = System.currentTimeMillis();
            
            List<Integer> allPrimes = new ArrayList<>();
            for (int i = 2; i < limit; i++) {
                if (isPrime(i)) {
                    allPrimes.add(i);
                }
            }
            List<Integer> first5Eager = allPrimes.subList(0, 5);
            
            long elapsed1 = System.currentTimeMillis() - start1;
            System.out.println("  Result: " + first5Eager);
            System.out.println("  Time: " + elapsed1 + "ms (checked all " + limit + " numbers!)\n");
            
            // LAZY: Process until found (fast!)
            System.out.println("✅ LAZY: Find first 5 primes");
            long start2 = System.currentTimeMillis();
            
            List<Integer> first5Lazy = Stream.iterate(2, n -> n + 1)
                .filter(LazyPerformanceExample::isPrime)
                .limit(5)  // Stop after finding 5!
                .collect(Collectors.toList());
            
            long elapsed2 = System.currentTimeMillis() - start2;
            System.out.println("  Result: " + first5Lazy);
            System.out.println("  Time: " + elapsed2 + "ms (stopped early!)");
            
            System.out.println("\n  Speedup: " + (elapsed1 / elapsed2) + "x faster!");
            System.out.println("  Benefits: Massive performance gains!");
        }
    }

    /**
     * PATTERN 6: Memoization (Caching Lazy Results)
     */
    static class MemoizationExample {
        
        static class LazyCache<T> {
            private Supplier<T> supplier;
            private T value;
            private boolean computed = false;
            
            public LazyCache(Supplier<T> supplier) {
                this.supplier = supplier;
            }
            
            public T get() {
                if (!computed) {
                    System.out.println("  Computing value...");
                    value = supplier.get();
                    computed = true;
                }
                return value;
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Memoization ===");
            System.out.println("Goal: Compute once, cache result\n");
            
            LazyCache<Double> expensiveCalculation = new LazyCache<>(() -> {
                try {
                    Thread.sleep(1000);  // Simulate expensive calculation
                } catch (InterruptedException e) {}
                return Math.PI * Math.E;
            });
            
            System.out.println("Lazy cache created (nothing computed yet)");
            
            System.out.println("\nFirst access:");
            long start1 = System.currentTimeMillis();
            double result1 = expensiveCalculation.get();
            long elapsed1 = System.currentTimeMillis() - start1;
            System.out.println("  Result: " + result1);
            System.out.println("  Time: " + elapsed1 + "ms");
            
            System.out.println("\nSecond access (cached):");
            long start2 = System.currentTimeMillis();
            double result2 = expensiveCalculation.get();
            long elapsed2 = System.currentTimeMillis() - start2;
            System.out.println("  Result: " + result2);
            System.out.println("  Time: " + elapsed2 + "ms");
            
            System.out.println("\n  Benefits: Pay once, use many times!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              LAZY EVALUATION PATTERN                           ║");
        System.out.println("║  Do work only when needed - not before!                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        StreamLazyEvaluationExample.demonstrate();
        ShortCircuitExample.demonstrate();
        SupplierPatternExample.demonstrate();
        LazyCollectionsExample.demonstrate();
        LazyPerformanceExample.demonstrate();
        MemoizationExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • Intermediate operations are lazy (map, filter)              ║");
        System.out.println("║  • Terminal operations trigger execution (collect, forEach)    ║");
        System.out.println("║  • Use Supplier for lazy initialization                        ║");
        System.out.println("║  • Short-circuit operations save massive computation           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
