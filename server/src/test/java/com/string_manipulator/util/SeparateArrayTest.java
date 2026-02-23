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
}