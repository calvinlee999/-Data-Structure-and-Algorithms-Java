package com.calvin.functional.patterns;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * OPTIONAL AND STREAM ENHANCEMENTS PATTERN (Java 9-11)
 * 
 * Think of Optional like a box that might contain treasure!
 * Old way: Check if box has treasure, then open it. New way: Tell box what to do!
 * 
 * Real-world analogy: Like ordering food - you might get fries (present) or not (empty).
 * Instead of constantly asking "Do I have fries?", you say "If fries, eat them; else get chips!"
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 8 (Optional), Java 9+ (Enhanced methods)
 */
public class OptionalStreamEnhancementPattern {

    /**
     * PATTERN 1: Optional.stream() (Java 9+)
     * Convert Optional to Stream for better composition
     */
    static class OptionalStreamExample {
        
        record Account(String id, Optional<String> email, Optional<String> phone) {}
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Optional.stream() (Java 9+) ===");
            System.out.println("Goal: Convert Optional to Stream\n");
            
            List<Account> accounts = List.of(
                new Account("ACC001", Optional.of("alice@example.com"), Optional.empty()),
                new Account("ACC002", Optional.empty(), Optional.of("+1234567890")),
                new Account("ACC003", Optional.of("bob@example.com"), Optional.of("+0987654321"))
            );
            
            System.out.println("❌ OLD WAY (Nested ifs - ugly):");
            System.out.println("  List<String> emails = new ArrayList<>();");
            System.out.println("  for (Account acc : accounts) {");
            System.out.println("      if (acc.email.isPresent()) {");
            System.out.println("          emails.add(acc.email.get());");
            System.out.println("      }");
            System.out.println("  }");
            
            System.out.println("\n✅ NEW WAY (Optional.stream() - elegant):");
            List<String> emails = accounts.stream()
                .flatMap(acc -> acc.email.stream())  // Flatten Optionals!
                .toList();
            
            System.out.println("  Emails: " + emails);
            
            // Collect all contact info
            List<String> allContacts = accounts.stream()
                .flatMap(acc -> Stream.concat(acc.email.stream(), acc.phone.stream()))
                .toList();
            
            System.out.println("  All contacts: " + allContacts);
            
            System.out.println("\n  Benefits: Clean Optional flattening!");
        }
    }

    /**
     * PATTERN 2: Optional.or() (Java 9+)
     * Fallback to another Optional
     */
    static class OptionalOrExample {
        
        record Transaction(String id, Optional<String> description, Optional<String> category) {}
        
        static String getLabel(Transaction tx) {
            // Try description, fall back to category, fall back to ID
            return tx.description
                .or(() -> tx.category)
                .or(() -> Optional.of(tx.id))
                .get();
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Optional.or() (Java 9+) ===");
            System.out.println("Goal: Chain Optional fallbacks\n");
            
            Transaction[] transactions = {
                new Transaction("TX001", Optional.of("Coffee purchase"), Optional.of("FOOD")),
                new Transaction("TX002", Optional.empty(), Optional.of("TRAVEL")),
                new Transaction("TX003", Optional.empty(), Optional.empty())
            };
            
            System.out.println("❌ OLD WAY (Nested orElse - hard to read):");
            System.out.println("  String label = tx.description.orElse(tx.category.orElse(tx.id));");
            System.out.println("  Problem: Evaluates all fallbacks eagerly!");
            
            System.out.println("\n✅ NEW WAY (or() - lazy evaluation):");
            System.out.println("  tx.description.or(() -> tx.category).or(() -> Optional.of(tx.id))");
            
            System.out.println("\nTransaction labels:");
            for (Transaction tx : transactions) {
                System.out.println("  " + tx.id + ": " + getLabel(tx));
            }
            
            System.out.println("\n  Benefits: Lazy fallback chain!");
        }
    }

    /**
     * PATTERN 3: Optional.ifPresentOrElse() (Java 9+)
     * Handle both present and absent cases
     */
    static class IfPresentOrElseExample {
        
        record Payment(String id, Optional<String> confirmationCode) {}
        
        static void processPayment(Payment payment) {
            payment.confirmationCode.ifPresentOrElse(
                code -> System.out.println("  ✅ Confirmed: " + payment.id + " (code: " + code + ")"),
                () -> System.out.println("  ⏳ Pending: " + payment.id + " (no confirmation yet)")
            );
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: ifPresentOrElse() (Java 9+) ===");
            System.out.println("Goal: Handle both present and absent\n");
            
            Payment[] payments = {
                new Payment("PAY001", Optional.of("CONF-12345")),
                new Payment("PAY002", Optional.empty()),
                new Payment("PAY003", Optional.of("CONF-67890"))
            };
            
            System.out.println("❌ OLD WAY (if-else):");
            System.out.println("  if (opt.isPresent()) { ... } else { ... }");
            
            System.out.println("\n✅ NEW WAY (ifPresentOrElse):");
            System.out.println("  opt.ifPresentOrElse(value -> ..., () -> ...)");
            
            System.out.println("\nProcessing payments:");
            for (Payment payment : payments) {
                processPayment(payment);
            }
            
            System.out.println("\n  Benefits: Functional present/absent handling!");
        }
    }

    /**
     * PATTERN 4: Stream.takeWhile/dropWhile (Java 9+)
     * Already covered in PredicateStreamPattern, show additional use
     */
    static class StreamTakeDropExample {
        
        record Transaction(String id, double amount, long timestamp) {}
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Stream takeWhile/dropWhile Review ===");
            System.out.println("Goal: Efficient ordered stream slicing\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("TX001", 100.0, 1000),
                new Transaction("TX002", 200.0, 2000),
                new Transaction("TX003", 300.0, 3000),
                new Transaction("TX004", 400.0, 4000),
                new Transaction("TX005", 500.0, 5000)
            );
            
            System.out.println("Get transactions until amount > $250:");
            List<Transaction> limited = transactions.stream()
                .takeWhile(tx -> tx.amount <= 250.0)
                .toList();
            
            limited.forEach(tx -> 
                System.out.println("  " + tx.id + ": $" + tx.amount));
            
            System.out.println("\n  Benefits: Short-circuit stream processing!");
        }
    }

    /**
     * PATTERN 5: Stream.iterate with Predicate (Java 9+)
     * Generate streams with termination condition
     */
    static class StreamIterateExample {
        
        record CompoundInterest(int year, double balance) {}
        
        static List<CompoundInterest> calculateInterest(double principal, double rate, int years) {
            return Stream.iterate(
                new CompoundInterest(0, principal),
                ci -> ci.year < years,                           // Termination condition (Java 9+)
                ci -> new CompoundInterest(
                    ci.year + 1,
                    ci.balance * (1 + rate)
                )
            ).toList();
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Stream.iterate with Predicate (Java 9+) ===");
            System.out.println("Goal: Generate finite streams easily\n");
            
            System.out.println("❌ OLD WAY (Java 8 - infinite stream + limit):");
            System.out.println("  Stream.iterate(0, n -> n + 1).limit(10)");
            
            System.out.println("\n✅ NEW WAY (Java 9+ - termination condition):");
            System.out.println("  Stream.iterate(0, n -> n < 10, n -> n + 1)");
            
            List<CompoundInterest> growth = calculateInterest(1000.0, 0.05, 10);
            
            System.out.println("\nCompound interest ($1000 at 5% for 10 years):");
            growth.forEach(ci ->
                System.out.println("  Year " + ci.year + ": $" + String.format("%.2f", ci.balance)));
            
            System.out.println("\n  Benefits: Clearer termination logic!");
        }
    }

    /**
     * PATTERN 6: Stream.ofNullable (Java 9+)
     * Create stream from potentially null value
     */
    static class StreamOfNullableExample {
        
        record Customer(String id, String name, String referralCode) {}
        
        static Stream<String> getAllIds(Customer customer) {
            // Include customer ID and referral code (if present)
            return Stream.of(
                Stream.of(customer.id),
                Stream.ofNullable(customer.referralCode)  // Safe null handling!
            ).flatMap(s -> s);
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Stream.ofNullable (Java 9+) ===");
            System.out.println("Goal: Stream from nullable values\n");
            
            Customer[] customers = {
                new Customer("CUST001", "Alice", "REF123"),
                new Customer("CUST002", "Bob", null),
                new Customer("CUST003", "Charlie", "REF456")
            };
            
            System.out.println("❌ OLD WAY (Manual null check):");
            System.out.println("  if (value != null) {");
            System.out.println("      stream = Stream.of(value);");
            System.out.println("  } else {");
            System.out.println("      stream = Stream.empty();");
            System.out.println("  }");
            
            System.out.println("\n✅ NEW WAY (Stream.ofNullable):");
            System.out.println("  Stream.ofNullable(value)  // Returns empty stream if null");
            
            System.out.println("\nCustomer IDs and referrals:");
            for (Customer customer : customers) {
                List<String> ids = getAllIds(customer).toList();
                System.out.println("  " + customer.name + ": " + ids);
            }
            
            // Collect all referral codes (skipping nulls)
            List<String> allReferrals = Arrays.stream(customers)
                .flatMap(c -> Stream.ofNullable(c.referralCode))
                .toList();
            
            System.out.println("\nAll referral codes: " + allReferrals);
            
            System.out.println("\n  Benefits: Null-safe stream creation!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║    OPTIONAL & STREAM ENHANCEMENTS (Java 9-11)                 ║");
        System.out.println("║  Enhanced functional utilities                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        OptionalStreamExample.demonstrate();
        OptionalOrExample.demonstrate();
        IfPresentOrElseExample.demonstrate();
        StreamTakeDropExample.demonstrate();
        StreamIterateExample.demonstrate();
        StreamOfNullableExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  OPTIONAL (Java 9+):                                           ║");
        System.out.println("║  • .stream(): Convert Optional to Stream                       ║");
        System.out.println("║  • .or(): Lazy fallback to another Optional                    ║");
        System.out.println("║  • .ifPresentOrElse(): Handle both cases functionally          ║");
        System.out.println("║                                                                ║");
        System.out.println("║  STREAM (Java 9+):                                             ║");
        System.out.println("║  • Stream.ofNullable(): Create stream from nullable value      ║");
        System.out.println("║  • Stream.iterate(seed, predicate, fn): Finite generation      ║");
        System.out.println("║  • takeWhile()/dropWhile(): Conditional slicing                ║");
        System.out.println("║                                                                ║");
        System.out.println("║  Introduced in: Java 9-11 (incremental enhancements)           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
