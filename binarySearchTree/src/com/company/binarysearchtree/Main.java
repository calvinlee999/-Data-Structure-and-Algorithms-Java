package com.company.binarysearchtree;

/**
 * 🎯 BINARY SEARCH TREE DEMONSTRATION
 * =====================================
 * 
 * This program demonstrates all the key operations of a Binary Search Tree:
 * ✓ Insertion - Adding new values
 * ✓ Searching - Finding specific values
 * ✓ Traversals - Visiting all nodes in different orders
 * ✓ Min/Max - Finding extreme values
 * ✓ Deletion - Removing nodes
 * 
 * LEARNING OBJECTIVES 📚:
 * By studying this code, you will understand:
 * 1. How to build a BST from scratch
 * 2. How different traversals produce different output orders
 * 3. How BST operations work in practice
 * 4. Performance characteristics of BSTs
 * 
 * @author Data Structures Learning Series
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {

        // ====================================================================
        // 🏗️ BUILDING THE TREE - Watch How It Grows!
        // ====================================================================
        
        System.out.println("🌳 BINARY SEARCH TREE DEMONSTRATION");
        System.out.println("=" .repeat(50));
        System.out.println();
        
        // Create an empty tree
        Tree intTree = new Tree();
        
        // Let's build this tree step by step:
        //              [25]           ← Root (inserted first)
        //             /    \
        //         [20]      [27]      ← Level 1
        //         /  \      /  \
        //      [15] [22] [26] [30]   ← Level 2
        //                      /  \
        //                   [29]  [32] ← Level 3
        
        System.out.println("📥 INSERTING VALUES...");
        System.out.println("Inserting: 25, 20, 15, 27, 30, 29, 26, 22, 32");
        System.out.println();
        
        intTree.insert(25);  // This becomes the root
        intTree.insert(20);  // 20 < 25, goes to left
        intTree.insert(15);  // 15 < 25, left; 15 < 20, left of 20
        intTree.insert(27);  // 27 > 25, goes to right
        intTree.insert(30);  // 30 > 25, right; 30 > 27, right of 27
        intTree.insert(29);  // 29 > 25, right; 29 > 27, right; 29 < 30, left of 30
        intTree.insert(26);  // 26 > 25, right; 26 < 27, left of 27
        intTree.insert(22);  // 22 < 25, left; 22 > 20, right of 20
        intTree.insert(32);  // 32 > 25, right; 32 > 27, right; 32 > 30, right of 30

        // ====================================================================
        // 🚶 TREE TRAVERSALS - Different Ways to Visit Nodes
        // ====================================================================
        
        System.out.println("🔄 TREE TRAVERSALS:");
        System.out.println("-".repeat(50));
        
        // INORDER: Left → Node → Right
        // This gives us SORTED output! (smallest to largest)
        System.out.print("📊 Inorder (sorted):   ");
        intTree.InorderTraversal();
        System.out.println();
        System.out.println("   ↑ Notice: The values are in ascending order!");
        System.out.println();

        // PREORDER: Node → Left → Right
        // Useful for creating a copy of the tree
        System.out.print("🎯 Preorder (NLR):     ");
        intTree.PreOrderTraversal();
        System.out.println();
        System.out.println("   ↑ Root (25) comes first!");
        System.out.println();

        // POSTORDER: Left → Right → Node
        // Useful for deleting the tree (delete children before parents)
        System.out.print("🎭 Postorder (LRN):    ");
        intTree.PostOrderTraversal();
        System.out.println();
        System.out.println("   ↑ Root (25) comes last!");
        System.out.println();

        // ====================================================================
        // 🔍 SEARCHING - Finding Values in the Tree
        // ====================================================================
        
        System.out.println("🔍 SEARCHING FOR VALUES:");
        System.out.println("-".repeat(50));
        
        // Uncomment these lines to test searching:
        // Example: Finding 27
        // Compare 27 with 25 → 27 > 25, go right
        // Compare 27 with 27 → Found it! ✓
        
//        System.out.println("Searching for 27: " + intTree.get(27).getData());
//        System.out.println("Searching for 30: " + intTree.get(30).getData());
//        
//        // This will return null (value not in tree)
//        TreeNode result = intTree.get(8888);
//        System.out.println("Searching for 8888: " + 
//                          (result == null ? "Not found ❌" : result.getData()));
//        System.out.println();

        // ====================================================================
        // 🔽🔼 FINDING MIN AND MAX
        // ====================================================================
        
        System.out.println("📏 EXTREME VALUES:");
        System.out.println("-".repeat(50));
        
        // Uncomment to find min and max:
//        System.out.println("🔽 Minimum value: " + intTree.min() + 
//                          " (leftmost node)");
//        System.out.println("🔼 Maximum value: " + intTree.max() + 
//                          " (rightmost node)");
//        System.out.println();

        // ====================================================================
        // 🗑️ DELETION - Removing Nodes
        // ====================================================================
        
        System.out.println("🗑️  DELETION DEMONSTRATION:");
        System.out.println("-".repeat(50));
        
        // Uncomment to test deletion:
        // Example 1: Delete a leaf node (no children)
//        System.out.println("Deleting 15 (leaf node)...");
//        intTree.delete(15);
//        System.out.print("After deletion: ");
//        intTree.InorderTraversal();
//        System.out.println();
//        System.out.println();
//        
//        // Example 2: Delete a node with one child
//        System.out.println("Deleting 27 (node with two children)...");
//        intTree.delete(27);
//        System.out.print("After deletion: ");
//        intTree.InorderTraversal();
//        System.out.println();
//        System.out.println();
//        
//        // Example 3: Delete the root (node with two children)
//        System.out.println("Deleting 25 (root with two children)...");
//        intTree.delete(25);
//        System.out.print("After deletion: ");
//        intTree.InorderTraversal();
//        System.out.println();


    }



}

/*
 * ============================================================================
 * 🎓 INTERVIEW QUESTIONS & ANSWERS - Binary Search Trees
 * ============================================================================
 * 
 * This section contains common interview questions about BSTs with detailed
 * answers. Study these to prepare for coding interviews!
 * 
 * ============================================================================
 * 
 * Q1: What is a Binary Search Tree? How is it different from a regular tree?
 * ---------------------------------------------------------------------------
 * 
 * A: A Binary Search Tree (BST) is a tree data structure where:
 *    - Each node has at most 2 children (binary)
 *    - For EVERY node:
 *      • All values in the left subtree are SMALLER
 *      • All values in the right subtree are LARGER
 *    
 *    Difference from regular tree:
 *    - Regular binary tree: No ordering requirement
 *    - BST: Strict left < parent < right ordering
 *    - This ordering enables O(log n) search in balanced trees!
 * 
 * ============================================================================
 * 
 * Q2: What is the time complexity of searching in a BST?
 * --------------------------------------------------------
 * 
 * A: It depends on the tree's shape:
 *    
 *    BALANCED TREE: O(log n) ⚡
 *    - Height ≈ log₂(n)
 *    - Each comparison eliminates half the remaining nodes
 *    - Example: 1000 nodes → ~10 comparisons max
 *    
 *    UNBALANCED TREE (worst case): O(n) ❌
 *    - Happens when data is inserted in sorted order
 *    - Tree becomes like a linked list
 *    - Example: [1,2,3,4,5] creates a straight line
 *    
 *    AVERAGE CASE: O(log n)
 *    - Random insertion order usually gives good balance
 * 
 * ============================================================================
 * 
 * Q3: How do you find the kth smallest element in a BST?
 * --------------------------------------------------------
 * 
 * A: Use Inorder Traversal! It visits nodes in sorted order.
 *    
 *    ALGORITHM:
 *    1. Perform inorder traversal (left → node → right)
 *    2. Keep a counter
 *    3. When counter reaches k, return that value
 *    
 *    CODE SKETCH:
 *    ```java
 *    int count = 0;
 *    int result = -1;
 *    
 *    void kthSmallest(TreeNode node, int k) {
 *        if (node == null) return;
 *        
 *        kthSmallest(node.left, k);      // Visit left
 *        
 *        count++;                         // Visit current
 *        if (count == k) {
 *            result = node.data;
 *            return;
 *        }
 *        
 *        kthSmallest(node.right, k);     // Visit right
 *    }
 *    ```
 *    
 *    TIME: O(k) - We stop after visiting k nodes
 *    SPACE: O(h) - Recursion depth
 * 
 * ============================================================================
 * 
 * Q4: How would you check if a binary tree is a valid BST?
 * ----------------------------------------------------------
 * 
 * A: Common mistake: Only checking immediate children! ❌
 *    You must check ALL descendants respect the BST property.
 *    
 *    CORRECT APPROACH: Use range checking
 *    
 *    CONCEPT:
 *    - Root can be any value
 *    - Left subtree: all values must be < root
 *    - Right subtree: all values must be > root
 *    - Recursively check with narrowing ranges
 *    
 *    CODE SKETCH:
 *    ```java
 *    boolean isValidBST(TreeNode node, int min, int max) {
 *        if (node == null) return true;
 *        
 *        // Current value must be within range
 *        if (node.data <= min || node.data >= max) 
 *            return false;
 *        
 *        // Left child: max becomes current value
 *        // Right child: min becomes current value
 *        return isValidBST(node.left, min, node.data) &&
 *               isValidBST(node.right, node.data, max);
 *    }
 *    
 *    // Start with infinite range
 *    isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
 *    ```
 *    
 *    TIME: O(n) - Visit each node once
 *    SPACE: O(h) - Recursion depth
 * 
 * ============================================================================
 * 
 * Q5: What is the difference between Inorder, Preorder, and Postorder?
 * ----------------------------------------------------------------------
 * 
 * A: They differ in when you "visit" (process) the current node:
 *    
 *    INORDER (Left → Node → Right):
 *    - Gives SORTED output for BST! 📊
 *    - Use when: Need data in ascending order
 *    
 *    PREORDER (Node → Left → Right):
 *    - Root comes first
 *    - Use when: Copying tree, prefix notation
 *    
 *    POSTORDER (Left → Right → Node):
 *    - Root comes last
 *    - Use when: Deleting tree, postfix notation
 *    
 *    EXAMPLE TREE:
 *         [5]
 *        /   \
 *      [3]   [7]
 *      / \   / \
 *    [2][4][6][8]
 *    
 *    Inorder:   2,3,4,5,6,7,8  ← Sorted!
 *    Preorder:  5,3,2,4,7,6,8
 *    Postorder: 2,4,3,6,8,7,5
 * 
 * ============================================================================
 * 
 * Q6: How would you delete a node with two children?
 * ----------------------------------------------------
 * 
 * A: Use the INORDER PREDECESSOR (or successor) replacement strategy:
 *    
 *    STEPS:
 *    1. Find the node to delete
 *    2. Find its inorder predecessor:
 *       → Maximum value in LEFT subtree
 *       (Go left once, then keep going right)
 *    3. Copy predecessor's value to current node
 *    4. Delete the predecessor node (it has at most 1 child!)
 *    
 *    WHY THIS WORKS:
 *    - Predecessor is < all nodes in right subtree ✓
 *    - Predecessor is > all other nodes in left subtree ✓
 *    - BST property maintained!
 *    
 *    ALTERNATIVE: Use inorder successor instead
 *    → Minimum value in RIGHT subtree
 *    (Go right once, then keep going left)
 *    
 *    Both work equally well!
 * 
 * ============================================================================
 * 
 * Q7: What are the advantages of BST over Hash Table?
 * -----------------------------------------------------
 * 
 * A: BSTs have several advantages:
 *    
 *    ✅ ORDERED DATA:
 *    - BST maintains sorted order automatically
 *    - Hash table has no ordering
 *    
 *    ✅ RANGE QUERIES:
 *    - Find all values between X and Y: O(k + log n) in BST
 *    - Hash table: Must check every element O(n)
 *    
 *    ✅ FINDING NEXT/PREVIOUS:
 *    - BST: O(log n) for successor/predecessor
 *    - Hash table: Impossible without full scan
 *    
 *    ✅ MINIMUM/MAXIMUM:
 *    - BST: O(log n) - just go left/right
 *    - Hash table: O(n) - must scan all
 *    
 *    ✅ NO HASH COLLISIONS:
 *    - BST performance is predictable
 *    - Hash table can degrade with collisions
 *    
 *    HASH TABLE WINS:
 *    ✅ Faster average search: O(1) vs O(log n)
 *    ✅ Simpler implementation
 *    ✅ Better for exact match lookups
 *    
 *    CHOOSE BST WHEN: Order matters, range queries needed
 *    CHOOSE HASH TABLE WHEN: Only need fast exact lookups
 * 
 * ============================================================================
 * 
 * Q8: How do you find the Lowest Common Ancestor (LCA) of two nodes?
 * --------------------------------------------------------------------
 * 
 * A: For BST, we can use the ordering property for an elegant solution!
 *    
 *    CONCEPT:
 *    The LCA is the "split point" where one value goes left and one goes right.
 *    
 *    ALGORITHM:
 *    1. Start at root
 *    2. If both values < current: LCA is in left subtree
 *    3. If both values > current: LCA is in right subtree
 *    4. Otherwise: current node IS the LCA!
 *    
 *    CODE SKETCH:
 *    ```java
 *    TreeNode findLCA(TreeNode root, int n1, int n2) {
 *        if (root == null) return null;
 *        
 *        // Both values in left subtree
 *        if (n1 < root.data && n2 < root.data)
 *            return findLCA(root.left, n1, n2);
 *        
 *        // Both values in right subtree
 *        if (n1 > root.data && n2 > root.data)
 *            return findLCA(root.right, n1, n2);
 *        
 *        // Values split (one left, one right) or one matches
 *        return root;  // This is the LCA!
 *    }
 *    ```
 *    
 *    EXAMPLE:
 *         [20]
 *        /    \
 *      [10]   [30]
 *      / \    /  \
 *    [5][15][25][35]
 *    
 *    LCA(5, 15) = 10   (both in left subtree of 20)
 *    LCA(5, 25) = 20   (split at root)
 *    LCA(25, 35) = 30  (both in right subtree of 20)
 *    
 *    TIME: O(h) where h is height
 *    SPACE: O(h) if recursive, O(1) if iterative
 * 
 * ============================================================================
 * 
 * Q9: What causes a BST to become unbalanced? How can you prevent it?
 * ---------------------------------------------------------------------
 * 
 * A: CAUSES OF IMBALANCE:
 *    
 *    1. SORTED INSERTION:
 *       Insert [1,2,3,4,5] creates:
 *       [1]
 *         \
 *         [2]
 *           \
 *           [3]  ← This is just a linked list!
 *             \
 *             [4]
 *               \
 *               [5]
 *       Height = n (worst case!)
 *    
 *    2. SKEWED DELETIONS:
 *       Repeatedly deleting from one side
 *    
 *    3. REAL-WORLD DATA PATTERNS:
 *       Timestamps, IDs, etc. often arrive in order
 *    
 *    SOLUTIONS:
 *    
 *    ✅ AVL TREES:
 *    - Self-balancing BST
 *    - Maintains strict height balance
 *    - Rotations after insert/delete
 *    - Height difference ≤ 1 for any node
 *    
 *    ✅ RED-BLACK TREES:
 *    - Self-balancing with color properties
 *    - Less strict than AVL (faster insertions)
 *    - Used in Java TreeMap, C++ map
 *    - Height ≤ 2*log(n)
 *    
 *    ✅ B-TREES:
 *    - Multi-way trees (more than 2 children)
 *    - Perfect for databases
 *    - Stay balanced by design
 *    
 *    ✅ RANDOMIZATION:
 *    - Shuffle data before insertion
 *    - Random order → balanced tree (usually)
 *    
 *    REMEMBER: Unbalanced BST = O(n) operations = bad! 😢
 *              Balanced BST = O(log n) operations = good! 😊
 * 
 * ============================================================================
 * 
 * Q10: How would you convert a sorted array to a balanced BST?
 * --------------------------------------------------------------
 * 
 * A: Use the MIDDLE element as root! This ensures balance.
 *    
 *    KEY INSIGHT:
 *    - Middle element has equal numbers of elements on each side
 *    - This creates perfect balance!
 *    
 *    ALGORITHM:
 *    1. Find middle of array → make it root
 *    2. Left half of array → build left subtree (recursively)
 *    3. Right half of array → build right subtree (recursively)
 *    
 *    CODE SKETCH:
 *    ```java
 *    TreeNode sortedArrayToBST(int[] arr, int start, int end) {
 *        // Base case: empty range
 *        if (start > end) return null;
 *        
 *        // Find middle element
 *        int mid = start + (end - start) / 2;
 *        
 *        // Create node with middle element
 *        TreeNode node = new TreeNode(arr[mid]);
 *        
 *        // Recursively build subtrees
 *        node.left = sortedArrayToBST(arr, start, mid - 1);
 *        node.right = sortedArrayToBST(arr, mid + 1, end);
 *        
 *        return node;
 *    }
 *    
 *    // Initial call
 *    sortedArrayToBST(arr, 0, arr.length - 1);
 *    ```
 *    
 *    EXAMPLE:
 *    Array: [1, 2, 3, 4, 5, 6, 7]
 *    
 *    Result:      [4]
 *                /   \
 *              [2]   [6]
 *              / \   / \
 *            [1][3][5][7]
 *    
 *    Perfect balance! Height = log₂(7) ≈ 3
 *    
 *    TIME: O(n) - Visit each element once
 *    SPACE: O(log n) - Recursion depth (balanced tree height)
 * 
 * ============================================================================
 * 
 * Q11: Can you implement a BST iterator?
 * ----------------------------------------
 * 
 * A: Yes! An iterator should return values in sorted order (inorder).
 *    Use a STACK to simulate recursive inorder traversal.
 *    
 *    APPROACH:
 *    - Push all left children onto stack (smallest values first)
 *    - Pop returns the next smallest value
 *    - After popping, push that node's right child's left children
 *    
 *    CODE SKETCH:
 *    ```java
 *    class BSTIterator {
 *        Stack<TreeNode> stack = new Stack<>();
 *        
 *        public BSTIterator(TreeNode root) {
 *            pushLeft(root);
 *        }
 *        
 *        // Push all left children onto stack
 *        private void pushLeft(TreeNode node) {
 *            while (node != null) {
 *                stack.push(node);
 *                node = node.left;
 *            }
 *        }
 *        
 *        public boolean hasNext() {
 *            return !stack.isEmpty();
 *        }
 *        
 *        public int next() {
 *            TreeNode node = stack.pop();
 *            pushLeft(node.right);  // Process right subtree
 *            return node.data;
 *        }
 *    }
 *    ```
 *    
 *    USAGE:
 *    ```java
 *    BSTIterator it = new BSTIterator(root);
 *    while (it.hasNext()) {
 *        System.out.println(it.next());  // Prints in sorted order!
 *    }
 *    ```
 *    
 *    TIME: O(1) average per next() call
 *    SPACE: O(h) for the stack
 * 
 * ============================================================================
 * 
 * Q12: How do you serialize and deserialize a BST?
 * --------------------------------------------------
 * 
 * A: SERIALIZATION = Convert tree to string/array
 *    DESERIALIZATION = Rebuild tree from string/array
 *    
 *    METHOD 1: Using Preorder Traversal (most efficient for BST)
 *    
 *    WHY PREORDER?
 *    - First value is always the root
 *    - Can rebuild without needing null markers!
 *    - Uses BST property (left < root < right)
 *    
 *    SERIALIZE:
 *    ```java
 *    void serialize(TreeNode node, StringBuilder sb) {
 *        if (node == null) return;
 *        
 *        sb.append(node.data).append(",");
 *        serialize(node.left, sb);   // Preorder: root first
 *        serialize(node.right, sb);
 *    }
 *    // Example output: "25,20,15,22,30,27,29,"
 *    ```
 *    
 *    DESERIALIZE:
 *    ```java
 *    int index = 0;
 *    TreeNode deserialize(String[] values, int min, int max) {
 *        if (index >= values.length) return null;
 *        
 *        int val = Integer.parseInt(values[index]);
 *        
 *        // Check if value fits in current range
 *        if (val < min || val > max) return null;
 *        
 *        index++;
 *        TreeNode node = new TreeNode(val);
 *        
 *        // Build left subtree (values < val)
 *        node.left = deserialize(values, min, val);
 *        
 *        // Build right subtree (values > val)
 *        node.right = deserialize(values, val, max);
 *        
 *        return node;
 *    }
 *    ```
 *    
 *    METHOD 2: Level-order with null markers (works for any binary tree)
 *    - Use queue for level-order traversal
 *    - Mark null children with "null"
 *    - Example: "25,20,30,15,22,null,null"
 *    
 *    TIME: O(n) for both serialize and deserialize
 *    SPACE: O(n) for storing the serialized string
 * 
 * ============================================================================
 * 
 * 🎯 BONUS TIPS FOR INTERVIEWS:
 * ============================================================================
 * 
 * 1. ALWAYS DRAW THE TREE:
 *    - Visualize the problem
 *    - Show your thinking process
 *    - Makes solutions clearer
 * 
 * 2. MENTION TIME/SPACE COMPLEXITY:
 *    - Shows you understand performance
 *    - Compare best/average/worst cases
 *    - Discuss balanced vs unbalanced
 * 
 * 3. CONSIDER EDGE CASES:
 *    - Empty tree (root = null)
 *    - Single node
 *    - Only left children or only right children
 *    - Duplicate values (BST usually doesn't allow)
 * 
 * 4. KNOW VARIATIONS:
 *    - BST, AVL, Red-Black, B-Tree
 *    - When to use each
 *    - Trade-offs between them
 * 
 * 5. PRACTICE RECURSION:
 *    - Most BST problems use recursion
 *    - Be comfortable with base cases
 *    - Understand the recursive pattern
 * 
 * 6. UNDERSTAND INORDER TRAVERSAL:
 *    - It's the most important for BST!
 *    - Gives sorted output
 *    - Many problems rely on this property
 * 
 * 7. COMMON FOLLOW-UP QUESTIONS:
 *    - "What if we want O(1) search?" → Hash Table
 *    - "What if tree becomes unbalanced?" → AVL/Red-Black
 *    - "How to handle duplicates?" → Store count in node
 *    - "How to make it thread-safe?" → Add locks/synchronization
 * 
 * ============================================================================
 * 
 * 📚 RECOMMENDED PRACTICE PROBLEMS:
 * ============================================================================
 * 
 * EASY:
 * - Validate BST
 * - Find min/max in BST
 * - Search in BST
 * - Insert into BST
 * - Inorder traversal
 * 
 * MEDIUM:
 * - Delete node in BST
 * - Kth smallest element
 * - Lowest common ancestor
 * - Convert sorted array to BST
 * - BST iterator
 * - Serialize and deserialize
 * 
 * HARD:
 * - Recover BST (two nodes swapped)
 * - Count smaller elements on right
 * - Largest BST in binary tree
 * - Merge two BSTs
 * - Create BST from preorder
 * 
 * ============================================================================
 * 
 * Good luck with your interviews! 🍀
 * Remember: Practice makes perfect! Keep coding! 💪
 * 
 * ============================================================================
 */
