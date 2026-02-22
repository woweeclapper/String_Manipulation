package com.string_manipulator.dto.string;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReverseRequest(
        @NotBlank(message = "Text cannot be blank")
        @Size(max = 10000, message = "Text cannot exceed 10000 characters")
        String text
) {
}

