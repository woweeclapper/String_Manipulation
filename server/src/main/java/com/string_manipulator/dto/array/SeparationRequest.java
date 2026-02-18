package com.string_manipulator.dto.array;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SeparationRequest(
        @NotNull(message = "Number list cannot be null")
        @Size(min = 1, message = "Number list cannot be empty")
        @Size(max = 1000, message = "Number list cannot exceed 1000 elements")
        List<@NotNull(message = "Number cannot be null") Number> numberList,

        @NotBlank(message = "Separation type cannot be blank")
        @Pattern(regexp = "^(parity|sign|p|s)$", message = "Separation type must be 'parity', 'sign', 'p', or 's'")
        String separationType
) {
}
