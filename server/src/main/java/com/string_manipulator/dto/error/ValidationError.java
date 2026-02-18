package com.string_manipulator.dto.error;

public record ValidationError(
        String field,
        String message,
        Object rejectedValue
) {
}
