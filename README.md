# 🚀 Data Structures and Algorithms - Java Interview Prep

**2-Day Intensive Interview Preparation Guide**

This repository contains comprehensive implementations of essential data structures and algorithms in Java, optimized for technical interview preparation.

## 📋 Table of Contents
- [Quick Start](#quick-start)
- [2-Day Study Plan](#2-day-study-plan)
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

## 📅 2-Day Study Plan

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

### Must-Do Problems (Day 1 & 2 Combined)

#### Easy (Complete at least 6)
1. [Two Sum](https://leetcode.com/problems/two-sum/) - Hash Table
2. [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) - Stack
3. [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/) - LinkedList
4. [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) - Array
5. [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) - LinkedList
6. [Binary Search](https://leetcode.com/problems/binary-search/) - Binary Search
7. [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/) - Tree
8. [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/) - Hash Table

#### Medium (Complete at least 8-10)
1. [Group Anagrams](https://leetcode.com/problems/group-anagrams/) - Hash Table
2. [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) - Sliding Window
3. [3Sum](https://leetcode.com/problems/3sum/) - Two Pointers
4. [Remove Nth Node From End](https://leetcode.com/problems/remove-nth-node-from-end-of-list/) - LinkedList
5. [Sort Colors](https://leetcode.com/problems/sort-colors/) - Sorting
6. [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) - Heap
7. [Kth Largest Element](https://leetcode.com/problems/kth-largest-element-in-an-array/) - Heap/QuickSelect
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
