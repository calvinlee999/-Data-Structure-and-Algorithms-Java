package com.company.hashtable;

/**
 * EMPLOYEE CLASS - A Simple Data Object
 * =====================================
 * 
 * Think of this class like a student ID card or library card - it holds basic information
 * about an employee that we want to store and retrieve quickly using a hash table.
 * 
 * WHAT IS THIS CLASS FOR?
 * -----------------------
 * This Employee class is a "data container" - it's like a box that holds three pieces of
 * information about a person: their first name, last name, and an ID number.
 * 
 * REAL-WORLD ANALOGY:
 * -------------------
 * Think of this like a recipe card in a recipe box:
 * - The card (Employee object) contains information about one recipe (one employee)
 * - Each card has specific information: ingredients, steps, cooking time
 * - Similarly, each Employee has: firstName, lastName, id
 * 
 * WHY USE THIS WITH HASH TABLES?
 * ------------------------------
 * Hash tables need to store SOMETHING - they're like filing cabinets that need files to store.
 * This Employee class is the "file" that contains the actual data we care about.
 * The hash table will organize these Employee objects so we can find them super fast!
 * 
 * @author Data Structures Learning Project
 * @version 1.0
 */
public class Employee {

    // PRIVATE VARIABLES - Like Locked Compartments
    // These are "private" which means only this class can directly access them
    // It's like having a locker - only you have the combination!
    
    private String firstName;  // Employee's first name (e.g., "Jane")
    private String lastName;   // Employee's last name (e.g., "Jones")
    private int id;            // Unique employee ID number (e.g., 123)

    private String firstName;  // Employee's first name (e.g., "Jane")
    private String lastName;   // Employee's last name (e.g., "Jones")
    private int id;            // Unique employee ID number (e.g., 123)

    /**
     * CONSTRUCTOR - Creating a New Employee
     * ======================================
     * 
     * This is like filling out a new student enrollment form - you need to provide
     * all the required information (first name, last name, ID) to create the record.
     * 
     * @param firstName The employee's first name
     * @param lastName The employee's last name
     * @param id The employee's unique ID number
     * 
     * EXAMPLE:
     * Employee john = new Employee("John", "Doe", 4567);
     * This creates a new employee record for John Doe with ID 4567
     */
    public Employee(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    /**
     * GETTER METHODS - Reading the Stored Information
     * ===============================================
     * 
     * These methods are like viewing windows - they let you LOOK at the information
     * stored inside the Employee object without changing it.
     * 
     * Think of it like checking your student ID card to see your name or ID number.
     */
    
    /**
     * Gets the employee's first name
     * @return The first name as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * SETTER METHODS - Updating the Stored Information
     * ================================================
     * 
     * These methods let you CHANGE the information, like updating your address
     * when you move to a new house.
     */
    
    /**
     * Updates the employee's first name
     * @param firstName The new first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the employee's last name
     * @return The last name as a String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Updates the employee's last name
     * @param lastName The new last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the employee's ID number
     * @return The ID as an integer
     */
    public int getId() {
        return id;
    }

    /**
     * Updates the employee's ID number
     * @param id The new ID number to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * TO-STRING METHOD - Converting to Readable Text
     * ==============================================
     * 
     * This is like creating a name tag or business card - it takes all the
     * employee information and formats it in a nice, readable way.
     * 
     * Instead of seeing gibberish computer memory addresses when you print an Employee,
     * you'll see something like: Employee{firstName='Jane', lastName='Jones', id=123}
     * 
     * @return A formatted string with all employee information
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

/**
 * ==========================================
 * INTERVIEW QUESTIONS & ANSWERS - Employee Class
 * ==========================================
 * 
 * Q1: Why are the instance variables (firstName, lastName, id) marked as private?
 * A1: Making them private is called "encapsulation" - it's like putting valuables in a safe.
 *     Only the methods inside this class can directly access these variables. This protects
 *     the data from being accidentally changed by code outside the class. If someone wants
 *     to see or change the data, they must use the getter and setter methods we provide.
 *     
 *     ANALOGY: Your locker combination is private - only you can open it directly.
 *     But you can show people what's inside (getter) or put new things in (setter).
 * 
 * Q2: What is the purpose of getter and setter methods?
 * A2: Getters and setters provide controlled access to private data:
 *     - GETTERS (like getFirstName()): Let you READ the data safely
 *     - SETTERS (like setFirstName()): Let you UPDATE the data with validation
 *     
 *     We could add validation in setters. For example:
 *     public void setId(int id) {
 *         if (id > 0) {  // Only allow positive IDs
 *             this.id = id;
 *         }
 *     }
 *     
 *     BENEFIT: If we made the variables public, anyone could set id to -999, which doesn't
 *     make sense! Setters let us check that the new value is valid before storing it.
 * 
 * Q3: Why do we override the toString() method?
 * A3: Without toString(), printing an Employee would show something useless like:
 *     "Employee@4e25154f" (a memory address in hexadecimal)
 *     
 *     By overriding toString(), we control what gets displayed:
 *     "Employee{firstName='Jane', lastName='Jones', id=123}"
 *     
 *     This is MUCH more helpful for debugging and displaying data to users!
 *     
 *     REAL-WORLD USE: When you call System.out.println(employee), Java automatically
 *     calls the toString() method behind the scenes.
 * 
 * Q4: What's the difference between "this.firstName" and "firstName" in the constructor?
 * A4: This is called "shadowing" - when a parameter has the same name as an instance variable:
 *     - "firstName" (no "this") refers to the PARAMETER (the input value)
 *     - "this.firstName" refers to the INSTANCE VARIABLE (the class field)
 *     
 *     Example from constructor:
 *     public Employee(String firstName, String lastName, int id) {
 *         this.firstName = firstName;  // Set the class field to the parameter value
 *     }
 *     
 *     "this.firstName" means "this object's firstName variable"
 *     "firstName" means "the parameter passed into the constructor"
 * 
 * Q5: How does this Employee class relate to hash tables?
 * A5: Employee is the VALUE stored in the hash table:
 *     - KEY: A string like "Jones" or "Doe" (the employee's last name)
 *     - VALUE: An Employee object (contains all the employee details)
 *     
 *     Think of it like a phone book:
 *     - KEY: Person's name (easy to look up)
 *     - VALUE: Phone number (the information you want)
 *     
 *     In our hash table:
 *     - KEY: "Jones"
 *     - VALUE: Employee{firstName='Jane', lastName='Jones', id=123}
 *     
 *     The hash table provides FAST lookup - when you search for "Jones",
 *     it quickly returns the Employee object with all of Jane's information!
 * 
 * ==========================================
 * COMPLEXITY ANALYSIS
 * ==========================================
 * 
 * Constructor: O(1) - Constant time
 * - Creating an Employee takes the same time regardless of how much data we have
 * 
 * Getters and Setters: O(1) - Constant time
 * - Reading or updating a single field is instant
 * 
 * toString(): O(1) - Constant time
 * - Creating the string representation takes constant time
 * 
 * Space Complexity: O(1)
 * - Each Employee object uses a fixed amount of memory (2 strings + 1 int)
 */
