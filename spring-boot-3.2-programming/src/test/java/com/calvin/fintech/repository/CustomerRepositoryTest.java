package com.calvin.fintech.repository;

import com.calvin.fintech.entity.Customer;
import com.calvin.fintech.entity.CustomerStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * CustomerRepository Test
 * 
 * @DataJpaTest:
 * - Uses in-memory H2 database
 * - Auto-configures JPA components
 * - Rolls back after each test
 * - Provides TestEntityManager for setup
 */
@DataJpaTest
@DisplayName("CustomerRepository Tests")
class CustomerRepositoryTest {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private TestEntityManager entityManager;
    
    // ========================================
    // BASIC CRUD TESTS
    // ========================================
    
    @Test
    @DisplayName("Should save and find customer by ID")
    void testSaveAndFindById() {
        // Given
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setStatus(CustomerStatus.ACTIVE);
        
        // When
        Customer saved = customerRepository.save(customer);
        entityManager.flush(); // Force SQL execution
        entityManager.clear(); // Clear persistence context
        
        Optional<Customer> found = customerRepository.findById(saved.getId());
        
        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(found.get().getFirstName()).isEqualTo("John");
    }
    
    @Test
    @DisplayName("Should find customer by email")
    void testFindByEmail() {
        // Given
        Customer customer = createCustomer("jane@example.com", "Jane", "Smith");
        entityManager.persist(customer);
        entityManager.flush();
        
        // When
        Optional<Customer> found = customerRepository.findByEmail("jane@example.com");
        
        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Jane");
    }
    
    @Test
    @DisplayName("Should return empty when email not found")
    void testFindByEmailNotFound() {
        // When
        Optional<Customer> found = customerRepository.findByEmail("nonexistent@example.com");
        
        // Then
        assertThat(found).isEmpty();
    }
    
    // ========================================
    // QUERY METHOD TESTS
    // ========================================
    
    @Test
    @DisplayName("Should find customers by status")
    void testFindByStatus() {
        // Given
        Customer active1 = createCustomer("active1@example.com", "Active", "One");
        Customer active2 = createCustomer("active2@example.com", "Active", "Two");
        Customer inactive = createCustomer("inactive@example.com", "Inactive", "User");
        inactive.setStatus(CustomerStatus.INACTIVE);
        
        entityManager.persist(active1);
        entityManager.persist(active2);
        entityManager.persist(inactive);
        entityManager.flush();
        
        // When
        List<Customer> activeCustomers = customerRepository.findByStatus(CustomerStatus.ACTIVE);
        
        // Then
        assertThat(activeCustomers).hasSize(2);
        assertThat(activeCustomers)
            .extracting(Customer::getEmail)
            .containsExactlyInAnyOrder("active1@example.com", "active2@example.com");
    }
    
    @Test
    @DisplayName("Should find active non-deleted customers")
    void testFindActiveCustomers() {
        // Given
        Customer active = createCustomer("active@example.com", "Active", "User");
        Customer deleted = createCustomer("deleted@example.com", "Deleted", "User");
        deleted.markAsDeleted();
        
        entityManager.persist(active);
        entityManager.persist(deleted);
        entityManager.flush();
        
        // When
        List<Customer> activeCustomers = customerRepository.findActiveCustomers();
        
        // Then
        assertThat(activeCustomers).hasSize(1);
        assertThat(activeCustomers.get(0).getEmail()).isEqualTo("active@example.com");
    }
    
    @Test
    @DisplayName("Should count customers by status")
    void testCountByStatus() {
        // Given
        entityManager.persist(createCustomer("active1@example.com", "Active", "One"));
        entityManager.persist(createCustomer("active2@example.com", "Active", "Two"));
        entityManager.flush();
        
        // When
        long count = customerRepository.countByStatus(CustomerStatus.ACTIVE);
        
        // Then
        assertThat(count).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should check if email exists")
    void testExistsByEmail() {
        // Given
        Customer customer = createCustomer("exists@example.com", "Exists", "User");
        entityManager.persist(customer);
        entityManager.flush();
        
        // When
        boolean exists = customerRepository.existsByEmail("exists@example.com");
        boolean notExists = customerRepository.existsByEmail("notexists@example.com");
        
        // Then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
    
    // ========================================
    // PAGINATION TESTS
    // ========================================
    
    @Test
    @DisplayName("Should paginate results")
    void testPagination() {
        // Given - create 25 customers
        for (int i = 0; i < 25; i++) {
            Customer customer = createCustomer("user" + i + "@example.com", "User", String.valueOf(i));
            entityManager.persist(customer);
        }
        entityManager.flush();
        
        // When
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Customer> page = customerRepository.findByDeletedFalse(pageRequest);
        
        // Then
        assertThat(page.getTotalElements()).isEqualTo(25);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getContent()).hasSize(10);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }
    
    @Test
    @DisplayName("Should sort results")
    void testSorting() {
        // Given
        entityManager.persist(createCustomer("charlie@example.com", "Charlie", "Brown"));
        entityManager.persist(createCustomer("alice@example.com", "Alice", "Smith"));
        entityManager.persist(createCustomer("bob@example.com", "Bob", "Jones"));
        entityManager.flush();
        
        // When
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("firstName").ascending());
        Page<Customer> page = customerRepository.findByDeletedFalse(pageRequest);
        
        // Then
        assertThat(page.getContent())
            .extracting(Customer::getFirstName)
            .containsExactly("Alice", "Bob", "Charlie");
    }
    
    // ========================================
    // CUSTOM QUERY TESTS
    // ========================================
    
    @Test
    @DisplayName("Should search customers by criteria")
    void testSearchCustomers() {
        // Given
        Customer customer1 = createCustomer("test@example.com", "Test", "User");
        Customer customer2 = createCustomer("another@example.com", "Another", "User");
        
        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.flush();
        
        // When
        List<Customer> results = customerRepository.searchCustomers(
            CustomerStatus.ACTIVE, 
            "test"
        );
        
        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getEmail()).isEqualTo("test@example.com");
    }
    
    // ========================================
    // HELPER METHODS
    // ========================================
    
    private Customer createCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setStatus(CustomerStatus.ACTIVE);
        return customer;
    }
}
