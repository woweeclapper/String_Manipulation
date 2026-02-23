package com.string_manipulator.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


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
    @DisplayName("Test large array sorting")
    void testLargeArraySorting() {
        int[] largeArray = new int[1000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = (int) (Math.random() * 1000);
        }

        int[] sortedAsc = SortingArray.sortAscending(largeArray);
        int[] sortedDesc = SortingArray.sortDescending(largeArray);

        // Verify ascending order
        for (int i = 1; i < sortedAsc.length; i++) {
            assertTrue(sortedAsc[i] >= sortedAsc[i - 1]);
        }

        // Verify descending order
        for (int i = 1; i < sortedDesc.length; i++) {
            assertTrue(sortedDesc[i] <= sortedDesc[i - 1]);
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