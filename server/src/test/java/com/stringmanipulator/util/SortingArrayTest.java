package com.stringmanipulator.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.Arrays;

class SortingArrayTest {

    @Test
    @DisplayName("Test int sort ascending basic")
    void testIntSortAscendingBasic() {
        int[] input = {5, 2, 8, 1, 9};
        int[] expected = {1, 2, 5, 8, 9};
        assertArrayEquals(expected, SortingArray.sortAscending(input));
    }

    @Test
    @DisplayName("Test int sort descending basic")
    void testIntSortDescendingBasic() {
        int[] input = {5, 2, 8, 1, 9};
        int[] expected = {9, 8, 5, 2, 1};
        assertArrayEquals(expected, SortingArray.sortDescending(input));
    }

    @Test
    @DisplayName("Test double sort ascending basic")
    void testDoubleSortAscendingBasic() {
        double[] input = {5.5, 2.2, 8.8, 1.1, 9.9};
        double[] expected = {1.1, 2.2, 5.5, 8.8, 9.9};
        assertArrayEquals(expected, SortingArray.sortAscending(input));
    }

    @Test
    @DisplayName("Test double sort descending basic")
    void testDoubleSortDescendingBasic() {
        double[] input = {5.5, 2.2, 8.8, 1.1, 9.9};
        double[] expected = {9.9, 8.8, 5.5, 2.2, 1.1};
        assertArrayEquals(expected, SortingArray.sortDescending(input));
    }

    @Test
    @DisplayName("Test already sorted arrays")
    void testAlreadySortedArrays() {
        int[] intInput = {1, 2, 3, 4, 5};
        int[] intExpected = {1, 2, 3, 4, 5};
        assertArrayEquals(intExpected, SortingArray.sortAscending(intInput));

        double[] doubleInput = {1.1, 2.2, 3.3, 4.4};
        double[] doubleExpected = {1.1, 2.2, 3.3, 4.4};
        assertArrayEquals(doubleExpected, SortingArray.sortAscending(doubleInput));
    }

    @Test
    @DisplayName("Test reverse sorted arrays")
    void testReverseSortedArrays() {
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, SortingArray.sortAscending(input));
    }

    @Test
    @DisplayName("Test arrays with duplicate values")
    void testArraysDuplicateValues() {
        int[] input = {3, 1, 3, 2, 1, 2};
        int[] expectedAsc = {1, 1, 2, 2, 3, 3};
        int[] expectedDesc = {3, 3, 2, 2, 1, 1};

        assertArrayEquals(expectedAsc, SortingArray.sortAscending(input));
        assertArrayEquals(expectedDesc, SortingArray.sortDescending(input));
    }

    @Test
    @DisplayName("Test arrays with negative numbers")
    void testArraysNegativeNumbers() {
        int[] input = {-3, 1, -2, 0, 2};
        int[] expectedAsc = {-3, -2, 0, 1, 2};
        int[] expectedDesc = {2, 1, 0, -2, -3};

        assertArrayEquals(expectedAsc, SortingArray.sortAscending(input));
        assertArrayEquals(expectedDesc, SortingArray.sortDescending(input));
    }

    @Test
    @DisplayName("Test single element arrays")
    void testSingleElementArrays() {
        int[] intInput = {5};
        assertArrayEquals(new int[]{5}, SortingArray.sortAscending(intInput));
        assertArrayEquals(new int[]{5}, SortingArray.sortDescending(intInput));

        double[] doubleInput = {3.14};
        assertArrayEquals(new double[]{3.14}, SortingArray.sortAscending(doubleInput));
        assertArrayEquals(new double[]{3.14}, SortingArray.sortDescending(doubleInput));
    }

    @Test
    @DisplayName("Test two element arrays")
    void testTwoElementArrays() {
        int[] input = {9, 3};
        assertArrayEquals(new int[]{3, 9}, SortingArray.sortAscending(input));
        assertArrayEquals(new int[]{9, 3}, SortingArray.sortDescending(input));
    }

    @Test
    @DisplayName("Test int separate even and odd")
    void testIntSeparateEvenAndOdd() {
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SortingArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(5, evenNumbers.size());
        assertEquals(5, oddNumbers.size());
        assertTrue(evenNumbers.containsAll(Arrays.asList(2, 4, 6, 8, 10)));
        assertTrue(oddNumbers.containsAll(Arrays.asList(1, 3, 5, 7, 9)));
    }

    @Test
    @DisplayName("Test double separate even and odd")
    void testDoubleSeparateEvenAndOdd() {
        double[] input = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        ArrayList<Double> evenNumbers = new ArrayList<>();
        ArrayList<Double> oddNumbers = new ArrayList<>();

        SortingArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(3, evenNumbers.size());
        assertEquals(3, oddNumbers.size());
        assertTrue(evenNumbers.containsAll(Arrays.asList(2.0, 4.0, 6.0)));
        assertTrue(oddNumbers.containsAll(Arrays.asList(1.0, 3.0, 5.0)));
    }

    @Test
    @DisplayName("Test double separate even and odd with decimals")
    void testDoubleSeparateEvenAndOddDecimals() {
        double[] input = {1.5, 2.0, 3.7, 4.0, 5.1};
        ArrayList<Double> evenNumbers = new ArrayList<>();
        ArrayList<Double> oddNumbers = new ArrayList<>();

        SortingArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

        assertEquals(2, evenNumbers.size());
        assertEquals(3, oddNumbers.size());
        assertTrue(evenNumbers.containsAll(Arrays.asList(2.0, 4.0)));
        assertTrue(oddNumbers.containsAll(Arrays.asList(1.5, 3.7, 5.1)));
    }

    @Test
    @DisplayName("Test int separate positive and negative")
    void testIntSeparatePositiveAndNegative() {
        int[] input = {-5, -2, 0, 3, 7};
        ArrayList<Integer> positiveNumbers = new ArrayList<>();
        ArrayList<Integer> negativeNumbers = new ArrayList<>();

        SortingArray.separatePositiveAndNegative(input, positiveNumbers, negativeNumbers);

        assertEquals(3, positiveNumbers.size()); // 0 is treated as positive
        assertEquals(2, negativeNumbers.size());
        assertTrue(positiveNumbers.containsAll(Arrays.asList(0, 3, 7)));
        assertTrue(negativeNumbers.containsAll(Arrays.asList(-5, -2)));
    }

    @Test
    @DisplayName("Test double separate positive and negative")
    void testDoubleSeparatePositiveAndNegative() {
        double[] input = {-3.5, -1.2, 0.0, 2.8, 5.1};
        ArrayList<Double> positiveNumbers = new ArrayList<>();
        ArrayList<Double> negativeNumbers = new ArrayList<>();

        SortingArray.separatePositiveAndNegative(input, positiveNumbers, negativeNumbers);

        assertEquals(3, positiveNumbers.size()); // 0.0 is treated as positive
        assertEquals(2, negativeNumbers.size());
        assertTrue(positiveNumbers.containsAll(Arrays.asList(0.0, 2.8, 5.1)));
        assertTrue(negativeNumbers.containsAll(Arrays.asList(-3.5, -1.2)));
    }

    @Test
    @DisplayName("Test all even numbers")
    void testAllEvenNumbers() {
        int[] input = {2, 4, 6, 8, 10};
        ArrayList<Integer> evenNumbers = new ArrayList<>();
        ArrayList<Integer> oddNumbers = new ArrayList<>();

        SortingArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

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

        SortingArray.separateEvenAndOdd(input, evenNumbers, oddNumbers);

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

        SortingArray.separatePositiveAndNegative(input, positiveNumbers, negativeNumbers);

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

        SortingArray.separatePositiveAndNegative(input, positiveNumbers, negativeNumbers);

        assertEquals(0, positiveNumbers.size());
        assertEquals(5, negativeNumbers.size());
        assertTrue(positiveNumbers.isEmpty());
    }

    @Test
    @DisplayName("Test large array sorting")
    void testLargeArraySorting() {
        int[] largeArray = new int[1000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = (int)(Math.random() * 1000);
        }

        int[] sortedAsc = SortingArray.sortAscending(largeArray);
        int[] sortedDesc = SortingArray.sortDescending(largeArray);

        // Verify ascending order
        for (int i = 1; i < sortedAsc.length; i++) {
            assertTrue(sortedAsc[i] >= sortedAsc[i-1]);
        }

        // Verify descending order
        for (int i = 1; i < sortedDesc.length; i++) {
            assertTrue(sortedDesc[i] <= sortedDesc[i-1]);
        }
    }

    @Test
    @DisplayName("Test original array not modified")
    void testOriginalArrayNotModified() {
        int[] original = {5, 2, 8, 1, 9};
        int[] originalCopy = Arrays.copyOf(original, original.length);

        SortingArray.sortAscending(original);
        SortingArray.sortDescending(original);

        assertArrayEquals(originalCopy, original);
    }

    @Test
    @DisplayName("Test extreme values")
    void testExtremeValues() {
        int[] input = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, -1, 1};
        int[] expectedAsc = {Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
        int[] expectedDesc = {Integer.MAX_VALUE, 1, 0, -1, Integer.MIN_VALUE};

        assertArrayEquals(expectedAsc, SortingArray.sortAscending(input));
        assertArrayEquals(expectedDesc, SortingArray.sortDescending(input));
    }

    @Test
    @DisplayName("Test double extreme values")
    void testDoubleExtremeValues() {
        double[] input = {Double.MAX_VALUE, Double.MIN_VALUE, 0.0, -1.0, 1.0};
        double[] sorted = SortingArray.sortAscending(input);

        assertEquals(-1.0, sorted[0]);           // Most negative
        assertEquals(0.0, sorted[1]);             // Zero
        assertEquals(Double.MIN_VALUE, sorted[2]); // Smallest positive
        assertEquals(1.0, sorted[3]);             // Positive
        assertEquals(Double.MAX_VALUE, sorted[4]); // Largest positive
    }
}