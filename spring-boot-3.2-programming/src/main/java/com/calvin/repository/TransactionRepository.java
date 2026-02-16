package com.calvin.repository;

import com.calvin.domain.Transaction;
import com.calvin.domain.CustomerTransactionReportDTO;
import com.calvin.domain.PaymentMethodStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

/**
 * Transaction Repository - Spring Data JPA with Functional Programming
 * 
 * <p>FinTech Principal Engineer's Guide to Modern Data Access</p>
 * 
 * <h2>The Layered Architecture (Hibernate → JPA → Spring Data JPA)</h2>
 * <ol>
 *   <li><b>JDBC:</b> Low-level database driver (java.sql.Connection)</li>
 *   <li><b>Hibernate:</b> ORM implementation (generates SQL from Java objects)</li>
 *   <li><b>JPA:</b> Specification (javax.persistence API)</li>
 *   <li><b>Spring Data JPA:</b> Repository abstraction (zero boilerplate)</li>
 * </ol>
 * 
 * <h2>Text Blocks Demonstration (Java 15+)</h2>
 * <p>80% reduction in SQL escaping - clean, readable queries</p>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    /**
     * Derived Query Method - Spring Data generates SQL automatically
     * 
     * <p>No SQL needed! Spring Data JPA derives query from method name</p>
     * <ul>
     *   <li>findBy → SELECT</li>
     *   <li>CustomerIdAndStatus → WHERE customer_id = ? AND status = ?</li>
     * </ul>
     */
    List<Transaction> findByCustomerIdAndStatus(Long customerId, String status);
    
    /**
     * Stream Query - Returns Stream instead of List
     * 
     * <p>Benefits:</p>
     * <ul>
     *   <li>Lazy evaluation - processes data on-demand</li>
     *   <li>Memory efficient for large result sets</li>
     *   <li>Perfect for functional pipelines</li>
     * </ul>
     * 
     * <p>IMPORTANT: Must use @Transactional on caller to keep connection open</p>
     */
    Stream<Transaction> findByCustomerId(Long customerId);
    
    /**
     * Custom JPQL Query with Text Blocks (Java 15+)
     * 
     * <p>Old Way (String concatenation hell):</p>
     * <pre>
     * &#64;Query("SELECT t FROM Transaction t " +
     *        "WHERE t.amount > :minAmount " +
     *        "AND t.status = 'APPROVED'")
     * </pre>
     * 
     * <p>New Way (Text Blocks - readable multiline SQL):</p>
     */
    @Query("""
        SELECT t 
        FROM Transaction t 
        WHERE t.amount > :minAmount 
        AND t.status = 'APPROVED'
        ORDER BY t.amount DESC
        """)
    List<Transaction> findHighValueApprovedTransactions(@Param("minAmount") BigDecimal minAmount);
    
    /**
     * Native SQL Query with Text Blocks
     * 
     * <p>Demonstrates complex SQL with JOINs, GROUP BY, HAVING</p>
     * <p>Notice: No ugly escape sequences (\n, \", +), just clean SQL!</p>
     */
    @Query(value = """
        SELECT 
            t.customer_id as customerId,
            'Customer-' || t.customer_id as customerName,
            COUNT(*) as totalTransactions,
            SUM(t.amount) as totalAmount,
            AVG(t.amount) as avgTransactionAmount
        FROM transactions t
        WHERE t.created_at BETWEEN :startDate AND :endDate
            AND t.status = 'APPROVED'
        GROUP BY t.customer_id
        HAVING COUNT(*) > :minTransactions
        ORDER BY totalAmount DESC
        """, nativeQuery = true)
    List<CustomerTransactionReportDTO> generateCustomerReport(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("minTransactions") int minTransactions
    );
    
    /**
     * DTO Projection with JPQL and Text Blocks
     * 
     * <p>Maps directly to Record constructor!</p>
     * <p>Note: "new com.calvin.domain.PaymentMethodStatsDTO(...)" syntax</p>
     */
    @Query("""
        SELECT new com.calvin.domain.PaymentMethodStatsDTO(
            t.paymentMethod,
            COUNT(t),
            SUM(t.amount),
            AVG(t.amount)
        )
        FROM Transaction t
        WHERE t.status = 'APPROVED'
        GROUP BY t.paymentMethod
        ORDER BY SUM(t.amount) DESC
        """)
    List<PaymentMethodStatsDTO> getPaymentMethodStatistics();
    
    /**
     * Complex Native Query with JSON (PostgreSQL-specific example)
     * 
     * <p>Text Blocks make JSON queries readable:</p>
     */
    @Query(value = """
        SELECT t.*
        FROM transactions t
        WHERE t.status = :status
            AND t.created_at >= CURRENT_DATE - INTERVAL '7 days'
            AND t.amount > :threshold
        ORDER BY t.created_at DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<Transaction> findRecentHighValueTransactions(
        @Param("status") String status,
        @Param("threshold") BigDecimal threshold,
        @Param("limit") int limit
    );
    
    /**
     * Method Query with Multiple Conditions
     * 
     * <p>Spring Data JPA derives complex query from method name</p>
     */
    List<Transaction> findByAmountBetweenAndStatusInOrderByCreatedAtDesc(
        BigDecimal minAmount,
        BigDecimal maxAmount,
        List<String> statuses
    );
    
    /**
     * Exists Query - Returns boolean for efficient checks
     * 
     * <p>Better than findBy...() != null for existence checks</p>
     */
    boolean existsByTransactionIdAndStatus(String transactionId, String status);
    
    /**
     * Count Query with Text Blocks
     */
    @Query("""
        SELECT COUNT(t)
        FROM Transaction t
        WHERE t.customerId = :customerId
            AND t.status = 'PENDING'
            AND t.createdAt > :since
        """)
    long countPendingTransactionsSince(
        @Param("customerId") Long customerId,
        @Param("since") LocalDateTime since
    );
}
