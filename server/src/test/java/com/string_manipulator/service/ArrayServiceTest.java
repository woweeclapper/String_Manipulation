package com.string_manipulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(RuntimeException.class, () -> arrayService.sortArray(array, null));
    }

    @Test
    void sortArray_IntArrayEmptyOrder_ThrowsIllegalArgumentException() {
        int[] array = {1, 2, 3};
        assertThrows(RuntimeException.class, () -> arrayService.sortArray(array, ""));
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

        assertEquals(SeparationResult.SeparationType.PARITY, result.getSeparationType());
        assertArrayEquals(new Integer[]{2, 4, 6}, result.getEven().toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{1, 3, 5}, result.getOdd().toArray(new Integer[0]));
    }

    @Test
    void separateArray_IntArraySign_ReturnsPositiveNegativeSeparation() {
        int[] array = {-2, -1, 0, 1, 2};
        SeparationResult<Integer> result = arrayService.separateArray(array, "sign");

        assertEquals(SeparationResult.SeparationType.SIGN, result.getSeparationType());
        assertArrayEquals(new Integer[]{0, 1, 2}, result.getPositive().toArray(new Integer[0]));
        assertArrayEquals(new Integer[]{-2, -1}, result.getNegative().toArray(new Integer[0]));
    }

    @Test
    void separateArray_IntArrayShortFormSeparationTypes_WorksCorrectly() {
        int[] array = {1, 2, 3, 4};

        SeparationResult<Integer> parityResult = arrayService.separateArray(array, "p");
        assertEquals(SeparationResult.SeparationType.PARITY, parityResult.getSeparationType());

        SeparationResult<Integer> signResult = arrayService.separateArray(array, "s");
        assertEquals(SeparationResult.SeparationType.SIGN, signResult.getSeparationType());
    }

    @Test
    void separateArray_IntArrayNullSeparationType_ThrowsRuntimeException() {
        int[] array = {1, 2, 3};
        assertThrows(RuntimeException.class, () -> arrayService.separateArray(array, null));
    }

    @Test
    void separateArray_IntArrayInvalidSeparationType_ThrowsRuntimeException() {
        int[] array = {1, 2, 3};
        assertThrows(RuntimeException.class, () -> arrayService.separateArray(array, "invalid"));
    }

    // Separate Array Tests - Double (ENABLED)
    @Test
    void separateArray_DoubleArrayParity_ReturnsEvenOddSeparation() {
        double[] array = {1.0, 2.0, 3.0, 4.0};
        SeparationResult<Double> result = arrayService.separateArray(array, "parity");

        assertEquals(SeparationResult.SeparationType.PARITY, result.getSeparationType());
        assertArrayEquals(new double[]{2.0, 4.0}, result.getEven().stream().mapToDouble(Double::doubleValue).toArray(), 0.001);
        assertArrayEquals(new double[]{1.0, 3.0}, result.getOdd().stream().mapToDouble(Double::doubleValue).toArray(), 0.001);

    }

    @Test
    void separateArray_DoubleArraySign_ReturnsPositiveNegativeSeparation() {
        double[] array = {-2.0, -1.0, 0.0, 1.0, 2.0};
        SeparationResult<Double> result = arrayService.separateArray(array, "sign");

        assertEquals(SeparationResult.SeparationType.SIGN, result.getSeparationType());
        // Convert -0.0 to 0.0 for comparison
        double[] positiveResult = result.getPositive().stream()
                .mapToDouble(d -> d == 0.0 ? 0.0 : d)  // Convert any -0.0 to 0.0
                .toArray();
        assertArrayEquals(new double[]{0.0, 1.0, 2.0}, positiveResult, 0.001);
        assertArrayEquals(new double[]{-2.0, -1.0}, result.getNegative().stream().mapToDouble(Double::doubleValue).toArray(), 0.001);

    }

    // Type Safety Tests
    @Test
    void separateArray_ParityResult_ThrowsExceptionWhenAccessingSignMethods() {
        int[] array = {1, 2, 3, 4};
        SeparationResult<Integer> result = arrayService.separateArray(array, "parity");

        assertThrows(IllegalStateException.class, result::getPositive);
        assertThrows(IllegalStateException.class, result::getNegative);
    }

    @Test
    void separateArray_SignResult_ThrowsExceptionWhenAccessingParityMethods() {
        int[] array = {-1, 0, 1};
        SeparationResult<Integer> result = arrayService.separateArray(array, "sign");

        assertThrows(IllegalStateException.class, result::getEven);
        assertThrows(IllegalStateException.class, result::getOdd);
    }

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

    // Double Array Separation Edge Cases
    @Test
    void separateArray_DoubleArrayAllEven_ReturnsEmptyOddList() {
        double[] array = {2.0, 4.0, 6.0, 8.0};
        SeparationResult<Double> result = arrayService.separateArray(array, "parity");

        assertEquals(4, result.getEven().size());
        assertEquals(0, result.getOdd().size());
    }

    @Test
    void separateArray_DoubleArrayAllOdd_ReturnsEmptyEvenList() {
        double[] array = {1.0, 3.0, 5.0, 7.0};
        SeparationResult<Double> result = arrayService.separateArray(array, "parity");

        assertEquals(0, result.getEven().size());
        assertEquals(4, result.getOdd().size());
    }

    @Test
    void separateArray_DoubleArrayAllPositive_ReturnsEmptyNegativeList() {
        double[] array = {1.0, 2.0, 3.0, 4.0};
        SeparationResult<Double> result = arrayService.separateArray(array, "sign");

        assertEquals(4, result.getPositive().size());
        assertEquals(0, result.getNegative().size());
    }

    @Test
    void separateArray_DoubleArrayAllNegative_ReturnsEmptyPositiveListExceptZero() {
        double[] array = {-1.0, -2.0, -3.0, -4.0};
        SeparationResult<Double> result = arrayService.separateArray(array, "sign");

        assertEquals(0, result.getPositive().size());
        assertEquals(4, result.getNegative().size());
    }
// ============================================================================
// INTEGRATION TESTS - Testing complete workflows
// ============================================================================

    @Test
    @DisplayName("Integration: Sort then separate int array")
    void integration_SortThenSeparateIntArray() {
        int[] array = {3, -1, 2, -4, 0, 5};

        // Sort ascending, then separate by sign
        int[] sorted = arrayService.sortArray(array, "ascending");
        SeparationResult<Integer> separated = arrayService.separateArray(sorted, "sign");

        assertEquals(SeparationResult.SeparationType.SIGN, separated.getSeparationType());
        assertArrayEquals(new int[]{0, 2, 3, 5}, separated.getPositive().stream().mapToInt(Integer::intValue).toArray());
        assertArrayEquals(new int[]{-4, -1}, separated.getNegative().stream().mapToInt(Integer::intValue).toArray());
    }

    @Test
    @DisplayName("Integration: Separate then sort double array")
    void integration_SeparateThenSortDoubleArray() {
        double[] array = {-2.5, 1.1, 0.0, -3.7, 2.2};

        // Separate by sign, then sort each part
        SeparationResult<Double> separated = arrayService.separateArray(array, "sign");

        // Sort the positive part
        double[] positiveArray = separated.getPositive().stream().mapToDouble(Double::doubleValue).toArray();
        double[] sortedPositive = arrayService.sortArray(positiveArray, "ascending");

        assertArrayEquals(new double[]{0.0, 1.1, 2.2}, sortedPositive, 0.001);
    }

    @Test
    @DisplayName("Integration: Sum after multiple operations")
    void integration_SumAfterMultipleOperations() {
        int[] array = {5, -3, 2, -1, 4};

        // Sort, separate, then sum both parts
        int[] sorted = arrayService.sortArray(array, "descending");
        SeparationResult<Integer> separated = arrayService.separateArray(sorted, "sign");

        int positiveSum = arrayService.sumArray(separated.getPositive().stream().mapToInt(Integer::intValue).toArray());
        int negativeSum = arrayService.sumArray(separated.getNegative().stream().mapToInt(Integer::intValue).toArray());

        assertEquals(11, positiveSum); // 5 + 2 + 4
        assertEquals(-4, negativeSum); // -3 + -1
    }

// ============================================================================
// EXTREME EDGE CASES - Boundary testing
// ============================================================================

    @Test
    @DisplayName("Edge case: Maximum array length")
    void edgeCase_MaximumArrayLength() {
        int[] maxArray = new int[1000];
        java.util.Arrays.fill(maxArray, 1);

        // Should handle maximum length without issues
        assertDoesNotThrow(() -> arrayService.sumArray(maxArray));
        assertEquals(1000, arrayService.sumArray(maxArray));
    }

    @Test
    @DisplayName("Edge case: Array with Integer.MAX_VALUE")
    void edgeCase_IntegerMaxValue() {
        int[] array = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 1, -1};

        assertDoesNotThrow(() -> arrayService.sumArray(array));
        assertEquals(-1, arrayService.sumArray(array)); // MAX_VALUE + MIN_VALUE + 0 + 1 + -1 = 1
    }

    @Test
    @DisplayName("Edge case: Array with Double.MAX_VALUE")
    void edgeCase_DoubleMaxValue() {
        double[] array = {Double.MAX_VALUE / 4, Double.MAX_VALUE / 4, Double.MAX_VALUE / 4};

        assertDoesNotThrow(() -> arrayService.sumArray(array));
        assertEquals(Double.MAX_VALUE * 0.75, arrayService.sumArray(array), 0.001);
    }

    @Test
    @DisplayName("Edge case: Very small decimal numbers")
    void edgeCase_VerySmallDecimals() {
        double[] array = {Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE};

        assertDoesNotThrow(() -> arrayService.sumArray(array));
        assertEquals(Double.MIN_VALUE * 3, arrayService.sumArray(array), 0.0);
    }

    @Test
    @DisplayName("Edge case: Array with alternating extremes")
    void edgeCase_AlternatingExtremes() {
        int[] array = {Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE};

        assertDoesNotThrow(() -> arrayService.sortArray(array, "ascending"));
        assertDoesNotThrow(() -> arrayService.separateArray(array, "sign"));
    }

// ============================================================================
// INPUT VALIDATION STRESS TESTS
// ============================================================================

    @Test
    @DisplayName("Validation stress test: Various invalid inputs")
    void validationStress_InvalidInputs() {
        // Test all validation scenarios
        assertThrows(IllegalArgumentException.class, () -> arrayService.sortArray((int[]) null, "ascending"));
        assertThrows(IllegalArgumentException.class, () -> arrayService.sortArray(new int[0], "ascending"));
        assertThrows(IllegalArgumentException.class, () -> arrayService.sortArray(new int[1001], "ascending"));
        assertThrows(IllegalArgumentException.class, () -> arrayService.sortArray(new int[]{1}, "invalid"));
        assertThrows(IllegalArgumentException.class, () -> arrayService.sortArray(new int[]{1}, ""));
        assertThrows(IllegalArgumentException.class, () -> arrayService.sortArray(new int[]{1}, null));
    }

    @Test
    @DisplayName("Validation stress test: Double array edge cases")
    void validationStress_DoubleEdgeCases() {
        // Test special double values
        assertThrows(IllegalArgumentException.class, () -> {
            double[] nanArray = {1.0, Double.NaN, 2.0};
            arrayService.sumArray(nanArray);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            double[] infArray = {1.0, Double.POSITIVE_INFINITY, 2.0};
            arrayService.sumArray(infArray);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            double[] negInfArray = {1.0, Double.NEGATIVE_INFINITY, 2.0};
            arrayService.sumArray(negInfArray);
        });

        // Test scientific notation
        assertThrows(IllegalArgumentException.class, () -> {
            double[] sciArray = {1.0, 1.5E10, 2.0};
            arrayService.sumArray(sciArray);
        });
    }

    @Test
    @DisplayName("Validation stress test: Boundary numeric values")
    void validationStress_BoundaryValues() {
        // Test values at the boundary of MAX_NUMERIC_VALUE
        double boundaryValue = Double.MAX_VALUE / 2;
        double[] boundaryArray = {boundaryValue, boundaryValue};

        assertDoesNotThrow(() -> arrayService.sumArray(boundaryArray));

        // Test values just over the boundary
        double overBoundary = Double.MAX_VALUE / 2 + 1.0;
        double[] overBoundaryArray = {overBoundary};

        assertThrows(IllegalArgumentException.class, () -> arrayService.sumArray(overBoundaryArray));
    }

// ============================================================================
// PERFORMANCE AND STRESS TESTS
// ============================================================================

    @Test
    @DisplayName("Performance test: Large array operations")
    void performance_LargeArrayOperations() {
        int[] largeArray = new int[500];
        java.util.Random random = new java.util.Random();

        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = random.nextInt(1000) - 500; // Random between -500 and 500
        }

        // Should handle large arrays efficiently
        assertDoesNotThrow(() -> {
            arrayService.sortArray(largeArray, "ascending");
            arrayService.separateArray(largeArray, "sign");
            arrayService.sumArray(largeArray);
        });
    }

    @Test
    @DisplayName("Stress test: Rapid successive operations")
    void stress_RapidSuccessiveOperations() {
        int[] array = {5, -3, 2, -1, 4, 0, 7, -2};

        // Perform many operations in succession
        for (int i = 0; i < 100; i++) {
            assertDoesNotThrow(() -> {
                arrayService.sortArray(array, "ascending");
                arrayService.sortArray(array, "descending");
                arrayService.separateArray(array, "sign");
                arrayService.separateArray(array, "parity");
                arrayService.sumArray(array);
            });
        }
    }

// ============================================================================
// THREAD SAFETY TESTS
// ============================================================================

    @Test
    @DisplayName("Thread safety: Concurrent operations")
    void threadSafety_ConcurrentOperations() throws InterruptedException {
        int[] sharedArray = {1, 2, 3, 4, 5};
        int threadCount = 10;
        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(threadCount);
        java.util.concurrent.atomic.AtomicInteger errorCount = new java.util.concurrent.atomic.AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    arrayService.sortArray(sharedArray, "ascending");
                    arrayService.separateArray(sharedArray, "sign");
                    arrayService.sumArray(sharedArray);
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await(5000, java.util.concurrent.TimeUnit.MILLISECONDS);
        assertEquals(0, errorCount.get(), "No errors should occur during concurrent operations");
    }

// ============================================================================
// DATA INTEGRITY TESTS
// ============================================================================

    @Test
    @DisplayName("Data integrity: Original array unchanged")
    void dataIntegrity_OriginalArrayUnchanged() {
        int[] original = {3, 1, 4, 1, 5};
        int[] copy = original.clone();

        // Perform operations
        arrayService.sortArray(original, "ascending");
        arrayService.separateArray(original, "sign");
        arrayService.sumArray(original);

        // Original array should be unchanged
        assertArrayEquals(copy, original, "Original array should not be modified by operations");
    }

    @Test
    @DisplayName("Data integrity: Immutability of separation results")
    void dataIntegrity_SeparationResultImmutability() {
        int[] array = {1, 2, 3, 4, 5};
        SeparationResult<Integer> result = arrayService.separateArray(array, "parity");

        // Try to modify the returned lists
        assertThrows(UnsupportedOperationException.class, () -> {
            result.getPositive().add(6);
        });

        assertThrows(UnsupportedOperationException.class, () -> {
            result.getNegative().add(0);
        });
    }

// ============================================================================
// ERROR PROPAGATION TESTS
// ============================================================================

    @Test
    @DisplayName("Error propagation: Nested exception handling")
    void errorPropagation_NestedExceptions() {
        // Test that exceptions from underlying utilities are properly wrapped
        Exception exception = assertThrows(RuntimeException.class, () -> {
            arrayService.separateArray(new int[]{1}, "invalid_type");
        });

        assertTrue(exception.getMessage().contains("Failed to separate"));
        assertNotNull(exception.getCause());
    }

    @Test
    @DisplayName("Error propagation: Validation error messages")
    void errorPropagation_ValidationMessages() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            arrayService.sortArray(new int[1001], "ascending");
        });

        assertTrue(exception.getMessage().contains("exceeds maximum length"));
    }

// ============================================================================
// BOUNDARY CONDITION TESTS
// ============================================================================

    @Test
    @DisplayName("Boundary: Single element arrays")
    void boundary_SingleElementArrays() {
        int[] singleInt = {42};
        double[] singleDouble = {3.14};

        assertEquals(42, arrayService.sumArray(singleInt));
        assertEquals(3.14, arrayService.sumArray(singleDouble), 0.001);

        SeparationResult<Integer> intResult = arrayService.separateArray(singleInt, "sign");
        assertEquals(1, intResult.getPositive().size());
        assertEquals(0, intResult.getNegative().size());

        SeparationResult<Double> doubleResult = arrayService.separateArray(singleDouble, "sign");
        assertEquals(1, doubleResult.getPositive().size());
        assertEquals(0, doubleResult.getNegative().size());
    }

    @Test
    @DisplayName("Boundary: Two element arrays")
    void boundary_TwoElementArrays() {
        int[] twoElements = {-1, 1};

        SeparationResult<Integer> signResult = arrayService.separateArray(twoElements, "sign");
        assertEquals(1, signResult.getPositive().size());
        assertEquals(1, signResult.getNegative().size());

        SeparationResult<Integer> parityResult = arrayService.separateArray(twoElements, "sign");
        assertEquals(1, parityResult.getPositive().size());
        assertEquals(1, parityResult.getNegative().size());
    }

// ============================================================================
// MEMORY STRESS TESTS
// ============================================================================

    @Test
    @DisplayName("Memory stress: Multiple large arrays")
    void memoryStress_MultipleLargeArrays() {
        java.util.List<int[]> arrays = new java.util.ArrayList<>();

        try {
            // Create multiple large arrays
            for (int i = 0; i < 10; i++) {
                int[] largeArray = new int[100];
                java.util.Arrays.fill(largeArray, i);
                arrays.add(largeArray);
            }

            // Perform operations on all arrays
            // Perform operations on all arrays
            for (int i = 0; i < arrays.size(); i++) {
                int[] array = arrays.get(i);
                assertDoesNotThrow(() -> {
                    arrayService.sortArray(array, "ascending");
                    arrayService.sumArray(array);
                    arrayService.separateArray(array, "sign");
                });
            }
        } finally {
            arrays.clear(); // Help GC
        }
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