package com.string_manipulator.dto.array.sort_responses;

import java.util.List;

public record IntSortResponse(
        List<Integer> sorted,
        String orderType) {}
