package com.string_manipulator.dto.array;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SumRequest(
        @NotNull(message = "Numbers list cannot be null")
        @Size(min = 1, message = "Numbers list cannot be empty")
        @Size(max = 1000, message = "Numbers list cannot exceed 1000 elements")
        List<@NotNull(message = "Number cannot be null") Number> numbersList
) {
}