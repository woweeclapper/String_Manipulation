package com.string_manipulator.dto.array;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SortRequest(
        @NotNull(message = "Numbers list cannot be null")
        @Size(min = 1, message = "Numbers list cannot be empty")
        @Size(max = 1000, message = "Numbers list cannot exceed 1000 elements")
        List<@NotNull(message = "Number cannot be null") Number> numbersList,

        @NotBlank(message = "Order type cannot be blank")
        @Pattern(regexp = "^(ascending|descending|a|d)$", message = "Order type must be 'ascending', 'descending', 'a', or 'd'")
        String orderType
) {
}
