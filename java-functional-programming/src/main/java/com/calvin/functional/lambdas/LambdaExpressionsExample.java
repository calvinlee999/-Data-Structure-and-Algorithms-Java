package com.calvin.functional.lambdas;

import java.util.*;
import java.util.function.*;

/**
 * Lambda Expressions: The Foundation of Functional Java
 * 
 * Lambda expressions provide a clear and concise way to represent one method interface
 * using an expression. They enable functional programming in Java.
 * 
 * Syntax: (parameters) -> expression
 *     or: (parameters) -> { statements; }
 */
public class LambdaExpressionsExample {

    public static void main(String[] args) {
        System.out.println("=== LAMBDA EXPRESSIONS ===\n");

        demonstrateLambdaSyntax();
        demonstrateLambdaTypes();
        demonstrateMethodReferences();
        demonstrateLambdaScope();
        demonstrateRealWorldExamples();
    }

    private static void demonstrateLambdaSyntax() {
        System.out.println("=== LAMBDA SYNTAX VARIATIONS ===\n");

        // 1. No parameters
        Runnable noParams = () -> System.out.println("No parameters");
        noParams.run();

        // 2. Single parameter (parentheses optional)
        Consumer<String> singleParam = message -> System.out.println("Message: " + message);
        Consumer<String> singleParamWithParens = (message) -> System.out.println("Message: " + message);
        singleParam.accept("Hello");

        // 3. Multiple parameters (parentheses required)
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("5 + 3 = " + add.apply(5, 3));

        // 4. Explicit type declarations
        BiFunction<Integer, Integer, Integer> multiply = (Integer a, Integer b) -> a * b;
        System.out.println("5 * 3 = " + multiply.apply(5, 3));

        // 5. Single expression (implicit return)
        Function<Integer, Integer> square = x -> x * x;
        System.out.println("Square of 5: " + square.apply(5));

        // 6. Block body (explicit return)
        Function<Integer, Integer> squareWithBlock = x -> {
            int result = x * x;
            System.out.println("Calculating square of " + x);
            return result;
        };
        System.out.println("Result: " + squareWithBlock.apply(5));

        // 7. Multi-line block
        BiConsumer<String, Integer> printInfo = (name, age) -> {
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Category: " + (age >= 18 ? "Adult" : "Minor"));
        };
        printInfo.accept("Alice", 25);

        System.out.println();
    }

    private static void demonstrateLambdaTypes() {
        System.out.println("=== LAMBDA WITH DIFFERENT FUNCTIONAL INTERFACES ===\n");

        // Predicate<T>: T -> boolean
        Predicate<String> isEmpty = s -> s.isEmpty();
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("Is '' empty? " + isEmpty.test(""));
        System.out.println("Is 4 even? " + isEven.test(4));

        // Function<T, R>: T -> R
        Function<String, Integer> length = s -> s.length();
        Function<Integer, String> toHex = n -> Integer.toHexString(n);
        System.out.println("Length of 'Hello': " + length.apply("Hello"));
        System.out.println("255 in hex: " + toHex.apply(255));

        // Consumer<T>: T -> void
        Consumer<String> print = s -> System.out.println("Value: " + s);
        Consumer<List<String>> printAll = list -> list.forEach(System.out::println);
        print.accept("Test");

        // Supplier<T>: () -> T
        Supplier<Double> random = () -> Math.random();
        Supplier<String> uuid = () -> UUID.randomUUID().toString();
        System.out.println("Random: " + random.get());
        System.out.println("UUID: " + uuid.get().substring(0, 8) + "...");

        // BiFunction<T, U, R>: (T, U) -> R
        BiFunction<String, String, String> concat = (a, b) -> a + " " + b;
        System.out.println("Concat: " + concat.apply("Hello", "World"));

        // UnaryOperator<T>: T -> T (specialization of Function)
        UnaryOperator<Integer> increment = n -> n + 1;
        UnaryOperator<String> uppercase = String::toUpperCase;
        System.out.println("Increment 5: " + increment.apply(5));
        System.out.println("Uppercase: " + uppercase.apply("hello"));

        // BinaryOperator<T>: (T, T) -> T (specialization of BiFunction)
        BinaryOperator<Integer> max = (a, b) -> a > b ? a : b;
        BinaryOperator<String> longer = (a, b) -> a.length() > b.length() ? a : b;
        System.out.println("Max(10, 20): " + max.apply(10, 20));
        System.out.println("Longer('hi', 'hello'): " + longer.apply("hi", "hello"));

        System.out.println();
    }

    private static void demonstrateMethodReferences() {
        System.out.println("=== METHOD REFERENCES (Shorthand Lambdas) ===\n");

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // 1. Reference to static method
        // Lambda: x -> ClassName.staticMethod(x)
        // Method reference: ClassName::staticMethod
        Function<String, Integer> parseInt = Integer::parseInt;
        System.out.println("Parse '123': " + parseInt.apply("123"));

        // 2. Reference to instance method of particular object
        // Lambda: x -> obj.instanceMethod(x)
        // Method reference: obj::instanceMethod
        String prefix = "Hello, ";
        Function<String, String> greeter = prefix::concat;
        System.out.println(greeter.apply("World"));

        // 3. Reference to instance method of arbitrary object
        // Lambda: (obj, args) -> obj.instanceMethod(args)
        // Method reference: ClassName::instanceMethod
        Function<String, String> toUpper = String::toUpperCase;
        Comparator<String> comparator = String ::compareToIgnoreCase;
        System.out.println("Uppercase: " + toUpper.apply("hello"));

        // 4. Reference to constructor
        // Lambda: () -> new ClassName()
        // Method reference: ClassName::new
        Supplier<ArrayList<String>> listSupplier = ArrayList::new;
        Function<Integer, ArrayList<String>> sizedListSupplier = ArrayList::new;
        List<String> newList = listSupplier.get();
        System.out.println("Created empty list: " + newList);

        // Common usage in streams
        System.out.println("\nMethod references in streams:");
        names.stream()
                .map(String::toUpperCase)  // Instance method reference
                .forEach(System.out::println);  // Instance method reference

        System.out.println();
    }

    private static void demonstrateLambdaScope() {
        System.out.println("=== LAMBDA SCOPE & VARIABLE CAPTURE ===\n");

        // 1. Accessing local variables (must be effectively final)
        String prefix = "Mr. ";  // Effectively final (not modified)
        Function<String, String> addPrefix = name -> prefix + name;
        System.out.println(addPrefix.apply("Smith"));

        // prefix = "Mrs. ";  // ❌ Would cause compilation error - can't modify captured variable

        // 2. Accessing instance variables (allowed)
        class Person {
            private String title = "Dr. ";

            public void demonstrate() {
                Function<String, String> fullName = name -> title + name;  // Can access 'title'
                System.out.println(fullName.apply("Jones"));
                
                // Can modify instance variable
                title = "Prof. ";
                System.out.println(fullName.apply("Brown"));
            }
        }
        new Person().demonstrate();

        // 3. Effectively final requirement
        int multiplier = 2;  // Effectively final
        Function<Integer, Integer> multiply = n -> n * multiplier;
        System.out.println("5 * 2 = " + multiply.apply(5));

        // multiplier = 3;  // ❌ Would break effectively final requirement

        // 4. 'this' reference in lambdas
        class Calculator {
            private int base = 10;

            public void demonstrate() {
                // Lambda uses enclosing class's 'this'
                Function<Integer, Integer> addBase = n -> n + this.base;
                System.out.println("5 + base(10) = " + addBase.apply(5));
            }
        }
        new Calculator().demonstrate();

        System.out.println();
    }

    private static void demonstrateRealWorldExamples() {
        System.out.println("=== REAL-WORLD LAMBDA EXAMPLES ===\n");

        List<String> products = Arrays.asList("Laptop", "Mouse", "Keyboard", "Monitor");

        // 1. Custom  sorting
        System.out.println("Sorted by length:");
        products.stream()
                .sorted((a, b) -> Integer.compare(a.length(), b.length()))
                .forEach(p -> System.out.println("  " + p));

        // 2. Filtering with complex logic
        Predicate<String> isLongName = name -> {
            boolean result = name.length() > 5;
            if (result) {
                System.out.println("  '" + name + "' is a long name");
            }
            return result;
        };
        
        System.out.println("\nLong names:");
        products.stream().filter(isLongName).count();

        // 3. Transformation pipeline
        System.out.println("\nTransformed products:");
        products.stream()
                .filter(p -> p.startsWith("M"))
                .map(p -> p.toUpperCase())
                .map(p -> "Product: " + p)
                .forEach(System.out::println);

        // 4. Grouping with lambdas
        Map<Integer, List<String>> byLength = products.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        p -> p.length()  // Key extractor lambda
                ));
        
        System.out.println("\nGrouped by length: " + byLength);

        // 5. Custom comparators
        Comparator<String> byLengthThenAlpha = 
                Comparator.comparing((String s) -> s.length())
                          .thenComparing(s -> s);
        
        System.out.println("\nSorted by length, then alphabetically:");
        products.stream()
                .sorted(byLengthThenAlpha)
                .forEach(p -> System.out.println("  " + p));

        System.out.println();
    }

    /**
     * KEY TAKEAWAYS:
     * 
     * 1. Lambda Syntax:
     *    - () -> expression                    // No parameters
     *    - x -> expression                     // Single parameter
     *    - (x, y) -> expression               // Multiple parameters
     *    - (x, y) -> { statements; }          // Block body
     * 
     * 2. Method References (shorthand):
     *    - ClassName::staticMethod            // Static method
     *    - object::instanceMethod             // Instance method of object
     *    - ClassName::instanceMethod          // Instance method of arbitrary object
     *    - ClassName::new                     // Constructor
     * 
     * 3. Variable Capture:
     *    - Local variables must be effectively final
     *    - Can access instance variables
     *    - Cannot modify captured local variables
     *    - 'this' refers to enclosing class
     * 
     * 4. Common Functional Interfaces:
     *    - Predicate<T>: T -> boolean
     *    - Function<T, R>: T -> R
     *    - Consumer<T>: T -> void
     *    - Supplier<T>: () -> T
     *    - BiFunction<T, U, R>: (T, U) -> R
     *    - UnaryOperator<T>: T -> T
     *    - BinaryOperator<T>: (T, T) -> T
     * 
     * 5. Best Practices:
     *    - ✅ Keep lambdas short and focused
     *    - ✅ Use method references when possible
     *    - ✅ Prefer standard functional interfaces
     *    - ✅ Name complex lambdas as variables
     *    - ❌ Avoid side effects in lambdas
     *    - ❌ Don't modify captured variables
     */
}
