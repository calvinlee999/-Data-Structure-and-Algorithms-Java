package com.calvin.fintech.domain;

import java.time.YearMonth;
import java.util.regex.Pattern;

/**
 * Credit Card Payment Request (Record)
 * 
 * Java 21 Feature: Records
 * - Immutable by default
 * - Transparent data carriers
 * - Automatic equals/hashCode/toString
 * - Compact constructor for validation
 * 
 * Replaces: Heavy POJOs with getters/setters/boilerplate
 */
public record CreditCardRequest(
    String gateway,
    double amount,
    String currency,
    String cardNumber,
    String cvv,
    YearMonth expiryDate,
    String cardholderName
) implements PaymentRequest {
    
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^\\d{16}$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^\\d{3,4}$");
    
    /**
     * Compact Constructor (Java 21)
     * - Validation logic before object creation
     * - Ensures immutability invariants
     * - Fails fast on invalid data
     */
    public CreditCardRequest {
        // Validate amount
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Amount must be positive, got: " + amount
            );
        }
        
        // Validate currency
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }
        
        // Validate card number (basic)
        if (cardNumber == null || !CARD_NUMBER_PATTERN.matcher(cardNumber).matches()) {
            throw new IllegalArgumentException("Invalid card number format");
        }
        
        // Validate CVV
        if (cvv == null || !CVV_PATTERN.matcher(cvv).matches()) {
            throw new IllegalArgumentException("Invalid CVV format");
        }
        
        // Validate expiry date
        if (expiryDate == null || expiryDate.isBefore(YearMonth.now())) {
            throw new IllegalArgumentException("Card is expired");
        }
        
        // Validate cardholder name
        if (cardholderName == null || cardholderName.isBlank()) {
            throw new IllegalArgumentException("Cardholder name is required");
        }
    }
    
    /**
     * Business method: Get masked card number
     * Records can have methods too!
     */
    public String getMaskedCardNumber() {
        return "**** **** **** " + cardNumber.substring(12);
    }
    
    /**
     * Business method: Check if card is about to expire
     */
    public boolean isExpiringSoon() {
        return expiryDate.isBefore(YearMonth.now().plusMonths(3));
    }
    
    /**
     * Override default validation to include credit card specific checks
     */
    @Override
    public boolean isValid() {
        return PaymentRequest.super.isValid() 
            && !isExpiringSoon()
            && CARD_NUMBER_PATTERN.matcher(cardNumber).matches();
    }
}
