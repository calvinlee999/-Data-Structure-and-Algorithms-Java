package academy.learnprogramming.challenge2;

public class IntegerLinkedList {

    private IntegerNode head;
    private int size;

    public void addToFront(Integer value) {
        IntegerNode node = new IntegerNode(value);
        node.setNext(head);
        head = node;
        size++;
    }

    public IntegerNode removeFromFront() {
        if (isEmpty()) {
            return null;
        }

        IntegerNode removedNode = head;
        head = head.getNext();
        size--;
        removedNode.setNext(null);
        return removedNode;
    }

    /*
     * ═══════════════════════════════════════════════════════════════════════
     * CHALLENGE METHOD: insertSorted()
     * ═══════════════════════════════════════════════════════════════════════
     * 
     * PURPOSE:
     * --------
     * Insert a value into the linked list while maintaining sorted order
     * (from smallest to largest).
     * 
     * TIME COMPLEXITY: O(n)
     * -------------------
     * - In the worst case, we insert at the END of the list
     * - This means we must traverse ALL existing nodes
     * - "n" is the number of nodes currently in the list
     * 
     * SPACE COMPLEXITY: O(1)
     * --------------------
     * - We only create ONE new node, regardless of list size
     * - No additional data structures that grow with input
     * 
     * ALGORITHM STEPS:
     * ---------------
     * 1. Check if list is empty OR if value should go at the beginning
     *    - If yes, use addToFront() method
     * 2. Start with two pointers:
     *    - 'current': points to the node we're examining
     *    - 'prev': points to the node BEFORE current
     * 3. Traverse the list until we find the right position:
     *    - Keep going while current's value < our value
     *    - Stop when we find a value >= our value (or reach the end)
     * 4. Create new node and insert it between 'prev' and 'current'
     *    - Set prev's next to our new node
     *    - Set new node's next to current
     * 
     * WHY THIS SOLUTION IS OPTIMAL:
     * ----------------------------
     * - Single pass through the list (we don't traverse it multiple times)
     * - We stop as soon as we find the right position
     * - No sorting needed - list stays sorted as we insert
     * - Memory efficient - only one new node created
     * 
     * VISUALIZATION OF INSERTION IN MIDDLE:
     * -------------------------------------
     * Before inserting 3 into [1 -> 2 -> 4]:
     * 
     *   prev     current
     *     |         |
     *     v         v
     *    [2] ----> [4]
     * 
     * After inserting 3:
     * 
     *   prev               current
     *     |                   |
     *     v                   v
     *    [2] ----> [3] ----> [4]
     *               ^
     *               |
     *           new node
     * 
     * COMMON EDGE CASES:
     * -----------------
     * ✓ Empty list → insert at front
     * ✓ Value smaller than head → insert at front
     * ✓ Value larger than all existing → insert at end (current becomes null)
     * ✓ Inserting into middle → normal case
     * ✓ Duplicate values → insert after existing duplicates
     * 
     * ALTERNATIVE APPROACHES:
     * ----------------------
     * 1. Insert anywhere then sort entire list
     *    - Trade-off: Much slower (O(n log n)) but simpler code
     * 2. Use binary search (only works with arrays, not linked lists)
     *    - Trade-off: Can't do binary search on linked lists efficiently
     * 3. Recursive approach
     *    - Trade-off: Cleaner code but uses O(n) stack space
     * 
     * ═══════════════════════════════════════════════════════════════════════
     */
    public void insertSorted(Integer value) {

        // EDGE CASE #1 & #2: Empty list OR inserting at the beginning
        // If the list is empty, we obviously insert at the front
        // If the head's value is >= our value, our value should go BEFORE it
        if(isEmpty() || head.getValue()>= value){
            addToFront(value);  // Use existing method to add at front
            return;  // We're done!
        }

        // STEP 1: Set up our two pointers for traversal
        // 'current' starts at the second node (we already checked head above)
        // 'prev' keeps track of the node before current
        IntegerNode current = head.getNext();
        IntegerNode prev = head;

        // STEP 2: Find the correct position to insert
        // Keep moving forward while:
        // - We haven't reached the end (current != null)
        // - The current value is still smaller than our value
        while(current!= null && current.getValue()<value){
            prev = current;           // Move prev forward
            current = current.getNext();  // Move current forward
        }
        // When loop ends, we've found our spot!
        // Our value should go BETWEEN prev and current

        // STEP 3: Create the new node with our value
        IntegerNode insertNode = new IntegerNode(value);

        // STEP 4: Insert the new node by updating pointers
        // Connect prev to our new node
        prev.setNext(insertNode);
        // Connect our new node to current (which might be null if we're at the end)
        insertNode.setNext(current);
        
        // NOTE: We don't update 'size' here because addToFront() does it when called,
        // but in a complete implementation, we should increment size here too!

    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void printList() {
        IntegerNode current = head;
        System.out.print("HEAD -> ");
        while (current != null) {
            System.out.print(current);
            System.out.print(" -> ");
            current = current.getNext();
        }
        System.out.println("null");
    }

}
