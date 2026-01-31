package com.company.heap;

/**
 * MAX HEAP IMPLEMENTATION
 * =======================
 * 
 * A Max Heap is like a family tree where parents are always "bigger" than
 * their children. The biggest person is always at the top (the root)!
 * 
 * COMPLETE BINARY TREE PROPERTY:
 * Every level is completely filled except possibly the last level,
 * which is filled from left to right.
 * 
 *     VALID Complete Binary Tree:     INVALID (not complete):
 *            80                              80
 *           /  \                            /  \
 *          75   60                        75   60
 *         /  \                              \    
 *        68   55                             55
 *     
 * WHY USE AN ARRAY?
 * Arrays are perfect for complete binary trees because there are no gaps!
 * We can calculate parent/child positions with simple math.
 * 
 * INDEX RELATIONSHIPS:
 * Parent of node at index i: (i-1)/2
 * Left child of node at index i: 2i+1
 * Right child of node at index i: 2i+2
 * 
 * HEAP PROPERTY (Max Heap):
 * For any node at index i:
 * heap[i] ≥ heap[2i+1] (if left child exists)
 * heap[i] ≥ heap[2i+2] (if right child exists)
 * 
 * ASCII ART EXAMPLE:
 * 
 *              [80]  ← index 0 (root, maximum)
 *             /    \
 *          [75]    [60]  ← indices 1, 2
 *          / \      / \
 *        [68][55] [40][52]  ← indices 3,4,5,6
 *        /
 *      [67]  ← index 7
 * 
 * Array: [80, 75, 60, 68, 55, 40, 52, 67]
 *         0   1   2   3   4   5   6   7
 */
public class Heap {

    private int[] heap;  // Array to store heap elements
    private int size;    // Number of elements currently in heap

    public Heap(int capacity) {
        heap = new int[capacity];
    }

    public void insert(int value) {   // time complexity: O(logn)
        if (isFull()) {
            throw new IndexOutOfBoundsException("Heap is full");
        }

        heap[size] = value;
        fixHeapAbove(size);
        size++;
    }

    /**
     * delete() - Remove an element at a specific index
     * 
     * PROCESS:
     * 1. Save the value we're deleting
     * 2. Replace it with the LAST element in the heap
     * 3. Decide: bubble up or bubble down?
     *    - If replacement < parent: bubble down
     *    - If replacement > parent: bubble up
     * 4. Restore heap property
     * 
     * EXAMPLE: Delete 75 (index 1) from [80, 75, 60, 68, 55, 40, 52]
     * 
     * Original:
     *         80
     *        /  \
     *      [75]  60  ← Delete this
     *      / \   / \
     *     68 55 40 52
     * 
     * Step 1: Replace with last element (52)
     *         80
     *        /  \
     *      [52]  60  ← 52 moved here
     *      / \   /
     *     68 55 40
     * 
     * Step 2: Check: 52 < 80 (parent), so bubble DOWN
     * Compare 52 with children (68 and 55)
     * 68 > 52, so swap!
     *         80
     *        /  \
     *      [68]  60
     *      / \   /
     *    [52] 55 40
     * 
     * Done! Heap property restored.
     * 
     * Time Complexity: O(log n) for the bubble operation
     * Note: If you need to FIND the element first, it's O(n) total!
     * 
     * @param index The index of the element to delete
     * @return The deleted value
     * @throws IndexOutOfBoundsException if heap is empty
     */
    public int delete(int index) { // O(log n) for deletion, O(n) if finding index first
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is Empty");
        }

        int parent = getParent(index);
        int deletedValue = heap[index];

        // REPLACEMENT: Use the last element (maintains complete tree property)
        heap[index] = heap[size-1];  // Last element moves to deleted position

        // DECIDE: Bubble up or down?
        if (index == 0 || heap[index] < heap[parent]) {
            fixHeapBelow(index, size-1);  // Replacement is smaller, bubble down
        }
        else {
            fixHeapAbove(index);  // Replacement is larger, bubble up
        }

        size--;  // Decrease size
        return deletedValue;

    }

    /**
     * sort() - Sort the heap in ascending order (Heap Sort algorithm)
     * 
     * HEAP SORT PROCESS:
     * 1. Swap root (max) with last element
     * 2. Reduce heap size by 1 (exclude the sorted max)
     * 3. Fix heap property for the reduced heap
     * 4. Repeat until heap is size 1
     * 
     * EXAMPLE: Sort [80, 75, 60, 68, 55]
     * 
     * Initial:  [80, 75, 60, 68, 55]
     *           Swap 80 and 55 → [55, 75, 60, 68 | 80]
     *           Fix heap → [75, 68, 60, 55 | 80]
     * 
     * Next:     [75, 68, 60, 55 | 80]
     *           Swap 75 and 55 → [55, 68, 60 | 75, 80]
     *           Fix heap → [68, 55, 60 | 75, 80]
     * 
     * Continue: [68, 55, 60 | 75, 80]
     *           Swap 68 and 60 → [60, 55 | 68, 75, 80]
     *           Fix heap → [60, 55 | 68, 75, 80]
     * 
     * Final:    [55 | 60, 68, 75, 80]
     *           Result: [55, 60, 68, 75, 80] ✓ SORTED!
     * 
     * Time Complexity: O(n log n)
     * - We do n iterations
     * - Each fixHeapBelow is O(log n)
     * - Total: n * log n
     * 
     * Space Complexity: O(1) - sorts in place!
     * 
     * WHY HEAP SORT IS COOL:
     * - Guaranteed O(n log n) even in worst case
     * - No extra memory needed (unlike merge sort)
     * - Not stable (equal elements might swap order)
     */
    public void sort() {//  time complexity: O(nlogn)
        int lastHeapIndex = size-1;
        for (int i =0; i< lastHeapIndex; i++) {
            // Swap root (max) with last unsorted element
            int tmp = heap[0];
            heap[0] = heap[lastHeapIndex - i];
            heap[lastHeapIndex - i] = tmp;

            // Fix heap for the reduced heap (excluding sorted elements)
            fixHeapBelow(0,lastHeapIndex - i - 1);
        }
    }


    /**
     * peek() - Get the maximum value WITHOUT removing it
     * 
     * In a max heap, the maximum is always at the root (index 0)!
     * This is the main advantage of heaps - instant access to max/min.
     * 
     * Time Complexity: O(1) - constant time!
     * 
     * @return The maximum value in the heap
     * @throws IndexOutOfBoundsException if heap is empty
     */
    public int peek() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return heap[0];  // Root is always the maximum
    }

    /**
     * fixHeapAbove() - Bubble up to restore heap property
     * 
     * This is called "heapify up" or "percolate up".
     * We keep swapping with the parent until heap property is satisfied.
     * 
     * VISUALIZATION:
     * Suppose we insert 90 at index 7:
     * 
     *         80             80             90  ← Done!
     *        /  \           /  \           /  \
     *      75    60  →   [90]   60  →    80   60
     *     / \           / \             / \
     *    68  55        68 [75]         68  75
     *   /                 /               /
     * [90]               55              55
     * 
     * Step-by-step:
     * 1. 90 > 75 (parent) → swap
     * 2. 90 > 80 (new parent) → swap
     * 3. At root, stop!
     * 
     * WHY THIS WORKS:
     * - We only move ONE value up
     * - Everything else is already a valid heap
     * - We stop when value ≤ parent or reach root
     * 
     * Time Complexity: O(log n) - height of tree
     * 
     * @param index The index to start bubbling up from
     */
    private void fixHeapAbove(int index) {
        int newValue = heap[index];  // Save the value we're bubbling up
        
        // Keep going up while:
        // 1. Not at root (index > 0)
        // 2. Value is greater than parent (violates heap property)
        while (index > 0 && newValue > heap[getParent(index)]) {
            heap[index] = heap[getParent(index)];  // Move parent down
            index = getParent(index);               // Move up to parent's position
        }
        
        heap[index] = newValue;  // Place value at final position
    }

    /**
     * fixHeapBelow() - Bubble down to restore heap property
     * 
     * This is called "heapify down" or "percolate down".
     * We swap with the LARGER child (to maintain max heap property).
     * 
     * VISUALIZATION:
     * Suppose we delete root and replace with 40:
     * 
     *        [40]            75             75
     *        /  \           /  \           /  \
     *      75    60  →   [40]   60  →    68   60
     *     / \           / \             / \
     *    68  55        68  55         [40] 55
     * 
     * Step-by-step:
     * 1. Compare 40 with children 75 and 60
     *    75 is larger, so swap 40 with 75
     * 2. Compare 40 with children 68 and 55
     *    68 is larger, so swap 40 with 68
     * 3. 40 has no children, stop!
     * 
     * KEY INSIGHT:
     * Always swap with the LARGER child!
     * Why? To ensure parent ≥ both children after swap.
     * 
     * COMPLETE TREE CASES:
     * - No children: Stop
     * - Only left child: Swap with left if needed
     * - Both children: Swap with larger child if needed
     * 
     * Time Complexity: O(log n) - height of tree
     * 
     * @param index The index to start bubbling down from
     * @param lastHeapIndex The last valid index in the heap
     */
    private void fixHeapBelow(int index, int lastHeapIndex) {

        int childToSwap;

        while (index <= lastHeapIndex) {
            int leftChild = getChild(index, true);
            int rightChild = getChild(index, false);
            
            // Check if left child exists (complete tree property)
            if (leftChild <= lastHeapIndex) {
                
                // CASE 1: Only left child exists (no right child)
                if (rightChild > lastHeapIndex) {
                    childToSwap = leftChild;
                } 
                // CASE 2: Both children exist - choose the LARGER one
                else {
                    childToSwap = (heap[leftChild] > heap[rightChild] ? leftChild : rightChild);
                }

                // Check if we need to swap
                if (heap[index] < heap[childToSwap]) {
                    // Swap parent with larger child
                    int tmp = heap[index];
                    heap[index] = heap[childToSwap];
                    heap[childToSwap] = tmp;
                }
                else {
                    break;  // Heap property satisfied, done!
                }
                
                // Move down to the swapped child's position
                index = childToSwap;
            }
            else {
                break;  // No children, we're at a leaf
            }
        }
    }

    /**
     * printHeap() - Display all elements in level-order
     * 
     * This shows the array representation.
     * To see the tree structure, you'd need to add spacing/levels.
     */
    public void printHeap() {
        for (int i =0;i<size; i++) {
            System.out.print(heap[i]);
            System.out.print(", ");
        }
        System.out.println();

    }

    /**
     * isFull() - Check if heap has reached capacity
     * @return true if heap is full
     */
    public boolean isFull () {
        return size == heap.length;
    }

    /**
     * getParent() - Calculate parent index
     * 
     * Formula: (index - 1) / 2
     * Example: Parent of index 5 is (5-1)/2 = 2
     * 
     * @param index The child's index
     * @return The parent's index
     */
    public int getParent (int index) {
        return (index - 1) / 2;
    }

    /**
     * isEmpty() - Check if heap has no elements
     * @return true if heap is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * getChild() - Calculate child index
     * 
     * Formula: 
     * - Left child: 2*i + 1
     * - Right child: 2*i + 2
     * 
     * Example: Children of index 1 are:
     * - Left: 2*1+1 = 3
     * - Right: 2*1+2 = 4
     * 
     * @param index The parent's index
     * @param left true for left child, false for right child
     * @return The child's index
     */
    public int getChild(int index, boolean left) {
        return 2 * index + (left?1:2);
    }

}

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * INTERVIEW QUESTIONS & ANSWERS
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * Q1: Find the Kth largest element in an unsorted array
 * ───────────────────────────────────────────────────────────────────────────
 * ANSWER:
 * Use a MIN heap of size k!
 * 
 * int findKthLargest(int[] nums, int k) {
 *     PriorityQueue<Integer> minHeap = new PriorityQueue<>();
 *     
 *     for (int num : nums) {
 *         minHeap.add(num);
 *         if (minHeap.size() > k) {
 *             minHeap.poll();  // Remove smallest
 *         }
 *     }
 *     
 *     return minHeap.peek();  // Top of heap is kth largest
 * }
 * 
 * HOW IT WORKS:
 * Example: nums = [3,2,1,5,6,4], k = 2
 * 
 * Process:
 * - Add 3: [3]
 * - Add 2: [2,3]
 * - Add 1: [1,2,3] → size > 2, remove 1 → [2,3]
 * - Add 5: [2,3,5] → size > 2, remove 2 → [3,5]
 * - Add 6: [3,5,6] → size > 2, remove 3 → [5,6]
 * - Add 4: [4,5,6] → size > 2, remove 4 → [5,6]
 * 
 * Result: heap.peek() = 5 (2nd largest) ✓
 * 
 * WHY IT WORKS:
 * Min heap keeps k largest elements. The smallest of those k is the kth largest!
 * 
 * Time: O(n log k), Space: O(k)
 * 
 * ───────────────────────────────────────────────────────────────────────────
 * Q2: Merge k sorted lists
 * ───────────────────────────────────────────────────────────────────────────
 * ANSWER:
 * Use a min heap to track the smallest element from each list!
 * 
 * class ListNode {
 *     int val;
 *     ListNode next;
 * }
 * 
 * ListNode mergeKLists(ListNode[] lists) {
 *     PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
 *         (a, b) -> a.val - b.val  // Compare by value
 *     );
 *     
 *     // Add first node from each list
 *     for (ListNode head : lists) {
 *         if (head != null) {
 *             minHeap.add(head);
 *         }
 *     }
 *     
 *     ListNode dummy = new ListNode(0);
 *     ListNode current = dummy;
 *     
 *     while (!minHeap.isEmpty()) {
 *         // Get smallest node
 *         ListNode smallest = minHeap.poll();
 *         current.next = smallest;
 *         current = current.next;
 *         
 *         // Add next node from same list
 *         if (smallest.next != null) {
 *             minHeap.add(smallest.next);
 *         }
 *     }
 *     
 *     return dummy.next;
 * }
 * 
 * EXAMPLE:
 * Lists: [1→4→5], [1→3→4], [2→6]
 * 
 * Heap operations:
 * 1. Initial: [1(L1), 1(L2), 2(L3)]
 * 2. Take 1(L1), add 4 → [1(L2), 2(L3), 4(L1)]
 * 3. Take 1(L2), add 3 → [2(L3), 3(L2), 4(L1)]
 * 4. Take 2(L3), add 6 → [3(L2), 4(L1), 6(L3)]
 * 5. Continue...
 * 
 * Result: 1→1→2→3→4→4→5→6 ✓
 * 
 * Time: O(N log k) where N=total nodes, k=number of lists
 * Space: O(k) for the heap
 * 
 * ───────────────────────────────────────────────────────────────────────────
 * Q3: Find median from data stream
 * ───────────────────────────────────────────────────────────────────────────
 * ANSWER:
 * Use TWO heaps - a max heap and a min heap!
 * 
 * class MedianFinder {
 *     PriorityQueue<Integer> maxHeap;  // Left half (smaller numbers)
 *     PriorityQueue<Integer> minHeap;  // Right half (larger numbers)
 *     
 *     public MedianFinder() {
 *         maxHeap = new PriorityQueue<>((a,b) -> b-a);  // Max heap
 *         minHeap = new PriorityQueue<>();               // Min heap
 *     }
 *     
 *     public void addNum(int num) {
 *         // Always add to maxHeap first
 *         maxHeap.add(num);
 *         
 *         // Balance: move largest from maxHeap to minHeap
 *         minHeap.add(maxHeap.poll());
 *         
 *         // Keep maxHeap size ≥ minHeap size
 *         if (maxHeap.size() < minHeap.size()) {
 *             maxHeap.add(minHeap.poll());
 *         }
 *     }
 *     
 *     public double findMedian() {
 *         if (maxHeap.size() > minHeap.size()) {
 *             return maxHeap.peek();  // Odd count
 *         }
 *         return (maxHeap.peek() + minHeap.peek()) / 2.0;  // Even count
 *     }
 * }
 * 
 * VISUALIZATION:
 * Stream: 1, 2, 3, 4, 5
 * 
 * After 1: maxHeap=[1], minHeap=[]      → median = 1
 * After 2: maxHeap=[1], minHeap=[2]      → median = 1.5
 * After 3: maxHeap=[2,1], minHeap=[3]    → median = 2
 * After 4: maxHeap=[2,1], minHeap=[3,4]  → median = 2.5
 * After 5: maxHeap=[3,2,1], minHeap=[4,5] → median = 3
 * 
 *        maxHeap (left half)  |  minHeap (right half)
 *              [3]             |       [4]
 *             /   \            |      / \
 *           [2]   [1]          |    [5]  
 *     all ≤ median              |  all ≥ median
 * 
 * WHY IT WORKS:
 * - MaxHeap stores smaller half (max on top)
 * - MinHeap stores larger half (min on top)
 * - Median is either top of maxHeap or average of both tops
 * 
 * Time: O(log n) per add, O(1) for findMedian
 * Space: O(n)
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * KEY TAKEAWAYS FOR INTERVIEWS:
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * 1. HEAP PROPERTY: Parent ≥ Children (max heap) or Parent ≤ Children (min heap)
 * 
 * 2. COMPLETE BINARY TREE: All levels filled except last (filled left-to-right)
 * 
 * 3. ARRAY REPRESENTATION:
 *    - Parent of i: (i-1)/2
 *    - Left child: 2i+1
 *    - Right child: 2i+2
 * 
 * 4. TIME COMPLEXITIES:
 *    - Insert/Delete: O(log n)
 *    - Peek: O(1)
 *    - Build heap: O(n)
 *    - Heap sort: O(n log n)
 * 
 * 5. WHEN TO USE HEAPS:
 *    - Finding kth largest/smallest
 *    - Maintaining running median
 *    - Priority queue operations
 *    - Merging k sorted lists
 *    - Scheduling tasks by priority
 * 
 * 6. MAX HEAP vs MIN HEAP:
 *    - Max heap: Quick access to largest
 *    - Min heap: Quick access to smallest
 *    - Can use both together (median finder!)
 * 
 * 7. HEAP vs BALANCED BST:
 *    - Heap: Faster insertion O(log n), only peek at top
 *    - BST: Can find any element O(log n), more versatile
 * 
 * 8. COMMON INTERVIEW MISTAKES:
 *    - Forgetting heap is NOT sorted (only parent-child relationship)
 *    - Confusing min and max heap
 *    - Not maintaining complete tree property
 *    - Wrong index calculations for parent/children
 */
