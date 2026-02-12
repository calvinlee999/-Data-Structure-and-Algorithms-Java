package com.calvin.functional;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Functional Programming Principles - 5 Core Concepts for Enterprise Applications
 * 
 * This class demonstrates the foundational principles that make functional programming
 * powerful and reliable. Key principle: We prefer functions that don't have side effects
 * and work with immutable data.
 * 
 * Think of it like cooking: A recipe (function) should always make the same dish (output)
 * given the same ingredients (input), and shouldn't rearrange your kitchen (no side effects).
 */
public class FunctionalPrinciples {

    // ==============================================================================
    // PRINCIPLE 1: IMMUTABILITY - Data doesn't change, we create new versions
    // ==============================================================================
    
    /**
     * Immutability Example: We don't modify existing data, we create new data.
     * 
     * Analogy: Like making a photocopy instead of marking up the original document.
     */
    static class ImmutabilityExample {
        
        // Account record with final fields (immutable)
        record Account(String id, String name, double balance) {}
        
        public static void demonstrate() {
            System.out.println("\n=== PRINCIPLE 1: IMMUTABILITY ===");
            
            Account account1 = new Account("ACC001", "Alice", 1000.0);
            System.out.println("Original account: " + account1);
            
            // BAD: Mutable approach (if Account had setters)
            System.out.println("\n❌ BAD - Mutable Approach:");
            System.out.println("account1.setBalance(1500); // Side effect - data changed!");
            System.out.println("Now it's unclear what the original balance was.");
            
            // GOOD: Immutable approach (functional)
            System.out.println("\n✅ GOOD - Immutable Approach:");
            Account account2 = new Account(account1.id(), account1.name(), 1500.0);
            System.out.println("Original account: " + account1);
            System.out.println("New account (unchanged original): " + account2);
            System.out.println("Benefits: Original data preserved, no side effects, thread-safe");
            
            // Stream example: immutable transformations
            System.out.println("\nStream Immutability:");
            List<Double> balances = List.of(1000.0, 2000.0, 500.0);
            List<Double> increased = balances.stream()
                .map(b -> b * 1.05)  // Creates new values, doesn't modify originals
                .collect(Collectors.toList());
            System.out.println("Original balances: " + balances);
            System.out.println("Increased balances: " + increased);
        }
    }

    // ==============================================================================
    // PRINCIPLE 2: STATELESSNESS - Functions should be independent, no memory
    // ==============================================================================
    
    /**
     * Statelessness Example: Pure functions that don't depend on external state.
     * 
     * Analogy: Like a calculator - it should always give the same answer for 2+3,
     * regardless of what you calculated before.
     */
    static class StatelessnessExample {
        
        public static void demonstrate() {
            System.out.println("\n=== PRINCIPLE 2: STATELESSNESS ===");
            
            // BAD: Stateful function (depends on external state)
            System.out.println("\n❌ BAD - Stateful Approach:");
            StatefulValidator validator = new StatefulValidator();
            System.out.println("Call 1: validator.validate(100) = " + validator.validate(100));
            System.out.println("Call 2: validator.validate(100) = " + validator.validate(100));
            System.out.println("Problem: Same input gives different output! Confusing and error-prone.");
            
            // GOOD: Stateless function (pure function)
            System.out.println("\n✅ GOOD - Stateless Approach:");
            Function<Double, Boolean> purePredicate = amount -> amount > 0 && amount < 100000;
            System.out.println("Call 1: purePredicate.apply(100) = " + purePredicate.apply(100));
            System.out.println("Call 2: purePredicate.apply(100) = " + purePredicate.apply(100));
            System.out.println("Benefit: Same input always gives same output. Predictable and testable.");
            
            System.out.println("\nFunctional stream operations are stateless:");
            List<Integer> amounts = List.of(50, 150, 200, 30);
            List<Integer> valid = amounts.stream()
                .filter(a -> a > 0 && a < 100000)
                .collect(Collectors.toList());
            System.out.println("Filtered amounts: " + valid);
            System.out.println("Same filter logic always produces same result.");
        }
        
        // Stateful validator (not recommended)
        static class StatefulValidator {
            private int callCount = 0;
            
            public boolean validate(double amount) {
                callCount++;
                // Result changes based on how many times called (stateful!)
                return amount > 0 && callCount % 2 == 0;
            }
        }
    }

    // ==============================================================================
    // PRINCIPLE 3: DECLARATIVE PROGRAMMING - Say WHAT, not HOW
    // ==============================================================================
    
    /**
     * Declarative Example: We describe what we want, not how to do it.
     * 
     * Analogy: Like ordering food at a restaurant - you say "I want pasta" (WHAT),
     * not "Heat water, boil for 8 minutes, drain..." (HOW).
     */
    static class DeclarativePipelinesExample {
        
        record Transaction(String id, double amount, String type) {}
        
        public static void demonstrate() {
            System.out.println("\n=== PRINCIPLE 3: DECLARATIVE PROGRAMMING ===");
            
            List<Transaction> transactions = List.of(
                new Transaction("T1", 1500.0, "DEPOSIT"),
                new Transaction("T2", -500.0, "WITHDRAWAL"),
                new Transaction("T3", 250.0, "TRANSFER"),
                new Transaction("T4", -100.0, "FEE")
            );
            
            // BAD: Imperative approach (HOW to do it)
            System.out.println("\n❌ BAD - Imperative (HOW):");
            double totalWithdrawals = 0;
            for (Transaction t : transactions) {
                if (t.type.equals("WITHDRAWAL") && t.amount < 0) {
                    totalWithdrawals += Math.abs(t.amount);
                }
            }
            System.out.println("Total withdrawals: " + totalWithdrawals);
            System.out.println("Problem: Verbose, hard to understand at a glance, room for bugs.");
            
            // GOOD: Declarative approach (WHAT we want)
            System.out.println("\n✅ GOOD - Declarative (WHAT):");
            double declTotal = transactions.stream()
                .filter(t -> t.type.equals("WITHDRAWAL") && t.amount < 0)
                .mapToDouble(t -> Math.abs(t.amount))
                .sum();
            System.out.println("Total withdrawals: " + declTotal);
            System.out.println("Benefit: Clear intent, concise, easier to reason about.");
            
            // More complex pipeline
            System.out.println("\nComplex Pipeline (Declarative):");
            var summary = transactions.stream()
                .filter(t -> !t.type.equals("FEE"))
                .collect(Collectors.groupingBy(
                    Transaction::type,
                    Collectors.summingDouble(t -> t.amount)
                ));
            System.out.println("By transaction type: " + summary);
            System.out.println("Notice: The whole logic is one clear statement of what we want.");
        }
    }

    // ==============================================================================
    // PRINCIPLE 4: CONCURRENCY SAFETY - No fights over shared data
    // ==============================================================================
    
    /**
     * Concurrency Safety Example: Multiple threads can safely process functional pipelines.
     * 
     * Analogy: Like a library where each person gets their own copy of a book.
     * No one fights over who can read the original.
     */
    static class ConcurrencySafeExample {
        
        static class MutableAccount {
            private int balance = 1000;
            
            public void deposit(int amount) { balance += amount; }
            
            public int getBalance() { return balance; }
        }
        
        record ImmutableAccount(int balance) {
            ImmutableAccount withDeposit(int amount) {
                return new ImmutableAccount(balance + amount);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PRINCIPLE 4: CONCURRENCY SAFETY ===");
            
            // BAD: Shared mutable state (threads fight over it)
            System.out.println("\n❌ BAD - Shared Mutable State:");
            MutableAccount account = new MutableAccount();
            System.out.println("Starting balance: " + account.getBalance());
            
            Thread t1 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    account.deposit(1);
                }
            });
            
            Thread t2 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    account.deposit(1);
                }
            });
            
            try {
                t1.start();
                t2.start();
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.println("After 2 threads each adding 1000: " + account.getBalance());
            System.out.println("Expected: 2000, Actual: " + account.getBalance());
            System.out.println("Problem: Race condition! Multiple threads corrupt the data.");
            
            // GOOD: Immutable approach (no conflicts)
            System.out.println("\n✅ GOOD - Immutable (No Conflicts):");
            ImmutableAccount immutableAcc = new ImmutableAccount(1000);
            System.out.println("Starting balance: " + immutableAcc.balance());
            
            List<ImmutableAccount> results = IntStream.range(0, 2)
                .boxed()
                .parallelStream()
                .flatMap(index -> IntStream.range(0, 1000)
                    .boxed()
                    .map(i -> index == 0 ? new ImmutableAccount(1) : new ImmutableAccount(1)))
                .collect(Collectors.toList());
            
            int total = results.stream()
                .mapToInt(ImmutableAccount::balance)
                .sum();
            
            System.out.println("Functional pipeline result: " + (1000 + total));
            System.out.println("Benefit: Each thread has its own data, no corruption possible.");
        }
    }

    // ==============================================================================
    // PRINCIPLE 5: SECURITY & COMPLIANCE - Predictable, auditable operations
    // ==============================================================================
    
    /**
     * Security Example: Functional approach makes operations auditable and secure.
     * 
     * Analogy: Like a transparent recipe - anyone can verify each step is correct
     * and no secret ingredients were added.
     */
    static class SecurityComplianceExample {
        
        record Transaction(String id, double amount, String type, boolean verified) {}
        
        public static void demonstrate() {
            System.out.println("\n=== PRINCIPLE 5: SECURITY & COMPLIANCE ===");
            
            List<Transaction> transactions = List.of(
                new Transaction("T1", 5000.0, "WIRE", true),
                new Transaction("T2", 25000.0, "TRANSFER", false),
                new Transaction("T3", 1000.0, "CHECK", true),
                new Transaction("T4", 75000.0, "CASH_DEPOSIT", false)
            );
            
            // Compliance checks as chainable predicates
            System.out.println("\n✅ Compliance Pipeline (Transparent & Auditable):");
            
            Predicate<Transaction> isVerified = t -> t.verified;
            Predicate<Transaction> isBelowThreshold = t -> t.amount < 50000;
            Predicate<Transaction> isAllowedType = t -> 
                List.of("WIRE", "TRANSFER", "CHECK").contains(t.type);
            
            System.out.println("Transaction Audit Trail:");
            transactions.forEach(t -> {
                boolean passesCompliance = isVerified.and(isBelowThreshold).and(isAllowedType).test(t);
                String reasons = "";
                if (!t.verified) reasons += "[Not Verified] ";
                if (t.amount >= 50000) reasons += "[Exceeds Threshold] ";
                if (!isAllowedType.test(t)) reasons += "[Suspicious Type] ";
                
                System.out.println("  " + t.id + ": $" + t.amount + " - " + 
                    (passesCompliance ? "✅ APPROVED" : "❌ FLAGGED " + reasons));
            });
            
            System.out.println("\nBenefit: Every decision is transparent, traceable, and auditable.");
            System.out.println("Easy to add compliance rules without changing existing code.");
        }
    }

    // ==============================================================================
    // MAIN: Run all demonstrations
    // ==============================================================================
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     FUNCTIONAL PROGRAMMING: 5 FOUNDATIONAL PRINCIPLES          ║");
        System.out.println("║     Enterprise Application Development with FinTech Scenarios  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        ImmutabilityExample.demonstrate();
        StatelessnessExample.demonstrate();
        DeclarativePipelinesExample.demonstrate();
        ConcurrencySafeExample.demonstrate();
        SecurityComplianceExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     KEY TAKEAWAY: These 5 principles make functional code     ║");
        System.out.println("║     safer, easier to test, and more suitable for enterprise   ║");
        System.out.println("║     applications that demand reliability and maintainability  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
