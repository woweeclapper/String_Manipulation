package com.string_manipulator.dto.string;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReverseRequest(
        @NotBlank(message = "Text cannot be blank")
        @Size(max = 1000, message = "Text cannot exceed 1000 characters")
        String text
) {
}

