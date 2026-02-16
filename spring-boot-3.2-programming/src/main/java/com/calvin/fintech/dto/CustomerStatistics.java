package com.calvin.fintech.dto;

import com.calvin.fintech.entity.CustomerStatus;

/**
 * CustomerStatistics DTO
 * 
 * Used for projection queries that aggregate customer data
 */
public class CustomerStatistics {
    
    private CustomerStatus status;
    private Long count;
    private Long withProfile;
    
    public CustomerStatistics() {
    }
    
    public CustomerStatistics(CustomerStatus status, Long count, Long withProfile) {
        this.status = status;
        this.count = count;
        this.withProfile = withProfile;
    }
    
    public CustomerStatus getStatus() {
        return status;
    }
    
    public void setStatus(CustomerStatus status) {
        this.status = status;
    }
    
    public Long getCount() {
        return count;
    }
    
    public void setCount(Long count) {
        this.count = count;
    }
    
    public Long getWithProfile() {
        return withProfile;
    }
    
    public void setWithProfile(Long withProfile) {
        this.withProfile = withProfile;
    }
    
    @Override
    public String toString() {
        return "CustomerStatistics{" +
                "status=" + status +
                ", count=" + count +
                ", withProfile=" + withProfile +
                '}';
    }
}
