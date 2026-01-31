package com.company.DoublyLinkedList;

/**
 * ============================================================================
 * EMPLOYEE NODE - A Link in Our Doubly Linked List Chain
 * ============================================================================
 * 
 * WHAT IS A DOUBLY LINKED NODE?
 * A node in a doubly linked list is like a train car that can connect in BOTH directions:
 * 1. Data (the Employee - like cargo)
 * 2. A link to the NEXT train car (next pointer)
 * 3. A link to the PREVIOUS train car (previous pointer) ⭐ NEW!
 * 
 * VISUAL DIAGRAM - HOW A DOUBLY LINKED NODE LOOKS:
 * 
 *    +------+------------------+-------+
 *    | Prev |   Employee Data  | Next  |
 *    | <--  |   (John Doe)     |  -->  |
 *    +------+------------------+-------+
 *       ^           ^              ^
 *       |           |              |
 *   Points to   Stores the    Points to
 *   previous     employee      next node
 *     node
 * 
 * 
 * DOUBLY LINKED LIST STRUCTURE:
 * (Each node points to BOTH the NEXT and PREVIOUS nodes!)
 * 
 *    HEAD                                        TAIL
 *     |                                           |
 *     v                                           v
 *   null <- [Jane] <-> [John] <-> [Mary] <-> [Mike] -> null
 *   
 *   - HEAD points to the first node (Jane)
 *   - TAIL points to the last node (Mike) ⭐ NEW!
 *   - <-> means two-way connection (both next and previous)
 *   - Jane's previous is null (she's first)
 *   - Mike's next is null (he's last)
 * 
 * 
 * SINGLY vs DOUBLY LINKED LIST:
 * 
 * SINGLY:  [Jane] -> [John] -> [Mary] -> null
 *          - Can only go forward (one-way street)
 *          - Each node knows who's NEXT
 *          - Can't go backwards
 * 
 * DOUBLY:  null <- [Jane] <-> [John] <-> [Mary] -> null
 *          - Can go forward AND backward (two-way street)
 *          - Each node knows who's NEXT and PREVIOUS
 *          - More flexible but uses more memory
 * 
 * 
 * REAL-WORLD ANALOGY:
 * Think of a subway train where:
 * - You can walk forward to the next car
 * - You can also walk backward to the previous car
 * - Each car knows which cars are on both sides
 * 
 * ============================================================================
 */
public class EmployeeNode {
public class EmployeeNode {



    // The actual employee data this node holds
    private Employee employee;
    
    // Pointer to the NEXT node in the list (moving forward)
    private EmployeeNode next;
    
    // Pointer to the PREVIOUS node in the list (moving backward) ⭐ NEW!
    // This is what makes it "doubly" linked!
    private EmployeeNode previous;

    /**
     * CONSTRUCTOR - Creates a new doubly linked node
     * 
     * @param employee The employee data to store
     * 
     * NOTE: When created, both next and previous are null.
     * We'll set them when we connect this node to others.
     */
    public EmployeeNode(Employee employee){
        this.employee = employee;
    }

    // GETTER and SETTER methods for accessing node data and pointers
    
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * getNext() - Returns the next node in the chain
     * Use this to move FORWARD through the list
     */
    public EmployeeNode getNext() {
        return next;
    }

    /**
     * getPrevious() - Returns the previous node in the chain ⭐ NEW!
     * Use this to move BACKWARD through the list
     */
    public EmployeeNode getPrevious() { return previous; }

    /**
     * setPrevious() - Connects this node to a previous node ⭐ NEW!
     * 
     * @param previous The node that should come before this one
     * 
     * Example: node2.setPrevious(node1) creates: [node1] <-> [node2]
     */
    public void setPrevious(EmployeeNode previous) { this.previous = previous; }

    /**
     * setNext() - Connects this node to a next node
     * 
     * @param next The node that should come after this one
     */
    public void setNext(EmployeeNode next) {
        this.next = next;
    }

    /**
     * toString() - Returns the employee data as a string
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
 * Q1: What are the advantages of a Doubly Linked List over Singly Linked?
 * A1: 
 *     ADVANTAGES:
 *     ✓ Can traverse BACKWARDS - visit previous nodes easily
 *     ✓ Deleting a node is easier - we have access to the previous node
 *     ✓ Can add/remove from BOTH ends efficiently (with head and tail pointers)
 *     ✓ More flexible for certain algorithms (like LRU cache)
 *     
 *     DISADVANTAGES:
 *     ✗ Uses MORE memory - each node has 2 pointers instead of 1
 *     ✗ More complex code - must maintain both next AND previous pointers
 *     ✗ Insertion/deletion takes more steps - must update 2 pointers per connection
 * 
 * 
 * Q2: How much extra memory does a doubly linked list use?
 * A2: Each node has ONE extra pointer (the previous pointer).
 *     
 *     Singly linked node:
 *     [Employee data] + [next pointer] = about 12-16 bytes overhead
 *     
 *     Doubly linked node:
 *     [Employee data] + [next pointer] + [previous pointer] = about 16-24 bytes overhead
 *     
 *     For a list of 1000 employees: about 4-8 KB extra memory
 *     (Not much on modern computers, but matters in memory-constrained systems!)
 * 
 * 
 * Q3: How do you traverse a doubly linked list BACKWARDS?
 * A3: Start from the TAIL and use getPrevious():
 *     
 *     EmployeeNode current = tail;  // Start at the end
 *     while (current != null) {
 *         System.out.println(current);
 *         current = current.getPrevious();  // Move backwards
 *     }
 *     
 *     This prints the list in REVERSE order!
 * 
 * 
 * Q4: How do you insert a node BETWEEN two existing nodes?
 * A4: You must update FOUR pointers! (This is why it's more complex)
 *     
 *     Example: Insert Mike between John and Mary
 *     
 *     Before: [John] <-> [Mary]
 *     
 *     Step 1: Mike's next = Mary
 *             mike.setNext(mary);
 *     
 *     Step 2: Mike's previous = John
 *             mike.setPrevious(john);
 *     
 *     Step 3: John's next = Mike
 *             john.setNext(mike);
 *     
 *     Step 4: Mary's previous = Mike
 *             mary.setPrevious(mike);
 *     
 *     After: [John] <-> [Mike] <-> [Mary]
 *     
 *     IMPORTANT: Do steps 1 & 2 FIRST (or you'll lose references!)
 * 
 * 
 * Q5: How do you DELETE a node from a doubly linked list?
 * A5: Much easier than singly linked! You have access to both neighbors.
 *     
 *     Example: Delete Mike from [John] <-> [Mike] <-> [Mary]
 *     
 *     Step 1: Get Mike's previous and next nodes
 *             prev = mike.getPrevious();  // John
 *             next = mike.getNext();      // Mary
 *     
 *     Step 2: Connect John to Mary
 *             prev.setNext(next);         // John -> Mary
 *     
 *     Step 3: Connect Mary to John
 *             next.setPrevious(prev);     // Mary <- John
 *     
 *     Step 4: Clear Mike's pointers (cleanup)
 *             mike.setNext(null);
 *             mike.setPrevious(null);
 *     
 *     After: [John] <-> [Mary]
 *     
 *     In a SINGLY linked list, we'd need to traverse from the head to find
 *     the previous node, making deletion O(n) instead of O(1)!
 * 
 * 
 * Q6: When should you use a Doubly Linked List?
 * A6: Use it when:
 *     ✓ You need to traverse in BOTH directions
 *     ✓ You need to delete nodes efficiently (knowing only the node itself)
 *     ✓ You need to add/remove from both ends frequently
 *     ✓ Memory is not extremely limited
 *     
 *     Examples:
 *     - Browser back/forward buttons (navigate history both ways)
 *     - Music player playlist (next/previous song)
 *     - LRU Cache (Least Recently Used - need quick access to both ends)
 *     - Undo/Redo functionality
 * 
 * 
 * Q7: How do you find the middle element of a doubly linked list?
 * A7: Several approaches:
 *     
 *     Option 1: Two-pointer technique (same as singly linked)
 *     - Slow pointer moves 1 step, fast moves 2 steps
 *     - When fast reaches end, slow is at middle
 *     
 *     Option 2: Use tail pointer (unique to doubly linked!)
 *     - Start one pointer at head, one at tail
 *     - Move both toward center until they meet
 *     - This is MORE efficient! O(n/2) instead of O(n)
 *     
 *     EmployeeNode front = head;
 *     EmployeeNode back = tail;
 *     while (front != back && front.getNext() != back) {
 *         front = front.getNext();
 *         back = back.getPrevious();
 *     }
 *     // front (or back) is now at the middle!
 * 
 * 
 * Q8: Can you have a circular doubly linked list?
 * A8: Yes! The last node's next points to the first node,
 *     AND the first node's previous points to the last node.
 *     
 *     [A] <-> [B] <-> [C]
 *      ^               |
 *      |_______________|
 *     
 *     - A's previous = C
 *     - C's next = A
 *     
 *     Use case: Round-robin scheduling with ability to go backwards
 * 
 * ============================================================================
 */
