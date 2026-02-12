package com.calvin.functional.streams;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

/**
 * Principle 4: Concurrency
 * 
 * Functional code is natively safer for Multi-core/Parallel execution.
 * Immutable data and pure functions eliminate race conditions.
 * This enables scalability and high performance.
 */
public class ConcurrencyExample {

    record Transaction(String id, String type, double amount, long timestamp) {}

    public static void main(String[] args) {
        System.out.println("=== PRINCIPLE 4: CONCURRENCY ===\n");

        demonstrateSequentialVsParallel();
        demonstrateThreadSafety();
        demonstratePerformanceBenefits();
        demonstrateParallelBestPractices();
    }

    private static void demonstrateSequentialVsParallel() {
        System.out.println("=== SEQUENTIAL vs PARALLEL STREAMS ===\n");

        List<Integer> numbers = IntStream.rangeClosed(1, 1_000_000)
                .boxed()
                .collect(Collectors.toList());

        // Sequential processing
        long startSeq = System.currentTimeMillis();
        long sumSeq = numbers.stream()
                .filter(n -> n % 2 == 0)
                .mapToLong(n -> (long) n * n)
                .sum();
        long endSeq = System.currentTimeMillis();

        // Parallel processing
        long startPar = System.currentTimeMillis();
        long sumPar = numbers.parallelStream()
                .filter(n -> n % 2 == 0)
                .mapToLong(n -> (long) n * n)
                .sum();
        long endPar = System.currentTimeMillis();

        System.out.println("Sequential sum: " + sumSeq + " (Time: " + (endSeq - startSeq) + "ms)");
        System.out.println("Parallel sum: " + sumPar + " (Time: " + (endPar - startPar) + "ms)");
        System.out.println("✅ Parallel execution utilizes multiple cores!\n");
    }

    private static void demonstrateThreadSafety() {
        System.out.println("=== THREAD SAFETY WITH IMMUTABILITY ===\n");

        List<Transaction> transactions = generateTransactions(10000);

        // ❌ Mutable shared state (DANGEROUS in parallel)
        System.out.println("❌ MUTABLE STATE (Not Thread-Safe):");
        class MutableCounter {
            private int count = 0;
            
            public void increment() {
                count++; // Race condition!
            }
            
            public int getCount() {
                return count;
            }
        }

        MutableCounter counter = new MutableCounter();
        transactions.parallelStream()
                .forEach(t -> counter.increment());
        
        System.out.println("Expected count: 10000");
        System.out.println("Actual count: " + counter.getCount());
        System.out.println("⚠️  Race conditions lead to incorrect results!\n");

        // ✅ Immutable functional approach (SAFE)
        System.out.println("✅ FUNCTIONAL APPROACH (Thread-Safe):");
        long count = transactions.parallelStream()
                .count();
        
        System.out.println("Count: " + count);
        System.out.println("✅ Functional operations are thread-safe!\n");

        // ✅ Parallel reduction (thread-safe aggregation)
        double totalAmount = transactions.parallelStream()
                .mapToDouble(Transaction::amount)
                .sum();
        
        System.out.println("Total amount: $" + String.format("%.2f", totalAmount));
        System.out.println("✅ Parallel reduction handles concurrency!\n");
    }

    private static void demonstratePerformanceBenefits() {
        System.out.println("=== PERFORMANCE BENEFITS ===\n");

        List<Transaction> transactions = generateTransactions(100000);

        // CPU-intensive operation
        System.out.println("Processing 100,000 transactions...\n");

        // Sequential
        long start1 = System.currentTimeMillis();
        Map<String, Double> sequentialResult = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::type,
                        Collectors.summingDouble(Transaction::amount)
                ));
        long end1 = System.currentTimeMillis();

        // Parallel
        long start2 = System.currentTimeMillis();
        Map<String, Double> parallelResult = transactions.parallelStream()
                .collect(Collectors.groupingByConcurrent(
                        Transaction::type,
                        Collectors.summingDouble(Transaction::amount)
                ));
        long end2 = System.currentTimeMillis();

        System.out.println("Sequential time: " + (end1 - start1) + "ms");
        System.out.println("Parallel time: " + (end2 - start2) + "ms");
        System.out.println("Speedup: " + String.format("%.2fx", (double)(end1 - start1) / (end2 - start2)));
        System.out.println("\n✅ Parallel processing scales with CPU cores!\n");
    }

    private static void demonstrateParallelBestPractices() {
        System.out.println("=== PARALLEL STREAM BEST PRACTICES ===\n");

        List<Integer> numbers = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());

        System.out.println("✅ WHEN TO USE PARALLEL:");
        System.out.println("1. Large datasets (10,000+ elements)");
        System.out.println("2. CPU-intensive operations");
        System.out.println("3. Stateless operations");
        System.out.println("4. No order dependency\n");

        System.out.println("❌ AVOID PARALLEL FOR:");
        System.out.println("1. Small datasets (overhead > benefit)");
        System.out.println("2. I/O operations (use async instead)");
        System.out.println("3. Operations with side effects");
        System.out.println("4. Order-dependent operations\n");

        // Good use case: CPU-intensive, large dataset
        long result1 = LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .filter(n -> isPrime(n))
                .count();
        System.out.println("Prime numbers up to 1,000,000: " + result1);
        System.out.println("✅ Good: CPU-intensive, large dataset\n");

        // Bad use case: small dataset
        List<Integer> small = Arrays.asList(1, 2, 3, 4, 5);
        int sum = small.stream()  // Use sequential for small datasets
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("Sum of small list: " + sum);
        System.out.println("✅ Good: Sequential for small dataset\n");
    }

    // Helper method: check if number is prime (CPU-intensive)
    private static boolean isPrime(long n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private static List<Transaction> generateTransactions(int count) {
        Random random = new Random(42); // Fixed seed for reproducibility
        String[] types = {"DEPOSIT", "WITHDRAWAL", "TRANSFER", "PAYMENT"};
        
        return IntStream.range(0, count)
                .mapToObj(i -> new Transaction(
                        "TX" + i,
                        types[random.nextInt(types.length)],
                        random.nextDouble() * 1000,
                        System.currentTimeMillis()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Advanced Concurrency Examples
     */
    static class AdvancedExamples {
        
        // Parallel collection with custom thread pool
        public static <T> List<T> processInParallel(List<T> items, ForkJoinPool customPool) {
            try {
                return customPool.submit(() ->
                        items.parallelStream()
                                .collect(Collectors.toList())
                ).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        // Safe parallel aggregation
        public static double parallelSum(List<Double> numbers) {
            return numbers.parallelStream()
                    .reduce(0.0, Double::sum);
        }

        // Parallel filtering with collecting
        public static <T> List<T> parallelFilter(List<T> items, java.util.function.Predicate<T> predicate) {
            return items.parallelStream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        }
    }

    /**
     * KEY TAKEAWAYS:
     * 
     * 1. Functional code enables easy parallelization:
     *    - Immutable data = no race conditions
     *    - Pure functions = no shared state
     *    - Just add .parallelStream()!
     * 
     * 2. When to parallelize:
     *    - ✅ Large datasets (10,000+)
     *    - ✅ CPU-intensive operations
     *    - ✅ Stateless transformations
     *    - ❌ Small datasets (overhead not worth it)
     *    - ❌ I/O operations
     *    - ❌ Side effects
     * 
     * 3. Thread safety for free:
     *    - Immutability prevents race conditions
     *    - No locks needed
     *    - Scales with CPU cores
     * 
     * 4. Performance benefits:
     *    - Utilize all CPU cores
     *    - Near-linear scaling for CPU-bound tasks
     *    - No code changes needed (sequential → parallel)
     * 
     * 5. Best practices:
     *    - Profile before parallelizing
     *    - Avoid shared mutable state
     *    - Use appropriate collectors
     *    - Consider custom ForkJoinPool for control
     */
}
