package com.string_manipulator.dto.array.separation_responses;

import java.util.List;

public record IntSepResponses(
        List<Integer> firstGroup,
        List<Integer> secondGroup,
        String separationType) {}
