package academy.learnprogramming.stackschallenge;

import java.util.LinkedList;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * STACK CHALLENGE: CHECK FOR PALINDROME USING A STACK
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Write a method that checks if a string is a palindrome using a Stack.
 * 
 * WHAT IS A PALINDROME?
 * ---------------------
 * A palindrome is a word, phrase, or sentence that reads the same
 * backward as forward, ignoring spaces, punctuation, and capitalization.
 * 
 * Examples:
 * - "racecar" → TRUE (same forwards and backwards)
 * - "hello" → FALSE (backwards is "olleh")
 * - "A man, a plan, a canal: Panama" → TRUE (ignoring punctuation)
 * - "Was it a car or a cat I saw?" → TRUE
 * 
 * WHAT IS A STACK?
 * ----------------
 * Think of a stack like a stack of plates:
 * - PUSH: Add a plate on TOP (like push() method)
 * - POP: Remove the plate from TOP (like pop() method)
 * - LIFO: Last In, First Out
 * 
 * Example: Push 1, 2, 3 → Stack looks like [3, 2, 1] (3 on top)
 *          Pop → Returns 3
 *          Pop → Returns 2
 *          Pop → Returns 1
 * 
 * WHY USE A STACK FOR PALINDROME CHECKING?
 * ----------------------------------------
 * - When we push all characters onto a stack, they get REVERSED!
 * - Popping them gives us the reverse order
 * - We can then compare the original with the reversed version
 * 
 * TIME COMPLEXITY: O(n)
 * -------------------
 * - We loop through the string once to push characters: O(n)
 * - We loop through the stack once to build reverse: O(n)
 * - Comparing two strings: O(n)
 * - Total: O(n) + O(n) + O(n) = O(3n) = O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * --------------------
 * - Stack stores all letters/digits from the string: O(n)
 * - StringBuilder for filtered string: O(n)
 * - StringBuilder for reverse: O(n)
 * - Total: O(n)
 * 
 * WHY THIS SOLUTION IS OPTIMAL:
 * ----------------------------
 * - Single pass through the string for filtering
 * - Stack naturally reverses the order (no manual reversal needed)
 * - String comparison is straightforward
 * - Clear and easy to understand
 * 
 * ALGORITHM STEPS:
 * ---------------
 * 1. Convert string to lowercase (for case-insensitive comparison)
 * 2. Filter out non-alphanumeric characters (keep only letters/digits)
 * 3. Push each valid character onto a stack
 * 4. Pop all characters from stack to build reversed string
 * 5. Compare original filtered string with reversed string
 * 
 * COMMON EDGE CASES:
 * -----------------
 * ✓ Empty string → TRUE (empty reads same forwards/backwards)
 * ✓ Single character → TRUE
 * ✓ String with spaces/punctuation → ignore them
 * ✓ Mixed case letters → convert to lowercase first
 * ✓ Numbers and letters → keep both
 * 
 * ALTERNATIVE APPROACHES:
 * ----------------------
 * 1. Two-pointer approach (without stack):
 *    - One pointer at start, one at end
 *    - Compare and move inward
 *    - Trade-off: O(1) space but less educational for stack practice
 * 
 * 2. Recursion:
 *    - Compare first and last character, recurse on middle
 *    - Trade-off: Cleaner code but uses O(n) call stack
 * 
 * 3. Built-in reverse:
 *    - Use StringBuilder.reverse()
 *    - Trade-off: Simpler but doesn't practice stack concepts
 * 
 * RELATED INTERVIEW QUESTIONS:
 * ---------------------------
 * - "Implement a stack using queues"
 * - "Check for balanced parentheses using a stack"
 * - "Reverse a string using a stack"
 * - "Evaluate postfix expression using a stack"
 * - "Find the longest palindromic substring"
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 */

public class Main {

    public static void main(String[] args) {
        // TEST CASE 1: Simple palindrome (all lowercase, no spaces)
        // should return true
        System.out.println(checkForPalindrome("abccba"));
        
        // TEST CASE 2: Palindrome with spaces and mixed case
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
        
        // TEST CASE 6: Very short palindrome
        System.out.println(checkForPalindrome("op"));

    }

    public static boolean checkForPalindrome(String string) {

        // Create a stack using LinkedList
        // In Java, we can use LinkedList as a stack with push() and pop() methods
        LinkedList<Character> stack = new LinkedList<Character>();

        // STEP 1: Convert to lowercase for case-insensitive comparison
        // "Hello" and "hello" should be treated the same
        String lowercase = string.toLowerCase();
        
        // STEP 2: Create a StringBuilder to store only letters and digits
        // We'll ignore spaces, punctuation, and special characters
        StringBuilder one = new StringBuilder(string.length());

        // STEP 3: Filter the string and push valid characters onto the stack
        for(int i =0;i<lowercase.length();i++){
            char pointer = lowercase.charAt(i);  // Get current character
            
            // Only process letters and digits (ignore spaces, punctuation, etc.)
            if(Character.isLetterOrDigit(pointer)){
                one.append(pointer);    // Add to our filtered string
                stack.push(pointer);    // Push onto stack (will be reversed)
            }
        }
        
        // At this point:
        // - 'one' contains filtered string: "wasitacaroracatisaw"
        // - 'stack' contains same characters but will pop in REVERSE order

        // STEP 4: Build the reverse string by popping from stack
        // Remember: stack pops in LIFO (Last In, First Out) order
        StringBuilder reverse = new StringBuilder(stack.size());
        while(!stack.isEmpty()){
            reverse.append(stack.pop());  // Pop each character and add to reverse
        }
        // Now 'reverse' contains: "wasitacaroracatisaw" (backwards)

        // STEP 5: Compare the filtered original with the reversed version
        // If they're the same, it's a palindrome!
        return one.toString().equals(reverse.toString());

    }
}
