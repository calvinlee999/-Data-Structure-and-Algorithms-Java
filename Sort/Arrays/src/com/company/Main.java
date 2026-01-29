package com.company;

public class Main {

    public static void main(String[] args) {
	    // Creating an array of 7 elements
        int[] intArray = new int[7];

	    intArray[0] = 20;
        intArray[1] = 35;
        intArray[2] = -15;
        intArray[3] = 7;
        intArray[4] = 55;
        intArray[5] = 1;
        intArray[6] = -22;

        int index = -1;
        //  Finding the index of 7 in the array
        // 1. Using a for loop
        // 2. Using a for-each loop
        // 3. Using a while loop
        for (int i=0;i<intArray.length;i++){
        // System.out.println(intArray[i]);
            if (intArray[i]==7){
                index =i;
                break;
            }
        }
        System.out.println("7 is in the index = "+ index);

    }
}
