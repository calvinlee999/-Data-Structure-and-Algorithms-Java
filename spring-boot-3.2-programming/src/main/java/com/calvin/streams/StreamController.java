package com.calvin.streams;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Stream API REST Controller - Spring Boot 3.2 + Java 21
 * 
 * <p>FinTech Principal Engineer's Guide to Declarative Data Processing</p>
 * 
 * <h2>Real-World Spring Boot Integration</h2>
 * <ul>
 *   <li><b>JPA Repositories</b> - Database operations return Streams</li>
 *   <li><b>Service Layer</b> - Business logic using Stream pipelines</li>
 *   <li><b>REST Controllers</b> - Transform domain models to DTOs</li>
 *   <li><b>Virtual Threads</b> - Each request runs on lightweight thread</li>
 * </ul>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 * @version 1.0.0
 * @since 2026-02-15
 */
@RestController
@RequestMapping("/api/streams")
public class StreamController {

    private final TransactionService transactionService;

    public StreamController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Get daily transaction summary using Stream aggregations
     */
    @GetMapping("/daily-summary")
    public DailySummaryResponse getDailySummary() {
        List<TransactionEntity> transactions = transactionService.getAllTransactions();

        BigDecimal totalVolume = transactions.stream()
            .map(TransactionEntity::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        long purchaseCount = transactions.stream()
            .filter(tx -> "PURCHASE".equals(tx.getType()))
            .count();

        long refundCount = transactions.stream()
            .filter(tx -> "REFUND".equals(tx.getType()))
            .count();

        BigDecimal avgTransactionSize = transactions.stream()
            .map(TransactionEntity::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .divide(BigDecimal.valueOf(transactions.size()), 2, BigDecimal.ROUND_HALF_UP);

        return new DailySummaryResponse(
            transactions.size(),
            totalVolume,
            avgTransactionSize,
            purchaseCount,
            refundCount
        );
    }

    /**
     * Get transactions grouped by type using Collectors.groupingBy()
     */
    @GetMapping("/grouped-by-type")
    public Map<String, List<TransactionDTO>> getTransactionsGroupedByType() {
        return transactionService.getAllTransactions().stream()
            .collect(Collectors.groupingBy(
                TransactionEntity::getType,
                Collectors.mapping(
                    tx -> new TransactionDTO(tx.getId(), tx.getAmount(), tx.getType(), tx.getCreatedAt()),
                    Collectors.toList()
                )
            ));
    }

    /**
     * Get high-value transactions (> $1000) sorted by amount descending
     */
    @GetMapping("/high-value")
    public List<TransactionDTO> getHighValueTransactions() {
        return transactionService.getAllTransactions().stream()
            .filter(tx -> tx.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0)
            .sorted(Comparator.comparing(TransactionEntity::getAmount).reversed())
            .map(tx -> new TransactionDTO(tx.getId(), tx.getAmount(), tx.getType(), tx.getCreatedAt()))
            .toList();
    }

    /**
     * Create batch transactions and return statistics
     */
    @PostMapping("/batch")
    public BatchResponse createBatchTransactions(@RequestBody List<CreateTransactionRequest> requests) {
        List<TransactionEntity> created = requests.stream()
            .map(req -> transactionService.createTransaction(req.amount(), req.type()))
            .toList();

        BigDecimal totalAmount = created.stream()
            .map(TransactionEntity::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new BatchResponse(created.size(), totalAmount);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // DTOs (Records)
    // ═══════════════════════════════════════════════════════════════════════════

    public record DailySummaryResponse(
        int totalTransactions,
        BigDecimal totalVolume,
        BigDecimal avgTransactionSize,
        long purchaseCount,
        long refundCount
    ) {}

    public record TransactionDTO(
        Long id,
        BigDecimal amount,
        String type,
        LocalDateTime createdAt
    ) {}

    public record CreateTransactionRequest(BigDecimal amount, String type) {}

    public record BatchResponse(int created, BigDecimal totalAmount) {}
}

/**
 * JPA Entity for Transaction
 */
@Entity
@Table(name = "transactions")
class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public TransactionEntity() {
        this.createdAt = LocalDateTime.now();
    }

    public TransactionEntity(BigDecimal amount, String type) {
        this.amount = amount;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

/**
 * JPA Repository for Transaction
 */
@Repository
interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}

/**
 * Service Layer with Functional Stream Operations
 */
@Service
class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<TransactionEntity> getAllTransactions() {
        return repository.findAll();
    }

    public TransactionEntity createTransaction(BigDecimal amount, String type) {
        return repository.save(new TransactionEntity(amount, type));
    }
}
