package com.calvin.fintech.repository.specification;

import com.calvin.fintech.entity.*;
import com.calvin.fintech.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static com.calvin.fintech.repository.specification.CustomerSpecifications.*;
import static org.assertj.core.api.Assertions.*;

/**
 * CustomerSpecification Functional Tests
 * 
 * Demonstrates:
 * - AssertJ fluent assertions
 * - Stream-based test data generation
 * - Specification composition testing
 * - Functional programming in tests
 */
@DataJpaTest
class CustomerSpecificationTest {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }
    
    // ========================================
    // BASIC SPECIFICATION TESTS
    // ========================================
    
    @Test
    @DisplayName("hasStatus() - Should filter by customer status")
    void testHasStatus() {
        // Given: customers with different statuses
        createCustomers(
            createCustomer("active1@test.com", CustomerStatus.ACTIVE),
            createCustomer("active2@test.com", CustomerStatus.ACTIVE),
            createCustomer("inactive@test.com", CustomerStatus.INACTIVE)
        );
        
        // When: filter by ACTIVE status
        List<Customer> activeCustomers = customerRepository
            .findAll(hasStatus(CustomerStatus.ACTIVE));
        
        // Then: AssertJ fluent assertions
        assertThat(activeCustomers)
            .hasSize(2)
            .extracting(Customer::getEmail)
            .containsExactlyInAnyOrder("active1@test.com", "active2@test.com");
    }
    
    @Test
    @DisplayName("emailContains() - Should perform case-insensitive search")
    void testEmailContains() {
        // Given
        createCustomers(
            createCustomer("john@BANK.com", CustomerStatus.ACTIVE),
            createCustomer("jane@bank.COM", CustomerStatus.ACTIVE),
            createCustomer("bob@company.com", CustomerStatus.ACTIVE)
        );
        
        // When
        List<Customer> bankCustomers = customerRepository
            .findAll(emailContains("@bank"));
        
        // Then: All emails containing "@bank" (case-insensitive)
        assertThat(bankCustomers)
            .hasSize(2)
            .allSatisfy(customer -> 
                assertThat(customer.getEmail().toLowerCase())
                    .contains("@bank")
            );
    }
    
    // ========================================
    // COMPOSITION TESTS
    // ========================================
    
    @Test
    @DisplayName("Specification.where().and() - Should compose conditions")
    void testSpecificationComposition() {
        // Given: Generate test data functionally
        List<Customer> testCustomers = IntStream.range(0, 5)
            .mapToObj(i -> createCustomer(
                "user" + i + "@bank.com",
                i % 2 == 0 ? CustomerStatus.ACTIVE : CustomerStatus.INACTIVE
            ))
            .toList();
        
        createCustomers(testCustomers.toArray(new Customer[0]));
        
        // When: Compose specifications
        Specification<Customer> spec = Specification
            .where(isActive())
            .and(emailContains("@bank"));
        
        List<Customer> results = customerRepository.findAll(spec);
        
        // Then: Only active bank customers
        assertThat(results)
            .isNotEmpty()
            .allSatisfy(customer -> {
                assertThat(customer.getStatus()).isEqualTo(CustomerStatus.ACTIVE);
                assertThat(customer.getEmail()).contains("@bank");
            });
    }
    
    @Test
    @DisplayName("Specification OR composition - Should match either condition")
    void testOrComposition() {
        // Given
        Customer vipCustomer = createCustomer("vip@premium.com", CustomerStatus.VIP);
        Customer activeCustomer = createCustomer("active@standard.com", CustomerStatus.ACTIVE);
        Customer inactiveCustomer = createCustomer("inactive@test.com", CustomerStatus.INACTIVE);
        
        createCustomers(vipCustomer, activeCustomer, inactiveCustomer);
        
        // When: OR composition
        Specification<Customer> spec = Specification
            .where(hasStatus(CustomerStatus.VIP))
            .or(hasStatus(CustomerStatus.ACTIVE));
        
        List<Customer> results = customerRepository.findAll(spec);
        
        // Then: VIP or ACTIVE customers
        assertThat(results)
            .hasSize(2)
            .extracting(Customer::getStatus)
            .containsExactlyInAnyOrder(CustomerStatus.VIP, CustomerStatus.ACTIVE);
    }
    
    @Test
    @DisplayName("Specification NOT - Should negate condition")
    void testNotComposition() {
        // Given
        createCustomers(
            createCustomer("active@test.com", CustomerStatus.ACTIVE),
            createCustomer("inactive@test.com", CustomerStatus.INACTIVE)
        );
        
        // When: Negate ACTIVE
        Specification<Customer> spec = Specification.not(isActive());
        
        List<Customer> results = customerRepository.findAll(spec);
        
        // Then: Non-active customers only
        assertThat(results)
            .hasSize(1)
            .first()
            .satisfies(customer -> 
                assertThat(customer.getStatus()).isNotEqualTo(CustomerStatus.ACTIVE)
            );
    }
    
    // ========================================
    // DATE/TIME SPECIFICATION TESTS
    // ========================================
    
    @Test
    @DisplayName("createdWithinDays() - Should find recent customers")
    void testCreatedWithinDays() {
        // Given: Customers created at different times
        LocalDateTime now = LocalDateTime.now();
        
        Customer recent = createCustomerWithDate(
            "recent@test.com", 
            now.minusDays(5)
        );
        
        Customer old = createCustomerWithDate(
            "old@test.com", 
            now.minusDays(40)
        );
        
        createCustomers(recent, old);
        
        // When: Find customers created within 30 days
        List<Customer> recentCustomers = customerRepository
            .findAll(createdWithinDays(30));
        
        // Then: Only recent customer
        assertThat(recentCustomers)
            .hasSize(1)
            .first()
            .extracting(Customer::getEmail)
            .isEqualTo("recent@test.com");
    }
    
    @Test
    @DisplayName("createdBetween() - Should find customers in date range")
    void testCreatedBetween() {
        // Given
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 31, 23, 59);
        
        Customer inRange = createCustomerWithDate(
            "inrange@test.com",
            LocalDateTime.of(2024, 6, 15, 12, 0)
        );
        
        Customer outOfRange = createCustomerWithDate(
            "outofrange@test.com",
            LocalDateTime.of(2023, 12, 31, 23, 59)
        );
        
        createCustomers(inRange, outOfRange);
        
        // When
        List<Customer> results = customerRepository
            .findAll(createdBetween(start, end));
        
        // Then
        assertThat(results)
            .hasSize(1)
            .extracting(Customer::getEmail)
            .containsExactly("inrange@test.com");
    }
    
    // ========================================
    // COMPLEX SCENARIO TESTS
    // ========================================
    
    @Test
    @DisplayName("isVip() - Should identify VIP customers")
    void testVipSpecification() {
        // Given: Multiple customers with various attributes
        Customer vipCandidate = createCustomer("vip@test.com", CustomerStatus.ACTIVE);
        vipCandidate.setProfile(createVerifiedProfile());
        // Add accounts and transactions to meet VIP criteria
        
        Customer regularCustomer = createCustomer("regular@test.com", CustomerStatus.ACTIVE);
        
        createCustomers(vipCandidate, regularCustomer);
        
        // When
        List<Customer> vipCustomers = customerRepository.findAll(isVip());
        
        // Then: AssertJ extracting and filtering
        assertThat(vipCustomers)
            .isNotEmpty()
            .allSatisfy(customer -> {
                assertThat(customer.getStatus()).isEqualTo(CustomerStatus.ACTIVE);
                assertThat(customer.getProfile()).isNotNull();
                assertThat(customer.getProfile().isKycVerified()).isTrue();
            });
    }
    
    @Test
    @DisplayName("Dynamic specification building - Build query at runtime")
    void testDynamicSpecificationBuilding() {
        // Given: Test data
        createCustomers(
            createCustomer("active@bank.com", CustomerStatus.ACTIVE),
            createCustomer("active@other.com", CustomerStatus.ACTIVE),
            createCustomer("inactive@bank.com", CustomerStatus.INACTIVE)
        );
        
        // When: Build specification dynamically based on search criteria
        SearchCriteria criteria = new SearchCriteria(
            CustomerStatus.ACTIVE,
            "@bank",
            30 // days
        );
        
        Specification<Customer> spec = buildDynamicSpec(criteria);
        List<Customer> results = customerRepository.findAll(spec);
        
        // Then: AssertJ assertions with multiple conditions
        assertThat(results)
            .isNotEmpty()
            .allMatch(c -> c.getStatus() == CustomerStatus.ACTIVE)
            .allMatch(c -> c.getEmail().contains("@bank"));
    }
    
    // ========================================
    // FUNCTIONAL ASSERTIONS
    // ========================================
    
    @Test
    @DisplayName("AssertJ extracting() - Extract and verify nested properties")
    void testExtractingNestedProperties() {
        // Given
        Customer customer1 = createCustomer("user1@test.com", CustomerStatus.ACTIVE);
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        
        Customer customer2 = createCustomer("user2@test.com", CustomerStatus.ACTIVE);
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        
        createCustomers(customer1, customer2);
        
        // When
        List<Customer> customers = customerRepository.findAll();
        
        // Then: Extract multiple properties
        assertThat(customers)
            .extracting(
                Customer::getFirstName, 
                Customer::getLastName,
                Customer::getEmail
            )
            .containsExactlyInAnyOrder(
                tuple("John", "Doe", "user1@test.com"),
                tuple("Jane", "Smith", "user2@test.com")
            );
    }
    
    @Test
    @DisplayName("AssertJ filteredOn() - Filter and assert")
    void testFilteredOn() {
        // Given: Stream-based test data generation
        List<Customer> testData = IntStream.rangeClosed(1, 10)
            .mapToObj(i -> {
                Customer c = createCustomer(
                    "user" + i + "@test.com",
                    i <= 5 ? CustomerStatus.ACTIVE : CustomerStatus.INACTIVE
                );
                c.setFirstName("User" + i);
                return c;
            })
            .toList();
        
        createCustomers(testData.toArray(new Customer[0]));
        
        // When
        List<Customer> allCustomers = customerRepository.findAll();
        
        // Then: Filter in assertion
        assertThat(allCustomers)
            .filteredOn(c -> c.getStatus() == CustomerStatus.ACTIVE)
            .hasSize(5)
            .allSatisfy(c -> 
                assertThat(c.getEmail()).startsWith("user")
            );
    }
    
    // ========================================
    // HELPER METHODS
    // ========================================
    
    private void createCustomers(Customer... customers) {
        customerRepository.saveAll(List.of(customers));
    }
    
    private Customer createCustomer(String email, CustomerStatus status) {
        Customer customer = new Customer("First", "Last", email);
        customer.setStatus(status);
        customer.setCreatedAt(LocalDateTime.now());
        return customer;
    }
    
    private Customer createCustomerWithDate(String email, LocalDateTime createdAt) {
        Customer customer = createCustomer(email, CustomerStatus.ACTIVE);
        customer.setCreatedAt(createdAt);
        return customer;
    }
    
    private CustomerProfile createVerifiedProfile() {
        CustomerProfile profile = new CustomerProfile();
        profile.setKycVerified(true);
        return profile;
    }
    
    /**
     * Dynamic specification builder
     */
    private Specification<Customer> buildDynamicSpec(SearchCriteria criteria) {
        Specification<Customer> spec = Specification.where(null);
        
        if (criteria.status() != null) {
            spec = spec.and(hasStatus(criteria.status()));
        }
        
        if (criteria.emailPattern() != null) {
            spec = spec.and(emailContains(criteria.emailPattern()));
        }
        
        if (criteria.withinDays() != null) {
            spec = spec.and(createdWithinDays(criteria.withinDays()));
        }
        
        return spec;
    }
    
    record SearchCriteria(
        CustomerStatus status,
        String emailPattern,
        Integer withinDays
    ) {}
}
