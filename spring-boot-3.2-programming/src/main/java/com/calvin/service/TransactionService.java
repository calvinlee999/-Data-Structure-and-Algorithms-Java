package com.calvin.service;

import com.calvin.domain.*;
import com.calvin.repository.TransactionRepository;
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
 * Transaction Service - "Best of Both Worlds" Paradigm
 * 
 * <p>FinTech Principal Engineer's Guide to Modern Service Architecture</p>
 * 
 * <h2>The Paradigm Shift</h2>
 * <table border="1">
 *   <tr>
 *     <th>Feature</th>
 *     <th>Old "Reactive" Way</th>
 *     <th>New "Loom" Way</th>
 *   </tr>
 *   <tr>
 *     <td>Logic Style</td>
 *     <td>Declarative Orchestration (flux.map().flatMap())</td>
 *     <td><b>Imperative Orchestration</b> (service.getData())</td>
 *   </tr>
 *   <tr>
 *     <td>Data Style</td>
 *     <td>Declarative Transformation (Streams/Lambdas)</td>
 *     <td><b>Declarative Transformation</b> (Streams + Records + Pattern Matching)</td>
 *   </tr>
 *   <tr>
 *     <td>Concurrency</td>
 *     <td>Event-Loop (Non-blocking)</td>
 *     <td><b>Virtual Threads</b> (Blocking but extremely cheap)</td>
 *   </tr>
 *   <tr>
 *     <td>Debugging</td>
 *     <td>Difficult (Stack traces don't follow logic)</td>
 *     <td><b>Easy</b> (Standard stack traces work)</td>
 *   </tr>
 * </table>
 * 
 * <h2>Design Principles</h2>
 * <ol>
 *   <li><b>Principle 3 (Statelessness):</b> No internal state, no for-loops</li>
 *   <li><b>External Iteration:</b> Use Stream API instead of for/while loops</li>
 *   <li><b>Functional Composition:</b> Build complex logic from pure functions</li>
 *   <li><b>Virtual Thread Compatible:</b> Blocking DB calls are now "cheap"</li>
 * </ol>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final Executor virtualThreadExecutor;

    public TransactionService(
            TransactionRepository repository,
            Executor virtualThreadExecutor) {
        this.repository = repository;
        this.virtualThreadExecutor = virtualThreadExecutor;
    }

    /**
     * PATTERN 1: "Clean Pipeline" - Imperative Orchestration + Declarative Transformation
     * 
     * <p>Old Way (Internal Looping - Enemy of Principle 3):</p>
     * <pre>
     * public List<TransactionSummaryDTO> getDailySummary(Long customerId) {
     *     List<Transaction> txns = repository.findByCustomerId(customerId);
     *     List<TransactionSummaryDTO> result = new ArrayList<>();
     *     for (Transaction t : txns) {  // ❌ Internal looping!
     *         if (t.getStatus().equals("APPROVED")) {
     *             result.add(new TransactionSummaryDTO(...));
     *         }
     *     }
     *     return result;
     * }
     * </pre>
     * 
     * <p>New Way (External Iteration - Declarative):</p>
     */
    @Transactional(readOnly = true)
    public List<TransactionSummaryDTO> getDailySummary(Long customerId) {
        // Step 1: Simple service call (Imperative) - looks blocking, but it's on Virtual Thread
        try (Stream<Transaction> stream = repository.findByCustomerId(customerId)) {
            
            // Step 2: Declarative transformation (No internal loops!)
            return stream
                .filter(t -> "APPROVED".equals(t.getStatus().name()))  // External iteration via Stream
                .map(this::toSummaryDTO)                                // Pure transformation
                .sorted(Comparator.comparing(TransactionSummaryDTO::createdAt).reversed())
                .toList();                                              // Terminal operation
        }
    }

    /**
     * PATTERN 2: Functional Composition - Build complex logic from pure functions
     * 
     * <p>Demonstrates: Function chaining, Predicate composition, Stream pipelines</p>
     */
    @Transactional(readOnly = true)
    public DailyTransactionSummaryDTO calculateDailySummary(LocalDateTime date) {
        List<Transaction> transactions = repository.findAll(); // All transactions
        
        // Define reusable predicates (Pure functions)
        Predicate<Transaction> isOnDate = t -> t.getCreatedAt().toLocalDate().equals(date.toLocalDate());
        Predicate<Transaction> isApproved = t -> "APPROVED".equals(t.getStatus().name());
        Predicate<Transaction> isRejected = t -> "REJECTED".equals(t.getStatus().name());
        Predicate<Transaction> isPending = t -> "PENDING".equals(t.getStatus().name());
        
        // Functional transformation pipeline (No loops!)
        var dailyTransactions = transactions.stream()
            .filter(isOnDate)
            .toList();
        
        return new DailyTransactionSummaryDTO(
            date,
            dailyTransactions.size(),
            calculateTotalVolume(dailyTransactions),
            calculateAverage(dailyTransactions),
            (int) dailyTransactions.stream().filter(isApproved).count(),
            (int) dailyTransactions.stream().filter(isRejected).count(),
            (int) dailyTransactions.stream().filter(isPending).count()
        );
    }

    /**
     * PATTERN 3: SequencedCollection (Java 21) - Eliminate index-based loops
     * 
     * <p>Old Way (Index-based loop - O(n) mental overhead):</p>
     * <pre>
     * List<Transaction> txns = repository.findAll();
     * Transaction first = txns.get(0);           // ❌ Index manipulation
     * Transaction last = txns.get(txns.size()-1); // ❌ Index calculation
     * </pre>
     * 
     * <p>New Way (SequencedCollection - O(1) mental overhead):</p>
     */
    @Transactional(readOnly = true)
    public Map<String, TransactionSummaryDTO> getFirstAndLastTransactions(Long customerId) {
        List<Transaction> transactions = repository.findByCustomerId(customerId).toList();
        
        // Java 21: SequencedCollection methods (No index math!)
        return Map.of(
            "first", toSummaryDTO(transactions.getFirst()),  // O(1) mental overhead
            "last", toSummaryDTO(transactions.getLast())     // No get(size-1) nonsense
        );
    }

    /**
     * PATTERN 4: Pattern Matching for Data (Java 21) - Replace if-else chains
     * 
     * <p>Old Way (Imperative if-else):</p>
     * <pre>
     * public String processTransaction(Transaction t) {
     *     if (t.getType().equals("PAYMENT")) {
     *         if (t.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
     *             return "High-value payment";
     *         } else {
     *             return "Standard payment";
     *         }
     *     } else if (t.getType().equals("REFUND")) {
     *         return "Refund";
     *     } // ... more else-if
     * }
     * </pre>
     * 
     * <p>New Way (Declarative switch expression + pattern matching):</p>
     */
    public String categorizeTransaction(Transaction transaction) {
        return switch (transaction.getType()) {
            case PAYMENT -> transaction.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0
                ? "High-Value Payment (requires approval)"
                : "Standard Payment";
            case REFUND -> "Refund Transaction";
            case TRANSFER -> "Internal Transfer";
            case WITHDRAWAL -> "Withdrawal Request";
            case DEPOSIT -> "Deposit Transaction";
        };
    }

    /**
     * PATTERN 5: Parallel Streams with Virtual Threads
     * 
     * <p>Demonstrates safe parallelism with Stream API</p>
     */
    @Transactional(readOnly = true)
    public BatchTransactionResultDTO processBatchApprovals(List<String> transactionIds) {
        List<String> processed = Collections.synchronizedList(new ArrayList<>());
        List<String> errors = Collections.synchronizedList(new ArrayList<>());
        
        // Parallel stream with Virtual Threads = High concurrency
        transactionIds.parallelStream()
            .forEach(txId -> {
                try {
                    // Simulate approval logic (blocking call, but cheap on Virtual Threads)
                    Optional<Transaction> txOpt = repository.findAll().stream()
                        .filter(t -> t.getTransactionId().equals(txId))
                        .findFirst();
                    
                    txOpt.ifPresent(tx -> {
                        tx.approve();
                        repository.save(tx);
                        processed.add(txId);
                    });
                } catch (Exception e) {
                    errors.add(txId + ": " + e.getMessage());
                }
            });
        
        return new BatchTransactionResultDTO(
            transactionIds.size(),
            processed.size(),
            errors.size(),
            processed,
            errors
        );
    }

    /**
     * PATTERN 6: Collectors and Grouping (Declarative aggregation)
     * 
     * <p>Old Way (Internal loop with mutable map):</p>
     * <pre>
     * Map<String, BigDecimal> volumeByType = new HashMap<>();
     * for (Transaction t : transactions) {
     *     String type = t.getType();
     *     volumeByType.put(type, volumeByType.getOrDefault(type, BigDecimal.ZERO).add(t.getAmount()));
     * }
     * </pre>
     * 
     * <p>New Way (Collectors - no mutation):</p>
     */
    @Transactional(readOnly = true)
    public Map<String, BigDecimal> getVolumeByTransactionType() {
        return repository.findAll().stream()
            .collect(Collectors.groupingBy(
                t -> t.getType().name(),
                Collectors.reducing(
                    BigDecimal.ZERO,
                    Transaction::getAmount,
                    BigDecimal::add
                )
            ));
    }

    /**
     * PATTERN 7: CompletableFuture with Virtual Threads
     * 
     * <p>Demonstrates asynchronous composition on Virtual Threads</p>
     */
    public CompletableFuture<Map<String, Object>> getCustomerDashboard(Long customerId) {
        // All blocking calls, but concurrent via Virtual Threads
        CompletableFuture<List<TransactionSummaryDTO>> summaryFuture = 
            CompletableFuture.supplyAsync(() -> getDailySummary(customerId), virtualThreadExecutor);
        
        CompletableFuture<BigDecimal> totalSpendFuture = 
            CompletableFuture.supplyAsync(() -> calculateTotalSpend(customerId), virtualThreadExecutor);
        
        CompletableFuture<Long> pendingCountFuture = 
            CompletableFuture.supplyAsync(() -> 
                repository.countPendingTransactionsSince(customerId, LocalDateTime.now().minusDays(30)),
                virtualThreadExecutor
            );
        
        // Combine results functionally
        return CompletableFuture.allOf(summaryFuture, totalSpendFuture, pendingCountFuture)
            .thenApply(v -> Map.of(
                "recentTransactions", summaryFuture.join(),
                "totalSpend", totalSpendFuture.join(),
                "pendingCount", pendingCountFuture.join()
            ));
    }

    // ============= Helper Methods (Pure Functions) =============

    /**
     * Pure transformation function (no side effects)
     */
    private TransactionSummaryDTO toSummaryDTO(Transaction t) {
        return new TransactionSummaryDTO(
            t.getId(),
            t.getTransactionId(),
            t.getAmount(),
            t.getStatus().name(),
            t.getCreatedAt()
        );
    }

    /**
     * Pure calculation function
     */
    private BigDecimal calculateTotalVolume(List<Transaction> transactions) {
        return transactions.stream()
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Pure calculation function
     */
    private BigDecimal calculateAverage(List<Transaction> transactions) {
        if (transactions.isEmpty()) return BigDecimal.ZERO;
        
        BigDecimal total = calculateTotalVolume(transactions);
        return total.divide(
            BigDecimal.valueOf(transactions.size()), 
            2, 
            RoundingMode.HALF_UP
        );
    }

    /**
     * Pure calculation function
     */
    private BigDecimal calculateTotalSpend(Long customerId) {
        return repository.findByCustomerId(customerId)
            .filter(t -> "APPROVED".equals(t.getStatus().name()))
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Create transaction (Demonstrates Record validation)
     */
    @Transactional
    public TransactionResponseDTO createTransaction(CreateTransactionRequest request) {
        // Record validation happens in constructor
        Transaction transaction = new Transaction(
            UUID.randomUUID().toString(),
            request.customerId(),
            request.amount(),
            TransactionType.valueOf(request.type()),
            request.paymentMethod(),
            request.description()
        );
        
        Transaction saved = repository.save(transaction);
        
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
    }
}
