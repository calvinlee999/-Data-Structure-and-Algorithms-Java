package com.calvin.fintech.domain;

import java.util.regex.Pattern;

/**
 * Bank Transfer Payment Request (Record)
 * 
 * Demonstrates:
 * - Third implementation of sealed interface
 * - IBAN/SWIFT validation
 * - Domestic vs International transfers
 */
public record BankTransferRequest(
    String bankName,
    double amount,
    String currency,
    String iban,
    String swiftCode,
    String beneficiaryName,
    String reference,
    TransferType transferType
) implements PaymentRequest {
    
    public enum TransferType {
        DOMESTIC,      // Same country
        SEPA,          // European
        INTERNATIONAL  // Cross-border
    }
    
    private static final Pattern IBAN_PATTERN = 
        Pattern.compile("^[A-Z]{2}\\d{2}[A-Z0-9]{1,30}$");
    
    private static final Pattern SWIFT_PATTERN = 
        Pattern.compile("^[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?$");
    
    /**
     * Compact Constructor with bank transfer validation
     */
    public BankTransferRequest {
        // Validate amount
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Amount must be positive, got: " + amount
            );
        }
        
        // Validate currency
        if (currency == null || currency.length() != 3) {
            throw new IllegalArgumentException("Invalid currency code");
        }
        
        // Validate IBAN
        if (iban == null || !IBAN_PATTERN.matcher(iban).matches()) {
            throw new IllegalArgumentException("Invalid IBAN format");
        }
        
        // Validate SWIFT code
        if (swiftCode == null || !SWIFT_PATTERN.matcher(swiftCode).matches()) {
            throw new IllegalArgumentException("Invalid SWIFT code format");
        }
        
        // Validate beneficiary name
        if (beneficiaryName == null || beneficiaryName.isBlank()) {
            throw new IllegalArgumentException("Beneficiary name is required");
        }
        
        // Validate transfer type
        if (transferType == null) {
            throw new IllegalArgumentException("Transfer type is required");
        }
    }
    
    /**
     * Business method: Check if requires additional documentation
     */
    public boolean requiresDocumentation() {
        return transferType == TransferType.INTERNATIONAL && amount > 10000.0;
    }
    
    /**
     * Business method: Get estimated processing days
     */
    public int getEstimatedProcessingDays() {
        return switch (transferType) {
            case DOMESTIC -> 1;
            case SEPA -> 2;
            case INTERNATIONAL -> amount > 50000 ? 5 : 3;
        };
    }
    
    /**
     * Business method: Calculate transfer fee
     */
    public double getTransferFee() {
        return switch (transferType) {
            case DOMESTIC -> 5.0;
            case SEPA -> 10.0;
            case INTERNATIONAL -> {
                if (amount < 1000) yield 25.0;
                if (amount < 10000) yield 35.0;
                yield 50.0;
            }
        };
    }
    
    /**
     * Business method: Check if express processing available
     */
    public boolean isExpressAvailable() {
        return transferType == TransferType.DOMESTIC 
            || (transferType == TransferType.SEPA && amount < 10000);
    }
}
