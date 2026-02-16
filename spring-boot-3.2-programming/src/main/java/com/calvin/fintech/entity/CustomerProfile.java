package com.calvin.fintech.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

/**
 * CustomerProfile Entity - ONE-TO-ONE Relationship
 * 
 * Demonstrates ONE-TO-ONE relationship with Customer.
 * 
 * A customer has exactly ONE profile.
 * A profile belongs to exactly ONE customer.
 * 
 * This is the "owning side" because it has the @JoinColumn.
 */
@Entity
@Table(name = "customer_profiles")
public class CustomerProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * ONE-TO-ONE relationship
     * 
     * This is the OWNING side (has the foreign key column)
     * @JoinColumn: Specifies the foreign key column name
     */
    @OneToOne(fetch = FetchType.LAZY)  // LAZY: don't load customer unless accessed
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;
    
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(length = 20)
    private String occupation;
    
    @Column(name = "annual_income")
    private java.math.BigDecimal annualIncome;
    
    @Column(name = "tax_id", length = 20)
    private String taxId;  // SSN, EIN, etc.
    
    @Column(length = 50)
    private String nationality;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "kyc_status", length = 20)
    private KycStatus kycStatus = KycStatus.PENDING;
    
    @Column(name = "kyc_verified_at")
    private java.time.LocalDateTime kycVerifiedAt;
    
    /**
     * Preferences stored as JSON
     */
    @Column(columnDefinition = "TEXT")
    private String preferences;  // Store as JSON string
    
    // Constructors
    
    public CustomerProfile() {
    }
    
    public CustomerProfile(Customer customer) {
        this.customer = customer;
    }
    
    // Business methods
    
    /**
     * Calculate customer's age
     */
    public int getAge() {
        if (dateOfBirth == null) {
            return 0;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
    
    /**
     * Check if KYC is complete
     */
    public boolean isKycVerified() {
        return kycStatus == KycStatus.VERIFIED;
    }
    
    /**
     * Verify KYC
     */
    public void verifyKyc() {
        this.kycStatus = KycStatus.VERIFIED;
        this.kycVerifiedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * Reject KYC
     */
    public void rejectKyc() {
        this.kycStatus = KycStatus.REJECTED;
    }
    
    // Getters and setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getOccupation() {
        return occupation;
    }
    
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    public java.math.BigDecimal getAnnualIncome() {
        return annualIncome;
    }
    
    public void setAnnualIncome(java.math.BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }
    
    public String getTaxId() {
        return taxId;
    }
    
    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
    
    public String getNationality() {
        return nationality;
    }
    
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    
    public KycStatus getKycStatus() {
        return kycStatus;
    }
    
    public void setKycStatus(KycStatus kycStatus) {
        this.kycStatus = kycStatus;
    }
    
    public java.time.LocalDateTime getKycVerifiedAt() {
        return kycVerifiedAt;
    }
    
    public void setKycVerifiedAt(java.time.LocalDateTime kycVerifiedAt) {
        this.kycVerifiedAt = kycVerifiedAt;
    }
    
    public String getPreferences() {
        return preferences;
    }
    
    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
}

/**
 * KYC (Know Your Customer) Status
 */
enum KycStatus {
    PENDING,
    IN_PROGRESS,
    VERIFIED,
    REJECTED,
    EXPIRED
}
