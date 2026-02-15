package com.calvin.lambdas;

import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Lambda Expressions REST Controller - Spring Boot 3.2 + Java 21
 * 
 * <p>FinTech Principal Engineer's Guide to Functional REST APIs</p>
 * 
 * <h2>Key Features</h2>
 * <ul>
 *   <li><b>Virtual Threads</b> - Each request runs on a lightweight virtual thread</li>
 *   <li><b>Functional Programming</b> - Stateless, composable endpoint logic</li>
 *   <li><b>Records</b> - Immutable request/response models</li>
 *   <li><b>Pattern Matching</b> - Declarative business logic</li>
 * </ul>
 * 
 * <p><b>Performance Note:</b> With spring.threads.virtual.enabled=true, this controller
 * can handle millions of concurrent requests on a single JVM instance.</p>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 * @version 1.0.0
 * @since 2026-02-15
 */
@RestController
@RequestMapping("/api/lambda")
public class LambdaController {

    /**
     * Currency Conversion using Function&lt;T, R&gt;
     * 
     * <p><b>Endpoint:</b> POST /api/lambda/convert</p>
     * <p><b>Use Case:</b> Trading desk currency conversion</p>
     * 
     * @param request conversion request
     * @return conversion result
     */
    @PostMapping("/convert")
    public ConversionResponse convertCurrency(@RequestBody ConversionRequest request) {
        // Pure function - no side effects, referentially transparent
        Function<BigDecimal, BigDecimal> usdToEur = 
            usd -> usd.multiply(BigDecimal.valueOf(0.92)).setScale(2, RoundingMode.HALF_UP);

        Function<BigDecimal, BigDecimal> usdToGbp = 
            usd -> usd.multiply(BigDecimal.valueOf(0.79)).setScale(2, RoundingMode.HALF_UP);

        // Function composition for fees
        Function<BigDecimal, BigDecimal> addTransactionFee = 
            amount -> amount.multiply(BigDecimal.valueOf(1.02)).setScale(2, RoundingMode.HALF_UP);

        BigDecimal result = switch (request.targetCurrency().toUpperCase()) {
            case "EUR" -> usdToEur.andThen(addTransactionFee).apply(request.amount());
            case "GBP" -> usdToGbp.andThen(addTransactionFee).apply(request.amount());
            default -> request.amount();
        };

        return new ConversionResponse(
            request.amount(),
            result,
            request.targetCurrency(),
            BigDecimal.valueOf(0.02)
        );
    }

    /**
     * Customer Validation using Predicate&lt;T&gt;
     * 
     * <p><b>Endpoint:</b> POST /api/lambda/validate</p>
     * <p><b>Use Case:</b> KYC (Know Your Customer) compliance checks</p>
     * 
     * @param request customer validation request
     * @return validation result
     */
    @PostMapping("/validate")
    public ValidationResponse validateCustomer(@RequestBody CustomerRequest request) {
        // Predicate composition for complex business rules
        Predicate<Integer> isAdult = age -> age >= 18;
        Predicate<Integer> isSenior = age -> age >= 65;
        Predicate<Integer> isWorkingAge = isAdult.and(isSenior.negate());
        
        Predicate<BigDecimal> hasMinimumIncome = income -> income.compareTo(BigDecimal.valueOf(30000)) >= 0;
        Predicate<Integer> hasGoodCredit = score -> score >= 650;

        boolean ageValid = isWorkingAge.test(request.age());
        boolean incomeValid = hasMinimumIncome.test(request.annualIncome());
        boolean creditValid = hasGoodCredit.test(request.creditScore());

        boolean approved = ageValid && incomeValid && creditValid;

        List<String> reasons = new ArrayList<>();
        if (!ageValid) reasons.add("Age must be between 18 and 64");
        if (!incomeValid) reasons.add("Minimum annual income: $30,000");
        if (!creditValid) reasons.add("Minimum credit score: 650");

        return new ValidationResponse(approved, reasons);
    }

    /**
     * Batch Transaction Processing using Consumer&lt;T&gt;
     * 
     * <p><b>Endpoint:</b> POST /api/lambda/process-batch</p>
     * <p><b>Use Case:</b> End-of-day batch processing with notifications</p>
     * 
     * @param transactions list of transactions to process
     * @return processing summary
     */
    @PostMapping("/process-batch")
    public BatchProcessingResponse processBatch(@RequestBody List<TransactionRequest> transactions) {
        List<String> processedIds = new ArrayList<>();
        List<String> notifications = new ArrayList<>();

        // Consumer chaining for side-effects
        Consumer<TransactionRequest> processTransaction = 
            tx -> processedIds.add(tx.id());

        Consumer<TransactionRequest> sendNotification = 
            tx -> notifications.add("Notification sent for " + tx.id());

        Consumer<TransactionRequest> completeFlow = 
            processTransaction.andThen(sendNotification);

        // Execute the chained consumers
        transactions.forEach(completeFlow);

        return new BatchProcessingResponse(
            processedIds.size(),
            processedIds,
            notifications
        );
    }

    /**
     * Risk Calculation using BiFunction&lt;T, U, R&gt;
     * 
     * <p><b>Endpoint:</b> POST /api/lambda/calculate-risk</p>
     * <p><b>Use Case:</b> Loan application risk assessment</p>
     * 
     * @param request risk calculation request
     * @return risk assessment result
     */
    @PostMapping("/calculate-risk")
    public RiskResponse calculateRisk(@RequestBody RiskRequest request) {
        // BiFunction for two-input computation
        BiFunction<Integer, BigDecimal, String> calculateRiskRating = (creditScore, annualIncome) -> {
            BigDecimal ratio = new BigDecimal(creditScore).divide(annualIncome, 6, RoundingMode.HALF_UP);
            if (ratio.compareTo(BigDecimal.valueOf(0.01)) > 0) return "LOW";
            if (ratio.compareTo(BigDecimal.valueOf(0.005)) > 0) return "MEDIUM";
            return "HIGH";
        };

        // BiFunction for loan amount calculation
        BiFunction<Integer, BigDecimal, BigDecimal> calculateMaxLoan = (creditScore, income) -> {
            return income.multiply(BigDecimal.valueOf(creditScore / 100.0)).setScale(2, RoundingMode.HALF_UP);
        };

        String riskRating = calculateRiskRating.apply(request.creditScore(), request.annualIncome());
        BigDecimal maxLoanAmount = calculateMaxLoan.apply(request.creditScore(), request.annualIncome());

        return new RiskResponse(riskRating, maxLoanAmount);
    }

    /**
     * Generate Report using Supplier&lt;T&gt;
     * 
     * <p><b>Endpoint:</b> GET /api/lambda/generate-report</p>
     * <p><b>Use Case:</b> On-demand report generation with lazy evaluation</p>
     * 
     * @return generated report
     */
    @GetMapping("/generate-report")
    public ReportResponse generateReport() {
        // Supplier for lazy evaluation
        Supplier<String> reportIdGenerator = 
            () -> "RPT-" + System.currentTimeMillis();

        Supplier<List<String>> generateTransactions = () -> 
            List.of("TX-001", "TX-002", "TX-003", "TX-004", "TX-005");

        // The suppliers are not executed until .get() is called
        String reportId = reportIdGenerator.get();
        List<String> transactions = generateTransactions.get();

        return new ReportResponse(reportId, transactions, transactions.size());
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // Request/Response Records (Java 14+, finalized in Java 16)
    // ═══════════════════════════════════════════════════════════════════════════

    public record ConversionRequest(BigDecimal amount, String targetCurrency) {}
    
    public record ConversionResponse(
        BigDecimal originalAmount,
        BigDecimal convertedAmount,
        String targetCurrency,
        BigDecimal feePercentage
    ) {}

    public record CustomerRequest(
        int age,
        BigDecimal annualIncome,
        int creditScore
    ) {}

    public record ValidationResponse(boolean approved, List<String> reasons) {}

    public record TransactionRequest(String id, BigDecimal amount, String type) {}

    public record BatchProcessingResponse(
        int totalProcessed,
        List<String> processedIds,
        List<String> notifications
    ) {}

    public record RiskRequest(int creditScore, BigDecimal annualIncome) {}

    public record RiskResponse(String riskRating, BigDecimal maxLoanAmount) {}

    public record ReportResponse(String reportId, List<String> transactions, int count) {}
}
