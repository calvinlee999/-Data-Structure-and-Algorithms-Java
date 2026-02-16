package com.calvin.fintech.controller;

import com.calvin.fintech.dto.CreateTransactionRequest;
import com.calvin.fintech.dto.TransactionResponse;
import com.calvin.fintech.entity.TransactionStatus;
import com.calvin.fintech.entity.TransactionType;
import com.calvin.fintech.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Transaction REST Controller
 * 
 * This is the "front desk" that handles HTTP requests from clients.
 * It delegates business logic to the TransactionService.
 * 
 * @RestController = @Controller + @ResponseBody (auto-converts to JSON)
 * @RequestMapping defines the base URL path
 * @CrossOrigin allows requests from other domains (CORS)
 */
@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin(origins = {"http://localhost:3000", "https://app.fintech.com"})
public class TransactionController {
    
    private final TransactionService transactionService;
    
    /**
     * Constructor injection (recommended over @Autowired)
     */
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    /**
     * GET /api/v1/transactions
     * Get all transactions with pagination
     * 
     * Example: GET /api/v1/transactions?page=0&size=20&sort=createdAt,desc
     * 
     * @param page Page number (0-based)
     * @param size Page size (default 20)
     * @param sort Sort criteria (field,direction)
     * @return Paginated list of transactions
     */
    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
        
        // Create pageable with sorting
        Sort.Direction direction = sort.length > 1 && sort[1].equalsIgnoreCase("asc") 
            ? Sort.Direction.ASC 
            : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        
        // This would typically filter by current user's customer ID
        // For demo, we'll get all (in production, add authentication)
        Page<TransactionResponse> transactions = Page.empty(pageable);
        
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * GET /api/v1/transactions/{id}
     * Get single transaction by ID
     * 
     * @PathVariable extracts ID from URL
     * Returns 404 if not found (handled by GlobalExceptionHandler)
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        TransactionResponse transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }
    
    /**
     * GET /api/v1/transactions/customer/{customerId}
     * Get all transactions for a customer
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TransactionResponse>> getCustomerTransactions(
            @PathVariable Long customerId) {
        List<TransactionResponse> transactions = transactionService.getCustomerTransactions(customerId);
        return ResponseEntity.ok(transactions);
    }
    
    /**
     * GET /api/v1/transactions/search
     * Advanced search with multiple filters
     * 
     * Example: GET /api/v1/transactions/search?customerId=1&status=COMPLETED&minAmount=100
     * 
     * @RequestParam(required = false) = optional parameter
     */
    @GetMapping("/search")
    public ResponseEntity<Page<TransactionResponse>> searchTransactions(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        Page<TransactionResponse> results = transactionService.searchTransactions(
            customerId, status, type, minAmount, maxAmount, startDate, endDate, pageable);
        
        return ResponseEntity.ok(results);
    }
    
    /**
     * POST /api/v1/transactions
     * Create new transaction
     * 
     * @Valid triggers validation on request body
     * Returns 201 Created with transaction in body
     */
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request) {
        
        TransactionResponse created = transactionService.createTransaction(request);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(created);
    }
    
    /**
     * PUT /api/v1/transactions/{id}/complete
     * Mark transaction as completed
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<TransactionResponse> completeTransaction(@PathVariable Long id) {
        TransactionResponse updated = transactionService.completeTransaction(id);
        return ResponseEntity.ok(updated);
    }
    
    /**
     * PUT /api/v1/transactions/{id}/fail
     * Mark transaction as failed
     */
    @PutMapping("/{id}/fail")
    public ResponseEntity<TransactionResponse> failTransaction(
            @PathVariable Long id,
            @RequestParam String reason) {
        
        TransactionResponse updated = transactionService.failTransaction(id, reason);
        return ResponseEntity.ok(updated);
    }
    
    /**
     * PUT /api/v1/transactions/{id}/cancel
     * Cancel transaction
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<TransactionResponse> cancelTransaction(@PathVariable Long id) {
        TransactionResponse updated = transactionService.cancelTransaction(id);
        return ResponseEntity.ok(updated);
    }
    
    /**
     * DELETE /api/v1/transactions/{id}
     * Delete transaction (soft delete)
     * 
     * Returns 204 No Content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * GET /api/v1/transactions/customer/{customerId}/count
     * Get transaction count for customer
     */
    @GetMapping("/customer/{customerId}/count")
    public ResponseEntity<Long> getTransactionCount(@PathVariable Long customerId) {
        long count = transactionService.getTransactionCount(customerId);
        return ResponseEntity.ok(count);
    }
    
    /**
     * GET /api/v1/transactions/customer/{customerId}/total
     * Calculate total amount for customer by status
     */
    @GetMapping("/customer/{customerId}/total")
    public ResponseEntity<BigDecimal> getTotalAmount(
            @PathVariable Long customerId,
            @RequestParam TransactionStatus status) {
        
        BigDecimal total = transactionService.calculateTotalAmount(customerId, status);
        return ResponseEntity.ok(total);
    }
    
    /**
     * GET /api/v1/transactions/recent
     * Get recent transactions (last N days)
     */
    @GetMapping("/recent")
    public ResponseEntity<List<TransactionResponse>> getRecentTransactions(
            @RequestParam(defaultValue = "7") int days) {
        
        List<TransactionResponse> transactions = transactionService.getRecentTransactions(days);
        return ResponseEntity.ok(transactions);
    }
}
