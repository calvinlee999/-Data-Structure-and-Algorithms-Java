package com.calvin.java17.models;

/**
 * Sealed Payment Status Interface - Java 17 State Management
 * 
 * Enforces DDD boundaries for payment status lifecycle.
 * Prevents external modules from creating unauthorized payment states.
 * 
 * Benefits:
 * - Compiler guarantees only 6 valid states
 * - Exhaustive pattern matching in switch
 * - Clear state transition rules
 * - No runtime surprises
 * 
 * FinTech Use Case: Payment state machine, approval workflows
 */
public sealed interface PaymentStatus 
    permits Pending, Approved, Declined, Processing, Completed, Failed {
    
    /**
     * All status types must provide these core attributes
     */
    String code();
    String message();
    
    /**
     * Check if status is terminal (no further transitions possible)
     */
    default boolean isTerminal() {
        return this instanceof Completed || 
               this instanceof Failed || 
               this instanceof Declined;
    }
    
    /**
     * Check if status allows retries
     */
    default boolean canRetry() {
        return this instanceof Failed || this instanceof Declined;
    }
    
    /**
     * Get next valid states for state machine
     */
    default String[] getValidNextStates() {
        return switch (this) {
            case Pending p -> new String[]{"PROCESSING", "DECLINED"};
            case Processing p -> new String[]{"APPROVED", "FAILED"};
            case Approved p -> new String[]{"COMPLETED", "FAILED"};
            case Completed c -> new String[]{};  // Terminal state
            case Failed f -> new String[]{"PENDING"};  // Can retry
            case Declined d -> new String[]{};  // Terminal state
        };
    }
}

/**
 * Pending Status - Initial state
 */
record Pending(String code, String message) implements PaymentStatus {
    public Pending {
        if (code == null) code = "PENDING";
        if (message == null) message = "Payment is pending review";
    }
    
    public Pending() {
        this("PENDING", "Payment is pending review");
    }
}

/**
 * Approved Status - Payment approved, awaiting completion
 */
record Approved(String code, String message, String approvedBy, long approvalTimestamp) 
    implements PaymentStatus {
    
    public Approved {
        if (code == null) code = "APPROVED";
        if (message == null) message = "Payment approved";
    }
    
    public Approved(String approvedBy, long approvalTimestamp) {
        this("APPROVED", "Payment approved by " + approvedBy, approvedBy, approvalTimestamp);
    }
}

/**
 * Declined Status - Payment declined (terminal)
 */
record Declined(String code, String message, String reason, String declinedBy) 
    implements PaymentStatus {
    
    public Declined {
        if (code == null) code = "DECLINED";
        if (message == null) message = "Payment declined: " + reason;
    }
    
    public Declined(String reason) {
        this("DECLINED", "Payment declined: " + reason, reason, "SYSTEM");
    }
}

/**
 * Processing Status - Payment is being processed
 */
record Processing(String code, String message, String processor) implements PaymentStatus {
    public Processing {
        if (code == null) code = "PROCESSING";
        if (message == null) message = "Payment is being processed by " + processor;
    }
    
    public Processing(String processor) {
        this("PROCESSING", "Payment is being processed by " + processor, processor);
    }
}

/**
 * Completed Status - Payment completed successfully (terminal)
 */
record Completed(String code, String message, String confirmationNumber, long completedTimestamp) 
    implements PaymentStatus {
    
    public Completed {
        if (code == null) code = "COMPLETED";
        if (message == null) message = "Payment completed: " + confirmationNumber;
    }
    
    public Completed(String confirmationNumber, long completedTimestamp) {
        this("COMPLETED", "Payment completed: " + confirmationNumber, 
             confirmationNumber, completedTimestamp);
    }
}

/**
 * Failed Status - Payment failed, can retry
 */
record Failed(String code, String message, String errorCode, String errorMessage) 
    implements PaymentStatus {
    
    public Failed {
        if (code == null) code = "FAILED";
        if (message == null) message = "Payment failed: " + errorMessage;
    }
    
    public Failed(String errorCode, String errorMessage) {
        this("FAILED", "Payment failed: " + errorMessage, errorCode, errorMessage);
    }
}
