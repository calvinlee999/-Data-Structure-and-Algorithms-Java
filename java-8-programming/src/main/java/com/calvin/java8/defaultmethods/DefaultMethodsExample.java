package com.calvin.java8.defaultmethods;

import java.math.BigDecimal;

/**
 * Default & Static Methods in Interfaces - API Evolution Without Breaking Changes
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
public class DefaultMethodsExample {


    public static void main(String[] args) {
        System.out.println("="repeat(80));
        System.out.println("JAVA 8 DEFAULT & STATIC METHODS - INTERFACE EVOLUTION");
        System.out.println("=".repeat(80));
        System.out.println();

        PaymentProcessor processor = new StripeProcessor();
        
        // Call original method
        System.out.println("1. ORIGINAL METHOD");
        String result = processor.process("TXN-123", new BigDecimal("100"));
        System.out.println("   Result: " + result);
        System.out.println();

        // Call default method (inherited automatically!)
        System.out.println("2. DEFAULT METHOD (Auto-inherited)");
        String retryResult = processor.processWithRetry("TXN-456", new BigDecimal("200"), 3);
        System.out.println("   Retry result: " + retryResult);
        System.out.println();

        // Call static method
        System.out.println("3. STATIC METHOD (Interface utility)");
        boolean valid = PaymentProcessor.isValidAmount(new BigDecimal("500"));
        System.out.println("   Is $500 valid? " + valid);
        System.out.println();
    }
}

/**
 * Payment Processor Interface with default and static methods
 */
interface PaymentProcessor {
    // Original abstract method
    String process(String txnId, BigDecimal amount);

    // Default method: Existing implementations get this for FREE!
    default String processWithRetry(String txnId, BigDecimal amount, int maxRetries) {
        for (int i = 0; i < maxRetries; i++) {
            try {
                return process(txnId, amount);
            } catch (Exception e) {
                if (i == maxRetries - 1) throw e;
            }
        }
        return "FAILED";
    }

    // Static method: Utility accessible via interface
    static boolean isValidAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }
}

/**
 * Implementation: ZERO code changes required to get default methods!
 */
class StripeProcessor implements PaymentProcessor {
    @Override
    public String process(String txnId, BigDecimal amount) {
        return "PROCESSED: " + txnId + " - $" + amount;
    }
    // processWithRetry() inherited automatically!
}
