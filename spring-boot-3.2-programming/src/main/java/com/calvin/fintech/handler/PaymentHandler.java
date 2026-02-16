package com.calvin.fintech.handler;

import com.calvin.fintech.domain.PaymentRequest;
import com.calvin.fintech.domain.PaymentResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * Payment Handler (Functional Request Handler)
 * 
 * Replaces @RestController methods
 * Works with RouterFunction configuration
 * 
 * Stateless - no instance variables
 */
@Component
public class PaymentHandler {
    
    private final Function<PaymentRequest, PaymentResponse> paymentProcessor;
    
    public PaymentHandler(Function<PaymentRequest, PaymentResponse> paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
    
    /**
     * Process payment request
     * 
     * Functional handler method:
     * - Takes ServerRequest (functional)
     * - Returns Mono<ServerResponse> (reactive)
     * - No @RequestBody annotation needed
     */
    public Mono<ServerResponse> processPayment(ServerRequest request) {
        return request
            .bodyToMono(PaymentRequest.class)
            .map(paymentProcessor)
            .flatMap(response -> ServerResponse.ok().bodyValue(response))
            .onErrorResume(e -> 
                ServerResponse.badRequest()
                    .bodyValue("Payment processing failed: " + e.getMessage())
            );
    }
    
    /**
     * Validate payment request
     */
    public Mono<ServerResponse> validatePayment(ServerRequest request) {
        return request
            .bodyToMono(PaymentRequest.class)
            .map(PaymentRequest::isValid)
            .flatMap(valid -> 
                ServerResponse.ok().bodyValue(
                    valid ? "Valid" : "Invalid"
                )
            )
            .onErrorResume(e -> 
                ServerResponse.badRequest()
                    .bodyValue("Validation failed: " + e.getMessage())
            );
    }
    
    /**
     * Get payment by ID
     */
    public Mono<ServerResponse> getPayment(ServerRequest request) {
        String id = request.pathVariable("id");
        // Simulate lookup
        return ServerResponse.ok()
            .bodyValue("Payment ID: " + id);
    }
    
    /**
     * Get payment status
     */
    public Mono<ServerResponse> getPaymentStatus(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.ok()
            .bodyValue("Status for payment " + id + ": COMPLETED");
    }
    
    /**
     * Get statistics (admin)
     */
    public Mono<ServerResponse> getStatistics(ServerRequest request) {
        return ServerResponse.ok()
            .bodyValue("Payment statistics");
    }
    
    /**
     * Get failed payments (admin)
     */
    public Mono<ServerResponse> getFailedPayments(ServerRequest request) {
        return ServerResponse.ok()
            .bodyValue("Failed payments list");
    }
    
    /**
     * Retry payment (admin)
     */
    public Mono<ServerResponse> retryPayment(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.ok()
            .bodyValue("Retrying payment " + id);
    }
    
    /**
     * Process priority payment (with API key)
     */
    public Mono<ServerResponse> processPriorityPayment(ServerRequest request) {
        String apiKey = request.headers().firstHeader("X-API-Key");
        
        return request
            .bodyToMono(PaymentRequest.class)
            .map(paymentProcessor)
            .flatMap(response -> 
                ServerResponse.ok()
                    .header("X-Priority", "HIGH")
                    .bodyValue(response)
            );
    }
}
