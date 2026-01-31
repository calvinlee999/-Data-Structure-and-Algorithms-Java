package com.company.mergesort;

/*
 * ========================================
 * MERGE SORT - Divide, Conquer, and Combine
 * ========================================
 * 
 * WHAT IS MERGE SORT?
 * Imagine you have a huge pile of papers to sort. Instead of sorting them all at once,
 * you split the pile in half, sort each half separately, then merge them back together.
 * Keep splitting until you have tiny piles (1-2 papers) that are easy to sort!
 * 
 * REAL-WORLD ANALOGY:
 * Think of organizing a tournament bracket. You divide players into groups, each group
 * plays their games, then winners merge into larger rounds. At each level, you're
 * combining already-sorted groups until you have one complete ranking.
 * 
 * HOW IT WORKS - STEP BY STEP:
 * 
 * Starting Array: [20, 35, -15, 7, 55, 1, -22]
 * 
 * DIVIDE PHASE (Split until we can't split anymore):
 * 
 * Level 1:      [20, 35, -15, 7, 55, 1, -22]
 *                        ↓ split
 *        [20, 35, -15, 7]  |  [55, 1, -22]
 * 
 * Level 2:   [20, 35] [-15, 7]  |  [55, 1] [-22]
 * 
 * Level 3:   [20] [35] [-15] [7]  |  [55] [1] [-22]
 *            ← Base case: single elements are already sorted!
 * 
 * MERGE PHASE (Combine sorted pieces):
 * 
 * Level 3→2: [20] + [35] = [20, 35]
 *            [-15] + [7] = [-15, 7]
 *            [55] + [1] = [1, 55]
 *            [-22] stays [-22]
 * 
 * Level 2→1: [20, 35] + [-15, 7] = [-15, 7, 20, 35]
 *            [1, 55] + [-22] = [-22, 1, 55]
 * 
 * Level 1:   [-15, 7, 20, 35] + [-22, 1, 55] = [-22, -15, 1, 7, 20, 35, 55]
 *            ← Fully sorted!
 * 
 * HOW MERGING WORKS (Detailed Example):
 * Merging [-15, 7] and [20, 35]:
 * 
 *   Left:  [-15, 7]      Right: [20, 35]
 *           ↑                     ↑
 *   Compare -15 vs 20 → -15 is smaller → Result: [-15]
 *   
 *   Left:  [-15, 7]      Right: [20, 35]
 *                ↑                ↑
 *   Compare 7 vs 20 → 7 is smaller → Result: [-15, 7]
 *   
 *   Left exhausted! Copy remaining right: [-15, 7, 20, 35]
 * 
 * KEY CHARACTERISTICS:
 * ✓ Uses "Divide and Conquer" strategy
 * ✓ Recursively splits array in half
 * ✓ Merges sorted halves back together
 * ✓ GUARANTEED O(n log n) performance!
 * ✓ Stable sort (maintains order of equal elements)
 * ✓ Great for linked lists and external sorting
 * 
 * TIME & SPACE COMPLEXITY:
 * ┌─────────────┬──────────┬──────────┬─────────────────────────┐
 * │ Case        │ Time     │ Space    │ Why?                    │
 * ├─────────────┼──────────┼──────────┼─────────────────────────┤
 * │ Best        │ O(n logn)│ O(n)     │ Always splits same way  │
 * │ Average     │ O(n logn)│ O(n)     │ Consistent performance  │
 * │ Worst       │ O(n logn)│ O(n)     │ No worst case!          │
 * └─────────────┴──────────┴──────────┴─────────────────────────┘
 * 
 * Why O(n log n)?
 * - We split log(n) times (each split cuts size in half)
 * - At each level, we do O(n) work to merge
 * - Total: log(n) levels × O(n) work = O(n log n)
 * 
 * WHEN TO USE:
 * ✓ Large datasets (1000+ elements)
 * ✓ When you need guaranteed O(n log n) performance
 * ✓ When you need a stable sort
 * ✓ Sorting linked lists (no extra space needed!)
 * ✓ External sorting (data doesn't fit in memory)
 * ✓ When consistent performance matters
 * 
 * WHEN NOT TO USE:
 * ✗ When memory is limited (uses O(n) extra space)
 * ✗ Very small datasets (overhead not worth it)
 * ✗ When you need in-place sorting
 * 
 * INTERVIEW QUESTIONS & SOLUTIONS:
 * 
 * Q1: What makes Merge Sort "stable"?
 * A: During the merge step, when two elements are equal, we ALWAYS take from
 *    the left array first. This preserves their original order!
 *    
 *    Example: [(3,"first"), (3,"second"), (1,"third")]
 *    Split: [(3,"first")] [(3,"second"), (1,"third")]
 *    Merge back: (3,"first") comes before (3,"second") ← Order preserved!
 * 
 * Q2: Why is Merge Sort better than Quick Sort for linked lists?
 * A: Merge Sort doesn't need random access! With linked lists:
 *    - Merge Sort: O(n log n) time, O(1) extra space (just rearrange pointers)
 *    - Quick Sort: O(n log n) but needs to traverse to find pivot each time
 * 
 * Q3: LEETCODE: Merge K Sorted Lists
 * A: Use the same merge idea from Merge Sort!
 *    
 *    public ListNode mergeKLists(ListNode[] lists) {
 *        if (lists.length == 0) return null;
 *        return mergeKListsHelper(lists, 0, lists.length - 1);
 *    }
 *    
 *    private ListNode mergeKListsHelper(ListNode[] lists, int left, int right) {
 *        if (left == right) return lists[left];
 *        int mid = left + (right - left) / 2;
 *        ListNode l1 = mergeKListsHelper(lists, left, mid);
 *        ListNode l2 = mergeKListsHelper(lists, mid + 1, right);
 *        return mergeTwoLists(l1, l2);
 *    }
 *    
 *    // Same merge logic as Merge Sort!
 * 
 * Q4: INTERVIEW: Count Inversions in Array
 * A: An inversion is when i < j but arr[i] > arr[j]. Count them while merging!
 *    Example: [2,4,1,3,5] has inversions: (2,1), (4,1), (4,3) = 3
 *    
 *    private int mergeAndCount(int[] arr, int l, int m, int r) {
 *        // ... merge logic ...
 *        // When we pick from right array:
 *        if (arr[j] < arr[i]) {
 *            count += (m - i + 1);  // All remaining left elements > arr[j]
 *        }
 *    }
 * 
 * Q5: Can you do Merge Sort in-place (without extra space)?
 * A: Theoretically yes, but it's complex and loses the O(n log n) benefit!
 *    Regular Merge Sort uses O(n) space but is O(n log n).
 *    In-place versions become O(n² log n) - not worth it!
 *    
 *    For in-place sorting, use Quick Sort or Heap Sort instead.
 * 
 * Q6: INTERVIEW: Sort a Nearly Sorted Array
 * A: If array is nearly sorted (each element is at most k positions away),
 *    use Insertion Sort O(nk) or Min Heap O(n log k).
 *    But if k is large, Merge Sort is still best!
 * 
 * Q7: External Sorting (data doesn't fit in memory)
 * A: Perfect for Merge Sort!
 *    1. Split file into chunks that fit in memory
 *    2. Sort each chunk and write to disk
 *    3. Merge sorted chunks (K-way merge)
 *    
 *    This is how databases sort huge datasets!
 * 
 * COMPARISON WITH OTHER SORTS:
 * ┌──────────────┬───────────┬─────────┬──────────┬─────────┐
 * │ Algorithm    │ Time      │ Space   │ Stable?  │ When?   │
 * ├──────────────┼───────────┼─────────┼──────────┼─────────┤
 * │ Merge Sort   │ O(n logn) │ O(n)    │ Yes      │ Large   │
 * │ Quick Sort   │ O(n logn) │ O(logn) │ No       │ General │
 * │ Heap Sort    │ O(n logn) │ O(1)    │ No       │ Memory  │
 * │ Insertion    │ O(n²)     │ O(1)    │ Yes      │ Small   │
 * │ Selection    │ O(n²)     │ O(1)    │ No       │ Min swap│
 * └──────────────┴───────────┴─────────┴──────────┴─────────┘
 * 
 * FUN FACTS:
 * - Invented by John von Neumann in 1945
 * - Used in Java's Arrays.sort() for objects (TimSort variant)
 * - Python's sort uses TimSort (combines Merge + Insertion)
 * - Best for parallel processing (can merge in parallel!)
 */

public class Main {

    public static void main(String[] args) {
        int[] intArray = { 20, 35, -15, 7, 55, 1, -22 };
    public static void main(String[] args) {
        int[] intArray = { 20, 35, -15, 7, 55, 1, -22 };

        // Call merge sort on the entire array
        mergeSort(intArray, 0, intArray.length);

        // Print the sorted result
        for (int i = 0; i < intArray.length; i++) {
            System.out.println(intArray[i]);
        }
    }

    /**
     * MERGE SORT - Recursive function that splits array and sorts
     * 
     * @param input - the array to sort
     * @param start - starting index (inclusive)
     * @param end - ending index (exclusive)
     * 
     * How it works:
     * 1. BASE CASE: If section has less than 2 elements, it's already sorted
     * 2. DIVIDE: Split array into two halves
     * 3. CONQUER: Recursively sort each half
     * 4. COMBINE: Merge the two sorted halves
     * 
     * Example with [20, 35, -15, 7]:
     * mergeSort([20,35,-15,7], 0, 4)
     *   → mergeSort([20,35], 0, 2)
     *       → mergeSort([20], 0, 1) → returns (base case)
     *       → mergeSort([35], 1, 2) → returns (base case)
     *       → merge([20], [35]) → [20, 35]
     *   → mergeSort([-15,7], 2, 4)
     *       → merge([-15], [7]) → [-15, 7]
     *   → merge([20,35], [-15,7]) → [-15, 7, 20, 35]
     */
    public static void mergeSort(int[] input, int start, int end) {

        // BASE CASE: If we have less than 2 elements, nothing to sort
        if (end - start < 2) {
            return;
        }

        // Find the middle point to split the array
        int mid = (start + end) / 2;
        
        // Recursively sort the left half
        mergeSort(input, start, mid);
        
        // Recursively sort the right half
        mergeSort(input, mid, end);
        
        // Merge the two sorted halves together
        merge(input, start, mid, end);
    }

    /**
     * MERGE - Combines two sorted subarrays into one sorted array
     * 
     * @param input - the array containing both subarrays
     * @param start - start of left subarray
     * @param mid - end of left subarray (and start of right subarray)
     * @param end - end of right subarray
     * 
     * Visual Example:
     * input = [..., -15, 7, ..., 20, 35, ...]
     *              ↑         ↑          ↑
     *            start      mid        end
     * 
     * Left sorted:  [-15, 7]
     * Right sorted: [20, 35]
     * 
     * Merge process:
     * Compare -15 vs 20 → pick -15 → temp = [-15]
     * Compare   7 vs 20 → pick   7 → temp = [-15, 7]
     * Left empty! Copy rest       → temp = [-15, 7, 20, 35]
     * 
     * OPTIMIZATION: If left's last element ≤ right's first element,
     * they're already in order - no merge needed!
     */
    public static void merge(int[] input, int start, int mid, int end) {

        // OPTIMIZATION: If already sorted, no need to merge!
        // Example: [1, 3] and [5, 7] → 3 < 5, already sorted!
        if (input[mid - 1] <= input[mid]) {
            return;
        }

        int i = start;      // Pointer for left subarray
        int j = mid;        // Pointer for right subarray
        int tempIndex = 0;  // Pointer for temporary array

        // Create temporary array to hold merged result
        int[] temp = new int[end - start];
        
        // Compare elements from left and right, pick smaller one
        while (i < mid && j < end) {
            // Pick smaller element and advance that pointer
            temp[tempIndex++] = input[i] <= input[j] ? input[i++] : input[j++];
        }

        // If left subarray has remaining elements, copy them
        // NOTE: We don't need to copy remaining right elements because
        // they're already in the correct position in the original array!
        System.arraycopy(input, i, input, start + tempIndex, mid - i);
        
        // Copy the merged elements from temp back to input
        System.arraycopy(temp, 0, input, start, tempIndex);
    }

}
