package com.stringmanipulator.util;

/* @author Joe Nguyen */

public class RecursiveString {

    public static int findSum(int[] array, int length) {
        if (length == 1) {
            return array[0];
        }
        return (findSum(array, length - 1) + array[length - 1]);
    }

}