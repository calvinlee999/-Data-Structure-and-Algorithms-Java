package com.calvin.java8.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Account domain model for FinTech examples.
 * Represents customer bank accounts.
 *
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
public class Account {
    private final String id;
    private final String customerId;
    private final String accountNumber;
    private final AccountType type;
    private BigDecimal balance;  // Mutable for balance updates
    private final String currency;

    public Account(String id, String customerId, String accountNumber, AccountType type,
                   BigDecimal balance, String currency) {
        this.id = Objects.requireNonNull(id, "Account ID cannot be null");
        this.customerId = Objects.requireNonNull(customerId, "Customer ID cannot be null");
        this.accountNumber = Objects.requireNonNull(accountNumber, "Account number cannot be null");
        this.type = Objects.requireNonNull(type, "Account type cannot be null");
        this.balance = Objects.requireNonNull(balance, "Balance cannot be null");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
    }

    // Getters
    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getAccountNumber() { return accountNumber; }
    public AccountType getType() { return type; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }

    // Business logic
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
    }

    public boolean hasMinimumBalance(BigDecimal minimum) {
        return balance.compareTo(minimum) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Account{id='%s', number='%s', type=%s, balance=%s %s}",
                id, accountNumber, type, balance, currency);
    }

    public enum AccountType {
        CHECKING, SAVINGS, MONEY_MARKET, CD, INVESTMENT, CREDIT_CARD
    }
}
