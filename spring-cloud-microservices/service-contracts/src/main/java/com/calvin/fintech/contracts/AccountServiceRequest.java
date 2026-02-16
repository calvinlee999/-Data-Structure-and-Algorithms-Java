package com.calvin.fintech.contracts;

/**
 * Account Service Request Contract (Sealed)
 */
public sealed interface AccountServiceRequest extends ServiceRequest
    permits GetAccountRequest, CheckBalanceRequest {
}

/**
 * Request to fetch account by ID
 */
record GetAccountRequest(
    String correlationId,
    java.time.Instant timestamp,
    String accountId
) implements AccountServiceRequest {}

/**
 * Request to check account balance
 */
record CheckBalanceRequest(
    String correlationId,
    java.time.Instant timestamp,
    String accountId,
    double requiredAmount
) implements AccountServiceRequest {}
