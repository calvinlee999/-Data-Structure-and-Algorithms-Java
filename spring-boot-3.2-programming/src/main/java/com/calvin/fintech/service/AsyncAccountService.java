package com.calvin.fintech.service;

import com.calvin.fintech.entity.*;
import com.calvin.fintech.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * AsyncAccountService - CompletableFuture Patterns
 * 
 * Demonstrates:
 * - Async method execution with @Async
 * - CompletableFuture composition
 * - Parallel execution patterns
 * - Error handling in async workflows
 * - Real-world FinTech async use cases
 * 
 * Why Async in FinTech?
 * - KYC verification calls (1-3s)
 * - Credit scoring APIs (1-2s)
 * - Risk calculation (2-5s)
 * - External payment processing (3-10s)
 * 
 * Running these in parallel can reduce latency by 70-90%!
 */
@Service
public class AsyncAccountService {
    
    private static final Logger log = LoggerFactory.getLogger(AsyncAccountService.class);
    
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CustomerProfileRepository profileRepository;
    private final Executor taskExecutor; // Configured thread pool
    
    public AsyncAccountService(
            AccountRepository accountRepository,
            CustomerRepository customerRepository,
            CustomerProfileRepository profileRepository,
            Executor taskExecutor) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.profileRepository = profileRepository;
        this.taskExecutor = taskExecutor;
    }
    
    // ========================================
    // BASIC ASYNC PATTERNS
    // ========================================
    
    /**
     * Simple async operation
     * 
     * @Async methods return:
     * - void
     * - Future<T>
     * - CompletableFuture<T>  (Preferred - more flexible)
     */
    @Async
    public CompletableFuture<Account> findAccountAsync(Long id) {
        log.info("Finding account {} on thread {}", id, 
            Thread.currentThread().getName());
        
        return CompletableFuture.completedFuture(
            accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"))
        );
    }
    
    /**
     * Async calculation
     */
    @Async
    public CompletableFuture<BigDecimal> calculateInterestAsync(Account account) {
        log.info("Calculating interest for account {} on thread {}", 
            account.getAccountNumber(), Thread.currentThread().getName());
        
        // Simulate complex calculation
        try {
            Thread.sleep(1000); // 1 second
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        BigDecimal rate = account.getInterestRate();
        if (rate == null) {
            rate = BigDecimal.ZERO;
        }
        
        BigDecimal interest = account.getBalance()
            .multiply(rate)
            .divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
        
        return CompletableFuture.completedFuture(interest);
    }
    
    // ========================================
    // PARALLEL EXECUTION
    // ========================================
    
    /**
     * Execute multiple operations in parallel
     * 
     * Synchronous approach: 3 seconds (1s + 1s + 1s)
     * Async approach: ~1 second (all parallel)
     */
    public CompletableFuture<AccountOpeningResult> openAccountWithChecks(
            Long customerId) {
        
        log.info("Starting account opening for customer {}", customerId);
        
        // Start all operations in parallel
        CompletableFuture<Customer> customerFuture = 
            CompletableFuture.supplyAsync(() -> {
                log.info("Loading customer on thread {}", 
                    Thread.currentThread().getName());
                return customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            }, taskExecutor);
        
        CompletableFuture<CustomerProfile> profileFuture = 
            CompletableFuture.supplyAsync(() -> {
                log.info("Loading profile on thread {}", 
                    Thread.currentThread().getName());
                simulateDelay(1000); // KYC service call
                return profileRepository.findByCustomerId(customerId)
                    .orElseThrow(() -> new RuntimeException("Profile not found"));
            }, taskExecutor);
        
        CompletableFuture<CreditScore> creditFuture = 
            CompletableFuture.supplyAsync(() -> {
                log.info("Fetching credit score on thread {}", 
                    Thread.currentThread().getName());
                simulateDelay(1000); // Credit bureau API
                return new CreditScore(customerId, 750);
            }, taskExecutor);
        
        CompletableFuture<RiskRating> riskFuture = 
            CompletableFuture.supplyAsync(() -> {
                log.info("Calculating risk on thread {}", 
                    Thread.currentThread().getName());
                simulateDelay(1000); // Risk engine
                return new RiskRating("LOW");
            }, taskExecutor);
        
        // Wait for all to complete
        return CompletableFuture.allOf(
                customerFuture, profileFuture, creditFuture, riskFuture
            )
            .thenApply(v -> {
                Customer customer = customerFuture.join();
                CustomerProfile profile = profileFuture.join();
                CreditScore score = creditFuture.join();
                RiskRating risk = riskFuture.join();
                
                log.info("All checks completed for customer {}", customerId);
                
                return new AccountOpeningResult(
                    customer,
                    profile,
                    score,
                    risk,
                    true,
                    "All checks passed"
                );
            });
    }
    
    // ========================================
    // FUNCTIONAL COMPOSITION
    // ========================================
    
    /**
     * Chain async operations
     * 
     * Demonstrates: supplyAsync → thenApply → thenCompose → exceptionally
     */
    public CompletableFuture<Account> createPremiumAccount(Long customerId) {
        return CompletableFuture
            // Step 1: Load customer
            .supplyAsync(() -> {
                log.info("Step 1: Loading customer");
                return customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            }, taskExecutor)
            
            // Step 2: Verify KYC (transform result)
            .thenApply(customer -> {
                log.info("Step 2: Verifying KYC for {}", customer.getEmail());
                
                if (customer.getProfile() == null || 
                    !customer.getProfile().isKycVerified()) {
                    throw new RuntimeException("KYC not verified");
                }
                
                return customer;
            })
            
            // Step 3: Check credit score (async operation)
            .thenCompose(customer -> {
                log.info("Step 3: Checking credit score");
                
                return CompletableFuture.supplyAsync(() -> {
                    simulateDelay(500);
                    int score = 750; // Simulated credit score
                    
                    if (score < 700) {
                        throw new RuntimeException("Credit score too low");
                    }
                    
                    return customer;
                }, taskExecutor);
            })
            
            // Step 4: Create account
            .thenApply(customer -> {
                log.info("Step 4: Creating premium account");
                
                Account account = new Account(
                    generateAccountNumber(),
                    AccountType.INVESTMENT
                );
                account.setStatus(AccountStatus.ACTIVE);
                account.setInterestRate(new BigDecimal("0.05")); // 5%
                
                return accountRepository.save(account);
            })
            
            // Error handling
            .exceptionally(ex -> {
                log.error("Account creation failed", ex);
                
                // Fallback: Create basic account
                Account basicAccount = new Account(
                    generateAccountNumber(),
                    AccountType.SAVINGS
                );
                basicAccount.setStatus(AccountStatus.ACTIVE);
                
                return accountRepository.save(basicAccount);
            });
    }
    
    // ========================================
    // BATCH ASYNC PROCESSING
    // ========================================
    
    /**
     * Apply interest to all savings accounts in parallel
     */
    public CompletableFuture<BatchResult> applyInterestToAllAccounts() {
        List<Account> savingsAccounts = accountRepository
            .findByAccountType(AccountType.SAVINGS);
        
        log.info("Applying interest to {} accounts", savingsAccounts.size());
        
        // Create async tasks for each account
        List<CompletableFuture<Void>> futures = savingsAccounts.stream()
            .map(account -> CompletableFuture.runAsync(() -> {
                try {
                    BigDecimal interest = account.getBalance()
                        .multiply(account.getInterestRate())
                        .divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
                    
                    account.deposit(interest);
                    accountRepository.save(account);
                    
                    log.debug("Interest applied to account {}: {}", 
                        account.getAccountNumber(), interest);
                } catch (Exception e) {
                    log.error("Failed to apply interest to account {}", 
                        account.getAccountNumber(), e);
                }
            }, taskExecutor))
            .collect(Collectors.toList());
        
        // Wait for all to complete
        return CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
            )
            .thenApply(v -> {
                long successful = futures.stream()
                    .filter(f -> !f.isCompletedExceptionally())
                    .count();
                
                long failed = futures.size() - successful;
                
                return new BatchResult(
                    (int) successful,
                    (int) failed,
                    savingsAccounts.size()
                );
            });
    }
    
    // ========================================
    // COMBINING ASYNC RESULTS
    // ========================================
    
    /**
     * Combine two independent async operations
     * 
     * Use thenCombine when results are independent
     */
    public CompletableFuture<TransferSummary> transferWithAudit(
            Long fromAccountId,
            Long toAccountId,
            BigDecimal amount) {
        
        CompletableFuture<Account> fromFuture = findAccountAsync(fromAccountId);
        CompletableFuture<Account> toFuture = findAccountAsync(toAccountId);
        
        // Combine both futures
        return fromFuture.thenCombine(toFuture, (from, to) -> {
            // Execute transfer
            from.withdraw(amount);
            to.deposit(amount);
            
            accountRepository.save(from);
            accountRepository.save(to);
            
            log.info("Transfer completed: {} → {}, amount: {}", 
                from.getAccountNumber(), to.getAccountNumber(), amount);
            
            return new TransferSummary(
                from.getAccountNumber(),
                to.getAccountNumber(),
                amount,
                LocalDateTime.now(),
                "SUCCESS"
            );
        });
    }
    
    /**
     * Race multiple async operations
     * 
     * Returns result from first to complete
     */
    public CompletableFuture<BigDecimal> getExchangeRateFastest(
            String currency) {
        
        // Call multiple exchange rate APIs in parallel
        CompletableFuture<BigDecimal> provider1 = 
            CompletableFuture.supplyAsync(() -> {
                simulateDelay(2000); // Slow provider
                return new BigDecimal("1.12");
            }, taskExecutor);
        
        CompletableFuture<BigDecimal> provider2 = 
            CompletableFuture.supplyAsync(() -> {
                simulateDelay(500); // Fast provider
                return new BigDecimal("1.13");
            }, taskExecutor);
        
        CompletableFuture<BigDecimal> provider3 = 
            CompletableFuture.supplyAsync(() -> {
                simulateDelay(1000); // Medium provider
                return new BigDecimal("1.14");
            }, taskExecutor);
        
        // Return first completed
        return CompletableFuture.anyOf(provider1, provider2, provider3)
            .thenApply(result -> (BigDecimal) result);
    }
    
    // ========================================
    // ERROR HANDLING PATTERNS
    // ========================================
    
    /**
     * Handle errors gracefully
     */
    public CompletableFuture<Account> openAccountWithFallback(Long customerId) {
        return CompletableFuture
            .supplyAsync(() -> {
                Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
                
                // Validate KYC
                if (customer.getProfile() == null || 
                    !customer.getProfile().isKycVerified()) {
                    throw new RuntimeException("KYC verification required");
                }
                
                // Create premium account
                Account account = new Account(
                    generateAccountNumber(),
                    AccountType.INVESTMENT
                );
                account.setStatus(AccountStatus.ACTIVE);
                
                return accountRepository.save(account);
            }, taskExecutor)
            .handle((account, throwable) -> {
                if (throwable != null) {
                    log.error("Premium account failed, creating basic", throwable);
                    
                    // Fallback to basic account
                    Account basicAccount = new Account(
                        generateAccountNumber(),
                        AccountType.SAVINGS
                    );
                    basicAccount.setStatus(AccountStatus.ACTIVE);
                    
                    return accountRepository.save(basicAccount);
                }
                
                return account;
            });
    }
    
    // ========================================
    // HELPER METHODS
    // ========================================
    
    private void simulateDelay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
    
    private String generateAccountNumber() {
        return "ACC-" + System.currentTimeMillis();
    }
}

// Helper classes

record AccountOpeningResult(
    Customer customer,
    CustomerProfile profile,
    CreditScore creditScore,
    RiskRating riskRating,
    boolean approved,
    String message
) {}

record CreditScore(Long customerId, int score) {}

record RiskRating(String level) {}

record BatchResult(int successful, int failed, int total) {}

record TransferSummary(
    String fromAccount,
    String toAccount,
    BigDecimal amount,
    LocalDateTime timestamp,
    String status
) {}
