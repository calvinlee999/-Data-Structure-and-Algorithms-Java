package com.company.bigo;

import java.util.Arrays;
//import java.util.HashMap;
import java.util.Map;

/**
 * Big O Notation Practice Examples
 * 
 * This class demonstrates different time complexities with practical examples.
 * Each method is annotated with its Big O complexity.
 */
public class Main {

    public static void main(String[] args) {
        Main bigO = new Main();
        
        System.out.println("=== BIG O NOTATION PRACTICE ===\n");
        
        // O(1) - Constant Time
        System.out.println("1. O(1) - Constant Time:");
        int[] array = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        System.out.println("   First element: " + bigO.getFirstElement(array));
        System.out.println("   Array length: " + bigO.getArrayLength(array));
        System.out.println();
        
        // O(log n) - Logarithmic Time
        System.out.println("2. O(log n) - Logarithmic Time:");
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int target = 7;
        int index = bigO.binarySearch(sortedArray, target);
        System.out.println("   Binary Search for " + target + ": found at index " + index);
        System.out.println();
        
        // O(n) - Linear Time
        System.out.println("3. O(n) - Linear Time:");
        System.out.println("   Sum of array: " + bigO.sumArray(array));
        System.out.println("   Max element: " + bigO.findMax(array));
        System.out.println();
        
        // O(n log n) - Linearithmic Time
        System.out.println("4. O(n log n) - Linearithmic Time:");
        int[] unsorted = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("   Before Merge Sort: " + Arrays.toString(unsorted));
        int[] sorted = bigO.mergeSort(unsorted);
        System.out.println("   After Merge Sort:  " + Arrays.toString(sorted));
        System.out.println();
        
        // O(n²) - Quadratic Time
        System.out.println("5. O(n²) - Quadratic Time:");
        int[] bubbleArray = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("   Before Bubble Sort: " + Arrays.toString(bubbleArray));
        bigO.bubbleSort(bubbleArray);
        System.out.println("   After Bubble Sort:  " + Arrays.toString(bubbleArray));
        System.out.println();
        
        // O(2^n) - Exponential Time
        System.out.println("6. O(2^n) - Exponential Time:");
        int n = 10;
        System.out.println("   Fibonacci(" + n + ") = " + bigO.fibonacci(n));
        System.out.println();
        
        // Comparison
        System.out.println("=== PERFORMANCE COMPARISON (n=1000) ===");
        bigO.demonstrateComplexities();
    }

    // ==================== O(1) - Constant Time ====================
    /**
     * O(1) - Always takes same time regardless of input size
     */
    public int getFirstElement(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }
        return array[0];  // Single operation, always O(1)
    }
    
    public int getArrayLength(int[] array) {
        return array.length;  // Single operation, O(1)
    }
    
    public void addToHashMap(Map<String, Integer> map, String key, int value) {
        map.put(key, value);  // Average O(1)
    }

    // ==================== O(log n) - Logarithmic Time ====================
    /**
     * O(log n) - Binary Search
     * Divides search space in half each iteration
     */
    public int binarySearch(int[] sortedArray, int target) {
        int left = 0;
        int right = sortedArray.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (sortedArray[mid] == target) {
                return mid;
            } else if (sortedArray[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;  // Not found
    }

    // ==================== O(n) - Linear Time ====================
    /**
     * O(n) - Must visit each element once
     */
    public int sumArray(int[] array) {
        int sum = 0;
        for (int num : array) {  // Loops through all n elements
            sum += num;
        }
        return sum;
    }
    
    public int findMax(int[] array) {
        if (array == null || array.length == 0) {
            return Integer.MIN_VALUE;
        }
        
        int max = array[0];
        for (int i = 1; i < array.length; i++) {  // O(n)
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
    
    public boolean linearSearch(int[] array, int target) {
        for (int num : array) {  // O(n) - worst case checks all elements
            if (num == target) {
                return true;
            }
        }
        return false;
    }

    // ==================== O(n log n) - Linearithmic Time ====================
    /**
     * O(n log n) - Merge Sort
     * Divides array (log n) and merges (n) at each level
     */
    public int[] mergeSort(int[] array) {
        if (array.length <= 1) {
            return array;
        }
        
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        
        return merge(mergeSort(left), mergeSort(right));
    }
    
    private int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;
        
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        
        while (i < left.length) {
            result[k++] = left[i++];
        }
        
        while (j < right.length) {
            result[k++] = right[j++];
        }
        
        return result;
    }

    // ==================== O(n²) - Quadratic Time ====================
    /**
     * O(n²) - Bubble Sort
     * Nested loops, each iterating through n elements
     */
    public void bubbleSort(int[] array) {
        int n = array.length;
        
        for (int i = 0; i < n - 1; i++) {           // Outer loop: n times
            for (int j = 0; j < n - i - 1; j++) {   // Inner loop: n times
                if (array[j] > array[j + 1]) {
                    // Swap
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
    
    /**
     * O(n²) - Find all pairs
     */
    public void printAllPairs(int[] array) {
        for (int i = 0; i < array.length; i++) {       // O(n)
            for (int j = 0; j < array.length; j++) {   // O(n)
                System.out.println("(" + array[i] + ", " + array[j] + ")");
            }
        }
    }

    // ==================== O(2^n) - Exponential Time ====================
    /**
     * O(2^n) - Fibonacci (recursive)
     * Each call branches into 2 more calls
     */
    public int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // ==================== Performance Demonstration ====================
    /**
     * Demonstrates the performance difference between complexities
     */
    public void demonstrateComplexities() {
        int n = 1000;
        int[] testArray = new int[n];
        for (int i = 0; i < n; i++) {
            testArray[i] = i;
        }
        
        // O(1)
        long start = System.nanoTime();
        getFirstElement(testArray);
        long end = System.nanoTime();
        System.out.println("O(1):      " + (end - start) + " ns");
        
        // O(log n)
        start = System.nanoTime();
        binarySearch(testArray, 500);
        end = System.nanoTime();
        System.out.println("O(log n):  " + (end - start) + " ns");
        
        // O(n)
        start = System.nanoTime();
        sumArray(testArray);
        end = System.nanoTime();
        System.out.println("O(n):      " + (end - start) + " ns");
        
        // O(n log n)
        int[] copy1 = Arrays.copyOf(testArray, testArray.length);
        start = System.nanoTime();
        mergeSort(copy1);
        end = System.nanoTime();
        System.out.println("O(n log n): " + (end - start) + " ns");
        
        // O(n²) - use smaller array to avoid long wait
        int[] smallArray = new int[100];
        for (int i = 0; i < 100; i++) {
            smallArray[i] = 100 - i;
        }
        start = System.nanoTime();
        bubbleSort(smallArray);
        end = System.nanoTime();
        System.out.println("O(n²):     " + (end - start) + " ns (n=100)");
        
        System.out.println("\n⚠️  Notice: As n grows, the difference becomes more dramatic!");
    }

    // ==================== Space Complexity Examples ====================
    /**
     * SPACE COMPLEXITY EXAMPLES
     */
    
    // O(1) Space - Constant space
    public int sumArrayConstantSpace(int[] array) {
        int sum = 0;  // Only one variable
        for (int num : array) {
            sum += num;
        }
        return sum;
    }
    
    // O(n) Space - Linear space
    public int[] copyArray(int[] array) {
        int[] copy = new int[array.length];  // New array of size n
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }
        return copy;
    }
    
    // O(n²) Space - Quadratic space
    public int[][] create2DArray(int n) {
        return new int[n][n];  // n × n matrix
    }
}
