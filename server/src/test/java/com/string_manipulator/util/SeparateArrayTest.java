package com.string_manipulator.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SeparateArrayTest {

    @Test
    @DisplayName("Test int separate even and odd")
    void testIntSeparateEvenAndOdd() {
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(5, evenNumbers.size());
        assertEquals(5, oddNumbers.size());

        // Convert to arrays, sort, and compare
        Integer[] evenArray = evenNumbers.toArray(new Integer[0]);
        Integer[] oddArray = oddNumbers.toArray(new Integer[0]);
        Arrays.sort(evenArray);
        Arrays.sort(oddArray);

        assertArrayEquals(new Integer[]{2, 4, 6, 8, 10}, evenArray);
        assertArrayEquals(new Integer[]{1, 3, 5, 7, 9}, oddArray);
    }

    @Test
    @DisplayName("Test double separate even and odd")
    void testDoubleSeparateEvenAndOdd() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        ArrayList<Double> evenNumbers = new ArrayList<>();
        ArrayList<Double> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(3, evenNumbers.size());
        assertEquals(3, oddNumbers.size());
        Double[] evenArray = evenNumbers.toArray(new Double[0]);
        Double[] oddArray = oddNumbers.toArray(new Double[0]);
        Arrays.sort(evenArray);
        Arrays.sort(oddArray);

        assertArrayEquals(new Double[]{2.0, 4.0, 6.0}, evenArray);
        assertArrayEquals(new Double[]{1.0, 3.0, 5.0}, oddArray);
    }

    @Test
    @DisplayName("Test double separate even and odd with decimals")
    void testDoubleSeparateEvenAndOddDecimals() {
        double[] input = {1.5, 2.0, 3.7, 4.0, 5.1};
        ArrayList<Double> evenNumbers = new ArrayList<>();
        ArrayList<Double> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(2, evenNumbers.size());
        assertEquals(3, oddNumbers.size());
        Double[] evenArray = evenNumbers.toArray(new Double[0]);
        Double[] oddArray = oddNumbers.toArray(new Double[0]);
        Arrays.sort(evenArray);
        Arrays.sort(oddArray);

        assertArrayEquals(new Double[]{2.0, 4.0}, evenArray);
        assertArrayEquals(new Double[]{1.5, 3.7, 5.1}, oddArray);
    }

    @Test
    @DisplayName("Test int separate positive and negative")
    void testIntSeparatePositiveAndNegative() {
        int[] input = {-5, -2, 0, 3, 7};
        ArrayList<Integer> positiveNumbers = new ArrayList<>();
        ArrayList<Integer> negativeNumbers = new ArrayList<>();

        SeparateArray.separatePositiveAndNegative(input, positiveNumbers, negativeNumbers);

        assertEquals(3, positiveNumbers.size()); // 0 is treated as positive
        assertEquals(2, negativeNumbers.size());
        Integer[] positiveArray = positiveNumbers.toArray(new Integer[0]);
        Integer[] negativeArray = negativeNumbers.toArray(new Integer[0]);
        Arrays.sort(positiveArray);
        Arrays.sort(negativeArray);

        assertArrayEquals(new Integer[]{0, 3, 7}, positiveArray);
        assertArrayEquals(new Integer[]{-5, -2}, negativeArray);
    }

    @Test
    @DisplayName("Test double separate positive and negative")
    void testDoubleSeparatePositiveAndNegative() {
        double[] input = {-3.5, -1.2, 0.0, 2.8, 5.1};
        ArrayList<Double> positiveNumbers = new ArrayList<>();
        ArrayList<Double> negativeNumbers = new ArrayList<>();

        SeparateArray.separatePositiveAndNegative(input, positiveNumbers, negativeNumbers);

        assertEquals(3, positiveNumbers.size()); // 0.0 is treated as positive
        assertEquals(2, negativeNumbers.size());
        Double[] positiveArray = positiveNumbers.toArray(new Double[0]);
        Double[] negativeArray = negativeNumbers.toArray(new Double[0]);
        Arrays.sort(positiveArray);
        Arrays.sort(negativeArray);

        assertArrayEquals(new Double[]{0.0, 2.8, 5.1}, positiveArray);
        assertArrayEquals(new Double[]{-3.5, -1.2}, negativeArray);
    }

    @Test
    @DisplayName("Test all even numbers")
    void testAllEvenNumbers() {
        int[] input = {2, 4, 6, 8, 10};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(5, evenNumbers.size());
        assertEquals(0, oddNumbers.size());
        assertTrue(oddNumbers.isEmpty());
    }

    @Test
    @DisplayName("Test all odd numbers")
    void testAllOddNumbers() {
        int[] input = {1, 3, 5, 7, 9};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(0, evenNumbers.size());
        assertEquals(5, oddNumbers.size());
        assertTrue(evenNumbers.isEmpty());
    }

    @Test
    @DisplayName("Test all positive numbers")
    void testAllPositiveNumbers() {
        int[] input = {1, 2, 3, 4, 5};
        ArrayList<Integer> positiveNumbers = new ArrayList<>();
        ArrayList<Integer> negativeNumbers = new ArrayList<>();

        SeparateArray.separatePositiveAndNegative(input, positiveNumbers, negativeNumbers);

        assertEquals(5, positiveNumbers.size());
        assertEquals(0, negativeNumbers.size());
        assertTrue(negativeNumbers.isEmpty());
    }

    @Test
    @DisplayName("Test all negative numbers")
    void testAllNegativeNumbers() {
        int[] input = {-1, -2, -3, -4, -5};
        ArrayList<Integer> positiveNumbers = new ArrayList<>();
        ArrayList<Integer> negativeNumbers = new ArrayList<>();

        SeparateArray.separatePositiveAndNegative(input, positiveNumbers, negativeNumbers);

        assertEquals(0, positiveNumbers.size());
        assertEquals(5, negativeNumbers.size());
        assertTrue(positiveNumbers.isEmpty());
    }

    @Test
    @DisplayName("Test empty array")
    void testEmptyArray() {
        int[] input = {};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(0, evenNumbers.size());
        assertEquals(0, oddNumbers.size());
        assertTrue(evenNumbers.isEmpty());
        assertTrue(oddNumbers.isEmpty());
    }

    @Test
    @DisplayName("Test single element array - even")
    void testSingleElementEven() {
        int[] input = {2};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(1, evenNumbers.size());
        assertEquals(0, oddNumbers.size());
        assertEquals(2, evenNumbers.getFirst());
    }

    @Test
    @DisplayName("Test single element array - odd")
    void testSingleElementOdd() {
        int[] input = {3};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(0, evenNumbers.size());
        assertEquals(1, oddNumbers.size());
        assertEquals(3, oddNumbers.getFirst());
    }

    @Test
    @DisplayName("Test single element array - zero")
    void testSingleElementZero() {
        int[] input = {0};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(1, evenNumbers.size());
        assertEquals(0, oddNumbers.size());
        assertEquals(0, evenNumbers.getFirst());
    }

    @Test
    @DisplayName("Test negative even and odd numbers")
    void testNegativeEvenAndOdd() {
        int[] input = {-4, -3, -2, -1, 0};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(3, evenNumbers.size());
        assertEquals(2, oddNumbers.size());

        Integer[] evenArray = evenNumbers.toArray(new Integer[0]);
        Integer[] oddArray = oddNumbers.toArray(new Integer[0]);
        Arrays.sort(evenArray);
        Arrays.sort(oddArray);

        assertArrayEquals(new Integer[]{-4, -2, 0}, evenArray);
        assertArrayEquals(new Integer[]{-3, -1}, oddArray);
    }

    @Test
    @DisplayName("Test large numbers")
    void testLargeNumbers() {
        int[] input = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 1, -1};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(2, evenNumbers.size()); // MAX_VALUE is even, MIN_VALUE is even, 0 is even
        assertEquals(3, oddNumbers.size());  // 1, -1 are odd

        Integer[] evenArray = evenNumbers.toArray(new Integer[0]);
        Integer[] oddArray = oddNumbers.toArray(new Integer[0]);
        Arrays.sort(evenArray);
        Arrays.sort(oddArray);

        assertArrayEquals(new Integer[]{Integer.MIN_VALUE, 0}, evenArray);
        assertArrayEquals(new Integer[]{-1, 1, Integer.MAX_VALUE}, oddArray);
    }

    @Test
    @DisplayName("Test alternating pattern")
    void testAlternatingPattern() {
        int[] input = {1, 2, 1, 2, 1, 2};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(3, evenNumbers.size());
        assertEquals(3, oddNumbers.size());

        for (Integer even : evenNumbers) {
            assertEquals(2, even);
        }

        for (Integer odd : oddNumbers) {
            assertEquals(1, odd);
        }
    }

    @Test
    @DisplayName("Test double precision edge cases")
    void testDoublePrecisionEdgeCases() {
        double[] input = {Double.MAX_VALUE, Double.MIN_VALUE, Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY, Double.NaN, 0.0};
        ArrayList<Double> evenNumbers = new ArrayList<>();
        ArrayList<Double> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        // Note: NaN and Infinity have special behavior with modulo operation
        // This test documents the current behavior
        assertEquals(6, evenNumbers.size() + oddNumbers.size());
    }

    @Test
    @DisplayName("Test double negative zero")
    void testDoubleNegativeZero() {
        double[] input = {-0.0, 0.0, -1.0, 1.0};
        ArrayList<Double> positiveNumbers = new ArrayList<>();
        ArrayList<Double> negativeNumbers = new ArrayList<>();

        SeparateArray.separatePositiveAndNegative(input, positiveNumbers, negativeNumbers);

        assertEquals(2, positiveNumbers.size()); // Both -0.0 and 0.0 are treated as positive
        assertEquals(2, negativeNumbers.size());
    }

    @Test
    @DisplayName("Test pre-filled lists are not cleared")
    void testPrefilledListsNotCleared() {
        int[] input = {1, 2, 3};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        // Pre-fill with existing data
        evenNumbers.add(99);
        oddNumbers.add(88);

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(2, evenNumbers.size()); // Original 99 + new even numbers
        assertEquals(3, oddNumbers.size());  // Original 88 + new odd numbers
        assertEquals(99, evenNumbers.getFirst());
        assertEquals(88, oddNumbers.getFirst());
    }

    @Test
    @DisplayName("Test deterministic behavior - same input produces same output")
    void testDeterministicBehavior() {
        int[] input = {1, 2, 3, 4, 5};

        // First run
        ArrayList<Integer> evenNumbers1 = new ArrayList<>();
        ArrayList<Integer> oddNumbers1 = new ArrayList<>();
        SeparateArray.separateEvenAndOdd(input, evenNumbers1, oddNumbers1);

        // Second run
        ArrayList<Integer> evenNumbers2 = new ArrayList<>();
        ArrayList<Integer> oddNumbers2 = new ArrayList<>();
        SeparateArray.separateEvenAndOdd(input, evenNumbers2, oddNumbers2);

        // Results should be identical
        assertEquals(evenNumbers1, evenNumbers2);
        assertEquals(oddNumbers1, oddNumbers2);
    }

    @Test
    @DisplayName("Test order preservation")
    void testOrderPreservation() {
        int[] input = {5, 2, 7, 4, 1, 6, 3};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SeparateArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        // Check that relative order is preserved
        assertArrayEquals(new Integer[]{2, 4, 6}, evenNumbers.toArray());
        assertArrayEquals(new Integer[]{5, 7, 1, 3}, oddNumbers.toArray());
    }
}