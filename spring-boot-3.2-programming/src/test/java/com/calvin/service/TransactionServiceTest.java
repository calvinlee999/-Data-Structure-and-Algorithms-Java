package com.calvin.service;

import com.calvin.domain.*;
import com.calvin.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Transaction Service Tests - JUnit 5 + Spring Boot Test
 * 
 * <p>Demonstrates:</p>
 * <ul>
 *   <li>Integration testing with H2 in-memory database</li>
 *   <li>Transactional tests (auto-rollback)</li>
 *   <li>Stream-based assertions</li>
 *   <li>Record pattern matching</li>
 * </ul>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
@SpringBootTest
@Transactional  // Rollback after each test
class TransactionServiceTest {

    @Autowired
    private TransactionService service;

    @Autowired
    private TransactionRepository repository;

    private final Long TEST_CUSTOMER_ID = 1001L;

    @BeforeEach
    void setUp() {
        // Clean slate for each test
        repository.deleteAll();
    }

    @Test
    @DisplayName("getDailySummary should filter only APPROVED transactions")
    void getDailySummary_shouldFilterApprovedTransactions() {
        // Given: Create test data
        Transaction approved = new Transaction(
            "TX-001", TEST_CUSTOMER_ID, BigDecimal.valueOf(5000),
            TransactionType.PAYMENT, "CREDIT_CARD", "Test approved"
        );
        approved.approve();  // Change status to APPROVED

        Transaction pending = new Transaction(
            "TX-002", TEST_CUSTOMER_ID, BigDecimal.valueOf(3000),
            TransactionType.PAYMENT, "DEBIT_CARD", "Test pending"
        );

        repository.saveAll(List.of(approved, pending));

        // When: Get daily summary
        List<TransactionSummaryDTO> result = service.getDailySummary(TEST_CUSTOMER_ID);

        // Then: Only approved transaction should be returned
        assertEquals(1, result.size());
        assertEquals("APPROVED", result.get(0).status());
        assertEquals("TX-001", result.get(0).transactionId());
    }

    @Test
    @DisplayName("calculateDailySummary should aggregate correctly")
    void calculateDailySummary_shouldAggregateCorrectly() {
        // Given: Create multiple transactions
        LocalDateTime today = LocalDateTime.now();
        
        Transaction t1 = new Transaction("TX-001", 1001L, BigDecimal.valueOf(5000), 
            TransactionType.PAYMENT, "CREDIT_CARD", "Test 1");
        t1.approve();
        
        Transaction t2 = new Transaction("TX-002", 1002L, BigDecimal.valueOf(3000), 
            TransactionType.PAYMENT, "DEBIT_CARD", "Test 2");
        t2.approve();
        
        Transaction t3 = new Transaction("TX-003", 1003L, BigDecimal.valueOf(2000), 
            TransactionType.REFUND, "PAYPAL", "Test 3");
        t3.reject("Test rejection");

        repository.saveAll(List.of(t1, t2, t3));

        // When: Calculate daily summary
        DailyTransactionSummaryDTO summary = service.calculateDailySummary(today);

        // Then: Verify aggregations
        assertEquals(3, summary.totalTransactions());
        assertEquals(2, summary.approvedCount());
        assertEquals(1, summary.rejectedCount());
    }

    @Test
    @DisplayName("getVolumeByTransactionType should group correctly")
    void getVolumeByTransactionType_shouldGroupCorrectly() {
        // Given: Different transaction types
        repository.saveAll(List.of(
            new Transaction("TX-P1", 1001L, BigDecimal.valueOf(1000), 
                TransactionType.PAYMENT, "CARD", "Payment 1"),
            new Transaction("TX-P2", 1002L, BigDecimal.valueOf(2000), 
                TransactionType.PAYMENT, "CARD", "Payment 2"),
            new Transaction("TX-R1", 1003L, BigDecimal.valueOf(500), 
                TransactionType.REFUND, "CARD", "Refund 1")
        ));

        // When: Get volume by type
        Map<String, BigDecimal> volumes = service.getVolumeByTransactionType();

        // Then: Verify grouping
        assertEquals(BigDecimal.valueOf(3000).setScale(2), 
            volumes.get("PAYMENT").setScale(2));
        assertEquals(BigDecimal.valueOf(500).setScale(2), 
            volumes.get("REFUND").setScale(2));
    }

    @Test
    @DisplayName("processBatchApprovals should handle mixed success/failure")
    void processBatchApprovals_shouldHandleMixedResults() {
        // Given: Create pending and non-pending transactions
        Transaction pending = new Transaction("TX-PENDING", 1001L, 
            BigDecimal.valueOf(1000), TransactionType.PAYMENT, "CARD", "Pending");
        
        Transaction approved = new Transaction("TX-APPROVED", 1002L, 
            BigDecimal.valueOf(2000), TransactionType.PAYMENT, "CARD", "Already approved");
        approved.approve();

        repository.saveAll(List.of(pending, approved));

        // When: Batch approve including non-existent transaction
        BatchTransactionResultDTO result = service.processBatchApprovals(
            List.of("TX-PENDING", "TX-APPROVED", "TX-NOTFOUND")
        );

        // Then: Verify results
        assertEquals(3, result.totalProcessed());
        assertTrue(result.successCount() >= 1);  // At least TX-PENDING
        assertTrue(result.failureCount() >= 1);  // At least TX-APPROVED (wrong state)
    }

    @Test
    @DisplayName("createTransaction should validate Record constraints")
    void createTransaction_shouldValidateRecordConstraints() {
        // Given: Invalid request (negative customer ID)
        CreateTransactionRequest invalidRequest = new CreateTransactionRequest(
            -1L,  // Invalid
            BigDecimal.valueOf(1000),
            "PAYMENT",
            "CARD",
            "Test"
        );

        // When/Then: Should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            service.createTransaction(invalidRequest);
        });
    }

    @Test
    @DisplayName("getFirstAndLastTransactions should use SequencedCollection")
    void getFirstAndLastTransactions_shouldUseSequencedCollection() {
        // Given: Multiple transactions
        repository.saveAll(List.of(
            new Transaction("TX-001", TEST_CUSTOMER_ID, BigDecimal.valueOf(1000), 
                TransactionType.PAYMENT, "CARD", "First"),
            new Transaction("TX-002", TEST_CUSTOMER_ID, BigDecimal.valueOf(2000), 
                TransactionType.PAYMENT, "CARD", "Middle"),
            new Transaction("TX-003", TEST_CUSTOMER_ID, BigDecimal.valueOf(3000), 
                TransactionType.PAYMENT, "CARD", "Last")
        ));

        // When: Get bookends
        Map<String, TransactionSummaryDTO> bookends = 
            service.getFirstAndLastTransactions(TEST_CUSTOMER_ID);

        // Then: Verify first and last
        assertNotNull(bookends.get("first"));
        assertNotNull(bookends.get("last"));
        assertNotEquals(bookends.get("first").id(), bookends.get("last").id());
    }

    @Test
    @DisplayName("categorizeTransaction should use pattern matching")
    void categorizeTransaction_shouldUsePatternMatching() {
        // Given: High-value payment
        Transaction highValue = new Transaction("TX-HIGH", 1001L, 
            BigDecimal.valueOf(15000), TransactionType.PAYMENT, "CARD", "High value");

        // When: Categorize
        String category = service.categorizeTransaction(highValue);

        // Then: Should identify as high-value
        assertTrue(category.contains("High-Value"));
    }
}
