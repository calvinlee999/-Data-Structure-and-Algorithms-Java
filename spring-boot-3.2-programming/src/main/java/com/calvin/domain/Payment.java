package com.calvin.domain;

/**
 * Payment Domain Model using Sealed Classes (Java 17+)
 * 
 * <p>Demonstrates Compiler-Enforced DDD Boundaries</p>
 * 
 * <h2>Sealed Classes Benefits</h2>
 * <ul>
 *   <li><b>Controlled Hierarchy:</b> Only permitted subtypes can exist</li>
 *   <li><b>Pattern Matching:</b> Exhaustive switch expressions (no default needed)</li>
 *   <li><b>Domain Modeling:</b> Perfect for Payment States, Order Status, etc.</li>
 *   <li><b>Compile-Time Safety:</b> Adding new payment type = compile error until handled</li>
 * </ul>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Sealed Payment Interface - Only 3 payment types allowed
 * 
 * <p>Old Way (Open Hierarchy - Runtime Errors):</p>
 * <pre>
 * public interface Payment { }
 * // Anyone can implement Payment anywhere!
 * // Missing case in switch = NullPointerException at runtime
 * </pre>
 * 
 * <p>New Way (Sealed Hierarchy - Compile-Time Errors):</p>
 */
public sealed interface Payment 
    permits CreditCardPayment, PayPalPayment, CryptoPayment {
    
    BigDecimal amount();
    String customerId();
    LocalDateTime timestamp();
    
    /**
     * Process payment using pattern matching (Java 21)
     * 
     * <p>No default case needed - compiler knows all subtypes!</p>
     */
    default String process() {
        return switch (this) {
            case CreditCardPayment cc -> processCreditCard(cc);
            case PayPalPayment pp -> processPayPal(pp);
            case CryptoPayment crypto -> processCrypto(crypto);
            // No 'default' needed - compiler enforces exhaustiveness!
        };
    }
    
    private String processCreditCard(CreditCardPayment payment) {
        // Record pattern matching (Java 21)
        return String.format("Processing Credit Card: %s (***%s) - $%.2f", 
            payment.cardNetwork(), 
            payment.lastFourDigits(), 
            payment.amount());
    }
    
    private String processPayPal(PayPalPayment payment) {
        return String.format("Processing PayPal: %s - $%.2f", 
            payment.email(), 
            payment.amount());
    }
    
    private String processCrypto(CryptoPayment payment) {
        return String.format("Processing Crypto: %s (%s) - $%.2f", 
            payment.cryptocurrency(), 
            payment.walletAddress().substring(0, 8) + "...", 
            payment.amount());
    }
}

/**
 * Credit Card Payment Record
 * 
 * <p>Combines Record (immutability) + Sealed Hierarchy (type safety)</p>
 */
public record CreditCardPayment(
    BigDecimal amount,
    String customerId,
    LocalDateTime timestamp,
    String cardNetwork,      // Visa, Mastercard, Amex
    String lastFourDigits,
    String expiryDate
) implements Payment {
    public CreditCardPayment {
        if (cardNetwork == null || cardNetwork.isBlank()) {
            throw new IllegalArgumentException("Card network required");
        }
        if (!lastFourDigits.matches("\\d{4}")) {
            throw new IllegalArgumentException("Last 4 digits must be numeric");
        }
    }
}

/**
 * PayPal Payment Record
 */
public record PayPalPayment(
    BigDecimal amount,
    String customerId,
    LocalDateTime timestamp,
    String email,
    String paypalTransactionId
) implements Payment {
    public PayPalPayment {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}

/**
 * Cryptocurrency Payment Record
 */
public record CryptoPayment(
    BigDecimal amount,
    String customerId,
    LocalDateTime timestamp,
    String cryptocurrency,   // BTC, ETH, USDC
    String walletAddress,
    String blockchainTxHash
) implements Payment {
    public CryptoPayment {
        if (walletAddress == null || walletAddress.length() < 26) {
            throw new IllegalArgumentException("Invalid wallet address");
        }
    }
}

/**
 * Payment Result using Sealed Classes for State Machine
 * 
 * <p>Demonstrates how Sealed Classes replace if-else chains with pattern matching</p>
 */
public sealed interface PaymentResult 
    permits PaymentSuccess, PaymentFailed, PaymentPending {
    
    String transactionId();
    
    /**
     * Handle result using pattern matching
     */
    default String toMessage() {
        return switch (this) {
            case PaymentSuccess(var txId, var confirmationCode) -> 
                "Success! Transaction: " + txId + ", Confirmation: " + confirmationCode;
            case PaymentFailed(var txId, var reason) -> 
                "Failed! Transaction: " + txId + ", Reason: " + reason;
            case PaymentPending(var txId, var estimatedTime) -> 
                "Pending! Transaction: " + txId + ", ETA: " + estimatedTime + " minutes";
        };
    }
}

public record PaymentSuccess(
    String transactionId,
    String confirmationCode
) implements PaymentResult {}

public record PaymentFailed(
    String transactionId,
    String reason
) implements PaymentResult {}

public record PaymentPending(
    String transactionId,
    int estimatedTime
) implements PaymentResult {}
