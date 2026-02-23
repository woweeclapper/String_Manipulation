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

class SortRequestTest {

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
        List<Number> validNumbers = List.of(3, 1, 4, 1, 5);
        SortRequest request = new SortRequest(validNumbers, "ascending");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    // NumbersList field tests
    @Test
    void shouldFailValidationWhenNumbersListIsNull() {
        // Given
        SortRequest request = new SortRequest(null, "ascending");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2); // Both @NotNull and @NotEmpty trigger
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Numbers list cannot be null",
                        "Numbers list cannot be empty"
                );
    }

    @Test
    void shouldFailValidationWhenNumbersListIsEmpty() {
        // Given
        List<Number> emptyList = List.of();
        SortRequest request = new SortRequest(emptyList, "ascending");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2); // Both @NotEmpty and @Size(min = 1) trigger
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Numbers list cannot be empty",
                        "Numbers list must have at least one element"
                );
    }

    @Test
    void shouldPassValidationWhenNumbersListHasExactlyOneElement() {
        // Given
        List<Number> singleElement = List.of(42);
        SortRequest request = new SortRequest(singleElement, "ascending");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWhenNumbersListHasExactlyMaxElements() {
        // Given
        List<Number> maxElements = List.copyOf(new ArrayList<>() {{
            for (int i = 0; i < 1000; i++) {
                add(i);
            }
        }});
        SortRequest request = new SortRequest(maxElements, "ascending");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWhenNumbersListExceedsMaxElements() {
        // Given
        List<Number> tooManyElements = List.copyOf(new ArrayList<>() {{
            for (int i = 0; i < 1001; i++) {
                add(i);
            }
        }});
        SortRequest request = new SortRequest(tooManyElements, "ascending");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Numbers list cannot exceed 1000 elements");
    }

    @Test
    void shouldFailValidationWhenNumbersListContainsNullElement() {
        // Given
        List<Number> listWithNull = new ArrayList<>();
        listWithNull.add(1);
        listWithNull.add(2);
        listWithNull.add(null);
        listWithNull.add(4);
        SortRequest request = new SortRequest(listWithNull, "ascending");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Number cannot be null");
    }

    @Test
    void shouldFailValidationWhenNumbersListContainsMultipleNullElements() {
        // Given
        List<Number> listWithMultipleNulls = new ArrayList<>();
        listWithMultipleNulls.add(1);
        listWithMultipleNulls.add(null);
        listWithMultipleNulls.add(3);
        listWithMultipleNulls.add(null);
        listWithMultipleNulls.add(5);
        SortRequest request = new SortRequest(listWithMultipleNulls, "ascending");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Number cannot be null",
                        "Number cannot be null"
                );
    }

    // OrderType field tests
    @Test
    void shouldFailValidationWhenOrderTypeIsNull() {
        // Given
        List<Number> validNumbers = List.of(3, 1, 4);
        SortRequest request = new SortRequest(validNumbers, null);

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Order type cannot be blank");
    }

    @Test
    void shouldFailValidationWhenOrderTypeIsEmpty() {
        // Given
        List<Number> validNumbers = List.of(3, 1, 4);
        SortRequest request = new SortRequest(validNumbers, "");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2); // Both @NotBlank and @Pattern trigger
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Order type cannot be blank",
                        "Order type must represent 'ascending', 'descending', 'a', or 'd'."
                );
    }

    @Test
    void shouldFailValidationWhenOrderTypeIsBlank() {
        // Given
        List<Number> validNumbers = List.of(3, 1, 4);
        SortRequest request = new SortRequest(validNumbers, "   ");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Order type cannot be blank",
                        "Order type must represent 'ascending', 'descending', 'a', or 'd'."
                );
    }

    @Test
    void shouldFailValidationWhenOrderTypeIsInvalid() {
        // Given
        List<Number> validNumbers = List.of(3, 1, 4);
        SortRequest request = new SortRequest(validNumbers, "random");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Order type must represent 'ascending', 'descending', 'a', or 'd'.");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ascending",
            "descending",
            "a",
            "d",
            "ASCENDING",
            "AsCeNdInG",
            " a s c e n d i n g ",
            "dEs CendIn G"
    })
    void shouldPassValidationForValidOrderTypes(String validOrderType) {
        // Given
        List<Number> validNumbers = List.of(3, 1, 4);
        SortRequest request = new SortRequest(validNumbers, validOrderType);

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithDifferentNumberTypes() {
        // Given
        List<Number> mixedNumberTypes = List.of(1, 2.5, 3L, 4.0f);
        SortRequest request = new SortRequest(mixedNumberTypes, "ascending");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    // Multiple violations tests
    @Test
    void shouldFailValidationWithMultipleViolations() {
        // Given
        List<Number> emptyList = List.of();
        SortRequest request = new SortRequest(emptyList, "invalid");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(3);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Numbers list cannot be empty",
                        "Numbers list must have at least one element",
                        "Order type must represent 'ascending', 'descending', 'a', or 'd'."
                );
    }

    @Test
    void shouldFailValidationWithAllFieldsInvalid() {
        // Given
        List<Number> listWithNull = new ArrayList<>();
        listWithNull.add(1);
        listWithNull.add(null);
        SortRequest request = new SortRequest(listWithNull, "");

        // When
        Set<ConstraintViolation<SortRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(3);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Number cannot be null",
                        "Order type cannot be blank",
                        "Order type must represent 'ascending', 'descending', 'a', or 'd'."
                );
    }
}