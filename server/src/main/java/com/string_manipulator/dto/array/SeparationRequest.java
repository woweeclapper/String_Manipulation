package com.string_manipulator.dto.array;

import jakarta.validation.constraints.*;

import java.util.List;

public record SeparationRequest(
        @NotNull(message = "Number list cannot be null")
        @NotEmpty(message = "Number list must not be empty")
        @Size(min = 1, message = "Number list must have at least one element")
        @Size(max = 1000, message = "Number list cannot exceed 1000 elements")
        List<@NotNull(message = "Number cannot be null") Number> numberList,

        @NotBlank(message = "Separation type cannot be blank")
        @Pattern(
                regexp =
                        "(?i)^\\s*(p\\s*a\\s*r\\s*i\\s*t\\s*y|" +
                                "s\\s*i\\s*g\\s*n|" +
                                "p|s)\\s*$",
                message = "Separation type must represent 'parity', 'sign', 'p', or 's'."
        )

        String separationType
) {
}
