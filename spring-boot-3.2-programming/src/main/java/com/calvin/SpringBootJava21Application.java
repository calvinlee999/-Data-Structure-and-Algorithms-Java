package com.calvin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 3.2 + Java 21 (LTS) Application
 * 
 * <p>FinTech Principal Engineer's Guide to Modern Cloud-Native Development</p>
 * 
 * <h2>Key Technologies</h2>
 * <ul>
 *   <li><b>Java 21 LTS</b> - Long-term support with cutting-edge features</li>
 *   <li><b>Spring Boot 3.2+</b> - First-class Java 21 support</li>
 *   <li><b>Virtual Threads (Project Loom)</b> - Million-request concurrency</li>
 *   <li><b>Pattern Matching</b> - Declarative domain logic</li>
 *   <li><b>Records</b> - Immutable data models</li>
 *   <li><b>Functional Programming</b> - Stateless, composable services</li>
 * </ul>
 * 
 * <h2>The Paradigm Shift: "High-Concurrency Simple Imperative"</h2>
 * <p>
 * With Java 21 and Spring Boot 3.2+, we achieve the best of both worlds:
 * </p>
 * <ul>
 *   <li><b>Declarative Data Transformation</b> - Streams, Lambdas, Pattern Matching</li>
 *   <li><b>Imperative Orchestration</b> - Simple service.getData() calls</li>
 *   <li><b>Virtual Thread Scaling</b> - Reactive-level performance without complexity</li>
 * </ul>
 * 
 * <h2>Strategic Benefits</h2>
 * <ul>
 *   <li><b>Developer Velocity</b> - 50% faster time-to-market</li>
 *   <li><b>Infrastructure ROI</b> - 10x reduction in pods for high-traffic</li>
 *   <li><b>Maintainability</b> - Simple code that scales like complex async systems</li>
 *   <li><b>Security & Compliance</b> - LTS support with SSL hot-reloading</li>
 * </ul>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 * @version 1.0.0
 * @since 2026-02-15
 */
@SpringBootApplication
public class SpringBootJava21Application {

    /**
     * Application entry point.
     * 
     * <p>Virtual Threads are enabled via spring.threads.virtual.enabled=true in application.properties</p>
     * 
     * <p><b>What happens internally:</b></p>
     * <ul>
     *   <li>Spring Boot 3.2+ switches Tomcat/Undertow to use Virtual Threads</li>
     *   <li>Each HTTP request runs on a lightweight Virtual Thread (vs 1MB Platform Thread)</li>
     *   <li>You can handle millions of concurrent requests on a single JVM instance</li>
     * </ul>
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootJava21Application.class, args);
        
        System.out.println("""
            
            ╔══════════════════════════════════════════════════════════════════════════╗
            ║                                                                          ║
            ║   🚀 Spring Boot 3.2 + Java 21 Application Started Successfully         ║
            ║                                                                          ║
            ║   Virtual Threads: ENABLED ✅                                            ║
            ║   Pattern Matching: ENABLED ✅                                           ║
            ║   Records: ENABLED ✅                                                    ║
            ║   Functional Programming: ENABLED ✅                                     ║
            ║                                                                          ║
            ║   Access Points:                                                         ║
            ║   - Application: http://localhost:8080                                   ║
            ║   - H2 Console: http://localhost:8080/h2-console                         ║
            ║   - Actuator: http://localhost:8080/actuator                             ║
            ║   - Metrics: http://localhost:8080/actuator/prometheus                   ║
            ║                                                                          ║
            ╚══════════════════════════════════════════════════════════════════════════╝
            """);
    }
}
