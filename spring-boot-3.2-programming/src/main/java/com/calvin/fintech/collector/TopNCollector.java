package com.calvin.fintech.collector;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * TopNCollector - Generic Top-N Collector
 * 
 * Collects the top N elements according to a comparator
 * 
 * Features:
 * - Generic type support
 * - Memory efficient (only keeps N elements)
 * - Works with parallel streams
 * - Maintains natural or custom ordering
 * 
 * Usage:
 * <pre>
 * List<Account> topAccounts = accounts.stream()
 *     .collect(new TopNCollector<>(
 *         10, 
 *         Comparator.comparing(Account::getBalance).reversed()
 *     ));
 * </pre>
 * 
 * @param <T> Type of elements to collect
 */
public class TopNCollector<T> implements Collector<T, PriorityQueue<T>, List<T>> {
    
    private final int n;
    private final Comparator<T> comparator;
    
    public TopNCollector(int n, Comparator<T> comparator) {
        this.n = n;
        this.comparator = comparator;
    }
    
    /**
     * Supplier: Creates a min-heap (PriorityQueue)
     * 
     * Why min-heap?
     * - Keep top N by discarding smallest
     * - O(log n) insertions
     * - Memory: O(n) instead of O(total elements)
     */
    @Override
    public Supplier<PriorityQueue<T>> supplier() {
        return () -> new PriorityQueue<>(comparator);
    }
    
    /**
     * Accumulator: Add element to heap
     * 
     * Maintains heap of size N:
     * - If size < N: Add element
     * - If size == N && element > min: Remove min, add element
     */
    @Override
    public BiConsumer<PriorityQueue<T>, T> accumulator() {
        return (heap, element) -> {
            if (heap.size() < n) {
                heap.offer(element);
            } else if (comparator.compare(element, heap.peek()) > 0) {
                heap.poll(); // Remove smallest
                heap.offer(element);
            }
        };
    }
    
    /**
     * Combiner: Merge two heaps (for parallel streams)
     */
    @Override
    public BinaryOperator<PriorityQueue<T>> combiner() {
        return (heap1, heap2) -> {
            // Add all from heap2 to heap1
            for (T element : heap2) {
                if (heap1.size() < n) {
                    heap1.offer(element);
                } else if (comparator.compare(element, heap1.peek()) > 0) {
                    heap1.poll();
                    heap1.offer(element);
                }
            }
            return heap1;
        };
    }
    
    /**
     * Finisher: Convert heap to sorted list
     */
    @Override
    public Function<PriorityQueue<T>, List<T>> finisher() {
        return heap -> {
            List<T> result = new ArrayList<>(heap);
            result.sort(comparator.reversed()); // Reverse to get descending order
            return result;
        };
    }
    
    /**
     * Characteristics: Empty set (no special characteristics)
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
    
    /**
     * Factory method for convenience
     */
    public static <T> TopNCollector<T> topN(int n, Comparator<T> comparator) {
        return new TopNCollector<>(n, comparator);
    }
}
