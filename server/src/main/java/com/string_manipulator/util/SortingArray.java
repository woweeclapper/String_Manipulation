package com.string_manipulator.util;

/* @author Joe Nguyen */

import java.util.Arrays;


public class SortingArray {

//call sort methods to sort small to big
    public static int[] sortAscending(int[] array) {
        int[] sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray);
        return sortedArray;
    }

//reverse whatever in ascending to get descending
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

}
