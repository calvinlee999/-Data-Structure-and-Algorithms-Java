package com.calvin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler - Production-Grade Error Handling
 * 
 * <p>FinTech Principal Engineer's Guide to Error Management</p>
 * 
 * <h2>Design Principles</h2>
 * <ul>
 *   <li><b>Consistent Error Format:</b> All errors return same structure</li>
 *   <li><b>Security:</b> Don't expose stack traces to clients</li>
 *   <li><b>Observability:</b> Log all errors for debugging</li>
 *   <li><b>User-Friendly:</b> Clear error messages for API consumers</li>
 * </ul>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle validation errors (400 Bad Request)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            IllegalArgumentException ex, WebRequest request) {
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "VALIDATION_ERROR",
            ex.getMessage(),
            request.getDescription(false),
            LocalDateTime.now()
        );
        
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Handle state errors (409 Conflict)
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleStateException(
            IllegalStateException ex, WebRequest request) {
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "STATE_ERROR",
            ex.getMessage(),
            request.getDescription(false),
            LocalDateTime.now()
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Handle generic exceptions (500 Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        // Log full stack trace (but don't send to client)
        System.err.println("Unexpected error: " + ex.getMessage());
        ex.printStackTrace();
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "INTERNAL_ERROR",
            "An unexpected error occurred. Please contact support.",
            request.getDescription(false),
            LocalDateTime.now()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

/**
 * Standardized Error Response DTO
 * 
 * <p>Immutable Record for consistent error format</p>
 */
record ErrorResponse(
    int status,
    String error,
    String message,
    String path,
    LocalDateTime timestamp
) {}
