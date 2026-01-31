package com.company.stacks;

/*
 * ============================================================================
 * EMPLOYEE CLASS - A Simple Data Object for Stack Examples
 * ============================================================================
 * 
 * PURPOSE:
 * This class represents an Employee object that we'll use to demonstrate
 * how stacks work. Think of it like a playing card or a plate - it's a simple
 * item that we can stack on top of other items!
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
 * When a new employee joins, you add their file to the TOP of the stack.
 * When you need to process someone, you take the file from the TOP.
 * This is exactly how our Stack will work!
 */

public class Employee {

    // Employee's first name (like "John")
    private String firstName;
    
    // Employee's last name (like "Smith")
    private String lastName;
    
    // Unique ID number for this employee (like a student ID)
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
     * EXAMPLE OUTPUT: "Employee{firstName='John', lastName='Smith', id=123}"
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
