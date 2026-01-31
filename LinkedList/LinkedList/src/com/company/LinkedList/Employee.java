package com.company.LinkedList;

/**
 * ============================================================================
 * EMPLOYEE CLASS - The Data We Store in Our Linked List
 * ============================================================================
 * 
 * WHAT IS THIS?
 * This class represents ONE employee - like a profile card with a person's info.
 * In our linked list, each node will hold one Employee object.
 * 
 * REAL-WORLD ANALOGY:
 * Think of this like a baseball card:
 * - It has a player's first name, last name, and jersey number
 * - You can collect many cards and organize them in different ways
 * - Each card is separate but can be connected to others
 * 
 * WHY USE A CLASS?
 * Instead of just storing simple numbers, we can store complex information
 * about real people. This makes our data structure more useful in real programs!
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
     * 
     * When you create an employee: Employee john = new Employee("John", "Doe", 123);
     * This constructor runs and sets up all the data.
     */
    public Employee(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    // GETTER METHODS - Allow other classes to READ the private data
    // (Like letting someone see your baseball card without giving it to them)
    
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
     * 
     * This method gets called automatically when you print an Employee object.
     * Example output: Employee{firstName='John', lastName='Doe', id=123}
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

/*
 * ============================================================================
 * INTERVIEW QUESTIONS & ANSWERS
 * ============================================================================
 * 
 * Q1: Why do we make the variables private instead of public?
 * A1: This is called "encapsulation" - it protects our data! If variables 
 *     were public, anyone could change them directly, which could break our 
 *     program. By using getter/setter methods, we control HOW data is accessed.
 * 
 * Q2: What's the difference between an Employee and an EmployeeNode?
 * A2: - Employee = The actual data (like the contents of a box)
 *     - EmployeeNode = A container that holds an Employee AND a link to the 
 *       next node (like a train car that holds cargo and connects to other cars)
 * 
 * Q3: Why override toString()?
 * A3: Without toString(), printing an Employee would show something like
 *     "Employee@2a84aee7" (memory address). With toString(), we get readable
 *     information about the employee!
 * 
 * ============================================================================
 */
