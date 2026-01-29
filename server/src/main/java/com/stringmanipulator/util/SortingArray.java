package com.stringmanipulator.util;

/* @author Joe Nguyen */

import java.util.ArrayList;
import java.util.Arrays;


public class SortingArray {


    public static int[] sortAscending(int[] array) {
        int[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray);
        return sortedArray;
    }

    public static int[] sortDescending(int[] array) {
        int[] sortedArray = sortAscending(array);
        int last = sortedArray.length - 1;
        int middle = sortedArray.length / 2;

        for (int i = 0; i < middle; i++) {
            int temp = sortedArray[i];
            sortedArray[i] = sortedArray[last - i];
            sortedArray[last - i] = temp;
        }

        return sortedArray;
    }

    public static double[] sortAscending(double[] array) {
        double[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray);
        return sortedArray;
    }

    public static double[] sortDescending(double[] array) {
        double[] sortedArray = sortAscending(array);
        int last = sortedArray.length - 1;
        int middle = sortedArray.length / 2;

        for (int i = 0; i < middle; i++) {
            double temp = sortedArray[i];
            sortedArray[i] = sortedArray[last - i];
            sortedArray[last - i] = temp;
        }
        return sortedArray;
    }

    /**************************************************************************/

    public static void separateEvenAndOdd(int[] array, ArrayList<Integer> evenNumbers, ArrayList<Integer> oddNumbers) {
        for (int num : array) {
            if (num % 2 == 0) {
                evenNumbers.add(num);
            } else {
                oddNumbers.add(num);
            }
        }
    }

    public static void separateEvenAndOdd(double[] array, ArrayList<Double> evenNumbers, ArrayList<Double> oddNumbers) {
        for (double num : array) {
            if (num % 2 == 0) {
                evenNumbers.add(num);
            } else {
                oddNumbers.add(num);
            }
        }
    }

    public static void separatePositiveAndNegative(int[] array, ArrayList<Integer> positiveNumbers, ArrayList<Integer> negativeNumbers) {
        for (int num : array) {
            if (num >= 0) {
                positiveNumbers.add(num);
            } else {
                negativeNumbers.add(num);
            }
        }
    }

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
