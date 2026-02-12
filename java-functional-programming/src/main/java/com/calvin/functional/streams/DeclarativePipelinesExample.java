package com.calvin.functional.streams;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Principle 3: Declarative Pipelines
 * 
 * Using the Stream API moves logic into clear, readable "Action-Steps".
 * Declarative code describes WHAT to do, not HOW to do it.
 * This improves code readability and maintainability.
 */
public class DeclarativePipelinesExample {

    record Product(String name, String category, double price, int stock) {}

    public static void main(String[] args) {
        System.out.println("=== PRINCIPLE 3: DECLARATIVE PIPELINES ===\n");

        List<Product> products = createSampleProducts();

        demonstrateImperativeVsDeclarative(products);
        demonstrateReadability(products);
        demonstrateChaining(products);
        demonstrateComplexPipeline(products);
    }

    private static List<Product> createSampleProducts() {
        return Arrays.asList(
            new Product("Laptop", "Electronics", 999.99, 15),
            new Product("Mouse", "Electronics", 29.99, 50),
            new Product("Keyboard", "Electronics", 79.99, 30),
            new Product("Desk", "Furniture", 299.99, 10),
            new Product("Chair", "Furniture", 199.99, 20),
            new Product("Monitor", "Electronics", 299.99, 25),
            new Product("Lamp", "Furniture", 49.99, 40)
        );
    }

    private static void demonstrateImperativeVsDeclarative(List<Product> products) {
        System.out.println("❌ IMPERATIVE APPROACH (HOW):");
        imperativeExample(products);

        System.out.println("\n✅ DECLARATIVE APPROACH (WHAT):");
        declarativeExample(products);
        System.out.println();
    }

    private static void imperativeExample(List<Product> products) {
        // Imperative: Tell the computer HOW to do it
        List<String> expensiveElectronics = new ArrayList<>();
        
        for (Product product : products) {
            if (product.category().equals("Electronics")) {  // Manual filtering
                if (product.price() > 100) {                  // More manual filtering
                    expensiveElectronics.add(product.name()); // Manual collection
                }
            }
        }
        
        // Manual sorting
        Collections.sort(expensiveElectronics);
        
        System.out.println("Expensive electronics (imperative): " + expensiveElectronics);
        System.out.println("⚠️  Verbose, error-prone, hard to read!");
    }

    private static void declarativeExample(List<Product> products) {
        // Declarative: Describe WHAT you want
        List<String> expensiveElectronics = products.stream()
                .filter(p -> p.category().equals("Electronics"))  // WHAT: Get electronics
                .filter(p -> p.price() > 100)                     // WHAT: Get expensive ones
                .map(Product::name)                               // WHAT: Get their names
                .sorted()                                          // WHAT: Sort them
                .collect(Collectors.toList());                    // WHAT: Collect to list

        System.out.println("Expensive electronics (declarative): " + expensiveElectronics);
        System.out.println("✅ Clear, concise, reads like business logic!");
    }

    private static void demonstrateReadability(List<Product> products) {
        System.out.println("=== READABILITY COMPARISON ===\n");

        // Business requirement: "Get total value of in-stock electronics"
        
        System.out.println("❌ IMPERATIVE (Multi-step manual process):");
        double totalImperative = 0;
        for (Product product : products) {
            if (product.category().equals("Electronics") && product.stock() > 0) {
                totalImperative += product.price() * product.stock();
            }
        }
        System.out.println("Total value: $" + String.format("%.2f", totalImperative));
        
        System.out.println("\n✅ DECLARATIVE (Reads like business logic):");
        double totalDeclarative = products.stream()
                .filter(p -> p.category().equals("Electronics"))  // Electronics only
                .filter(p -> p.stock() > 0)                       // In stock only
                .mapToDouble(p -> p.price() * p.stock())          // Calculate value
                .sum();                                            // Sum it up
        
        System.out.println("Total value: $" + String.format("%.2f", totalDeclarative));
        System.out.println("✅ Each line explains itself!\n");
    }

    private static void demonstrateChaining(List<Product> products) {
        System.out.println("=== CHAINING OPERATIONS ===\n");

        // Complex query in one fluent chain
        List<String> result = products.stream()
                .filter(p -> p.stock() > 15)           // Step 1: Good stock
                .filter(p -> p.price() < 300)          // Step 2: Affordable
                .sorted(Comparator.comparing(Product::price))  // Step 3: Cheapest first
                .limit(3)                               // Step 4: Top 3
                .map(p -> p.name() + " ($" + p.price() + ")")  // Step 5: Format
                .collect(Collectors.toList());         // Step 6: Collect

        System.out.println("Top 3 affordable products with good stock:");
        result.forEach(System.out::println);
        System.out.println("✅ Complex logic in clear, sequential steps!\n");
    }

    private static void demonstrateComplexPipeline(List<Product> products) {
        System.out.println("=== COMPLEX PIPELINE EXAMPLE ===\n");

        // Business requirement: 
        // "Group products by category, show average price per category, 
        //  but only for categories with more than 2 products"
        
        System.out.println("Business Requirement:");
        System.out.println("- Group by category");
        System.out.println("- Calculate average price per category");
        System.out.println("- Only show categories with 2+ products\n");

        Map<String, Double> categoryAverages = products.stream()
                // Step 1: Group by category
                .collect(Collectors.groupingBy(
                        Product::category,
                        // Step 2: Calculate average price
                        Collectors.averagingDouble(Product::price)
                ))
                .entrySet().stream()
                // Step 3: Filter categories with 2+ products
                .filter(entry -> {
                    long count = products.stream()
                            .filter(p -> p.category().equals(entry.getKey()))
                            .count();
                    return count >= 2;
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        categoryAverages.forEach((category, avgPrice) ->
                System.out.println(category + ": $" + String.format("%.2f", avgPrice)));
        
        System.out.println("\n✅ Complex business logic expressed clearly!\n");
    }

    /**
     * More Declarative Examples
     */
    static class AdditionalExamples {
        
        // Example: Find most expensive product
        public static Optional<Product> findMostExpensive(List<Product> products) {
            return products.stream()
                    .max(Comparator.comparing(Product::price));
        }

        // Example: Check if any out of stock
        public static boolean hasOutOfStock(List<Product> products) {
            return products.stream()
                    .anyMatch(p -> p.stock() == 0);
        }

        // Example: Get all unique categories
        public static Set<String> getAllCategories(List<Product> products) {
            return products.stream()
                    .map(Product::category)
                    .collect(Collectors.toSet());
        }

        // Example: Calculate total inventory value
        public static double getTotalInventoryValue(List<Product> products) {
            return products.stream()
                    .mapToDouble(p -> p.price() * p.stock())
                    .sum();
        }
    }

    /**
     * KEY TAKEAWAYS:
     * 
     * 1. Declarative = WHAT (not HOW)
     *    - Describe the desired result
     *    - Let the framework handle the implementation
     * 
     * 2. Benefits:
     *    - More readable (reads like business requirements)
     *    - Less error-prone (no manual loops)
     *    - Easier to maintain
     *    - Can be optimized by the runtime
     * 
     * 3. Stream operations:
     *    - Intermediate: filter, map, sorted, limit, skip, distinct
     *    - Terminal: collect, forEach, reduce, count, anyMatch, allMatch
     * 
     * 4. When to use:
     *    - ✅ Collection transformations
     *    - ✅ Filtering and searching
     *    - ✅ Aggregations and reductions
     *    - ✅ Complex data processing pipelines
     * 
     * 5. Each operation should:
     *    - Be self-explanatory
     *    - Represent one logical step
     *    - Chain naturally with others
     */
}
