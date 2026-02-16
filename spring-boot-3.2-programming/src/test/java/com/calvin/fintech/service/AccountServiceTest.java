package com.calvin.fintech.service;

import com.calvin.fintech.entity.*;
import com.calvin.fintech.repository.AccountRepository;
import com.calvin.fintech.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * AccountService Unit Test
 * 
 * Uses Mockito to mock dependencies
 * Tests business logic in isolation
 */
@DisplayName("AccountService Unit Tests")
class AccountServiceTest {
    
    @Mock
    private AccountRepository accountRepository;
    
    @Mock
    private CustomerRepository customerRepository;
    
    @InjectMocks
    private AccountService accountService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    // ========================================
    // DEPOSIT TESTS
    // ========================================
    
    @Test
    @DisplayName("Should deposit successfully")
    void testDepositSuccess() {
        // Given
        Long accountId = 1L;
        BigDecimal depositAmount = new BigDecimal("100.00");
        
        Account account = new Account("ACC001", AccountType.CHECKING);
        account.setId(accountId);
        account.setBalance(new BigDecimal("500.00"));
        account.setStatus(AccountStatus.ACTIVE);
        
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        
        // When
        Account result = accountService.deposit(accountId, depositAmount);
        
        // Then
        assertThat(result.getBalance()).isEqualTo(new BigDecimal("600.00"));
        verify(accountRepository, times(1)).findById(accountId);
        verify(accountRepository, times(1)).save(account);
    }
    
    @Test
    @DisplayName("Should throw exception when account not found")
    void testDepositAccountNotFound() {
        // Given
        Long accountId = 999L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
        
        // When & Then
        assertThatThrownBy(() -> 
            accountService.deposit(accountId, new BigDecimal("100.00"))
        )
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Account not found");
    }
    
    @Test
    @DisplayName("Should reject negative deposit")
    void testDepositNegativeAmount() {
        // Given
        Account account = new Account("ACC001", AccountType.CHECKING);
        account.setId(1L);
        account.setStatus(AccountStatus.ACTIVE);
        
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        
        // When & Then
        assertThatThrownBy(() -> 
            accountService.deposit(1L, new BigDecimal("-50.00"))
        )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("must be positive");
    }
    
    // ========================================
    // WITHDRAWAL TESTS
    // ========================================
    
    @Test
    @DisplayName("Should withdraw successfully")
    void testWithdrawSuccess() {
        // Given
        Account account = new Account("ACC001", AccountType.CHECKING);
        account.setId(1L);
        account.setBalance(new BigDecimal("500.00"));
        account.setStatus(AccountStatus.ACTIVE);
        
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        
        // When
        Account result = accountService.withdraw(1L, new BigDecimal("100.00"));
        
        // Then
        assertThat(result.getBalance()).isEqualTo(new BigDecimal("400.00"));
    }
    
    @Test
    @DisplayName("Should reject withdrawal with insufficient funds")
    void testWithdrawInsufficientFunds() {
        // Given
        Account account = new Account("ACC001", AccountType.SAVINGS);
        account.setId(1L);
        account.setBalance(new BigDecimal("50.00"));
        account.setStatus(AccountStatus.ACTIVE);
        
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        
        // When & Then
        assertThatThrownBy(() -> 
            accountService.withdraw(1L, new BigDecimal("100.00"))
        )
        .isInstanceOf(InsufficientFundsException.class);
    }
    
    @Test
    @DisplayName("Should allow overdraft for checking accounts")
    void testWithdrawWithOverdraft() {
        // Given
        Account account = new Account("ACC001", AccountType.CHECKING);
        account.setId(1L);
        account.setBalance(new BigDecimal("50.00"));
        account.setOverdraftLimit(new BigDecimal("100.00"));
        account.setStatus(AccountStatus.ACTIVE);
        
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        
        // When
        Account result = accountService.withdraw(1L, new BigDecimal("120.00"));
        
        // Then
        assertThat(result.getBalance()).isEqualTo(new BigDecimal("-70.00"));
    }
    
    // ========================================
    // TRANSFER TESTS
    // ========================================
    
    @Test
    @DisplayName("Should transfer successfully")
    void testTransferSuccess() {
        // Given
        Account fromAccount = new Account("ACC001", AccountType.CHECKING);
        fromAccount.setId(1L);
        fromAccount.setBalance(new BigDecimal("1000.00"));
        fromAccount.setStatus(AccountStatus.ACTIVE);
        
        Account toAccount = new Account("ACC002", AccountType.SAVINGS);
        toAccount.setId(2L);
        toAccount.setBalance(new BigDecimal("500.00"));
        toAccount.setStatus(AccountStatus.ACTIVE);
        
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));
        
        // When
        accountService.transfer(1L, 2L, new BigDecimal("300.00"));
        
        // Then
        assertThat(fromAccount.getBalance()).isEqualTo(new BigDecimal("700.00"));
        assertThat(toAccount.getBalance()).isEqualTo(new BigDecimal("800.00"));
        verify(accountRepository, times(2)).save(any(Account.class));
    }
    
    @Test
    @DisplayName("Should reject transfer to same account")
    void testTransferSameAccount() {
        // When & Then
        assertThatThrownBy(() -> 
            accountService.transfer(1L, 1L, new BigDecimal("100.00"))
        )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("same account");
    }
    
    @Test
    @DisplayName("Should reject negative transfer amount")
    void testTransferNegativeAmount() {
        // When & Then
        assertThatThrownBy(() -> 
            accountService.transfer(1L, 2L, new BigDecimal("-100.00"))
        )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("must be positive");
    }
    
    @Test
    @DisplayName("Should rollback transfer on insufficient funds")
    void testTransferRollback() {
        // Given
        Account fromAccount = new Account("ACC001", AccountType.SAVINGS);
        fromAccount.setId(1L);
        fromAccount.setBalance(new BigDecimal("50.00"));
        fromAccount.setStatus(AccountStatus.ACTIVE);
        
        Account toAccount = new Account("ACC002", AccountType.SAVINGS);
        toAccount.setId(2L);
        toAccount.setBalance(new BigDecimal("100.00"));
        toAccount.setStatus(AccountStatus.ACTIVE);
        
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));
        
        // When & Then
        assertThatThrownBy(() -> 
            accountService.transfer(1L, 2L, new BigDecimal("100.00"))
        )
        .isInstanceOf(InsufficientFundsException.class);
        
        // Verify balances unchanged
        assertThat(fromAccount.getBalance()).isEqualTo(new BigDecimal("50.00"));
        assertThat(toAccount.getBalance()).isEqualTo(new BigDecimal("100.00"));
        
        // Verify save was never called (transaction rolled back)
        verify(accountRepository, never()).save(any(Account.class));
    }
}
