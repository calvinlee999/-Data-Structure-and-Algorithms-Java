package com.company.quicksort;

/*
 * ========================================
 * QUICK SORT - The Fast Partition Champion
 * ========================================
 * 
 * WHAT IS QUICK SORT?
 * Imagine organizing a classroom by height. You pick one student as a "pivot" (middle height).
 * Everyone shorter goes to the left, everyone taller to the right. Then you repeat this process
 * for the left group and right group separately. That's Quick Sort!
 * 
 * REAL-WORLD ANALOGY:
 * Think of organizing a deck of cards. Pick any card (the pivot), then split the deck:
 * cards lower than pivot go left, higher cards go right. Now you have two smaller piles
 * to organize the same way. Eventually, everything is sorted!
 * 
 * HOW IT WORKS - STEP BY STEP:
 * 
 * Starting Array: [20, 35, -15, 7, 55, 1, -22]
 * 
 * Step 1: Pick PIVOT = 20 (first element)
 *         Partition around 20:
 *         [-15, 7, 1, -22] | 20 | [35, 55]
 *         ← less than 20      ↑     greater than 20 →
 * 
 * Step 2: Quick Sort LEFT side [-15, 7, 1, -22]
 *         PIVOT = -15
 *         Partition: [-22] | -15 | [7, 1]
 * 
 * Step 3: Quick Sort RIGHT of -15: [7, 1]
 *         PIVOT = 7
 *         Partition: [1] | 7 | []
 * 
 * Step 4: Quick Sort RIGHT side of original: [35, 55]
 *         PIVOT = 35
 *         Partition: [] | 35 | [55]
 * 
 * Final: [-22, -15, 1, 7, 20, 35, 55] ← All sorted!
 * 
 * PARTITIONING IN DETAIL (The Magic Step):
 * Array: [20, 35, -15, 7, 55, 1, -22], PIVOT = 20
 * 
 * Start: i→[20, 35, -15, 7, 55, 1, -22]←j
 * 
 * Step 1: Move j left until find element < pivot
 *         i→[20, 35, -15, 7, 55, 1, -22]
 *                                      ↑j (-22 < 20!)
 *         Swap: [−22, 35, -15, 7, 55, 1, 20]
 * 
 * Step 2: Move i right until find element > pivot
 *         [−22, 35, -15, 7, 55, 1, 20]
 *            ↑i     ↑j (35 > 20!)
 *         Swap: [−22, 1, -15, 7, 55, 35, 20]
 * 
 * Step 3: Continue until i and j meet
 *         [−22, 1, -15, 7] | [55, 35, 20]
 *                        ↑i,j meet here
 * 
 * Step 4: Put pivot in its final position
 *         [−22, 1, -15, 7, 20, 35, 55]
 *                         ↑ pivot is in correct spot!
 * 
 * KEY CHARACTERISTICS:
 * ✓ Uses "Divide and Conquer" with PARTITIONING
 * ✓ Pivot element ends up in its final sorted position
 * ✓ Elements < pivot go left, elements > pivot go right
 * ✓ FASTEST average-case sort in practice!
 * ✓ In-place (no extra array needed like Merge Sort)
 * ✓ Cache-friendly (good memory access patterns)
 * 
 * TIME & SPACE COMPLEXITY:
 * ┌─────────────┬──────────┬──────────┬─────────────────────────┐
 * │ Case        │ Time     │ Space    │ Why?                    │
 * ├─────────────┼──────────┼──────────┼─────────────────────────┤
 * │ Best        │ O(n logn)│ O(logn)  │ Balanced partitions     │
 * │ Average     │ O(n logn)│ O(logn)  │ Usually balanced        │
 * │ Worst       │ O(n²)    │ O(n)     │ Already sorted + pivot  │
 * └─────────────┴──────────┴──────────┴─────────────────────────┘
 * 
 * WORST CASE happens when:
 * - Array is already sorted and we pick first/last as pivot
 * - Example: [1,2,3,4,5] with pivot=1 → partition is [1] and [2,3,4,5]
 * - Next: [2] and [3,4,5], then [3] and [4,5]... → O(n²)!
 * 
 * WHEN TO USE:
 * ✓ Large datasets (1000+ elements)
 * ✓ General-purpose sorting (most common choice!)
 * ✓ When average O(n log n) is acceptable
 * ✓ When memory is limited (in-place sorting)
 * ✓ When cache performance matters
 * ✓ Randomized data
 * 
 * WHEN NOT TO USE:
 * ✗ When you need guaranteed O(n log n) (use Merge Sort or Heap Sort)
 * ✗ When you need stable sorting
 * ✗ When data is already sorted or nearly sorted (without randomization)
 * ✗ When worst-case O(n²) is unacceptable
 * 
 * INTERVIEW QUESTIONS & SOLUTIONS:
 * 
 * Q1: How can we avoid the O(n²) worst case?
 * A: THREE main strategies:
 *    1. Random Pivot: Pick random element instead of first/last
 *       int randomIndex = start + random.nextInt(end - start);
 *       swap(input, start, randomIndex);
 *    
 *    2. Median-of-Three: Use median of first, middle, last
 *       int mid = (start + end) / 2;
 *       int pivot = median(input[start], input[mid], input[end-1]);
 *    
 *    3. IntroSort: Start with QuickSort, switch to HeapSort if too many recursions
 *       (This is what C++'s std::sort uses!)
 * 
 * Q2: Why is Quick Sort faster than Merge Sort in practice?
 * A: Three reasons:
 *    1. IN-PLACE: Quick Sort doesn't need extra O(n) array like Merge Sort
 *    2. CACHE-FRIENDLY: Better memory locality (accesses nearby elements)
 *    3. FEWER OPERATIONS: Less data movement overall
 *    
 *    Even though both are O(n log n), Quick Sort has better constants!
 * 
 * Q3: LEETCODE: Kth Largest Element (QuickSelect Algorithm)
 * A: Use Quick Sort's partition but DON'T sort both sides!
 *    
 *    public int findKthLargest(int[] nums, int k) {
 *        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
 *    }
 *    
 *    private int quickSelect(int[] nums, int left, int right, int k) {
 *        int pivot = partition(nums, left, right);
 *        if (pivot == k) return nums[k];
 *        if (pivot < k) return quickSelect(nums, pivot + 1, right, k);
 *        return quickSelect(nums, left, pivot - 1, k);
 *    }
 *    
 *    Time: O(n) average! (We only recurse into one partition)
 * 
 * Q4: INTERVIEW: Sort Colors / Dutch National Flag
 * A: Use Quick Sort's partitioning idea with TWO pivots!
 *    Sort [2,0,2,1,1,0] → [0,0,1,1,2,2]
 *    
 *    public void sortColors(int[] nums) {
 *        int low = 0, mid = 0, high = nums.length - 1;
 *        while (mid <= high) {
 *            if (nums[mid] == 0) {
 *                swap(nums, low++, mid++);      // 0 to front
 *            } else if (nums[mid] == 2) {
 *                swap(nums, mid, high--);       // 2 to back
 *            } else {
 *                mid++;                         // 1 stays middle
 *            }
 *        }
 *    }
 *    
 *    This is O(n) with one pass!
 * 
 * Q5: Is Quick Sort stable?
 * A: NO! The partitioning step can swap equal elements out of order.
 *    Example: [3a, 5, 3b, 2] with pivot=3a
 *    After partition: [2, 3b, 3a, 5] ← 3b came before 3a!
 *    
 *    For stable quick sort, you'd need extra space (defeats the purpose)
 * 
 * Q6: INTERVIEW: Partition Array into Three Parts
 * A: Extension of Quick Sort partition - use two pivots!
 *    Example: Partition around pivot, < pivot, = pivot, > pivot
 *    
 *    public int[] partition3way(int[] arr, int pivot) {
 *        int low = 0, mid = 0, high = arr.length - 1;
 *        while (mid <= high) {
 *            if (arr[mid] < pivot) swap(arr, low++, mid++);
 *            else if (arr[mid] > pivot) swap(arr, mid, high--);
 *            else mid++;
 *        }
 *        return new int[]{low, high}; // boundaries
 *    }
 * 
 * Q7: LEETCODE: Top K Frequent Elements
 * A: Use QuickSelect on frequency array!
 *    
 *    public int[] topKFrequent(int[] nums, int k) {
 *        // Build frequency map
 *        Map<Integer, Integer> count = new HashMap<>();
 *        for (int n : nums) count.put(n, count.getOrDefault(n, 0) + 1);
 *        
 *        // QuickSelect to find top k
 *        int[] unique = count.keySet().stream().mapToInt(i->i).toArray();
 *        quickSelect(unique, 0, unique.length - 1, k, count);
 *        return Arrays.copyOfRange(unique, unique.length - k, unique.length);
 *    }
 * 
 * Q8: Why does Quick Sort have O(log n) space complexity?
 * A: For the RECURSION STACK! Each recursive call uses stack space.
 *    - Best case: Balanced partitions → log(n) depth
 *    - Worst case: Unbalanced → n depth
 *    
 *    Optimization: Always recurse on smaller partition first → guarantees O(log n) stack!
 * 
 * COMPARISON WITH OTHER SORTS:
 * ┌──────────────┬───────────┬─────────┬──────────┬────────────┐
 * │ Algorithm    │ Avg Time  │ Space   │ Stable?  │ In-place?  │
 * ├──────────────┼───────────┼─────────┼──────────┼────────────┤
 * │ Quick Sort   │ O(n logn) │ O(logn) │ No       │ Yes        │
 * │ Merge Sort   │ O(n logn) │ O(n)    │ Yes      │ No         │
 * │ Heap Sort    │ O(n logn) │ O(1)    │ No       │ Yes        │
 * │ Insertion    │ O(n²)     │ O(1)    │ Yes      │ Yes        │
 * └──────────────┴───────────┴─────────┴──────────┴────────────┘
 * 
 * REAL-WORLD USAGE:
 * - C++ std::sort uses IntroSort (Quick Sort + Heap Sort hybrid)
 * - Java's Arrays.sort() uses Quick Sort for primitives
 * - Python's sort uses TimSort (Merge Sort + Insertion Sort)
 * - Most database systems use Quick Sort for in-memory sorting
 * 
 * FUN FACTS:
 * - Invented by Tony Hoare in 1959 (he was 26!)
 * - Called "Quick" because it's fast in practice
 * - The partition algorithm is also called "Hoare partition"
 * - QuickSelect (finding kth element) is used in median-finding algorithms
 */

public class Main {

    public static void main(String[] args) {
        int[] intArray = { 20, 35, -15, 7, 55, 1, -22 };
    public static void main(String[] args) {
        int[] intArray = { 20, 35, -15, 7, 55, 1, -22 };

        // Call quickSort on entire array
        quickSort(intArray,0,intArray.length);

        // Print sorted array
        for(int i =0;i<intArray.length;i++){
            System.out.println(intArray[i]);
        }
    }
    
    /**
     * QUICK SORT - Main recursive function
     * 
     * @param input - array to sort
     * @param start - starting index (inclusive)
     * @param end - ending index (exclusive)
     * 
     * How it works:
     * 1. BASE CASE: If section has less than 2 elements, return (already sorted)
     * 2. PARTITION: Rearrange array so pivot is in correct position
     * 3. RECURSE: Quick sort left side and right side of pivot
     * 
     * Example: [20, 35, -15, 7, 55, 1, -22]
     * → partition around 20 → [-15, 7, 1, -22, 20, 35, 55]
     *                         ← sort left →  ↑  ← sort right →
     *                                      pivot (correct position)
     */
    public static void quickSort(int[] input, int start, int end){
        // BASE CASE: If section has less than 2 elements, nothing to sort
        if(end- start<2){
            return;
        }

        // Partition array and get pivot's final position
        int pivotIndex = partition(input, start, end);
        
        // Recursively sort left side (elements less than pivot)
        quickSort(input, start, pivotIndex);
        
        // Recursively sort right side (elements greater than pivot)
        quickSort(input, pivotIndex+1, end);
    }
    
    /**
     * PARTITION - The heart of Quick Sort!
     * Rearranges array so all elements < pivot are on left,
     * all elements > pivot are on right, and returns pivot's final position
     * 
     * @param input - array to partition
     * @param start - starting index
     * @param end - ending index (exclusive)
     * @return final position of pivot
     * 
     * Algorithm (Two-pointer approach):
     * 1. Choose first element as pivot
     * 2. i starts at beginning, j starts at end
     * 3. Move j left until we find element < pivot, swap it to position i
     * 4. Move i right until we find element > pivot, swap it to position j
     * 5. Repeat until i and j meet
     * 6. Put pivot at the meeting point
     * 
     * Example: [20, 35, -15, 7, 55, 1, -22], pivot=20
     * 
     * Initial:  i→[20, 35, -15, 7, 55, 1, -22]←j
     * 
     * j finds -22 < 20:  [−22, 35, -15, 7, 55, 1, 20]
     *                      ↑i                      ↑j
     * 
     * i finds 35 > 20:   [−22, 1, -15, 7, 55, 35, 20]
     *                          ↑i           ↑j
     * 
     * Continue until i,j meet at position 4
     * Put pivot there:   [−22, 1, -15, 7, 20, 55, 35]
     *                                      ↑
     *                                   pivot position
     */
    public static int partition(int[] input, int start, int end){
        // Choose first element as pivot
        int pivot = input[start];
        int i =start;
        int j = end;
        
        // Continue until i and j meet
        while (i<j){

            // Move j left until we find element < pivot
            // NOTE: j-- is done BEFORE comparison (pre-decrement)
            while(i<j&&input[--j]>=pivot);
            if(i<j){
                input[i] = input[j];  // Move small element to left side
            }

            // Move i right until we find element > pivot
            // NOTE: i++ is done BEFORE comparison (pre-increment)
            while(i<j&&input[++i]<=pivot);
            if(i<j){
                input[j] = input[i];  // Move large element to right side
            }
        }

        // Put pivot in its final sorted position
        input[j] = pivot;
        return j;  // Return pivot's final position
    }
}
