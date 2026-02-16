package com.calvin.fintech.collector;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * TransactionStatisticsCollector
 * 
 * Custom collector for aggregating transaction statistics
 * 
 * Demonstrates:
 * - Building a custom Collector from scratch
 * - Mutable accumulation pattern
 * - Parallel stream support with combiner
 * - Immutable result with finisher
 * 
 * Usage:
 * <pre>
 * TransactionStatistics stats = transactions.stream()
 *     .collect(new TransactionStatisticsCollector());
 * </pre>
 */
public class TransactionStatisticsCollector 
        implements Collector<TransactionAmount, 
                             TransactionStatisticsCollector.Accumulator, 
                             TransactionStatistics> {
    
    /**
     * Mutable accumulator for collecting statistics
     * 
     * This is modified during stream processing
     */
    public static class Accumulator {
        private long count = 0;
        private BigDecimal sum = BigDecimal.ZERO;
        private BigDecimal min = null;
        private BigDecimal max = null;
        private final List<BigDecimal> amounts = new ArrayList<>();
        
        public void accept(TransactionAmount transaction) {
            BigDecimal amount = transaction.amount();
            
            count++;
            sum = sum.add(amount);
            
            if (min == null || amount.compareTo(min) < 0) {
                min = amount;
            }
            
            if (max == null || amount.compareTo(max) > 0) {
                max = amount;
            }
            
            amounts.add(amount);
        }
        
        public Accumulator combine(Accumulator other) {
            count += other.count;
            sum = sum.add(other.sum);
            
            if (other.min != null) {
                min = (min == null || other.min.compareTo(min) < 0) 
                    ? other.min : min;
            }
            
            if (other.max != null) {
                max = (max == null || other.max.compareTo(max) > 0) 
                    ? other.max : max;
            }
            
            amounts.addAll(other.amounts);
            
            return this;
        }
        
        public TransactionStatistics finish() {
            if (count == 0) {
                return new TransactionStatistics(
                    0, BigDecimal.ZERO, BigDecimal.ZERO, 
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
                );
            }
            
            BigDecimal average = sum.divide(
                new BigDecimal(count), 
                2, 
                BigDecimal.ROUND_HALF_UP
            );
            
            // Calculate median
            Collections.sort(amounts);
            BigDecimal median;
            int size = amounts.size();
            
            if (size % 2 == 0) {
                median = amounts.get(size / 2 - 1)
                    .add(amounts.get(size / 2))
                    .divide(new BigDecimal("2"), 2, BigDecimal.ROUND_HALF_UP);
            } else {
                median = amounts.get(size / 2);
            }
            
            return new TransactionStatistics(
                count, sum, average, min, max, median
            );
        }
    }
    
    // ========================================
    // COLLECTOR INTERFACE IMPLEMENTATION
    // ========================================
    
    /**
     * 1. Supplier: Creates a new accumulator
     */
    @Override
    public Supplier<Accumulator> supplier() {
        return Accumulator::new;
    }
    
    /**
     * 2. Accumulator: Adds element to accumulator
     */
    @Override
    public BiConsumer<Accumulator, TransactionAmount> accumulator() {
        return Accumulator::accept;
    }
    
    /**
     * 3. Combiner: Merges two accumulators (for parallel streams)
     */
    @Override
    public BinaryOperator<Accumulator> combiner() {
        return Accumulator::combine;
    }
    
    /**
     * 4. Finisher: Converts accumulator to final result
     */
    @Override
    public Function<Accumulator, TransactionStatistics> finisher() {
        return Accumulator::finish;
    }
    
    /**
     * 5. Characteristics: Metadata about collector
     */
    @Override
    public Set<Characteristics> characteristics() {
        // Not CONCURRENT - our accumulator is not thread-safe
        // Not UNORDERED - order matters for median calculation
        // Not IDENTITY_FINISH - we transform accumulator to result
        return Collections.emptySet();
    }
}

/**
 * Immutable result of transaction statistics
 */
record TransactionStatistics(
    long count,
    BigDecimal total,
    BigDecimal average,
    BigDecimal min,
    BigDecimal max,
    BigDecimal median
) {}

/**
 * Simple wrapper for transaction amount
 */
record TransactionAmount(BigDecimal amount) {}
