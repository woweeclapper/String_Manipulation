package com.stringmanipulator.util;

/* @author Joe Nguyen */

public class ShiftedString {
    public static String shifting(String response, int numOfShifts, String choice) {

        // Check if we need to use String-based shifting for complex Unicode
        if (hasComplexUnicode(response)) {
            return shiftWithString(response, numOfShifts, choice);
        }

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

        // Convert back to String using code points
        StringBuilder result = new StringBuilder();
        for (int codePoint : shiftedCodePoints) {
            result.appendCodePoint(codePoint);
        }
        return result.toString();
    }

    private static boolean hasComplexUnicode(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            // Check for zero-width joiner (U+200D) - always indicates complex sequences
            if (c == '\u200D') {
                return true;
            }

            // Check for combining characters (Unicode combining diacritical marks)
            if (c >= '\u0300' && c <= '\u036F') {
                return true;
            }

            // Check for skin tone modifiers (U+1F3FB to U+1F3FF)
            if (c >= '\uD83C' && i < str.length() - 1) {
                char next = str.charAt(i + 1);
                if (next >= '\uDFFB' && next <= '\uDFFF') {
                    return true;
                }
            }

            // Check for variation selectors (U+FE00 to U+FE0F)
            if (c >= '\uFE00' && c <= '\uFE0F') {
                return true;
            }

            // Check for multiple consecutive surrogate pairs (complex emoji sequences)
            if (Character.isHighSurrogate(c)) {
                // Count consecutive surrogate pairs
                int surrogateCount = 0;
                int j = i;
                while (j < str.length() - 1) {
                    char current = str.charAt(j);
                    char next = str.charAt(j + 1);
                    if (Character.isHighSurrogate(current) && Character.isLowSurrogate(next)) {
                        surrogateCount++;
                        j += 2;
                    } else {
                        break;
                    }
                }
                // Only consider complex if we have 2+ consecutive surrogate pairs
                if (surrogateCount >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String shiftWithString(String response, int numOfShifts, String choice) {
        int length = response.length();

        if (length == 0) {
            return response;
        }

        // Normalize shifts
        numOfShifts = numOfShifts % length;

        // For when shift is 0
        if (numOfShifts == 0) {
            return response;
        }

        if ("left".equals(choice)) {
            return response.substring(numOfShifts) + response.substring(0, numOfShifts);
        } else if ("right".equals(choice)) {
            return response.substring(length - numOfShifts) + response.substring(0, length - numOfShifts);
        } else {
            throw new IllegalArgumentException("Choice must be 'left' or 'right'");
        }
    }
}
