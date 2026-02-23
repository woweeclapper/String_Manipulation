package com.string_manipulator.dto.string;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ShiftRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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
    @Test
    void shouldFailValidationWhenTextIsNull() {
        // Given
        ShiftRequest request = new ShiftRequest(null, 5, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Text cannot be blank");
    }

    @Test
    void shouldFailValidationWhenTextIsEmpty() {
        // Given
        ShiftRequest request = new ShiftRequest("", 5, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Text cannot be blank");
    }

    @Test
    void shouldFailValidationWhenTextIsBlank() {
        // Given
        ShiftRequest request = new ShiftRequest("   ", 5, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Text cannot be blank");
    }

    @Test
    void shouldFailValidationWhenTextExceedsMaxLength() {
        // Given
        String longText = "a".repeat(10001);
        ShiftRequest request = new ShiftRequest(longText, 5, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Text cannot exceed 10000 characters");
    }

    @Test
    void shouldPassValidationWhenTextIsExactlyMaxLength() {
        // Given
        String maxLengthText = "a".repeat(10000);
        ShiftRequest request = new ShiftRequest(maxLengthText, 5, "left");

        // When
        Set<ConstraintViolation<ShiftRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
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
}