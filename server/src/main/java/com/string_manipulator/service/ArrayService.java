package com.string_manipulator.service;

import com.string_manipulator.util.SortingArray;
import com.string_manipulator.util.sum_logic.DoubleSumArray;
import com.string_manipulator.util.sum_logic.RecursiveSumArray;
import java.util.ArrayList;

public class ArrayService {
    /* @author Joe Nguyen */
    /**
     * This class contains methods for performing operations on arrays.
     */
        private static final int MAX_ARRAY_LENGTH = 1000;
        private static final double MAX_NUMERIC_VALUE = Double.MAX_VALUE / 2;

    /**************************************************************************/

    public int sumArray(int[] arrayToSum) {
        validateArray(arrayToSum);
        if (arrayToSum.length == 1) {
            return arrayToSum[0];
        }
        return RecursiveSumArray.findSum(arrayToSum, arrayToSum.length);
    }

    public double sumArray(double[] arrayToSum) {
        validateArray(arrayToSum);
        if (arrayToSum.length == 1) {
            return arrayToSum[0];
        }
        return DoubleSumArray.findSum(arrayToSum, arrayToSum.length);
    }

    /**************************************************************************/

    public int[] sortArray(int[] arrayToSort, String orderType) {
        try {
            validateArray(arrayToSort);
            validateOrderParameters(orderType);
            String normalizedOrder = normalizeOrderType(orderType);
            return handleSorting(arrayToSort, normalizedOrder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to sort int array: " + e.getMessage(), e);
        }
    }

    public double[] sortArray(double[] arrayToSort, String orderType) {
        try {
            validateArray(arrayToSort);
            validateOrderParameters(orderType);
            String normalizedOrder = normalizeOrderType(orderType);
            return handleSorting(arrayToSort, normalizedOrder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to sort double array: " + e.getMessage(), e);
        }
    }

    /**************************************************************************/

    public SeparationResult<Integer> separateArray(int[] arrayToPart, String separationType) {
        try {
            validateArray(arrayToPart);
            validateSeparationParameters(separationType);
            String normalizedSeparation = normalizeSeparationType(separationType);
            return handleSeparation(arrayToPart, normalizedSeparation);
        } catch (Exception e) {
            throw new RuntimeException("Failed to separate int array: " + e.getMessage(), e);
        }
    }

    public SeparationResult<Double> separateArray(double[] arrayToPart, String separationType) {
        try {
            validateArray(arrayToPart);
            validateSeparationParameters(separationType);
            String normalizedSeparation = normalizeSeparationType(separationType);
            return handleSeparation(arrayToPart, normalizedSeparation);
        } catch (Exception e) {
            throw new RuntimeException("Failed to separate double array: " + e.getMessage(), e);
        }
    }

    /**************************************************************************/

    // Parameter validation
    private void validateOrderParameters(String orderType) {
        if (orderType == null || orderType.trim().isEmpty()) {
            throw new IllegalArgumentException("Order type cannot be null or empty");
        }
    }

    private void validateSeparationParameters(String separationType) {
        if (separationType == null || separationType.trim().isEmpty()) {
            throw new IllegalArgumentException("Separation type cannot be null or empty");
        }
    }

    // Parameter normalization methods
    private String normalizeOrderType(String orderType) {
        String normalized = orderType.toLowerCase().trim();
        return switch (normalized) {
            case "ascending", "a" -> "ascending";
            case "descending", "d" -> "descending";
            default -> throw new IllegalArgumentException("Order must be 'ascending'/'a' or 'descending'/'d'");
        };
    }

    private String normalizeSeparationType(String separationType) {
        String normalized = separationType.toLowerCase().trim();
        return switch (normalized) {
            case "parity", "p" -> "parity";
            case "sign", "s" -> "sign";
            default -> throw new IllegalArgumentException("Separation must be 'parity'/'p' or 'sign'/'s'");
        };
    }

    // Type-specific handlers
    private int[] handleSorting(int[] array, String orderType) {
        if ("ascending".equals(orderType)) {
            return SortingArray.sortAscending(array);
        } else if ("descending".equals(orderType)) {
            return SortingArray.sortDescending(array);
        } else {
            throw new IllegalArgumentException("Order must be 'ascending'/'a' or 'descending'/'d'");
        }
    }

    private double[] handleSorting(double[] array, String orderType) {
        if ("ascending".equals(orderType)) {
            return SortingArray.sortAscending(array);
        } else if ("descending".equals(orderType)) {
            return SortingArray.sortDescending(array);
        } else {
            throw new IllegalArgumentException("Order must be 'ascending'/'a' or 'descending'/'d'");
        }
    }

    // Final practical solution - minimal duplication, maximum type safety
    private SeparationResult<Integer> handleSeparation(int[] array, String separationType) {
        ArrayList<Integer> first = new ArrayList<>();
        ArrayList<Integer> second = new ArrayList<>();

        SeparationResult.SeparationType type = SeparationResult.SeparationType.fromString(separationType);

        switch (type) {
            case PARITY:
                SortingArray.separateEvenAndOdd(array, first, second);
                break;
            case SIGN:
                SortingArray.separatePositiveAndNegative(array, first, second);
                break;
        }

        return new SeparationResult<>(first, second, type);
    }

    private SeparationResult<Double> handleSeparation(double[] array, String separationType) {
        ArrayList<Double> first = new ArrayList<>();
        ArrayList<Double> second = new ArrayList<>();

        SeparationResult.SeparationType type = SeparationResult.SeparationType.fromString(separationType);

        switch (type) {
            case PARITY:
                SortingArray.separateEvenAndOdd(array, first, second);
                break;
            case SIGN:
                SortingArray.separatePositiveAndNegative(array, first, second);
                break;
        }

        return new SeparationResult<>(first, second, type);
    }

    /**************************************************************************/

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
