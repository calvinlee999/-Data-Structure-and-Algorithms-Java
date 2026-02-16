package com.calvin.domain;

/**
 * Transaction DTOs using Java Records (Java 16+)
 * 
 * <p>Demonstrates 90% boilerplate reduction with Records</p>
 * 
 * <h2>Benefits of Records for DTOs</h2>
 * <ul>
 *   <li><b>Immutability:</b> Thread-safe by default</li>
 *   <li><b>Compact:</b> Single-line declarations</li>
 *   <li><b>Automatic:</b> equals(), hashCode(), toString() generated</li>
 *   <li><b>Pattern Matching:</b> Destructuring support in Java 21</li>
 * </ul>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Transaction Summary DTO - Immutable projection from database
 * 
 * <p>Old Way (100+ lines with Lombok):</p>
 * <pre>
 * &#64;Data
 * public class TransactionSummaryDTO {
 *     private Long id;
 *     private String transactionId;
 *     private BigDecimal amount;
 *     private String status;
 *     // ... getters, setters, equals, hashCode, toString
 * }
 * </pre>
 * 
 * <p>New Way (1 line with Record):</p>
 */
public record TransactionSummaryDTO(
    Long id,
    String transactionId,
    BigDecimal amount,
    String status,
    LocalDateTime createdAt
) {
    /**
     * Custom validation in compact constructor
     */
    public TransactionSummaryDTO {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    /**
     * Derived method - still allowed in Records
     */
    public boolean isHighValue() {
        return amount.compareTo(BigDecimal.valueOf(10000)) > 0;
    }
}

/**
 * Customer Transaction Report DTO
 * 
 * <p>Used for Spring Data JPA projections with aggregated data</p>
 */
public record CustomerTransactionReportDTO(
    Long customerId,
    String customerName,
    int totalTransactions,
    BigDecimal totalAmount,
    BigDecimal avgTransactionAmount
) {}

/**
 * Transaction Create Request DTO
 * 
 * <p>Immutable request object for REST endpoints</p>
 */
public record CreateTransactionRequest(
    Long customerId,
    BigDecimal amount,
    String type,
    String paymentMethod,
    String description
) {
    public CreateTransactionRequest {
        // Validation in compact constructor
        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}

/**
 * Transaction Response DTO
 * 
 * <p>Immutable response object with enriched data</p>
 */
public record TransactionResponseDTO(
    Long id,
    String transactionId,
    Long customerId,
    BigDecimal amount,
    String type,
    String status,
    String paymentMethod,
    LocalDateTime createdAt,
    boolean highValue
) {}

/**
 * Daily Transaction Summary DTO
 * 
 * <p>Aggregated statistics for dashboard</p>
 */
public record DailyTransactionSummaryDTO(
    LocalDateTime date,
    int totalTransactions,
    BigDecimal totalVolume,
    BigDecimal avgTransactionSize,
    int approvedCount,
    int rejectedCount,
    int pendingCount
) {}

/**
 * Payment Method Statistics DTO
 * 
 * <p>Grouped statistics by payment method</p>
 */
public record PaymentMethodStatsDTO(
    String paymentMethod,
    int count,
    BigDecimal totalAmount,
    BigDecimal avgAmount
) {}

/**
 * Batch Transaction Result DTO
 * 
 * <p>Result of batch processing operations</p>
 */
public record BatchTransactionResultDTO(
    int totalProcessed,
    int successCount,
    int failureCount,
    List<String> processedIds,
    List<String> errorMessages
) {}
