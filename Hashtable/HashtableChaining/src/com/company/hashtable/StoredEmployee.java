package com.company.hashtable;

/**
 * STOREDEMPLOYEE - Key-Value Pair for Chaining
 * =============================================
 * 
 * Same structure as in linear probing implementation.
 * Stores both the key and the employee together.
 * 
 * In chaining, this is even MORE important because:
 * - Multiple items can be in the same chain
 * - We must check each item's key to find the right one
 * 
 * See hashtable/StoredEmployee.java for detailed documentation.
 */
public class StoredEmployee {

    public String key;
    public Employee employee;

    /**
     * Constructor: Creates a key-value pair
     * @param key The search key (last name)
     * @param employee The employee object
     */
    public StoredEmployee(String key, Employee employee) {
        this.key = key;
        this.employee = employee;
    }
}
