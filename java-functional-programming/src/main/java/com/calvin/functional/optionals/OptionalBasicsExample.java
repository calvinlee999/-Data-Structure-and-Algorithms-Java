package com.calvin.functional.optionals;

import java.util.*;

/**
 * Optional: Handling Null Safely - Basics
 * 
 * Optional<T> is a container that may or may not contain a value.
 * It forces explicit handling of "missing" values, preventing NullPointerExceptions.
 */
public class OptionalBasicsExample {

    record User(String id, String name, String email) {}

    public static void main(String[] args) {
        System.out.println("=== OPTIONAL BASICS ===\n");

        demonstrateCreation();
        demonstrateChecking();
        demonstrateRetrieval();
        demonstrateDefaultValues();
    }

    private static void demonstrateCreation() {
        System.out.println("=== CREATING OPTIONALS ===\n");

        // 1. Optional.of() - value must not be null
        Optional<String> notNull = Optional.of("Hello");
        System.out.println("Optional.of(): " + notNull);

        try {
            Optional<String> willFail = Optional.of(null);  // Throws NullPointerException
        } catch (NullPointerException e) {
            System.out.println("❌ Optional.of(null) throws NPE!");
        }

        // 2. Optional.ofNullable() - safe for possibly null values
        Optional<String> maybeNull = Optional.ofNullable(null);
        System.out.println("Optional.ofNullable(null): " + maybeNull);

        Optional<String> notNullSafe = Optional.ofNullable("World");
        System.out.println("Optional.ofNullable(\"World\"): " + notNullSafe);

        // 3. Optional.empty() - explicitly empty
        Optional<String> empty = Optional.empty();
        System.out.println("Optional.empty(): " + empty);

        System.out.println("\n✅ Choose the right creation method!\n");
    }

    private static void demonstrateChecking() {
        System.out.println("=== CHECKING FOR VALUES ===\n");

        Optional<String> present = Optional.of("Value");
        Optional<String> absent = Optional.empty();

        // isPresent() - check if value exists
        System.out.println("present.isPresent(): " + present.isPresent());
        System.out.println("absent.isPresent(): " + absent.isPresent());

        // isEmpty() - check if empty (Java 11+)
        System.out.println("present.isEmpty(): " + present.isEmpty());
        System.out.println("absent.isEmpty(): " + absent.isEmpty());

        // ifPresent() - execute code if value exists
        System.out.println("\nifPresent() usage:");
        present.ifPresent(value -> System.out.println("  Value: " + value));
        absent.ifPresent(value -> System.out.println("  This won't print"));

        // ifPresentOrElse() - execute code for both cases (Java 9+)
        System.out.println("\nifPresentOrElse() usage:");
        present.ifPresentOrElse(
                value -> System.out.println("  Has value: " + value),
                () -> System.out.println("  No value")
        );

        absent.ifPresentOrElse(
                value -> System.out.println("  Has value: " + value),
                () -> System.out.println("  No value (absent)")
        );

        System.out.println("\n✅ Explicit handling of presence!\n");
    }

    private static void demonstrateRetrieval() {
        System.out.println("=== RETRIEVING VALUES ===\n");

        Optional<String> present = Optional.of("Hello");
        Optional<String> absent = Optional.empty();

        // get() - DANGEROUS! Throws if empty
        System.out.println("❌ get() method (avoid):");
        System.out.println("present.get(): " + present.get());

        try {
            String value = absent.get(); // Throws NoSuchElementException!
        } catch (NoSuchElementException e) {
            System.out.println("absent.get() throws exception!");
        }

        System.out.println("\n✅ Safe retrieval methods (use these):");

        // orElse() - provide default value
        String value1 = present.orElse("default");
        String value2 = absent.orElse("default");
        System.out.println("present.orElse(\"default\"): " + value1);
        System.out.println("absent.orElse(\"default\"): " + value2);

        // orElseGet() - provide default via supplier (lazy evaluation)
        String value3 = present.orElseGet(() -> "computed default");
        String value4 = absent.orElseGet(() -> {
            System.out.println("  Computing default...");
            return "computed default";
        });
        System.out.println("present.orElseGet(): " + value3);
        System.out.println("absent.orElseGet(): " + value4);

        // orElseThrow() - throw custom exception
        System.out.println("\norElseThrow() usage:");
        String value5 = present.orElseThrow(() -> new IllegalStateException("Missing!"));
        System.out.println("present.orElseThrow(): " + value5);

        try {
            String value6 = absent.orElseThrow(() -> new IllegalStateException("Missing!"));
        } catch (IllegalStateException e) {
            System.out.println("absent.or ElseThrow(): " + e.getMessage());
        }

        System.out.println("\n✅ Always provide fallbacks!\n");
    }

    private static void demonstrateDefaultValues() {
        System.out.println("=== DEFAULT VALUE PATTERNS ===\n");

        // Example: User lookup
        User user = findUser("123").orElse(new User("0", "Guest", "guest@example.com"));
        System.out.println("Found user or guest: " + user.name());

        // Example: Configuration value
        int timeout = getConfigValue("timeout")
                .map(Integer::parseInt)
                .orElse(30); // Default 30 seconds
        System.out.println("Timeout: " + timeout + " seconds");

        // Example: Lazy default computation
        String expensive = findCachedValue("key")
                .orElseGet(() -> {
                    System.out.println("  Cache miss, computing value...");
                    return "computed value";
                });
        System.out.println("Result: " + expensive);

        System.out.println("\n✅ Flexible default handling!\n");
    }

    // Helper methods
    private static Optional<User> findUser(String id) {
        // Simulate database lookup
        if ("123".equals(id)) {
            return Optional.of(new User("123", "Alice", "alice@example.com"));
        }
        return Optional.empty();
    }

    private static Optional<String> getConfigValue(String key) {
        // Simulate config lookup
        Map<String, String> config = Map.of("port", "8080", "host", "localhost");
        return Optional.ofNullable(config.get(key));
    }

    private static Optional<String> findCachedValue(String key) {
        // Simulate cache miss
        return Optional.empty();
    }

    /**
     * KEY TAKEAWAYS:
     * 
     * 1. Creation:
     *    - Optional.of(value) - value must not be null
     *    - Optional.ofNullable(value) - safe for null
     *    - Optional.empty() - explicitly empty
     * 
     * 2. Checking:
     *    - isPresent() / isEmpty()
     *    - ifPresent(consumer)
     *    - ifPresentOrElse(consumer, runnable)
     * 
     * 3. Retrieval:
     *    - ❌ get() - avoid (throws if empty)
     *    - ✅ orElse(default) - eager default
     *    - ✅ orElseGet(supplier) - lazy default
     *    - ✅ orElseThrow(supplier) - custom exception
     * 
     * 4. Best Practices:
     *    - Never call get() without checking
     *    - Use orElse() for simple defaults
     *    - Use orElseGet() for expensive defaults
     *    - Use orElseThrow() for required values
     */
}
