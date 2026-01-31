package com.company.queue;

/**
 * QUEUE DATA STRUCTURE - First In, First Out (FIFO)
 * ===================================================
 * 
 * WHAT IS A QUEUE?
 * A queue is like a line of people waiting at a store checkout.
 * The first person to join the line is the first person to be served.
 * 
 * REAL-WORLD EXAMPLES:
 * 1. LINE AT A STORE: First person in line gets served first
 * 2. PRINTER QUEUE: Documents print in the order they were sent
 * 3. MUSIC PLAYLIST: Songs play in the order you added them
 * 4. CALL CENTER: Customers are helped in the order they called
 * 5. TRAFFIC LIGHT: Cars go through in the order they arrived
 * 
 * VISUAL REPRESENTATION:
 * 
 *     [Front] → [John] [Mary] [Steve] [Lisa] ← [Back]
 *     ↑                                        ↑
 *   Remove                                   Add
 *   (dequeue)                              (enqueue)
 * 
 * BASIC OPERATIONS:
 * - add/enqueue: Add an element to the BACK of the queue
 * - remove/dequeue: Remove an element from the FRONT of the queue
 * - peek: Look at the front element without removing it
 * - size: Count how many elements are in the queue
 * 
 * TIME COMPLEXITY:
 * - add (enqueue): O(1) - constant time (instant)
 * - remove (dequeue): O(1) - constant time (instant)
 * - peek: O(1) - constant time (instant)
 * - size: O(1) - constant time (instant)
 * 
 * SPACE COMPLEXITY: O(n) where n = number of elements in the queue
 * 
 * INTERVIEW QUESTIONS COVERED:
 * ===========================
 * Q1: Implement a queue using two stacks
 * Q2: Reverse the first k elements of a queue
 * Q3: Implement a circular queue
 * Q4: Generate binary numbers from 1 to n using a queue
 */
public class Main {

    public static void main(String[] args) {

        // CREATE EMPLOYEES
        // Think of these as people joining a line at a coffee shop
        Employee janeJohns = new Employee("Jane", "Jones", 123);
        Employee johnDoe = new Employee("John", "Does", 6546);
        Employee dickJiba = new Employee("Dick", "Jiba", 78);
        Employee fnckUdemy = new Employee("Fnck", "Udemy", 111);
        Employee gfdgddfg = new Employee("gfdg", "ddfg", 77888);

        // CREATE QUEUE
        // This is like setting up a line with space for 10 people
        ArrayQueue queue = new ArrayQueue(10);
        
        // ADD EMPLOYEES TO QUEUE (Enqueue)
        // People join the line one by one at the BACK
        // Queue looks like: [Jane] [John] [Dick] [Fnck]
        queue.add(janeJohns);   // Jane joins first (she's at the front)
        queue.add(johnDoe);      // John joins second
        queue.add(dickJiba);     // Dick joins third
        queue.add(fnckUdemy);    // Fnck joins fourth (he's at the back)
        
        // REMOVE FROM QUEUE (Dequeue)
        // The first person in line (Jane) gets served and leaves
        // Queue now looks like: [John] [Dick] [Fnck]
        queue.remove();
        
        // PRINT QUEUE
        // Show who's still waiting in line
        queue.printQueue();

        // PEEK AT FRONT
        // See who's next in line without removing them
        // This will show John (he's now at the front)
        System.out.println(queue.peek());

    }
}
