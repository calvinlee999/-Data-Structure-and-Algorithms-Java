package com.calvin.functional.patterns;

import java.util.*;
import java.util.function.*;

/**
 * OPTIONAL PATTERN
 * 
 * Think of Optional like a safe box that might or might not have something inside!
 * Instead of worrying about null (which crashes programs), Optional tells you
 * if a value exists before you use it.
 * 
 * Real-world analogy: Like checking if there's milk in the fridge before
 * pouring. Optional says "check first, then use" - no spilled milk!
 * 
 * @author FinTech Principal Software Engineer
 */
public class OptionalPattern {

    record User(String id, String name, Optional<String> email, Optional<String> phone) {}
    record Account(String id, double balance) {}
    record Transaction(String id, double amount) {}

    /**
     * PATTERN 1: Creating Optional - of, ofNullable, empty
     */
    static class CreatingOptionalExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Creating Optional ===");
            System.out.println("Goal: Wrap values safely\n");
            
            // Optional.of() - value must not be null
            Optional<String> present = Optional.of("Hello");
            System.out.println("  Optional.of('Hello'): " + present);
            
            // Optional.ofNullable() - value might be null (safe!)
            String maybeNull = null;
            Optional<String> maybe = Optional.ofNullable(maybeNull);
            System.out.println("  Optional.ofNullable(null): " + maybe);
            
            // Optional.empty() - explicitly empty
            Optional<String> nothing = Optional.empty();
            System.out.println("  Optional.empty(): " + nothing);
            
            System.out.println("\n  Benefits: Clear communication about null possibility!");
        }
    }

    /**
     * PATTERN 2: Checking and Retrieving - isPresent, get, orElse
     */
    static class CheckingOptionalExample {
        
        static Optional<User> findUser(String id) {
            Map<String, User> users = Map.of(
                "U001", new User("U001", "Alice", Optional.of("alice@example.com"), Optional.empty()),
                "U002", new User("U002", "Bob", Optional.empty(), Optional.of("555-1234"))
            );
            return Optional.ofNullable(users.get(id));
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Checking and Retrieving ===");
            System.out.println("Goal: Safe value access\n");
            
            // OLD WAY: null checks everywhere
            System.out.println("❌ OLD WAY (Null checks):");
            User user = null;  // Simulate database lookup
            if (user != null) {
                System.out.println("  User: " + user.name);
            } else {
                System.out.println("  User not found");
            }
            
            // NEW WAY: Optional
            System.out.println("\n✅ NEW WAY (Optional):");
            Optional<User> optUser = findUser("U001");
            
            // Method 1: isPresent() + get()
            if (optUser.isPresent()) {
                System.out.println("  Found: " + optUser.get().name);
            }
            
            // Method 2: ifPresent() with lambda
            optUser.ifPresent(u -> System.out.println("  User: " + u.name));
            
            // Method 3: orElse() with default
            User user2 = findUser("U999").orElse(
                new User("GUEST", "Guest User", Optional.empty(), Optional.empty())
            );
            System.out.println("  User or Guest: " + user2.name);
            
            // Method 4: orElseGet() with supplier (lazy evaluation)
            User user3 = findUser("U999").orElseGet(() -> {
                System.out.println("  Creating default user...");
                return new User("DEFAULT", "Default", Optional.empty(), Optional.empty());
            });
            
            // Method 5: orElseThrow() for required values
            try {
                User user4 = findUser("U999").orElseThrow(() ->
                    new IllegalArgumentException("User not found!"));
            } catch (Exception e) {
                System.out.println("  Exception: " + e.getMessage());
            }
            
            System.out.println("\n  Benefits: No NullPointerException!");
        }
    }

    /**
     * PATTERN 3: Transforming Optional - map, flatMap
     */
    static class TransformingOptionalExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Transforming Optional ===");
            System.out.println("Goal: Transform values inside Optional\n");
            
            Optional<User> user = Optional.of(
                new User("U001", "Alice", Optional.of("alice@example.com"), Optional.empty())
            );
            
            // map() - transform if present
            Optional<String> userName = user.map(User::name);
            System.out.println("  User name: " + userName.orElse("Unknown"));
            
            // map() with uppercase
            Optional<String> upperName = user.map(User::name).map(String::toUpperCase);
            System.out.println("  Uppercase: " + upperName.orElse("Unknown"));
            
            // flatMap() - for nested Optionals
            Optional<String> email = user.flatMap(User::email);
            System.out.println("  Email: " + email.orElse("No email"));
            
            Optional<String> phone = user.flatMap(User::phone);
            System.out.println("  Phone: " + phone.orElse("No phone"));
            
            System.out.println("\n  Benefits: Chain operations safely!");
        }
    }

    /**
     * PATTERN 4: Filtering Optional - filter
     */
    static class FilteringOptionalExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Filtering Optional ===");
            System.out.println("Goal: Conditionally keep values\n");
            
            List<Optional<Account>> accounts = List.of(
                Optional.of(new Account("A001", 1000.0)),
                Optional.of(new Account("A002", 50.0)),
                Optional.empty(),
                Optional.of(new Account("A003", 500.0))
            );
            
            System.out.println("Accounts with balance > $100:");
            accounts.stream()
                .flatMap(Optional::stream)                    // Unwrap Optionals
                .filter(acc -> acc.balance > 100.0)
                .forEach(acc -> System.out.println("  " + acc.id + ": $" + acc.balance));
            
            // Or filter inside Optional
            Optional<Account> account = Optional.of(new Account("A001", 1000.0));
            Optional<Account> highBalance = account.filter(a -> a.balance > 500.0);
            
            highBalance.ifPresentOrElse(
                a -> System.out.println("\n  High balance account: " + a.id),
                () -> System.out.println("\n  No high balance account")
            );
            
            System.out.println("\n  Benefits: Combine Optional with filtering!");
        }
    }

    /**
     * PATTERN 5: Chaining Multiple Optionals
     */
    static class ChainingOptionalsExample {
        
        static Optional<String> getConfig(String key) {
            Map<String, String> config = Map.of(
                "db.host", "localhost",
                "db.port", "5432"
            );
            return Optional.ofNullable(config.get(key));
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Chaining Optionals ===");
            System.out.println("Goal: Fallback chain (try multiple sources)\n");
            
            // Try multiple config sources
            String dbHost = getConfig("db.host.override")
                .or(() -> getConfig("db.host"))              // Fallback
                .or(() -> Optional.of("default.db.com"))     // Final fallback
                .orElse("localhost");
            
            System.out.println("  DB Host: " + dbHost);
            
            // Try to get email, then phone, then default
            User user = new User("U001", "Alice", Optional.empty(), Optional.of("555-1234"));
            
            String contact = user.email()
                .or(() -> user.phone())
                .orElse("No contact info");
            
            System.out.println("  Contact: " + contact);
            
            System.out.println("\n  Benefits: Elegant fallback handling!");
        }
    }

    /**
     * PATTERN 6: Optional in Streams
     */
    static class OptionalInStreamsExample {
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Optional in Streams ===");
            System.out.println("Goal: Work with Optional values in collections\n");
            
            List<User> users = List.of(
                new User("U001", "Alice", Optional.of("alice@example.com"), Optional.empty()),
                new User("U002", "Bob", Optional.empty(), Optional.of("555-1234")),
                new User("U003", "Carol", Optional.of("carol@example.com"), Optional.empty())
            );
            
            // Extract all emails that exist
            System.out.println("All emails:");
            users.stream()
                .map(User::email)                    // Stream<Optional<String>>
                .flatMap(Optional::stream)           // Unwrap Optionals
                .forEach(email -> System.out.println("  📧 " + email));
            
            // Count users with email
            long emailCount = users.stream()
                .map(User::email)
                .filter(Optional::isPresent)
                .count();
            
            System.out.println("\n  Users with email: " + emailCount);
            
            System.out.println("\n  Benefits: Clean stream processing!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                  OPTIONAL PATTERN                              ║");
        System.out.println("║  Handle null values safely and elegantly                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        CreatingOptionalExample.demonstrate();
        CheckingOptionalExample.demonstrate();
        TransformingOptionalExample.demonstrate();
        FilteringOptionalExample.demonstrate();
        ChainingOptionalsExample.demonstrate();
        OptionalInStreamsExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • Optional replaces null checks                               ║");
        System.out.println("║  • Use map/flatMap to transform values safely                  ║");
        System.out.println("║  • Use orElse/orElseGet for defaults                           ║");
        System.out.println("║  • Never use .get() without .isPresent()!                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
