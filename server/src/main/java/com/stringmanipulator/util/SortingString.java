package com.stringmanipulator.util;

/* @author Joe Nguyen */

import java.util.ArrayList;
import java.util.Arrays;

public class SortingString {

    public static int[] sortAscending(int[] array) {
        int[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray);
        return sortedArray;
    }

    public static int[] sortDescending(int[] array) {
        int[] sortedArray = sortAscending(array);
        int last = sortedArray.length - 1;
        int middle = sortedArray.length / 2;

        for (int i = 0; i <= middle; i++) {
            int temp = sortedArray[i];
            sortedArray[i] = sortedArray[last - i];
            sortedArray[last - i] = temp;
        }

        return sortedArray;
    }

    public static ArrayList<Integer> separateEvenNumbers(int[] array) {
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        for (int num : array) {
            if (num % 2 == 0) {
                evenNumbers.add(num);
            }
        }
        return evenNumbers;
    }

    public static ArrayList<Integer> separateOddNumbers(int[] array) {
        ArrayList<Integer> oddNumbers = new ArrayList<>();
        for (int num : array) {
            if (num % 2 != 0) {
                oddNumbers.add(num);
            }
        }
        return oddNumbers;
    }

    public static void separateEvenAndOdd(int[] array, ArrayList<Integer> evenNumbers, ArrayList<Integer> oddNumbers) {
        for (int num : array) {
            if (num % 2 == 0) {
                evenNumbers.add(num);
            } else {
                oddNumbers.add(num);
            }
        }
    }

    // Double array sorting methods
    public static double[] sortAscending(double[] array) {
        double[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray);
        return sortedArray;
    }

    public static double[] sortDescending(double[] array) {
        double[] sortedArray = sortAscending(array);
        int last = sortedArray.length - 1;
        int middle = sortedArray.length / 2;

        for (int i = 0; i <= middle; i++) {
            double temp = sortedArray[i];
            sortedArray[i] = sortedArray[last - i];
            sortedArray[last - i] = temp;
        }
        return sortedArray;
    }

    // Double separation logic
    public static void separatePositiveAndNegative(double[] array, ArrayList<Double> positiveNumbers, ArrayList<Double> negativeNumbers) {
        for (double num : array) {
            if (num >= 0) {
                positiveNumbers.add(num);
            } else {
                negativeNumbers.add(num);
            }
        }
    }
}
