package com.stringmanipulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
//import java.util.ArrayList;

class ArrayServiceTest {

    private ArrayService arrayService;

    @BeforeEach
    void setUp() {
        arrayService = new ArrayService();
    }

    // Sum Array Tests
    @Test
    void sumArray_SingleIntElement_ReturnsElement() {
        int[] array = {5};
        assertEquals(5, arrayService.sumArray(array));
    }

    @Test
    void sumArray_MultipleIntElements_ReturnsSum() {
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(15, arrayService.sumArray(array));
    }

    @Test
    void sumArray_IntWithNegativeNumbers_ReturnsCorrectSum() {
        int[] array = {-2, 3, -1, 4};
        assertEquals(4, arrayService.sumArray(array));
    }

    @Test
    void sumArray_SingleDoubleElement_ReturnsElement() {
        double[] array = {5.5};
        assertEquals(5.5, arrayService.sumArray(array));
    }

    @Test
    void sumArray_MultipleDoubleElements_ReturnsSum() {
        double[] array = {1.1, 2.2, 3.3};
        assertEquals(6.6, arrayService.sumArray(array), 0.001);
    }

    @Test
    void sumArray_NullIntArray_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> arrayService.sumArray((int[]) null));
    }

    @Test
    void sumArray_EmptyIntArray_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> arrayService.sumArray(new int[]{}));
    }

    @Test
    void sumArray_ArrayExceedingMaxLength_ThrowsIllegalArgumentException() {
        int[] largeArray = new int[1001];
        assertThrows(IllegalArgumentException.class, () -> arrayService.sumArray(largeArray));
    }

    @Test
    void sumArray_DoubleArrayWithNaN_ThrowsIllegalArgumentException() {
        double[] array = {1.0, Double.NaN, 3.0};
        assertThrows(IllegalArgumentException.class, () -> arrayService.sumArray(array));
    }

    @Test
    void sumArray_DoubleArrayWithInfinite_ThrowsIllegalArgumentException() {
        double[] array = {1.0, Double.POSITIVE_INFINITY, 3.0};
        assertThrows(IllegalArgumentException.class, () -> arrayService.sumArray(array));
    }

    @Test
    void sumArray_DoubleArrayWithNegativeZero_ThrowsIllegalArgumentException() {
        double[] array = {1.0, -0.0, 3.0};
        assertThrows(IllegalArgumentException.class, () -> arrayService.sumArray(array));
    }

    @Test
    void sumArray_DoubleArrayWithScientificNotation_ThrowsIllegalArgumentException() {
        double[] array = {1.0, 1.5E10, 3.0};
        assertThrows(IllegalArgumentException.class, () -> arrayService.sumArray(array));
    }

    @Test
    void sumArray_DoubleArrayWithExcessiveValue_ThrowsIllegalArgumentException() {
        double[] array = {1.0, Double.MAX_VALUE, 3.0};
        assertThrows(IllegalArgumentException.class, () -> arrayService.sumArray(array));
    }

    // Sort Array Tests
    @ParameterizedTest
    @CsvSource({
            "'ascending', '1,2,3,4,5'",
            "'a', '1,2,3,4,5'",
            "'descending', '5,4,3,2,1'",
            "'d', '5,4,3,2,1'"
    })
    void sortArray_IntArray_ValidOrders_ReturnsSortedArray(String orderType, String expected) {
        int[] array = {5, 2, 4, 1, 3};
        int[] result = arrayService.sortArray(array, orderType);
        assertArrayEquals(parseIntArray(expected), result);
    }

    @ParameterizedTest
    @CsvSource({
            "'ascending', '1.1,2.2,3.3'",
            "'a', '1.1,2.2,3.3'",
            "'descending', '3.3,2.2,1.1'",
            "'d', '3.3,2.2,1.1'"
    })
    void sortArray_DoubleArray_ValidOrders_ReturnsSortedArray(String orderType, String expected) {
        double[] array = {3.3, 1.1, 2.2};
        double[] result = arrayService.sortArray(array, orderType);
        assertArrayEquals(parseDoubleArray(expected), result, 0.001);
    }

    @Test
    void sortArray_IntArrayNullOrder_ThrowsIllegalArgumentException() {
        int[] array = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> arrayService.sortArray(array, null));
    }

    @Test
    void sortArray_IntArrayEmptyOrder_ThrowsIllegalArgumentException() {
        int[] array = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> arrayService.sortArray(array, ""));
    }

    @Test
    void sortArray_IntArrayInvalidOrder_ThrowsIllegalArgumentException() {
        int[] array = {1, 2, 3};
        assertThrows(RuntimeException.class, () -> arrayService.sortArray(array, "invalid"));
    }

    @Test
    void sortArray_NullIntArray_ThrowsIllegalArgumentException() {
        assertThrows(RuntimeException.class, () -> arrayService.sortArray((int[]) null, "ascending"));
    }

    // Separate Array Tests - Int
    @Test
    void separateArray_IntArrayParity_ReturnsEvenOddSeparation() {
        int[] array = {1, 2, 3, 4, 5, 6};
        SeparationResult<Integer> result = arrayService.separateArray(array, "parity");

        assertEquals("parity", result.getSeparationType());
        assertArrayEquals(new Integer[]{2, 4, 6}, result.getEven().toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 3, 5}, result.getOdd().toArray(new Integer[0]));
    }

    @Test
    void separateArray_IntArraySign_ReturnsPositiveNegativeSeparation() {
        int[] array = {-2, -1, 0, 1, 2};
        SeparationResult<Integer> result = arrayService.separateArray(array, "sign");

        assertEquals("sign", result.getSeparationType());
        assertArrayEquals(new Integer[]{0, 1, 2}, result.getPositive().toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{-2, -1}, result.getNegative().toArray(new Integer[0]));
    }

    @Test
    void separateArray_IntArrayShortFormSeparationTypes_WorksCorrectly() {
        int[] array = {1, 2, 3, 4};

        SeparationResult<Integer> parityResult = arrayService.separateArray(array, "p");
        assertEquals("parity", parityResult.getSeparationType());

        SeparationResult<Integer> signResult = arrayService.separateArray(array, "s");
        assertEquals("sign", signResult.getSeparationType());
    }

    @Test
    void separateArray_IntArrayNullSeparationType_ThrowsIllegalArgumentException() {
        int[] array = {1, 2, 3};
        assertThrows(RuntimeException.class, () -> arrayService.separateArray(array, null));
    }

    @Test
    void separateArray_IntArrayInvalidSeparationType_ThrowsIllegalArgumentException() {
        int[] array = {1, 2, 3};
        assertThrows(RuntimeException.class, () -> arrayService.separateArray(array, "invalid"));
    }
/*
    // Separate Array Tests - Double
    @Test
    void separateArray_DoubleArrayParity_ReturnsEvenOddSeparation() {
        double[] array = {1.0, 2.0, 3.0, 4.0};
        SeparationResult<Double> result = arrayService.separateArray(array, "parity");

        assertEquals("parity", result.getSeparationType());
        assertArrayEquals(new Double[]{2.0, 4.0}, result.getEven().toArray(new Double[0]), 0.001);
        assertArrayEquals(new Double[]{1.0, 3.0}, result.getOdd().toArray(new Double[0]), 0.001);
    }

    @Test
    void separateArray_DoubleArraySign_ReturnsPositiveNegativeSeparation() {
        double[] array = {-2.0, -1.0, 0.0, 1.0, 2.0};
        SeparationResult<Double> result = arrayService.separateArray(array, "sign");

        assertEquals("sign", result.getSeparationType());
        assertArrayEquals(new Double[]{0.0, 1.0, 2.0}, result.getPositive().toArray(new Double[0]), 0.001);
        assertArrayEquals(new Double[]{-2.0, -1.0}, result.getNegative().toArray(new Double[0]), 0.001);
    }
*/
    // Edge Cases and Boundary Tests
    @Test
    void sortArray_IntArrayWithDuplicates_MaintainsCorrectCount() {
        int[] array = {2, 2, 1, 1, 3, 3};
        int[] result = arrayService.sortArray(array, "ascending");
        assertArrayEquals(new int[]{1, 1, 2, 2, 3, 3}, result);
    }

    @Test
    void sortArray_IntArrayWithSameElements_ReturnsSameArray() {
        int[] array = {5, 5, 5, 5};
        int[] result = arrayService.sortArray(array, "ascending");
        assertArrayEquals(new int[]{5, 5, 5, 5}, result);
    }

    @Test
    void separateArray_IntArrayAllEven_ReturnsEmptyOddList() {
        int[] array = {2, 4, 6, 8};
        SeparationResult<Integer> result = arrayService.separateArray(array, "parity");

        assertEquals(4, result.getEven().size());
        assertEquals(0, result.getOdd().size());
    }

    @Test
    void separateArray_IntArrayAllOdd_ReturnsEmptyEvenList() {
        int[] array = {1, 3, 5, 7};
        SeparationResult<Integer> result = arrayService.separateArray(array, "parity");

        assertEquals(0, result.getEven().size());
        assertEquals(4, result.getOdd().size());
    }

    @Test
    void separateArray_IntArrayAllPositive_ReturnsEmptyNegativeList() {
        int[] array = {1, 2, 3, 4};
        SeparationResult<Integer> result = arrayService.separateArray(array, "sign");

        assertEquals(4, result.getPositive().size());
        assertEquals(0, result.getNegative().size());
    }

    @Test
    void separateArray_IntArrayAllNegative_ReturnsEmptyPositiveListExceptZero() {
        int[] array = {-1, -2, -3, -4};
        SeparationResult<Integer> result = arrayService.separateArray(array, "sign");

        assertEquals(0, result.getPositive().size());
        assertEquals(4, result.getNegative().size());
    }

    @Test
    void sortArray_IntArrayWithMinMaxValues_HandlesCorrectly() {
        int[] array = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        int[] result = arrayService.sortArray(array, "ascending");
        assertArrayEquals(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, result);
    }

    @Test
    void sumArray_LargeArrayWithinLimit_HandlesCorrectly() {
        int[] array = new int[100];
        for (int i = 0; i < 100; i++) {
            array[i] = 1;
        }
        assertEquals(100, arrayService.sumArray(array));
    }

    // Helper methods
    private int[] parseIntArray(String str) {
        return java.util.Arrays.stream(str.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private double[] parseDoubleArray(String str) {
        return java.util.Arrays.stream(str.split(","))
                .map(String::trim)
                .mapToDouble(Double::parseDouble)
                .toArray();
    }
}