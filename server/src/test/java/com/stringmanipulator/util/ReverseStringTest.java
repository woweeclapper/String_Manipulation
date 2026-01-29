package com.stringmanipulator.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class ReverseStringTest {

    @Test
    @DisplayName("Test empty string")
    void testEmptyString() {
        assertEquals("", ReverseString.reverse(""));
    }

    @Test
    @DisplayName("Test single character")
    void testSingleCharacter() {
        assertEquals("a", ReverseString.reverse("a"));
        assertEquals("Z", ReverseString.reverse("Z"));
    }

    @Test
    @DisplayName("Test two characters")
    void testTwoCharacters() {
        assertEquals("ba", ReverseString.reverse("ab"));
        assertEquals("ZY", ReverseString.reverse("YZ"));
    }

    @Test
    @DisplayName("Test multiple characters")
    void testMultipleCharacters() {
        assertEquals("dcba", ReverseString.reverse("abcd"));
        assertEquals("olleH", ReverseString.reverse("Hello"));
    }

    @Test
    @DisplayName("Test palindrome")
    void testPalindrome() {
        assertEquals("racecar", ReverseString.reverse("racecar"));
        assertEquals("madam", ReverseString.reverse("madam"));
    }

    @Test
    @DisplayName("Test null input - should throw exception")
    void testNullInput() {
        assertThrows(NullPointerException.class, () -> {
            ReverseString.reverse(null);
        });
    }

    @Test
    @DisplayName("Test special characters")
    void testSpecialCharacters() {
        assertEquals("!@#$", ReverseString.reverse("$#@!"));
        assertEquals(",;.:", ReverseString.reverse(":.;,"));
    }

    @Test
    @DisplayName("Test unicode characters")
    void testUnicodeCharacters() {
        assertEquals("😊cba", ReverseString.reverse("abc😊"));
        assertEquals("ñáéíóú", ReverseString.reverse("úóíéáñ"));
    }

    @Test
    @DisplayName("Test whitespace")
    void testWhitespace() {
        assertEquals(" d c b a", ReverseString.reverse("a b c d "));
        assertEquals("\t\n", ReverseString.reverse("\n\t"));
    }

    @Test
    @DisplayName("Test mixed case")
    void testMixedCase() {
        assertEquals("OlLeH", ReverseString.reverse("HeLlO"));
        assertEquals("tseT", ReverseString.reverse("Test"));
    }

    @Test
    @DisplayName("Test alphanumeric")
    void testAlphanumeric() {
        assertEquals("321cba", ReverseString.reverse("abc123"));
        assertEquals("!@#123ABC", ReverseString.reverse("CBA321#@!"));
    }

    @Test
    @DisplayName("Test long string")
    void testLongString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("a");
        }
        String original = sb.toString();
        StringBuilder reversed = new StringBuilder(original).reverse();
        assertEquals(reversed.toString(), ReverseString.reverse(original));
    }

    @Test
    @DisplayName("Test repeated characters")
    void testRepeatedCharacters() {
        assertEquals("aaaa", ReverseString.reverse("aaaa"));
        assertEquals("bbb", ReverseString.reverse("bbb"));
    }

    @Test
    @DisplayName("Test sentence")
    void testSentence() {
        assertEquals("!dlroW ,olleH", ReverseString.reverse("Hello, World!"));
        assertEquals("esrever ni si gnirts sihT", ReverseString.reverse("This string is in reverse"));
    }
}