package com.calvin.fintech.repository;

import com.calvin.fintech.entity.Customer;
import com.calvin.fintech.entity.CustomerStatus;
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
 * Customer Repository - Advanced Query Examples
 * 
 * Demonstrates:
 * 1. Method naming queries
 * 2. @Query with JPQL
 * 3. @EntityGraph to solve N+1 problem
 * 4. Native SQL queries
 * 5. Projections
 * 6. Specifications support
 * 7. Modifying queries
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, 
                                           JpaSpecificationExecutor<Customer> {
    
    // ========================================
    // METHOD NAMING QUERIES
    // ========================================
    
    /**
     * Find by email (unique field)
     */
    Optional<Customer> findByEmail(String email);
    
    /**
     * Find by status
     */
    List<Customer> findByStatus(CustomerStatus status);
    
    /**
     * Find by status with deleted filter
     */
    List<Customer> findByStatusAndDeletedFalse(CustomerStatus status);
    
    /**
     * Find active customers only
     */
    default List<Customer> findActiveCustomers() {
        return findByStatusAndDeletedFalse(CustomerStatus.ACTIVE);
    }
    
    /**
     * Find by name (case-insensitive contains)
     */
    List<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
        String firstName, String lastName);
    
    /**
     * Find created between dates
     */
    List<Customer> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    /**
     * Count by status
     */
    long countByStatus(CustomerStatus status);
    long countByDeletedFalse();
    
    /**
     * Exists by email
     */
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
    
    // ========================================
    // @QUERY WITH JPQL
    // ========================================
    
    /**
     * Find with profile (JOIN FETCH to avoid N+1)
     */
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.profile WHERE c.id = :id")
    Optional<Customer> findByIdWithProfile(@Param("id") Long id);
    
    /**
     * Find with all relationships loaded
     */
    @Query("""
        SELECT DISTINCT c FROM Customer c 
        LEFT JOIN FETCH c.profile 
        LEFT JOIN FETCH c.accounts 
        WHERE c.id = :id
        """)
    Optional<Customer> findByIdWithAllRelationships(@Param("id") Long id);
    
    /**
     * Search by multiple criteria
     */
    @Query("""
        SELECT c FROM Customer c 
        WHERE (:status IS NULL OR c.status = :status)
        AND (:email IS NULL OR c.email LIKE CONCAT('%', :email, '%'))
        AND c.deleted = false
        ORDER BY c.createdAt DESC
        """)
    List<Customer> searchCustomers(
        @Param("status") CustomerStatus status,
        @Param("email") String email);
    
    /**
     * Find customers with transactions above amount
     */
    @Query("""
        SELECT DISTINCT c FROM Customer c 
        JOIN c.transactions t 
        WHERE t.amount > :amount 
        AND t.deleted = false
        """)
    List<Customer> findCustomersWithTransactionsAbove(@Param("amount") BigDecimal amount);
    
    /**
     * Get customer statistics
     */
    @Query("""
        SELECT 
            c.status as status,
            COUNT(c) as count,
            COUNT(DISTINCT c.profile) as withProfile
        FROM Customer c 
        WHERE c.deleted = false
        GROUP BY c.status
        """)
    List<Object[]> getCustomerStatistics();
    
    /**
     * Better: Use projection for statistics
     */
    @Query("""
        SELECT new com.calvin.fintech.dto.CustomerStatistics(
            c.status,
            COUNT(c),
            COUNT(c.profile)
        )
        FROM Customer c 
        WHERE c.deleted = false
        GROUP BY c.status
        """)
    List<CustomerStatisticsProjection> getCustomerStatisticsProjection();
    
    /**
     * Find customers by account type
     */
    @Query("""
        SELECT DISTINCT c FROM Customer c 
        JOIN c.accounts a 
        WHERE a.accountType = :accountType 
        AND a.status = 'ACTIVE'
        """)
    List<Customer> findByAccountType(@Param("accountType") String accountType);
    
    /**
     * Get high-value customers (total balance > threshold)
     */
    @Query("""
        SELECT c FROM Customer c 
        JOIN c.accounts a 
        GROUP BY c 
        HAVING SUM(a.balance) > :threshold
        ORDER BY SUM(a.balance) DESC
        """)
    List<Customer> findHighValueCustomers(@Param("threshold") BigDecimal threshold);
    
    // ========================================
    // @ENTITYGRAPH - Solve N+1 Problem
    // ========================================
    
    /**
     * Load customer with profile using EntityGraph
     */
    @EntityGraph(attributePaths = {"profile"})
    Optional<Customer> findWithProfileById(Long id);
    
    /**
     * Load customers with multiple associations
     */
    @EntityGraph(attributePaths = {"profile", "accounts", "transactions"})
    List<Customer> findByStatus(CustomerStatus status);
    
    /**
     * EntityGraph with pagination
     */
    @EntityGraph(attributePaths = {"profile"})
    Page<Customer> findByDeletedFalse(Pageable pageable);
    
    // ========================================
    // NATIVE SQL QUERIES
    // ========================================
    
    /**
     * Complex aggregate query using window functions
     */
    @Query(value = """
        SELECT 
            c.*,
            COUNT(t.id) OVER (PARTITION BY c.id) as transaction_count,
            SUM(t.amount) OVER (PARTITION BY c.id) as total_amount,
            RANK() OVER (ORDER BY COUNT(t.id) DESC) as customer_rank
        FROM customers c
        LEFT JOIN transactions t ON c.id = t.customer_id
        WHERE c.deleted = false
        ORDER BY transaction_count DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<Object[]> findTopCustomersByTransactionCount(@Param("limit") int limit);
    
    /**
     * CTE (Common Table Expression) example
     */
    @Query(value = """
        WITH monthly_activity AS (
            SELECT 
                customer_id,
                DATE_TRUNC('month', created_at) as month,
                COUNT(*) as transaction_count
            FROM transactions
            WHERE created_at >= :startDate
            GROUP BY customer_id, DATE_TRUNC('month', created_at)
        )
        SELECT c.* 
        FROM customers c
        JOIN monthly_activity ma ON c.id = ma.customer_id
        WHERE ma.transaction_count > :threshold
        """, nativeQuery = true)
    List<Customer> findActiveCustomersByMonthlyTransactions(
        @Param("startDate") LocalDateTime startDate,
        @Param("threshold") long threshold);
    
    /**
     * Full-text search (PostgreSQL specific)
     */
    @Query(value = """
        SELECT * FROM customers
        WHERE to_tsvector('english', first_name || ' ' || last_name || ' ' || email)
        @@ to_tsquery('english', :searchTerm)
        AND deleted = false
        """, nativeQuery = true)
    List<Customer> fullTextSearch(@Param("searchTerm") String searchTerm);
    
    // ========================================
    // MODIFYING QUERIES (UPDATE/DELETE)
    // ========================================
    
    /**
     * Bulk update status
     */
    @Modifying
    @Query("UPDATE Customer c SET c.status = :newStatus WHERE c.status = :oldStatus")
    int bulkUpdateStatus(
        @Param("oldStatus") CustomerStatus oldStatus,
        @Param("newStatus") CustomerStatus newStatus);
    
    /**
     * Soft delete by IDs
     */
    @Modifying
    @Query("""
        UPDATE Customer c 
        SET c.deleted = true, c.deletedAt = :deletedAt 
        WHERE c.id IN :ids
        """)
    int softDeleteByIds(@Param("ids") List<Long> ids, @Param("deletedAt") LocalDateTime deletedAt);
    
    /**
     * Archive old customers (update example)
     */
    @Modifying
    @Query("""
        UPDATE Customer c 
        SET c.status = 'INACTIVE' 
        WHERE c.createdAt < :cutoffDate 
        AND c.status = 'ACTIVE'
        AND NOT EXISTS (
            SELECT 1 FROM Transaction t 
            WHERE t.customer = c 
            AND t.createdAt > :activityDate
        )
        """)
    int archiveInactiveCustomers(
        @Param("cutoffDate") LocalDateTime cutoffDate,
        @Param("activityDate") LocalDateTime activityDate);
    
    // ========================================
    // PROJECTIONS
    // ========================================
    
    /**
     * Interface-based projection
     */
    List<CustomerSummary> findByDeletedFalse();
    
    /**
     * Nested projection
     */
    List<CustomerWithProfile> findByStatusAndDeletedFalse(CustomerStatus status);
    
    /**
     * Dynamic projection
     */
    <T> List<T> findByStatus(CustomerStatus status, Class<T> type);
    
    // ========================================
    // PAGINATION & SORTING
    // ========================================
    
    /**
     * Paginated results with criteria
     */
    Page<Customer> findByStatusAndDeletedFalse(CustomerStatus status, Pageable pageable);
    
    /**
     * Slice for infinite scroll (no total count)
     */
    org.springframework.data.domain.Slice<Customer> findByDeletedFalse(
        String lastProcessedId, 
        Pageable pageable);
}

// ========================================
// PROJECTION INTERFACES
// ========================================

/**
 * Simple projection - only load needed fields
 */
interface CustomerSummary {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    CustomerStatus getStatus();
    
    // Computed property
    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}

/**
 * Nested projection
 */
interface CustomerWithProfile {
    Long getId();
    String getEmail();
    
    // Nested projection
    ProfileInfo getProfile();
    
    interface ProfileInfo {
        String getPhoneNumber();
        String getOccupation();
    }
}

/**
 * DTO for statistics projection
 */
interface CustomerStatisticsProjection {
    CustomerStatus getStatus();
    Long getCount();
    Long getWithProfile();
}
