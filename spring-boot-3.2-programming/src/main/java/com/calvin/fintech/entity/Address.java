package com.calvin.fintech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Address Value Object
 * 
 * This is NOT an entity - it's an @Embeddable value object.
 * It doesn't have its own table or ID.
 * 
 * Instead, its fields are stored directly in the parent entity's table.
 * Think of it as a "group of related fields" that can be reused.
 * 
 * Example: Customer has both billing and shipping addresses.
 * Instead of duplicating fields, we use one Address class for both.
 */
@Embeddable
public class Address {
    
    @Column(length = 200)
    private String street;
    
    @Column(length = 100)
    private String city;
    
    @Column(length = 50)
    private String state;
    
    @Column(name = "zip_code", length = 20)
    private String zipCode;
    
    @Column(length = 50)
    private String country;
    
    // No-arg constructor required by JPA
    public Address() {
    }
    
    public Address(String street, String city, String state, String zipCode, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }
    
    // Getters and setters
    
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
   public void setState(String state) {
        this.state = state;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * Get formatted address
     */
    public String getFormattedAddress() {
        return String.format("%s, %s, %s %s, %s", 
            street, city, state, zipCode, country);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) &&
               Objects.equals(city, address.city) &&
               Objects.equals(state, address.state) &&
               Objects.equals(zipCode, address.zipCode) &&
               Objects.equals(country, address.country);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(street, city, state, zipCode, country);
    }
    
    @Override
    public String toString() {
        return getFormattedAddress();
    }
}
