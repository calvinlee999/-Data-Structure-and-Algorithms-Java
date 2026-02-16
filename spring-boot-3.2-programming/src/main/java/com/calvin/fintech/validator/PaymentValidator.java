package com.calvin.fintech.validator;

import com.calvin.fintech.domain.PaymentRequest;
import org.springframework.stereotype.Component;

/**
 * Payment Request Validator
 * 
 * Stateless component for validation logic
 * Separated from business logic for testing
 */
@Component
public class PaymentValidator {
    
    /**
     * Validate payment request
     * 
     * Throws exception if invalid (fail-fast)
     */
    public void validate(PaymentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Payment request cannot be null");
        }
        
        if (!request.isValid()) {
            throw new IllegalArgumentException("Payment request validation failed");
        }
        
        // Additional business rules can be added here
    }
}
