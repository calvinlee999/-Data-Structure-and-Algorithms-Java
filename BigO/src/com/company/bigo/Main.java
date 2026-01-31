package com.company.bigo;

import java.util.Arrays;
//import java.util.HashMap;
import java.util.Map;

/**
 * Big O Notation Practice Examples - A Student's Guide
 * 
 * WHAT IS BIG O?
 * Big O notation tells us how fast an algorithm runs as the input gets bigger.
 * Think of it like describing how long it takes to find a book:
 * - O(1): You know exactly where it is (constant time)
 * - O(log n): You use the library system to narrow down the location (logarithmic)
 * - O(n): You check each shelf one by one (linear time)
 * - O(n²): You compare every book with every other book (quadratic time)
 * 
 * WHY DOES IT MATTER?
 * When you have a small amount of data, any algorithm works fine.
 * But with millions of users or items, a bad algorithm can take hours or crash!
 * A good algorithm might finish in seconds.
 * 
 * INTERVIEW TIP:
 * Always analyze your solution's Big O complexity when solving coding problems!
 * Companies like Google, Amazon, and Microsoft care about efficient code.
 * 
 * @author Data Structures Learning Lab
 * @version 2.0
 */
public class Main {

    public static void main(String[] args) {
        Main bigO = new Main();
        
        System.out.println("=== BIG O NOTATION PRACTICE ===\n");
        
        // O(1) - Constant Time
        System.out.println("1. O(1) - Constant Time:");
        int[] array = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        System.out.println("   First element: " + bigO.getFirstElement(array));
        System.out.println("   Array length: " + bigO.getArrayLength(array));
        System.out.println();
        
        // O(log n) - Logarithmic Time
        System.out.println("2. O(log n) - Logarithmic Time:");
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int target = 7;
        int index = bigO.binarySearch(sortedArray, target);
        System.out.println("   Binary Search for " + target + ": found at index " + index);
        System.out.println();
        
        // O(n) - Linear Time
        System.out.println("3. O(n) - Linear Time:");
        System.out.println("   Sum of array: " + bigO.sumArray(array));
        System.out.println("   Max element: " + bigO.findMax(array));
        System.out.println();
        
        // O(n log n) - Linearithmic Time
        System.out.println("4. O(n log n) - Linearithmic Time:");
        int[] unsorted = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("   Before Merge Sort: " + Arrays.toString(unsorted));
        int[] sorted = bigO.mergeSort(unsorted);
        System.out.println("   After Merge Sort:  " + Arrays.toString(sorted));
        System.out.println();
        
        // O(n²) - Quadratic Time
        System.out.println("5. O(n²) - Quadratic Time:");
        int[] bubbleArray = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("   Before Bubble Sort: " + Arrays.toString(bubbleArray));
        bigO.bubbleSort(bubbleArray);
        System.out.println("   After Bubble Sort:  " + Arrays.toString(bubbleArray));
        System.out.println();
        
        // O(2^n) - Exponential Time
        System.out.println("6. O(2^n) - Exponential Time:");
        int n = 10;
        System.out.println("   Fibonacci(" + n + ") = " + bigO.fibonacci(n));
        System.out.println();
        
        // Comparison
        System.out.println("=== PERFORMANCE COMPARISON (n=1000) ===");
        bigO.demonstrateComplexities();
    }

    // ==================== O(1) - Constant Time ====================
    /**
     * O(1) - CONSTANT TIME
     * 
     * What it means: Takes the same amount of time no matter how big the array is.
     * Real-world example: Getting the first person in line - always takes one step!
     * 
     * Why O(1)? We're just accessing one spot in memory. Whether the array has
     * 10 items or 10 million items, accessing array[0] is always one operation.
     * 
     * Interview Question: "Why is accessing an array element O(1)?"
     * Answer: Arrays store elements in continuous memory locations. The computer
     * can calculate exactly where any element is using: address = start + (index × size)
     * 
     * @param array The input array
     * @return The first element, or -1 if array is empty
     */
    public int getFirstElement(int[] array) {
        // Always check for null or empty arrays to avoid crashes!
        if (array == null || array.length == 0) {
            return -1;
        }
        return array[0];  // Single operation, always O(1)
    }
    
    /**
     * Getting the length of an array is O(1) because the length is stored
     * as a property, not calculated each time.
     */
    public int getArrayLength(int[] array) {
        return array.length;  // Single operation, O(1)
    }
    
    /**
     * Adding to a HashMap is usually O(1) on average.
     * The hash function quickly calculates where to store the item.
     */
    public void addToHashMap(Map<String, Integer> map, String key, int value) {
        map.put(key, value);  // Average O(1)
    }

    // ==================== O(log n) - Logarithmic Time ====================
    /**
     * O(log n) - LOGARITHMIC TIME (Binary Search)
     * 
     * What it means: The time grows slowly as input grows. When you double the data,
     * you only add one more step!
     * 
     * Real-world example: Finding a word in a dictionary. You open to the middle,
     * see if your word comes before or after, then repeat with half the remaining pages.
     * 
     * Why O(log n)? Each step cuts the problem in HALF!
     * - Array of 1000 items: ~10 steps (2^10 = 1024)
     * - Array of 1,000,000 items: ~20 steps (2^20 = 1,048,576)
     * 
     * IMPORTANT: The array MUST be sorted for binary search to work!
     * 
     * Interview Questions:
     * Q: "What if the array isn't sorted?"
     * A: Binary search won't work correctly. You'd need to sort it first O(n log n)
     *    or use linear search O(n).
     * 
     * Q: "Can binary search work on a linked list?"
     * A: Not efficiently! You can't jump to the middle of a linked list in O(1).
     *    You'd have to traverse, making it O(n) to find the middle.
     * 
     * @param sortedArray An array sorted in ascending order
     * @param target The value we're searching for
     * @return The index where target is found, or -1 if not found
     */
    public int binarySearch(int[] sortedArray, int target) {
        int left = 0;  // Start of search range
        int right = sortedArray.length - 1;  // End of search range
        
        // Keep searching while there's a valid range
        while (left <= right) {
            // Find the middle index (using this formula prevents integer overflow!)
            int mid = left + (right - left) / 2;
            
            // Found it! Return the index
            if (sortedArray[mid] == target) {
                return mid;
            } 
            // Target is in the right half, so ignore left half
            else if (sortedArray[mid] < target) {
                left = mid + 1;
            } 
            // Target is in the left half, so ignore right half
            else {
                right = mid - 1;
            }
        }
        
        return -1;  // Not found after checking all possibilities
    }

    // ==================== O(n) - Linear Time ====================
    /**
     * O(n) - LINEAR TIME
     * 
     * What it means: Time grows directly with input size. Double the data = double the time.
     * Real-world example: Reading every page in a book to count words.
     * 
     * Why O(n)? We must look at each element once. Can't skip any to calculate the sum!
     * 
     * Interview Question: "Can you sum an array faster than O(n)?"
     * Answer: No! You must look at every element at least once to include it in the sum.
     * This is the best possible time complexity for this problem.
     * 
     * @param array The input array of numbers
     * @return The sum of all elements
     */
    public int sumArray(int[] array) {
        int sum = 0;
        // Visit each element exactly once - that's why it's O(n)
        for (int num : array) {
            sum += num;
        }
        return sum;
    }
    
    /**
     * Finding the maximum also requires checking every element - O(n)
     * 
     * Interview Tip: Even though we might find the max early and return,
     * Big O measures WORST CASE. The max could be the last element!
     */
    public int findMax(int[] array) {
        if (array == null || array.length == 0) {
            return Integer.MIN_VALUE;
        }
        
        int max = array[0];  // Start with first element as max
        // Check each remaining element
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];  // Found a new maximum!
            }
        }
        return max;
    }
    
    /**
     * Linear Search - O(n)
     * 
     * Unlike binary search which is O(log n), linear search doesn't require
     * the array to be sorted, but it's slower on large datasets.
     * 
     * Trade-off: Binary search is faster (O(log n)) BUT requires sorted data.
     * Linear search is slower (O(n)) BUT works on unsorted data.
     */
    public boolean linearSearch(int[] array, int target) {
        // Check each element until we find it or run out of elements
        for (int num : array) {
            if (num == target) {
                return true;  // Found it!
            }
        }
        return false;  // Checked everything, not here
    }

    // ==================== O(n log n) - Linearithmic Time ====================
    /**
     * O(n log n) - LINEARITHMIC TIME (Merge Sort)
     * 
     * What it means: Faster than O(n²) but slower than O(n). The sweet spot for sorting!
     * Real-world example: Organizing a deck of cards by splitting into piles,
     * sorting each pile, then merging them back together.
     * 
     * Why O(n log n)?
     * - We divide the array in half repeatedly: log n levels (like a tree)
     * - At each level, we process all n elements (merging)
     * - Total: n × log n operations
     * 
     * Fun Fact: This is the best possible time complexity for comparison-based sorting!
     * You CAN'T sort by comparing elements faster than O(n log n) in the worst case.
     * 
     * Interview Questions:
     * Q: "Why use merge sort instead of bubble sort?"
     * A: Merge sort is O(n log n), bubble sort is O(n²). For 1000 items:
     *    - Merge sort: ~10,000 operations
     *    - Bubble sort: ~1,000,000 operations (100x slower!)
     * 
     * Q: "What's the space complexity of merge sort?"
     * A: O(n) - we need extra space for the temporary arrays while merging.
     * 
     * @param array The unsorted array
     * @return A new sorted array
     */
    public int[] mergeSort(int[] array) {
        // Base case: arrays with 0 or 1 element are already sorted!
        if (array.length <= 1) {
            return array;
        }
        
        // Divide: Split array into two halves
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        
        // Conquer: Recursively sort both halves, then merge them
        return merge(mergeSort(left), mergeSort(right));
    }
    
    /**
     * Helper method to merge two sorted arrays into one sorted array.
     * This is the "merge" part of merge sort.
     * 
     * @param left First sorted array
     * @param right Second sorted array
     * @return Combined sorted array
     */
    private int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;  // Pointers for left, right, and result arrays
        
        // Compare elements from left and right, add smaller one to result
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        
        // Copy any remaining elements from left array
        while (i < left.length) {
            result[k++] = left[i++];
        }
        
        // Copy any remaining elements from right array
        while (j < right.length) {
            result[k++] = right[j++];
        }
        
        return result;
    }

    // ==================== O(n²) - Quadratic Time ====================
    /**
     * O(n²) - QUADRATIC TIME (Bubble Sort)
     * 
     * What it means: Time grows VERY fast! Double the data = 4x the time!
     * Real-world example: Comparing every student's height with every other student's height.
     * 
     * Why O(n²)? TWO nested loops, each running n times.
     * - Outer loop runs n times
     * - For each outer loop, inner loop runs n times
     * - Total: n × n = n² operations
     * 
     * Warning: This gets slow FAST!
     * - 100 items: 10,000 operations
     * - 1,000 items: 1,000,000 operations
     * - 10,000 items: 100,000,000 operations (might take minutes!)
     * 
     * Interview Questions:
     * Q: "When is it OK to use O(n²) algorithms?"
     * A: When n is small (< 100) or when code simplicity matters more than speed.
     *    Also acceptable if it's a one-time operation on small data.
     * 
     * Q: "How can you optimize bubble sort?"
     * A: Add a flag to detect if array is already sorted (early exit).
     *    If no swaps happen in a pass, array is sorted!
     * 
     * @param array The array to sort (modified in place)
     */
    public void bubbleSort(int[] array) {
        int n = array.length;
        
        // Outer loop: make n-1 passes through the array
        for (int i = 0; i < n - 1; i++) {
            // Inner loop: compare adjacent elements and swap if needed
            // Note: we can stop earlier (n-i-1) because the largest
            // elements "bubble up" to the end after each pass
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Swap the elements - they're in wrong order
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
    
    /**
     * O(n²) - Print all possible pairs
     * 
     * Another example of quadratic time. For an array of 5 elements,
     * this prints 25 pairs (5 × 5).
     * 
     * Common interview problem: "Find all pairs that sum to a target"
     * Naive solution is O(n²), but using a HashSet can make it O(n)!
     */
    public void printAllPairs(int[] array) {
        // For each element...
        for (int i = 0; i < array.length; i++) {
            // ...pair it with every element (including itself)
            for (int j = 0; j < array.length; j++) {
                System.out.println("(" + array[i] + ", " + array[j] + ")");
            }
        }
        // Total pairs = n × n = n²
    }

    // ==================== O(2^n) - Exponential Time ====================
    /**
     * O(2^n) - EXPONENTIAL TIME (Recursive Fibonacci)
     * 
     * What it means: EXTREMELY SLOW! Time DOUBLES with each additional input!
     * Real-world example: Trying all possible combinations of a password.
     * 
     * Why O(2^n)? Each function call makes TWO more calls, creating a tree.
     * - fibonacci(5) makes 2 calls
     * - Each of those makes 2 calls (4 total)
     * - Each of those makes 2 calls (8 total)
     * - Keeps doubling!
     * 
     * The Horror Story:
     * - fibonacci(10): 1,024 operations
     * - fibonacci(20): 1,048,576 operations
     * - fibonacci(40): 1,099,511,627,776 operations (takes minutes!)
     * - fibonacci(100): More operations than atoms in the universe!
     * 
     * Interview Questions:
     * Q: "How can you make fibonacci faster?"
     * A: Use Dynamic Programming! Store results you already calculated.
     *    This reduces O(2^n) to O(n)! That's the power of optimization!
     * 
     * Q: "Give examples of when O(2^n) is unavoidable."
     * A: Some problems like the Traveling Salesman Problem or generating all
     *    subsets truly require checking all possibilities. But usually there's
     *    a better way using techniques like dynamic programming or greedy algorithms.
     * 
     * @param n The position in Fibonacci sequence (0, 1, 1, 2, 3, 5, 8, 13...)
     * @return The nth Fibonacci number
     */
    public int fibonacci(int n) {
        // Base cases: fibonacci(0) = 0, fibonacci(1) = 1
        if (n <= 1) {
            return n;
        }
        // WARNING: This creates a massive tree of duplicate calculations!
        // fibonacci(5) calculates fibonacci(3) twice, fibonacci(2) three times!
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // ==================== Performance Demonstration ====================
    /**
     * Demonstrates the performance difference between complexities
     */
    public void demonstrateComplexities() {
        int n = 1000;
        int[] testArray = new int[n];
        for (int i = 0; i < n; i++) {
            testArray[i] = i;
        }
        
        // O(1)
        long start = System.nanoTime();
        getFirstElement(testArray);
        long end = System.nanoTime();
        System.out.println("O(1):      " + (end - start) + " ns");
        
        // O(log n)
        start = System.nanoTime();
        binarySearch(testArray, 500);
        end = System.nanoTime();
        System.out.println("O(log n):  " + (end - start) + " ns");
        
        // O(n)
        start = System.nanoTime();
        sumArray(testArray);
        end = System.nanoTime();
        System.out.println("O(n):      " + (end - start) + " ns");
        
        // O(n log n)
        int[] copy1 = Arrays.copyOf(testArray, testArray.length);
        start = System.nanoTime();
        mergeSort(copy1);
        end = System.nanoTime();
        System.out.println("O(n log n): " + (end - start) + " ns");
        
        // O(n²) - use smaller array to avoid long wait
        int[] smallArray = new int[100];
        for (int i = 0; i < 100; i++) {
            smallArray[i] = 100 - i;
        }
        start = System.nanoTime();
        bubbleSort(smallArray);
        end = System.nanoTime();
        System.out.println("O(n²):     " + (end - start) + " ns (n=100)");
        
        System.out.println("\n⚠️  Notice: As n grows, the difference becomes more dramatic!");
    }

    // ==================== Space Complexity Examples ====================
    /**
     * SPACE COMPLEXITY - How much memory does your algorithm use?
     * 
     * Just like time complexity, we measure how memory usage grows with input size.
     * Sometimes you trade space for speed (or vice versa)!
     */
    
    /**
     * O(1) SPACE - Constant Space
     * 
     * Uses the same amount of memory regardless of input size.
     * We only create one variable (sum), so it's constant space.
     * 
     * Interview Tip: Algorithms that modify input in-place often have O(1) space.
     */
    public int sumArrayConstantSpace(int[] array) {
        int sum = 0;  // Only one variable - doesn't grow with array size!
        for (int num : array) {
            sum += num;
        }
        return sum;
    }
    
    /**
     * O(n) SPACE - Linear Space
     * 
     * Creates a new array the same size as input.
     * Double the input size = double the memory needed.
     * 
     * Trade-off: Using O(n) extra space can sometimes make algorithm faster!
     */
    public int[] copyArray(int[] array) {
        int[] copy = new int[array.length];  // New array of size n
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }
        return copy;
    }
    
    /**
     * O(n²) SPACE - Quadratic Space
     * 
     * Creates a 2D array (matrix).
     * If n = 100, this creates 10,000 spaces!
     * 
     * Interview Question: "When would you use a 2D array?"
     * Answer: Games (chess board), dynamic programming (edit distance),
     * graphs (adjacency matrix), or any data with 2 dimensions.
     */
    public int[][] create2DArray(int n) {
        return new int[n][n];  // n × n matrix = n² space
    }
}

/*
 * ============================================================
 * COMMON INTERVIEW QUESTIONS ABOUT BIG O
 * ============================================================
 * 
 * Q1: "What's the difference between O(n) and O(2n)?"
 * A: They're the same! In Big O, we drop constants. O(2n) = O(n).
 *    Why? Because Big O describes growth rate, not exact operations.
 *    As n gets huge, the constant doesn't matter much.
 * 
 * Q2: "What about O(n + m)?"
 * A: This is when you have TWO different inputs!
 *    Example: Comparing two arrays of different sizes.
 *    You can't simplify this to O(n) because m might be different.
 * 
 * Q3: "How do you calculate Big O for complex code?"
 * A: Follow these steps:
 *    1. Identify loops and recursive calls
 *    2. Nested loops multiply: for + for = O(n²)
 *    3. Sequential operations add: O(n) + O(n) = O(n)
 *    4. Drop constants: O(3n) = O(n)
 *    5. Keep only the fastest-growing term: O(n² + n) = O(n²)
 * 
 * Q4: "Best case vs Average case vs Worst case?"
 * A: - Best case: Lucky scenario (rarely useful)
 *    - Average case: Typical scenario (most realistic)
 *    - Worst case: Unlucky scenario (what Big O usually refers to)
 *    Example: Searching for 5 in [5, 1, 2, 3, 4]
 *    - Best: O(1) - found immediately!
 *    - Worst: O(n) - it's at the end or not there
 * 
 * Q5: "Why does sorting matter so much?"
 * A: Many problems become easier with sorted data!
 *    - Finding duplicates: O(n) with sorted data vs O(n²) without
 *    - Finding median: O(1) with sorted data vs O(n) without
 *    - Binary search: O(log n) with sorted data vs O(n) without
 * 
 * ============================================================
 * PRACTICE PROBLEMS (Try to solve these!)
 * ============================================================
 * 
 * PROBLEM 1: Find duplicates in an array
 * Input: [1, 2, 3, 2, 4, 5, 1]
 * Output: [1, 2]
 * 
 * Solution 1 (Easy but slow - O(n²)):
 * - Compare each element with every other element
 * 
 * Solution 2 (Optimal - O(n)):
 * - Use a HashSet! Add elements as you see them.
 * - If element is already in set, it's a duplicate.
 * 
 * PROBLEM 2: Find two numbers that sum to target
 * Input: array = [2, 7, 11, 15], target = 9
 * Output: [0, 1] (because array[0] + array[1] = 2 + 7 = 9)
 * 
 * Solution 1 (Slow - O(n²)):
 * - Try all pairs with nested loops
 * 
 * Solution 2 (Fast - O(n)):
 * - Use a HashMap! For each number x, check if (target - x) exists.
 * - This is a famous interview question from Amazon, Google, etc!
 * 
 * PROBLEM 3: Reverse a string
 * Input: "hello"
 * Output: "olleh"
 * 
 * Solution (O(n) time, O(n) space):
 * - Create new string or use StringBuilder
 * 
 * Bonus (O(n) time, O(1) space):
 * - Convert to char array and swap from both ends moving inward
 * 
 * ============================================================
 * BIG O CHEAT SHEET - Memorize This!
 * ============================================================
 * 
 * From FASTEST to SLOWEST:
 * 
 * O(1)       - Constant       - Accessing array[0]
 * O(log n)   - Logarithmic    - Binary search
 * O(n)       - Linear         - Simple loop
 * O(n log n) - Linearithmic   - Merge sort, quick sort
 * O(n²)      - Quadratic      - Nested loops, bubble sort
 * O(2^n)     - Exponential    - Recursive fibonacci
 * O(n!)      - Factorial      - Trying all permutations (VERY SLOW!)
 * 
 * Growth Comparison (n = 10):
 * O(1):       1 operation
 * O(log n):   3 operations
 * O(n):       10 operations
 * O(n log n): 30 operations
 * O(n²):      100 operations
 * O(2^n):     1,024 operations
 * O(n!):      3,628,800 operations 😱
 * 
 * ============================================================
 * FINAL INTERVIEW TIPS
 * ============================================================
 * 
 * 1. ALWAYS state the Big O of your solution!
 *    Interviewers want to see you think about efficiency.
 * 
 * 2. Think of better solutions!
 *    "This works but it's O(n²). With a HashMap, we could do O(n)."
 * 
 * 3. Discuss trade-offs!
 *    "This is faster O(n log n) but uses more memory O(n)."
 * 
 * 4. Ask about constraints!
 *    "How big is n? If it's small, simple O(n²) might be fine."
 * 
 * 5. Practice, practice, practice!
 *    Do LeetCode problems and analyze their Big O complexity.
 * 
 * Remember: Big O isn't about being perfect, it's about understanding
 * how your code will behave as data grows. That's what separates good
 * programmers from great ones!
 * 
 * Good luck with your interviews! 🚀
 */
