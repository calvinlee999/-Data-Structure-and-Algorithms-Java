package academy.learnprogramming.hashtableschallenge;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * HASHTABLE CHALLENGE #1: IMPLEMENT A SIMPLE HASH FUNCTION
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Implement a simple hash function that maps integer values to array indices.
 * The hash function should convert any integer into a valid index for an
 * array of size 10.
 * 
 * WHAT IS A HASH FUNCTION?
 * ------------------------
 * A hash function is like a magic formula that:
 * 1. Takes in data (like a number, string, or object)
 * 2. Converts it to an array index (0 to array.length-1)
 * 3. Always gives the SAME index for the SAME input
 * 
 * Think of it like organizing books in a library:
 * - Instead of searching through every book, we use a system
 * - The hash function tells us exactly which shelf to check
 * - This makes finding things SUPER fast!
 * 
 * WHAT IS A HASHTABLE?
 * --------------------
 * A hashtable (also called hash map) is a data structure that:
 * - Stores key-value pairs
 * - Uses a hash function to determine where to store each item
 * - Provides O(1) average time for insert, delete, and search!
 * 
 * WHY IS THIS CHALLENGE IMPORTANT?
 * --------------------------------
 * - Foundation of many data structures (HashMap, HashSet, Dictionary)
 * - Used everywhere: databases, caches, password storage
 * - Interview favorite: Tests understanding of hashing concepts
 * - Real-world use: Fast lookups in large datasets
 * 
 * OUR HASH FUNCTION:
 * -----------------
 * hash(value) = |value| % 10
 * 
 * Breaking it down:
 * - % 10: Modulo operation gives remainder when dividing by 10
 * - |value|: Absolute value handles negative numbers
 * - Result: Always between 0 and 9 (perfect for array of size 10!)
 * 
 * Examples:
 * - hash(43) = 43 % 10 = 3
 * - hash(6894) = 6894 % 10 = 4
 * - hash(-58) = |-58| % 10 = 58 % 10 = 8
 * 
 * TIME COMPLEXITY: O(1)
 * -------------------
 * - The hash function does simple math operations
 * - Doesn't depend on the size of the array or number of elements
 * - Always takes the same amount of time
 * 
 * SPACE COMPLEXITY: O(1)
 * --------------------
 * - No extra space used besides the array itself
 * - Hash function doesn't create any additional data structures
 * 
 * LIMITATIONS OF THIS SIMPLE HASH FUNCTION:
 * ----------------------------------------
 * 1. COLLISIONS: Multiple values can hash to the same index!
 *    - Example: 43, 53, 63 all hash to index 3
 *    - Real hashtables handle this with chaining or open addressing
 * 
 * 2. SMALL ARRAY: Only 10 slots means lots of collisions
 *    - Larger arrays reduce collisions but use more memory
 * 
 * 3. DISTRIBUTION: Not all indices used equally
 *    - Numbers ending in same digit go to same index
 *    - Better hash functions distribute more evenly
 * 
 * COMMON EDGE CASES:
 * -----------------
 * ✓ Negative numbers → use absolute value
 * ✓ Zero → hashes to index 0
 * ✓ Numbers larger than array size → modulo handles it
 * ✓ Collision (multiple values to same index) → overwrites in this simple version
 * 
 * BETTER HASH FUNCTIONS:
 * ---------------------
 * 1. Prime number modulo: hash(x) = |x| % 31
 *    - Prime numbers reduce collisions
 * 
 * 2. Multiplication method: hash(x) = floor(m * (x * A % 1))
 *    - Better distribution of values
 * 
 * 3. Universal hashing: Use random coefficients
 *    - Prevents malicious collision attacks
 * 
 * ALTERNATIVE APPROACHES:
 * ----------------------
 * Instead of simple modulo, we could:
 * 1. Use multiple hash functions (double hashing)
 * 2. Use cryptographic hash (SHA-256) - slower but more secure
 * 3. Use string representation and sum character codes
 * 
 * RELATED INTERVIEW QUESTIONS:
 * ---------------------------
 * - "Design a HashMap from scratch"
 * - "Handle collisions in a hashtable using chaining"
 * - "Implement LRU cache using HashMap"
 * - "Find first non-repeating character using a HashMap"
 * - "Group anagrams using a HashMap"
 * - "Two Sum problem using HashMap"
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 */

public class Main {

    public static void main(String[] args) {

        // STEP 1: Create an array to act as our hashtable
        // This array has 10 slots (indices 0-9)
        int[] nums = new int[10];
        
        // STEP 2: Create some test values to hash
        // Notice the variety: positive, negative, large, small
        int[] numsToAdd = { 59382, 43, 6894, 500, 99, -58 };
        
        // STEP 3: Hash each value and store it in the array
        // For each number, we:
        // 1. Calculate its hash (which gives us an index 0-9)
        // 2. Store the number at that index in our array
        for (int i = 0; i < numsToAdd.length; i++) {
            nums[hash(numsToAdd[i])] = numsToAdd[i];
        }
        
        // Let's trace through what happens:
        // hash(59382) = 59382 % 10 = 2 → nums[2] = 59382
        // hash(43) = 43 % 10 = 3 → nums[3] = 43
        // hash(6894) = 6894 % 10 = 4 → nums[4] = 6894
        // hash(500) = 500 % 10 = 0 → nums[0] = 500
        // hash(99) = 99 % 10 = 9 → nums[9] = 99
        // hash(-58) = 58 % 10 = 8 → nums[8] = -58
        
        // STEP 4: Display the hashtable
        // Notice: Some indices will be 0 (default value) because
        // no number hashed to those indices
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
        
        // OUTPUT EXPLANATION:
        // Index 0: 500 (hash(500) = 0)
        // Index 1: 0 (nothing hashed here)
        // Index 2: 59382 (hash(59382) = 2)
        // Index 3: 43 (hash(43) = 3)
        // Index 4: 6894 (hash(6894) = 4)
        // Index 5-7: 0 (nothing hashed here)
        // Index 8: -58 (hash(-58) = 8)
        // Index 9: 99 (hash(99) = 9)
    }

    /*
     * HASH FUNCTION IMPLEMENTATION
     * -----------------------------
     * This is our simple hash function that converts any integer
     * into a valid array index (0-9).
     * 
     * @param value - The integer to hash
     * @return An index between 0 and 9
     */
    public static int hash(int value) {
        // Math.abs() ensures we handle negative numbers correctly
        // % 10 gives us the remainder when dividing by 10
        // Result is always 0, 1, 2, 3, 4, 5, 6, 7, 8, or 9
        return Math.abs(value  % 10);

    }
}
