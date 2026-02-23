package com.string_manipulator.dto.array;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SeparationRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldPassValidationWhenAllFieldsAreValid() {
        // Given
        List<Number> validNumbers = List.of(1, -2, 3, -4, 5);
        SeparationRequest request = new SeparationRequest(validNumbers, "parity");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    // NumberList field tests
    @Test
    void shouldFailValidationWhenNumberListIsNull() {
        // Given
        SeparationRequest request = new SeparationRequest(null, "parity");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2); // Both @NotNull and @NotEmpty trigger
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Number list cannot be null",
                        "Number list must not be empty"
                );
    }

    @Test
    void shouldFailValidationWhenNumberListIsEmpty() {
        // Given
        List<Number> emptyList = List.of();
        SeparationRequest request = new SeparationRequest(emptyList, "parity");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2); // Both @NotEmpty and @Size(min = 1) trigger
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Number list must not be empty",
                        "Number list must have at least one element"
                );
    }

    @Test
    void shouldPassValidationWhenNumberListHasExactlyOneElement() {
        // Given
        List<Number> singleElement = List.of(42);
        SeparationRequest request = new SeparationRequest(singleElement, "parity");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWhenNumberListHasExactlyMaxElements() {
        // Given
        List<Number> maxElements = List.copyOf(new ArrayList<>() {{
            for (int i = 0; i < 1000; i++) {
                add(i);
            }
        }});
        SeparationRequest request = new SeparationRequest(maxElements, "parity");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWhenNumberListExceedsMaxElements() {
        // Given
        List<Number> tooManyElements = List.copyOf(new ArrayList<>() {{
            for (int i = 0; i < 1001; i++) {
                add(i);
            }
        }});
        SeparationRequest request = new SeparationRequest(tooManyElements, "parity");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Number list cannot exceed 1000 elements");
    }

    @Test
    void shouldFailValidationWhenNumberListContainsNullElement() {
        // Given
        List<Number> listWithNull = new ArrayList<>();
        listWithNull.add(1);
        listWithNull.add(2);
        listWithNull.add(null);
        listWithNull.add(4);
        SeparationRequest request = new SeparationRequest(listWithNull, "parity");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Number cannot be null");
    }

    @Test
    void shouldFailValidationWhenNumberListContainsMultipleNullElements() {
        // Given
        List<Number> listWithMultipleNulls = new ArrayList<>();
        listWithMultipleNulls.add(1);
        listWithMultipleNulls.add(null);
        listWithMultipleNulls.add(3);
        listWithMultipleNulls.add(null);
        listWithMultipleNulls.add(5);
        SeparationRequest request = new SeparationRequest(listWithMultipleNulls, "parity");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Number cannot be null",
                        "Number cannot be null"
                );
    }

    // SeparationType field tests
    @Test
    void shouldFailValidationWhenSeparationTypeIsNull() {
        // Given
        List<Number> validNumbers = List.of(1, -2, 3);
        SeparationRequest request = new SeparationRequest(validNumbers, null);

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Separation type cannot be blank");
    }

    @Test
    void shouldFailValidationWhenSeparationTypeIsEmpty() {
        // Given
        List<Number> validNumbers = List.of(1, -2, 3);
        SeparationRequest request = new SeparationRequest(validNumbers, "");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2); // Both @NotBlank and @Pattern trigger
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Separation type cannot be blank",
                        "Separation type must represent 'parity', 'sign', 'p', or 's'."
                );
    }

    @Test
    void shouldFailValidationWhenSeparationTypeIsBlank() {
        // Given
        List<Number> validNumbers = List.of(1, -2, 3);
        SeparationRequest request = new SeparationRequest(validNumbers, "   ");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Separation type cannot be blank",
                        "Separation type must represent 'parity', 'sign', 'p', or 's'."
                );
    }

    @Test
    void shouldFailValidationWhenSeparationTypeIsInvalid() {
        // Given
        List<Number> validNumbers = List.of(1, -2, 3);
        SeparationRequest request = new SeparationRequest(validNumbers, "random");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Separation type must represent 'parity', 'sign', 'p', or 's'.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"parity", "sign", "p", "s", "PARITY", "PaRiTy", " p a r i t y ", "Pa R iTy"})
    void shouldPassValidationWhenSeparationTypeIsValid(String separationType) {
        // Given
        List<Number> validNumbers = List.of(1, -2, 3);
        SeparationRequest request = new SeparationRequest(validNumbers, separationType);

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithDifferentNumberTypes() {
        // Given
        List<Number> mixedNumberTypes = List.of(1, 2.5, -3L, 4.0f);
        SeparationRequest request = new SeparationRequest(mixedNumberTypes, "parity");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithPositiveAndNegativeNumbers() {
        // Given
        List<Number> mixedSignNumbers = List.of(-1, 2, -3, 4, -5);
        SeparationRequest request = new SeparationRequest(mixedSignNumbers, "sign");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithEvenAndOddNumbers() {
        // Given
        List<Number> mixedParityNumbers = List.of(1, 2, 3, 4, 5);
        SeparationRequest request = new SeparationRequest(mixedParityNumbers, "parity");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    // Multiple violations tests
    @Test
    void shouldFailValidationWithMultipleViolations() {
        // Given
        List<Number> emptyList = List.of();
        SeparationRequest request = new SeparationRequest(emptyList, "invalid");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(3);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Number list must not be empty",
                        "Number list must have at least one element",
                        "Separation type must represent 'parity', 'sign', 'p', or 's'."
                );
    }

    @Test
    void shouldFailValidationWithAllFieldsInvalid() {
        // Given
        List<Number> listWithNull = new ArrayList<>();
        listWithNull.add(1);
        listWithNull.add(null);
        SeparationRequest request = new SeparationRequest(listWithNull, "");

        // When
        Set<ConstraintViolation<SeparationRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(3);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Number cannot be null",
                        "Separation type cannot be blank",
                        "Separation type must represent 'parity', 'sign', 'p', or 's'."
                );
    }
}