package com.string_manipulator.service;

import java.util.ArrayList;

// Enhanced SeparationResult with better type safety
public class SeparationResult<T> {
    private final ArrayList<T> first;
    private final ArrayList<T> second;
    private final SeparationType separationType;

    public enum SeparationType {
        PARITY("parity"),
        SIGN("sign");

        private final String value;

        SeparationType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static SeparationType fromString(String value) {
            for (SeparationType type : values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown separation type: " + value);
        }
    }

    public SeparationResult(ArrayList<T> first, ArrayList<T> second, SeparationType separationType) {
        this.first = first;
        this.second = second;
        this.separationType = separationType;
    }

    // Type-safe getters with proper validation
    public ArrayList<T> getEven() {
        validateSeparationType(SeparationType.PARITY);
        return first;
    }

    public ArrayList<T> getOdd() {
        validateSeparationType(SeparationType.PARITY);
        return second;
    }

    public ArrayList<T> getPositive() {
        validateSeparationType(SeparationType.SIGN);
        return first;
    }

    public ArrayList<T> getNegative() {
        validateSeparationType(SeparationType.SIGN);
        return second;
    }

    private void validateSeparationType(SeparationType expectedType) {
        if (separationType != expectedType) {
            throw new IllegalStateException(
                    String.format("Cannot get %s from separation type %s",
                            expectedType.name().toLowerCase(), separationType.name().toLowerCase())
            );
        }
    }

    // Generic getters
    public ArrayList<T> getFirst() { return first; }
    public ArrayList<T> getSecond() { return second; }
    public SeparationType getSeparationType() { return separationType; }
}

