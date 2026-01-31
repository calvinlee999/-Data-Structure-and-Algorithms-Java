package com.company.linkedliststacks;

/*
 * ============================================================================
 * LINKEDLIST STACK DEMONSTRATION
 * ============================================================================
 * 
 * This program demonstrates how a LinkedList-based Stack works!
 * 
 * We'll practice:
 * 1. Creating a LinkedList stack (no size limit!)
 * 2. Pushing employees onto the stack (adding to front)
 * 3. Peeking at the top employee (looking without removing)
 * 4. Popping employees from the stack (removing from front)
 * 5. Comparing with Array stack implementation
 * 
 * KEY DIFFERENCES FROM ARRAY STACK:
 * - No initial capacity needed
 * - Never needs resizing
 * - Always O(1) for push (no slow resizing)
 * - Uses slightly more memory (for links)
 * 
 * WATCH FOR LIFO PATTERN:
 * - Last employee pushed = First employee popped
 * - "Last In, First Out" - like a stack of plates!
 */

public class Main {

    public static void main(String[] args) {

        /*
         * STEP 1: CREATE EMPLOYEE OBJECTS
         * Making some employees to add to our stack
         * 
         * Note: We create these separately to make it easier to see
         * what's happening when we push them onto the stack
         */
        Employee janeJohns = new Employee("Jane", "Jones", 123);
        Employee johnDoe = new Employee("John", "Does", 6546);
        Employee dickJiba = new Employee("Dick", "Jiba", 78);
        Employee fnckUdemy = new Employee("Fnck", "Udemy", 111);
        Employee gfdgddfg = new Employee("gfdg", "ddfg", 77888);

        /*
         * STEP 2: CREATE THE LINKEDLIST STACK
         * Notice: No capacity needed!
         * 
         * DIFFERENCE FROM ARRAY:
         * - Array version: new ArrayStack(10) - need to specify size
         * - LinkedList version: new LinkedStack() - grows automatically!
         * 
         * This is one of the main advantages of LinkedList!
         */
        LinkedStack stack = new LinkedStack();

        /*
         * STEP 3: PUSH EMPLOYEES ONTO THE STACK
         * Adding employees one by one to the FRONT of the LinkedList
         * 
         * Watch the order - this is LIFO in action!
         * 
         * LINKEDLIST STATE AFTER EACH PUSH:
         * After push(Jane):  Jane -> null  <- Front is top!
         * After push(John):  John -> Jane -> null
         * After push(Dick):  Dick -> John -> Jane -> null
         * After push(Fnck):  Fnck -> Dick -> John -> Jane -> null
         *                    ^--- This is the TOP!
         * 
         * Notice: Each new employee goes to the FRONT (becomes new top)
         */
        stack.push(janeJohns);   // First one in
        stack.push(johnDoe);
        stack.push(dickJiba);
        stack.push(fnckUdemy);   // Last one in - now on TOP!
        
        /*
         * STEP 4: PRINT THE ENTIRE STACK
         * This shows all employees from FRONT to BACK (top to bottom)
         * 
         * You should see Fnck first (top), then Dick, John, Jane (bottom)
         * 
         * This demonstrates LIFO:
         * - Jane was pushed first -> She's at the bottom
         * - Fnck was pushed last -> He's at the top!
         */
        System.out.println("\n=== FULL STACK (Top to Bottom) ===");
        stack.printStack();

        /*
         * STEP 5: PEEK AT THE TOP EMPLOYEE
         * Looking at who's on top WITHOUT removing them
         * 
         * Expected: Fnck (the last one we pushed)
         * The stack doesn't change - we're just looking!
         * 
         * LIFO CHECK: The Last one In (Fnck) is the one we see First!
         */
        System.out.println("\n=== PEEK (Look at top without removing) ===");
        System.out.println(stack.peek());

        /*
         * STEP 6: POP THE TOP EMPLOYEE
         * Now we REMOVE the top employee and return them
         * 
         * Expected: Fnck gets removed and returned
         * New top: Dick
         * 
         * LINKEDLIST CHANGES:
         * Before pop: Fnck -> Dick -> John -> Jane -> null
         * After pop:  Dick -> John -> Jane -> null
         *             ^--- New front/top!
         * 
         * This is LIFO in action! Last In (Fnck) is First Out!
         */
        System.out.println("\n=== POP (Remove and return top) ===");
        System.out.println("Popped: " + stack.pop());

        /*
         * STEP 7: PEEK AT NEW TOP
         * Verify the new top is Dick (the next one in line)
         * 
         * Notice the pattern:
         * - We pushed in order: Jane, John, Dick, Fnck
         * - We're popping in reverse: Fnck, Dick, John, Jane
         * - That's LIFO (Last In, First Out)!
         */
        System.out.println("\n=== NEW TOP AFTER POP ===");
        System.out.println(stack.peek());
        
        /*
         * STEP 8: PRINT STACK AGAIN
         * Stack should now be shorter - Fnck is gone!
         * Should show: Dick, John, Jane
         */
        System.out.println("\n=== STACK AFTER POP ===");
        stack.printStack();
        
        /*
         * ========================================================================
         * KEY TAKEAWAYS - COMPARING LINKEDLIST vs ARRAY STACK:
         * ========================================================================
         * 
         * SIMILARITIES:
         * ✓ Both follow LIFO (Last In, First Out)
         * ✓ Both have push, pop, peek, isEmpty operations
         * ✓ All operations are O(1) time complexity
         * ✓ Both work the same way from user's perspective
         * 
         * DIFFERENCES:
         * 
         * LINKEDLIST STACK (This file):
         * ✓ No initial capacity needed
         * ✓ Never needs resizing
         * ✓ Push is ALWAYS O(1) (no occasional slowdown)
         * ✗ Uses extra memory for links between items
         * ✗ Slightly slower memory access
         * 
         * ARRAY STACK:
         * ✓ Faster memory access (items stored together)
         * ✓ No extra memory for links
         * ✗ Needs initial capacity
         * ✗ Occasionally needs resizing (O(n) operation)
         * ✗ Might waste space if oversized
         * 
         * ========================================================================
         * WHEN TO USE EACH:
         * ========================================================================
         * 
         * Use LINKEDLIST when:
         * - You don't know how big it will get
         * - Size changes frequently
         * - You want guaranteed O(1) push always
         * 
         * Use ARRAY when:
         * - You know approximate size
         * - Size is stable
         * - You want maximum speed
         * - Memory is limited
         * 
         * ========================================================================
         * INTERVIEW TIP:
         * ========================================================================
         * 
         * Interviewers love asking:
         * "What's the difference between array and LinkedList stack?"
         * 
         * Good answer:
         * "Both implement LIFO and have O(1) operations. Array is faster but
         * needs resizing. LinkedList uses more memory but never needs resizing.
         * I'd choose based on whether I know the size in advance."
         * 
         * ========================================================================
         */
    }
}
