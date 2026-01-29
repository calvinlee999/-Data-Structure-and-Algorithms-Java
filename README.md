# 🚀 Data Structures and Algorithms - Java Interview Prep

**Comprehensive Interview Preparation Guide**

This repository contains comprehensive implementations of essential data structures and algorithms in Java, optimized for technical interview preparation.

## 📋 Table of Contents
- [Quick Start](#quick-start)
- [Study Plans](#study-plans)
  - [2-Day Intensive Plan](#2-day-intensive-plan)
  - [4-Day Comprehensive Plan](#4-day-comprehensive-plan)
- [Data Structures Implemented](#data-structures-implemented)
- [Algorithms Implemented](#algorithms-implemented)
- [Interview Pattern Guide](#interview-pattern-guide)
- [LeetCode Problem Mapping](#leetcode-problem-mapping)
- [Setup](#setup)
- [Resources](#resources)

## ⚡ Quick Start

```bash
# Clone the repository
git clone https://github.com/calvinlee999/-Data-Structure-and-Algorithms-Java.git
cd Data-Structure-and-Algorithms-Java

# Each topic is in its own folder with runnable examples
cd Sort/BubbleSort/src/com/company/bubblesort
javac Main.java
java Main
```

## 📅 Study Plans

Choose the plan that fits your timeline. Both plans cover essential concepts but the 4-day plan provides deeper practice and more problem-solving time.

---

## 🔥 2-Day Intensive Plan

**Target:** Quick review for experienced developers or last-minute preparation

### Day 1: Core Data Structures (8 hours)

#### Morning (4 hours)
1. **Arrays & Sorting (2 hours)**
   - Review: `Sort/Arrays/`, `Sort/BubbleSort/`, `Sort/InsertionSort/`, `Sort/SelectionSort/`
   - **Practice Problems:**
     - [Two Sum](https://leetcode.com/problems/two-sum/) (Easy)
     - [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) (Easy)
     - [Sort Colors](https://leetcode.com/problems/sort-colors/) (Medium)

2. **LinkedLists (2 hours)**
   - Review: `LinkedList/LinkedList/`, `LinkedList/DoublyLinkedList/`
   - **Practice Problems:**
     - [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) (Easy)
     - [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/) (Easy)
     - [Remove Nth Node From End](https://leetcode.com/problems/remove-nth-node-from-end-of-list/) (Medium)

#### Afternoon (4 hours)
3. **Stacks & Queues (2 hours)**
   - Review: `Stacks/stacks/`, `Queue/Queue/`
   - **Practice Problems:**
     - [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) (Easy)
     - [Implement Queue using Stacks](https://leetcode.com/problems/implement-queue-using-stacks/) (Easy)
     - [Min Stack](https://leetcode.com/problems/min-stack/) (Medium)

4. **Hash Tables (2 hours)**
   - Review: `Hashtable/hashtable/`, `Hashtable/HashtableChaining/`
   - **Practice Problems:**
     - [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/) (Easy)
     - [Group Anagrams](https://leetcode.com/problems/group-anagrams/) (Medium)
     - [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) (Medium)

### Day 2: Advanced Structures & Algorithms (8 hours)

#### Morning (4 hours)
5. **Trees (2 hours)**
   - Review: `binarySearchTree/`
   - **Practice Problems:**
     - [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/) (Easy)
     - [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) (Medium)
     - [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) (Medium)

6. **Heaps & Priority Queues (2 hours)**
   - Review: `maxHeap/`, `PriorityQueue/`
   - **Practice Problems:**
     - [Kth Largest Element](https://leetcode.com/problems/kth-largest-element-in-an-array/) (Medium)
     - [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) (Medium)
     - [Merge K Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/) (Hard)

#### Afternoon (4 hours)
7. **Advanced Sorting & Searching (2 hours)**
   - Review: `Sort/MergeSort/`, `Sort/QuickSort/`, `binarySearch/`
   - **Practice Problems:**
---

## 📚 4-Day Comprehensive Plan

**Target:** Thorough preparation with practice and mastery

### Day 1: Foundations - Arrays, Strings & Sorting (6-8 hours)

#### Morning Session (3-4 hours)
**1. Arrays & Two Pointers (2 hours)**
- Review: `Sort/Arrays/`
- **Concept Focus:** Array manipulation, two-pointer technique, in-place operations
- **Practice Problems:**
  - [Two Sum](https://leetcode.com/problems/two-sum/) (Easy) ⭐
  - [Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) (Easy)
  - [Container With Most Water](https://leetcode.com/problems/container-with-most-water/) (Medium)
  - [3Sum](https://leetcode.com/problems/3sum/) (Medium) ⭐
  - [Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/) (Medium)

**2. Strings & Sliding Window (1-2 hours)**
- **Concept Focus:** String manipulation, sliding window pattern
- **Practice Problems:**
  - [Valid Anagram](https://leetcode.com/problems/valid-anagram/) (Easy)
  - [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) (Medium) ⭐
  - [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/) (Hard)

#### Afternoon Session (3-4 hours)
**3. Sorting Algorithms Deep Dive (2 hours)**
- Review: `Sort/BubbleSort/`, `Sort/InsertionSort/`, `Sort/SelectionSort/`, `Sort/MergeSort/`, `Sort/QuickSort/`
- **Concept Focus:** Understanding time/space complexity trade-offs
- **Practice Problems:**
  - [Sort Colors](https://leetcode.com/problems/sort-colors/) (Medium) ⭐
  - [Merge Intervals](https://leetcode.com/problems/merge-intervals/) (Medium) ⭐
  - [Insert Interval](https://leetcode.com/problems/insert-interval/) (Medium)
  - [Largest Number](https://leetcode.com/problems/largest-number/) (Medium)

**4. Practice & Review (1-2 hours)**
- Implement one sorting algorithm from scratch
- Review Big O complexity for all sorting algorithms
- **Additional Problem:**
  - [Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/) (Medium)

### Day 2: LinkedLists, Stacks & Queues (6-8 hours)

#### Morning Session (3-4 hours)
**1. LinkedList Fundamentals (2 hours)**
- Review: `LinkedList/LinkedList/`, `LinkedList/DoublyLinkedList/`
- **Concept Focus:** Pointer manipulation, fast/slow pointer, reversal
- **Practice Problems:**
  - [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) (Easy) ⭐
  - [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/) (Easy) ⭐
  - [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/) (Easy)
  - [Remove Nth Node From End](https://leetcode.com/problems/remove-nth-node-from-end-of-list/) (Medium) ⭐
  - [Reorder List](https://leetcode.com/problems/reorder-list/) (Medium)

**2. Advanced LinkedList (1-2 hours)**
- **Practice Problems:**
  - [Add Two Numbers](https://leetcode.com/problems/add-two-numbers/) (Medium)
  - [Copy List with Random Pointer](https://leetcode.com/problems/copy-list-with-random-pointer/) (Medium)
  - [Reverse Nodes in k-Group](https://leetcode.com/problems/reverse-nodes-in-k-group/) (Hard)

#### Afternoon Session (3-4 hours)
**3. Stacks (1.5 hours)**
- Review: `Stacks/stacks/`, `Stacks/LinkedListStack/`
- **Concept Focus:** LIFO operations, monotonic stack
- **Practice Problems:**
  - [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) (Easy) ⭐
  - [Min Stack](https://leetcode.com/problems/min-stack/) (Medium) ⭐
  - [Evaluate Reverse Polish Notation](https://leetcode.com/problems/evaluate-reverse-polish-notation/) (Medium)
  - [Daily Temperatures](https://leetcode.com/problems/daily-temperatures/) (Medium)
  - [Largest Rectangle in Histogram](https://leetcode.com/problems/largest-rectangle-in-histogram/) (Hard)

**4. Queues (1.5 hours)**
- Review: `Queue/Queue/`
- **Concept Focus:** FIFO operations, deque, circular queue
- **Practice Problems:**
  - [Implement Queue using Stacks](https://leetcode.com/problems/implement-queue-using-stacks/) (Easy)
  - [Implement Stack using Queues](https://leetcode.com/problems/implement-stack-using-queues/) (Easy)
  - [Design Circular Queue](https://leetcode.com/problems/design-circular-queue/) (Medium)
  - [Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/) (Hard)

### Day 3: Trees, Hash Tables & Heaps (6-8 hours)

#### Morning Session (3-4 hours)
**1. Hash Tables & HashMaps (1.5 hours)**
- Review: `Hashtable/hashtable/`, `Hashtable/HashtableChaining/`
- **Concept Focus:** Hashing, collision handling, frequency counting
- **Practice Problems:**
  - [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/) (Easy)
  - [Two Sum](https://leetcode.com/problems/two-sum/) (Easy) ⭐ (revisit with hash approach)
  - [Group Anagrams](https://leetcode.com/problems/group-anagrams/) (Medium) ⭐
  - [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) (Medium) ⭐
  - [Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/) (Medium)

**2. Binary Trees - Traversal & Basic Operations (1.5-2 hours)**
- Review: `binarySearchTree/`
- **Concept Focus:** DFS (preorder, inorder, postorder), BFS, recursion
- **Practice Problems:**
  - [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/) (Easy) ⭐
  - [Same Tree](https://leetcode.com/problems/same-tree/) (Easy)
  - [Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/) (Easy)
  - [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) (Medium) ⭐
  - [Binary Tree Right Side View](https://leetcode.com/problems/binary-tree-right-side-view/) (Medium)

#### Afternoon Session (3-4 hours)
**3. Binary Search Trees (1.5 hours)**
- **Concept Focus:** BST properties, validation, search operations
- **Practice Problems:**
  - [Lowest Common Ancestor of BST](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/) (Easy)
  - [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) (Medium) ⭐
  - [Kth Smallest Element in BST](https://leetcode.com/problems/kth-smallest-element-in-a-bst/) (Medium)
  - [Construct Binary Tree from Preorder and Inorder](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) (Medium)

**4. Heaps & Priority Queues (1.5-2 hours)**
- Review: `maxHeap/`, `PriorityQueue/`
- **Concept Focus:** Heap operations, top K problems, median finding
- **Practice Problems:**
  - [Kth Largest Element in Array](https://leetcode.com/problems/kth-largest-element-in-an-array/) (Medium) ⭐
  - [Last Stone Weight](https://leetcode.com/problems/last-stone-weight/) (Easy)
  - [K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/) (Medium)
  - [Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/) (Hard)
  - [Merge K Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/) (Hard) ⭐

### Day 4: Advanced Topics & Mock Interviews (6-8 hours)

#### Morning Session (3-4 hours)
**1. Binary Search & Variants (1.5 hours)**
- Review: `binarySearch/`
- **Concept Focus:** Search space reduction, modified binary search
- **Practice Problems:**
  - [Binary Search](https://leetcode.com/problems/binary-search/) (Easy) ⭐
  - [First Bad Version](https://leetcode.com/problems/first-bad-version/) (Easy)
  - [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) (Medium) ⭐
  - [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/) (Medium)
  - [Time Based Key-Value Store](https://leetcode.com/problems/time-based-key-value-store/) (Medium)

**2. Advanced Algorithms (1.5-2 hours)**
- **Dynamic Programming Introduction:**
  - [Climbing Stairs](https://leetcode.com/problems/climbing-stairs/) (Easy)
  - [House Robber](https://leetcode.com/problems/house-robber/) (Medium)
  - [Coin Change](https://leetcode.com/problems/coin-change/) (Medium)
  
- **Backtracking Introduction:**
  - [Subsets](https://leetcode.com/problems/subsets/) (Medium)
  - [Permutations](https://leetcode.com/problems/permutations/) (Medium)
  - [Combination Sum](https://leetcode.com/problems/combination-sum/) (Medium)

#### Afternoon Session (3-4 hours)
**3. Mixed Practice - Common Interview Problems (1.5 hours)**
- **Practice Problems (solve as many as possible):**
  - [LRU Cache](https://leetcode.com/problems/lru-cache/) (Medium) ⭐
  - [Word Search](https://leetcode.com/problems/word-search/) (Medium)
  - [Number of Islands](https://leetcode.com/problems/number-of-islands/) (Medium)
  - [Course Schedule](https://leetcode.com/problems/course-schedule/) (Medium)
  - [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/) (Medium)

**4. Mock Interviews (1.5-2 hours)**
- **Round 1 (45 min):** Pick 1 medium problem you haven't solved
  - 5 min: Understand & clarify
  - 5 min: Discuss approach
  - 25 min: Code solution
  - 10 min: Test & optimize
  
- **Round 2 (45 min):** Pick 1 different medium problem
  - Follow same timing structure
  - Practice explaining your thought process aloud

**5. Final Review (30 min)**
- Review your notes from all 4 days
- List 3 problem patterns you struggled with
- Quick review of Big O complexities

---

     - [Binary Search](https://leetcode.com/problems/binary-search/) (Easy)
     - [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) (Medium)
     - [Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/) (Hard)

8. **Mock Interview Practice (2 hours)**
   - Solve 2 random medium problems under time constraint (45 min each)
   - Focus on explaining your thought process aloud

## 📚 Data Structures Implemented

| Data Structure | Location | Time Complexity | Space Complexity |
|---------------|----------|-----------------|------------------|
| **Array** | `Sort/Arrays/` | Access: O(1), Search: O(n) | O(n) |
| **LinkedList** | `LinkedList/LinkedList/` | Access: O(n), Insert: O(1) | O(n) |
| **DoublyLinkedList** | `LinkedList/DoublyLinkedList/` | Access: O(n), Insert: O(1) | O(n) |
| **Stack** | `Stacks/stacks/` | Push/Pop: O(1) | O(n) |
| **Queue** | `Queue/Queue/` | Enqueue/Dequeue: O(1) | O(n) |
| **Hash Table** | `Hashtable/hashtable/` | Insert/Search: O(1) avg | O(n) |
| **Binary Search Tree** | `binarySearchTree/` | Search: O(log n) avg | O(n) |
| **Max Heap** | `maxHeap/` | Insert: O(log n) | O(n) |
| **Priority Queue** | `PriorityQueue/` | Insert: O(log n) | O(n) |

## 🔧 Algorithms Implemented

### Sorting Algorithms
| Algorithm | Location | Time Complexity | Space Complexity | When to Use |
|-----------|----------|-----------------|------------------|-------------|
| **Bubble Sort** | `Sort/BubbleSort/` | O(n²) | O(1) | Small datasets, educational |
| **Selection Sort** | `Sort/SelectionSort/` | O(n²) | O(1) | Small datasets, minimal swaps |
| **Insertion Sort** | `Sort/InsertionSort/` | O(n²) | O(1) | Nearly sorted data |
| **Shell Sort** | `Sort/ShellSort/` | O(n log n) | O(1) | Medium datasets |
| **Merge Sort** | `Sort/MergeSort/` | O(n log n) | O(n) | Stable sort needed |
| **Quick Sort** | `Sort/QuickSort/` | O(n log n) avg | O(log n) | General purpose, fast |
| **Counting Sort** | `Sort/countingsort/` | O(n + k) | O(k) | Integer sorting, limited range |
| **Radix Sort** | `Sort/radixsort/` | O(d × n) | O(n + k) | Integer sorting |
| **Bucket Sort** | `Hashtable/bucketSort/` | O(n + k) | O(n) | Uniformly distributed data |

### Searching Algorithms
| Algorithm | Location | Time Complexity | Best For |
|-----------|----------|-----------------|----------|
| **Binary Search** | `binarySearch/` | O(log n) | Sorted arrays |
| **Linear Search** | Various | O(n) | Unsorted small datasets |

## 🎯 Interview Pattern Guide

### Pattern 1: Two Pointers
**When to use:** Problems involving arrays/strings with sorting or finding pairs
- **Example:** Two Sum, Remove Duplicates
- **Code location:** Check `LinkedList/` implementations

### Pattern 2: Sliding Window
**When to use:** Subarray/substring problems
- **Example:** Longest Substring Without Repeating Characters
- **Code location:** Can be applied to `Sort/Arrays/`

### Pattern 3: Fast & Slow Pointers
**When to use:** LinkedList cycle detection, finding middle
- **Example:** Linked List Cycle
- **Code location:** `LinkedList/LinkedList/`

### Pattern 4: Merge Intervals
**When to use:** Overlapping intervals
- **Example:** Merge Intervals
- **Related:** `Sort/MergeSort/`

### Pattern 5: In-place LinkedList Reversal
**When to use:** Reverse LinkedList problems
- **Example:** Reverse Linked List
- **Code location:** `LinkedList/`

### Pattern 6: Tree BFS/DFS
**When to use:** Tree traversal problems
- **Example:** Level Order Traversal
- **Code location:** `binarySearchTree/`

### Pattern 7: Top K Elements
**When to use:** Finding K largest/smallest elements
- **Example:** Top K Frequent Elements
- **Code location:** `maxHeap/`, `PriorityQueue/`

### Pattern 8: Binary Search Modified
**When to use:** Search in rotated/modified arrays
- **Example:** Search in Rotated Sorted Array
- **Code location:** `binarySearch/`

## 🎓 LeetCode Problem Mapping

### Priority Problems by Plan

#### 2-Day Plan: Must-Do Problems (20 problems)

**Easy (6 problems)**
1. [Two Sum](https://leetcode.com/problems/two-sum/) - Hash Table ⭐
2. [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) - Stack ⭐
3. [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/) - LinkedList ⭐
4. [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) - Array ⭐
5. [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) - LinkedList ⭐
6. [Binary Search](https://leetcode.com/problems/binary-search/) - Binary Search ⭐

**Medium (12 problems)**
1. [Group Anagrams](https://leetcode.com/problems/group-anagrams/) - Hash Table ⭐
2. [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) - Sliding Window ⭐
3. [3Sum](https://leetcode.com/problems/3sum/) - Two Pointers ⭐
4. [Remove Nth Node From End](https://leetcode.com/problems/remove-nth-node-from-end-of-list/) - LinkedList ⭐
5. [Sort Colors](https://leetcode.com/problems/sort-colors/) - Sorting ⭐
6. [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) - Heap ⭐
7. [Kth Largest Element](https://leetcode.com/problems/kth-largest-element-in-an-array/) - Heap ⭐
8. [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) - BFS ⭐
9. [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) - BST ⭐
10. [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) - Binary Search ⭐
11. [LRU Cache](https://leetcode.com/problems/lru-cache/) - Hash + LinkedList ⭐
12. [Min Stack](https://leetcode.com/problems/min-stack/) - Stack ⭐

**Hard (2 problems)**
1. [Merge K Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/) - Heap ⭐
2. [Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/) - Binary Search

---

#### 4-Day Plan: Comprehensive List (60+ problems)

**Arrays & Strings (12 problems)**
- Easy: Two Sum, Remove Duplicates from Sorted Array, Valid Anagram
- Medium: 3Sum, Container With Most Water, Product of Array Except Self, Longest Substring Without Repeating Characters, Merge Intervals, Insert Interval, Largest Number, Meeting Rooms II, Longest Palindromic Substring
- Hard: Minimum Window Substring

**LinkedLists (11 problems)**
- Easy: Reverse Linked List, Merge Two Sorted Lists, Linked List Cycle
- Medium: Remove Nth Node From End, Reorder List, Add Two Numbers, Copy List with Random Pointer
- Hard: Reverse Nodes in k-Group, Merge K Sorted Lists

**Stacks & Queues (9 problems)**
- Easy: Valid Parentheses, Implement Queue using Stacks, Implement Stack using Queues
- Medium: Min Stack, Evaluate Reverse Polish Notation, Daily Temperatures, Design Circular Queue
- Hard: Largest Rectangle in Histogram, Sliding Window Maximum

**Hash Tables (6 problems)**
- Easy: Contains Duplicate
- Medium: Two Sum (hash approach), Group Anagrams, Top K Frequent Elements, Subarray Sum Equals K
- Hard: LRU Cache

**Trees & BST (14 problems)**
- Easy: Maximum Depth of Binary Tree, Same Tree, Invert Binary Tree, Lowest Common Ancestor of BST
- Medium: Binary Tree Level Order Traversal, Binary Tree Right Side View, Validate Binary Search Tree, Kth Smallest Element in BST, Construct Binary Tree from Preorder and Inorder, Number of Islands, Course Schedule, Word Search
- Hard: None specific

**Heaps (5 problems)**
- Easy: Last Stone Weight
- Medium: Kth Largest Element in Array, K Closest Points to Origin
- Hard: Find Median from Data Stream, Merge K Sorted Lists

**Binary Search (5 problems)**
- Easy: Binary Search, First Bad Version
- Medium: Search in Rotated Sorted Array, Find Minimum in Rotated Sorted Array, Time Based Key-Value Store

**Advanced Topics (10 problems)**
- DP: Climbing Stairs (Easy), House Robber (Medium), Coin Change (Medium)
- Backtracking: Subsets (Medium), Permutations (Medium), Combination Sum (Medium)
- Mixed: Word Search (Medium), Number of Islands (Medium), Course Schedule (Medium), Longest Palindromic Substring (Medium)

### Problems by Difficulty

#### Easy (Complete at least 10-12)
1. [Two Sum](https://leetcode.com/problems/two-sum/) - Hash Table
2. [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) - Stack
3. [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/) - LinkedList
4. [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) - Array
5. [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) - LinkedList
6. [Binary Search](https://leetcode.com/problems/binary-search/) - Binary Search
7. [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/) - Tree ⭐
8. [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/) - Hash Table ⭐
9. [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/) - LinkedList
10. [Valid Anagram](https://leetcode.com/problems/valid-anagram/) - String
11. [Climbing Stairs](https://leetcode.com/problems/climbing-stairs/) - DP
12. [Same Tree](https://leetcode.com/problems/same-tree/) - Tree
13. [Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/) - Tree
14. [First Bad Version](https://leetcode.com/problems/first-bad-version/) - Binary Search
15. [Last Stone Weight](https://leetcode.com/problems/last-stone-weight/) - Heap

#### Medium (Complete at least 20-25)
1. [Group Anagrams](https://leetcode.com/problems/group-anagrams/) - Hash Table
2. [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) - Sliding Window
3. [3Sum](https://leetcode.com/problems/3sum/) - Two Pointers ⭐
11. [LRU Cache](https://leetcode.com/problems/lru-cache/) - Hash + LinkedList ⭐
12. [Min Stack](https://leetcode.com/problems/min-stack/) - Stack ⭐
13. [Container With Most Water](https://leetcode.com/problems/container-with-most-water/) - Two Pointers
14. [Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/) - Array
15. [Merge Intervals](https://leetcode.com/problems/merge-intervals/) - Sorting ⭐
16. [Insert Interval](https://leetcode.com/problems/insert-interval/) - Array
17. [Reorder List](https://leetcode.com/problems/reorder-list/) - LinkedList
18. [Add Two Numbers](https://leetcode.com/problems/add-two-numbers/) - LinkedList
5. [Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/) - Heap
6. [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/) - Sliding Window
7. [Largest Rectangle in Histogram](https://leetcode.com/problems/largest-rectangle-in-histogram/) - Stack
8. [Reverse Nodes in k-Group](https://leetcode.com/problems/reverse-nodes-in-k-group/) - LinkedList

### Pattern-Based Problem Selection

Use this guide to practice specific patterns:

| Pattern | Easy | Medium | Hard |
|---------|------|--------|------|
| **Two Pointers** | Two Sum, Valid Palindrome | 3Sum, Container With Most Water | Trapping Rain Water |
| **Sliding Window** | - | Longest Substring, Minimum Window | - |
| **Fast & Slow Pointers** | Linked List Cycle | Find Duplicate Number | - |
| **Merge Intervals** | - | Merge Intervals, Insert Interval | - |
| **LinkedList Reversal** | Reverse Linked List | Reorder List, Reverse in k-Group | - |
| **Tree BFS** | Max Depth, Level Order | Binary Tree Right Side View | - |
| **Tree DFS** | Invert Tree, Same Tree | Validate BST, Path Sum II | - |
| **Top K Elements** | Kth Largest | Top K Frequent, K Closest Points | Find Median Stream |
| **Binary Search** | Binary Search | Search Rotated Array | Median Two Sorted |
| **Hash Table** | Contains Duplicate | Group Anagrams, LRU Cache | - |
| **Stack** | Valid Parentheses | Min Stack, Daily Temperatures | Largest Rectangle |
| **Heap** | Last Stone Weight | Kth Largest, Merge K Lists | Find Median Stream |

---
19. [Evaluate Reverse Polish Notation](https://leetcode.com/problems/evaluate-reverse-polish-notation/) - Stack
20. [Daily Temperatures](https://leetcode.com/problems/daily-temperatures/) - Stack
21. [Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/) - Hash
22. [K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/) - Heap
23. [House Robber](https://leetcode.com/problems/house-robber/) - DP
24. [Coin Change](https://leetcode.com/problems/coin-change/) - DP
25. [Subsets](https://leetcode.com/problems/subsets/) - Backtracking
26. [Permutations](https://leetcode.com/problems/permutations/) - Backtracking
27. [Combination Sum](https://leetcode.com/problems/combination-sum/) - Backtracking
28. [Word Search](https://leetcode.com/problems/word-search/) - DFS
29. [Number of Islands](https://leetcode.com/problems/number-of-islands/) - DFS/BFS
30. [Course Schedule](https://leetcode.com/problems/course-schedule/) - Graph

#### Hard (Understand at least 4-5eetcode.com/problems/kth-largest-element-in-an-array/) - Heap/QuickSelect
8. [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) - BFS
9. [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) - BST
10. [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) - Binary Search
11. [LRU Cache](https://leetcode.com/problems/lru-cache/) - Hash + LinkedList
12. [Min Stack](https://leetcode.com/problems/min-stack/) - Stack

#### Hard (Understand at least 2-3)
1. [Merge K Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/) - Heap
2. [Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/) - Binary Search
3. [Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/) - Two Pointers
4. [Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/) - Deque

## 🛠️ Setup

### Prerequisites
- Java JDK 17 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Progress Tracking

Create a tracking sheet to monitor your progress:

| Day | Topics Covered | Problems Solved | Time Spent | Confidence (1-10) |
|-----|----------------|-----------------|------------|-------------------|
| 1   | Arrays, Sorting | 0/15 | 0h | - |
- [LeetCode Explore - Learn](https://leetcode.com/explore/learn/)
- [LeetCode Java Tag](https://leetcode.com/problemset/all/?topicSlugs=array&difficulty=EASY&difficulty=MEDIUM&page=1)
- [Grind 75 - Curated List](https://www.techinterviewhandbook.org/grind75)
- [Java Algorithm Problems](https://leetcode.com/tag/array/)

### Java-Specific Resources
- [Java Collections Framework](https://docs.oracle.com/javase/8/docs/technotes/guides/collections/overview.html)
- [Java Stream API Guide](https://www.baeldung.com/java-streams)
- [Effective Java by Joshua Bloch](https://www.oreilly.com/library/view/effective-java/9780134686097/)

### Video Resources
- [NeetCode YouTube Channel](https://www.youtube.com/@NeetCode) - Problem walkthroughs
- [Back To Back SWE](https://www.youtube.com/@BackToBackSWE) - Detailed explanations
- [Abdul Bari Algorithms](https://www.youtube.com/@abdul_bari) - Theory and concepts
| 2   | LinkedList, Stack, Queue | 0/20 | 0h | - |
| 3   | Trees, Hash, Heaps | 0/25 | 0h | - |
| 4   | Advanced, Mock | 0/10 | 0h | - |

**Recommended Daily Schedule (4-Day Plan):**
- Morning: 9:00 AM - 1:00 PM (4 hours study)
- Break: 1:00 PM - 2:00 PM
- Afternoon: 2:00 PM - 6:00 PM (4 hours practice)
- Review: 8:00 PM - 9:00 PM (1 hour review notes)

### Running Examples
```bash
# Navigate to any implementation
cd Sort/QuickSort/src/com/company/quicksort

# Compile
javac Main.java

# Run
java Main
```

## 💡 Interview Tips

### Before the Interview
- [ ] Review Big O notation for all implementations
- [ ] Practice explaining your thought process aloud
- [ ] Prepare 2-3 questions to ask the interviewer
- [ ] Test your setup (mic, camera, IDE)

### During the Interview
1. **Clarify the problem** - Ask about edge cases, constraints, input size
2. **Think aloud** - Explain your approach before coding
3. **Start simple** - Brute force first, then optimize
4. **Test your code** - Walk through examples
5. **Analyze complexity** - State time and space complexity

### Common Pitfalls to Avoid
- ❌ Starting to code immediately without a plan
- ❌ Not asking clarifying questions
- ❌ Forgetting edge cases (null, empty, single element)
- ❌ Not testing your solution
- ❌ Poor variable naming

## 🎯 Interview Day Checklist

### 1 Hour Before Interview
- [ ] Review one medium problem you solved well
- [ ] Quick review of Big O cheatsheet
- [ ] Have pen and paper ready
- [ ] Close unnecessary applications
- [ ] Have water nearby

### Common Interview Questions to Expect
1. **Array/String Manipulation** (80% chance)
2. **LinkedList Operations** (60% chance)
3. **Tree Traversal** (50% chance)
4. **Hash Table Application** (70% chance)
5. **Sorting/Searching** (40% chance)

## 📖 Resources

### Additional Resources
- [LeetCode Patterns](https://leetcode.com/discuss/career/448285/List-of-questions-sorted-by-common-patterns)
- [Big O Cheat Sheet](https://www.bigocheatsheet.com/)
- [Visualgo - Algorithm Visualizations](https://visualgo.net/)
- [NeetCode - Curated Problem List](https://neetcode.io/)

## 🤝 Contributing

Feel free to add more implementations or improve existing ones via pull requests.

## 📝 License

This project is open source and available for educational purposes.

---

**Good luck with your interview! 🚀**

*Remember: The goal isn't to memorize solutions, but to understand patterns and problem-solving approaches.*
