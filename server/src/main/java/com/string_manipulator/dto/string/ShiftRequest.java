package com.string_manipulator.dto.string;

public record ShiftRequest(
        String text,
        int numOfShifts,
        String direction) {}
