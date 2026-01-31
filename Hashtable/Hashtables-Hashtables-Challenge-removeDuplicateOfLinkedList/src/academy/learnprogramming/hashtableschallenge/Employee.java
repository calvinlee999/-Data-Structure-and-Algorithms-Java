package academy.learnprogramming.hashtableschallenge;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * EMPLOYEE CLASS - Demonstrates HashMap Best Practices
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * PURPOSE:
 * --------
 * This class represents an employee and is used in the duplicate removal
 * challenge. It properly implements hashCode() and equals() methods which
 * are essential for working with HashMaps and HashSets.
 * 
 * CRITICAL METHODS FOR HASHMAP:
 * ----------------------------
 * 
 * 1. hashCode():
 *    - Generates an integer hash value for this employee
 *    - Used by HashMap to determine which "bucket" to place the object in
 *    - Based on firstName, lastName, and id
 *    - Uses magic number 31 (a prime) to reduce collisions
 *    
 * 2. equals():
 *    - Determines if two Employee objects are "equal"
 *    - Returns true if id, firstName, and lastName all match
 *    - Used by HashMap when there's a hash collision
 *    
 * 3. getId():
 *    - Returns the employee ID
 *    - Used as the KEY in our HashMap for duplicate detection
 *    
 * WHY THE MAGIC NUMBER 31 in hashCode()?
 * ---------------------------------------
 * result = 31 * result + field
 * 
 * Reasons for using 31:
 * 1. It's a prime number (reduces hash collisions)
 * 2. Multiplication by 31 can be optimized by JVM: 31*i = (i<<5)-i
 * 3. It's large enough to reduce collisions but small enough to avoid overflow
 * 4. It's an odd number (important for hash distribution)
 * 
 * THE CONTRACT: hashCode() and equals()
 * -------------------------------------
 * These two methods MUST follow these rules:
 * 
 * 1. If a.equals(b) returns true, then:
 *    a.hashCode() MUST equal b.hashCode()
 *    
 * 2. If a.hashCode() == b.hashCode(), then:
 *    a.equals(b) might be true OR false (hash collision)
 *    
 * 3. If you override equals(), you MUST override hashCode()!
 *    (Otherwise HashMap won't work correctly)
 * 
 * WHAT HAPPENS IF WE DON'T OVERRIDE THESE?
 * ----------------------------------------
 * - Without equals(): Two employees with same data would be considered different
 * - Without hashCode(): HashMap would place identical employees in different buckets
 * - Result: Duplicate detection would FAIL!
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
