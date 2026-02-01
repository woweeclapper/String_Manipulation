package com.string_manipulator.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReverseStringTest {

    @Nested
    @DisplayName("Basic String Operations")
    class BasicStringOperations {

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
            assertEquals("😀", ReverseString.reverse("😀"));
        }

        @Test
        @DisplayName("Test two characters")
        void testTwoCharacters() {
            assertEquals("ba", ReverseString.reverse("ab"));
            assertEquals("ZY", ReverseString.reverse("YZ"));
            assertEquals("😀😃", ReverseString.reverse("😃😀"));
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
            assertThrows(NullPointerException.class, () -> ReverseString.reverse(null));
        }

        @Test
        @DisplayName("Test special characters")
        void testSpecialCharacters() {
            assertEquals("!@#$", ReverseString.reverse("$#@!"));
            assertEquals(",;.:", ReverseString.reverse(":.;,"));
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

    @Nested
    @DisplayName("Grapheme Cluster Tests")
    class GraphemeClusterTests {

        @Test
        @DisplayName("Test basic emoji reversal")
        void testBasicEmojiReversal() {
            assertEquals("😀cba", ReverseString.reverse("abc😀"));
            assertEquals("😀😃😄😁", ReverseString.reverse("😁😄😃😀"));
            assertEquals("🙂🙃😊😎", ReverseString.reverse("😎😊🙃🙂"));
        }

        @Test
        @DisplayName("Test Zero Width Joiner (ZWJ) sequences")
        void testZWJSequences() {
            // Family emoji: 👨‍👩‍👧‍👦 (man + ZWJ + woman + ZWJ + girl + ZWJ + boy)
            String family = "👨‍👩‍👧‍👦";
            assertEquals(family, ReverseString.reverse(family)); // Should be identical

            // Flag emojis: 🇺🇸 (US flag)
            String usFlag = "🇺🇸";
            assertEquals(usFlag, ReverseString.reverse(usFlag)); // Should be identical

            // Mixed with other characters
            assertEquals("👨‍👩‍👧‍👦cba", ReverseString.reverse("abc👨‍👩‍👧‍👦"));
        }

        @Test
        @DisplayName("Test combining characters")
        void testCombiningCharacters() {
            // e + combining acute accent = é
            String eAcute = "e\u0301";
            assertEquals(eAcute + "cba", ReverseString.reverse("abc" + eAcute));

            // Multiple combining marks
            String aWithMarks = "a\u0301\u0308"; // a + acute + umlaut
            assertEquals(aWithMarks + "cba", ReverseString.reverse("abc" + aWithMarks));

            // Mixed combining characters
            String mixed = "a\u0308o\u0308u\u0308"; // äöü
            assertEquals("üöä", ReverseString.reverse(mixed)); // Should be identical
        }

        @Test
        @DisplayName("Test skin tone modifiers")
        void testSkinToneModifiers() {
            // 👋🏻 (waving hand + light skin tone)
            String waveLight = "👋🏻";
            assertEquals(waveLight, ReverseString.reverse(waveLight)); // Should be identical

            // 👋🏿 (waving hand + dark skin tone)
            String waveDark = "👋🏿";
            assertEquals(waveDark, ReverseString.reverse(waveDark)); // Should be identical

            // Mixed with other characters
            assertEquals("👋🏿cba", ReverseString.reverse("abc👋🏿"));
        }

        @Test
        @DisplayName("Test variation selectors")
        void testVariationSelectors() {
            // Heart with variation selector
            String heartVar = "❤️"; // heart + variation selector-16
            assertEquals(heartVar, ReverseString.reverse(heartVar)); // Should be identical

            // Mixed with other characters
            assertEquals("❤️cba", ReverseString.reverse("abc❤️"));
        }

        @Test
        @DisplayName("Test complex emoji sequences")
        void testComplexEmojiSequences() {
            // 🧑‍💻 (technologist)
            String technologist = "🧑‍💻";
            assertEquals(technologist, ReverseString.reverse(technologist));

            // 👩‍⚕️ (woman health worker)
            String doctor = "👩‍⚕️";
            assertEquals(doctor, ReverseString.reverse(doctor));

            // Multiple complex emojis
            assertEquals("🧑‍💻👩‍⚕️", ReverseString.reverse("👩‍⚕️🧑‍💻"));
        }
    }

    @Nested
    @DisplayName("Complex Script Tests")
    class ComplexScriptTests {

        @Test
        @DisplayName("Test Chinese characters")
        void testChineseCharacters() {
            assertEquals("你好世界", ReverseString.reverse("界世好你"));
            assertEquals("测试", ReverseString.reverse("试测"));
            assertEquals("编程", ReverseString.reverse("程编"));
        }

        @Test
        @DisplayName("Test Arabic text")
        void testArabicText() {
            assertEquals("مرحبا", ReverseString.reverse("ابحرم"));
            assertEquals("العرةي", ReverseString.reverse("يةرعلا"));
        }

        @Test
        @DisplayName("Test Hebrew text")
        void testHebrewText() {
            assertEquals("שלום", ReverseString.reverse("םולש"));
            assertEquals("תודה", ReverseString.reverse("הדות"));
        }

        @Test
        @DisplayName("Test Devanagari script")
        void testDevanagariScript() {
            assertEquals("नमसत्े", ReverseString.reverse("ेत्समन"));
            assertEquals("धयन्वदा", ReverseString.reverse("दावन्यध"));
        }

        @Test
        @DisplayName("Test Japanese mixed script")
        void testJapaneseMixedScript() {
            assertEquals("こんにちは", ReverseString.reverse("はちにんこ"));
            assertEquals("漢字ひらがな", ReverseString.reverse("ながらひ字漢"));
        }

        @Test
        @DisplayName("Test Korean Hangul")
        void testKoreanHangul() {
            assertEquals("안녕하세요", ReverseString.reverse("요세하녕안"));
            assertEquals("감사다니", ReverseString.reverse("니다사감"));
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Test zero-width characters")
        void testZeroWidthCharacters() {
            // Zero-width space
            assertEquals("cba\u200B", ReverseString.reverse("\u200Babc"));

            // Zero-width non-joiner
            assertEquals("cba\u200C", ReverseString.reverse("\u200Cabc"));

            // Zero-width joiner
            assertEquals("cba\u200D", ReverseString.reverse("\u200Dabc"));
        }

        @Test
        @DisplayName("Test control characters")
        void testControlCharacters() {
            assertEquals("cba\r", ReverseString.reverse("\rabc"));
            assertEquals("cba\n", ReverseString.reverse("\nabc"));
            assertEquals("cba\t", ReverseString.reverse("\tabc"));
        }

        @Test
        @DisplayName("Test surrogate pairs")
        void testSurrogatePairs() {
            // Musical symbols (outside BMP)
            assertEquals("𝄞cba", ReverseString.reverse("abc𝄞"));

            // Mathematical symbols
            assertEquals("∀cba", ReverseString.reverse("abc∀"));
        }

        @Test
        @DisplayName("Test mixed grapheme clusters")
        void testMixedGraphemeClusters() {
            String complex = "a\u0301😀👨‍👩‍👧‍👦你";
            String reversed = "你👨‍👩‍👧‍👦😀a\u0301";
            assertEquals(reversed, ReverseString.reverse(complex));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "", "a", "😀", "👨‍👩‍👧‍👦", "e\u0301", "a\u0301\u0308"
        })
        @DisplayName("Test single grapheme cluster reversal")
        void testSingleGraphemeCluster(String input) {
            assertEquals(input, ReverseString.reverse(input));
        }
    }

    @Nested
    @DisplayName("Performance and Stress Tests")
    class PerformanceTests {

        @Test
        @DisplayName("Test long string")
        void testLongString() {
            String original = "a".repeat(1000);
            StringBuilder reversed = new StringBuilder(original).reverse();
            assertEquals(reversed.toString(), ReverseString.reverse(original));
        }

        @Test
        @DisplayName("Test long string with emojis")
        void testLongStringWithEmojis() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100; i++) {
                sb.append("abc");
                sb.append("😀");
            }
            String original = sb.toString();

            // Manually construct expected result
            StringBuilder expected = new StringBuilder();
            for (int i = 99; i >= 0; i--) {
                expected.append("😀");
                expected.append("cba");
            }

            assertEquals(expected.toString(), ReverseString.reverse(original));
        }

        @Test
        @DisplayName("Test performance with complex grapheme clusters")
        void testPerformanceWithComplexGraphemeClusters() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 50; i++) {
                sb.append("abc");
                sb.append("👨‍👩‍👧‍👦"); // Complex family emoji
                sb.append("e\u0301"); // Combining character
            }

            String original = sb.toString();
            assertDoesNotThrow(() -> ReverseString.reverse(original));

            String reversed = ReverseString.reverse(original);
            assertEquals(original.length(), reversed.length());
        }

        @Test
        @DisplayName("Test memory efficiency")
        void testMemoryEfficiency() {
            // Very long string to test memory usage
            String original = "a".repeat(10000) + "😀".repeat(1000);
            assertDoesNotThrow(() -> ReverseString.reverse(original));
        }
    }

    @Nested
    @DisplayName("Regression Tests")
    class RegressionTests {

        @Test
        @DisplayName("Test accented characters work correctly")
        void testAccentedCharacters() {
            assertEquals("café", ReverseString.reverse("éfac"));
            assertEquals("naïve", ReverseString.reverse("evïan"));
            assertEquals("señor", ReverseString.reverse("roñes"));
        }

        @Test
        @DisplayName("Test encoding consistency")
        void testEncodingConsistency() {
            String test = "Hello 世界 🌍 👨‍👩‍👧‍👦";
            String reversed = ReverseString.reverse(test);

            // Verify the reversed string maintains proper encoding
            assertDoesNotThrow(() -> reversed.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            // Verify round-trip encoding doesn't change the string
            byte[] bytes = reversed.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            String roundTrip = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
            assertEquals(reversed, roundTrip);
        }

        @Test
        @DisplayName("Test grapheme cluster boundaries")
        void testGraphemeClusterBoundaries() {
            // Test that grapheme clusters are not split
            String complex = "👩‍⚕️"; // Woman health worker
            assertEquals(complex, ReverseString.reverse(complex));

            // Test multiple adjacent complex clusters
            String multiple = "👩‍⚕️🧑‍💻";
            assertEquals("🧑‍💻👩‍⚕️", ReverseString.reverse(multiple));
        }
    }
    @Nested
    @DisplayName("Extreme Edge Cases")
    class ExtremeEdgeCases {

        @Test
        @DisplayName("Test string with only control characters")
        void testOnlyControlCharacters() {
            String controlOnly = "\r\n\t\b\f\u0000\u001F";
            String reversed = "\u001F\u0000\f\b\t\n\r";
            assertEquals(reversed, ReverseString.reverse(controlOnly));
        }

        @Test
        @DisplayName("Test string with only whitespace variations")
        void testOnlyWhitespaceVariations() {
            String whitespaceOnly = " \t\n\r\u00A0\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u202F\u205F\u3000";
            String reversed = new StringBuilder(whitespaceOnly).reverse().toString();
            assertEquals(reversed, ReverseString.reverse(whitespaceOnly));
        }

        @Test
        @DisplayName("Test string with maximum Unicode characters")
        void testMaximumUnicodeCharacters() {
            // Test with characters from different Unicode planes
            String extremeUnicode = "\u0000\u007F\u07FF\uFFFF\u10FFFF";
            String reversed = "\u10FFFF\uFFFF\u07FF\u007F\u0000";
            assertEquals(reversed, ReverseString.reverse(extremeUnicode));
        }

        @Test
        @DisplayName("Test string with invalid Unicode sequences")
        void testInvalidUnicodeSequences() {
            // These should still reverse without throwing exceptions
            String invalidUnicode = "abc\uD800def"; // Unpaired high surrogate
            String reversed = "fed\uD800cba";
            assertEquals(reversed, ReverseString.reverse(invalidUnicode));

            String invalidUnicode2 = "abc\uDC00def"; // Unpaired low surrogate
            String reversed2 = "fed\uDC00cba";
            assertEquals(reversed2, ReverseString.reverse(invalidUnicode2));
        }
    }
    @Nested
    @DisplayName("Mathematical and Technical Symbols")
    class MathematicalTechnicalSymbols {

        @Test
        @DisplayName("Test mathematical operators")
        void testMathematicalOperators() {
            String math = "∑∏∫∆∇∂∞±×÷≠≤≥≈≝∈∉⊂⊃∪∩";
            String reversed = "∩∪⊃⊂∉∈≝≈≥≤≠÷×±∞∂∇∆∫∏∑";
            assertEquals(reversed, ReverseString.reverse(math));
        }

        @Test
        @DisplayName("Test currency symbols")
        void testCurrencySymbols() {
            String currency = "$€£¥₹₽₩₪₫₡₨₦₱₲₴₸₼₾";
            String reversed = "₾₼₸₴₲₱₦₨₡₫₪₩₽₹¥£€$";
            assertEquals(reversed, ReverseString.reverse(currency));
        }

        @Test
        @DisplayName("Test technical symbols")
        void testTechnicalSymbols() {
            String technical = "⚡⚠️⚽♠️♥️♦️♣️🎵🎶🔔🔕";
            String reversed = "🔕🔔🎶🎵♣️♦️♥️♠️⚽⚠️⚡";
            assertEquals(reversed, ReverseString.reverse(technical));
        }

        @Test
        @DisplayName("Test bracket and punctuation combinations")
        void testBracketsAndPunctuation() {
            String brackets = "({[<>]})«»‹›''`";
            String reversed = "`''‹›»»({[<>]})";
            assertEquals(reversed, ReverseString.reverse(brackets));
        }
    }
    @Nested
    @DisplayName("Complex Emoji Combinations")
    class ComplexEmojiCombinations {

        @Test
        @DisplayName("Test emoji with multiple skin tones")
        void testEmojiWithMultipleSkinTones() {
            String holdingHands = "🧑🏽‍🤝‍🧑🏿"; // People holding hands with different skin tones
            assertEquals(holdingHands, ReverseString.reverse(holdingHands));

            String mixedSkinTones = "👋🏻👋🏼👋🏽👋🏾👋🏿";
            String reversed = "👋🏿👋🏾👋🏽👋🏼👋🏻";
            assertEquals(reversed, ReverseString.reverse(mixedSkinTones));
        }

        @Test
        @DisplayName("Test professional emojis with variations")
        void testProfessionalEmojisWithVariations() {
            String professionals = "👩‍⚕️👨‍⚕️👩‍🎓👨‍🎓👩‍🏫👨‍🏫👩‍💻👨‍💻👩‍🏭👨‍🏭";
            String reversed = "👨‍🏭👩‍🏭👨‍💻👩‍💻👨‍🏫👩‍🏫👨‍🎓👩‍🎓👨‍⚕️👩‍⚕️";
            assertEquals(reversed, ReverseString.reverse(professionals));
        }

        @Test
        @DisplayName("Test flag emojis")
        void testFlagEmojis() {
            String flags = "🇺🇸🇨🇦🇲🇽🇯🇵🇰🇷🇩🇪🇫🇷🇬🇧🇮🇹🇪🇸🇦🇺";
            String reversed = "🇦🇺🇪🇸🇮🇹🇬🇧🇫🇷🇩🇪🇰🇷🇯🇵🇲🇽🇨🇦🇺🇸";
            assertEquals(reversed, ReverseString.reverse(flags));
        }

        @Test
        @DisplayName("Test mixed emoji sequences")
        void testMixedEmojiSequences() {
            String mixed = "❤️🧡💛💚💙💜🖤🤍🤎💔❣️💕💞💓💗💖💘💝💟☮️✝️☪️🕉️☸️✡️🔯🕎☯️☦️🛐";
            String reversed = "🛐☦️☯️🕎🔯✡️☸️🕉️☪️✝️☮️💟💝💘💖💗💓💞💕❣️💔🤎🤍🖤💜💙💚💛🧡❤️";
            assertEquals(reversed, ReverseString.reverse(mixed));
        }
    }
    @Nested
    @DisplayName("Incomprehensible and Chaotic Strings")
    class IncomprehensibleStrings {

        @Test
        @DisplayName("Test completely random Unicode characters")
        void testRandomUnicodeCharacters() {
            String random = "⚡🌟💫✨🌈🔥💧🌊🎵🎶🎨🎭🎪🎯🎲🎸🎹🎺🎻🥁🎤";
            String reversed = "🎤🥁🎻🎺🎹🎸🎲🎯🎪🎭🎨🎶🎵🌊💧🔥🌈✨💫🌟⚡";
            assertEquals(reversed, ReverseString.reverse(random));
        }

        @Test
        @DisplayName("Test mixed scripts and symbols")
        void testMixedScriptsAndSymbols() {
            String chaotic = "Hello🌍世界👨‍👩‍👧‍👦مرحبا😀नमस्ते🧠💡⚡🔮✨🎭🎪🎨🎵";
            String reversed = "🎵🎨🎪🎭✨🔮⚡💡🧠नमस्ते😀مرحبا👨‍👩‍👧‍👦世界🌍Hello";
            assertEquals(reversed, ReverseString.reverse(chaotic));
        }

        @Test
        @DisplayName("Test string with every type of character")
        void testEveryCharacterType() {
            StringBuilder everything = new StringBuilder();
            // Add ASCII
            everything.append("Hello World! 123");
            // Add emojis
            everything.append("😀😃😄😁😆😅😂🤣☺️😊");
            // Add complex emojis
            everything.append("👨‍👩‍👧‍👦👩‍⚕️🧑‍💻");
            // Add combining characters
            everything.append("e\u0301a\u0308o\u0308");
            // Add various scripts
            everything.append("你好世界مرحباनमस्ते");
            // Add mathematical symbols
            everything.append("∑∏∫∆∇∂∞");
            // Add control characters
            everything.append("\r\n\t");

            String original = everything.toString();
            String reversed = ReverseString.reverse(original);

            // Should not throw and should maintain same length
            assertEquals(original.length(), reversed.length());
            // Should be reversible (double reverse returns original)
            assertEquals(original, ReverseString.reverse(reversed));
        }

        @Test
        @DisplayName("Test extremely long chaotic string")
        void testExtremelyLongChaoticString() {
            StringBuilder chaos = new StringBuilder();
            String[] elements = {
                    "😀", "👨‍👩‍👧‍👦", "e\u0301", "你", "∑", "\t", "🌟", "مرح", "💡", "🎭"
            };

            // Create a very long chaotic string
            for (int i = 0; i < 1000; i++) {
                chaos.append(elements[i % elements.length]);
            }

            String original = chaos.toString();
            assertDoesNotThrow(() -> ReverseString.reverse(original));

            String reversed = ReverseString.reverse(original);
            assertEquals(original.length(), reversed.length());
            assertEquals(original, ReverseString.reverse(reversed));
        }
    }
    @Nested
    @DisplayName("Numbers and Data Patterns")
    class NumbersAndDataPatterns {

        @Test
        @DisplayName("Test various number formats")
        void testVariousNumberFormats() {
            String numbers = "1234567890-+.,/()[]{}";
            String reversed = "{}[])(/,.,+-+0987654321";
            assertEquals(reversed, ReverseString.reverse(numbers));
        }

        @Test
        @DisplayName("Test scientific notation")
        void testScientificNotation() {
            String scientific = "1.23E+10 4.56E-8 7.89e15";
            String reversed = "51e9.87 8-E45.6 01+E3.21";
            assertEquals(reversed, ReverseString.reverse(scientific));
        }

        @Test
        @DisplayName("Test binary and hexadecimal")
        void testBinaryAndHexadecimal() {
            String binary = "1010101010101010";
            String reversed = "0101010101010101";
            assertEquals(reversed, ReverseString.reverse(binary));

            String hex = "0xDEADBEEF 0xCAFEBABE";
            String reversedHex = "EBABEFCAX 0xFEEBDAED0";
            assertEquals(reversedHex, ReverseString.reverse(hex));
        }

        @Test
        @DisplayName("Test data patterns")
        void testDataPatterns() {
            String data = "ID:12345,NAME:John,AGE:25";
            String reversed = "52:EGA,nhoJ:EMAN,54321:DI";
            assertEquals(reversed, ReverseString.reverse(data));
        }
    }
    @Nested
    @DisplayName("Bidirectional Text Tests")
    class BidirectionalTextTests {

        @Test
        @DisplayName("Test mixed RTL and LTR text")
        void testMixedRTLLTRText() {
            String mixed = "Hello مرحبا World";
            String reversed = "dlroW مرحبا olleH";
            assertEquals(reversed, ReverseString.reverse(mixed));
        }

        @Test
        @DisplayName("Test complex bidirectional text")
        void testComplexBidirectionalText() {
            String complex = "English العربية עברית 中文 日本語 한국어";
            String reversed = "어리한국어 일본어 中文 עברית العربية hsilgnE";
            assertEquals(reversed, ReverseString.reverse(complex));
        }
    }
    @Nested
    @DisplayName("Reversibility Verification")
    class ReversibilityVerification {

        @ParameterizedTest
        @ValueSource(strings = {
                "", "a", "😀", "👨‍👩‍👧‍👦", "e\u0301", "Hello", "مرحبا", "你好",
                "Hello🌍World", "123😀ABC", "!@#$%^&*()", "\r\n\t",
                "∑∏∫∆∇∂∞", "🇺🇸🇨🇦🇲🇽", "a\u0308o\u0308u\u0308"
        })
        @DisplayName("Double reverse should return original")
        void testDoubleReverseReturnsOriginal(String input) {
            String reversed = ReverseString.reverse(input);
            String doubleReversed = ReverseString.reverse(reversed);
            assertEquals(input, doubleReversed, "Double reverse should return original for: " + input);
        }

        @Test
        @DisplayName("Test reversibility with extremely long string")
        void testReversibilityWithExtremelyLongString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10000; i++) {
                sb.append((char) (i % 1000 + 32)); // Printable ASCII range
            }
            String original = sb.toString();
            String doubleReversed = ReverseString.reverse(ReverseString.reverse(original));
            assertEquals(original, doubleReversed);
        }
    }
}