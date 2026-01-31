package com.company.stacks;

/*
 * ============================================================================
 * ARRAY STACK DEMONSTRATION
 * ============================================================================
 * 
 * This program demonstrates how an Array-based Stack works!
 * 
 * We'll practice:
 * 1. Creating a stack
 * 2. Pushing employees onto the stack (adding to top)
 * 3. Peeking at the top employee (looking without removing)
 * 4. Popping employees from the stack (removing from top)
 * 5. Seeing LIFO in action!
 * 
 * WATCH FOR THE LIFO PATTERN:
 * - Last employee pushed = First employee popped
 * - "Last In, First Out" - like a stack of plates!
 */

public class Main {

    public static void main(String[] args) {

        /*
         * STEP 1: CREATE THE STACK
         * Making a stack that can hold 10 employees initially
         * (it will grow automatically if we add more)
         * 
         * Think of this as getting an empty plate holder!
         */
        ArrayStack stack = new ArrayStack(10);

        /*
         * STEP 2: PUSH EMPLOYEES ONTO THE STACK
         * Adding employees one by one to the TOP
         * 
         * Watch the order carefully - this is important!
         * 
         * STACK AFTER EACH PUSH:
         * After push "one":   [one]  <- top
         * After push "two":   [one, two]  <- top
         * After push "three": [one, two, three]  <- top  
         * After push "four":  [one, two, three, four]  <- top
         * 
         * Notice: "four" was pushed LAST, so it's on TOP!
         */
        stack.push(new Employee("one","one",123));
        stack.push(new Employee("two","two",2433));
        stack.push(new Employee("three","three",333));
        stack.push(new Employee("four","four",45123));  // This one is on TOP!

        /*
         * STEP 3: PRINT THE ENTIRE STACK
         * This shows all employees from TOP to BOTTOM
         * You should see "four" at the top, then "three", "two", "one"
         * 
         * This demonstrates LIFO - the Last one In ("four") is shown First!
         */
        System.out.println("\n=== FULL STACK (Top to Bottom) ===");
        stack.printStack();
        
        /*
         * STEP 4: PEEK AT THE TOP EMPLOYEE
         * Looking at who's on top WITHOUT removing them
         * 
         * Expected: "four" (the last one we pushed)
         * The stack doesn't change - we're just looking!
         */
        System.out.println("\n=== PEEK (Look at top without removing) ===");
        System.out.println("peek is "+ stack.peek());

        /*
         * STEP 5: POP THE TOP EMPLOYEE
         * Now we REMOVE the top employee and return it
         * 
         * Expected: "four" gets removed and returned
         * New top: "three"
         * 
         * STACK CHANGES:
         * Before pop: [one, two, three, four]  <- top
         * After pop:  [one, two, three]  <- new top
         * 
         * This is LIFO in action! Last In ("four") is First Out!
         */
        System.out.println("\n=== POP (Remove and return top) ===");
        System.out.println("Popped: "+ stack.pop());
        
        /*
         * STEP 6: PRINT STACK AGAIN
         * Now the stack should be shorter - "four" is gone!
         * Top should now be "three"
         */
        System.out.println("\n=== STACK AFTER POP ===");
        stack.printStack();
        
        /*
         * STEP 7: PEEK AGAIN
         * Verify the new top is "three"
         * 
         * Notice: We pushed in order 1,2,3,4
         * But when we pop, we get them in reverse: 4,3,2,1
         * That's the LIFO (Last In, First Out) principle!
         */
        System.out.println("\n=== NEW TOP AFTER POP ===");
        System.out.println("peek is "+ stack.peek());
        
        /*
         * KEY TAKEAWAYS:
         * 1. Items are always added and removed from the TOP
         * 2. You can't access items in the middle
         * 3. Last item added is always first item removed (LIFO)
         * 4. Peek lets you look without changing the stack
         * 5. Pop removes AND returns the top item
         * 
         * REAL-WORLD CONNECTION:
         * This is exactly how your browser's back button works!
         * Each page you visit gets pushed onto a stack.
         * Clicking back pops the last page you visited!
         */
    }
}
