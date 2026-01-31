package com.company.hashtable;


import java.util.*;

/**
 * ========================================================================
 * BUCKET SORT - Using Hash Tables for Sorting!
 * ========================================================================
 * 
 * This program demonstrates BUCKET SORT - a sorting algorithm that uses
 * the hash table concept to sort numbers efficiently!
 * 
 * WHAT IS BUCKET SORT?
 * --------------------
 * Bucket sort distributes items into "buckets" (like hash table buckets),
 * sorts each bucket, then combines them back together.
 * 
 * Think of it like:
 * 1. SORTING MAIL: Put letters into bins by zip code, sort each bin,
 *    then collect them in order
 * 2. ORGANIZING CARDS: Separate cards by suit, sort each suit, then
 *    combine them
 * 3. GRADING TESTS: Put tests into piles by grade (A, B, C...), organize
 *    each pile, then stack them
 * 
 * HOW IT WORKS:
 * -------------
 * 1. SCATTERING: Distribute items into buckets using a hash function
 * 2. SORTING: Sort each bucket individually
 * 3. GATHERING: Collect items from buckets back into the array
 * 
 * CONNECTION TO HASH TABLES:
 * --------------------------
 * - Each bucket is like a hash table slot
 * - The hash function determines which bucket
 * - Instead of key-value pairs, we store just numbers
 * - Instead of retrieving, we sort and collect
 * 
 * WHEN IS BUCKET SORT USEFUL?
 * ----------------------------
 * ✓ Numbers are UNIFORMLY DISTRIBUTED (spread evenly)
 * ✓ Know the RANGE of values (e.g., 0-99)
 * ✓ Have ENOUGH MEMORY for buckets
 * ✓ Numbers are INTEGERS or can be converted to integers
 * 
 * PERFORMANCE:
 * ------------
 * - Best Case: O(n + k) where n=items, k=buckets
 * - Average Case: O(n + k)
 * - Worst Case: O(n²) if all items go to one bucket
 * - Space: O(n + k) for buckets and items
 * 
 * COMPARISON WITH OTHER SORTS:
 * ----------------------------
 * Bucket Sort: O(n) average (when buckets distributed well)
 * Quick Sort: O(n log n) average
 * Merge Sort: O(n log n) always
 * Bubble Sort: O(n²) average
 * 
 * Bucket sort can be FASTER than comparison-based sorts!
 * But only works well with specific data distributions.
 * 
 * @author Data Structures Learning Project
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {

        // Sample array of integers to sort
        // Notice these are 2-digit numbers (10-99 range)
        int[] intArray = {54,46,83,66,95,92,43};

        // Sort the array using bucket sort
        bucketSort(intArray);

        // Print the sorted result
        for(int i =0;i<intArray.length;i++){
            System.out.println(intArray[i]);
        }
        // Expected output (sorted):
        // 43, 46, 54, 66, 83, 92, 95

    }


    /**
     * BUCKET SORT IMPLEMENTATION
     * ==========================
     * 
     * Sorts an array of integers using the bucket sort algorithm.
     * 
     * ALGORITHM STEPS:
     * ----------------
     * 1. Create buckets (one for each possible hash value)
     * 2. SCATTER: Distribute items into buckets using hash function
     * 3. SORT: Sort each bucket individually
     * 4. GATHER: Collect items from buckets back into original array
     * 
     * DETAILED EXAMPLE:
     * -----------------
     * Input: [54, 46, 83, 66, 95, 92, 43]
     * 
     * Step 1: Create 10 buckets (indices 0-9)
     * bucket[0] = []
     * bucket[1] = []
     * ...
     * bucket[9] = []
     * 
     * Step 2: SCATTER using hash function (tens digit)
     * - hash(54) = 5 → bucket[5] = [54]
     * - hash(46) = 4 → bucket[4] = [46]
     * - hash(83) = 8 → bucket[8] = [83]
     * - hash(66) = 6 → bucket[6] = [66]
     * - hash(95) = 9 → bucket[9] = [95]
     * - hash(92) = 9 → bucket[9] = [95, 92]
     * - hash(43) = 4 → bucket[4] = [46, 43]
     * 
     * Result after scattering:
     * bucket[0] = []
     * bucket[1] = []
     * bucket[2] = []
     * bucket[3] = []
     * bucket[4] = [46, 43]
     * bucket[5] = [54]
     * bucket[6] = [66]
     * bucket[7] = []
     * bucket[8] = [83]
     * bucket[9] = [95, 92]
     * 
     * Step 3: SORT each bucket
     * bucket[4] = [43, 46]  (sorted)
     * bucket[5] = [54]      (already sorted, only 1 item)
     * bucket[6] = [66]      (already sorted)
     * bucket[8] = [83]      (already sorted)
     * bucket[9] = [92, 95]  (sorted)
     * 
     * Step 4: GATHER from buckets in order
     * Collect from bucket[0]: nothing
     * Collect from bucket[1]: nothing
     * Collect from bucket[2]: nothing
     * Collect from bucket[3]: nothing
     * Collect from bucket[4]: 43, 46
     * Collect from bucket[5]: 54
     * Collect from bucket[6]: 66
     * Collect from bucket[7]: nothing
     * Collect from bucket[8]: 83
     * Collect from bucket[9]: 92, 95
     * 
     * Final sorted array: [43, 46, 54, 66, 83, 92, 95]
     * 
     * @param input The array to sort (modified in place)
     * 
     * TIME COMPLEXITY:
     * - Scattering: O(n) - process each item once
     * - Sorting buckets: O(n log(n/k)) where k = number of buckets
     *   - If items distributed evenly: O(n)
     *   - If all in one bucket: O(n log n)
     * - Gathering: O(n + k) - visit all buckets and items
     * - Overall: O(n + k) average case
     * 
     * SPACE COMPLEXITY: O(n + k)
     * - k buckets (ArrayList objects)
     * - n items stored across buckets
     */
    public static void bucketSort(int[] input){
        // Note: This uses List interface - can hold any list type!
        // List is the parent interface of ArrayList, LinkedList, Vector, etc.
        // This makes the code flexible!
        
        // ===================================================================
        // STEP 1: CREATE BUCKETS
        // ===================================================================
        // Create an array of 10 lists (one for each digit 0-9)
        // We use List interface so we can choose implementation later
        
        List<Integer>[] buckets = new List[10];
        
        // Initialize each bucket as an ArrayList
        // ArrayList is chosen because:
        // - Fast random access for sorting
        // - Dynamic size (can grow as needed)
        // - Good for sequential access during gathering
        for(int i =0;i<buckets.length;i++){
            buckets[i] = new ArrayList<Integer>();  // Create empty ArrayList
            
            // ALTERNATIVES (commented out):
            // You could use LinkedList instead:
            // buckets[i] = new LinkedList<Integer>();
            // Better for frequent insertions, but slower to sort
            
            // Or Vector (thread-safe but slower):
            // buckets[i] = new Vector<Integer>();
            // Use if multiple threads access same bucket
        }

        // ===================================================================
        // STEP 2: SCATTERING - Distribute items into buckets
        // ===================================================================
        // Use the hash function to determine which bucket for each item
        
        for(int i =0;i<input.length;i++){
            // hash(input[i]) returns the bucket index (0-9)
            // add() appends the item to that bucket's list
            buckets[hash(input[i])].add(input[i]);
        }
        
        // Example iteration:
        // i=0: input[0]=54, hash(54)=5, buckets[5].add(54)
        // i=1: input[1]=46, hash(46)=4, buckets[4].add(46)
        // i=2: input[2]=83, hash(83)=8, buckets[8].add(83)
        // ... and so on
        
        // ===================================================================
        // STEP 3: SORTING - Sort each bucket individually
        // ===================================================================
        // Collections.sort() uses an optimized sorting algorithm
        // (Timsort - combination of merge sort and insertion sort)
        
        for(int i =0;i<buckets.length;i++){
            Collections.sort(buckets[i]);
        }
        
        // This sorts each bucket in place:
        // buckets[4]: [46, 43] → [43, 46]
        // buckets[9]: [95, 92] → [92, 95]
        
        // WHY SORT SMALL BUCKETS?
        // - If items distributed evenly, each bucket has ~n/k items
        // - Sorting small lists is very fast!
        // - Example: 100 items, 10 buckets = 10 items per bucket
        //   - Sorting 10 items: ~10 log 10 = ~33 comparisons
        //   - Doing this 10 times: ~330 comparisons
        //   - vs sorting all 100: ~100 log 100 = ~664 comparisons
        //   - Bucket sort is ~2x faster!
        
        // ===================================================================
        // STEP 4: GATHERING - Collect items back into original array
        // ===================================================================
        // Walk through buckets in order, collecting items
        
        int j =0;  // Index for writing back to input array
        
        // For each bucket (in order 0, 1, 2, ..., 9)
        for(int i =0;i<buckets.length;i++){
            // For each item in this bucket
            for(int value: buckets[i]){  // Enhanced for loop
                input[j++] = value;  // Place item in array, increment index
            }
        }
        
        // Example gathering:
        // bucket[0]: empty → skip
        // bucket[1]: empty → skip
        // bucket[2]: empty → skip
        // bucket[3]: empty → skip
        // bucket[4]: [43, 46] → input[0]=43, input[1]=46, j=2
        // bucket[5]: [54] → input[2]=54, j=3
        // bucket[6]: [66] → input[3]=66, j=4
        // bucket[7]: empty → skip
        // bucket[8]: [83] → input[4]=83, j=5
        // bucket[9]: [92, 95] → input[5]=92, input[6]=95, j=7
        // 
        // Final: input = [43, 46, 54, 66, 83, 92, 95] ✓
    }

    /**
     * HASH FUNCTION - Determines Which Bucket
     * ========================================
     * 
     * This hash function extracts the TENS DIGIT of a number.
     * This works like the Most Significant Digit (MSD) in radix sort.
     * 
     * HOW IT WORKS:
     * -------------
     * For 2-digit numbers (10-99):
     * 1. Divide by 10: Gets the tens digit
     * 2. Mod by 10: Ensures result is 0-9
     * 
     * EXAMPLES:
     * ---------
     * hash(54):
     *   54 / 10 = 5 (integer division drops decimal)
     *   5 % 10 = 5
     *   Result: 5 (bucket 5)
     * 
     * hash(95):
     *   95 / 10 = 9
     *   9 % 10 = 9
     *   Result: 9 (bucket 9)
     * 
     * hash(43):
     *   43 / 10 = 4
     *   4 % 10 = 4
     *   Result: 4 (bucket 4)
     * 
     * hash(7):
     *   7 / 10 = 0 (less than 10)
     *   0 % 10 = 0
     *   Result: 0 (bucket 0)
     * 
     * WHY THIS HASH FUNCTION?
     * -----------------------
     * - Simple and fast
     * - Distributes 2-digit numbers evenly across 10 buckets
     * - Numbers with same tens digit go to same bucket
     * - After sorting buckets, items are in correct order!
     * 
     * LIMITATIONS:
     * ------------
     * - Only works well for 2-digit numbers
     * - 3-digit numbers (100-999) all hash to 0-9 (lots of collisions!)
     * - Negative numbers need special handling
     * 
     * ALTERNATIVE HASH FUNCTIONS:
     * ---------------------------
     * For floating point (0.0 to 1.0):
     * return (int)(value * 10);  // Multiply by bucket count
     * 
     * For any range (min to max):
     * return (int)((value - min) / (max - min) * bucketCount);
     * 
     * @param value The integer to hash
     * @return A bucket index from 0 to 9
     * 
     * TIME COMPLEXITY: O(1) - Just division and modulo
     */
    private static int hash(int value){
        return value /(int)10%10;  // Extract tens digit
        
        // Breakdown:
        // value / 10: Integer division, gets tens digit
        // (int)10: Cast to int (technically unnecessary in Java)
        // % 10: Modulo 10, keeps result in 0-9 range
    }
}

/**
 * ==========================================
 * INTERVIEW QUESTIONS - Bucket Sort
 * ==========================================
 * 
 * Q1: When is bucket sort better than quicksort or mergesort?
 * A1: Bucket sort can be FASTER when:
 *     
 *     CONDITIONS:
 *     1. Items are UNIFORMLY DISTRIBUTED
 *        - Numbers spread evenly across range
 *        - Not clustered in one area
 *     
 *     2. Know the VALUE RANGE
 *        - Example: test scores (0-100)
 *        - Example: ages (0-120)
 *        - Can create appropriate number of buckets
 *     
 *     3. Have ENOUGH MEMORY
 *        - Need space for buckets + items
 *        - Not a problem for modern computers
 *     
 *     EXAMPLE COMPARISON:
 *     Sorting 1,000,000 uniformly distributed integers (0-999):
 *     
 *     Bucket Sort with 1000 buckets:
 *     - Scatter: 1,000,000 operations
 *     - Sort: 1000 buckets × ~1000 items × log(1000) ≈ 10,000,000 ops
 *     - Gather: 1,000,000 operations
 *     - Total: ~12,000,000 operations
 *     
 *     QuickSort:
 *     - O(n log n) = 1,000,000 × log(1,000,000) ≈ 20,000,000 ops
 *     
 *     Bucket sort is ~40% faster!
 *     
 *     But with POOR DISTRIBUTION (all items hash to one bucket):
 *     - Bucket sort degrades to O(n²)
 *     - QuickSort is better!
 * 
 * Q2: How would you modify bucket sort to handle negative numbers?
 * A2: SOLUTION 1: Offset the values
 *     
 *     If range is -50 to 50:
 *     1. Find min value (-50)
 *     2. Add offset to make all positive: value - min
 *     3. Hash the offset value: hash(value - min)
 *     4. After sorting, no adjustment needed
 *     
 *     Example:
 *     private static int hash(int value) {
 *         int min = -50;  // Known minimum
 *         int offsetValue = value - min;  // Now 0 to 100
 *         return offsetValue / 10;  // Bucket 0-10
 *     }
 *     
 *     Values: -50, -25, 0, 25, 50
 *     Offset: 0, 25, 50, 75, 100
 *     Buckets: 0, 2, 5, 7, 10
 *     
 *     SOLUTION 2: Separate positive and negative buckets
 *     
 *     Create 2 sets of buckets:
 *     - Negative buckets: 0-9 for -90 to -10, -9 to -1
 *     - Positive buckets: 10-19 for 0-9, 10-99
 *     
 *     Sort negative buckets in reverse, then positive buckets
 * 
 * Q3: Compare bucket sort with radix sort.
 * A3: BUCKET SORT vs RADIX SORT:
 *     
 *     BUCKET SORT:
 *     - Uses hash function to distribute items
 *     - Sorts each bucket independently
 *     - Single pass of distribution
 *     - Works well with uniform distribution
 *     
 *     Example: [54, 46, 83, 66]
 *     - Bucket by tens digit: bucket[4]=[46], bucket[5]=[54], ...
 *     - Sort each bucket
 *     - Gather
 *     
 *     RADIX SORT:
 *     - Sorts digit-by-digit (ones, tens, hundreds, ...)
 *     - Distributes items multiple times
 *     - Multiple passes (one per digit)
 *     - Works with any distribution
 *     
 *     Example: [54, 46, 83, 66]
 *     Pass 1 (ones): [83, 54, 46, 66]  (sorted by 3,4,6,6)
 *     Pass 2 (tens): [46, 54, 66, 83]  (sorted by 4,5,6,8)
 *     
 *     SIMILARITIES:
 *     - Both use bucketing concept
 *     - Both can be O(n) average case
 *     - Both work best with integers
 *     - Both are non-comparison sorts
 *     
 *     DIFFERENCES:
 *     - Bucket sort: 1 distribution pass + sorting
 *     - Radix sort: Multiple distribution passes (d passes for d digits)
 *     
 *     WHEN TO USE:
 *     - Bucket sort: Numbers uniformly distributed, known range
 *     - Radix sort: Any distribution, fixed-length keys (like integers)
 * 
 * Q4: What's the worst-case scenario for bucket sort?
 * A4: WORST CASE: All items hash to ONE bucket
 *     
 *     Example with our hash function:
 *     Input: [50, 51, 52, 53, 54, 55, 56, 57, 58, 59]
 *     All have tens digit = 5
 *     All hash to bucket[5]!
 *     
 *     Result:
 *     bucket[0] = []
 *     bucket[1] = []
 *     ...
 *     bucket[4] = []
 *     bucket[5] = [50, 51, 52, 53, 54, 55, 56, 57, 58, 59]
 *     bucket[6] = []
 *     ...
 *     bucket[9] = []
 *     
 *     Now we must sort the entire array in one bucket!
 *     - Collections.sort() uses Timsort: O(n log n)
 *     - No benefit from bucketing
 *     - Wasted time creating buckets!
 *     
 *     PERFORMANCE:
 *     - Scattering: O(n)
 *     - Sorting one bucket: O(n log n)
 *     - Gathering: O(n)
 *     - Total: O(n log n) - same as quicksort!
 *     
 *     Even worse: If sort algorithm is bubble sort: O(n²)!
 *     
 *     PREVENTION:
 *     1. Choose good hash function that distributes items evenly
 *     2. Use more buckets (reduce items per bucket)
 *     3. Check distribution before sorting
 *     4. Switch to quicksort if too many items in one bucket
 * 
 * Q5: How is bucket sort related to hash tables?
 * A5: DEEP CONNECTION:
 *     
 *     HASH TABLES:
 *     - Array of buckets
 *     - Hash function maps keys to buckets
 *     - Handle collisions (multiple keys per bucket)
 *     - Purpose: Fast lookup (O(1))
 *     
 *     BUCKET SORT:
 *     - Array of buckets
 *     - Hash function maps values to buckets
 *     - Allow collisions (multiple values per bucket)
 *     - Purpose: Efficient sorting (O(n))
 *     
 *     KEY SIMILARITIES:
 *     1. Both use BUCKETS (array of lists)
 *     2. Both use HASH FUNCTION to distribute items
 *     3. Both allow MULTIPLE ITEMS per bucket
 *     4. Both use List/LinkedList for bucket storage
 *     
 *     EXAMPLE:
 *     Hash table for employees:
 *     buckets[hash(\"Jones\")] = [janeEmployee]
 *     buckets[hash(\"Smith\")] = [maryEmployee]
 *     
 *     Bucket sort for numbers:
 *     buckets[hash(54)] = [54, 57, 52]
 *     buckets[hash(43)] = [43, 46, 49]
 *     
 *     DIFFERENCES:
 *     Hash table:
 *     - Stores key-value pairs
 *     - Searches for specific keys
 *     - Items stay in buckets
 *     - Random access pattern
 *     
 *     Bucket sort:
 *     - Stores just values
 *     - Sorts all values
 *     - Items returned to array
 *     - Sequential collection
 *     
 *     LESSON:
 *     Bucket sort applies hash table concepts to sorting!
 *     Both leverage the power of hashing to organize data efficiently.
 * 
 * ==========================================
 * COMPLEXITY ANALYSIS - Bucket Sort
 * ==========================================
 * 
 * BEST CASE: O(n + k)
 * - Items distributed evenly across k buckets
 * - Each bucket has ~n/k items
 * - Sorting k buckets of n/k items: k × (n/k) log(n/k) ≈ n log(n/k)
 * - If k ≈ n, then log(n/k) ≈ log(1) = 0, so O(n)!
 * 
 * AVERAGE CASE: O(n + k)
 * - Assumes uniform distribution
 * - Most practical scenarios fall here
 * 
 * WORST CASE: O(n²)
 * - All items in one bucket
 * - Sorting one bucket: O(n log n) with good sort, O(n²) with bubble sort
 * 
 * SPACE COMPLEXITY: O(n + k)
 * - k buckets (ArrayList objects)
 * - n items distributed across buckets
 * - Temporary storage, returns sorted array in place
 * 
 * ==========================================
 * WHEN TO USE BUCKET SORT
 * ==========================================
 * 
 * GOOD FOR:
 * ✓ Uniformly distributed data
 * ✓ Known range of values
 * ✓ Integers or easily bucketed data
 * ✓ Large datasets with good distribution
 * 
 * BAD FOR:
 * ✗ Clustered data (many items hash to same bucket)
 * ✗ Unknown or unbounded value range
 * ✗ Small datasets (overhead not worth it)
 * ✗ Limited memory (need space for buckets)
 * 
 * ALTERNATIVES:
 * - QuickSort: General purpose, O(n log n) average
 * - MergeSort: Stable, O(n log n) guaranteed
 * - Radix Sort: Multiple passes, O(d×n) where d=digits
 * - Counting Sort: For limited range integers, O(n+k)
 */
