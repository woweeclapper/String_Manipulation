package com.string_manipulator.dto.array.sort_responses;


import java.util.List;

public record DoubleSortResponse(
        List<Double> sorted,
        String orderType) {}
