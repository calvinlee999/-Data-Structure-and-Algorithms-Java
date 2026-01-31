package academy.learnprogramming.challenge1;

public class EmployeeDoublyLinkedList {

    private EmployeeNode head;
    private EmployeeNode tail;
    private int size;

    public void addToFront(Employee employee) {
        EmployeeNode node = new EmployeeNode(employee);

        if (head == null) {
            tail = node;
        }
        else {
            head.setPrevious(node);
            node.setNext(head);
        }

        head = node;
        size++;
    }

    public void addToEnd(Employee employee) {
        EmployeeNode node = new EmployeeNode(employee);
        if (tail == null) {
            head = node;
        }
        else {
            tail.setNext(node);
            node.setPrevious(tail);
        }

        tail = node;
        size++;
    }

    /*
     * ═══════════════════════════════════════════════════════════════════════
     * CHALLENGE METHOD: addBefore()
     * ═══════════════════════════════════════════════════════════════════════
     * 
     * PURPOSE:
     * --------
     * Insert a new employee node BEFORE an existing employee in the list.
     * Return true if successful, false if the existing employee doesn't exist.
     * 
     * TIME COMPLEXITY: O(n)
     * -------------------
     * - We might need to search through ALL nodes to find the existing employee
     * - In the worst case, the employee is at the end or doesn't exist
     * - "n" represents the number of employees in the list
     * 
     * SPACE COMPLEXITY: O(1)
     * --------------------
     * - We only create ONE new node, regardless of list size
     * - We don't use any additional data structures that grow with input
     * 
     * ALGORITHM STEPS:
     * ---------------
     * 1. Check if list is empty (edge case)
     * 2. Search for the existing employee by traversing the list
     * 3. If found, create new node and update pointers:
     *    a. Set new node's previous to existing node's previous
     *    b. Set new node's next to existing node
     *    c. Update existing node's previous to point to new node
     *    d. If inserting before head, update head pointer
     *    e. Otherwise, update previous node's next pointer
     * 4. Increment size and return true
     * 
     * WHY THIS SOLUTION IS OPTIMAL:
     * ----------------------------
     * - We can't do better than O(n) without additional data structures
     * - We traverse the list only ONCE (single pass)
     * - We handle all edge cases (empty list, inserting at head)
     * - Memory efficient - only creates one new node
     * 
     * COMMON EDGE CASES:
     * -----------------
     * ✓ Empty list → return false immediately
     * ✓ Existing employee not found → return false after search
     * ✓ Inserting before head → update head pointer
     * ✓ Inserting in middle → update both surrounding nodes
     * 
     * ALTERNATIVE APPROACHES:
     * ----------------------
     * 1. HashMap approach: Store employee->node mapping for O(1) lookup
     *    - Trade-off: Uses O(n) extra space but faster lookups
     * 2. Recursive approach: Cleaner code but uses stack space
     *    - Trade-off: More elegant but less efficient (recursion overhead)
     * 
     * ═══════════════════════════════════════════════════════════════════════
     */
    public boolean addBefore(Employee newEmployee, Employee existingEmployee) {

        // EDGE CASE #1: Empty list check
        // If there are no employees, we can't insert "before" anyone!
        if(isEmpty()){
            return false;  // Nothing to insert before
        }

        // STEP 1: Search for the existing employee
        // Start from the head and traverse until we find a match or reach the end
        EmployeeNode current = head;
        while(current!= null && !current.getEmployee().equals(existingEmployee)){
            current=current.getNext();  // Move to next node
        }

        // EDGE CASE #2: Employee not found
        // If current is null, we've reached the end without finding the employee
        if(current==null){
            return false;  // Can't insert before someone who doesn't exist
        }

        // STEP 2: Create the new node for our new employee
        EmployeeNode newNode = new EmployeeNode(newEmployee);
        
        // STEP 3: Set up the new node's pointers
        // The new node should point to:
        // - PREVIOUS: whatever the current node was pointing to before
        // - NEXT: the current node (since we're inserting BEFORE it)
        newNode.setPrevious(current.getPrevious());
        newNode.setNext(current);
        
        // STEP 4: Update the current node to point back to our new node
        current.setPrevious(newNode);

        // STEP 5: Handle the special case of inserting before the head
        if(head==current){  // Are we inserting before the first node?
            head = newNode;  // Update head to be our new node
        }
        else{
            // STEP 6: Normal case - update the previous node's next pointer
            // The node that was before 'current' should now point to our new node
            newNode.getPrevious().setNext(newNode);
        }

        // STEP 7: Increment the list size and return success
        size++;

        return true;  // Successfully inserted!
    }

    public EmployeeNode removeFromFront() {
        if (isEmpty()) {
            return null;
        }

        EmployeeNode removedNode = head;

        if (head.getNext() == null) {
            tail = null;
        }
        else {
            head.getNext().setPrevious(null);
        }

        head = head.getNext();
        size--;
        removedNode.setNext(null);
        return removedNode;
    }

    public EmployeeNode removeFromEnd() {
        if (isEmpty()) {
            return null;
        }

        EmployeeNode removedNode = tail;

        if (tail.getPrevious() == null) {
            head = null;
        }
        else {
            tail.getPrevious().setNext(null);
        }

        tail = tail.getPrevious();
        size--;
        removedNode.setPrevious(null);
        return removedNode;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void printList() {
        EmployeeNode current = head;
        System.out.print("HEAD -> ");
        while (current != null) {
            System.out.print(current);
            System.out.print(" <=> ");
            current = current.getNext();
        }
        System.out.println("null");
    }

}
