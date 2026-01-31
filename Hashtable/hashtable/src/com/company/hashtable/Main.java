package com.company.hashtable;

/**
 * ========================================================================
 * HASH TABLE DEMONSTRATION - Linear Probing Implementation
 * ========================================================================
 * 
 * This program demonstrates how a hash table with LINEAR PROBING works.
 * 
 * WHAT YOU'LL SEE:
 * ----------------
 * 1. Creating employee objects (the data we want to store)
 * 2. Storing them in a hash table using keys (last names)
 * 3. Retrieving employees quickly using their keys
 * 4. Removing employees and seeing how the table reorganizes
 * 
 * KEY CONCEPTS DEMONSTRATED:
 * --------------------------
 * - HASHING: Converting keys to array indices
 * - COLLISIONS: What happens when two keys map to the same index
 * - LINEAR PROBING: Finding the next available spot when there's a collision
 * - REHASHING: How the table reorganizes after deletions
 * 
 * REAL-WORLD ANALOGY:
 * -------------------
 * Think of this like a small office building with 10 parking spaces:
 * - Each employee gets a "preferred" parking spot based on their name
 * - If their spot is taken (collision), they park in the next available spot
 * - You can quickly find where someone parked by checking their preferred spot first
 * 
 * @author Data Structures Learning Project
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        
        // ===================================================================
        // STEP 1: CREATE EMPLOYEE OBJECTS
        // ===================================================================
        // These are the data items we want to store in our hash table.
        // Each employee has a first name, last name, and ID number.
        
        Employee janeJones = new Employee("Jane", "Jones", 123);
        Employee johnDoe = new Employee("John", "Doe", 4567);
        Employee marySmith = new Employee("Mary", "Smith", 22);
        Employee mikeWilson = new Employee("Mike", "Wilson", 3245);
        Employee billEnd = new Employee("Bill", "End", 78);



        // ===================================================================
        // STEP 2: CREATE HASH TABLE AND ADD EMPLOYEES
        // ===================================================================
        // We create a SimpleHashtable (size 10) and store employees using
        // their last names as keys.
        
        SimpleHashtable ht= new SimpleHashtable();  // Creates array of 10 slots
        
        // ADD EMPLOYEES TO HASH TABLE
        // Each put() call:
        // 1. Hashes the key (last name) to get an index
        // 2. Stores the employee at that index (or next available if collision)
        
        ht.put("Jones",janeJones);    // "Jones" (5 letters) → 5 % 10 = index 5
        ht.put("Doe",johnDoe);        // "Doe" (3 letters) → 3 % 10 = index 3
        ht.put("Wilson",mikeWilson);  // "Wilson" (6 letters) → 6 % 10 = index 6
        ht.put("Smith",marySmith);    // "Smith" (5 letters) → 5 % 10 = index 5
                                       // COLLISION! "Jones" is already at index 5
                                       // Linear probing: try index 6... occupied!
                                       // Try index 7... empty! Store there.

        // ===================================================================
        // STEP 3: DISPLAY THE HASH TABLE
        // ===================================================================
        // This shows where each employee is actually stored in the array.
        // You'll see some "empty" slots and some with employee data.
        // Notice how collisions caused some employees to be stored at
        // different indices than their hash value!
        
        ht.printHashtable();

        // ===================================================================
        // STEP 4: RETRIEVE EMPLOYEES BY KEY
        // ===================================================================
        // The get() method uses the hash function to quickly find employees.
        // Even though there were collisions, it still finds them fast!
        
        System.out.println("Retrieve key Wilson: "+ht.get("Wilson"));
        // How it works:
        // 1. Hash "Wilson" → index 6
        // 2. Check index 6: key matches "Wilson"
        // 3. Return the employee at index 6
        // Result: Employee{firstName='Mike', lastName='Wilson', id=3245}
        
        System.out.println("Retrieve key Smith: "+ht.get("Smith"));
        // How it works:
        // 1. Hash "Smith" → index 5
        // 2. Check index 5: key is "Jones" (not "Smith")
        // 3. Linear probe to index 6: key is "Wilson" (not "Smith")
        // 4. Linear probe to index 7: key is "Smith" - FOUND!
        // 5. Return the employee at index 7
        // Result: Employee{firstName='Mary', lastName='Smith', id=22}

        // ===================================================================
        // STEP 5: REMOVE EMPLOYEES
        // ===================================================================
        // Removing from a hash table with linear probing requires REHASHING
        // the entire table to maintain the integrity of the probe sequences.
        
        ht.remove("Wilson");  // Remove Mike Wilson
        ht.remove("Jones");   // Remove Jane Jones
        
        // After removal, the table is REHASHED:
        // 1. All remaining employees are re-inserted
        // 2. They might end up at different positions!
        // 3. This fixes the probe chains so get() still works correctly
        
        ht.printHashtable();
        // You'll notice the positions have changed!
        // "Smith" might have moved from index 7 to index 5 (her natural hash position)
        // because "Jones" was removed from index 5.

        // ===================================================================
        // STEP 6: VERIFY DATA INTEGRITY
        // ===================================================================
        // Even after removals and rehashing, we can still retrieve employees
        // correctly!
        
        System.out.println("Retrieve key Smith: "+ht.get("Smith"));
        // This still works! The hash table correctly finds Mary Smith
        // even though her position in the array might have changed.

    }
}

/**
 * ==========================================
 * EXPECTED OUTPUT EXPLANATION
 * ==========================================
 * 
 * First printHashtable() output (after adding 4 employees):
 * ----------------------------------------------------------
 * empty                                      (index 0)
 * empty                                      (index 1)
 * empty                                      (index 2)
 * position3: Employee{...John Doe...}        (index 3) ← "Doe" hashed here
 * empty                                      (index 4)
 * position5: Employee{...Jane Jones...}      (index 5) ← "Jones" hashed here
 * position6: Employee{...Mike Wilson...}     (index 6) ← "Wilson" hashed here
 * position7: Employee{...Mary Smith...}      (index 7) ← "Smith" collided, probed here
 * empty                                      (index 8)
 * empty                                      (index 9)
 * 
 * Notice: "Smith" hashed to 5 but ended up at index 7 due to collision!
 * 
 * Retrieval outputs:
 * ------------------
 * Retrieve key Wilson: Employee{firstName='Mike', lastName='Wilson', id=3245}
 * Retrieve key Smith: Employee{firstName='Mary', lastName='Smith', id=22}
 * 
 * Second printHashtable() output (after removing Wilson and Jones):
 * ------------------------------------------------------------------
 * The table is REHASHED, so positions change:
 * empty                                      (index 0)
 * empty                                      (index 1)
 * empty                                      (index 2)
 * position3: Employee{...John Doe...}        (index 3) ← Still at index 3
 * empty                                      (index 4)
 * position5: Employee{...Mary Smith...}      (index 5) ← MOVED from 7 to 5!
 * empty                                      (index 6)
 * empty                                      (index 7)
 * empty                                      (index 8)
 * empty                                      (index 9)
 * 
 * Notice: After rehashing, "Smith" moved to her "natural" position (index 5)
 * because "Jones" is no longer blocking it!
 * 
 * Final retrieval:
 * ----------------
 * Retrieve key Smith: Employee{firstName='Mary', lastName='Smith', id=22}
 * Still works perfectly!
 * 
 * ==========================================
 * KEY LEARNING POINTS
 * ==========================================
 * 
 * 1. COLLISION HANDLING:
 *    "Smith" and "Jones" both hash to index 5.
 *    Linear probing automatically finds the next available spot.
 * 
 * 2. FAST RETRIEVAL:
 *    Even with collisions, finding an employee is very fast.
 *    We don't search the entire array - just probe a few spots.
 * 
 * 3. REHASHING OVERHEAD:
 *    Removal is expensive (O(n)) because we must rehash the entire table.
 *    This is a tradeoff of using linear probing.
 * 
 * 4. DATA INTEGRITY:
 *    Despite rehashing and items moving around, the hash table
 *    maintains perfect data integrity - we always get the right employee!
 * 
 * ==========================================
 * TRY THIS YOURSELF
 * ==========================================
 * 
 * Experiment 1: Add more employees with 5-letter last names
 * - Try adding "Brown", "Clark", "Davis"
 * - They all hash to index 5!
 * - Watch the clustering effect (all stored near each other)
 * 
 * Experiment 2: Change the hash function in SimpleHashtable
 * - Use key.hashCode() % hashtable.length instead of key.length()
 * - See how this distributes employees better
 * - Fewer collisions!
 * 
 * Experiment 3: Add billEnd to the hash table
 * - Employee billEnd = new Employee("Bill", "End", 78)
 * - ht.put("End", billEnd)
 * - "End" has 3 letters, same as "Doe" (collision!)
 * - Where does it end up?
 * 
 * Experiment 4: Try to fill the table completely
 * - Add 10+ employees
 * - What happens when the table is full?
 * - You'll get an error message!
 * 
 * ==========================================
 * INTERVIEW PRACTICE
 * ==========================================
 * 
 * Q: Walk me through what happens when we call ht.put("Smith", marySmith).
 * 
 * A: Step-by-step process:
 *    1. Call hashKeys("Smith")
 *       - "Smith".length() = 5
 *       - 5 % 10 = 5
 *       - Target index: 5
 *    
 *    2. Check if index 5 is occupied
 *       - hashtable[5] is not null (Jane Jones is there)
 *       - COLLISION detected!
 *    
 *    3. Start linear probing
 *       - Set stopIndex = 5 (remember where we started)
 *       - Move to next index: 5 + 1 = 6
 *    
 *    4. Check index 6
 *       - hashtable[6] is not null (Mike Wilson is there)
 *       - Still occupied, continue probing
 *       - Move to next index: (6 + 1) % 10 = 7
 *    
 *    5. Check index 7
 *       - hashtable[7] is null (empty!)
 *       - Found an available spot!
 *    
 *    6. Store the employee
 *       - Create StoredEmployee("Smith", marySmith)
 *       - Place it at hashtable[7]
 *       - Done!
 *    
 *    Even though "Smith" hashed to 5, it's stored at 7 due to collisions.
 *    The hash table remembers this and can still find it quickly later!
 */
