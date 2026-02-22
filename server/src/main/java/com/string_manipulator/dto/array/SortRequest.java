package com.string_manipulator.dto.array;

import jakarta.validation.constraints.*;

import java.util.List;

public record SortRequest(
        @NotNull(message = "Numbers list cannot be null")
        @NotEmpty(message = "Numbers list cannot be empty")
        @Size(min = 1, message = "Numbers list must have at least one element")
        @Size(max = 1000, message = "Numbers list cannot exceed 1000 elements")
        List<@NotNull(message = "Number cannot be null") Number> numbersList,

        @NotBlank(message = "Order type cannot be blank")
        @Pattern(
                regexp =
                        "(?i)^\\s*(a\\s*s\\s*c\\s*e\\s*n\\s*d\\s*i\\s*n\\s*g|" +
                                "d\\s*e\\s*s\\s*c\\s*e\\s*n\\s*d\\s*i\\s*n\\s*g|" +
                                "a|d)\\s*$",
                message = "Order type must represent 'ascending', 'descending', 'a', or 'd'."
        )

        String orderType
) {
}
