# Data Structures and Algorithms in Java

> **Comprehensive Learning Repository for Computer Science Fundamentals**  
> *Master the essential data structures that power modern systems*

---

## Overview

This repository contains in-depth implementations and explanations of core data structures and algorithms in Java. Whether you're preparing for technical interviews, system design, or building optimal software, these implementations provide both theoretical understanding and practical code.

---

## Table of Contents

- [Core Data Structures](#core-data-structures)
- [Sorting Algorithms](#sorting-algorithms)
- [Advanced Data Structures](#advanced-data-structures)
- [How to Use This Repository](#how-to-use-this-repository)
- [Learning Path](#learning-path)

---

## Core Data Structures

### Linear Structures

#### 1. **Arrays**
**Location:** `/Sort/Arrays/`

- Foundation of most data structures
- Fixed-size, contiguous memory
- O(1) access, O(n) insertion/deletion
- Use cases: Direct access to elements, static collections

**Key Concepts:**
- Multi-dimensional arrays
- Array transformations
- Array manipulation techniques

---

#### 2. **Linked Lists**
**Location:** `/LinkedList/`

- Dynamic size, non-contiguous memory
- O(n) access, O(1) insertion/deletion at node
- Perfect for: Queue/Stack implementations, dynamic collections

**Implementations:**
- `LinkedList/LinkedList/` - Singly Linked List
- `LinkedList/DoublyLinkedList/` - Doubly Linked List for bidirectional traversal
- `LinkedList/jdkLinkedList/` - JDK's built-in LinkedList

**Key Operations:**
- Insert at beginning, middle, end
- Delete by value or position
- Reverse list
- Detect cycles

**Challenges:**
- Lists-Linked-Lists-Challenge-#1
- Lists-Linked-Lists-Challenge-#2

---

#### 3. **Stacks**
**Location:** `/Stacks/`

**Principle:** LIFO (Last In, First Out)

- Remove only from top
- O(1) push, O(1) pop
- Real-world: Browser back button, undo functionality, expression evaluation

**Implementations:**
- `Stacks/stacks/` - Array-based stack
- `Stacks/LinkedListStack/` - Linked list-based stack

**Key Operations:**
- Push, Pop, Peek
- Check if empty

**Challenge:** Lists-Stacks-Challenge

---

#### 4. **Queues**
**Location:** `/Queue/`

**Principle:** FIFO (First In, First Out)

- Insert at rear, remove from front
- O(1) enqueue, O(1) dequeue
- Real-world: Job scheduling, message processing, resource management

**Implementations:**
- `Queue/Queue/` - Based on ArrayList
- `PriorityQueue/` - Elements processed by priority, not insertion order

**Challenge:** Queues-Queues-Challenge

---

### Hierarchical Structures

#### 5. **Binary Search Trees (BST)**
**Location:** `/binarySearchTree/`

- Sorted binary tree
- O(log n) search, insertion, deletion (average case)
- Maintains order, allows range queries
- Real-world: Database indexes, sorted maps

**Properties:**
- Left child < Parent < Right child
- Enable in-order traversal (sorted output)

---

#### 6. **Heaps**
**Location:** `/maxHeap/`

**Principle:** Complete binary tree with heap property

- Max Heap: Parent ≥ Children
- Min Heap: Parent ≤ Children
- O(log n) insertion, O(1) find-max
- Real-world: Priority queues, scheduling, stream processing

**Key Operations:**
- Insert, Extract Max, Heapify

---

### Hash-Based Structures

#### 7. **Hash Tables**
**Location:** `/Hashtable/`

**Implementations:**
- `hashtable/` - Simple hash table with collision handling
- `HashtableChaining/` - Chaining for collision resolution
- `hashtableJDK/` - Using JDK's HashMap
- `bucketSort/` - Using hash concepts for sorting

**Key Concepts:**
- Hash function design
- Collision resolution strategies:
  - Chaining (simple, but overhead)
  - Open addressing (linear probing, quadratic probing)
- Load factor and resizing

**Average Performance:**
- O(1) search, insert, delete
- O(n) worst case (many collisions)

**Real-world:** Databases, caching, duplicate detection

**Challenges:**
- Hashtables-Hashtables-Challenge-#1-hashfunc
- Hashtables-Hashtables-Challenge-removeDuplicateOfLinkedList

---

## Sorting Algorithms

**Location:** `/Sort/`

Sorting is fundamental. Different algorithms excel in different scenarios.

### Comparison-Based Sorting

| Algorithm | Best | Average | Worst | Space | Stable? | Notes |
|-----------|------|---------|-------|-------|---------|-------|
| **Bubble Sort** | O(n) | O(n²) | O(n²) | O(1) | Yes | Simple, rarely used in practice |
| **Selection Sort** | O(n²) | O(n²) | O(n²) | O(1) | No | Minimizes swaps |
| **Insertion Sort** | O(n) | O(n²) | O(n²) | O(1) | Yes | Good for small n, nearly sorted |
| **Merge Sort** | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes | Guaranteed O(n log n), stable |
| **Quick Sort** | O(n log n) | O(n log n) | O(n²) | O(log n) | No | Fastest average, cache-friendly |
| **Shell Sort** | O(n log n) | O(n^1.3) | O(n²) | O(1) | No | Good for medium n |
| **Heap Sort** | O(n log n) | O(n log n) | O(n log n) | O(1) | No | Guaranteed O(n log n), in-place |

### Non-Comparison Sorting

| Algorithm | Time | Space | When to Use |
|-----------|------|-------|-------------|
| **Counting Sort** | O(n+k) | O(k) | Small key range |
| **Radix Sort** | O(d·n) | O(n+k) | Strings, integers, fixed-length keys |
| **Bucket Sort** | O(n+k) | O(n+k) | Uniformly distributed data |

### Implementations

- `Sort/BubbleSort/` - Simple O(n²) sort
- `Sort/SelectionSort/` - Selection sort
- `Sort/InsertionSort/` - Basic insertion sort
- `Sort/insertionSortRecursive/` - Recursive version
- `Sort/MergeSort/` - Stable O(n log n)
- `Sort/MergeSort2/` - Alternative implementation
- `Sort/MergeSortDescend/` - Descending order
- `Sort/QuickSort/` - Fastest average case
- `Sort/ShellSort/` - Gap-based sorting
- `Sort/Arrays/` - Built-in Arrays sorting
- `Sort/countingsort/` - Linear time for bounded integers
- `Sort/radixsort/` - Linear time sorting
- `Sort/bucketSort/` - Bucket-based distribution

---

## Advanced Data Structures

### Big O Analysis
**Location:** `/BigO/`

Understanding complexity:
- Time complexity: How runtime grows with input size
- Space complexity: How memory grows with input size

Common complexities (best to worst):
- O(1) - Constant
- O(log n) - Logarithmic
- O(n) - Linear
- O(n log n) - Linearithmic
- O(n²) - Quadratic
- O(2ⁿ) - Exponential
- O(n!) - Factorial

---

## How to Use This Repository

### 1. **Explore by Data Structure**

Choose a folder matching your target:

```bash
# Learn LinkedLists
cd LinkedList/LinkedList
ls -la
```

### 2. **Compile and Run**

```bash
# Navigate to implementation
cd LinkedList/LinkedList/src/com/company

# Compile
javac LinkedListMain.java

# Run
java LinkedListMain
```

### 3. **Study the Code**

Each implementation includes:
- Clear variable names
- Inline comments explaining logic
- Example usage in main()

### 4. **Solve Challenges**

Push yourself:
- `LinkedList/Lists-Linked-Lists-Challenge-#1`
- `LinkedList/Lists-Linked-Lists-Challenge-#2`
- `Stacks/Lists-Stacks-Challenge`
- Others...

---

## Learning Path

### Beginner (1-2 weeks)
1. Arrays and Big O Analysis
2. Linked Lists (singly)
3. Stacks and Queues
4. Basic sorting: Bubble, Selection, Insertion

**Goal:** Understand basic structures and why they matter

### Intermediate (2-3 weeks)
5. Advanced sorting: Merge, Quick, Heap
6. Binary Search Trees
7. Hash Tables
8. More advanced Linked Lists (doubly, circular)

**Goal:** Solve tree and hash problems confidently

### Advanced (2-3 weeks)
9. Heaps and Priority Queues
10. Balanced trees, Tries, Graphs (if available)
11. Challenge problems
12. System design applications

**Goal:** Prepare for technical interviews, handle real-world design

### System Design Context
13. Integration with `java-functional-programming/`
14. Building reactive pipelines with collections
15. Concurrent collections for multi-threaded systems

---

## Key Algorithmic Concepts

### Divide and Conquer
- Merge Sort: Divide array, solve subproblems, combine
- Quick Sort: Partition around pivot, recursively sort

### Dynamic Programming
- Break into overlapping subproblems
- Store results to avoid recomputation
- Often solves NP-hard problems in polynomial time

### Greedy Algorithms
- Make locally optimal choices
- Hope they lead to global optimum
- Use for: scheduling, approximation algorithms

### Pattern Matching
- KMP Algorithm: Linear time string matching
- Rabin-Karp: Hash-based matching
- Boyer-Moore: Skipping irrelevant positions

---

## Project Structure

```
Data-Structure-and-Algorithms-Java/
├── BigO/                          # Complexity analysis
├── LinkedList/                    # Singly, doubly, challenges
├── Stacks/                        # Stack implementations + challenges
├── Queue/                         # Queue, Priority Queue, challenges
├── binarySearchTree/              # BST implementations
├── maxHeap/                       # Heap implementations
├── Hashtable/                     # Hash tables, chaining, challenges
├── Sort/                          # 10+ sorting algorithms
├── binarySearch/                  # Binary search variations
├── java-functional-programming/   # Related: Functional Java patterns
└── README files                   # Documentation
```

---

## Best Practices

### When Choosing a Data Structure

| Need | Structure | Why |
|------|-----------|-----|
| Fast access by index | Array | O(1) access |
| Frequent insertion/deletion | LinkedList | O(1) insertion at known position |
| LIFO semantics | Stack | Natural fit |
| FIFO semantics | Queue | Natural fit |
| Ordered, sorted access | BST | O(log n) operations |
| Fast lookup | Hash Table | O(1) average case |
| Priority-based | Heap | O(log n), best for top-k |

### Code Quality Checklist

- [ ] Choose the right data structure
- [ ] Implement with correct complexity
- [ ] Handle edge cases (empty, single, large) 
- [ ] Write unit tests
- [ ] Document time/space complexity
- [ ] Consider memory overhead

---

## Performance Benchmarking

When building systems, always benchmark against actual data:

```java
long start = System.nanoTime();
// ... operation ...
long end = System.nanoTime();
System.out.println("Time: " + (end - start) / 1_000_000 + "ms");
```

What matters:
- Real-world data sizes
- Access patterns (sequential vs. random)
- Hardware (CPU cache, RAM speed)
- Garbage collection impact

---

## Further Learning

### Essential Algorithms
- Binary Search
- Depth-First Search (DFS)
- Breadth-First Search (BFS)
- Dijkstra's Algorithm
- Floyd-Warshall Algorithm
- Topological Sort

### Advanced Topics
- Graph algorithms
- Segment trees, Fenwick trees
- Suffix arrays, Suffix trees
- Approximation algorithms
- Machine learning data structures

### Interview Preparation
- 200 classic LeetCode problems
- Focus on patterns, not brute force
- Practice under time constraints
- Explain your reasoning clearly

---

## Version History

**v1.0** - Feb 2026  
Initial comprehensive repo with all major data structures and algorithms

---

## Contributing

Found a bug? Want to add an algorithm? Follow these steps:

1. Fork the repository
2. Create a branch for your changes
3. Add comprehensive comments
4. Include complexity analysis in docstring
5. Submit a pull request

---

## License

This educational material is provided as-is for learning purposes.

---

**Last Updated:** February 2026  
**Maintained By:** @calvinlee999  
**Repository:** [GitHub](https://github.com/calvinlee999/-Data-Structure-and-Algorithms-Java)

