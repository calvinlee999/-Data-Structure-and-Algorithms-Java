package com.calvin.java8.parallelsorting;

import com.calvin.java8.models.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Parallel Array Sorting - 4x Speedup on Multi-Core Machines
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
public class ParallelSortingExample {

    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("JAVA 8 PARALLEL ARRAY SORTING - PERFORMANCE BENCHMARK");
        System.out.println("=".repeat(80));
        System.out.println();

        int size = 100_000;
        Transaction[] transactions1 = createRandomTransactions(size);
        Transaction[] transactions2 = Arrays.copyOf(transactions1, size);

        // Sequential sort
        long start = System.nanoTime();
        Arrays.sort(transactions1, Comparator.comparing(Transaction::getTimestamp));
        long sequentialTime = (System.nanoTime() - start) / 1_000_000;

        // Parallel sort
        start = System.nanoTime();
        Arrays.parallelSort(transactions2, Comparator.comparing(Transaction::getTimestamp));
        long parallelTime = (System.nanoTime() - start) / 1_000_000;

        System.out.println("   Array size: " + size + " transactions");
        System.out.println("   Sequential sort: " + sequentialTime + "ms");
        System.out.println("   Parallel sort: " + parallelTime + "ms");
        System.out.println("   Speedup: " + (double) sequentialTime / parallelTime + "x faster");
        System.out.println();
    }

    private static Transaction[] createRandomTransactions(int count) {
        Transaction[] txns = new Transaction[count];
        for (int i = 0; i < count; i++) {
            txns[i] = new Transaction(
                "TXN-" + i,
                "ACC-" + (i % 1000),
                new BigDecimal(Math.random() * 10000),
                "USD",
                Transaction.TransactionType.PAYMENT,
                LocalDateTime.now().minusSeconds((long) (Math.random() * 1000000)),
                "Transaction " + i,
                "USA"
            );
        }
        return txns;
    }
}
