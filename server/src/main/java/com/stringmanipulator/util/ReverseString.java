package com.stringmanipulator.util;

/* @author Joe Nguyen */

public class ReverseString {

    public static String reverse(String str) {
        // Use code points to properly handle Unicode characters (including emoji)
        int[] codePoints = str.codePoints().toArray();

        // Reverse the code points
        for (int i = 0; i < codePoints.length / 2; i++) {
            int temp = codePoints[i];
            codePoints[i] = codePoints[codePoints.length - 1 - i];
            codePoints[codePoints.length - 1 - i] = temp;
        }

        // Convert back to String using StringBuilder for better reliability
        StringBuilder sb = new StringBuilder();
        for (int codePoint : codePoints) {
            sb.appendCodePoint(codePoint);
        }
        return sb.toString();
    }
}