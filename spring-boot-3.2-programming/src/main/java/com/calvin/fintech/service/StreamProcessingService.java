package com.calvin.fintech.service;

import com.calvin.fintech.entity.*;
import com.calvin.fintech.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * StreamProcessingService - Stream API in Production
 * 
 * Demonstrates:
 * - Primitive streams for performance
 * - Complex stream pipelines
 * - Parallel processing
 * - Collectors mastery
 * - Real-world FinTech use cases
 */
@Service
public class StreamProcessingService {
    
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    
    public StreamProcessingService(
            CustomerRepository customerRepository,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }
    
    // ========================================
    // PRIMITIVE STREAMS (PERFORMANCE)
    // ========================================
    
    /**
     * Calculate total balance across all accounts
     * 
     * Uses primitive stream (mapToDouble) for better performance
     * 5-10x faster than object streams for numeric operations
     */
    public BigDecimal calculateTotalBalance() {
        List<Account> accounts = accountRepository.findAll();
        
        // Convert to double stream (primitive)
        double total = accounts.stream()
            .mapToDouble(account -> account.getBalance().doubleValue())
            .sum();
        
        return BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Get account balance statistics
     * 
     * Primitive streams provide built-in summary statistics
     */
    public BalanceStatistics getBalanceStatistics() {
        DoubleSummaryStatistics stats = accountRepository.findAll()
            .stream()
            .mapToDouble(account -> account.getBalance().doubleValue())
            .summaryStatistics();
        
        return new BalanceStatistics(
            BigDecimal.valueOf(stats.getSum()),
            BigDecimal.valueOf(stats.getAverage()),
            BigDecimal.valueOf(stats.getMax()),
            BigDecimal.valueOf(stats.getMin()),
            stats.getCount()
        );
    }
    
    /**
     * Calculate average transaction amount by type
     */
    public Map<TransactionType, BigDecimal> getAverageAmountByType() {
        return transactionRepository.findAll()
            .stream()
            .collect(Collectors.groupingBy(
                Transaction::getType,
                Collectors.averagingDouble(tx -> tx.getAmount().doubleValue())
            ))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> BigDecimal.valueOf(e.getValue()).setScale(2, RoundingMode.HALF_UP)
            ));
    }
    
    // ========================================
    // COMPLEX STREAM PIPELINES
    // ========================================
    
    /**
     * Transaction Reconciliation Report
     * 
     * Find customers with failed transactions in last 30 days,
     * calculate total failed amount,
     * filter those > $10,000
     */
    public Map<Customer, BigDecimal> getHighValueFailedTransactions() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        
        return transactionRepository.findAll()
            .stream()
            // Filter: Recent failed transactions
            .filter(tx -> tx.getCreatedAt().isAfter(thirtyDaysAgo))
            .filter(tx -> tx.getStatus() == TransactionStatus.FAILED)
            // Group by customer and sum amounts
            .collect(Collectors.groupingBy(
                Transaction::getCustomer,
                Collectors.reducing(
                    BigDecimal.ZERO,
                    Transaction::getAmount,
                    BigDecimal::add
                )
            ))
            // Filter: Total > $10,000
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue().compareTo(new BigDecimal("10000")) > 0)
            // Sort by amount descending
            .sorted(Map.Entry.<Customer, BigDecimal>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }
    
    /**
     * Customer Segmentation
     * 
     * Segment customers into tiers based on total account balance
     */
    public Map<String, List<Customer>> segmentCustomersByBalance() {
        return customerRepository.findAll()
            .stream()
            .collect(Collectors.groupingBy(customer -> {
                // Calculate total balance for customer
                BigDecimal totalBalance = accountRepository.findByCustomerId(customer.getId())
                    .stream()
                    .map(Account::getBalance)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                // Segment logic
                if (totalBalance.compareTo(new BigDecimal("100000")) >= 0) {
                    return "PLATINUM";
                } else if (totalBalance.compareTo(new BigDecimal("50000")) >= 0) {
                    return "GOLD";
                } else if (totalBalance.compareTo(new BigDecimal("10000")) >= 0) {
                    return "SILVER";
                } else {
                    return "BRONZE";
                }
            }));
    }
    
    /**
     * Top N Accounts by Balance
     * 
     * Demonstrates: Sorting, limiting, collecting
     */
    public List<Account> getTopAccountsByBalance(int n) {
        return accountRepository.findAll()
            .stream()
            .filter(account -> account.getStatus() == AccountStatus.ACTIVE)
            .sorted(Comparator.comparing(Account::getBalance).reversed())
            .limit(n)
            .collect(Collectors.toList());
    }
    
    /**
     * Multi-level Grouping
     * 
     * Group transactions by customer → account type → transaction type
     */
    public Map<Customer, Map<AccountType, Map<TransactionType, Long>>> 
    getTransactionDistribution() {
        return transactionRepository.findAll()
            .stream()
            .collect(Collectors.groupingBy(
                Transaction::getCustomer,
                Collectors.groupingBy(
                    tx -> {
                        // Get account type (assuming transaction has account reference)
                        // Simplified: use CHECKING as default
                        return AccountType.CHECKING;
                    },
                    Collectors.groupingBy(
                        Transaction::getType,
                        Collectors.counting()
                    )
                )
            ));
    }
    
    // ========================================
    // PARALLEL STREAMS
    // ========================================
    
    /**
     * Process large dataset in parallel
     * 
     * Use when:
     * - Dataset > 10,000 elements
     * - CPU-intensive operations
     * - Stateless operations
     */
    public List<TransactionSummary> processTransactionsParallel() {
        return transactionRepository.findAll()
            .parallelStream()  // Parallel processing
            .filter(tx -> tx.getAmount().compareTo(new BigDecimal("1000")) > 0)
            .map(tx -> new TransactionSummary(
                tx.getId(),
                tx.getCustomer().getFullName(),
                tx.getAmount(),
                tx.getType(),
                tx.getStatus()
            ))
            .collect(Collectors.toList());
    }
    
    /**
     * Calculate daily transaction totals (parallel)
     */
    public Map<String, BigDecimal> getDailyTransactionTotals() {
        return transactionRepository.findAll()
            .parallelStream()
            .filter(tx -> tx.getStatus() == TransactionStatus.COMPLETED)
            .collect(Collectors.groupingByConcurrent(  // Thread-safe collector
                tx -> tx.getCreatedAt().toLocalDate().toString(),
                Collectors.reducing(
                    BigDecimal.ZERO,
                    Transaction::getAmount,
                    BigDecimal::add
                )
            ));
    }
    
    // ========================================
    // COLLECTORS MASTERY
    // ========================================
    
    /**
     * Partitioning: Split into two groups
     */
    public Map<Boolean, List<Customer>> partitionCustomersByKyc() {
        return customerRepository.findAll()
            .stream()
            .collect(Collectors.partitioningBy(customer -> 
                customer.getProfile() != null && 
                customer.getProfile().isKycVerified()
            ));
    }
    
    /**
     * Joining: Concatenate strings
     */
    public String getCustomerEmailList() {
        return customerRepository.findAll()
            .stream()
            .map(Customer::getEmail)
            .collect(Collectors.joining(", ", "Emails: [", "]"));
    }
    
    /**
     * Mapping downstream collector
     */
    public Map<CustomerStatus, List<String>> getEmailsByStatus() {
        return customerRepository.findAll()
            .stream()
            .collect(Collectors.groupingBy(
                Customer::getStatus,
                Collectors.mapping(
                    Customer::getEmail,
                    Collectors.toList()
                )
            ));
    }
    
    /**
     * Teeing: Apply two collectors simultaneously
     */
    public SummaryStats getTransactionStats() {
        return transactionRepository.findAll()
            .stream()
            .collect(Collectors.teeing(
                Collectors.counting(),
                Collectors.summingDouble(tx -> tx.getAmount().doubleValue()),
                (count, sum) -> new SummaryStats(
                    count,
                    BigDecimal.valueOf(sum)
                )
            ));
    }
    
    // ========================================
    // REAL-WORLD USE CASES
    // ========================================
    
    /**
     * Fraud Detection Pipeline
     * 
     * Find suspicious transactions:
     * - Amount > $50,000
     * - Created within last 24 hours
     * - From customers with < 30 days history
     */
    public List<Transaction> detectSuspiciousTransactions() {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        
        return transactionRepository.findAll()
            .stream()
            .filter(tx -> tx.getAmount().compareTo(new BigDecimal("50000")) > 0)
            .filter(tx -> tx.getCreatedAt().isAfter(oneDayAgo))
            .filter(tx -> tx.getCustomer().getCreatedAt().isAfter(thirtyDaysAgo))
            .sorted(Comparator.comparing(Transaction::getAmount).reversed())
            .collect(Collectors.toList());
    }
    
    /**
     * Monthly Account Summary
     * 
     * Generate report for regulatory compliance
     */
    public Map<String, AccountSummary> getMonthlyAccountSummary() {
        return accountRepository.findAll()
            .stream()
            .filter(account -> account.getStatus() == AccountStatus.ACTIVE)
            .collect(Collectors.groupingBy(
                account -> account.getCreatedAt().getMonth().toString(),
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    accounts -> new AccountSummary(
                        accounts.size(),
                        accounts.stream()
                            .map(Account::getBalance)
                            .reduce(BigDecimal.ZERO, BigDecimal::add),
                        accounts.stream()
                            .map(Account::getAccountType)
                            .collect(Collectors.groupingBy(
                                type -> type,
                                Collectors.counting()
                            ))
                    )
                )
            ));
    }
    
    /**
     * Generate unique account numbers
     * 
     * Demonstrates: IntStream, range operations
     */
    public List<String> generateAccountNumbers(int count, String prefix) {
        return IntStream.range(1, count + 1)
            .mapToObj(i -> String.format("%s-%06d", prefix, i))
            .collect(Collectors.toList());
    }
    
    /**
     * Find duplicates
     * 
     * Useful for data quality checks
     */
    public Set<String> findDuplicateEmails() {
        return customerRepository.findAll()
            .stream()
            .map(Customer::getEmail)
            .collect(Collectors.groupingBy(
                email -> email,
                Collectors.counting()
            ))
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() > 1)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    }
}

// Helper classes

record BalanceStatistics(
    BigDecimal total,
    BigDecimal average,
    BigDecimal max,
    BigDecimal min,
    long count
) {}

record TransactionSummary(
    String id,
    String customerName,
    BigDecimal amount,
    TransactionType type,
    TransactionStatus status
) {}

record SummaryStats(
    long count,
    BigDecimal totalAmount
) {}

record AccountSummary(
    int accountCount,
    BigDecimal totalBalance,
    Map<AccountType, Long> distributionByType
) {}
