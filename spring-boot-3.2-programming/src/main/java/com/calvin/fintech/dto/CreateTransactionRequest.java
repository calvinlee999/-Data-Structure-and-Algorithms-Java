package com.calvin.fintech.dto;

import com.calvin.fintech.entity.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Create Transaction Request DTO
 * 
 * DTO = Data Transfer Object
 * This is the "form" clients fill out when creating a transaction.
 * 
 * Java 21 Record - perfect for DTOs because:
 * - Immutable (data can't be changed after creation)
 * - Automatic getters, equals, hashCode, toString
 * - Compact syntax
 * 
 * Validation annotations ensure data quality before processing.
 */
public record CreateTransactionRequest(
    
    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be a positive number")
    Long customerId,
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    @Digits(integer = 17, fraction = 2, message = "Amount can have max 17 digits and 2 decimal places")
    BigDecimal amount,
    
    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code (e.g., USD, EUR)")
    @Pattern(regexp = "[A-Z]{3}", message = "Currency must be 3 uppercase letters")
    String currency,
    
    @NotNull(message = "Transaction type is required")
    TransactionType type,
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description,
    
    @Pattern(regexp = "^[A-Z0-9-]{0,100}$", message = "Reference number can only contain uppercase letters, numbers, and hyphens")
    String referenceNumber
    
) {
    /**
     * Compact constructor for validation
     * 
     * This runs automatically when a record is created.
     * Use it for additional validation beyond annotations.
     */
    public CreateTransactionRequest {
        // Normalize currency to uppercase
        if (currency != null) {
            currency = currency.toUpperCase();
        }
        
        // Additional business rule validation
        if (amount != null && amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        
        // Withdrawal and Payment must have positive amounts
        if (type == TransactionType.WITHDRAWAL || type == TransactionType.PAYMENT) {
            if (amount != null && amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(
                    type + " transactions must have positive amounts"
                );
            }
        }
    }
}
