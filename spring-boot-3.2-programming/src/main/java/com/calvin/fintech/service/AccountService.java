package com.calvin.fintech.service;

import com.calvin.fintech.entity.*;
import com.calvin.fintech.repository.AccountRepository;
import com.calvin.fintech.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

import java.math.BigDecimal;
import java.util.List;

/**
 * Account Service - Complex Transaction Examples
 * 
 * Demonstrates:
 * 1. Multi-entity transactions
 * 2. Pessimistic locking
 * 3. Optimistic locking with @Version
 * 4. Transaction compensation (rollback handling)
 */
@Service
@Transactional(readOnly = true)
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    
    public AccountService(
            AccountRepository accountRepository,
            CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }
    
    // ========================================
    // ACCOUNT OPERATIONS
    // ========================================
    
    /**
     * Deposit money
     * 
     * Uses optimistic locking (@Version)
     */
    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        account.deposit(amount);
        
        return accountRepository.save(account);
        // If @Version changed, throws OptimisticLockException
    }
    
    /**
     * Withdraw money
     */
    @Transactional
    public Account withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        account.withdraw(amount);
        
        return accountRepository.save(account);
    }
    
    /**
     * Transfer money between accounts
     * 
     * CRITICAL: Both accounts must be locked to prevent race conditions
     * Uses SERIALIZABLE isolation for maximum safety
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        // Validate
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }
        
        // Load accounts (order by ID to prevent deadlock)
        Long firstId = fromAccountId < toAccountId ? fromAccountId : toAccountId;
        Long secondId = fromAccountId < toAccountId ? toAccountId : fromAccountId;
        
        Account first = accountRepository.findById(firstId)
            .orElseThrow(() -> new RuntimeException("Account not found: " + firstId));
        Account second = accountRepository.findById(secondId)
            .orElseThrow(() -> new RuntimeException("Account not found: " + secondId));
        
        Account fromAccount = fromAccountId.equals(firstId) ? first : second;
        Account toAccount = toAccountId.equals(firstId) ? first : second;
        
        // Execute transfer
        fromAccount.withdraw(amount);  // May throw InsufficientFundsException
        toAccount.deposit(amount);
        
        // Save both (dirty checking will update)
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        
        // Transaction commits automatically
        // If any exception, both operations rollback
    }
    
    /**
     * Transfer with explicit pessimistic locking
     * 
     * Alternative approach using database-level locks
     */
    @Transactional
    public void transferWithLocking(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        // In real app, use:
        // @Lock(LockModeType.PESSIMISTIC_WRITE)
        // Account findByIdForUpdate(Long id);
        
        // This prevents other transactions from reading/writing
        // until this transaction completes
        
        Account fromAccount = accountRepository.findById(fromAccountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }
    
    /**
     * Batch interest calculation
     * 
     * Demonstrates bulk operations in transaction
     */
    @Transactional
    public int applyMonthlyInterest() {
        // Get all savings accounts
        List<Account> savingsAccounts = accountRepository
            .findByAccountType(AccountType.SAVINGS);
        
        int processed = 0;
        for (Account account : savingsAccounts) {
            if (account.getInterestRate() != null && 
                account.getStatus() == AccountStatus.ACTIVE) {
                
                BigDecimal interest = account.getBalance()
                    .multiply(account.getInterestRate())
                    .divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP);
                
                account.deposit(interest);
                processed++;
            }
        }
        
        return processed;
        // All changes tracked by dirty checking
    }
    
    /**
     * Close account workflow
     * 
     * Multi-step business logic in transaction
     */
    @Transactional
    public void closeAccount(Long accountId) {
        Account account = accountRepository.findWithCustomersById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        
        // Validate can close
        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException(
                "Cannot close account with balance: " + account.getBalance()
            );
        }
        
        // Remove from all customers
        for (Customer customer : account.getCustomers()) {
            customer.removeAccount(account);
        }
        
        // Close account
        account.close();
        
        // Save changes (or rely on dirty checking)
        accountRepository.save(account);
    }
    
    /**
     * Create joint account
     * 
     * Many-to-many relationship management
     */
    @Transactional
    public Account createJointAccount(
            List<Long> customerIds, 
            AccountType accountType,
            String accountNumber) {
        
        if (customerIds.size() < 2) {
            throw new IllegalArgumentException("Joint account requires at least 2 customers");
        }
        
        // Load all customers
        List<Customer> customers = customerRepository.findAllById(customerIds);
        
        if (customers.size() != customerIds.size()) {
            throw new RuntimeException("Some customers not found");
        }
        
        // Verify all customers are active and KYC verified
        for (Customer customer : customers) {
            if (!customer.isActive()) {
                throw new IllegalStateException(
                    "Customer not active: " + customer.getEmail()
                );
            }
            
            if (customer.getProfile() == null || !customer.getProfile().isKycVerified()) {
                throw new IllegalStateException(
                    "KYC not verified for: " + customer.getEmail()
                );
            }
        }
        
        // Create account
        Account account = new Account(accountNumber, accountType);
        account.setStatus(AccountStatus.ACTIVE);
        
        account = accountRepository.save(account);
        
        // Link all customers
        for (Customer customer : customers) {
            customer.addAccount(account);
        }
        
        return account;
    }
    
    /**
     * Freeze accounts by criteria
     * 
     * Demonstrates conditional bulk operations
     */
    @Transactional
    public int freezeInactiveAccounts(int daysInactive) {
        java.time.LocalDateTime cutoff = java.time.LocalDateTime.now()
            .minusDays(daysInactive);
        
        List<Account> accounts = accountRepository.findDormantAccounts(cutoff);
        
        int frozen = 0;
        for (Account account : accounts) {
            account.freeze();
            frozen++;
        }
        
        return frozen;
    }
    
    /**
     * Error handling with partial rollback
     * 
     * Demonstrates transaction compensation pattern
     */
    @Transactional
    public void batchDeposit(List<DepositRequest> requests) {
        int processed = 0;
        
        try {
            for (DepositRequest request : requests) {
                Account account = accountRepository.findById(request.accountId())
                    .orElseThrow(() -> new RuntimeException("Account not found"));
                
                account.deposit(request.amount());
                processed++;
            }
        } catch (Exception e) {
            // Entire transaction rolls back
            System.err.println("Failed after processing " + processed + " deposits");
            throw e; // Re-throw to rollback
        }
    }
    
    /**
     * Better approach: Process individually with separate transactions
     * 
     * Each deposit is independent transaction
     */
    public BatchResult batchDepositSafe(List<DepositRequest> requests) {
        int success = 0;
        int failed = 0;
        
        for (DepositRequest request : requests) {
            try {
                depositSingle(request.accountId(), request.amount());
                success++;
            } catch (Exception e) {
                failed++;
                // Log error but continue
                System.err.println("Failed deposit for account " + 
                    request.accountId() + ": " + e.getMessage());
            }
        }
        
        return new BatchResult(success, failed);
    }
    
    @Transactional
    private void depositSingle(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        account.deposit(amount);
    }
}

// Helper classes
record DepositRequest(Long accountId, BigDecimal amount) {}
record BatchResult(int success, int failed) {}
