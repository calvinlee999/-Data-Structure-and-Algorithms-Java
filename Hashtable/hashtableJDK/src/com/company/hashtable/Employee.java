package com.company.hashtable;

/**
 * EMPLOYEE CLASS - For Java's Built-in HashMap Example
 * ====================================================
 * 
 * Same Employee class, but this time we'll use it with Java's
 * built-in HashMap class instead of our custom implementations.
 * 
 * JAVA'S HASHMAP:
 * ---------------
 * - Professional, production-ready hash table
 * - Uses chaining with optimizations (trees for long chains)
 * - Automatically resizes when load factor exceeds 0.75
 * - Handles millions of items efficiently
 * 
 * See hashtable/Employee.java for detailed documentation.
 */
public class Employee {

    private String firstName;
    private String lastName;
    private int id;

    public Employee(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }
}
