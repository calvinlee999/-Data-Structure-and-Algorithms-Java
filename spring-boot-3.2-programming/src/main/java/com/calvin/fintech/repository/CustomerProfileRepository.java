package com.calvin.fintech.repository;

import com.calvin.fintech.entity.CustomerProfile;
import com.calvin.fintech.entity.KycStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * CustomerProfile Repository
 * 
 * Demonstrates queries for ONE-TO-ONE relationships
 */
@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
    
    // Find by customer ID
    Optional<CustomerProfile> findByCustomerId(Long customerId);
    
    // Find by KYC status
    List<CustomerProfile> findByKycStatus(KycStatus kycStatus);
    
    // Find by occupation
    List<CustomerProfile> findByOccupation(String occupation);
    
    // Find by nationality
    List<CustomerProfile> findByNationality(String nationality);
    
    // Find profiles pending KYC
    default List<CustomerProfile> findPendingKyc() {
        return findByKycStatus(KycStatus.PENDING);
    }
    
    /**
     * Find profiles by age range
     */
    @Query("""
        SELECT p FROM CustomerProfile p 
        WHERE p.dateOfBirth BETWEEN :startDate AND :endDate
        """)
    List<CustomerProfile> findByAgeRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate);
    
    /**
     * Find profiles needing KYC renewal
     */
    @Query("""
        SELECT p FROM CustomerProfile p 
        WHERE p.kycStatus = 'VERIFIED' 
        AND p.kycVerifiedAt < :renewalDate
        """)
    List<CustomerProfile> findProfilesNeedingKycRenewal(
        @Param("renewalDate") LocalDateTime renewalDate);
    
    /**
     * Bulk update KYC status
     */
    @Modifying
    @Query("""
        UPDATE CustomerProfile p 
        SET p.kycStatus = 'EXPIRED' 
        WHERE p.kycStatus = 'VERIFIED' 
        AND p.kycVerifiedAt < :expiryDate
        """)
    int expireOldKycVerifications(@Param("expiryDate") LocalDateTime expiryDate);
}
