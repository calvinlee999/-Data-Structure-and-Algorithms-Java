package com.company.shellsort;

/*
 * ========================================
 * SHELL SORT - Insertion Sort on Steroids!
 * ========================================
 * 
 * WHAT IS SHELL SORT?
 * Imagine you have a messy bookshelf. Instead of organizing books one by one (like Insertion Sort),
 * you first organize books that are far apart (every 4th book), then closer books (every 2nd book),
 * and finally adjacent books. This is Shell Sort - it's like doing Insertion Sort multiple times
 * with decreasing "gaps" between elements!
 * 
 * REAL-WORLD ANALOGY:
 * Think of organizing students by height. First, compare students 5 positions apart and swap if needed.
 * Then compare students 2 positions apart. Finally, compare neighbors. By the time you do the final
 * comparison, most students are close to their correct positions, making it super fast!
 * 
 * HOW IT WORKS - STEP BY STEP:
 * 
 * Starting Array: [20, 35, -15, 7, 55, 1, -22]
 * Array length = 7, so gaps will be: 3, then 1
 * 
 * ═══════════════════════════════════════════════════════════════
 * PHASE 1: GAP = 3 (Compare elements 3 positions apart)
 * ═══════════════════════════════════════════════════════════════
 * 
 * Compare positions 0 and 3:  20 vs 7   → [7, 35, -15, 20, 55, 1, -22]
 * Compare positions 1 and 4:  35 vs 55  → no swap needed
 * Compare positions 2 and 5:  -15 vs 1  → no swap needed
 * Compare positions 3 and 6:  20 vs -22 → [-22, 35, -15, 20, 55, 1, 7]
 * 
 * After GAP=3: [7, 1, -15, -22, 35, 20, 55]
 * 
 * Notice: Large elements moved far to the right quickly!
 *         Small elements moved far to the left quickly!
 * 
 * ═══════════════════════════════════════════════════════════════
 * PHASE 2: GAP = 1 (Regular Insertion Sort on nearly-sorted array)
 * ═══════════════════════════════════════════════════════════════
 * 
 * Now the array is MUCH closer to being sorted!
 * Insertion Sort will be very fast because elements are nearly in place.
 * 
 * Final Result: [-22, -15, 1, 7, 20, 35, 55]
 * 
 * WHY IS IT FASTER?
 * ┌──────────────────┬─────────────────┬────────────────────┐
 * │ Regular Insertion│ Shell Sort      │ Result             │
 * ├──────────────────┼─────────────────┼────────────────────┤
 * │ Moves element    │ Large gaps move │ Fewer total moves! │
 * │ one position at  │ elements far    │                    │
 * │ a time           │ quickly         │                    │
 * └──────────────────┴─────────────────┴────────────────────┘
 * 
 * VISUAL EXAMPLE WITH GAPS:
 * 
 * Array: [9, 8, 7, 6, 5, 4, 3, 2, 1]  (worst case for insertion sort!)
 * 
 * Gap = 4:  [9, 8, 7, 6, 5, 4, 3, 2, 1]
 *           Compare: 9 vs 5 → [5, 8, 7, 6, 9, 4, 3, 2, 1]
 *           Compare: 8 vs 4 → [5, 4, 7, 6, 9, 8, 3, 2, 1]
 *           Compare: 7 vs 3 → [5, 4, 3, 6, 9, 8, 7, 2, 1]
 *           Compare: 6 vs 2 → [5, 4, 3, 2, 9, 8, 7, 6, 1]
 *           Compare: 9 vs 1 → [5, 4, 3, 2, 1, 8, 7, 6, 9]
 * 
 * Gap = 2:  [5, 4, 3, 2, 1, 8, 7, 6, 9]
 *           More sorting with gap=2...
 *           [3, 2, 1, 4, 5, 6, 7, 8, 9]
 * 
 * Gap = 1:  Final insertion sort on nearly-sorted array - FAST!
 *           [1, 2, 3, 4, 5, 6, 7, 8, 9]
 * 
 * KEY CHARACTERISTICS:
 * ✓ Variation of Insertion Sort
 * ✓ Uses "gap sequence" (starts large, decreases to 1)
 * ✓ Each pass does Insertion Sort with different gap
 * ✓ Final pass is always regular Insertion Sort (gap=1)
 * ✓ In-place sorting (no extra array needed)
 * ✓ Adaptive (faster on partially sorted data)
 * 
 * TIME & SPACE COMPLEXITY:
 * ┌─────────────┬──────────────┬──────────┬─────────────────────┐
 * │ Case        │ Time         │ Space    │ Why?                │
 * ├─────────────┼──────────────┼──────────┼─────────────────────┤
 * │ Best        │ O(n log n)   │ O(1)     │ Already sorted      │
 * │ Average     │ O(n^1.5)     │ O(1)     │ Depends on gap seq  │
 * │ Worst       │ O(n²)        │ O(1)     │ Poor gap sequence   │
 * └─────────────┴──────────────┴──────────┴─────────────────────┘
 * 
 * GAP SEQUENCES (Different ways to choose gaps):
 * 
 * 1. Shell's Original: n/2, n/4, n/8, ..., 1
 *    - Simple but not optimal
 *    - This is what our code uses!
 * 
 * 2. Knuth's Sequence: 1, 4, 13, 40, 121, ... (3k+1)/2
 *    - Better performance than Shell's
 *    - Time complexity: O(n^1.5)
 * 
 * 3. Sedgewick's Sequence: 1, 5, 19, 41, 109, ...
 *    - Even better performance
 *    - Time complexity: O(n^1.33)
 * 
 * WHEN TO USE:
 * ✓ Medium-sized datasets (100-5000 elements)
 * ✓ When you want better than O(n²) but don't need O(n log n)
 * ✓ When memory is limited (in-place)
 * ✓ When code simplicity matters (simpler than Quick/Merge Sort)
 * ✓ Embedded systems with limited resources
 * ✓ When you want to improve on Insertion Sort
 * 
 * WHEN NOT TO USE:
 * ✗ Very large datasets (Quick/Merge Sort better)
 * ✗ When you need guaranteed O(n log n)
 * ✗ When you need stable sorting
 * ✗ When consistency matters (performance depends on gap sequence)
 * 
 * INTERVIEW QUESTIONS & SOLUTIONS:
 * 
 * Q1: Why is Shell Sort faster than regular Insertion Sort?
 * A: Insertion Sort is slow because it moves elements ONE position at a time.
 *    If an element needs to move 100 positions, that's 100 swaps!
 *    
 *    Shell Sort moves elements in LARGE JUMPS first (gap=50, then 25, etc.)
 *    By the time gap=1 (regular insertion sort), elements are close to their
 *    final positions, so very few moves are needed!
 *    
 *    Example: Moving 1 to the front of [100, 99, 98, ..., 2, 1]
 *    - Insertion Sort: 99 swaps
 *    - Shell Sort: Maybe 4-5 swaps with good gap sequence!
 * 
 * Q2: Is Shell Sort stable?
 * A: NO! Elements with equal values can swap positions.
 *    Example with gap=2: [(3,"a"), 1, (3,"b")]
 *    After sorting: [(3,"b"), 1, (3,"a")] ← Order changed!
 *    
 *    This happens because we compare elements that are gap positions apart,
 *    not just adjacent elements.
 * 
 * Q3: What's the best gap sequence to use?
 * A: It depends on your data! But here are recommendations:
 *    
 *    - For simplicity: Shell's original (n/2, n/4, ..., 1)
 *    - For better performance: Knuth's (1, 4, 13, 40, ...)
 *      Formula: gap = (gap * 3) + 1
 *      
 *      List<Integer> gaps = new ArrayList<>();
 *      int gap = 1;
 *      while (gap < array.length / 3) {
 *          gaps.add(gap);
 *          gap = gap * 3 + 1;
 *      }
 *    
 *    - For best performance: Sedgewick's sequence
 *    
 *    IMPORTANT: Always end with gap=1 to ensure complete sorting!
 * 
 * Q4: Can Shell Sort be used for Linked Lists?
 * A: Not efficiently! Shell Sort needs random access to elements that are
 *    gap positions apart. With linked lists, you'd have to traverse the list
 *    repeatedly, making it very slow.
 *    
 *    For linked lists, use Merge Sort or Insertion Sort instead.
 * 
 * Q5: INTERVIEW: Optimize Shell Sort implementation
 * A: Here's an optimized version with better gap sequence:
 *    
 *    public void shellSortOptimized(int[] array) {
 *        int n = array.length;
 *        
 *        // Generate Knuth's gap sequence
 *        int gap = 1;
 *        while (gap < n / 3) {
 *            gap = 3 * gap + 1;  // 1, 4, 13, 40, 121, ...
 *        }
 *        
 *        // Decrease gap each iteration
 *        while (gap >= 1) {
 *            // Insertion sort with current gap
 *            for (int i = gap; i < n; i++) {
 *                int temp = array[i];
 *                int j = i;
 *                while (j >= gap && array[j - gap] > temp) {
 *                    array[j] = array[j - gap];
 *                    j -= gap;
 *                }
 *                array[j] = temp;
 *            }
 *            gap /= 3;  // Move to next gap in sequence
 *        }
 *    }
 * 
 * Q6: When would you choose Shell Sort over Quick Sort?
 * A: Choose Shell Sort when:
 *    1. Code simplicity is more important than optimal speed
 *    2. You're working with embedded systems (simpler to implement)
 *    3. Data size is small-to-medium (100-5000 elements)
 *    4. You want to avoid Quick Sort's O(n²) worst case
 *    5. Stack space is limited (Shell Sort is iterative, not recursive)
 * 
 * Q7: LEETCODE-STYLE: Sort array with many duplicates
 * A: Shell Sort can be adapted for this, but Three-Way Quick Sort is better:
 *    
 *    // Shell Sort works but isn't optimal for many duplicates
 *    // Better approach: Three-Way Partitioning
 *    public void sort(int[] nums) {
 *        threeWayQuickSort(nums, 0, nums.length - 1);
 *    }
 *    
 *    void threeWayQuickSort(int[] a, int lo, int hi) {
 *        if (hi <= lo) return;
 *        int lt = lo, gt = hi, i = lo;
 *        int pivot = a[lo];
 *        
 *        while (i <= gt) {
 *            if (a[i] < pivot) swap(a, lt++, i++);
 *            else if (a[i] > pivot) swap(a, i, gt--);
 *            else i++;
 *        }
 *        threeWayQuickSort(a, lo, lt - 1);
 *        threeWayQuickSort(a, gt + 1, hi);
 *    }
 * 
 * Q8: Can Shell Sort be parallelized?
 * A: Yes! Each gap-separated subsequence can be sorted independently:
 *    With gap=3: Sort positions [0,3,6,...], [1,4,7,...], [2,5,8,...] in parallel
 *    
 *    But the overhead usually isn't worth it for practical purposes.
 * 
 * COMPARISON WITH OTHER SORTS:
 * ┌──────────────┬───────────┬─────────┬──────────┬────────────┐
 * │ Algorithm    │ Time      │ Space   │ Stable?  │ Complexity │
 * ├──────────────┼───────────┼─────────┼──────────┼────────────┤
 * │ Shell Sort   │ O(n^1.5)  │ O(1)    │ No       │ Medium     │
 * │ Insertion    │ O(n²)     │ O(1)    │ Yes      │ Simple     │
 * │ Quick Sort   │ O(n logn) │ O(logn) │ No       │ Complex    │
 * │ Merge Sort   │ O(n logn) │ O(n)    │ Yes      │ Complex    │
 * │ Heap Sort    │ O(n logn) │ O(1)    │ No       │ Complex    │
 * └──────────────┴───────────┴─────────┴──────────┴────────────┘
 * 
 * Shell Sort occupies a sweet spot: faster than O(n²) simple sorts,
 * simpler than O(n log n) complex sorts!
 * 
 * REAL-WORLD USAGE:
 * - Used in uClibc library (embedded Linux)
 * - Linux kernel uses it for some sorting tasks
 * - Good for sorting moderate-sized arrays in embedded systems
 * - Teaching tool to understand gap-based sorting
 * 
 * FUN FACTS:
 * - Invented by Donald Shell in 1959
 * - First algorithm to break the O(n²) barrier for simple sorting
 * - The gap sequence problem is still being researched!
 * - No one has proven the exact time complexity for all gap sequences
 * - It's one of the oldest sorting algorithms still in use today
 */

public class Main {

    public static void main(String[] args) {
        int[] intArray = {20,35, -15, 7, 55, 1, -22};
    public static void main(String[] args) {
        int[] intArray = {20,35, -15, 7, 55, 1, -22};

        // SHELL SORT IMPLEMENTATION
        // Outer loop: Decrease gap size each iteration (gap = length/2, then gap/2, ...)
        // Start with large gap, then gradually reduce to 1 (final pass is insertion sort)
        for(int gap = intArray.length/2; gap>0; gap/=2){

            // Inner loops: Do insertion sort with current gap
            // Start from position 'gap' and insert each element into its correct position
            for(int i =gap; i<intArray.length; i++){
                // Save the element we want to insert
                int newElement = intArray[i];
                int j=i;

                // Shift elements that are 'gap' positions apart
                // Continue while:
                // 1. We haven't gone too far left (j >= gap)
                // 2. Element 'gap' positions back is greater than our new element
                while(j>=gap && intArray[j-gap]>newElement){
                    intArray[j] = intArray[j-gap];  // Shift element forward by gap
                    j-=gap;  // Move backwards by gap
                }
                
                // Insert the element at its correct position
                intArray[j] = newElement;
            }
        }

        // Print the sorted array
        for(int i =0; i<intArray.length; i++){
            System.out.println(intArray[i]);
        }
    }
}
