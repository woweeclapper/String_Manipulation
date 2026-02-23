package com.string_manipulator.util;

import java.util.ArrayList;

public class SeparateArray {

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
