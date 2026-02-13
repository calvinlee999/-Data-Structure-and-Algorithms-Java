package com.calvin.functional.patterns;

/**
 * PATTERN MATCHING SWITCH PATTERN (Java 21)
 * 
 * Think of pattern matching switch like a smart sorting machine!
 * Old way: Check type → cast → use. New way: Check and extract in one step!
 * 
 * Real-world analogy: Like sorting mail - instead of "Is this a package? Yes. Open it. Get contents",
 * you do "If package(contents), use contents" - check and extract together!
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 21 (Pattern Matching for switch - Standard)
 */
public class PatternMatchingSwitchPattern {

    /**
     * PATTERN 1: Type Pattern Matching
     * Match and extract types in switch
     */
    static class TypePatternExample {
        
        record Transaction(String id, double amount) {}
        record Refund(String id, String originalTxId, double amount) {}
        record Transfer(String id, String fromAccount, String toAccount, double amount) {}
        
        static String processPayment(Object payment) {
            return switch (payment) {
                case Transaction tx -> 
                    "💳 Transaction " + tx.id + ": $" + tx.amount;
                case Refund ref -> 
                    "↩️ Refund " + ref.id + " for " + ref.originalTxId + ": $" + ref.amount;
                case Transfer transfer -> 
                    "🔄 Transfer " + transfer.id + ": " + transfer.fromAccount + 
                    " → " + transfer.toAccount + " ($" + transfer.amount + ")";
                case null -> 
                    "⚠️ Null payment";
                default -> 
                    "❓ Unknown payment type: " + payment.getClass().getSimpleName();
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Type Pattern Matching ===");
            System.out.println("Goal: Match and extract types\n");
            
            System.out.println("❌ OLD WAY:");
            System.out.println("  if (obj instanceof Transaction) {");
            System.out.println("      Transaction tx = (Transaction) obj;");
            System.out.println("      // use tx");
            System.out.println("  }");
            
            System.out.println("\n✅ NEW WAY:");
            System.out.println("  case Transaction tx -> // use tx directly");
            
            Object[] payments = {
                new Transaction("TX001", 1000.0),
                new Refund("REF001", "TX001", 500.0),
                new Transfer("TRF001", "ACC001", "ACC002", 750.0),
                null,
                "InvalidData"
            };
            
            System.out.println("\nProcessing payments:");
            for (Object payment : payments) {
                System.out.println("  " + processPayment(payment));
            }
            
            System.out.println("\n  Benefits: Type-safe extraction!");
        }
    }

    /**
     * PATTERN 2: Guard Clauses
     * Add conditions to patterns
     */
    static class GuardClauseExample {
        
        record Account(String id, double balance, String tier) {}
        
        static String classifyAccount(Account account) {
            return switch (account) {
                case Account a when a.balance < 0 -> 
                    "🚨 Overdrawn: " + a.id + " ($" + a.balance + ")";
                case Account a when a.balance < 1000 -> 
                    "💰 Basic: " + a.id + " ($" + a.balance + ")";
                case Account a when a.balance < 10000 -> 
                    "💎 Silver: " + a.id + " ($" + a.balance + ")";
                case Account a when a.balance < 100000 -> 
                    "🥇 Gold: " + a.id + " ($" + a.balance + ")";
                case Account a -> 
                    "👑 Platinum: " + a.id + " ($" + a.balance + ")";
            };
        }
        
        static double getInterestRate(Account account) {
            return switch (account) {
                case Account a when a.tier.equals("PLATINUM") && a.balance > 100000 -> 0.05;
                case Account a when a.tier.equals("GOLD") && a.balance > 50000 -> 0.04;
                case Account a when a.tier.equals("SILVER") -> 0.03;
                case Account a when a.balance > 0 -> 0.02;
                default -> 0.0;
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Guard Clauses ===");
            System.out.println("Goal: Add conditions to patterns\n");
            
            Account[] accounts = {
                new Account("ACC001", -500.0, "BASIC"),
                new Account("ACC002", 500.0, "BASIC"),
                new Account("ACC003", 5000.0, "SILVER"),
                new Account("ACC004", 50000.0, "GOLD"),
                new Account("ACC005", 500000.0, "PLATINUM")
            };
            
            System.out.println("Account classification:");
            for (Account account : accounts) {
                double rate = getInterestRate(account);
                System.out.println("  " + classifyAccount(account) + 
                    " | Interest: " + (rate * 100) + "%");
            }
            
            System.out.println("\n  Benefits: Conditional pattern matching!");
        }
    }

    /**
     * PATTERN 3: Sealed Type Exhaustiveness
     * Handle all cases exhaustively with sealed types
     */
    static class SealedExhaustivenessExample {
        
        sealed interface PaymentStatus permits Pending, Processing, Completed, Failed {}
        record Pending(long queuePosition) implements PaymentStatus {}
        record Processing(String processorId, int progress) implements PaymentStatus {}
        record Completed(String confirmationCode, long timestamp) implements PaymentStatus {}
        record Failed(String errorCode, String message) implements PaymentStatus {}
        
        static String formatStatus(PaymentStatus status) {
            // No default needed - compiler checks all cases!
            return switch (status) {
                case Pending p -> 
                    "⏳ Pending (position: " + p.queuePosition + ")";
                case Processing pr -> 
                    "⚙️ Processing by " + pr.processorId + " (" + pr.progress + "%)";
                case Completed c -> 
                    "✅ Completed: " + c.confirmationCode;
                case Failed f -> 
                    "❌ Failed: " + f.errorCode + " - " + f.message;
            };
        }
        
        static boolean canRetry(PaymentStatus status) {
            return switch (status) {
                case Failed f when !f.errorCode.equals("FRAUD") -> true;
                case Pending p, Processing pr -> false;  // Can't retry in progress
                case Completed c, Failed f -> false;
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Sealed Type Exhaustiveness ===");
            System.out.println("Goal: Compiler-verified case coverage\n");
            
            PaymentStatus[] statuses = {
                new Pending(5),
                new Processing("PROC-001", 75),
                new Completed("CONF-12345", System.currentTimeMillis()),
                new Failed("INSUFFICIENT_FUNDS", "Available: $50, Required: $100"),
                new Failed("FRAUD", "Suspicious activity detected")
            };
            
            System.out.println("Payment status handling:");
            for (PaymentStatus status : statuses) {
                String formatted = formatStatus(status);
                boolean retry = canRetry(status);
                System.out.println("  " + formatted);
                if (retry) System.out.println("    → Can retry");
            }
            
            System.out.println("\n  Benefits: Compiler ensures no cases missed!");
        }
    }

    /**
     * PATTERN 4: Null Handling
     * Handle null explicitly in switch
     */
    static class NullHandlingExample {
        
        record Product(String id, String name, double price) {}
        
        static String describeProduct(Product product) {
            return switch (product) {
                case null -> "⚠️ No product";
                case Product p when p.price == 0 -> "🎁 Free: " + p.name;
                case Product p when p.price < 10 -> "💵 Budget: " + p.name + " ($" + p.price + ")";
                case Product p when p.price < 100 -> "💰 Standard: " + p.name + " ($" + p.price + ")";
                case Product p -> "💎 Premium: " + p.name + " ($" + p.price + ")";
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Null Handling ===");
            System.out.println("Goal: Explicit null handling\n");
            
            Product[] products = {
                new Product("PROD001", "Coffee", 5.0),
                new Product("PROD002", "Laptop", 1200.0),
                new Product("PROD003", "Free Sample", 0.0),
                null
            };
            
            System.out.println("Product descriptions:");
            for (Product product : products) {
                System.out.println("  " + describeProduct(product));
            }
            
            System.out.println("\n  Benefits: Null-safe switch statements!");
        }
    }

    /**
     * PATTERN 5: Complex Conditional Logic
     * Replace complex if-else chains
     */
    static class ComplexLogicExample {
        
        record LoanApplication(String id, double amount, int creditScore, double income) {}
        
        static String evaluateLoan(LoanApplication app) {
            return switch (app) {
                case LoanApplication a when a.amount > 1_000_000 -> 
                    "❌ Rejected: Amount exceeds limit";
                    
                case LoanApplication a when a.creditScore < 600 -> 
                    "❌ Rejected: Low credit score (" + a.creditScore + ")";
                    
                case LoanApplication a when a.amount > (a.income * 5) -> 
                    "❌ Rejected: DTI ratio too high";
                    
                case LoanApplication a when a.creditScore >= 750 && a.income > 100000 -> 
                    "✅ Approved: Premium rate (3.5%)";
                    
                case LoanApplication a when a.creditScore >= 700 -> 
                    "✅ Approved: Standard rate (4.5%)";
                    
                case LoanApplication a when a.creditScore >= 650 -> 
                    "⚠️ Conditionally approved: Higher rate (6.0%)";
                    
                case LoanApplication a -> 
                    "📋 Manual review required";
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Complex Conditional Logic ===");
            System.out.println("Goal: Replace if-else chains\n");
            
            LoanApplication[] applications = {
                new LoanApplication("LOAN001", 200000, 780, 150000),
                new LoanApplication("LOAN002", 300000, 720, 80000),
                new LoanApplication("LOAN003", 2000000, 800, 200000),
                new LoanApplication("LOAN004", 100000, 580, 50000),
                new LoanApplication("LOAN005", 150000, 660, 60000)
            };
            
            System.out.println("Loan evaluations:");
            for (LoanApplication app : applications) {
                System.out.println("  " + app.id + ": " + evaluateLoan(app));
            }
            
            System.out.println("\n  Benefits: Readable complex logic!");
        }
    }

    /**
     * PATTERN 6: Primitive and String Patterns
     * Match on primitives and strings
     */
    static class PrimitivePatternExample {
        
        static String categorizeTransactionAmount(Object amount) {
            return switch (amount) {
                case Integer i when i < 0 -> "❌ Invalid amount";
                case Integer i when i < 10 -> "💵 Micro-transaction: $" + i;
                case Integer i when i < 100 -> "💰 Small: $" + i;
                case Integer i when i < 1000 -> "💎 Medium: $" + i;
                case Integer i -> "🏦 Large: $" + i;
                case Double d when d < 0 -> "❌ Invalid amount";
                case Double d -> "💲 Decimal: $" + String.format("%.2f", d);
                case String s -> "📝 Amount string: " + s;
                case null -> "⚠️ No amount";
                default -> "❓ Unknown type";
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Primitive and String Patterns ===");
            System.out.println("Goal: Match on primitive types\n");
            
            Object[] amounts = {5, 50, 500, 5000, 99.99, -10, "1000", null};
            
            System.out.println("Transaction categorization:");
            for (Object amount : amounts) {
                System.out.println("  " + categorizeTransactionAmount(amount));
            }
            
            System.out.println("\n  Benefits: Type-safe primitive handling!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║      PATTERN MATCHING SWITCH PATTERN (Java 21)                ║");
        System.out.println("║  Type-safe, exhaustive pattern matching in switch             ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        TypePatternExample.demonstrate();
        GuardClauseExample.demonstrate();
        SealedExhaustivenessExample.demonstrate();
        NullHandlingExample.demonstrate();
        ComplexLogicExample.demonstrate();
        PrimitivePatternExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • case Type var: Match and extract type                       ║");
        System.out.println("║  • when clause: Add guard conditions                           ║");
        System.out.println("║  • Null case: Explicit null handling                           ║");
        System.out.println("║  • Exhaustiveness: Compiler checks all sealed cases            ║");
        System.out.println("║  • Replaces: instanceof chains, complex if-else                ║");
        System.out.println("║  • Use case: Payment processing, status handling, validation   ║");
        System.out.println("║  • Introduced in: Java 21 (Standard feature)                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
