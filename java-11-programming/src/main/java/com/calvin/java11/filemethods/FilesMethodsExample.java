package com.calvin.java11.filemethods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Demonstrates Java 11 New Files Methods.
 * 
 * New Methods:
 * - Files.readString(Path): Read entire file as String
 * - Files.writeString(Path, String): Write String to file
 * 
 * Impact: 75% reduction in file I/O boilerplate
 * 
 * Old Way (Java 8): 12 lines with BufferedReader/Writer chains
 * New Way (Java 11): 1 line
 * 
 * FinTech Use Cases:
 * - Configuration file management
 * - Audit log writing
 * - Report generation
 * - CSV data processing
 * 
 * @author Calvin Lee
 * @since Java 11
 */
public class FilesMethodsExample {
    
    private static final Path CONFIG_FILE = Path.of("payment-config.json");
    private static final Path AUDIT_LOG = Path.of("audit-log.txt");
    private static final Path REPORT_FILE = Path.of("daily-report.txt");
    
    public static void main(String[] args) {
        System.out.println("=== Java 11: New Files Methods ===\n");
        
        FilesMethodsExample example = new FilesMethodsExample();
        
        try {
            // 1. writeString() - Write configuration
            example.demonstrateWriteString();
            
            // 2. readString() - Read configuration  
            example.demonstrateReadString();
            
            // 3. Append to audit log
            example.demonstrateAppendToLog();
            
            // 4. Complete example: Daily report generation
            example.demonstrateDailyReport();
            
            // 5. Comparison: Java 8 vs Java 11
            example.demonstrateComparison();
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Cleanup
            example.cleanup();
        }
    }
    
    /**
     * Demonstrates Files.writeString() for writing configuration.
     */
    public void demonstrateWriteString() throws IOException {
        System.out.println("1. Files.writeString() - Write Configuration:\n");
        
        String config = """
            {
                "paymentGateway": "Stripe",
                "apiVersion": "2023-10-01",
                "timeout": 5000,
                "retryAttempts": 3,
                "enableFraudDetection": true
            }
            """;
        
        // Java 11: Write string to file (1 line)
        Files.writeString(CONFIG_FILE, config);
        
        System.out.println("   ✅ Configuration written to: " + CONFIG_FILE);
        System.out.println("   📄 Content:\n" + config);
        System.out.println("   📊 Code: 1 line (vs 12 lines in Java 8)\n");
    }
    
    /**
     * Demonstrates Files.readString() for reading configuration.
     */
    public void demonstrateReadString() throws IOException {
        System.out.println("2. Files.readString() - Read Configuration:\n");
        
        // Java 11: Read entire file as String (1 line)
        String config = Files.readString(CONFIG_FILE);
        
        System.out.println("   ✅ Configuration loaded from: " + CONFIG_FILE);
        System.out.println("   📄 Content:\n" + config);
        System.out.println("   📊 Code: 1 line (vs 12 lines in Java 8)\n");
    }
    
    /**
     * Demonstrates appending to audit log.
     */
    public void demonstrateAppendToLog() throws IOException {
        System.out.println("3. Append to Audit Log:\n");
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String auditEntry = String.format("[%s] Payment processed: TXN-001, Amount: $5000.00, Status: COMPLETED\n", timestamp);
        
        // Java 11: Append to file
        Files.writeString(AUDIT_LOG, auditEntry, 
            StandardOpenOption.CREATE, 
            StandardOpenOption.APPEND);
        
        System.out.println("   ✅ Audit entry written to: " + AUDIT_LOG);
        System.out.println("   📝 Entry: " + auditEntry);
        
        // Add more entries
        String auditEntry2 = String.format("[%s] Refund processed: TXN-002, Amount: $1500.00, Status: COMPLETED\n", timestamp);
        Files.writeString(AUDIT_LOG, auditEntry2, 
            StandardOpenOption.CREATE, 
            StandardOpenOption.APPEND);
        
        // Read entire audit log
        String fullLog = Files.readString(AUDIT_LOG);
        System.out.println("   📋 Full audit log:\n" + fullLog);
        System.out.println("   📊 Impact: Simplified logging workflow\n");
    }
    
    /**
     * Demonstrates daily report generation.
     */
    public void demonstrateDailyReport() throws IOException {
        System.out.println("4. Daily Report Generation:\n");
        
        String report = generateDailyReport();
        
        // Write report to file
        Files.writeString(REPORT_FILE, report);
        
        System.out.println("   ✅ Report generated: " + REPORT_FILE);
        System.out.println("   📄 Content:\n");
        System.out.println(report);
        System.out.println("   📊 Impact: One-liner file writing\n");
    }
    
    /**
     * Generate daily report content.
     */
    private String generateDailyReport() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String separator = "=".repeat(60);
        
        return String.format("""
            %s
            DAILY PAYMENT REPORT - %s
            %s
            
            Total Transactions: 156
            Total Amount: $1,234,567.89
            Successful: 145 (93%%)
            Failed: 11 (7%%)
            
            Payment Methods:
            - Credit Card: 89 transactions ($789,456.12)
            - Debit Card: 45 transactions ($345,678.90)
            - ACH Transfer: 22 transactions ($99,432.87)
            
            Top Merchants:
            1. Amazon - $234,567.00
            2. Walmart - $189,234.50
            3. Target - $156,789.25
            
            %s
            Report Generated: %s
            %s
            """, 
            separator, 
            date, 
            separator,
            separator,
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            separator
        );
    }
    
    /**
     * Comparison: Java 8 vs Java 11 for file I/O.
     */
    public void demonstrateComparison() throws IOException {
        System.out.println("5. Comparison: Java 8 vs Java 11:\n");
        
        System.out.println("   ❌ Java 8 (Old Way) - 12 lines:");
        System.out.println("""
               StringBuilder sb = new StringBuilder();
               try (BufferedReader reader = Files.newBufferedReader(
                       Paths.get("config.json"), StandardCharsets.UTF_8)) {
                   String line;
                   while ((line = reader.readLine()) != null) {
                       sb.append(line).append("\\n");
                   }
               }
               String config = sb.toString();
            """);
        
        System.out.println("   ✅ Java 11 (New Way) - 1 line:");
        System.out.println("       String config = Files.readString(Path.of(\"config.json\"));\n");
        
        System.out.println("   📊 Code Reduction: 12 lines → 1 line (91.7% reduction)");
        System.out.println("   🎯 Benefits:");
        System.out.println("      - Less boilerplate");
        System.out.println("      - Easier to read");
        System.out.println("      - Less error-prone");
        System.out.println("      - Auto-handles encoding (UTF-8 default)\n");
    }
    
    /**
     * Cleanup test files.
     */
    private void cleanup() {
        try {
            Files.deleteIfExists(CONFIG_FILE);
            Files.deleteIfExists(AUDIT_LOG);
            Files.deleteIfExists(REPORT_FILE);
            System.out.println("✅ Cleanup complete (test files removed)\n");
        } catch (IOException e) {
            System.err.println("Cleanup failed: " + e.getMessage());
        }
    }
}
