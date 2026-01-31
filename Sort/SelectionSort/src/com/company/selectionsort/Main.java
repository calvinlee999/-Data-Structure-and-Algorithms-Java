package com.company.selectionsort;

/*
 * ========================================
 * SELECTION SORT - Finding the Best Each Time
 * ========================================
 * 
 * WHAT IS SELECTION SORT?
 * Think of how you would organize a deck of cards in your hand. You look through all the cards,
 * find the smallest one, and put it at the front. Then you find the next smallest, and so on.
 * That's exactly how Selection Sort works!
 * 
 * REAL-WORLD ANALOGY:
 * Imagine you're a teacher selecting students for a basketball team. You want them lined up
 * shortest to tallest. You'd look at all students, find the shortest, and place them first.
 * Then you'd find the next shortest from the remaining students, and so on.
 * 
 * HOW IT WORKS - STEP BY STEP:
 * 
 * Starting Array: [20, 35, -15, 7, 55, 1, -22]
 * 
 * Step 1: Find the LARGEST in whole array → 55
 *         Swap with last position
 *         [20, 35, -15, 7, -22, 1, 55] ← 55 is now sorted!
 *         
 * Step 2: Find LARGEST in remaining → 35
 *         Swap with last unsorted position
 *         [20, 1, -15, 7, -22, 35, 55] ← 35 and 55 sorted!
 *         
 * Step 3: Find LARGEST in remaining → 20
 *         [1, -15, 7, -22, 20, 35, 55] ← Last 3 sorted!
 *         
 * Step 4: Continue until all sorted
 *         [-22, -15, 1, 7, 20, 35, 55] ← All sorted!
 * 
 * KEY CHARACTERISTICS:
 * ✓ Divides array into: SORTED (right) | UNSORTED (left)
 * ✓ Always makes exactly n-1 passes (where n = array size)
 * ✓ Each pass finds one correct element
 * ✓ Doesn't care if array is already sorted (still does all comparisons)
 * 
 * TIME & SPACE COMPLEXITY:
 * ┌─────────────┬──────────┬──────────┬──────────┐
 * │ Case        │ Time     │ Space    │ Why?     │
 * ├─────────────┼──────────┼──────────┼──────────┤
 * │ Best        │ O(n²)    │ O(1)     │ Always   │
 * │ Average     │ O(n²)    │ O(1)     │ compares │
 * │ Worst       │ O(n²)    │ O(1)     │ all      │
 * └─────────────┴──────────┴──────────┴──────────┘
 * 
 * WHEN TO USE:
 * ✓ Small datasets (less than 20-30 elements)
 * ✓ When memory is extremely limited (uses no extra space!)
 * ✓ When you want to minimize the number of swaps
 * 
 * WHEN NOT TO USE:
 * ✗ Large datasets (gets very slow!)
 * ✗ When you need fast sorting
 * ✗ When you need stable sorting (maintains order of equal elements)
 * 
 * INTERVIEW QUESTIONS & SOLUTIONS:
 * 
 * Q1: Why is Selection Sort called "unstable"?
 * A: It can change the relative order of equal elements.
 *    Example: [3a, 3b, 1] → [1, 3b, 3a]
 *    The two 3's swapped order!
 * 
 * Q2: How many swaps does Selection Sort make?
 * A: At most n-1 swaps (one per pass). This is better than Bubble Sort!
 *    Bubble Sort can make up to n²/2 swaps.
 * 
 * Q3: Can you optimize Selection Sort?
 * A: Not really for time complexity. But you can:
 *    - Find both min AND max in each pass (cuts iterations in half)
 *    - Skip pass if array is already sorted (add a flag)
 * 
 * Q4: LEETCODE-STYLE: Sort Array by Parity (Even numbers first, then odd)
 * A: Use Selection Sort idea - find evens, swap to front:
 *    [3,1,2,4] → [2,4,3,1]
 *    
 *    public void sortArrayByParity(int[] nums) {
 *        int left = 0;
 *        for (int i = 0; i < nums.length; i++) {
 *            if (nums[i] % 2 == 0) {
 *                swap(nums, left++, i);
 *            }
 *        }
 *    }
 * 
 * Q5: INTERVIEW: Find the kth largest element using Selection Sort idea
 * A: Just do k passes of Selection Sort!
 *    For k=2 in [3,2,1,5,6,4]:
 *    Pass 1: [3,2,1,4,5,6] → 6 is largest
 *    Pass 2: [3,2,1,4,5,6] → 5 is 2nd largest ← Answer!
 * 
 * COMPARISON WITH OTHER SORTS:
 * Selection Sort vs Bubble Sort: Selection does fewer swaps
 * Selection Sort vs Insertion Sort: Insertion is faster for nearly-sorted data
 * Selection Sort vs Merge Sort: Merge is WAY faster but uses extra memory
 * 
 * FUN FACT: Selection Sort makes the MINIMUM number of swaps among all 
 * comparison-based sorts - great when writing to memory is expensive!
 */

public class Main {

    public static void main(String[] args) {

        int[] intArray = {20, 35, -15, 7, 55, 1, -22};
        
        // SELECTION SORT IMPLEMENTATION
        // Loop through array from right to left
        // Each iteration finds the largest element and puts it in its correct position
        for( int lastUnsortedIndex = intArray.length-1; lastUnsortedIndex>0; lastUnsortedIndex--){
        // SELECTION SORT IMPLEMENTATION
        // Loop through array from right to left
        // Each iteration finds the largest element and puts it in its correct position
        for( int lastUnsortedIndex = intArray.length-1; lastUnsortedIndex>0; lastUnsortedIndex--){
            // Assume first element is the largest
            int largest = 0;
            
            // Compare with all elements in unsorted portion to find the actual largest
            for (int i=0;i<=lastUnsortedIndex; i++){
                if(intArray[i]>intArray[largest]){
                    largest=i;  // Update largest if we find a bigger number
                }
            }
            
            // Swap the largest element with the last unsorted position
            // This grows the sorted portion by one element
            swap(intArray, largest, lastUnsortedIndex);
        }

        // Print the sorted array
        for(int i =0;i<intArray.length; i++){
            System.out.println(intArray[i]);
        }
    }

    /**
     * Swaps two elements in an array
     * @param array - the array containing elements to swap
     * @param i - index of first element
     * @param j - index of second element
     * 
     * Example: array = [5, 2, 8], swap(array, 0, 2)
     *          Before: [5, 2, 8]
     *          After:  [8, 2, 5]
     */
    public static void swap(int[] array, int i, int j){
        if (i==j){
            return;  // No need to swap if same position
        }
        // Classic swap using temporary variable
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
