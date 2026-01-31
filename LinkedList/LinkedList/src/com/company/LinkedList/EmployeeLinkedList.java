package com.company.LinkedList;

/**
 * ============================================================================
 * EMPLOYEE LINKED LIST - A Complete Singly Linked List Implementation
 * ============================================================================
 * 
 * WHAT IS A LINKED LIST?
 * A linked list is a data structure where elements are stored in nodes, and
 * each node points to the next node, forming a chain.
 * 
 * REAL-WORLD ANALOGY #1 - TRAIN CARS:
 * Think of a train where each car is connected to the next car:
 * - Each train car = a Node
 * - The cargo in each car = the Employee data
 * - The coupling between cars = the "next" pointer
 * - The engine at the front = the HEAD pointer
 * 
 * 
 * REAL-WORLD ANALOGY #2 - TREASURE HUNT:
 * Each clue tells you where to find the next clue:
 * - Clue 1: "Go to the old oak tree" (this is the HEAD)
 * - Clue 2 (at oak tree): "Go to the red barn"
 * - Clue 3 (at barn): "Go to the pond"
 * - Clue 4 (at pond): "You found the treasure!" (this points to null - the end)
 * 
 * 
 * VISUAL DIAGRAM OF OUR LIST:
 * 
 *    HEAD
 *     |
 *     v
 *   +-------+    +-------+    +-------+    +-------+
 *   | Jane  | -> | John  | -> | Mary  | -> | Mike  | -> null
 *   +-------+    +-------+    +-------+    +-------+
 *   
 *   - HEAD always points to the first node
 *   - Each node points to the next
 *   - The last node points to null (end marker)
 * 
 * 
 * VS. ARRAY COMPARISON:
 * 
 * ARRAY: [Jane][John][Mary][Mike]
 *        - All elements stored together in memory
 *        - Fixed size (hard to grow)
 *        - Fast access: array[2] gets Mary instantly
 *        - Slow insertion in middle (must shift elements)
 * 
 * LINKED LIST: [Jane]-->[John]-->[Mary]-->[Mike]-->null
 *        - Elements scattered in memory, connected by pointers
 *        - Dynamic size (grows easily)
 *        - Slow access: must walk through list to find element
 *        - Fast insertion at front (just change pointers)
 * 
 * ============================================================================
 */
public class EmployeeLinkedList {


    // HEAD is our entry point - it points to the first node
    // If head is null, the list is empty
    private EmployeeNode head;
    
    // SIZE keeps track of how many nodes we have
    // This is optional but makes getSize() faster!
    private int size;

    // HEAD is our entry point - it points to the first node
    // If head is null, the list is empty
    private EmployeeNode head;
    
    // SIZE keeps track of how many nodes we have
    // This is optional but makes getSize() faster!
    private int size;

    /**
     * ADD TO FRONT - Inserts a new employee at the beginning of the list
     * 
     * TIME COMPLEXITY: O(1) - Always fast! Just 3 steps no matter how big the list is.
     * SPACE COMPLEXITY: O(1) - Only creates one new node.
     * 
     * @param employee The employee to add
     * 
     * STEP-BY-STEP PROCESS:
     * 
     * Before: HEAD -> [John] -> [Mary] -> null
     * We want to add Jane to the front
     * 
     * Step 1: Create a new node for Jane
     *         [Jane]
     * 
     * Step 2: Make Jane's "next" point to current head (John)
     *         [Jane] -> [John] -> [Mary] -> null
     *         
     * Step 3: Move HEAD to point to Jane
     *         HEAD -> [Jane] -> [John] -> [Mary] -> null
     * 
     * Done! Jane is now first.
     * 
     * WHY IS THIS FAST?
     * We don't have to move or shift any existing nodes!
     * We just create a new node and update two pointers.
     */
    public void addToFront(Employee employee){
        EmployeeNode node = new EmployeeNode(employee);  // Create new node
        node.setNext(head);        // New node points to current head
        head = node;               // Head now points to new node
        size++;                    // Increase our count
    }


    /**
     * REMOVE FROM FRONT - Removes and returns the first employee
     * 
     * TIME COMPLEXITY: O(1) - Always fast! Just move the head pointer.
     * SPACE COMPLEXITY: O(1) - No new memory needed.
     * 
     * @return The removed node, or null if list is empty
     * 
     * STEP-BY-STEP PROCESS:
     * 
     * Before: HEAD -> [Jane] -> [John] -> [Mary] -> null
     * We want to remove Jane
     * 
     * Step 1: Save reference to Jane (the node we're removing)
     *         removedNode = HEAD
     * 
     * Step 2: Move HEAD to point to John (the next node)
     *         HEAD -> [John] -> [Mary] -> null
     *         removedNode = [Jane] (still pointing to John)
     * 
     * Step 3: Break Jane's link to John (cleanup)
     *         removedNode = [Jane] -> null
     * 
     * Step 4: Return Jane
     * 
     * WHY IS THIS FAST?
     * We just move the head pointer - no shifting needed!
     */
    public EmployeeNode removeFromFront(){
        if (isEmpty()){
            return null;  // Can't remove from empty list
        }

        EmployeeNode removedNode = head;      // Save the node we're removing
        head = head.getNext();                // Move head to next node
        size--;                               // Decrease our count
        removedNode.setNext(null);            // Break the removed node's link
        return removedNode;                   // Return removed node

    }
    
    /**
     * GET SIZE - Returns the number of nodes in the list
     * 
     * TIME COMPLEXITY: O(1) - We keep track with a variable!
     * 
     * @return The number of employees in the list
     * 
     * NOTE: If we didn't keep a size variable, we'd have to traverse
     * the entire list and count, which would be O(n).
     */
    public int getSize(){
        return size;
    }

    /**
     * IS EMPTY - Checks if the list has any nodes
     * 
     * TIME COMPLEXITY: O(1) - Just check if head is null
     * 
     * @return true if list is empty, false otherwise
     * 
     * LOGIC: If head is null, there are no nodes, so the list is empty.
     */
    public boolean isEmpty(){
        return head==null;
    }

    /**
     * PRINT LIST - Displays all employees in the list
     * 
     * TIME COMPLEXITY: O(n) - Must visit every node
     * SPACE COMPLEXITY: O(1) - Just uses one "current" pointer
     * 
     * HOW IT WORKS:
     * We start at the head and "walk" through the list, printing each node
     * until we reach null (the end).
     * 
     * TRAVERSAL PROCESS:
     * 
     * Step 1: current = head
     *         HEAD -> [Jane] -> [John] -> [Mary] -> null
     *         ^current
     * 
     * Step 2: Print Jane, move to next
     *         HEAD -> [Jane] -> [John] -> [Mary] -> null
     *                           ^current
     * 
     * Step 3: Print John, move to next
     *         HEAD -> [Jane] -> [John] -> [Mary] -> null
     *                                     ^current
     * 
     * Step 4: Print Mary, move to next
     *         HEAD -> [Jane] -> [John] -> [Mary] -> null
     *                                               ^current (null - stop!)
     */
    public void printList(){
        EmployeeNode current = head;          // Start at the head
        System.out.print("head------>");
        while(current!=null){                 // Keep going until we hit null
            System.out.print(current);        // Print current node
            System.out.println("->");
            current = current.getNext();      // Move to next node
        }
    }
}

/*
 * ============================================================================
 * TIME & SPACE COMPLEXITY SUMMARY
 * ============================================================================
 * 
 * OPERATION          | TIME      | EXPLANATION
 * -------------------+-----------+------------------------------------------
 * addToFront()       | O(1)      | Just update pointers - always 3 steps
 * removeFromFront()  | O(1)      | Just move head pointer - always fast
 * getSize()          | O(1)      | Return a variable we're tracking
 * isEmpty()          | O(1)      | Just check if head is null
 * printList()        | O(n)      | Must visit all n nodes
 * 
 * SPACE USAGE:
 * - Each node takes up space for: Employee object + next pointer
 * - Total space: O(n) where n is the number of nodes
 * - Extra overhead compared to array: the "next" pointers
 * 
 * ============================================================================
 * INTERVIEW QUESTIONS & ANSWERS
 * ============================================================================
 * 
 * Q1: Why don't we have addToEnd() in this implementation?
 * A1: We COULD add it, but it would be O(n) because we'd have to traverse
 *     the entire list to find the last node! To make addToEnd() O(1), we'd
 *     need to keep a TAIL pointer that always points to the last node.
 * 
 * 
 * Q2: How would you add a node in the MIDDLE of the list?
 * A2: Steps:
 *     1. Traverse to the node BEFORE where you want to insert
 *     2. Create new node
 *     3. New node's next = previous node's next
 *     4. Previous node's next = new node
 *     
 *     Example: Insert Mike between John and Mary
 *     Before: [John] -> [Mary]
 *     
 *     newNode.setNext(john.getNext());  // Mike points to Mary
 *     john.setNext(newNode);            // John points to Mike
 *     
 *     After: [John] -> [Mike] -> [Mary]
 * 
 * 
 * Q3: What happens if you try to remove from an empty list?
 * A3: Our isEmpty() check prevents errors! We return null instead of crashing.
 *     Without this check, we'd get a NullPointerException when trying to
 *     call head.getNext() on a null head.
 * 
 * 
 * Q4: How would you search for a specific employee by ID?
 * A4: Traverse the list and check each node:
 *     
 *     public Employee findById(int id) {
 *         EmployeeNode current = head;
 *         while (current != null) {
 *             if (current.getEmployee().getId() == id) {
 *                 return current.getEmployee();  // Found it!
 *             }
 *             current = current.getNext();
 *         }
 *         return null;  // Not found
 *     }
 *     
 *     Time Complexity: O(n) - might have to check every node
 * 
 * 
 * Q5: What's the advantage of keeping a size variable?
 * A5: Without it, to get the size we'd have to count nodes like this:
 *     
 *     int count = 0;
 *     EmployeeNode current = head;
 *     while (current != null) {
 *         count++;
 *         current = current.getNext();
 *     }
 *     return count;  // O(n) operation!
 *     
 *     With the size variable, it's just O(1)! We trade a little extra space
 *     (one integer) for much faster size lookups.
 * 
 * 
 * Q6: Can you have a circular linked list?
 * A6: Yes! Instead of the last node pointing to null, it points back to the head.
 *     
 *     Regular: [A] -> [B] -> [C] -> null
 *     Circular: [A] -> [B] -> [C] -> (back to A)
 *                ^                    |
 *                |____________________|
 *     
 *     Use case: Round-robin scheduling (taking turns)
 *     WARNING: Be careful with traversal - you need a stop condition or you'll loop forever!
 * 
 * 
 * Q7: How do you sort a linked list?
 * A7: Several approaches:
 *     
 *     Option 1: Merge Sort (preferred for linked lists)
 *     - Time: O(n log n)
 *     - Space: O(log n) for recursion
 *     - Works well because merge sort uses sequential access
 *     
 *     Option 2: Convert to array, sort, rebuild list
 *     - Time: O(n log n) for sorting
 *     - Space: O(n) for the array
 *     - Easier to code but uses extra space
 *     
 *     Option 3: Insertion Sort
 *     - Time: O(n²) - slow for large lists
 *     - Space: O(1)
 *     - Good for small or nearly-sorted lists
 * 
 * 
 * Q8: What are common mistakes when working with linked lists?
 * A8: 
 *     1. LOSING REFERENCES: Always save a reference before changing pointers!
 *        Bad:  node.setNext(newNode)  // Lost access to old next!
 *        Good: temp = node.getNext(); node.setNext(newNode);
 *     
 *     2. NULL POINTER EXCEPTIONS: Always check if a node is null before using it!
 *        if (current != null) { current.getNext(); }
 *     
 *     3. FORGETTING TO UPDATE SIZE: Leads to wrong size count
 *     
 *     4. NOT HANDLING EDGE CASES: Empty list, single node, etc.
 *     
 *     5. INFINITE LOOPS: In circular lists or when traversal logic is wrong
 * 
 * ============================================================================
 */
