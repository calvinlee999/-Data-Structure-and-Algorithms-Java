package com.calvin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Application Configuration - Virtual Threads Setup
 * 
 * <p>FinTech Principal Engineer's Guide to Virtual Threads Configuration</p>
 * 
 * <h2>Virtual Threads: The Game Changer</h2>
 * <p>Before Java 21 (Platform Threads):</p>
 * <ul>
 *   <li>1 thread = 1 OS thread = ~2MB memory</li>
 *   <li>Max ~5,000 threads per server</li>
 *   <li>Thread pool exhaustion = 503 errors</li>
 *   <li>Solution: Reactive programming (complex)</li>
 * </ul>
 * 
 * <p>After Java 21 (Virtual Threads):</p>
 * <ul>
 *   <li>1 virtual thread = ~1KB memory</li>
 *   <li>Millions of threads per server</li>
 *   <li>Blocking code = no problem</li>
 *   <li>Solution: Simple imperative code (readable)</li>
 * </ul>
 * 
 * <h2>Configuration Options</h2>
 * <ol>
 *   <li><b>application.properties:</b> spring.threads.virtual.enabled=true (Global)</li>
 *   <li><b>@Bean Executor:</b> For CompletableFuture/async tasks (This file)</li>
 *   <li><b>Tomcat:</b> Auto-configured by Spring Boot 3.2+</li>
 * </ol>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
@Configuration
@EnableAsync
public class VirtualThreadConfig {

    /**
     * Virtual Thread Executor for async operations
     * 
     * <p>Usage: CompletableFuture.supplyAsync(() -> task, virtualThreadExecutor)</p>
     * 
     * <p>Benefits:</p>
     * <ul>
     *   <li>No thread pool sizing needed</li>
     *   <li>Auto-scales to millions of threads</li>
     *   <li>Blocking DB calls = cheap</li>
     * </ul>
     */
    @Bean
    public Executor virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    /**
     * Alternative: Named virtual thread executor
     * 
     * <p>For debugging - threads have meaningful names</p>
     */
    @Bean
    public Executor namedVirtualThreadExecutor() {
        return task -> Thread.startVirtualThread(() -> {
            Thread.currentThread().setName("fintech-vt-" + System.currentTimeMillis());
            task.run();
        });
    }
}
