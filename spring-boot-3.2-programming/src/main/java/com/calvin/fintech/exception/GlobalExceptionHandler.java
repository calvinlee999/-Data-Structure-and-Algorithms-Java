package com.calvin.fintech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global Exception Handler
 * 
 * Catches all exceptions in the application and converts them to
 * consistent, user-friendly HTTP responses.
 * 
 * Think of this as "customer service" - when things go wrong,
 * this translates technical errors into helpful messages.
 * 
 * @RestControllerAdvice applies to all @RestController classes
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Handle validation errors (400 Bad Request)
     * 
     * Triggered by @Valid annotation on request DTOs
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        
        // Collect all field validation errors
        Map<String, String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                error -> error.getDefaultMessage() != null 
                    ? error.getDefaultMessage() 
                    : "Invalid value",
                (existing, replacement) -> existing  // Keep first error if duplicates
            ));
        
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Validation failed for one or more fields. Please check your input."
        );
        problem.setTitle("Validation Error");
        problem.setProperty("errors", errors);
        problem.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(problem);
    }
    
    /**
     * Handle resource not found (404 Not Found)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFound(
            ResourceNotFoundException ex) {
        
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            ex.getMessage()
        );
        problem.setTitle("Resource Not Found");
        problem.setProperty("resourceType", ex.getResourceType());
        problem.setProperty("resourceId", ex.getResourceId());
        problem.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(problem);
    }
    
    /**
     * Handle business logic errors (422 Unprocessable Entity)
     * 
     * Business rules violations like:
     * - Insufficient funds
     * - Invalid transaction state
     * - Account closed
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> handleBusinessException(
            BusinessException ex) {
        
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.UNPROCESSABLE_ENTITY,
            ex.getMessage()
        );
        problem.setTitle("Business Rule Violation");
        problem.setProperty("errorCode", ex.getErrorCode());
        problem.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(problem);
    }
    
    /**
     * Handle illegal argument exceptions (400 Bad Request)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(
            IllegalArgumentException ex) {
        
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            ex.getMessage()
        );
        problem.setTitle("Invalid Argument");
        problem.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(problem);
    }
    
    /**
     * Handle illegal state exceptions (409 Conflict)
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> handleIllegalState(
            IllegalStateException ex) {
        
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.CONFLICT,
            ex.getMessage()
        );
        problem.setTitle("Invalid State");
        problem.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(problem);
    }
    
    /**
     * Handle all other unexpected errors (500 Internal Server Error)
     * 
     * IMPORTANT: Don't expose internal error details to clients!
     * Log the full exception for debugging, but return generic message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericError(Exception ex) {
        
        // TODO: Log the full exception for debugging
        // log.error("Unexpected error occurred", ex);
        
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred. Please try again later. If the problem persists, contact support."
        );
        problem.setTitle("Internal Server Error");
        problem.setProperty("timestamp", LocalDateTime.now());
        
        // DO NOT include ex.getMessage() in production - might expose sensitive info!
        
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(problem);
    }
}
