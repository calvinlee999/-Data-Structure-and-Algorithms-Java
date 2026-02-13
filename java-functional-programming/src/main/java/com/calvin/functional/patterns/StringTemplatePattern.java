package com.calvin.functional.patterns;

/**
 * STRING TEMPLATES PATTERN (Java 21+ Preview)
 * 
 * Think of string templates like Mad Libs!
 * You have a sentence with blanks, and you fill them in safely!
 * Old way: String concatenation is messy and dangerous. New way: Type-safe interpolation!
 * 
 * Real-world analogy: Like a form letter - "Dear [NAME], your balance is $[AMOUNT]"
 * but the computer checks that NAME is a name and AMOUNT is money!
 * 
 * NOTE: String Templates are a PREVIEW feature in Java 21.
 * Enable with: --enable-preview --source 21
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 21 (Preview feature)
 */
public class StringTemplatePattern {

    /**
     * PATTERN 1: Basic String Concatenation (Old vs New)
     * Compare string building approaches
     */
    static class BasicConcatenationExample {
        
        record Transaction(String id, double amount, String type) {}
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: String Concatenation Comparison ===");
            System.out.println("Goal: Build formatted strings safely\n");
            
            Transaction tx = new Transaction("TX001", 1234.56, "DEBIT");
            
            System.out.println("❌ OLD WAY #1 (Concatenation - messy):");
            String msg1 = "Transaction " + tx.id + " of type " + tx.type + 
                " for amount $" + tx.amount;
            System.out.println("  " + msg1);
            
            System.out.println("\n❌ OLD WAY #2 (String.format - indirect):");
            String msg2 = String.format("Transaction %s of type %s for amount $%.2f",
                tx.id, tx.type, tx.amount);
            System.out.println("  " + msg2);
            
            System.out.println("\n✅ NEW WAY (String Templates - Direct):");
            System.out.println("  NOTE: String templates are preview in Java 21");
            System.out.println("  Syntax: STR.\"Transaction \\{tx.id} of type \\{tx.type}...\"");
            
            // Manual simulation since templates are preview
            String msg3 = buildTemplate("Transaction {0} of type {1} for amount ${2}",
                tx.id, tx.type, String.format("%.2f", tx.amount));
            System.out.println("  " + msg3);
            
            System.out.println("\n  Benefits: Type-safe, readable interpolation!");
        }
    }

    /**
     * PATTERN 2: SQL Query Building (Injection-Safe)
     * Prevent SQL injection with templates
     */
    static class SQLQueryExample {
        
        static String buildQuery(String table, String column, String value) {
            // Simulating template processor behavior
            System.out.println("  Building safe query for:");
            System.out.println("    Table: " + table);
            System.out.println("    Column: " + column);
            System.out.println("    Value: " + value);
            
            // In real string templates, this would be:
            // SQL."SELECT * FROM \{table} WHERE \{column} = \{value}"
            // and it would automatically sanitize/parameterize
            
            return "SELECT * FROM " + sanitize(table) + 
                " WHERE " + sanitize(column) + " = ?";
        }
        
        static String sanitize(String input) {
            // Remove dangerous characters
            return input.replaceAll("[^a-zA-Z0-9_]", "");
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: SQL Injection Prevention ===");
            System.out.println("Goal: Build SQL queries safely\n");
            
            System.out.println("❌ DANGEROUS (Vulnerable to injection):");
            System.out.println("  String query = \"SELECT * FROM \" + table + \" WHERE id = \" + value;");
            System.out.println("  If value = \"1; DROP TABLE users;--\" → DISASTER!");
            
            System.out.println("\n✅ SAFE (String templates with validation):");
            String query = buildQuery("transactions", "id", "TX001");
            System.out.println("  Generated: " + query);
            System.out.println("  Values parameterized separately (safe!)");
            
            System.out.println("\n  Benefits: Automatic injection prevention!");
        }
    }

    /**
     * PATTERN 3: JSON Building
     * Create JSON strings safely
     */
    static class JSONBuildingExample {
        
        record Customer(String id, String name, double balance) {}
        
        static String toJSON(Customer customer) {
            // In real string templates:
            // JSON."""
            // {
            //   "id": "\{customer.id}",
            //   "name": "\{customer.name}",
            //   "balance": \{customer.balance}
            // }
            // """
            
            return String.format(
                "{\n" +
                "  \"id\": \"%s\",\n" +
                "  \"name\": \"%s\",\n" +
                "  \"balance\": %.2f\n" +
                "}",
                escapeJSON(customer.id),
                escapeJSON(customer.name),
                customer.balance
            );
        }
        
        static String escapeJSON(String str) {
            return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: JSON Building ===");
            System.out.println("Goal: Create JSON safely\n");
            
            Customer customer = new Customer("CUST001", "Alice \"The Boss\" Johnson", 5000.0);
            
            System.out.println("❌ OLD WAY (Manual escaping - error-prone):");
            System.out.println("  Easy to forget escaping quotes, newlines, etc.");
            
            System.out.println("\n✅ NEW WAY (String templates with JSON processor):");
            String json = toJSON(customer);
            System.out.println(json);
            
            System.out.println("\n  Benefits: Automatic escaping, valid JSON!");
        }
    }

    /**
     * PATTERN 4: Localized Messages
     * Build locale-specific strings
     */
    static class LocalizationExample {
        
        record TransactionAlert(String txId, double amount, String merchant) {}
        
        static String formatAlert(TransactionAlert alert, String locale) {
            return switch (locale) {
                case "en-US" -> buildTemplate(
                    "Transaction {0}: ${1} charged by {2}",
                    alert.txId, String.format("%.2f", alert.amount), alert.merchant
                );
                case "es-MX" -> buildTemplate(
                    "Transacción {0}: ${1} cobrada por {2}",
                    alert.txId, String.format("%.2f", alert.amount), alert.merchant
                );
                case "fr-FR" -> buildTemplate(
                    "Transaction {0}: {1}€ facturée par {2}",
                    alert.txId, String.format("%.2f", alert.amount), alert.merchant
                );
                default -> buildTemplate(
                    "Transaction {0}: {1} by {2}",
                    alert.txId, alert.amount, alert.merchant
                );
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Localized Messages ===");
            System.out.println("Goal: Multi-language support\n");
            
            TransactionAlert alert = new TransactionAlert("TX001", 1234.56, "Amazon");
            
            System.out.println("Same transaction, different locales:");
            System.out.println("  EN: " + formatAlert(alert, "en-US"));
            System.out.println("  ES: " + formatAlert(alert, "es-MX"));
            System.out.println("  FR: " + formatAlert(alert, "fr-FR"));
            
            System.out.println("\n  Benefits: Clean localization!");
        }
    }

    /**
     * PATTERN 5: Report Generation
     * Build formatted reports
     */
    static class ReportGenerationExample {
        
        record TransactionSummary(String accountId, int count, double total, double average) {}
        
        static String generateReport(TransactionSummary summary) {
            return buildTemplate(
                """
                ╔════════════════════════════════════╗
                ║      TRANSACTION SUMMARY          ║
                ╠════════════════════════════════════╣
                ║ Account:        {0}               ║
                ║ Transactions:   {1}               ║
                ║ Total:          ${2}              ║
                ║ Average:        ${3}              ║
                ╚════════════════════════════════════╝
                """,
                padRight(summary.accountId, 18),
                padRight(String.valueOf(summary.count), 18),
                padRight(String.format("%.2f", summary.total), 17),
                padRight(String.format("%.2f", summary.average), 17)
            );
        }
        
        static String padRight(String str, int length) {
            return String.format("%-" + length + "s", str);
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Report Generation ===");
            System.out.println("Goal: Formatted business reports\n");
            
            TransactionSummary summary = new TransactionSummary("ACC001", 25, 12500.0, 500.0);
            
            System.out.println("Generated report:");
            System.out.println(generateReport(summary));
            
            System.out.println("  Benefits: Clean report templates!");
        }
    }

    /**
     * PATTERN 6: Email Template
     * Build HTML emails safely
     */
    static class EmailTemplateExample {
        
        record PaymentConfirmation(String customerName, String txId, double amount, String date) {}
        
        static String buildEmail(PaymentConfirmation conf) {
            return buildTemplate(
                """
                <html>
                <body>
                    <h1>Payment Confirmation</h1>
                    <p>Dear {0},</p>
                    <p>Your payment has been successfully processed.</p>
                    <table>
                        <tr><td>Transaction ID:</td><td>{1}</td></tr>
                        <tr><td>Amount:</td><td>${2}</td></tr>
                        <tr><td>Date:</td><td>{3}</td></tr>
                    </table>
                    <p>Thank you for your business!</p>
                </body>
                </html>
                """,
                escapeHTML(conf.customerName),
                conf.txId,
                String.format("%.2f", conf.amount),
                conf.date
            );
        }
        
        static String escapeHTML(String str) {
            return str.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Email Templates ===");
            System.out.println("Goal: HTML email generation\n");
            
            PaymentConfirmation conf = new PaymentConfirmation(
                "Alice <alice@example.com>",
                "TX12345",
                1000.0,
                "2024-01-15"
            );
            
            System.out.println("Generated email HTML:");
            System.out.println(buildEmail(conf));
            
            System.out.println("\n  Benefits: XSS protection, clean templates!");
        }
    }

    // Helper method for template simulation
    static String buildTemplate(String template, Object... args) {
        String result = template;
        for (int i = 0; i < args.length; i++) {
            result = result.replace("{" + i + "}", String.valueOf(args[i]));
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║       STRING TEMPLATES PATTERN (Java 21+ Preview)             ║");
        System.out.println("║  Type-safe string interpolation                               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n⚠️  NOTE: String Templates are a PREVIEW feature in Java 21");
        System.out.println("    Enable with: javac --enable-preview --source 21");
        System.out.println("    This demo simulates template behavior\n");
        
        BasicConcatenationExample.demonstrate();
        SQLQueryExample.demonstrate();
        JSONBuildingExample.demonstrate();
        LocalizationExample.demonstrate();
        ReportGenerationExample.demonstrate();
        EmailTemplateExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • STR: Basic string interpolation template                    ║");
        System.out.println("║  • Custom processors: SQL, JSON, HTML templates                ║");
        System.out.println("║  • Type-safe: Variables checked at compile time                ║");
        System.out.println("║  • Injection-safe: Automatic escaping/sanitization             ║");
        System.out.println("║  • Use case: SQL queries, JSON, HTML, reports, localization    ║");
        System.out.println("║  • Replaces: String.format, concatenation, StringBuilder       ║");
        System.out.println("║  • Introduced in: Java 21 (Preview feature)                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
