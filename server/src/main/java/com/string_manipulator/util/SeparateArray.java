package com.string_manipulator.util;

import java.util.List;


/*Change the data structure to List instead of ArrayList, change back if something breaks*/
public class SeparateArray {

    private SeparateArray() {
        /* This utility class should not be instantiated */
    }

    public static void separateEvenAndOdd(int[] array, List<Integer> evenNumbers, List<Integer> oddNumbers) {
        for (int num : array) {
            if (num % 2 == 0) {
                evenNumbers.add(num);
            } else {
                oddNumbers.add(num);
            }
        }
    }

    public static void separateEvenAndOdd(double[] array, List<Double> evenNumbers, List<Double> oddNumbers) {
        for (double num : array) {
            if (num % 2 == 0) {
                evenNumbers.add(num);
            } else {
                oddNumbers.add(num);
            }
        }
    }

    public static void separatePositiveAndNegative(int[] array, List<Integer> positiveNumbers, List<Integer> negativeNumbers) {
        for (int num : array) {
            if (num >= 0) {
                positiveNumbers.add(num);
            } else {
                negativeNumbers.add(num);
            }
        }
    }

    //added in to handle negative zero
    //may have to move it to service layer since this is a validation
    public static void separatePositiveAndNegative(double[] array, List<Double> positiveNumbers, List<Double> negativeNumbers) {
        for (double num : array) {
            if (num > 0 || (num == 0.0 && Double.doubleToLongBits(num) >= 0)) {
                positiveNumbers.add(num);
            } else {
                negativeNumbers.add(num);
            }
        }
    }

}
