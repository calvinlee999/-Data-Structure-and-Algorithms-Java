package com.calvin.fintech.example;

import com.calvin.fintech.collector.FinancialAggregatorCollector;
import com.calvin.fintech.collector.FinancialTransaction;
import com.calvin.fintech.collector.TopNCollector;
import com.calvin.fintech.entity.*;
import com.calvin.fintech.repository.*;
import com.calvin.fintech.repository.specification.CustomerSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * FunctionalPipelineDemo - Integration Example
 * 
 * Demonstrates how all functional programming patterns work together:
 * - Specifications (functional queries)
 * - Stream API (data processing)
 * - Custom Collectors (aggregation)
 * - CompletableFuture (async execution)
 * - Optional handling
 * - Method references
 * 
 * Real-world scenario: Month-end account statement processing
 */
@Service
public class FunctionalPipelineDemo {
    
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    
    public FunctionalPipelineDemo(
            CustomerRepository customerRepository,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }
    
    /**
     * SCENARIO 1: Generate month-end account statements
     * 
     * Pipeline:
     * 1. Find eligible customers (Specifications)
     * 2. Load accounts in parallel (CompletableFuture)
     * 3. Process transactions (Stream API)
     * 4. Aggregate data (Custom Collectors)
     * 5. Generate statements (Optional handling)
     */
    public CompletableFuture<List<MonthlyStatement>> generateMonthlyStatements(
            int month, 
            int year) {
        
        // Step 1: Find eligible customers using Specifications
        Specification<Customer> eligibleCustomers = Specification
            .where(CustomerSpecifications.isActive())
            .and(CustomerSpecifications.isNotDeleted())
            .and(CustomerSpecifications.hasVerifiedKyc());
        
        List<Customer> customers = customerRepository.findAll(eligibleCustomers);
        
        // Step 2: Process each customer in parallel
        List<CompletableFuture<MonthlyStatement>> futures = customers.stream()
            .map(customer -> generateStatementAsync(customer, month, year))
            .toList();
        
        // Step 3: Wait for all statements
        return CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
            )
            .thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList())
            );
    }
    
    /**
     * Generate statement for one customer (async)
     */
    private CompletableFuture<MonthlyStatement> generateStatementAsync(
            Customer customer,
            int month,
            int year) {
        
        return CompletableFuture.supplyAsync(() -> {
            // Get all accounts
            List<Account> accounts = accountRepository
                .findByCustomerId(customer.getId());
            
            // Process transactions with streams
            LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
            LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);
            
            List<Transaction> transactions = accounts.stream()
                // Step 1: Get transactions for each account
                .flatMap(account -> 
                    transactionRepository.findByAccountId(account.getId()).stream()
                )
                // Step 2: Filter by date range
                .filter(t -> 
                    t.getTransactionDate().isAfter(startDate) &&
                    t.getTransactionDate().isBefore(endDate)
                )
                // Step 3: Sort by date
                .sorted(Comparator.comparing(Transaction::getTransactionDate))
                .toList();
            
            // Aggregate with custom collector
            var summary = transactions.stream()
                .map(t -> new FinancialTransaction(
                    t.getId().toString(),
                    t.getAmount(),
                    "USD", // Assume USD
                    t.getTransactionType().name(),
                    t.getDescription()
                ))
                .collect(new FinancialAggregatorCollector());
            
            // Calculate balances
            BigDecimal totalBalance = accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            return new MonthlyStatement(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                month,
                year,
                accounts.size(),
                transactions.size(),
                totalBalance,
                summary.getBalance("USD"),
                summary
            );
        });
    }
    
    /**
     * SCENARIO 2: Identify high-value customers for retention campaign
     * 
     * Pipeline:
     * 1. Stream all customers
     * 2. Calculate customer lifetime value (parallel)
     * 3. Filter by threshold
     * 4. Collect top N
     * 5. Enrich with additional data
     */
    public List<CustomerRetentionProfile> identifyHighValueCustomers(
            int topN,
            BigDecimal minLifetimeValue) {
        
        return customerRepository.findAll().parallelStream()
            // Step 1: Calculate lifetime value for each customer
            .map(customer -> {
                BigDecimal lifetimeValue = calculateLifetimeValue(customer);
                
                return new CustomerValue(
                    customer,
                    lifetimeValue
                );
            })
            // Step 2: Filter by minimum threshold
            .filter(cv -> cv.lifetimeValue().compareTo(minLifetimeValue) >= 0)
            // Step 3: Collect top N
            .collect(new TopNCollector<>(
                topN,
                Comparator.comparing(CustomerValue::lifetimeValue).reversed()
            ))
            // Step 4: Enrich with additional data
            .stream()
            .map(this::enrichCustomerProfile)
            .toList();
    }
    
    /**
     * Calculate customer lifetime value
     */
    private BigDecimal calculateLifetimeValue(Customer customer) {
        return accountRepository.findByCustomerId(customer.getId()).stream()
            // Get all transactions for all accounts
            .flatMap(account -> 
                transactionRepository.findByAccountId(account.getId()).stream()
            )
            // Filter successful transactions
            .filter(t -> t.getStatus() == TransactionStatus.COMPLETED)
            // Map to amounts (use primitive stream for performance)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Enrich customer profile with additional data
     */
    private CustomerRetentionProfile enrichCustomerProfile(CustomerValue cv) {
        Customer customer = cv.customer();
        
        // Get account summary
        Map<AccountType, Long> accountDistribution = accountRepository
            .findByCustomerId(customer.getId()).stream()
            .collect(Collectors.groupingBy(
                Account::getAccountType,
                Collectors.counting()
            ));
        
        // Get risk assessment (Optional handling)
        String riskLevel = Optional.ofNullable(customer.getProfile())
            .map(CustomerProfile::isKycVerified)
            .map(verified -> verified ? "LOW" : "HIGH")
            .orElse("UNKNOWN");
        
        // Calculate engagement score
        int engagementScore = calculateEngagementScore(customer);
        
        return new CustomerRetentionProfile(
            customer.getId(),
            customer.getFullName(),
            customer.getEmail(),
            cv.lifetimeValue(),
            accountDistribution,
            riskLevel,
            engagementScore,
            customer.getCreatedAt()
        );
    }
    
    /**
     * SCENARIO 3: Fraud detection with complex pipeline
     * 
     * Pipeline:
     * 1. Get recent transactions
     * 2. Group by customer
     * 3. Detect suspicious patterns
     * 4. Calculate risk scores
     * 5. Filter high-risk cases
     */
    public List<FraudAlert> detectSuspiciousActivity(int lastNDays) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(lastNDays);
        
        return transactionRepository.findAll().stream()
            // Step 1: Filter recent transactions
            .filter(t -> t.getTransactionDate().isAfter(cutoff))
            // Step 2: Group by customer
            .collect(Collectors.groupingBy(
                t -> accountRepository.findById(t.getAccount().getId())
                    .map(Account::getCustomer)
                    .map(Customer::getId)
                    .orElse(null)
            ))
            .entrySet().stream()
            // Step 3: Analyze each customer's transactions
            .map(entry -> {
                Long customerId = entry.getKey();
                List<Transaction> transactions = entry.getValue();
                
                // Detect patterns
                boolean rapidTransactions = hasRapidTransactions(transactions);
                boolean unusualAmounts = hasUnusualAmounts(transactions);
                boolean multipleFailures = hasMultipleFailures(transactions);
                
                // Calculate risk score
                int riskScore = 0;
                if (rapidTransactions) riskScore += 30;
                if (unusualAmounts) riskScore += 40;
                if (multipleFailures) riskScore += 30;
                
                return new FraudAlert(
                    customerId,
                    riskScore,
                    transactions.size(),
                    rapidTransactions,
                    unusualAmounts,
                    multipleFailures,
                    LocalDateTime.now()
                );
            })
            // Step 4: Filter high-risk cases
            .filter(alert -> alert.riskScore() >= 50)
            // Step 5: Sort by risk score
            .sorted(Comparator.comparing(FraudAlert::riskScore).reversed())
            .toList();
    }
    
    /**
     * SCENARIO 4: Complex aggregation with teeing collector
     * 
     * Demonstrates: Applying multiple collectors simultaneously
     */
    public AccountSummaryReport generateAccountSummaryReport() {
        return accountRepository.findAll().stream()
            .collect(Collectors.teeing(
                // Collector 1: Count by type
                Collectors.groupingBy(
                    Account::getAccountType,
                    Collectors.counting()
                ),
                // Collector 2: Sum balances by type
                Collectors.groupingBy(
                    Account::getAccountType,
                    Collectors.mapping(
                        Account::getBalance,
                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                    )
                ),
                // Combiner: Create report
                (counts, balances) -> new AccountSummaryReport(
                    counts,
                    balances,
                    LocalDateTime.now()
                )
            ));
    }
    
    /**
     * SCENARIO 5: Functional error handling
     * 
     * Demonstrates: Optional chaining and error recovery
     */
    public Optional<CustomerReport> generateCustomerReport(Long customerId) {
        return customerRepository.findById(customerId)
            // Map customer to report
            .map(customer -> {
                // Get accounts (with default empty list)
                List<Account> accounts = Optional
                    .ofNullable(accountRepository.findByCustomerId(customerId))
                    .orElse(Collections.emptyList());
                
                // Calculate total balance
                BigDecimal totalBalance = accounts.stream()
                    .map(Account::getBalance)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                // Get KYC status (with fallback)
                boolean kycVerified = Optional.ofNullable(customer.getProfile())
                    .map(CustomerProfile::isKycVerified)
                    .orElse(false);
                
                return new CustomerReport(
                    customer.getId(),
                    customer.getFullName(),
                    accounts.size(),
                    totalBalance,
                    kycVerified,
                    customer.getStatus()
                );
            });
    }
    
    // ========================================
    // HELPER METHODS
    // ========================================
    
    private int calculateEngagementScore(Customer customer) {
        // Simplified engagement calculation
        long accountCount = accountRepository.findByCustomerId(customer.getId()).size();
        long transactionCount = accountRepository.findByCustomerId(customer.getId()).stream()
            .flatMap(account -> 
                transactionRepository.findByAccountId(account.getId()).stream()
            )
            .count();
        
        return (int) (accountCount * 10 + transactionCount);
    }
    
    private boolean hasRapidTransactions(List<Transaction> transactions) {
        // Check if more than 5 transactions in 10 minutes
        return transactions.stream()
            .sorted(Comparator.comparing(Transaction::getTransactionDate))
            .collect(Collectors.groupingBy(
                t -> t.getTransactionDate().toLocalDate().atTime(
                    t.getTransactionDate().getHour(),
                    t.getTransactionDate().getMinute() / 10 * 10
                )
            ))
            .values().stream()
            .anyMatch(group -> group.size() > 5);
    }
    
    private boolean hasUnusualAmounts(List<Transaction> transactions) {
        // Check if any transaction is 10x average
        BigDecimal avg = transactions.stream()
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(new BigDecimal(transactions.size()), 2, BigDecimal.ROUND_HALF_UP);
        
        BigDecimal threshold = avg.multiply(new BigDecimal("10"));
        
        return transactions.stream()
            .anyMatch(t -> t.getAmount().compareTo(threshold) > 0);
    }
    
    private boolean hasMultipleFailures(List<Transaction> transactions) {
        long failedCount = transactions.stream()
            .filter(t -> t.getStatus() == TransactionStatus.FAILED)
            .count();
        
        return failedCount >= 3;
    }
}

// ========================================
// HELPER RECORDS
// ========================================

record MonthlyStatement(
    Long customerId,
    String customerName,
    String email,
    int month,
    int year,
    int accountCount,
    int transactionCount,
    BigDecimal totalBalance,
    BigDecimal monthlyActivity,
    Object summary
) {}

record CustomerValue(
    Customer customer,
    BigDecimal lifetimeValue
) {}

record CustomerRetentionProfile(
    Long customerId,
    String name,
    String email,
    BigDecimal lifetimeValue,
    Map<AccountType, Long> accountDistribution,
    String riskLevel,
    int engagementScore,
    LocalDateTime memberSince
) {}

record FraudAlert(
    Long customerId,
    int riskScore,
    int transactionCount,
    boolean rapidTransactions,
    boolean unusualAmounts,
    boolean multipleFailures,
    LocalDateTime detectedAt
) {}

record AccountSummaryReport(
    Map<AccountType, Long> accountCounts,
    Map<AccountType, BigDecimal> balancesByType,
    LocalDateTime generatedAt
) {}

record CustomerReport(
    Long customerId,
    String name,
    int accountCount,
    BigDecimal totalBalance,
    boolean kycVerified,
    CustomerStatus status
) {}
