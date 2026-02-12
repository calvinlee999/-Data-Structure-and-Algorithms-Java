package com.calvin.functional.interfaces;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Principle 5: Continuous Security & Compliance
 * 
 * Security checks as pure functions embedded in data pipelines.
 * Treat security validation as transformations in the stream.
 * This ensures uninterrupted journeys and automated safety.
 */
public class SecurityAsFunctionExample {

    record Payment(String id, String userId, double amount, String token, boolean verified) {}
    record User(String id, String email, String role, boolean active, int trustScore) {}

    public static void main(String[] args) {
        System.out.println("=== PRINCIPLE 5: CONTINUOUS SECURITY & COMPLIANCE ===\n");

        demonstrateEmbeddedSecurityChecks();
        demonstrateZeroTrustPipeline();
        demonstrateComplianceValidation();
        demonstrateSecurityCombinators();
    }

    private static void demonstrateEmbeddedSecurityChecks() {
        System.out.println("=== EMBEDDED SECURITY GUARDRAILS ===\n");

        List<Payment> allPayments = generatePayments();
        System.out.println("Total payments received: " + allPayments.size());

        // ✅ Security as pipeline transformations
        List<Payment> securePayments = allPayments.stream()
                .filter(Security::isTokenValid)          // Guardrail 1: AuthN/AuthZ
                .filter(Security::isWithinLimit)         // Guardrail 2: Fraud Check
                .filter(Security::isNotBlacklisted)      // Guardrail 3: Risk Management
                .filter(Security::hasValidVerification)  // Guardrail 4: 2FA Check
                .collect(Collectors.toList());

        System.out.println("Secure payments (passed all checks): " + securePayments.size());
        System.out.println("Rejected payments: " + (allPayments.size() - securePayments.size()));
        System.out.println("\n✅ Security integrated into data flow!\n");
    }

    private static void demonstrateZeroTrustPipeline() {
        System.out.println("=== ZERO-TRUST ARCHITECTURE ===\n");

        List<User> users = generateUsers();

        // Zero-Trust: Verify everything, trust nothing
        List<User> trustedUsers = users.stream()
                .filter(ZeroTrust::isEmailVerified)      // Step 1: Email verified
                .filter(ZeroTrust::isAccountActive)      // Step 2: Active account
                .filter(ZeroTrust::hasRequiredRole)      // Step 3: Proper permissions
                .filter(ZeroTrust::meetsComplianceScore) // Step 4: Trust score check
                .collect(Collectors.toList());

        System.out.println("Total users: " + users.size());
        System.out.println("Trusted users: " + trustedUsers.size());
        System.out.println("✅ Every step validates security!\n");

        // Show rejection reasons
        demonstrateRejectionReasons(users);
    }

    private static void demonstrateRejectionReasons(List<User> users) {
        System.out.println("=== SECURITY REJECTION ANALYSIS ===\n");

        long emailFails = users.stream()
                .filter(u -> !ZeroTrust.isEmailVerified(u))
                .count();

        long inactiveAccounts = users.stream()
                .filter(u -> !ZeroTrust.isAccountActive(u))
                .count();

        long roleFailures = users.stream()
                .filter(ZeroTrust::isAccountActive)
                .filter(u -> !ZeroTrust.hasRequiredRole(u))
                .count();

        long scoreFails = users.stream()
                .filter(ZeroTrust::isAccountActive)
                .filter(ZeroTrust::hasRequiredRole)
                .filter(u -> !ZeroTrust.meetsComplianceScore(u))
                .count();

        System.out.println("Rejection reasons:");
        System.out.println("  Email not verified: " + emailFails);
        System.out.println("  Inactive accounts: " + inactiveAccounts);
        System.out.println("  Insufficient role: " + roleFailures);
        System.out.println("  Low trust score: " + scoreFails);
        System.out.println("✅ Security metrics for monitoring!\n");
    }

    private static void demonstrateComplianceValidation() {
        System.out.println("=== REAL-TIME COMPLIANCE VALIDATION ===\n");

        List<Payment> payments = generatePayments();

        // Compliance rules as functional predicates
        Map<String, List<Payment>> complianceReport = payments.stream()
                .filter(Compliance::meetsKYCRequirements)
                .filter(Compliance::withinDailyLimit)
                .filter(Compliance::inAllowedJurisdiction)
                .collect(Collectors.groupingBy(
                        p -> "Amount: $" + (int)p.amount() / 100 * 100 + "-" + ((int)p.amount() / 100 + 1) * 100
                ));

        System.out.println("Compliant payments by amount range:");
        complianceReport.forEach((range, list) ->
                System.out.println("  " + range + ": " + list.size() + " payments"));

        System.out.println("\n✅ Compliance verified in real-time!\n");
    }

    private static void demonstrateSecurityCombinators() {
        System.out.println("=== SECURITY COMBINATORS ===\n");

        List<Payment> payments = generatePayments();

        // Compose security checks using combinators
        Predicate<Payment> isFullySecure = 
                Security::isTokenValid
                .and(Security::isWithinLimit)
                .and(Security::isNotBlacklisted)
                .and(Security::hasValidVerification);

        Predicate<Payment> isCompliant =
                Compliance::meetsKYCRequirements
                .and(Compliance::withinDailyLimit)
                .and(Compliance::inAllowedJurisdiction);

        // Combine security and compliance
        Predicate<Payment> isSafeToProcess = isFullySecure.and(isCompliant);

        List<Payment> safePayments = payments.stream()
                .filter(isSafeToProcess)
                .collect(Collectors.toList());

        System.out.println("Payments passing all security & compliance: " + safePayments.size());
        System.out.println("✅ Composable security rules!\n");

        // Advanced: Security with recovery
        demonstrateSecurityWithRecovery(payments);
    }

    private static void demonstrateSecurityWithRecovery(List<Payment> payments) {
        System.out.println("=== SECURITY WITH GRACEFUL RECOVERY ===\n");

        record ValidationResult(Payment payment, boolean passed, List<String> failures) {}

        List<ValidationResult> results = payments.stream()
                .map(payment -> {
                    List<String> failures = new ArrayList<>();
                    
                    if (!Security.isTokenValid(payment)) failures.add("Invalid token");
                    if (!Security.isWithinLimit(payment)) failures.add("Exceeds limit");
                    if (!Security.isNotBlacklisted(payment)) failures.add("Blacklisted");
                    
                    return new ValidationResult(payment, failures.isEmpty(), failures);
                })
                .collect(Collectors.toList());

        long passed = results.stream().filter(ValidationResult::passed).count();
        long failed = results.size() - passed;

        System.out.println("Validation results:");
        System.out.println("  Passed: " + passed);
        System.out.println("  Failed: " + failed);

        // Show sample failures
        results.stream()
                .filter(r -> !r.passed())
                .limit(3)
                .forEach(r -> System.out.println("  Payment " + r.payment().id() + 
                        ": " + String.join(", ", r.failures())));

        System.out.println("\n✅ Detailed security audit trail!\n");
    }

    // Security guardrails
    static class Security {
        static boolean isTokenValid(Payment p) {
            return p.token() != null && p.token().startsWith("TK");
        }

        static boolean isWithinLimit(Payment p) {
            return p.amount() <= 10000;
        }

        static boolean isNotBlacklisted(Payment p) {
            return !p.userId().contains("BLOCKED");
        }

        static boolean hasValidVerification(Payment p) {
            return p.verified();
        }
    }

    // Zero-Trust validators
    static class ZeroTrust {
        static boolean isEmailVerified(User u) {
            return u.email() != null && u.email().contains("@");
        }

        static boolean isAccountActive(User u) {
            return u.active();
        }

        static boolean hasRequiredRole(User u) {
            return u.role().equals("ADMIN") || u.role().equals("USER");
        }

        static boolean meetsComplianceScore(User u) {
            return u.trustScore() >= 70;
        }
    }

    // Compliance checks
    static class Compliance {
        static boolean meetsKYCRequirements(Payment p) {
            return p.verified();
        }

        static boolean withinDailyLimit(Payment p) {
            return p.amount() <= 5000;
        }

        static boolean inAllowedJurisdiction(Payment p) {
            return true; // Simplified
        }
    }

    // Test data generators
    private static List<Payment> generatePayments() {
        Random rand = new Random(42);
        return IntStream.range(0, 100)
                .mapToObj(i -> new Payment(
                        "PAY" + i,
                        (i % 10 == 0) ? "BLOCKED" + i : "USER" + i,
                        rand.nextDouble() * 15000,
                        (i % 5 == 0) ? null : "TK" + i,
                        i % 3 != 0
                ))
                .collect(Collectors.toList());
    }

    private static List<User> generateUsers() {
        Random rand = new Random(42);
        String[] roles = {"ADMIN", "USER", "GUEST", "INVALID"};
        return IntStream.range(0, 50)
                .mapToObj(i -> new User(
                        "U" + i,
                        (i % 7 == 0) ? "invalid" : "user" + i + "@example.com",
                        roles[i % 4],
                        i % 5 != 0,
                        rand.nextInt(100)
                ))
                .collect(Collectors.toList());
    }

    /**
     * KEY TAKEAWAYS:
     * 
     * 1. Security as a Function:
     *    - Security checks are pure predicates
     *    - Embed in data pipelines
     *    - No separate security layer
     * 
     * 2. Benefits:
     *    - ✅ Real-time validation
     *    - ✅ Uninterrupted user journeys
     *    - ✅ Automated compliance
     *    - ✅ Auditable security trail
     *    - ✅ Composable security rules
     * 
     * 3. Zero-Trust Architecture:
     *    - Verify at every step
     *    - Never trust, always verify
     *    - Functional validation pipeline
     * 
     * 4. Compliance Integration:
     *    - KYC/AML checks in pipeline
     *    - Real-time compliance reporting
     *    - Automated enforcement
     * 
     * 5. Patterns:
     *    - Each check is a predicate
     *    - Combine with .and(), .or(), .negate()
     *    - Filter streams with security checks
     *    - Collect metrics for monitoring
     * 
     * 6. Recovery & Reporting:
     *    - Capture validation failures
     *    - Provide detailed reasons
     *    - Enable security analytics
     */
}
