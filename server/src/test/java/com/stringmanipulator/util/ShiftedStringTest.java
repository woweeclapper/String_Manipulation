package com.stringmanipulator.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class ShiftedStringTest {

    @Test
    @DisplayName("Test left shift basic")
    void testLeftShiftBasic() {
        assertEquals("bcda", ShiftedString.shifting("abcd", 1, "left"));
        assertEquals("cdab", ShiftedString.shifting("abcd", 2, "left"));
        assertEquals("dabc", ShiftedString.shifting("abcd", 3, "left"));
    }

    @Test
    @DisplayName("Test right shift basic")
    void testRightShiftBasic() {
        assertEquals("dabc", ShiftedString.shifting("abcd", 1, "right"));
        assertEquals("cdab", ShiftedString.shifting("abcd", 2, "right"));
        assertEquals("bcda", ShiftedString.shifting("abcd", 3, "right"));
    }

    @Test
    @DisplayName("Test zero shifts")
    void testZeroShifts() {
        assertEquals("abcd", ShiftedString.shifting("abcd", 0, "left"));
        assertEquals("abcd", ShiftedString.shifting("abcd", 0, "right"));
    }

    @Test
    @DisplayName("Test full rotation")
    void testFullRotation() {
        assertEquals("abcd", ShiftedString.shifting("abcd", 4, "left"));
        assertEquals("abcd", ShiftedString.shifting("abcd", 4, "right"));
        assertEquals("hello", ShiftedString.shifting("hello", 5, "left"));
        assertEquals("hello", ShiftedString.shifting("hello", 5, "right"));
    }

    @Test
    @DisplayName("Test single character")
    void testSingleCharacter() {
        assertEquals("a", ShiftedString.shifting("a", 1, "left"));
        assertEquals("a", ShiftedString.shifting("a", 5, "right"));
        assertEquals("a", ShiftedString.shifting("a", 100, "left"));
    }

    @Test
    @DisplayName("Test two characters")
    void testTwoCharacters() {
        assertEquals("ba", ShiftedString.shifting("ab", 1, "left"));
        assertEquals("ba", ShiftedString.shifting("ab", 1, "right"));
        assertEquals("ab", ShiftedString.shifting("ab", 2, "left"));
        assertEquals("ba", ShiftedString.shifting("ab", 3, "right"));
    }

    @Test
    @DisplayName("Test large shift values")
    void testLargeShiftValues() {
        assertEquals("bcda", ShiftedString.shifting("abcd", 5, "left")); // 5 % 4 = 1
        assertEquals("cdab", ShiftedString.shifting("abcd", 10, "left")); // 10 % 4 = 2
        assertEquals("bcda", ShiftedString.shifting("abcd", 7, "right")); // 7 % 4 = 3
        assertEquals("abcd", ShiftedString.shifting("abcd", 12, "right")); // 12 % 4 = 0
    }

    @Test
    @DisplayName("Test modulo behavior")
    void testModuloBehavior() {
        String original = "abcdefghij";
        assertEquals(original, ShiftedString.shifting(original, 20, "left")); // 20 % 10 = 0
        assertEquals(original, ShiftedString.shifting(original, 30, "right")); // 30 % 10 = 0
        assertEquals("bcdefghija", ShiftedString.shifting(original, 21, "left")); // 21 % 10 = 1
    }

    @Test
    @DisplayName("Test mixed characters")
    void testMixedCharacters() {
        assertEquals("2341", ShiftedString.shifting("1234", 1, "left"));
        assertEquals("4123", ShiftedString.shifting("1234", 1, "right"));
        assertEquals("aBcDeF", ShiftedString.shifting("aBcDeF", 0, "left"));
        assertEquals("FaBcDe", ShiftedString.shifting("aBcDeF", 1, "right"));
    }

    @Test
    @DisplayName("Test special characters")
    void testSpecialCharacters() {
        assertEquals("%#@$", ShiftedString.shifting("$%#@", 1, "left"));
        assertEquals("%#@$", ShiftedString.shifting("$%#@", 3, "right"));
        assertEquals("#$%^!@", ShiftedString.shifting("!@#$%^", 2, "left"));
        assertEquals("%^!@#$", ShiftedString.shifting("!@#$%^", 2, "right"));
    }

    @Test
    @DisplayName("Test unicode characters - proper code point handling")
    void testUnicodeCharacters() {
        // Test with emoji (multi-byte Unicode characters)
        assertEquals("😊bca", ShiftedString.shifting("a😊bc", 1, "left"));      // ✅ CORRECTED
        assertEquals("bca😊", ShiftedString.shifting("a😊bc", 2, "left"));      // ✅ CORRECTED
        assertEquals("ca😊b", ShiftedString.shifting("a😊bc", 1, "right"));     // ✅ CORRECTED

        // Test with accented characters
        assertEquals("ñáéíóú", ShiftedString.shifting("úñáéíó", 1, "left"));
        assertEquals("óúñáéí", ShiftedString.shifting("úñáéíó", 2, "right"));

        // Test complex Unicode sequences
        assertEquals("👨‍👩‍👧‍👦abc", ShiftedString.shifting("abc👨‍👩‍👧‍👦", 3, "left"));
        assertEquals("c👨‍👩‍👧‍👦ab", ShiftedString.shifting("abc👨‍👩‍👧‍👦", 1, "right"));
    }

    @Test
    @DisplayName("Test whitespace")
    void testWhitespace() {
        assertEquals(" bcda", ShiftedString.shifting("a bcd", 1, "left"));
        assertEquals("da bc", ShiftedString.shifting("a bcd", 4, "left"));
        assertEquals("da bc", ShiftedString.shifting("a bcd", 1, "right"));
        assertEquals("\tbcda", ShiftedString.shifting("a\tbcd", 1, "left"));
    }

    @Test
    @DisplayName("Test maximum shifts")
    void testMaximumShifts() {
        String original = "test";
        assertEquals("ttes", ShiftedString.shifting(original, Integer.MAX_VALUE, "left"));
        assertEquals("estt", ShiftedString.shifting(original, Integer.MAX_VALUE, "right"));
    }

    @Test
    @DisplayName("Test repeated patterns")
    void testRepeatedPatterns() {
        assertEquals("aaaa", ShiftedString.shifting("aaaa", 1, "left"));
        assertEquals("aaaa", ShiftedString.shifting("aaaa", 2, "right"));
        assertEquals("abab", ShiftedString.shifting("abab", 2, "left"));
        assertEquals("baba", ShiftedString.shifting("abab", 1, "right"));
    }

    @Test
    @DisplayName("Test palindrome shifting")
    void testPalindromeShifting() {
        assertEquals("bcbaa", ShiftedString.shifting("abcba", 1, "left"));
        assertEquals("aabcb", ShiftedString.shifting("abcba", 1, "right"));
        assertEquals("cbaab", ShiftedString.shifting("abcba", 2, "left"));
    }

    @Test
    @DisplayName("Test long string shifting")
    void testLongStringShifting() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            sb.append((char)('a' + (i % 26)));
        }
        String original = sb.toString();

        // Use code point aware calculation for expected results
        int[] codePoints = original.codePoints().toArray();
        int[] leftShifted = new int[codePoints.length];
        int[] rightShifted = new int[codePoints.length];

        // Calculate expected left shift
        System.arraycopy(codePoints, 1, leftShifted, 0, codePoints.length - 1);
        leftShifted[codePoints.length - 1] = codePoints[0];

        // Calculate expected right shift
        rightShifted[0] = codePoints[codePoints.length - 1];
        System.arraycopy(codePoints, 0, rightShifted, 1, codePoints.length - 1);

        assertEquals(new String(leftShifted, 0, leftShifted.length), ShiftedString.shifting(original, 1, "left"));
        assertEquals(new String(rightShifted, 0, rightShifted.length), ShiftedString.shifting(original, 1, "right"));
    }

    @Test
    @DisplayName("Test empty string edge case")
    void testEmptyString() {
        String empty = "";
        assertEquals(empty, ShiftedString.shifting(empty, 1, "left"));
        assertEquals(empty, ShiftedString.shifting(empty, 5, "right"));
        assertEquals(empty, ShiftedString.shifting(empty, 0, "left"));
    }

    @Test
    @DisplayName("Test edge case - invalid choice throws exception")
    void testInvalidChoice() {
        String original = "test";

        assertThrows(IllegalArgumentException.class, () -> {
            ShiftedString.shifting(original, 1, "invalid");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            ShiftedString.shifting(original, 2, "up");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            ShiftedString.shifting(original, 3, "down");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            ShiftedString.shifting(original, 1, null);
        });
    }

    @Test
    @DisplayName("Test Unicode surrogate pairs - complex emoji")
    void testUnicodeSurrogatePairs() {
        // Test with complex emoji that use surrogate pairs
        String complexEmoji = "👍🏽🎉🚀";
        assertEquals("🎉🚀👍🏽", ShiftedString.shifting(complexEmoji, 1, "left"));
        assertEquals("🚀👍🏽🎉", ShiftedString.shifting(complexEmoji, 2, "left"));
        assertEquals("🚀👍🏽🎉", ShiftedString.shifting(complexEmoji, 1, "right"));
        assertEquals("🎉🚀👍🏽", ShiftedString.shifting(complexEmoji, 2, "right"));
    }

    @Test
    @DisplayName("Test mixed Unicode and ASCII")
    void testMixedUnicodeAndASCII() {
        String mixed = "a😊b🎉c";
        assertEquals("😊b🎉ca", ShiftedString.shifting(mixed, 1, "left"));
        assertEquals("b🎉ca😊", ShiftedString.shifting(mixed, 2, "left"));
        assertEquals("ca😊b🎉", ShiftedString.shifting(mixed, 1, "right"));
        assertEquals("🎉ca😊b", ShiftedString.shifting(mixed, 2, "right"));
    }

    @Test
    @DisplayName("Test zero length string with shifts")
    void testZeroLengthStringWithShifts() {
        String empty = "";
        // Test various shift values on empty string
        assertEquals(empty, ShiftedString.shifting(empty, 0, "left"));
        assertEquals(empty, ShiftedString.shifting(empty, 1, "left"));
        assertEquals(empty, ShiftedString.shifting(empty, 100, "right"));
    }

    @Test
    @DisplayName("Test performance with large Unicode strings")
    void testPerformanceWithLargeUnicodeStrings() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("😊");
        }
        String largeUnicode = sb.toString();

        // Should handle large Unicode strings efficiently
        String result = ShiftedString.shifting(largeUnicode, 100, "left");
        assertEquals(largeUnicode.length(), result.length());

        // Verify the shift worked correctly
        assertEquals(largeUnicode.substring(100) + largeUnicode.substring(0, 100), result);
    }
}