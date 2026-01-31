package com.company.LinkedList;

import javax.xml.soap.Node;

/**
 * ============================================================================
 * EMPLOYEE NODE - A Single Link in Our Linked List Chain
 * ============================================================================
 * 
 * WHAT IS A NODE?
 * A node is like a train car that holds:
 * 1. Data (the Employee - like cargo in the train car)
 * 2. A link/pointer to the NEXT train car (node) in line
 * 
 * VISUAL DIAGRAM - HOW A NODE LOOKS:
 * 
 *    +------------------+-------+
 *    |   Employee Data  | Next  |
 *    |   (John Doe)     |  -->  |
 *    +------------------+-------+
 *         ^                 ^
 *         |                 |
 *    Stores the data    Points to next node
 * 
 * 
 * SINGLY LINKED LIST STRUCTURE:
 * (Each node points ONLY to the NEXT node, not backwards)
 * 
 *    HEAD
 *     |
 *     v
 *   [Jane] -> [John] -> [Mary] -> null
 *   
 *   - HEAD points to the first node (Jane)
 *   - Each arrow (->) is the "next" pointer
 *   - The last node points to null (end of list)
 * 
 * 
 * WHY USE NODES?
 * - Nodes allow us to create a chain of data
 * - Each piece of data knows where to find the next piece
 * - We can easily add or remove items from anywhere in the chain
 * 
 * ============================================================================
 */
public class EmployeeNode {
public class EmployeeNode {



    // The actual employee data this node holds
    private Employee employee;
    
    // Pointer/reference to the NEXT node in the linked list
    // If this is null, it means this is the last node (tail)
    private EmployeeNode next;

    /**
     * CONSTRUCTOR - Creates a new node
     * 
     * @param employee The employee data to store in this node
     * 
     * NOTE: When we create a new node, "next" is automatically null.
     * We'll set it later when we connect this node to others!
     */
    public EmployeeNode(Employee employee){
        this.employee = employee;
    }

    // GETTER and SETTER methods to access and modify the node's data
    
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * getNext() - Returns the next node in the chain
     * 
     * This is how we "travel" through the linked list!
     * If it returns null, we've reached the end.
     */
    public EmployeeNode getNext() {
        return next;
    }

    /**
     * setNext() - Connects this node to another node
     * 
     * @param next The node that should come after this one
     * 
     * This is like hooking up train cars!
     * Example: node1.setNext(node2) creates: [node1] -> [node2]
     */
    public void setNext(EmployeeNode next) {
        this.next = next;
    }

    /**
     * toString() - Shows the employee data in this node
     * 
     * When printing a node, we just print the employee inside it.
     */
    public String toString(){
        return employee.toString();
    }
}

/*
 * ============================================================================
 * INTERVIEW QUESTIONS & ANSWERS
 * ============================================================================
 * 
 * Q1: What's the difference between a Singly Linked List and an Array?
 * A1: 
 * ARRAY:
 *   - Elements are stored next to each other in memory (contiguous)
 *   - Fixed size (or expensive to resize)
 *   - Fast access by index: array[5] is O(1)
 *   - Inserting in middle requires shifting elements: O(n)
 *   
 *   [0] [1] [2] [3] [4]  <- indices
 *   Memory: [■][■][■][■][■]  <- all together
 * 
 * SINGLY LINKED LIST:
 *   - Elements can be anywhere in memory (non-contiguous)
 *   - Dynamic size (grows and shrinks easily)
 *   - Slow access by index: must traverse from head: O(n)
 *   - Inserting at front is very fast: O(1)
 *   
 *   [■] --> [■] --> [■] --> null  <- nodes can be anywhere in memory
 * 
 * 
 * Q2: Why can't we go backwards in a singly linked list?
 * A2: Each node only has a "next" pointer, not a "previous" pointer!
 *     It's like a one-way street. If you want to go backwards, you'd
 *     need a DOUBLY linked list (which has both next AND previous pointers).
 * 
 * 
 * Q3: How do you find the middle element of a linked list?
 * A3: Use the "Two Pointer Technique":
 *     - One pointer moves 1 step at a time (slow)
 *     - Another pointer moves 2 steps at a time (fast)
 *     - When the fast pointer reaches the end, the slow pointer is at the middle!
 *     
 *     Example:
 *     [A] -> [B] -> [C] -> [D] -> [E] -> null
 *      ^s/f                                      (both start at head)
 *            ^s          ^f                      (after 1 iteration)
 *                   ^s              ^f           (after 2 iterations)
 *                        ^s                null  (fast is null, slow is at C = middle!)
 * 
 * 
 * Q4: How do you detect a cycle (loop) in a linked list?
 * A4: Also use the Two Pointer Technique (Floyd's Cycle Detection):
 *     - If there's a cycle, the fast pointer will eventually "lap" the slow pointer
 *     - If fast reaches null, there's no cycle
 *     
 *     Think of it like two runners on a circular track:
 *     - The faster runner will eventually catch up to the slower one
 * 
 * 
 * Q5: What are the time complexities for a Singly Linked List?
 * A5: 
 *     - Insert at front: O(1) - very fast! Just change head pointer
 *     - Insert at end: O(n) - must traverse entire list (unless you keep a tail pointer)
 *     - Delete from front: O(1) - just move head to next node
 *     - Delete from end: O(n) - must traverse to find second-to-last node
 *     - Search: O(n) - might have to check every node
 *     - Access by index: O(n) - must count from head
 * 
 * 
 * Q6: When should you use a Linked List instead of an Array?
 * A6: Use a Linked List when:
 *     - You need to insert/delete from the beginning frequently
 *     - The size changes a lot (dynamic sizing)
 *     - You don't need random access by index
 *     
 *     Use an Array when:
 *     - You need fast access to any element by index
 *     - The size is relatively stable
 *     - You're doing lots of searching/sorting
 * 
 * 
 * Q7: How would you reverse a singly linked list?
 * A7: Keep three pointers and reverse the links one at a time:
 *     
 *     Original: [A] -> [B] -> [C] -> null
 *     
 *     Step 1: null <- [A]   [B] -> [C] -> null
 *     Step 2: null <- [A] <- [B]   [C] -> null  
 *     Step 3: null <- [A] <- [B] <- [C]
 *     
 *     Code pattern:
 *     prev = null
 *     current = head
 *     while current != null:
 *         next = current.next     // Save next node
 *         current.next = prev     // Reverse the link
 *         prev = current          // Move prev forward
 *         current = next          // Move current forward
 *     head = prev                 // Update head
 * 
 * ============================================================================
 */
