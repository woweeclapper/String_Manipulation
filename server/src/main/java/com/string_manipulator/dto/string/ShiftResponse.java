package com.string_manipulator.dto.string;

public record ShiftResponse(
        String shiftedText,
        int numOfShifts,
        String direction) {}
