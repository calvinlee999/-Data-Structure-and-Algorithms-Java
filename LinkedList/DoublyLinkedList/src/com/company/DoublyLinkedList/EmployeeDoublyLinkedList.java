package com.company.DoublyLinkedList;

/**
 * ============================================================================
 * EMPLOYEE DOUBLY LINKED LIST - Complete Implementation
 * ============================================================================
 * 
 * WHAT IS A DOUBLY LINKED LIST?
 * A data structure where each node has TWO pointers:
 * - One pointing to the NEXT node (forward direction)
 * - One pointing to the PREVIOUS node (backward direction)
 * 
 * REAL-WORLD ANALOGY #1 - TWO-WAY STREET:
 * Imagine a street where you can drive both directions:
 * - Each house (node) knows its neighbor on both sides
 * - You can drive forward OR backward
 * - The first house has no house behind it (previous = null)
 * - The last house has no house ahead (next = null)
 * 
 * 
 * REAL-WORLD ANALOGY #2 - MUSIC PLAYLIST:
 * Think of a music player playlist:
 * - You can go to the NEXT song (forward)
 * - You can go to the PREVIOUS song (backward)
 * - Each song knows what comes before and after it
 * 
 * 
 * VISUAL DIAGRAM OF OUR DOUBLY LINKED LIST:
 * 
 *    HEAD                                        TAIL
 *     |                                           |
 *     v                                           v
 *   null <- [Jane] <-> [John] <-> [Mary] <-> [Mike] -> null
 *   
 *   - HEAD points to the first node (Jane)
 *   - TAIL points to the last node (Mike)
 *   - <-> shows two-way connections (next AND previous pointers)
 *   - null on both ends marks the boundaries
 * 
 * 
 * COMPARISON WITH SINGLY LINKED LIST:
 * 
 * SINGLY:
 *    HEAD
 *     |
 *     v
 *   [Jane] -> [John] -> [Mary] -> null
 *   
 *   Pros: Uses less memory (only 1 pointer per node)
 *   Cons: Can't go backwards, removing from end is O(n)
 * 
 * DOUBLY:
 *    HEAD                            TAIL
 *     |                               |
 *     v                               v
 *   null <- [Jane] <-> [John] <-> [Mary] -> null
 *   
 *   Pros: Can go both directions, remove from end is O(1)
 *   Cons: Uses more memory (2 pointers per node), more complex
 * 
 * ============================================================================
 */
public class EmployeeDoublyLinkedList {


    // HEAD - points to the first node in the list
    private EmployeeNode head;
    
    // TAIL - points to the last node in the list ⭐ NEW!
    // This allows O(1) operations at the end of the list!
    private EmployeeNode tail;
    
    // SIZE - tracks how many nodes we have
    private int size;

    // HEAD - points to the first node in the list
    private EmployeeNode head;
    
    // TAIL - points to the last node in the list ⭐ NEW!
    // This allows O(1) operations at the end of the list!
    private EmployeeNode tail;
    
    // SIZE - tracks how many nodes we have
    private int size;

    /**
     * ADD TO FRONT - Inserts a new employee at the beginning
     * 
     * TIME COMPLEXITY: O(1) - Always fast!
     * SPACE COMPLEXITY: O(1) - One new node
     * 
     * @param employee The employee to add
     * 
     * STEP-BY-STEP PROCESS:
     * 
     * Example: Add Jane to front of [John] <-> [Mary]
     * 
     * Step 1: Create new node for Jane
     *         [Jane] (next = null, prev = null)
     * 
     * Step 2: Jane's next points to current head (John)
     *         [Jane] -> [John] <-> [Mary]
     * 
     * Step 3: John's previous points to Jane
     *         [Jane] <-> [John] <-> [Mary]
     * 
     * Step 4: Move HEAD to Jane
     *         HEAD -> [Jane] <-> [John] <-> [Mary]
     * 
     * SPECIAL CASE - EMPTY LIST:
     * If the list is empty (head == null):
     * - Both HEAD and TAIL point to the new node
     * - The new node's next and previous are null
     */
    public void addToFront(Employee employee){
        EmployeeNode node = new EmployeeNode(employee);  // Create new node
        node.setNext(head);        // New node points to current head

        if(head==null){            // SPECIAL CASE: Empty list
            tail=node;             // New node is also the tail
        }
        else{                      // NORMAL CASE: List has nodes
            head.setPrevious(node);  // Old head's previous = new node
        }
        head = node;               // New node becomes the head
        size++;
    }

    /**
     * ADD TO END - Inserts a new employee at the end ⭐ NEW CAPABILITY!
     * 
     * TIME COMPLEXITY: O(1) - Fast! Thanks to the tail pointer!
     * SPACE COMPLEXITY: O(1) - One new node
     * 
     * @param employee The employee to add
     * 
     * STEP-BY-STEP PROCESS:
     * 
     * Example: Add Mike to end of [Jane] <-> [John]
     * 
     * Step 1: Create new node for Mike
     *         [Mike] (next = null, prev = null)
     * 
     * Step 2: Old tail (John) points to Mike
     *         [Jane] <-> [John] -> [Mike]
     * 
     * Step 3: Mike's previous points to John
     *         [Jane] <-> [John] <-> [Mike]
     * 
     * Step 4: Move TAIL to Mike
     *         [Jane] <-> [John] <-> [Mike] <- TAIL
     * 
     * WHY THIS IS GREAT:
     * In a singly linked list WITHOUT a tail pointer, we'd have to
     * traverse the ENTIRE list to find the end - O(n) time!
     * With a tail pointer, we can add to the end in O(1) time!
     * 
     * SPECIAL CASE - EMPTY LIST:
     * If the list is empty (head == null):
     * - Both HEAD and TAIL point to the new node
     */
    public void addToEnd(Employee employee){
        EmployeeNode node = new EmployeeNode(employee);  // Create new node

        if(head==null){            // SPECIAL CASE: Empty list
            head=node;             // New node is also the head
        }
        else{                      // NORMAL CASE: List has nodes
            tail.setNext(node);      // Old tail's next = new node
            node.setPrevious(tail);  // New node's prev = old tail
        }

        tail=node;                 // New node becomes the tail
        size++;

    }


    /**
     * REMOVE FROM FRONT - Removes and returns the first employee
     * 
     * TIME COMPLEXITY: O(1) - Fast!
     * SPACE COMPLEXITY: O(1)
     * 
     * @return The removed node, or null if empty
     * 
     * STEP-BY-STEP PROCESS:
     * 
     * Example: Remove Jane from [Jane] <-> [John] <-> [Mary]
     * 
     * Step 1: Save Jane (the node we're removing)
     *         removedNode = HEAD
     * 
     * Step 2: Check if Jane is the ONLY node
     *         If yes: set tail = null
     *         If no: John's previous = null
     * 
     * Step 3: Move HEAD to John
     *         HEAD -> [John] <-> [Mary]
     * 
     * Step 4: Break Jane's link to John
     *         [Jane] (next = null, prev = null)
     * 
     * Step 5: Return Jane
     */
    public EmployeeNode removeFromFront(){
        if (isEmpty()){
            return null;  // Can't remove from empty list
        }

        EmployeeNode removedNode = head;  // Save node we're removing

        if(head.getNext()==null){         // SPECIAL CASE: Only one node
            tail=null;                    // List will be empty after removal
        }
        else{                             // NORMAL CASE: Multiple nodes
            head.getNext().setPrevious(null);  // New head's prev = null
        }
        head = head.getNext();            // Move head to next node
        size--;
        removedNode.setNext(null);        // Clean up removed node
        return removedNode;
    }

        head = head.getNext();            // Move head to next node
        size--;
        removedNode.setNext(null);        // Clean up removed node
        return removedNode;
    }

    /**
     * REMOVE FROM END - Removes and returns the last employee ⭐ NEW!
     * 
     * TIME COMPLEXITY: O(1) - Fast! Thanks to tail pointer!
     * SPACE COMPLEXITY: O(1)
     * 
     * @return The removed node, or null if empty
     * 
     * STEP-BY-STEP PROCESS:
     * 
     * Example: Remove Mary from [Jane] <-> [John] <-> [Mary]
     * 
     * Step 1: Save Mary (the node we're removing)
     *         removedNode = TAIL
     * 
     * Step 2: Check if Mary is the ONLY node
     *         If yes: set head = null
     *         If no: John's next = null
     * 
     * Step 3: Move TAIL to John
     *         TAIL -> [John] <-> [Jane]
     * 
     * Step 4: Break Mary's link
     *         [Mary] (next = null, prev = null)
     * 
     * Step 5: Return Mary
     * 
     * WHY THIS IS AMAZING:
     * In a SINGLY linked list, removing from the end is O(n) because
     * we have to traverse the entire list to find the second-to-last node!
     * With doubly linked + tail pointer, it's O(1)! We just use getPrevious().
     */
    public EmployeeNode removeFromEnd(){
        if (isEmpty()){
            return null;  // Can't remove from empty list
        }

        EmployeeNode removedNode = tail;  // Save node we're removing

        if(tail.getPrevious()==null){     // SPECIAL CASE: Only one node
            head=null;                    // List will be empty
        }
        else{                             // NORMAL CASE: Multiple nodes
            tail.getPrevious().setNext(null);  // New tail's next = null
        }
        tail = tail.getPrevious();        // Move tail to previous node
        size--;
        removedNode.setNext(null);        // Clean up (even though it's redundant)
        return removedNode;

    }

    /**
     * GET SIZE - Returns the number of nodes
     * 
     * TIME COMPLEXITY: O(1)
     * 
     * @return The number of employees in the list
     */
    public int getSize(){
        return size;
    }

    /**
     * IS EMPTY - Checks if the list has any nodes
     * 
     * TIME COMPLEXITY: O(1)
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty(){
        return head==null;
    }

    /**
     * PRINT LIST - Displays all employees from head to tail
     * 
     * TIME COMPLEXITY: O(n) - Must visit every node
     * SPACE COMPLEXITY: O(1)
     * 
     * NOTE: We COULD also print backwards from tail using getPrevious()!
     * That's a unique capability of doubly linked lists.
     */
    public void printList(){
        EmployeeNode current = head;
        System.out.print("head------>");
        while(current!=null){
            System.out.print(current);
            System.out.println("<->");      // <-> shows it's doubly linked!
            current = current.getNext();
        }
    }
}

/*
 * ============================================================================
 * TIME & SPACE COMPLEXITY SUMMARY
 * ============================================================================
 * 
 * OPERATION          | TIME  | SINGLY  | WHY DOUBLY IS BETTER
 * -------------------+-------+---------+--------------------------------
 * addToFront()       | O(1)  | O(1)    | Same performance
 * addToEnd()         | O(1)  | O(n)*   | Tail pointer makes it O(1)!
 * removeFromFront()  | O(1)  | O(1)    | Same performance
 * removeFromEnd()    | O(1)  | O(n)    | getPrevious() makes it O(1)!
 * getSize()          | O(1)  | O(1)    | Same (we track size)
 * isEmpty()          | O(1)  | O(1)    | Same
 * printList()        | O(n)  | O(n)    | Same (must visit all nodes)
 * 
 * * Singly linked list with only head pointer
 * 
 * SPACE USAGE:
 * - Each node: Employee + next + previous (2 pointers vs 1 in singly)
 * - Approximately 50% more memory per node than singly linked
 * - List overhead: head + tail + size (vs just head + size in singly)
 * 
 * ============================================================================
 * INTERVIEW QUESTIONS & ANSWERS
 * ============================================================================
 * 
 * Q1: Why is addToEnd() O(1) in a doubly linked list but O(n) in singly?
 * A1: 
 *     DOUBLY LINKED (with tail):
 *     - We have a TAIL pointer that always points to the last node
 *     - We can directly access the end: just use tail!
 *     - Adding involves: tail.setNext(newNode), etc. - O(1)
 *     
 *     SINGLY LINKED (without tail):
 *     - We only have a HEAD pointer
 *     - To reach the end, we must traverse: head -> next -> next -> ... -> last
 *     - This traversal takes O(n) time
 *     
 *     SINGLY LINKED (with tail):
 *     - Actually CAN be O(1) if we add a tail pointer!
 *     - But we still can't do removeFromEnd() in O(1) (no previous pointer)
 * 
 * 
 * Q2: How would you reverse a doubly linked list?
 * A2: Swap the next and previous pointers for each node!
 *     
 *     Method 1: Swap pointers in place
 *     EmployeeNode current = head;
 *     EmployeeNode temp = null;
 *     
 *     while (current != null) {
 *         // Swap next and previous
 *         temp = current.getPrevious();
 *         current.setPrevious(current.getNext());
 *         current.setNext(temp);
 *         
 *         // Move to next node (which is actually the previous!)
 *         current = current.getPrevious();
 *     }
 *     
 *     // Swap head and tail
 *     temp = head;
 *     head = tail;
 *     tail = temp;
 *     
 *     Time: O(n), Space: O(1)
 * 
 * 
 * Q3: How would you find if there's a palindrome in a doubly linked list?
 * A3: Use two pointers - one from head, one from tail!
 *     
 *     EmployeeNode front = head;
 *     EmployeeNode back = tail;
 *     
 *     while (front != back && front.getPrevious() != back) {
 *         if (!front.getEmployee().equals(back.getEmployee())) {
 *             return false;  // Not a palindrome
 *         }
 *         front = front.getNext();
 *         back = back.getPrevious();
 *     }
 *     return true;  // Is a palindrome
 *     
 *     This is MUCH more efficient than with a singly linked list!
 *     Time: O(n/2) = O(n), but twice as fast as singly linked approach
 * 
 * 
 * Q4: What are the trade-offs between doubly and singly linked lists?
 * A4: 
 *     USE DOUBLY LINKED when:
 *     ✓ You need to traverse backwards
 *     ✓ You need O(1) removal from the end
 *     ✓ You frequently delete nodes (easier with access to previous)
 *     ✓ Memory is not extremely limited
 *     
 *     USE SINGLY LINKED when:
 *     ✓ Memory is tight (50% less pointer overhead)
 *     ✓ You only need forward traversal
 *     ✓ You mainly add/remove from the front
 *     ✓ Simpler code is preferred (fewer pointers to maintain)
 * 
 * 
 * Q5: How do you insert a node BEFORE a given node?
 * A5: This is MUCH easier in a doubly linked list!
 *     
 *     Example: Insert Mike before John
 *     
 *     void insertBefore(EmployeeNode target, Employee employee) {
 *         EmployeeNode newNode = new EmployeeNode(employee);
 *         EmployeeNode prev = target.getPrevious();  // Get John's previous
 *         
 *         // Connect newNode
 *         newNode.setNext(target);           // Mike -> John
 *         newNode.setPrevious(prev);         // prev <- Mike
 *         
 *         // Update John
 *         target.setPrevious(newNode);       // Mike <- John
 *         
 *         // Update previous node (or head if target was first)
 *         if (prev != null) {
 *             prev.setNext(newNode);         // prev -> Mike
 *         } else {
 *             head = newNode;                // Mike is new head
 *         }
 *         
 *         size++;
 *     }
 *     
 *     In a SINGLY linked list, we'd have to traverse from head to find
 *     the node BEFORE target - O(n) instead of O(1)!
 * 
 * 
 * Q6: How would you implement an LRU (Least Recently Used) Cache?
 * A6: Doubly linked lists are PERFECT for LRU caches!
 *     
 *     CONCEPT:
 *     - Most recently used items go to the FRONT
 *     - Least recently used items drift to the BACK
 *     - When cache is full, remove from the BACK
 *     
 *     OPERATIONS:
 *     1. Access item: Move it to the front (recently used)
 *     2. Add new item: Add to front
 *     3. Cache full: Remove from back (least recently used)
 *     
 *     WHY DOUBLY LINKED?
 *     - O(1) add to front (mark as recently used)
 *     - O(1) remove from back (evict least recently used)
 *     - O(1) remove from middle (when moving an item to front)
 *     - Usually combined with a HashMap for O(1) lookup!
 *     
 *     STRUCTURE:
 *     HashMap: {key -> node} for fast lookup
 *     Doubly Linked List: maintains order of usage
 *     
 *     Most Recent -> [Item3] <-> [Item1] <-> [Item2] -> Least Recent
 * 
 * 
 * Q7: How do you handle edge cases in a doubly linked list?
 * A7: Always check these special cases:
 *     
 *     1. EMPTY LIST (head == null):
 *        - Both head and tail should be null
 *        - Operations should return null or false
 *     
 *     2. SINGLE NODE (head == tail):
 *        - node.previous == null
 *        - node.next == null
 *        - Removing it should set both head and tail to null
 *     
 *     3. FIRST NODE:
 *        - node.previous == null
 *        - Removing it should update head
 *        - Inserting before it should update head
 *     
 *     4. LAST NODE:
 *        - node.next == null
 *        - Removing it should update tail
 *        - Inserting after it should update tail
 *     
 *     Always test your code with these edge cases!
 * 
 * 
 * Q8: Can you sort a doubly linked list efficiently?
 * A8: Yes! Merge sort works well:
 *     
 *     TIME: O(n log n)
 *     SPACE: O(log n) for recursion stack
 *     
 *     ADVANTAGE over singly linked:
 *     - Can find the middle in O(n/2) using both head and tail
 *     - Can merge from both ends simultaneously
 *     
 *     STEPS:
 *     1. Find middle (use head and tail pointers)
 *     2. Split into two halves
 *     3. Recursively sort each half
 *     4. Merge the sorted halves
 *     
 *     The merge step is easier because we can traverse both directions!
 * 
 * ============================================================================
 */
