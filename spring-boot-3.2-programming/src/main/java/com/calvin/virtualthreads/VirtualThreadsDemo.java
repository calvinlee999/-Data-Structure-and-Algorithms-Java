package com.calvin.virtualthreads;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Virtual Threads (Project Loom) in Spring Boot 3.2 + Java 21
 * 
 * <p>FinTech Principal Engineer's Guide to High-Concurrency Simple Imperative</p>
 * 
 * <h2>The Game Changer: Virtual Threads</h2>
 * <p>
 * Java 21 introduces <b>Virtual Threads (Project Loom)</b>, which are lightweight threads
 * managed by the JVM. Unlike platform threads (1MB RAM each), virtual threads are "mountains"
 * of lightweight threads that can scale to millions.
 * </p>
 * 
 * <h2>Performance Impact</h2>
 * <ul>
 *   <li><b>Platform Threads:</b> 1MB RAM each → ~10,000 threads max</li>
 *   <li><b>Virtual Threads:</b> Few KB each → Millions of threads possible</li>
 *   <li><b>Spring Boot 3.2+:</b> Simply set spring.threads.virtual.enabled=true</li>
 * </ul>
 * 
 * <h2>Strategic Benefits</h2>
 * <ul>
 *   <li><b>Infrastructure ROI:</b> 10x reduction in pods for high-traffic scenarios</li>
 *   <li><b>Developer Velocity:</b> Simple blocking code that scales like async</li>
 *   <li><b>Maintainability:</b> No reactive complexity, standard stack traces work</li>
 * </ul>
 * 
 * <h2>Proactive Actions: The "Lemons" Table</h2>
 * <table>
 *   <tr><th>Potential Risk</th><th>Impact</th><th>Mitigation</th></tr>
 *   <tr>
 *     <td>Thread Pinning</td>
 *     <td>synchronized blocks pin virtual threads to platform threads</td>
 *     <td>Replace synchronized with ReentrantLock</td>
 *   </tr>
 *   <tr>
 *     <td>Pooled Resources</td>
 *     <td>Small thread pools limit virtual thread benefits</td>
 *     <td>Use Executors.newVirtualThreadPerTaskExecutor()</td>
 *   </tr>
 * </table>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 * @version 1.0.0
 * @since 2026-02-15
 */
@Component
public class VirtualThreadsDemo implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║  Virtual Threads in Spring Boot 3.2 + Java 21            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        demonstratePlatformVsVirtualThreads();
        demonstrateVirtualThreadExecutor();
        demonstrateBlockingIOWithVirtualThreads();
    }

    /**
     * Platform Threads vs Virtual Threads - Performance Comparison
     */
    private void demonstratePlatformVsVirtualThreads() throws Exception {
        System.out.println("1️⃣  Platform Threads vs Virtual Threads");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        int taskCount = 10_000;

        // Platform Threads
        Instant startPlatform = Instant.now();
        try (var executor = Executors.newFixedThreadPool(100)) {
            IntStream.range(0, taskCount)
                .forEach(i -> executor.submit(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }));
        }
        Instant endPlatform = Instant.now();

        // Virtual Threads
        Instant startVirtual = Instant.now();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, taskCount)
                .forEach(i -> executor.submit(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }));
        }
        Instant endVirtual = Instant.now();

        System.out.println("📊 Performance Comparison (" + taskCount + " tasks):");
        System.out.println("  ├─ Platform Threads (100 pool): " + 
            Duration.between(startPlatform, endPlatform).toMillis() + "ms");
        System.out.println("  └─ Virtual Threads: " + 
            Duration.between(startVirtual, endVirtual).toMillis() + "ms");
        System.out.println("✅ Virtual threads handle massive concurrency efficiently\n");
    }

    /**
     * Virtual Thread Executor - The New Standard
     * 
     * <p><b>Best Practice:</b> Use Executors.newVirtualThreadPerTaskExecutor()</p>
     */
    private void demonstrateVirtualThreadExecutor() throws Exception {
        System.out.println("2️⃣  Virtual Thread Executor - Best Practices");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = IntStream.range(1, 6)
                .mapToObj(i -> executor.submit(() -> {
                    Thread.sleep(100);
                    return "Task-" + i + " completed on " + Thread.currentThread();
                }))
                .toList();

            System.out.println("📋 Concurrent Task Execution:");
            for (Future<String> future : futures) {
                System.out.println("  ├─ " + future.get());
            }
        }

        System.out.println("✅ Each task runs on a separate virtual thread\n");
    }

    /**
     * Blocking I/O with Virtual Threads - The Sweet Spot
     * 
     * <p><b>Use Case:</b> Database queries, REST API calls, Kafka operations</p>
     * <p>With virtual threads, blocking code scales like async code without complexity</p>
     */
    private void demonstrateBlockingIOWithVirtualThreads() {
        System.out.println("3️⃣  Blocking I/O - The Simple Imperative Paradigm");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        System.out.println("💡 With spring.threads.virtual.enabled=true:");
        System.out.println("  ├─ Each HTTP request runs on a virtual thread");
        System.out.println("  ├─ You can write simple blocking code:");
        System.out.println("      var user = userService.findById(id);");
        System.out.println("      var account = accountService.getAccount(user);");
        System.out.println("      return transactionService.processPayment(account);");
        System.out.println("  └─ This simple code now scales to millions of requests");
        System.out.println("\n✅ High-Concurrency Simple Imperative - Best of Both Worlds\n");
    }
}

/**
 * Virtual Threads REST Controller
 * 
 * <p>With spring.threads.virtual.enabled=true, each request automatically runs on a virtual thread</p>
 */
@RestController
@RequestMapping("/api/virtual-threads")
class VirtualThreadsController {

    /**
     * Simulate blocking I/O operation
     * 
     * <p>In the old world, this would block a platform thread (1MB RAM).</p>
     * <p>With virtual threads, this blocks a lightweight virtual thread (few KB).</p>
     */
    @GetMapping("/simulate-blocking-io")
    public Response simulateBlockingIO() throws InterruptedException {
        Instant start = Instant.now();

        // Simulate database query (blocking)
        Thread.sleep(100);
        String user = "User-" + Thread.currentThread().threadId();

        // Simulate REST API call (blocking)
        Thread.sleep(100);
        String account = "Account-" + Thread.currentThread().threadId();

        // Simulate Kafka operation (blocking)
        Thread.sleep(100);
        String transaction = "TX-" + Thread.currentThread().threadId();

        Instant end = Instant.now();

        return new Response(
            user,
            account,
            transaction,
            Duration.between(start, end).toMillis(),
            Thread.currentThread().toString()
        );
    }

    /**
     * Concurrent processing endpoint
     * 
     * <p>Each blocking operation runs on a separate virtual thread for maximum throughput</p>
     */
    @GetMapping("/concurrent-processing")
    public ConcurrentResponse concurrentProcessing() throws Exception {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> f1 = executor.submit(() -> {
                Thread.sleep(100);
                return "Database query completed";
            });

            Future<String> f2 = executor.submit(() -> {
                Thread.sleep(100);
                return "API call completed";
            });

            Future<String> f3 = executor.submit(() -> {
                Thread.sleep(100);
                return "Kafka message sent";
            });

            return new ConcurrentResponse(List.of(f1.get(), f2.get(), f3.get()));
        }
    }

    public record Response(String user, String account, String transaction, long durationMs, String thread) {}
    public record ConcurrentResponse(List<String> results) {}
}
