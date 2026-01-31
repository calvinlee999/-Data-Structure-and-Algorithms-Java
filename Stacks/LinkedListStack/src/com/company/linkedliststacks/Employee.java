package com.company.linkedliststacks;

/*
 * ============================================================================
 * EMPLOYEE CLASS - A Simple Data Object for LinkedList Stack Examples
 * ============================================================================
 * 
 * PURPOSE:
 * This class represents an Employee object that we'll use to demonstrate
 * how LinkedList-based stacks work. Same concept as the array version, but
 * this one will be used in a LinkedList implementation!
 * 
 * WHY USE THIS CLASS?
 * Instead of just storing numbers or names in our stack, we're using a more
 * realistic example. In real jobs, you might stack up tasks, customer orders,
 * or employee records just like this!
 * 
 * REAL-WORLD EXAMPLE:
 * Imagine you're a manager with a stack of employee files on your desk.
 * Each file (Employee object) contains:
 * - First Name
 * - Last Name  
 * - ID Number
 * 
 * DIFFERENCE FROM ARRAY VERSION:
 * This Employee class is identical to the array version, but we keep it separate
 * because it's in a different package (linkedliststacks). This lets us compare
 * the two implementations side-by-side!
 */

public class Employee {

    // Employee's first name (like "Jane")
    private String firstName;
    
    // Employee's last name (like "Jones")
    private String lastName;
    
    // Unique ID number for this employee (like a badge number)
    private int id;

    /**
     * CONSTRUCTOR - Creates a new Employee object
     * 
     * Think of this like filling out a new employee form!
     * We need all three pieces of information to create an employee.
     * 
     * @param firstName - Employee's first name
     * @param lastName - Employee's last name
     * @param id - Unique identification number
     */
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

    /**
     * TOSTRING METHOD - Converts employee data to readable text
     * 
     * This method creates a nice text description of the employee.
     * It's like creating a name tag that shows all the employee's info!
     * 
     * @return A string showing all employee details
     * 
     * EXAMPLE OUTPUT: "Employee{firstName='Jane', lastName='Jones', id=123}"
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
