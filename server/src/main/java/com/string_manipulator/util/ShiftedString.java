package com.string_manipulator.util;

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

            if (isZeroWidthJoiner(c)) return true;
            if (isCombiningCharacter(c)) return true;
            if (isSkinToneModifier(str, i)) return true;
            if (isVariationSelector(c)) return true;
            if (hasMultipleConsecutiveSurrogates(str, i)) return true;
        }
        return false;
    }

    private static boolean isZeroWidthJoiner(char c) {
        return c == '\u200D';
    }

    private static boolean isCombiningCharacter(char c) {
        return c >= '\u0300' && c <= '\u036F';
    }

    private static boolean isSkinToneModifier(String str, int i) {
        char c = str.charAt(i);
        return c >= '\uD83C' && i < str.length() - 1 &&
                str.charAt(i + 1) >= '\uDFFB' && str.charAt(i + 1) <= '\uDFFF';
    }

    private static boolean isVariationSelector(char c) {
        return c >= '\uFE00' && c <= '\uFE0F';
    }

    private static boolean hasMultipleConsecutiveSurrogates(String str, int i) {
        if (!Character.isHighSurrogate(str.charAt(i))) {
            return false;
        }

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
        return surrogateCount >= 2;
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
