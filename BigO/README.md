# Big O Notation Practice

This folder contains practical examples demonstrating different time and space complexities.

## 📚 What's Inside

The `Main.java` file demonstrates **6 different time complexities** with working examples:

### 1. **O(1) - Constant Time** ⚡
- `getFirstElement()` - Access array element
- `getArrayLength()` - Get array length
- **Real-world:** HashMap lookups, array access by index

### 2. **O(log n) - Logarithmic Time** 📉
- `binarySearch()` - Search in sorted array
- **Real-world:** Binary search trees, finding elements in sorted data

### 3. **O(n) - Linear Time** 📏
- `sumArray()` - Sum all elements
- `findMax()` - Find maximum element
- `linearSearch()` - Search unsorted array
- **Real-world:** Iterating through lists, finding items

### 4. **O(n log n) - Linearithmic Time** 📊
- `mergeSort()` - Efficient sorting algorithm
- **Real-world:** Merge sort, quick sort (average case), heap sort

### 5. **O(n²) - Quadratic Time** 📐
- `bubbleSort()` - Simple sorting algorithm
- `printAllPairs()` - Generate all pairs
- **Real-world:** Nested loops, comparing all pairs

### 6. **O(2^n) - Exponential Time** 💥
- `fibonacci()` - Recursive Fibonacci (inefficient)
- **Real-world:** Recursive algorithms without memoization, subset generation

## 🚀 How to Run

### Option 1: From BigO/src directory
```bash
cd BigO/src
javac com/company/bigo/Main.java
java com.company.bigo.Main
```

### Option 2: From project root
```bash
cd BigO/src && javac com/company/bigo/Main.java && java com.company.bigo.Main
```

## 📊 Sample Output

```
=== BIG O NOTATION PRACTICE ===

1. O(1) - Constant Time:
   First element: 5
   Array length: 9

2. O(log n) - Logarithmic Time:
   Binary Search for 7: found at index 6

3. O(n) - Linear Time:
   Sum of array: 45
   Max element: 9

4. O(n log n) - Linearithmic Time:
   Before Merge Sort: [64, 34, 25, 12, 22, 11, 90]
   After Merge Sort:  [11, 12, 22, 25, 34, 64, 90]

5. O(n²) - Quadratic Time:
   Before Bubble Sort: [64, 34, 25, 12, 22, 11, 90]
   After Bubble Sort:  [11, 12, 22, 25, 34, 64, 90]

6. O(2^n) - Exponential Time:
   Fibonacci(10) = 55

=== PERFORMANCE COMPARISON (n=1000) ===
O(1):      208 ns
O(log n):  916 ns
O(n):      18084 ns
O(n log n): 518125 ns
O(n²):     154041 ns (n=100)
```

## 📖 Learning Points

### Time Complexity Ranking (Best to Worst)
1. **O(1)** - Constant ⚡ BEST
2. **O(log n)** - Logarithmic 📉
3. **O(n)** - Linear 📏
4. **O(n log n)** - Linearithmic 📊
5. **O(n²)** - Quadratic 📐
6. **O(2^n)** - Exponential 💥 WORST

### Space Complexity Examples
The code also includes space complexity examples:
- **O(1)** - `sumArrayConstantSpace()` - Uses only a few variables
- **O(n)** - `copyArray()` - Creates a copy of the array
- **O(n²)** - `create2DArray()` - Creates a 2D matrix

## 🎯 Practice Tips

1. **Identify the loops**: Count how many times the code runs
   - One loop = O(n)
   - Nested loops = O(n²)
   - Dividing in half = O(log n)

2. **Ignore constants**: O(2n) = O(n), O(n + 100) = O(n)

3. **Drop non-dominant terms**: O(n² + n) = O(n²)

4. **Consider best, average, worst cases**:
   - Binary Search: Best O(1), Average/Worst O(log n)
   - Quick Sort: Best/Avg O(n log n), Worst O(n²)

## 🔗 Related LeetCode Problems

Practice Big O analysis with these problems:
- **O(1)**: Two Sum (using HashMap)
- **O(log n)**: Binary Search, Search Insert Position
- **O(n)**: Maximum Subarray, Best Time to Buy and Sell Stock
- **O(n log n)**: Merge K Sorted Lists, Sort Colors
- **O(n²)**: 3Sum, Container With Most Water

## 📝 Interview Tips

When analyzing Big O in interviews:
1. **Always state your assumptions** (e.g., "Assuming n is the array length...")
2. **Discuss both time AND space complexity**
3. **Mention trade-offs** (e.g., "We can improve time from O(n²) to O(n) using a HashMap, but space increases to O(n)")
4. **Use the performance comparison** to show real impact of different complexities
