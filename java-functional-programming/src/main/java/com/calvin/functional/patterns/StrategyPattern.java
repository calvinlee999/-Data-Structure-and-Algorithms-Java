package com.calvin.functional.patterns;

import java.util.*;
import java.util.function.*;

/**
 * STRATEGY PATTERN (Functional Approach)
 * 
 * Think of the Strategy Pattern like choosing different routes on a map!
 * You have the same destination (goal), but different strategies to get there:
 * fastest route, shortest route, scenic route. Pick the strategy you need!
 * 
 * Real-world analogy: Like paying at a store. You can pay with cash, credit card,
 * or mobile payment - same goal (buy item), different strategies (payment methods).
 * With functional programming, strategies become simple lambda expressions!
 * 
 * @author FinTech Principal Software Engineer
 */
public class StrategyPattern {

    record Transaction(String id, double amount, String type, String priority) {}
    record Payment(double amount, String currency) {}

    /**
     * PATTERN 1: Traditional OOP Strategy Pattern
     */
    static class TraditionalStrategyExample {
        
        // Strategy interface
        interface PaymentStrategy {
            boolean pay(double amount);
        }
        
        // Concrete strategies
        static class CreditCardPayment implements PaymentStrategy {
            @Override
            public boolean pay(double amount) {
                System.out.println("  💳 Paid $" + amount + " with Credit Card");
                return true;
            }
        }
        
        static class PayPalPayment implements PaymentStrategy {
            @Override
            public boolean pay(double amount) {
                System.out.println("  📱 Paid $" + amount + " with PayPal");
                return true;
            }
        }
        
        static class BitcoinPayment implements PaymentStrategy {
            @Override
            public boolean pay(double amount) {
                System.out.println("  ₿ Paid $" + amount + " with Bitcoin");
                return true;
            }
        }
        
        // Context class
        static class PaymentProcessor {
            private PaymentStrategy strategy;
            
            public void setStrategy(PaymentStrategy strategy) {
                this.strategy = strategy;
            }
            
            public boolean processPayment(double amount) {
                return strategy.pay(amount);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Traditional OOP Strategy ===");
            System.out.println("Goal: Multiple implementations of same behavior\n");
            
            PaymentProcessor processor = new PaymentProcessor();
            
            processor.setStrategy(new CreditCardPayment());
            processor.processPayment(100.0);
            
            processor.setStrategy(new PayPalPayment());
            processor.processPayment(200.0);
            
            processor.setStrategy(new BitcoinPayment());
            processor.processPayment(300.0);
            
            System.out.println("\n  ❌ Problem: Lots of classes just to change behavior!");
        }
    }

    /**
     * PATTERN 2: Functional Strategy with Lambdas
     */
    static class FunctionalStrategyExample {
        
        // Strategy is just a Function!
        static class PaymentProcessor {
            private Function<Double, Boolean> paymentStrategy;
            
            public void setStrategy(Function<Double, Boolean> strategy) {
                this.paymentStrategy = strategy;
            }
            
            public boolean processPayment(double amount) {
                return paymentStrategy.apply(amount);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Functional Strategy ===");
            System.out.println("Goal: Strategies as lambda expressions\n");
            
            PaymentProcessor processor = new PaymentProcessor();
            
            // Strategy 1: Credit card (lambda)
            processor.setStrategy(amount -> {
                System.out.println("  💳 Paid $" + amount + " with Credit Card");
                return true;
            });
            processor.processPayment(100.0);
            
            // Strategy 2: PayPal (lambda)
            processor.setStrategy(amount -> {
                System.out.println("  📱 Paid $" + amount + " with PayPal");
                return true;
            });
            processor.processPayment(200.0);
            
            // Strategy 3: Bitcoin (lambda)
            processor.setStrategy(amount -> {
                System.out.println("  ₿ Paid $" + amount + " with Bitcoin");
                return true;
            });
            processor.processPayment(300.0);
            
            System.out.println("\n  ✅ Benefits: No extra classes, concise code!");
        }
    }

    /**
     * PATTERN 3: Validation Strategies with Predicate
     */
    static class ValidationStrategyExample {
        
        static class TransactionValidator {
            private List<Predicate<Transaction>> rules = new ArrayList<>();
            
            public void addRule(Predicate<Transaction> rule) {
                rules.add(rule);
            }
            
            public boolean validate(Transaction tx) {
                return rules.stream().allMatch(rule -> rule.test(tx));
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Validation Strategies ===");
            System.out.println("Goal: Composable validation rules\n");
            
            TransactionValidator validator = new TransactionValidator();
            
            // Add validation rules as lambdas
            validator.addRule(tx -> tx.amount > 0);
            validator.addRule(tx -> tx.amount < 10000);
            validator.addRule(tx -> tx.type != null && !tx.type.isEmpty());
            validator.addRule(tx -> List.of("DEBIT", "CREDIT").contains(tx.type));
            
            // Test transactions
            List<Transaction> transactions = List.of(
                new Transaction("TX001", 500.0, "DEBIT", "HIGH"),
                new Transaction("TX002", -100.0, "CREDIT", "LOW"),  // Invalid!
                new Transaction("TX003", 15000.0, "DEBIT", "HIGH"),  // Invalid!
                new Transaction("TX004", 200.0, "INVALID", "LOW")   // Invalid!
            );
            
            transactions.forEach(tx -> {
                boolean valid = validator.validate(tx);
                System.out.println("  " + (valid ? "✅" : "❌") + " " + tx.id + 
                    ": $" + tx.amount + " " + tx.type);
            });
            
            System.out.println("\n  Benefits: Flexible, reusable validation!");
        }
    }

    /**
     * PATTERN 4: Sorting Strategies with Comparator
     */
    static class SortingStrategyExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Sorting Strategies ===");
            System.out.println("Goal: Different sorting behaviors\n");
            
            List<Transaction> transactions = new ArrayList<>(List.of(
                new Transaction("TX003", 500.0, "DEBIT", "LOW"),
                new Transaction("TX001", 1000.0, "CREDIT", "HIGH"),
                new Transaction("TX002", 200.0, "DEBIT", "MEDIUM")
            ));
            
            // Strategy 1: Sort by amount
            System.out.println("Sorted by amount:");
            transactions.sort(Comparator.comparingDouble(Transaction::amount));
            transactions.forEach(tx -> 
                System.out.println("  " + tx.id + ": $" + tx.amount));
            
            // Strategy 2: Sort by priority
            System.out.println("\nSorted by priority:");
            Map<String, Integer> priorityOrder = Map.of(
                "HIGH", 1, "MEDIUM", 2, "LOW", 3
            );
            transactions.sort(Comparator.comparingInt(tx -> 
                priorityOrder.get(tx.priority)));
            transactions.forEach(tx -> 
                System.out.println("  " + tx.id + ": " + tx.priority));
            
            // Strategy 3: Sort by ID
            System.out.println("\nSorted by ID:");
            transactions.sort(Comparator.comparing(Transaction::id));
            transactions.forEach(tx -> 
                System.out.println("  " + tx.id));
            
            // Strategy 4: Reverse order
            System.out.println("\nSorted by amount (descending):");
            transactions.sort(Comparator.comparingDouble(Transaction::amount).reversed());
            transactions.forEach(tx -> 
                System.out.println("  " + tx.id + ": $" + tx.amount));
            
            System.out.println("\n  Benefits: One-line strategy changes!");
        }
    }

    /**
     * PATTERN 5: Processing Strategies with Consumer
     */
    static class ProcessingStrategyExample {
        
        static class TransactionProcessor {
            private Consumer<Transaction> processingStrategy;
            
            public void setStrategy(Consumer<Transaction> strategy) {
                this.processingStrategy = strategy;
            }
            
            public void process(List<Transaction> transactions) {
                transactions.forEach(processingStrategy);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Processing Strategies ===");
            System.out.println("Goal: Different processing behaviors\n");
            
            List<Transaction> transactions = List.of(
                new Transaction("TX001", 1000.0, "DEBIT", "HIGH"),
                new Transaction("TX002", 200.0, "CREDIT", "LOW")
            );
            
            TransactionProcessor processor = new TransactionProcessor();
            
            // Strategy 1: Log transactions
            System.out.println("Strategy: Logging");
            processor.setStrategy(tx -> 
                System.out.println("  📝 " + tx.id + ": $" + tx.amount));
            processor.process(transactions);
            
            // Strategy 2: Alert for high amounts
            System.out.println("\nStrategy: High amount alerts");
            processor.setStrategy(tx -> {
                if (tx.amount > 500.0) {
                    System.out.println("  🚨 Alert: High amount transaction " + tx.id);
                }
            });
            processor.process(transactions);
            
            // Strategy 3: Email notifications
            System.out.println("\nStrategy: Email notifications");
            processor.setStrategy(tx -> 
                System.out.println("  📧 Email sent for " + tx.id));
            processor.process(transactions);
            
            System.out.println("\n  Benefits: Swap behavior at runtime!");
        }
    }

    /**
     * PATTERN 6: Calculation Strategies with BiFunction
     */
    static class CalculationStrategyExample {
        
        static class FeeCalculator {
            private BiFunction<Double, String, Double> feeStrategy;
            
            public void setStrategy(BiFunction<Double, String, Double> strategy) {
                this.feeStrategy = strategy;
            }
            
            public double calculateFee(double amount, String type) {
                return feeStrategy.apply(amount, type);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Calculation Strategies ===");
            System.out.println("Goal: Different calculation methods\n");
            
            FeeCalculator calculator = new FeeCalculator();
            double amount = 1000.0;
            
            // Strategy 1: Flat fee
            System.out.println("Strategy: Flat fee");
            calculator.setStrategy((amt, type) -> 5.0);
            System.out.println("  Fee: $" + calculator.calculateFee(amount, "DEBIT"));
            
            // Strategy 2: Percentage fee
            System.out.println("\nStrategy: Percentage fee (2%)");
            calculator.setStrategy((amt, type) -> amt * 0.02);
            System.out.println("  Fee: $" + calculator.calculateFee(amount, "DEBIT"));
            
            // Strategy 3: Tiered fee
            System.out.println("\nStrategy: Tiered fee");
            calculator.setStrategy((amt, type) -> {
                if (amt < 100) return 1.0;
                if (amt < 1000) return 3.0;
                return 5.0;
            });
            System.out.println("  Fee: $" + calculator.calculateFee(amount, "DEBIT"));
            
            // Strategy 4: Type-based fee
            System.out.println("\nStrategy: Type-based fee");
            calculator.setStrategy((amt, type) -> 
                type.equals("CREDIT") ? amt * 0.03 : amt * 0.01);
            System.out.println("  DEBIT fee: $" + calculator.calculateFee(amount, "DEBIT"));
            System.out.println("  CREDIT fee: $" + calculator.calculateFee(amount, "CREDIT"));
            
            System.out.println("\n  Benefits: Easy to add new fee structures!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              STRATEGY PATTERN (Functional)                     ║");
        System.out.println("║  Replace complex class hierarchies with simple lambdas         ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        TraditionalStrategyExample.demonstrate();
        FunctionalStrategyExample.demonstrate();
        ValidationStrategyExample.demonstrate();
        SortingStrategyExample.demonstrate();
        ProcessingStrategyExample.demonstrate();
        CalculationStrategyExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • Traditional strategy: Many classes implementing interface   ║");
        System.out.println("║  • Functional strategy: Simple lambda expressions              ║");
        System.out.println("║  • Use Function, Predicate, Consumer, Comparator               ║");
        System.out.println("║  • Benefits: Less code, more flexibility, easier to test       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
