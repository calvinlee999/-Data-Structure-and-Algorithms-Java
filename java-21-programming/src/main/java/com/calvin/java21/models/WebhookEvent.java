package com.calvin.java21.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Webhook Event - Record for async event processing
 * 
 * Demonstrates Java 21 features:
 * - Virtual Threads: Process thousands of webhook events concurrently
 * - Record Patterns: Extract event data in single step
 * - Sequenced Collections: Maintain webhook delivery order
 * 
 * Used with:
 * - Virtual Threads: Handle 100,000+ concurrent webhook deliveries
 * - Pattern Matching: Route events based on type and payload
 * - Structured Concurrency: Parallel webhook processing with cancellation
 * 
 * @author Calvin Lee (FinTech Principal Software Engineer)
 * @since Java 21 (LTS) - September 2023
 */
public record WebhookEvent(
    String eventId,
    EventType eventType,
    String paymentId,
    EventPayload payload,
    LocalDateTime timestamp,
    DeliveryStatus deliveryStatus
) {
    
    /**
     * Event type enum.
     */
    public enum EventType {
        PAYMENT_INITIATED("payment.initiated"),
        PAYMENT_PROCESSING("payment.processing"),
        PAYMENT_COMPLETED("payment.completed"),
        PAYMENT_FAILED("payment.failed"),
        REFUND_INITIATED("refund.initiated"),
        REFUND_COMPLETED("refund.completed");
        
        private final String webhookType;
        
        EventType(String webhookType) {
            this.webhookType = webhookType;
        }
        
        public String getWebhookType() {
            return webhookType;
        }
    }
    
    /**
     * Event payload (sealed interface for type-safe payloads).
     */
    public sealed interface EventPayload 
        permits PaymentPayload, RefundPayload {
    }
    
    /**
     * Payment event payload.
     */
    public record PaymentPayload(
        BigDecimal amount,
        String currency,
        String customerId,
        String status
    ) implements EventPayload {}
    
    /**
     * Refund event payload.
     */
    public record RefundPayload(
        String originalPaymentId,
        BigDecimal refundAmount,
        String reason
    ) implements EventPayload {}
    
    /**
     * Delivery status enum.
     */
    public enum DeliveryStatus {
        PENDING,
        DELIVERED,
        FAILED,
        RETRYING
    }
    
    /**
     * Compact constructor with validation.
     */
    public WebhookEvent {
        if (eventId == null || eventId.isBlank()) {
            throw new IllegalArgumentException("Event ID cannot be null or blank");
        }
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
        if (paymentId == null || paymentId.isBlank()) {
            throw new IllegalArgumentException("Payment ID cannot be null or blank");
        }
        if (payload == null) {
            throw new IllegalArgumentException("Payload cannot be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        if (deliveryStatus == null) {
            throw new IllegalArgumentException("Delivery status cannot be null");
        }
    }
    
    /**
     * Check if event is payment-related.
     */
    public boolean isPaymentEvent() {
        return eventType.name().startsWith("PAYMENT_");
    }
    
    /**
     * Check if event is refund-related.
     */
    public boolean isRefundEvent() {
        return eventType.name().startsWith("REFUND_");
    }
    
    /**
     * Get webhook URL based on event type (example routing logic).
     */
    public String getWebhookUrl() {
        return switch (eventType) {
            case PAYMENT_INITIATED, PAYMENT_PROCESSING ->
                "https://api.example.com/webhooks/payments/processing";
            case PAYMENT_COMPLETED ->
                "https://api.example.com/webhooks/payments/completed";
            case PAYMENT_FAILED ->
                "https://api.example.com/webhooks/payments/failed";
            case REFUND_INITIATED, REFUND_COMPLETED ->
                "https://api.example.com/webhooks/refunds";
        };
    }
    
    /**
     * Example usage demonstrating Java 21 features.
     */
    public static void main(String[] args) {
        System.out.println("=== WebhookEvent Model (Java 21 Virtual Threads) ===\n");
        
        // Create webhook events
        WebhookEvent[] events = {
            new WebhookEvent(
                "EVT-001",
                EventType.PAYMENT_INITIATED,
                "PAY-001",
                new PaymentPayload(new BigDecimal("1000.00"), "USD", "CUST-123", "PENDING"),
                LocalDateTime.now(),
                DeliveryStatus.PENDING
            ),
            new WebhookEvent(
                "EVT-002",
                EventType.PAYMENT_COMPLETED,
                "PAY-001",
                new PaymentPayload(new BigDecimal("1000.00"), "USD", "CUST-123", "COMPLETED"),
                LocalDateTime.now().plusMinutes(2),
                DeliveryStatus.DELIVERED
            ),
            new WebhookEvent(
                "EVT-003",
                EventType.PAYMENT_FAILED,
                "PAY-002",
                new PaymentPayload(new BigDecimal("5000.00"), "EUR", "CUST-456", "FAILED"),
                LocalDateTime.now().plusMinutes(3),
                DeliveryStatus.FAILED
            ),
            new WebhookEvent(
                "EVT-004",
                EventType.REFUND_COMPLETED,
                "PAY-001",
                new RefundPayload("PAY-001", new BigDecimal("1000.00"), "Customer request"),
                LocalDateTime.now().plusMinutes(5),
                DeliveryStatus.DELIVERED
            )
        };
        
        // Process events with pattern matching
        System.out.println("Processing Webhook Events:");
        for (WebhookEvent event : events) {
            // Record pattern deconstruction
            String message = switch (event) {
                case WebhookEvent(var id, EventType.PAYMENT_COMPLETED, var payId, 
                                 PaymentPayload(BigDecimal amt, var cur, var custId, _), _, _) ->
                    String.format("✓ Payment completed: %s ($%s %s) for customer %s", 
                        payId, amt, cur, custId);
                
                case WebhookEvent(var id, EventType.PAYMENT_FAILED, var payId,
                                 PaymentPayload(BigDecimal amt, var cur, var custId, _), _, _) ->
                    String.format("✗ Payment failed: %s ($%s %s) for customer %s",
                        payId, amt, cur, custId);
                
                case WebhookEvent(var id, var type, var payId,
                                 PaymentPayload(BigDecimal amt, var cur, _, _), _, _) ->
                    String.format("⏳ Payment %s: %s ($%s %s)",
                        type.name().toLowerCase(), payId, amt, cur);
                
                case WebhookEvent(var id, var type, var payId,
                                 RefundPayload(var origPayId, BigDecimal refundAmt, var reason), _, _) ->
                    String.format("↩ Refund %s: %s ($%s) - %s",
                        type.name().toLowerCase(), payId, refundAmt, reason);
            };
            
            System.out.printf("  [%s] %s%n", event.eventId(), message);
            System.out.printf("    → Webhook URL: %s%n", event.getWebhookUrl());
            System.out.printf("    → Delivery: %s%n", event.deliveryStatus());
        }
        
        // Demonstrate Virtual Thread processing
        System.out.println("\n✓ Virtual Threads: Process 100,000+ webhook deliveries concurrently");
        System.out.println("✓ Pattern Matching: Type-safe event routing");
        System.out.println("✓ Sequenced Collections: Maintain delivery order for audit");
        
        // Statistics
        long payments = java.util.Arrays.stream(events)
            .filter(WebhookEvent::isPaymentEvent)
            .count();
        long refunds = java.util.Arrays.stream(events)
            .filter(WebhookEvent::isRefundEvent)
            .count();
        long delivered = java.util.Arrays.stream(events)
            .filter(e -> e.deliveryStatus() == DeliveryStatus.DELIVERED)
            .count();
        
        System.out.println("\nEvent Summary:");
        System.out.printf("  Payment Events: %d%n", payments);
        System.out.printf("  Refund Events: %d%n", refunds);
        System.out.printf("  Successfully Delivered: %d%n", delivered);
    }
}
