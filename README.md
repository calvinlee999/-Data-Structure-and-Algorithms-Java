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

### 🕐 Understanding Time Complexity (Big O)

**Think of it as "How much slower does it get when you add more items?"**

- **O(1)** = "Constant" - Always the same speed! 🚀
  - Example: Getting item from array by index
  
- **O(log n)** = "Logarithmic" - Gets slower, but not by much 😊
  - Example: Binary search (like finding a word in a dictionary)
  
- **O(n)** = "Linear" - Double the items = double the time 📈
  - Example: Looking through a list one by one
  
- **O(n log n)** = "Linearithmic" - Faster than O(n²), slower than O(n) ⚡
  - Example: Good sorting algorithms (Merge Sort, Quick Sort)
  
- **O(n²)** = "Quadratic" - Gets SLOW with lots of items! 🐌
  - Example: Bubble Sort, Insertion Sort (for large lists)

**Rule of thumb:** Smaller is faster! O(1) < O(log n) < O(n) < O(n log n) < O(n²)

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
