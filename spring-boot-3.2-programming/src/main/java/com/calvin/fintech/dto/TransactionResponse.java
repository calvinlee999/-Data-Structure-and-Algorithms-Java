package com.calvin.fintech.dto;

import com.calvin.fintech.entity.Transaction;
import com.calvin.fintech.entity.TransactionStatus;
import com.calvin.fintech.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction Response DTO
 * 
 * This is what we send back to API clients.
 * Never expose internal entity structure directly!
 * 
 * Benefits of using DTOs:
 * - Control what data is exposed
 * - Hide internal database structure
 * - Add computed fields
 * - Different views for different roles
 */
public record TransactionResponse(
    Long id,
    Long customerId,
    BigDecimal amount,
    String currency,
    TransactionType type,
    TransactionStatus status,
    String description,
    String referenceNumber,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    
    // Computed field - not stored in database
    String formattedAmount
) {
    /**
     * Factory method - convert Entity to Response DTO
     * 
     * This is the standard pattern for entity → DTO conversion.
     * Think of it as "translating" internal format to external format.
     */
    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
            transaction.getId(),
            transaction.getCustomerId(),
            transaction.getAmount(),
            transaction.getCurrency(),
            transaction.getType(),
            transaction.getStatus(),
            transaction.getDescription(),
            transaction.getReferenceNumber(),
            transaction.getCreatedAt(),
            transaction.getUpdatedAt(),
            formatAmount(transaction.getAmount(), transaction.getCurrency())
        );
    }
    
    /**
     * Format amount with currency symbol
     * 
     * Example: 100.00 USD → "$100.00"
     */
    private static String formatAmount(BigDecimal amount, String currency) {
        if (amount == null || currency == null) {
            return null;
        }
        
        String symbol = switch (currency) {
            case "USD" -> "$";
            case "EUR" -> "€";
            case "GBP" -> "£";
            case "JPY" -> "¥";
            default -> currency + " ";
        };
        
        return symbol + String.format("%,.2f", amount);
    }
}
