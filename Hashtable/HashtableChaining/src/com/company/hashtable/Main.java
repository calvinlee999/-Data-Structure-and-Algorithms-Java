package com.company.hashtable;

/**
 * ========================================================================
 * CHAINED HASH TABLE DEMONSTRATION
 * ========================================================================
 * 
 * This program demonstrates hash tables using CHAINING for collision resolution.
 * 
 * KEY DIFFERENCES FROM LINEAR PROBING:
 * ------------------------------------
 * 1. Each slot holds a LINKED LIST (not just one item)
 * 2. Collisions are handled by ADDING TO THE CHAIN
 * 3. Deletion is EASY (no rehashing needed!)
 * 4. Table never becomes "full"
 * 
 * WATCH FOR:
 * ----------
 * - How multiple employees can share the same index (in a chain)
 * - How removal doesn't affect other employees
 * - The arrow notation (→) showing linked list chains
 */
public class Main {

    public static void main(String[] args) {
            // Create employees (same as linear probing example)
            Employee janeJones = new Employee("Jane", "Jones", 123);
            Employee johnDoe = new Employee("John", "Doe", 4567);
            Employee marySmith = new Employee("Mary", "Smith", 22);
            Employee mikeWilson = new Employee("Mike", "Wilson", 3245);
            Employee billEnd = new Employee("Bill", "End", 78);


            // Create CHAINED hash table (uses LinkedLists internally)
            ChainedHashtable ht= new ChainedHashtable();
            
            // Add employees - collisions automatically handled by chaining!
            ht.put("Jones",janeJones);
            ht.put("Doe",johnDoe);
            ht.put("Wilson",mikeWilson);
            ht.put("Smith",marySmith);

            // Uncomment to see the internal structure:
//            ht.printHashtable();
            // You'll see chains like: position7: Employee{...}->Employee{...}->null

            // Retrieve employees - searches through appropriate chain
            System.out.println("Retrieve key Wilson: "+ht.get("Wilson"));
            System.out.println("Retrieve key Smith: "+ht.get("Smith"));

            // Remove employees - NO REHASHING needed!
            // Just removes from the linked list
            ht.remove("Wilson");
            ht.remove("Jones");
            
            // Print to see the chains after removal
            ht.printHashtable();
            // Notice: Other employees stay in place, chains are shorter

            // Verify data integrity
            System.out.println("Retrieve key Smith: "+ht.get("Smith"));
            // Still works perfectly!
    }
}

/**
 * KEY LEARNING POINTS:
 * ====================
 * 
 * 1. CHAINING vs LINEAR PROBING:
 *    - Chaining: Multiple items per slot (in a chain)
 *    - Linear Probing: One item per slot (find next empty)
 * 
 * 2. DELETION PERFORMANCE:
 *    - Chaining: O(1) - Just remove from chain
 *    - Linear Probing: O(n) - Must rehash entire table
 * 
 * 3. NEVER FULL:
 *    - Chaining: Chains can grow indefinitely
 *    - Linear Probing: Can run out of slots
 * 
 * 4. TRADE-OFFS:
 *    - Chaining: Uses more memory (pointers)
 *    - Linear Probing: Better cache performance
 */
