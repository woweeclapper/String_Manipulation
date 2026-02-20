package com.string_manipulator.service;

import com.string_manipulator.util.SortingArray;
import com.string_manipulator.util.sum_logic.DoubleSumArray;
import com.string_manipulator.util.sum_logic.RecursiveSumArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ArrayService {
    /* @author Joe Nguyen */
    /**
     * This class contains methods for performing operations on arrays.
     */
    private static final int MAX_ARRAY_LENGTH = 1000;
    private static final double MAX_NUMERIC_VALUE = Double.MAX_VALUE / 2;
    private static final Logger logger = LoggerFactory.getLogger(ArrayService.class);

    /**************************************************************************/

    public int sumArray(int[] arrayToSum) {
        logger.info("Entering IntSum with input of length: {}", arrayToSum.length);
        validateArray(arrayToSum);
        if (arrayToSum.length == 1) {
            return arrayToSum[0];
        }
        int result = RecursiveSumArray.findSum(arrayToSum, arrayToSum.length);
        logger.info("Exiting IntSum with result: {}", result);
        return result;
    }

    public double sumArray(double[] arrayToSum) {
        logger.info("Entering DoubleSum with input of length: {}", arrayToSum.length);
        validateArray(arrayToSum);
        if (arrayToSum.length == 1) {
            return arrayToSum[0];
        }

        double result = DoubleSumArray.findSum(arrayToSum, arrayToSum.length);
        logger.info("Exiting DoubleSum with result: {}", result);
        return result;
    }

    /**************************************************************************/

    public int[] sortArray(int[] arrayToSort, String orderType) {
        logger.info("Entering IntSort with input of length {} and orderType {}",
                arrayToSort.length, orderType);
        try {
            validateArray(arrayToSort);
            validateOrderParameters(orderType);
            String normalizedOrder = normalizeOrderType(orderType);
            logger.info("Exiting IntSort successful");
            return handleSorting(arrayToSort, normalizedOrder);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Array length exceeds maximum length of " + MAX_ARRAY_LENGTH); // Let validation exceptions propagate
        } catch (Exception e) {
            logger.warn("Failed to sort int array {} and orderType {}",
                    arrayToSort, orderType);
            throw new UnsupportedOperationException("Failed to sort int array: " + e.getMessage(), e);
        }
    }

    public double[] sortArray(double[] arrayToSort, String orderType) {
        logger.info("Entering DoubleSort with input of length {} and orderType {}",
                arrayToSort.length, orderType);
        try {
            validateArray(arrayToSort);
            validateOrderParameters(orderType);
            String normalizedOrder = normalizeOrderType(orderType);
            logger.info("Exiting DoubleSort successful");
            return handleSorting(arrayToSort, normalizedOrder);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Array length exceeds maximum length of " + MAX_ARRAY_LENGTH); // Let validation exceptions propagate
        } catch (Exception e) {
            logger.warn("Failed to sort double array {} and orderType {}",
                    arrayToSort, orderType);
            throw new UnsupportedOperationException("Failed to sort int array: " + e.getMessage(), e);
        }
    }

    /**************************************************************************/

    public SeparationResult<Integer> separateArray(int[] arrayToPart, String separationType) {
        logger.info("Entering IntSeparate with input of length {} and separationType {}",
                arrayToPart.length, separationType);
        try {
            validateArray(arrayToPart);
            validateSeparationParameters(separationType);
            String normalizedSeparation = normalizeSeparationType(separationType);
            SeparationResult<Integer> result = handleSeparation(arrayToPart, normalizedSeparation);
            logger.info("Exiting IntSeparate successfully with result: {}", result);
            return result;
        } catch (Exception e) {
            logger.warn("Failed to separate int array {} and separationType {}",
                    arrayToPart, separationType);
            throw new UnsupportedOperationException("Failed to separate int array: " + e.getMessage(), e);
        }
    }

    public SeparationResult<Double> separateArray(double[] arrayToPart, String separationType) {
        logger.info("Entering DoubleSeparate with input of length {} and separationType {}",
                arrayToPart.length, separationType);
        try {
            validateArray(arrayToPart);
            validateSeparationParameters(separationType);
            String normalizedSeparation = normalizeSeparationType(separationType);
            SeparationResult<Double> result = handleSeparation(arrayToPart, normalizedSeparation);
            logger.info("Exiting DoubleSeparate successfully with result: {}", result);
            return result;
        } catch (Exception e) {
            logger.warn("Failed to separate double array {} and separationType {}",
                    arrayToPart, separationType);
            throw new UnsupportedOperationException("Failed to separate double array: " + e.getMessage(), e);
        }
    }

    /**************************************************************************/

    // Parameter validation
    private void validateOrderParameters(String orderType) {
        if (orderType == null || orderType.trim().isEmpty()) {
            logger.warn("Order type validation failed: null or empty");
            throw new IllegalArgumentException("Order type cannot be null or empty");
        }
    }

    private void validateSeparationParameters(String separationType) {
        if (separationType == null || separationType.trim().isEmpty()) {
            logger.warn("Separation type validation failed: null or empty");
            throw new IllegalArgumentException("Separation type cannot be null or empty");
        }
    }

    // Parameter normalization methods
    private String normalizeOrderType(String orderType) {
        logger.info("Normalizing order type: {}", orderType);
        String normalized = orderType.toLowerCase().trim();
        return switch (normalized) {
            case "ascending", "a" -> {
                logger.info("Normalized order to ascending");
                yield "ascending";
            }
            case "descending", "d" -> {
                logger.info("Normalized order to descending");
                yield "descending";
            }
            default -> {
                logger.warn("Invalid order type {}", orderType);
                throw new IllegalArgumentException("Order must be 'ascending'/'a' or 'descending'/'d'");
            }
        };
    }

    private String normalizeSeparationType(String separationType) {
        logger.info("Normalizing separation type: {}", separationType);
        String normalized = separationType.toLowerCase().trim();
        return switch (normalized) {
            case "parity", "p" -> {
                logger.info("Separation type normalized to parity");
                yield "parity";
            }
            case "sign", "s" -> {
                logger.info("Separation type normalized to sign");
                yield "sign";
            }
            default -> {
                logger.warn("Invalid separation type {}", separationType);
                throw new IllegalArgumentException("Separation must be 'parity'/'p' or 'sign'/'s'");
            }
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
            logger.warn("Array validation failed: null array");
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (java.lang.reflect.Array.getLength(array) <= 0) {
            logger.warn("Array validation failed: empty array");
            throw new IllegalArgumentException("Array cannot be empty");
        }
        if (java.lang.reflect.Array.getLength(array) > MAX_ARRAY_LENGTH) {
            logger.warn("Array validation failed: array length is {}", java.lang.reflect.Array.getLength(array));
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
            logger.warn("Int Array validation failed: no valid elements");
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
                logger.warn("Array validation failed: contains NaN");
                throw new IllegalArgumentException("Array contains NaN value");
            }

            if (Double.isInfinite(element)) {
                logger.warn("Array validation failed: contains infinite value");
                throw new IllegalArgumentException("Array contains infinite value");
            }

            if (Math.abs(element) > MAX_NUMERIC_VALUE) {
                logger.warn("DoubleArray validation failed: values too large");
                throw new IllegalArgumentException("Array contains value exceeding reasonable bounds: " + element);
            }

            hasValidElement = true;
        }

        // Meaning-level validation
        if (!hasValidElement) {
            logger.warn("Array validation failed: no valid elements");
            throw new IllegalArgumentException("Array must contain at least one valid numeric value");
        }
    }

}
