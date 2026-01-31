package com.string_manipulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;



import static org.junit.jupiter.api.Assertions.*;

class StringServiceTest {

    private StringService stringService;

    @BeforeEach
    void setUp() {
        stringService = new StringService();
    }

    // ==================== reverseString Tests ====================

    @Test
    @DisplayName("reverseString - valid input returns reversed string")
    void reverseString_ValidInput_ReturnsReversedString() {
        assertEquals("olleh", stringService.reverseString("hello"));
        assertEquals("A", stringService.reverseString("A"));
        assertEquals("dlrow olleh", stringService.reverseString("hello world"));
        assertThrows(IllegalArgumentException.class,
                () -> stringService.reverseString("  "));
        assertEquals("Ba", stringService.reverseString("aB"));
        assertEquals("12345", stringService.reverseString("54321"));
        assertEquals("!@#$%", stringService.reverseString("%$#@!"));
    }

    @ParameterizedTest
    @DisplayName("reverseString - parameterized test cases")
    @CsvSource({
            "'hello', 'olleh'",
            "'world', 'dlrow'",
            "'test', 'tset'",
            "'a', 'a'",
            "'ab', 'ba'"
    })
    void reverseString_ParameterizedTest(String input, String expected) {
        assertEquals(expected, stringService.reverseString(input));
    }

    @Test
    @DisplayName("reverseString - control characters are removed")
    void reverseString_WithControlCharacters_RemovesControlCharacters() {
        assertEquals("tset", stringService.reverseString("t\0e\1s\2t"));
        assertEquals("t\te\n\r t", stringService.reverseString("t\r\ne\tt"));// Should keep these
    }

    @Test
    @DisplayName("reverseString - null input throws exception")
    void reverseString_NullInput_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> stringService.reverseString(null)
        );
        assertEquals("String cannot by null", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("reverseString - invalid inputs throw exceptions")
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n", "\r", "  \t\n  "})
    void reverseString_InvalidInputs_ThrowsIllegalArgumentException(String input) {
        assertThrows(IllegalArgumentException.class, () -> stringService.reverseString(input));
    }

    @Test
    @DisplayName("reverseString - exceeds max length throws exception")
    void reverseString_ExceedsMaxLength_ThrowsIllegalArgumentException() {
        String longString = "a".repeat(10001);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> stringService.reverseString(longString)
        );
        assertTrue(exception.getMessage().contains("exceeds maximum length"));
    }

    @Test
    @DisplayName("reverseString - boundary length works correctly")
    void reverseString_BoundaryLength_WorksCorrectly() {
        String maxLengthString = "a".repeat(1000);
        assertDoesNotThrow(() -> stringService.reverseString(maxLengthString));
        assertEquals(maxLengthString, stringService.reverseString(maxLengthString));
    }

    // ==================== shiftString Tests ====================

    @Test
    @DisplayName("shiftString - left shift works correctly")
    void shiftString_LeftShift_ValidInput_ReturnsShiftedString() {
        assertEquals("llohe", stringService.shiftString("hello", 2, "left"));
        assertEquals("llohe", stringService.shiftString("hello", 2, "LEFT"));
        assertEquals("llohe", stringService.shiftString("hello", 2, "l"));
        assertEquals("llohe", stringService.shiftString("hello", 2, "L"));
    }

    @Test
    @DisplayName("shiftString - right shift works correctly")
    void shiftString_RightShift_ValidInput_ReturnsShiftedString() {
        assertEquals("lohel", stringService.shiftString("hello", 2, "right"));
        assertEquals("lohel", stringService.shiftString("hello", 2, "RIGHT"));
        assertEquals("lohel", stringService.shiftString("hello", 2, "r"));
        assertEquals("lohel", stringService.shiftString("hello", 2, "R"));
    }

    @ParameterizedTest
    @DisplayName("shiftString - parameterized shift tests")
    @CsvSource({
            "'hello', 2, 'left', 'llohe'",
            "'hello', 2, 'right', 'lohel'",
            "'hello', 5, 'left', 'hello'",
            "'hello', 5, 'right', 'hello'",
            "'hello', 7, 'left', 'llohe'",
            "'hello', 7, 'right', 'lohel'"
    })
    void shiftString_ParameterizedTest(String input, int shifts, String direction, String expected) {
        assertEquals(expected, stringService.shiftString(input, shifts, direction));
    }

    @Test
    @DisplayName("shiftString - zero shift returns original")
    void shiftString_ZeroShift_ReturnsOriginalString() {
        assertEquals("hello", stringService.shiftString("hello", 0, "left"));
        assertEquals("hello", stringService.shiftString("hello", 0, "right"));
    }

    @Test
    @DisplayName("shiftString - special characters handled correctly")
    void shiftString_WithSpecialCharacters_HandlesCorrectly() {
        assertEquals("llo!he", stringService.shiftString("hello!", 2, "left"));
        assertEquals("lo@!@", stringService.shiftString("@!@lo", 2, "right"));
    }

    @Test
    @DisplayName("shiftString - null string throws exception")
    void shiftString_NullString_ThrowsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> stringService.shiftString(null, 1, "left")
        );
    }

    @Test
    @DisplayName("shiftString - empty string throws exception")
    void shiftString_EmptyString_ThrowsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> stringService.shiftString("", 1, "left")
        );
    }

    @Test
    @DisplayName("shiftString - negative shifts throw exception")
    void shiftString_NegativeShifts_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> stringService.shiftString("hello", -1, "left")
        );
        assertEquals("Shifts cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("shiftString - null direction throws exception")
    void shiftString_NullDirection_ThrowsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> stringService.shiftString("hello", 1, null)
        );
    }

    @ParameterizedTest
    @DisplayName("shiftString - invalid directions throw exceptions")
    @ValueSource(strings = {"up", "down", "forward", "backward", "", "invalid"})
    void shiftString_InvalidDirection_ThrowsIllegalArgumentException(String direction) {
        assertThrows(
                IllegalArgumentException.class,
                () -> stringService.shiftString("hello", 1, direction)
        );
    }

    // ==================== Performance Tests ====================

    @Test
    @DisplayName("reverseString - large input performance")
    @Timeout(value = 100, unit = java.util.concurrent.TimeUnit.MILLISECONDS)
    void reverseString_LargeInput_PerformsWithinTimeLimit() {
        String largeString = "a".repeat(999);
        assertDoesNotThrow(() -> stringService.reverseString(largeString));
    }

    @Test
    @DisplayName("shiftString - large input performance")
    @Timeout(value = 100, unit = java.util.concurrent.TimeUnit.MILLISECONDS)
    void shiftString_LargeInput_PerformsWithinTimeLimit() {
        String largeString = "a".repeat(999);
        assertDoesNotThrow(() -> stringService.shiftString(largeString, 100, "left"));
    }

    // ==================== Unicode Tests ====================

    @Test
    @DisplayName("reverseString - unicode characters")
    void reverseString_UnicodeCharacters_HandlesCorrectly() {
        assertEquals("👍😀", stringService.reverseString("😀👍"));
        assertEquals("ñáéíóú", stringService.reverseString("úóíéáñ"));
    }

    @Test
    @DisplayName("shiftString - unicode characters")
    void shiftString_UnicodeCharacters_HandlesCorrectly() {
        assertEquals("👍😀", stringService.shiftString("😀👍", 1, "left"));
        assertEquals("😀👍", stringService.shiftString("👍😀", 1, "right"));
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("shiftString - single character string")
    void shiftString_SingleCharacterString_ReturnsSameString() {
        assertEquals("a", stringService.shiftString("a", 1, "left"));
        assertEquals("a", stringService.shiftString("a", 1, "right"));
        assertEquals("a", stringService.shiftString("a", 100, "left"));
    }

    @Test
    @DisplayName("shiftString - two character string")
    void shiftString_TwoCharacterString_WorksCorrectly() {
        assertEquals("ba", stringService.shiftString("ab", 1, "left"));
        assertEquals("ba", stringService.shiftString("ab", 1, "right"));
        assertEquals("ab", stringService.shiftString("ab", 2, "left"));
    }

    @Test
    @DisplayName("reverseString - mixed whitespace and characters")
    void reverseString_MixedWhitespaceAndCharacters_HandlesCorrectly() {
        assertEquals(" olleh", stringService.reverseString("hello "));
        assertEquals("olleh ", stringService.reverseString(" hello"));
        assertEquals("  dlrow  ", stringService.reverseString("  world  "));
    }

    // ==================== Integration Tests ====================

    @Test
    @DisplayName("Integration - reverse and shift operations")
    void integration_ReverseAndShiftOperations_WorkTogether() {
        String original = "hello";
        String reversed = stringService.reverseString(original);
        String shifted = stringService.shiftString(reversed, 2, "left");

        assertEquals("olleh", reversed);
        assertEquals("lehol", shifted);
    }

    @Test
    @DisplayName("Integration - multiple operations on same string")
    void integration_MultipleOperationsOnSameString_MaintainConsistency() {
        String original = "testing";

        // Multiple reverses should return original
        String reversed1 = stringService.reverseString(original);
        String reversed2 = stringService.reverseString(reversed1);
        assertEquals(original, reversed2);

        // Multiple shifts with full rotation should return original
        String shifted1 = stringService.shiftString(original, 2, "left");
        String shifted2 = stringService.shiftString(shifted1, original.length() - 2, "left");
        assertEquals(original, shifted2);
    }
}