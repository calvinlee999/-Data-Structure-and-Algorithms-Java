package com.calvin.functional.combinators;

import java.util.function.Function;

/**
 * Combinator Pattern: Customer Validation
 * 
 * The Combinator pattern builds complex functionality from simpler, reusable functions.
 * This example shows a complete validation system using combinators.
 */
public class CustomerValidationExample {

    // Domain models
    record Customer(String name, String email, String phone, int age) {}

    enum ValidationResult {
        SUCCESS,
        EMAIL_NOT_VALID,
        PHONE_NOT_VALID,
        IS_NOT_ADULT,
        NAME_TOO_SHORT,
        VALIDATION_FAILED;

        public boolean isValid() {
            return this == SUCCESS;
        }
    }

    // The Combinator Interface
    @FunctionalInterface
    public interface CustomerValidation extends Function<Customer, ValidationResult> {

        // ========== PRIMITIVE VALIDATORS ==========
        
        static CustomerValidation isEmailValid() {
            return customer -> customer.email().contains("@") && customer.email().contains(".")
                    ? ValidationResult.SUCCESS
                    : ValidationResult.EMAIL_NOT_VALID;
        }

        static CustomerValidation isPhoneValid() {
            return customer -> customer.phone().matches("\\d{10}")
                    ? ValidationResult.SUCCESS
                    : ValidationResult.PHONE_NOT_VALID;
        }

        static CustomerValidation isAdult() {
            return customer -> customer.age() >= 18
                    ? ValidationResult.SUCCESS
                    : ValidationResult.IS_NOT_ADULT;
        }

        static CustomerValidation hasValidName() {
            return customer -> customer.name() != null && customer.name().length() >= 2
                    ? ValidationResult.SUCCESS
                    : ValidationResult.NAME_TOO_SHORT;
        }

        // ========== COMBINATORS ==========

        // AND combinator: Both validations must pass
        default CustomerValidation and(CustomerValidation other) {
            return customer -> {
                ValidationResult result = this.apply(customer);
                return result.isValid() ? other.apply(customer) : result;
            };
        }

        // OR combinator: At least one validation must pass
        default CustomerValidation or(CustomerValidation other) {
            return customer -> {
                ValidationResult result = this.apply(customer);
                return result.isValid() ? result : other.apply(customer);
            };
        }

        // NOT combinator: Inverts the validation
        default CustomerValidation negate() {
            return customer -> {
                ValidationResult result = this.apply(customer);
                return result.isValid()
                        ? ValidationResult.VALIDATION_FAILED
                        : ValidationResult.SUCCESS;
            };
        }
    }

    public static void main(String[] args) {
        System.out.println("=== COMBINATOR PATTERN: CUSTOMER VALIDATION ===\n");

        demonstratePrimitiveValidators();
        demonstrateAndCombinator();
        demonstrateOrCombinator();
        demonstrateComplexCombinations();
        demonstrateRealWorldUsage();
    }

    private static void demonstratePrimitiveValidators() {
        System.out.println("=== PRIMITIVE VALIDATORS ===\n");

        Customer validCustomer = new Customer("Alice Smith", "alice@example.com", "1234567890", 25);
        Customer invalidEmail = new Customer("Bob", "bob.com", "1234567890", 30);
        Customer invalidPhone = new Customer("Charlie", "charlie@example.com", "123", 22);
        Customer minor = new Customer("David", "david@example.com", "1234567890", 15);

        // Test each primitive validator
        System.out.println("Valid customer:");
        testValidation(CustomerValidation.isEmailValid(), validCustomer);
        testValidation(CustomerValidation.isPhoneValid(), validCustomer);
        testValidation(CustomerValidation.isAdult(), validCustomer);

        System.out.println("\nInvalid email:");
        testValidation(CustomerValidation.isEmailValid(), invalidEmail);

        System.out.println("\nInvalid phone:");
        testValidation(CustomerValidation.isPhoneValid(), invalidPhone);

        System.out.println("\nMinor:");
        testValidation(CustomerValidation.isAdult(), minor);

        System.out.println();
    }

    private static void demonstrateAndCombinator() {
        System.out.println("=== AND COMBINATOR ===\n");

        // Combine validators with AND
        CustomerValidation emailAndPhone = CustomerValidation.isEmailValid()
                .and(CustomerValidation.isPhoneValid());

        Customer valid = new Customer("Alice", "alice@example.com", "1234567890", 25);
        Customer invalidPhone = new Customer("Bob", "bob@example.com", "123", 30);

        System.out.println("Valid customer (email AND phone):");
        testValidation(emailAndPhone, valid);

        System.out.println("\nInvalid phone (fails AND):");
        testValidation(emailAndPhone, invalidPhone);

        // Chain multiple ANDs
        CustomerValidation fullValidation = CustomerValidation.isEmailValid()
                .and(CustomerValidation.isPhoneValid())
                .and(CustomerValidation.isAdult())
                .and(CustomerValidation.hasValidName());

        System.out.println("\nFull validation (all checks):");
        testValidation(fullValidation, valid);

        System.out.println();
    }

    private static void demonstrateOrCombinator() {
        System.out.println("=== OR COMBINATOR ===\n");

        // Accept if EITHER condition is true
        CustomerValidation emailOrPhone = CustomerValidation.isEmailValid()
                .or(CustomerValidation.isPhoneValid());

        Customer validEmail = new Customer("Alice", "alice@example.com", "123", 25);
        Customer validPhone = new Customer("Bob", "bob.com", "1234567890", 30);
        Customer neither = new Customer("Charlie", "charlie", "123", 22);

        System.out.println("Valid email (passes OR):");
        testValidation(emailOrPhone, validEmail);

        System.out.println("\nValid phone (passes OR):");
        testValidation(emailOrPhone, validPhone);

        System.out.println("\nNeither valid (fails OR):");
        testValidation(emailOrPhone, neither);

        System.out.println();
    }

    private static void demonstrateComplexCombinations() {
        System.out.println("=== COMPLEX COMBINATIONS ===\n");

        // Business Rule: "Must have (valid email AND valid phone) OR be an adult"
        CustomerValidation complexRule = CustomerValidation.isEmailValid()
                .and(CustomerValidation.isPhoneValid())
                .or(CustomerValidation.isAdult());

        Customer adultWithBadEmail = new Customer("Alice", "alice", "123", 25);
        Customer minorWithValidContact = new Customer("Bob", "bob@example.com", "1234567890", 15);
        Customer minorWithBadContact = new Customer("Charlie", "charlie", "123", 15);

        System.out.println("Adult with bad contact (passes via adult check):");
        testValidation(complexRule, adultWithBadEmail);

        System.out.println("\nMinor with valid contact (passes via email+phone):");
        testValidation(complexRule, minorWithValidContact);

        System.out.println("\nMinor with bad contact (fails both paths):");
        testValidation(complexRule, minorWithBadContact);

        System.out.println();
    }

    private static void demonstrateRealWorldUsage() {
        System.out.println("=== REAL-WORLD USAGE ===\n");

        // Define business rules as named validators
        CustomerValidation basicValidation = CustomerValidation.hasValidName()
                .and(CustomerValidation.isEmailValid());

        CustomerValidation premiumValidation = basicValidation
                .and(CustomerValidation.isPhoneValid())
                .and(CustomerValidation.isAdult());

        // Test customers
        Customer basic = new Customer("Alice", "alice@example.com", "123", 17);
        Customer premium = new Customer("Bob Smith", "bob@example.com", "1234567890", 25);

        System.out.println("Basic customer:");
        ValidationResult result1 = basicValidation.apply(basic);
        System.out.println("  Result: " + result1);

        System.out.println("\nPremium customer:");
        ValidationResult result2 = premiumValidation.apply(premium);
        System.out.println("  Result: " + result2);

        // Usage in application code
        if (premiumValidation.apply(premium).isValid()) {
            System.out.println("\n✅ Premium customer validated - granting access!");
        }

        System.out.println("\n✅ Validators read like business requirements!\n");
    }

    private static void testValidation(CustomerValidation validation, Customer customer) {
        ValidationResult result = validation.apply(customer);
        System.out.println("  Customer: " + customer.name());
        System.out.println("  Result: " + result);
    }

    /**
     * KEY TAKEAWAYS:
     * 
     * 1. Combinator Pattern Structure:
     *    - Primitives: Simple, atomic validators
     *    - Combinators: Methods that combine validators (and, or, negate)
     *    - Returns same type for further composition
     * 
     * 2. Benefits:
     *    - ✅ Highly modular and reusable
     *    - ✅ Reads like business requirements
     *    - ✅ Easy to test each piece separately
     *    - ✅ Composable in any combination
     *    - ✅ Type-safe composition
     * 
     * 3. Common Combinators:
     *    - and(): Both conditions must be true
     *    - or(): At least one condition must be true
     *    - negate(): Inverts the condition
     * 
     * 4. Real-World Applications:
     *    - Form validation
     *    - Business rule engines
     *    - Security checks
     *    - Data quality validation
     *    - API input validation
     * 
     * 5. Best Practices:
     *    - Keep primitives simple and focused
     *    - Name validators after business concepts
     *    - Test primitives independently
     *    - Build complex validators from simple ones
     *    - Make validation results meaningful
     */
}
