package com.calvin.functional.streams;

import java.util.*;
import java.util.stream.*;

/**
 * Stream API Basics: Filter, Map, Reduce
 * 
 * Streams enable functional-style operations on collections.
 * Learn the fundamental operations: filter, map, and reduce.
 */
public class StreamBasicsExample {

    record Product(String name, String category, double price, int quantity) {}

    public static void main(String[] args) {
        System.out.println("=== STREAM API BASICS ===\n");

        List<Product> products = createProducts();

        demonstrateFilter(products);
        demonstrateMap(products);
        demonstrateReduce(products);
        demonstrateCombinedOperations(products);
        demonstrateCollectors(products);
    }

    private static List<Product> createProducts() {
        return Arrays.asList(
                new Product("Laptop", "Electronics", 999.99, 5),
                new Product("Mouse", "Electronics", 29.99, 50),
                new Product("Keyboard", "Electronics", 79.99, 30),
                new Product("Desk", "Furniture", 299.99, 10),
                new Product("Chair", "Furniture", 199.99, 15),
                new Product("Monitor", "Electronics", 299.99, 20),
                new Product("Lamp", "Furniture", 49.99, 25)
        );
    }

    private static void demonstrateFilter(List<Product> products) {
        System.out.println("=== FILTER: SELECT ELEMENTS ===\n");

        // Filter expensive products
        List<Product> expensive = products.stream()
                .filter(p -> p.price() > 100)
                .toList();

        System.out.println("Expensive products (> $100):");
        expensive.forEach(p -> System.out.println("  " + p.name() + ": $" + p.price()));

        // Filter by category
        List<Product> electronics = products.stream()
                .filter(p -> p.category().equals("Electronics"))
                .toList();

        System.out.println("\nElectronics:");
        electronics.forEach(p -> System.out.println("  " + p.name()));

        // Multiple filters (chaining)
        List<Product> affordableElectronics = products.stream()
                .filter(p -> p.category().equals("Electronics"))
                .filter(p -> p.price() < 100)
                .toList();

        System.out.println("\nAffordable electronics (< $100):");
        affordableElectronics.forEach(p -> System.out.println("  " + p.name() + ": $" + p.price()));

        System.out.println();
    }

    private static void demonstrateMap(List<Product> products) {
        System.out.println("=== MAP: TRANSFORM ELEMENTS ===\n");

        // Extract product names
        List<String> names = products.stream()
                .map(Product::name)
                .toList();

        System.out.println("Product names: " + names);

        // Transform to uppercase
        List<String> upperNames = products.stream()
                .map(Product::name)
                .map(String::toUpperCase)
                .toList();

        System.out.println("\nUppercase names:" + upperNames);

        // Calculate discounted prices
        List<Double> discountedPrices = products.stream()
                .map(Product::price)
                .map(price -> price * 0.9)  // 10% discount
                .toList();

        System.out.println("\nDiscounted prices:");
        discountedPrices.forEach(price -> System.out.println("  $" + String.format("%.2f", price)));

        // Complex transformation
        List<String> descriptions = products.stream()
                .map(p -> p.name() + " - $" + p.price() + " (" + p.quantity() + " in stock)")
                .toList();

        System.out.println("\nProduct descriptions:");
        descriptions.forEach(d -> System.out.println("  " + d));

        System.out.println();
    }

    private static void demonstrateReduce(List<Product> products) {
        System.out.println("=== REDUCE: COMBINE TO SINGLE VALUE ===\n");

        // Sum all prices
        double totalValue = products.stream()
                .mapToDouble(Product::price)
                .sum();

        System.out.println("Total value: $" + String.format("%.2f", totalValue));

        // Count products
        long count = products.stream().count();
        System.out.println("Total products: " + count);

        // Find max price
        OptionalDouble maxPrice = products.stream()
                .mapToDouble(Product::price)
                .max();

        maxPrice.ifPresent(price -> 
                System.out.println("Max price: $" + String.format("%.2f", price)));

        // Find min price
        OptionalDouble minPrice = products.stream()
                .mapToDouble(Product::price)
                .min();

        minPrice.ifPresent(price -> 
                System.out.println("Min price: $" + String.format("%.2f", price)));

        // Calculate average
        OptionalDouble avgPrice = products.stream()
                .mapToDouble(Product::price)
                .average();

        avgPrice.ifPresent(price -> 
                System.out.println("Average price: $" + String.format("%.2f", price)));

        // Custom reduce: concatenate names
        String allNames = products.stream()
                .map(Product::name)
                .reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);

        System.out.println("\nAll product names: " + allNames);

        System.out.println();
    }

    private static void demonstrateCombinedOperations(List<Product> products) {
        System.out.println("=== COMBINED OPERATIONS ===\n");

        // Complex pipeline: Filter → Map → Reduce
        double totalElectronicsValue = products.stream()
                .filter(p -> p.category().equals("Electronics"))  // Filter
                .mapToDouble(p -> p.price() * p.quantity())        // Map to value
                .sum();                                             // Reduce

        System.out.println("Total electronics inventory value: $" + 
                String.format("%.2f", totalElectronicsValue));

        // Find most expensive product
        Optional<Product> mostExpensive = products.stream()
                .max(Comparator.comparing(Product::price));

        mostExpensive.ifPresent(p -> 
                System.out.println("Most expensive: " + p.name() + " ($" + p.price() + ")"));

        // Get top 3 by price
        List<String> top3 = products.stream()
                .sorted(Comparator.comparing(Product::price).reversed())
                .limit(3)
                .map(Product::name)
                .toList();

        System.out.println("\nTop 3 most expensive:");
        top3.forEach(name -> System.out.println("  - " + name));

        // Count by category
        long electronicsCount = products.stream()
                .filter(p -> p.category().equals("Electronics"))
                .count();

        System.out.println("\nElectronics count: " + electronicsCount);

        System.out.println();
    }

    private static void demonstrateCollectors(List<Product> products) {
        System.out.println("=== COLLECTORS ===\n");

        // Collect to List
        List<String> categories = products.stream()
                .map(Product::category)
                .distinct()
                .toList();

        System.out.println("Categories: " + categories);

        // Collect to Set (automatic deduplication)
        Set<String> uniqueCategories = products.stream()
                .map(Product::category)
                .collect(Collectors.toSet());

        System.out.println("Unique categories: " + uniqueCategories);

        // Joining strings
        String productList = products.stream()
                .map(Product::name)
                .collect(Collectors.joining(", "));

        System.out.println("\nAll products: " + productList);

        // Joining with prefix/suffix
        String formatted = products.stream()
                .map(Product::name)
                .collect(Collectors.joining(", ", "[", "]"));

        System.out.println("Formatted: " + formatted);

        System.out.println();
    }

    /**
     * KEY TAKEAWAYS:
     * 
     * 1. Stream Operations:
     *    - Intermediate: filter, map, sorted, limit, distinct
     *      → Return a new stream, can be chained
     *    - Terminal: collect, forEach, reduce, count, sum
     *      → Trigger evaluation, end the pipeline
     * 
     * 2. Filter:
     *    - Selects elements matching a condition
     *    - Takes a Predicate<T>
     *    - Can chain multiple filters
     * 
     * 3. Map:
     *    - Transforms each element
     *    - Takes a Function<T, R>
     *    - Changes the type if needed
     * 
     * 4. Reduce:
     *    - Combines elements into single value
     *    - sum(), count(), max(), min(), average()
     *    - Custom reduce(identity, accumulator)
     * 
     * 5. Collectors:
     *    - toList(), toSet(), toMap()
     *    - joining() for strings
     *    - groupingBy(), partitioningBy() (advanced)
     * 
     * 6. Best Practices:
     *    - ✅ Chain operations for readability
     *    - ✅ Use method references when possible
     *    - ✅ Keep lambdas short and clear
     *    - ✅ Don't modify external state in streams
     *    - ✅ Use appropriate terminal operation
     */
}
