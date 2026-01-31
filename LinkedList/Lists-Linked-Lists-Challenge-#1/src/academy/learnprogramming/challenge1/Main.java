package academy.learnprogramming.challenge1;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * CHALLENGE #1: INSERT NODE BEFORE ANOTHER NODE IN DOUBLY LINKED LIST
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Implement a method called 'addBefore()' that inserts a new employee node
 * BEFORE an existing employee in a doubly linked list.
 * 
 * For example, if the list contains: [Mike -> Mary -> John -> Jane]
 * and you want to add Bill BEFORE John, the result should be:
 * [Mike -> Mary -> Bill -> John -> Jane]
 * 
 * WHAT IS A DOUBLY LINKED LIST?
 * -----------------------------
 * Think of it like a train where each car (node) has:
 * - A pointer to the NEXT car (next reference)
 * - A pointer to the PREVIOUS car (previous reference)
 * - Some cargo (the employee data)
 * 
 * This makes it easier to move forward AND backward through the list!
 * 
 * WHY IS THIS CHALLENGE IMPORTANT?
 * --------------------------------
 * - Real-world applications: Music playlists, browser history, undo/redo features
 * - Interview favorite: Tests your understanding of pointer manipulation
 * - Complexity: Requires handling multiple edge cases carefully
 * 
 * EDGE CASES TO CONSIDER:
 * ----------------------
 * 1. What if the list is empty?
 * 2. What if we're inserting before the head (first node)?
 * 3. What if the existing employee doesn't exist in the list?
 * 4. What if we're inserting before the tail (last node)?
 * 
 * RELATED INTERVIEW QUESTIONS:
 * ---------------------------
 * - "Implement a function to delete a node from a doubly linked list"
 * - "How would you reverse a doubly linked list?"
 * - "What's the difference between singly and doubly linked lists?"
 * - "Implement an LRU (Least Recently Used) cache using a doubly linked list"
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 */

public class Main {

    public static void main(String[] args) {

        // STEP 1: Create some employee objects to work with
        // Think of these as the "data" we want to store in our linked list
        Employee janeJones = new Employee("Jane", "Jones", 123);
        Employee johnDoe = new Employee("John", "Doe", 4567);
        Employee marySmith = new Employee("Mary", "Smith", 22);
        Employee mikeWilson = new Employee("Mike", "Wilson", 3245);
        Employee billEnd = new Employee("Bill", "End", 78);

        // STEP 2: Create our doubly linked list (like creating an empty train)
        EmployeeDoublyLinkedList list = new EmployeeDoublyLinkedList();

        // STEP 3: Add employees to the front of the list
        // Each time we add to front, the new employee becomes the "head" (first car)
        // After these operations, the list looks like:
        // Mike -> Mary -> John -> Jane
        list.addToFront(janeJones);   // List: Jane
        list.addToFront(johnDoe);     // List: John -> Jane
        list.addToFront(marySmith);   // List: Mary -> John -> Jane
        list.addToFront(mikeWilson);  // List: Mike -> Mary -> John -> Jane
        list.printList();             // Display the current list

        // STEP 4: TEST OUR CHALLENGE - Insert Bill BEFORE John
        // This should place Bill right before John in the list
        // Expected result: Mike -> Mary -> Bill -> John -> Jane
        list.addBefore(billEnd, johnDoe);
        
        // TEST CASE 2: Insert "Someone Else" BEFORE Mary (who is now near the head)
        // Expected result: Mike -> Someone -> Mary -> Bill -> John -> Jane
		list.addBefore(new Employee("Someone", "Else", 1111), marySmith);
        list.printList();  // Display the final list

    }
}
