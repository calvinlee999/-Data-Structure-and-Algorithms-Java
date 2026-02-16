package com.calvin.fintech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Class
 * 
 * This is the entry point of our Spring Boot application.
 * 
 * @SpringBootApplication combines three annotations:
 * - @Configuration: Declares this as a configuration class
 * - @EnableAutoConfiguration: Enables Spring Boot's auto-configuration
 * - @ComponentScan: Scans for Spring components in this package and sub-packages
 */
@SpringBootApplication
public class FintechApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FintechApplication.class, args);
        System.out.println("""
            
            ========================================
            FinTech Transaction Service Started
            ========================================
            API Base URL: http://localhost:8080/api/v1
            Swagger UI: http://localhost:8080/swagger-ui.html
            Actuator: http://localhost:8080/actuator
            ========================================
            """);
    }
}
