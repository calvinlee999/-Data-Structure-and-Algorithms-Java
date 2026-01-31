package com.company.binarysearchtree;

/**
 * 🌳 TREENODE CLASS - The Building Block of Binary Search Trees
 * =================================================================
 * 
 * WHAT IS A TREE NODE? 🤔
 * Think of a tree node like a person in your family tree! Each person (node) has:
 * - Their own information (data)
 * - A left child (like a younger sibling on the left)
 * - A right child (like a younger sibling on the right)
 * 
 * REAL-WORLD ANALOGY 🏢:
 * Imagine an organizational chart at a company. Each box (node) represents a person,
 * and the lines connecting them show who reports to whom. That's exactly how tree nodes work!
 * 
 * ASCII EXAMPLE:
 *        [25]  ← This is a TreeNode with data = 25
 *       /    \
 *    [20]    [30]  ← These are its left and right children
 * 
 * THE BINARY SEARCH TREE RULE 📏:
 * - All values in the LEFT subtree are SMALLER than the parent
 * - All values in the RIGHT subtree are LARGER than the parent
 * - This makes searching super fast! ⚡
 * 
 * @author Data Structures Learning Series
 * @version 1.0
 */
public class TreeNode {

    // 📦 DATA FIELDS
    private int data;           // The value stored in this node (like a person's ID number)
    private TreeNode leftChild;  // Pointer to the left child (smaller values go here)
    private TreeNode rightChild; // Pointer to the right child (larger values go here)

    /**
     * 🏗️ CONSTRUCTOR - Creates a new TreeNode
     * 
     * Think of this like adding a new person to your family tree.
     * When they're first added, they don't have any children yet!
     * 
     * @param data The value to store in this node
     * 
     * TIME COMPLEXITY: O(1) - Creating a single node is instant!
     * SPACE COMPLEXITY: O(1) - We only use space for one node
     */
    public TreeNode(int data){
        this.data = data;
        // Note: leftChild and rightChild are automatically set to null
        // This means the node has no children when first created
    }

    /**
     * 🌱 INSERT METHOD - Adding a New Value to the Tree
     * ===================================================
     * 
     * HOW IT WORKS (Like sorting your books!):
     * Imagine you have a bookshelf organized by book number. When you get a new book:
     * 1. Compare it with the current shelf section
     * 2. If it's smaller, go LEFT (lower numbers)
     * 3. If it's larger, go RIGHT (higher numbers)
     * 4. Keep going until you find an empty spot
     * 
     * STEP-BY-STEP PROCESS 👣:
     * Example: Insert 18 into this tree:
     *        [20]
     *       /    \
     *    [15]    [25]
     * 
     * Step 1: Compare 18 with 20 → 18 < 20, so go LEFT
     * Step 2: Compare 18 with 15 → 18 > 15, so go RIGHT
     * Step 3: Right child of 15 is empty → Insert 18 here!
     * 
     * Result:
     *        [20]
     *       /    \
     *    [15]    [25]
     *       \
     *       [18]
     * 
     * @param value The number we want to add to the tree
     * 
     * TIME COMPLEXITY: O(h) where h = height of tree
     *   - Best case: O(log n) for a balanced tree (like cutting a deck of cards in half)
     *   - Worst case: O(n) for a skewed tree (like a straight line)
     * SPACE COMPLEXITY: O(h) due to recursion call stack
     * 
     * 💡 PRO TIP: If the value already exists, we don't add it again (no duplicates!)
     */
    public void insert(int value){
        // BASE CASE: If the value equals current data, stop (no duplicates allowed!)
        if(value== data){
            return;
        }

        // RECURSIVE CASE 1: Value is smaller, go LEFT 👈
        if(value< data){
            if(leftChild==null){
                // Found an empty spot on the left! Create new node here
                leftChild = new TreeNode(value);
            }
            else{
                // Left child exists, keep searching down the left subtree
                leftChild.insert(value);
            }
        }
        // RECURSIVE CASE 2: Value is larger, go RIGHT 👉
        else{
            if(rightChild==null){
                // Found an empty spot on the right! Create new node here
                rightChild = new TreeNode(value);
            }
            else{
                // Right child exists, keep searching down the right subtree
                rightChild.insert(value);
            }
        }
    }

    /**
     * 🔍 GET METHOD - Finding a Value in the Tree (Binary Search!)
     * =============================================================
     * 
     * REAL-WORLD ANALOGY 📚:
     * Think of looking up a word in a dictionary:
     * - Open to the middle
     * - Is your word before or after this page?
     * - Keep dividing the remaining pages in half
     * - Much faster than reading page by page!
     * 
     * HOW IT WORKS:
     * Example: Find 27 in this tree:
     *        [25]
     *       /    \
     *    [20]    [30]
     *           /    \
     *         [27]   [35]
     * 
     * Step 1: Compare 27 with 25 → 27 > 25, go RIGHT
     * Step 2: Compare 27 with 30 → 27 < 30, go LEFT
     * Step 3: Found 27! Return this node ✓
     * 
     * @param value The number we're searching for
     * @return The TreeNode containing the value, or null if not found
     * 
     * TIME COMPLEXITY: O(h) where h = height
     *   - Best case: O(log n) - Like a guessing game with "higher/lower" hints
     *   - Worst case: O(n) - If tree is unbalanced
     * SPACE COMPLEXITY: O(h) - Recursion uses memory for each level
     * 
     * 💡 WHY IT'S FAST: We eliminate half the remaining nodes at each step!
     */
    public TreeNode get(int value) {
        // BASE CASE 1: Found it! The value matches this node's data
        if (value==data) {
            return this;
        }

        // RECURSIVE CASE 1: Value is smaller, search LEFT subtree
        if (value<data) {
            if (leftChild!=null) {
                return leftChild.get(value);  // Ask left child to search its subtree
            }
        }
        // RECURSIVE CASE 2: Value is larger, search RIGHT subtree
        else {
            if (rightChild!=null) {
                return rightChild.get(value);  // Ask right child to search its subtree
            }
        }

        // BASE CASE 2: Not found! We've reached a dead end (null child)
        return null;
    }

    
    // ========================================================================
    // 🚶‍♂️ TREE TRAVERSAL METHODS - Different Ways to Visit All Nodes
    // ========================================================================
    // 
    // Think of traversal like touring a museum:
    // - Different paths give you different experiences
    // - You still see all the exhibits, just in different orders!
    //
    // WHY TRAVERSALS MATTER 🎯:
    // - Inorder: Gets values in sorted order (smallest to largest)
    // - Preorder: Useful for copying/cloning trees
    // - Postorder: Useful for deleting trees (delete children first!)
    // ========================================================================

    /**
     * 📊 INORDER TRAVERSAL - Visit nodes in SORTED ORDER
     * ====================================================
     * 
     * PATTERN: Left → Root → Right (LNR)
     * 
     * REAL-WORLD ANALOGY 🎵:
     * Like reading musical notes from left to right on sheet music.
     * You read each note in the order it should be played!
     * 
     * VISUAL EXAMPLE:
     *        [25]
     *       /    \
     *    [20]    [30]
     *    /  \    /  \
     * [15][22][27][35]
     * 
     * Visit order: 15, 20, 22, 25, 27, 30, 35 (Notice: It's sorted! 📈)
     * 
     * HOW IT WORKS 🔄:
     * 1. Visit all nodes in the LEFT subtree (smallest values)
     * 2. Visit the ROOT (current node)
     * 3. Visit all nodes in the RIGHT subtree (largest values)
     * 
     * TIME COMPLEXITY: O(n) - We visit every node exactly once
     * SPACE COMPLEXITY: O(h) - Recursion stack depth equals tree height
     * 
     * 💡 USE CASE: When you need data in ascending order!
     */
    public void InorderTraversal() {
        //left - Visit all smaller values first
        if (leftChild!=null) {
            leftChild.InorderTraversal();
        }
        //root - Visit current node
        System.out.print(data+",");
        //right - Visit all larger values last
        if (rightChild!=null) {
            rightChild.InorderTraversal();
        }

    }

    /**
     * 🎯 PREORDER TRAVERSAL - Visit ROOT First
     * ==========================================
     * 
     * PATTERN: Root → Left → Right (NLR)
     * 
     * REAL-WORLD ANALOGY 👨‍👩‍👧‍👦:
     * Like introducing your family to someone new:
     * "First, meet ME (root), then my LEFT child, then my RIGHT child"
     * 
     * VISUAL EXAMPLE:
     *        [25]  ← Start here!
     *       /    \
     *    [20]    [30]
     *    /  \    /  \
     * [15][22][27][35]
     * 
     * Visit order: 25, 20, 15, 22, 30, 27, 35
     * 
     * HOW IT WORKS 🔄:
     * 1. Visit the ROOT first (current node)
     * 2. Visit all nodes in the LEFT subtree
     * 3. Visit all nodes in the RIGHT subtree
     * 
     * TIME COMPLEXITY: O(n) - Every node visited once
     * SPACE COMPLEXITY: O(h) - Recursion depth
     * 
     * 💡 USE CASE: Useful for creating a copy of the tree or prefix notation!
     */
    public void PreOrderTraveral() {
        //root - Visit current node FIRST
        System.out.print(data+",");
        //left - Then visit left subtree
        if (leftChild!=null) {
            leftChild.PreOrderTraveral();
        }
        //right - Finally visit right subtree
        if (rightChild!=null) {
            rightChild.PreOrderTraveral();
        }
    }

    /**
     * 🎭 POSTORDER TRAVERSAL - Visit ROOT Last
     * ==========================================
     * 
     * PATTERN: Left → Right → Root (LRN)
     * 
     * REAL-WORLD ANALOGY 🏗️:
     * Like demolishing a building:
     * - Remove items from bottom floors BEFORE removing upper floors
     * - You must remove children before you can remove the parent!
     * 
     * VISUAL EXAMPLE:
     *        [25]  ← Visit LAST!
     *       /    \
     *    [20]    [30]
     *    /  \    /  \
     * [15][22][27][35]  ← Visit these first
     * 
     * Visit order: 15, 22, 20, 27, 35, 30, 25
     * 
     * HOW IT WORKS 🔄:
     * 1. Visit all nodes in the LEFT subtree first
     * 2. Visit all nodes in the RIGHT subtree
     * 3. Visit the ROOT last (current node)
     * 
     * TIME COMPLEXITY: O(n) - Visit every node once
     * SPACE COMPLEXITY: O(h) - Recursion call stack
     * 
     * 💡 USE CASE: Perfect for deleting a tree safely (delete children first!)
     *              Also used in postfix notation evaluation
     */
    public void PostOrderTraveral() {
        // left - Visit left subtree first
        if (leftChild!=null) {
            leftChild.PostOrderTraveral();
        }
        // right - Then visit right subtree
        if (rightChild!=null) {
            rightChild.PostOrderTraveral();
        }
        // root - Visit current node LAST
        System.out.print(data+",");

    }

    
    // ========================================================================
    // 🔢 MIN AND MAX METHODS - Finding Extreme Values
    // ========================================================================
    // These methods take advantage of the BST property:
    // - Smallest values are always on the LEFT
    // - Largest values are always on the RIGHT
    // ========================================================================

    /**
     * 🔽 MIN METHOD - Find the Smallest Value
     * =========================================
     * 
     * SIMPLE RULE: Keep going LEFT until you can't go anymore!
     * 
     * VISUAL EXAMPLE:
     *        [25]
     *       /    \
     *    [20]    [30]
     *    /
     * [15]  ← This is the minimum!
     *  /
     * [10]  ← Actually, THIS is the minimum!
     * 
     * REAL-WORLD ANALOGY 📏:
     * Like finding the shortest person in a line organized by height,
     * where shorter people always stand on the left.
     * Just keep going left until you find the leftmost person!
     * 
     * @return The smallest value in this subtree
     * 
     * TIME COMPLEXITY: O(h) where h = height of tree
     * SPACE COMPLEXITY: O(h) due to recursion
     * 
     * 💡 FUN FACT: In a balanced BST, this is O(log n) - super fast!
     */
    public int min() {
        // BASE CASE: No left child? Then I'M the smallest!
        if (leftChild==null) {
            return data;
        }
        // RECURSIVE CASE: Ask my left child to find the minimum
        else {
            return leftChild.min();
        }
    }

    /**
     * 🔼 MAX METHOD - Find the Largest Value
     * ========================================
     * 
     * SIMPLE RULE: Keep going RIGHT until you can't go anymore!
     * 
     * VISUAL EXAMPLE:
     *        [25]
     *       /    \
     *    [20]    [30]
     *              \
     *              [35]  ← This is the maximum!
     *                \
     *                [40]  ← Actually, THIS is the maximum!
     * 
     * REAL-WORLD ANALOGY 📏:
     * Like finding the tallest person in a line organized by height,
     * where taller people always stand on the right.
     * Just keep going right until you find the rightmost person!
     * 
     * @return The largest value in this subtree
     * 
     * TIME COMPLEXITY: O(h) where h = height of tree
     * SPACE COMPLEXITY: O(h) due to recursion
     * 
     * 💡 PATTERN NOTICE: This is exactly like min(), but mirror image!
     */
    public int max() {
        // BASE CASE: No right child? Then I'M the largest!
        if (rightChild==null) {
            return data;
        }
        // RECURSIVE CASE: Ask my right child to find the maximum
        return rightChild.max();
    }

    
    // ========================================================================
    // 🔧 GETTER AND SETTER METHODS - Accessing Private Data
    // ========================================================================
    // These methods let other classes safely access and modify our data
    // This is called "encapsulation" - protecting our data! 🔒
    // ========================================================================

    /**
     * Gets the value stored in this node
     * @return The integer data value
     */
    public int getData() {
        return data;
    }

    /**
     * Updates the value stored in this node
     * @param data The new value to store
     */
    public void setData(int data) {
        this.data = data;
    }

    /**
     * Gets the left child node
     * @return Reference to the left child (or null if none)
     */
    public TreeNode getLeftChild() {
        return leftChild;
    }

    /**
     * Sets the left child node
     * @param leftChild The new left child node
     */
    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Gets the right child node
     * @return Reference to the right child (or null if none)
     */
    public TreeNode getRightChild() {
        return rightChild;
    }

    /**
     * Sets the right child node
     * @param rightChild The new right child node
     */
    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }




}

/*
 * ============================================================================
 * 🎓 KEY TAKEAWAYS FOR TREENODE CLASS
 * ============================================================================
 * 
 * 1. TreeNode is the BUILDING BLOCK of a Binary Search Tree
 * 2. Each node stores data and references to left/right children
 * 3. BST Rule: Left < Parent < Right (this makes searching fast!)
 * 4. Operations use RECURSION - a method calling itself on child nodes
 * 5. Three traversal types give us different visiting orders:
 *    - Inorder: Gives sorted output ✓
 *    - Preorder: Root first (good for copying)
 *    - Postorder: Root last (good for deleting)
 * 
 * ============================================================================
 * 💭 REMEMBER:
 * A tree is just a special type of linked list where each node can point
 * to TWO nodes instead of just one! This creates a branching structure
 * that makes searching much faster than a regular list.
 * ============================================================================
 */
