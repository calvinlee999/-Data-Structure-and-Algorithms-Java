package com.calvin.functional.patterns;

import java.util.*;
import java.util.function.*;

/**
 * FLUENT API PATTERN (Java 8+)
 * 
 * Think of Fluent APIs like building with LEGO blocks that snap together!
 * Each method returns "this" so you can chain more methods. It reads like English!
 * 
 * Real-world analogy: Like ordering at Subway - "I want a footlong, wheat bread,
 * turkey, swiss cheese, lettuce, tomato, mayo" - each step builds on the last!
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 8
 */
public class FluentAPIPattern {

    /**
     * PATTERN 1: Builder Pattern with Fluent API
     * Build complex objects step-by-step
     */
    static class FluentBuilderExample {
        
        // Immutable result
        record Transaction(String id, double amount, String type, 
                          String account, String description, Map<String, String> metadata) {}
        
        // Fluent builder
        static class TransactionBuilder {
            private String id;
            private double amount;
            private String type;
            private String account;
            private String description = "";
            private Map<String, String> metadata = new HashMap<>();
            
            public TransactionBuilder id(String id) {
                this.id = id;
                return this;  // Return 'this' for chaining!
            }
            
            public TransactionBuilder amount(double amount) {
                this.amount = amount;
                return this;
            }
            
            public TransactionBuilder type(String type) {
                this.type = type;
                return this;
            }
            
            public TransactionBuilder account(String account) {
                this.account = account;
                return this;
            }
            
            public TransactionBuilder description(String description) {
                this.description = description;
                return this;
            }
            
            public TransactionBuilder addMetadata(String key, String value) {
                this.metadata.put(key, value);
                return this;
            }
            
            public Transaction build() {
                return new Transaction(id, amount, type, account, 
                    description, Map.copyOf(metadata));
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Fluent Builder ===");
            System.out.println("Goal: Build complex objects readably\n");
            
            // OLD WAY: Constructor with many parameters
            System.out.println("❌ OLD WAY (Constructor hell):");
            System.out.println("  new Transaction(\"TX001\", 1000.0, \"DEBIT\", \"ACC001\", \"Payment\", metadata);");
            System.out.println("  Problem: Hard to read, easy to mix up parameters!\n");
            
            // NEW WAY: Fluent builder
            System.out.println("✅ NEW WAY (Fluent API):");
            Transaction tx = new TransactionBuilder()
                .id("TX001")
                .amount(1000.0)
                .type("DEBIT")
                .account("ACC001")
                .description("Monthly subscription payment")
                .addMetadata("category", "subscription")
                .addMetadata("merchant", "Netflix")
                .build();
            
            System.out.println("  Built transaction: " + tx.id);
            System.out.println("  Amount: $" + tx.amount);
            System.out.println("  Metadata: " + tx.metadata);
            
            System.out.println("\n  Benefits: Reads like English, self-documenting!");
        }
    }

    /**
     * PATTERN 2: Query DSL Pattern
     * Build queries fluently
     */
    static class QueryDSLExample {
        
        record Transaction(String id, double amount, String type, String status) {}
        
        static class TransactionQuery {
            private final List<Transaction> data;
            private Predicate<Transaction> filter = tx -> true;
            private Comparator<Transaction> sorter = null;
            private int limitCount = Integer.MAX_VALUE;
            
            public TransactionQuery(List<Transaction> data) {
                this.data = data;
            }
            
            public TransactionQuery whereAmountGreaterThan(double amount) {
                filter = filter.and(tx -> tx.amount > amount);
                return this;
            }
            
            public TransactionQuery whereType(String type) {
                filter = filter.and(tx -> tx.type.equals(type));
                return this;
            }
            
            public TransactionQuery whereStatus(String status) {
                filter = filter.and(tx -> tx.status.equals(status));
                return this;
            }
            
            public TransactionQuery orderByAmountDesc() {
                sorter = Comparator.comparingDouble(Transaction::amount).reversed();
                return this;
            }
            
            public TransactionQuery orderByAmountAsc() {
                sorter = Comparator.comparingDouble(Transaction::amount);
                return this;
            }
            
            public TransactionQuery limit(int count) {
                this.limitCount = count;
                return this;
            }
            
            public List<Transaction> execute() {
                var stream = data.stream().filter(filter);
                if (sorter != null) {
                    stream = stream.sorted(sorter);
                }
                return stream.limit(limitCount).toList();
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Query DSL ===");
            System.out.println("Goal: Build queries fluently\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("TX001", 1000.0, "DEBIT", "COMPLETED"),
                new Transaction("TX002", 500.0, "CREDIT", "COMPLETED"),
                new Transaction("TX003", 1500.0, "DEBIT", "PENDING"),
                new Transaction("TX004", 2000.0, "DEBIT", "COMPLETED"),
                new Transaction("TX005", 750.0, "CREDIT", "FAILED")
            );
            
            // Fluent query
            List<Transaction> results = new TransactionQuery(transactions)
                .whereType("DEBIT")
                .whereAmountGreaterThan(500.0)
                .whereStatus("COMPLETED")
                .orderByAmountDesc()
                .limit(2)
                .execute();
            
            System.out.println("Query: DEBIT transactions > $500, COMPLETED, top 2:");
            results.forEach(tx ->
                System.out.println("  " + tx.id + ": $" + tx.amount + " (" + tx.status + ")"));
            
            System.out.println("\n  Benefits: SQL-like readability in code!");
        }
    }

    /**
     * PATTERN 3: Pipeline Pattern
     * Chain transformations fluently
     */
    static class PipelineExample {
        
        record Payment(double amount, String currency) {}
        
        static class PaymentPipeline {
            private Payment payment;
            
            public PaymentPipeline(Payment payment) {
                this.payment = payment;
            }
            
            public PaymentPipeline convertToUSD() {
                if (!payment.currency.equals("USD")) {
                    double rate = switch (payment.currency) {
                        case "EUR" -> 1.18;
                        case "GBP" -> 1.27;
                        default -> 1.0;
                    };
                    payment = new Payment(payment.amount * rate, "USD");
                    System.out.println("  ➜ Converted to USD: $" + payment.amount);
                }
                return this;
            }
            
            public PaymentPipeline applyTax(double rate) {
                payment = new Payment(payment.amount * (1 + rate), payment.currency);
                System.out.println("  ➜ Applied " + (rate * 100) + "% tax: $" + payment.amount);
                return this;
            }
            
            public PaymentPipeline applyFee(double fee) {
                payment = new Payment(payment.amount + fee, payment.currency);
                System.out.println("  ➜ Applied $" + fee + " fee: $" + payment.amount);
                return this;
            }
            
            public PaymentPipeline roundToTwoDecimals() {
                payment = new Payment(
                    Math.round(payment.amount * 100.0) / 100.0, 
                    payment.currency
                );
                System.out.println("  ➜ Rounded: $" + payment.amount);
                return this;
            }
            
            public Payment complete() {
                System.out.println("  ✅ Final amount: $" + payment.amount + " " + payment.currency);
                return payment;
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Pipeline Pattern ===");
            System.out.println("Goal: Chain transformations step-by-step\n");
            
            System.out.println("Processing EUR payment:");
            Payment result1 = new PaymentPipeline(new Payment(100.0, "EUR"))
                .convertToUSD()
                .applyTax(0.08)
                .applyFee(2.50)
                .roundToTwoDecimals()
                .complete();
            
            System.out.println("\nProcessing GBP payment:");
            Payment result2 = new PaymentPipeline(new Payment(200.0, "GBP"))
                .convertToUSD()
                .applyTax(0.08)
                .applyFee(5.00)
                .roundToTwoDecimals()
                .complete();
            
            System.out.println("\n  Benefits: Clear transformation steps!");
        }
    }

    /**
     * PATTERN 4: Validation Chain Pattern
     * Fluent validation rules
     */
    static class ValidationChainExample {
        
        record Account(String id, String name, double balance) {}
        
        static class AccountValidator {
            private final List<String> errors = new ArrayList<>();
            private Account account;
            
            public AccountValidator(Account account) {
                this.account = account;
            }
            
            public AccountValidator requireNonNullId() {
                if (account.id == null || account.id.isEmpty()) {
                    errors.add("ID is required");
                }
                return this;
            }
            
            public AccountValidator requireNonNullName() {
                if (account.name == null || account.name.isEmpty()) {
                    errors.add("Name is required");
                }
                return this;
            }
            
            public AccountValidator requirePositiveBalance() {
                if (account.balance < 0) {
                    errors.add("Balance must be positive");
                }
                return this;
            }
            
            public AccountValidator requireMinimumBalance(double min) {
                if (account.balance < min) {
                    errors.add("Balance must be at least $" + min);
                }
                return this;
            }
            
            public boolean isValid() {
                return errors.isEmpty();
            }
            
            public List<String> getErrors() {
                return List.copyOf(errors);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Validation Chain ===");
            System.out.println("Goal: Fluent validation rules\n");
            
            Account validAccount = new Account("ACC001", "Alice", 1000.0);
            Account invalidAccount = new Account("", null, -100.0);
            
            // Validate good account
            var validator1 = new AccountValidator(validAccount)
                .requireNonNullId()
                .requireNonNullName()
                .requirePositiveBalance()
                .requireMinimumBalance(100.0);
            
            System.out.println("Valid account:");
            System.out.println("  Valid: " + validator1.isValid());
            
            // Validate bad account
            var validator2 = new AccountValidator(invalidAccount)
                .requireNonNullId()
                .requireNonNullName()
                .requirePositiveBalance()
                .requireMinimumBalance(100.0);
            
            System.out.println("\nInvalid account:");
            System.out.println("  Valid: " + validator2.isValid());
            System.out.println("  Errors:");
            validator2.getErrors().forEach(err ->
                System.out.println("    ❌ " + err));
            
            System.out.println("\n  Benefits: Collect all errors at once!");
        }
    }

    /**
     * PATTERN 5: Functional Configuration Pattern
     * Configure objects fluently with lambdas
     */
    static class FunctionalConfigExample {
        
        record Transaction(String id, double amount, String type) {}
        
        static class TransactionProcessor {
            private Function<Transaction, Transaction> pipeline = Function.identity();
            
            public TransactionProcessor apply(Function<Transaction, Transaction> step) {
                pipeline = pipeline.andThen(step);
                return this;
            }
            
            public TransactionProcessor validate(Predicate<Transaction> rule, String errorMsg) {
                pipeline = pipeline.andThen(tx -> {
                    if (!rule.test(tx)) {
                        throw new IllegalStateException(errorMsg + ": " + tx.id);
                    }
                    return tx;
                });
                return this;
            }
            
            public TransactionProcessor log(String message) {
                pipeline = pipeline.andThen(tx -> {
                    System.out.println("  " + message + ": " + tx.id);
                    return tx;
                });
                return this;
            }
            
            public Transaction process(Transaction tx) {
                return pipeline.apply(tx);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Functional Configuration ===");
            System.out.println("Goal: Configure behavior with lambdas\n");
            
            Transaction tx = new Transaction("TX001", 1000.0, "DEBIT");
            
            var processor = new TransactionProcessor()
                .log("Starting processing")
                .validate(t -> t.amount > 0, "Amount must be positive")
                .validate(t -> t.type != null, "Type is required")
                .apply(t -> {
                    System.out.println("  Enriching transaction: " + t.id);
                    return t;
                })
                .log("Processing complete");
            
            Transaction result = processor.process(tx);
            System.out.println("\nProcessed: " + result.id);
            
            System.out.println("\n  Benefits: Flexible, composable configuration!");
        }
    }

    /**
     * PATTERN 6: Immutable Update Pattern
     * Fluent updates returning new instances
     */
    static class ImmutableUpdateExample {
        
        record Money(double amount, String currency) {
            public Money add(double value) {
                return new Money(amount + value, currency);
            }
            
            public Money subtract(double value) {
                return new Money(amount - value, currency);
            }
            
            public Money multiply(double factor) {
                return new Money(amount * factor, currency);
            }
            
            public Money convert(String targetCurrency, double rate) {
                return new Money(amount * rate, targetCurrency);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Immutable Update Pattern ===");
            System.out.println("Goal: Fluent operations on immutable data\n");
            
            Money initial = new Money(100.0, "USD");
            System.out.println("Initial: $" + initial.amount + " " + initial.currency);
            
            Money result = initial
                .add(50.0)
                .multiply(1.08)  // Add 8% tax
                .subtract(10.0)  // Apply discount
                .convert("EUR", 0.85);
            
            System.out.println("After operations: " + result.amount + " " + result.currency);
            System.out.println("Original unchanged: $" + initial.amount + " " + initial.currency);
            
            System.out.println("\n  Benefits: Immutable + fluent = safe + readable!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              FLUENT API PATTERN (Java 8+)                      ║");
        System.out.println("║  Chainable methods for readable DSLs                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        FluentBuilderExample.demonstrate();
        QueryDSLExample.demonstrate();
        PipelineExample.demonstrate();
        ValidationChainExample.demonstrate();
        FunctionalConfigExample.demonstrate();
        ImmutableUpdateExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • Return 'this' from methods to enable chaining               ║");
        System.out.println("║  • Builder pattern: Construct complex objects step-by-step     ║");
        System.out.println("║  • DSL pattern: Create domain-specific mini-languages          ║");
        System.out.println("║  • Pipeline pattern: Chain transformations clearly             ║");
        System.out.println("║  • Introduced in: Java 8                                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
