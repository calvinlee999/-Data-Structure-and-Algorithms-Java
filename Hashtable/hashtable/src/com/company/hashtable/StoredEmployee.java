package com.company.hashtable;

/**
 * STOREDEMPLOYEE CLASS - The Key-Value Pair Container
 * ====================================================
 * 
 * WHAT IS THIS CLASS?
 * -------------------
 * This class is like a labeled box that holds two things together:
 * 1. A KEY (label on the box) - a String that helps us find the employee
 * 2. A VALUE (contents of the box) - the actual Employee object with all their information
 * 
 * REAL-WORLD ANALOGY:
 * -------------------
 * Think of a library card catalog (or contact list on your phone):
 * - KEY: The person's name ("Jones") - what you search for
 * - VALUE: The person's information (phone number, address, etc.) - what you want to find
 * 
 * Together, they form a PAIR:
 * "Jones" → Employee{firstName='Jane', lastName='Jones', id=123}
 * 
 * WHY DO WE NEED THIS CLASS?
 * --------------------------
 * When we store an Employee in a hash table, we need to remember BOTH:
 * 1. The key we used to store it (so we can verify we found the right one)
 * 2. The actual Employee object (the data we care about)
 * 
 * EXAMPLE WITHOUT StoredEmployee (BAD):
 * If we only stored the Employee object, we'd lose track of what key was used.
 * How would we know if we found the right employee when there are collisions?
 * 
 * EXAMPLE WITH StoredEmployee (GOOD):
 * We can check: "Is this the employee I'm looking for?"
 * - Check if the stored key matches the key I'm searching for
 * - If yes, return the employee object
 * - If no, keep looking (handle collision)
 * 
 * This is especially important for handling HASH COLLISIONS (when two different
 * keys map to the same array position).
 * 
 * @author Data Structures Learning Project
 * @version 1.0
 */
public class StoredEmployee {

    // PUBLIC FIELDS - Directly accessible
    // Note: In a production system, these would normally be private with getters/setters
    // They're public here for simplicity in the learning example
    
    /**
     * The KEY used to store and retrieve this employee
     * Example: "Jones", "Doe", "Smith"
     * 
     * This is like the contact name in your phone - you search by this to find the details
     */
    public String key;
    
    /**
     * The VALUE - the actual Employee object containing all the employee data
     * Example: Employee{firstName='Jane', lastName='Jones', id=123}
     * 
     * This is like the phone number and address - the information you want
     */
    public Employee employee;

    /**
     * CONSTRUCTOR - Creating a Key-Value Pair
     * ========================================
     * 
     * This bundles together a key and an employee into a single unit.
     * It's like creating a contact entry: you need both a name (key) and details (employee).
     * 
     * @param key The search key (usually the employee's last name)
     * @param employee The Employee object containing all the employee's information
     * 
     * EXAMPLE:
     * StoredEmployee entry = new StoredEmployee("Jones", janeJonesEmployee);
     * 
     * Now we have a package that contains:
     * - The label "Jones" (the key)
     * - Jane's complete employee record (the value)
     */
    public StoredEmployee(String key, Employee employee) {
        this.key = key;
        this.employee = employee;
    }
}

/**
 * ==========================================
 * INTERVIEW QUESTIONS & ANSWERS - StoredEmployee Class
 * ==========================================
 * 
 * Q1: Why do we need both the key AND the employee object in StoredEmployee?
 * A1: This is crucial for COLLISION HANDLING in hash tables!
 *     
 *     When a collision occurs (two keys hash to the same index), we need to:
 *     1. Store multiple key-value pairs at the same location
 *     2. Check each pair to find the one with the MATCHING KEY
 *     
 *     Example Scenario:
 *     Both "Jones" and "Smith" hash to index 3.
 *     
 *     Index 3 might contain:
 *     [StoredEmployee("Jones", janeEmployee), StoredEmployee("Smith", maryEmployee)]
 *     
 *     When searching for "Smith":
 *     - Go to index 3
 *     - Check first StoredEmployee: key is "Jones" → not a match, keep looking
 *     - Check second StoredEmployee: key is "Smith" → MATCH! Return the employee
 *     
 *     If we didn't store the key, we wouldn't know which Employee is which!
 * 
 * Q2: Why are the fields public instead of private?
 * A2: This is a design choice for SIMPLICITY in this learning example:
 *     
 *     PROS of public fields here:
 *     - Less code to write (no getters/setters needed)
 *     - Direct access is faster
 *     - StoredEmployee is used internally by SimpleHashtable (not exposed to users)
 *     
 *     CONS of public fields:
 *     - Breaks encapsulation (OOP best practice)
 *     - No validation possible
 *     - Can't change internal implementation without breaking code
 *     
 *     IN PRODUCTION CODE: You should make these private with getters/setters!
 *     
 *     Example with proper encapsulation:
 *     private String key;
 *     private Employee employee;
 *     
 *     public String getKey() { return key; }
 *     public Employee getEmployee() { return employee; }
 * 
 * Q3: How does StoredEmployee help with LINEAR PROBING?
 * A3: Linear probing is a collision resolution technique where we search for the next
 *     available slot when a collision occurs.
 *     
 *     StoredEmployee is essential because:
 *     1. We probe through the array looking for an empty slot OR matching key
 *     2. At each position, we check: storedEmployee.key.equals(searchKey)
 *     3. If the key matches, we found it! Return storedEmployee.employee
 *     4. If not, keep probing to the next position
 *     
 *     Example:
 *     put("Jones", janeEmployee) → goes to index 3
 *     put("Smith", maryEmployee) → also hashes to 3 (COLLISION!)
 *     
 *     Linear probing steps:
 *     - Index 3 is occupied by StoredEmployee("Jones", janeEmployee)
 *     - Move to index 4 → empty, store StoredEmployee("Smith", maryEmployee)
 *     
 *     Later, when searching for "Smith":
 *     - Hash "Smith" → index 3
 *     - Check index 3: key is "Jones" (not "Smith") → keep probing
 *     - Check index 4: key is "Smith" → FOUND IT!
 *     
 *     Without storing the key, we couldn't tell if we found the right employee!
 * 
 * Q4: What's the difference between this class and a Map.Entry in Java?
 * A4: Great observation! They serve similar purposes:
 *     
 *     StoredEmployee (our custom class):
 *     - Holds a String key and Employee value
 *     - Simple, specific to our needs
 *     - Public fields for easy access
 *     
 *     Map.Entry<K,V> (Java's built-in interface):
 *     - Generic - works with any key/value types
 *     - Used by HashMap, TreeMap, etc.
 *     - Has getKey() and getValue() methods
 *     
 *     We created StoredEmployee to:
 *     - Learn how hash tables work internally
 *     - Keep our implementation simple and clear
 *     - Avoid Java's generics complexity for beginners
 *     
 *     In professional code, you might use Map.Entry or create a custom class
 *     depending on your needs.
 * 
 * Q5: Could we just store Employee objects without the key?
 * A5: NO! This would break the hash table's ability to find items correctly.
 *     
 *     Problem scenario:
 *     Suppose "Jones" and "Smith" both hash to index 3.
 *     
 *     Without keys stored:
 *     hashtable[3] = janeEmployee  // First, store Jane
 *     hashtable[4] = maryEmployee  // Collision, probe to index 4
 *     
 *     When searching for "Smith":
 *     - Hash "Smith" → index 3
 *     - Find janeEmployee at index 3
 *     - How do we know this ISN'T Smith? WE CAN'T!
 *     - We might incorrectly return Jane when searching for Smith
 *     
 *     With keys stored:
 *     hashtable[3] = StoredEmployee("Jones", janeEmployee)
 *     hashtable[4] = StoredEmployee("Smith", maryEmployee)
 *     
 *     When searching for "Smith":
 *     - Hash "Smith" → index 3
 *     - Check: "Jones".equals("Smith")? NO → keep probing
 *     - Check index 4: "Smith".equals("Smith")? YES → return maryEmployee
 *     
 *     The key is ESSENTIAL for correctness!
 * 
 * ==========================================
 * DESIGN PATTERNS
 * ==========================================
 * 
 * This class implements the KEY-VALUE PAIR pattern:
 * - Common in all associative data structures (hash tables, maps, dictionaries)
 * - Separates the "how to find it" (key) from the "what it is" (value)
 * - Allows efficient searching, insertion, and deletion
 * 
 * ==========================================
 * COMPLEXITY ANALYSIS
 * ==========================================
 * 
 * Constructor: O(1) - Constant time
 * - Just assigns two references, instant operation
 * 
 * Space Complexity: O(1)
 * - Stores two references: one String, one Employee
 * - Fixed size regardless of how much data is in the hash table
 */
