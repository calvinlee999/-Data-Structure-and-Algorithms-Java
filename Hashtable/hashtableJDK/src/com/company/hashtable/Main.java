package com.company.hashtable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ========================================================================
 * JAVA'S BUILT-IN HASHMAP DEMONSTRATION
 * ========================================================================
 * 
 * This program shows how to use Java's HashMap class - the professional,
 * production-ready hash table that you'll use in real projects!
 * 
 * WHY USE JAVA'S HASHMAP?
 * -----------------------
 * Our SimpleHashtable and ChainedHashtable were great for LEARNING,
 * but Java's HashMap is what you use in PRODUCTION because:
 * 
 * 1. OPTIMIZED PERFORMANCE:
 *    - Uses better hash function
 *    - Automatically resizes (no manual rehashing)
 *    - Converts long chains to balanced trees (O(log n) worst case)
 * 
 * 2. MORE FEATURES:
 *    - putIfAbsent, getOrDefault, containsKey, containsValue
 *    - Thread-safe version available (ConcurrentHashMap)
 *    - Works with any key/value types (generics)
 * 
 * 3. WELL-TESTED:
 *    - Used by millions of developers
 *    - Handles edge cases we didn't think of
 *    - Highly optimized by Java experts
 * 
 * HASHMAP INTERNALS:
 * ------------------
 * HashMap uses CHAINING with enhancements:
 * - Initial capacity: 16 buckets
 * - Load factor threshold: 0.75 (resizes at 75% full)
 * - Chains longer than 8 items convert to balanced trees!
 * - Hash function: Uses object's hashCode() with additional mixing
 * 
 * COMPARISON WITH OUR IMPLEMENTATIONS:
 * ------------------------------------
 * SimpleHashtable (Linear Probing):
 * - Fixed size: 10 slots
 * - Simple hash: key.length() % 10
 * - No auto-resize
 * - O(n) deletion (rehashing)
 * 
 * ChainedHashtable:
 * - Fixed size: 10 slots
 * - Better hash: key.hashCode() % 10
 * - No auto-resize
 * - Simple linked lists
 * 
 * Java's HashMap:
 * - Auto-resize: Starts at 16, doubles when needed
 * - Excellent hash: hashCode() with mixing
 * - Auto-resize at load factor 0.75
 * - Trees for long chains (optimization!)
 * 
 * @author Data Structures Learning Project
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {

        // Create employees (same as before)
        Employee janeJones = new Employee("Jane", "Jones", 123);
        Employee johnDoe = new Employee("John", "Doe", 4567);
        Employee marySmith = new Employee("Mary", "Smith", 22);
        Employee mikeWilson = new Employee("Mike", "Wilson", 3245);
        Employee billEnd = new Employee("Bill", "End", 78);

        // ===================================================================
        // CREATE HASHMAP - Java's Professional Hash Table
        // ===================================================================
        // Map is the interface, HashMap is the implementation
        // <String, Employee> means: String keys, Employee values
        // This is called "generics" - type-safe at compile time!
        
        Map<String, Employee> hashmap = new HashMap<String, Employee>();
        // Or with type inference (Java 7+): 
        // Map<String, Employee> hashmap = new HashMap<>();

        // ===================================================================
        // PUT - Add Employees to HashMap
        // ===================================================================
        // put(key, value) adds or updates an entry
        // If key already exists, the old value is REPLACED
        
        hashmap.put("Jones",janeJones);   // Add Jane Jones
        hashmap.put("Doe",johnDoe);       // Add John Doe
        hashmap.put("Smith",marySmith);   // Add Mary Smith

        // ===================================================================
        // PUTIFABSENT - Conditional Insert
        // ===================================================================
        // putIfAbsent only adds if key doesn't exist
        // This is safer than put() if you don't want to overwrite!
        
        hashmap.putIfAbsent("Doe", mikeWilson);
        // "Doe" already exists (John), so Mike is NOT added
        // John Doe remains in the map
        
        // Comparison:
        // hashmap.put("Doe", mikeWilson);  // Would REPLACE John with Mike!
        // hashmap.putIfAbsent("Doe", mikeWilson);  // KEEPS John, ignores Mike


        // ===================================================================
        // GET - Retrieve Employees
        // ===================================================================
        // get(key) returns the value, or null if key doesn't exist
        
        System.out.println(hashmap.get("Smith"));
        // Output: Employee{firstName='Mary', lastName='Smith', id=22}
        
        // ===================================================================
        // GETORDEFAULT - Safe Retrieval with Fallback
        // ===================================================================
        // getOrDefault(key, defaultValue) prevents null pointer exceptions!
        // If key exists, return its value
        // If key doesn't exist, return the default value
        
        System.out.println(hashmap.getOrDefault("someone", billEnd));
        // "someone" doesn't exist in the map
        // So it returns billEnd as the default
        // Output: Employee{firstName='Bill', lastName='End', id=78}
        
        // WHY IS THIS USEFUL?
        // Without getOrDefault:
        // Employee emp = hashmap.get("someone");  // emp is null!
        // emp.getFirstName();  // NullPointerException! Program crashes!
        // 
        // With getOrDefault:
        // Employee emp = hashmap.getOrDefault("someone", billEnd);
        // emp.getFirstName();  // Returns "Bill" - no crash!

        // ===================================================================
        // REMOVE - Delete an Employee
        // ===================================================================
        // remove(key) removes the entry and returns the removed value
        
        System.out.println(hashmap.remove("Smith"));
        // Removes Mary Smith from the map
        // Returns: Employee{firstName='Mary', lastName='Smith', id=22}
        // If we try to remove again:
        // hashmap.remove("Smith");  // Returns null (already removed)


        // ===================================================================
        // CONTAINSKEY and CONTAINSVALUE - Checking Existence
        // ===================================================================
        // These methods let you check if something is in the map
        // WITHOUT retrieving it
        
        // containsKey: Check if a KEY exists (FAST - O(1))
//        System.out.println(hashmap.containsKey("Doe"));
        // Returns: true ("Doe" is a key in the map)
        // Uses hash table lookup - very fast!
        
        // containsValue: Check if a VALUE exists (SLOW - O(n))
//        System.out.println(hashmap.containsValue(marySmith));
        // Returns: false (we removed Mary Smith earlier)
        // Must search through ALL values - slow!
        
        // PERFORMANCE TIP:
        // - containsKey() is O(1) - uses hash table magic!
        // - containsValue() is O(n) - must check every value
        // - Prefer containsKey() when possible

        // ===================================================================
        // ITERATION - Loop Through the HashMap
        // ===================================================================
        // There are multiple ways to iterate through a HashMap:
        
        // METHOD 1: Iterator over values
        // Use this when you only need the VALUES (employees)
//        Iterator<Employee> iterator = hashmap.values().iterator();
//        while(iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
        // Output:
        // Employee{firstName='Jane', lastName='Jones', id=123}
        // Employee{firstName='John', lastName='Doe', id=4567}
        
        // METHOD 2: forEach with lambda (MODERN - Java 8+)
        // Use this when you need both KEYS and VALUES
        hashmap.forEach((k,v) -> System.out.println("key = "+k+", value="+v));
        // Output:
        // key = Jones, value=Employee{firstName='Jane', lastName='Jones', id=123}
        // key = Doe, value=Employee{firstName='John', lastName='Doe', id=4567}
        
        // LAMBDA EXPLANATION FOR 8TH GRADERS:
        // (k,v) -> ... is shorthand for:
        // for each entry in the map, k is the key, v is the value
        // It's like saying: "For each (key, value) pair, do this..."
        
        // OTHER ITERATION METHODS:
        // 
        // For just keys:
        // for (String key : hashmap.keySet()) { ... }
        // 
        // For key-value pairs (traditional):
        // for (Map.Entry<String, Employee> entry : hashmap.entrySet()) {
        //     String key = entry.getKey();
        //     Employee value = entry.getValue();
        // }

    }
}

/**
 * ==========================================
 * INTERVIEW QUESTIONS - Java's HashMap
 * ==========================================
 * 
 * Q1: How does HashMap handle hash collisions?
 * A1: HashMap uses SEPARATE CHAINING (like our ChainedHashtable):
 *     - Each bucket holds a linked list of entries
 *     - OPTIMIZATION: If a chain grows beyond 8 items, it converts to a
 *       balanced tree (Red-Black tree) for O(log n) worst-case performance
 *     - When chain shrinks below 6 items, converts back to linked list
 *     
 *     Example:
 *     bucket[5] → LinkedList: [entry1] → [entry2] → [entry3] → ...
 *     If list grows to 9 items:
 *     bucket[5] → Red-Black Tree with 9 nodes
 *     
 *     Benefit:
 *     - Worst case O(n) becomes O(log n)
 *     - Protects against poor hash functions or malicious input
 * 
 * Q2: When does HashMap resize, and how?
 * A2: HashMap automatically resizes when:
 *     - Load factor exceeds 0.75 (default threshold)
 *     - Formula: if (size > capacity * loadFactor) resize()
 *     
 *     Example:
 *     - Initial capacity: 16
 *     - Threshold: 16 * 0.75 = 12
 *     - After adding 12th item: resize triggered!
 *     
 *     Resize process:
 *     1. Create new array 2× larger (16 → 32)
 *     2. Rehash all entries into new array
 *     3. Update capacity and threshold
 *     4. Replace old array with new array
 *     
 *     Size progression: 16 → 32 → 64 → 128 → 256 → ...
 *     
 *     WHY 0.75?
 *     - Balance between space and performance
 *     - Lower (0.5): Wastes space, but faster
 *     - Higher (0.9): Saves space, but slower
 *     - 0.75 is the sweet spot!
 *     
 *     You can customize:
 *     new HashMap<>(32, 0.5f);  // Initial capacity 32, load factor 0.5
 * 
 * Q3: What makes a good hashCode() for use with HashMap?
 * A3: A good hashCode() should:
 *     
 *     1. CONSISTENT: Same object always returns same hash
 *        Bad: return Math.random();  // Different every time!
 *        Good: return id * 31;  // Always same for same id
 *     
 *     2. EQUAL OBJECTS HAVE EQUAL HASHES:
 *        If obj1.equals(obj2), then obj1.hashCode() == obj2.hashCode()
 *        
 *        Example:
 *        Employee emp1 = new Employee("Jane", "Jones", 123);
 *        Employee emp2 = new Employee("Jane", "Jones", 123);
 *        
 *        If we override equals() to compare by id:
 *        emp1.equals(emp2) → true (same id)
 *        
 *        Then we MUST override hashCode():
 *        public int hashCode() { return id; }
 *        
 *        Now both have same hash, so HashMap works correctly!
 *     
 *     3. GOOD DISTRIBUTION: Different objects have different hashes
 *        Bad: return 1;  // All objects hash to same bucket!
 *        Good: return id * 31 + firstName.hashCode();
 *        
 *        Why multiply by 31?
 *        - Prime number (reduces patterns)
 *        - Fast to compute (31*x = (x << 5) - x)
 *        - Proven to work well in practice
 *     
 *     4. FAST TO COMPUTE:
 *        Bad: Complex calculation taking milliseconds
 *        Good: Simple arithmetic in nanoseconds
 *        
 *        hashCode() is called EVERY time you access the map!
 *        Slow hashCode() = Slow HashMap!
 *     
 *     EXAMPLE IMPLEMENTATION:
 *     @Override
 *     public int hashCode() {
 *         int result = id;
 *         result = 31 * result + firstName.hashCode();
 *         result = 31 * result + lastName.hashCode();
 *         return result;
 *     }
 *     
 *     Or use Java's Objects.hash() utility:
 *     @Override
 *     public int hashCode() {
 *         return Objects.hash(id, firstName, lastName);
 *     }
 * 
 * Q4: What's the difference between HashMap, Hashtable, and ConcurrentHashMap?
 * A4: 
 *     HASHMAP (this example):
 *     - NOT thread-safe (only use in single-threaded code)
 *     - Allows null key and null values
 *     - Fast (no synchronization overhead)
 *     - Modern, recommended for most use cases
 *     
 *     Example:
 *     hashmap.put(null, employee);  // OK
 *     hashmap.put("key", null);     // OK
 *     
 *     HASHTABLE (legacy, avoid!):  
 *     - Thread-safe (synchronized methods)
 *     - Does NOT allow null keys or values
 *     - Slow (synchronization on every operation)
 *     - Old Java 1.0 class, mostly obsolete
 *     
 *     Example:
 *     Hashtable<String, Employee> ht = new Hashtable<>();
 *     ht.put(null, employee);  // NullPointerException!
 *     
 *     CONCURRENTHASHMAP (for multi-threading):
 *     - Thread-safe with better performance
 *     - Does NOT allow null keys or values
 *     - Uses fine-grained locking (segments)
 *     - Modern, use this for concurrent access
 *     
 *     Example:
 *     ConcurrentHashMap<String, Employee> map = new ConcurrentHashMap<>();
 *     // Safe to use from multiple threads simultaneously!
 *     
 *     WHEN TO USE WHAT:
 *     
 *     Single thread: HashMap
 *     - Fastest option
 *     - Most common use case
 *     
 *     Multiple threads (rare updates): Collections.synchronizedMap(new HashMap<>())
 *     - Wraps HashMap with synchronization
 *     - Simple but slow
 *     
 *     Multiple threads (frequent updates): ConcurrentHashMap
 *     - Best for high concurrency
 *     - More complex but much faster than synchronized HashMap
 *     
 *     Never use: Hashtable
 *     - Obsolete, use ConcurrentHashMap instead
 * 
 * Q5: Explain the time complexity of HashMap operations.
 * A5: 
 *     AVERAGE CASE (good hash function, load factor < 0.75):
 *     - put(): O(1)
 *     - get(): O(1)
 *     - remove(): O(1)
 *     - containsKey(): O(1)
 *     - containsValue(): O(n)  // Must check all values!
 *     
 *     WORST CASE (all keys hash to same bucket):
 *     - With linked list: O(n)
 *     - With tree (> 8 items): O(log n)  // Java 8+ optimization!
 *     
 *     AMORTIZED CASE (including resizing):
 *     - put(): O(1) amortized
 *       - Most inserts are O(1)
 *       - Occasional resize is O(n)
 *       - Average over many operations: O(1)
 *     
 *     Example calculation:
 *     Insert 100 items into HashMap:
 *     - 99 insertions: O(1) each = 99 operations
 *     - 1 resize at size 16: O(16) = 16 operations
 *     - 1 resize at size 32: O(32) = 32 operations
 *     - 1 resize at size 64: O(64) = 64 operations
 *     - Total: 99 + 16 + 32 + 64 = 211 operations
 *     - Average per insert: 211 / 100 = 2.11 ≈ O(1)
 *     
 *     SPACE COMPLEXITY:
 *     - O(n) where n is number of entries
 *     - Plus overhead for buckets (capacity)
 *     - Typically capacity = n / 0.75, so space = O(1.33n) = O(n)
 * 
 * ==========================================
 * KEY TAKEAWAYS
 * ==========================================
 * 
 * 1. USE JAVA'S HASHMAP IN PRODUCTION:
 *    - Don't implement your own (unless learning!)
 *    - HashMap is highly optimized and well-tested
 * 
 * 2. UNDERSTAND THE BASICS:
 *    - HashMap uses hashing for O(1) operations
 *    - Handles collisions with chaining (+ tree optimization)
 *    - Auto-resizes to maintain performance
 * 
 * 3. IMPORTANT METHODS:
 *    - put/get/remove: Core operations
 *    - putIfAbsent/getOrDefault: Safe operations
 *    - containsKey: Fast membership test
 *    - forEach: Modern iteration
 * 
 * 4. REMEMBER:
 *    - containsKey() is O(1) - uses hash!
 *    - containsValue() is O(n) - must search all!
 *    - Always override hashCode() when you override equals()
 */
