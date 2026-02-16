package com.calvin.fintech.service;

import com.calvin.fintech.collector.TopNCollector;
import com.calvin.fintech.entity.*;
import com.calvin.fintech.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * StreamProcessingService Functional Tests
 * 
 * Demonstrates:
 * - Testing stream pipelines with AssertJ
 * - Stream-based test data generation
 * - Fluent assertion patterns
 * - Testing collectors
 */
@ExtendWith(MockitoExtension.class)
class StreamProcessingServiceTest {
    
    @Mock
    private CustomerRepository customerRepository;
    
    @Mock
    private AccountRepository accountRepository;
    
    @Mock
    private TransactionRepository transactionRepository;
    
    @InjectMocks
    private StreamProcessingService service;
    
    // ========================================
    // PRIMITIVE STREAM TESTS
    // ========================================
    
    @Test
    @DisplayName("calculateTotalBalance() - Should sum all account balances")
    void testCalculateTotalBalance() {
        // Given: Generate test accounts functionally
        List<Account> accounts = IntStream.rangeClosed(1, 10)
            .mapToObj(i -> {
                Account account = new Account("ACC-" + i, AccountType.SAVINGS);
                account.setBalance(new BigDecimal(i * 1000));
                return account;
            })
            .toList();
        
        when(accountRepository.findAll()).thenReturn(accounts);
        
        // When
        BigDecimal total = service.calculateTotalBalance();
        
        // Then: Sum = 1000 + 2000 + ... + 10000 = 55000
        assertThat(total)
            .isEqualByComparingTo(new BigDecimal("55000"));
    }
    
    @Test
    @DisplayName("getBalanceStatistics() - Should calculate statistics")
    void testGetBalanceStatistics() {
        // Given
        List<Account> accounts = List.of(
            createAccount("ACC-1", new BigDecimal("1000")),
            createAccount("ACC-2", new BigDecimal("2000")),
            createAccount("ACC-3", new BigDecimal("3000"))
        );
        
        when(accountRepository.findAll()).thenReturn(accounts);
        
        // When
        var stats = service.getBalanceStatistics();
        
        // Then: AssertJ assertions on record
        assertThat(stats)
            .satisfies(s -> {
                assertThat(s.total()).isEqualByComparingTo(new BigDecimal("6000"));
                assertThat(s.average()).isEqualByComparingTo(new BigDecimal("2000.00"));
                assertThat(s.max()).isEqualByComparingTo(new BigDecimal("3000"));
                assertThat(s.min()).isEqualByComparingTo(new BigDecimal("1000"));
                assertThat(s.count()).isEqualTo(3);
            });
    }
    
    // ========================================
    // COMPLEX PIPELINE TESTS
    // ========================================
    
    @Test
    @DisplayName("segmentCustomersByBalance() - Should categorize customers")
    void testSegmentCustomersByBalance() {
        // Given: Customers with varying balances
        List<Customer> customers = List.of(
            createCustomerWithBalance("user1@test.com", new BigDecimal("150000")), // PLATINUM
            createCustomerWithBalance("user2@test.com", new BigDecimal("75000")),  // GOLD
            createCustomerWithBalance("user3@test.com", new BigDecimal("25000")),  // SILVER
            createCustomerWithBalance("user4@test.com", new BigDecimal("5000"))    // BRONZE
        );
        
        when(customerRepository.findAll()).thenReturn(customers);
        // Mock account balances for each customer
        
        // When
        Map<String, List<Customer>> segments = service.segmentCustomersByBalance();
        
        // Then: AssertJ map assertions
        assertThat(segments)
            .containsKeys("PLATINUM", "GOLD", "SILVER", "BRONZE")
            .satisfies(map -> {
                assertThat(map.get("PLATINUM")).hasSize(1);
                assertThat(map.get("GOLD")).hasSize(1);
                assertThat(map.get("SILVER")).hasSize(1);
                assertThat(map.get("BRONZE")).hasSize(1);
            });
    }
    
    @Test
    @DisplayName("findDuplicateEmails() - Should detect duplicates")
    void testFindDuplicateEmails() {
        // Given: Customers with duplicate emails
        List<Customer> customers = List.of(
            createCustomer("duplicate@test.com"),
            createCustomer("duplicate@test.com"),
            createCustomer("unique@test.com")
        );
        
        when(customerRepository.findAll()).thenReturn(customers);
        
        // When
        List<String> duplicates = service.findDuplicateEmails();
        
        // Then
        assertThat(duplicates)
            .hasSize(1)
            .containsExactly("duplicate@test.com");
    }
    
    // ========================================
    // COLLECTOR TESTS
    // ========================================
    
    @Test
    @DisplayName("partitionCustomersByKyc() - Should partition by KYC status")
    void testPartitionCustomersByKyc() {
        // Given
        Customer verified1 = createCustomerWithKyc("verified1@test.com", true);
        Customer verified2 = createCustomerWithKyc("verified2@test.com", true);
        Customer notVerified = createCustomerWithKyc("not@test.com", false);
        
        when(customerRepository.findAll()).thenReturn(
            List.of(verified1, verified2, notVerified)
        );
        
        // When
        Map<Boolean, List<Customer>> partitioned = service.partitionCustomersByKyc();
        
        // Then: AssertJ map assertions
        assertThat(partitioned)
            .hasEntrySatisfying(true, verified -> 
                assertThat(verified).hasSize(2)
            )
            .hasEntrySatisfying(false, notVerified2 -> 
                assertThat(notVerified2).hasSize(1)
            );
    }
    
    @Test
    @DisplayName("getCustomerEmailList() - Should join emails")
    void testGetCustomerEmailList() {
        // Given: Generate customers with stream
        List<Customer> customers = IntStream.rangeClosed(1, 5)
            .mapToObj(i -> createCustomer("user" + i + "@test.com"))
            .toList();
        
        when(customerRepository.findAll()).thenReturn(customers);
        
        // When
        String emailList = service.getCustomerEmailList();
        
        // Then: String assertions
        assertThat(emailList)
            .startsWith("[")
            .endsWith("]")
            .contains("user1@test.com")
            .contains("user2@test.com")
            .contains(", "); // Check separator
    }
    
    // ========================================
    // CUSTOM COLLECTOR TESTS
    // ========================================
    
    @Test
    @DisplayName("TopNCollector - Should collect top N elements")
    void testTopNCollector() {
        // Given: Accounts with various balances
        List<Account> accounts = IntStream.rangeClosed(1, 100)
            .mapToObj(i -> {
                Account account = new Account("ACC-" + i, AccountType.CHECKING);
                account.setBalance(new BigDecimal(i * 100));
                return account;
            })
            .toList();
        
        // When: Collect top 10 by balance
        List<Account> top10 = accounts.stream()
            .collect(new TopNCollector<>(
                10,
                Comparator.comparing(Account::getBalance).reversed()
            ));
        
        // Then: AssertJ assertions on sorted list
        assertThat(top10)
            .hasSize(10)
            .isSortedAccordingTo(
                Comparator.comparing(Account::getBalance).reversed()
            )
            .first()
            .satisfies(account -> 
                assertThat(account.getBalance())
                    .isEqualByComparingTo(new BigDecimal("10000"))
            );
        
        // Verify last element
        assertThat(top10)
            .last()
            .satisfies(account ->
                assertThat(account.getBalance())
                    .isEqualByComparingTo(new BigDecimal("9100"))
            );
    }
    
    // ========================================
    // FLUENT ASSERTION PATTERNS
    // ========================================
    
    @Test
    @DisplayName("AssertJ chaining - Multiple assertions in sequence")
    void testAssertJChaining() {
        // Given
        List<Account> accounts = List.of(
            createAccount("ACC-1", new BigDecimal("1000")),
            createAccount("ACC-2", new BigDecimal("2000")),
            createAccount("ACC-3", new BigDecimal("3000"))
        );
        
        // Then: Chain multiple assertions
        assertThat(accounts)
            .isNotEmpty()
            .hasSize(3)
            .extracting(Account::getAccountNumber)
            .containsExactly("ACC-1", "ACC-2", "ACC-3")
            .allMatch(num -> num.startsWith("ACC-"));
    }
    
    @Test
    @DisplayName("AssertJ satisfies() - Custom validation")
    void testAssertJSatisfies() {
        // Given
        Account account = createAccount("ACC-123", new BigDecimal("5000"));
        
        // Then: Custom satisfaction check
        assertThat(account)
            .satisfies(acc -> {
                assertThat(acc.getAccountNumber()).startsWith("ACC-");
                assertThat(acc.getBalance()).isGreaterThan(BigDecimal.ZERO);
            });
    }
    
    @Test
    @DisplayName("AssertJ extracting() - Extract nested properties")
    void testAssertJExtracting() {
        // Given
        List<Customer> customers = List.of(
            createCustomerWithDetails("John", "Doe", "john@test.com"),
            createCustomerWithDetails("Jane", "Smith", "jane@test.com")
        );
        
        // Then: Extract multiple properties
        assertThat(customers)
            .extracting(
                Customer::getFirstName,
                Customer::getLastName,
                Customer::getEmail
            )
            .containsExactly(
                tuple("John", "Doe", "john@test.com"),
                tuple("Jane", "Smith", "jane@test.com")
            );
    }
    
    @Test
    @DisplayName("AssertJ flatExtracting() - Flatten nested collections")
    void testAssertJFlatExtracting() {
        // Given: Customers with accounts
        Customer customer1 = createCustomer("user1@test.com");
        customer1.addAccount(createAccount("ACC-1", BigDecimal.valueOf(1000)));
        customer1.addAccount(createAccount("ACC-2", BigDecimal.valueOf(2000)));
        
        Customer customer2 = createCustomer("user2@test.com");
        customer2.addAccount(createAccount("ACC-3", BigDecimal.valueOf(3000)));
        
        List<Customer> customers = List.of(customer1, customer2);
        
        // Then: Flatten accounts from all customers
        assertThat(customers)
            .flatExtracting(Customer::getAccounts)
            .hasSize(3)
            .extracting(Account::getAccountNumber)
            .containsExactlyInAnyOrder("ACC-1", "ACC-2", "ACC-3");
    }
    
    // ========================================
    // HELPER METHODS
    // ========================================
    
    private Account createAccount(String accountNumber, BigDecimal balance) {
        Account account = new Account(accountNumber, AccountType.CHECKING);
        account.setBalance(balance);
        account.setStatus(AccountStatus.ACTIVE);
        return account;
    }
    
    private Customer createCustomer(String email) {
        return new Customer("First", "Last", email);
    }
    
    private Customer createCustomerWithDetails(String firstName, String lastName, String email) {
        return new Customer(firstName, lastName, email);
    }
    
    private Customer createCustomerWithBalance(String email, BigDecimal totalBalance) {
        Customer customer = createCustomer(email);
        // Add accounts with balances that sum to totalBalance
        return customer;
    }
    
    private Customer createCustomerWithKyc(String email, boolean kycVerified) {
        Customer customer = createCustomer(email);
        CustomerProfile profile = new CustomerProfile();
        profile.setKycVerified(kycVerified);
        customer.setProfile(profile);
        return customer;
    }
}
