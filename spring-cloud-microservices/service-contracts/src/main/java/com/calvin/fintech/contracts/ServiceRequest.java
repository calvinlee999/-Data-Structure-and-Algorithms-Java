package com.calvin.fintech.contracts;

/**
 * Base Service Request Contract
 * 
 * All service requests MUST implement this sealed interface.
 * 
 * Why Sealed?
 * - Compile-time validation: Only authorized services can implement
 * - Pattern matching: Exhaustive switch statements
 * - Security: Prevents unauthorized implementations
 * - Type-safe: Incorrect types caught at compile time
 * 
 * Every request has:
 * -correlation ID: For distributed tracing (same ID across all services)
 * - timestamp: When request was created
 */
public sealed interface ServiceRequest
    permits PaymentServiceRequest, UserServiceRequest, AccountServiceRequest {
    
    /**
     * Correlation ID for distributed tracing
     * Same ID propagates through Gateway → Payment → User → Account
     */
    String correlationId();
    
    /**
     * Request creation timestamp
     */
    java.time.Instant timestamp();
}
