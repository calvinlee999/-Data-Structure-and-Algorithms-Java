package com.calvin.fintech.collector;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * FinancialAggregatorCollector
 * 
 * Aggregates financial data with multi-currency support
 * 
 * Features:
 * - Multi-currency aggregation
 * - Category breakdown
 * - Running balance calculation
 * - Audit trail
 * 
 * Real-world use case:
 * - Daily transaction summary
 * - Multi-currency wallet balance
 * - Financial reporting
 * 
 * Usage:
 * <pre>
 * FinancialSummary summary = transactions.stream()
 *     .collect(new FinancialAggregatorCollector());
 * </pre>
 */
public class FinancialAggregatorCollector 
        implements Collector<FinancialTransaction, 
                             FinancialAggregatorCollector.Accumulator, 
                             FinancialSummary> {
    
    /**
     * Mutable accumulator for financial data
     */
    public static class Accumulator {
        private final Map<String, BigDecimal> balancesByCurrency = new HashMap<>();
        private final Map<String, BigDecimal> totalsByCategory = new HashMap<>();
        private long transactionCount = 0;
        private BigDecimal highestAmount = BigDecimal.ZERO;
        private BigDecimal lowestAmount = BigDecimal.ZERO;
        private final List<FinancialTransaction> largeTransactions = new ArrayList<>();
        
        private static final BigDecimal LARGE_TRANSACTION_THRESHOLD = 
            new BigDecimal("10000");
        
        public void accept(FinancialTransaction transaction) {
            String currency = transaction.currency();
            BigDecimal amount = transaction.amount();
            String category = transaction.category();
            
            // Update currency totals
            balancesByCurrency.merge(currency, amount, BigDecimal::add);
            
            // Update category totals
            totalsByCategory.merge(category, amount.abs(), BigDecimal::add);
            
            // Track counts
            transactionCount++;
            
            // Track extremes
            if (amount.compareTo(highestAmount) > 0) {
                highestAmount = amount;
            }
            if (amount.compareTo(lowestAmount) < 0) {
                lowestAmount = amount;
            }
            
            // Track large transactions
            if (amount.abs().compareTo(LARGE_TRANSACTION_THRESHOLD) > 0) {
                largeTransactions.add(transaction);
            }
        }
        
        public Accumulator combine(Accumulator other) {
            // Merge currency balances
            other.balancesByCurrency.forEach((currency, amount) ->
                balancesByCurrency.merge(currency, amount, BigDecimal::add)
            );
            
            // Merge category totals
            other.totalsByCategory.forEach((category, amount) ->
                totalsByCategory.merge(category, amount, BigDecimal::add)
            );
            
            // Merge counts
            transactionCount += other.transactionCount;
            
            // Merge extremes
            if (other.highestAmount.compareTo(highestAmount) > 0) {
                highestAmount = other.highestAmount;
            }
            if (other.lowestAmount.compareTo(lowestAmount) < 0) {
                lowestAmount = other.lowestAmount;
            }
            
            // Merge large transactions
            largeTransactions.addAll(other.largeTransactions);
            
            return this;
        }
        
        public FinancialSummary finish() {
            return new FinancialSummary(
                Collections.unmodifiableMap(new HashMap<>(balancesByCurrency)),
                Collections.unmodifiableMap(new HashMap<>(totalsByCategory)),
                transactionCount,
                highestAmount,
                lowestAmount,
                Collections.unmodifiableList(new ArrayList<>(largeTransactions))
            );
        }
    }
    
    @Override
    public Supplier<Accumulator> supplier() {
        return Accumulator::new;
    }
    
    @Override
    public BiConsumer<Accumulator, FinancialTransaction> accumulator() {
        return Accumulator::accept;
    }
    
    @Override
    public BinaryOperator<Accumulator> combiner() {
        return Accumulator::combine;
    }
    
    @Override
    public Function<Accumulator, FinancialSummary> finisher() {
        return Accumulator::finish;
    }
    
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}

/**
 * Input: Financial transaction
 */
record FinancialTransaction(
    String id,
    BigDecimal amount,
    String currency,
    String category,
    String description
) {}

/**
 * Output: Financial summary (immutable)
 */
record FinancialSummary(
    Map<String, BigDecimal> balancesByCurrency,
    Map<String, BigDecimal> totalsByCategory,
    long transactionCount,
    BigDecimal highestAmount,
    BigDecimal lowestAmount,
    List<FinancialTransaction> largeTransactions
) {
    /**
     * Get total in specific currency
     */
    public BigDecimal getBalance(String currency) {
        return balancesByCurrency.getOrDefault(currency, BigDecimal.ZERO);
    }
    
    /**
     * Get total for category
     */
    public BigDecimal getCategoryTotal(String category) {
        return totalsByCategory.getOrDefault(category, BigDecimal.ZERO);
    }
    
    /**
     * Check if summary contains large transactions
     */
    public boolean hasLargeTransactions() {
        return !largeTransactions.isEmpty();
    }
}
