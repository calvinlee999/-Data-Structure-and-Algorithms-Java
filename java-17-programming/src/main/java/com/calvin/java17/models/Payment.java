package com.calvin.java17.models;

import java.math.BigDecimal;

/**
 * Sealed Payment Interface - Java 17 DDD Boundaries
 * 
 * Sealed classes/interfaces restrict which classes or interfaces may extend/implement them.
 * This creates "Closed Domain Models" that enforce DDD boundaries at compile time.
 * 
 * Benefits:
 * - Compiler-enforced type hierarchy
 * - Exhaustive pattern matching in switch statements
 * - Prevents unauthorized payment types
 * - Clear domain model documentation
 * 
 * FinTech Use Case: Payment processing with type-safe routing
 */
public sealed interface Payment 
    permits CreditCardPayment, CryptoPayment, BankTransferPayment {
    
    /**
     * All payment types must provide these core attributes
     */
    String paymentId();
    BigDecimal amount();
    PaymentStatus status();
    
    /**
     * Default method: Process payment based on concrete type
     * Pattern matching ensures exhaustiveness (compiler checks all cases)
     */
    default String processPayment() {
        return switch (this) {
            case CreditCardPayment cc -> processCreditCard(cc);
            case CryptoPayment crypto -> processBlockchain(crypto);
            case BankTransferPayment bank -> processBankTransfer(bank);
        };
    }
    
    /**
     * Default method: Validate payment amount
     */
    default boolean isValidAmount() {
        return amount() != null && amount().compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * Default method: Check if high-value transaction
     */
    default boolean isHighValue() {
        return amount().compareTo(new BigDecimal("10000")) > 0;
    }
    
    /**
     * Route payment to appropriate processor
     */
    default String routePayment() {
        return switch (this) {
            case CreditCardPayment cc when cc.amount().compareTo(new BigDecimal("10000")) > 0
                -> "HIGH_VALUE_CREDIT_ROUTE";
            case CreditCardPayment cc 
                -> "STANDARD_CREDIT_ROUTE";
            case CryptoPayment crypto when crypto.blockchain().equals("Ethereum")
                -> "ETH_BLOCKCHAIN_ROUTE";
            case CryptoPayment crypto 
                -> "CRYPTO_ROUTE: " + crypto.blockchain();
            case BankTransferPayment bank when bank.amount().compareTo(new BigDecimal("50000")) > 0
                -> "WIRE_TRANSFER_ROUTE";
            case BankTransferPayment bank 
                -> "ACH_ROUTE";
        };
    }
    
    // Private methods for payment processing
    private String processCreditCard(CreditCardPayment payment) {
        return "Processing Credit Card: " + payment.cardNumber().substring(0, 4) + "****";
    }
    
    private String processBlockchain(CryptoPayment payment) {
        return "Processing Crypto on " + payment.blockchain() + ": " + payment.walletAddress();
    }
    
    private String processBankTransfer(BankTransferPayment payment) {
        return "Processing Bank Transfer from " + payment.bankName() + ": " + payment.accountNumber();
    }
}

/**
 * Credit Card Payment - Permitted subtype
 * Must be final, sealed, or non-sealed
 */
final record CreditCardPayment(
    String paymentId,
    BigDecimal amount,
    PaymentStatus status,
    String cardNumber,
    String cvv,
    String expiryDate,
    String cardholderName
) implements Payment {
    
    public CreditCardPayment {
        if (cardNumber == null || cardNumber.length() != 16) {
            throw new IllegalArgumentException("Invalid card number");
        }
        if (cvv == null || cvv.length() != 3) {
            throw new IllegalArgumentException("Invalid CVV");
        }
    }
    
    public boolean isCvvValid() {
        return cvv != null && cvv.length() == 3;
    }
}

/**
 * Crypto Payment - Permitted subtype
 */
final record CryptoPayment(
    String paymentId,
    BigDecimal amount,
    PaymentStatus status,
    String walletAddress,
    String blockchain,
    String transactionHash
) implements Payment {
    
    public CryptoPayment {
        if (walletAddress == null || walletAddress.isBlank()) {
            throw new IllegalArgumentException("Wallet address cannot be empty");
        }
        if (blockchain == null || blockchain.isBlank()) {
            throw new IllegalArgumentException("Blockchain cannot be empty");
        }
    }
    
    public boolean isEthereum() {
        return "Ethereum".equalsIgnoreCase(blockchain);
    }
    
    public boolean isBitcoin() {
        return "Bitcoin".equalsIgnoreCase(blockchain);
    }
}

/**
 * Bank Transfer Payment - Permitted subtype
 */
final record BankTransferPayment(
    String paymentId,
    BigDecimal amount,
    PaymentStatus status,
    String accountNumber,
    String routingNumber,
    String bankName,
    TransferType transferType
) implements Payment {
    
    public enum TransferType {
        ACH,           // Automated Clearing House (standard)
        WIRE,          // Wire transfer (same-day)
        INTERNATIONAL  // SWIFT/cross-border
    }
    
    public BankTransferPayment {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        if (routingNumber == null || routingNumber.length() != 9) {
            throw new IllegalArgumentException("Invalid routing number");
        }
    }
    
    public boolean isInternational() {
        return transferType == TransferType.INTERNATIONAL;
    }
    
    public boolean isWireTransfer() {
        return transferType == TransferType.WIRE;
    }
}
