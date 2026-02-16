package com.calvin.fintech.config;

import com.calvin.fintech.domain.*;
import com.calvin.fintech.gateway.PaymentGateway;
import com.calvin.fintech.validator.PaymentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Function;

/**
 * Functional Bean Registration (Java 21 + Spring Boot 3.2)
 * 
 * Demonstrates:
 * - Function<T, R> as a Spring Bean
 * - Pattern Matching for switch expressions
 * - Sealed interfaces for type safety
 * - Deployment-agnostic functional units
 * 
 * Impact:
 * - Can be deployed as REST, Lambda, Kafka consumer
 * - No reflection overhead
 * - GraalVM native-friendly
 * - Testable in isolation
 */
@Configuration
public class PaymentFunctionConfig {
    
    private static final Logger log = LoggerFactory.getLogger(PaymentFunctionConfig.class);
    
    /**
     * Main payment processing function
     * 
     * Java 21 Pattern Matching:
     * - Exhaustive switch on sealed interface
     * - No default case needed (compiler enforces)
     * - Type-safe branching
     * 
     * Spring Cloud Function:
     * - Can be exposed as HTTP endpoint
     * - Can be deployed to AWS Lambda
     * - Can consume from Kafka/RabbitMQ
     */
    @Bean
    public Function<PaymentRequest, PaymentResponse> processPayment(
            PaymentGateway gateway,
            PaymentValidator validator) {
        
        return request -> {
            log.info("Processing payment request: {}", request.getClass().getSimpleName());
            
            // Validate first
            validator.validate(request);
            
            // Pattern Matching for switch (Java 21)
            // Replaces complex if-else chains
            // Compiler enforces exhaustiveness
            return switch (request) {
                case CreditCardRequest cc -> {
                    log.debug("Processing credit card payment: {}", cc.getMaskedCardNumber());
                    yield processCreditCard(cc, gateway);
                }
                
                case CryptoRequest crypto -> {
                    log.debug("Processing crypto payment: {} {}", 
                        crypto.amount(), crypto.currency());
                    yield processCrypto(crypto, gateway);
                }
                
                case BankTransferRequest bank -> {
                    log.debug("Processing bank transfer: {} {}", 
                        bank.amount(), bank.currency());
                    yield processBankTransfer(bank, gateway);
                }
                
                // No default case needed!
                // Sealed interface ensures only these 3 types exist
                // Compiler error if we miss any case
            };
        };
    }
    
    /**
     * Credit Card Processing (SFAS Pattern)
     * 
     * Service: processPayment (orchestrator)
     * Function: processCreditCard (this method)
     * Action: gateway.authorizeCreditCard (external call)
     * Step: @Transactional, @Retry (infrastructure concern)
     */
    private PaymentResponse processCreditCard(
            CreditCardRequest request, 
            PaymentGateway gateway) {
        
        String txId = generateTransactionId();
        
        try {
            // Check if card is expiring soon (business logic in record)
            if (request.isExpiringSoon()) {
                log.warn("Card expiring soon: {}", request.getMaskedCardNumber());
                return PaymentResponse.pending(
                    txId,
                    request.amount(),
                    request.currency(),
                    "Card expiring soon - please update"
                );
            }
            
            // Call payment gateway
            boolean authorized = gateway.authorizeCreditCard(
                request.cardNumber(),
                request.cvv(),
                request.amount(),
                request.currency()
            );
            
            if (authorized) {
                return PaymentResponse.success(
                    txId,
                    request.amount(),
                    request.currency(),
                    new PaymentMetadata(
                        "GATEWAY-" + txId,
                        "AUTH-" + UUID.randomUUID().toString().substring(0, 8),
                        "APPROVED"
                    )
                );
            } else {
                return PaymentResponse.declined(
                    txId,
                    request.amount(),
                    request.currency(),
                    "Insufficient funds or card declined"
                );
            }
            
        } catch (Exception e) {
            log.error("Credit card processing failed", e);
            return PaymentResponse.failed(
                txId,
                request.amount(),
                request.currency(),
                "Gateway error: " + e.getMessage()
            );
        }
    }
    
    /**
     * Crypto Processing
     * 
     * Demonstrates:
     * - Business logic using record methods
     * - Conditional processing based on transaction value
     */
    private PaymentResponse processCrypto(
            CryptoRequest request,
            PaymentGateway gateway) {
        
        String txId = generateTransactionId();
        
        try {
            // High-value transactions require additional verification
            if (request.isHighValue()) {
                log.info("High-value crypto transaction detected: {} {}", 
                    request.amount(), request.currency());
                
                return PaymentResponse.pending(
                    txId,
                    request.amount(),
                    request.currency(),
                    String.format(
                        "High-value transaction pending %d blockchain confirmations (est. %d min)",
                        request.confirmationsRequired(),
                        request.getEstimatedProcessingMinutes()
                    )
                );
            }
            
            // Process normal crypto payment
            boolean confirmed = gateway.processCrypto(
                request.walletId(),
                request.amount(),
                request.currency(),
                request.blockchain()
            );
            
            if (confirmed) {
                double networkFee = request.getEstimatedNetworkFee();
                
                return PaymentResponse.success(
                    txId,
                    request.amount(),
                    request.currency(),
                    new PaymentMetadata(
                        "BLOCKCHAIN-TX-" + txId,
                        null,
                        String.format("Confirmed on %s, Network fee: %.6f", 
                            request.blockchain(), networkFee)
                    )
                );
            } else {
                return PaymentResponse.pending(
                    txId,
                    request.amount(),
                    request.currency(),
                    "Awaiting blockchain confirmations"
                );
            }
            
        } catch (Exception e) {
            log.error("Crypto processing failed", e);
            return PaymentResponse.failed(
                txId,
                request.amount(),
                request.currency(),
                "Blockchain error: " + e.getMessage()
            );
        }
    }
    
    /**
     * Bank Transfer Processing
     * 
     * Demonstrates:
     * - Using record business methods
     * - Compliance checks based on amount
     */
    private PaymentResponse processBankTransfer(
            BankTransferRequest request,
            PaymentGateway gateway) {
        
        String txId = generateTransactionId();
        
        try {
            // Check if requires documentation (AML/KYC)
            if (request.requiresDocumentation()) {
                log.info("Bank transfer requires documentation: {} {}", 
                    request.amount(), request.currency());
                
                return new PaymentResponse(
                    txId,
                    PaymentResponse.PaymentStatus.REQUIRES_VERIFICATION,
                    "Additional documentation required for international transfer > $10,000",
                    Instant.now(),
                    request.amount(),
                    request.currency(),
                    new PaymentMetadata(null, null, "KYC_REQUIRED")
                );
            }
            
            // Process bank transfer
            boolean initiated = gateway.initiateBankTransfer(
                request.iban(),
                request.swiftCode(),
                request.amount(),
                request.currency(),
                request.beneficiaryName()
            );
            
            if (initiated) {
                int processingDays = request.getEstimatedProcessingDays();
                double fee = request.getTransferFee();
                
                return PaymentResponse.pending(
                    txId,
                    request.amount(),
                    request.currency(),
                    String.format(
                        "%s transfer initiated. Processing time: %d business days, Fee: $%.2f",
                        request.transferType(),
                        processingDays,
                        fee
                    )
                );
            } else {
                return PaymentResponse.declined(
                    txId,
                    request.amount(),
                    request.currency(),
                    "Bank transfer could not be initiated"
                );
            }
            
        } catch (Exception e) {
            log.error("Bank transfer processing failed", e);
            return PaymentResponse.failed(
                txId,
                request.amount(),
                request.currency(),
                "Banking system error: " + e.getMessage()
            );
        }
    }
    
    /**
     * Helper: Generate transaction ID
     */
    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis() + "-" + 
               UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    /**
     * Additional function: Payment validation
     * 
     * Demonstrates: Function composition
     */
    @Bean
    public Function<PaymentRequest, Boolean> validatePayment(
            PaymentValidator validator) {
        
        return request -> {
            try {
                validator.validate(request);
                return true;
            } catch (Exception e) {
                log.error("Validation failed", e);
                return false;
            }
        };
    }
    
    /**
     * Additional function: Payment routing
     * 
     * Demonstrates: Function that returns another function
     */
    @Bean
    public Function<PaymentRequest, String> routePayment() {
        return request -> switch (request) {
            case CreditCardRequest cc -> "CREDIT_CARD_GATEWAY";
            case CryptoRequest crypto -> "CRYPTO_GATEWAY_" + crypto.blockchain();
            case BankTransferRequest bank -> 
                bank.transferType() == BankTransferRequest.TransferType.INTERNATIONAL
                    ? "SWIFT_GATEWAY"
                    : "DOMESTIC_GATEWAY";
        };
    }
}
