package com.string_manipulator.dto.array;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SumRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWhenNumbersListIsValid() {
        // Given
        List<Number> validNumbers = List.of(1, 2, 3, 4, 5);
        SumRequest request = new SumRequest(validNumbers);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWhenNumbersListIsNull() {
        // Given
        SumRequest request = new SumRequest(null);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2); //edge case, both null and empty trigger
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Numbers list cannot be empty",
                        "Numbers list cannot be null"
                );
    }

    @Test
    void shouldFailValidationWhenNumbersListIsEmpty() {
        // Given
        List<Number> emptyList = List.of();
        SumRequest request = new SumRequest(emptyList);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

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
        SumRequest request = new SumRequest(singleElement);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWhenNumbersListHasExactlyMaxElements() {
        // Given
        List<Number> maxElements = List.copyOf(new java.util.ArrayList<Number>() {{
            for (int i = 0; i < 1000; i++) {
                add(i);
            }
        }});
        SumRequest request = new SumRequest(maxElements);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWhenNumbersListExceedsMaxElements() {
        // Given
        List<Number> tooManyElements = List.copyOf(new java.util.ArrayList<Number>() {{
            for (int i = 0; i < 1001; i++) {
                add(i);
            }
        }});
        SumRequest request = new SumRequest(tooManyElements);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

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


        SumRequest request = new SumRequest(listWithNull);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Number cannot be null");
    }

    @Test
    void shouldFailValidationWhenNumbersListContainsMultipleNullElements() {
        // Given
        List<Number> listWithMultipleNulls = new ArrayList<>();
        listWithMultipleNulls.add(1);
        listWithMultipleNulls.add(2);
        listWithMultipleNulls.add(null);
        listWithMultipleNulls.add(4);
        listWithMultipleNulls.add(null);
        SumRequest request = new SumRequest(listWithMultipleNulls);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Number cannot be null",
                        "Number cannot be null"
                );
    }

    @Test
    void shouldPassValidationWithDifferentNumberTypes() {
        // Given
        List<Number> mixedNumberTypes = List.of(1, 2.5, 3L, 4.0f);
        SumRequest request = new SumRequest(mixedNumberTypes);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithNegativeNumbers() {
        // Given
        List<Number> negativeNumbers = List.of(-1, -2, -3);
        SumRequest request = new SumRequest(negativeNumbers);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithZero() {
        // Given
        List<Number> withZero = List.of(0, 1, 2);
        SumRequest request = new SumRequest(withZero);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithDecimalNumbers() {
        // Given
        List<Number> decimalNumbers = List.of(1.5, 2.75, 3.25);
        SumRequest request = new SumRequest(decimalNumbers);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithLargeNumbers() {
        // Given
        List<Number> largeNumbers = List.of(Integer.MAX_VALUE, Long.MAX_VALUE, Double.MAX_VALUE);
        SumRequest request = new SumRequest(largeNumbers);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithSmallList() {
        // Given
        List<Number> smallList = List.of(1);
        SumRequest request = new SumRequest(smallList);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithMediumSizedList() {
        // Given
        List<Number> mediumList = List.copyOf(new java.util.ArrayList<Number>() {{
            for (int i = 0; i < 100; i++) {
                add(i);
            }
        }});
        SumRequest request = new SumRequest(mediumList);

        // When
        Set<ConstraintViolation<SumRequest>> violations = validator.validate(request);

        // Then
        assertThat(violations).isEmpty();
    }
}