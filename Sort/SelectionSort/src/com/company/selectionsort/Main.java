package com.company.selectionsort;

public class Main {

    public static void main(String[] args) {

        int[] intArray = {20, 35, -15, 7, 55, 1, -22};
        // Selection sort algorithm
        // 1. The selection sort algorithm divides the array into two parts: sorted and unsorted
        // 2. The sorted part is built up from left to right
        // 3. The unsorted part is built up from right to left
        // 4. The algorithm repeatedly selects the largest element from the unsorted part and swaps it with the last element of the unsorted part
        // 5. The algorithm continues until the unsorted part is empty
        // 6. The time complexity of the selection sort algorithm is O(n^2), where n is the number of elements in the array
        // 7. The space complexity of the selection sort algorithm is O(1), where n is the number of elements in the array
        // 8. The selection sort algorithm is not a stable sort, meaning that the relative order of equal elements may not be preserved
        for( int lastUnsortedIndex = intArray.length-1; lastUnsortedIndex>0; lastUnsortedIndex--){
            int largest = 0;
            for (int i=0;i<=lastUnsortedIndex; i++){
                if(intArray[i]>intArray[largest]){
                    largest=i;
                }
            }
            swap(intArray, largest, lastUnsortedIndex);


        }






        for(int i =0;i<intArray.length; i++){
            System.out.println(intArray[i]);
        }




    }

    public static void swap(int[] array, int i, int j){
        if (i==j){
            return;
        }
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
