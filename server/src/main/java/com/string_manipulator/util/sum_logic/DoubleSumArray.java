package com.string_manipulator.util.sum_logic;

public class DoubleSumArray {

    public static double findSum(double[] array, int length) {
        if (length == 1) {
            return array[0];
        }

        //using recursion in this design context is fine, but I see the limitation now
        return (findSum(array, length - 1) + array[length - 1]);
    }
}
