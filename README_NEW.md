# 🚀 Data Structures & Algorithms - Complete Interview Preparation

> **A systematic, progressive learning path from beginner to interview-ready**

## 🎯 How to Use This Guide

This repository is organized by **learning progression**, not alphabetically. Follow the order below to build strong foundations before tackling advanced topics.

**Recommended Approach:**
1. **Beginners**: Start with Level 1, complete all exercises before moving to Level 2
2. **Intermediate**: Review Level 1 quickly, focus on Levels 2-3
3. **Advanced/Interview Prep**: Focus on Levels 3-4 and Interview Questions section

---

## 📚 Table of Contents
1. [Prerequisites & Setup](#prerequisites--setup)
2. [Level 1: Foundations (Days 1-2)](#level-1-foundations-days-1-2)
3. [Level 2: Core Data Structures (Days 3-5)](#level-2-core-data-structures-days-3-5)
4. [Level 3: Advanced Structures (Days 6-8)](#level-3-advanced-structures-days-6-8)
5. [Level 4: Interview Mastery (Days 9-10)](#level-4-interview-mastery-days-9-10)
6. [Interview Questions & Answers](#-advanced-interview-questions--answers)
7. [Study Plans](#study-plans)
8. [Resources & Next Steps](#resources--next-steps)

---

## ✅ Prerequisites & Setup

### What You Need to Know
- ✅ **Java Basics**: variables, if/else, loops, functions
- ✅ **Compile & run**: `javac` and `java` commands
- ✅ **Basic math**: addition, comparison, modulo

### Environment Setup

**Step 1: Clone Repository**
```bash
git clone https://github.com/calvinlee999/-Data-Structure-and-Algorithms-Java.git
cd Data-Structure-and-Algorithms-Java
```

**Step 2: Verify Java Installation**
```bash
java -version    # Should show Java 8 or higher
javac -version   # Should show javac
```

**Step 3: Test Your Setup**
```bash
cd BigO/src
javac com/company/bigo/Main.java
java com.company.bigo.Main
```

✅ **Expected Output**: You should see Big O examples with timing comparisons

---

## 🟢 LEVEL 1: Foundations (Days 1-2)

**Goal**: Understand how to analyze algorithm efficiency and master basic operations

### Day 1: Big O Notation & Array Fundamentals

#### 📖 Topic 1.1: Big O Notation (2-3 hours)

**What is Big O?** 
It tells you how fast your code runs as data grows.

**Learning Path:**
1. **Read**: [BigO/README.md](BigO/README.md)
2. **Run**: 
   ```bash
   cd BigO/src
   javac com/company/bigo/Main.java
   java com.company.bigo.Main
   ```
3. **Understand**: Watch the performance differences between O(1), O(n), O(n²)

**Key Concepts:**
- **O(1)** - Constant: Array access by index
- **O(log n)** - Logarithmic: Binary search
- **O(n)** - Linear: Loop through array once
- **O(n log n)** - Linearithmic: Merge sort
- **O(n²)** - Quadratic: Nested loops
- **O(2^n)** - Exponential: Recursive fibonacci

**Practice Problems:**
- [ ] [Time Complexity Quiz](https://www.bigocheatsheet.com/)
- [ ] [Two Sum](https://leetcode.com/problems/two-sum/) - Use HashMap for O(n) instead of O(n²)

**✅ Checkpoint**: Can you explain why binary search is O(log n)?

---

#### 📖 Topic 1.2: Arrays & Basic Operations (3-4 hours)

**What are Arrays?**
A row of numbered boxes storing data side-by-side in memory.

**Learning Path:**
1. **Review Code**:
   ```bash
   cd Sort/Arrays/src/com/company
   cat Main.java  # Read the code
   javac Main.java
   java Main
   ```

**Key Operations & Complexity:**
- Access by index: `arr[i]` → O(1)
- Search for value: Loop through → O(n)
- Insert at end: `arr[n] = value` → O(1)
- Insert at middle: Shift all elements → O(n)
- Delete: Shift elements → O(n)

**Practice Problems:**
- [ ] [Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
- [ ] [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)
- [ ] [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/)

**✅ Checkpoint**: Implement a function that finds the maximum element in an array

---

### Day 2: Sorting Algorithms

#### 📖 Topic 1.3: Simple Sorting - O(n²) Algorithms (2 hours)

**Why Learn Sorting?**
- Fundamental CS concept
- Appears in 30% of interviews
- Teaches you to optimize code

**Bubble Sort - Simplest But Slowest**
```bash
cd Sort/BubbleSort/src/com/company/bubblesort
javac Main.java
java Main
```

**How it works**: Compare neighbors, swap if wrong order, repeat

**When to use**: Never in production, but good for learning!

**Selection Sort**
```bash
cd Sort/SelectionSort/src/com/company/selectionsort
javac Main.java
java Main
```

**How it works**: Find minimum, swap with first, repeat for rest

**Insertion Sort**
```bash
cd Sort/InsertionSort/src/com/company/insertionsort
javac Main.java
java Main
```

**How it works**: Like sorting cards - insert each into correct position

**Comparison Table:**

| Algorithm | Best Case | Average Case | Worst Case | Space | Stable? |
|-----------|-----------|--------------|------------|-------|---------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) | Yes |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) | No |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | Yes |

**Practice Problems:**
- [ ] [Sort Colors](https://leetcode.com/problems/sort-colors/) - Single pass solution
- [ ] [Merge Sorted Array](https://leetcode.com/problems/merge-sorted-array/)

**✅ Checkpoint**: Can you implement bubble sort from memory?

---

#### 📖 Topic 1.4: Efficient Sorting - O(n log n) Algorithms (2-3 hours)

**Merge Sort - Divide and Conquer**
```bash
cd Sort/MergeSort/src/com/company/mergesort
javac Main.java
java Main
```

**How it works**: 
1. Split array in half recursively
2. Sort each half
3. Merge sorted halves

**Time**: O(n log n) always
**Space**: O(n) - needs extra array

**Quick Sort - Industry Standard**
```bash
cd Sort/QuickSort/src/com/company/quicksort
javac Main.java
java Main
```

**How it works**:
1. Pick pivot element
2. Partition: smaller left, larger right
3. Recursively sort partitions

**Time**: O(n log n) average, O(n²) worst
**Space**: O(log n) - recursion stack

**When to Use:**
- **Merge Sort**: Need guaranteed O(n log n), have extra space
- **Quick Sort**: Want in-place sorting, average case performance

**Practice Problems:**
- [ ] [Sort an Array](https://leetcode.com/problems/sort-an-array/)
- [ ] [Kth Largest Element](https://leetcode.com/problems/kth-largest-element-in-an-array/) - Use QuickSelect
- [ ] [Merge Intervals](https://leetcode.com/problems/merge-intervals/)

**✅ Checkpoint**: Explain the difference between Merge Sort and Quick Sort

---

#### 📖 Topic 1.5: Binary Search (2 hours)

**What is Binary Search?**
Efficiently find element in **sorted** array by eliminating half each time.

```bash
cd binarySearch/src/com/company/binarysearch
javac Main.java
java Main
```

**Algorithm:**
```java
int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;  // Avoid overflow
        
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    
    return -1;  // Not found
}
```

**Key Points:**
- ✅ Array MUST be sorted
- ✅ O(log n) time
- ✅ O(1) space
- ✅ Can be applied to many problems beyond searching

**Practice Problems:**
- [ ] [Binary Search](https://leetcode.com/problems/binary-search/)
- [ ] [First Bad Version](https://leetcode.com/problems/first-bad-version/)
- [ ] [Search Insert Position](https://leetcode.com/problems/search-insert-position/)
- [ ] [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) 🔥

**✅ Checkpoint**: Solve Binary Search on LeetCode in under 10 minutes

---

## 🟡 LEVEL 2: Core Data Structures (Days 3-5)

**Goal**: Master linear data structures and their applications

### Day 3: LinkedLists

#### 📖 Topic 2.1: Singly LinkedList (3 hours)

**What is a LinkedList?**
A chain where each element points to the next. Unlike arrays, elements aren't side-by-side in memory.

```bash
cd LinkedList/LinkedList/src/com/company/linkedlist
javac Main.java
java Main
```

**Structure:**
```
[Data|Next] -> [Data|Next] -> [Data|Next] -> null
```

**vs Array:**
| Operation | Array | LinkedList |
|-----------|-------|------------|
| Access by index | O(1) | O(n) |
| Insert at beginning | O(n) | O(1) ✅ |
| Insert at end | O(1) | O(n) or O(1) with tail pointer |
| Insert in middle | O(n) | O(1) if you have the node |
| Delete | O(n) | O(1) if you have previous node |
| Memory | Contiguous | Scattered |

**When to Use LinkedList:**
- ✅ Frequent insertions/deletions at beginning
- ✅ Don't know size in advance
- ✅ Implementing stacks, queues
- ❌ Need random access by index

**Practice Problems:**
- [ ] [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) ⭐ Must-know
- [ ] [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/)
- [ ] [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/) - Floyd's algorithm
- [ ] [Remove Nth Node From End](https://leetcode.com/problems/remove-nth-node-from-end-of-list/)

**✅ Checkpoint**: Implement reverse linkedlist in under 15 minutes

---

#### 📖 Topic 2.2: Doubly LinkedList (1-2 hours)

**What's Different?**
Each node has TWO pointers: previous and next

```bash
cd LinkedList/DoublyLinkedList/src/com/company/doublylinkedlist
javac Main.java
java Main
```

**Structure:**
```
null <- [Prev|Data|Next] <-> [Prev|Data|Next] <-> [Prev|Data|Next] -> null
```

**Advantages:**
- ✅ Can traverse backwards
- ✅ Easier deletion (have previous node)

**Disadvantages:**
- ❌ More memory (extra pointer per node)
- ❌ More complex code

**When to Use:**
- Browser history (forward/back buttons)
- Undo/Redo functionality
- LRU Cache implementation

**Practice Problems:**
- [ ] [LRU Cache](https://leetcode.com/problems/lru-cache/) 🔥 Classic problem
- [ ] [Design Browser History](https://leetcode.com/problems/design-browser-history/)

**✅ Checkpoint**: Explain when you'd use Doubly vs Singly LinkedList

---

### Day 4: Stacks & Queues

#### 📖 Topic 2.3: Stack - LIFO (2-3 hours)

**What is a Stack?**
Last In, First Out - like a stack of plates

```bash
cd Stacks/stacks/src/com/company/stacks
javac Main.java
java Main
```

**Operations:**
- `push(x)` - Add to top - O(1)
- `pop()` - Remove from top - O(1)
- `peek()` - Look at top - O(1)
- `isEmpty()` - Check if empty - O(1)

**Real-World Uses:**
- Function call stack
- Undo mechanism
- Browser back button
- Expression evaluation: `(3 + 4) * 5`
- DFS (Depth-First Search)

**Practice Problems:**
- [ ] [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) ⭐ Classic
- [ ] [Min Stack](https://leetcode.com/problems/min-stack/)
- [ ] [Evaluate Reverse Polish Notation](https://leetcode.com/problems/evaluate-reverse-polish-notation/)
- [ ] [Daily Temperatures](https://leetcode.com/problems/daily-temperatures/) - Monotonic stack

**✅ Checkpoint**: Solve Valid Parentheses in under 10 minutes

---

#### 📖 Topic 2.4: Queue - FIFO (2 hours)

**What is a Queue?**
First In, First Out - like waiting in line

```bash
cd Queue/Queue/src/com/company/queue
javac Main.java
java Main
```

**Operations:**
- `enqueue(x)` / `offer(x)` - Add to back - O(1)
- `dequeue()` / `poll()` - Remove from front - O(1)
- `peek()` - Look at front - O(1)

**Real-World Uses:**
- Print queue
- Task scheduling
- BFS (Breadth-First Search)
- Message queues

**Practice Problems:**
- [ ] [Implement Queue using Stacks](https://leetcode.com/problems/implement-queue-using-stacks/)
- [ ] [Number of Recent Calls](https://leetcode.com/problems/number-of-recent-calls/)
- [ ] [Design Circular Queue](https://leetcode.com/problems/design-circular-queue/)

**✅ Checkpoint**: Implement Queue using two stacks

---

### Day 5: Hash Tables

#### 📖 Topic 2.5: Hash Tables (3-4 hours)

**What is a Hash Table?**
Super-fast lookup using keys (like a dictionary)

```bash
cd Hashtable/hashtable/src/com/company/hashtable
javac Main.java
java Main
```

**How It Works:**
1. Key → Hash Function → Index
2. Store value at that index
3. Handle collisions (chaining or open addressing)

**Operations:**
- `put(key, value)` - O(1) average
- `get(key)` - O(1) average  
- `remove(key)` - O(1) average
- Worst case: O(n) if all keys collide

**Collision Handling:**

**1. Chaining** (Java HashMap uses this)
```bash
cd Hashtable/HashtableChaining/src/com/company/hashtablechaining
javac Main.java
java Main
```
- Each bucket is a LinkedList
- Multiple keys can hash to same index

**2. Open Addressing**
- Find next empty slot
- Linear probing, quadratic probing

**Java Collections:**
- `HashMap` - Not synchronized, allows null
- `Hashtable` - Synchronized, no null
- `ConcurrentHashMap` - Thread-safe, better performance

**When to Use:**
- ✅ Need O(1) lookups
- ✅ Counting frequency
- ✅ Caching
- ✅ Finding duplicates
- ❌ Need ordered data (use TreeMap)
- ❌ Range queries

**Practice Problems:**
- [ ] [Two Sum](https://leetcode.com/problems/two-sum/) ⭐ HashMap solution
- [ ] [Group Anagrams](https://leetcode.com/problems/group-anagrams/)
- [ ] [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/)
- [ ] [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)
- [ ] [Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/)

**✅ Checkpoint**: Solve Two Sum using HashMap in O(n) time

---

## 🟠 LEVEL 3: Advanced Structures (Days 6-8)

**Goal**: Master hierarchical data structures and advanced algorithms

### Day 6-7: Trees

#### 📖 Topic 3.1: Binary Trees (3 hours)

**What is a Binary Tree?**
Hierarchical structure where each node has at most 2 children

```bash
cd binarySearchTree/src/com/company/binarysearchtree
javac Main.java
java Main
```

**Structure:**
```
        10
       /  \
      5    15
     / \   / \
    3   7 12  20
```

**Tree Traversal:**

1. **Inorder** (Left, Root, Right): `[3, 5, 7, 10, 12, 15, 20]` - **Sorted order for BST!**
2. **Preorder** (Root, Left, Right): `[10, 5, 3, 7, 15, 12, 20]`
3. **Postorder** (Left, Right, Root): `[3, 7, 5, 12, 20, 15, 10]`
4. **Level-order** (BFS): `[10, 5, 15, 3, 7, 12, 20]`

**Binary Search Tree (BST) Property:**
- Left subtree < Root < Right subtree
- Enables O(log n) search (if balanced)

**Operations:**
- Search: O(log n) average, O(n) worst (skewed tree)
- Insert: O(log n) average, O(n) worst
- Delete: O(log n) average, O(n) worst

**Practice Problems:**
- [ ] [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/)
- [ ] [Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/)
- [ ] [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) ⭐
- [ ] [Lowest Common Ancestor of BST](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/)
- [ ] [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) ⭐
- [ ] [Kth Smallest Element in BST](https://leetcode.com/problems/kth-smallest-element-in-a-bst/)

**✅ Checkpoint**: Implement inorder traversal recursively and iteratively

---

### Day 8: Heaps & Priority Queues

#### 📖 Topic 3.2: Heaps (3-4 hours)

**What is a Heap?**
A complete binary tree where parent is always larger (max-heap) or smaller (min-heap) than children

```bash
cd maxHeap/src/com/company/maxheap
javac Main.java
java Main
```

**Max Heap:**
```
        100
       /   \
      80    60
     / \   / \
    40 50 30 20
```

**Properties:**
- Complete binary tree (filled left to right)
- Parent ≥ Children (max-heap) or Parent ≤ Children (min-heap)
- Implemented using array: `children of arr[i]` are at `2i+1` and `2i+2`

**Operations:**
- Insert: O(log n)
- Extract max/min: O(log n)
- Peek max/min: O(1)
- Build heap: O(n)

**Priority Queue:**
```bash
cd PriorityQueue/src/com/company/priorityqueue
javac Main.java
java Main
```

Java's `PriorityQueue` is a min-heap by default

**When to Use:**
- ✅ Find min/max repeatedly
- ✅ Top K problems
- ✅ Merge K sorted lists/arrays
- ✅ Task scheduling by priority
- ✅ Dijkstra's algorithm

**Practice Problems:**
- [ ] [Kth Largest Element](https://leetcode.com/problems/kth-largest-element-in-an-array/) ⭐
- [ ] [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/)
- [ ] [Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/) 🔥
- [ ] [Merge K Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/) ⭐
- [ ] [K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/)

**✅ Checkpoint**: Solve Kth Largest Element using heap in O(n log k)

---

## 🔴 LEVEL 4: Interview Mastery (Days 9-10)

**Goal**: Combine concepts, solve complex problems, master interview patterns

### Day 9: Advanced Algorithms & Patterns

#### 📖 Topic 4.1: Two Pointers Pattern (2 hours)

**When to Use:**
- Sorted arrays
- LinkedList problems
- String problems

**Problems:**
- [ ] [3Sum](https://leetcode.com/problems/3sum/)
- [ ] [Container With Most Water](https://leetcode.com/problems/container-with-most-water/)
- [ ] [Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/)

#### 📖 Topic 4.2: Sliding Window Pattern (2 hours)

**When to Use:**
- Substring problems
- Subarray problems

**Problems:**
- [ ] [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)
- [ ] [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/)
- [ ] [Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/)

#### 📖 Topic 4.3: DFS & BFS (2 hours)

**Practice:**
- [ ] [Number of Islands](https://leetcode.com/problems/number-of-islands/)
- [ ] [Clone Graph](https://leetcode.com/problems/clone-graph/)
- [ ] [Course Schedule](https://leetcode.com/problems/course-schedule/)

#### 📖 Topic 4.4: Dynamic Programming Intro (2 hours)

**Problems:**
- [ ] [Climbing Stairs](https://leetcode.com/problems/climbing-stairs/)
- [ ] [House Robber](https://leetcode.com/problems/house-robber/)
- [ ] [Coin Change](https://leetcode.com/problems/coin-change/)

### Day 10: Mock Interviews & Review

- **Morning**: 2-3 mock interviews (45 min each)
- **Afternoon**: Review weak areas, practice problems

---

## 💼 Advanced Interview Questions & Answers

> These questions test engineering judgment and system design understanding

### Question 1: Why does Java use Red-Black Tree for TreeMap instead of AVL Tree?

**Level**: Senior

**Answer**:

While AVL trees maintain stricter balance (height difference ≤ 1), Red-Black trees are "balanced enough" with better insertion/deletion performance.

**Key Trade-offs:**

| Feature | AVL Tree | Red-Black Tree |
|---------|----------|----------------|
| Balance | Stricter | Looser |
| Search | Faster (fewer comparisons) | Slightly slower |
| Insert/Delete | Slower (more rotations) | Faster ✅ |
| Rotations per insert | Up to log n | At most 2 |

**Real-world Impact:**
For write-heavy workloads (stock prices, transaction logs), Red-Black trees have higher throughput due to fewer rotations.

---

### Question 2: How does Java HashMap handle collisions after Java 8?

**Level**: Mid to Senior

**Answer**:

**Before Java 8**: Chaining with LinkedList → O(n) worst case

**After Java 8**: 
- Start with LinkedList
- When bucket exceeds 8 elements → **Treeify** to Red-Black Tree
- Improves worst case: O(n) → O(log n)
- When drops below 6 → Convert back to LinkedList

**Why threshold 8?**
Poisson distribution shows probability of 8+ collisions is ~0.00000006 with good hash function

---

### Question 3: BlockingQueue - Exactly-Once Delivery Challenge

**Level**: Senior (Concurrency)

**The Problem**:
```java
Transaction txn = queue.take();  // Removed from queue
// ⚠️ If crash happens here, txn is lost!
processTransaction(txn);
```

**Solutions**:
1. **Transactional Outbox**: Save to DB before processing
2. **Kafka**: Commit offset only after successful processing
3. **Acknowledgment-based Queue**: Remove only after explicit ACK

---

### Question 4: When to use Skip List over Binary Search Tree?

**Level**: Senior

**Answer**: 

Skip Lists excel in **concurrent environments** because:
- **Probabilistic structure** (no strict balancing)
- **Local modifications** (insertions don't affect distant nodes)
- **Simpler lock-free implementations**

Java's `ConcurrentSkipListMap` is easier to make thread-safe than concurrent Red-Black tree.

**Use When:**
- High concurrency sorted map/set
- Real-time leaderboards, price books

---

### Question 5: Design LRU Cache

**Level**: Medium to Senior

**Solution**: HashMap + Doubly LinkedList

**Why?**
- HashMap: O(1) key lookup
- Doubly LinkedList: O(1) reordering (move to front)

**Implementation highlights**:
```java
class LRUCache {
    Map<Integer, Node> cache;
    Node head, tail;  // Dummy nodes
    
    // get() → move to front
    // put() → add to front, evict tail if over capacity
}
```

---

## 📊 Study Plans

### 🔥 2-Day Intensive (16 hours total)

**For:** Last-minute prep, experienced developers

**Day 1 (8h)**: Arrays, Sorting, LinkedLists, Stacks, Queues, Hash Tables
**Day 2 (8h)**: Trees, Heaps, Binary Search, Mock Interviews

**Focus**: 20 must-know LeetCode problems

---

### 📚 10-Day Comprehensive (60-80 hours)

**For:** Thorough preparation, interview confidence

Follow Levels 1-4 above with deep practice (60+ problems)

---

## 📖 Resources & Next Steps

### After Completing This Guide:

1. **Practice Platforms**:
   - [LeetCode](https://leetcode.com) - Start with Easy, progress to Medium
   - [HackerRank](https://hackerrank.com) - Good for beginners
   - [NeetCode](https://neetcode.io) - Curated problem lists

2. **Advanced Topics** (after mastering basics):
   - Graphs (BFS, DFS, Dijkstra, Union-Find)
   - Dynamic Programming
   - Advanced Trees (Trie, Segment Tree, Fenwick Tree)
   - System Design

3. **Books**:
   - "Cracking the Coding Interview" by Gayle McDowell
   - "Elements of Programming Interviews" by Adnan Aziz

4. **Online Resources**:
   - [Big O Cheat Sheet](https://www.bigocheatsheet.com/)
   - [VisuAlgo](https://visualgo.net) - Visualize algorithms

---

## 🎯 Success Checklist

Before your interview, ensure you can:

- [ ] Explain time/space complexity of your solutions
- [ ] Implement common data structures from scratch
- [ ] Recognize and apply 8+ problem-solving patterns
- [ ] Write bug-free code in 30-45 minutes
- [ ] Ask clarifying questions before coding
- [ ] Test your code with edge cases
- [ ] Optimize brute-force solutions

---

## 🤝 Contributing

Found an error? Have a better explanation? Submit a PR!

## 📄 License

This repository is for educational purposes. Feel free to use for interview prep.

---

**Good luck with your interviews! 🚀**

Remember: Consistency beats intensity. 30 minutes daily > 5 hours once a week.
