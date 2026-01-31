package com.company.stacks;

import java.util.EmptyStackException;

/*
 * ============================================================================
 * ARRAY-BASED STACK IMPLEMENTATION
 * ============================================================================
 * 
 * WHAT IS A STACK?
 * A stack is a data structure that follows the LIFO principle:
 * LIFO = Last In, First Out
 * 
 * REAL-WORLD ANALOGIES:
 * 
 * 1. STACK OF PLATES:
 *    - You add new plates to the TOP
 *    - You remove plates from the TOP
 *    - You can't pull a plate from the middle without removing the ones on top!
 * 
 * 2. BROWSER BACK BUTTON:
 *    - Each webpage you visit gets "pushed" onto a stack
 *    - Clicking "Back" "pops" the last page off the stack
 *    - The most recent page is always on top!
 * 
 * 3. UNDO FUNCTION (Ctrl+Z):
 *    - Each action you take gets pushed onto a stack
 *    - Pressing Undo pops the last action and reverses it
 *    - You undo things in reverse order of how you did them!
 * 
 * 4. STACK OF BOOKS:
 *    - The last book you put on top is the first one you'll take off
 * 
 * ============================================================================
 * MAIN STACK OPERATIONS:
 * ============================================================================
 * 
 * 1. PUSH - Add an item to the TOP of the stack
 * 2. POP - Remove and return the item from the TOP of the stack
 * 3. PEEK - Look at the TOP item without removing it
 * 4. isEmpty - Check if the stack is empty
 * 
 * ============================================================================
 * ARRAY vs LINKEDLIST IMPLEMENTATION:
 * ============================================================================
 * 
 * ARRAY-BASED STACK (This file):
 * ✓ PROS:
 *   - Fast access (constant time O(1) for all operations)
 *   - Simple to understand
 *   - Better memory locality (items stored together)
 * 
 * ✗ CONS:
 *   - Fixed initial size (might need to resize)
 *   - Resizing takes time O(n)
 *   - Wasted space if stack isn't full
 * 
 * LINKEDLIST-BASED STACK:
 * ✓ PROS:
 *   - Dynamic size (grows/shrinks as needed)
 *   - No resizing needed
 *   - Never wastes space
 * 
 * ✗ CONS:
 *   - Extra memory for storing links/pointers
 *   - Slightly slower due to pointer following
 * 
 * ============================================================================
 * TIME COMPLEXITY ANALYSIS:
 * ============================================================================
 * 
 * push()  : O(1) normally, O(n) when resizing needed
 * pop()   : O(1) - Always constant time!
 * peek()  : O(1) - Just looking at top element
 * isEmpty(): O(1) - Simple comparison
 * size()  : O(1) - We track this with 'top' variable
 * 
 * ============================================================================
 * SPACE COMPLEXITY: O(n) where n is the number of elements
 * ============================================================================
 * 
 * ============================================================================
 * COMMON USE CASES IN REAL PROGRAMMING:
 * ============================================================================
 * 
 * 1. Function Call Stack (How programs run!)
 * 2. Undo/Redo functionality in applications
 * 3. Browser history (back/forward buttons)
 * 4. Expression evaluation (math calculators)
 * 5. Balanced parentheses checking
 * 6. Depth-First Search (DFS) in graphs
 * 7. Backtracking algorithms
 * 
 * ============================================================================
 * INTERVIEW QUESTIONS COVERED:
 * ============================================================================
 * 
 * QUESTION 1: Balanced Parentheses
 * Problem: Check if parentheses in a string are balanced
 * Example: "({[]})" is balanced, "({[}])" is NOT balanced
 * 
 * Solution Approach:
 * - Push each opening bracket onto stack
 * - When you see a closing bracket, pop from stack and check if it matches
 * - If stack is empty at the end, parentheses are balanced!
 * 
 * QUESTION 2: Reverse a String
 * Problem: Reverse "HELLO" to become "OLLEH"
 * 
 * Solution Approach:
 * - Push each character onto stack: H, E, L, L, O
 * - Pop each character: O, L, L, E, H (reversed!)
 * - Stack naturally reverses the order!
 * 
 * QUESTION 3: Function Call Stack
 * Problem: Explain how programs track function calls
 * 
 * Answer:
 * - When function A calls function B, A gets pushed onto the call stack
 * - When B calls C, B gets pushed on top
 * - When C finishes, it gets popped, and we return to B
 * - When B finishes, it gets popped, and we return to A
 * - This is why "stack overflow" happens with too many function calls!
 * 
 * QUESTION 4: Next Greater Element
 * Problem: For each element in array, find the next greater element to its right
 * Example: [4,5,2,10] → [5,10,10,-1]
 * 
 * Solution: Use stack to track elements we haven't found greater elements for yet!
 * 
 * ============================================================================
 */

public class ArrayStack {

    /*
     * INSTANCE VARIABLES:
     * These are the building blocks of our stack!
     */
    
    // The array that holds our Employee objects (like a column of plates)
    private Employee[] stack;
    
    /*
     * 'top' is SUPER IMPORTANT! It serves two purposes:
     * 1. Points to the next available spot in the array (where we'll add the next item)
     * 2. Also tells us how many items are in the stack!
     * 
     * Example: If top = 0, stack is empty
     *          If top = 5, we have 5 items (at positions 0,1,2,3,4)
     * 
     * Think of 'top' like the height of your plate stack!
     */
    private int top;

    /**
     * CONSTRUCTOR - Creates a new empty stack
     * 
     * @param capacity - How many employees the stack can hold initially
     * 
     * IMPORTANT: The stack can grow if needed! If we run out of space,
     * the push() method will automatically make the array bigger.
     * 
     * EXAMPLE: ArrayStack stack = new ArrayStack(10);
     * This creates a stack that can hold 10 employees to start.
     */
    public ArrayStack (int capacity){
        stack = new Employee[capacity];
        // top starts at 0 because the stack is empty
        // (Java automatically initializes int to 0, but it's good to understand why!)
    }

    /**
     * PUSH - Add a new employee to the TOP of the stack
     * 
     * THINK OF IT LIKE: Placing a new plate on top of a stack of plates
     * 
     * STEP-BY-STEP PROCESS:
     * 1. Check if the array is full (top == stack.length)
     * 2. If full, make a bigger array (double the size)
     * 3. Copy all items to the new bigger array
     * 4. Place the new employee at position 'top'
     * 5. Increment 'top' to point to next available spot
     * 
     * @param employee - The employee object to add to the stack
     * 
     * TIME COMPLEXITY:
     * - Usually O(1) - constant time, super fast!
     * - O(n) when resizing is needed - must copy all n items to new array
     * 
     * EXAMPLE:
     * Before: [Jane, John] (top = 2)
     * push(Mary)
     * After:  [Jane, John, Mary] (top = 3)
     * 
     * REAL-WORLD ANALOGY:
     * Like adding a new task to your to-do list on top!
     */
    public void push(Employee employee){

        // Check if array is full (like checking if plate stack will topple)
        if(top == stack.length){
            /*
             * RESIZING THE ARRAY:
             * We need more space! Let's make a bigger stack.
             * 
             * Why double the size?
             * - If we only added 1 spot each time, we'd resize constantly!
             * - Doubling reduces how often we need to resize
             * - This is called "amortized constant time"
             */
            Employee[] newArray = new Employee[2*stack.length];
            
            // Copy all employees from old array to new array
            // Like carefully moving all plates to a bigger stack!
            System.arraycopy(stack, 0 , newArray, 0 , stack.length);
            
            // Now use the bigger array as our stack
            stack = newArray;
        } // Resizing is O(n) because we copy n elements
        
        /*
         * Add the employee and move 'top' up:
         * - stack[top++] does TWO things:
         *   1. Puts employee at position 'top'
         *   2. Increases 'top' by 1 (using ++)
         * 
         * This is like placing a plate and marking the new height!
         */
        stack[top++] = employee;
    }

    /**
     * POP - Remove and return the TOP employee from the stack
     * 
     * THINK OF IT LIKE: Taking the top plate off a stack
     * 
     * STEP-BY-STEP PROCESS:
     * 1. Check if stack is empty (can't take plate from empty stack!)
     * 2. Decrease 'top' by 1 (using --top)
     * 3. Get the employee at that position
     * 4. Set that position to null (clean up - good practice!)
     * 5. Return the employee we removed
     * 
     * @return The employee that was on top of the stack
     * @throws EmptyStackException if stack is empty
     * 
     * TIME COMPLEXITY: O(1) - Always constant time! Super fast!
     * 
     * EXAMPLE:
     * Before: [Jane, John, Mary] (top = 3)
     * pop() returns Mary
     * After:  [Jane, John, null] (top = 2)
     * 
     * WHY SET TO NULL?
     * - Helps Java's garbage collector free up memory
     * - Prevents memory leaks in large applications
     * - Good programming practice!
     * 
     * REAL-WORLD ANALOGY:
     * Like clicking "Undo" - you remove the last action you did!
     */
    public Employee pop(){
        // Safety check: Can't pop from an empty stack!
        // Like checking if there are any plates before trying to take one
        if(isEmpty()){
            throw new EmptyStackException();
        }

        /*
         * TRICKY PART - Understanding --top:
         * 
         * Remember: 'top' points to the NEXT available position (empty spot)
         * So the actual top item is at position (top - 1)
         * 
         * --top does TWO things in one line:
         * 1. Decreases top by 1 first
         * 2. Then uses that new value
         * 
         * Example: If top = 3
         * - --top makes it 2, then uses stack[2]
         * - This gets us the last item we pushed!
         */
        Employee employee = stack[--top];
        
        // Clean up: Remove the reference to help garbage collector
        stack[top] = null;
        
        return employee;
    }

    /**
     * PEEK - Look at the TOP employee WITHOUT removing it
     * 
     * THINK OF IT LIKE: Looking at the top plate without picking it up
     * 
     * This is useful when you want to see what's on top but aren't ready
     * to remove it yet!
     * 
     * @return The employee on top of the stack
     * @throws EmptyStackException if stack is empty
     * 
     * TIME COMPLEXITY: O(1) - Instant! Just looking at one position
     * 
     * EXAMPLE:
     * Stack: [Jane, John, Mary] (top = 3)
     * peek() returns Mary
     * Stack: [Jane, John, Mary] (top = 3) - Nothing changed!
     * 
     * REAL-WORLD ANALOGY:
     * Like previewing your next undo without actually undoing it!
     */
    public Employee peek(){
        // Safety check: Can't peek at empty stack!
        if(isEmpty()){
            throw new EmptyStackException();
        }

        /*
         * Return the item at position (top - 1)
         * Why top - 1?
         * Because 'top' points to the NEXT empty spot,
         * so the actual top item is one position before that!
         */
        return stack[top-1];
    }

    /**
     * SIZE - Get the number of employees in the stack
     * 
     * @return Number of employees currently in stack
     * 
     * TIME COMPLEXITY: O(1) - We always know the size!
     * 
     * WHY IS THIS SO EASY?
     * Because 'top' tells us the size! If top = 5, we have 5 items.
     * 
     * EXAMPLE:
     * Stack: [Jane, John, Mary] (top = 3)
     * size() returns 3
     */
    public int size(){
        return top;
    }

    /**
     * ISEMPTY - Check if the stack has any employees
     * 
     * @return true if stack is empty, false if it has items
     * 
     * TIME COMPLEXITY: O(1) - Just comparing a number!
     * 
     * WHY CHECK IF EMPTY?
     * - Prevents errors when trying to pop or peek
     * - Useful for processing all items (pop until empty)
     * 
     * EXAMPLE USE:
     * while (!stack.isEmpty()) {
     *     Employee e = stack.pop();
     *     // Process employee
     * }
     * 
     * This processes all employees one by one!
     */
    public boolean isEmpty(){
        // If top is 0, we have no items in the stack
        if(top==0){
            return true;
        }
        return false;

        // ALTERNATIVE SHORTER VERSION (commented out):
        // return top == 0;
        // Both ways work! The shorter version is more concise.
    }

    /**
     * PRINTSTACK - Display all employees in the stack
     * 
     * IMPORTANT: Prints from TOP to BOTTOM (newest to oldest)
     * 
     * WHY START AT (top - 1)?
     * - 'top' points to next empty position
     * - Last item is at position (top - 1)
     * 
     * WHY COUNT DOWN (i--)?
     * - We want to show the stack from top to bottom
     * - Top employee was added most recently
     * - Bottom employee was added first
     * 
     * EXAMPLE OUTPUT:
     * Employee{firstName='Mary', lastName='Smith', id=3}   <- Top (added last)
     * Employee{firstName='John', lastName='Doe', id=2}
     * Employee{firstName='Jane', lastName='Jones', id=1}   <- Bottom (added first)
     * 
     * This shows LIFO order - Last In (Mary) appears First!
     */
    public void printStack(){
        // Loop from top to bottom of stack
        for(int i = top-1; i >= 0; i--){
            System.out.println(stack[i]);
        }
    }
}
