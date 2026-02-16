package com.calvin.fintech.repository;

import com.calvin.fintech.entity.Account;
import com.calvin.fintech.entity.AccountStatus;
import com.calvin.fintech.entity.AccountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Account Repository - Advanced Query Examples
 * 
 * Demonstrates queries for MANY-TO-MANY relationships
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>,
                                          JpaSpecificationExecutor<Account> {
    
    // ========================================
    // BASIC METHOD NAMING QUERIES
    // ========================================
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    List<Account> findByAccountType(AccountType accountType);
    
    List<Account> findByStatus(AccountStatus status);
    
    List<Account> findByBalanceGreaterThan(BigDecimal balance);
    
    List<Account> findByBalanceBetween(BigDecimal min, BigDecimal max);
    
    boolean existsByAccountNumber(String accountNumber);
    
    long countByAccountType(AccountType accountType);
    long countByStatus(AccountStatus status);
    
    // ========================================
    // MANY-TO-MANY QUERY EXAMPLES
    // ========================================
    
    /**
     * Find accounts by customer ID
     */
    @Query("SELECT a FROM Account a JOIN a.customers c WHERE c.id = :customerId")
    List<Account> findByCustomerId(@Param("customerId") Long customerId);
    
    /**
     * Find accounts by customer email
     */
    @Query("""
        SELECT a FROM Account a 
        JOIN a.customers c 
        WHERE c.email = :email 
        AND a.status = 'ACTIVE'
        """)
    List<Account> findByCustomerEmail(@Param("email") String email);
    
    /**
     * Find joint accounts (accounts with multiple customers)
     */
    @Query("""
SELECT a FROM Account a 
        WHERE SIZE(a.customers) > 1
        """)
    List<Account> findJointAccounts();
    
    /**
     * Find accounts with specific number of customers
     */
    @Query("SELECT a FROM Account a WHERE SIZE(a.customers) = :count")
    List<Account> findByCustomerCount(@Param("count") int count);
    
    /**
     * Load account with all customers (solve N+1)
     */
    @EntityGraph(attributePaths = {"customers"})
    Optional<Account> findWithCustomersById(Long id);
    
    /**
     * Load accounts with customers for a specific type
     */
    @EntityGraph(attributePaths = {"customers"})
    List<Account> findByAccountType(AccountType accountType);
    
    /**
     * Load account with customers and their profiles
     */
    @EntityGraph(attributePaths = {"customers", "customers.profile"})
    Optional<Account> findWithCustomerDetailsById(Long id);
    
    // ========================================
    // AGGREGATIONS & STATISTICS
    // ========================================
    
    /**
     * Get total balance by account type
     */
    @Query("""
        SELECT a.accountType, SUM(a.balance), COUNT(a) 
        FROM Account a 
        WHERE a.status = 'ACTIVE'
        GROUP BY a.accountType
        """)
    List<Object[]> getTotalBalanceByType();
    
    /**
     * Get total balance by currency
     */
    @Query("""
        SELECT a.currency, SUM(a.balance), AVG(a.balance), COUNT(a)
        FROM Account a 
        WHERE a.status = 'ACTIVE'
        GROUP BY a.currency
        ORDER BY SUM(a.balance) DESC
        """)
    List<Object[]> getTotalBalanceByCurrency();
    
    /**
     * Find high-balance accounts
     */
    @Query("""
        SELECT a FROM Account a 
        WHERE a.balance > :threshold 
        AND a.status = 'ACTIVE'
        ORDER BY a.balance DESC
        """)
    List<Account> findHighBalanceAccounts(@Param("threshold") BigDecimal threshold);
    
    /**
     * Get account distribution by type and status
     */
    @Query("""
        SELECT a.accountType, a.status, COUNT(a)
        FROM Account a
        GROUP BY a.accountType, a.status
        ORDER BY a.accountType, a.status
        """)
    List<Object[]> getAccountDistribution();
    
    /**
     * Calculate total assets for customer
     */
    @Query("""
        SELECT SUM(a.balance) 
        FROM Account a 
        JOIN a.customers c 
        WHERE c.id = :customerId 
        AND a.status = 'ACTIVE'
        """)
    BigDecimal calculateTotalAssets(@Param("customerId") Long customerId);
    
    // ========================================
    // COMPLEX QUERIES
    // ========================================
    
    /**
     * Find accounts opened in date range
     */
    @Query("""
        SELECT a FROM Account a 
        WHERE a.createdAt BETWEEN :start AND :end
        ORDER BY a.createdAt DESC
        """)
    List<Account> findAccountsOpenedBetween(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end);
    
    /**
     * Find dormant accounts (no activity)
     */
    @Query("""
        SELECT a FROM Account a 
        WHERE a.updatedAt < :cutoffDate 
        AND a.status = 'ACTIVE'
        AND a.balance > 0
        """)
    List<Account> findDormantAccounts(@Param("cutoffDate") LocalDateTime cutoffDate);
    
    /**
     * Search accounts by customer name
     */
    @Query("""
        SELECT DISTINCT a FROM Account a 
        JOIN a.customers c 
        WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :name, '%'))
        OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :name, '%'))
        """)
    List<Account> searchByCustomerName(@Param("name") String name);
    
    /**
     * Find accounts with low balance
     */
    @Query("""
        SELECT a FROM Account a 
        WHERE a.balance < :threshold 
        AND a.accountType = 'CHECKING'
        AND a.status = 'ACTIVE'
        """)
    List<Account> findLowBalanceCheckingAccounts(@Param("threshold") BigDecimal threshold);
    
    /**
     * Get savings accounts with high interest
     */
    @Query("""
        SELECT a FROM Account a 
        WHERE a.accountType = 'SAVINGS' 
        AND a.interestRate > :minRate
        AND a.status = 'ACTIVE'
        ORDER BY a.interestRate DESC
        """)
    List<Account> findHighInterestSavingsAccounts(@Param("minRate") BigDecimal minRate);
    
    // ========================================
    // NATIVE SQL QUERIES
    // ========================================
    
    /**
     * Complex aggregation with window functions
     */
    @Query(value = """
        SELECT 
            a.*,
            COUNT(ca.customer_id) as customer_count,
            SUM(a.balance) OVER (PARTITION BY a.account_type) as type_total,
            RANK() OVER (ORDER BY a.balance DESC) as balance_rank
        FROM accounts a
        LEFT JOIN customer_accounts ca ON a.id = ca.account_id
        WHERE a.status = 'ACTIVE'
        GROUP BY a.id
        ORDER BY a.balance DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<Object[]> getTopAccountsWithRanking(@Param("limit") int limit);
    
    /**
     * Find accounts with specific relationship pattern
     */
    @Query(value = """
        WITH account_customers AS (
            SELECT 
                a.id,
                a.account_number,
                a.balance,
                COUNT(ca.customer_id) as customer_count
            FROM accounts a
            JOIN customer_accounts ca ON a.id = ca.account_id
            GROUP BY a.id, a.account_number, a.balance
        )
        SELECT * FROM account_customers
        WHERE customer_count >= :minCustomers
        ORDER BY balance DESC
        """, nativeQuery = true)
    List<Object[]> findAccountsByCustomerCount(@Param("minCustomers") int minCustomers);
    
    // ========================================
    // MODIFYING QUERIES
    // ========================================
    
    /**
     * Bulk update status
     */
    @Modifying
    @Query("UPDATE Account a SET a.status = :newStatus WHERE a.status = :oldStatus")
    int bulkUpdateStatus(
        @Param("oldStatus") AccountStatus oldStatus,
        @Param("newStatus") AccountStatus newStatus);
    
    /**
     * Apply interest to savings accounts
     */
    @Modifying
    @Query("""
        UPDATE Account a 
        SET a.balance = a.balance * (1 + a.interestRate)
        WHERE a.accountType = 'SAVINGS' 
        AND a.status = 'ACTIVE'
        AND a.interestRate IS NOT NULL
        """)
    int applyInterestToSavingsAccounts();
    
    /**
     * Freeze accounts with low balance
     */
    @Modifying
    @Query("""
        UPDATE Account a 
        SET a.status = 'FROZEN' 
        WHERE a.balance < :threshold 
        AND a.accountType = 'CHECKING'
        AND a.status = 'ACTIVE'
        """)
    int freezeLowBalanceAccounts(@Param("threshold") BigDecimal threshold);
    
    /**
     * Close dormant accounts
     */
    @Modifying
    @Query("""
        UPDATE Account a 
        SET a.status = 'CLOSED' 
        WHERE a.updatedAt < :cutoffDate 
        AND a.balance = 0
        AND a.status = 'ACTIVE'
        """)
    int closeDormantAccounts(@Param("cutoffDate") LocalDateTime cutoffDate);
    
    // ========================================
    // PROJECTIONS
    // ========================================
    
    /**
     * Lightweight projection for lists
     */
    List<AccountSummary> findByStatus(AccountStatus status);
    
    /**
     * Projection with customer info
     */
    @Query("""
        SELECT a FROM Account a 
        LEFT JOIN FETCH a.customers 
        WHERE a.id = :id
        """)
    Optional<AccountWithCustomers> findAccountWithCustomers(@Param("id") Long id);
    
    /**
     * Dynamic projection
     */
    <T> List<T> findByAccountType(AccountType accountType, Class<T> type);
    
    // ========================================
    // PAGINATION
    // ========================================
    
    Page<Account> findByAccountType(AccountType accountType, Pageable pageable);
    
    Page<Account> findByStatusAndBalanceGreaterThan(
        AccountStatus status, 
        BigDecimal balance, 
        Pageable pageable);
    
    @EntityGraph(attributePaths = {"customers"})
    Page<Account> findByStatus(AccountStatus status, Pageable pageable);
}

// ========================================
// PROJECTION INTERFACES
// ========================================

/**
 * Account summary projection
 */
interface AccountSummary {
    Long getId();
    String getAccountNumber();
    AccountType getAccountType();
    BigDecimal getBalance();
    String getCurrency();
    AccountStatus getStatus();
    
    // Computed property
    default String getFormattedBalance() {
        return getCurrency() + " " + getBalance();
    }
}

/**
 * Account with customers projection
 */
interface AccountWithCustomers {
    Long getId();
    String getAccountNumber();
    BigDecimal getBalance();
    
    List<CustomerInfo> getCustomers();
    
    interface CustomerInfo {
        String getFirstName();
        String getLastName();
        String getEmail();
    }
}
