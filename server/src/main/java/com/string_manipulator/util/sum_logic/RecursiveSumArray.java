package com.string_manipulator.util.sum_logic;

/* @author Joe Nguyen */

public class RecursiveSumArray {

    public static int findSum(int[] array, int length) {
        if (length == 1) {
            return array[0];
        }

        if (length <= 0){
            throw new ArrayIndexOutOfBoundsException("Invalid length: "+ length);
        }

        //using recursion in this design context is fine, but I see the limitation now
        return (findSum(array, length - 1) + array[length - 1]);
    }

}