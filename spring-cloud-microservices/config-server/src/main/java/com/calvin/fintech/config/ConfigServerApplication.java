package com.calvin.fintech.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Configuration Server
 * 
 * Purpose: Centralized configuration management for all microservices
 * 
 * Features:
 * - Centralized config: All service configs in one Git repository
 * - Environment-specific: dev, staging, prod configs
 * - Dynamic refresh: Update configs without redeployment
 * - Secrets management: Integration with Vault
 * - Audit trail: Git history tracks all config changes
 * 
 * Why Needed:
 * - Traditional: Each service has application.yml (scattered)
 * - Config Server: Single source of truth (Git repo)
 * - Change timeout? Update Git, refresh services (no redeploy!)
 * 
 * Repository Structure:
 * config-repo/
 *   ├── application.yml             # Shared config (all services)
 *   ├── payment-service.yml         # Payment service default
 *   ├── payment-service-dev.yml     # Payment service dev override
 *   ├── payment-service-prod.yml    # Payment service prod override
 *   └── ... (other services)
 * 
 * Port: 8888 (default Config Server port)
 * Config Endpoint: http://localhost:8888/{service}/{profile}
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
        
        System.out.println("""
            
            ╔════════════════════════════════════════════════════════════════╗
            ║                   CONFIG SERVER STARTED                         ║
            ╠════════════════════════════════════════════════════════════════╣
            ║  Config Endpoints:                                              ║
            ║    http://localhost:8888/payment-service/default               ║
            ║    http://localhost:8888/payment-service/dev                   ║
            ║    http://localhost:8888/payment-service/prod                  ║
            ║                                                                 ║
            ║  Services fetch config on startup from this server             ║
            ║  Refresh endpoint: POST /actuator/refresh                      ║
            ╚════════════════════════════════════════════════════════════════╝
            """);
    }
}
