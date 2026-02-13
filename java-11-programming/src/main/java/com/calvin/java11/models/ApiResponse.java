package com.calvin.java11.models;

import java.time.Instant;
import java.util.Objects;

/**
 * API Response model for HTTP Client examples.
 * Generic wrapper for API responses with metadata.
 *
 * @author Calvin Lee
 * @since Java 11
 */
public class ApiResponse<T> {
    private final int statusCode;
    private final T data;
    private final String message;
    private final Instant timestamp;
    private final boolean success;

    public ApiResponse(int statusCode, T data, String message, Instant timestamp, boolean success) {
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
        this.timestamp = Objects.requireNonNull(timestamp, "Timestamp cannot be null");
        this.success = success;
    }

    // Factory methods
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, data, "Success", Instant.now(), true);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(200, data, message, Instant.now(), true);
    }

    public static <T> ApiResponse<T> error(int statusCode, String message) {
        return new ApiResponse<>(statusCode, null, message, Instant.now(), false);
    }

    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(404, null, message, Instant.now(), false);
    }

    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(400, null, message, Instant.now(), false);
    }

    // Getters
    public int getStatusCode() { return statusCode; }
    public T getData() { return data; }
    public String getMessage() { return message; }
    public Instant getTimestamp() { return timestamp; }
    public boolean isSuccess() { return success; }

    // Business logic
    public boolean isError() {
        return !success;
    }

    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500;
    }

    public boolean isServerError() {
        return statusCode >= 500;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", success=" + success +
                '}';
    }
}
