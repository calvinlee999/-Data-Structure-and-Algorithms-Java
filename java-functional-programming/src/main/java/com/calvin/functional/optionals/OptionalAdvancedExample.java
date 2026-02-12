package com.calvin.functional.optionals;

import java.util.*;
import java.util.stream.Stream;

/**
 * Optional: Advanced Patterns and Anti-Patterns
 * 
 * Learn when to use Optional, when not to use it, and advanced techniques.
 */
public class OptionalAdvancedExample {

    record User(String id, String name) {}

    public static void main(String[] args) {
        System.out.println("=== OPTIONAL ADVANCED PATTERNS ===\n");

        demonstrateAntiPatterns();
        demonstrateGoodPatterns();
        demonstrateStreamIntegration();
        demonstrateOrMethod();
    }

    private static void demonstrateAntiPatterns() {
        System.out.println("=== ❌ ANTI-PATTERNS (AVOID THESE) ===\n");

        Optional<String> optional = Optional.of("value");

        // ❌ Anti-pattern 1: Using get() without  checking
        try {
            String value = Optional.empty().get(); // Throws!
        } catch (NoSuchElementException e) {
            System.out.println("❌ Don't use get() without isPresent()!");
        }

        // ❌ Anti-pattern 2: Using isPresent() + get() (defeats the purpose)
        if (optional.isPresent()) {
            String value = optional.get(); // Just use ifPresent()!
            System.out.println("❌ Don't do: isPresent() + get()");
        }

        // ✅ Better: Use ifPresent()
        optional.ifPresent(value -> System.out.println("✅ Use ifPresent(): " + value));

        // ❌ Anti-pattern 3: Optional as field
        class BadExample {
            private Optional<String> name; // ❌ Don't do this!
        }
        System.out.println("❌ Don't use Optional as class fields!");

        // ❌ Anti-pattern 4: Optional as method parameter
        class AlsoBad {
            public void process(Optional<String> data) { // ❌ Don't do this!
            }
        }
        System.out.println("❌ Don't use Optional as method parameters!");

        // ❌ Anti-pattern 5: Returning Optional.of(null)
        try {
            Optional<String> bad = Optional.of(null); // Throws!
        } catch (NullPointerException e) {
            System.out.println("❌ Don't use Optional.of() with null!");
        }

        System.out.println("✅ Use Optional.ofNullable() for possibly-null values\n");
    }

    private static void demonstrateGoodPatterns() {
        System.out.println("=== ✅ GOOD PATTERNS (USE THESE) ===\n");

        // ✅ Pattern 1: Return Optional from methods that might not have a value
        Optional<User> user = findUserById("123");
        user.ifPresent(u -> System.out.println("✅ Found user: " + u.name()));

        // ✅ Pattern 2: Use orElse() for simple defaults
        String name = findUserById("123")
                .map(User::name)
                .orElse("Guest");
        System.out.println("✅ User name: " + name);

        // ✅ Pattern 3: Use orElseGet() for expensive defaults
        String expensiveName = findUserById("999")
                .map(User::name)
                .orElseGet(() -> {
                    System.out.println("  Computing expensive default...");
                    return queryDatabaseForDefaultName();
                });
        System.out.println("✅ Expensive default: " + expensiveName);

        // ✅ Pattern 4: Use orElseThrow() when value is required
        try {
            User requiredUser = findUserById("999")
                    .orElseThrow(() -> new IllegalArgumentException("User not found!"));
        } catch (IllegalArgumentException e) {
            System.out.println("✅ Throwing custom exception: " + e.getMessage());
        }

        // ✅ Pattern 5: Chain with map/flatMap/filter
        String email = findUserById("123")
                .map(User::name)
                .map(String::toLowerCase)
                .map(name -> name + "@example.com")
                .orElse("unknown@example.com");
        System.out.println("✅ Generated email: " + email);

        System.out.println();
    }

    private static void demonstrateStreamIntegration() {
        System.out.println("=== OPTIONAL + STREAMS ===\n");

        List<String> userIds = Arrays.asList("123", "456", "999", "789");

        // Convert Optional to Stream (Java 9+)
        List<User> users = userIds.stream()
                .map(TypeConverter::findUserById)
                .flatMap(opt -> opt.stream())  // Converts Optional<User> to Stream<User>
                .toList();

        System.out.println("Found " + users.size() + " users");
        users.forEach(u -> System.out.println("  - " + u.name()));

        // Filter out empty Optionals
        List<String> names = userIds.stream()
                .map(TypeConverter::findUserById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(User::name)
                .toList();

        System.out.println("\nUser names: " + names);

        System.out.println("\n✅ Integrate smoothly with streams!\n");
    }

    private static void demonstrateOrMethod() {
        System.out.println("=== OR METHOD (Java 9+) ===\n");

        // or() provides alternative Optional if first is empty
        Optional<String> primary = Optional.empty();
        Optional<String> fallback = Optional.of("fallback");

        Optional<String> result = primary.or(() -> fallback);
        System.out.println("Result: " + result.orElse("none"));

        // Chaining or() for multiple fallbacks
        String value = findInCache("key")
                .or(() -> findInDatabase("key"))
                .or(() -> findInRemoteService("key"))
                .orElse("default");

        System.out.println("Value with fallbacks: " + value);

        System.out.println("\n✅ Chain Optional fallbacks!\n");
    }

    // Helper methods
    private static Optional<User> findUserById(String id) {
        if ("123".equals(id)) {
            return Optional.of(new User("123", "Alice"));
        } else if ("456".equals(id)) {
            return Optional.of(new User("456", "Bob"));
        }
        return Optional.empty();
    }

    private static String queryDatabaseForDefaultName() {
        return "DefaultUser";
    }

    private static Optional<String> findInCache(String key) {
        return Optional.empty(); // Simulate cache miss
    }

    private static Optional<String> findInDatabase(String key) {
        return Optional.empty(); // Simulate DB miss
    }

    private static Optional<String> findInRemoteService(String key) {
        return Optional.of("remote-value"); // Simulate remote hit
    }

    // Helper class for stream integration
    static class TypeConverter {
        static Optional<User> findUserById(String id) {
            return OptionalAdvancedExample.findUserById(id);
        }
    }

    /**
     * SUMMARY: WHEN TO USE OPTIONAL
     * 
     * ✅ DO use Optional:
     *    - As return type for methods that might not have a value
     *    - To represent missing or absent values
     *    - In streams to filter/map optionals
     *    - When nullability is part of the API contract
     * 
     * ❌ DON'T use Optional:
     *    - As class fields (adds overhead, not serializable)
     *    - As method parameters (use overloading or null)
     *    - Just to avoid null checks (that's abuse)
     *    - In performance-critical code (small overhead)
     * 
     * BEST PRACTICES:
     *    1. Return Optional instead of null
     *    2. Never call get() without checking
     *    3. Use orElse() for simple defaults
     *    4. Use orElseGet() for expensive defaults
     *    5. Use orElseThrow() for required values
     *    6. Chain operations with map/flatMap/filter
     *    7. Integrate with streams using flatMap(Optional::stream)
     *    8. Use or() for fallback chains (Java 9+)
     * 
     * ANTI-PATTERNS TO AVOID:
     *    1. Optional.of(null) → use Optional.ofNullable()
     *    2. isPresent() + get() → use ifPresent() or orElse()
     *    3. Optional fields → use  nullable fields
     *    4. Optional parameters → use overloading
     *    5. Returning null instead of Optional.empty()
     */
}
