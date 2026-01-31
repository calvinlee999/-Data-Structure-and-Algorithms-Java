package academy.learnprogramming.challenge2;

/*
 * ═══════════════════════════════════════════════════════════════════════════
 * INTEGER NODE - Building Block of Singly Linked List
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * WHAT IS A NODE?
 * ---------------
 * A node is like a box that contains:
 * 1. DATA: The actual value (an Integer)
 * 2. NEXT POINTER: A reference to the next node in the list
 * 
 * DIFFERENCE FROM DOUBLY LINKED LIST NODE:
 * ----------------------------------------
 * This is a SINGLY linked list node, so it only has:
 * - A 'value' field (the data)
 * - A 'next' field (pointer to next node)
 * 
 * It does NOT have a 'previous' pointer like doubly linked lists.
 * This makes it simpler and uses less memory, but we can only
 * travel FORWARD through the list, not backward.
 * 
 * WHY USE Integer INSTEAD OF int?
 * -------------------------------
 * - Integer is an object wrapper for primitive int
 * - Objects can be null (useful for representing "no value")
 * - Objects can be compared using methods like compareTo()
 * - Linked lists store objects, not primitive types
 * 
 * MEMORY LAYOUT:
 * --------------
 * Each node takes up:
 * - 1 Integer object reference (8 bytes on 64-bit systems)
 * - 1 IntegerNode reference for 'next' (8 bytes)
 * - Plus the memory for the Integer object itself (typically 16 bytes)
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 */

public class IntegerNode {

    // The actual integer value this node is storing
    private Integer value;
    
    // Pointer to the next node in the list (null if this is the last node)
    private IntegerNode next;

    public IntegerNode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public IntegerNode getNext() {
        return next;
    }

    public void setNext(IntegerNode next) {
        this.next = next;
    }

    public String toString() {
        return value.toString();
    }

}
