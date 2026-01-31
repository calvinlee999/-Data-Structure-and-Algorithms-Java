package com.company.linkedliststacks;

import java.util.LinkedList;
import java.util.ListIterator;

/*
 * ============================================================================
 * LINKEDLIST-BASED STACK IMPLEMENTATION
 * ============================================================================
 * 
 * WHAT'S DIFFERENT FROM ARRAY STACK?
 * This stack uses Java's built-in LinkedList instead of an array!
 * 
 * ============================================================================
 * LINKEDLIST vs ARRAY - WHICH IS BETTER?
 * ============================================================================
 * 
 * LINKEDLIST STACK (This file):
 * ✓ PROS:
 *   - No resizing needed! Grows automatically
 *   - Never wastes space (only uses what we need)
 *   - No "stack full" problem
 *   - Each push is ALWAYS O(1), never slows down
 * 
 * ✗ CONS:
 *   - Uses extra memory for links between items
 *   - Slightly slower than array (following pointers)
 *   - Less efficient memory access pattern
 * 
 * ARRAY STACK:
 * ✓ PROS:
 *   - Faster access (items stored together in memory)
 *   - No extra memory for links
 *   - Simple and straightforward
 * 
 * ✗ CONS:
 *   - Might need to resize (slow operation)
 *   - Can waste space if mostly empty
 *   - Need to guess initial size
 * 
 * ============================================================================
 * WHEN TO USE WHICH?
 * ============================================================================
 * 
 * Use LINKEDLIST STACK when:
 * - You don't know how big the stack will get
 * - Size changes a lot (grows and shrinks)
 * - You want predictable performance (always O(1))
 * 
 * Use ARRAY STACK when:
 * - You know approximate size in advance
 * - Size is relatively stable
 * - You want maximum speed
 * - Memory is limited (no extra pointers)
 * 
 * ============================================================================
 * HOW LINKEDLIST WORKS:
 * ============================================================================
 * 
 * Instead of using an array, LinkedList connects items with "links":
 * 
 * [Jane] -> [John] -> [Mary] -> null
 *   ^                    ^
 *   |                    |
 *  First              Last (Top of stack)
 * 
 * Each item knows about the next item (like holding hands in a chain!)
 * 
 * TO PUSH: Add new item at the front (makes it the new "first")
 * TO POP: Remove item from the front (the "first" item)
 * 
 * ============================================================================
 * TIME COMPLEXITY - All Operations are O(1)!
 * ============================================================================
 * 
 * push()   : O(1) - ALWAYS constant time! (no resizing)
 * pop()    : O(1) - ALWAYS constant time!
 * peek()   : O(1) - Just look at first item
 * isEmpty(): O(1) - LinkedList tracks this
 * 
 * ============================================================================
 * SPACE COMPLEXITY: O(n) + extra memory for links
 * ============================================================================
 * 
 * ============================================================================
 * USING JAVA'S BUILT-IN LINKEDLIST:
 * ============================================================================
 * 
 * We're using Java's LinkedList class, which already has stack methods:
 * - push() - adds to front
 * - pop() - removes from front
 * - peek() - looks at front
 * 
 * We're just wrapping these to make our own Stack interface!
 * This is called the "Adapter Pattern" - we're adapting LinkedList to be a Stack.
 * 
 * ============================================================================
 * COMMON INTERVIEW QUESTIONS (Same as Array Stack):
 * ============================================================================
 * 
 * QUESTION 1: Why use LinkedList instead of ArrayList for a stack?
 * ANSWER:
 * - LinkedList is better because we only add/remove from one end (the top)
 * - ArrayList would need to shift elements when removing from front
 * - LinkedList just changes a pointer - much faster!
 * 
 * QUESTION 2: Could we implement a stack with ArrayList?
 * ANSWER:
 * - Yes! But we'd push/pop from the END of the ArrayList (not the front)
 * - That way we don't need to shift elements
 * - ArrayList.add() and ArrayList.remove(size-1) would work great!
 * 
 * QUESTION 3: What's the difference between Stack and Queue?
 * ANSWER:
 * - Stack = LIFO (Last In, First Out) - like a stack of plates
 * - Queue = FIFO (First In, First Out) - like a line at a store
 * - Both can use LinkedList or Array!
 * 
 * ============================================================================
 */

public class LinkedStack {

    /*
     * Our underlying data structure - Java's built-in LinkedList!
     * 
     * The LinkedList will hold all our Employee objects.
     * We'll use it like a stack by only accessing the front (head).
     * 
     * Think of it as a chain of employees, where we can easily
     * add or remove from the front of the chain!
     */
    private LinkedList<Employee> stack;

    /**
     * CONSTRUCTOR - Creates a new empty LinkedList stack
     * 
     * This is simpler than the array version!
     * - No need to specify capacity (LinkedList grows automatically)
     * - No need to worry about resizing
     * - Just create an empty LinkedList and we're ready!
     * 
     * EXAMPLE: LinkedStack stack = new LinkedStack();
     */
    public LinkedStack(){
        stack = new LinkedList<Employee>();
    }

    /**
     * PUSH - Add a new employee to the TOP of the stack
     * 
     * THINK OF IT LIKE: Adding a new link to the front of a chain
     * 
     * HOW IT WORKS:
     * - LinkedList.push() adds to the FRONT (head) of the list
     * - This becomes the new "first" item
     * - No array resizing needed - just create a new link!
     * 
     * @param employee - The employee to add to the stack
     * 
     * TIME COMPLEXITY: O(1) - ALWAYS constant time!
     * (No resizing, no copying - just add one link)
     * 
     * EXAMPLE:
     * Before: Jane -> John -> null
     * push(Mary)
     * After:  Mary -> Jane -> John -> null
     *         ^--- New top!
     * 
     * REAL-WORLD ANALOGY:
     * Like adding a new task to the top of your to-do list!
     */
    public void push(Employee employee){
        stack.push(employee);
    }

    /**
     * POP - Remove and return the TOP employee from the stack
     * 
     * THINK OF IT LIKE: Removing the first link from a chain
     * 
     * HOW IT WORKS:
     * - LinkedList.pop() removes from the FRONT (head)
     * - Returns the removed employee
     * - Throws exception if stack is empty
     * 
     * @return The employee that was on top
     * @throws NoSuchElementException if stack is empty
     * 
     * TIME COMPLEXITY: O(1) - ALWAYS constant time!
     * 
     * EXAMPLE:
     * Before: Mary -> Jane -> John -> null
     * pop() returns Mary
     * After:  Jane -> John -> null
     *         ^--- New top!
     * 
     * LIFO IN ACTION: Mary was pushed last, so she's popped first!
     */
    public Employee pop(){
        return stack.pop();
    }

    /**
     * PEEK - Look at the TOP employee WITHOUT removing
     * 
     * THINK OF IT LIKE: Looking at the first link without breaking the chain
     * 
     * This lets you see what's on top without changing the stack!
     * 
     * @return The employee on top of the stack
     * @throws NoSuchElementException if stack is empty
     * 
     * TIME COMPLEXITY: O(1) - Just looking at first item!
     * 
     * EXAMPLE:
     * Stack: Mary -> Jane -> John -> null
     * peek() returns Mary
     * Stack: Mary -> Jane -> John -> null  (unchanged!)
     */
    public Employee peek(){
        return stack.peek();
    }

    /**
     * ISEMPTY - Check if the stack has any employees
     * 
     * @return true if stack is empty, false otherwise
     * 
     * TIME COMPLEXITY: O(1) - LinkedList tracks its size!
     * 
     * USEFUL FOR:
     * - Preventing errors before pop/peek
     * - Processing all items:
     *   while (!stack.isEmpty()) {
     *       Employee e = stack.pop();
     *       // Process employee
     *   }
     */
    public boolean isEmpty(){
        return stack.isEmpty();
    }

    /**
     * PRINTSTACK - Display all employees in the stack
     * 
     * IMPORTANT: This prints from FRONT to BACK (top to bottom)
     * 
     * WHAT'S A LISTITERATOR?
     * - It's like a bookmark that moves through the LinkedList
     * - hasNext() checks if there are more items
     * - next() gives us the next item and moves the bookmark
     * 
     * WHY USE ITERATOR?
     * - LinkedList doesn't have array-style indexes
     * - Iterator is the standard way to go through a LinkedList
     * - It's safe and efficient!
     * 
     * EXAMPLE OUTPUT:
     * Employee{firstName='Mary', lastName='Smith', id=3}   <- Front (top)
     * Employee{firstName='Jane', lastName='Jones', id=2}
     * Employee{firstName='John', lastName='Doe', id=1}     <- Back (bottom)
     * 
     * This shows LIFO order - Last In (Mary) is shown First!
     * 
     * HOW IT WORKS:
     * 1. Create iterator pointing to start of list
     * 2. While there are more employees:
     *    a. Get the next employee
     *    b. Print it
     *    c. Move to next position
     * 3. Stop when we reach the end
     */
    public void printStack(){
        // Create an iterator to traverse the LinkedList
        ListIterator<Employee> iterator = stack.listIterator();
        
        // Loop through all employees
        while(iterator.hasNext()){
            // Print current employee and move to next
            System.out.println(iterator.next());
        }
    }

}
