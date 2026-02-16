package com.calvin.fintech.gateway;

/**
 * Payment Gateway Interface
 * 
 * Abstraction for external payment processing systems
 * In a real system, this would integrate with:
 * - Stripe, Square, PayPal (credit cards)
 * - Coinbase, Binance (crypto)
 * - SWIFT,

 SEPA networks (bank transfers)
 */
public interface PaymentGateway {
    
    /**
     * Authorize credit card payment
     */
    boolean authorizeCreditCard(
        String cardNumber,
        String cvv,
        double amount,
        String currency
    );
    
    /**
     * Process cryptocurrency payment
     */
    boolean processCrypto(
        String walletId,
        double amount,
        String currency,
        String blockchain
    );
    
    /**
     * Initiate bank transfer
     */
    boolean initiateBankTransfer(
        String iban,
        String swiftCode,
        double amount,
        String currency,
        String beneficiaryName
    );
}
