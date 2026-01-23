package com.stringmanipulator.util;

/* @author Joe Nguyen */

import java.util.Scanner;

public class RecursiveString {

    static int findSum(int[] array, int length) {
        if (length <= 0)
            return 0;
        return (findSum(array, length - 1) + array[length - 1]);
    }

    public void Sum() {
        Scanner scan = new Scanner(System.in);
        String input;

        do {
            // Define the array size
            System.out.println("Enter the NUMBER of elements: ");
            String size = scan.nextLine();
            int arraySize = Integer.parseInt(size);
            int[] array = new int[arraySize];

            // Inputting the elements into the array
            System.out.println("Enter the elements to calculate: ");
            for (int i = 0; i < array.length; i++) {
                array[i] = scan.nextInt();
            }
            scan.nextLine(); // this is to prevent the token return an empty string in the next call

            System.out.println("The sum of the array is: " + findSum(array, array.length));

            // Asking the user if they would like to continue
            System.out.println("Would you like to continue? (Y or Yes)");
            input = scan.nextLine();
            input = input.toLowerCase();

        } while ("yes".equals(input) || "y".equals(input));

        //scan.close();
    }
}