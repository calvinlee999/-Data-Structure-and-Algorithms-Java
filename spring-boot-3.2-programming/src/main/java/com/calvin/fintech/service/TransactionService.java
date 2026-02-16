package com.calvin.fintech.service;

import com.calvin.fintech.dto.CreateTransactionRequest;
import com.calvin.fintech.dto.TransactionResponse;
import com.calvin.fintech.entity.Transaction;
import com.calvin.fintech.entity.TransactionStatus;
import com.calvin.fintech.entity.TransactionType;
import com.calvin.fintech.exception.BusinessException;
import com.calvin.fintech.exception.ResourceNotFoundException;
import com.calvin.fintech.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Transaction Service
 * 
 * This is the "business logic layer" - where the real work happens.
 * Think of it as the "manager" that coordinates database operations
 * and enforces business rules.
 * 
 * @Service tells Spring this is a service component
 * @Transactional ensures database operations are atomic (all-or-nothing)
 */
@Service
@Transactional
public class TransactionService {
    
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    
    private final TransactionRepository transactionRepository;
    
    /**
     * Constructor injection (recommended over @Autowired)
     * 
     * Benefits:
     * - Easier to test (can pass mock repository)
     * - Required dependencies are explicit
     * - Immutable (final field)
     */
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    
    /**
     * Create a new transaction
     * 
     * @Transactional ensures if anything fails, everything rolls back
     */
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        log.info("Creating transaction for customer {}: {} {} {}",
            request.customerId(), request.amount(), request.currency(), request.type());
        
        // Validate business rules
        validateTransactionRequest(request);
        
        // Create entity from request
        Transaction transaction = new Transaction(
            request.customerId(),
            request.amount(),
            request.currency(),
            request.type()
        );
        
        transaction.setDescription(request.description());
        
        // Generate unique reference number if not provided
        if (request.referenceNumber() != null) {
            // Verify reference number is unique
            if (transactionRepository.existsByReferenceNumberAndDeletedFalse(request.referenceNumber())) {
                throw new BusinessException(
                    "DUPLICATE_REFERENCE",
                    "Transaction with reference number " + request.referenceNumber() + " already exists"
                );
            }
            transaction.setReferenceNumber(request.referenceNumber());
        } else {
            transaction.setReferenceNumber(generateReferenceNumber());
        }
        
        // Save to database
        Transaction saved = transactionRepository.save(transaction);
        
        log.info("Created transaction {}", saved.getId());
        
        // Convert to response DTO
        return TransactionResponse.from(saved);
    }
    
    /**
     * Get transaction by ID
     * 
     * @Transactional(readOnly = true) optimizes read-only operations
     */
    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
            .filter(t -> !t.getDeleted())  // Exclude soft-deleted
            .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));
        
        return TransactionResponse.from(transaction);
    }
    
    /**
     * Get all transactions for a customer
     */
    @Transactional(readOnly = true)
    public List<TransactionResponse> getCustomerTransactions(Long customerId) {
        return transactionRepository.findByCustomerIdAndDeletedFalse(customerId)
            .stream()
            .map(TransactionResponse::from)
            .toList();
    }
    
    /**
     * Get customer transactions with pagination
     */
    @Transactional(readOnly = true)
    public Page<TransactionResponse> getCustomerTransactions(Long customerId, Pageable pageable) {
        return transactionRepository.findByCustomerIdAndDeletedFalse(customerId, pageable)
            .map(TransactionResponse::from);
    }
    
    /**
     * Search transactions with multiple filters
     */
    @Transactional(readOnly = true)
    public Page<TransactionResponse> searchTransactions(
            Long customerId,
            TransactionStatus status,
            TransactionType type,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable) {
        
        return transactionRepository.searchTransactions(
                customerId, status, type, minAmount, maxAmount, startDate, endDate, pageable)
            .map(TransactionResponse::from);
    }
    
    /**
     * Complete a transaction
     * 
     * Business logic: Only PENDING transactions can be completed
     */
    public TransactionResponse completeTransaction(Long id) {
        log.info("Completing transaction {}", id);
        
        Transaction transaction = findTransactionOrThrow(id);
        
        // Use entity business method
        transaction.complete();
        
        Transaction updated = transactionRepository.save(transaction);
        
        log.info("Transaction {} completed", id);
        
        return TransactionResponse.from(updated);
    }
    
    /**
     * Fail a transaction
     */
    public TransactionResponse failTransaction(Long id, String reason) {
        log.warn("Failing transaction {}: {}", id, reason);
        
        Transaction transaction = findTransactionOrThrow(id);
        
        transaction.fail(reason);
        
        Transaction updated = transactionRepository.save(transaction);
        
        return TransactionResponse.from(updated);
    }
    
    /**
     * Cancel a transaction
     * 
     * Business logic: Cannot cancel COMPLETED transactions
     */
    public TransactionResponse cancelTransaction(Long id) {
        log.info("Cancelling transaction {}", id);
        
        Transaction transaction = findTransactionOrThrow(id);
        
        transaction.cancel();
        
        Transaction updated = transactionRepository.save(transaction);
        
        log.info("Transaction {} cancelled", id);
        
        return TransactionResponse.from(updated);
    }
    
    /**
     * Delete transaction (soft delete)
     * 
     * In FinTech, we NEVER hard-delete transactions.
     * Just mark as deleted for audit trail.
     */
    public void deleteTransaction(Long id) {
        log.info("Soft deleting transaction {}", id);
        
        Transaction transaction = findTransactionOrThrow(id);
        
        transaction.setDeleted(true);
        transactionRepository.save(transaction);
        
        log.info("Transaction {} soft deleted", id);
    }
    
    /**
     * Get transaction count for customer
     */
    @Transactional(readOnly = true)
    public long getTransactionCount(Long customerId) {
        return transactionRepository.countByCustomerIdAndDeletedFalse(customerId);
    }
    
    /**
     * Calculate total amount by customer and status
     */
    @Transactional(readOnly = true)
    public BigDecimal calculateTotalAmount(Long customerId, TransactionStatus status) {
        BigDecimal total = transactionRepository.calculateTotalAmountByCustomerAndStatus(
            customerId, status);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Get recent transactions (last N days)
     */
    @Transactional(readOnly = true)
    public List<TransactionResponse> getRecentTransactions(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        return transactionRepository.findRecentTransactions(startDate)
            .stream()
            .map(TransactionResponse::from)
            .toList();
    }
    
    // ===== Private Helper Methods =====
    
    /**
     * Find transaction or throw exception if not found
     */
    private Transaction findTransactionOrThrow(Long id) {
        return transactionRepository.findById(id)
            .filter(t -> !t.getDeleted())
            .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));
    }
    
    /**
     * Validate transaction request against business rules
     */
    private void validateTransactionRequest(CreateTransactionRequest request) {
        // Validate amount is positive
        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(
                "INVALID_AMOUNT",
                "Transaction amount must be greater than zero"
            );
        }
        
        // Validate currency is supported
        if (!isSupportedCurrency(request.currency())) {
            throw new BusinessException(
                "UNSUPPORTED_CURRENCY",
                "Currency " + request.currency() + " is not supported"
            );
        }
        
        // Additional type-specific validations
        validateTransactionType(request.type(), request.amount());
    }
    
    /**
     * Check if currency is supported
     */
    private boolean isSupportedCurrency(String currency) {
        // In production, this would check against a configuration
        return List.of("USD", "EUR", "GBP", "JPY", "CAD", "AUD").contains(currency);
    }
    
    /**
     * Validate transaction type specific rules
     */
    private void validateTransactionType(TransactionType type, BigDecimal amount) {
        switch (type) {
            case WITHDRAWAL, PAYMENT -> {
                // These must have positive amounts (already validated)
                if (amount.compareTo(BigDecimal.valueOf(1000000)) > 0) {
                    throw new BusinessException(
                        "AMOUNT_EXCEEDS_LIMIT",
                        type + " cannot exceed $1,000,000 per transaction"
                    );
                }
            }
            case TRANSFER -> {
                // Transfer limits might be different
                if (amount.compareTo(BigDecimal.valueOf(500000)) > 0) {
                    throw new BusinessException(
                        "AMOUNT_EXCEEDS_LIMIT",
                        "Transfer cannot exceed $500,000 per transaction"
                    );
                }
            }
            // Add more type-specific validations
        }
    }
    
    /**
     * Generate unique reference number
     * 
     * Format: TXN-YYYYMMDD-UUID
     * Example: TXN-20260216-a1b2c3d4
     */
    private String generateReferenceNumber() {
        String datePart = LocalDateTime.now().toString().substring(0, 10).replace("-", "");
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);
        return "TXN-" + datePart + "-" + uuidPart.toUpperCase();
    }
}
