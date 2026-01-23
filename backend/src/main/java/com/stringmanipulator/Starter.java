package com.stringmanipulator;

/* @author Joe Nguyen */

import java.util.*;
import static com.stringmanipulator.util.ReverseString.reverse;
import com.stringmanipulator.util.ShiftedString;
import com.stringmanipulator.util.RecursiveString;
import com.stringmanipulator.util.SortingString;

public class Starter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String mainInput, reverseInput;

        do {
            System.out.println("What would you like to do with the String?: ");
            System.out.println("Reverse (or R)");
            System.out.println("Shift (or S)");
            System.out.println("Sum (or A)");
            System.out.println("Sort (or T)");

            mainInput = sc.nextLine();
            mainInput = mainInput.toLowerCase(); // set all input capitalization to lower case to ensure consistent for
                                                 // the system

            switch (mainInput) {
                case "reverse", "r" -> {

                    do {
                        // Take input from the user
                        System.out.println("Enter the String :");
                        String str = sc.nextLine(); // Input the string
                        // Call a recursive function to reverse the string
                        System.out.println("Original String: " + str);
                        System.out.println("Reverse String: ");
                        reverse(str);
                        System.out.println("Would you like to continue? (y or yes)"); // ask if the user want to continue
                        reverseInput = sc.nextLine();
                        reverseInput = reverseInput.toLowerCase();

                    } while ("yes".equals(reverseInput) || "y".equals(reverseInput));
                    sc.nextLine();
                }
                case "shift", "s" -> {
                    // Constructor for ShiftedString class
                    ShiftedString c = new ShiftedString();
                    // Call the shifting method
                    c.shifting();
                }
                case "sum", "a" -> {
                    RecursiveString c = new RecursiveString();
                    c.Sum();
                }
                case "sort", "t" -> {
                    SortingString c = new SortingString();
                    c.sort();
                }
                default -> System.out.println("Invalid Input, that option is not available!");
            }


            System.out.println("Would you like keep using the program? (Y or Yes)"); // ask if the user want to continue
            mainInput = sc.nextLine();
            mainInput = mainInput.toLowerCase();// accept the input if spell correctly regardless of capitalization

            if ("y".equals(mainInput) || "yes".equals(mainInput)) {
                System.out.println("Thank you for using the program :)");
            }

        } while ("yes".equals(mainInput) || "y".equals(mainInput));
        sc.close();
    }
}