package com.calvin.functional.optionals;

import java.util.*;

/**
 * Optional: Chaining Operations Safely
 * 
 * Optional allows chaining transformations safely without null checks.
 * Use map(), flatMap(), and filter() to build transformation pipelines.
 */
public class OptionalChainingExample {

    record Address(String street, String city, String zipCode) {}
    record User(String id, String name, Address address, String email) {}
    record Order(String id, User user, double amount) {}

    public static void main(String[] args) {
        System.out.println("=== OPTIONAL CHAINING ===\n");

        demonstrateMap();
        demonstrateFlatMap();
        demonstrateFilter();
        demonstrateChaining();
        demonstrateRealWorldExample();
    }

    private static void demonstrateMap() {
        System.out.println("=== MAP: TRANSFORM VALUE ===\n");

        // ❌ Old way (manual null checks)
        System.out.println("❌ Without Optional:");
        User user = findUser("123");
        String email = null;
        if (user != null) {
            email = user.email();
            if (email != null) {
                email = email.toUpperCase();
            }
        }
        System.out.println("Email: " + email);

        // ✅ Optional way (clean chaining)
        System.out.println("\n✅ With Optional:");
        String emailOpt = findUserOptional("123")
                .map(User::email)
                .map(String::toUpperCase)
                .orElse("NO EMAIL");
        System.out.println("Email: " + emailOpt);

        // map() returns empty if any step is empty
        String missing = findUserOptional("999")
                .map(User::email)
                .map(String::toUpperCase)
                .orElse("NO EMAIL");
        System.out.println("Missing user email: " + missing);

        System.out.println("\n✅ Safe null navigation!\n");
    }

    private static void demonstrateFlatMap() {
        System.out.println("=== FLATMAP: HANDLE NESTED OPTIONALS ===\n");

        // Problem: map() with Optional-returning function creates Optional<Optional<T>>
        Optional<Optional<String>> nested = findUserOptional("123")
                .map(user -> getEmailOptional(user)); // Returns Optional<Optional<String>>
        System.out.println("Nested Optional: " + nested);

        // ❌ Would need double unwrapping (ugly!)
        // String email = nested.orElse(Optional.empty()).orElse("NO EMAIL");

        // ✅ flatMap() flattens nested Optionals
        String email = findUserOptional("123")
                .flatMap(user -> getEmailOptional(user))  // Flattens to Optional<String>
                .map(String::toUpperCase)
                .orElse("NO EMAIL");
        System.out.println("Email with flatMap: " + email);

        // Example: Deep navigation
        String city = findUserOptional("123")
                .flatMap(user -> getAddress(user))    // Optional<Address>
                .map(Address::city)                    // Optional<String>
                .orElse("Unknown City");
        System.out.println("City: " + city);

        System.out.println("\n✅ flatMap prevents Optional<Optional<T>>!\n");
    }

    private static void demonstrateFilter() {
        System.out.println("=== FILTER: CONDITIONAL PRESENCE ===\n");

        // Filter keeps value only if predicate is true
        Optional<String> email = findUserOptional("123")
                .map(User::email)
                .filter(e -> e.contains("@"));  // Keep only if valid email

        System.out.println("Valid email: " + email);

        // Filter makes it empty if condition fails
        Optional<String> invalidEmail = Optional.of("notAnEmail")
                .filter(e -> e.contains("@"));

        System.out.println("Invalid email (filtered out): " + invalidEmail);

        // Multiple filters
        Optional<User> verifiedAdult = findUserOptional("123")
                .filter(u -> u.email().contains("@"))     // Has valid email
                .filter(u -> u.name().length() > 2);      // Has real name

        verifiedAdult.ifPresentOrElse(
                u -> System.out.println("Verified user: " + u.name()),
                () -> System.out.println("User not verified")
        );

        System.out.println("\n✅ Declarative conditional logic!\n");
    }

    private static void demonstrateChaining() {
        System.out.println("=== COMPLETE CHAINING EXAMPLE ===\n");

        // Complex navigation chain
        String result = findOrderOptional("ORD123")
                .flatMap(order -> Optional.ofNullable(order.user()))
                .flatMap(user -> getAddress(user))
                .map(Address::zipCode)
                .filter(zip -> zip.length() == 5)
                .orElse("00000");

        System.out.println("Shipping zip code: " + result);

        // Chain with side effects
        findUserOptional("123")
                .filter(u -> u.email() != null)
                .map(User::email)
                .map(String::toLowerCase)
                .ifPresentOrElse(
                        email -> System.out.println("Sending email to: " + email),
                        () -> System.out.println("No valid email")
                );

        System.out.println("\n✅ Complex logic expressed clearly!\n");
    }

    private static void demonstrateRealWorldExample() {
        System.out.println("=== REAL-WORLD EXAMPLE: USER EMAIL RETRIEVAL ===\n");

        // Requirement: Get user's email in uppercase, default to "no-reply@example.com"
        
        System.out.println("Old imperative way:");
        String email1 = getUserEmailOldWay("123");
        System.out.println("Result: " + email1);

        System.out.println("\nNew functional way:");
        String email2 = getUserEmailFunctional("123");
        System.out.println("Result: " + email2);

        System.out.println("\nMissing user:");
        String email3 = getUserEmailFunctional("999");
        System.out.println("Result: " + email3);

        System.out.println("\n✅ Functional approach is cleaner!\n");
    }

    // ❌ Old way: manual null checks
    private static String getUserEmailOldWay(String userId) {
        User user = findUser(userId);
        if (user != null) {
            Address address = user.address();
            if (address != null) {
                String email = user.email();
                if (email != null && !email.isEmpty()) {
                    return email.toUpperCase();
                }
            }
        }
        return "no-reply@example.com";
    }

    // ✅ Functional way: clean chaining
    private static String getUserEmailFunctional(String userId) {
        return findUserOptional(userId)
                .map(User::email)
                .filter(email -> !email.isEmpty())
                .map(String::toUpperCase)
                .orElse("no-reply@example.com");
    }

    // Helper methods
    private static User findUser(String id) {
        if ("123".equals(id)) {
            return new User("123", "Alice", 
                    new Address("123 Main St", "New York", "10001"),
                    "alice@example.com");
        }
        return null;
    }

    private static Optional<User> findUserOptional(String id) {
        return Optional.ofNullable(findUser(id));
    }

    private static Optional<String> getEmailOptional(User user) {
        return Optional.ofNullable(user.email());
    }

    private static Optional<Address> getAddress(User user) {
        return Optional.ofNullable(user.address());
    }

    private static Optional<Order> findOrderOptional(String orderId) {
        if ("ORD123".equals(orderId)) {
            return Optional.of(new Order("ORD123", findUser("123"), 99.99));
        }
        return Optional.empty();
    }

    /**
     * KEY TAKEAWAYS:
     * 
     * 1. map(function):
     *    - Transform the value if present
     *    - Returns Optional with transformed value
     *    - Returns empty if original was empty
     * 
     * 2. flatMap(function):
     *    - Like map, but for functions returning Optional
     *    - Flattens Optional<Optional<T>> to Optional<T>
     *    - Essential for chaining Optional-returning methods
     * 
     * 3. filter(predicate):
     *    - Keeps value only if predicate is true
     *    - Returns empty if predicate fails
     *    - Enables conditional logic in chain
     * 
     * 4. Chaining Benefits:
     *    - No null checks needed
     *    - Reads like a pipeline
     *    - Short-circuits on empty
     *    - Safer than imperative code
     * 
     * 5. Best Practices:
     *    - Use map() for transformations
     *    - Use flatMap() for Optional-returning methods
     *    - Use filter() for conditions
     *    - Chain operations for clarity
     *    - Provide sensible defaults with orElse()
     */
}
