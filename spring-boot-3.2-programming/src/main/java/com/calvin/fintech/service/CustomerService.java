package com.calvin.fintech.service;

import com.calvin.fintech.entity.*;
import com.calvin.fintech.repository.CustomerRepository;
import com.calvin.fintech.repository.CustomerProfileRepository;
import com.calvin.fintech.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Isolation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Customer Service - Transaction Management Examples
 * 
 * Demonstrates:
 * 1. @Transactional basics
 * 2. Propagation levels (REQUIRED, REQUIRES_NEW, MANDATORY, etc.)
 * 3. Isolation levels
 * 4. Read-only optimization
 * 5. Rollback rules
 * 6. Transaction pitfalls and solutions
 */
@Service
@Transactional(readOnly = true)  // Default for all methods (can override)
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    private final CustomerProfileRepository profileRepository;
    private final AccountRepository accountRepository;
    
    public CustomerService(
            CustomerRepository customerRepository,
            CustomerProfileRepository profileRepository,
            AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.profileRepository = profileRepository;
        this.accountRepository = accountRepository;
    }
    
    // ========================================
    // BASIC TRANSACTIONS
    // ========================================
    
    /**
     * Create customer with profile
     * 
     * @Transactional default propagation is REQUIRED
     * If no transaction exists, creates one
     * If transaction exists, joins it
     */
    @Transactional
    public Customer createCustomerWithProfile(
            String firstName, 
            String lastName, 
            String email,
            String phoneNumber,
            LocalDate dateOfBirth) {
        
        // Check if email already exists
        if (customerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        
        // Create customer
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setStatus(CustomerStatus.ACTIVE);
        
        // Save customer first (to get ID)
        customer = customerRepository.save(customer);
        
        // Create profile
        CustomerProfile profile = new CustomerProfile(customer);
        profile.setPhoneNumber(phoneNumber);
        profile.setDateOfBirth(dateOfBirth);
        profile.setKycStatus(KycStatus.PENDING);
        
        // Save profile
        profileRepository.save(profile);
        
        // Set bidirectional relationship
        customer.setProfile(profile);
        
        return customer;
        // Transaction commits automatically if no exception
        // Transaction rolls back if any RuntimeException occurs
    }
    
    /**
     * Update customer
     * 
     * readOnly = false means this can modify data
     */
    @Transactional
    public Customer updateCustomer(Long id, String firstName, String lastName) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
        
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        
        // No need to call save() - JPA tracks changes automatically
        // This is called "dirty checking"
        return customer;
    }
    
    /**
     * Read-only transaction (optimization)
     * 
     * readOnly = true tells JPA:
     * 1. Skip dirty checking (better performance)
     * 2. Flush mode can be set to MANUAL
     * 3. Database can optimize (read-only connection)
     */
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public Customer findByIdWithProfile(Long id) {
        return customerRepository.findByIdWithProfile(id)
            .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Customer> findActiveCustomers() {
        return customerRepository.findActiveCustomers();
    }
    
    // ========================================
    // PROPAGATION LEVELS
    // ========================================
    
    /**
     * REQUIRED (default)
     * - Uses existing transaction if present
     * - Creates new transaction if none exists
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Customer activateCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        customer.setStatus(CustomerStatus.ACTIVE);
        
        // This runs in the same transaction
        logActivation(id);  // Joins this transaction
        
        return customer;
    }
    
    /**
     * REQUIRES_NEW
     * - ALWAYS creates a NEW transaction
     * - Suspends current transaction if exists
     * 
     * Use case: Audit logging that should commit even if main transaction fails
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logActivation(Long customerId) {
        // This runs in a SEPARATE transaction
        // Even if activateCustomer() fails, this log will be saved
        System.out.println("Customer activated: " + customerId + " at " + LocalDateTime.now());
        
        // In real app, save to audit log table
    }
    
    /**
     * MANDATORY
     * - MUST run within existing transaction
     * - Throws exception if no transaction exists
     * 
     * Use case: Methods that should only be called from transactional context
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void validateCustomerState(Customer customer) {
        if (customer.isDeleted()) {
            throw new IllegalStateException("Cannot operate on deleted customer");
        }
        if (!customer.isActive()) {
            throw new IllegalStateException("Customer is not active");
        }
    }
    
    /**
     * SUPPORTS
     * - Runs with transaction if exists
     * - Runs without transaction if none exists
     * 
     * Use case: Read operations that can work with or without transaction
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long countActiveCustomers() {
        return customerRepository.countByStatus(CustomerStatus.ACTIVE);
    }
    
    /**
     * NOT_SUPPORTED
     * - ALWAYS runs without transaction
     * - Suspends current transaction if exists
     * 
     * Use case: Operations that should not be transactional (external API calls)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendWelcomeEmail(String email) {
        // External API call - should not be in transaction
        System.out.println("Sending welcome email to: " + email);
        // In real app: emailService.send(email, "Welcome!");
    }
    
    /**
     * NEVER
     * - MUST run without transaction
     * - Throws exception if transaction exists
     * 
     * Use case: Operations that must not run in transaction
     */
    @Transactional(propagation = Propagation.NEVER)
    public void checkSystemHealth() {
        // Health checks should not be transactional
        long count = customerRepository.count();
        System.out.println("Total customers: " + count);
    }
    
    /**
     * NESTED
     * - Creates nested transaction (savepoint) if transaction exists
     * - Creates new transaction if none exists
     * 
     * Note: Not supported by all databases
     */
    @Transactional(propagation = Propagation.NESTED)
    public void updateOptionalData(Long customerId, String occupation) {
        // This can rollback independently of parent transaction
        CustomerProfile profile = profileRepository.findByCustomerId(customerId)
            .orElse(null);
        
        if (profile != null) {
            profile.setOccupation(occupation);
        }
    }
    
    // ========================================
    // ISOLATION LEVELS
    // ========================================
    
    /**
     * READ_UNCOMMITTED (lowest isolation)
     * - Can read uncommitted changes (dirty reads)
     * - Fastest but least safe
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public long getApproximateCustomerCount() {
        return customerRepository.count();
    }
    
    /**
     * READ_COMMITTED (default for most databases)
     * - Only reads committed data
     * - Prevents dirty reads
     * - Non-repeatable reads possible
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
    
    /**
     * REPEATABLE_READ
     * - Guarantees same read returns same data within transaction
     * - Prevents dirty reads and non-repeatable reads
     * - Phantom reads possible
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal calculateTotalAssets(Long customerId) {
        // Even if data changes, this will see consistent view
        return accountRepository.calculateTotalAssets(customerId);
    }
    
    /**
     * SERIALIZABLE (highest isolation)
     * - Complete isolation
     * - Prevents dirty reads, non-repeatable reads, phantom reads
     * - Slowest performance
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transferCustomerOwnership(Long fromId, Long toId) {
        // Critical operation requiring complete isolation
        Customer from = customerRepository.findById(fromId)
            .orElseThrow(() -> new RuntimeException("Source customer not found"));
        Customer to = customerRepository.findById(toId)
            .orElseThrow(() -> new RuntimeException("Target customer not found"));
        
        // Transfer accounts...
    }
    
    // ========================================
    // ROLLBACK RULES
    // ========================================
    
    /**
     * Default: Rollback on RuntimeException and Error
     * No rollback on checked exceptions
     */
    @Transactional
    public Customer createCustomerDefault(String firstName, String email) throws Exception {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setEmail(email);
        
        customerRepository.save(customer);
        
        // RuntimeException -> ROLLBACK
        if (email.contains("bad")) {
            throw new RuntimeException("Bad email");
        }
        
        // Checked exception -> NO ROLLBACK (by default)
        if (email.contains("invalid")) {
            throw new Exception("Invalid email");
        }
        
        return customer;
    }
    
    /**
     * Custom rollback rules
     */
    @Transactional(
        rollbackFor = Exception.class,           // Rollback on ANY exception
        noRollbackFor = IllegalArgumentException.class  // Except IllegalArgumentException
    )
    public Customer createCustomerCustomRollback(String firstName, String email) throws Exception {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setEmail(email);
        
        customerRepository.save(customer);
        
        // Will rollback
        if (email.contains("bad")) {
            throw new Exception("Bad email");
        }
        
        // Will NOT rollback
        if (email.contains("skip")) {
            throw new IllegalArgumentException("Skip this error");
        }
        
        return customer;
    }
    
    // ========================================
    // COMPLEX TRANSACTIONS
    // ========================================
    
    /**
     * Open new account for customer
     * 
     * Demonstrates:
     * - Multiple repository operations
     * - Bidirectional relationship management
     * - Validation within transaction
     */
    @Transactional
    public Account openAccount(Long customerId, AccountType accountType, String accountNumber) {
        // Load customer with accounts to avoid N+1
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        // Validate customer state (MANDATORY propagation)
        validateCustomerState(customer);
        
        // Verify KYC for certain account types
        if (accountType == AccountType.INVESTMENT) {
            if (customer.getProfile() == null || !customer.getProfile().isKycVerified()) {
                throw new IllegalStateException("KYC verification required for investment accounts");
            }
        }
        
        // Check if account number already exists
        if (accountRepository.existsByAccountNumber(accountNumber)) {
            throw new IllegalArgumentException("Account number already exists");
        }
        
        // Create account
        Account account = new Account(accountNumber, accountType);
        account.setStatus(AccountStatus.PENDING_APPROVAL);
        
        // Set interest rate for savings
        if (accountType == AccountType.SAVINGS) {
            account.setInterestRate(new BigDecimal("0.0250")); // 2.5%
        }
        
        // Set overdraft for checking
        if (accountType == AccountType.CHECKING) {
            account.setOverdraftLimit(new BigDecimal("500.00"));
        }
        
        // Save account
        account = accountRepository.save(account);
        
        // Link to customer (many-to-many)
        customer.addAccount(account);
        
        // Log audit (REQUIRES_NEW - separate transaction)
        logActivation(customerId);
        
        // Send notification (NOT_SUPPORTED - no transaction)
        sendWelcomeEmail(customer.getEmail());
        
        return account;
    }
    
    /**
     * Complex workflow with multiple steps
     */
    @Transactional
    public void completeKycProcess(Long customerId, String taxId, String nationality) {
        Customer customer = customerRepository.findByIdWithProfile(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        CustomerProfile profile = customer.getProfile();
        if (profile == null) {
            throw new IllegalStateException("Customer has no profile");
        }
        
        // Update profile data
        profile.setTaxId(taxId);
        profile.setNationality(nationality);
        
        // Verify KYC
        profile.verifyKyc();
        
        // Activate customer if pending
        if (customer.getStatus() == CustomerStatus.PENDING) {
            customer.setStatus(CustomerStatus.ACTIVE);
        }
        
        // Approve pending accounts
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        for (Account account : accounts) {
            if (account.getStatus() == AccountStatus.PENDING_APPROVAL) {
                account.setStatus(AccountStatus.ACTIVE);
            }
        }
        
        // All changes are tracked and saved automatically (dirty checking)
    }
    
    /**
     * Soft delete with cascade
     */
    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        // Check for active accounts
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        boolean hasActiveAccounts = accounts.stream()
            .anyMatch(a -> a.getStatus() == AccountStatus.ACTIVE);
        
        if (hasActiveAccounts) {
            throw new IllegalStateException("Cannot delete customer with active accounts");
        }
        
        // Soft delete
        customer.markAsDeleted();
        
        // No need to call save() - dirty checking handles it
    }
    
    // ========================================
    // COMMON PITFALLS & SOLUTIONS
    // ========================================
    
    /**
     * PITFALL: Calling @Transactional method from same class
     * 
     * Problem: Spring uses proxies for transactions
     * Solution: Inject self or use separate service
     */
    @Transactional
    public void examplePitfall(Long customerId) {
        // THIS WILL NOT START A NEW TRANSACTION!
        // logActivation(customerId);  
        
        // Solution: Inject self and call through proxy
        // Or move to separate service
    }
    
    /**
     * PITFALL: LazyInitializationException
     * 
     * Problem: Accessing lazy collection outside transaction
     * Solution: Load eagerly or use @Transactional on caller
     */
    @Transactional(readOnly = true)
    public void avoidLazyInitException() {
        Customer customer = customerRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("Not found"));
        
        // Force initialization within transaction
        customer.getTransactions().size();
        customer.getAccounts().size();
        
        // Or use EntityGraph/JOIN FETCH in repository
    }
}
