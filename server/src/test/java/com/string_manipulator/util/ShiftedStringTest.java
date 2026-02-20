package com.string_manipulator.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("Test zero numOfShifts")
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
        assertEquals("íóúñáé", ShiftedString.shifting("úñáéíó", 2, "right"));

        // Test complex Unicode sequences
        assertEquals("👨‍👩‍👧‍👦abc", ShiftedString.shifting("abc👨‍👩‍👧‍👦", 3, "left"));
        assertEquals("👨‍👩‍👧‍👦abc", ShiftedString.shifting("abc👨‍👩‍👧‍👦", 1, "right"));
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
    @DisplayName("Test maximum numOfShifts")
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
            sb.append((char) ('a' + (i % 26)));
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
    @DisplayName("Test performance with large Unicode strings")
    void testPerformanceWithLargeUnicodeStrings() {
        String largeUnicode = "😊".repeat(1000);

        // Should handle large Unicode strings efficiently
        String result = ShiftedString.shifting(largeUnicode, 100, "left");
        assertEquals(largeUnicode.length(), result.length());

        // Verify the shift worked correctly
        assertEquals(largeUnicode.substring(100) + largeUnicode.substring(0, 100), result);
    }

    @Test
    @DisplayName("Test shift equals string length - identity operation")
    void testShiftEqualsLength() {
        assertEquals("abcd", ShiftedString.shifting("abcd", 4, "left"));
        assertEquals("abcd", ShiftedString.shifting("abcd", 4, "right"));
        assertEquals("hello", ShiftedString.shifting("hello", 5, "left"));
        assertEquals("world", ShiftedString.shifting("world", 5, "right"));
    }

    @Test
    @DisplayName("Test shift greater than string length - large modulo")
    void testLargeModuloShifts() {
        assertEquals("bcda", ShiftedString.shifting("abcd", 9, "left"));   // 9 % 4 = 1
        assertEquals("cdab", ShiftedString.shifting("abcd", 14, "left"));  // 14 % 4 = 2
        assertEquals("dabc", ShiftedString.shifting("abcd", 19, "left"));  // 19 % 4 = 3
        assertEquals("abcd", ShiftedString.shifting("abcd", 20, "left"));  // 20 % 4 = 0

        assertEquals("dabc", ShiftedString.shifting("abcd", 9, "right"));  // 9 % 4 = 1 (right)
        assertEquals("cdab", ShiftedString.shifting("abcd", 14, "right")); // 14 % 4 = 2 (right)
    }

    @Test
    @DisplayName("Test negative shift scenarios via large positive numOfShifts")
    void testNegativeEquivalentShifts() {
        // Left shift by 3 is equivalent to right shift by 1 for 4-char string
        assertEquals("dabc", ShiftedString.shifting("abcd", 3, "left"));
        assertEquals("dabc", ShiftedString.shifting("abcd", 1, "right"));

        // Right shift by 3 is equivalent to left shift by 1 for 4-char string
        assertEquals("bcda", ShiftedString.shifting("abcd", 3, "right"));
        assertEquals("bcda", ShiftedString.shifting("abcd", 1, "left"));
    }

    @Test
    @DisplayName("Test prime length strings")
    void testPrimeLengthStrings() {
        String prime = "abcdefg"; // length 7 (prime)
        assertEquals("bcdefga", ShiftedString.shifting(prime, 1, "left"));
        assertEquals("cdefgab", ShiftedString.shifting(prime, 2, "left"));
        assertEquals("gabcdef", ShiftedString.shifting(prime, 1, "right"));
        assertEquals("fgabcde", ShiftedString.shifting(prime, 2, "right"));

        // Test large numOfShifts on prime length
        assertEquals("bcdefga", ShiftedString.shifting(prime, 8, "left"));   // 8 % 7 = 1
        assertEquals("cdefgab", ShiftedString.shifting(prime, 16, "left"));  // 16 % 7 = 2
    }

    @Test
    @DisplayName("Test power of 2 length strings")
    void testPowerOf2LengthStrings() {
        String power2 = "abcdefgh"; // length 8 (power of 2)
        assertEquals("bcdefgha", ShiftedString.shifting(power2, 1, "left"));
        assertEquals("cdefghab", ShiftedString.shifting(power2, 2, "left"));
        assertEquals("habcdefg", ShiftedString.shifting(power2, 1, "right"));
        assertEquals("ghabcdef", ShiftedString.shifting(power2, 2, "right"));

        // Test half rotation
        assertEquals("efghabcd", ShiftedString.shifting(power2, 4, "left"));
        assertEquals("efghabcd", ShiftedString.shifting(power2, 4, "right"));
    }

    @Test
    @DisplayName("Test alternating pattern strings")
    void testAlternatingPatterns() {
        String alternating = "abababab";
        assertEquals("babababa", ShiftedString.shifting(alternating, 1, "left"));
        assertEquals("abababab", ShiftedString.shifting(alternating, 2, "left"));
        assertEquals("babababa", ShiftedString.shifting(alternating, 1, "right"));

        String pattern = "abcabcabc";
        assertEquals("bcabcabca", ShiftedString.shifting(pattern, 1, "left"));
        assertEquals("cabcabcab", ShiftedString.shifting(pattern, 2, "left"));
        assertEquals("abcabcabc", ShiftedString.shifting(pattern, 3, "left"));
    }

    @Test
    @DisplayName("Test boundary values for numOfShifts")
    void testBoundaryShiftValues() {
        String test = "test";

        // Test Integer.MAX_VALUE modulo behavior
        int maxShiftLeft = Integer.MAX_VALUE % 4;
        int maxShiftRight = Integer.MAX_VALUE % 4;

        String expectedLeft = ShiftedString.shifting(test, maxShiftLeft, "left");
        String expectedRight = ShiftedString.shifting(test, maxShiftRight, "right");

        assertEquals(expectedLeft, ShiftedString.shifting(test, Integer.MAX_VALUE, "left"));
        assertEquals(expectedRight, ShiftedString.shifting(test, Integer.MAX_VALUE, "right"));
    }

    @Test
    @DisplayName("Test single character edge cases")
    void testSingleCharacterEdgeCases() {
        String single = "x";

        // Any shift on single character should return the same character
        assertEquals("x", ShiftedString.shifting(single, 1, "left"));
        assertEquals("x", ShiftedString.shifting(single, 100, "left"));
        assertEquals("x", ShiftedString.shifting(single, Integer.MAX_VALUE, "left"));
        assertEquals("x", ShiftedString.shifting(single, 1, "right"));
        assertEquals("x", ShiftedString.shifting(single, 100, "right"));
        assertEquals("x", ShiftedString.shifting(single, Integer.MAX_VALUE, "right"));
    }

    @Test
    @DisplayName("Test two character edge cases")
    void testTwoCharacterEdgeCases() {
        String two = "xy";

        // Left shift by 1 = right shift by 1 for 2-char string
        assertEquals("yx", ShiftedString.shifting(two, 1, "left"));
        assertEquals("yx", ShiftedString.shifting(two, 1, "right"));

        // Even numOfShifts return original
        assertEquals("xy", ShiftedString.shifting(two, 2, "left"));
        assertEquals("xy", ShiftedString.shifting(two, 4, "left"));
        assertEquals("xy", ShiftedString.shifting(two, 2, "right"));
        assertEquals("xy", ShiftedString.shifting(two, 4, "right"));

        // Odd swaps characters
        assertEquals("yx", ShiftedString.shifting(two, 3, "left"));
        assertEquals("yx", ShiftedString.shifting(two, 5, "left"));
        assertEquals("yx", ShiftedString.shifting(two, 3, "right"));
        assertEquals("yx", ShiftedString.shifting(two, 5, "right"));
    }

    @Test
    @DisplayName("Test Unicode grapheme cluster boundaries")
    void testUnicodeGraphemeBoundaries() {
        // Test with combining characters that should stay together
        String combining = "e\u0301a\u0300o\u0302"; // é à ô
        assertEquals("a\u0300o\u0302e\u0301", ShiftedString.shifting(combining, 1, "left"));
        assertEquals("o\u0302e\u0301a\u0300", ShiftedString.shifting(combining, 2, "left"));
        assertEquals("o\u0302e\u0301a\u0300", ShiftedString.shifting(combining, 1, "right"));

        // Test with emoji sequences
        String family = "👨‍👩‍👧‍👦👨‍👩‍👧‍👦";
        assertEquals(family, ShiftedString.shifting(family, 2, "left")); // Should be identical
        assertEquals(family, ShiftedString.shifting(family, 2, "right"));
    }

    @Test
    @DisplayName("Test whitespace and control characters")
    void testWhitespaceAndControl() {
        String whitespace = "a\tb\nc d";
        assertEquals("\tb\nc da", ShiftedString.shifting(whitespace, 1, "left"));
        assertEquals("\nc da\tb", ShiftedString.shifting(whitespace, 3, "left"));
        assertEquals("da\tb\nc ", ShiftedString.shifting(whitespace, 1, "right"));

        String control = "a\u0001b\u0002c";
        assertEquals("\u0001b\u0002ca", ShiftedString.shifting(control, 1, "left"));
        assertEquals("b\u0002ca\u0001", ShiftedString.shifting(control, 2, "left"));
    }

    @Test
    @DisplayName("Test mathematical properties of shifting")
    void testMathematicalProperties() {
        String original = "abcdef";

        // Property 1: left shift by n = right shift by (length - n)
        assertEquals(
                ShiftedString.shifting(original, 2, "left"),
                ShiftedString.shifting(original, 4, "right") // 6 - 2 = 4
        );

        // Property 2: shifting left then right by same amount returns original
        String leftShifted = ShiftedString.shifting(original, 2, "left");
        assertEquals(original, ShiftedString.shifting(leftShifted, 2, "right"));

        // Property 3: multiple small numOfShifts = one large shift
        assertEquals(
                ShiftedString.shifting(original, 3, "left"),
                ShiftedString.shifting(
                        ShiftedString.shifting(original, 1, "left"), 2, "left"
                )
        );
    }

    @Test
    @DisplayName("Test extreme modulo scenarios")
    void testExtremeModulo() {
        String test = "abc";

        // Test numOfShifts that are multiples of length
        assertEquals(test, ShiftedString.shifting(test, 3000000, "left"));   // 3000000 % 3 = 0
        assertEquals("bca", ShiftedString.shifting(test, 3000001, "left"));   // 3000001 % 3 = 1
        assertEquals("cab", ShiftedString.shifting(test, 3000002, "left"));   // 3000002 % 3 = 2

        // Test with very large numbers
        assertEquals(test, ShiftedString.shifting(test, 999999999, "left")); // 999999999 % 3 = 0, but wait...
        assertEquals("bca", ShiftedString.shifting(test, 1000000000, "left")); // 1000000000 % 3 = 1
    }

}