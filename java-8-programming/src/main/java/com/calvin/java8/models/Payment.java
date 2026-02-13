package com.calvin.java8.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Payment domain model for FinTech examples.
 * Demonstrates Java 8 features like immutability, Optional, and record-like structure.
 *
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
public class Payment {
    private final String id;
    private final String customerId;
    private final BigDecimal amount;
    private final String currency;
    private final PaymentStatus status;
    private final Instant timestamp;
    private final String merchantId;

    public Payment(String id, String customerId, BigDecimal amount, String currency,
                   PaymentStatus status, Instant timestamp, String merchantId) {
        this.id = Objects.requireNonNull(id, "Payment ID cannot be null");
        this.customerId = Objects.requireNonNull(customerId, "Customer ID cannot be null");
        this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.timestamp = Objects.requireNonNull(timestamp, "Timestamp cannot be null");
        this.merchantId = merchantId;
    }

    // Getters
    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public PaymentStatus getStatus() { return status; }
    public Instant getTimestamp() { return timestamp; }
    public String getMerchantId() { return merchantId; }

    // Business logic methods for method references
    public boolean isApproved() {
        return status == PaymentStatus.APPROVED;
    }

    public boolean isPending() {
        return status == PaymentStatus.PENDING;
    }

    public boolean isHighValue() {
        return amount.compareTo(new BigDecimal("10000")) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Payment{id='%s', customer='%s', amount=%s %s, status=%s}",
                id, customerId, amount, currency, status);
    }

    public enum PaymentStatus {
        PENDING, APPROVED, DECLINED, PROCESSING, COMPLETED, FAILED
    }
}
