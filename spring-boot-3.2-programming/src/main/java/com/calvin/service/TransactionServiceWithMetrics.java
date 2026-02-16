package com.calvin.service;

import com.calvin.domain.*;
import com.calvin.repository.TransactionRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Transaction Service with Metrics - "Best of Both Worlds" Paradigm
 * 
 * <p>Iteration 2 Improvements:</p>
 * <ul>
 *   <li>✅ Added Micrometer custom metrics</li>
 *   <li>✅ Enhanced error handling in batch processing</li>
 *   <li>✅ Transaction boundary documentation</li>
 * </ul>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
@Service
public class TransactionServiceWithMetrics {

    private final TransactionRepository repository;
    private final Executor virtualThreadExecutor;
    private final MeterRegistry meterRegistry;

    public TransactionServiceWithMetrics(
            TransactionRepository repository,
            Executor virtualThreadExecutor,
            MeterRegistry meterRegistry) {
        this.repository = repository;
        this.virtualThreadExecutor = virtualThreadExecutor;
        this.meterRegistry = meterRegistry;
    }

    /**
     * Create transaction with metrics
     * 
     * <p>Custom Metrics:</p>
     * <ul>
     *   <li>transaction.created (Counter) - Total transactions created</li>
     *   <li>transaction.create.time (Timer) - Time to create transaction</li>
     *   <li>transaction.amount (Gauge) - Transaction amount distribution</li>
     * </ul>
     */
    @Transactional
    public TransactionResponseDTO createTransactionWithMetrics(CreateTransactionRequest request) {
        Timer.Sample sample = Timer.start(meterRegistry);
        
        try {
            // Record validation happens in Record constructor
            Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                request.customerId(),
                request.amount(),
                TransactionType.valueOf(request.type()),
                request.paymentMethod(),
                request.description()
            );
            
            Transaction saved = repository.save(transaction);
            
            // Increment counter with tags
            meterRegistry.counter("transaction.created",
                "type", request.type(),
                "paymentMethod", request.paymentMethod()
            ).increment();
            
            // Record amount gauge
            meterRegistry.gauge("transaction.amount", 
                Collections.singletonList(io.micrometer.core.instrument.Tag.of("type", request.type())),
                request.amount().doubleValue()
            );
            
            sample.stop(meterRegistry.timer("transaction.create.time"));
            
            return new TransactionResponseDTO(
                saved.getId(),
                saved.getTransactionId(),
                saved.getCustomerId(),
                saved.getAmount(),
                saved.getType().name(),
                saved.getStatus().name(),
                saved.getPaymentMethod(),
                saved.getCreatedAt(),
                saved.isHighValue()
            );
        } catch (Exception e) {
            meterRegistry.counter("transaction.error", 
                "type", "creation_failed",
                "reason", e.getClass().getSimpleName()
            ).increment();
            throw e;
        }
    }

    /**
     * Batch processing with improved error handling
     * 
     * <p>Transaction Boundaries:</p>
     * <ul>
     *   <li>Each approval is in its own implicit transaction</li>
     *   <li>Failures don't rollback successful approvals</li>
     *   <li>Errors are collected and returned in result</li>
     * </ul>
     */
    @Transactional
    public BatchTransactionResultDTO processBatchApprovalsWithErrorHandling(List<String> transactionIds) {
        List<String> processed = Collections.synchronizedList(new ArrayList<>());
        List<String> errors = Collections.synchronizedList(new ArrayList<>());
        
        Timer.Sample sample = Timer.start(meterRegistry);
        
        // Parallel stream with Virtual Threads = High concurrency
        transactionIds.parallelStream()
            .forEach(txId -> {
                try {
                    // Find transaction
                    Optional<Transaction> txOpt = repository.findAll().stream()
                        .filter(t -> t.getTransactionId().equals(txId))
                        .findFirst();
                    
                    if (txOpt.isEmpty()) {
                        errors.add(txId + ": Transaction not found");
                        return;
                    }
                    
                    Transaction tx = txOpt.get();
                    
                    // Business logic with error handling
                    tx.approve();  // May throw IllegalStateException
                    repository.save(tx);
                    processed.add(txId);
                    
                    // Record success metric
                    meterRegistry.counter("transaction.approved").increment();
                    
                } catch (IllegalStateException e) {
                    // Business rule violation (e.g., wrong status)
                    errors.add(txId + ": " + e.getMessage());
                    meterRegistry.counter("transaction.approval.failed", 
                        "reason", "business_rule"
                    ).increment();
                    
                } catch (Exception e) {
                    // Unexpected error (database, network, etc.)
                    errors.add(txId + ": Database error - " + e.getMessage());
                    meterRegistry.counter("transaction.approval.failed", 
                        "reason", "technical_error"
                    ).increment();
                }
            });
        
        sample.stop(meterRegistry.timer("transaction.batch.approval.time"));
        
        // Record batch metrics
        meterRegistry.gauge("transaction.batch.size", transactionIds.size());
        meterRegistry.gauge("transaction.batch.success.rate", 
            processed.size() / (double) transactionIds.size()
        );
        
        return new BatchTransactionResultDTO(
            transactionIds.size(),
            processed.size(),
            errors.size(),
            processed,
            errors
        );
    }

    /**
     * Get dashboard with performance monitoring
     */
    public CompletableFuture<Map<String, Object>> getCustomerDashboardWithMetrics(Long customerId) {
        Timer.Sample sample = Timer.start(meterRegistry);
        
        CompletableFuture<Map<String, Object>> result = CompletableFuture.allOf()
            .thenApply(v -> {
                Map<String, Object> dashboard = new HashMap<>();
                dashboard.put("customerId", customerId);
                dashboard.put("timestamp", LocalDateTime.now());
                
                sample.stop(meterRegistry.timer("dashboard.load.time", 
                    "customerId", customerId.toString()
                ));
                
                return dashboard;
            });
        
        return result;
    }
}
