package academy.learnprogramming.hashtableschallenge;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * EMPLOYEE CLASS - For Hashtable Challenges
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * PURPOSE:
 * --------
 * This class represents an employee and demonstrates important concepts
 * for working with hashtables and HashMaps.
 * 
 * IMPORTANT METHODS FOR HASHTABLES:
 * ---------------------------------
 * 
 * 1. hashCode():
 *    - Generates a hash value for this employee
 *    - Used by HashMap/HashSet to determine which "bucket" to use
 *    - MUST be consistent with equals() method!
 *    
 * 2. equals():
 *    - Determines if two employees are "the same"
 *    - Used to handle collisions (when two objects have same hash)
 *    - If equals() returns true, hashCode() MUST return same value
 *    
 * WHY ARE BOTH NEEDED?
 * -------------------
 * - hashCode() is fast but can have collisions (different objects, same hash)
 * - equals() is the final check to see if objects are really equal
 * - Together they make HashMap work correctly and efficiently
 * 
 * CONTRACT BETWEEN hashCode() AND equals():
 * ----------------------------------------
 * 1. If a.equals(b) is true, then a.hashCode() == b.hashCode()
 * 2. If a.hashCode() == b.hashCode(), a.equals(b) might be true or false
 * 3. If a.equals(b) is false, hashCode can be same or different
 * 
 * ═══════════════════════════════════════════════════════════════════════════
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (!firstName.equals(employee.firstName)) return false;
        return lastName.equals(employee.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + id;
        return result;
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
