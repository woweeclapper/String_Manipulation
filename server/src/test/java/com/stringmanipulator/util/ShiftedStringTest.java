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
        assertEquals("ab", ShiftedString.shifting("ab", 1, "right"));
        assertEquals("ab", ShiftedString.shifting("ab", 2, "left"));
        assertEquals("ab", ShiftedString.shifting("ab", 3, "right"));
    }

    @Test
    @DisplayName("Test large shift values")
    void testLargeShiftValues() {
        assertEquals("bcda", ShiftedString.shifting("abcd", 5, "left")); // 5 % 4 = 1
        assertEquals("cdab", ShiftedString.shifting("abcd", 10, "left")); // 10 % 4 = 2
        assertEquals("dabc", ShiftedString.shifting("abcd", 7, "right")); // 7 % 4 = 3
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
        assertEquals("@#$%", ShiftedString.shifting("$%#@", 1, "left"));
        assertEquals("@#$%", ShiftedString.shifting("$%#@", 3, "right"));
        assertEquals("!@#$%^", ShiftedString.shifting("!@#$%^", 2, "left"));
        assertEquals("%^!@#$", ShiftedString.shifting("!@#$%^", 2, "right"));
    }

    @Test
    @DisplayName("Test unicode characters")
    void testUnicodeCharacters() {
        assertEquals("bca😊", ShiftedString.shifting("a😊bc", 1, "left"));
        assertEquals("ca😊b", ShiftedString.shifting("a😊bc", 2, "left"));
        assertEquals("😊bca", ShiftedString.shifting("a😊bc", 1, "right"));
        assertEquals("ñáéíóú", ShiftedString.shifting("úñáéíó", 1, "left"));
        assertEquals("óúñáéí", ShiftedString.shifting("úñáéíó", 2, "right"));
    }

    @Test
    @DisplayName("Test whitespace")
    void testWhitespace() {
        assertEquals("bcd a", ShiftedString.shifting("a bcd", 1, "left"));
        assertEquals("a bcd", ShiftedString.shifting("a bcd", 4, "left"));
        assertEquals("a bcd", ShiftedString.shifting("a bcd", 1, "right"));
        assertEquals("\tbcda", ShiftedString.shifting("a\tbcd", 1, "left"));
    }

    @Test
    @DisplayName("Test maximum shifts")
    void testMaximumShifts() {
        String original = "test";
        assertEquals("estt", ShiftedString.shifting(original, Integer.MAX_VALUE, "left"));
        assertEquals("ttes", ShiftedString.shifting(original, Integer.MAX_VALUE, "right"));
    }

    @Test
    @DisplayName("Test repeated patterns")
    void testRepeatedPatterns() {
        assertEquals("aaaa", ShiftedString.shifting("aaaa", 1, "left"));
        assertEquals("aaaa", ShiftedString.shifting("aaaa", 2, "right"));
        assertEquals("abab", ShiftedString.shifting("abab", 1, "left"));
        assertEquals("baba", ShiftedString.shifting("abab", 1, "right"));
    }

    @Test
    @DisplayName("Test palindrome shifting")
    void testPalindromeShifting() {
        assertEquals("abcba", ShiftedString.shifting("abcba", 1, "left"));
        assertEquals("aabc b", ShiftedString.shifting("abcba", 1, "right"));
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
        String expectedLeft = original.substring(1) + original.charAt(0);
        String expectedRight = original.charAt(original.length() - 1) + original.substring(0, original.length() - 1);

        assertEquals(expectedLeft, ShiftedString.shifting(original, 1, "left"));
        assertEquals(expectedRight, ShiftedString.shifting(original, 1, "right"));
    }

    @Test
    @DisplayName("Test edge case - invalid choice (should return original)")
    void testInvalidChoice() {
        String original = "test";
        assertEquals(original, ShiftedString.shifting(original, 1, "invalid"));
        assertEquals(original, ShiftedString.shifting(original, 2, "up"));
        assertEquals(original, ShiftedString.shifting(original, 3, "down"));
    }
}