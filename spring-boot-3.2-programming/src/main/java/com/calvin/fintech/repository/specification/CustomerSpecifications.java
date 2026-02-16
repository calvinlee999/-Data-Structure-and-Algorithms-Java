package com.calvin.fintech.repository.specification;

import com.calvin.fintech.entity.Customer;
import com.calvin.fintech.entity.CustomerStatus;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CustomerSpecifications - Functional Query Building
 * 
 * Demonstrates:
 * - Functional composition with Specifications
 * - Type-safe dynamic queries
 * - Reusable query building blocks
 * 
 * Specifications are COMPOSABLE using:
 * - Specification.where(spec)
 * - spec.and(otherSpec)
 * - spec.or(otherSpec)
 * - Specification.not(spec)
 */
public class CustomerSpecifications {
    
    // ========================================
    // BASIC SPECIFICATIONS
    // ========================================
    
    /**
     * Find customers by status
     */
    public static Specification<Customer> hasStatus(CustomerStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction(); // Always true
            }
            return cb.equal(root.get("status"), status);
        };
    }
    
    /**
     * Find active customers only
     */
    public static Specification<Customer> isActive() {
        return hasStatus(CustomerStatus.ACTIVE);
    }
    
    /**
     * Find non-deleted customers
     */
    public static Specification<Customer> isNotDeleted() {
        return (root, query, cb) -> 
            cb.equal(root.get("deleted"), false);
    }
    
    /**
     * Email contains (case-insensitive)
     */
    public static Specification<Customer> emailContains(String email) {
        return (root, query, cb) -> {
            if (email == null || email.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(
                cb.lower(root.get("email")),
                "%" + email.toLowerCase() + "%"
            );
        };
    }
    
    /**
     * Email equals (exact match)
     */
    public static Specification<Customer> emailEquals(String email) {
        return (root, query, cb) -> {
            if (email == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("email"), email);
        };
    }
    
    /**
     * First name or last name contains
     */
    public static Specification<Customer> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) {
                return cb.conjunction();
            }
            
            String pattern = "%" + name.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("firstName")), pattern),
                cb.like(cb.lower(root.get("lastName")), pattern)
            );
        };
    }
    
    // ========================================
    // DATE/TIME SPECIFICATIONS
    // ========================================
    
    /**
     * Created after date
     */
    public static Specification<Customer> createdAfter(LocalDateTime date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.conjunction();
            }
            return cb.greaterThan(root.get("createdAt"), date);
        };
    }
    
    /**
     * Created before date
     */
    public static Specification<Customer> createdBefore(LocalDateTime date) {
        return (root, query, cb) -> {
            if (date == null) {
                return cb.conjunction();
            }
            return cb.lessThan(root.get("createdAt"), date);
        };
    }
    
    /**
     * Created between dates
     */
    public static Specification<Customer> createdBetween(
            LocalDateTime start, 
            LocalDateTime end) {
        return (root, query, cb) -> {
            if (start == null && end == null) {
                return cb.conjunction();
            }
            if (start == null) {
                return cb.lessThan(root.get("createdAt"), end);
            }
            if (end == null) {
                return cb.greaterThan(root.get("createdAt"), start);
            }
            return cb.between(root.get("createdAt"), start, end);
        };
    }
    
    /**
     * Recently created (within days)
     */
    public static Specification<Customer> createdWithinDays(int days) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        return createdAfter(cutoff);
    }
    
    // ========================================
    // RELATIONSHIP SPECIFICATIONS
    // ========================================
    
    /**
     * Has KYC verified profile
     */
    public static Specification<Customer> hasVerifiedKyc() {
        return (root, query, cb) -> {
            var profile = root.join("profile", JoinType.INNER);
            return cb.equal(profile.get("kycStatus"), "VERIFIED");
        };
    }
    
    /**
     * Has active accounts
     */
    public static Specification<Customer> hasActiveAccounts() {
        return (root, query, cb) -> {
            var accounts = root.join("accounts", JoinType.INNER);
            return cb.equal(accounts.get("status"), "ACTIVE");
        };
    }
    
    /**
     * Has transactions with amount greater than threshold
     */
    public static Specification<Customer> hasTransactionsAbove(BigDecimal amount) {
        return (root, query, cb) -> {
            if (amount == null) {
                return cb.conjunction();
            }
            
            var transactions = root.join("transactions", JoinType.INNER);
            return cb.greaterThan(transactions.get("amount"), amount);
        };
    }
    
    /**
     * Has at least N accounts
     */
    public static Specification<Customer> hasMinAccounts(int minAccounts) {
        return (root, query, cb) -> {
            // SIZE function to count collection
            return cb.greaterThanOrEqualTo(
                cb.size(root.get("accounts")),
                minAccounts
            );
        };
    }
    
    // ========================================
    // LOCATION SPECIFICATIONS
    // ========================================
    
    /**
     * Billing address in city
     */
    public static Specification<Customer> billingCity(String city) {
        return (root, query, cb) -> {
            if (city == null || city.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("billingAddress").get("city"), city);
        };
    }
    
    /**
     * Billing address in state
     */
    public static Specification<Customer> billingState(String state) {
        return (root, query, cb) -> {
            if (state == null || state.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("billingAddress").get("state"), state);
        };
    }
    
    /**
     * Billing address in country
     */
    public static Specification<Customer> billingCountry(String country) {
        return (root, query, cb) -> {
            if (country == null || country.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("billingAddress").get("country"), country);
        };
    }
    
    // ========================================
    // COMPLEX SPECIFICATIONS
    // ========================================
    
    /**
     * VIP customers:
     * - Active status
     * - KYC verified
     * - Has at least 3 accounts
     * - Has transactions > $10,000
     */
    public static Specification<Customer> isVip() {
        return Specification
            .where(isActive())
            .and(isNotDeleted())
            .and(hasVerifiedKyc())
            .and(hasMinAccounts(3))
            .and(hasTransactionsAbove(new BigDecimal("10000")));
    }
    
    /**
     * High-risk customers:
     * - Has failed transactions
     * - OR KYC not verified
     * - OR inactive for 90+ days
     */
    public static Specification<Customer> isHighRisk() {
        LocalDateTime ninetyDaysAgo = LocalDateTime.now().minusDays(90);
        
        return (root, query, cb) -> {
            // Has failed transactions
            var failedTransactions = root.join("transactions", JoinType.LEFT);
            var hasFailed = cb.equal(failedTransactions.get("status"), "FAILED");
            
            // KYC not verified
            var profile = root.join("profile", JoinType.LEFT);
            var kycNotVerified = cb.notEqual(profile.get("kycStatus"), "VERIFIED");
            
            // Inactive (no updates in 90 days)
            var inactive = cb.lessThan(root.get("updatedAt"), ninetyDaysAgo);
            
            return cb.or(hasFailed, kycNotVerified, inactive);
        };
    }
    
    /**
     * Eligible for credit:
     * - Active
     * - KYC verified
     * - Created at least 6 months ago
     * - Has active checking account
     */
    public static Specification<Customer> isCreditEligible() {
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);
        
        return Specification
            .where(isActive())
            .and(hasVerifiedKyc())
            .and(createdBefore(sixMonthsAgo))
            .and((root, query, cb) -> {
                var accounts = root.join("accounts", JoinType.INNER);
                return cb.and(
                    cb.equal(accounts.get("accountType"), "CHECKING"),
                    cb.equal(accounts.get("status"), "ACTIVE")
                );
            });
    }
    
    // ========================================
    // DEMO: HOW TO USE SPECIFICATIONS
    // ========================================
    
    /**
     * Example usage in service/repository:
     * 
     * // Simple query
     * List<Customer> activeCustomers = customerRepository.findAll(
     *     CustomerSpecifications.isActive()
     * );
     * 
     * // Composed query
     * Specification<Customer> spec = Specification
     *     .where(CustomerSpecifications.isActive())
     *     .and(CustomerSpecifications.emailContains("@bank.com"))
     *     .and(CustomerSpecifications.createdWithinDays(30));
     * 
     * List<Customer> results = customerRepository.findAll(spec);
     * 
     * // Dynamic query building
     * public List<Customer> searchCustomers(CustomerSearchCriteria criteria) {
     *     Specification<Customer> spec = Specification.where(isNotDeleted());
     *     
     *     if (criteria.getStatus() != null) {
     *         spec = spec.and(hasStatus(criteria.getStatus()));
     *     }
     *     
     *     if (criteria.getEmail() != null) {
     *         spec = spec.and(emailContains(criteria.getEmail()));
     *     }
     *     
     *     if (criteria.getMinAccounts() != null) {
     *         spec = spec.and(hasMinAccounts(criteria.getMinAccounts()));
     *     }
     *     
     *     return customerRepository.findAll(spec);
     * }
     * 
     * // Negation
     * Specification<Customer> notVip = Specification.not(
     *     CustomerSpecifications.isVip()
     * );
     * 
     * // OR composition
     * Specification<Customer> activeOrVip = Specification
     *     .where(isActive())
     *     .or(isVip());
     */
}
