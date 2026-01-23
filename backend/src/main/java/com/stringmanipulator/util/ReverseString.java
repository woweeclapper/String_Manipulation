package com.stringmanipulator.util;

/* @author Joe Nguyen */

public class ReverseString
{
    //Recursive function that reverses a string
   public static void reverse(String str) 
    { 
        //If the string is null or consists of single character, then output the entered string 
        if ((str == null)||(str.length() <= 1)) 
           System.out.println(str); 
        else
        { 
            //If string consists of multiple strings
            System.out.print(str.charAt(str.length()-1)); 
            
            //Call the function recursively to reverse the string
            reverse(str.substring(0,str.length()-1)); 
        }         
    }    
}