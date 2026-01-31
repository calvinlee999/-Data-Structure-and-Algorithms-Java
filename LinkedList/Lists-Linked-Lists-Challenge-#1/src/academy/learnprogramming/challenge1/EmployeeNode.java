package academy.learnprogramming.challenge1;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * EMPLOYEE NODE - Building Block of Doubly Linked List
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * WHAT IS A NODE?
 * ---------------
 * Think of a node as a "container" or "train car" that holds:
 * 1. DATA: The actual information (an Employee object)
 * 2. NEXT POINTER: A reference to the next node (train car ahead)
 * 3. PREVIOUS POINTER: A reference to the previous node (train car behind)
 * 
 * WHY THREE FIELDS?
 * -----------------
 * - employee: Stores the actual data we care about
 * - next: Allows us to move FORWARD through the list
 * - previous: Allows us to move BACKWARD through the list (this is what makes
 *             it "doubly" linked instead of just "singly" linked)
 * 
 * MEMORY LAYOUT:
 * --------------
 * Each node takes up memory for:
 * - 1 Employee object reference (8 bytes on 64-bit systems)
 * - 2 EmployeeNode references (16 bytes total)
 * - Plus whatever memory the Employee object itself uses
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 */

public class EmployeeNode {

    // The actual data this node is storing
    private Employee employee;
    
    // Pointer to the next node in the list (null if this is the last node)
    private EmployeeNode next;
    
    // Pointer to the previous node in the list (null if this is the first node)
    private EmployeeNode previous;

    public EmployeeNode(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeNode getNext() {
        return next;
    }

    public void setNext(EmployeeNode next) {
        this.next = next;
    }

    public EmployeeNode getPrevious() {
        return previous;
    }

    public void setPrevious(EmployeeNode previous) {
        this.previous = previous;
    }

    public String toString() {
        return employee.toString();
    }


}
