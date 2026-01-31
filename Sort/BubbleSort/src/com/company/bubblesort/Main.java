package com.company.bubblesort;

/*
 * ============================================================================
 * BUBBLE SORT - A Simple Sorting Algorithm 🫧
 * ============================================================================
 * 
 * WHAT IS BUBBLE SORT?
 * Bubble Sort is one of the simplest sorting algorithms. It repeatedly steps 
 * through the list, compares adjacent elements, and swaps them if they're in 
 * the wrong order. This process continues until the entire list is sorted.
 * 
 * 🎈 REAL-WORLD ANALOGY:
 * Imagine you have a row of students lined up by height, but they're all mixed up.
 * You walk down the line comparing each pair of students standing next to each other.
 * If the taller student is on the left, you ask them to switch places.
 * You keep doing this over and over until everyone is perfectly sorted from 
 * shortest to tallest. The tallest students "bubble up" to the end, just like 
 * bubbles float to the top of water!
 * 
 * HOW IT WORKS:
 * 1. Start at the beginning of the array
 * 2. Compare the first two numbers
 * 3. If they're in the wrong order (left > right), swap them
 * 4. Move to the next pair and repeat
 * 5. After one complete pass, the largest number is now at the end
 * 6. Repeat the process for the remaining unsorted portion
 * 7. Each pass puts one more number in its correct position
 * 8. Continue until the array is fully sorted
 * 
 * ⏱️ TIME COMPLEXITY:
 * - Best Case: O(n) - when the array is already sorted (with optimization)
 * - Average Case: O(n²) - when elements are randomly ordered
 * - Worst Case: O(n²) - when the array is sorted in reverse order
 * 
 * 💾 SPACE COMPLEXITY:
 * - O(1) - Only uses a constant amount of extra memory (for swapping)
 * - This is called "in-place" sorting because we don't need extra arrays
 * 
 * ✅ WHEN TO USE BUBBLE SORT:
 * - Small datasets (less than 50 elements)
 * - Data that's already nearly sorted
 * - When you need a simple, easy-to-understand algorithm
 * - When memory is very limited (it's an in-place sort)
 * - For educational purposes to learn sorting concepts
 * 
 * ❌ WHEN NOT TO USE BUBBLE SORT:
 * - Large datasets (use Quick Sort or Merge Sort instead)
 * - When performance is critical (it's one of the slowest sorting algorithms)
 * - Production code (better alternatives exist like Arrays.sort())
 * 
 * COMPARISON WITH OTHER SORTS:
 * - Bubble Sort vs Selection Sort: Both are O(n²), but Selection Sort makes fewer swaps
 * - Bubble Sort vs Insertion Sort: Insertion Sort is usually faster in practice
 * - Bubble Sort vs Quick Sort: Quick Sort is O(n log n) on average, much faster!
 * - Bubble Sort vs Merge Sort: Merge Sort is O(n log n) but uses O(n) extra space
 * 
 * 🚨 COMMON MISTAKES TO AVOID:
 * 1. Forgetting to decrease the unsorted boundary after each pass
 * 2. Not checking if array is null or empty before sorting
 * 3. Using wrong comparison operator (< instead of >)
 * 4. Not optimizing by stopping early if no swaps occur
 * 5. Comparing same element with itself (causes unnecessary operations)
 * 
 * ============================================================================
 */
public class Main {

    /**
     * Main method - demonstrates bubble sort in action
     * This is where our program starts running!
     */
    public static void main(String[] args) {

        // Our test array: A mix of positive and negative numbers
        // This represents an unsorted list we want to organize
        int[] intArray = {20, 35, -15, 7, 55, 1, -22};
        
        System.out.println("===== BUBBLE SORT DEMONSTRATION =====");
        System.out.println("Original array: ");
        printArray(intArray);
        
        // ====================================================================
        // BUBBLE SORT ALGORITHM - The Main Logic
        // ====================================================================
        
        /*
         * OUTER LOOP EXPLANATION:
         * - 'lastUnsortedIndex' marks the boundary of our unsorted section
         * - We start at the end of the array (length - 1)
         * - After each complete pass, the largest unsorted element "bubbles up" 
         *   to its correct position
         * - So we can decrease lastUnsortedIndex by 1 each time
         * - We stop when lastUnsortedIndex reaches 0 (everything is sorted!)
         * 
         * Think of it like this: We're shrinking the "work zone" by one position
         * each time because we know one more element is in the right spot.
         */
        for(int lastUnsortedIndex = intArray.length - 1; 
            lastUnsortedIndex > 0; 
            lastUnsortedIndex--){
            
            /*
             * INNER LOOP EXPLANATION:
             * - This is where the actual comparing and swapping happens
             * - 'i' represents the current position we're examining
             * - We compare element at position i with element at position i+1
             * - We only go up to lastUnsortedIndex (not beyond it)
             * - Why? Because everything after lastUnsortedIndex is already sorted!
             * 
             * Visual example for one pass:
             * [20, 35, -15, 7] (lastUnsortedIndex = 3)
             *  ↑   ↑           Compare 20 and 35 → No swap (20 < 35)
             *      ↑    ↑      Compare 35 and -15 → Swap! (35 > -15)
             *          ↑   ↑   Compare 35 and 7 → Swap! (35 > 7)
             * Result: [20, -15, 7, 35] ← 35 is now in correct position!
             */
            for(int i = 0; i < lastUnsortedIndex; i++){
                
                /*
                 * COMPARISON AND SWAP:
                 * - If current element is greater than the next element
                 * - They're in the wrong order, so we swap them
                 * - This is how larger values "bubble" toward the end
                 * 
                 * 💡 TIP: For descending order, change > to <
                 */
                if (intArray[i] > intArray[i + 1]){
                    swap(intArray, i, i + 1);  // Call our swap helper method
                }
            }
            
            // 📊 Optional: Print array after each pass to see the progress
            // Uncomment the lines below to visualize each step:
            // System.out.println("After pass " + (intArray.length - lastUnsortedIndex) + ":");
            // printArray(intArray);
        }
        
        // ====================================================================
        // DISPLAY THE SORTED RESULT
        // ====================================================================
        System.out.println("\nSorted array:");
        printArray(intArray);
    }
    
    /**
     * Helper method to swap two elements in an array
     * 
     * This is a classic "three-cup shuffle" technique:
     * Imagine you have two cups with different colored liquids and want to swap them.
     * You need a third empty cup to hold one liquid temporarily while you pour!
     * 
     * @param array The array containing elements to swap
     * @param i     Index of the first element
     * @param j     Index of the second element
     * 
     * Time Complexity: O(1) - constant time, always takes the same 3 steps
     * Space Complexity: O(1) - only uses one extra variable (temp)
     */
    public static void swap(int[] array, int i, int j){
        // ⚠️ IMPORTANT: Check if indices are the same
        // Swapping an element with itself is unnecessary and wastes time
        if (i == j){
            return;  // Early exit - nothing to do!
        }
        
        // THE THREE-STEP SWAP:
        int temp = array[i];      // Step 1: Save first value in temporary storage
        array[i] = array[j];      // Step 2: Put second value in first position
        array[j] = temp;          // Step 3: Put saved value in second position
        
        // Result: array[i] and array[j] have traded places!
    }
    
    /**
     * Helper method to print array elements
     * Makes it easier to visualize what's happening
     * 
     * @param array The array to print
     */
    public static void printArray(int[] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");  // Add comma between elements (but not after last one)
            }
        }
        System.out.println();  // New line after printing all elements
    }
}

/*
 * ============================================================================
 * 📝 COMMON INTERVIEW QUESTIONS ABOUT BUBBLE SORT
 * ============================================================================
 * 
 * Q1: What is the time complexity of Bubble Sort?
 * A1: The time complexity is O(n²) in the worst and average cases because we 
 *     have two nested loops. The outer loop runs n-1 times, and the inner loop 
 *     runs n, n-1, n-2... times. This gives us roughly n²/2 comparisons, which 
 *     simplifies to O(n²) in Big-O notation.
 * 
 * Q2: Can Bubble Sort be optimized?
 * A2: Yes! We can add a flag to track if any swaps were made during a pass.
 *     If no swaps occur, the array is already sorted and we can stop early.
 *     This makes the best-case time complexity O(n) for already-sorted arrays.
 *     
 *     Example optimization:
 *     boolean swapped;
 *     do {
 *         swapped = false;
 *         for (int i = 0; i < lastUnsortedIndex; i++) {
 *             if (array[i] > array[i+1]) {
 *                 swap(array, i, i+1);
 *                 swapped = true;
 *             }
 *         }
 *         lastUnsortedIndex--;
 *     } while (swapped);
 * 
 * Q3: Is Bubble Sort stable?
 * A3: Yes! Bubble Sort is a stable sorting algorithm. This means that if two 
 *     elements have the same value, their relative order is preserved in the 
 *     sorted output. We only swap when elements are strictly greater (not >=),
 *     which maintains stability.
 * 
 * Q4: What is the space complexity of Bubble Sort?
 * A4: O(1) - Bubble Sort is an in-place sorting algorithm. It only needs one 
 *     extra variable (temp) for swapping, regardless of the input size. This 
 *     makes it memory-efficient.
 * 
 * Q5: When would you actually use Bubble Sort in real projects?
 * A5: Honestly, almost never in production code! However, it's useful when:
 *     - Teaching sorting concepts (it's easy to understand)
 *     - Sorting very small arrays (< 10 elements) where simplicity matters
 *     - Data is already nearly sorted (with optimization)
 *     - You're working on embedded systems with strict memory constraints
 *     
 *     For real projects, use:
 *     - Arrays.sort() in Java (uses optimized Quick Sort/Merge Sort)
 *     - Collections.sort() for Lists
 *     - Stream API: list.stream().sorted().collect(Collectors.toList())
 * 
 * Q6: How many passes does Bubble Sort need?
 * A6: In the worst case, Bubble Sort needs n-1 passes (where n is the number 
 *     of elements). Each pass guarantees that at least one element reaches its 
 *     final sorted position. After n-1 passes, all elements are sorted.
 * 
 * Q7: What's the difference between Bubble Sort and Selection Sort?
 * A7: Both have O(n²) time complexity, but:
 *     - Bubble Sort: Swaps adjacent elements many times per pass
 *     - Selection Sort: Finds the minimum and swaps only once per pass
 *     Selection Sort typically performs fewer swaps, which can be faster if 
 *     swapping is expensive (like with large objects).
 * 
 * Q8: Can you sort in descending order with Bubble Sort?
 * A8: Yes! Just change the comparison operator from > to < in the if statement:
 *     if (intArray[i] < intArray[i+1]) {  // Changed from >
 *         swap(intArray, i, i+1);
 *     }
 *     This makes larger values bubble toward the beginning instead of the end.
 * 
 * Q9: How would you test if Bubble Sort is working correctly?
 * A9: Test with these cases:
 *     - Already sorted array: [1, 2, 3, 4, 5]
 *     - Reverse sorted array: [5, 4, 3, 2, 1] (worst case)
 *     - Array with duplicates: [3, 1, 4, 1, 5, 9, 2]
 *     - Array with negative numbers: [-3, 5, -1, 0, 2]
 *     - Single element array: [42]
 *     - Empty array: []
 *     - Array with all same elements: [7, 7, 7, 7]
 * 
 * Q10: What happens if the array is null?
 * A10: The current implementation will throw a NullPointerException. In 
 *      production code, you should add null checking:
 *      
 *      if (array == null || array.length == 0) {
 *          return;  // or throw IllegalArgumentException
 *      }
 * 
 * ============================================================================
 * 🎓 PRACTICE EXERCISES
 * ============================================================================
 * 
 * Try these challenges to master Bubble Sort:
 * 
 * 1. Add the optimization flag to stop early when array is sorted
 * 2. Modify the code to sort in descending order
 * 3. Count and print the number of swaps made
 * 4. Create a version that sorts Strings alphabetically
 * 5. Implement a version that sorts objects (like Person by age)
 * 6. Add null/empty array validation
 * 7. Create a method that returns true if array is already sorted
 * 8. Visualize each step by printing the array after every swap
 * 
 * ============================================================================
 * 📚 FURTHER LEARNING
 * ============================================================================
 * 
 * After mastering Bubble Sort, learn these sorting algorithms in order:
 * 1. Selection Sort - Similar complexity but different approach
 * 2. Insertion Sort - Better for nearly-sorted data
 * 3. Merge Sort - Divide and conquer, O(n log n)
 * 4. Quick Sort - Very fast in practice, O(n log n) average
 * 5. Heap Sort - Uses a heap data structure
 * 6. Radix Sort - Non-comparison sort for integers
 * 
 * Remember: Understanding WHY an algorithm works is more important than 
 * memorizing code. Always trace through examples by hand first! ✏️
 * 
 * ============================================================================
 */
