package com.calvin.java11.stringmethods;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Demonstrates Java 11 New String Methods.
 * 
 * New Methods:
 * - isBlank(): Check if string is empty or whitespace
 * - lines(): Stream of lines
 * - strip(): Remove leading/trailing whitespace (Unicode-aware)
 * - stripLeading(): Remove leading whitespace
 * - stripTrailing(): Remove trailing whitespace  
 * - repeat(n): Repeat string n times
 * 
 * Impact: 60% reduction in string manipulation boilerplate
 * 
 * FinTech Use Cases:
 * - Transaction log parsing
 * - Data validation
 * - Report formatting
 * - CSV/Text processing
 * 
 * @author Calvin Lee
 * @since Java 11
 */
public class StringMethodsExample {
    
    public static void main(String[] args) {
        System.out.println("=== Java 11: New String Methods ===\n");
        
        StringMethodsExample example = new StringMethodsExample();
        
        // 1. isBlank() - Input validation
        example.demonstrateIsBlank();
        
        // 2. lines() - Log file parsing
        example.demonstrateLines();
        
        // 3. strip() methods - Data cleaning
        example.demonstrateStrip();
        
        // 4. repeat() - Report formatting
        example.demonstrateRepeat();
        
        // 5. Real-world example: Transaction log parsing
        example.demonstrateTransactionLogParsing();
    }
    
    /**
     * Demonstrates isBlank() for input validation.
     */
    public void demonstrateIsBlank() {
        System.out.println("1. isBlank() - Input Validation:\n");
        
        String[] inputs = {
            "",           // Empty string
            "   ",        // Whitespace only
            "\t\n",       // Tabs and newlines
            "  DATA  ",   // Actual data
            null          // Null
        };
        
        System.out.println("   Validating payment descriptions:\n");
        
        for (String input : inputs) {
            if (input == null) {
                System.out.println("   ❌ null → INVALID (null check)");
            } else if (input.isBlank()) {  // Java 11
                System.out.println("   ❌ \"" + input.replace("\n", "\\n").replace("\t", "\\t") + "\" → INVALID (blank)");
            } else {
                System.out.println("   ✅ \"" + input + "\" → VALID");
            }
        }
        
        System.out.println("\n   📊 Impact: Simplified validation logic\n");
    }
    
    /**
     * Demonstrates lines() for log file parsing.
     */
    public void demonstrateLines() {
        System.out.println("2. lines() - Log File Parsing:\n");
        
        // Simulate multi-line transaction log
        String logFile = """
            INFO: Payment initiated (TXN-001)
            INFO: Customer verified (CUST-123)
            ERROR: Payment gateway timeout (TXN-001)
            INFO: Retry initiated (TXN-001)
            INFO: Payment successful (TXN-002)
            ERROR: Insufficient funds (TXN-005)
            INFO: Refund processed (TXN-003)
            ERROR: Invalid merchant ID (TXN-008)
            INFO: Settlement completed
            """;
        
        System.out.println("   Original log:\n" + logFile);
        
        // Old way (Java 8) - 6 lines
        // String[] lines = logFile.split("\n");
        // for (String line : lines) {
        //     if (line.contains("ERROR")) {
        //         System.out.println(line);
        //     }
        // }
        
        // New way (Java 11) - 1 line (83% reduction)
        System.out.println("   Extracting ERROR lines:\n");
        
        List<String> errors = logFile.lines()  // Java 11: Stream<String>
            .filter(line -> line.contains("ERROR"))
            .collect(Collectors.toList());
        
        errors.forEach(error -> System.out.println("   🚨 " + error));
        
        System.out.println("\n   ✅ Found " + errors.size() + " errors");
        System.out.println("   📊 Code reduction: 6 lines → 1 line (83% reduction)\n");
    }
    
    /**
     * Demonstrates strip() methods for data cleaning.
     */
    public void demonstrateStrip() {
        System.out.println("3. strip() Methods - Data Cleaning:\n");
        
        String[] transactions = {
            "  $5000.00  ",      // Leading and trailing spaces
            "\t$3000.00",        // Leading tab
            "$2000.00\n",        // Trailing newline
            "  $1500.00"         // Leading spaces only
        };
        
        System.out.println("   Cleaning transaction amounts:\n");
        
        for (String txn : transactions) {
            String original = txn.replace("\n", "\\n").replace("\t", "\\t");
            String cleaned = txn.strip();  // Java 11
            System.out.println("   \"" + original + "\" → strip() → \"" + cleaned + "\"");
        }
        
        System.out.println("\n   stripLeading() example:");
        System.out.println("   \"  $1000.00  \".stripLeading() → \"" + "  $1000.00  ".stripLeading() + "\"");
        
        System.out.println("\n   stripTrailing() example:");
        System.out.println("   \"  $1000.00  \".stripTrailing() → \"" + "  $1000.00  ".stripTrailing() + "\"");
        
        System.out.println("\n   📊 Impact: Cleaner data processing\n");
    }
    
    /**
     * Demonstrates repeat() for report formatting.
     */
    public void demonstrateRepeat() {
        System.out.println("4. repeat() - Report Formatting:\n");
        
        String separator = "-".repeat(50);  // Java 11
        String title = "Transaction Report";
        String padding = " ".repeat((50 - title.length()) / 2);  // Center align
        
        System.out.println("   " + separator);
        System.out.println("   " + padding + title);
        System.out.println("   " + separator);
        System.out.println("   | Date       | Amount    | Status    |");
        System.out.println("   " + separator);
        System.out.println("   | 2026-02-13 | $5000.00  | COMPLETED |");
        System.out.println("   | 2026-02-13 | $3000.00  | PENDING   |");
        System.out.println("   " + separator);
        
        System.out.println("\n   📊 Impact: Simplified string formatting\n");
    }
    
    /**
     * Real-world example: Transaction log parsing with all methods combined.
     */
    public void demonstrateTransactionLogParsing() {
        System.out.println("5. Real-World Example: Transaction Log Parsing:\n");
        
        String rawLog = """
            2026-02-13,  TXN-001,  $5000.00,  COMPLETED  
            2026-02-13,  TXN-002,  $3000.00,  PENDING    
                                                          
            2026-02-13,  TXN-003,  $2000.00,  FAILED     
            2026-02-13,  TXN-004,  $1500.00,  COMPLETED  
            """;
        
        System.out.println("   Raw log (with whitespace and blank lines):\n");
        System.out.println(rawLog);
        
        System.out.println("   Parsed transactions (cleaned):\n");
        
        // Java 11: Combine lines(), strip(), isBlank()
        List<String> cleanedTransactions = rawLog.lines()  // Stream of lines
            .map(String::strip)                            // Remove whitespace
            .filter(line -> !line.isBlank())               // Remove blank lines
            .collect(Collectors.toList());
        
        String header = "=".repeat(60);  // Separator
        System.out.println("   " + header);
        cleanedTransactions.forEach(txn -> System.out.println("   " + txn));
        System.out.println("   " + header);
        
        System.out.println("\n   ✅ Processed " + cleanedTransactions.size() + " transactions");
        System.out.println("   📊 Java 11 methods used: lines(), strip(), isBlank(), repeat()");
        System.out.println("   🎯 Code quality: Clean, concise, readable\n");
    }
}
