package com.calvin.lambdas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.*;

/**
 * Lambda Expressions in Spring Boot 3.2 + Java 21
 * 
 * <p>FinTech Principal Engineer's Guide to Functional Programming in Production</p>
 * 
 * <h2>What Are Lambda Expressions?</h2>
 * <p>
 * Lambda expressions are anonymous functions that implement functional interfaces.
 * Think of them as "code as data" - you can pass behavior around like variables.
 * </p>
 * 
 * <h2>Syntax Evolution</h2>
 * <pre>
 * // Java 7: Anonymous Inner Class (Verbose)
 * Function&lt;BigDecimal, BigDecimal&gt; converter = new Function&lt;&gt;() {
 *     &#64;Override
 *     public BigDecimal apply(BigDecimal usd) {
 *         return usd.multiply(BigDecimal.valueOf(0.92));
 *     }
 * };
 * 
 * // Java 8+: Lambda Expression (Concise)
 * Function&lt;BigDecimal, BigDecimal&gt; converter = usd -&gt; usd.multiply(BigDecimal.valueOf(0.92));
 * </pre>
 * 
 * <h2>Real-World FinTech Use Cases</h2>
 * <ul>
 *   <li><b>Function</b> - Currency conversion, price calculations</li>
 *   <li><b>Predicate</b> - KYC validation, fraud detection rules</li>
 *   <li><b>Consumer</b> - Transaction notifications, audit logging</li>
 *   <li><b>Supplier</b> - ID generation, correlation ID creation</li>
 *   <li><b>BiFunction</b> - Risk scoring, fee calculations</li>
 * </ul>
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 * @version 1.0.0
 * @since 2026-02-15
 */
@Component
public class LambdaExpressionsDemo implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║  Lambda Expressions in Spring Boot 3.2 + Java 21         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

        demonstrateFunction();
        demonstratePredicate();
        demonstrateConsumer();
        demonstrateSupplier();
        demonstrateBiFunction();
        demonstrateMethodReferences();
    }

    /**
     * Function&lt;T, R&gt; - Transforms input T into result R
     * 
     * <p><b>FinTech Example:</b> Currency Conversion</p>
     * <pre>
     * // Trading Desk: Convert USD to EUR based on current market rate
     * // This is a pure function - same input always produces same output (referential transparency)
     * </pre>
     */
    private void demonstrateFunction() {
        System.out.println("1️⃣  Function<T, R> - Currency Conversion");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Pure function - no side effects, referentially transparent
        Function<BigDecimal, BigDecimal> usdToEur = 
            usd -> usd.multiply(BigDecimal.valueOf(0.92)).setScale(2, RoundingMode.HALF_UP);

        BigDecimal usdAmount = new BigDecimal("1000.00");
        BigDecimal eurAmount = usdToEur.apply(usdAmount);

        System.out.println("💵 USD Amount: $" + usdAmount);
        System.out.println("💶 EUR Amount: €" + eurAmount);
        System.out.println("✅ Pure Function - No side effects, same input = same output\n");

        // Function Composition - Building complex transformations from simple ones
        Function<BigDecimal, BigDecimal> addFee = amount -> amount.multiply(BigDecimal.valueOf(1.02));
        Function<BigDecimal, BigDecimal> convertWithFee = usdToEur.andThen(addFee);

        BigDecimal eurWithFee = convertWithFee.apply(usdAmount);
        System.out.println("💶 EUR with 2% Fee: €" + eurWithFee);
        System.out.println("🔗 Function Composition - andThen() creates pipeline\n");
    }

    /**
     * Predicate&lt;T&gt; - Tests a condition and returns boolean
     * 
     * <p><b>FinTech Example:</b> KYC (Know Your Customer) Validation</p>
     * <pre>
     * // Compliance: Check if customer meets legal banking requirements
     * // Used extensively in filtering streams and conditional logic
     * </pre>
     */
    private void demonstratePredicate() {
        System.out.println("2️⃣  Predicate<T> - KYC Validation");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Single validation rule
        Predicate<Integer> isAdult = age -> age >= 18;
        
        // Combining predicates with AND logic
        Predicate<Integer> isEligibleForPremium = 
            isAdult.and(age -> age <= 65);

        int customerAge = 25;
        System.out.println("👤 Customer Age: " + customerAge);
        System.out.println("✅ Is Adult (≥18): " + isAdult.test(customerAge));
        System.out.println("✅ Eligible for Premium (18-65): " + isEligibleForPremium.test(customerAge));
        
        // Predicate composition for complex business rules
        Predicate<Integer> highRisk = age -> age < 18 || age > 75;
        Predicate<Integer> acceptableRisk = highRisk.negate();
        
        System.out.println("🔗 Acceptable Risk: " + acceptableRisk.test(customerAge));
        System.out.println("💡 Predicate Composition - and(), or(), negate()\n");
    }

    /**
     * Consumer&lt;T&gt; - Accepts input and performs side-effect (returns void)
     * 
     * <p><b>FinTech Example:</b> Transaction Notification</p>
     * <pre>
     * // Customer Engagement: Send push notification after successful payment
     * // This IS a side-effect by design - we want to notify external systems
     * </pre>
     */
    private void demonstrateConsumer() {
        System.out.println("3️⃣  Consumer<T> - Transaction Notification");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Consumer for single action
        Consumer<Transaction> sendNotification = 
            tx -> System.out.println("📱 Push: $" + tx.amount() + " charged to your card ending in " + tx.cardLast4());

        // Consumer chaining - execute multiple side-effects in sequence
        Consumer<Transaction> auditLog = 
            tx -> System.out.println("📝 Audit: Transaction " + tx.id() + " logged at " + LocalDateTime.now());

        Consumer<Transaction> completeFlow = 
            sendNotification.andThen(auditLog);

        Transaction tx = new Transaction("TX123", new BigDecimal("45.99"), "4242");
        completeFlow.accept(tx);
        
        System.out.println("🔗 Consumer Chaining - andThen() for sequential side-effects\n");
    }

    /**
     * Supplier&lt;T&gt; - Provides a value without taking any input
     * 
     * <p><b>FinTech Example:</b> Transaction ID Generation</p>
     * <pre>
     * // Ledger System: Generate unique transaction IDs for distributed tracing
     * // Lazy evaluation - the value is computed only when needed
     * </pre>
     */
    private void demonstrateSupplier() {
        System.out.println("4️⃣  Supplier<T> - Transaction ID Generation");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // Supplier for lazy value generation
        Supplier<String> transactionIdGenerator = 
            () -> "TX-" + System.currentTimeMillis() + "-" + Math.random();

        System.out.println("🆔 Generated ID 1: " + transactionIdGenerator.get());
        System.out.println("🆔 Generated ID 2: " + transactionIdGenerator.get());
        System.out.println("💡 Each call to get() produces a new value\n");

        // Supplier for expensive computation (lazy evaluation)
        Supplier<BigDecimal> marketRateSupplier = () -> {
            System.out.println("  📊 Fetching real-time market rate from API...");
            return BigDecimal.valueOf(0.92); // Simulated API call
        };

        System.out.println("💡 Supplier defined but not executed yet");
        System.out.println("💶 Market Rate: " + marketRateSupplier.get()); // Now it executes
        System.out.println("✅ Lazy Evaluation - Computed only when .get() is called\n");
    }

    /**
     * BiFunction&lt;T, U, R&gt; - Accepts two inputs and produces a result
     * 
     * <p><b>FinTech Example:</b> Loan Risk Scoring</p>
     * <pre>
     * // Risk Management: Combine credit score and income to calculate risk rating
     * // This is a pure function that supports complex business logic
     * </pre>
     */
    private void demonstrateBiFunction() {
        System.out.println("5️⃣  BiFunction<T, U, R> - Loan Risk Scoring");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        // BiFunction for two-input computation
        BiFunction<Integer, BigDecimal, String> calculateRiskRating = (creditScore, annualIncome) -> {
            BigDecimal ratio = new BigDecimal(creditScore).divide(annualIncome, 4, RoundingMode.HALF_UP);
            if (ratio.compareTo(BigDecimal.valueOf(0.01)) > 0) return "LOW RISK";
            if (ratio.compareTo(BigDecimal.valueOf(0.005)) > 0) return "MEDIUM RISK";
            return "HIGH RISK";
        };

        int creditScore = 750;
        BigDecimal annualIncome = new BigDecimal("80000");
        String riskRating = calculateRiskRating.apply(creditScore, annualIncome);

        System.out.println("📊 Credit Score: " + creditScore);
        System.out.println("💰 Annual Income: $" + annualIncome);
        System.out.println("⚠️  Risk Rating: " + riskRating);
        System.out.println("✅ BiFunction - Combines multiple inputs into business logic\n");
    }

    /**
     * Method References - Shorthand for lambdas that call existing methods
     * 
     * <p><b>Types of Method References:</b></p>
     * <ul>
     *   <li>Static Method: <code>ClassName::staticMethod</code></li>
     *   <li>Instance Method: <code>instance::instanceMethod</code></li>
     *   <li>Constructor: <code>ClassName::new</code></li>
     * </ul>
     */
    private void demonstrateMethodReferences() {
        System.out.println("6️⃣  Method References - Clean Syntax");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        List<String> transactions = List.of("TX-001", "TX-002", "TX-003");

        System.out.println("Lambda Syntax:");
        transactions.forEach(tx -> System.out.println("  " + tx));

        System.out.println("\nMethod Reference Syntax (equivalent):");
        transactions.forEach(System.out::println);
        
        System.out.println("\n✅ Method References - Cleaner syntax when lambda just calls a method\n");
    }

    /**
     * Immutable Transaction Record (Java 14+, finalized in Java 16)
     * 
     * <p><b>Records are perfect for FinTech:</b></p>
     * <ul>
     *   <li>Immutable by default (thread-safe)</li>
     *   <li>Automatic equals(), hashCode(), toString()</li>
     *   <li>Perfect for data transfer and domain events</li>
     * </ul>
     */
    private record Transaction(String id, BigDecimal amount, String cardLast4) {}
}
