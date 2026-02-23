package com.string_manipulator.dto.string;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ShiftRequest(
        @NotBlank(message = "Text cannot be blank")
        @Size(max = 10000, message = "Text cannot exceed 10000 characters")
        String text,


        @PositiveOrZero(message = "Number of shifts cannot be negative")
        int numOfShifts,

        @NotBlank(message = "Direction cannot be blank")
        @Pattern(
                regexp = "(?i)^\\s*(l\\s*e\\s*f\\s*t|r\\s*i\\s*g\\s*h\\s*t|l|r)\\s*$",
                message = "Direction must represent 'left', 'right', 'l', or 'r'."
        )
        String direction
) {
}