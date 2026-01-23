package com.stringmanipulator.util;

/* @author Joe Nguyen */

public class ReverseString {
    //Recursive function that reverses a string
    public static String reverse(String str) {
        //If the string is null or consists of single character, then output the entered string 
        if (str.length() <= 1)
            return str;
        else {
            //Call the function recursively to reverse the string
            return str.charAt(str.length() - 1) + reverse(str.substring(0, str.length() - 1));
        }
    }
}