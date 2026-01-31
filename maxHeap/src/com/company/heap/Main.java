package com.company.heap;

/**
 * MAX HEAP DATA STRUCTURE
 * ========================
 * 
 * WHAT IS A HEAP?
 * A heap is a special type of binary tree where:
 * 1. It's COMPLETE: All levels are filled except possibly the last,
 *    which is filled from left to right
 * 2. It satisfies the HEAP PROPERTY: Every parent is larger (or smaller)
 *    than its children
 * 
 * MAX HEAP vs MIN HEAP:
 * - MAX HEAP: Parent ≥ Children (biggest value at top)
 * - MIN HEAP: Parent ≤ Children (smallest value at top)
 * 
 * We're implementing a MAX HEAP here!
 * 
 * VISUAL REPRESENTATION:
 * 
 *                    80  ← Root (largest value)
 *                   /  \
 *                 75    60
 *                / \    / \
 *               68  55 40  52
 *              /
 *             67
 * 
 * ARRAY REPRESENTATION:
 * Index:  0   1   2   3   4   5   6   7
 * Value: [80][75][60][68][55][40][52][67]
 * 
 * PARENT-CHILD RELATIONSHIP FORMULAS:
 * - Parent of index i: (i-1)/2
 * - Left child of index i: 2*i + 1
 * - Right child of index i: 2*i + 2
 * 
 * EXAMPLE:
 * For element at index 1 (value 75):
 * - Parent: (1-1)/2 = 0 (value 80) ✓
 * - Left child: 2*1+1 = 3 (value 68) ✓
 * - Right child: 2*1+2 = 4 (value 55) ✓
 * 
 * TIME COMPLEXITY:
 * - Insert: O(log n) - bubble up the tree
 * - Delete: O(log n) - bubble down the tree
 * - Peek (get max): O(1) - just look at root
 * - Heapify: O(n) - build heap from array
 * - Heap Sort: O(n log n) - sort using heap
 * 
 * SPACE COMPLEXITY: O(n) for storing n elements
 * 
 * REAL-WORLD USES:
 * - Priority Queues (emergency room: most urgent patient first)
 * - Job Scheduling (run highest priority task first)
 * - Finding kth largest/smallest element
 * - Median finding in data streams
 * - Graph algorithms (Dijkstra's, Prim's)
 */
public class Main {

    public static void main(String[] args) {

        // CREATE HEAP
        // Think of this as building a priority system where bigger numbers
        // have higher priority (like urgency levels: 10=emergency, 1=can wait)
        Heap heap = new Heap(10);

        // INSERT ELEMENTS
        // Watch how each number finds its correct position!
        // After each insert, the heap property is maintained
        
        System.out.println("Building the heap step by step:");
        System.out.println("================================\n");
        
        heap.insert(80);  // 80 becomes root (it's the largest so far)
        System.out.println("After inserting 80: [80]");
        
        heap.insert(75);  // 75 goes to left child of 80
        System.out.println("After inserting 75: [80, 75]");
        
        heap.insert(60);  // 60 goes to right child of 80
        System.out.println("After inserting 60: [80, 75, 60]");
        
        heap.insert(68);  // 68 bubbles up! Bigger than 75, becomes left child of 80
        heap.insert(55);  // 55 becomes right child of 75 (now at index 1)
        heap.insert(40);  // 40 becomes left child of 60
        heap.insert(52);  // 52 becomes right child of 60
        heap.insert(67);  // 67 becomes left child of 68
        
        System.out.println("\nFinal heap:");
        heap.printHeap();
        
        /*
         * VISUAL REPRESENTATION OF OUR HEAP:
         * 
         *                    80
         *                   /  \
         *                 75    60
         *                / \    / \
         *               68  55 40  52
         *              /
         *             67
         * 
         * Array: [80, 75, 60, 68, 55, 40, 52, 67]
         */

        // PEEK - Look at maximum without removing
        System.out.println("\nMaximum value (peek): " + heap.peek());

        // DELETE EXAMPLES (uncommented for demonstration)
        // heap.delete(5);  // Delete element at index 5 (value 40)
        // heap.printHeap();
        //
        // heap.delete(0);  // Delete root (removes maximum: 80)
        // heap.printHeap();
        //
        // System.out.println(heap.peek());  // New maximum

        // HEAP SORT
        // Convert the heap into a sorted array!
        // This repeatedly removes the max and rebuilds the heap
        System.out.println("\nSorting the heap (ascending order):");
        heap.sort();
        heap.printHeap();
        
        System.out.println("\nNotice: After sorting, elements are in ascending order!");
        System.out.println("This is because heap sort removes the max repeatedly.");

    }
}
