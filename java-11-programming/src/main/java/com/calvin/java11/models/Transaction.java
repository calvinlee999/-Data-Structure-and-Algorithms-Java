package com.calvin.java11.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Transaction domain model for Java 11 examples.
 * Represents a financial transaction in the system.
 *
 * @author Calvin Lee
 * @since Java 11
 */
public class Transaction {
    private final String id;
    private final String accountId;
    private final BigDecimal amount;
    private final String currency;
    private final TransactionType type;
    private final LocalDateTime timestamp;
    private final String description;
    private final String country;

    public enum TransactionType {
        DEBIT, CREDIT, TRANSFER, WITHDRAWAL, DEPOSIT, PAYMENT, REFUND, FEE
    }

    public Transaction(String id, String accountId, BigDecimal amount, String currency,
                       TransactionType type, LocalDateTime timestamp, String description, String country) {
        this.id = Objects.requireNonNull(id, "Transaction ID cannot be null");
        this.accountId = Objects.requireNonNull(accountId, "Account ID cannot be null");
        this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
        this.type = Objects.requireNonNull(type, "Type cannot be null");
        this.timestamp = Objects.requireNonNull(timestamp, "Timestamp cannot be null");
        this.description = description;
        this.country = country;
    }

    // Getters
    public String getId() { return id; }
    public String getAccountId() { return accountId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public TransactionType getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getDescription() { return description; }
    public String getCountry() { return country; }

    // Business logic methods
    public boolean isInternational() {
        return country != null && !country.equals("US");
    }

    public boolean isDebit() {
        return type == TransactionType.DEBIT || type == TransactionType.WITHDRAWAL || type == TransactionType.FEE;
    }

    public boolean isCredit() {
        return type == TransactionType.CREDIT || type == TransactionType.DEPOSIT || type == TransactionType.REFUND;
    }

    public boolean isHighValue() {
        return amount.compareTo(new BigDecimal("10000.00")) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", type=" + type +
                ", timestamp=" + timestamp +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
