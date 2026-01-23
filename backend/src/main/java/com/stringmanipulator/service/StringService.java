package com.stringmanipulator.service;

import com.stringmanipulator.util.RecursiveString;
import com.stringmanipulator.util.ShiftedString;
import com.stringmanipulator.util.SortingString;

import java.util.ArrayList;

import static com.stringmanipulator.util.ReverseString.reverse;

//handle inputs, errors, and calling the functions


public class StringService {

    public String reverseString(String stringToReverse) {

        if (stringToReverse == null || stringToReverse.isEmpty()) {
            throw new IllegalArgumentException("String cannot be null");
        }
        return reverse(stringToReverse);
    }


    public String shiftString(String stringToShift, int shifts, String direction) {

        try {
            if (stringToShift == null || stringToShift.isEmpty()) {
                throw new IllegalArgumentException("String cannot be null");
            }
            if (shifts < 0) {
                throw new IllegalArgumentException("Shifts cannot be negative");
            }
            if (!"left".equalsIgnoreCase(direction) && !"right".equalsIgnoreCase(direction)) {
                throw new IllegalArgumentException("Direction must be left or right");
            }

            //return the shifted string while lowercasing the direction after validation for the next part
            return ShiftedString.shifting(stringToShift, shifts, direction.toLowerCase());
        } catch (Exception e) {
            throw new RuntimeException("Failed to shift string: " + e.getMessage());

        }
    }

    public int sumArray(int[] arrayToSum) {
        int lengthArray = arrayToSum.length;
        if (lengthArray == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        return RecursiveString.findSum(arrayToSum, lengthArray);
    }

    public int[] sortArray(int[] arrayToSort, String sortType, String order) {
        try {
            if (arrayToSort == null || arrayToSort.length == 0) {
                throw new IllegalArgumentException("Array cannot be null or empty");
            }
            if (sortType == null || sortType.trim().isEmpty()) {
                throw new IllegalArgumentException("Sort type cannot be null or empty");
            }

            sortType = sortType.toLowerCase().trim();

            if ("order".equals(sortType) || "o".equals(sortType)) {
                if (order == null || order.trim().isEmpty()) {
                    throw new IllegalArgumentException("Order cannot be null or empty for sorting");
                }

                order = order.toLowerCase().trim();
                if ("ascending".equals(order) || "a".equals(order)) {
                    return SortingString.sortAscending(arrayToSort);
                } else if ("descending".equals(order) || "d".equals(order)) {
                    return SortingString.sortDescending(arrayToSort);
                } else {
                    throw new IllegalArgumentException("Order must be 'ascending'/'a' or 'descending'/'d'");
                }
            } else if ("separate".equals(sortType) || "s".equals(sortType)) {
                // For separation, return the original array since even/odd lists are handled separately
                ArrayList<Integer> evenNumbers = new ArrayList<>();
                ArrayList<Integer> oddNumbers = new ArrayList<>();
                SortingString.separateEvenAndOdd(arrayToSort, evenNumbers, oddNumbers);
                return arrayToSort; // Return original array, controller can access even/odd lists if needed
            } else {
                throw new IllegalArgumentException("Sort type must be 'order'/'o' or 'separate'/'s'");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to sort array: " + e.getMessage(), e);
        }
    }

}





