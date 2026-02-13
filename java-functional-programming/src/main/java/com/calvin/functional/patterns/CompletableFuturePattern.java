package com.calvin.functional.patterns;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * COMPLETABLE FUTURE PATTERN (Java 8+, Enhanced in Java 11+)
 * 
 * Think of CompletableFuture like ordering food delivery on your phone!
 * You don't wait by the door - you continue doing other things. When food arrives,
 * the app notifies you. That's async programming!
 * 
 * Real-world analogy: Like a restaurant where you give your phone number.
 * They call when your table is ready - you don't stand there waiting!
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 8 (CompletableFuture), Java 11+ (timeout/orTimeout)
 */
public class CompletableFuturePattern {

    /**
     * PATTERN 1: Simple Async Execution
     * Run tasks asynchronously without blocking
     */
    static class SimpleAsyncExample {
        
        record Transaction(String id, double amount, String status) {}
        
        // Simulate slow external API call
        static Transaction fetchTransaction(String id) {
            System.out.println("  [" + Thread.currentThread().getName() + "] Fetching " + id + "...");
            sleep(1000); // Simulate network delay
            return new Transaction(id, Math.random() * 1000, "COMPLETED");
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Simple Async Execution ===");
            System.out.println("Goal: Don't block while waiting\n");
            
            // OLD WAY: Blocking call
            System.out.println("❌ OLD WAY (Blocking):");
            long start1 = System.currentTimeMillis();
            Transaction tx1 = fetchTransaction("TX001");
            Transaction tx2 = fetchTransaction("TX002");
            long time1 = System.currentTimeMillis() - start1;
            System.out.println("  Time taken: " + time1 + "ms (sequential!)");
            
            // NEW WAY: Async execution
            System.out.println("\n✅ NEW WAY (Async):");
            long start2 = System.currentTimeMillis();
            CompletableFuture<Transaction> future1 = CompletableFuture.supplyAsync(() -> 
                fetchTransaction("TX003"));
            CompletableFuture<Transaction> future2 = CompletableFuture.supplyAsync(() -> 
                fetchTransaction("TX004"));
            
            // Wait for both to complete
            CompletableFuture.allOf(future1, future2).join();
            long time2 = System.currentTimeMillis() - start2;
            
            System.out.println("  Time taken: " + time2 + "ms (parallel!)");
            System.out.println("\n  Benefits: ~50% faster! Run tasks in parallel!");
        }
    }

    /**
     * PATTERN 2: Chaining Transformations
     * Transform results without blocking
     */
    static class ChainingExample {
        
        record Account(String id, double balance) {}
        record Transaction(String id, double amount) {}
        record Receipt(String transactionId, double finalBalance) {}
        
        static Account fetchAccount(String id) {
            System.out.println("  Step 1: Fetching account " + id);
            sleep(500);
            return new Account(id, 1000.0);
        }
        
        static Transaction processPayment(Account account, double amount) {
            System.out.println("  Step 2: Processing payment on account " + account.id);
            sleep(500);
            return new Transaction("TXN-" + System.currentTimeMillis(), amount);
        }
        
        static Receipt generateReceipt(Account account, Transaction tx) {
            System.out.println("  Step 3: Generating receipt for " + tx.id);
            sleep(300);
            return new Receipt(tx.id, account.balance - tx.amount);
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Chaining Transformations ===");
            System.out.println("Goal: Chain async operations fluently\n");
            
            // Fluent async chain
            CompletableFuture<Receipt> receiptFuture = CompletableFuture
                .supplyAsync(() -> fetchAccount("ACC001"))
                .thenApply(account -> processPayment(account, 250.0))
                .thenCombine(
                    CompletableFuture.supplyAsync(() -> fetchAccount("ACC001")),
                    (tx, account) -> generateReceipt(account, tx)
                );
            
            Receipt receipt = receiptFuture.join();
            System.out.println("\nGenerated: " + receipt.transactionId);
            System.out.println("Final balance: $" + receipt.finalBalance);
            
            System.out.println("\n  Benefits: Fluent async pipeline!");
        }
    }

    /**
     * PATTERN 3: Error Handling
     * Handle failures gracefully
     */
    static class ErrorHandlingExample {
        
        static double fetchExchangeRate(String currency) {
            System.out.println("  Fetching exchange rate for " + currency);
            sleep(500);
            if (currency.equals("XXX")) {
                throw new IllegalArgumentException("Unknown currency: " + currency);
            }
            return switch (currency) {
                case "EUR" -> 1.18;
                case "GBP" -> 1.27;
                default -> 1.0;
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Error Handling ===");
            System.out.println("Goal: Handle failures gracefully\n");
            
            // Using exceptionally()
            System.out.println("Example 1: Using exceptionally()");
            CompletableFuture<Double> rate1 = CompletableFuture
                .supplyAsync(() -> fetchExchangeRate("XXX"))
                .exceptionally(ex -> {
                    System.out.println("  ⚠️ Error caught: " + ex.getMessage());
                    return 1.0; // Default fallback
                });
            
            System.out.println("  Result: " + rate1.join());
            
            // Using handle() - always called
            System.out.println("\nExample 2: Using handle()");
            CompletableFuture<Double> rate2 = CompletableFuture
                .supplyAsync(() -> fetchExchangeRate("EUR"))
                .handle((result, ex) -> {
                    if (ex != null) {
                        System.out.println("  Error: " + ex.getMessage());
                        return 1.0;
                    }
                    System.out.println("  ✅ Success: " + result);
                    return result;
                });
            
            System.out.println("  Result: " + rate2.join());
            
            System.out.println("\n  Benefits: Robust error handling!");
        }
    }

    /**
     * PATTERN 4: Combine Multiple Futures
     * Wait for all or any to complete
     */
    static class CombiningExample {
        
        record PaymentGatewayResponse(String gateway, boolean success, long timeMs) {}
        
        static PaymentGatewayResponse callGateway(String gateway) {
            System.out.println("  Calling " + gateway + "...");
            long delay = (long) (Math.random() * 2000);
            sleep(delay);
            return new PaymentGatewayResponse(gateway, Math.random() > 0.2, delay);
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Combine Multiple Futures ===");
            System.out.println("Goal: Coordinate multiple async operations\n");
            
            // Call 3 payment gateways in parallel
            System.out.println("Calling 3 payment gateways in parallel...");
            CompletableFuture<PaymentGatewayResponse> stripe = 
                CompletableFuture.supplyAsync(() -> callGateway("Stripe"));
            CompletableFuture<PaymentGatewayResponse> paypal = 
                CompletableFuture.supplyAsync(() -> callGateway("PayPal"));
            CompletableFuture<PaymentGatewayResponse> square = 
                CompletableFuture.supplyAsync(() -> callGateway("Square"));
            
            // Strategy 1: Wait for ALL
            System.out.println("\nStrategy 1: Wait for ALL gateways");
            long start = System.currentTimeMillis();
            CompletableFuture.allOf(stripe, paypal, square).join();
            long timeAll = System.currentTimeMillis() - start;
            
            List<PaymentGatewayResponse> allResults = List.of(
                stripe.join(), paypal.join(), square.join()
            );
            allResults.forEach(r -> 
                System.out.println("  " + r.gateway + ": " + (r.success ? "✅" : "❌") + " (" + r.timeMs + "ms)"));
            System.out.println("  Total time: " + timeAll + "ms");
            
            // Strategy 2: Wait for ANY (first to complete)
            System.out.println("\nStrategy 2: Wait for ANY gateway (fastest wins)");
            CompletableFuture<PaymentGatewayResponse> stripe2 = 
                CompletableFuture.supplyAsync(() -> callGateway("Stripe"));
            CompletableFuture<PaymentGatewayResponse> paypal2 = 
                CompletableFuture.supplyAsync(() -> callGateway("PayPal"));
            
            start = System.currentTimeMillis();
            PaymentGatewayResponse fastest = CompletableFuture.anyOf(stripe2, paypal2)
                .thenApply(result -> (PaymentGatewayResponse) result)
                .join();
            long timeAny = System.currentTimeMillis() - start;
            
            System.out.println("  Fastest: " + fastest.gateway + " (" + fastest.timeMs + "ms)");
            System.out.println("  Total time: " + timeAny + "ms (didn't wait for others!)");
            
            System.out.println("\n  Benefits: Parallel execution + flexible coordination!");
        }
    }

    /**
     * PATTERN 5: Timeout Handling (Java 11+)
     * Don't wait forever - set timeouts
     */
    static class TimeoutExample {
        
        static String callSlowAPI() {
            System.out.println("  Calling slow API...");
            sleep(5000); // Takes 5 seconds
            return "SUCCESS";
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Timeout Handling (Java 11+) ===");
            System.out.println("Goal: Don't wait forever\n");
            
            // Without timeout - would wait 5 seconds!
            System.out.println("Example 1: With timeout (2 seconds)");
            CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> callSlowAPI())
                .orTimeout(2, TimeUnit.SECONDS)
                .exceptionally(ex -> {
                    if (ex instanceof TimeoutException) {
                        System.out.println("  ⚠️ Timeout! API took too long");
                        return "FALLBACK_RESULT";
                    }
                    return "ERROR";
                });
            
            try {
                String result = future.join();
                System.out.println("  Result: " + result);
            } catch (Exception e) {
                System.out.println("  Failed: " + e.getMessage());
            }
            
            // completeOnTimeout - provide default value
            System.out.println("\nExample 2: CompleteOnTimeout with default");
            CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> callSlowAPI())
                .completeOnTimeout("DEFAULT_VALUE", 2, TimeUnit.SECONDS);
            
            try {
                String result = future2.join();
                System.out.println("  Result: " + result + " (timeout fallback)");
            } catch (Exception e) {
                System.out.println("  Failed: " + e.getMessage());
            }
            
            System.out.println("\n  Benefits: Responsive apps with timeouts!");
        }
    }

    /**
     * PATTERN 6: Parallel Stream Processing
     * Process collections in parallel
     */
    static class ParallelProcessingExample {
        
        record Transaction(String id, double amount, String type) {}
        
        static boolean validateTransaction(Transaction tx) {
            sleep(100); // Simulate validation API call
            return tx.amount > 0 && tx.amount < 10000;
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Parallel Stream Processing ===");
            System.out.println("Goal: Process collections concurrently\n");
            
            List<Transaction> transactions = IntStream.range(0, 10)
                .mapToObj(i -> new Transaction("TX" + i, Math.random() * 15000, "DEBIT"))
                .toList();
            
            // Sequential processing
            System.out.println("Sequential processing:");
            long start1 = System.currentTimeMillis();
            List<Transaction> valid1 = transactions.stream()
                .filter(tx -> validateTransaction(tx))
                .toList();
            long time1 = System.currentTimeMillis() - start1;
            System.out.println("  Validated " + valid1.size() + "/" + transactions.size() + " in " + time1 + "ms");
            
            // Parallel processing with CompletableFuture
            System.out.println("\nParallel processing with CompletableFuture:");
            long start2 = System.currentTimeMillis();
            List<CompletableFuture<Transaction>> futures = transactions.stream()
                .map(tx -> CompletableFuture.supplyAsync(() -> {
                    if (validateTransaction(tx)) return tx;
                    return null;
                }))
                .toList();
            
            List<Transaction> valid2 = futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList();
            long time2 = System.currentTimeMillis() - start2;
            System.out.println("  Validated " + valid2.size() + "/" + transactions.size() + " in " + time2 + "ms");
            
            System.out.println("\n  Speed improvement: " + (100 - (time2 * 100 / time1)) + "% faster!");
            System.out.println("  Benefits: Massive speedup for I/O-bound tasks!");
        }
    }

    // Helper method
    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║        COMPLETABLE FUTURE PATTERN (Java 8+, Java 11+)         ║");
        System.out.println("║  Async programming without blocking                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        SimpleAsyncExample.demonstrate();
        ChainingExample.demonstrate();
        ErrorHandlingExample.demonstrate();
        CombiningExample.demonstrate();
        TimeoutExample.demonstrate();
        ParallelProcessingExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • supplyAsync(): Run task asynchronously                      ║");
        System.out.println("║  • thenApply(): Transform result without blocking              ║");
        System.out.println("║  • allOf(): Wait for all futures                               ║");
        System.out.println("║  • anyOf(): Wait for fastest future                            ║");
        System.out.println("║  • exceptionally()/handle(): Error handling                    ║");
        System.out.println("║  • orTimeout()/completeOnTimeout(): Timeout handling (Java 11+)║");
        System.out.println("║  • Introduced in: Java 8, Enhanced in Java 11                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
