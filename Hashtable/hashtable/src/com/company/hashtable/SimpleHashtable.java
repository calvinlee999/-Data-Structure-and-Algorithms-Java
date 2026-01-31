package com.company.hashtable;

/**
 * ========================================================================
 * SIMPLE HASHTABLE - A Learning Implementation with Linear Probing
 * ========================================================================
 * 
 * WHAT IS A HASH TABLE?
 * ---------------------
 * A hash table is a data structure that stores key-value pairs and allows SUPER FAST
 * lookups, insertions, and deletions - usually in O(1) constant time!
 * 
 * Think of it like:
 * 1. A LOCKER ROOM: Each locker has a number (index), and you use a formula to
 *    determine which locker to use based on your name.
 *    
 * 2. A LIBRARY CARD CATALOG: You don't search every card; you go directly to
 *    the drawer labeled with the first letter of the author's name.
 *    
 * 3. A PHONE BOOK APP: You type a name and instantly get the phone number -
 *    you don't flip through every entry!
 * 
 * HOW DOES IT WORK?
 * -----------------
 * 1. HASH FUNCTION: Converts a key (like "Jones") into an array index (like 5)
 *    - Example: "Jones" has 5 letters → 5 % 10 = index 5
 *    
 * 2. STORE: Put the value at that index in the array
 *    - hashtable[5] = Employee{Jane Jones, id=123}
 *    
 * 3. RETRIEVE: Use the same hash function to find the index instantly
 *    - Want "Jones"? Hash it → index 5 → grab hashtable[5] → DONE!
 * 
 * THE COLLISION PROBLEM:
 * ----------------------
 * What if "Jones" (5 letters) and "Smith" (5 letters) both hash to index 5?
 * This is called a COLLISION - two keys trying to use the same spot!
 * 
 * SOLUTION: LINEAR PROBING
 * ------------------------
 * If a spot is taken, check the next spot, then the next, until you find an empty one.
 * It's like a parking lot - if your favorite spot is taken, you park in the next available spot!
 * 
 * Example:
 * - "Jones" → index 5 → STORED at index 5
 * - "Smith" → index 5 → OCCUPIED! → Try index 6 → STORED at index 6
 * - Later search "Smith" → index 5 → Wrong key! → Try index 6 → FOUND IT!
 * 
 * PERFORMANCE:
 * ------------
 * - BEST CASE: O(1) - Direct access, no collisions
 * - AVERAGE CASE: O(1) - A few collisions, but still very fast
 * - WORST CASE: O(n) - Everything hashes to same spot (rare with good hash function)
 * 
 * COMPARISON WITH OTHER DATA STRUCTURES:
 * ---------------------------------------
 * 
 * HASH TABLE vs ARRAY:
 * - Array: Find by index (fast), find by value (slow - must search entire array)
 * - Hash Table: Find by key (fast - calculate index directly)
 * 
 * HASH TABLE vs LINKED LIST:
 * - Linked List: Must traverse from head, O(n) time
 * - Hash Table: Direct access via hash function, O(1) average time
 * 
 * When to use what:
 * - Use ARRAY when: You know the index, need ordered access
 * - Use LINKED LIST when: Frequent insertions/deletions at beginning
 * - Use HASH TABLE when: Need fast lookups by key, don't care about order
 * 
 * @author Data Structures Learning Project
 * @version 1.0
 */
public class SimpleHashtable {

    /**
     * The underlying array that stores our key-value pairs.
     * Each element is a StoredEmployee (key + employee object) or null (empty slot).
     * 
     * Think of this like a row of 10 lockers, numbered 0-9.
     * Some lockers have items in them (StoredEmployee objects).
     * Some lockers are empty (null).
     */
    private StoredEmployee[] hashtable;

    private StoredEmployee[] hashtable;

    /**
     * CONSTRUCTOR - Initialize the Hash Table
     * ========================================
     * 
     * Creates a new hash table with 10 slots (indices 0-9).
     * All slots start as null (empty).
     * 
     * Think of this like setting up 10 empty lockers in a locker room.
     * 
     * WHY SIZE 10?
     * - Small enough to understand and visualize
     * - Large enough to reduce collisions for our examples
     * - In production, hash tables often start at 16, 32, or larger sizes
     * - When the table gets ~70% full, it's often resized (rehashed) to maintain performance
     */
    public SimpleHashtable(){
        hashtable = new StoredEmployee[10];  // Create array of 10 empty slots
    }


    /**
     * PUT - Insert a Key-Value Pair into the Hash Table
     * ==================================================
     * 
     * This method stores an employee using a key (usually their last name).
     * If there's a collision (spot already taken), it uses LINEAR PROBING to find
     * the next available slot.
     * 
     * STEP-BY-STEP PROCESS:
     * ---------------------
     * 1. Hash the key to get an index: "Jones" → 5
     * 2. Check if that spot is occupied
     * 3. If OCCUPIED: Use linear probing (check next spots)
     * 4. If EMPTY: Store the employee there
     * 5. If table is FULL: Print error message
     * 
     * LINEAR PROBING EXAMPLE:
     * -----------------------
     * Suppose our hash function is: key.length() % 10
     * 
     * put("Jones", janeEmployee):  // "Jones" = 5 letters
     *   - Hash: 5 % 10 = 5
     *   - Index 5 is empty
     *   - Store at index 5 ✓
     * 
     * put("Smith", maryEmployee):  // "Smith" = 5 letters  
     *   - Hash: 5 % 10 = 5
     *   - Index 5 is OCCUPIED by Jones! (COLLISION!)
     *   - Try index 6 → empty
     *   - Store at index 6 ✓
     * 
     * put("Brown", billEmployee):  // "Brown" = 5 letters
     *   - Hash: 5 % 10 = 5
     *   - Index 5 occupied by Jones
     *   - Index 6 occupied by Smith
     *   - Index 7 is empty
     *   - Store at index 7 ✓
     * 
     * WRAP-AROUND:
     * If we reach the end of the array (index 9), we wrap around to index 0.
     * This is like going around a circular track - when you reach the finish line,
     * you loop back to the start.
     * 
     * @param key The search key (usually the employee's last name)
     * @param employee The Employee object to store
     * 
     * TIME COMPLEXITY:
     * - Best Case: O(1) - No collision, direct insert
     * - Average Case: O(1) - Few collisions
     * - Worst Case: O(n) - Must probe through entire array (table is nearly full)
     */
    public void put(String key, Employee employee){
        int hashedKey = hashKeys(key);  // Step 1: Calculate the index
        
        if(occupied(hashedKey)){  // Step 2: Is this spot taken?
            // YES! We have a COLLISION - use LINEAR PROBING
            
            // Remember where we started to avoid infinite loops
            int stopIndex = hashedKey;
            
            // Move to the next index (wrap around if at end)
            if(hashedKey==hashtable.length-1){
                hashedKey=0;  // Wrap to beginning
            }
            else{
                hashedKey++;  // Move to next spot
            }

            // Keep probing until we find an empty spot OR loop back to start
            while(occupied(hashedKey) && hashedKey!=stopIndex){
                hashedKey = (hashedKey +1) % hashtable.length;  // Use modulo for wrap-around
            }

        }

        // After probing, check if we found an empty spot
        if(occupied(hashedKey)){
            // Uh oh! The entire table is FULL - no space left!
            System.out.println("Sorry there is already an employee at position"+hashedKey);
        }
        else{
            // SUCCESS! Found an empty spot - store the employee here
            hashtable[hashedKey]=new StoredEmployee(key,employee);
        }
    }


    /**
     * GET - Retrieve an Employee by Key
     * ==================================
     * 
     * This method finds and returns an employee using their key (last name).
     * It uses the same hash function and linear probing logic as put().
     * 
     * STEP-BY-STEP PROCESS:
     * ---------------------
     * 1. Hash the key to get starting index
     * 2. Use findKey() to locate the employee (handles collisions)
     * 3. If found, return the employee
     * 4. If not found, return null
     * 
     * EXAMPLE:
     * --------
     * Table state:
     * Index 5: StoredEmployee("Jones", janeEmployee)
     * Index 6: StoredEmployee("Smith", maryEmployee)
     * 
     * get("Smith"):
     *   - Hash "Smith" → index 5
     *   - Check index 5: key is "Jones" (not "Smith") → keep looking
     *   - Check index 6: key is "Smith" → MATCH! Return maryEmployee ✓
     * 
     * get("Wilson"):
     *   - Hash "Wilson" → index 6
     *   - Use findKey() to search
     *   - findKey() returns -1 (not found)
     *   - Return null (Wilson is not in the table)
     * 
     * @param key The search key (usually the employee's last name)
     * @return The Employee object if found, or null if not found
     * 
     * TIME COMPLEXITY:
     * - Best Case: O(1) - Found at first hash location
     * - Average Case: O(1) - Found after a few probes
     * - Worst Case: O(n) - Must search entire array
     */
    public Employee get(String key){
        int hashedKey = findKey(key);  // Find the index where this key is stored
        
        if(hashedKey==-1){
            // Key not found in the hash table
            return null;
        }

        // Key found! Return the employee at that index
        return hashtable[hashedKey].employee;
    }

    /**
     * REMOVE - Delete an Employee from the Hash Table
     * ================================================
     * 
     * Removing from a hash table with linear probing is TRICKY!
     * We can't just delete the item and leave a hole, because that would break
     * the probing chain for other items.
     * 
     * THE PROBLEM:
     * ------------
     * Table state:
     * Index 5: StoredEmployee("Jones", janeEmployee)
     * Index 6: StoredEmployee("Smith", maryEmployee)  // Collided, probed here
     * 
     * If we simply delete "Jones" and set index 5 to null:
     * Index 5: null  (empty!)
     * Index 6: StoredEmployee("Smith", maryEmployee)
     * 
     * Now when we search for "Smith":
     * - Hash "Smith" → index 5
     * - Index 5 is NULL → Not found! Return null ✗
     * - We never check index 6 because we stopped at the null!
     * 
     * But Smith IS in the table at index 6! We lost it!
     * 
     * THE SOLUTION: REHASHING
     * -----------------------
     * After removing an item, we REHASH the entire table:
     * 1. Save a copy of the current table
     * 2. Create a fresh empty table
     * 3. Re-insert all items (except the removed one) using put()
     * 4. This rebuilds the probing chains correctly
     * 
     * EXAMPLE:
     * --------
     * remove("Jones"):
     * 1. Find "Jones" at index 5
     * 2. Save janeEmployee to return later
     * 3. Copy hashtable to oldhashtable
     * 4. Create new empty hashtable
     * 5. Re-insert all items from oldhashtable except "Jones"
     *    - "Smith" gets re-hashed → index 5 (now empty!) → stored at index 5
     * 6. Return janeEmployee
     * 
     * Result:
     * Index 5: StoredEmployee("Smith", maryEmployee)  // Moved up!
     * Index 6: null  (empty)
     * 
     * Now searching for "Smith" works correctly!
     * 
     * @param key The search key of the employee to remove
     * @return The removed Employee object, or null if not found
     * 
     * TIME COMPLEXITY:
     * - O(n) where n is the table size
     * - Must rehash all items, which is expensive!
     * - This is why hash tables with linear probing can be slow for deletions
     * 
     * ALTERNATIVE: Chaining (see ChainedHashtable) avoids this problem!
     */
    public Employee remove(String key){
        int hashedKey  = findKey(key);  // Step 1: Find where the employee is stored
        
        if(hashtable[hashedKey]==null){
            // Employee not found
            return null;
        }
        
        // Step 2: Save the employee to return later
        Employee employee = hashtable[hashedKey].employee;
        hashtable[hashedKey] = null;  // Delete the employee

        // Step 3-5: REHASH the entire table to fix probing chains
        StoredEmployee[] oldhashtable = hashtable;  // Save current table
        hashtable = new StoredEmployee[oldhashtable.length];  // Create fresh empty table
        
        // Re-insert all non-null items from old table
        for(int i =0;i<oldhashtable.length;i++){
            if(oldhashtable[i]!=null){
                put(oldhashtable[i].key, oldhashtable[i].employee);
            }
        }

        return employee;  // Return the removed employee
    }


    /**
     * HASH KEYS - The Hash Function
     * ==============================
     * 
     * This is the HEART of the hash table! The hash function converts a key (String)
     * into an array index (integer from 0 to 9).
     * 
     * OUR SIMPLE HASH FUNCTION:
     * -------------------------
     * index = key.length() % hashtable.length
     * 
     * Translation: Use the length of the key, then use modulo to fit it in the array.
     * 
     * EXAMPLES:
     * ---------
     * "Jones" (5 letters) → 5 % 10 = 5
     * "Doe" (3 letters) → 3 % 10 = 3
     * "Smith" (5 letters) → 5 % 10 = 5 (COLLISION with Jones!)
     * "Wilson" (6 letters) → 6 % 10 = 6
     * 
     * WHY MODULO (%)?
     * ---------------
     * Modulo ensures the result fits in our array:
     * - Array has indices 0-9 (size 10)
     * - Any number % 10 gives a result from 0-9
     * - Example: 15 % 10 = 5, 23 % 10 = 3
     * 
     * REAL-WORLD ANALOGY:
     * -------------------
     * Think of a clock face with hours 1-12. When you add 5 hours to 10:00,
     * you get 3:00 (not 15:00). That's like modulo wrapping around!
     * 
     * GOOD vs BAD HASH FUNCTIONS:
     * ----------------------------
     * 
     * BAD Hash Function (this one!):
     * - Uses only string length
     * - Many collisions: "Jones", "Smith", "Brown" all → 5
     * - Doesn't distribute keys evenly
     * - Only good for learning!
     * 
     * GOOD Hash Function (like Java's String.hashCode()):
     * - Uses all characters in the string
     * - Formula: s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
     * - Much better distribution
     * - Fewer collisions
     * 
     * Example with good hash function:
     * "Jones".hashCode() = 71337770
     * "Smith".hashCode() = 80788917
     * "Brown".hashCode() = 64986311
     * All different! Less collisions!
     * 
     * @param key The key to hash (usually employee's last name)
     * @return An index from 0 to 9 (for our size-10 array)
     * 
     * TIME COMPLEXITY: O(1) - Constant time
     */
    private int hashKeys(String key){
        return key.length() % hashtable.length;
    }

    /**
     * FIND KEY - Locate a Key Using Linear Probing
     * =============================================
     * 
     * This helper method searches for a key in the hash table, handling collisions
     * using the same linear probing logic as put().
     * 
     * SEARCH ALGORITHM:
     * -----------------
     * 1. Hash the key to get starting index
     * 2. Check if that slot has the matching key
     * 3. If NO MATCH: Probe forward (linear probing)
     * 4. Stop when:
     *    a) We find the matching key → return its index
     *    b) We find an empty slot (null) → key doesn't exist, return -1
     *    c) We loop back to where we started → key doesn't exist, return -1
     * 
     * DETAILED EXAMPLE:
     * -----------------
     * Table state:
     * Index 3: StoredEmployee("Doe", johnEmployee)
     * Index 4: null
     * Index 5: StoredEmployee("Jones", janeEmployee)
     * Index 6: StoredEmployee("Smith", maryEmployee)  // Collided from index 5
     * Index 7: null
     * 
     * findKey("Smith"):
     *   1. Hash "Smith" → index 5
     *   2. Check index 5: key is "Jones" (not "Smith") → continue probing
     *   3. Move to index 6 (wrap: (5+1) % 10 = 6)
     *   4. Check index 6: key is "Smith" → MATCH! Return 6 ✓
     * 
     * findKey("Wilson"):
     *   1. Hash "Wilson" → index 6
     *   2. Check index 6: key is "Smith" (not "Wilson") → continue probing
     *   3. Move to index 7
     *   4. Check index 7: null (empty) → "Wilson" is not in table, return -1 ✓
     * 
     * WHY STOP AT NULL?
     * -----------------
     * If we hit a null slot during linear probing, it means the key was never inserted.
     * When we INSERT using linear probing, we fill slots consecutively.
     * So if there's a gap (null), the item we're searching for isn't beyond that gap.
     * 
     * Think of it like searching for a book on a shelf:
     * - Books are placed left-to-right without gaps
     * - If you find an empty space while searching, the book isn't further right
     * 
     * @param key The key to search for
     * @return The index where the key is stored, or -1 if not found
     * 
     * TIME COMPLEXITY:
     * - Best Case: O(1) - Found at first hash location
     * - Average Case: O(1) - Found after a few probes  
     * - Worst Case: O(n) - Must search entire array
     */
    private int findKey(String key){
        int hashedKey = hashKeys(key);  // Step 1: Calculate starting index
        
        // Step 2: Check if we found it at the first try
        if(hashtable[hashedKey]!=null &&
                hashtable[hashedKey].key.equals(key)){
            return hashedKey;  // Lucky! Found it immediately
        }

        // Not found at first try - need to probe
        int stopIndex = hashedKey;  // Remember where we started
        
        // Move to next index (with wrap-around)
        if (hashedKey == hashtable.length - 1) {
            hashedKey = 0;  // At end, wrap to beginning
        } else {
            hashedKey++;  // Move to next spot
        }
        
        // Keep probing until one of three conditions:
        // 1. We loop back to start (stopIndex)
        // 2. We find an empty slot (null)
        // 3. We find the matching key
        while (hashedKey != stopIndex &&
                hashtable[hashedKey] != null &&
                !hashtable[hashedKey].key.equals(key)) {
            hashedKey = (hashedKey + 1) % hashtable.length;  // Probe to next slot
        }
        
        // Check why we stopped:
        if(hashtable[hashedKey]!= null &&
                hashtable[hashedKey].key.equals(key)){
            // We found the matching key!
            return hashedKey;
        }
        else{
            // We hit null OR looped back to start → key not in table
            return -1;
        }
    }

    /**
     * OCCUPIED - Check if a Slot is Occupied
     * =======================================
     * 
     * Simple helper method that returns true if a slot contains data,
     * or false if it's empty (null).
     * 
     * This makes the code more readable:
     * - "if(occupied(5))" is clearer than "if(hashtable[5] != null)"
     * 
     * @param hashedkey The index to check
     * @return true if the slot is occupied, false if empty
     * 
     * TIME COMPLEXITY: O(1) - Constant time, just checking null
     */
    private boolean occupied(int hashedkey){
        return hashtable[hashedkey]!=null;
    }

    /**
     * PRINT HASHTABLE - Display the Current State
     * ============================================
     * 
     * This method prints out the entire hash table, showing which slots are
     * occupied and which are empty. Very useful for debugging and understanding
     * how the hash table works!
     * 
     * OUTPUT FORMAT:
     * --------------
     * position0: Employee{firstName='John', lastName='Doe', id=4567}
     * position1: empty
     * position2: empty
     * position3: Employee{firstName='Mary', lastName='Smith', id=22}
     * ...
     * 
     * This helps visualize:
     * - Where employees are stored
     * - Which slots have collisions (items not at their "natural" hash position)
     * - How full the table is
     * 
     * TIME COMPLEXITY: O(n) where n is table size
     * - Must check every slot in the array
     */
    public void printHashtable(){
        for(int i=0;i<hashtable.length;i++){
            if(hashtable[i]==null){
                System.out.println("empty");
            }
            else{
                System.out.println("position"+i+": "+hashtable[i].employee);
            }
        }
    }
}

/**
 * ==========================================
 * INTERVIEW QUESTIONS & ANSWERS - Hash Tables
 * ==========================================
 * 
 * Q1: Explain the Two Sum problem and how hash tables solve it efficiently.
 * A1: TWO SUM PROBLEM:
 *     Given an array of integers and a target sum, find two numbers that add up to the target.
 *     
 *     Example: nums = [2, 7, 11, 15], target = 9
 *     Answer: [0, 1] because nums[0] + nums[1] = 2 + 7 = 9
 *     
 *     BRUTE FORCE SOLUTION: O(n²)
 *     Check every pair of numbers:
 *     for (i = 0 to n-1)
 *         for (j = i+1 to n-1)
 *             if (nums[i] + nums[j] == target)
 *                 return [i, j]
 *     
 *     HASH TABLE SOLUTION: O(n)
 *     Use a hash table to remember numbers we've seen:
 *     
 *     HashMap<Integer, Integer> map = new HashMap<>();
 *     for (int i = 0; i < nums.length; i++) {
 *         int complement = target - nums[i];  // What number do we need?
 *         if (map.containsKey(complement)) {  // Have we seen it before?
 *             return new int[]{map.get(complement), i};  // Yes! Return indices
 *         }
 *         map.put(nums[i], i);  // Remember this number and its index
 *     }
 *     
 *     Example walkthrough with nums = [2, 7, 11, 15], target = 9:
 *     i=0: nums[0]=2, complement=7, map is empty, add (2,0) to map
 *     i=1: nums[1]=7, complement=2, map contains 2! Return [0,1] ✓
 *     
 *     WHY IS IT FASTER?
 *     - Hash table lookup is O(1)
 *     - We make one pass through the array: O(n)
 *     - Total: O(n) vs O(n²) for brute force
 *     - For 1000 items: 1000 operations vs 1,000,000 operations!
 * 
 * Q2: How would you use a hash table to group anagrams?
 * A2: ANAGRAM GROUPING PROBLEM:
 *     Group strings that are anagrams (same letters, different order).
 *     
 *     Example: ["eat", "tea", "tan", "ate", "nat", "bat"]
 *     Output: [["eat","tea","ate"], ["tan","nat"], ["bat"]]
 *     
 *     SOLUTION:
 *     Use the SORTED STRING as the key in a hash table.
 *     All anagrams will have the same sorted key!
 *     
 *     Map<String, List<String>> groups = new HashMap<>();
 *     for (String word : words) {
 *         char[] chars = word.toCharArray();
 *         Arrays.sort(chars);  // Sort the letters
 *         String key = new String(chars);  // This is our hash key
 *         
 *         if (!groups.containsKey(key)) {
 *             groups.put(key, new ArrayList<>());
 *         }
 *         groups.get(key).add(word);  // Add word to its group
 *     }
 *     return new ArrayList<>(groups.values());
 *     
 *     Example walkthrough:
 *     "eat" → sorted: "aet" → groups["aet"] = ["eat"]
 *     "tea" → sorted: "aet" → groups["aet"] = ["eat", "tea"]
 *     "tan" → sorted: "ant" → groups["ant"] = ["tan"]
 *     "ate" → sorted: "aet" → groups["aet"] = ["eat", "tea", "ate"]
 *     "nat" → sorted: "ant" → groups["ant"] = ["tan", "nat"]
 *     "bat" → sorted: "abt" → groups["abt"] = ["bat"]
 *     
 *     Result: [["eat","tea","ate"], ["tan","nat"], ["bat"]]
 *     
 *     TIME COMPLEXITY: O(n * k log k)
 *     - n = number of words
 *     - k = average word length
 *     - Sorting each word takes O(k log k)
 *     - Hash operations are O(1)
 * 
 * Q3: What causes hash collisions and how do you handle them?
 * A3: CAUSES OF HASH COLLISIONS:
 *     
 *     1. PIGEONHOLE PRINCIPLE:
 *        If you have more items than slots, collisions are guaranteed!
 *        Example: 100 keys, 10 slots → at least 10 keys in one slot
 *     
 *     2. POOR HASH FUNCTION:
 *        Our simple function (key.length() % 10) causes many collisions:
 *        "Jones", "Smith", "Brown" all have 5 letters → all hash to same index!
 *     
 *     3. NON-UNIFORM DISTRIBUTION:
 *        Even good hash functions have collisions due to randomness.
 *     
 *     COLLISION HANDLING METHODS:
 *     
 *     METHOD 1: LINEAR PROBING (used in SimpleHashtable)
 *     - If slot is taken, try the next slot, then next, etc.
 *     - PROS: Simple, good cache performance (data is contiguous)
 *     - CONS: Clustering (groups of occupied slots form), expensive deletions
 *     
 *     METHOD 2: CHAINING (used in ChainedHashtable)
 *     - Each slot holds a LINKED LIST of all items that hash there
 *     - PROS: Easy insertion/deletion, no clustering, no table-full problem
 *     - CONS: Extra memory for links, poor cache performance
 *     
 *     METHOD 3: QUADRATIC PROBING
 *     - Instead of checking i+1, i+2, i+3, check i+1², i+2², i+3²
 *     - PROS: Reduces clustering compared to linear probing
 *     - CONS: More complex, can still have clustering
 *     
 *     METHOD 4: DOUBLE HASHING
 *     - Use a second hash function to determine probe step size
 *     - PROS: Best distribution, minimizes clustering
 *     - CONS: More complex, requires good second hash function
 *     
 *     COMPARISON:
 *     Linear Probing: Fast but clusters
 *     Chaining: Flexible but uses more memory
 *     Quadratic/Double: Complex but better distribution
 * 
 * Q4: What is load factor and why does it matter?
 * A4: LOAD FACTOR DEFINITION:
 *     Load Factor = (Number of items in table) / (Table size)
 *     
 *     Example:
 *     - Table size = 10
 *     - Items stored = 7
 *     - Load factor = 7/10 = 0.7 = 70%
 *     
 *     WHY IT MATTERS:
 *     As load factor increases, collisions become more frequent!
 *     
 *     Load Factor 0.25 (25% full):
 *     - Few collisions
 *     - Lots of wasted space
 *     - Very fast operations
 *     
 *     Load Factor 0.5 (50% full):
 *     - Some collisions
 *     - Balanced space/performance
 *     - Still fast operations
 *     
 *     Load Factor 0.75 (75% full):
 *     - More frequent collisions
 *     - Good space utilization
 *     - Performance starts degrading
 *     - Java's HashMap rehashes at 0.75!
 *     
 *     Load Factor 0.9+ (90%+ full):
 *     - MANY collisions
 *     - Poor performance
 *     - Approaching O(n) instead of O(1)
 *     
 *     IMPACT ON LINEAR PROBING:
 *     At 90% full with linear probing, you might probe through 10+ slots
 *     to find an empty one! This defeats the purpose of O(1) access.
 *     
 *     IMPACT ON CHAINING:
 *     At high load factors, chains get long. If load factor = 2.0,
 *     average chain length is 2, so you're doing O(2) = O(1) work.
 *     But at load factor 10.0, chains have 10 items, and searching
 *     becomes O(10) = O(n) for practical purposes.
 * 
 * Q5: Explain the rehashing process and when it's needed.
 * A5: WHAT IS REHASHING?
 *     Rehashing is creating a NEW, LARGER hash table and moving all items to it.
 *     
 *     WHEN TO REHASH:
 *     - When load factor exceeds a threshold (usually 0.75)
 *     - Java's HashMap rehashes when 75% full
 *     - Our SimpleHashtable rehashes after remove() to fix probing chains
 *     
 *     REHASHING PROCESS:
 *     1. Create new array (usually 2× the current size)
 *     2. For each item in old array:
 *        a. Calculate NEW hash (because table size changed!)
 *        b. Insert into new array
 *     3. Replace old array with new array
 *     
 *     EXAMPLE:
 *     Old table (size 10, load factor 0.8):
 *     [item0, item1, null, item2, item3, item4, null, item5, item6, item7]
 *     
 *     Trigger: Try to insert 9th item → 9/10 = 0.9 > 0.75 threshold
 *     
 *     Rehashing steps:
 *     1. Create new table of size 20
 *     2. Re-hash all 8 items:
 *        - Old: item0 at index 3 (using hash % 10)
 *        - New: item0 at index 13 (using hash % 20) - different position!
 *     3. Insert new 9th item
 *     4. New load factor: 9/20 = 0.45 - much better!
 *     
 *     WHY RECALCULATE HASHES?
 *     Hash function uses table size: index = hash % size
 *     - Old: hash % 10 might give index 7
 *     - New: hash % 20 might give index 17
 *     - Same hash value, different index!
 *     
 *     TIME COMPLEXITY OF REHASHING:
 *     - O(n) where n is number of items
 *     - Must touch every item once
 *     - Expensive! But infrequent
 *     
 *     AMORTIZED COST:
 *     Although rehashing is O(n), it happens rarely.
 *     If we double the size each time, rehashing happens at sizes:
 *     16, 32, 64, 128, 256, ...
 *     
 *     Over many insertions, the AVERAGE cost per insertion is still O(1)!
 *     This is called "amortized constant time."
 *     
 *     Example: Insert 100 items
 *     - Rehash at 16, 32, 64, 128
 *     - Total rehash cost: 16+32+64 = 112 operations
 *     - Average per insert: 112/100 = 1.12 ≈ O(1)
 * 
 * ==========================================
 * COMPLEXITY SUMMARY
 * ==========================================
 * 
 * SIMPLE HASHTABLE (Linear Probing):
 * 
 * Operation    | Best Case | Average Case | Worst Case | Notes
 * -------------|-----------|--------------|------------|----------------------------
 * put()        | O(1)      | O(1)         | O(n)       | Worst: table nearly full
 * get()        | O(1)      | O(1)         | O(n)       | Worst: long probe sequence
 * remove()     | O(n)      | O(n)         | O(n)       | Must rehash entire table
 * hashKeys()   | O(1)      | O(1)         | O(1)       | Simple calculation
 * findKey()    | O(1)      | O(1)         | O(n)       | Depends on probe length
 * 
 * SPACE COMPLEXITY: O(n)
 * - Array of size n (actually slightly larger for load factor < 1)
 * - Each slot holds StoredEmployee (2 references)
 * - Total: O(n) space
 * 
 * COMPARISON WITH OTHER DATA STRUCTURES:
 * 
 * Data Structure | Insert  | Search  | Delete  | Ordered? | Notes
 * ---------------|---------|---------|---------|----------|----------------------
 * Array          | O(n)    | O(n)    | O(n)    | Yes      | Search by value
 * Sorted Array   | O(n)    | O(log n)| O(n)    | Yes      | Binary search
 * Linked List    | O(1)*   | O(n)    | O(n)    | No       | *At head/tail
 * Hash Table     | O(1)**  | O(1)**  | O(1)*** | No       | **Average, ***Chaining
 * Binary Search  | O(log n)| O(log n)| O(log n)| Yes      | Balanced tree
 * 
 * WHEN TO USE HASH TABLES:
 * ✓ Need fast lookups by key
 * ✓ Don't need items in sorted order
 * ✓ Have good hash function for keys
 * ✓ Approximately know the data size (to set initial capacity)
 * 
 * WHEN NOT TO USE:
 * ✗ Need sorted order
 * ✗ Need to find min/max efficiently
 * ✗ Need range queries (find all keys between X and Y)
 * ✗ Memory is extremely limited
 */
