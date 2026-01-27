package com.stringmanipulator.util.SumLogic;

public class DoubleSumArray {
    public static double findSum(double[] array, int length) {
        if (length == 1) {
            return array[0];
        }
        return (findSum(array, length - 1) + array[length - 1]);
    }
}
