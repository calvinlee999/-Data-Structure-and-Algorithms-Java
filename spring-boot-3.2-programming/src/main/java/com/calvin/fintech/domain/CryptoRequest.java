package com.calvin.fintech.domain;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Cryptocurrency Payment Request (Record)
 * 
 * Demonstrates:
 * - Record with custom validation
 * - Business logic in records
 * - Immutability benefits for concurrent processing
 */
public record CryptoRequest(
    String network,
    double amount,
    String currency,
    String walletId,
    String blockchain,
    int confirmationsRequired
) implements PaymentRequest {
    
    private static final List<String> SUPPORTED_CURRENCIES = 
        List.of("BTC", "ETH", "USDT", "USDC", "BNB");
    
    private static final List<String> SUPPORTED_BLOCKCHAINS = 
        List.of("BITCOIN", "ETHEREUM", "BINANCE_SMART_CHAIN", "POLYGON");
    
    private static final Pattern WALLET_PATTERN = 
        Pattern.compile("^0x[a-fA-F0-9]{40}$|^[13][a-km-zA-HJ-NP-Z1-9]{25,34}$");
    
    /**
     * Compact Constructor with crypto-specific validation
     */
    public CryptoRequest {
        // Validate amount
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Amount must be positive, got: " + amount
            );
        }
        
        // Validate currency
        if (!SUPPORTED_CURRENCIES.contains(currency)) {
            throw new IllegalArgumentException(
                "Unsupported currency: " + currency + 
                ". Supported: " + SUPPORTED_CURRENCIES
            );
        }
        
        // Validate blockchain
        if (!SUPPORTED_BLOCKCHAINS.contains(blockchain)) {
            throw new IllegalArgumentException(
                "Unsupported blockchain: " + blockchain +
                ". Supported: " + SUPPORTED_BLOCKCHAINS
            );
        }
        
        // Validate wallet address
        if (walletId == null || !WALLET_PATTERN.matcher(walletId).matches()) {
            throw new IllegalArgumentException("Invalid wallet address format");
        }
        
        // Validate confirmations
        if (confirmationsRequired < 1 || confirmationsRequired > 12) {
            throw new IllegalArgumentException(
                "Confirmations must be between 1-12, got: " + confirmationsRequired
            );
        }
    }
    
    /**
     * Business method: Check if high-value transaction
     */
    public boolean isHighValue() {
        return switch (currency) {
            case "BTC" -> amount > 0.1;
            case "ETH" -> amount > 1.0;
            case "USDT", "USDC" -> amount > 10000.0;
            default -> amount > 5000.0;
        };
    }
    
    /**
     * Business method: Get estimated processing time
     */
    public int getEstimatedProcessingMinutes() {
        return switch (blockchain) {
            case "BITCOIN" -> confirmationsRequired * 10;
            case "ETHEREUM" -> confirmationsRequired * 2;
            case "BINANCE_SMART_CHAIN" -> confirmationsRequired * 1;
            case "POLYGON" -> confirmationsRequired * 1;
            default -> confirmationsRequired * 5;
        };
    }
    
    /**
     * Business method: Calculate network fee estimate
     */
    public double getEstimatedNetworkFee() {
        return switch (blockchain) {
            case "BITCOIN" -> 0.0001 * amount;
            case "ETHEREUM" -> isHighValue() ? 0.01 * amount : 0.005 * amount;
            case "BINANCE_SMART_CHAIN" -> 0.0001 * amount;
            case "POLYGON" -> 0.00001 * amount;
            default -> 0.001 * amount;
        };
    }
}
