package com.company.hashtable;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * ========================================================================
 * CHAINED HASHTABLE - A Learning Implementation with Separate Chaining
 * ========================================================================
 * 
 * WHAT IS CHAINING?
 * -----------------
 * Chaining is a collision resolution technique where each slot in the hash table
 * holds a LINKED LIST of all items that hash to that index.
 * 
 * Think of it like:
 * 1. A FILE CABINET: Each drawer (array index) contains a folder (linked list)
 *    with multiple documents (employees) inside.
 *    
 * 2. A PHONE DIRECTORY: Each letter of the alphabet (array index) has a list
 *    of all people whose names start with that letter.
 *    
 * 3. A PARKING LOT: Each row (array index) can fit multiple cars (employees)
 *    parked one behind the other.
 * 
 * CHAINING vs LINEAR PROBING:
 * ----------------------------
 * 
 * LINEAR PROBING (SimpleHashtable):
 * - One item per slot
 * - Collisions solved by finding next empty slot
 * - Problem: Clustering, expensive deletions (must rehash)
 * - Benefit: Simple, good cache performance
 * 
 * CHAINING (this class):
 * - Multiple items per slot (in a linked list)
 * - Collisions solved by adding to the list
 * - Benefit: Easy insertion/deletion, no clustering, never "full"
 * - Tradeoff: Extra memory for linked list pointers
 * 
 * VISUAL EXAMPLE:
 * ---------------
 * Suppose "Jones" and "Smith" both hash to index 5:
 * 
 * LINEAR PROBING:
 * Index 5: Jones
 * Index 6: Smith  (probed here due to collision)
 * Index 7: empty
 * 
 * CHAINING:
 * Index 5: Jones → Smith → null  (linked list with both)
 * Index 6: empty
 * Index 7: empty
 * 
 * PERFORMANCE:
 * ------------
 * - BEST CASE: O(1) - Item is alone in its chain
 * - AVERAGE CASE: O(1 + α) where α = load factor (items/slots)
 *   - If load factor is 0.75, average chain length is 0.75
 *   - Still very fast!
 * - WORST CASE: O(n) - All items hash to same index (one long chain)
 *   - Rare with good hash function!
 * 
 * ADVANTAGES OVER LINEAR PROBING:
 * --------------------------------
 * 1. NEVER FULL: Can always add more items (chains just get longer)
 * 2. EASY DELETION: Just remove from linked list, no rehashing needed!
 * 3. NO CLUSTERING: Items don't "block" nearby slots
 * 4. PREDICTABLE: Performance degrades gracefully as table fills
 * 
 * WHEN TO USE CHAINING:
 * ---------------------
 * ✓ Frequent insertions and deletions
 * ✓ Unknown or variable number of items
 * ✓ Can't tolerate expensive rehashing operations
 * ✓ Have enough memory for linked list overhead
 * 
 * @author Data Structures Learning Project
 * @version 1.0
 */
public class ChainedHashtable {

    /**
     * The underlying array that stores linked lists.
     * Each element is a LinkedList<StoredEmployee>, which can hold multiple employees.
     * 
     * Think of this like a row of 10 buckets, each bucket can hold multiple items.
     * 
     * MEMORY STRUCTURE:
     * hashtable[0] → LinkedList → [StoredEmployee] → [StoredEmployee] → null
     * hashtable[1] → LinkedList → null (empty)
     * hashtable[2] → LinkedList → [StoredEmployee] → null
     * ...
     */
    private LinkedList<StoredEmployee>[] hashtable;

    private LinkedList<StoredEmployee>[] hashtable;

    /**
     * CONSTRUCTOR - Initialize the Chained Hash Table
     * ================================================
     * 
     * Creates a hash table with 10 slots, where each slot contains an empty LinkedList.
     * 
     * INITIALIZATION PROCESS:
     * -----------------------
     * 1. Create an array of 10 LinkedList references
     * 2. For each slot, create a new empty LinkedList
     * 3. Now we have 10 empty buckets ready to store employees!
     * 
     * WHY PRE-CREATE ALL LINKEDLISTS?
     * --------------------------------
     * - Avoids null pointer exceptions
     * - Simplifies put() logic (don't need to check if list exists)
     * - Small memory cost (empty LinkedList is just a few bytes)
     * 
     * REAL-WORLD ANALOGY:
     * -------------------
     * Think of setting up 10 file folders in a filing cabinet.
     * Even if some folders will be empty, we create all of them upfront
     * so we can quickly add documents to any folder without checking
     * if the folder exists first.
     */
    public ChainedHashtable(){
        hashtable = new LinkedList[10];  // Create array of 10 LinkedList references
        
        // Initialize each LinkedList (create 10 empty buckets)
        for(int i =0;i<hashtable.length;i++){
            hashtable[i] = new LinkedList<StoredEmployee>();
        }
    }

    /**
     * PUT - Insert a Key-Value Pair into the Hash Table
     * ==================================================
     * 
     * This method stores an employee by adding it to the appropriate linked list.
     * Unlike linear probing, we don't need to search for an empty slot -
     * we just add to the end of the chain!
     * 
     * STEP-BY-STEP PROCESS:
     * ---------------------
     * 1. Hash the key to get the bucket index
     * 2. Add the employee to that bucket's linked list
     * 3. Done! No collision handling needed!
     * 
     * EXAMPLE WITH COLLISIONS:
     * ------------------------
     * Suppose our hash function gives us:
     * - "Jones" → index 5
     * - "Smith" → index 5 (COLLISION!)
     * - "Wilson" → index 6
     * 
     * After put("Jones", janeEmployee):
     *   hashtable[5] → [Jones]
     * 
     * After put("Smith", maryEmployee):
     *   hashtable[5] → [Jones] → [Smith]  (added to chain!)
     * 
     * After put("Wilson", mikeEmployee):
     *   hashtable[6] → [Wilson]
     * 
     * Notice: Both "Jones" and "Smith" are at index 5, stored in a chain!
     * No searching for alternative slots needed!
     * 
     * COMPARISON WITH LINEAR PROBING:
     * --------------------------------
     * Linear Probing:
     * - "Jones" → index 5
     * - "Smith" → index 5 (collision!) → probe to index 6
     * - "Wilson" → index 6 (collision with Smith!) → probe to index 7
     * - Items scattered across array
     * 
     * Chaining:
     * - "Jones" → index 5 chain
     * - "Smith" → index 5 chain (same chain as Jones!)
     * - "Wilson" → index 6 chain
     * - Items grouped by hash value
     * 
     * @param key The search key (usually employee's last name)
     * @param employee The Employee object to store
     * 
     * TIME COMPLEXITY:
     * - Best/Average/Worst for insertion: O(1)
     * - LinkedList.add() at end is O(1)
     * - No searching or probing needed!
     * 
     * SPACE COMPLEXITY:
     * - O(1) per item (one StoredEmployee node + one LinkedList node)
     */
    public void put(String key, Employee employee){
        int hashedKey = hashKey(key);  // Step 1: Calculate which bucket
        
        // Step 2: Add to the chain at that bucket
        // This is MUCH simpler than linear probing!
        // No checking if occupied, no probing, just add!
        hashtable[hashedKey].add(new StoredEmployee(key,employee));
    }

    /**
     * GET - Retrieve an Employee by Key
     * ==================================
     * 
     * This method finds an employee by searching through the appropriate chain.
     * 
     * STEP-BY-STEP PROCESS:
     * ---------------------
     * 1. Hash the key to find which bucket to search
     * 2. Get an iterator for that bucket's linked list
     * 3. Walk through the chain, comparing keys
     * 4. If we find a match, return the employee
     * 5. If we reach the end without a match, return null
     * 
     * DETAILED EXAMPLE:
     * -----------------
     * Table state:
     * hashtable[5] → [("Jones", janeEmployee)] → [("Smith", maryEmployee)] → null
     * hashtable[6] → [("Wilson", mikeEmployee)] → null
     * 
     * get("Smith"):
     *   1. Hash "Smith" → index 5
     *   2. Start at beginning of chain at index 5
     *   3. Check first node: key is "Jones" (not "Smith") → continue
     *   4. Check second node: key is "Smith" → MATCH! Return maryEmployee ✓
     * 
     * get("Wilson"):
     *   1. Hash "Wilson" → index 6
     *   2. Start at beginning of chain at index 6
     *   3. Check first node: key is "Wilson" → MATCH! Return mikeEmployee ✓
     * 
     * get("Brown"):
     *   1. Hash "Brown" → some index (let's say 5)
     *   2. Start at beginning of chain at index 5
     *   3. Check "Jones" → not a match
     *   4. Check "Smith" → not a match
     *   5. Reached end of chain → "Brown" not found, return null ✗
     * 
     * WHY USE AN ITERATOR?
     * --------------------
     * An iterator is like a bookmark that helps us walk through the linked list:
     * - Keeps track of our current position
     * - Lets us move to the next item easily
     * - Handles the details of traversing the list
     * 
     * @param key The search key (usually employee's last name)
     * @return The Employee object if found, or null if not found
     * 
     * TIME COMPLEXITY:
     * - Best Case: O(1) - Item is first in chain
     * - Average Case: O(1 + α) where α is load factor
     *   - If load factor is 0.75, average chain length is 0.75
     *   - On average, check less than 1 item!
     * - Worst Case: O(n) - All items in one chain
     *   - Must check every item in the chain
     *   - Rare with good hash function!
     */
    public Employee get(String key){
        int hashedKey = hashKey(key);  // Step 1: Find which bucket
        
        // Step 2: Get an iterator to walk through the chain
        ListIterator<StoredEmployee> iterator= hashtable[hashedKey].listIterator();
        StoredEmployee employee=null;
        
        // Step 3: Walk through the chain looking for matching key
        while(iterator.hasNext()){
            employee = iterator.next();  // Move to next item in chain
            if(employee.key.equals(key)){  // Check if this is the one we want
                return employee.employee;  // Found it! Return the employee
            }
        }

        // Step 4: Reached end of chain without finding it
        return null;  // Not in the hash table

    }

    /**
     * REMOVE - Delete an Employee from the Hash Table
     * ================================================
     * 
     * This method removes an employee from the appropriate chain.
     * Unlike linear probing, we DON'T need to rehash the entire table!
     * We just remove one node from a linked list.
     * 
     * @param key The search key of the employee to remove
     * @return The removed Employee object, or null if not found
     */
    public Employee remove(String key){
        int hashedKey = hashKey(key);
        ListIterator<StoredEmployee> iterator= hashtable[hashedKey].listIterator();
        StoredEmployee employee=null;
        int index = -1;
        while(iterator.hasNext()){
            employee = iterator.next();
            index++;
            if(employee.key.equals(key)){
                break;
            }
        }

        if(employee==null || !employee.key.equals(key)){
            return null;
        }
        else{
            hashtable[hashedKey].remove(index);
            return employee.employee;
        }

    }

    /**
     * HASH KEY - The Hash Function (IMPROVED!)
     * =========================================
     * 
     * Uses Java's built-in String.hashCode() for better distribution.
     * Much better than the simple length-based function!
     * 
     * @param key The key to hash
     * @return An index from 0 to 9
     */
    private int hashKey(String key){
//        return key.length() % hashtable.length;

        return Math.abs(key.hashCode()%hashtable.length);
    }

    /**
     * PRINT HASHTABLE - Display the Current State
     * ============================================
     * 
     * Shows all chains in the hash table.
     * Very useful for understanding chaining!
     */
    public void printHashtable(){
        for(int i =0;i<hashtable.length;i++){
            if(hashtable[i].isEmpty()){
                System.out.println("position"+i+": is empty");
            }
            else{
                System.out.print("position"+i+": ");
                ListIterator<StoredEmployee> iterator= hashtable[i].listIterator();
                while(iterator.hasNext()){
                    System.out.print(iterator.next().employee);
                    System.out.print("->");
                }

                System.out.println("null");
            }
        }
    }
}

/**
 * ==========================================
 * ADDITIONAL INTERVIEW QUESTIONS
 * ==========================================
 * 
 * See SimpleHashtable.java for comprehensive interview questions.
 * Chaining-specific advantages: easier deletion, no clustering!
 */
