# 🚀 Data Structures & Algorithms - Complete Interview Mastery Guide

> **A systematic, battle-tested learning path from beginner to FAANG-ready**  
> **Total Time Investment: 60-80 hours over 10 days**

[![Learning Path](https://img.shields.io/badge/Learning-Progressive-brightgreen)]() [![Interview Ready](https://img.shields.io/badge/Interview-Ready-blue)]() [![Tested](https://img.shields.io/badge/All%20Code-Tested-success)]()

---

## 🎯 How to Use This Guide

**This repository is organized by LEARNING PROGRESSION, not alphabetically.**

### Choose Your Path:

| Experience Level | Start Here | Time Needed | Focus |
|-----------------|------------|-------------|-------|
| **Complete Beginner** | [Level 1](#-level-1-foundations-days-1-2) | 80 hours | Master fundamentals |
| **CS Student / Bootcamp Grad** | [Level 2](#-level-2-core-data-structures-days-3-5) | 60 hours | Core structures + practice |
| **2+ Years Experience** | [Level 3](#-level-3-advanced-structures-days-6-8) | 40 hours | Advanced topics |
| **Interview in 2 Days** | [2-Day Intensive](#-2-day-intensive-16-hours) | 16 hours | Essential problems |

---

## 📚 Table of Contents

1. [Prerequisites & Setup](#-prerequisites--setup)
2. [Common Errors & Solutions](#-common-errors--solutions)
3. [Level 1: Foundations](#-level-1-foundations-days-1-2)
4. [Level 2: Core Data Structures](#-level-2-core-data-structures-days-3-5)
5. [Level 3: Advanced Structures](#-level-3-advanced-structures-days-6-8)
6. [Level 4: Interview Mastery](#-level-4-interview-mastery-days-9-10)
7. [Java Collections Cheat Sheet](#-java-collections-cheat-sheet)
8. [Interview Framework (REACTO)](#-interview-framework-reacto)
9. [Pattern Recognition Guide](#-pattern-recognition-guide-10-essentials)
10. [Advanced Interview Q&A](#-advanced-interview-questions--answers)
11. [How Interviewers Grade You](#-how-interviewers-grade-you)
12. [Production Engineering Notes](#-production-engineering-notes)

---

## ✅ Prerequisites & Setup

### What "Java Basics" Means (Self-Check):

**You should be comfortable with:**
- [ ] Variables & data types (`int`, `String`, `boolean`, arrays)
- [ ] Operators (`+`, `-`, `*`, `/`, `%`, `==`, `!=`, `<`, `>`)
- [ ] Control flow (`if/else`, `switch`, `for`, `while`, `do-while`)
- [ ] Functions/methods (parameters, return values)
- [ ] Classes & objects (basic OOP)
- [ ] Basic I/O (`System.out.println()`, `Scanner`)

**Test yourself:**
```java
// Can you write this without looking it up?
public static int sumArray(int[] nums) {
    int sum = 0;
    for (int num : nums) {
        sum += num;
    }
    return sum;
}
```

If yes → **You're ready!** ✅  
If no → **Take 2-3 days** to learn Java basics first ([Codecademy Java](https://www.codecademy.com/learn/learn-java) or [Java Tutorial](https://docs.oracle.com/javase/tutorial/))

---

### Environment Setup (10 minutes)

**Step 1: Verify Java (Required)**
```bash
java -version    # Should show 1.8 or higher
javac -version   # Should show javac
```

❌ **If not installed**: [Download JDK](https://www.oracle.com/java/technologies/downloads/)

**Step 2: Clone Repository**
```bash
git clone https://github.com/calvinlee999/-Data-Structure-and-Algorithms-Java.git
cd Data-Structure-and-Algorithms-Java
```

**Step 3: Verify Setup**
```bash
cd BigO/src
javac com/company/bigo/Main.java
java com.company.bigo.Main
```

✅ **Expected**: You see "=== BIG O NOTATION PRACTICE ===" with timing comparisons

---

## 🆘 Common Errors & Solutions

### Error 1: "Could not find or load main class Main"

**Cause**: Wrong directory or missing package name

**Solution**:
```bash
# ❌ WRONG
cd Sort/BubbleSort/src/com/company/bubblesort
javac Main.java
java Main  # FAILS!

# ✅ CORRECT
cd Sort/BubbleSort/src
javac com/company/bubblesort/Main.java
java com.company.bubblesort.Main  # Works!
```

**Rule**: Always compile/run from the `src` folder with FULL package name

---

### Error 2: "javac: command not found"

**Cause**: Java JDK not installed or not in PATH

**Solution (Mac)**:
```bash
brew install openjdk@17
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

**Solution (Windows)**: Add Java bin folder to System PATH

---

### Error 3: "package does not exist" when compiling

**Cause**: Compiling from wrong directory

**Solution**: Always cd to `src` folder first, then use package path

---

## 🟢 LEVEL 1: Foundations (Days 1-2)

**Goal**: Master algorithm analysis and basic operations  
**Time**: 16-20 hours  
**Prerequisites**: Java basics (see checklist above)

---

### Day 1, Morning: Big O Notation (3 hours)

#### 📖 Topic 1.1: Understanding Algorithm Efficiency

**Why Learn This First?**
Without Big O, you can't evaluate if your solution is good enough for an interview.

**Core Concept**: Big O describes how runtime/space grows as input size increases.

**The 6 Essential Complexities:**

| Notation | Name | Example | 1000 items | 1M items |
|----------|------|---------|------------|----------|
| O(1) | Constant | Array access | 1 step | 1 step |
| O(log n) | Logarithmic | Binary search | ~10 steps | ~20 steps |
| O(n) | Linear | Loop through array | 1000 steps | 1M steps |
| O(n log n) | Linearithmic | Merge sort | ~10K steps | ~20M steps |
| O(n²) | Quadratic | Nested loops | 1M steps | 1T steps ⚠️ |
| O(2^n) | Exponential | Recursive fibonacci | ∞ (unusable) | ∞ (unusable) |

**Hands-On Practice:**
```bash
cd BigO/src
javac com/company/bigo/Main.java
java com.company.bigo.Main
```

**Observe the output:**
- O(1): 200-500 nanoseconds (nearly instant)
- O(log n): ~1,000 ns
- O(n): ~7,000 ns
- O(n log n): ~500,000 ns
- O(n²): ~150,000 ns (only n=100!)

**📊 Key Takeaway**: O(n²) is 150x slower than O(n) for just 100 items!

**Practice:**
- [ ] [Big O Quiz](https://www.bigocheatsheet.com/)
- [ ] Analyze: What's the Big O of this code?
```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        System.out.println(i + j);
    }
}
// Answer: O(n²) - two nested loops
```

**✅ Checkpoint**: Can you explain why binary search is O(log n)?

**Estimated Time**: 3 hours (2h learning, 1h practice)

---

### Day 1, Afternoon: Arrays & Two Pointers (4 hours)

#### 📖 Topic 1.2: Arrays - The Foundation

**What are Arrays?**
Contiguous memory blocks with indexed access.

```
Index:  [0] [1] [2] [3] [4]
Value:  [5] [2] [8] [1] [9]
Memory: 0x100 0x104 0x108 0x10C 0x110
        (each int = 4 bytes)
```

**Run the Code:**
```bash
cd Sort/Arrays/src
javac com/company/Main.java
java com.company.Main
```

**Core Operations:**

| Operation | Code | Time | Space |
|-----------|------|------|-------|
| Access | `arr[i]` | O(1) | O(1) |
| Search (unsorted) | Loop | O(n) | O(1) |
| Insert at end | `arr[n] = x` | O(1)* | O(1) |
| Insert at index i | Shift all | O(n) | O(1) |
| Delete | Shift all | O(n) | O(1) |

*Assuming space available

**When to Use Arrays:**
- ✅ Random access by index needed
- ✅ Size known in advance
- ✅ Sequential access (cache-friendly)
- ❌ Frequent insertions/deletions in middle
- ❌ Dynamic size (use ArrayList)

**Java Collections Mapping:**
- **Array** (`int[]`, `String[]`) - Fixed size, primitive friendly
- **ArrayList** - Dynamic array, auto-resizing
- **Arrays.asList()** - Fixed-size list wrapper

**Practice Problems:**
- [ ] [Two Sum](https://leetcode.com/problems/two-sum/) (Easy) ⭐
  - Brute force: O(n²)
  - HashMap solution: O(n) ✅ Better!
- [ ] [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)
- [ ] [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/)

**✅ Checkpoint**: Solve Two Sum using HashMap in O(n)

**Estimated Time**: 4 hours (2h learning, 2h practice)

---

### Day 1, Evening: Simple Sorting (2 hours)

#### 📖 Topic 1.3: O(n²) Sorting Algorithms

**Why Learn "Bad" Algorithms?**
- Understand trade-offs
- Some are optimal for small n or nearly-sorted data
- Interview warmup

**Bubble Sort:**
```bash
cd Sort/BubbleSort/src
javac com/company/bubblesort/Main.java
java com.company.bubblesort.Main
```

**How it works**: Repeatedly swap adjacent elements if wrong order

**Pseudocode:**
```
for i = 0 to n-1:
    for j = 0 to n-i-1:
        if arr[j] > arr[j+1]:
            swap(arr[j], arr[j+1])
```

**Analysis:**
- Time: O(n²) - two nested loops
- Space: O(1) - in-place
- Stable: Yes (equal elements maintain order)

**Selection Sort:**
```bash
cd Sort/SelectionSort/src
javac com/company/selectionsort/Main.java
java com.company.selectionsort.Main
```

**Insertion Sort:**
```bash
cd Sort/InsertionSort/src
javac com/company/insertionsort/Main.java
java com.company.insertionsort.Main
```

**Best for**: Nearly sorted data (O(n) best case)

**Comparison:**

| Algorithm | Best | Average | Worst | Space | Stable | Use When |
|-----------|------|---------|-------|-------|--------|----------|
| Bubble | O(n) | O(n²) | O(n²) | O(1) | Yes | Never (learning only) |
| Selection | O(n²) | O(n²) | O(n²) | O(1) | No | Minimize swaps |
| Insertion | O(n) | O(n²) | O(n²) | O(1) | Yes | Small/nearly sorted |

**✅ Checkpoint**: Code bubble sort from memory

**Estimated Time**: 2 hours

---

### Day 2, Morning: Efficient Sorting (4 hours)

#### 📖 Topic 1.4: O(n log n) Sorting

**Merge Sort - Divide and Conquer:**
```bash
cd Sort/MergeSort/src
javac com/company/mergesort/Main.java
java com.company.mergesort.Main
```

**How it works:**
```
[38, 27, 43, 3]
    ↓ split
[38, 27] [43, 3]
    ↓ split
[38] [27] [43] [3]
    ↓ merge
[27, 38] [3, 43]
    ↓ merge
[3, 27, 38, 43]
```

**Analysis:**
- Time: O(n log n) always (guaranteed!)
- Space: O(n) - needs temporary array
- Stable: Yes
- Parallelizable: Yes (good for large data)

**Quick Sort:**
```bash
cd Sort/QuickSort/src
javac com/company/quicksort/Main.java
java com.company.quicksort.Main
```

**How it works:**
1. Pick pivot (e.g., last element)
2. Partition: smaller left, larger right
3. Recursively sort partitions

**Analysis:**
- Time: O(n log n) average, O(n²) worst (rare with good pivot selection)
- Space: O(log n) - recursion stack
- Stable: No (unless modified)
- In-place: Yes ✅
- Industry standard (used in Java's `Arrays.sort()` for primitives)

**When to Use:**

| Algorithm | Use When |
|-----------|----------|
| **Merge Sort** | Need stable sort, guaranteed O(n log n), have extra space |
| **Quick Sort** | Want in-place, average case is fine, primitives |
| **Insertion Sort** | n < 10, nearly sorted |
| **Tim Sort** | Hybrid (Python, Java objects) - best of both worlds |

**Production Note:**
- `Arrays.sort()` for primitives → Dual-Pivot QuickSort
- `Collections.sort()` for objects → TimSort (Merge + Insertion)

**Practice:**
- [ ] [Sort an Array](https://leetcode.com/problems/sort-an-array/)
- [ ] [Sort Colors](https://leetcode.com/problems/sort-colors/) (Dutch flag problem)
- [ ] [Merge Intervals](https://leetcode.com/problems/merge-intervals/)

**✅ Checkpoint**: Explain why QuickSort is O(n²) worst case

**Estimated Time**: 4 hours

---

### Day 2, Afternoon: Binary Search (3 hours)

#### 📖 Topic 1.5: The O(log n) Superpower

**Why Binary Search Matters:**
- Appears in 20% of interviews
- Foundation for many algorithms
- Demonstrates understanding of O(log n)

**Run the Code:**
```bash
cd binarySearch/src
javac com/company/binarysearch/Main.java
java com.company.binarysearch.Main
```

**The Template (Memorize This!):**
```java
int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;  // Avoid overflow!
        
        if (arr[mid] == target) {
            return mid;  // Found!
        } else if (arr[mid] < target) {
            left = mid + 1;  // Search right half
        } else {
            right = mid - 1;  // Search left half
        }
    }
    
    return -1;  // Not found
}
```

**Critical Details:**
1. **`mid = left + (right - left) / 2`** not `(left + right) / 2` (prevents overflow)
2. **`left <= right`** not `left < right` (check all elements)
3. **Array MUST be sorted** (otherwise use linear search)

**Variants:**
- Find first occurrence
- Find last occurrence
- Find insertion position
- Search in rotated array

**Practice:**
- [ ] [Binary Search](https://leetcode.com/problems/binary-search/) ⭐ Must solve
- [ ] [First Bad Version](https://leetcode.com/problems/first-bad-version/)
- [ ] [Search Insert Position](https://leetcode.com/problems/search-insert-position/)
- [ ] [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) 🔥

**✅ Checkpoint**: Solve Binary Search in <10 minutes

**Estimated Time**: 3 hours (1h learning, 2h practice)

---

## 🟡 LEVEL 2: Core Data Structures (Days 3-5)

**Goal**: Master linear data structures  
**Time**: 24-30 hours

---

### Day 3: LinkedLists (8 hours)

#### 📖 Topic 2.1: Singly LinkedList (4 hours)

**What is it?**
Nodes connected by "next" pointers. Unlike arrays, elements are scattered in memory.

**Structure:**
```
[5|●]→[2|●]→[8|●]→[1|null]
 ↑head              ↑tail
```

**Run the Code:**
```bash
cd LinkedList/LinkedList/src
javac com/company/linkedlist/Main.java
java com.company.linkedlist.Main
```

**Node Class:**
```java
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}
```

**Core Operations:**

| Operation | Array | LinkedList | Why Different? |
|-----------|-------|------------|----------------|
| Access by index | O(1) ✅ | O(n) ❌ | Must traverse from head |
| Search value | O(n) | O(n) | Must check each node |
| Insert at head | O(n) | O(1) ✅ | Just change head pointer |
| Insert at tail | O(1)* | O(n) or O(1)** | *If space, **If tail pointer |
| Insert after node | O(n) | O(1) ✅ | Change pointers |
| Delete node | O(n) | O(1) ✅ | If you have previous node |

**When to Use:**
- ✅ Frequent insert/delete at beginning
- ✅ Unknown size
- ✅ Implementing Stack/Queue
- ❌ Random access needed
- ❌ Memory overhead concern (each node = data + pointer)

**Java Collections:**
- `LinkedList` - Implements both List and Deque
- Backed by doubly-linked list

**Common Patterns:**

**1. Two Pointers (Fast & Slow):**
```java
// Detect cycle
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow == fast) return true;  // Cycle!
}
```

**2. Reverse LinkedList:**
```java
ListNode reverse(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

**Practice:**
- [ ] [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) ⭐ MUST MASTER
- [ ] [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/)
- [ ] [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/)
- [ ] [Remove Nth Node From End](https://leetcode.com/problems/remove-nth-node-from-end-of-list/)

**✅ Checkpoint**: Code reverse linkedlist in <15 min

---

#### 📖 Topic 2.2: Doubly LinkedList (2 hours)

**What's Different?**
```
null←[●|5|●]⇄[●|2|●]⇄[●|8|●]→null
```

**Run:**
```bash
cd LinkedList/DoublyLinkedList/src
javac com/company/doublylinkedlist/Main.java
java com.company.doublylinkedlist.Main
```

**Advantages:**
- ✅ Traverse backwards
- ✅ Delete node without previous reference

**Disadvantages:**
- ❌ 2x pointer overhead
- ❌ More complex

**When to Use:**
- Browser history
- LRU Cache
- Undo/Redo

**Practice:**
- [ ] [LRU Cache](https://leetcode.com/problems/lru-cache/) 🔥 Classic

---

### Day 4: Stacks & Queues (8 hours)

#### 📖 Topic 2.3: Stack - LIFO (3 hours)

**What is it?** Last In, First Out

**Run:**
```bash
cd Stacks/stacks/src
javac com/company/stacks/Main.java
java com.company.stacks.Main
```

**Operations:**
```java
Stack<Integer> stack = new Stack<>();
stack.push(5);      // [5]
stack.push(2);      // [5, 2]
stack.peek();       // 2 (look, don't remove)
stack.pop();        // 2 (remove and return) → [5]
```

**When to Use:**
- ✅ Matching parentheses/brackets
- ✅ Undo mechanism
- ✅ DFS (Depth-First Search)
- ✅ Function call stack simulation
- ✅ Expression evaluation

**Java Collections:**
- `Stack<E>` - Legacy class (avoid)
- `Deque<E>` - Use `ArrayDeque` instead ✅

**Practice:**
- [ ] [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) ⭐
- [ ] [Min Stack](https://leetcode.com/problems/min-stack/)
- [ ] [Daily Temperatures](https://leetcode.com/problems/daily-temperatures/) (Monotonic stack)

---

#### 📖 Topic 2.4: Queue - FIFO (3 hours)

**What is it?** First In, First Out

**Run:**
```bash
cd Queue/Queue/src
javac com/company/queue/Main.java
java com.company.queue.Main
```

**Operations:**
```java
Queue<Integer> queue = new LinkedList<>();
queue.offer(5);     // [5]
queue.offer(2);     // [5, 2]
queue.peek();       // 5
queue.poll();       // 5 → [2]
```

**When to Use:**
- ✅ BFS (Breadth-First Search)
- ✅ Task scheduling
- ✅ Request handling (web servers)

**Java Collections:**
- `Queue<E>` interface
- `LinkedList` - general purpose
- `ArrayDeque` - fastest ✅
- `PriorityQueue` - min-heap

**Practice:**
- [ ] [Implement Queue using Stacks](https://leetcode.com/problems/implement-queue-using-stacks/)
- [ ] [Design Circular Queue](https://leetcode.com/problems/design-circular-queue/)

---

### Day 5: Hash Tables (8 hours)

#### 📖 Topic 2.5: HashMap - O(1) Lookups (4 hours)

**What is it?** Key→Value mapping with fast access

**Run:**
```bash
cd Hashtable/hashtable/src
javac com/company/hashtable/Main.java
java com.company.hashtable.Main
```

**How it Works:**
1. Hash function: `key → hash code → index`
2. Store at buckets[index]
3. Handle collisions (chaining or probing)

**Operations:**
```java
HashMap<String, Integer> map = new HashMap<>();
map.put("Alice", 95);    // O(1)
map.get("Alice");        // O(1) average
map.containsKey("Bob");  // O(1) average
map.remove("Alice");     // O(1) average
```

**Collision Handling:**

**Chaining** (Java's approach):
```bash
cd Hashtable/HashtableChaining/src
javac com/company/hashtablechaining/Main.java
java com.company.hashtablechaining.Main
```

**Java 8 Improvement**: Treeification
- Bucket with >8 elements → Red-Black Tree
- O(n) → O(log n) worst case!

**When to Use:**
- ✅ O(1) lookups needed
- ✅ Counting frequency
- ✅ Caching
- ✅ Finding duplicates
- ❌ Ordered iteration (use TreeMap)

**Java Collections:**
- `HashMap<K,V>` - Fast, unsynchronized
- `Hashtable<K,V>` - Synchronized (legacy)
- `ConcurrentHashMap<K,V>` - Thread-safe, modern
- `LinkedHashMap<K,V>` - Maintains insertion order
- `TreeMap<K,V>` - Sorted by key (Red-Black tree)

**Practice:**
- [ ] [Two Sum](https://leetcode.com/problems/two-sum/) ⭐
- [ ] [Group Anagrams](https://leetcode.com/problems/group-anagrams/)
- [ ] [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/)

**✅ Checkpoint**: Solve Two Sum with HashMap

**Estimated Time for Level 2**: 24-30 hours

---

## 🟠 LEVEL 3: Advanced Structures (Days 6-8)

### Day 6-7: Trees (12 hours)

#### 📖 Topic 3.1: Binary Trees & BST (6 hours)

**Run:**
```bash
cd binarySearchTree/src
javac com/company/binarysearchtree/Main.java
java com.company.binarysearchtree.Main
```

**BST Property:** Left < Root < Right

**Traversals:**
- **Inorder** (L, Root, R): Sorted order! ⭐
- **Preorder** (Root, L, R): Copy tree
- **Postorder** (L, R, Root): Delete tree
- **Level-order** (BFS): Use Queue

**Practice:**
- [ ] [Validate BST](https://leetcode.com/problems/validate-binary-search-tree/) ⭐
- [ ] [Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/)
- [ ] [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/)

### Day 8: Heaps (6 hours)

#### 📖 Topic 3.2: Priority Queues

**Run:**
```bash
cd maxHeap/src
javac com/company/maxheap/Main.java
java com.company.maxheap.Main

cd PriorityQueue/src
javac com/company/priorityqueue/Main.java
java com.company.priorityqueue.Main
```

**When to Use:**
- ✅ Top K problems
- ✅ Merge K sorted lists
- ✅ Median finding

**Practice:**
- [ ] [Kth Largest Element](https://leetcode.com/problems/kth-largest-element-in-an-array/) ⭐
- [ ] [Merge K Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/)
- [ ] [Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/)

---

## 🔴 LEVEL 4: Interview Mastery (Days 9-10)

**Goal**: Combine concepts, master interview communication

---

## 🗺️ Pattern Recognition Guide (10 Essentials)

Master these patterns to solve 80% of interview problems:

### Pattern 1: Two Pointers
**When**: Sorted array, palindrome, pair sum
**Problems**: 3Sum, Container With Most Water

### Pattern 2: Sliding Window
**When**: Substring, subarray, continuous sequence
**Problems**: Longest Substring Without Repeating, Min Window Substring

### Pattern 3: Fast & Slow Pointers
**When**: Cycle detection, middle of list
**Problems**: Linked List Cycle, Happy Number

### Pattern 4: HashMap/HashSet
**When**: O(1) lookup, frequency counting
**Problems**: Two Sum, Group Anagrams

### Pattern 5: Stack
**When**: Matching brackets, monotonic problems
**Problems**: Valid Parentheses, Daily Temperatures

### Pattern 6: BFS
**When**: Shortest path, level-order, minimum steps
**Problems**: Binary Tree Level Order, Number of Islands

### Pattern 7: DFS/Backtracking
**When**: All combinations, path finding
**Problems**: Permutations, Word Search

### Pattern 8: Binary Search
**When**: Sorted data, search space reduction
**Problems**: Search in Rotated Array, Find Peak Element

### Pattern 9: Heap/Priority Queue
**When**: Top K, merge K, median
**Problems**: Kth Largest, Merge K Lists

### Pattern 10: Tree Traversal
**When**: Binary tree problems
**Problems**: Validate BST, Lowest Common Ancestor

---

## 🎤 Interview Framework (REACTO)

### R - Repeat
- Restate problem in your own words
- Clarify ambiguities
- Ask about constraints (size limits, edge cases)

### E - Examples
- Walk through 2-3 examples
- Include edge cases
- Verify understanding

### A - Approach
- Explain brute force first
- Discuss optimization
- State time/space complexity

### C - Code
- Write clean, compilable code
- Use meaningful variable names
- Add comments for complex logic

### T - Test
- Walk through code with example
- Check edge cases
- Find and fix bugs

### O - Optimize
- Discuss trade-offs
- Alternative solutions
- Space-time tradeoffs

---

## 👨‍💼 How Interviewers Grade You

### Scoring Rubric (Out of 4):

**Problem Solving (40%)**
- 4: Optimal solution independently
- 3: Optimal with hints
- 2: Working solution, suboptimal
- 1: Partial solution
- 0: No working solution

**Coding (30%)**
- 4: Clean, bug-free, efficient
- 3: Minor bugs, fixed quickly
- 2: Multiple bugs, needs help
- 1: Significant errors
- 0: Incomplete/doesn't compile

**Communication (20%)**
- 4: Clear explanation, collaborative
- 3: Adequate explanation
- 2: Unclear, doesn't explain
- 1: Silent coding
- 0: No communication

**Analysis (10%)**
- 4: Correct complexity, trade-offs discussed
- 3: Correct complexity
- 2: Approximate understanding
- 1: Incorrect analysis
- 0: No analysis

**Passing Score: 10/16** (Average of 2.5 per category)

---

## 💼 Advanced Interview Questions & Answers

[Content from previous advanced Q&A section - Red-Black vs AVL, HashMap treeification, etc.]

---

## 🏭 Production Engineering Notes

### Cache-Friendly Data Structures:
- **ArrayList > LinkedList** for iteration (cache locality)
- **Array of structs > Struct of arrays** for modern CPUs

### Thread-Safety:
- `ConcurrentHashMap` > `Collections.synchronizedMap()`
- `CopyOnWriteArrayList` for read-heavy workloads
- `BlockingQueue` for producer-consumer

### GC Impact:
- **LinkedList**: More objects → more GC pressure
- **ArrayList**: Continuous memory → better for GC
- **Object pools**: Reduce allocation for hot paths

### When to Use What (Production):

| Use Case | Data Structure | Why |
|----------|---------------|-----|
| Cache | `ConcurrentHashMap` | Thread-safe, fast |
| Task Queue | `BlockingQueue` | Handles backpressure |
| LRU Cache | `LinkedHashMap` (accessOrder=true) | Built-in |
| Leaderboard | `TreeMap` or Skip List | Sorted, range queries |
| Rate Limiting | `TreeMap` (timestamps) | Time-based cleanup |

---

## 📊 Study Plans

### 🔥 2-Day Intensive (16 hours)

**Day 1 (8h)**: Big O, Arrays, Sorting, Binary Search, LinkedLists, Stacks, Queues, Hash Tables  
**Day 2 (8h)**: Trees, Heaps, 20 must-know problems

**Focus**: Speed over depth. Cover essentials, practice top 20 LeetCode Easy/Medium.

---

### 📚 10-Day Comprehensive (60-80 hours)

Follow Levels 1-4 with deep practice. Target: 60+ problems solved.

---

## ✅ Success Checklist

Before your interview:
- [ ] Can explain Big O of every solution
- [ ] Can code LinkedList reversal in 10 min
- [ ] Can code Binary Search in 5 min
- [ ] Can solve Two Sum in 5 min
- [ ] Know when to use HashMap vs TreeMap
- [ ] Can implement basic Stack/Queue
- [ ] Understand BST properties
- [ ] Know DFS vs BFS
- [ ] Can identify 10+ patterns
- [ ] Practiced REACTO framework on 20+ problems

---

## 📚 Java Collections Cheat Sheet

| Interface | Implementation | Ordered? | Sorted? | Thread-Safe? | Null? | Time (avg) |
|-----------|---------------|----------|---------|--------------|-------|------------|
| List | ArrayList | Yes | No | No | Yes | O(1) get, O(n) add/remove |
| List | LinkedList | Yes | No | No | Yes | O(n) get, O(1) add/remove |
| List | Vector | Yes | No | Yes (sync) | Yes | O(1) get (slow) |
| Set | HashSet | No | No | No | One null | O(1) |
| Set | LinkedHashSet | Insertion | No | No | One null | O(1) |
| Set | TreeSet | No | Yes | No | No | O(log n) |
| Map | HashMap | No | No | No | One null key | O(1) |
| Map | LinkedHashMap | Insertion | No | No | One null key | O(1) |
| Map | TreeMap | No | By key | No | No | O(log n) |
| Map | ConcurrentHashMap | No | No | Yes | No | O(1) |
| Queue | ArrayDeque | FIFO | No | No | No | O(1) |
| Queue | PriorityQueue | No | Min-heap | No | No | O(log n) |

---

## 🤝 Contributing

Found errors? Better explanations? Submit a PR!

**Good luck! You've got this! 🚀**

Remember: **Consistency > Intensity**. Daily practice beats cramming.
