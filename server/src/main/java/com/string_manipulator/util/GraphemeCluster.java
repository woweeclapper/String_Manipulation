package com.string_manipulator.util;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class GraphemeCluster {

    /**
     * Splits a string into a list of grapheme clusters (user-perceived characters).
     * Safe for complex emojis, combining marks, ZWJ sequences, etc.
     */
    public static List<String> splitIntoGraphemes(String text) {
        if (text == null || text.isEmpty()) {
            return List.of();
        }

        List<String> graphemes = new ArrayList<>();

        BreakIterator iterator = BreakIterator.getCharacterInstance();  // Uses Extended Grapheme Clusters in Java 20+
        iterator.setText(text);

        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            graphemes.add(text.substring(start, end));
        }

        return graphemes;
    }


    /**
     * Gets the number of grapheme clusters (better than codePointCount for human length).
     */
    public static int graphemeLength(String text) {
        if (text == null) return 0;

        BreakIterator iterator = BreakIterator.getCharacterInstance();
        iterator.setText(text);

        int count = 0;
        for (int end = iterator.first(); end != BreakIterator.DONE; end = iterator.next()) {
            count++;
        }
        return count;
    }

    /**
     * Safe truncate to max graphemes (adds ellipsis if needed).
     */
    public static String truncate(String text, int maxGraphemes) {
        List<String> graphemes = splitIntoGraphemes(text);
        if (graphemes.size() <= maxGraphemes) {
            return text;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxGraphemes; i++) {
            sb.append(graphemes.get(i));
        }
        return sb.append("…").toString();
    }
}
