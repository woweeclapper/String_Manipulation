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
        assertEquals("t\te\ns\rt", stringService.reverseString("t\rs\ne\tt"));// Should keep these
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
    // ==================== Extreme Input Validation Tests ====================

    @Test
    @DisplayName("reverseString - string with only control characters")
    void reverseString_OnlyControlCharacters_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> stringService.reverseString("\0\1\2\3\4\5\6\7"));
        assertThrows(IllegalArgumentException.class,
                () -> stringService.reverseString("\u0000\u0001\u0002"));
    }

    @Test
    @DisplayName("reverseString - mixed control and printable characters")
    void reverseString_MixedControlAndPrintable_HandlesCorrectly() {
        assertEquals("tset", stringService.reverseString("t\0e\1s\2t"));
        assertEquals("cba", stringService.reverseString("a\0b\1c\2"));
    }

    @Test
    @DisplayName("shiftString - extremely large shift values")
    void shiftString_ExtremelyLargeShifts_HandlesCorrectly() {
        String input = "hello";
        // Test with Integer.MAX_VALUE
        assertDoesNotThrow(() -> stringService.shiftString(input, Integer.MAX_VALUE, "left"));
        assertDoesNotThrow(() -> stringService.shiftString(input, Integer.MAX_VALUE, "right"));

        // Test with very large numbers
        assertDoesNotThrow(() -> stringService.shiftString(input, 1000000, "left"));
        assertDoesNotThrow(() -> stringService.shiftString(input, 1000000, "right"));
    }

    @Test
    @DisplayName("shiftString - shift equals string length multiples")
    void shiftString_ShiftEqualsLengthMultiples_ReturnsOriginal() {
        String input = "testing";
        int length = input.length();

        // Test multiples of length
        assertEquals(input, stringService.shiftString(input, length, "left"));
        assertEquals(input, stringService.shiftString(input, length * 2, "left"));
        assertEquals(input, stringService.shiftString(input, length * 3, "right"));
        assertEquals(input, stringService.shiftString(input, length * 10, "right"));
    }

// ==================== Unicode and Complex Character Tests ====================

    @Test
    @DisplayName("reverseString - complex emoji sequences")
    void reverseString_ComplexEmojiSequences_HandlesCorrectly() {
        // ZWJ sequences (zero-width joiner)
        assertEquals("👨‍👩‍👧‍👦", stringService.reverseString("👨‍👩‍👧‍👦")); // Family emoji
        assertEquals("🏳️‍🌈", stringService.reverseString("🏳️‍🌈")); // Rainbow flag

        // Skin tone modifiers
        assertEquals("👍🏿", stringService.reverseString("👍🏿")); // Dark skin tone
        assertEquals("👋🏻", stringService.reverseString("👋🏻")); // Light skin tone
    }

    @Test
    @DisplayName("shiftString - complex emoji sequences")
    void shiftString_ComplexEmojiSequences_HandlesCorrectly() {
        String family = "👨‍👩‍👧‍👦";
        assertEquals(family, stringService.shiftString(family, family.length(), "left"));
        assertEquals(family, stringService.shiftString(family, family.length(), "right"));

        // Test partial shifts on complex emojis
        String result = stringService.shiftString(family, 1, "left");
        assertNotNull(result);
        assertEquals(family.length(), result.length());
    }

    @Test
    @DisplayName("reverseString - combining diacritical marks")
    void reverseString_CombiningDiacriticalMarks_HandlesCorrectly() {
        // Base character + combining marks
        assertEquals("é", stringService.reverseString("é")); // e + acute accent
        assertEquals("ñ", stringService.reverseString("ñ")); // n + tilde
        assertEquals("ñáéíóú", stringService.reverseString("úóíéáñ")); // Multiple accents
    }

    @Test
    @DisplayName("shiftString - RTL and LTR mixed text")
    void shiftString_MixedDirectionText_HandlesCorrectly() {
        String mixed = "Hello مرحبا Hello";
        String shifted = stringService.shiftString(mixed, 5, "left");
        assertNotNull(shifted);
        assertEquals(mixed.length(), shifted.length());
    }

// ==================== Memory and Performance Stress Tests ====================

    @Test
    @DisplayName("reverseString - maximum length boundary test")
    void reverseString_MaximumLengthBoundary_ExactlyAtLimit() {
        String maxLength = "a".repeat(1000);
        String result = stringService.reverseString(maxLength);
        assertEquals(maxLength, result);
    }


    @Test
    @DisplayName("shiftString - memory stress with large unicode")
    void shiftString_MemoryStressWithLargeUnicode_PerformsWithinLimits() {
        // Create a string with many complex characters
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append("👨‍👩‍👧‍👦"); // Complex family emoji
        }
        String largeUnicode = sb.toString();

        assertDoesNotThrow(() -> stringService.shiftString(largeUnicode, 100, "left"));
        assertDoesNotThrow(() -> stringService.shiftString(largeUnicode, 100, "right"));
    }

    @Test
    @DisplayName("Performance - concurrent access simulation")
    void performance_ConcurrentAccessSimulation_HandlesCorrectly() {
        String input = "test";

        // Simulate rapid successive calls
        for (int i = 0; i < 1000; i++) {
            final int shift = i % 10;
            assertDoesNotThrow(() -> {
                String reversed = stringService.reverseString(input);
                String shifted = stringService.shiftString(reversed, shift, "left");
                assertNotNull(shifted);
            });
        }
    }

// ==================== Edge Case Boundary Tests ====================

    @Test
    @DisplayName("shiftString - single character with large shift")
    void shiftString_SingleCharacterWithLargeShift_ReturnsSameCharacter() {
        String single = "x";
        assertEquals(single, stringService.shiftString(single, 999999, "left"));
        assertEquals(single, stringService.shiftString(single, 999999, "right"));
    }

    @Test
    @DisplayName("shiftString - two character boundary conditions")
    void shiftString_TwoCharacterBoundaryConditions_WorksCorrectly() {
        String twoChars = "ab";

        // Test all possible shift values
        for (int shift = 0; shift <= 10; shift++) {
            final int currentShift = shift;
            assertDoesNotThrow(() -> {
                String result = stringService.shiftString(twoChars, currentShift, "left");
                assertTrue(result.equals("ab") || result.equals("ba"));
            });
        }
    }

    @Test
    @DisplayName("reverseString - whitespace edge cases")
    void reverseString_WhitespaceEdgeCases_HandlesCorrectly() {
        // Leading/trailing whitespace with content
        assertEquals(" tnetnoc ", stringService.reverseString(" content "));
        assertEquals("\ttnetnoc\t", stringService.reverseString("\tcontent\t"));
        assertEquals("\ntnetnoc\n", stringService.reverseString("\ncontent\n"));

        // Mixed whitespace types
        assertEquals(" \n\t tnetnoc \t\n ", stringService.reverseString(" \n\t content \t\n "));
    }

// ==================== Integration and Complex Scenario Tests ====================

    @Test
    @DisplayName("Integration - reverse then shift complex unicode")
    void integration_ReverseThenShiftComplexUnicode_MaintainsIntegrity() {
        String complex = "👍🏿Hello👨‍👩‍👧‍👦Worldñ";

        String reversed = stringService.reverseString(complex);
        String shifted = stringService.shiftString(reversed, 5, "left");

        // Verify length is preserved
        assertEquals(complex.length(), reversed.length());
        assertEquals(complex.length(), shifted.length());

        // Verify round-trip works
        String shiftedBack = stringService.shiftString(shifted, 5, "right");
        assertEquals(reversed, shiftedBack);
    }

    @Test
    @DisplayName("Integration - multiple operations chain")
    void integration_MultipleOperationsChain_ConsistentResults() {
        String original = "Complex👍🏿Test123";

        // Chain of operations
        String step1 = stringService.reverseString(original);
        String step2 = stringService.shiftString(step1, 3, "left");
        String step3 = stringService.reverseString(step2);
        String step4 = stringService.shiftString(step3, 7, "right");

        // Verify all steps maintain length
        assertEquals(original.length(), step1.length());
        assertEquals(original.length(), step2.length());
        assertEquals(original.length(), step3.length());
        assertEquals(original.length(), step4.length());

        // Verify final result is different from original
        assertNotEquals(original, step4);
    }

    @Test
    @DisplayName("Integration - shift by full length then reverse")
    void integration_ShiftByFullLengthThenReverse_ReturnsReversedOriginal() {
        String original = "testing123";

        String shifted = stringService.shiftString(original, original.length(), "left");
        assertEquals(original, shifted); // Should be same after full rotation

        String reversed = stringService.reverseString(shifted);
        assertEquals("321gnitset", reversed);
    }

// ==================== Error Message Validation Tests ====================

    @Test
    @DisplayName("Error messages - specific validation")
    void errorMessages_SpecificValidation_CorrectText() {
        // Test null error message
        IllegalArgumentException nullEx = assertThrows(
                IllegalArgumentException.class,
                () -> stringService.reverseString(null)
        );
        assertEquals("String cannot by null", nullEx.getMessage()); // Note the typo in original

        // Test empty error message
        IllegalArgumentException emptyEx = assertThrows(
                IllegalArgumentException.class,
                () -> stringService.reverseString("")
        );
        assertEquals("String must contain at least 1 character", emptyEx.getMessage());

        // Test whitespace error message
        IllegalArgumentException whitespaceEx = assertThrows(
                IllegalArgumentException.class,
                () -> stringService.reverseString("   ")
        );
        assertEquals("String cannot contain only whitespace", whitespaceEx.getMessage());

        // Test negative shift error message
        IllegalArgumentException negativeEx = assertThrows(
                IllegalArgumentException.class,
                () -> stringService.shiftString("test", -1, "left")
        );
        assertEquals("Shifts cannot be negative", negativeEx.getMessage());
    }

// ==================== Sanitization Tests ====================

    @Test
    @DisplayName("Sanitization - control character removal verification")
    void sanitization_ControlCharacterRemoval_Verification() {
        // Test various control characters
        assertEquals("tset", stringService.reverseString("te\0st"));
        assertEquals("tset", stringService.reverseString("te\u0001st"));
        assertEquals("tset", stringService.reverseString("te\u001Fst"));
        assertEquals("tset", stringService.reverseString("te\u007Fst"));

        // But keep valid control characters
        assertEquals("t\te", stringService.reverseString("e\tt"));
        assertEquals("t\ne", stringService.reverseString("e\nt"));
        assertEquals("t\re", stringService.reverseString("e\rt"));
    }

    @Test
    @DisplayName("Sanitization - mixed valid and invalid control chars")
    void sanitization_MixedValidInvalidControlChars_HandlesCorrectly() {
        // Mix of valid (tab, newline, carriage return) and invalid control chars
        String input = "start\0\tmiddle\1\nend\2";
        String expected = "dne\nelddim\ttrats"; // After sanitization and reversal
        assertEquals(expected, stringService.reverseString(input));
    }
}