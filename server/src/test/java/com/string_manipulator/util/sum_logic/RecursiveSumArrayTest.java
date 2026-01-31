package com.string_manipulator.util.sum_logic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;

class RecursiveSumArrayTest {

    @Test
    @DisplayName("Test single element array")
    void testSingleElement() {
        int[] array = {5};
        assertEquals(5, RecursiveSumArray.findSum(array, 1));
    }

    @Test
    @DisplayName("Test two element array")
    void testTwoElements() {
        int[] array = {3, 7};
        assertEquals(10, RecursiveSumArray.findSum(array, 2));
    }

    @Test
    @DisplayName("Test multiple elements")
    void testMultipleElements() {
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(15, RecursiveSumArray.findSum(array, 5));
    }

    @Test
    @DisplayName("Test partial array sum")
    void testPartialArray() {
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(6, RecursiveSumArray.findSum(array, 3)); // Sum of first 3 elements
    }

    @Test
    @DisplayName("Test negative numbers")
    void testNegativeNumbers() {
        int[] array = {-1, -2, -3};
        assertEquals(-6, RecursiveSumArray.findSum(array, 3));
    }

    @Test
    @DisplayName("Test mixed positive and negative numbers")
    void testMixedNumbers() {
        int[] array = {5, -3, 2, -1};
        assertEquals(3, RecursiveSumArray.findSum(array, 4));
    }

    @Test
    @DisplayName("Test array with zeros")
    void testWithZeros() {
        int[] array = {0, 5, 0, 3};
        assertEquals(8, RecursiveSumArray.findSum(array, 4));
    }

    @Test
    @DisplayName("Test all zeros")
    void testAllZeros() {
        int[] array = {0, 0, 0, 0};
        assertEquals(0, RecursiveSumArray.findSum(array, 4));
    }

    @Test
    @DisplayName("Test maximum integer values")
    void testMaxIntegerValues() {
        int[] array = {Integer.MAX_VALUE, 1};
        assertEquals(Integer.MAX_VALUE + 1L, RecursiveSumArray.findSum(array, 2));
    }

    @Test
    @DisplayName("Test minimum integer values")
    void testMinIntegerValues() {
        int[] array = {Integer.MIN_VALUE, -1};
        assertEquals(Integer.MIN_VALUE - 1L, RecursiveSumArray.findSum(array, 2));
    }

    @Test
    @DisplayName("Test mixed extreme values")
    void testMixedExtremeValues() {
        int[] array = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertEquals(-1L, RecursiveSumArray.findSum(array, 2));
    }

    @Test
    @DisplayName("Test large array performance")
    void testLargeArray() {
        int[] array = new int[1000];
        Arrays.fill(array, 1);
        assertEquals(1000, RecursiveSumArray.findSum(array, 1000));
    }

    @Test
    @DisplayName("Test consecutive numbers")
    void testConsecutiveNumbers() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertEquals(55, RecursiveSumArray.findSum(array, 10));
    }

    @Test
    @DisplayName("Test alternating pattern")
    void testAlternatingPattern() {
        int[] array = {1, -1, 1, -1, 1};
        assertEquals(1, RecursiveSumArray.findSum(array, 5));
    }

    @Test
    @DisplayName("Test single negative element")
    void testSingleNegativeElement() {
        int[] array = {-5};
        assertEquals(-5, RecursiveSumArray.findSum(array, 1));
    }

    @Test
    @DisplayName("Test single zero element")
    void testSingleZeroElement() {
        int[] array = {0};
        assertEquals(0, RecursiveSumArray.findSum(array, 1));
    }
}