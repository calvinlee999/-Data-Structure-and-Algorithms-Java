package com.company.priorityqueue;

import java.util.PriorityQueue;

/**
 * PRIORITY QUEUE - A Heap-Backed Data Structure
 * ==============================================
 * 
 * WHAT IS A PRIORITY QUEUE?
 * A Priority Queue is a special queue where elements are served based on
 * PRIORITY, not arrival order!
 * 
 * Think of it like an emergency room:
 * - Most urgent patient gets treated first (highest priority)
 * - Not first-come, first-served!
 * 
 * REAL-WORLD EXAMPLES:
 * 1. EMERGENCY ROOM: Critical patients before minor injuries
 * 2. CPU SCHEDULING: Important tasks run before background tasks
 * 3. TRAFFIC CONTROL: Emergency vehicles get priority
 * 4. EMAIL INBOX: Important emails marked and shown first
 * 5. GAME AI: Most threatening enemies handled first
 * 
 * HOW IT WORKS (Under the Hood):
 * Java's PriorityQueue uses a MIN HEAP!
 * - Smallest element has highest priority (at the root)
 * - Remove operations always get the minimum element
 * - Elements are ordered by natural ordering or custom comparator
 * 
 * VISUAL REPRESENTATION (Min Heap for [25, -22, 1343, 54, 0, -3492, 429]):
 * 
 *                [-3492]  ← Root (minimum, highest priority)
 *                /      \
 *             [-22]     [0]
 *             /   \     /  \
 *           [54] [25][1343][429]
 * 
 * Array: [-3492, -22, 0, 54, 25, 1343, 429]
 * 
 * BASIC OPERATIONS:
 * - add(element): Add element and maintain heap property - O(log n)
 * - poll(): Remove and return highest priority element - O(log n)
 * - peek(): Look at highest priority WITHOUT removing - O(1)
 * - remove(element): Remove specific element - O(n)
 * 
 * TIME COMPLEXITY:
 * - add/offer: O(log n) - bubble up
 * - poll/remove: O(log n) - bubble down
 * - peek/element: O(1) - just look at root
 * - contains: O(n) - need to search
 * - remove(Object): O(n) - need to find then remove
 * 
 * SPACE COMPLEXITY: O(n) for n elements
 * 
 * MIN HEAP vs MAX HEAP:
 * - Default PriorityQueue: MIN heap (smallest first)
 * - For MAX heap: PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
 * 
 * INTERVIEW INSIGHT:
 * "Why use a heap for priority queue?"
 * Answer: Heap gives us O(log n) for add/remove while maintaining priority,
 * and O(1) for peek. Better than keeping a sorted array (O(n) insert) or
 * unsorted array (O(n) to find min)!
 */
public class Main {

    public static void main(String[] args) {
        
        // CREATE PRIORITY QUEUE (Min Heap)
        // This will keep the SMALLEST number at the front (highest priority)
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();

        System.out.println("Adding elements to Priority Queue:");
        System.out.println("===================================\n");
        
        // ADD ELEMENTS
        // Watch how the heap organizes them by priority (smallest first)
        pq.add(25);
        System.out.println("Added 25");
        
        pq.add(-22);
        System.out.println("Added -22 (now this has highest priority - it's smallest!)");
        
        pq.add(1343);
        System.out.println("Added 1343");
        
        pq.add(54);
        System.out.println("Added 54");
        
        pq.add(0);
        System.out.println("Added 0");
        
        pq.add(-3492);
        System.out.println("Added -3492 (NEW highest priority - most negative!)");
        
        pq.add(429);
        System.out.println("Added 429");
        
        /*
         * After all insertions, internal heap structure:
         * 
         *                [-3492]  ← Root (minimum)
         *                /      \
         *             [-22]     [0]
         *             /   \     /  \
         *           [54] [25][1343][429]
         * 
         * Array representation: [-3492, -22, 0, 54, 25, 1343, 429]
         * Notice: NOT completely sorted! Only heap property maintained.
         */
        
        // PEEK - Look at highest priority without removing
        System.out.println("\nPeek (highest priority): " + pq.peek());
        System.out.println("Expected: -3492 (smallest number) ✓");
        
        // POLL/REMOVE - Get and remove highest priority
        System.out.println("\nRemoving elements in priority order:");
        System.out.println(pq.poll() + " (removed, was -3492)");
        System.out.println("New peek: " + pq.peek() + " (should be -22)");
        
        System.out.println(pq.poll() + " (removed, was -22)");
        System.out.println("New peek: " + pq.peek() + " (should be 0)");
        
        // REMOVE SPECIFIC ELEMENT
        System.out.println("\nRemoving specific element (54): " + pq.remove(54));
        System.out.println("This takes O(n) time - has to find it first!\n");

        // PRINT ALL ELEMENTS
        // WARNING: Array representation doesn't show sorted order!
        // It shows heap's internal array structure
        System.out.println("\nInternal heap array (NOT sorted order!):");
        System.out.println("==========================================");
        Object[] ints = pq.toArray();
        for(Object num: ints) {
            System.out.println(num);
        }
        
        System.out.println("\nTo get elements in sorted order, keep polling:");
        System.out.println("================================================");
        PriorityQueue<Integer> pqCopy = new PriorityQueue<>(pq);
        while (!pqCopy.isEmpty()) {
            System.out.println(pqCopy.poll());
        }
        System.out.println("\nNow they're in ascending order! (Min to Max)");

    }
}

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * INTERVIEW QUESTIONS & ANSWERS
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * Q1: How would you find the k most frequent elements in an array?
 * ───────────────────────────────────────────────────────────────────────────
 * ANSWER:
 * Use a HashMap to count frequencies, then a Min Heap of size k!
 * 
 * List<Integer> topKFrequent(int[] nums, int k) {
 *     // Step 1: Count frequencies
 *     Map<Integer, Integer> count = new HashMap<>();
 *     for (int num : nums) {
 *         count.put(num, count.getOrDefault(num, 0) + 1);
 *     }
 *     
 *     // Step 2: Use min heap (priority by frequency)
 *     PriorityQueue<Integer> pq = new PriorityQueue<>(
 *         (a, b) -> count.get(a) - count.get(b)  // Compare frequencies
 *     );
 *     
 *     // Step 3: Keep only k most frequent
 *     for (int num : count.keySet()) {
 *         pq.add(num);
 *         if (pq.size() > k) {
 *             pq.poll();  // Remove least frequent
 *         }
 *     }
 *     
 *     return new ArrayList<>(pq);
 * }
 * 
 * EXAMPLE:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * 
 * Frequencies: {1:3, 2:2, 3:1}
 * 
 * After processing:
 * - Add 1 (freq=3): pq = [1]
 * - Add 2 (freq=2): pq = [2,1]  (1 has freq 3, 2 has freq 2)
 * - Add 3 (freq=1): pq = [3,2,1] → size > 2, remove 3 → pq = [2,1]
 * 
 * Result: [1, 2] (most frequent) ✓
 * 
 * Time: O(n log k), Space: O(n)
 * 
 * ───────────────────────────────────────────────────────────────────────────
 * Q2: Design a task scheduler with cooldown period
 * ───────────────────────────────────────────────────────────────────────────
 * ANSWER:
 * Use a Max Heap to always run most frequent task, plus a queue for cooldown!
 * 
 * int leastInterval(char[] tasks, int n) {
 *     // Count task frequencies
 *     Map<Character, Integer> counts = new HashMap<>();
 *     for (char task : tasks) {
 *         counts.put(task, counts.getOrDefault(task, 0) + 1);
 *     }
 *     
 *     // Max heap by frequency
 *     PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> b-a);
 *     maxHeap.addAll(counts.values());
 *     
 *     int time = 0;
 *     Queue<int[]> cooldown = new LinkedList<>();  // [count, availableTime]
 *     
 *     while (!maxHeap.isEmpty() || !cooldown.isEmpty()) {
 *         time++;
 *         
 *         if (!maxHeap.isEmpty()) {
 *             int count = maxHeap.poll() - 1;
 *             if (count > 0) {
 *                 cooldown.add(new int[]{count, time + n});
 *             }
 *         }
 *         
 *         // Check if any task finished cooling down
 *         if (!cooldown.isEmpty() && cooldown.peek()[1] == time) {
 *             maxHeap.add(cooldown.poll()[0]);
 *         }
 *     }
 *     
 *     return time;
 * }
 * 
 * EXAMPLE:
 * tasks = ['A','A','A','B','B','B'], n = 2
 * 
 * Timeline:
 * 0: A (A:2 on cooldown until time 3)
 * 1: B (B:2 on cooldown until time 4)
 * 2: idle (waiting)
 * 3: A (A:1 on cooldown until time 6)
 * 4: B (B:1 on cooldown until time 7)
 * 5: idle
 * 6: A
 * 7: B
 * 
 * Total: 8 intervals
 * 
 * WHY MAX HEAP?
 * Always do most frequent task first to minimize idle time!
 * 
 * Time: O(n log k) where k = unique tasks
 * 
 * ───────────────────────────────────────────────────────────────────────────
 * Q3: Implement a simple LRU Cache with max size
 * ───────────────────────────────────────────────────────────────────────────
 * ANSWER:
 * Use LinkedHashMap or Priority Queue with timestamps!
 * 
 * class LRUCache {
 *     private final int capacity;
 *     private final Map<Integer, int[]> cache;  // key -> [value, timestamp]
 *     private final PriorityQueue<int[]> pq;    // [key, timestamp]
 *     private int time = 0;
 *     
 *     public LRUCache(int capacity) {
 *         this.capacity = capacity;
 *         this.cache = new HashMap<>();
 *         this.pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);  // Min heap by time
 *     }
 *     
 *     public int get(int key) {
 *         if (!cache.containsKey(key)) return -1;
 *         
 *         int value = cache.get(key)[0];
 *         put(key, value);  // Update timestamp
 *         return value;
 *     }
 *     
 *     public void put(int key, int value) {
 *         cache.put(key, new int[]{value, time});
 *         pq.add(new int[]{key, time++});
 *         
 *         if (cache.size() > capacity) {
 *             // Evict least recently used
 *             while (!pq.isEmpty()) {
 *                 int[] oldest = pq.poll();
 *                 if (cache.containsKey(oldest[0]) && 
 *                     cache.get(oldest[0])[1] == oldest[1]) {
 *                     cache.remove(oldest[0]);
 *                     break;
 *                 }
 *             }
 *         }
 *     }
 * }
 * 
 * NOTE: LinkedHashMap is better for production (O(1) operations),
 * but this shows how PriorityQueue can track access patterns!
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * KEY TAKEAWAYS FOR INTERVIEWS:
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * 1. PRIORITY QUEUE = HEAP:
 *    - Java's PriorityQueue is a MIN heap by default
 *    - Smallest element has highest priority
 * 
 * 2. CUSTOM COMPARATORS:
 *    - Use (a,b) -> a-b for min heap (default)
 *    - Use (a,b) -> b-a for max heap
 *    - Can compare by object fields: (a,b) -> a.priority - b.priority
 * 
 * 3. WHEN TO USE:
 *    - Need to repeatedly get min/max: O(log n) is good!
 *    - Scheduling by priority
 *    - k largest/smallest problems
 *    - Merging sorted streams
 * 
 * 4. COMMON OPERATIONS:
 *    - add/offer: Insert with O(log n)
 *    - poll/remove: Remove min with O(log n)
 *    - peek: See min with O(1)
 * 
 * 5. GOTCHAS:
 *    - toArray() doesn't return sorted array! Shows heap structure
 *    - remove(Object) is O(n) - need to search first
 *    - Not thread-safe (use PriorityBlockingQueue if needed)
 * 
 * 6. MIN vs MAX HEAP CHOICE:
 *    - Min heap: Find k largest (keep k largest, remove smallest)
 *    - Max heap: Find k smallest (keep k smallest, remove largest)
 *    - Think opposite!
 * 
 * 7. ALTERNATIVES:
 *    - TreeSet: If you need both priority and iteration order
 *    - LinkedHashMap: For LRU cache (O(1) operations)
 *    - Simple sorting: If only one-time processing needed
 */
