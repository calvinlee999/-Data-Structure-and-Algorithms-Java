package com.calvin.controller;

import com.calvin.domain.*;
import com.calvin.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Transaction REST Controller - Spring Boot 3.2 + Java 21
 * 
 * <p>FinTech Principal Engineer's Production-Ready API Design</p>
 * 
 * <h2>Why Spring MVC + Virtual Threads > WebFlux for most cases</h2>
 * <table border="1">
 *   <tr>
 *     <th>Scenario</th>
 *     <th>Spring MVC + Virtual Threads</th>
 *     <th>WebFlux (Reactive)</th>
 *   </tr>
 *   <tr>
 *     <td>CRUD Operations</td>
 *     <td>✅ Simple, readable code</td>
 *     <td>❌ Complex Reactor APIs</td>
 *   </tr>
 *   <tr>
 *     <td>Database Access</td>
 *     <td>✅ Blocking JDBC (but cheap)</td>
 *     <td>⚠️ Requires R2DBC (limited drivers)</td>
 *   </tr>
 *   <tr>
 *     <td>High Concurrency</td>
 *     <td>✅ Millions of Virtual Threads</td>
 *     <td>✅ Event-loop (but complex)</td>
 *   </tr>
 *   <tr>
 *     <td>Debugging</td>
 *     <td>✅ Standard stack traces</td>
 *     <td>❌ Reactor debugging hell</td>
 *   </tr>
 *   <tr>
 *     <td>Team Velocity</td>
 *     <td>✅ Junior devs productive day 1</td>
 *     <td>❌ 6-month learning curve</td>
 *   </tr>
 * </table>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    /**
     * Create Transaction - Demonstrates Record validation
     * 
     * <p>POST /api/transactions</p>
     * <pre>
     * {
     *   "customerId": 1001,
     *   "amount": 5000.00,
     *   "type": "PAYMENT",
     *   "paymentMethod": "CREDIT_CARD",
     *   "description": "Invoice #12345"
     * }
     * </pre>
     */
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @RequestBody CreateTransactionRequest request) {
        
        // Record validation happens automatically in constructor
        TransactionResponseDTO response = service.createTransaction(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get Customer Daily Summary - Demonstrates Stream API with JPA
     * 
     * <p>GET /api/transactions/customer/{customerId}/summary</p>
     * 
     * <p>Shows: Stream-based data transformation (no loops)</p>
     */
    @GetMapping("/customer/{customerId}/summary")
    public ResponseEntity<List<TransactionSummaryDTO>> getDailySummary(
            @PathVariable Long customerId) {
        
        // Virtual Thread handles blocking DB call elegantly
        List<TransactionSummaryDTO> summary = service.getDailySummary(customerId);
        return ResponseEntity.ok(summary);
    }

    /**
     * Calculate Daily Summary - Demonstrates Functional composition
     * 
     * <p>GET /api/transactions/daily-summary?date=2026-02-15T00:00:00</p>
     * 
     * <p>Shows: Predicate composition, collectors, no internal loops</p>
     */
    @GetMapping("/daily-summary")
    public ResponseEntity<DailyTransactionSummaryDTO> calculateDailySummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        
        DailyTransactionSummaryDTO summary = service.calculateDailySummary(date);
        return ResponseEntity.ok(summary);
    }

    /**
     * Get Volume by Type - Demonstrates Collectors.groupingBy
     * 
     * <p>GET /api/transactions/volume-by-type</p>
     * 
     * <p>Response:</p>
     * <pre>
     * {
     *   "PAYMENT": 125000.00,
     *   "REFUND": 5000.00,
     *   "TRANSFER": 50000.00
     * }
     * </pre>
     */
    @GetMapping("/volume-by-type")
    public ResponseEntity<Map<String, BigDecimal>> getVolumeByType() {
        Map<String, BigDecimal> volumes = service.getVolumeByTransactionType();
        return ResponseEntity.ok(volumes);
    }

    /**
     * Batch Approve Transactions - Demonstrates Parallel Streams
     * 
     * <p>POST /api/transactions/batch-approve</p>
     * <pre>
     * {
     *   "transactionIds": ["TX001", "TX002", "TX003"]
     * }
     * </pre>
     * 
     * <p>Shows: Safe parallelism with Stream API + Virtual Threads</p>
     */
    @PostMapping("/batch-approve")
    public ResponseEntity<BatchTransactionResultDTO> batchApprove(
            @RequestBody List<String> transactionIds) {
        
        BatchTransactionResultDTO result = service.processBatchApprovals(transactionIds);
        return ResponseEntity.ok(result);
    }

    /**
     * Get Customer Dashboard - Demonstrates CompletableFuture composition
     * 
     * <p>GET /api/transactions/customer/{customerId}/dashboard</p>
     * 
     * <p>Shows: Multiple concurrent queries composed functionally</p>
     */
    @GetMapping("/customer/{customerId}/dashboard")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> getCustomerDashboard(
            @PathVariable Long customerId) {
        
        // Returns CompletableFuture - Spring MVC handles it automatically!
        return service.getCustomerDashboard(customerId)
            .thenApply(ResponseEntity::ok);
    }

    /**
     * Get First and Last Transactions - Demonstrates SequencedCollection (Java 21)
     * 
     * <p>GET /api/transactions/customer/{customerId}/bookends</p>
     * 
     * <p>Shows: getFirst()/getLast() instead of index manipulation</p>
     */
    @GetMapping("/customer/{customerId}/bookends")
    public ResponseEntity<Map<String, TransactionSummaryDTO>> getBookends(
            @PathVariable Long customerId) {
        
        Map<String, TransactionSummaryDTO> bookends = 
            service.getFirstAndLastTransactions(customerId);
        return ResponseEntity.ok(bookends);
    }

    /**
     * Health Check - Demonstrates simple endpoint for monitoring
     * 
     * <p>GET /api/transactions/health</p>
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "TransactionService",
            "java", System.getProperty("java.version"),
            "virtualThreads", Thread.currentThread().isVirtual() ? "ENABLED" : "DISABLED"
        ));
    }
}

/**
 * Payment Controller - Demonstrates Sealed Classes with Pattern Matching
 * 
 * <p>Shows how Sealed Classes provide compile-time exhaustiveness</p>
 */
@RestController
@RequestMapping("/api/payments")
class PaymentController {

    /**
     * Process Payment - Demonstrates Pattern Matching with Sealed Classes
     * 
     * <p>POST /api/payments/credit-card</p>
     * <pre>
     * {
     *   "amount": 500.00,
     *   "customerId": "CUST123",
     *   "cardNetwork": "Visa",
     *   "lastFourDigits": "4242",
     *   "expiryDate": "12/25"
     * }
     * </pre>
     */
    @PostMapping("/credit-card")
    public ResponseEntity<Map<String, String>> processCreditCard(
            @RequestBody Map<String, Object> request) {
        
        Payment payment = new CreditCardPayment(
            new BigDecimal(request.get("amount").toString()),
            request.get("customerId").toString(),
            LocalDateTime.now(),
            request.get("cardNetwork").toString(),
            request.get("lastFourDigits").toString(),
            request.get("expiryDate").toString()
        );
        
        // Pattern matching happens in Payment.process()
        String result = payment.process();
        
        return ResponseEntity.ok(Map.of(
            "message", result,
            "transactionId", "TX-" + System.currentTimeMillis(),
            "status", "SUCCESS"
        ));
    }

    /**
     * Process PayPal Payment
     * 
     * <p>POST /api/payments/paypal</p>
     */
    @PostMapping("/paypal")
    public ResponseEntity<Map<String, String>> processPayPal(
            @RequestBody Map<String, Object> request) {
        
        Payment payment = new PayPalPayment(
            new BigDecimal(request.get("amount").toString()),
            request.get("customerId").toString(),
            LocalDateTime.now(),
            request.get("email").toString(),
            "PP-" + System.currentTimeMillis()
        );
        
        String result = payment.process();
        
        return ResponseEntity.ok(Map.of(
            "message", result,
            "status", "SUCCESS"
        ));
    }

    /**
     * Process Crypto Payment
     * 
     * <p>POST /api/payments/crypto</p>
     */
    @PostMapping("/crypto")
    public ResponseEntity<Map<String, String>> processCrypto(
            @RequestBody Map<String, Object> request) {
        
        Payment payment = new CryptoPayment(
            new BigDecimal(request.get("amount").toString()),
            request.get("customerId").toString(),
            LocalDateTime.now(),
            request.get("cryptocurrency").toString(),
            request.get("walletAddress").toString(),
            "0x" + System.currentTimeMillis()
        );
        
        String result = payment.process();
        
        return ResponseEntity.ok(Map.of(
            "message", result,
            "status", "PENDING"
        ));
    }

    /**
     * Payment Result Handler - Demonstrates Pattern Matching with Sealed Results
     * 
     * <p>GET /api/payments/result/{transactionId}</p>
     */
    @GetMapping("/result/{transactionId}")
    public ResponseEntity<Map<String, String>> getPaymentResult(
            @PathVariable String transactionId) {
        
        // Simulate different payment results
        PaymentResult result = switch ((int) (Math.random() * 3)) {
            case 0 -> new PaymentSuccess(transactionId, "CONF-" + System.currentTimeMillis());
            case 1 -> new PaymentFailed(transactionId, "Insufficient funds");
            default -> new PaymentPending(transactionId, 5);
        };
        
        // Pattern matching with sealed classes (no default needed!)
        String message = result.toMessage();
        
        return ResponseEntity.ok(Map.of(
            "transactionId", transactionId,
            "message", message,
            "resultType", result.getClass().getSimpleName()
        ));
    }
}
