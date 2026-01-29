# 🚀 Data Structures and Algorithms - Java Interview Prep

**Comprehensive Interview Preparation Guide**

This repository contains comprehensive implementations of essential data structures and algorithms in Java, optimized for technical interview preparation.

## 🎓 New to Data Structures? Start Here!

### What Are Data Structures?

Think of data structures as **containers that organize information** in your computer, just like how you organize things in real life:

- **Array** = A row of lockers 🗄️ (numbered 0, 1, 2, 3...) where each locker holds one item
- **LinkedList** = A treasure hunt 🗺️ where each clue points to the next location
- **Stack** = A stack of plates 🍽️ (you can only add/remove from the top)
- **Queue** = A line at a movie theater 🎬 (first person in line gets served first)
- **Hash Table** = A dictionary 📖 (look up words super fast)
- **Tree** = A family tree 👨‍👩‍👧‍👦 (parents connected to children)
- **Heap** = A tournament bracket 🏆 (always know who's #1)

### What Are Algorithms?

Algorithms are **step-by-step instructions** to solve a problem, like a recipe 👨‍🍳:

- **Sorting** = Organizing your cards from smallest to largest
- **Searching** = Finding a specific book on a bookshelf
- **Traversing** = Visiting every house in your neighborhood

### Why Learn This?

1. **Job Interviews** - Most tech companies ask these questions
2. **Better Code** - Write programs that run faster and use less memory
3. **Problem Solving** - Learn to think like a programmer

### Before You Begin - Prerequisites

**What You Need to Know:**
- ✅ Basic Java (variables, if/else, loops, functions)
- ✅ How to compile and run Java programs
- ✅ Basic math (adding, multiplying, comparing numbers)

**What You'll Learn:**
- 📊 How to organize data efficiently
- 🔍 How to find things quickly
- ⚡ How to make your programs faster
- 🧠 How to solve coding interview problems

---

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

### Step-by-Step Setup (For Beginners)

**Step 1: Download the Code**
```bash
# This downloads all the code to your computer
git clone https://github.com/calvinlee999/-Data-Structure-and-Algorithms-Java.git

# Go into the folder
cd Data-Structure-and-Algorithms-Java
```

**Step 2: Try Your First Example**
```bash
# Go to the BubbleSort example
cd Sort/BubbleSort/src/com/company/bubblesort

# Compile the Java code (turn it into a program the computer can run)
javac Main.java

# Run the program
java Main
```

**Step 3: What You'll See**
The program will show you how Bubble Sort works - it sorts numbers from smallest to largest!

### Understanding the Folder Structure

```
📁 Data-Structure-and-Algorithms-Java/
├── 📁 Sort/                    ← All sorting algorithms
│   ├── 📁 BubbleSort/          ← Simplest sorting (good to start!)
│   ├── 📁 InsertionSort/       ← Like sorting cards in your hand
│   └── 📁 QuickSort/           ← Very fast sorting
├── 📁 LinkedList/              ← Like a chain of connected items
├── 📁 Stacks/                  ← Like a stack of books
├── 📁 Queue/                   ← Like waiting in line
├── 📁 Hashtable/               ← Find things super fast
└── 📁 binarySearchTree/        ← Organized like a family tree
```

**Each folder has:**
- `Main.java` - The example program you can run
- Code that shows how the data structure works
- Comments explaining what each line does

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

## 📚 Data Structures Explained (Simple Version)

### What Each Data Structure Does

#### 🗄️ Array
**What it is:** A list of items stored side-by-side in memory (like lockers numbered 0, 1, 2, 3...)

**Real-life example:** Your class seating chart - each seat has a number

**When to use it:**
- When you know how many items you need
- When you need to access items by position (like "give me the 5th item")

**Example:**
```java
int[] scores = {85, 92, 78, 95, 88}; // Test scores
System.out.println(scores[0]); // Prints 85 (first score)
```

**Speed:**
- ✅ Super fast to access (if you know the position): O(1)
- ❌ Slow to search (need to check each item): O(n)

**Where to find it:** `Sort/Arrays/`

---

#### 🔗 LinkedList
**What it is:** Items connected like a chain, where each item points to the next one

**Real-life example:** A scavenger hunt where each clue tells you where the next clue is

**When to use it:**
- When you don't know how many items you'll have
- When you need to add/remove items from the middle a lot

**Example:**
```java
// Item 1 → Item 2 → Item 3 → null
// Each arrow is a "pointer" to the next item
```

**Speed:**
- ✅ Fast to add/remove from beginning: O(1)
- ❌ Slow to find things (must follow the chain): O(n)

**Where to find it:** `LinkedList/LinkedList/`

---

#### 📚 Stack
**What it is:** A pile where you can only add or remove from the top (Last In, First Out - LIFO)

**Real-life example:** A stack of plates - you always take from the top

**When to use it:**
- "Undo" button (undo the last thing you did)
- Going back in your web browser
- Checking if parentheses match: `((()))`

**Example:**
```java
Stack<String> plates = new Stack<>();
plates.push("Plate 1");  // Add to top
plates.push("Plate 2");  // Add to top
plates.pop();            // Remove "Plate 2" (the top one)
```

**Speed:**
- ✅ Super fast to add/remove from top: O(1)

**Where to find it:** `Stacks/stacks/`

---

#### 🎬 Queue
**What it is:** A line where first person in is first person out (First In, First Out - FIFO)

**Real-life example:** Waiting in line at lunch - whoever came first gets served first

**When to use it:**
- Print queue (documents print in order)
- Customer service line
- Video game turn order

**Example:**
```java
Queue<String> line = new LinkedList<>();
line.add("Person 1");    // Join the line
line.add("Person 2");    // Join the line
line.remove();           // "Person 1" leaves (was first)
```

**Speed:**
- ✅ Fast to add to back and remove from front: O(1)

**Where to find it:** `Queue/Queue/`

---

#### 📖 Hash Table (or HashMap)
**What it is:** Like a dictionary - you can look things up super fast using a "key"

**Real-life example:** A phone book - look up someone's name (key) to get their number (value)

**When to use it:**
- When you need to find things super fast
- Counting how many times something appears
- Storing user settings (username → password)

**Example:**
```java
HashMap<String, Integer> grades = new HashMap<>();
grades.put("Alice", 95);   // Store Alice's grade
grades.put("Bob", 87);     // Store Bob's grade
int aliceGrade = grades.get("Alice"); // Get Alice's grade instantly!
```

**Speed:**
- ✅ Super fast to add, find, and remove: O(1) on average

**Where to find it:** `Hashtable/hashtable/`

---

#### 🌳 Binary Search Tree (BST)
**What it is:** Data organized like a family tree, where left children are smaller and right children are larger

**Real-life example:** A family tree, or a tournament bracket

**When to use it:**
- When you need things sorted
- When you need fast searching
- Range queries (find all numbers between 10 and 20)

**Example:**
```
        50
       /  \
     30    70
    /  \   /  \
  20  40 60  80
  
(Left side < 50, Right side > 50)
```

**Speed:**
- ✅ Pretty fast to search, add, remove: O(log n) on average
- ❌ Can be slow if tree gets unbalanced: O(n) worst case

**Where to find it:** `binarySearchTree/`

---

#### 🏆 Heap (Priority Queue)
**What it is:** A special tree where the biggest (or smallest) item is always at the top

**Real-life example:** A tournament bracket - the winner is always at the top

**When to use it:**
- Finding the biggest or smallest item quickly
- Sorting items
- Hospital emergency room (most urgent patient first)

**Example:**
```
Max Heap:
        90
       /  \
     80    70
    /  \
  50   60

(90 is biggest and always on top!)
```

**Speed:**
- ✅ Fast to get the max/min: O(1)
- ✅ Fast to add/remove: O(log n)

**Where to find it:** `maxHeap/`, `PriorityQueue/`

---

### Quick Reference Table

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

---

## 🕐 Big O Notation - The Complete Beginner's Guide

### What is Big O? (The Simple Answer)

Big O is like a **speedometer for your code** 🚗💨. It tells you:
- **How slow will my program get** when I give it more data?
- **Should I use this algorithm** for 10 items? 1,000 items? 1,000,000 items?

**Think of it this way:** If you're organizing 5 books, any method works. But if you're organizing 5,000 books, you need a smart method!

---

### The Big O Scale (From Fastest to Slowest)

```
🚀 O(1)        ← SUPER FAST! Always same speed
😊 O(log n)    ← Very fast! Barely slows down
📈 O(n)        ← Pretty good. Doubles when data doubles
⚡ O(n log n)  ← Good for sorting
😰 O(n²)       ← Gets slow with lots of data
💀 O(2^n)      ← SUPER SLOW! Avoid if possible!
```

**The Golden Rule:** Lower is better! Pick algorithms with lower Big O when possible.

---

### 🚀 O(1) - Constant Time: "The Flash"

**What it means:** Always takes the same amount of time, no matter how much data you have!

**Real-life examples:**
- Opening your locker (always takes the same time whether school has 100 or 1,000 students)
- Turning on a light switch
- Looking at the first item in a list

**Code examples:**

```java
// Example 1: Get first item from array
int[] numbers = {10, 20, 30, 40, 50};
int first = numbers[0];  // ← O(1) - Instant!

// Example 2: Add to end of ArrayList (usually)
ArrayList<Integer> list = new ArrayList<>();
list.add(100);  // ← O(1) - Instant!

// Example 3: Check HashMap
HashMap<String, Integer> ages = new HashMap<>();
ages.put("Alice", 15);
int aliceAge = ages.get("Alice");  // ← O(1) - Instant lookup!
```

**Visual - Array Access:**
```
Array: [10, 20, 30, 40, 50]
Index:   0   1   2   3   4

Get numbers[3] → Go directly to position 3 → 40 ✅
(Doesn't matter if array has 5 or 5,000 items!)
```

**Why it's O(1):**
- Computer knows exactly where to look
- Like having GPS coordinates - go straight there!

**Where you see it:**
- Array access: `arr[5]`
- Stack push/pop
- Queue enqueue/dequeue
- HashMap get/put

---

### 😊 O(log n) - Logarithmic Time: "The Dictionary Search"

**What it means:** Each step cuts the problem in HALF! Gets slower as data grows, but very slowly.

**Real-life examples:**
- Finding a word in a dictionary (flip to middle, then middle of that half, etc.)
- Guessing a number 1-100 (use binary search: too high? try lower half)
- Phone book search

**The Magic of Log:**
- 10 items → ~3 steps
- 100 items → ~7 steps  
- 1,000 items → ~10 steps
- 1,000,000 items → ~20 steps! 🤯

**Code example - Binary Search:**

```java
// Find 7 in sorted array [1, 2, 3, 4, 5, 6, 7, 8, 9]
public int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {
        int mid = (left + right) / 2;  // Check middle
        
        if (arr[mid] == target) return mid;  // Found it! ✅
        if (arr[mid] < target) left = mid + 1;  // Go right half
        else right = mid - 1;  // Go left half
    }
    return -1;  // Not found
}
```

**Visual - Binary Search:**
```
Find 7 in: [1, 2, 3, 4, 5, 6, 7, 8, 9]

Step 1: Check middle (5)
        [1, 2, 3, 4, 5] | [6, 7, 8, 9]
        7 > 5, so go right half →

Step 2: Check middle of right half (7)
        [6, 7, 8, 9]
        Found 7! ✅

Only 2 steps for 9 items!
```

**Where you see it:**
- Binary Search (on sorted arrays)
- Binary Search Trees (balanced)
- Finding elements in heaps
- Many "divide and conquer" algorithms

**Code location:** `binarySearch/`

---

### 📈 O(n) - Linear Time: "The Line Walker"

**What it means:** Time grows proportionally with data. 2x data = 2x time.

**Real-life examples:**
- Reading every name in a class roster
- Counting all the money in your piggy bank
- High-fiving everyone in a line

**Code examples:**

```java
// Example 1: Find max in array
public int findMax(int[] numbers) {
    int max = numbers[0];
    for (int i = 1; i < numbers.length; i++) {  // Visit each item once
        if (numbers[i] > max) {
            max = numbers[i];
        }
    }
    return max;
}
// 10 items = 10 checks
// 100 items = 100 checks
// 1,000 items = 1,000 checks

// Example 2: Print all items
public void printAll(String[] names) {
    for (String name : names) {  // O(n) - visit each once
        System.out.println(name);
    }
}

// Example 3: Sum all numbers
public int sum(int[] nums) {
    int total = 0;
    for (int num : nums) {  // O(n) - go through each
        total += num;
    }
    return total;
}
```

**Visual:**
```
Array: [3, 1, 4, 1, 5, 9, 2, 6]
        ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓
Check: 1st 2nd 3rd 4th 5th 6th 7th 8th

8 items = 8 steps
100 items = 100 steps
```

**Growth chart:**
```
Items  | Steps
-------|-------
10     | 10
100    | 100
1,000  | 1,000
10,000 | 10,000
```

**Where you see it:**
- Linear Search
- Traversing an array/list
- Finding sum/average
- LinkedList traversal

**Code locations:** Many places in `Sort/`, `LinkedList/`

---

### ⚡ O(n log n) - Linearithmic Time: "The Smart Sorter"

**What it means:** Better than O(n²) but slower than O(n). The "sweet spot" for sorting!

**Real-life example:**
- Organizing a deck of cards using Merge Sort
- Tournament brackets (sort by skill, then pair them up)

**Why it's called "n log n":**
- Divide data into halves (log n)
- Process each piece (n)
- Combine: n × log n

**Code example - Merge Sort:**

```java
public void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = (left + right) / 2;
        
        mergeSort(arr, left, mid);      // Sort left half
        mergeSort(arr, mid + 1, right); // Sort right half
        merge(arr, left, mid, right);   // Merge them
    }
}
// Dividing: log n steps (split in half each time)
// Merging: n work (look at each item)
// Total: O(n log n)
```

**Visual - Merge Sort:**
```
[5, 2, 8, 1, 9, 3]
       ↓ Split
[5, 2, 8] [1, 9, 3]
    ↓ Split more
[5] [2, 8] [1] [9, 3]
    ↓ Split more
[5] [2] [8] [1] [9] [3]
    ↓ Merge (compare and combine)
[2, 5, 8] [1, 3, 9]
    ↓ Merge
[1, 2, 3, 5, 8, 9] ✅
```

**Growth chart:**
```
Items  | Steps (approx)
-------|----------------
10     | 33
100    | 664
1,000  | 9,966
10,000 | 132,877
```

**Where you see it:**
- Merge Sort
- Quick Sort (average case)
- Heap Sort
- Many efficient sorting algorithms

**Code locations:** `Sort/MergeSort/`, `Sort/QuickSort/`

---

### 😰 O(n²) - Quadratic Time: "The Nested Loop"

**What it means:** Time grows FAST! 2x data = 4x time. 10x data = 100x time!

**Real-life examples:**
- Comparing every person with every other person (handshakes at a party)
- Checking every seat pair in a classroom

**Code examples:**

```java
// Example 1: Bubble Sort
public void bubbleSort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {        // Outer loop: n times
        for (int j = 0; j < arr.length - 1; j++) { // Inner loop: n times
            if (arr[j] > arr[j + 1]) {
                // Swap
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}
// n × n = n² operations!

// Example 2: Find duplicates (naive way)
public boolean hasDuplicate(int[] arr) {
    for (int i = 0; i < arr.length; i++) {        // Outer: n
        for (int j = i + 1; j < arr.length; j++) { // Inner: n
            if (arr[i] == arr[j]) return true;
        }
    }
    return false;
}

// Example 3: Print all pairs
public void printPairs(int[] arr) {
    for (int i = 0; i < arr.length; i++) {        // n times
        for (int j = 0; j < arr.length; j++) {     // n times
            System.out.println(arr[i] + ", " + arr[j]);
        }
    }
}
// Total: n × n = n² prints
```

**Visual - Nested Loops:**
```
Array: [1, 2, 3]

Compare:
1 with 1, 1 with 2, 1 with 3
2 with 1, 2 with 2, 2 with 3
3 with 1, 3 with 2, 3 with 3

3 items = 3 × 3 = 9 comparisons
10 items = 10 × 10 = 100 comparisons!
```

**Growth chart (Notice how FAST it grows!):**
```
Items  | Steps
-------|--------
10     | 100
100    | 10,000
1,000  | 1,000,000! 😱
10,000 | 100,000,000! 💀
```

**Where you see it:**
- Bubble Sort
- Insertion Sort
- Selection Sort
- Nested loops comparing all pairs

**Code locations:** `Sort/BubbleSort/`, `Sort/InsertionSort/`, `Sort/SelectionSort/`

**Warning:** Use only for small datasets (< 1,000 items)!

---

### 💀 O(2^n) - Exponential Time: "The Nightmare"

**What it means:** DOUBLES for each new item! Extremely slow!

**Real-life example:**
- Trying every possible password combination
- Chess: considering every possible move, then every response, then...

**Growth chart (See why it's terrible?):**
```
Items | Steps
------|--------
5     | 32
10    | 1,024
20    | 1,048,576
30    | 1,073,741,824! (over 1 BILLION!)
```

**Code example - Fibonacci (bad way):**
```java
public int fibonacci(int n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);  // O(2^n) - Very slow!
}
```

**Where you see it:**
- Recursive Fibonacci (without memoization)
- Generating all subsets
- Brute force solutions

**Avoid this when possible!** Usually there's a better way.

---

## 📊 Big O Comparison Chart

### Visual Speed Comparison

```
Number of items (n) →

Speed ↓     | 10    | 100   | 1,000  | 10,000
------------|-------|-------|--------|--------
O(1)        | 1     | 1     | 1      | 1      🚀
O(log n)    | 3     | 7     | 10     | 13     😊
O(n)        | 10    | 100   | 1,000  | 10,000 📈
O(n log n)  | 33    | 664   | 9,966  | 132K   ⚡
O(n²)       | 100   | 10K   | 1M     | 100M   😰
O(2^n)      | 1K    | ...   | ...    | ...    💀
```

### Real-World Time Example

Let's say each operation takes 1 microsecond (1 millionth of a second):

**For 1,000 items:**
- O(1): **0.001 ms** - Instant! ✅
- O(log n): **0.01 ms** - Still instant! ✅
- O(n): **1 ms** - Very fast! ✅
- O(n log n): **10 ms** - Fast! ✅
- O(n²): **1 second** - Noticeable ⚠️
- O(2^n): **Never finishes** - Don't even try! ❌

**For 1,000,000 items:**
- O(1): **0.001 ms** - Instant! ✅
- O(log n): **0.02 ms** - Still instant! ✅
- O(n): **1 second** - Fine ✅
- O(n log n): **20 seconds** - Okay ✅
- O(n²): **11.5 DAYS** - Nope! ❌
- O(2^n): **Heat death of universe** - 💀

---

## 🎯 How to Calculate Big O (Simple Rules)

### Rule 1: Drop Constants

```java
// This:
for (int i = 0; i < n; i++) {
    System.out.println(i);
}
// Is still O(n), NOT O(2n) or O(n + 5)
```

**Why?** Big O cares about growth, not exact numbers.

### Rule 2: Drop Smaller Terms

```java
// This code:
for (int i = 0; i < n; i++) {      // O(n)
    System.out.println(i);
}
for (int i = 0; i < n; i++) {      // O(n)
    for (int j = 0; j < n; j++) {   // O(n²)
        System.out.println(i + j);
    }
}
// Total: O(n + n²) → Simplify to O(n²)
// (n² grows way faster, so n doesn't matter)
```

### Rule 3: Different Inputs = Different Variables

```java
for (int i = 0; i < a.length; i++) {     // O(a)
    System.out.println(a[i]);
}
for (int j = 0; j < b.length; j++) {     // O(b)
    System.out.println(b[j]);
}
// Total: O(a + b), NOT O(n)!
```

### Rule 4: Nested Loops = Multiply

```java
for (int i = 0; i < n; i++) {      // O(n)
    for (int j = 0; j < n; j++) {   // O(n)
        // ...
    }
}
// Total: O(n × n) = O(n²)
```

---

## 🎓 Practice: Guess the Big O!

### Exercise 1
```java
public int getFirst(int[] arr) {
    return arr[0];
}
```
**Answer:** O(1) - Direct access!

### Exercise 2
```java
public void printAll(int[] arr) {
    for (int num : arr) {
        System.out.println(num);
    }
}
```
**Answer:** O(n) - Visit each item once!

### Exercise 3
```java
public void printPairs(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr.length; j++) {
            System.out.println(arr[i] + "," + arr[j]);
        }
    }
}
```
**Answer:** O(n²) - Nested loops!

### Exercise 4
```java
public int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = (left + right) / 2;
        if (arr[mid] == target) return mid;
        if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```
**Answer:** O(log n) - Divide and conquer!

---

## 📚 Big O for Each Data Structure

### Arrays
- **Access:** O(1) - Use index to jump directly
- **Search:** O(n) - Might need to check all items
- **Insert (end):** O(1) - Add to end
- **Insert (middle):** O(n) - Shift everything over
- **Delete:** O(n) - Shift everything over

### LinkedList
- **Access:** O(n) - Follow the chain
- **Search:** O(n) - Follow the chain
- **Insert (beginning):** O(1) - Just change pointers!
- **Insert (end):** O(n) - Must walk to end (unless you keep tail pointer)
- **Delete:** O(n) - Find it first, then O(1) to delete

### Stack
- **Push:** O(1) - Add to top
- **Pop:** O(1) - Remove from top
- **Peek:** O(1) - Look at top

### Queue
- **Enqueue:** O(1) - Add to back
- **Dequeue:** O(1) - Remove from front
- **Peek:** O(1) - Look at front

### Hash Table
- **Insert:** O(1) average - Direct placement!
- **Search:** O(1) average - Direct lookup!
- **Delete:** O(1) average - Direct removal!
- **Worst case:** O(n) - If many collisions

### Binary Search Tree (Balanced)
- **Insert:** O(log n) - Navigate down tree
- **Search:** O(log n) - Navigate down tree
- **Delete:** O(log n) - Navigate down tree
- **Worst case (unbalanced):** O(n) - Becomes a linked list!

### Heap
- **Insert:** O(log n) - Bubble up
- **Delete Max/Min:** O(log n) - Bubble down
- **Get Max/Min:** O(1) - It's at the top!

---

## 🎯 Choosing the Right Data Structure

**Need fast access by index?**
→ Use **Array** (O(1) access)

**Need to add/remove from beginning a lot?**
→ Use **LinkedList** (O(1) insert/delete at start)

**Need fast lookups by key?**
→ Use **HashMap** (O(1) lookup)

**Need to keep things sorted?**
→ Use **Binary Search Tree** (O(log n) operations)

**Need to always get min/max quickly?**
→ Use **Heap** (O(1) to peek, O(log n) to remove)

**Need LIFO (Last In, First Out)?**
→ Use **Stack** (O(1) push/pop)

**Need FIFO (First In, First Out)?**
→ Use **Queue** (O(1) enqueue/dequeue)

---

## 💡 Quick Tips for Interviews

1. **Always state the Big O** after explaining your solution
2. **Explain trade-offs**: "This is O(n) time but O(1) space"
3. **Know common complexities**: Sorting is usually O(n log n), Hash lookups are O(1)
4. **Watch for hidden loops**: `.contains()`, `.indexOf()` are O(n) operations!
5. **Space complexity matters too!** Recursion uses O(n) space on the call stack

---

### 🕐 Understanding Time Complexity (Big O) - Quick Reference

**Rule of thumb:** Smaller is faster! O(1) < O(log n) < O(n) < O(n log n) < O(n²)

**Common Complexities:**
- **O(1)** = "Constant" - Always the same speed! 🚀
- **O(log n)** = "Logarithmic" - Gets slower, but not by much 😊  
- **O(n)** = "Linear" - Double the items = double the time 📈
- **O(n log n)** = "Linearithmic" - Faster than O(n²), slower than O(n) ⚡
- **O(n²)** = "Quadratic" - Gets SLOW with lots of items! 🐌
- **O(2^n)** = "Exponential" - AVOID! 💀

---

## 🔧 Sorting Algorithms Explained (Simple Version)

### Why Do We Sort?

Sorting makes things easier to find! Think about:
- A dictionary (words are sorted A-Z)
- Your music playlist (sorted by artist or song name)
- Leaderboard in a game (sorted by high score)

### Sorting Algorithms - From Easiest to Understand

#### 1. 🫧 Bubble Sort - "The Bubble Float"

**How it works:** Compare neighbors and swap if they're in wrong order. Repeat until sorted.

**Visual example:**
```
[5, 2, 8, 1, 9]
Compare 5 and 2 → Swap!
[2, 5, 8, 1, 9]
Compare 5 and 8 → Don't swap
[2, 5, 8, 1, 9]
Compare 8 and 1 → Swap!
[2, 5, 1, 8, 9]
... keep going until all sorted!
```

**Real-life example:** Like arranging people in line by height - keep swapping neighbors until everyone is in order

**Speed:** O(n²) - Slow for big lists, but easy to understand!

**When to use:** Learning! Not for real programs with lots of data

**Where to find it:** `Sort/BubbleSort/`

---

#### 2. 📥 Insertion Sort - "The Card Sorter"

**How it works:** Take one item at a time and put it in the right place

**Visual example:**
```
Cards in hand: [2, 5]
New card: 3
Insert 3 between 2 and 5
Result: [2, 3, 5]
```

**Real-life example:** Sorting playing cards in your hand - you pick up one card and put it in the right spot

**Speed:** O(n²) - Slow for big lists, but good if list is almost sorted!

**When to use:** When you're adding items one at a time to an already sorted list

**Where to find it:** `Sort/InsertionSort/`

---

#### 3. 🎯 Selection Sort - "The Trophy Hunter"

**How it works:** Find the smallest item, put it first. Find next smallest, put it second. Repeat.

**Visual example:**
```
[5, 2, 8, 1, 9]
Find smallest (1) → Put it first
[1, 2, 8, 5, 9]
Find next smallest (2) → Already in place
[1, 2, 5, 8, 9]
... continue until done!
```

**Real-life example:** Picking teams - always pick the best available player first

**Speed:** O(n²) - Slow, but makes fewest swaps

**When to use:** When swapping items is expensive (takes a lot of time/memory)

**Where to find it:** `Sort/SelectionSort/`

---

#### 4. 🚀 Merge Sort - "The Divide and Conquer"

**How it works:** Split list in half, sort each half, then merge them back together

**Visual example:**
```
[5, 2, 8, 1]
Split: [5, 2] and [8, 1]
Split more: [5] [2] and [8] [1]
Merge: [2, 5] and [1, 8]
Merge final: [1, 2, 5, 8]
```

**Real-life example:** Sorting two decks of cards separately, then merging them together in order

**Speed:** O(n log n) - Much faster! Good for big lists

**When to use:** When you need a fast, reliable sort

**Where to find it:** `Sort/MergeSort/`

---

#### 5. ⚡ Quick Sort - "The Pivot Point"

**How it works:** Pick a "pivot" number. Put smaller numbers on left, bigger on right. Repeat for each side.

**Visual example:**
```
[5, 2, 8, 1, 9]   Pivot = 5
[2, 1] 5 [8, 9]   Numbers < 5 on left, > 5 on right
Sort left: [1, 2]
Sort right: [8, 9]
Result: [1, 2, 5, 8, 9]
```

**Real-life example:** Organizing books - pick a middle book, put shorter books left, taller books right

**Speed:** O(n log n) average - One of the fastest!

**When to use:** When you need speed and don't mind a small risk it could be slower

**Where to find it:** `Sort/QuickSort/`

---

#### 6. 🔢 Counting Sort - "The Counter"

**How it works:** Count how many of each number you have, then write them out in order

**Visual example:**
```
Numbers: [3, 1, 3, 2, 1]
Count: 1 appears 2 times
       2 appears 1 time
       3 appears 2 times
Write out: [1, 1, 2, 3, 3]
```

**Real-life example:** Counting votes - tally marks for each candidate, then announce results

**Speed:** O(n + k) - Super fast when numbers are in a small range!

**When to use:** Sorting integers in a small range (like test scores 0-100)

**Where to find it:** `Sort/countingsort/`

---

### Sorting Algorithm Comparison

| Algorithm | Speed | Memory | Best For | Difficulty to Learn |
|-----------|-------|--------|----------|---------------------|
| **Bubble Sort** | 🐌 Slow (n²) | ✅ Low | Learning | ⭐ Easy |
| **Insertion Sort** | 🐌 Slow (n²) | ✅ Low | Almost sorted data | ⭐ Easy |
| **Selection Sort** | 🐌 Slow (n²) | ✅ Low | Small lists | ⭐ Easy |
| **Merge Sort** | ⚡ Fast (n log n) | ❌ More memory | Large lists | ⭐⭐ Medium |
| **Quick Sort** | ⚡ Fast (n log n) | ✅ Low | General use | ⭐⭐⭐ Hard |
| **Counting Sort** | 🚀 Very Fast (n+k) | ❌ More memory | Small range integers | ⭐⭐ Medium |

### Which One Should I Learn First?

1. **Start with Bubble Sort** - Easiest to understand
2. **Then Insertion Sort** - Still simple, more practical
3. **Then Selection Sort** - Understand the pattern
4. **Then Merge Sort** - Learn divide and conquer
5. **Finally Quick Sort** - The professional's choice

---

## 🔍 Searching Algorithms Explained

### 1. Linear Search - "The Line Walker"

**How it works:** Check every item one by one until you find what you want

**Example:**
```java
Find 7 in [3, 1, 7, 5, 9]
Check 3 ❌
Check 1 ❌
Check 7 ✅ Found it!
```

**Speed:** O(n) - Slow but works on any list

**When to use:** Small lists or unsorted data

---

### 2. Binary Search - "The Dictionary Search"

**How it works:** Only works on sorted lists! Check the middle. If your target is smaller, check left half. If bigger, check right half. Repeat.

**Example:**
```java
Find 7 in [1, 3, 5, 7, 9]
Check middle (5): 7 > 5, so look right
Check middle of right half (7): Found it!
```

**Speed:** O(log n) - Super fast!

**When to use:** Sorted lists when you need speed

**Where to find it:** `binarySearch/`

---

### Complete Algorithm Reference Table

#### Sorting Algorithms
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

## 🎯 Interview Pattern Guide (Explained for Beginners)

### What Are Patterns?

Patterns are like **recipes** for solving similar problems. Once you learn a pattern, you can use it over and over again!

Think of it like this: If you know how to make cookies, you can make chocolate chip cookies, oatmeal cookies, or sugar cookies - it's the same basic pattern!

---

### Pattern 1: Two Pointers 👉👈

**Simple explanation:** Use two fingers to point at different spots in a list

**When to use:** Finding pairs of numbers, removing duplicates, reversing a string

**Real-life example:** Two people searching a library - one starts from the beginning, one from the end

**Visual:**
```
[1, 2, 3, 4, 5, 6]
 ↑           ↑
left       right
```

**Example problem:** Find two numbers that add up to a target
```java
// If array is sorted: [1, 3, 5, 7, 9]
// Find two numbers that sum to 10
// Start: left=1, right=9 → 1+9=10 ✅ Found!
```

**Where to practice:** Two Sum, Remove Duplicates

**Code location:** Check `LinkedList/` implementations

---

### Pattern 2: Sliding Window 🪟

**Simple explanation:** Look at a "window" of items, then slide it along

**When to use:** Finding longest/shortest substring, maximum sum of K elements

**Real-life example:** Looking through a train window as it moves - you see different things as it slides along

**Visual:**
```
[1, 2, 3, 4, 5]
[window]     → slide →
   [window]  → slide →
      [window]
```

**Example problem:** Find longest substring without repeating characters
```java
"abcabcbb"
Window: "abc" (no repeats) ✅
Window: "abca" (a repeats) ❌ shrink window
```

**Where to practice:** Longest Substring Without Repeating Characters

**Code location:** Can be applied to `Sort/Arrays/`

---

### Pattern 3: Fast & Slow Pointers 🐰🐢

**Simple explanation:** One pointer moves fast (2 steps), one moves slow (1 step)

**When to use:** Detect cycles in LinkedList, find middle of LinkedList

**Real-life example:** The tortoise and the hare race - if they're on a circular track, the fast one will lap the slow one!

**Visual:**
```
1 → 2 → 3 → 4 → 5 → back to 3 (cycle!)
    ↑   ↑
   slow fast (fast moves 2 steps at a time)
```

**Example problem:** Does this LinkedList have a cycle?
```java
// If there's a cycle, fast and slow will eventually meet
// Like runners on a circular track
```

**Where to practice:** Linked List Cycle

**Code location:** `LinkedList/LinkedList/`

---

### Pattern 4: Merge Intervals 📊

**Simple explanation:** Combine overlapping time periods

**When to use:** Scheduling problems, merging time ranges

**Real-life example:** Combining your class schedule - if Math is 9-10am and Science is 10-11am, you're busy 9-11am

**Visual:**
```
Meeting 1: [1, 3]
Meeting 2: [2, 6]
Merged:    [1, 6]  (they overlap!)

1──3
  2────6
1──────6 (merged)
```

**Example problem:** Merge overlapping intervals
```java
[[1,3], [2,6], [8,10]]  →  [[1,6], [8,10]]
```

**Where to practice:** Merge Intervals

**Related:** `Sort/MergeSort/`

---

### Pattern 5: In-place LinkedList Reversal 🔄

**Simple explanation:** Reverse the direction of arrows in a LinkedList

**When to use:** Reverse a LinkedList, reverse parts of a LinkedList

**Real-life example:** Reversing a chain - unhook and reattach each link in reverse order

**Visual:**
```
Before: 1 → 2 → 3 → 4
After:  1 ← 2 ← 3 ← 4
```

**Example problem:** Reverse a LinkedList
```java
Input:  1 → 2 → 3 → null
Output: 3 → 2 → 1 → null
```

**Where to practice:** Reverse Linked List

**Code location:** `LinkedList/`

---

### Pattern 6: Tree BFS/DFS 🌳

**Simple explanation:** Two ways to explore a tree

**BFS (Breadth-First Search)** = Level by level (like reading a book left to right)
**DFS (Depth-First Search)** = Go deep first (like exploring a cave - go as far as you can, then back up)

**Real-life example:**
- BFS: Exploring your neighborhood street by street
- DFS: Following one street all the way to the end before trying another

**Visual (BFS):**
```
        1
       / \
      2   3
     / \
    4   5

Visit order: 1, 2, 3, 4, 5 (level by level)
```

**Visual (DFS):**
```
        1
       / \
      2   3
     / \
    4   5

Visit order: 1, 2, 4, 5, 3 (deep first)
```

**Where to practice:** Binary Tree Level Order Traversal

**Code location:** `binarySearchTree/`

---

### Pattern 7: Top K Elements 🏆

**Simple explanation:** Find the K largest or smallest items

**When to use:** Finding top 5 scores, K most frequent items

**Real-life example:** Finding the top 3 students in class

**How it works:** Use a heap (priority queue) to keep track of the top K

**Visual:**
```
Numbers: [3, 1, 5, 2, 4]
Find top 3: [5, 4, 3]
```

**Where to practice:** Top K Frequent Elements

**Code location:** `maxHeap/`, `PriorityQueue/`

---

### Pattern 8: Modified Binary Search 🔍

**Simple explanation:** Binary search with a twist (array might be rotated or modified)

**When to use:** Searching in rotated sorted arrays, finding peaks

**Real-life example:** Finding a book in a library where some shelves have been rearranged

**Visual:**
```
Normal sorted: [1, 2, 3, 4, 5]
Rotated:       [4, 5, 1, 2, 3]
Still can use binary search with modifications!
```

**Where to practice:** Search in Rotated Sorted Array

**Code location:** `binarySearch/`

---

### How to Learn Patterns

**Step 1:** Pick one pattern
**Step 2:** Solve 3 easy problems with that pattern
**Step 3:** Solve 2 medium problems with that pattern
**Step 4:** Move to next pattern
**Step 5:** Mix patterns on Day 4!

### Quick Pattern Reference

| Pattern | What It Does | When You See... |
|---------|-------------|-----------------|
| **Two Pointers** | Use 2 positions | "Find pair", "Remove duplicates" |
| **Sliding Window** | Move a window | "Longest substring", "Maximum sum of K items" |
| **Fast & Slow** | Two different speeds | "Find cycle", "Find middle" |
| **Merge Intervals** | Combine ranges | "Merge meetings", "Overlapping times" |
| **LinkedList Reversal** | Reverse connections | "Reverse list", "Reverse K nodes" |
| **Tree BFS/DFS** | Explore tree | "Level order", "All paths" |
| **Top K** | Find biggest/smallest K | "Top K frequent", "Kth largest" |
| **Binary Search** | Divide and conquer | "Find in sorted", "Search rotated" |

---

## �️ BONUS: Advanced Data Structures in Java

### Ready for the Next Level?

Once you've mastered the basics, these **advanced data structures** will make you a coding superstar! 🌟

These are like **power-ups** in a video game - they solve specific problems way faster than basic structures. Many companies ask about these in **senior-level interviews**.

**Note:** These are more challenging. Start learning them AFTER you're comfortable with basic structures!

---

## 🌳 Advanced Trees

### 1. AVL Tree - "The Perfect Balancer" ⚖️

**What it is:** A Binary Search Tree that automatically keeps itself balanced (height difference between left and right is at most 1)

**Real-life example:** A perfectly organized bookshelf that automatically rearranges when you add a book to keep weight balanced

**Why it's special:** Regular BSTs can become like a LinkedList (very slow). AVL Trees prevent this!

**Visual - Unbalanced vs Balanced:**
```
Bad (Unbalanced BST):        Good (AVL Tree):
    1                            4
     \                         /   \
      2                       2     6
       \                     / \   / \
        3                   1   3 5   7
         \
          4                Fast: O(log n)
           \
            5              Always balanced!
             \
              6
               \
                7

Slow: O(n) - like a LinkedList!
```

**How it works:**
1. Insert/delete like normal BST
2. Check if tree became unbalanced
3. "Rotate" nodes to rebalance

**Rotations (simple example):**
```
Before (right-heavy):    After (rotate left):
    2                        3
     \                      / \
      3        →           2   4
       \
        4
```

**Time Complexity:**
- ✅ Search: **O(log n)** GUARANTEED (unlike regular BST)
- ✅ Insert: O(log n)
- ✅ Delete: O(log n)

**When to use:**
- Need guaranteed fast operations
- Lots of searches with occasional inserts/deletes
- Can't afford worst-case O(n) of regular BST

**Java implementation:**
```java
class AVLNode {
    int data;
    int height;
    AVLNode left, right;
    
    AVLNode(int data) {
        this.data = data;
        this.height = 1;
    }
}

class AVLTree {
    AVLNode root;
    
    // Get height of node
    int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }
    
    // Get balance factor
    int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }
    
    // Right rotate
    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        
        return x;
    }
    
    // Left rotate (similar to right)
    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        
        return y;
    }
}
```

**Practice Problems:**
- Implement AVL insertion
- Count rotations needed to balance a BST
- Design a self-balancing binary search tree

---

### 2. Red-Black Tree - "The Professional's Choice" 🔴⚫

**What it is:** Another self-balancing BST, but uses colors (red/black) instead of height tracking

**Real-life example:** Traffic lights controlling flow to prevent congestion

**Why it's special:** Used in Java's **TreeMap** and **TreeSet**! Also in Linux kernel.

**Rules:**
1. Every node is RED or BLACK
2. Root is always BLACK
3. Red nodes can't have red children (no two reds in a row)
4. Every path from root to leaf has same number of black nodes

**Visual:**
```
        B(10)
       /     \
     R(5)   B(15)
    /   \
  B(3) B(7)

B = Black, R = Red
```

**Why colors help:**
- Simpler rules than AVL height tracking
- Fewer rotations needed (faster inserts/deletes)
- Still guarantees O(log n) operations

**Time Complexity:**
- ✅ Search: O(log n)
- ✅ Insert: O(log n)
- ✅ Delete: O(log n)

**When to use:**
- More inserts/deletes than searches (fewer rotations than AVL)
- Use **TreeMap** or **TreeSet** in Java (they're Red-Black Trees!)

**Java Example (using TreeMap):**
```java
import java.util.TreeMap;

// TreeMap uses Red-Black Tree internally!
TreeMap<Integer, String> map = new TreeMap<>();
map.put(3, "Three");
map.put(1, "One");
map.put(2, "Two");

// Always sorted!
for (int key : map.keySet()) {
    System.out.println(key + ": " + map.get(key));
}
// Output: 1: One, 2: Two, 3: Three
```

**Practice Problems:**
- Validate Red-Black Tree properties
- Understand TreeMap internals
- Compare AVL vs Red-Black trade-offs

---

### 3. Trie (Prefix Tree) - "The Word Wizard" 📚

**What it is:** A tree where each path from root to leaf represents a word/string

**Real-life example:** 
- Dictionary's index
- Autocomplete in your phone
- Spell checker

**Visual:**
```
Store: "cat", "car", "card", "dog"

       (root)
       /    \
      c      d
      |      |
      a      o
     / \     |
    t   r    g
        |
        d

Finding "car": Follow c→a→r (3 steps!)
Finding all words starting with "ca": Follow c→a, then explore all branches
```

**How it works:**
- Each node = one character
- Path from root = word/prefix
- Special marker shows "end of word"

**Java Implementation:**
```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];  // a-z
    boolean isEndOfWord;
}

class Trie {
    TrieNode root = new TrieNode();
    
    // Insert word
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';  // Convert 'a'-'z' to 0-25
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfWord = true;
    }
    
    // Search for word
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) return false;
            node = node.children[index];
        }
        return node.isEndOfWord;
    }
    
    // Check if prefix exists
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) return false;
            node = node.children[index];
        }
        return true;  // Found prefix path!
    }
}

// Usage
Trie trie = new Trie();
trie.insert("apple");
trie.search("apple");      // true
trie.search("app");        // false (not a complete word)
trie.startsWith("app");    // true (prefix exists!)
```

**Time Complexity:**
- ✅ Insert: **O(m)** where m = word length
- ✅ Search: **O(m)**
- ✅ Prefix search: **O(m)**

**Space Complexity:**
- ❌ Can use a lot of memory: O(ALPHABET_SIZE × m × n)
  - 26 pointers per node for English alphabet!

**When to use:**
- Autocomplete features
- Spell checkers
- IP routing tables
- Finding all words with common prefix

**Practice Problems:**
- [Implement Trie](https://leetcode.com/problems/implement-trie-prefix-tree/) (Medium) ⭐
- [Word Search II](https://leetcode.com/problems/word-search-ii/) (Hard)
- [Design Add and Search Words Data Structure](https://leetcode.com/problems/design-add-and-search-words-data-structure/) (Medium)

---

### 4. Segment Tree - "The Range Master" 📊

**What it is:** A tree that stores information about array ranges, allowing fast range queries

**Real-life example:** 
- Finding max temperature in a date range
- Stock price analysis (min/max in a range)
- Game leaderboard (sum of scores in a range)

**Visual - Array & its Segment Tree:**
```
Array: [1, 3, 5, 7, 9, 11]

Segment Tree (stores SUM of ranges):
              36 [0-5]           ← Sum of entire array
            /          \
      9 [0-2]          27 [3-5]  ← Sum of halves
      /    \          /      \
  4[0-1]  5[2-2]  16[3-4]  11[5-5]
  /   \            /    \
1[0] 3[1]        7[3]  9[4]

Can query sum of any range in O(log n)!
```

**What it does:**
- Build: O(n)
- Range query (sum, min, max): **O(log n)**
- Update value: **O(log n)**

**Java Implementation (Range Sum):**
```java
class SegmentTree {
    int[] tree;
    int n;
    
    SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];  // Tree needs 4x space
        build(arr, 0, 0, n - 1);
    }
    
    void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;
            build(arr, 2*node+1, start, mid);      // Left child
            build(arr, 2*node+2, mid+1, end);      // Right child
            tree[node] = tree[2*node+1] + tree[2*node+2];  // Sum of children
        }
    }
    
    int query(int node, int start, int end, int L, int R) {
        if (R < start || end < L) return 0;  // No overlap
        if (L <= start && end <= R) return tree[node];  // Complete overlap
        
        // Partial overlap
        int mid = (start + end) / 2;
        int leftSum = query(2*node+1, start, mid, L, R);
        int rightSum = query(2*node+2, mid+1, end, L, R);
        return leftSum + rightSum;
    }
    
    int rangeSum(int L, int R) {
        return query(0, 0, n-1, L, R);
    }
}

// Usage
int[] arr = {1, 3, 5, 7, 9, 11};
SegmentTree st = new SegmentTree(arr);
System.out.println(st.rangeSum(1, 4));  // Sum from index 1 to 4 = 3+5+7+9 = 24
```

**When to use:**
- Lots of range queries (sum, min, max, GCD)
- Array values update frequently
- Need fast queries AND updates

**Practice Problems:**
- [Range Sum Query - Mutable](https://leetcode.com/problems/range-sum-query-mutable/) (Medium)
- [Range Minimum Query](https://leetcode.com/problems/range-minimum-query/) (Medium)

---

## 🕸️ Graphs - Advanced Concepts

### What is a Graph?

**Simple definition:** A collection of nodes (cities) connected by edges (roads)

**Real-life examples:**
- Social network (people = nodes, friendships = edges)
- Google Maps (places = nodes, roads = edges)
- Website links (pages = nodes, hyperlinks = edges)

### Types of Graphs

**1. Directed vs Undirected**
```
Undirected (two-way):    Directed (one-way):
    A --- B                  A → B
    |     |                  ↓   ↓
    C --- D                  C → D
    
(Friendship)             (Twitter follows)
```

**2. Weighted vs Unweighted**
```
Unweighted:              Weighted:
    A --- B                 A -5- B
    |     |                 |     |
    C --- D                 C -3- D
                            
(Connections)            (Distance in miles)
```

### Graph Representation in Java

**Method 1: Adjacency List (Most Common)**
```java
import java.util.*;

class Graph {
    private Map<Integer, List<Integer>> adjList = new HashMap<>();
    
    // Add edge
    void addEdge(int from, int to) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.get(from).add(to);
        // For undirected graph, also add reverse:
        // adjList.putIfAbsent(to, new ArrayList<>());
        // adjList.get(to).add(from);
    }
    
    // Get neighbors
    List<Integer> getNeighbors(int node) {
        return adjList.getOrDefault(node, new ArrayList<>());
    }
}

// Usage
Graph g = new Graph();
g.addEdge(0, 1);
g.addEdge(0, 2);
g.addEdge(1, 2);
```

**Method 2: Adjacency Matrix**
```java
class GraphMatrix {
    int[][] matrix;
    int vertices;
    
    GraphMatrix(int v) {
        vertices = v;
        matrix = new int[v][v];
    }
    
    void addEdge(int from, int to, int weight) {
        matrix[from][to] = weight;
        // For undirected: matrix[to][from] = weight;
    }
}
```

### Graph Traversals

**1. BFS (Breadth-First Search) - "Explore Layer by Layer"**

**Like:** Ripples in a pond - spreading outward

```java
void BFS(int start) {
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();
    
    queue.add(start);
    visited.add(start);
    
    while (!queue.isEmpty()) {
        int node = queue.poll();
        System.out.println(node);
        
        for (int neighbor : adjList.get(node)) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                queue.add(neighbor);
            }
        }
    }
}
```

**Visual:**
```
    0
   / \
  1   2
 / \
3   4

BFS from 0: 0 → 1, 2 → 3, 4 (level by level)
```

**2. DFS (Depth-First Search) - "Explore as Deep as Possible"**

**Like:** Exploring a maze - go as far as you can, then backtrack

```java
void DFS(int node, Set<Integer> visited) {
    visited.add(node);
    System.out.println(node);
    
    for (int neighbor : adjList.get(node)) {
        if (!visited.contains(neighbor)) {
            DFS(neighbor, visited);
        }
    }
}

// Call: DFS(0, new HashSet<>());
```

**Visual:**
```
    0
   / \
  1   2
 / \
3   4

DFS from 0: 0 → 1 → 3 → 4 → 2 (go deep first)
```

### Advanced Graph Algorithms

**1. Dijkstra's Algorithm - "Shortest Path" 🛣️**

**What it does:** Finds shortest path from one node to all others

**Real-life:** GPS finding fastest route

```java
void dijkstra(int start) {
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    Map<Integer, Integer> dist = new HashMap<>();
    
    pq.offer(new int[]{start, 0});
    dist.put(start, 0);
    
    while (!pq.isEmpty()) {
        int[] current = pq.poll();
        int node = current[0];
        int distance = current[1];
        
        if (distance > dist.getOrDefault(node, Integer.MAX_VALUE)) continue;
        
        for (int[] neighbor : getWeightedNeighbors(node)) {
            int nextNode = neighbor[0];
            int weight = neighbor[1];
            int newDist = distance + weight;
            
            if (newDist < dist.getOrDefault(nextNode, Integer.MAX_VALUE)) {
                dist.put(nextNode, newDist);
                pq.offer(new int[]{nextNode, newDist});
            }
        }
    }
}
```

**Time Complexity:** O((V + E) log V) with priority queue

**Practice Problems:**
- [Network Delay Time](https://leetcode.com/problems/network-delay-time/) (Medium)
- [Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/) (Medium)

**2. Union-Find (Disjoint Set) - "The Connector" 🔗**

**What it does:** Efficiently tracks connected components

**Real-life:** Finding if two people are in same friend group

```java
class UnionFind {
    int[] parent, rank;
    
    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;  // Each node is its own parent initially
        }
    }
    
    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // Path compression
        }
        return parent[x];
    }
    
    boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) return false;  // Already connected
        
        // Union by rank
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return true;
    }
}
```

**Time Complexity:** Nearly O(1) for both operations!

**Practice Problems:**
- [Number of Connected Components](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/) (Medium)
- [Redundant Connection](https://leetcode.com/problems/redundant-connection/) (Medium)

---

## 🎁 Specialized Java Data Structures

### 1. Deque (Double-Ended Queue) - "The Two-Way Street" ⬅️➡️

**What it is:** Queue that allows add/remove from BOTH ends

**Real-life example:** A line where VIPs can cut to the front AND people can leave from back

```java
import java.util.ArrayDeque;
import java.util.Deque;

Deque<Integer> deque = new ArrayDeque<>();

// Add to both ends
deque.addFirst(1);   // [1]
deque.addLast(2);    // [1, 2]
deque.addFirst(0);   // [0, 1, 2]

// Remove from both ends
deque.removeFirst(); // [1, 2]
deque.removeLast();  // [1]

// Peek at both ends
deque.peekFirst();
deque.peekLast();
```

**When to use:**
- Sliding window problems
- Implementing both stack AND queue
- Palindrome checking

**Time Complexity:** O(1) for all operations!

**Practice Problems:**
- [Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/) (Hard) ⭐

---

### 2. PriorityQueue with Custom Comparator - "The Smart Sorter" 🎯

**What it is:** Heap that can sort by any criteria you want!

```java
import java.util.PriorityQueue;

// Min-heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max-heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// Custom objects
class Task {
    String name;
    int priority;
    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
}

PriorityQueue<Task> tasks = new PriorityQueue<>((a, b) -> b.priority - a.priority);
tasks.offer(new Task("Homework", 5));
tasks.offer(new Task("Game", 2));
tasks.offer(new Task("Sleep", 10));

// Always get highest priority first!
Task next = tasks.poll();  // Sleep (priority 10)
```

**When to use:**
- Task scheduling
- Finding K largest/smallest
- Merge K sorted lists

**Practice Problems:**
- [Task Scheduler](https://leetcode.com/problems/task-scheduler/) (Medium)
- [Kth Largest Element in Stream](https://leetcode.com/problems/kth-largest-element-in-a-stream/) (Easy)

---

### 3. TreeMap & TreeSet - "The Sorted Collections" 🗂️

**What they are:** Maps/Sets that keep keys SORTED automatically (using Red-Black Tree)

```java
import java.util.TreeMap;
import java.util.TreeSet;

// TreeSet - sorted unique values
TreeSet<Integer> sorted = new TreeSet<>();
sorted.add(5);
sorted.add(1);
sorted.add(3);
System.out.println(sorted);  // [1, 3, 5] - Always sorted!

// Useful methods
sorted.first();    // 1 (smallest)
sorted.last();     // 5 (largest)
sorted.floor(4);   // 3 (largest ≤ 4)
sorted.ceiling(2); // 3 (smallest ≥ 2)

// TreeMap - sorted key-value pairs
TreeMap<Integer, String> map = new TreeMap<>();
map.put(3, "Three");
map.put(1, "One");
map.put(2, "Two");
// Iteration is always in sorted order!
```

**Time Complexity:** O(log n) for add, remove, contains

**When to use:**
- Need sorted iteration
- Range queries (between, floor, ceiling)
- Maintaining sorted data with frequent updates

**Practice Problems:**
- [My Calendar I](https://leetcode.com/problems/my-calendar-i/) (Medium)
- [Count of Range Sum](https://leetcode.com/problems/count-of-range-sum/) (Hard)

---

### 4. BitSet - "The Memory Saver" 💾

**What it is:** Array of bits (true/false) that uses WAY less memory

**Memory comparison:**
- `boolean[] array = new boolean[1000000]` → ~1 MB
- `BitSet bits = new BitSet(1000000)` → ~125 KB (8x smaller!)

```java
import java.util.BitSet;

BitSet bits = new BitSet(100);

// Set individual bits
bits.set(5);     // Set bit 5 to true
bits.set(10);
bits.clear(5);   // Set bit 5 to false

// Check bit
if (bits.get(10)) {  // true
    System.out.println("Bit 10 is set");
}

// Bitwise operations
BitSet set1 = new BitSet();
BitSet set2 = new BitSet();
set1.set(1); set1.set(2);
set2.set(2); set2.set(3);

set1.and(set2);  // Intersection: {2}
set1.or(set2);   // Union: {1, 2, 3}
set1.xor(set2);  // Symmetric difference
```

**When to use:**
- Large boolean arrays
- Prime number sieve (Sieve of Eratosthenes)
- Tracking visited states in large space

**Practice Problems:**
- [Prime Number Sieve](https://leetcode.com/problems/count-primes/) (Medium)
- State space search with millions of states

---

### 5. Skip List - "The Probabilistic Speedster" 🎲

**What it is:** Sorted linked list with express lanes for faster search

**Visual:**
```
Level 3:  1 -----------------> 10
Level 2:  1 -------> 5 ------> 10
Level 1:  1 --> 3 -> 5 --> 8 -> 10
Level 0:  1->2->3->4->5->6->7->8->9->10

Search for 8: Follow top lane → drop down → find it in ~log n steps!
```

**Concept:** Like a highway system with express lanes

**Time Complexity:** O(log n) average for search, insert, delete

**Note:** Not in Java standard library, but used in `ConcurrentSkipListMap`

```java
import java.util.concurrent.ConcurrentSkipListMap;

ConcurrentSkipListMap<Integer, String> skipMap = new ConcurrentSkipListMap<>();
skipMap.put(3, "Three");
skipMap.put(1, "One");
skipMap.put(2, "Two");

// Thread-safe and sorted!
```

**When to use:**
- Concurrent programming (thread-safe sorted map)
- Alternative to balanced trees
- When you want sorted data with good performance

---

### 6. BlockingQueue - "The Wait-in-Line Queue" 🚦

**What it is:** Thread-safe queue that makes threads WAIT when queue is empty/full

**Real-life example:** Restaurant - chef waits if no orders, customer waits if kitchen is full

```java
import java.util.concurrent.*;

// Create queue with capacity 10
BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

// Producer thread
new Thread(() -> {
    try {
        queue.put("Task 1");  // Blocks if queue is full
        queue.put("Task 2");
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}).start();

// Consumer thread
new Thread(() -> {
    try {
        String task = queue.take();  // Blocks if queue is empty
        System.out.println("Processing: " + task);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}).start();
```

**When to use:**
- Producer-Consumer pattern
- Thread pools
- Task queues in concurrent applications

**Practice:** Implement producer-consumer with BlockingQueue

---

## 🎯 Advanced Data Structures: Quick Reference

| Structure | Best For | Time Complexity | When to Use |
|-----------|----------|-----------------|-------------|
| **AVL Tree** | Guaranteed O(log n) | Search/Insert/Delete: O(log n) | More searches than updates |
| **Red-Black Tree** | Balanced performance | Search/Insert/Delete: O(log n) | More updates than AVL |
| **Trie** | String operations | O(m) where m=word length | Autocomplete, spell check |
| **Segment Tree** | Range queries | Query/Update: O(log n) | Range sum/min/max |
| **Graph** | Relationships | BFS/DFS: O(V+E) | Networks, paths |
| **Deque** | Both ends access | All ops: O(1) | Sliding window |
| **TreeMap/Set** | Sorted data | Ops: O(log n) | Sorted iteration |
| **BitSet** | Boolean flags | Set/Get: O(1) | Memory-efficient flags |
| **Skip List** | Sorted concurrent | Ops: O(log n) avg | Thread-safe sorted data |
| **BlockingQueue** | Thread coordination | Ops: O(1) | Producer-consumer |

---

## 🏆 Bonus LeetCode Problems for Advanced Structures

### Advanced Trees
- [Implement Trie](https://leetcode.com/problems/implement-trie-prefix-tree/) (Medium) ⭐
- [Word Search II](https://leetcode.com/problems/word-search-ii/) (Hard)
- [Range Sum Query - Mutable](https://leetcode.com/problems/range-sum-query-mutable/) (Medium)

### Graphs
- [Number of Islands](https://leetcode.com/problems/number-of-islands/) (Medium) ⭐
- [Course Schedule](https://leetcode.com/problems/course-schedule/) (Medium) ⭐
- [Network Delay Time](https://leetcode.com/problems/network-delay-time/) (Medium)
- [Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/) (Medium)
- [Min Cost to Connect All Points](https://leetcode.com/problems/min-cost-to-connect-all-points/) (Medium)

### Specialized Structures
- [Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/) (Hard) - Deque
- [LRU Cache](https://leetcode.com/problems/lru-cache/) (Medium) ⭐ - HashMap + Doubly LinkedList
- [Design Twitter](https://leetcode.com/problems/design-twitter/) (Medium) - Multiple structures
- [My Calendar I](https://leetcode.com/problems/my-calendar-i/) (Medium) - TreeMap

---

## 🎓 When to Learn Advanced Structures

**Timeline Recommendation:**

**Weeks 1-2:** Master basic structures (Array, LinkedList, Stack, Queue, HashMap)

**Weeks 3-4:** Learn Trees and Heaps

**Weeks 5-6:** Study Graphs (BFS, DFS, basic algorithms)

**Weeks 7-8:** Explore advanced trees (AVL, Red-Black, Trie)

**Weeks 9+:** Learn specialized structures as needed for specific problems

**Remember:** Master the basics first! Advanced structures build on fundamentals.

---

## 💼 Advanced Interview Questions & Answers

### Real-World Scenarios for Senior Engineers

These questions are commonly asked in **senior-level technical interviews** at companies like **fintech startups, FAANG companies, and financial institutions**. They test not just your knowledge of data structures, but your ability to make **engineering trade-offs** and apply them to **real-world systems**.

---

### 🎯 Top 15 Advanced Data Structure Interview Questions

---

### Question 1: Why does Java use Red-Black Tree for TreeMap instead of AVL Tree?

**Level:** Senior

**Answer:**

While AVL trees are **more strictly balanced** (height difference ≤ 1), providing faster lookups, Red-Black Trees are "**balanced enough**" and perform significantly better on **insertions and deletions**.

**Key Differences:**

| Feature | AVL Tree | Red-Black Tree |
|---------|----------|----------------|
| **Balance** | Stricter (height diff ≤ 1) | Looser (height ≤ 2 × log n) |
| **Rotations on Insert** | Up to log n | Up to 2 |
| **Rotations on Delete** | Up to log n | Up to 3 |
| **Search Speed** | Faster ✅ | Slightly slower |
| **Insert/Delete Speed** | Slower | Faster ✅ |

**Real-World Impact:**

In an environment with **frequent stock price updates, transaction logging, or real-time data streams**, the throughput of a Red-Black Tree is superior because:
- Fewer rotations = less CPU overhead
- Better for write-heavy workloads
- Still maintains O(log n) for all operations

**Code Example:**
```java
// TreeMap uses Red-Black Tree internally
TreeMap<String, Double> stockPrices = new TreeMap<>();

// Frequent updates (where RB-Tree excels)
stockPrices.put("AAPL", 150.25);  // Fast insert
stockPrices.put("GOOGL", 2800.50); // Fast insert
stockPrices.put("AAPL", 151.00);   // Fast update

// Still fast lookups
Double price = stockPrices.get("AAPL");  // O(log n)
```

**Follow-up:** When would you choose AVL over Red-Black?
- **Answer:** When you have a **read-heavy** workload with infrequent updates, like a static dictionary or configuration store where you do millions of lookups but rarely insert/delete.

---

### Question 2: How does Java HashMap handle collisions after Java 8?

**Level:** Mid to Senior

**Answer:**

Java HashMap has evolved to handle collisions more efficiently:

**Before Java 8:**
- Collisions handled via **LinkedList (Chaining)**
- Worst-case lookup: **O(n)** if many collisions in one bucket

**After Java 8:**
- Initially uses **LinkedList** for collisions
- When bucket exceeds **threshold (8 elements)**, it **"Treeifies"**
- Converts LinkedList → **Red-Black Tree**
- Worst-case lookup improves: **O(n) → O(log n)**

**Visual:**
```
Before Treeification (≤ 8 elements):
Bucket 5: [Entry1] → [Entry2] → [Entry3] ... → [Entry8]
Search: O(n) in worst case

After Treeification (> 8 elements):
Bucket 5: Red-Black Tree
         [Entry5]
        /        \
    [Entry2]    [Entry7]
    /    \      /    \
  [E1] [E3]  [E6]  [E8]
Search: O(log n)!
```

**Code to Demonstrate:**
```java
HashMap<BadHashKey, String> map = new HashMap<>();

// BadHashKey always returns same hashCode (worst case)
class BadHashKey {
    int value;
    BadHashKey(int value) { this.value = value; }
    
    @Override
    public int hashCode() { return 1; }  // All collide!
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BadHashKey && 
               ((BadHashKey)obj).value == this.value;
    }
}

// Add 10 elements - they all go to same bucket
for (int i = 0; i < 10; i++) {
    map.put(new BadHashKey(i), "Value " + i);
}

// After 8th element, bucket becomes a Red-Black Tree
// Search is now O(log n) instead of O(n)!
```

**De-Treeification:**
When elements drop below **6**, the tree converts back to LinkedList (to avoid overhead for small buckets).

**Follow-up:** Why threshold 8 and not 4 or 16?
- **Answer:** Statistical analysis shows that under random hash distribution, having 8+ elements in a bucket is extremely rare (Poisson distribution). The threshold balances between:
  - Tree overhead (higher memory)
  - Performance gain (only worthwhile when collisions are significant)

---

### Question 3: Explain the "Exactly-Once" challenge using BlockingQueue

**Level:** Senior (Concurrency)

**Answer:**

**The Problem:**
In a concurrent application using `BlockingQueue`, if a consumer **takes** a task but **fails before processing**, the data is **lost** (at-most-once delivery).

**Scenario:**
```java
BlockingQueue<Transaction> queue = new ArrayBlockingQueue<>(100);

// Consumer thread
new Thread(() -> {
    while (true) {
        Transaction txn = queue.take();  // Removed from queue
        // ⚠️ IF CRASH HAPPENS HERE, txn is lost!
        processTransaction(txn);  // Might fail
    }
}).start();
```

**Solutions:**

**1. Transactional Outbox Pattern**
```java
// Store task in database first, then process
@Transactional
public void processTransaction(Transaction txn) {
    // 1. Save to "processing" table
    outboxRepository.save(txn);
    
    // 2. Process
    paymentService.process(txn);
    
    // 3. Mark as complete (in same transaction)
    outboxRepository.markComplete(txn.getId());
    
    // If anything fails, entire transaction rolls back
}
```

**2. Use Persistent Queue (Apache Kafka)**
```java
// Kafka doesn't remove message until consumer commits offset
KafkaConsumer<String, Transaction> consumer = new KafkaConsumer<>(props);

while (true) {
    ConsumerRecords<String, Transaction> records = consumer.poll(100);
    
    for (ConsumerRecord<String, Transaction> record : records) {
        try {
            processTransaction(record.value());
            // Only commit offset AFTER successful processing
            consumer.commitSync();  // Exactly-once guaranteed!
        } catch (Exception e) {
            // Don't commit - message will be redelivered
            log.error("Processing failed", e);
        }
    }
}
```

**3. Acknowledgment-Based Queue**
```java
// Custom implementation with acknowledgment
class AckBlockingQueue<T> {
    private BlockingQueue<Task<T>> queue = new LinkedBlockingQueue<>();
    
    public Task<T> take() throws InterruptedException {
        return queue.take();  // Item still tracked
    }
    
    public void acknowledge(Task<T> task) {
        // Only now is it truly removed
        task.markComplete();
    }
    
    public void nack(Task<T> task) {
        // Put back in queue
        queue.offer(task);
    }
}
```

**Real-World Use:**
In a **Spring Boot lending app** processing loan applications:
- Producer: API receives loan applications
- Queue: Holds applications for processing
- Consumer: Background worker validates credit score

Without exactly-once, a crash during credit check means the application is **lost** (customer never gets response!).

---

### Question 4: When would you prefer Skip List over Binary Search Tree?

**Level:** Senior (Concurrent Systems)

**Answer:**

**Skip Lists** are highly effective in **concurrent/parallel environments** because they're easier to make thread-safe.

**The Problem with Concurrent BSTs:**

Implementing a **thread-safe balanced BST** is notoriously difficult:
```java
// Red-Black Tree concurrent insert requires:
synchronized void insert(int value) {
    // 1. Insert node
    // 2. Fix violations (may need rotations)
    // 3. Rotations require locking ENTIRE subtrees
    // ⚠️ Complex locking = deadlocks + poor performance
}
```

**Why Skip Lists Win:**

1. **Probabilistic Structure** (no strict balancing)
2. **Local Modifications** (changes are localized)
3. **Lock-Free Algorithms** (or fine-grained locking)

**Visual:**
```
Skip List (multiple levels):
Level 3:  1 -----------------> 10
Level 2:  1 -------> 5 ------> 10
Level 1:  1 --> 3 -> 5 --> 8 -> 10
Level 0:  1->2->3->4->5->6->7->8->9->10

Inserting 6:
- Only affects nodes near 6
- No global rebalancing needed!
- Other threads can work on other parts
```

**Java Example:**
```java
import java.util.concurrent.ConcurrentSkipListMap;

// Thread-safe sorted map using Skip List
ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

// Multiple threads can safely insert/remove
ExecutorService executor = Executors.newFixedThreadPool(10);

for (int i = 0; i < 10; i++) {
    final int id = i;
    executor.submit(() -> {
        map.put(id, "Value " + id);  // Thread-safe!
    });
}

// Safe concurrent iteration (snapshot)
for (Map.Entry<Integer, String> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

**Comparison:**

| Feature | Skip List | Balanced BST |
|---------|-----------|--------------|
| **Concurrent Insert** | Easy (local changes) | Hard (global rotations) |
| **Lock Complexity** | Fine-grained or lock-free | Requires complex locking |
| **Performance** | O(log n) average | O(log n) guaranteed |
| **Implementation** | Simpler concurrent version | Very complex |

**When to Use:**
- ✅ High-concurrency sorted map/set
- ✅ Frequent updates from multiple threads
- ✅ Real-time leaderboards, price books, auction systems

**When NOT to Use:**
- ❌ Single-threaded (BST is simpler)
- ❌ Need deterministic O(log n) (Skip List is average-case)

---

### Question 5: How would you model a "Follower" relationship in a social-finance app?

**Level:** Mid to Senior (Graph Modeling)

**Answer:**

Use a **Directed Graph** implemented with an **Adjacency List**.

**Why Directed?**
- Following is **one-way**: A → B (A follows B) doesn't mean B → A

**Implementation:**
```java
class SocialGraph {
    // UserId → Set of UserIds they follow
    private Map<Integer, Set<Integer>> following = new HashMap<>();
    
    // UserId → Set of UserIds who follow them
    private Map<Integer, Set<Integer>> followers = new HashMap<>();
    
    // Follow: A follows B
    public void follow(int userA, int userB) {
        following.putIfAbsent(userA, new HashSet<>());
        followers.putIfAbsent(userB, new HashSet<>());
        
        following.get(userA).add(userB);  // A follows B
        followers.get(userB).add(userA);  // B is followed by A
        
        // O(1) average time!
    }
    
    // Unfollow
    public void unfollow(int userA, int userB) {
        if (following.containsKey(userA)) {
            following.get(userA).remove(userB);
        }
        if (followers.containsKey(userB)) {
            followers.get(userB).remove(userA);
        }
    }
    
    // Check if A follows B
    public boolean isFollowing(int userA, int userB) {
        return following.getOrDefault(userA, Collections.emptySet())
                        .contains(userB);
        // O(1) lookup!
    }
    
    // Get all followers of a user
    public Set<Integer> getFollowers(int userId) {
        return followers.getOrDefault(userId, Collections.emptySet());
    }
    
    // Get all users that userId follows
    public Set<Integer> getFollowing(int userId) {
        return following.getOrDefault(userId, Collections.emptySet());
    }
    
    // Get follower count (for social proof)
    public int getFollowerCount(int userId) {
        return followers.getOrDefault(userId, Collections.emptySet()).size();
    }
}
```

**Why HashSet?**
- **Uniqueness**: No duplicate followers
- **O(1) lookups**: Fast to check "does A follow B?"
- **O(1) add/remove**: Fast follow/unfollow

**Advanced Features:**

**1. Recommend Users (Find Friends of Friends)**
```java
public Set<Integer> recommendUsers(int userId) {
    Set<Integer> recommendations = new HashSet<>();
    Set<Integer> myFollowing = getFollowing(userId);
    
    // For each person I follow
    for (int friend : myFollowing) {
        // Get who they follow (friends of friends)
        for (int friendOfFriend : getFollowing(friend)) {
            // Recommend if I don't already follow them
            if (!myFollowing.contains(friendOfFriend) && 
                friendOfFriend != userId) {
                recommendations.add(friendOfFriend);
            }
        }
    }
    
    return recommendations;
}
```

**2. Find Influencers (Most Followers)**
```java
public List<Integer> getTopInfluencers(int limit) {
    return followers.entrySet().stream()
        .sorted((a, b) -> b.getValue().size() - a.getValue().size())
        .limit(limit)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
}
```

**3. Detect Mutual Followers**
```java
public boolean areMutualFollowers(int userA, int userB) {
    return isFollowing(userA, userB) && isFollowing(userB, userA);
}
```

**Space Complexity:** O(V + E) where V = users, E = follow relationships

**Time Complexity:**
- Follow/Unfollow: O(1)
- Check following: O(1)
- Get followers/following: O(1) to get set, O(k) to iterate (k = count)

**Real-World Use (Social-Finance):**
- **Stock Tip Sharing**: Users follow expert traders
- **Investment Clubs**: Track group memberships
- **Referral Networks**: Model who referred whom
- **Fraud Detection**: Analyze suspicious follow patterns

---

### Question 6: Explain HashMap "Treeification" threshold and why it's 8

**Level:** Mid

**Answer:**

**The Magic Number 8:**

Java uses a **threshold of 8** elements in a bucket before converting a LinkedList to a Red-Black Tree.

**Mathematical Reason (Poisson Distribution):**

Under **ideal random hashing**, the probability of having k elements in a bucket follows a Poisson distribution:

```
P(k elements) = (e^(-0.5) * 0.5^k) / k!

P(8) ≈ 0.00000006 (1 in 16 million!)
```

This means with a **good hash function**, having 8+ collisions in one bucket is **extremely rare**.

**Why Not Lower (4) or Higher (16)?**

**Too Low (e.g., 4):**
```java
// Tree overhead not worth it
Tree Node: 40 bytes per node (pointers, color, etc.)
List Node: 24 bytes per node

For 4 elements:
- LinkedList: 4 × 24 = 96 bytes
- Tree: 4 × 40 = 160 bytes
- Overhead: 67% more memory!
- Search savings: O(4) → O(log 4) = O(2) (minimal gain)
```

**Too High (e.g., 16):**
```java
// Suffering with slow LinkedList for too long
O(16) linear search vs O(log 16) = O(4) tree search
- 4x slower when collisions DO occur
```

**De-Treeification at 6:**

When size drops below **6**, tree converts back to LinkedList.

**Why 6 and not 8?**
- **Hysteresis**: Avoids "thrashing" (constant converting back-and-forth)
- If threshold was 8 for both, adding/removing 8th element would cause constant conversion

**Code Example:**
```java
// Force collisions to see treeification
class AlwaysCollide {
    @Override
    public int hashCode() { return 1; }  // All hash to 1!
    
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}

HashMap<AlwaysCollide, String> map = new HashMap<>();

// Add 9 elements (all collide)
for (int i = 0; i < 9; i++) {
    map.put(new AlwaysCollide(), "Value " + i);
}

// Internally:
// Elements 0-7: LinkedList (O(n) search)
// Element 8+: Converts to Red-Black Tree (O(log n) search)
```

---

### Question 7: How does BitSet save memory compared to boolean[]?

**Level:** Mid

**Answer:**

**Memory Comparison:**

**boolean[] array:**
```java
boolean[] flags = new boolean[1_000_000];
// Each boolean takes 1 BYTE (8 bits)
// Total: 1,000,000 bytes = ~1 MB
```

**BitSet:**
```java
BitSet bits = new BitSet(1_000_000);
// Each element takes 1 BIT
// Total: 1,000,000 bits = 125,000 bytes = ~122 KB
// Savings: 8x less memory!
```

**How It Works:**

BitSet stores bits in **long[] array** (each long = 64 bits):

```java
// Internally
class BitSet {
    long[] words;  // Each long holds 64 bits
    
    // To set bit 100:
    public void set(int bitIndex) {
        int wordIndex = bitIndex / 64;    // Which long? (100/64 = 1)
        int bitPosition = bitIndex % 64;  // Which bit in that long? (100%64 = 36)
        words[wordIndex] |= (1L << bitPosition);  // Set bit using OR
    }
    
    // To get bit 100:
    public boolean get(int bitIndex) {
        int wordIndex = bitIndex / 64;
        int bitPosition = bitIndex % 64;
        return (words[wordIndex] & (1L << bitPosition)) != 0;  // Check bit using AND
    }
}
```

**Real-World Use Case (Financial System):**

**Scenario:** Mark 10 million transactions for "stress test" status

```java
// Bad approach: boolean array
boolean[] isStressTest = new boolean[10_000_000];
// Memory: 10 MB

// Good approach: BitSet
BitSet isStressTest = new BitSet(10_000_000);
// Memory: 1.25 MB (8x savings!)

// Mark transactions 1000-2000 as stress test
for (int txnId = 1000; txnId <= 2000; txnId++) {
    isStressTest.set(txnId);
}

// Check if transaction 1500 is stress test
if (isStressTest.get(1500)) {
    // Apply stress test logic
}

// Bitwise operations (FAST!)
BitSet setA = new BitSet();
BitSet setB = new BitSet();
// ... populate sets ...

setA.and(setB);  // Intersection
setA.or(setB);   // Union
setA.xor(setB);  // Symmetric difference
```

**Performance:**
- Set/Get: **O(1)** (bit manipulation)
- Operations are **cache-friendly** (packed data)

**When to Use:**
- ✅ Large boolean arrays (> 10,000 elements)
- ✅ Prime number sieves
- ✅ Tracking millions of flags/states
- ✅ Bloom filters

---

### Question 8: Design an LRU Cache - Explain the Data Structure Choice

**Level:** Medium to Senior

**Answer:**

**Requirements:**
- Get(key): O(1)
- Put(key, value): O(1)
- Evict least recently used when full

**Solution: HashMap + Doubly LinkedList**

**Why This Combination?**

| Operation | HashMap | Doubly LinkedList |
|-----------|---------|-------------------|
| **Find key** | O(1) ✅ | O(n) ❌ |
| **Update order** | ❌ | O(1) ✅ (if have node reference) |
| **Remove from middle** | ❌ | O(1) ✅ (if have node reference) |
| **Find LRU item** | ❌ | O(1) ✅ (tail) |

**Together: O(1) for everything!**

**Implementation:**
```java
class LRUCache {
    class Node {
        int key, value;
        Node prev, next;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Map<Integer, Node> cache = new HashMap<>();
    private Node head = new Node(0, 0);  // Dummy head
    private Node tail = new Node(0, 0);  // Dummy tail
    private int capacity;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (!cache.containsKey(key)) return -1;
        
        Node node = cache.get(key);
        // Move to front (most recently used)
        remove(node);
        addToFront(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            remove(cache.get(key));
        }
        
        Node node = new Node(key, value);
        addToFront(node);
        cache.put(key, node);
        
        // Evict LRU if over capacity
        if (cache.size() > capacity) {
            Node lru = tail.prev;  // Least recently used
            remove(lru);
            cache.remove(lru.key);
        }
    }
    
    // O(1) remove (we have node reference!)
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    // O(1) add to front (most recent)
    private void addToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
```

**Visual:**
```
Most Recent                    Least Recent
   HEAD → [K1:V1] ⇄ [K2:V2] ⇄ [K3:V3] ← TAIL

Get(K2):
   HEAD → [K2:V2] ⇄ [K1:V1] ⇄ [K3:V3] ← TAIL
          (moved to front)

Put(K4:V4) when full:
   HEAD → [K4:V4] ⇄ [K2:V2] ⇄ [K1:V1] ← TAIL
          (K3 evicted)
```

**Why Not Just HashMap?**
- Can't track access order

**Why Not Just LinkedList?**
- O(n) to find elements

**Real-World Use:**
- Database query cache
- Browser cache
- Redis cache implementation
- CDN cache

---

### Question 9: Explain the difference between ConcurrentHashMap and Collections.synchronizedMap()

**Level:** Senior (Concurrency)

**Answer:**

**Key Difference: Lock Granularity**

**Collections.synchronizedMap():**
```java
Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());

// Internally wraps EVERY method with synchronized
synchronized Map<String, Integer> {
    public Integer get(Object key) {
        synchronized (mutex) {  // ENTIRE map locked!
            return m.get(key);
        }
    }
    
    public Integer put(String key, Integer value) {
        synchronized (mutex) {  // ENTIRE map locked!
            return m.put(key, value);
        }
    }
}
```

**Problem:** Only ONE thread can access map at a time (even for reads!)

**ConcurrentHashMap:**
```java
// Uses lock striping (multiple locks for different segments)
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// Internally divides into segments (Java 7) or buckets (Java 8+)
// Multiple threads can access different segments simultaneously!
```

**Performance Comparison:**

| Feature | synchronizedMap | ConcurrentHashMap |
|---------|-----------------|-------------------|
| **Read Concurrency** | ❌ Locked | ✅ Lock-free reads |
| **Write Concurrency** | ❌ One at a time | ✅ Multiple (different buckets) |
| **Null Keys/Values** | ✅ Allowed | ❌ Not allowed |
| **Iteration** | Need external sync | ✅ Weakly consistent |
| **Performance** | Poor under contention | Excellent |

**Code Comparison:**
```java
// Benchmark: 10 threads, 1M operations each

// synchronizedMap
Map<Integer, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
long start = System.currentTimeMillis();
runConcurrentTest(syncMap, 10, 1_000_000);
// Time: ~5000ms (threads wait for lock)

// ConcurrentHashMap
ConcurrentHashMap<Integer, Integer> concMap = new ConcurrentHashMap<>();
start = System.currentTimeMillis();
runConcurrentTest(concMap, 10, 1_000_000);
// Time: ~800ms (threads run in parallel!)
```

**ConcurrentHashMap Special Methods:**
```java
ConcurrentHashMap<String, Integer> counts = new ConcurrentHashMap<>();

// Atomic increment (thread-safe)
counts.compute("key", (k, v) -> v == null ? 1 : v + 1);

// Atomic get-or-create
counts.computeIfAbsent("key", k -> expensiveComputation());

// Atomic conditional update
counts.replace("key", oldValue, newValue);

// Atomic merge
counts.merge("key", 1, Integer::sum);  // Increment by 1
```

**When to Use:**
- **ConcurrentHashMap**: High-concurrency scenarios (web servers, caches)
- **synchronizedMap**: Low contention, need null support

---

### Question 10: How would you implement a Rate Limiter using data structures?

**Level:** Senior (System Design)

**Answer:**

**Requirement:** Allow max N requests per time window (e.g., 100 requests/minute)

**Solution: Sliding Window with TreeMap**

```java
class RateLimiter {
    private TreeMap<Long, Integer> requestCounts = new TreeMap<>();
    private int maxRequests;
    private long windowMs;
    
    public RateLimiter(int maxRequests, long windowMs) {
        this.maxRequests = maxRequests;
        this.windowMs = windowMs;
    }
    
    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();
        long windowStart = now - windowMs;
        
        // Remove old requests outside window
        requestCounts.headMap(windowStart).clear();
        
        // Count requests in current window
        int count = requestCounts.values().stream()
                                 .mapToInt(Integer::intValue)
                                 .sum();
        
        if (count < maxRequests) {
            requestCounts.put(now, requestCounts.getOrDefault(now, 0) + 1);
            return true;  // Allow
        }
        
        return false;  // Rate limited!
    }
}

// Usage
RateLimiter limiter = new RateLimiter(100, 60_000);  // 100 req/min

if (limiter.allowRequest()) {
    processRequest();
} else {
    return "429 Too Many Requests";
}
```

**Why TreeMap?**
- ✅ Sorted by timestamp
- ✅ `headMap(windowStart)` gets all old entries in O(log n)
- ✅ `.clear()` removes them efficiently

**Alternative: Token Bucket (More Memory Efficient)**

```java
class TokenBucket {
    private double tokens;
    private double maxTokens;
    private double refillRate;  // tokens per second
    private long lastRefill;
    
    public TokenBucket(double maxTokens, double refillRate) {
        this.tokens = maxTokens;
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.lastRefill = System.currentTimeMillis();
    }
    
    public synchronized boolean tryConsume() {
        refill();
        
        if (tokens >= 1.0) {
            tokens -= 1.0;
            return true;
        }
        
        return false;
    }
    
    private void refill() {
        long now = System.currentTimeMillis();
        double secondsPassed = (now - lastRefill) / 1000.0;
        tokens = Math.min(maxTokens, tokens + secondsPassed * refillRate);
        lastRefill = now;
    }
}

// 100 requests per minute = 100/60 = 1.67 tokens/second
TokenBucket bucket = new TokenBucket(100, 1.67);
```

**Comparison:**

| Approach | Memory | Precision | Implementation |
|----------|--------|-----------|----------------|
| **Sliding Window** | O(n) requests | Exact | TreeMap |
| **Token Bucket** | O(1) | Approximate | Simple variables |

**Real-World:**
- API rate limiting (Stripe, Twitter, AWS)
- DDoS protection
- Resource throttling

---

## 🏦 Financial Engineering Use Cases

### Real-World Applications in Finance

| Data Structure | Financial Use Case | Why This Structure? |
|----------------|-------------------|---------------------|
| **BlockingQueue** | High-scale lending: Producer-Consumer pattern for loan processing | Thread-safe, handles backpressure, FIFO order |
| **BitSet** | Transaction flags: Mark 10M transactions (approved/declined/pending) | 8x memory savings vs boolean[], O(1) operations |
| **ConcurrentSkipListMap** | Real-time order book (price → quantity) | Thread-safe sorted map, concurrent reads/writes |
| **TreeMap** | Stock price history (timestamp → price) | Sorted by time, range queries (get prices between T1 and T2) |
| **ConcurrentHashMap** | Account balance cache (accountId → balance) | High concurrency, lock-free reads |
| **PriorityQueue** | Loan applications sorted by credit score | Process high-credit applicants first |
| **Deque** | Undo/Redo in trading platform | O(1) add/remove from both ends |
| **Segment Tree** | Portfolio analytics: Sum of holdings in range | O(log n) range sum queries |

---

## 🎯 Quick Interview Prep Summary

### Must-Know Trade-Offs

**AVL vs Red-Black:**
- AVL: More lookups → Choose AVL
- Red-Black: More updates → Choose RB (Java's choice)

**HashMap vs TreeMap:**
- HashMap: Unordered, O(1) ops
- TreeMap: Sorted, O(log n) ops, range queries

**ArrayList vs LinkedList:**
- ArrayList: Random access O(1), good cache locality
- LinkedList: Insert/delete at ends O(1), poor cache

**BlockingQueue vs Regular Queue:**
- BlockingQueue: Thread-safe, handles backpressure
- Regular Queue: Single-threaded, faster

**Skip List vs BST:**
- Skip List: Better for concurrent access
- BST: Better for single-threaded, deterministic

### Common Interview Mistakes to Avoid

❌ Saying "HashMap is always O(1)"
- ✅ Correct: "O(1) average, O(n) worst case, O(log n) after treeification"

❌ Using ArrayList for frequent middle insertions
- ✅ Use LinkedList or ArrayDeque

❌ Not mentioning thread-safety when asked about concurrency
- ✅ Discuss ConcurrentHashMap, CopyOnWriteArrayList, BlockingQueue

❌ Implementing your own LinkedList when Deque exists
- ✅ Use ArrayDeque (faster, less memory)

❌ Forgetting about BitSet for boolean flags
- ✅ Mention memory savings for large boolean arrays

### Interview Framework (REACTO)

1. **Repeat** the question
2. **Examples** - walk through cases
3. **Approach** - explain your solution
4. **Code** - implement clearly
5. **Test** - walk through test cases
6. **Optimize** - discuss trade-offs

---

## �🎓 LeetCode Problem Mapping

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
