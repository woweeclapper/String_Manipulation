package com.string_manipulator.dto.string;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ReverseRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldPassValidationWhenTextIsValid() {
        // Given
        String validText = "Hello World";
        ReverseRequest request = new ReverseRequest(validText);

        // When
        Set<ConstraintViolation<ReverseRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWhenTextIsNull() {
        // Given
        ReverseRequest request = new ReverseRequest(null);

        // When
        Set<ConstraintViolation<ReverseRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Text cannot be blank");
    }

    @Test
    void shouldFailValidationWhenTextIsEmpty() {
        // Given
        ReverseRequest request = new ReverseRequest("");

        // When
        Set<ConstraintViolation<ReverseRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Text cannot be blank");
    }

    @Test
    void shouldFailValidationWhenTextIsBlank() {
        // Given
        ReverseRequest request = new ReverseRequest("   ");

        // When
        Set<ConstraintViolation<ReverseRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Text cannot be blank");
    }

    @Test
    void shouldFailValidationWhenTextExceedsMaxLength() {
        // Given
        String longText = "a".repeat(10001);
        ReverseRequest request = new ReverseRequest(longText);

        // When
        Set<ConstraintViolation<ReverseRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Text cannot exceed 10000 characters");
    }

    @Test
    void shouldPassValidationWhenTextIsExactlyMaxLength() {
        // Given
        String maxLengthText = "a".repeat(10000);
        ReverseRequest request = new ReverseRequest(maxLengthText);

        // When
        Set<ConstraintViolation<ReverseRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWhenTextContainsOnlyWhitespaceAndValidCharacters() {
        // Given
        String textWithSpaces = "Hello World with spaces";
        ReverseRequest request = new ReverseRequest(textWithSpaces);

        // When
        Set<ConstraintViolation<ReverseRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }
}