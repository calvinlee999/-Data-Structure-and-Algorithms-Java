package com.company.binarysearchtree;

/**
 * 🌲 BINARY SEARCH TREE (BST) - A Smart Data Structure for Organizing Data
 * ===========================================================================
 * 
 * WHAT IS A BINARY SEARCH TREE? 🤔
 * A Binary Search Tree is like a smart filing system that keeps things organized
 * automatically! It's a tree structure where:
 * - Each node can have at most TWO children (that's why it's "binary")
 * - The LEFT child is always SMALLER than the parent
 * - The RIGHT child is always LARGER than the parent
 * - This pattern repeats for EVERY node in the tree
 * 
 * VISUAL EXAMPLE OF A BST:
 *              [25]           ← Root (the top of the tree)
 *             /    \
 *         [20]      [30]      ← Level 1
 *         /  \      /  \
 *      [15] [22] [27] [35]   ← Level 2 (leaves)
 * 
 * Notice: 15 < 20 < 22 and 27 < 30 < 35 ✓
 * 
 * REAL-WORLD ANALOGIES 🌍:
 * 
 * 1. PHONE BOOK 📱:
 *    - Start in the middle
 *    - Name comes before? Go left (earlier in alphabet)
 *    - Name comes after? Go right (later in alphabet)
 *    - Much faster than checking every single name!
 * 
 * 2. TWENTY QUESTIONS GAME 🎮:
 *    - "Is your number less than 50?" (decision point)
 *    - Yes → search numbers < 50
 *    - No → search numbers ≥ 50
 *    - Each question eliminates half the possibilities!
 * 
 * 3. FAMILY TREE 👨‍👩‍👧‍👦:
 *    - Parents at top, children below
 *    - Each person can have left and right descendants
 * 
 * WHY USE A BST? 💡
 * ✅ Fast searching: O(log n) in balanced trees
 * ✅ Keeps data sorted automatically
 * ✅ Easy to find min/max values
 * ✅ Dynamic size - grows and shrinks as needed
 * ✅ Natural for hierarchical data
 * 
 * BST vs OTHER DATA STRUCTURES:
 * 
 * 📊 COMPARISON TABLE:
 * Operation    | Array    | Linked List | BST (balanced)
 * -------------|----------|-------------|---------------
 * Search       | O(n)     | O(n)        | O(log n) ⚡
 * Insert       | O(n)     | O(1)*       | O(log n)
 * Delete       | O(n)     | O(1)*       | O(log n)
 * Find Min/Max | O(n)     | O(n)        | O(log n) ⚡
 * Sorted Order | O(n log n)| O(n log n) | O(n) ⚡
 * 
 * *assuming you already have the position
 * 
 * WHEN TO USE BST:
 * ✓ Need frequent searching
 * ✓ Data changes often (insertions/deletions)
 * ✓ Need data in sorted order
 * ✓ Range queries (find all values between X and Y)
 * ✓ Finding successors/predecessors
 * 
 * WHEN NOT TO USE BST:
 * ✗ Data doesn't need to be sorted
 * ✗ Need constant-time access by index (use array instead)
 * ✗ Very simple operations (linked list might be simpler)
 * ✗ Data arrives in sorted order (tree becomes unbalanced)
 * 
 * @author Data Structures Learning Series
 * @version 1.0
 */
public class Tree {

    // 🌳 The root is the top node - the starting point of the tree
    // If root is null, the tree is empty (like an empty folder)
    private TreeNode root;

    /**
     * 🌱 INSERT METHOD - Adding a New Value to the Tree
     * ===================================================
     * 
     * This is the "front door" to inserting values into the tree.
     * It handles the special case when the tree is empty!
     * 
     * HOW IT WORKS 🔄:
     * 
     * CASE 1 - Empty Tree:
     *   Before: (empty)
     *   Insert 25
     *   After:  [25]  ← This becomes the root
     * 
     * CASE 2 - Tree has nodes:
     *   We delegate to the root node, which recursively finds the right spot
     * 
     * EXAMPLE SEQUENCE - Building a tree:
     * Insert 25:        [25]
     * Insert 20:        [25]
     *                   /
     *                 [20]
     * Insert 30:        [25]
     *                   /  \
     *                [20]  [30]
     * Insert 15:        [25]
     *                   /  \
     *                [20]  [30]
     *                /
     *             [15]
     * 
     * @param value The number to insert into the tree
     * 
     * TIME COMPLEXITY: O(h) where h is the height of the tree
     *   - Balanced tree: O(log n) ⚡
     *   - Worst case (skewed): O(n) - like a linked list
     * SPACE COMPLEXITY: O(h) - recursive call stack
     * 
     * 💡 REMEMBER: The BST property is maintained automatically!
     *    Left < Parent < Right for every node
     */
    public void insert(int value){
        // Special case: Is this the first node in the tree?
        if(root==null){
            root = new TreeNode(value);  // Create the root
        }
        else{
            // Tree already exists, delegate to root to find correct position
            root.insert(value);
        }
    }

    /**
     * 🔍 GET METHOD - Searching for a Value in the Tree
     * ===================================================
     * 
     * REAL-WORLD ANALOGY 🎯:
     * Like playing a guessing game where someone tells you "higher" or "lower"
     * after each guess. You can find the number super fast!
     * 
     * Example: Find 27 in our tree
     *              [25]           Compare 27 with 25 → 27 > 25, go RIGHT →
     *             /    \
     *         [20]      [30]      Compare 27 with 30 → 27 < 30, go LEFT →
     *         /  \      /  \
     *      [15] [22] [27] [35]   Found 27! ✓
     * 
     * WHY IT'S FAST ⚡:
     * Each comparison eliminates HALF of the remaining nodes!
     * - 1000 nodes? Only need ~10 comparisons
     * - 1,000,000 nodes? Only need ~20 comparisons
     * - This is the power of binary search!
     * 
     * @param value The number we're searching for
     * @return The TreeNode containing the value, or null if not found
     * 
     * TIME COMPLEXITY: O(h) where h = height
     *   - Balanced tree: O(log n) - Very fast! ⚡
     *   - Worst case: O(n) - If tree is like a straight line
     * SPACE COMPLEXITY: O(h) - recursion depth
     * 
     * 💡 TIP: This is called "binary search" - the same concept as
     *    searching a sorted array by dividing it in half repeatedly!
     */
    public TreeNode get(int value) {
        // Check if tree is empty first
        if (root!=null) {
            return root.get(value);  // Let root handle the recursive search
        }

        // Tree is empty, value cannot be found
        return null;
    }

    // ========================================================================
    // 🚶 TRAVERSAL METHODS - Different Ways to Visit All Nodes
    // ========================================================================
    // These are "wrapper" methods that start the traversal at the root
    // ========================================================================

    /**
     * 📊 INORDER TRAVERSAL - Visits nodes in sorted order
     * 
     * Pattern: Left → Node → Right
     * Result: Values printed from smallest to largest
     * 
     * TIME COMPLEXITY: O(n) - visits every node once
     * SPACE COMPLEXITY: O(h) - recursion depth
     */
    public void InorderTraversal() {
        if (root!=null) {
            root.InorderTraversal();
        }
    }
    
    /**
     * 🎯 PREORDER TRAVERSAL - Visits root before children
     * 
     * Pattern: Node → Left → Right
     * Use case: Copying/cloning the tree structure
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(h)
     */
    public void PreOrderTraversal() {
        if (root!=null) {
            root.PreOrderTraveral();
        }
    }

    /**
     * 🎭 POSTORDER TRAVERSAL - Visits root after children
     * 
     * Pattern: Left → Right → Node
     * Use case: Deleting the tree (delete children before parent)
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(h)
     */
    public void PostOrderTraversal() {
        if (root!=null) {
            root.PostOrderTraveral();
        }
    }

    /**
     * 🔽 MIN METHOD - Find the smallest value in the tree
     * 
     * Strategy: Keep going LEFT until you can't anymore
     * 
     * Example:     [25]
     *             /    \
     *          [20]    [30]
     *          /
     *       [15]  ← This is the minimum!
     * 
     * @return The smallest value, or Integer.MIN_VALUE if tree is empty
     * 
     * TIME COMPLEXITY: O(h)
     * SPACE COMPLEXITY: O(h)
     */
    public int min() {
        if (root==null) {
            return Integer.MIN_VALUE;  // Tree is empty
        }
        return root.min();
    }

    /**
     * 🔼 MAX METHOD - Find the largest value in the tree
     * 
     * Strategy: Keep going RIGHT until you can't anymore
     * 
     * Example:     [25]
     *             /    \
     *          [20]    [30]
     *                    \
     *                    [35]  ← This is the maximum!
     * 
     * @return The largest value, or Integer.MAX_VALUE if tree is empty
     * 
     * TIME COMPLEXITY: O(h)
     * SPACE COMPLEXITY: O(h)
     */
    public int max() {
        if (root==null) {
            return Integer.MAX_VALUE;  // Tree is empty
        }
        return root.max();
    }

    /**
     * 🗑️ DELETE METHOD - Removing a Node from the Tree
     * ==================================================
     * 
     * This is the TRICKIEST operation in a BST! 😅
     * We need to remove a node while maintaining the BST property.
     * 
     * THREE CASES TO HANDLE:
     * 
     * CASE 1: Node has NO children (it's a leaf) 🍃
     * ------------------------------------------------
     * Easy! Just remove it.
     * 
     *     [25]              [25]
     *    /    \      →     /    \
     * [20]    [30]      [20]    [30]
     *  /                         
     * [15] ← Delete        (gone!)
     * 
     * 
     * CASE 2: Node has ONE child 👶
     * ------------------------------------------------
     * Replace the node with its child (promote the child up)
     * 
     *     [25]              [25]
     *    /    \      →     /    \
     * [20]    [30]      [22]    [30]
     *    \                      
     *   [22]         (20 deleted, 22 moved up)
     * 
     * 
     * CASE 3: Node has TWO children 👶👶
     * ------------------------------------------------
     * This is the tricky one! We need a replacement that maintains BST order.
     * 
     * STRATEGY: Replace with the MAXIMUM value from the LEFT subtree
     * (This is called the "inorder predecessor")
     * 
     * Why? Because it's:
     * - Larger than everything in the left subtree ✓
     * - Smaller than everything in the right subtree ✓
     * - Perfect replacement!
     * 
     *     [25]              [22]
     *    /    \      →     /    \
     * [20]    [30]      [20]    [30]
     *  / \               /  
     * [15][22]         [15]   
     * 
     * (25 deleted, replaced by 22 - the max from left subtree)
     * 
     * ALTERNATIVE STRATEGY (not used here):
     * Could also use MINIMUM from RIGHT subtree ("inorder successor")
     * 
     * @param value The value to delete from the tree
     * 
     * TIME COMPLEXITY: O(h) - find node + potentially find replacement
     * SPACE COMPLEXITY: O(h) - recursion depth
     * 
     * 💡 REAL-WORLD ANALOGY:
     * Think of a company org chart. When a manager leaves:
     * - If they have no team → just remove their box
     * - If they have one direct report → promote that person
     * - If they have multiple reports → promote the most senior one
     */
    public void delete(int value) {
        root = delete(root, value);

    }

    /**
     * 🔧 PRIVATE DELETE HELPER - The Recursive Workhorse
     * ====================================================
     * 
     * This private method does the actual deletion work recursively.
     * It returns the new subtree root after deletion.
     * 
     * STEP-BY-STEP PROCESS:
     * 
     * Step 1: FIND the node to delete
     *   - Compare value with current node
     *   - Go left if smaller, right if larger
     *   - Recursive until found (or not found)
     * 
     * Step 2: HANDLE the three deletion cases
     *   See detailed explanation in public delete() method above
     * 
     * Step 3: RETURN the new subtree root
     *   - This allows parent nodes to update their child references
     * 
     * @param subTreeroot The root of the current subtree being examined
     * @param value The value to delete
     * @return The new root of this subtree after deletion
     * 
     * 💭 WHY RETURN THE NODE?
     * When we delete a node, its parent needs to know what to connect to!
     * By returning the new subtree root, we update all parent connections.
     */
    private TreeNode delete(TreeNode subTreeroot, int value) {
        // BASE CASE: Node not found (reached a null child)
        if (subTreeroot == null) {
            return null;
        }

        // SEARCH PHASE: Find the node to delete
        
        // Value is smaller → search LEFT subtree
        if (value < subTreeroot.getData()) {
            subTreeroot.setLeftChild(delete(subTreeroot.getLeftChild(), value));
        }

        // Value is larger → search RIGHT subtree
        if (value > subTreeroot.getData()) {
            subTreeroot.setRightChild(delete(subTreeroot.getRightChild(), value));
        }
        
        // FOUND IT! Now handle the three deletion cases
        else {
            // CASE 1: No left child (either one child or no children)
            // Solution: Replace this node with its right child
            if (subTreeroot.getLeftChild() == null) {
                return subTreeroot.getRightChild();  // Could be null (no children) or a node (one child)
            }
            
            // CASE 2: No right child (has left child only)
            // Solution: Replace this node with its left child
            if (subTreeroot.getRightChild() == null ) {
                return subTreeroot.getLeftChild();
            }
            
            // CASE 3: Has BOTH children (the complex case!)
            // Solution: Replace with max value from left subtree
            else {
                // Find the replacement value (largest in left subtree)
                // This is the "inorder predecessor"
                int replacementValue = subTreeroot.getLeftChild().max();
                
                // Replace current node's data with the replacement value
                subTreeroot.setData(replacementValue);

                // Now delete the replacement node from left subtree
                // (we've copied its value up, so the original must be removed)
                subTreeroot.setLeftChild(delete(subTreeroot.getLeftChild(), subTreeroot.getData()));
            }


        }
        // Return the (possibly modified) subtree root
        return subTreeroot;

    }


}

/*
 * ============================================================================
 * 🎓 COMPLETE BST SUMMARY - Everything You Need to Know!
 * ============================================================================
 * 
 * CORE CONCEPT:
 * A Binary Search Tree organizes data so that for EVERY node:
 * - Left subtree contains SMALLER values
 * - Right subtree contains LARGER values
 * - This makes searching efficient (like binary search in an array!)
 * 
 * KEY OPERATIONS SUMMARY:
 * 
 * INSERT 🌱:
 *   - Start at root, compare values
 *   - Go left if smaller, right if larger
 *   - Repeat until finding empty spot
 *   - O(log n) average, O(n) worst case
 * 
 * SEARCH 🔍:
 *   - Binary search through the tree
 *   - Eliminate half remaining nodes each step
 *   - O(log n) average, O(n) worst case
 * 
 * DELETE 🗑️:
 *   - Three cases: 0, 1, or 2 children
 *   - Replace with inorder predecessor if 2 children
 *   - O(log n) average, O(n) worst case
 * 
 * TRAVERSALS 🚶:
 *   - Inorder: Left → Node → Right (sorted output!)
 *   - Preorder: Node → Left → Right (copy tree)
 *   - Postorder: Left → Right → Node (delete tree)
 *   - All are O(n) time, O(h) space
 * 
 * MIN/MAX 🔽🔼:
 *   - Min: Keep going left
 *   - Max: Keep going right
 *   - O(h) time and space
 * 
 * ============================================================================
 * ⚠️ IMPORTANT LIMITATION - Tree Balance
 * ============================================================================
 * 
 * PROBLEM: If data arrives in sorted order, tree becomes unbalanced!
 * 
 * Balanced tree:        Unbalanced tree (worst case):
 *       [25]                [10]
 *      /    \                  \
 *   [20]    [30]               [20]
 *   / \      / \                  \
 * [15][22][27][35]                [30]
 *                                    \
 * Height: 2 (good!)                 [40]
 *                                      \
 *                                      [50]
 *                                  Height: 4 (bad!)
 * 
 * SOLUTION: Use self-balancing trees like:
 * - AVL Trees (strict balancing)
 * - Red-Black Trees (used in Java TreeMap)
 * - B-Trees (used in databases)
 * 
 * These automatically balance themselves during insertion/deletion!
 * 
 * ============================================================================
 * 🌟 WHEN TO USE BINARY SEARCH TREES
 * ============================================================================
 * 
 * GREAT FOR:
 * ✅ Maintaining sorted data dynamically
 * ✅ Fast searching, insertion, and deletion
 * ✅ Finding min/max efficiently
 * ✅ Range queries (find all values between X and Y)
 * ✅ Finding next larger/smaller value (successor/predecessor)
 * ✅ Priority queues (with modifications)
 * 
 * NOT IDEAL FOR:
 * ❌ Random access by index (use array instead)
 * ❌ Data arrives in sorted order (use balanced tree instead)
 * ❌ Memory-constrained environments (extra pointers use space)
 * ❌ Simple FIFO/LIFO operations (use queue/stack instead)
 * 
 * REAL-WORLD APPLICATIONS:
 * 🗂️ Database indexing (B-Trees are specialized BSTs)
 * 📝 Autocomplete systems
 * 🎮 Game development (spatial partitioning)
 * 📊 Expression parsing (expression trees)
 * 💾 File systems (directory structures)
 * 🔐 Encryption algorithms
 * 
 * ============================================================================
 * 💡 FINAL TIPS FOR LEARNING BSTs
 * ============================================================================
 * 
 * 1. Draw pictures! Visualize the tree as you work with it
 * 2. Practice with small examples first (5-10 nodes)
 * 3. Understand recursion - it's the key to tree operations
 * 4. Remember the BST property: Left < Parent < Right
 * 5. Test edge cases: empty tree, single node, duplicate values
 * 6. Trace through operations step by step
 * 7. Compare to simpler structures (arrays, linked lists) to understand trade-offs
 * 
 * Keep practicing and trees will become your friends! 🌳✨
 * ============================================================================
 */
