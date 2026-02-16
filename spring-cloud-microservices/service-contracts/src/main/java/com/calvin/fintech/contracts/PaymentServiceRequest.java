package com.calvin.fintech.contracts;

import com.calvin.fintech.domain.PaymentRequest;

/**
 * Payment Service Request Contract (Sealed)
 * 
 * Only these request types are allowed for payment service:
 * - ProcessPaymentRequest
 * - ValidatePaymentRequest
 * 
 * Benefits:
 * - Compiler enforces exhaustive pattern matching
 * - Cannot inject unauthorized request types
 * - API versioning: New version = new sealed interface
 */
public sealed interface PaymentServiceRequest extends ServiceRequest
    permits ProcessPaymentRequest, ValidatePaymentRequest {
}

/**
 * Request to process a payment
 */
record ProcessPaymentRequest(
    String correlationId,
    java.time.Instant timestamp,
    PaymentRequest paymentDetails,  // From Phase 6 (sealed interface)
    String userId
) implements PaymentServiceRequest {}

/**
 * Request to validate payment (without processing)
 */
record ValidatePaymentRequest(
    String correlationId,
    java.time.Instant timestamp,
    PaymentRequest paymentDetails
) implements PaymentServiceRequest {}
