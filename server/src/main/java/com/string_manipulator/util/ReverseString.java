package com.string_manipulator.util;
import java.util.List;

import static com.string_manipulator.util.GraphemeCluster.splitIntoGraphemes;

/* @author Joe Nguyen */

public class ReverseString {

    public static String reverse(String text) {
        List<String> graphemes = splitIntoGraphemes(text);
        StringBuilder reversed = new StringBuilder(text.length());
        for (int i = graphemes.size() - 1; i >= 0; i--) {
            reversed.append(graphemes.get(i));
        }
        return reversed.toString();
    }
}