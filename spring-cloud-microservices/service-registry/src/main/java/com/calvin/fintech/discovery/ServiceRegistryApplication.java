package com.calvin.fintech.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Service Registry (Eureka Server)
 * 
 * Purpose: Central registry for all microservices to register and discover each other
 * 
 * Features:
 * - Service registration: Microservices register themselves on startup
 * - Service discovery: Services find each other by name (not IP)
 * - Health monitoring: Detects failed instances
 * - Load balancing metadata: Provides instance info for load balancing
 * 
 * Why Needed:
 * - In microservices, instances are dynamic (scale up/down, restarts)
 * - Can't hardcode IP addresses
 * - Service names resolve to healthy instances automatically
 * 
 * Example:
 * - Payment Service registers as "payment-service"
 * - User Service calls "http://payment-service/api/..." (Eureka resolves)
 * 
 * Port: 8761 (default Eureka port)
 * Dashboard: http://localhost:8761
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryApplication.class, args);
        
        System.out.println("""
            
            ╔════════════════════════════════════════════════════════════════╗
            ║                   SERVICE REGISTRY STARTED                      ║
            ╠════════════════════════════════════════════════════════════════╣
            ║  Eureka Dashboard: http://localhost:8761                       ║
            ║                                                                 ║
            ║  Services will register here on startup:                       ║
            ║    - payment-service                                            ║
            ║    - user-service                                               ║
            ║    - account-service                                            ║
            ║    - api-gateway                                                ║
            ║                                                                 ║
            ║  Health checks run every 30 seconds                             ║
            ║  Failed instances removed after 90 seconds                      ║
            ╚════════════════════════════════════════════════════════════════╝
            """);
    }
}
