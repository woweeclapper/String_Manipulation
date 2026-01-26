package com.stringmanipulator.service;

import com.stringmanipulator.util.ShiftedString;
import com.stringmanipulator.util.SortingString;
import com.stringmanipulator.util.SumLogic.DoubleSumString;
import com.stringmanipulator.util.SumLogic.RecursiveSumString;

import java.util.ArrayList;
import java.util.function.Supplier;

import static com.stringmanipulator.util.ReverseString.reverse;

//handle inputs, errors, and calling the functions


public class StringService {


    private static final int MAX_ARRAY_LENGTH = 1000;
    private static final double MAX_NUMERIC_VALUE = Double.MAX_VALUE / 2;

    public String reverseString(String stringToReverse) {

        // these validation is to ensure the string must have at least 1 character through null, empty, and whitespace check
        validateString(stringToReverse);

        //input sanitation
        String sanitizedInput = sanitizeStringInput(stringToReverse);
        return reverse(sanitizedInput);
    }

    /**************************************************************************/

    public String shiftString(String stringToShift, int shifts, String direction) {

        validateString(stringToShift);

        if (shifts < 0) {
            throw new IllegalArgumentException("Shifts cannot be negative");
        }

        String normalizedDirection = validateAndNormalizeDirection(direction);

        if (shifts == 0) {
            return sanitizeStringInput(stringToShift);
        }

        String sanitizedInput = sanitizeStringInput(stringToShift);

        //return the shifted string while lowercasing the direction after validation for the next part
        return processShift(sanitizedInput, shifts, normalizedDirection);

    }

    private String validateAndNormalizeDirection(String direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        String normalized = direction.trim().toLowerCase();

        return switch (normalized) {
            case "left", "l" -> "left";
            case "right", "r" -> "right";
            default -> throw new IllegalArgumentException("Direction must be 'left'/'l' or 'right'/'r'");
        };
    }

    private String processShift(String input, int shifts, String direction) {
        try {
            // Normalize shifts to prevent unnecessary operations
            int normalizedShifts = shifts % input.length();
            return ShiftedString.shifting(input, normalizedShifts, direction);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to shift string: " + e.getMessage(), e);
        }
    }

    /**************************************************************************/

    public int sumArray(int[] arrayToSum) {
        validateArray(arrayToSum);
        if (arrayToSum.length == 1) {
            return arrayToSum[0];
        }
        return RecursiveSumString.findSum(arrayToSum, arrayToSum.length);
    }

    public double sumArray(double[] arrayToSum) {
        validateArray(arrayToSum);
        if (arrayToSum.length == 1) {
            return arrayToSum[0];
        }
        return DoubleSumString.findSum(arrayToSum, arrayToSum.length);
    }

    /**************************************************************************/

    public int[] sortArray(int[] arrayToSort, String sortType, String order) {
        try {
            validateArray(arrayToSort);
            return sortArrayCommon(
                    () -> handleOrderSort(arrayToSort, order.toLowerCase().trim()),
                    () -> handleSeparation(arrayToSort),
                    sortType, order);

        } catch (Exception e) {
            throw new RuntimeException("Failed to sort int array: " + e.getMessage(), e);
        }
    }

    public double[] sortArray(double[] arrayToSort, String sortType, String order) {
        try {
            validateArray(arrayToSort);
            return sortArrayCommon(
                    () -> handleOrderSort(arrayToSort, order.toLowerCase().trim()),
                    () -> handleSeparation(arrayToSort),
                    sortType, order);

        } catch (Exception e) {
            throw new RuntimeException("Failed to sort double array: " + e.getMessage(), e);
        }
    }

    //this method can be called by either of the array data type method and will return based on what the overload function return
    private <T> T sortArrayCommon(Supplier<T> orderAction, Supplier<T> separateAction, String sortType, String order) {

        validateSortParameters(sortType, order);
        sortType = sortType.toLowerCase().trim();

        if ("order".equals(sortType) || "o".equals(sortType)) {
            return orderAction.get();
        } else if ("separate".equals(sortType) || "s".equals(sortType)) {
            return separateAction.get();
        } else {
            throw new IllegalArgumentException("Sort type must be 'order'/'o' or 'separate'/'s'");
        }
    }

    // sort Parameter validation
    private void validateSortParameters(String sortType, String order) {
        if (sortType == null || sortType.trim().isEmpty()) {
            throw new IllegalArgumentException("Sort type cannot be null or empty");
        }
        if (("order".equals(sortType.toLowerCase().trim()) || "o".equals(sortType.toLowerCase().trim()))
                && (order == null || order.trim().isEmpty())) {
            throw new IllegalArgumentException("Order cannot be null or empty for order sorting");
        }
    }

    // Type-specific handlers
    private int[] handleOrderSort(int[] array, String order) {
        if ("ascending".equals(order) || "a".equals(order)) {
            return SortingString.sortAscending(array);
        } else if ("descending".equals(order) || "d".equals(order)) {
            return SortingString.sortDescending(array);
        } else {
            throw new IllegalArgumentException("Order must be 'ascending'/'a' or 'descending'/'d'");
        }
    }

    private double[] handleOrderSort(double[] array, String order) {
        if ("ascending".equals(order) || "a".equals(order)) {
            return SortingString.sortAscending(array);
        } else if ("descending".equals(order) || "d".equals(order)) {
            return SortingString.sortDescending(array);
        } else {
            throw new IllegalArgumentException("Order must be 'ascending'/'a' or 'descending'/'d'");
        }
    }

    private int[] handleSeparation(int[] array) {
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();
        SortingString.separateEvenAndOdd(array, evenNumbers, oddNumbers);
        return array; // Return original, controller can access separated lists
    }

    private double[] handleSeparation(double[] array) {
        ArrayList<Double> positiveNumbers = new ArrayList<>();
        ArrayList<Double> negativeNumbers = new ArrayList<>();
        SortingString.separatePositiveAndNegative(array, positiveNumbers, negativeNumbers);
        return array; // Return original, controller can access separated lists
    }

    /**************************************************************************/
//Reusable Method

    // Input sanitization method
    private String sanitizeStringInput(String input) {
        // Remove control characters except common ones (tab, newline, carriage return)
        String sanitized = input.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

        // Log warning if characters were removed
        if (!sanitized.equals(input)) {
            System.out.println("Warning: Control characters removed from input");
        }

        return sanitized;
    }

    //validating string method
    private void validateString(String input) {

        final int MAX_LENGTH = 10000;
        if (input == null) {
            throw new IllegalArgumentException("String cannot by null");
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException("String must contain at least 1 character");
        }
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("String cannot contain only whitespace");
        }
        if (input.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("String exceeds maximum length of " + MAX_LENGTH + " characters. "
                    + "Provided length: " + input.length());
        }
    }

    //array validation methods
    private void validateArrayCommon(Object array) {
        // Common validation for null, empty, and length
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (java.lang.reflect.Array.getLength(array) == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        if (java.lang.reflect.Array.getLength(array) > MAX_ARRAY_LENGTH) {
            throw new IllegalArgumentException("Array length exceeds maximum of " + MAX_ARRAY_LENGTH);
        }
    }

    private void validateArray(int[] intArray) {
        // Array-level validation
        validateArrayCommon(intArray);

        // Element-level validation with fail-fast
        boolean hasValidElement = false;
        for (int element : intArray) {
            // Int-specific validations
            hasValidElement = true;
        }

        // Meaning-level validation
        if (!hasValidElement) {
            throw new IllegalArgumentException("Array must contain at least one valid numeric value");
        }
    }

    private void validateArray(double[] doubleArray) {
        // Array-level validation
        validateArrayCommon(doubleArray);

        // Element-level validation with fail-fast
        boolean hasValidElement = false;
        for (double element : doubleArray) {
            if (Double.isNaN(element)) {
                throw new IllegalArgumentException("Array contains NaN value");
            }
            if (Double.isInfinite(element)) {
                throw new IllegalArgumentException("Array contains infinite value");
            }
            if (element == -0.0) {
                throw new IllegalArgumentException("Array contains negative zero");
            }
            if (Math.abs(element) > MAX_NUMERIC_VALUE) {
                throw new IllegalArgumentException("Array contains value exceeding reasonable bounds: " + element);
            }
            if (String.valueOf(element).contains("E")) {
                throw new IllegalArgumentException("Array contains scientific notation: " + element);
            }
            hasValidElement = true;
        }

        // Meaning-level validation
        if (!hasValidElement) {
            throw new IllegalArgumentException("Array must contain at least one valid numeric value");
        }
    }

}







