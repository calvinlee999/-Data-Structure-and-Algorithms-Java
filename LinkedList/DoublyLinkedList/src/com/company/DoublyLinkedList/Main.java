package com.company.DoublyLinkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * MAIN CLASS - Testing Our Doubly Linked List
 * ============================================================================
 * 
 * PURPOSE:
 * This class demonstrates the capabilities of a doubly linked list.
 * It shows how we can add to BOTH ends and remove from BOTH ends efficiently!
 * 
 * WHAT YOU'LL LEARN:
 * - How to create a doubly linked list
 * - How to add nodes to the front AND back
 * - How to remove nodes from the front AND back
 * - The difference between singly and doubly linked lists
 * 
 * KEY DIFFERENCES FROM SINGLY LINKED LIST:
 * 1. We have a TAIL pointer (can access the end quickly!)
 * 2. We can add to the END in O(1) time
 * 3. We can remove from the END in O(1) time
 * 4. Each node knows its previous neighbor
 * 
 * ============================================================================
 */
public class Main {

    public static void main(String[] args) {
        // ARRAYLIST EXAMPLE (for comparison)
        List<Employee> employeeList = new ArrayList<>();
//        employeeList.add(new Employee("Jane", "Jones", 123));
//        employeeList.add(new Employee("John", "Doe", 4567));
//        employeeList.add(new Employee("Mary", "Smith", 22));
//        employeeList.add(new Employee("Mike", "Wilson", 3245));

        // CREATE EMPLOYEE OBJECTS
        // These are the data we'll store in our doubly linked list
        Employee janeJohns = new Employee("Jane", "Jones", 123);
        Employee johnDoe = new Employee("John", "Does", 6546);
        Employee dickJiba = new Employee("Dick", "Jiba", 78);
        Employee fnckUdemy = new Employee("Fnck", "Udemy", 111);
        Employee gfdgddfg = new Employee("gfdg", "ddfg", 77888);


        // CREATE THE DOUBLY LINKED LIST
        // Starts empty: head = null, tail = null, size = 0
        EmployeeDoublyLinkedList list = new EmployeeDoublyLinkedList();
        // CREATE THE DOUBLY LINKED LIST
        // Starts empty: head = null, tail = null, size = 0
        EmployeeDoublyLinkedList list = new EmployeeDoublyLinkedList();
        
        // ====================================================================
        // ADDING TO THE FRONT (same as singly linked list)
        // ====================================================================
        
        // Add Jane to front
        // After: HEAD -> [Jane] <- TAIL (she's the only one, so also the tail!)
        list.addToFront(janeJohns);
        
        // Add John to front  
        // After: HEAD -> [John] <-> [Jane] <- TAIL
        list.addToFront(johnDoe);
        
        // Add Dick to front
        // After: HEAD -> [Dick] <-> [John] <-> [Jane] <- TAIL
        list.addToFront(dickJiba);
        
        // Add Fnck to front
        // After: HEAD -> [Fnck] <-> [Dick] <-> [John] <-> [Jane] <- TAIL
        list.addToFront(fnckUdemy);
        
        // PRINT THE LIST (from head to tail)
        // Shows: Fnck -> Dick -> John -> Jane
        list.printList();
        
        // CHECK SIZE (should be 4)
        System.out.println(list.getSize());


        // ====================================================================
        // ADDING TO THE END ⭐ NEW CAPABILITY!
        // This is O(1) thanks to the tail pointer!
        // ====================================================================
        
        // Add gfdg to the END (not the front!)
        // After: HEAD -> [Fnck] <-> [Dick] <-> [John] <-> [Jane] <-> [gfdg] <- TAIL
        list.addToEnd(gfdgddfg);
        
        // PRINT AGAIN - notice gfdg is at the END!
        list.printList();
        
        // SIZE SHOULD BE 5 NOW
        System.out.println(list.getSize());


        // ====================================================================
        // REMOVING FROM THE FRONT
        // ====================================================================
        
        // Remove Fnck (the first one)
        // After: HEAD -> [Dick] <-> [John] <-> [Jane] <-> [gfdg] <- TAIL
        list.removeFromFront();
        
        // SIZE SHOULD BE 4
        System.out.println(list.getSize());
        
        // PRINT TO SEE THE CHANGE
        list.printList();


        // ====================================================================
        // REMOVING FROM THE END ⭐ NEW CAPABILITY!
        // This is O(1) thanks to doubly linked structure!
        // ====================================================================
        
        // Remove gfdg (the last one)
        // After: HEAD -> [Dick] <-> [John] <-> [Jane] <- TAIL
        list.removeFromEnd();
        
        // SIZE SHOULD BE 3
        System.out.println(list.getSize());
        
        // PRINT FINAL STATE
        list.printList();
    }
}

/*
 * ============================================================================
 * EXPECTED OUTPUT:
 * ============================================================================
 * 
 * head------>Employee{firstName='Fnck', lastName='Udemy', id=111}<->
 * Employee{firstName='Dick', lastName='Jiba', id=78}<->
 * Employee{firstName='John', lastName='Does', id=6546}<->
 * Employee{firstName='Jane', lastName='Jones', id=123}<->
 * 4
 * head------>Employee{firstName='Fnck', lastName='Udemy', id=111}<->
 * Employee{firstName='Dick', lastName='Jiba', id=78}<->
 * Employee{firstName='John', lastName='Does', id=6546}<->
 * Employee{firstName='Jane', lastName='Jones', id=123}<->
 * Employee{firstName='gfdg', lastName='ddfg', id=77888}<->
 * 5
 * 4
 * head------>Employee{firstName='Dick', lastName='Jiba', id=78}<->
 * Employee{firstName='John', lastName='Does', id=6546}<->
 * Employee{firstName='Jane', lastName='Jones', id=123}<->
 * Employee{firstName='gfdg', lastName='ddfg', id=77888}<->
 * 3
 * head------>Employee{firstName='Dick', lastName='Jiba', id=78}<->
 * Employee{firstName='John', lastName='Does', id=6546}<->
 * Employee{firstName='Jane', lastName='Jones', id=123}<->
 * 
 * ============================================================================
 * KEY OBSERVATIONS:
 * ============================================================================
 * 
 * 1. ADDING TO FRONT REVERSES ORDER (like singly linked)
 *    We added: Jane, John, Dick, Fnck
 *    They appear: Fnck, Dick, John, Jane
 *    (Because each new one goes to the front!)
 * 
 * 
 * 2. ADDING TO END PRESERVES ORDER ⭐ NEW!
 *    When we added "gfdg" to the end, it appeared at the END!
 *    This is O(1) thanks to the tail pointer.
 *    In a singly linked list without tail, this would be O(n).
 * 
 * 
 * 3. REMOVING FROM END IS FAST ⭐ NEW!
 *    When we removed from the end, it was instant (O(1)).
 *    In a singly linked list, we'd have to traverse the entire list
 *    to find the second-to-last node - O(n).
 * 
 * 
 * 4. NOTICE THE <-> ARROWS
 *    The printList() shows <-> instead of -> to remind us
 *    that this is a DOUBLY linked list with two-way connections!
 * 
 * ============================================================================
 * INTERVIEW QUESTIONS & ANSWERS
 * ============================================================================
 * 
 * Q1: What's the main advantage shown in this demo?
 * A1: We can efficiently add and remove from BOTH ends!
 *     
 *     DOUBLY LINKED LIST:
 *     - addToFront(): O(1) ✓
 *     - addToEnd(): O(1) ✓
 *     - removeFromFront(): O(1) ✓
 *     - removeFromEnd(): O(1) ✓
 *     
 *     SINGLY LINKED LIST (without tail):
 *     - addToFront(): O(1) ✓
 *     - addToEnd(): O(n) ✗ (must traverse entire list)
 *     - removeFromFront(): O(1) ✓
 *     - removeFromEnd(): O(n) ✗ (must find second-to-last node)
 * 
 * 
 * Q2: What if we wanted to build the list in the CORRECT order?
 * A2: Use addToEnd() instead of addToFront()!
 *     
 *     // This builds the list in the order we add:
 *     list.addToEnd(jane);    // [Jane]
 *     list.addToEnd(john);    // [Jane] <-> [John]
 *     list.addToEnd(dick);    // [Jane] <-> [John] <-> [Dick]
 *     
 *     Result: HEAD -> [Jane] <-> [John] <-> [Dick] <- TAIL
 *     
 *     With a singly linked list (no tail), this would be slow because
 *     each addToEnd() would require traversing the entire list!
 * 
 * 
 * Q3: How would you print the list BACKWARDS?
 * A3: Start from the tail and use getPrevious()!
 *     
 *     void printListBackwards() {
 *         EmployeeNode current = tail;  // Start at the end
 *         System.out.print("tail------>");
 *         while (current != null) {
 *             System.out.print(current);
 *             System.out.println("<->");
 *             current = current.getPrevious();  // Move backwards!
 *         }
 *     }
 *     
 *     This would print: Jane -> John -> Dick -> Fnck
 *     (Opposite of the normal forward print!)
 *     
 *     YOU CAN'T DO THIS with a singly linked list!
 * 
 * 
 * Q4: When is a doubly linked list better than an ArrayList?
 * A4: 
 *     USE DOUBLY LINKED LIST when:
 *     ✓ Frequent insertions/deletions at BOTH ends
 *     ✓ Need to traverse backwards
 *     ✓ Unknown or highly variable size
 *     ✓ Don't need random access by index
 *     
 *     Example: Browser history (back/forward buttons)
 *     
 *     USE ARRAYLIST when:
 *     ✓ Need fast random access: list.get(100)
 *     ✓ Mostly reading data, not inserting/deleting
 *     ✓ Size is relatively stable
 *     ✓ Want to use less memory (no pointer overhead)
 *     
 *     Example: Storing a list of products to display
 * 
 * 
 * Q5: What happens to the "middle" when we remove from both ends?
 * A5: The middle nodes are unaffected! Only the head and tail change.
 *     
 *     Original: HEAD -> [Fnck] <-> [Dick] <-> [John] <-> [Jane] <-> [gfdg] <- TAIL
 *     
 *     After removeFromFront():
 *               HEAD -> [Dick] <-> [John] <-> [Jane] <-> [gfdg] <- TAIL
 *               (Dick's previous is set to null, making him the new head)
 *     
 *     After removeFromEnd():
 *               HEAD -> [Dick] <-> [John] <-> [Jane] <- TAIL
 *               (Jane's next is set to null, making her the new tail)
 *     
 *     The connections between Dick, John, and Jane never changed!
 *     Only the boundary pointers (head/tail) were updated.
 * 
 * 
 * Q6: Could we implement a Deque (Double-Ended Queue) with this?
 * A6: YES! A doubly linked list is PERFECT for implementing a Deque!
 *     
 *     DEQUE operations:
 *     - addFirst() -> addToFront() - O(1)
 *     - addLast() -> addToEnd() - O(1)
 *     - removeFirst() -> removeFromFront() - O(1)
 *     - removeLast() -> removeFromEnd() - O(1)
 *     - peekFirst() -> return head.getEmployee() - O(1)
 *     - peekLast() -> return tail.getEmployee() - O(1)
 *     
 *     All operations are O(1)! This is exactly what Java's LinkedList
 *     class does under the hood - it's a doubly linked list!
 * 
 * 
 * Q7: What's the difference between this and Java's built-in LinkedList?
 * A7: Java's LinkedList is also a doubly linked list with similar features!
 *     
 *     OUR IMPLEMENTATION:
 *     - Simple, educational
 *     - Only stores Employee objects
 *     - Basic operations (add/remove from ends)
 *     
 *     JAVA'S LinkedList<E>:
 *     - Generic (works with any type)
 *     - Implements List and Deque interfaces
 *     - Many more methods: add by index, contains, indexOf, etc.
 *     - More robust error handling
 *     - Implements Iterable (can use for-each loops)
 *     
 *     Our version helps you UNDERSTAND how it works!
 *     Java's version is what you'd use in production code.
 * 
 * ============================================================================
 */
