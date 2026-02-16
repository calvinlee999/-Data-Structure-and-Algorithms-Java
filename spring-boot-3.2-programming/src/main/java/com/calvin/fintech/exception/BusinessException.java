package com.calvin.fintech.exception;

/**
 * Business Exception
 * 
 * Thrown when business rules are violated.
 * Maps to HTTP 422 Unprocessable Entity
 * 
 * Examples:
 * - Insufficient funds
 * - Account is closed
 * - Transaction already processed
 */
public class BusinessException extends RuntimeException {
    
    private final String errorCode;
    
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public BusinessException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
