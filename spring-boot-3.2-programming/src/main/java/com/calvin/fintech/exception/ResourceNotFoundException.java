package com.calvin.fintech.exception;

/**
 * Resource Not Found Exception
 * 
 * Thrown when a requested resource doesn't exist in the database.
 * Maps to HTTP 404 Not Found
 */
public class ResourceNotFoundException extends RuntimeException {
    
    private final String resourceType;
    private final Object resourceId;
    
    public ResourceNotFoundException(String resourceType, Object resourceId) {
        super(String.format("%s with ID '%s' not found", resourceType, resourceId));
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }
    
    public String getResourceType() {
        return resourceType;
    }
    
    public Object getResourceId() {
        return resourceId;
    }
}
