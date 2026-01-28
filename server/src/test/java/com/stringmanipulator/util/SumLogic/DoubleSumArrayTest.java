package com.stringmanipulator.util.SumLogic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class DoubleSumArrayTest {

    @Test
    @DisplayName("Test single element array")
    void testSingleElement() {
        double[] array = {5.0};
        assertEquals(5.0, DoubleSumArray.findSum(array, 1));
    }

    @Test
    @DisplayName("Test two element array")
    void testTwoElements() {
        double[] array = {3.0, 7.0};
        assertEquals(10.0, DoubleSumArray.findSum(array, 2));
    }

    @Test
    @DisplayName("Test multiple elements")
    void testMultipleElements() {
        double[] array = {1.0, 2.0, 3.0, 4.0, 5.0};
        assertEquals(15.0, DoubleSumArray.findSum(array, 5));
    }

    @Test
    @DisplayName("Test partial array sum")
    void testPartialArray() {
        double[] array = {1.0, 2.0, 3.0, 4.0, 5.0};
        assertEquals(6.0, DoubleSumArray.findSum(array, 3)); // Sum of first 3 elements
    }

    @Test
    @DisplayName("Test negative numbers")
    void testNegativeNumbers() {
        double[] array = {-1.0, -2.0, -3.0};
        assertEquals(-6.0, DoubleSumArray.findSum(array, 3));
    }

    @Test
    @DisplayName("Test mixed positive and negative numbers")
    void testMixedNumbers() {
        double[] array = {5.0, -3.0, 2.0, -1.0};
        assertEquals(3.0, DoubleSumArray.findSum(array, 4));
    }

    @Test
    @DisplayName("Test array with zeros")
    void testWithZeros() {
        double[] array = {0.0, 5.0, 0.0, 3.0};
        assertEquals(8.0, DoubleSumArray.findSum(array, 4));
    }

    @Test
    @DisplayName("Test decimal values")
    void testDecimalValues() {
        double[] array = {1.5, 2.3, 3.7};
        assertEquals(7.5, DoubleSumArray.findSum(array, 3), 0.001);
    }

    @Test
    @DisplayName("Test large numbers")
    void testLargeNumbers() {
        double[] array = {Double.MAX_VALUE / 2, Double.MAX_VALUE / 2};
        assertEquals(Double.MAX_VALUE, DoubleSumArray.findSum(array, 2));
    }

    @Test
    @DisplayName("Test empty array - should throw exception")
    void testEmptyArray() {
        double[] array = {};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            DoubleSumArray.findSum(array, 0);
        });
    }

    @Test
    @DisplayName("Test length greater than array size")
    void testLengthGreaterThanArray() {
        double[] array = {1.0, 2.0};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            DoubleSumArray.findSum(array, 3);
        });
    }

    @Test
    @DisplayName("Test negative length")
    void testNegativeLength() {
        double[] array = {1.0, 2.0, 3.0};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            DoubleSumArray.findSum(array, -1);
        });
    }

    @Test
    @DisplayName("Test large array performance")
    void testLargeArray() {
        double[] array = new double[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = 1.0;
        }
        assertEquals(1000.0, DoubleSumArray.findSum(array, 1000));
    }
}