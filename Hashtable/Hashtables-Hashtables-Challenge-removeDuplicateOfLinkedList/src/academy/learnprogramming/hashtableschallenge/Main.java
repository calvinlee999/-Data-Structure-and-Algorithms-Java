package academy.learnprogramming.hashtableschallenge;

import java.util.*;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * HASHTABLE CHALLENGE #2: REMOVE DUPLICATES FROM LINKED LIST
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Remove all duplicate employees from a LinkedList using a HashMap.
 * 
 * For example:
 * Before: [Jane(123), John(5678), Mike(45), Mary(5555), John(5678), 
 *          Bill(3948), Jane(123)]
 * After:  [Jane(123), John(5678), Mike(45), Mary(5555), Bill(3948)]
 * 
 * Duplicates are determined by employee ID. If two employees have the
 * same ID, they are considered duplicates.
 * 
 * WHY USE A HASHMAP FOR THIS?
 * ---------------------------
 * - HashMap provides O(1) lookup time (super fast!)
 * - We can check if we've seen an employee before in constant time
 * - Much faster than nested loops which would be O(n²)
 * 
 * REAL-WORLD APPLICATIONS:
 * -----------------------
 * - Cleaning up customer databases with duplicate entries
 * - Removing duplicate records in data processing
 * - Finding unique visitors on a website
 * - Detecting duplicate transactions
 * - Email deduplication in spam filtering
 * 
 * TIME COMPLEXITY: O(n)
 * -------------------
 * - First loop (with iterator): O(n) - visit each employee once
 * - HashMap.containsKey(): O(1) average case
 * - Second loop (removing): O(m) where m = number of duplicates
 * - Total: O(n + m) = O(n) since m ≤ n
 * 
 * SPACE COMPLEXITY: O(n)
 * --------------------
 * - HashMap stores at most n unique employees: O(n)
 * - List to track duplicates: O(m) where m is number of duplicates
 * - Total: O(n + m) = O(n)
 * 
 * WHY THIS SOLUTION IS OPTIMAL:
 * ----------------------------
 * - Single pass through the list to identify duplicates
 * - O(1) lookup time using HashMap
 * - Preserves the order of first occurrence
 * - Much better than O(n²) nested loop approach
 * 
 * ALGORITHM STEPS:
 * ---------------
 * 1. Create a HashMap to track seen employees (by ID)
 * 2. Create a list to track which employees are duplicates
 * 3. Iterate through the LinkedList using an iterator
 * 4. For each employee:
 *    - If ID already in HashMap → it's a duplicate, add to removal list
 *    - If ID not in HashMap → it's unique, add to HashMap
 * 5. Remove all employees in the removal list from LinkedList
 * 6. Print the cleaned list
 * 
 * VISUALIZATION:
 * --------------
 * Processing: [Jane(123), John(5678), Mike(45), Jane(123)]
 * 
 * Step 1: Check Jane(123)
 *   - HashMap: {} → {123: Jane}
 *   - Not a duplicate! Add to HashMap
 * 
 * Step 2: Check John(5678)
 *   - HashMap: {123: Jane} → {123: Jane, 5678: John}
 *   - Not a duplicate! Add to HashMap
 * 
 * Step 3: Check Mike(45)
 *   - HashMap: {123: Jane, 5678: John} → {123: Jane, 5678: John, 45: Mike}
 *   - Not a duplicate! Add to HashMap
 * 
 * Step 4: Check Jane(123) again
 *   - HashMap contains 123 already!
 *   - It's a duplicate! Add to removal list
 * 
 * Step 5: Remove all from removal list
 *   - Final list: [Jane(123), John(5678), Mike(45)]
 * 
 * COMMON EDGE CASES:
 * -----------------
 * ✓ Empty list → nothing to remove
 * ✓ No duplicates → list unchanged
 * ✓ All duplicates → keep only first of each
 * ✓ Consecutive duplicates → all removed except first
 * ✓ Duplicates at beginning/end → handled correctly
 * 
 * ALTERNATIVE APPROACHES:
 * ----------------------
 * 1. Nested loops (brute force):
 *    - Compare every employee with every other
 *    - Time: O(n²), Space: O(1)
 *    - Trade-off: No extra space but very slow
 * 
 * 2. HashSet approach:
 *    - Create new list with unique employees
 *    - Time: O(n), Space: O(n)
 *    - Trade-off: Creates new list instead of modifying existing
 * 
 * 3. Sorting first:
 *    - Sort by ID, then remove consecutive duplicates
 *    - Time: O(n log n), Space: O(1)
 *    - Trade-off: Loses original order
 * 
 * 4. Stream API (Java 8+):
 *    - Use distinct() with custom comparator
 *    - Trade-off: More concise but less control
 * 
 * WHY OUR APPROACH IS BETTER:
 * --------------------------
 * - Maintains original order (first occurrence kept)
 * - O(n) time - faster than sorting or nested loops
 * - Modifies list in place (memory efficient)
 * - Easy to understand and debug
 * 
 * RELATED INTERVIEW QUESTIONS:
 * ---------------------------
 * - "Remove duplicates from an array"
 * - "Find the first non-repeating character in a string"
 * - "Detect duplicates in O(n) time and O(1) space" (challenging!)
 * - "Group anagrams together using HashMap"
 * - "Two Sum problem using HashMap"
 * - "Implement LRU Cache"
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 */

public class Main {

    public static void main(String[] args) {

        // STEP 1: Create a LinkedList and populate it with employees
        // Notice we have intentional duplicates:
        // - John Doe appears twice (at positions 2 and 5)
        // - Jane Jones appears twice (at positions 1 and 7)
        LinkedList<Employee> employees = new LinkedList<>();
        employees.add(new Employee("Jane", "Jones", 123));
        employees.add(new Employee("John", "Doe", 5678));
        employees.add(new Employee("Mike", "Wilson", 45));
        employees.add(new Employee("Mary", "Smith", 5555));
        employees.add(new Employee("John", "Doe", 5678));      // DUPLICATE!
        employees.add(new Employee("Bill", "End", 3948));
        employees.add(new Employee("Jane", "Jones", 123));     // DUPLICATE!

        // Uncomment to see the list WITH duplicates:
//        employees.forEach(e -> System.out.println(e));

        // STEP 2: Create a HashMap to track which employees we've seen
        // Key: Employee ID (Integer)
        // Value: Employee object
        // This allows us to quickly check "Have we seen this ID before?"
        HashMap<Integer, Employee> hashtable = new HashMap<Integer, Employee>();
        
        // STEP 3: Create an iterator to safely traverse the LinkedList
        // We use an iterator (not a for-loop) because we might modify
        // the list while iterating
        ListIterator<Employee> iterator = employees.listIterator();
        
        // STEP 4: Create a list to store employees that need to be removed
        // We can't remove directly while iterating (would cause ConcurrentModificationException)
        List<Employee> remove = new ArrayList<>();

        // STEP 5: First pass - identify duplicates
        while(iterator.hasNext()){
            Employee employee = iterator.next();  // Get next employee

            // Check if we've already seen this employee ID
            if(hashtable.containsKey(employee.getId())){
                // DUPLICATE FOUND!
                // This employee's ID is already in our HashMap
                // Add this duplicate to our removal list
                remove.add(employee);
            }
            else{
                // FIRST TIME seeing this ID
                // Add the employee to our HashMap
                // This will help us detect duplicates later
                hashtable.put(employee.getId(), employee);
            }
        }

        // STEP 6: Second pass - remove all duplicates
        // Now we safely remove all employees marked as duplicates
        for(Employee employee: remove){
            employees.remove(employee);  // Remove from the LinkedList
        }

        // STEP 7: Print the cleaned list (no duplicates!)
        // Now the list only contains unique employees (by ID)
        employees.forEach(e -> System.out.println(e));

        // EXPECTED OUTPUT:
        // Employee{firstName='Jane', lastName='Jones', id=123}
        // Employee{firstName='John', lastName='Doe', id=5678}
        // Employee{firstName='Mike', lastName='Wilson', id=45}
        // Employee{firstName='Mary', lastName='Smith', id=5555}
        // Employee{firstName='Bill', lastName='End', id=3948}

//        int[] nums = new int[10];
//        int[] numsToAdd = { 59382, 43, 6894, 500, 99, -58 };
//        for (int i = 0; i < numsToAdd.length; i++) {
//            nums[hash(numsToAdd[i])] = numsToAdd[i];
//        }
//        for (int i = 0; i < nums.length; i++) {
//            System.out.println(nums[i]);
//        }
    }

    /*
     * HASH FUNCTION (from previous challenge)
     * ----------------------------------------
     * This simple hash function is kept from Challenge #1.
     * It's not used in this challenge, but demonstrates the concept.
     */
    public static int hash(int value) {
        return Math.abs(value % 10);
    }
}
