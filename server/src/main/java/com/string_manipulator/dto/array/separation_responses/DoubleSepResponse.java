package com.string_manipulator.dto.array.separation_responses;

import java.util.List;

public record DoubleSepResponse(
        List<Double> firstGroup,
        List<Double> secondGroup,
        String separationType) {}
