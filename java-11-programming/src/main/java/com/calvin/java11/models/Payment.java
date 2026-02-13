package com.calvin.java11.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Payment domain model for Java 11 examples.
 * Immutable value object representing a payment transaction.
 *
 * @author Calvin Lee
 * @since Java 11
 */
public class Payment {
    private final String id;
    private final String customerId;
    private final BigDecimal amount;
    private final String currency;
    private final PaymentStatus status;
    private final Instant timestamp;
    private final String merchantId;
    private final String description;

    public enum PaymentStatus {
        PENDING, APPROVED, DECLINED, PROCESSING, COMPLETED, FAILED, REFUNDED
    }

    public Payment(String id, String customerId, BigDecimal amount, String currency,
                   PaymentStatus status, Instant timestamp, String merchantId, String description) {
        this.id = Objects.requireNonNull(id, "Payment ID cannot be null");
        this.customerId = Objects.requireNonNull(customerId, "Customer ID cannot be null");
        this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.timestamp = Objects.requireNonNull(timestamp, "Timestamp cannot be null");
        this.merchantId = merchantId;
        this.description = description;
    }

    // Getters
    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public PaymentStatus getStatus() { return status; }
    public Instant getTimestamp() { return timestamp; }
    public String getMerchantId() { return merchantId; }
    public String getDescription() { return description; }

    // Business logic methods
    public boolean isApproved() {
        return status == PaymentStatus.APPROVED || status == PaymentStatus.COMPLETED;
    }

    public boolean isPending() {
        return status == PaymentStatus.PENDING || status == PaymentStatus.PROCESSING;
    }

    public boolean isHighValue() {
        return amount.compareTo(new BigDecimal("5000.00")) > 0;
    }

    public boolean requiresFraudCheck() {
        return isHighValue() || merchantId == null;
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
        return "Payment{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status=" + status +
                ", timestamp=" + timestamp +
                ", merchantId='" + merchantId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
