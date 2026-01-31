package com.company.insertionsort;

/*
 * ========================================
 * INSERTION SORT - Building Up One by One
 * ========================================
 * 
 * WHAT IS INSERTION SORT?
 * Think of how you sort playing cards in your hand. You pick up one card at a time
 * and insert it into its correct position among the cards you're already holding.
 * That's exactly how Insertion Sort works!
 * 
 * REAL-WORLD ANALOGY:
 * Imagine you're organizing your bookshelf. You start with an empty sorted section.
 * You pick up one book at a time from the pile and slide it into the correct spot
 * on the shelf, moving other books to make room. Eventually, all books are sorted!
 * 
 * HOW IT WORKS - STEP BY STEP:
 * 
 * Starting Array: [20, 35, -15, 7, 55, 1, -22]
 * 
 * Step 1: [20] | 35, -15, 7, 55, 1, -22
 *         First element (20) is already "sorted"
 *         
 * Step 2: [20, 35] | -15, 7, 55, 1, -22
 *         Insert 35: Compare with 20 → 35 > 20, so it stays
 *         
 * Step 3: [-15, 20, 35] | 7, 55, 1, -22
 *         Insert -15: Shift 35 and 20 to the right, insert -15 at start
 *         
 * Step 4: [-15, 7, 20, 35] | 55, 1, -22
 *         Insert 7: Shift 35 and 20, insert 7 between -15 and 20
 *         
 * Step 5: [-15, 7, 20, 35, 55] | 1, -22
 *         Insert 55: Already in correct position
 *         
 * Step 6: [-15, 1, 7, 20, 35, 55] | -22
 *         Insert 1: Shift several elements, insert after -15
 *         
 * Step 7: [-22, -15, 1, 7, 20, 35, 55] | (done!)
 *         Insert -22: Shift all elements, insert at start
 * 
 * KEY CHARACTERISTICS:
 * ✓ Divides array into: SORTED (left) | UNSORTED (right)
 * ✓ Grows sorted portion one element at a time
 * ✓ Elements "slide" to make room for new insertions
 * ✓ GREAT for nearly-sorted data!
 * ✓ Stable sort (maintains order of equal elements)
 * 
 * TIME & SPACE COMPLEXITY:
 * ┌─────────────┬──────────┬──────────┬─────────────────────┐
 * │ Case        │ Time     │ Space    │ Why?                │
 * ├─────────────┼──────────┼──────────┼─────────────────────┤
 * │ Best        │ O(n)     │ O(1)     │ Already sorted!     │
 * │ Average     │ O(n²)    │ O(1)     │ Random order        │
 * │ Worst       │ O(n²)    │ O(1)     │ Reverse sorted      │
 * └─────────────┴──────────┴──────────┴─────────────────────┘
 * 
 * WHEN TO USE:
 * ✓ Small datasets (10-30 elements)
 * ✓ Nearly sorted data (BEST CHOICE! Can be O(n))
 * ✓ Online sorting (sorting data as it arrives)
 * ✓ When you need a stable sort
 * ✓ When simplicity matters
 * 
 * WHEN NOT TO USE:
 * ✗ Large datasets with random order
 * ✗ Reverse-sorted data (worst case!)
 * ✗ When you need guaranteed O(n log n) performance
 * 
 * INTERVIEW QUESTIONS & SOLUTIONS:
 * 
 * Q1: Why is Insertion Sort better for nearly-sorted data?
 * A: Because when data is almost sorted, the inner loop does very few shifts!
 *    Already sorted: [1,2,3,4,5] → Only 4 comparisons, 0 shifts → O(n)
 *    Reverse sorted: [5,4,3,2,1] → Many shifts needed → O(n²)
 * 
 * Q2: Is Insertion Sort stable? Why does it matter?
 * A: YES! It's stable because we only shift when element is GREATER (not >=).
 *    Example: [(3,"a"), (3,"b"), (1,"c")]
 *    After sorting: [(1,"c"), (3,"a"), (3,"b")]
 *    The two 3's maintained their original order!
 *    
 *    This matters when sorting by multiple keys, like sorting students
 *    first by grade, then by name - you want to preserve the name order!
 * 
 * Q3: LEETCODE: Sort Colors (Sort array with only 0s, 1s, and 2s)
 * A: While Insertion Sort works, Dutch National Flag is better O(n):
 *    
 *    public void sortColors(int[] nums) {
 *        int low = 0, mid = 0, high = nums.length - 1;
 *        while (mid <= high) {
 *            if (nums[mid] == 0) {
 *                swap(nums, low++, mid++);      // 0 goes to front
 *            } else if (nums[mid] == 2) {
 *                swap(nums, mid, high--);       // 2 goes to back
 *            } else {
 *                mid++;                         // 1 stays in middle
 *            }
 *        }
 *    }
 * 
 * Q4: INTERVIEW: Merge Intervals
 * A: First SORT intervals by start time (can use Insertion Sort for small inputs):
 *    [[1,3],[2,6],[8,10],[15,18]]
 *    
 *    public int[][] merge(int[][] intervals) {
 *        // Sort by start time
 *        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
 *        
 *        List<int[]> merged = new ArrayList<>();
 *        int[] current = intervals[0];
 *        merged.add(current);
 *        
 *        for (int[] interval : intervals) {
 *            if (interval[0] <= current[1]) {
 *                current[1] = Math.max(current[1], interval[1]); // Merge
 *            } else {
 *                current = interval;
 *                merged.add(current);
 *            }
 *        }
 *        return merged.toArray(new int[merged.size()][]);
 *    }
 * 
 * Q5: Can you optimize Insertion Sort?
 * A: YES! Binary Insertion Sort:
 *    - Use binary search to FIND position: O(log n)
 *    - But still need to SHIFT elements: O(n)
 *    - Total: Still O(n²), but fewer comparisons
 *    
 *    Good when comparisons are expensive but shifts are cheap!
 * 
 * Q6: INTERVIEW: Insertion Sort on Linked List
 * A: Actually PERFECT for linked lists - no shifting needed!
 *    
 *    public ListNode insertionSortList(ListNode head) {
 *        ListNode dummy = new ListNode(0);
 *        ListNode current = head;
 *        
 *        while (current != null) {
 *            ListNode prev = dummy;
 *            // Find insertion point
 *            while (prev.next != null && prev.next.val < current.val) {
 *                prev = prev.next;
 *            }
 *            // Insert current between prev and prev.next
 *            ListNode next = current.next;
 *            current.next = prev.next;
 *            prev.next = current;
 *            current = next;
 *        }
 *        return dummy.next;
 *    }
 * 
 * COMPARISON WITH OTHER SORTS:
 * Insertion vs Selection: Insertion is adaptive (faster for nearly-sorted)
 * Insertion vs Bubble: Insertion is generally faster (fewer swaps)
 * Insertion vs Merge: Merge is faster for large data, but Insertion uses less memory
 * 
 * FUN FACT: Insertion Sort is used in advanced algorithms like TimSort (Python's sort)
 * and IntroSort (C++ sort) for small subarrays because it's so efficient on small data!
 */

public class Main {

    public static void main(String[] args) {

        int[] intArray = {20, 35, -15, 7, 55, 1, -22};
        
        // INSERTION SORT IMPLEMENTATION
        // Start from index 1 (first element is already "sorted")
        // For each element, insert it into the correct position in the sorted portion
        for (int firstUnsortedIndex = 1; firstUnsortedIndex< intArray.length; firstUnsortedIndex++){
        // INSERTION SORT IMPLEMENTATION
        // Start from index 1 (first element is already "sorted")
        // For each element, insert it into the correct position in the sorted portion
        for (int firstUnsortedIndex = 1; firstUnsortedIndex< intArray.length; firstUnsortedIndex++){

            // Save the element we want to insert
            int newElement = intArray[firstUnsortedIndex];

            int i ;

            // Shift elements to the right until we find the correct position
            // Keep moving left (i--) while:
            // 1. We haven't reached the start (i > 0)
            // 2. The element to the left is greater than our new element
            for (i=firstUnsortedIndex; i>0 && intArray[i-1]>newElement; i--){
                intArray[i] = intArray[i-1];  // Shift element to the right
            }

            // Insert the new element at the correct position
            intArray[i]=newElement;
        }

        // Print the sorted array
        for(int i =0;i<intArray.length; i++){
            System.out.println(intArray[i]);
        }
    }
}
