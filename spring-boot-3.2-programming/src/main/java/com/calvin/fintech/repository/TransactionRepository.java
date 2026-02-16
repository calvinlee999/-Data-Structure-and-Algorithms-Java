package com.calvin.fintech.repository;

import com.calvin.fintech.entity.Transaction;
import com.calvin.fintech.entity.TransactionStatus;
import com.calvin.fintech.entity.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Transaction Repository
 * 
 * This interface provides database access for Transaction entities.
 * Spring Data JPA automatically implements these methods - you just
 * define the method signature and Spring does the work!
 * 
 * Think of this as a "librarian" that knows how to find, save,
 * and organize transactions in the database.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    /**
     * Find all transactions for a specific customer
     * 
     * Method naming convention:
     * - "findBy" + field name = Spring generates the query automatically
     * - "CustomerIdAndDeleted" = WHERE customer_id = ? AND deleted = ?
     */
    List<Transaction> findByCustomerIdAndDeletedFalse(Long customerId);
    
    /**
     * Find transactions for a customer with pagination
     * 
     * Pageable allows sorting and pagination:
     * - Page 0, size 20 = first 20 transactions
     * - Page 1, size 20 = next 20 transactions
     */
    Page<Transaction> findByCustomerIdAndDeletedFalse(Long customerId, Pageable pageable);
    
    /**
     * Find transactions by status
     * 
     * Useful for:
     * - Finding all PENDING transactions to process
     * - Finding FAILED transactions to retry
     */
    List<Transaction> findByStatusAndDeletedFalse(TransactionStatus status);
    
    /**
     * Find transactions by type
     */
    List<Transaction> findByTypeAndDeletedFalse(TransactionType type);
    
    /**
     * Find transactions by reference number
     * 
     * Optional return type because reference might not exist.
     * Using Optional prevents NullPointerException.
     */
    Optional<Transaction> findByReferenceNumberAndDeletedFalse(String referenceNumber);
    
    /**
     * Find transactions within a date range
     * 
     * "Between" keyword generates SQL: WHERE created_at BETWEEN ? AND ?
     */
    List<Transaction> findByCreatedAtBetweenAndDeletedFalse(
        LocalDateTime startDate, 
        LocalDateTime endDate
    );
    
    /**
     * Find transactions by customer and status
     * 
     * Combining multiple conditions with "And"
     */
    List<Transaction> findByCustomerIdAndStatusAndDeletedFalse(
        Long customerId, 
        TransactionStatus status
    );
    
    /**
     * Find transactions by customer and type
     */
    List<Transaction> findByCustomerIdAndTypeAndDeletedFalse(
        Long customerId, 
        TransactionType type
    );
    
    /**
     * Find transactions by amount greater than
     * 
     * "GreaterThanEqual" generates SQL: WHERE amount >= ?
     */
    List<Transaction> findByAmountGreaterThanEqualAndDeletedFalse(BigDecimal minAmount);
    
    /**
     * Find transactions by amount between range
     */
    List<Transaction> findByAmountBetweenAndDeletedFalse(
        BigDecimal minAmount, 
        BigDecimal maxAmount
    );
    
    /**
     * Custom query using JPQL (Java Persistence Query Language)
     * 
     * @Query allows writing custom queries when method naming gets too complex.
     * :customerId is a named parameter (clearer than ?1, ?2)
     */
    @Query("SELECT t FROM Transaction t WHERE t.customerId = :customerId " +
           "AND t.status = :status AND t.deleted = false " +
           "ORDER BY t.createdAt DESC")
    List<Transaction> findCustomerTransactionsByStatus(
        @Param("customerId") Long customerId,
        @Param("status") TransactionStatus status
    );
    
    /**
     * Complex search query with multiple optional filters
     * 
     * This query handles NULL parameters gracefully:
     * - If status is NULL, it's excluded from the WHERE clause
     * - If minAmount is NULL, it's excluded
     * etc.
     */
    @Query("SELECT t FROM Transaction t WHERE " +
           "(:customerId IS NULL OR t.customerId = :customerId) AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:type IS NULL OR t.type = :type) AND " +
           "(:minAmount IS NULL OR t.amount >= :minAmount) AND " +
           "(:maxAmount IS NULL OR t.amount <= :maxAmount) AND " +
           "(:startDate IS NULL OR t.createdAt >= :startDate) AND " +
           "(:endDate IS NULL OR t.createdAt <= :endDate) AND " +
           "t.deleted = false")
    Page<Transaction> searchTransactions(
        @Param("customerId") Long customerId,
        @Param("status") TransactionStatus status,
        @Param("type") TransactionType type,
        @Param("minAmount") BigDecimal minAmount,
        @Param("maxAmount") BigDecimal maxAmount,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        Pageable pageable
    );
    
    /**
     * Calculate total amount by customer and status
     * 
     * Aggregate function: SUM, COUNT, AVG, etc.
     * Returns BigDecimal (the sum of amounts)
     */
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE " +
           "t.customerId = :customerId AND t.status = :status AND t.deleted = false")
    BigDecimal calculateTotalAmountByCustomerAndStatus(
        @Param("customerId") Long customerId,
        @Param("status") TransactionStatus status
    );
    
    /**
     * Count transactions by customer
     */
    long countByCustomerIdAndDeletedFalse(Long customerId);
    
    /**
     * Count transactions by status
     */
    long countByStatusAndDeletedFalse(TransactionStatus status);
    
    /**
     * Check if transaction exists by reference number
     * 
     * Returns boolean - useful for validation
     * "exists" is more efficient than "find" when you only need yes/no
     */
    boolean existsByReferenceNumberAndDeletedFalse(String referenceNumber);
    
    /**
     * Soft delete transaction
     * 
     * Updates deleted flag instead of actually deleting the row.
     * Financial records should NEVER be hard-deleted!
     * 
     * @Modifying tells Spring this query modifies data
     * Returns number of rows updated
     */
    @org.springframework.data.jpa.repository.Modifying
    @Query("UPDATE Transaction t SET t.deleted = true WHERE t.id = :id")
    int softDelete(@Param("id") Long id);
    
    /**
     * Find recently created transactions (last N days)
     */
    @Query("SELECT t FROM Transaction t WHERE " +
           "t.createdAt >= :startDate AND t.deleted = false " +
           "ORDER BY t.createdAt DESC")
    List<Transaction> findRecentTransactions(@Param("startDate") LocalDateTime startDate);
    
    /**
     * Find top N transactions by amount
     * 
     * Useful for finding largest transactions (fraud detection)
     */
    @Query("SELECT t FROM Transaction t WHERE t.deleted = false " +
           "ORDER BY t.amount DESC")
    List<Transaction> findTopByAmountDesc(Pageable pageable);
}
