package com.stringmanipulator.util;

/* @author Joe Nguyen */

public class ShiftedString {
    public static String shifting(String response, int numOfShifts, String choice) {

        if ("left".equals(choice)) {

            numOfShifts = numOfShifts % response.length();
            response = response.substring(numOfShifts) + response.substring(0, numOfShifts);

        } else if ("right".equals(choice)) {

            numOfShifts = numOfShifts % response.length();
            response = response.substring(response.length() - numOfShifts, response.length())
                    + response.substring(0, response.length() - numOfShifts);
        }

        return response;
    }
}