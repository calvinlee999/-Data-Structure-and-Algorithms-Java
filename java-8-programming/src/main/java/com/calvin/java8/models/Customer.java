package com.calvin.java8.models;

import java.util.Objects;
import java.util.Optional;

/**
 * Customer domain model for FinTech examples.
 * Demonstrates Optional API usage for nullable fields.
 *
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
public class Customer {
    private final String id;
    private final String name;
    private final int age;
    private final String email;
    private final Address address;  // Nullable
    private final Integer creditScore;  // Nullable

    public Customer(String id, String name, int age, String email, Address address, Integer creditScore) {
        this.id = Objects.requireNonNull(id, "Customer ID cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.age = age;
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.address = address;
        this.creditScore = creditScore;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    
    // Optional getters for nullable fields
    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    public Optional<Integer> getCreditScore() {
        return Optional.ofNullable(creditScore);
    }

    // Business logic
    public boolean isEligibleForBanking() {
        return age >= 18;
    }

    public boolean isHighRisk() {
        return creditScore != null && creditScore < 600;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Customer{id='%s', name='%s', age=%d, email='%s'}",
                id, name, age, email);
    }

    /**
     * Address inner class (can be null for some customers)
     */
    public static class Address {
        private final String street;
        private final String city;
        private final String state;
        private final String zipCode;
        private final String country;

        public Address(String street, String city, String state, String zipCode, String country) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
            this.country = country;
        }

        public String getStreet() { return street; }
        public String getCity() { return city; }
        public String getState() { return state; }
        public String getZipCode() { return zipCode; }
        public String getCountry() { return country; }

        @Override
        public String toString() {
            return String.format("%s, %s, %s %s, %s", street, city, state, zipCode, country);
        }
    }
}
