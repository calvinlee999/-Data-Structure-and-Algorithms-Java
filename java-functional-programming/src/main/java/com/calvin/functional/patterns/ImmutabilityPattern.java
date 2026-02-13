package com.calvin.functional.patterns;

import java.util.*;
import java.util.stream.*;

/**
 * IMMUTABILITY PATTERN
 * 
 * Think of immutable objects like written notes - once written, they can't change!
 * You can read them forever, but to "change" them, you create a new note.
 * 
 * Real-world analogy: Like ice vs water. Once frozen (immutable), ice stays ice.
 * To change it, you create something new (melt it to water). Multiple people
 * can safely look at the same ice cube without worrying it'll change!
 * 
 * @author FinTech Principal Software Engineer
 */
public class ImmutabilityPattern {

    /**
     * PATTERN 1: Immutable Records (Java 14+)
     */
    static class ImmutableRecordsExample {
        
        // Records are automatically immutable!
        record Account(String id, String owner, double balance) {
            // Compact constructor for validation
            public Account {
                if (balance < 0) {
                    throw new IllegalArgumentException("Balance cannot be negative");
                }
            }
            
            // Methods return NEW objects instead of modifying
            public Account deposit(double amount) {
                return new Account(id, owner, balance + amount);
            }
            
            public Account withdraw(double amount) {
                if (amount > balance) {
                    throw new IllegalArgumentException("Insufficient funds");
                }
                return new Account(id, owner, balance - amount);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Immutable Records ===");
            System.out.println("Goal: Data that can't be modified\n");
            
            Account original = new Account("A001", "Alice", 1000.0);
            System.out.println("  Original: " + original);
            
            // Each operation creates a NEW account
            Account afterDeposit = original.deposit(500.0);
            System.out.println("  After deposit: " + afterDeposit);
            System.out.println("  Original unchanged: " + original);
            
            Account afterWithdraw = afterDeposit.withdraw(200.0);
            System.out.println("  After withdraw: " + afterWithdraw);
            
            System.out.println("\n  Benefits: Thread-safe, predictable, no side effects!");
        }
    }

    /**
     * PATTERN 2: Immutable Collections
     */
    static class ImmutableCollectionsExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Immutable Collections ===");
            System.out.println("Goal: Collections that cannot be modified\n");
            
            // OLD WAY: Mutable list (dangerous!)
            List<String> mutable = new ArrayList<>();
            mutable.add("Transaction1");
            mutable.add("Transaction2");
            System.out.println("❌ Mutable list: " + mutable);
            mutable.add("Transaction3");  // Can be modified!
            
            // NEW WAY 1: List.of() - immutable (Java 9+)
            List<String> immutable1 = List.of("TX001", "TX002", "TX003");
            System.out.println("\n✅ List.of(): " + immutable1);
            
            try {
                immutable1.add("TX004");  // This will throw exception!
            } catch (UnsupportedOperationException e) {
                System.out.println("  Cannot modify! " + e.getClass().getSimpleName());
            }
            
            // NEW WAY 2: Stream to immutable
            List<String> immutable2 = Stream.of("TX001", "TX002", "TX003")
                .collect(Collectors.toUnmodifiableList());
            
            // NEW WAY 3: Map.of() for immutable maps
            Map<String, Double> balances = Map.of(
                "A001", 1000.0,
                "A002", 2500.0,
                "A003", 750.0
            );
            System.out.println("  Immutable map: " + balances);
            
            // NEW WAY 4: Set.of() for immutable sets
            Set<String> statuses = Set.of("PENDING", "APPROVED", "REJECTED");
            System.out.println("  Immutable set: " + statuses);
            
            System.out.println("\n  Benefits: Safe sharing across threads!");
        }
    }

    /**
     * PATTERN 3: Transforming Immutable Data
     */
    static class TransformingImmutableExample {
        
        record Transaction(String id, double amount, String status) {}
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Transforming Immutable Data ===");
            System.out.println("Goal: Create modified copies\n");
            
            List<Transaction> original = List.of(
                new Transaction("TX001", 100.0, "PENDING"),
                new Transaction("TX002", 200.0, "PENDING"),
                new Transaction("TX003", 300.0, "PENDING")
            );
            
            System.out.println("Original:");
            original.forEach(tx -> System.out.println("  " + tx));
            
            // Transform to new collection with modified elements
            List<Transaction> approved = original.stream()
                .map(tx -> new Transaction(tx.id, tx.amount, "APPROVED"))
                .collect(Collectors.toUnmodifiableList());
            
            System.out.println("\nAfter approval:");
            approved.forEach(tx -> System.out.println("  " + tx));
            
            System.out.println("\nOriginal unchanged:");
            original.forEach(tx -> System.out.println("  " + tx));
            
            System.out.println("\n  Benefits: Original data preserved!");
        }
    }

    /**
     * PATTERN 4: Defensive Copying
     */
    static class DefensiveCopyingExample {
        
        // BAD: Mutable class exposing internal state
        static class BadAccount {
            private List<String> transactions = new ArrayList<>();
            
            public List<String> getTransactions() {
                return transactions;  // ❌ Exposes internal list!
            }
        }
        
        // GOOD: Defensive copying
        static class GoodAccount {
            private final List<String> transactions = new ArrayList<>();
            
            public List<String> getTransactions() {
                return List.copyOf(transactions);  // ✅ Returns copy!
            }
            
            public void addTransaction(String tx) {
                transactions.add(tx);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Defensive Copying ===");
            System.out.println("Goal: Protect internal state\n");
            
            // BAD Example
            System.out.println("❌ BAD: Exposed internal state");
            BadAccount bad = new BadAccount();
            List<String> badTx = bad.getTransactions();
            badTx.add("Hacker transaction!");  // Can modify internal state!
            System.out.println("  Modified externally: " + bad.getTransactions());
            
            // GOOD Example
            System.out.println("\n✅ GOOD: Defensive copy");
            GoodAccount good = new GoodAccount();
            good.addTransaction("TX001");
            List<String> goodTx = good.getTransactions();
            
            try {
                goodTx.add("Hacker transaction!");  // This will fail!
            } catch (UnsupportedOperationException e) {
                System.out.println("  Protection works! " + e.getClass().getSimpleName());
            }
            
            System.out.println("  Internal state safe: " + good.getTransactions());
            
            System.out.println("\n  Benefits: Encapsulation preserved!");
        }
    }

    /**
     * PATTERN 5: Builder Pattern with Immutability
     */
    static class BuilderPatternExample {
        
        record User(String id, String name, String email, String phone, String address) {
            
            // Builder for complex construction
            static class Builder {
                private String id;
                private String name;
                private String email = "";
                private String phone = "";
                private String address = "";
                
                public Builder id(String id) {
                    this.id = id;
                    return this;
                }
                
                public Builder name(String name) {
                    this.name = name;
                    return this;
                }
                
                public Builder email(String email) {
                    this.email = email;
                    return this;
                }
                
                public Builder phone(String phone) {
                    this.phone = phone;
                    return this;
                }
                
                public Builder address(String address) {
                    this.address = address;
                    return this;
                }
                
                public User build() {
                    return new User(id, name, email, phone, address);
                }
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Builder Pattern ===");
            System.out.println("Goal: Fluent API for immutable objects\n");
            
            // Build complex immutable object
            User user = new User.Builder()
                .id("U001")
                .name("Alice")
                .email("alice@example.com")
                .phone("555-1234")
                .address("123 Main St")
                .build();
            
            System.out.println("  Built user: " + user);
            
            // Create variation
            User similarUser = new User.Builder()
                .id("U002")
                .name("Bob")
                .email("bob@example.com")
                .build();
            
            System.out.println("  Similar user: " + similarUser);
            
            System.out.println("\n  Benefits: Readable, flexible construction!");
        }
    }

    /**
     * PATTERN 6: Immutable Updates with Record Patterns
     */
    static class ImmutableUpdatesExample {
        
        record Money(double amount, String currency) {
            public Money add(Money other) {
                if (!currency.equals(other.currency)) {
                    throw new IllegalArgumentException("Currency mismatch");
                }
                return new Money(amount + other.amount, currency);
            }
            
            public Money multiply(double factor) {
                return new Money(amount * factor, currency);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Immutable Updates ===");
            System.out.println("Goal: Mathematical operations on immutable data\n");
            
            Money price = new Money(100.0, "USD");
            Money tax = new Money(8.0, "USD");
            
            System.out.println("  Price: " + price);
            System.out.println("  Tax: " + tax);
            
            Money total = price.add(tax);
            System.out.println("  Total: " + total);
            
            Money discounted = price.multiply(0.9);
            System.out.println("  Discounted (10% off): " + discounted);
            
            System.out.println("\n  Original price unchanged: " + price);
            
            System.out.println("\n  Benefits: Mathematical clarity!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                 IMMUTABILITY PATTERN                           ║");
        System.out.println("║  Data that cannot change - safe and predictable                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        ImmutableRecordsExample.demonstrate();
        ImmutableCollectionsExample.demonstrate();
        TransformingImmutableExample.demonstrate();
        DefensiveCopyingExample.demonstrate();
        BuilderPatternExample.demonstrate();
        ImmutableUpdatesExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • Use records for immutable data classes                      ║");
        System.out.println("║  • Use List.of(), Map.of(), Set.of() for collections          ║");
        System.out.println("║  • Return defensive copies to protect internal state           ║");
        System.out.println("║  • Benefits: Thread-safe, predictable, easier to reason about ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
