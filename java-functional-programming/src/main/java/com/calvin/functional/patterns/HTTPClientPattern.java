package com.calvin.functional.patterns;

import java.net.*;
import java.net.http.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

/**
 * HTTP CLIENT PATTERN (Java 11+)
 * 
 * Think of HTTP Client like ordering food delivery via app!
 * You send request → wait for response → get your food (data)!
 * Old way (HttpURLConnection): Like calling restaurant directly - complicated!
 * New way (HttpClient): Like using DoorDash - simple and modern!
 * 
 * Real-world analogy: Like sending a text message (request) and waiting for reply (response).
 * The new HTTP Client handles all the complicated phone network stuff for you!
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 11
 */
public class HTTPClientPattern {

    /**
     * PATTERN 1: Synchronous HTTP GET
     * Simple blocking HTTP request
     */
    static class SyncGetExample {
        
        static String fetchAccountBalance(String accountId) {
            try {
                HttpClient client = HttpClient.newHttpClient();
                
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.example.com/accounts/" + accountId))
                    .header("Authorization", "Bearer token123")
                    .GET()
                    .build();
                
                // Synchronous call - blocks until response
                HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
                );
                
                if (response.statusCode() == 200) {
                    return response.body();
                } else {
                    return "Error: " + response.statusCode();
                }
                
            } catch (Exception e) {
                return "Failed: " + e.getMessage();
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Synchronous HTTP GET ===");
            System.out.println("Goal: Simple blocking HTTP request\n");
            
            System.out.println("❌ OLD WAY (HttpURLConnection - verbose):");
            System.out.println("  URL url = new URL(\"...\");");
            System.out.println("  HttpURLConnection conn = (HttpURLConnection) url.openConnection();");
            System.out.println("  conn.setRequestMethod(\"GET\");");
            System.out.println("  // 20+ lines of boilerplate...");
            
            System.out.println("\n✅ NEW WAY (HttpClient - clean):");
            System.out.println("  HttpClient client = HttpClient.newHttpClient();");
            System.out.println("  HttpRequest request = HttpRequest.newBuilder()");
            System.out.println("      .uri(URI.create(url))");
            System.out.println("      .GET().build();");
            System.out.println("  HttpResponse<String> response = client.send(request, ...);");
            
            System.out.println("\n  Example: Fetching account ACC001");
            System.out.println("  Note: Using mock example (real API would connect)");
            System.out.println("  Response: { \"accountId\": \"ACC001\", \"balance\": 1000.0 }");
            
            System.out.println("\n  Benefits: Clean API, less boilerplate!");
        }
    }

    /**
     * PATTERN 2: Asynchronous HTTP with CompletableFuture
     * Non-blocking HTTP requests
     */
    static class AsyncExample {
        
        static CompletableFuture<String> fetchTransactionAsync(String txId) {
            HttpClient client = HttpClient.newHttpClient();
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.example.com/transactions/" + txId))
                .header("Content-Type", "application/json")
                .GET()
                .build();
            
            // Async call - returns immediately!
            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .exceptionally(ex -> "Error: " + ex.getMessage());
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Asynchronous HTTP ===");
            System.out.println("Goal: Non-blocking parallel requests\n");
            
            System.out.println("Making 3 concurrent API calls:");
            
            long start = System.currentTimeMillis();
            
            CompletableFuture<String> tx1 = fetchTransactionAsync("TX001");
            CompletableFuture<String> tx2 = fetchTransactionAsync("TX002");
            CompletableFuture<String> tx3 = fetchTransactionAsync("TX003");
            
            // Wait for all to complete
            CompletableFuture.allOf(tx1, tx2, tx3).join();
            
            long duration = System.currentTimeMillis() - start;
            
            System.out.println("  TX001: [Mock response data]");
            System.out.println("  TX002: [Mock response data]");
            System.out.println("  TX003: [Mock response data]");
            System.out.println("  Completed in: " + duration + "ms (parallel!)");
            
            System.out.println("\n  Benefits: Non-blocking, parallel execution!");
        }
    }

    /**
     * PATTERN 3: POST Request with JSON Body
     * Send data to API
     */
    static class PostRequestExample {
        
        record PaymentRequest(String fromAccount, String toAccount, double amount) {}
        
        static String createPayment(PaymentRequest payment) {
            try {
                String jsonBody = String.format(
                    "{\"fromAccount\":\"%s\",\"toAccount\":\"%s\",\"amount\":%.2f}",
                    payment.fromAccount, payment.toAccount, payment.amount
                );
                
                HttpClient client = HttpClient.newHttpClient();
                
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.example.com/payments"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer token123")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .timeout(Duration.ofSeconds(10))
                    .build();
                
                HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
                );
                
                return "Status: " + response.statusCode() + " | Response: " + response.body();
                
            } catch (Exception e) {
                return "Failed: " + e.getMessage();
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: POST Request with JSON ===");
            System.out.println("Goal: Send data to API\n");
            
            PaymentRequest payment = new PaymentRequest("ACC001", "ACC002", 1000.0);
            
            System.out.println("Creating payment:");
            System.out.println("  From: " + payment.fromAccount);
            System.out.println("  To: " + payment.toAccount);
            System.out.println("  Amount: $" + payment.amount);
            
            System.out.println("\nHTTP POST with JSON body:");
            System.out.println("  POST /payments");
            System.out.println("  Content-Type: application/json");
            System.out.println("  Body: {\"fromAccount\":\"ACC001\", ...}");
            
            System.out.println("\n  Mock response: Status 201 Created");
            
            System.out.println("\n  Benefits: Simple JSON posting!");
        }
    }

    /**
     * PATTERN 4: Request Timeout and Retry
     * Handle timeouts gracefully
     */
    static class TimeoutRetryExample {
        
        static String fetchWithRetry(String url, int maxRetries) {
            HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
            
            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                try {
                    System.out.println("  Attempt " + attempt + "...");
                    
                    HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(10))
                        .GET()
                        .build();
                    
                    HttpResponse<String> response = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                    );
                    
                    if (response.statusCode() == 200) {
                        return "Success: " + response.body();
                    } else if (response.statusCode() >= 500) {
                        System.out.println("    Server error, retrying...");
                        Thread.sleep(1000 * attempt); // Exponential backoff
                        continue;
                    } else {
                        return "Error: " + response.statusCode();
                    }
                    
                } catch (HttpTimeoutException e) {
                    System.out.println("    Timeout! Retrying...");
                    if (attempt == maxRetries) {
                        return "Failed after " + maxRetries + " attempts";
                    }
                } catch (Exception e) {
                    return "Error: " + e.getMessage();
                }
            }
            
            return "Failed after " + maxRetries + " attempts";
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Timeout and Retry ===");
            System.out.println("Goal: Resilient API calls\n");
            
            System.out.println("Fetching with 3 retry attempts:");
            System.out.println("  (Simulating timeout/retry logic)");
            System.out.println("  Timeout: 10 seconds");
            System.out.println("  Retry delay: Exponential backoff");
            
            System.out.println("\n  Benefits: Automatic retry with backoff!");
        }
    }

    /**
     * PATTERN 5: Multiple API Aggregation
     * Fetch from multiple APIs in parallel
     */
    static class APIAggregationExample {
        
        record AccountData(String balance, String status) {}
        record TransactionData(List<String> recent) {}
        record CreditScore(int score) {}
        record CustomerProfile(AccountData account, TransactionData transactions, CreditScore credit) {}
        
        static CompletableFuture<AccountData> fetchAccount(String id) {
            return CompletableFuture.supplyAsync(() -> 
                new AccountData("$1000.00", "ACTIVE")
            );
        }
        
        static CompletableFuture<TransactionData> fetchTransactions(String accountId) {
            return CompletableFuture.supplyAsync(() -> 
                new TransactionData(List.of("TX001", "TX002", "TX003"))
            );
        }
        
        static CompletableFuture<CreditScore> fetchCreditScore(String customerId) {
            return CompletableFuture.supplyAsync(() -> 
                new CreditScore(750)
            );
        }
        
        static CompletableFuture<CustomerProfile> getCustomerProfile(String customerId) {
            CompletableFuture<AccountData> accountFuture = fetchAccount(customerId);
            CompletableFuture<TransactionData> transactionsFuture = fetchTransactions(customerId);
            CompletableFuture<CreditScore> creditFuture = fetchCreditScore(customerId);
            
            // Combine all 3 results
            return CompletableFuture.allOf(accountFuture, transactionsFuture, creditFuture)
                .thenApply(v -> new CustomerProfile(
                    accountFuture.join(),
                    transactionsFuture.join(),
                    creditFuture.join()
                ));
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Multiple API Aggregation ===");
            System.out.println("Goal: Fetch from multiple APIs in parallel\n");
            
            System.out.println("Fetching customer profile (3 parallel API calls):");
            long start = System.currentTimeMillis();
            
            CompletableFuture<CustomerProfile> profileFuture = getCustomerProfile("CUST001");
            CustomerProfile profile = profileFuture.join();
            
            long duration = System.currentTimeMillis() - start;
            
            System.out.println("  Account: " + profile.account.balance + " (" + profile.account.status + ")");
            System.out.println("  Recent transactions: " + profile.transactions.recent.size());
            System.out.println("  Credit score: " + profile.credit.score);
            System.out.println("  Completed in: " + duration + "ms (parallel!)");
            
            System.out.println("\n  Benefits: Parallel API aggregation!");
        }
    }

    /**
     * PATTERN 6: Webhook Callback Pattern
     * Async processing with callbacks
     */
    static class WebhookExample {
        
        record WebhookPayload(String event, String transactionId, String status) {}
        
        static CompletableFuture<String> sendWebhook(String url, WebhookPayload payload) {
            String jsonBody = String.format(
                "{\"event\":\"%s\",\"transactionId\":\"%s\",\"status\":\"%s\"}",
                payload.event, payload.transactionId, payload.status
            );
            
            HttpClient client = HttpClient.newHttpClient();
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
            
            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println("    Webhook delivered: " + response.statusCode());
                    return "Delivered";
                })
                .exceptionally(ex -> {
                    System.out.println("    Webhook failed: " + ex.getMessage());
                    return "Failed";
                });
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Webhook Callbacks ===");
            System.out.println("Goal: Async notification delivery\n");
            
            WebhookPayload payload = new WebhookPayload("payment.completed", "TX001", "SUCCESS");
            
            System.out.println("Sending webhook notification:");
            System.out.println("  Event: " + payload.event);
            System.out.println("  Transaction: " + payload.transactionId);
            
            System.out.println("\n  (Webhook would be sent asynchronously)");
            System.out.println("  URL: https://merchant.com/webhooks");
            
            System.out.println("\n  Benefits: Non-blocking notifications!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║           HTTP CLIENT PATTERN (Java 11+)                      ║");
        System.out.println("║  Modern HTTP client with sync/async support                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        SyncGetExample.demonstrate();
        AsyncExample.demonstrate();
        PostRequestExample.demonstrate();
        TimeoutRetryExample.demonstrate();
        APIAggregationExample.demonstrate();
        WebhookExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • HttpClient.newHttpClient(): Create client                   ║");
        System.out.println("║  • HttpRequest.newBuilder(): Build requests                    ║");
        System.out.println("║  • send(): Synchronous (blocking)                              ║");
        System.out.println("║  • sendAsync(): Asynchronous (returns CompletableFuture)       ║");
        System.out.println("║  • Timeout support: Built-in connection and request timeouts   ║");
        System.out.println("║  • Use case: REST APIs, webhooks, microservices communication  ║");
        System.out.println("║  • Replaces: HttpURLConnection (old, verbose API)              ║");
        System.out.println("║  • Introduced in: Java 11                                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
