package com.company.queue;

import java.util.NoSuchElementException;

/**
 * ARRAY-BASED QUEUE IMPLEMENTATION
 * ==================================
 * 
 * This is a circular array-based queue that uses a technique called "wrap-around".
 * Think of it like a circular race track where the end connects back to the beginning.
 * 
 * CIRCULAR QUEUE VISUALIZATION:
 * 
 *     Index: 0    1    2    3    4    5
 *           [  ] [  ] [  ] [  ] [  ] [  ]
 *            ↑                        ↑
 *          front                    back
 * 
 * After adding elements:
 *     Index: 0    1    2    3    4    5
 *           [Jane][John][Dick][  ] [  ] [  ]
 *            ↑              ↑
 *          front          back
 * 
 * WHY CIRCULAR?
 * When we remove elements from the front, we don't shift everything over.
 * Instead, we just move the "front" pointer forward. This saves time!
 * When we reach the end of the array, we wrap back to the beginning.
 * 
 * ADVANTAGES OF ARRAY-BASED QUEUE:
 * - Fast access (O(1) for add, remove, peek)
 * - Good cache performance (elements stored together in memory)
 * - Simple to implement
 * 
 * DISADVANTAGES:
 * - Fixed size (need to resize when full)
 * - Resizing costs time (O(n) to copy elements)
 * 
 * INTERVIEW INSIGHT:
 * "Why use a circular array instead of shifting elements?"
 * Answer: Shifting all elements left after each removal would be O(n).
 * Using front/back pointers makes removal O(1), much faster!
 */
public class ArrayQueue {

    private Employee[] queue;  // The array that holds our queue elements
    private int front;         // Index of the first element (where we remove)
    private int back;          // Index where we add the next element

    public ArrayQueue(int capacity){
        queue = new Employee[capacity];
    }

    /**
     * add() - Add an employee to the BACK of the queue (Enqueue)
     * 
     * This is like a new person joining the line at a store.
     * They always go to the BACK, never cut in line!
     * 
     * STEPS:
     * 1. Check if the queue is full
     * 2. If full, create a bigger array (resize)
     * 3. Add the employee at the 'back' position
     * 4. Move the 'back' pointer forward (or wrap around to 0)
     * 
     * RESIZING STRATEGY:
     * When full, we create a new array that's TWICE as big.
     * This is called "doubling strategy" - it's efficient!
     * 
     * WHY DOUBLE THE SIZE?
     * - Doubling gives us amortized O(1) time for add operations
     * - If we only increased by 1, we'd resize too often (slow!)
     * 
     * Time Complexity: 
     * - Best case: O(1) when there's space
     * - Worst case: O(n) when we need to resize (rare!)
     * - Amortized: O(1) - on average, very fast!
     * 
     * @param employee The employee to add to the queue
     */
    public void add(Employee employee){
        // STEP 1: Check if queue is full and resize if needed
        if(size()==queue.length){
            int numItems = size();
            // Create a new array twice as big
            Employee[] newArray = new Employee[2*queue.length];
            // Copy elements from front to end of old array
            System.arraycopy(queue, front , newArray, 0, queue.length-front);
            // Copy wrapped-around elements from beginning of old array
            System.arraycopy(queue,front, newArray,queue.length-front, back);
            queue = newArray;
        }

        // STEP 2: Add employee at the back position
        queue[back] = employee;
        
        // EXAMPLE of how the queue might look:
        // Index: 0      1      2       3      4
        //       [Jane] [John] [Dick] [Steve] [   ] ← back points here
        //        ↑                                  
        //      front
        
        // STEP 3: Move back pointer forward (with wrap-around)
        if(back<queue.length-1){
            back++;  // Move forward normally
        }
        else{
            back = 0;  // Wrap around to the beginning (circular!)
        }
    }

    /**
     * remove() - Remove and return the employee at the FRONT (Dequeue)
     * 
     * This is like the first person in line being served and leaving.
     * We ALWAYS serve from the front - it's fair!
     * 
     * STEPS:
     * 1. Check if queue is empty (can't remove from empty queue!)
     * 2. Save the employee at the front
     * 3. Set that position to null (cleanup)
     * 4. Move front pointer forward
     * 5. If queue becomes empty, reset both pointers to 0
     * 
     * WHY SET TO NULL?
     * - Prevents memory leaks (lets garbage collector free memory)
     * - Good programming practice!
     * 
     * Time Complexity: O(1) - constant time (super fast!)
     * Space Complexity: O(1) - no extra space needed
     * 
     * @return The employee who was at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public Employee remove(){
        // STEP 1: Check if queue is empty
        if(size()==0){
            throw new NoSuchElementException();
        }

        // STEP 2 & 3: Get the front employee and clean up
        Employee employee = queue[front];
        queue[front] = null;  // Help garbage collector
        
        // STEP 4: Move front pointer forward
        front++;
        
        // STEP 5: Reset if queue is now empty
        if(size()==0){
            front=0;
            back=0;
        }

        return employee;
    }

    /**
     * peek() - Look at the front employee WITHOUT removing them
     * 
     * This is like seeing who's next in line without serving them yet.
     * Useful for checking what's coming next!
     * 
     * Time Complexity: O(1) - instant!
     * 
     * @return The employee at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public Employee peek(){
        if(size()==0){
            throw new NoSuchElementException();
        }

        return queue[front];
    }

    /**
     * size() - Count how many employees are in the queue
     * 
     * Simple math: back position - front position = number of elements
     * Example: If front=2 and back=5, there are 5-2=3 employees
     * 
     * Time Complexity: O(1) - just subtraction!
     * 
     * @return The number of employees currently in the queue
     */
    public int size(){
        return back-front;
    }

    /**
     * printQueue() - Display all employees in the queue
     * 
     * This shows everyone waiting in line, from front to back.
     * Helpful for debugging and visualizing the queue!
     * 
     * Time Complexity: O(n) where n = number of employees
     */
    public void printQueue(){

        for(int i =front;i<back;i++){
            System.out.println(queue[i]);
        }
    }

}

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * INTERVIEW QUESTIONS & ANSWERS
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * Q1: How would you implement a queue using two stacks?
 * ───────────────────────────────────────────────────────────────────────────
 * ANSWER:
 * Use two stacks - one for enqueue (adding), one for dequeue (removing).
 * 
 * class QueueWithStacks {
 *     Stack<Integer> stackIn = new Stack<>();   // For adding
 *     Stack<Integer> stackOut = new Stack<>();  // For removing
 *     
 *     void enqueue(int x) {
 *         stackIn.push(x);  // O(1) - just push to stackIn
 *     }
 *     
 *     int dequeue() {
 *         if (stackOut.isEmpty()) {
 *             // Transfer all from stackIn to stackOut (reverses order)
 *             while (!stackIn.isEmpty()) {
 *                 stackOut.push(stackIn.pop());
 *             }
 *         }
 *         return stackOut.pop();  // Amortized O(1)
 *     }
 * }
 * 
 * WHY IT WORKS: 
 * - StackIn gets elements in order: [1,2,3] (3 on top)
 * - When we transfer to stackOut, order reverses: [3,2,1] (1 on top)
 * - Now popping from stackOut gives us FIFO behavior!
 * 
 * ───────────────────────────────────────────────────────────────────────────
 * Q2: Reverse the first k elements of a queue
 * ───────────────────────────────────────────────────────────────────────────
 * ANSWER:
 * Use a stack to reverse the first k elements!
 * 
 * void reverseFirstK(Queue<Integer> q, int k) {
 *     Stack<Integer> stack = new Stack<>();
 *     
 *     // Step 1: Remove first k elements and push to stack
 *     for (int i = 0; i < k; i++) {
 *         stack.push(q.remove());
 *     }
 *     
 *     // Step 2: Pop from stack and add back to queue (now reversed!)
 *     while (!stack.isEmpty()) {
 *         q.add(stack.pop());
 *     }
 *     
 *     // Step 3: Move remaining elements to back
 *     int remaining = q.size() - k;
 *     for (int i = 0; i < remaining; i++) {
 *         q.add(q.remove());
 *     }
 * }
 * 
 * EXAMPLE:
 * Original: [1,2,3,4,5], k=3
 * After step 1: Queue=[4,5], Stack=[3,2,1]
 * After step 2: Queue=[4,5,3,2,1]
 * After step 3: Queue=[3,2,1,4,5] ✓
 * 
 * Time: O(n), Space: O(k)
 * 
 * ───────────────────────────────────────────────────────────────────────────
 * Q3: Generate binary numbers from 1 to n using a queue
 * ───────────────────────────────────────────────────────────────────────────
 * ANSWER:
 * Start with "1", then for each number, create two new by appending "0" and "1"
 * 
 * void generateBinary(int n) {
 *     Queue<String> q = new LinkedList<>();
 *     q.add("1");
 *     
 *     for (int i = 0; i < n; i++) {
 *         String current = q.remove();
 *         System.out.println(current);
 *         
 *         // Create next binary numbers
 *         q.add(current + "0");
 *         q.add(current + "1");
 *     }
 * }
 * 
 * HOW IT WORKS:
 * Start: Queue = ["1"]
 * i=0: Print "1", Queue = ["10", "11"]
 * i=1: Print "10", Queue = ["11", "100", "101"]
 * i=2: Print "11", Queue = ["100", "101", "110", "111"]
 * ...
 * 
 * Output for n=5: 1, 10, 11, 100, 101
 * (which is 1, 2, 3, 4, 5 in decimal!)
 * 
 * Time: O(n), Space: O(n)
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * KEY TAKEAWAYS FOR INTERVIEWS:
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * 1. FIFO PRINCIPLE: First In, First Out - like a real line!
 * 
 * 2. OPERATIONS ARE O(1): add, remove, and peek are all constant time
 * 
 * 3. USE CASES: 
 *    - Task scheduling (OS processes)
 *    - Breadth-First Search (BFS) in graphs
 *    - Print job scheduling
 *    - Handling requests in web servers
 * 
 * 4. CIRCULAR ARRAY ADVANTAGE: No shifting needed, saves time!
 * 
 * 5. ARRAY vs LINKED LIST QUEUE:
 *    - Array: Better cache performance, need to resize
 *    - Linked List: No resizing, more memory per element
 * 
 * 6. COMMON MISTAKES TO AVOID:
 *    - Forgetting to check if queue is empty before remove/peek
 *    - Not handling wrap-around in circular implementation
 *    - Forgetting to set removed elements to null (memory leak!)
 */
