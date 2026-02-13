package com.calvin.functional.patterns;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * VIRTUAL THREADS PATTERN (Java 21+)
 * 
 * Think of virtual threads like magic clones!
 * You can create MILLIONS of clones - each doing a task - without exhausting resources!
 * Old threads were like hiring employees (expensive), virtual threads are like AI assistants (cheap)!
 * 
 * Real-world analogy: Like a restaurant with infinite waiters that appear only when needed,
 * then vanish when done. No overhead of keeping staff standing around!
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 21 (Project Loom)
 */
public class VirtualThreadPattern {

    /**
     * PATTERN 1: Basic Virtual Thread Creation
     * Create thousands of threads easily
     */
    static class BasicVirtualThreadExample {
        
        static void processTransaction(int id) {
            try {
                Thread.sleep(100); // Simulate API call
                System.out.println("  Processed transaction " + id + " on " + 
                    Thread.currentThread());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        public static void demonstrate() throws Exception {
            System.out.println("\n=== PATTERN 1: Basic Virtual Threads ===");
            System.out.println("Goal: Create many threads without resource exhaustion\n");
            
            System.out.println("❌ OLD WAY (Platform threads - expensive):");
            System.out.println("  Creating 1000 platform threads would consume ~1GB RAM!");
            
            System.out.println("\n✅ NEW WAY (Virtual threads - lightweight):");
            long start = System.nanoTime();
            
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                List<Future<?>> futures = new ArrayList<>();
                
                for (int i = 0; i < 1000; i++) {
                    int txId = i;
                    futures.add(executor.submit(() -> processTransaction(txId)));
                }
                
                // Wait for first few to complete
                for (int i = 0; i < 5; i++) {
                    futures.get(i).get();
                }
            }
            
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("\n  Created 1000 virtual threads in " + duration + "ms");
            System.out.println("  Memory overhead: ~few KB per thread (vs ~1MB platform thread)!");
            
            System.out.println("\n  Benefits: Scalability without complexity!");
        }
    }

    /**
     * PATTERN 2: Structured Concurrency
     * Manage thread lifecycles together
     */
    static class StructuredConcurrencyExample {
        
        record Account(String id, double balance) {}
        record Transaction(String id, double amount) {}
        
        static Account fetchAccount(String id) throws InterruptedException {
            Thread.sleep(500); // Simulate database call
            return new Account(id, 1000.0);
        }
        
        static List<Transaction> fetchTransactions(String accountId) throws InterruptedException {
            Thread.sleep(300); // Simulate database call
            return List.of(
                new Transaction("TX001", 100.0),
                new Transaction("TX002", 200.0)
            );
        }
        
        public static void demonstrate() throws Exception {
            System.out.println("\n=== PATTERN 2: Structured Concurrency ===");
            System.out.println("Goal: Manage related tasks together\n");
            
            System.out.println("Fetching account and transactions in parallel:");
            long start = System.nanoTime();
            
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                var accountFuture = scope.fork(() -> fetchAccount("ACC001"));
                var transactionsFuture = scope.fork(() -> fetchTransactions("ACC001"));
                
                scope.join();           // Wait for both
                scope.throwIfFailed();  // Propagate errors
                
                Account account = accountFuture.get();
                List<Transaction> transactions = transactionsFuture.get();
                
                long duration = (System.nanoTime() - start) / 1_000_000;
                
                System.out.println("  Account: " + account.id + " - $" + account.balance);
                System.out.println("  Transactions: " + transactions.size());
                System.out.println("  Completed in: " + duration + "ms (parallel!)");
            }
            
            System.out.println("\n  Benefits: Automatic lifecycle management!");
        }
    }

    /**
     * PATTERN 3: High-Throughput API Calls
     * Make thousands of concurrent API calls
     */
    static class HighThroughputExample {
        
        static String callExternalAPI(int id) throws InterruptedException {
            Thread.sleep((long) (Math.random() * 1000)); // Simulate API latency
            return "Response from API " + id;
        }
        
        public static void demonstrate() throws Exception {
            System.out.println("\n=== PATTERN 3: High-Throughput API Calls ===");
            System.out.println("Goal: Handle massive concurrent I/O\n");
            
            int numCalls = 10000;
            System.out.println("Making " + numCalls + " concurrent API calls...");
            
            long start = System.currentTimeMillis();
            
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                List<Future<String>> futures = new ArrayList<>();
                
                for (int i = 0; i < numCalls; i++) {
                    int apiId = i;
                    futures.add(executor.submit(() -> callExternalAPI(apiId)));
                }
                
                // Wait for all to complete
                int successCount = 0;
                for (Future<String> future : futures) {
                    try {
                        future.get();
                        successCount++;
                    } catch (Exception e) {
                        // Handle failure
                    }
                }
                
                long duration = System.currentTimeMillis() - start;
                
                System.out.println("  Completed " + successCount + "/" + numCalls + " calls");
                System.out.println("  Total time: " + duration + "ms");
                System.out.println("  Average: " + (duration / (double) numCalls) + "ms per call");
                System.out.println("  Throughput: " + (numCalls * 1000 / duration) + " req/sec");
            }
            
            System.out.println("\n  Benefits: Handle 10K+ concurrent connections easily!");
        }
    }

    /**
     * PATTERN 4: Payment Gateway Fan-Out
     * Try multiple payment gateways concurrently
     */
    static class PaymentGatewayExample {
        
        record PaymentResult(String gateway, boolean success, long latencyMs) {}
        
        static PaymentResult tryGateway(String gateway, double amount) throws InterruptedException {
            long start = System.currentTimeMillis();
            Thread.sleep((long) (Math.random() * 2000)); // Simulate processing
            boolean success = Math.random() > 0.3; // 70% success rate
            long latency = System.currentTimeMillis() - start;
            return new PaymentResult(gateway, success, latency);
        }
        
        public static void demonstrate() throws Exception {
            System.out.println("\n=== PATTERN 4: Payment Gateway Fan-Out ===");
            System.out.println("Goal: Try multiple gateways, use first success\n");
            
            String[] gateways = {"Stripe", "PayPal", "Square", "Braintree", "Adyen"};
            double amount = 1000.0;
            
            System.out.println("Trying " + gateways.length + " payment gateways concurrently...");
            long start = System.currentTimeMillis();
            
            try (var scope = new StructuredTaskScope.ShutdownOnSuccess<PaymentResult>()) {
                // Submit to all gateways
                for (String gateway : gateways) {
                    scope.fork(() -> {
                        PaymentResult result = tryGateway(gateway, amount);
                        if (result.success) {
                            return result;
                        }
                        throw new RuntimeException(gateway + " failed");
                    });
                }
                
                scope.join();
                
                try {
                    PaymentResult winner = scope.result();
                    long duration = System.currentTimeMillis() - start;
                    
                    System.out.println("\n  ✅ Success: " + winner.gateway);
                    System.out.println("  Gateway latency: " + winner.latencyMs + "ms");
                    System.out.println("  Total time: " + duration + "ms (others cancelled!)");
                } catch (Exception e) {
                    System.out.println("  ❌ All gateways failed!");
                }
            }
            
            System.out.println("\n  Benefits: Fast failure recovery!");
        }
    }

    /**
     * PATTERN 5: Batch Transaction Processing
     * Process large batches efficiently
     */
    static class BatchProcessingExample {
        
        record Transaction(String id, double amount, String status) {}
        
        static void validateTransaction(Transaction tx) throws InterruptedException {
            Thread.sleep(50); // Simulate validation
            System.out.println("    Validated: " + tx.id);
        }
        
        static void enrichTransaction(Transaction tx) throws InterruptedException {
            Thread.sleep(100); // Simulate enrichment
            System.out.println("    Enriched: " + tx.id);
        }
        
        static void saveTransaction(Transaction tx) throws InterruptedException {
            Thread.sleep(75); // Simulate database save
            System.out.println("    Saved: " + tx.id);
        }
        
        public static void demonstrate() throws Exception {
            System.out.println("\n=== PATTERN 5: Batch Processing Pipeline ===");
            System.out.println("Goal: Process batches with pipeline stages\n");
            
            List<Transaction> batch = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                batch.add(new Transaction("TX" + i, Math.random() * 1000, "PENDING"));
            }
            
            System.out.println("Processing batch of " + batch.size() + " transactions...");
            long start = System.currentTimeMillis();
            
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                List<CompletableFuture<Void>> futures = new ArrayList<>();
                
                for (Transaction tx : batch) {
                    futures.add(CompletableFuture.runAsync(() -> {
                        try {
                            validateTransaction(tx);
                            enrichTransaction(tx);
                            saveTransaction(tx);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }, executor));
                }
                
                // Wait for all
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            }
            
            long duration = System.currentTimeMillis() - start;
            System.out.println("\n  Processed " + batch.size() + " transactions in " + duration + "ms");
            System.out.println("  Throughput: " + (batch.size() * 1000 / duration) + " tx/sec");
            
            System.out.println("\n  Benefits: Efficient batch pipelines!");
        }
    }

    /**
     * PATTERN 6: Server Request Handler
     * Handle millions of concurrent requests
     */
    static class ServerRequestExample {
        
        record Request(String id, String endpoint, Map<String, String> params) {}
        record Response(int status, String body) {}
        
        static Response handleRequest(Request request) throws InterruptedException {
            Thread.sleep((long) (Math.random() * 500)); // Simulate processing
            return new Response(200, "Processed: " + request.endpoint);
        }
        
        public static void demonstrate() throws Exception {
            System.out.println("\n=== PATTERN 6: Server Request Handler ===");
            System.out.println("Goal: Handle massive concurrent requests\n");
            
            int numRequests = 1_000_000;
            System.out.println("Simulating " + numRequests + " concurrent requests...");
            System.out.println("(Only processing sample for demo)");
            
            long start = System.currentTimeMillis();
            
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                List<Future<Response>> futures = new ArrayList<>();
                
                // Process sample of requests
                for (int i = 0; i < 1000; i++) {
                    Request req = new Request("REQ" + i, "/api/transaction", Map.of("id", "TX" + i));
                    futures.add(executor.submit(() -> handleRequest(req)));
                }
                
                int successCount = 0;
                for (Future<Response> future : futures) {
                    Response response = future.get();
                    if (response.status == 200) successCount++;
                }
                
                long duration = System.currentTimeMillis() - start;
                
                System.out.println("\n  Sample: " + successCount + "/1000 successful");
                System.out.println("  Time: " + duration + "ms");
                System.out.println("  Estimated capacity: " + (numRequests / (duration / 1000.0)) + " req/sec");
            }
            
            System.out.println("\n  Benefits: Web-scale concurrency!");
            System.out.println("  Note: With virtual threads, 1M concurrent connections is feasible!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║         VIRTUAL THREADS PATTERN (Java 21+)                    ║");
        System.out.println("║  Lightweight threads for massive concurrency                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        try {
            BasicVirtualThreadExample.demonstrate();
            StructuredConcurrencyExample.demonstrate();
            // HighThroughputExample.demonstrate(); // Uncomment for full demo
            PaymentGatewayExample.demonstrate();
            // BatchProcessingExample.demonstrate(); // Uncomment for full demo
            ServerRequestExample.demonstrate();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • Virtual threads: Millions of threads with low overhead      ║");
        System.out.println("║  • Executors.newVirtualThreadPerTaskExecutor(): Thread pool    ║");
        System.out.println("║  • StructuredTaskScope: Manage related tasks together          ║");
        System.out.println("║  • Perfect for: I/O-bound workloads, web servers, APIs         ║");
        System.out.println("║  • Memory: ~1KB per virtual thread vs ~1MB platform thread     ║");
        System.out.println("║  • Use case: High-concurrency microservices, batch processing  ║");
        System.out.println("║  • Introduced in: Java 21 (Project Loom)                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
