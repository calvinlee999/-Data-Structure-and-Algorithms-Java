package com.company.DoublyLinkedList;

/**
 * ============================================================================
 * EMPLOYEE CLASS - The Data We Store in Our Doubly Linked List
 * ============================================================================
 * 
 * WHAT IS THIS?
 * This class represents ONE employee - like a profile card with a person's info.
 * In our doubly linked list, each node will hold one Employee object.
 * 
 * REAL-WORLD ANALOGY:
 * Think of this like a baseball card:
 * - It has a player's first name, last name, and jersey number
 * - You can collect many cards and organize them in different ways
 * - Each card is separate but can be connected to others
 * 
 * NOTE: This is the SAME as the Employee class in the singly linked list!
 * The difference is in how the NODES connect (doubly vs singly).
 * 
 * ============================================================================
 */
public class Employee {

    // Private variables - only this class can directly change them (data protection!)
    private String firstName;
    private String lastName;
    private int id;

    // Private variables - only this class can directly change them (data protection!)
    private String firstName;
    private String lastName;
    private int id;

    /**
     * CONSTRUCTOR - Creates a new Employee
     * 
     * @param firstName The employee's first name
     * @param lastName The employee's last name
     * @param id The employee's unique ID number
     */
    public Employee(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    // GETTER and SETTER methods - safe access to private data
    
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

    /**
     * toString() - Converts employee info to a readable string
     * Gets called automatically when printing an Employee object.
     */
    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }
}
