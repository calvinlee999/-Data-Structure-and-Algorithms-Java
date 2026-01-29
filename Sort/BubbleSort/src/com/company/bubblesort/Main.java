package com.company.bubblesort;

public class Main {

    public static void main(String[] args) {

        int[] intArray = {20, 35, -15, 7, 55, 1, -22};
        // Bubble sort algorithm
        // 1. Compare the first two elements of the array with unsorted partition index
        // 2. If the first element is greater than the second element, swap them
        // 3. Move to the next pair of elements and repeat the process
        // 4. Repeat the process until the array is sorted
        // 5. The outer loop will run n-1 times, where n is the number of elements in the array
        // 6. The inner loop will run n-i-1 times, where i is the current iteration of the outer loop
        // 7. The inner loop will compare the first element with the second element, the second element with the third element, and so on
        // 8. The inner loop will swap the elements if the first element is greater than the second element
        // 9. The outer loop will repeat the process until the array is sorted
        // 10. The time complexity of the bubble sort algorithm is O(n^2), where n is the number of elements in the array
        for(int lastUnsortedIndex = intArray.length-1; lastUnsortedIndex > 0; lastUnsortedIndex--){
            for(int i =0;i< lastUnsortedIndex; i++){
                if (intArray[i]> intArray[i+1]){
                    swap(intArray, i, i+1);
                }
            }
        }
        for(int i =0;i<intArray.length; i++){
            System.out.println(intArray[i]);
        }
    }
    // Swap method to swap two elements in an array
    // 1. The swap method takes an array and two indices as parameters
    // 2. The method checks if the two indices are the same
    // 3. If the indices are the same, the method returns
    // 4. If the indices are different, the method swaps the elements at the two indices
    // 5. The method uses a temporary variable to store the value of the first element
    // 6. The method assigns the value of the second element to the first element
    // 7. The method assigns the value of the temporary variable to the second element
    // 8. The time complexity of the swap method is O(1), where n is the number of elements in the array
    public static void swap(int[] array, int i, int j){
        if (i==j){
            return;
        }
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
