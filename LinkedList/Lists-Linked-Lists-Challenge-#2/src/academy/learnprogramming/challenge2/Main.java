package academy.learnprogramming.challenge2;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * CHALLENGE #2: INSERT INTO SORTED LINKED LIST
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Implement a method called 'insertSorted()' that inserts a number into
 * a singly linked list while MAINTAINING the sorted order.
 * 
 * For example:
 * - Start with empty list: []
 * - Insert 3: [3]
 * - Insert 2: [2 -> 3]  (2 goes before 3)
 * - Insert 1: [1 -> 2 -> 3]  (1 goes at the front)
 * - Insert 4: [1 -> 2 -> 3 -> 4]  (4 goes at the end)
 * 
 * The list should ALWAYS remain sorted from smallest to largest!
 * 
 * WHAT IS A SINGLY LINKED LIST?
 * -----------------------------
 * Unlike a doubly linked list, each node only points to the NEXT node.
 * There's no "previous" pointer, which makes it simpler but also means
 * we can only move FORWARD through the list.
 * 
 * WHY IS THIS CHALLENGE IMPORTANT?
 * --------------------------------
 * - Real-world use: Maintaining priority queues, sorted data streams
 * - Interview favorite: Tests your understanding of list traversal and insertion
 * - Efficiency: Better than sorting the entire list after each insertion!
 * 
 * EDGE CASES TO CONSIDER:
 * ----------------------
 * 1. Inserting into an empty list
 * 2. Inserting at the beginning (smallest value)
 * 3. Inserting at the end (largest value)
 * 4. Inserting in the middle
 * 5. Inserting a duplicate value
 * 
 * RELATED INTERVIEW QUESTIONS:
 * ---------------------------
 * - "Merge two sorted linked lists into one sorted list"
 * - "Remove duplicates from a sorted linked list"
 * - "Find the middle element of a linked list"
 * - "Detect if a linked list has a cycle"
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 */

public class Main {

    public static void main(String[] args) {

        // Create some Integer objects to insert
        // NOTE: We're using Integer objects (not primitive int) because
        // our linked list stores objects, not primitive types
        Integer one = 1;
        Integer two = 2;
        Integer three = 3;
        Integer four = 4;

        // Create our sorted linked list
        IntegerLinkedList list = new IntegerLinkedList();
        
        // TEST CASE 1: Insert 3 into empty list
        // Expected: [3]
        list.insertSorted(three);
        list.printList();
        
        // TEST CASE 2: Insert 2 (smaller than existing element)
        // Expected: [2 -> 3]
        list.insertSorted(two);
        list.printList();
        
        // TEST CASE 3: Insert 1 (smallest element - goes to front)
        // Expected: [1 -> 2 -> 3]
        list.insertSorted(one);
        list.printList();
        
        // TEST CASE 4: Insert 4 (largest element - goes to end)
        // Expected: [1 -> 2 -> 3 -> 4]
        list.insertSorted(four);
        list.printList();
        
        // NOTICE: The list stays sorted after each insertion!
        // We never had to call a separate "sort" method.
    }
}
