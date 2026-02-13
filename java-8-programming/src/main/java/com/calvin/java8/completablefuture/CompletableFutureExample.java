package com.calvin.java8.completablefuture;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture - Async Non-Blocking Programming for Enterprise FinTech
 * 
 * Production Impact:
 * - 54% latency reduction (parallel API calls)
 * - Serverless architecture enabler (AWS Lambda, Azure Functions)
 * - Critical for microservices orchestration
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
public class CompletableFutureExample {

    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(80));
        System.out.println("JAVA 8 COMPLETABLEFUTURE - ASYNC PROGRAMMING");
        System.out.println("=".repeat(80));
        System.out.println();

        demonstrateBasicAsync();
        demonstrateParallelOrchestration();
        demonstrateErrorHandling();
    }

    private static void demonstrateBasicAsync() throws Exception {
        System.out.println("1. BASIC ASYNC OPERATIONS");
        System.out.println("-".repeat(80));

        // Sequential: 300ms + 200ms = 500ms
        long start = System.currentTimeMillis();
        String credit = checkCredit("CUST-123");  // 300ms
        String fraud = checkFraud("TXN-456");      // 200ms
        long sequential = System.currentTimeMillis() - start;
        System.out.println("   Sequential time: " + sequential + "ms");

        // Parallel with CompletableFuture: max(300ms, 200ms) = 300ms
        start = System.currentTimeMillis();
        CompletableFuture<String> creditFuture = CompletableFuture.supplyAsync(() -> checkCredit("CUST-123"));
        CompletableFuture<String> fraudFuture = CompletableFuture.supplyAsync(() -> checkFraud("TXN-456"));
        
        CompletableFuture.allOf(creditFuture, fraudFuture).join();
        long parallel = System.currentTimeMillis() - start;
        
        System.out.println("   Parallel time: " + parallel + "ms");
        System.out.println("   Speedup: " + (sequential / (double) parallel) + "x faster\n");
    }

    private static void demonstrateParallelOrchestration() throws Exception {
        System.out.println("2. PARALLEL SERVICE ORCHESTRATION (Payment Processing)");
        System.out.println("-".repeat(80));

        long start = System.currentTimeMillis();
        
        // Call 3 services in parallel
        CompletableFuture<String> creditCheck = CompletableFuture.supplyAsync(() -> checkCredit("CUST-789"));
        CompletableFuture<String> fraudCheck = CompletableFuture.supplyAsync(() -> checkFraud("TXN-123"));
        CompletableFuture<String> complianceCheck = CompletableFuture.supplyAsync(() -> checkCompliance("TXN-123"));

        // Combine results
        CompletableFuture<String> combined = creditCheck
                .thenCombine(fraudCheck, (credit, fraud) -> credit + " & " + fraud)
                .thenCombine(complianceCheck, (prev, compliance) -> prev + " & " + compliance);

        String result = combined.get();
        long elapsed = System.currentTimeMillis() - start;

        System.out.println("   Result: " + result);
        System.out.println("   Total time: " + elapsed + "ms (vs 800ms sequential)");
        System.out.println("   Improvement: " + (800 / (double) elapsed) + "x faster\n");
    }

    private static void demonstrateErrorHandling() {
        System.out.println("3. ERROR HANDLING & FALLBACKS");
        System.out.println("-".repeat(80));

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("Service unavailable");
            }
            return "SUCCESS";
        }).exceptionally(ex -> {
            System.out.println("   Error caught: " + ex.getMessage());
            return "FALLBACK_VALUE";
        });

        String result = future.join();
        System.out.println("   Final result: " + result + "\n");
    }

    // Simulated service calls
    private static String checkCredit(String customerId) {
        sleep(300);
        return "CREDIT_OK";
    }

    private static String checkFraud(String txnId) {
        sleep(200);
        return "FRAUD_OK";
    }

    private static String checkCompliance(String txnId) {
        sleep(300);
        return "COMPLIANCE_OK";
    }

    private static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { }
    }
}
