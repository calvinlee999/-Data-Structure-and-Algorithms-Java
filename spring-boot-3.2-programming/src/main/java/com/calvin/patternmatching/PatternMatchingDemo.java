package com.calvin.patternmatching;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Pattern Matching & Records in Spring Boot 3.2 + Java 21
 * 
 * <p>FinTech Principal Engineer's Guide to Declarative Domain Logic</p>
 * 
 * <h2>Pattern Matching Evolution</h2>
 * <ul>
 *   <li><b>Java 14-15:</b> Pattern Matching for instanceof (Preview)</li>
 *   <li><b>Java 16:</b> Pattern Matching for instanceof (Final)</li>
 *   <li><b>Java 17:</b> Sealed Classes (Final)</li>
 *   <li><b>Java 21:</b> Pattern Matching for switch (Final), Record Patterns (Final)</li>
 * </ul>
 * 
 * <h2>The Paradigm Shift: Declarative Domain Mapping</h2>
 * <p>
 * Pattern matching allows you to handle different domain events as "Data-as-a-Product"
 * in a declarative way, eliminating complex if-else chains and instanceof checks.
 * </p>
 * 
 * <h2>Real-World FinTech Use Cases</h2>
 * <ul>
 *   <li><b>Payment Processing:</b> Handle CreditCard, PayPal, Crypto differently</li>
 *   <li><b>Transaction Routing:</b> Route based on transaction type</li>
 *   <li><b>Risk Assessment:</b> Different risk rules for different customer types</li>
 *   <li><b>Event Handling:</b> Process domain events with type safety</li>
 * </ul>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 * @version 1.0.0
 * @since 2026-02-15
 */
@Component
public class PatternMatchingDemo implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║  Pattern Matching & Records in Spring Boot 3.2           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        demonstratePatternMatchingForInstanceof();
        demonstratePatternMatchingForSwitch();
        demonstrateRecordPatterns();
        demonstrateSealedClasses();
    }

    /**
     * Pattern Matching for instanceof (Java 16+)
     * 
     * <p><b>Old Way:</b> instanceof + cast</p>
     * <p><b>New Way:</b> Pattern variable automatically cast</p>
     */
    private void demonstratePatternMatchingForInstanceof() {
        System.out.println("1️⃣  Pattern Matching for instanceof");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        Object payment = new CreditCardPayment("4242-4242-4242-4242", new BigDecimal("100.00"));

        // Old way (Java 15 and earlier)
        // if (payment instanceof CreditCardPayment) {
        //     CreditCardPayment cc = (CreditCardPayment) payment;
        //     System.out.println(cc.cardNumber());
        // }

        // New way (Java 16+) - Pattern variable
        if (payment instanceof CreditCardPayment cc) {
            System.out.println("💳 Credit Card Payment:");
            System.out.println("  ├─ Card: " + maskCardNumber(cc.cardNumber()));
            System.out.println("  └─ Amount: $" + cc.amount());
        }

        System.out.println("✅ Pattern variable 'cc' automatically cast\n");
    }

    /**
     * Pattern Matching for switch (Java 21)
     * 
     * <p><b>Revolutionary Feature:</b> Handle different types in switch expressions</p>
     */
    private void demonstratePatternMatchingForSwitch() {
        System.out.println("2️⃣  Pattern Matching for switch - Payment Routing");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        Payment[] payments = {
            new CreditCardPayment("4242-4242-4242-4242", new BigDecimal("100.00")),
            new PayPalPayment("user@example.com", new BigDecimal("50.00")),
            new CryptoPayment("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", new BigDecimal("200.00"))
        };

        for (Payment payment : payments) {
            String result = processPayment(payment);
            System.out.println("  ├─ " + result);
        }

        System.out.println("✅ Declarative multi-type handling in switch\n");
    }

    /**
     * Process payment using pattern matching switch
     */
    private String processPayment(Payment payment) {
        return switch (payment) {
            case CreditCardPayment cc -> 
                "Credit Card " + maskCardNumber(cc.cardNumber()) + ": $" + cc.amount();
            case PayPalPayment pp -> 
                "PayPal " + pp.email() + ": $" + pp.amount();
            case CryptoPayment crypto -> 
                "Cryptocurrency " + crypto.walletAddress().substring(0, 10) + "...: $" + crypto.amount();
            case null -> 
                "Invalid payment";
        };
    }

    /**
     * Record Patterns (Java 21) - Destructuring
     * 
     * <p><b>Revolutionary Feature:</b> Extract record components in pattern matching</p>
     */
    private void demonstrateRecordPatterns() {
        System.out.println("3️⃣  Record Patterns - Destructuring");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        Transaction tx = new Transaction(
            "TX-12345",
            new BigDecimal("1500.00"),
            new CreditCardPayment("4242-4242-4242-4242", new BigDecimal("1500.00")),
            LocalDateTime.now()
        );

        // Record pattern - destructure the transaction
        if (tx instanceof Transaction(var id, var amount, CreditCardPayment(var card, var ccAmount), var timestamp)) {
            System.out.println("🔍 Destructured Transaction:");
            System.out.println("  ├─ ID: " + id);
            System.out.println("  ├─ Amount: $" + amount);
            System.out.println("  ├─ Card: " + maskCardNumber(card));
            System.out.println("  └─ Time: " + timestamp);
        }

        System.out.println("✅ Record patterns enable nested destructuring\n");
    }

    /**
     * Sealed Classes + Pattern Matching (Java 17+)
     * 
     * <p><b>Strategic Benefit:</b> Exhaustive pattern matching with compile-time safety</p>
     */
    private void demonstrateSealedClasses() {
        System.out.println("4️⃣  Sealed Classes - Exhaustive Pattern Matching");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        Payment payment = new CreditCardPayment("4242-4242-4242-4242", new BigDecimal("100.00"));

        // Exhaustive switch - compiler ensures all cases covered
        BigDecimal fee = switch (payment) {
            case CreditCardPayment cc -> cc.amount().multiply(new BigDecimal("0.029")); // 2.9%
            case PayPalPayment pp -> pp.amount().multiply(new BigDecimal("0.034")); // 3.4%
            case CryptoPayment crypto -> crypto.amount().multiply(new BigDecimal("0.01")); // 1%
        };

        System.out.println("💰 Fee Calculation:");
        System.out.println("  ├─ Payment: " + payment.getClass().getSimpleName());
        System.out.println("  └─ Fee: $" + fee.setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println("✅ Sealed classes provide exhaustiveness guarantee\n");
    }

    private String maskCardNumber(String cardNumber) {
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // Domain Models - Sealed Classes & Records
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Sealed Payment hierarchy - Only these 3 types permitted
     * 
     * <p><b>Strategic Value:</b> Exhaustive pattern matching, no default case needed</p>
     */
    public sealed interface Payment permits CreditCardPayment, PayPalPayment, CryptoPayment {}

    public record CreditCardPayment(String cardNumber, BigDecimal amount) implements Payment {}
    public record PayPalPayment(String email, BigDecimal amount) implements Payment {}
    public record CryptoPayment(String walletAddress, BigDecimal amount) implements Payment {}

    /**
     * Transaction Record with nested Payment
     */
    public record Transaction(
        String id,
        BigDecimal amount,
        Payment payment,
        LocalDateTime timestamp
    ) {}
}

/**
 * Pattern Matching REST Controller
 * 
 * <p>Real-world Spring Boot integration with pattern matching</p>
 */
@RestController
@RequestMapping("/api/pattern-matching")
class PatternMatchingController {

    /**
     * Process payment endpoint with pattern matching
     */
    @PostMapping("/process-payment")
    public PaymentResponse processPayment(@RequestBody PaymentRequest request) {
        String method = switch (request) {
            case PaymentRequest(_, "CREDIT_CARD", var details) -> 
                "Credit Card: " + details;
            case PaymentRequest(_, "PAYPAL", var details) -> 
                "PayPal: " + details;
            case PaymentRequest(_, "CRYPTO", var details) -> 
                "Cryptocurrency: " + details;
            default -> 
                "Unknown payment method";
        };

        BigDecimal fee = calculateFee(request);

        return new PaymentResponse(
            "TX-" + System.currentTimeMillis(),
            request.amount(),
            fee,
            request.amount().add(fee),
            method
        );
    }

    private BigDecimal calculateFee(PaymentRequest request) {
        return switch (request.paymentType()) {
            case "CREDIT_CARD" -> request.amount().multiply(new BigDecimal("0.029"));
            case "PAYPAL" -> request.amount().multiply(new BigDecimal("0.034"));
            case "CRYPTO" -> request.amount().multiply(new BigDecimal("0.01"));
            default -> BigDecimal.ZERO;
        };
    }

    public record PaymentRequest(BigDecimal amount, String paymentType, String details) {}
    
    public record PaymentResponse(
        String transactionId,
        BigDecimal amount,
        BigDecimal fee,
        BigDecimal total,
        String method
    ) {}
}
