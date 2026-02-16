package com.calvin.fintech.contracts;

/**
 * User Service Request Contract (Sealed)
 */
public sealed interface UserServiceRequest extends ServiceRequest
    permits GetUserRequest, ValidateUserRequest {
}

/**
 * Request to fetch user by ID
 */
record GetUserRequest(
    String correlationId,
    java.time.Instant timestamp,
    String userId
) implements UserServiceRequest {}

/**
 * Request to validate user (KYC check)
 */
record ValidateUserRequest(
    String correlationId,
    java.time.Instant timestamp,
    String userId
) implements UserServiceRequest {}
