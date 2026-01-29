package com.stringmanipulator.util;

/* @author Joe Nguyen */

public class ShiftedString {
    public static String shifting(String response, int numOfShifts, String choice) {

        int[] codePoints = response.codePoints().toArray();
        int length = codePoints.length;

        if (length == 0) {
            return response;
        }

        // Normalize shifts
        numOfShifts = numOfShifts % length;

        //for when shift is 0
        if (numOfShifts == 0) {
            return response;
        }

        int[] shiftedCodePoints;

        if ("left".equals(choice)) {
            shiftedCodePoints = new int[length];
            // Left shift: move first numOfShifts elements to end
            System.arraycopy(codePoints, numOfShifts, shiftedCodePoints, 0, length - numOfShifts);
            System.arraycopy(codePoints, 0, shiftedCodePoints, length - numOfShifts, numOfShifts);
        } else if ("right".equals(choice)) {
            shiftedCodePoints = new int[length];
            // Right shift: move last numOfShifts elements to beginning
            System.arraycopy(codePoints, length - numOfShifts, shiftedCodePoints, 0, numOfShifts);
            System.arraycopy(codePoints, 0, shiftedCodePoints, numOfShifts, length - numOfShifts);
        } else {
            throw new IllegalArgumentException("Choice must be 'left' or 'right'");
        }

        // Convert back to String
        // Convert back to String using code points
        StringBuilder result = new StringBuilder();
        for (int codePoint : shiftedCodePoints) {
            result.appendCodePoint(codePoint);
        }
        return result.toString();
    }
}
