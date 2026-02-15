package com.calvin.interfaces;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.util.function.*;

/**
 * Functional Interfaces in Spring Boot 3.2 + Java 21
 * 
 * <p>FinTech Principal Engineer's Guide to Stateless Service Architecture</p>
 * 
 * <h2>What is a Functional Interface?</h2>
 * <p>
 * A functional interface is an interface with <b>exactly one abstract method</b>.
 * It can have multiple default or static methods, but only ONE abstract method.
 * This single abstract method defines the contract for lambda expressions.
 * </p>
 * 
 * <h2>Custom Functional Interfaces for FinTech</h2>
 * <p>
 * While Java provides built-in functional interfaces (Function, Predicate, Consumer, etc.),
 * you can create <b>custom functional interfaces</b> to represent domain-specific operations
 * with meaningful names that align with business requirements.
 * </p>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 * @version 1.0.0
 * @since 2026-02-15
 */
@Component
public class FunctionalInterfacesDemo implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║  Functional Interfaces in Spring Boot 3.2 + Java 21      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        demonstrateCustomFunctionalInterfaces();
        demonstrateStrategy Pattern();
    }

    /**
     * Custom Functional Interfaces for FinTech Domain
     */
    private void demonstrateCustomFunctionalInterfaces() {
        System.out.println("1️⃣  Custom Functional Interfaces - Domain-Driven Design");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // CurrencyConverter - Custom functional interface
        CurrencyConverter usdToEur = (amount, rate) -> 
            amount.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal converted = usdToEur.convert(
            new BigDecimal("1000.00"), 
            new BigDecimal("0.92")
        );
        
        System.out.println("💱 Currency Conversion:");
        System.out.println("  ├─ USD: $1000.00");
        System.out.println("  ├─ Rate: 0.92");
        System.out.println("  └─ EUR: €" + converted);

        // RiskCalculator - Custom functional interface
        RiskCalculator creditRiskCalc = (creditScore, income, loanAmount) -> {
            BigDecimal debtToIncome = loanAmount.divide(income, 4, BigDecimal.ROUND_HALF_UP);
            if (creditScore >= 750 && debtToIncome.compareTo(new BigDecimal("0.3")) < 0) 
                return RiskLevel.LOW;
            if (creditScore >= 650 && debtToIncome.compareTo(new BigDecimal("0.4")) < 0) 
                return RiskLevel.MEDIUM;
            return RiskLevel.HIGH;
        };

        RiskLevel risk = creditRiskCalc.calculateRisk(
            720, 
            new BigDecimal("80000"), 
            new BigDecimal("20000")
        );

        System.out.println("\n📊 Risk Assessment:");
        System.out.println("  ├─ Credit Score: 720");
        System.out.println("  ├─ Income: $80,000");
        System.out.println("  ├─ Loan: $20,000");
        System.out.println("  └─ Risk: " + risk);
        System.out.println("✅ Custom interfaces provide domain-specific naming\n");
    }

    /**
     * Strategy Pattern with Functional Interfaces
     * 
     * <p>Replace complex inheritance hierarchies with simple lambda expressions</p>
     */
    private void demonstrateStrategyPattern() {
        System.out.println("2️⃣  Strategy Pattern - Payment Processing");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Different payment strategies as lambda expressions
        PaymentProcessor creditCardProcessor = amount -> {
            System.out.println("  ├─ Processing Credit Card: $" + amount);
            return "CC-" + System.currentTimeMillis();
        };

        PaymentProcessor paypalProcessor = amount -> {
            System.out.println("  ├─ Processing PayPal: $" + amount);
            return "PP-" + System.currentTimeMillis();
        };

        PaymentProcessor cryptoProcessor = amount -> {
            System.out.println("  ├─ Processing Cryptocurrency: $" + amount);
            return "BTC-" + System.currentTimeMillis();
        };

        BigDecimal amount = new BigDecimal("250.00");

        System.out.println("💳 Payment Strategy Selection:");
        String txId1 = creditCardProcessor.process(amount);
        String txId2 = paypalProcessor.process(amount);
        String txId3 = cryptoProcessor.process(amount);

        System.out.println("\n✅ Strategy Pattern - Behavior injection via lambdas\n");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // Custom Functional Interfaces
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Currency Converter - Converts amounts between currencies
     * 
     * <p><b>Domain Example:</b> Trading desk currency conversion</p>
     */
    @FunctionalInterface
    public interface CurrencyConverter {
        BigDecimal convert(BigDecimal amount, BigDecimal exchangeRate);
    }

    /**
     * Risk Calculator - Calculates loan risk level
     * 
     * <p><b>Domain Example:</b> Credit risk assessment</p>
     */
    @FunctionalInterface
    public interface RiskCalculator {
        RiskLevel calculateRisk(int creditScore, BigDecimal annualIncome, BigDecimal loanAmount);
    }

    /**
     * Payment Processor - Processes payments through different channels
     * 
     * <p><b>Domain Example:</b> Multi-channel payment gateway</p>
     */
    @FunctionalInterface
    public interface PaymentProcessor {
        String process(BigDecimal amount);
    }

    /**
     * Risk Level Enum
     */
    public enum RiskLevel {
        LOW, MEDIUM, HIGH
    }
}
