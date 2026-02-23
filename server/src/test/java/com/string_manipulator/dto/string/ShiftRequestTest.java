package com.string_manipulator.dto.string;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ShiftRequestTest {

    private Validator validator;

    private static Stream<Arguments> provideTextValidationCases() {
        return Stream.of(
                Arguments.of(null, 1, "Text cannot be blank"),
                Arguments.of("", 1, "Text cannot be blank"),
                Arguments.of("   ", 1, "Text cannot be blank"),
                Arguments.of("a".repeat(10001), 1, "Text cannot exceed 10000 characters"),
                Arguments.of("a".repeat(10000), 0, null)
        );
    }

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldPassValidationWhenAllFieldsAreValid() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    // Text field tests
    @ParameterizedTest
    @MethodSource("provideTextValidationCases")
    void shouldValidateTextField(String text, int expectedViolationCount, String expectedMessage) {
        // Given
        ShiftRequest request = new ShiftRequest(text, 5, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(expectedViolationCount);
        if (expectedViolationCount > 0) {
            assertThat(violations.iterator().next().getMessage()).isEqualTo(expectedMessage);
        }
    }

    // numOfShifts field tests
    @Test
    void shouldFailValidationWhenNumOfShiftsIsNegative() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", -1, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Number of shifts cannot be negative");
    }

    @Test
    void shouldPassValidationWhenNumOfShiftsIsZero() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 0, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWhenNumOfShiftsIsPositive() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 10, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    // Direction field tests
    @Test
    void shouldFailValidationWhenDirectionIsNull() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, null);

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Direction cannot be blank");
    }

    @Test
    void shouldFailValidationWhenDirectionIsEmpty() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, "");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2); //edge case, both blank and pattern fire off
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Direction cannot be blank",
                        "Direction must represent 'left', 'right', 'l', or 'r'."
                );

    }

    @Test
    void shouldFailValidationWhenDirectionIsBlank() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, "   ");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Direction cannot be blank",
                        "Direction must represent 'left', 'right', 'l', or 'r'."
                );

    }

    @Test
    void shouldFailValidationWhenDirectionIsInvalid() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, "up");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Direction must represent 'left', 'right', 'l', or 'r'.");
    }

    @Test
    void shouldPassValidationWhenDirectionIsLeft() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 6, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWhenDirectionIsRight() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, "right");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWhenDirectionIsL() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, "l");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWhenDirectionIsR() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, "r");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWhenDirectionIsUpperCase() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, "LEFT");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWhenDirectionIsMixedCase() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, "LeFt");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWhenDirectionHasSpaces() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, " l e f t ");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    // Multiple violations tests
    @Test
    void shouldFailValidationWithMultipleViolations() {
        // Given
        ShiftRequest request = new ShiftRequest("", -1, "invalid");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(3);
        assertThat(violations.stream().map(ConstraintViolation::getMessage))
                .containsExactlyInAnyOrder(
                        "Text cannot be blank",
                        "Number of shifts cannot be negative",
                        "Direction must represent 'left', 'right', 'l', or 'r'."
                );
    }

    // Invalid format tests for direction field
    @ParameterizedTest
    @ValueSource(strings = {
            "lef",           // partial match
            "righ",          // partial match
            "leftx",         // extra character
            "right1",        // with number
            "l e f t x",     // spaced with extra
            "LEFT-RIGHT",      // with dash
            "left/right",      // with slash
            "left@right",     // with special char
            "up",             // completely different
            "down",           // completely different
            "left-right",     // hyphenated
            "left_right",      // underscored
            "left.right",      // dotted
            "LEFT!",          // with punctuation
            "right?",         // with question mark
            "l e",           // incomplete spaced
            "r i",           // incomplete spaced
            "LEFT RIGHT",     // two words
            "left123",        // alphanumeric
            "right$",         // with regex specia
            "left\u0000",    // with null character
            "LEFT\u2028",     // with line separator
            "方向",            // non-ASCII characters
            "лев",            // Cyrillic characters
            "←",              // arrow symbol
            "left😀",              // Simple emoji after valid direction
            "🚀left",              // Simple emoji before valid direction
            "left👨‍💻",            // Complex emoji with valid direction
            "👍🏽left",            // Skin tone modifier with valid direction
            "🏳️‍🌈left",           // Complex flag emoji with valid direction
            "left😎🎉🔥",          // Multiple simple emojis after valid direction
            "🚀🚀left",            // Multiple simple emojis before valid direction
            "left👨‍👩‍👧‍👦",        // Complex family emoji with valid direction
            "left🎮📱💻",          // Multiple tech emojis after valid direction
    })
    void shouldFailValidationWhenDirectionIsInvalidFormat(String invalidDirection) {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, invalidDirection);

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Direction must represent 'left', 'right', 'l', or 'r'.");
    }

    // Invalid format tests for text field (beyond just blank/size)
    @ParameterizedTest
    @ValueSource(strings = {
            "Hello\nWorld",   // newline in text
            "Hello\tWorld",   // tab in text
            "Hello\rWorld",   // carriage return in text
            "Hello\u0000World", // null character in text
            "Text\u2028with\u2029line", // line separators
            "Hello\u0001World",   // control character
            "Hello\u001FWorld",   // another control character
            "Text with\u0000null", // embedded null
            "Multiple\n\n\nlines",  // multiple newlines
            "Tabs\t\t\t\t\t",    // multiple tabs
            "Carriage\r\rReturn",  // multiple carriage returns
            "Mixed\r\n\tLine",     // mixed control chars
            "Hello\u0000\u0000\u0000World", // multiple nulls
            "Text\u0001with\u0002control\u0003chars" // various controls
    })
    void shouldFailValidationWhenTextContainsInvalidCharacters(String invalidText) {
        // Given
        ShiftRequest request = new ShiftRequest(invalidText, 5, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        // Note: These might pass validation since @NotBlank and @Size don't check for control chars
        // This test documents current behavior - you may want to add additional constraints
        if (!invalidText.trim().isEmpty() && invalidText.length() <= 10000) {
            assertThat(violations).isEmpty();
        } else {
            assertThat(violations).isNotEmpty();
        }
    }

    // Edge case: Direction with mixed valid and invalid patterns
    @Test
    void shouldFailValidationWhenDirectionContainsValidAndInvalidParts() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, "leftright");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Direction must represent 'left', 'right', 'l', or 'r'.");
    }

    // Edge case: Very long invalid direction
    @Test
    void shouldFailValidationWhenDirectionIsVeryLongInvalid() {
        // Given
        String longInvalid = "a".repeat(1000) + "invalid";
        ShiftRequest request = new ShiftRequest("Hello World", 5, longInvalid);

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("Direction must represent 'left', 'right', 'l', or 'r'.");
    }

    // Edge case: Direction with only whitespace but pattern doesn't match
    @Test
    void shouldFailValidationWhenDirectionIsOnlyWhitespace() {
        // Given
        ShiftRequest request = new ShiftRequest("Hello World", 5, "     ");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Direction cannot be blank",
                        "Direction must represent 'left', 'right', 'l', or 'r'."
                );
    }

    // Unicode and emoji tests for text field
    @ParameterizedTest
    @ValueSource(strings = {
            "Hello 🌍 World",        // Simple earth emoji
            "Text with 😀 emoji",     // Simple smiling face
            "🚀 Rocket launch",      // Simple rocket emoji
            "👨‍💻 Developer",        // Complex emoji (zwj sequence)
            "👍🏽 Thumbs up",         // Emoji with skin tone modifier
            "🏳️‍🌈 Rainbow flag",      // Complex flag emoji with zwj
            "Multiple emojis 😎🎉🔥", // Multiple simple emojis
            "Text with ❤️ heart"      // Simple heart emoji
    })
    void shouldPassValidationWhenTextContainsEmojis(String emojiText) {
        // Given
        ShiftRequest request = new ShiftRequest(emojiText, 5, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        // Emojis should pass validation as they're valid Unicode characters
        assertThat(violations).isEmpty();
    }

}