package com.calvin.functional.interfaces;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Principle 1: Immutability
 * 
 * Functional Java discourages changing data; instead, it creates new versions (SSOT).
 * This ensures data integrity and thread safety.
 */
public class ImmutabilityExample {

    // ❌ Mutable class (BAD - can lead to bugs)
    static class MutableUser {
        private String name;
        private int age;

        public MutableUser(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Dangerous! Allows external modification
        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
    }

    // ✅ Immutable class (GOOD - safe and predictable)
    static final class ImmutableUser {
        private final String name;
        private final int age;

        public ImmutableUser(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // No setters! Only getters
        public String getName() { return name; }
        public int getAge() { return age; }

        // To "modify", create a new instance
        public ImmutableUser withName(String newName) {
            return new ImmutableUser(newName, this.age);
        }

        public ImmutableUser withAge(int newAge) {
            return new ImmutableUser(this.name, newAge);
        }

        @Override
        public String toString() {
            return "User{name='" + name + "', age=" + age + "}";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== PRINCIPLE 1: IMMUTABILITY ===\n");

        // Demo 1: Mutable vs Immutable
        demonstrateMutableProblems();
        demonstrateImmutableBenefits();

        // Demo 2: Immutable Collections
        demonstrateImmutableCollections();

        // Demo 3: Functional transformations
        demonstrateFunctionalTransformations();
    }

    private static void demonstrateMutableProblems() {
        System.out.println("❌ MUTABLE OBJECT PROBLEM:");
        
        MutableUser user = new MutableUser("Alice", 25);
        System.out.println("Original: " + user.getName() + ", age " + user.getAge());
        
        // Danger! Someone can modify our object
        modifyUser(user);
        System.out.println("After modification: " + user.getName() + ", age " + user.getAge());
        System.out.println("⚠️  Our object was changed unexpectedly!\n");
    }

    private static void modifyUser(MutableUser user) {
        user.setName("Bob"); // Side effect!
        user.setAge(30);
    }

    private static void demonstrateImmutableBenefits() {
        System.out.println("✅ IMMUTABLE OBJECT BENEFITS:");
        
        ImmutableUser user = new ImmutableUser("Alice", 25);
        System.out.println("Original: " + user);
        
        // To "change", we create a new instance
        ImmutableUser olderUser = user.withAge(26);
        
        System.out.println("Original (unchanged): " + user);
        System.out.println("New instance: " + olderUser);
        System.out.println("✅ Original data is safe!\n");
    }

    private static void demonstrateImmutableCollections() {
        System.out.println("=== IMMUTABLE COLLECTIONS ===\n");

        // ❌ Mutable list (can be modified)
        List<String> mutableList = new ArrayList<>(Arrays.asList("A", "B", "C"));
        System.out.println("Mutable list: " + mutableList);
        mutableList.add("D"); // Can modify!
        System.out.println("After add: " + mutableList + " ❌\n");

        // ✅ Immutable list (cannot be modified)
        List<String> immutableList = List.of("A", "B", "C");
        System.out.println("Immutable list: " + immutableList);
        
        try {
            immutableList.add("D"); // Will throw exception!
        } catch (UnsupportedOperationException e) {
            System.out.println("✅ Cannot modify immutable list!\n");
        }

        // ✅ Creating modified versions without changing original
        List<String> withNewElement = new ArrayList<>(immutableList);
        withNewElement.add("D");
        System.out.println("Original immutable list: " + immutableList);
        System.out.println("New list with 'D': " + withNewElement);
        System.out.println("✅ Original unchanged!\n");
    }

    private static void demonstrateFunctionalTransformations() {
        System.out.println("=== FUNCTIONAL TRANSFORMATIONS (Immutable) ===\n");

        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        System.out.println("Original list: " + numbers);

        // Functional transformation creates NEW list
        List<Integer> doubled = numbers.stream()
                .map(n -> n * 2)
                .collect(Collectors.toList());

        System.out.println("Original (unchanged): " + numbers);
        System.out.println("Doubled (new list): " + doubled);

        // Chaining transformations
        List<Integer> result = numbers.stream()
                .filter(n -> n % 2 == 0)  // Keep even numbers
                .map(n -> n * 2)           // Double them
                .collect(Collectors.toList());

        System.out.println("\nEven numbers doubled: " + result);
        System.out.println("Original still: " + numbers);
        System.out.println("✅ Immutability preserved throughout!\n");
    }

    /**
     * KEY TAKEAWAYS:
     * 
     * 1. Immutable objects cannot be changed after creation
     * 2. To "modify", create a new instance with the changes
     * 3. Use List.of(), Set.of(), Map.of() for immutable collections
     * 4. Functional operations (map, filter) create new collections
     * 5. Benefits:
     *    - Thread-safe by default
     *    - No unexpected side effects
     *    - Easier to reason about
     *    - Single Source of Truth (SSOT)
     */
}
