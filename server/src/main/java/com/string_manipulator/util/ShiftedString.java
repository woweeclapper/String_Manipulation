package com.string_manipulator.util;

/* @author Joe Nguyen */

import java.util.List;
import static com.string_manipulator.util.GraphemeCluster.splitIntoGraphemes;


public class ShiftedString {
    public static String shifting(String response, int numOfShifts, String choice) {

        List<String> graphemes = splitIntoGraphemes(response);
        StringBuilder shifted = new StringBuilder(response.length());
        int length = graphemes.size();

        //for when shift is 0
        if (numOfShifts == 0) {
            return response;
        }

        // Normalize numOfShifts
        numOfShifts = numOfShifts % length;


        if ("left".equals(choice)) {
            // Left shift: move first elements to end
            for (int i = 0; i < length; i++) {
                shifted.append(graphemes.get((i + numOfShifts) % length));
            }
        } else if ("right".equals(choice)) {
            // Right shift: move last elements to beginning
            for (int i = 0; i < length; i++) {
                shifted.append(graphemes.get((i - numOfShifts + length) % length));
            }
        } else {
            throw new IllegalArgumentException("Choice must be 'left' or 'right'");
        }

        return shifted.toString();

    }
}
