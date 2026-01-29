package com.company.insertionsort;

public class Main {

    public static void main(String[] args) {

        int[] intArray = {20, 35, -15, 7, 55, 1, -22};
        // Insertion sort algorithm
        // 1. The insertion sort algorithm divides the array into two parts: sorted and unsorted
        // 2. The sorted part is built up from left to right
        // 3. The unsorted part is built up from right to left
        // 4. The algorithm repeatedly selects the first element from the unsorted part and inserts it 
        // into the correct position in the sorted part
        // 5. The algorithm continues until the unsorted part is empty
        // 6. The time complexity of the insertion sort algorithm is O(n^2), where n is the number of 
        // elements in the array`
        // 7. The space complexity of the insertion sort algorithm is O(1), where n is the number of
        // elements in the array
        for (int firstUnsortedIndex = 1; firstUnsortedIndex< intArray.length; firstUnsortedIndex++){

            int newElement = intArray[firstUnsortedIndex];

            int i ;

            for (i=firstUnsortedIndex; i>0 && intArray[i-1]>newElement; i--){

                intArray[i] = intArray[i-1];

            }

            intArray[i]=newElement;


        }



        for(int i =0;i<intArray.length; i++){
            System.out.println(intArray[i]);
        }
    }
}
