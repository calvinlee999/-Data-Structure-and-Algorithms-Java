package com.company.LinkedList;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * MAIN CLASS - Testing Our Singly Linked List
 * ============================================================================
 * 
 * PURPOSE:
 * This class demonstrates how to use our EmployeeLinkedList.
 * It shows adding employees, removing them, and printing the list.
 * 
 * WHAT YOU'LL LEARN:
 * - How to create a linked list
 * - How to add nodes to the front
 * - How to remove nodes from the front
 * - How to check the size
 * - How linked lists behave differently than ArrayLists
 * 
 * ============================================================================
 */
public class Main {

    public static void main(String[] args) {

        // ARRAYLIST EXAMPLE (commented out) - for comparison
        // ArrayList stores elements in a resizable array
        List<Employee> employeeList = new ArrayList<>();
//        employeeList.add(new Employee("Jane", "Jones", 123));
//        employeeList.add(new Employee("John", "Doe", 4567));
//        employeeList.add(new Employee("Mary", "Smith", 22));
//        employeeList.add(new Employee("Mike", "Wilson", 3245));

        // CREATE EMPLOYEE OBJECTS
        // These are the data we'll store in our linked list nodes
        Employee janeJohns = new Employee("Jane", "Jones", 123);
        Employee johnDoe = new Employee("John", "Does", 6546);
        Employee dickJiba = new Employee("Dick", "Jiba", 78);
        Employee fnckUdemy = new Employee("Fnck", "Udemy", 111);

        // CREATE THE LINKED LIST
        // This starts with an empty list (head = null, size = 0)
        EmployeeLinkedList list = new EmployeeLinkedList();
        // CREATE THE LINKED LIST
        // This starts with an empty list (head = null, size = 0)
        EmployeeLinkedList list = new EmployeeLinkedList();
        
        // ADD EMPLOYEES TO THE FRONT OF THE LIST
        // Remember: Each add goes to the FRONT, so the last one added becomes first!
        
        // After this: HEAD -> [Jane] -> null
        list.addToFront(janeJohns);
        
        // After this: HEAD -> [John] -> [Jane] -> null
        list.addToFront(johnDoe);
        
        // After this: HEAD -> [Dick] -> [John] -> [Jane] -> null
        list.addToFront(dickJiba);
        
        // After this: HEAD -> [Fnck] -> [Dick] -> [John] -> [Jane] -> null
        list.addToFront(fnckUdemy);

        // CHECK THE SIZE
        // Should print: 4
        System.out.println(list.getSize());

        // CHECK IF EMPTY
        // Should print: false (because we have 4 employees)
        System.out.println(list.isEmpty());

        // PRINT THE ENTIRE LIST
        // Will show all employees in order from head to tail
        list.printList();

        // REMOVE FROM FRONT
        // This removes Fnck (the first node)
        // After this: HEAD -> [Dick] -> [John] -> [Jane] -> null
        list.removeFromFront();
        
        // SIZE SHOULD BE 3 NOW
        System.out.println(list.getSize());
        
        // PRINT AGAIN TO SEE THE CHANGE
        list.printList();

    }
}

/*
 * ============================================================================
 * EXPECTED OUTPUT:
 * ============================================================================
 * 
 * 4
 * false
 * head------>Employee{firstName='Fnck', lastName='Udemy', id=111}->
 * Employee{firstName='Dick', lastName='Jiba', id=78}->
 * Employee{firstName='John', lastName='Does', id=6546}->
 * Employee{firstName='Jane', lastName='Jones', id=123}->
 * 3
 * head------>Employee{firstName='Dick', lastName='Jiba', id=78}->
 * Employee{firstName='John', lastName='Does', id=6546}->
 * Employee{firstName='Jane', lastName='Jones', id=123}->
 * 
 * ============================================================================
 * KEY OBSERVATIONS:
 * ============================================================================
 * 
 * 1. ORDER IS REVERSED!
 *    We added: Jane, John, Dick, Fnck
 *    List shows: Fnck, Dick, John, Jane
 *    
 *    WHY? Because we added to the FRONT each time!
 *    Think of stacking plates - the last one you put on top is the first one you see.
 * 
 * 
 * 2. REMOVE FROM FRONT IS FAST
 *    When we removed, Fnck disappeared immediately.
 *    We didn't have to move or shift any other nodes!
 * 
 * 
 * 3. SIZE IS TRACKED AUTOMATICALLY
 *    The size variable is updated every time we add or remove.
 * 
 * ============================================================================
 * INTERVIEW QUESTIONS & ANSWERS
 * ============================================================================
 * 
 * Q1: What would happen if we wanted to add to the END instead of the FRONT?
 * A1: With our current implementation, we'd have to:
 *     1. Traverse the entire list to find the last node (O(n))
 *     2. Set that node's next to the new node
 *     
 *     To make it O(1), we'd need a TAIL pointer!
 * 
 * 
 * Q2: How is this different from using an ArrayList?
 * A2: 
 *     LINKEDLIST:
 *     - Adding to front: O(1) - very fast!
 *     - Uses more memory (each node has a "next" pointer)
 *     - Elements can be anywhere in memory
 *     - Can't access by index quickly
 *     
 *     ARRAYLIST:
 *     - Adding to front: O(n) - must shift all elements!
 *     - Uses less memory (no pointers)
 *     - Elements stored together in memory
 *     - Can access by index instantly: list.get(5) is O(1)
 * 
 * 
 * Q3: What happens if we call removeFromFront() on an empty list?
 * A3: Our implementation checks isEmpty() and returns null.
 *     This is safe and won't crash the program!
 * 
 * 
 * Q4: Can we access the 3rd employee directly like an array?
 * A4: No! Unlike array[2], we can't jump to the 3rd node.
 *     We must start at head and traverse: head -> next -> next
 *     This is why accessing by index is O(n) in a linked list.
 * 
 * 
 * Q5: How would you print the list in REVERSE order?
 * A5: Three approaches:
 *     
 *     Option 1: Use recursion
 *     void printReverse(EmployeeNode node) {
 *         if (node == null) return;
 *         printReverse(node.getNext());  // Recursive call
 *         System.out.println(node);       // Print after recursion
 *     }
 *     
 *     Option 2: Reverse the list first, then print
 *     (But this changes the list structure!)
 *     
 *     Option 3: Use a Stack
 *     - Push all nodes onto a stack
 *     - Pop and print (stack is LIFO - last in, first out)
 * 
 * ============================================================================
 */
