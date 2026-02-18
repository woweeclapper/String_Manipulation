package com.string_manipulator.dto.string;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ShiftRequest(
        @NotBlank(message = "Text cannot be blank")
        @Size(max = 1000, message = "Text cannot exceed 1000 characters")
        String text,

        @Min(value = 0, message = "Number of shifts cannot be negative")
        int numOfShifts,

        @NotBlank(message = "Direction cannot be blank")
        @Pattern(regexp = "^(left|right|l|r)$", message = "Direction must be 'left', 'right', 'l', or 'r'")
        String direction
) {
}