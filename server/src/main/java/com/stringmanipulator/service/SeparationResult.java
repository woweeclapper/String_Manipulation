package com.stringmanipulator.service;

import java.util.ArrayList;

public class SeparationResult<T> {
    private final ArrayList<T> first;
    private final ArrayList<T> second;
    private final String separationType;

    public SeparationResult(ArrayList<T> first, ArrayList<T> second, String separationType) {
        this.first = first;
        this.second = second;
        this.separationType = separationType;
    }

    // Type-safe getters for specific separation types
    public ArrayList<T> getPositive() {
        return "sign".equals(separationType) ? first : null;
    }

    public ArrayList<T> getNegative() {
        return "sign".equals(separationType) ? second : null;
    }

    public ArrayList<T> getEven() {
        return "parity".equals(separationType) ? first : null;
    }

    public ArrayList<T> getOdd() {
        return "parity".equals(separationType) ? second : null;
    }

    // Generic getters
    public ArrayList<T> getFirst() {
        return first;
    }

    public ArrayList<T> getSecond() {
        return second;
    }

    public String getSeparationType() {
        return separationType;
    }
}