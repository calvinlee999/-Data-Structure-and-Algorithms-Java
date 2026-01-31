package com.company.binarysearch;

/**
 * Binary Search - The Smart Way to Find Things!
 * 
 * WHAT IS BINARY SEARCH?
 * Imagine you're looking for a word in a dictionary. Would you start from page 1
 * and check every page? NO! You'd open to the middle, see if your word comes before
 * or after, then repeat with the remaining half. That's binary search!
 * 
 * KEY REQUIREMENTS:
 * ⚠️ THE ARRAY MUST BE SORTED! Binary search ONLY works on sorted data.
 * 
 * WHY IS IT SO FAST?
 * - Linear search: O(n) - checks every element
 * - Binary search: O(log n) - cuts problem in half each time
 * 
 * For 1,000,000 items:
 * - Linear search: up to 1,000,000 comparisons
 * - Binary search: only 20 comparisons! 🚀
 * 
 * REAL-WORLD USES:
 * - Phone contacts (sorted alphabetically)
 * - Dictionary lookups
 * - Finding a specific date in logs
 * - Database indexing
 * - Any "find this item" in sorted data
 * 
 * @author Data Structures Learning Lab
 * @version 2.0
 */
public class Main {

    public static void main(String[] args) {
        // Test array - NOTICE IT'S SORTED! That's crucial!
        int[] intArray = {-22, -15, 1, 7, 20, 35, 55};

        System.out.println("=== BINARY SEARCH DEMONSTRATION ===\n");
        
        // Testing Iterative Binary Search
        System.out.println("Iterative Binary Search:");
        System.out.println("  Finding -15: index " + iterativeBinarySearch(intArray, -15));
        System.out.println("  Finding 7: index " + iterativeBinarySearch(intArray, 7));
        System.out.println("  Finding 55: index " + iterativeBinarySearch(intArray, 55));
        System.out.println("  Finding 8888 (not there): " + iterativeBinarySearch(intArray, 8888));
        
        System.out.println("\nRecursive Binary Search:");
        System.out.println("  Finding -15: index " + recursiveBinarySearch(intArray, 0, intArray.length, -15));
        System.out.println("  Finding 7: index " + recursiveBinarySearch(intArray, 0, intArray.length, 7));
        System.out.println("  Finding 55: index " + recursiveBinarySearch(intArray, 0, intArray.length, 55));
        System.out.println("  Finding 8888 (not there): " + recursiveBinarySearch(intArray, 0, intArray.length, 8888));
    }

    /**
     * ITERATIVE BINARY SEARCH
     * 
     * This version uses a loop (iterative approach).
     * Generally preferred because it uses less memory than recursion.
     * 
     * HOW IT WORKS:
     * 1. Start with the whole array (start = 0, end = length)
     * 2. Find the middle element
     * 3. If middle == value, FOUND IT!
     * 4. If middle > value, search the left half (value must be smaller)
     * 5. If middle < value, search the right half (value must be bigger)
     * 6. Repeat until found or no more elements to check
     * 
     * TIME COMPLEXITY: O(log n) - cuts search space in half each time
     * SPACE COMPLEXITY: O(1) - only uses a few variables
     * 
     * INTERVIEW QUESTION: "Why calculate midpoint as start + (end - start) / 2?"
     * ANSWER: To prevent integer overflow! If start and end are very large,
     * (start + end) could exceed Integer.MAX_VALUE and become negative!
     * The formula start + (end - start) / 2 is safer.
     * 
     * @param input The sorted array to search in
     * @param value The value we're looking for
     * @return The index where value is found, or -1 if not found
     */
    public static int iterativeBinarySearch(int[] input, int value) {
        int start = 0;  // Left boundary of search range
        int end = input.length;  // Right boundary of search range
        
        // Keep searching while there's a valid range
        while (start < end) {
            // Find middle index (safe from overflow!)
            int midpoint = start + (end - start) / 2;
            
            // Case 1: Found it! Return the index
            if (input[midpoint] == value) {
                return midpoint;
            }
            // Case 2: Value must be in left half (smaller numbers)
            else if (input[midpoint] > value) {
                end = midpoint;  // Ignore right half
            }
            // Case 3: Value must be in right half (bigger numbers)
            else if (input[midpoint] < value) {
                start = midpoint + 1;  // Ignore left half
            }
        }
        
        // Not found after checking all possibilities
        return -1;
    }

    /**
     * RECURSIVE BINARY SEARCH
     * 
     * This version uses recursion (function calls itself).
     * More elegant code, but uses more memory due to call stack.
     * 
     * HOW IT WORKS:
     * - Base case: If no more elements to check (start >= end), return -1
     * - Recursive case: Check middle, then call itself on left or right half
     * 
     * TIME COMPLEXITY: O(log n) - same as iterative
     * SPACE COMPLEXITY: O(log n) - each recursive call uses stack space
     * 
     * INTERVIEW QUESTIONS:
     * 
     * Q: "When would you use recursion vs iteration for binary search?"
     * A: - Iteration: Generally better - uses less memory, slightly faster
     *    - Recursion: Cleaner code, easier to understand, good for interviews
     *    In production code, use iteration. In interviews, either works!
     * 
     * Q: "What happens if the array isn't sorted?"
     * A: Binary search will give WRONG results! Always verify array is sorted
     *    or sort it first (which takes O(n log n) time).
     * 
     * Q: "Can you use binary search on a linked list?"
     * A: Not efficiently! You can't jump to the middle in O(1) time.
     *    You'd need to traverse to the middle (O(n)), defeating the purpose.
     * 
     * @param input The sorted array to search in
     * @param start The left boundary of current search range
     * @param end The right boundary of current search range
     * @param value The value we're looking for
     * @return The index where value is found, or -1 if not found
     */
    public static int recursiveBinarySearch(int[] input, int start, int end, int value) {
        // BASE CASE: No more elements to check
        if (start >= end) {
            return -1;  // Not found
        }
        
        // Find the middle of current range
        int midpoint = start + (end - start) / 2;
        
        // FOUND IT! Return the index
        if (input[midpoint] == value) {
            return midpoint;
        }
        // Value is in the LEFT half - search there
        else if (input[midpoint] > value) {
            return recursiveBinarySearch(input, start, midpoint, value);
        }
        // Value is in the RIGHT half - search there
        else {
            return recursiveBinarySearch(input, midpoint + 1, end, value);
        }
    }
}

/*
 * ============================================================
 * INTERVIEW QUESTIONS & SOLUTIONS
 * ============================================================
 * 
 * QUESTION 1: Find First and Last Position of Element in Sorted Array
 * -----------------------------------------------------------------
 * Given: [5, 7, 7, 8, 8, 10], target = 8
 * Output: [3, 4] (8 appears at index 3 and 4)
 * 
 * SOLUTION:
 * - Use binary search TWICE!
 * - First binary search: Find leftmost occurrence
 * - Second binary search: Find rightmost occurrence
 * - Time: O(log n), Space: O(1)
 * 
 * QUESTION 2: Search in Rotated Sorted Array
 * -----------------------------------------------------------------
 * Given: [4, 5, 6, 7, 0, 1, 2], target = 0
 * The array was sorted [0, 1, 2, 4, 5, 6, 7] then rotated
 * 
 * SOLUTION:
 * - Modified binary search!
 * - One half is always sorted - figure out which half
 * - Check if target is in the sorted half
 * - If yes, search there. If no, search other half
 * - Time: O(log n), Space: O(1)
 * 
 * QUESTION 3: Find Peak Element
 * -----------------------------------------------------------------
 * Given: [1, 2, 3, 1], find any peak (element greater than neighbors)
 * Output: index 2 (value 3)
 * 
 * SOLUTION:
 * - Use binary search approach!
 * - If mid > mid+1, peak is on left side (or mid is peak)
 * - If mid < mid+1, peak is on right side
 * - Time: O(log n), Space: O(1)
 * 
 * QUESTION 4: Square Root (without using sqrt function)
 * -----------------------------------------------------------------
 * Given: x = 8
 * Output: 2 (sqrt(8) = 2.82, we return integer part)
 * 
 * SOLUTION:
 * - Binary search from 1 to x!
 * - If mid * mid = x, found it!
 * - If mid * mid > x, answer is in left half
 * - If mid * mid < x, answer is in right half
 * - Time: O(log n), Space: O(1)
 * 
 * QUESTION 5: Search a 2D Matrix
 * -----------------------------------------------------------------
 * Matrix where each row is sorted AND first element of each row
 * is greater than last element of previous row.
 * Given: [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
 * Output: true
 * 
 * SOLUTION:
 * - Treat 2D matrix as 1D sorted array!
 * - Binary search from 0 to (rows × cols - 1)
 * - Convert index to row/col: row = index / cols, col = index % cols
 * - Time: O(log(m × n)), Space: O(1)
 * 
 * ============================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================
 * 
 * ❌ MISTAKE 1: Using binary search on unsorted data
 * ✅ FIX: Always verify data is sorted first!
 * 
 * ❌ MISTAKE 2: Infinite loop due to wrong midpoint calculation
 * ✅ FIX: When going right, use start = mid + 1 (not start = mid)
 * 
 * ❌ MISTAKE 3: Integer overflow in midpoint calculation
 * ✅ FIX: Use start + (end - start) / 2 instead of (start + end) / 2
 * 
 * ❌ MISTAKE 4: Off-by-one errors with boundaries
 * ✅ FIX: Be consistent - either [start, end) or [start, end]
 *         Stick with one convention!
 * 
 * ❌ MISTAKE 5: Not handling edge cases
 * ✅ FIX: Test with empty array, single element, target not found
 * 
 * ============================================================
 * BINARY SEARCH VARIATIONS (Good to Know!)
 * ============================================================
 * 
 * 1. LOWER BOUND: Find first element >= target
 * 2. UPPER BOUND: Find first element > target
 * 3. EXACT MATCH: Find element == target (what we implemented)
 * 4. CLOSEST VALUE: Find element with minimum absolute difference
 * 5. COUNT OCCURRENCES: Use lower/upper bound to count
 * 
 * ============================================================
 * WHEN TO USE BINARY SEARCH (Interview Tip!)
 * ============================================================
 * 
 * Look for these CLUES in the problem statement:
 * ✓ "Sorted array"
 * ✓ "Find in O(log n)"
 * ✓ "Minimize/maximize something"
 * ✓ "Find boundary/threshold"
 * ✓ Array has some monotonic property
 * 
 * If you see these, think: "Can I use binary search?"
 * 
 * ============================================================
 * PRACTICE MAKES PERFECT!
 * ============================================================
 * 
 * LeetCode Problems to Practice:
 * - Easy: 704 (Binary Search), 35 (Search Insert Position)
 * - Medium: 33 (Search Rotated Array), 34 (Find First and Last)
 * - Hard: 4 (Median of Two Sorted Arrays)
 * 
 * Remember: Binary search is one of the most important algorithms
 * in computer science. Master it, and you'll ace many interview questions!
 * 
 * Good luck! 🎯
 */
