package academy.learnprogramming.challenge1;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * EMPLOYEE CLASS - The Data We're Storing
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * PURPOSE:
 * --------
 * This is a simple class representing an employee with basic information.
 * It's the "cargo" that each node in our linked list carries.
 * 
 * IMPORTANT METHOD: equals()
 * --------------------------
 * The equals() method is CRUCIAL for our challenge because we need to
 * COMPARE employees to find the right one to insert before.
 * 
 * Two employees are considered "equal" if they have:
 * - Same ID
 * - Same first name
 * - Same last name
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
