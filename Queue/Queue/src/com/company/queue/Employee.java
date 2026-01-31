package com.company.queue;

/**
 * Employee Class - A Simple Data Container
 * ==========================================
 * Think of this like a student ID card that holds information about a person.
 * 
 * This class stores information about an employee:
 * - firstName: Their first name (like "John")
 * - lastName: Their last name (like "Smith")
 * - id: A unique number to identify them (like a student ID number)
 * 
 * We'll use Employee objects to demonstrate how queues work!
 */
public class Employee {

    private String firstName;
    private String lastName;
    private int id;

    /**
     * Constructor - Creates a new Employee
     * This is like filling out a form with someone's information.
     * 
     * @param firstName The employee's first name
     * @param lastName The employee's last name
     * @param id A unique identification number
     */
    public Employee(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    // Getters and Setters - Access and modify employee information
    // Think of these like reading or updating information on a form
    
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
     * toString() - Convert Employee to a readable string
     * This is like creating a name tag that shows all the employee's info.
     * When you print an Employee object, this method is automatically called.
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
