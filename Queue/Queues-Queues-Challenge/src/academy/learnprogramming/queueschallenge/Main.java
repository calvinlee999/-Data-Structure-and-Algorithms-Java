package academy.learnprogramming.queueschallenge;

import java.util.LinkedList;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * QUEUE CHALLENGE: CHECK FOR PALINDROME USING STACK AND QUEUE
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Write a method that checks if a string is a palindrome using BOTH
 * a Stack and a Queue working together.
 * 
 * WHAT IS A PALINDROME?
 * ---------------------
 * A palindrome reads the same forwards and backwards.
 * Examples: "racecar", "madam", "Was it a car or a cat I saw?"
 * 
 * KEY DIFFERENCE FROM STACK CHALLENGE:
 * -----------------------------------
 * Instead of building a reversed string, we use the natural properties
 * of Stack (LIFO) and Queue (FIFO) to compare characters directly!
 * 
 * WHAT IS A QUEUE?
 * ----------------
 * Think of a queue like a line at a store:
 * - ADD (enqueue): Join the END of the line
 * - REMOVE (dequeue): Leave from the FRONT of the line
 * - FIFO: First In, First Out
 * 
 * Example: Add 1, 2, 3 → Queue looks like [1, 2, 3] (1 at front)
 *          Remove → Returns 1
 *          Remove → Returns 2
 *          Remove → Returns 3
 * 
 * WHY USE BOTH STACK AND QUEUE?
 * -----------------------------
 * - STACK gives us REVERSE order (LIFO - Last In, First Out)
 * - QUEUE gives us NORMAL order (FIFO - First In, First Out)
 * - By comparing them element by element, we check if it's the same
 *   forwards and backwards!
 * 
 * CLEVER INSIGHT:
 * --------------
 * If we add the same characters to both a stack and a queue:
 * - Stack will pop them in REVERSE order
 * - Queue will remove them in ORIGINAL order
 * - If they match at each step → it's a palindrome!
 * 
 * Example with "aba":
 *   Stack: push a, b, a → pops as: a, b, a (reversed)
 *   Queue: add a, b, a → removes as: a, b, a (original)
 *   Compare: a==a ✓, b==b ✓, a==a ✓ → PALINDROME!
 * 
 * TIME COMPLEXITY: O(n)
 * -------------------
 * - Loop through string once to filter and add to structures: O(n)
 * - Loop through stack/queue to compare: O(n)
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * --------------------
 * - Stack stores all valid characters: O(n)
 * - Queue stores all valid characters: O(n)
 * - Total: O(2n) = O(n)
 * 
 * WHY THIS SOLUTION IS OPTIMAL:
 * ----------------------------
 * - More elegant than building reversed string
 * - Directly compares characters without extra string construction
 * - Demonstrates understanding of both Stack and Queue
 * - Early exit on first mismatch (efficient!)
 * 
 * ALGORITHM STEPS:
 * ---------------
 * 1. Convert string to lowercase
 * 2. Filter: keep only letters (a-z)
 * 3. Add each letter to BOTH stack and queue
 * 4. Pop from stack and remove from queue simultaneously
 * 5. If any pair doesn't match → NOT a palindrome
 * 6. If all pairs match → IT IS a palindrome
 * 
 * COMMON EDGE CASES:
 * -----------------
 * ✓ Empty string → TRUE
 * ✓ Single character → TRUE
 * ✓ String with spaces/punctuation → filter them out
 * ✓ Mixed case → convert to lowercase
 * ✓ Only letters (this version doesn't check digits)
 * 
 * ALTERNATIVE APPROACHES:
 * ----------------------
 * 1. Two-pointer approach:
 *    - Compare first and last, move inward
 *    - Trade-off: O(1) space, but doesn't practice Stack/Queue
 * 
 * 2. Single stack only (like previous challenge):
 *    - Build reversed string
 *    - Trade-off: Uses less space but less elegant
 * 
 * 3. Recursion:
 *    - Compare first and last recursively
 *    - Trade-off: Cleaner code but uses call stack
 * 
 * RELATED INTERVIEW QUESTIONS:
 * ---------------------------
 * - "Implement a queue using two stacks"
 * - "Implement a stack using two queues"
 * - "Design a circular queue"
 * - "Check for balanced parentheses"
 * - "Reverse first k elements of a queue"
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 */

public class Main {

    public static void main(String[] args) {
        // TEST CASE 1: Simple palindrome
        // should return true
        System.out.println(checkForPalindrome("abccba"));
        
        // TEST CASE 2: Palindrome with spaces and punctuation
        // should return true
        System.out.println(checkForPalindrome("Was it a car or a cat I saw?"));
        
        // TEST CASE 3: Palindrome with punctuation
        // should return true
        System.out.println(checkForPalindrome("I did, did I?"));
        
        // TEST CASE 4: NOT a palindrome
        // should return false
        System.out.println(checkForPalindrome("hello"));
        
        // TEST CASE 5: Palindrome with apostrophe
        // should return true
        System.out.println(checkForPalindrome("Don't nod"));
    }

    public static boolean checkForPalindrome(String string) {

        // Create both data structures
        // Stack: will give us REVERSE order (LIFO)
        LinkedList<Character> stack = new LinkedList<Character>();
        // Queue: will give us NORMAL order (FIFO)
        LinkedList<Character> queue = new LinkedList<Character>();

        // STEP 1: Convert to lowercase for case-insensitive comparison
        String lowerCase = string.toLowerCase();
        
        // STEP 2: Filter and add valid characters to BOTH stack and queue
        for(int i =0;i<lowerCase.length();i++){
            Character point = lowerCase.charAt(i);  // Get current character
            
            // Only process lowercase letters (a-z)
            // NOTE: This version only checks letters, not digits
            if(point>='a' && point<='z'){
                queue.add(point);   // Add to END of queue (FIFO)
                stack.push(point);  // Push to TOP of stack (LIFO)
            }

        }
        
        // At this point, both structures have the same characters:
        // For "aba":
        // - queue: [a, b, a] (a is at front)
        // - stack: [a, b, a] (a is on top)

        // STEP 3: Compare characters from stack and queue
        // Stack pops in REVERSE order, Queue removes in ORIGINAL order
        while(!stack.isEmpty()){
            // Pop from stack (gets LAST character added)
            // Remove from queue (gets FIRST character added)
            if(!stack.pop().equals(queue.remove())){
                return false;  // Mismatch found! Not a palindrome.
            }
        }
        
        // If we made it through all comparisons without mismatch,
        // the string IS a palindrome!
        return true;
    }
}
