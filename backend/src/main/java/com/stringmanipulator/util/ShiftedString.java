package com.stringmanipulator.util;

/* @author Joe Nguyen */

import java.util.*;

public class ShiftedString {
    public void shifting() {
        // Initializing variables
        String response ;
        String Shifted = "";
        String Shift = "";
        int numOfShifts = 0;
        Boolean isValid = true;
        String confirmedInput = "";
        String choice;
        Scanner sc = new Scanner(System.in);


        do {
            System.out.println("Enter the String :");
            response = sc.nextLine();

            while (isValid || "yes".equals(confirmedInput) || "y".equals(confirmedInput)) {
                try {
                    // Validate whether this is a number or letter through try and catch error
                    System.out.println("Input the number of shifts you want to perform: ");
                    Shift = sc.nextLine();
                    numOfShifts = Integer.parseInt(Shift);
                    isValid = false;
                    confirmedInput = "N";

                }

                catch (NumberFormatException e) {
                    System.out.println(Shift + " is not a valid input"); // output if invalid input
                    isValid = true;
                }
            }
            System.out.println("Would you like to shift to the right or left?");
            System.out.println("Please make sure to spell right or left correctly!");
            choice = sc.nextLine();
            choice = choice.toLowerCase();

            if ("left".equals(choice)) {
                // Initialize response to a different variable in order to shift the copy and
                // not the original
                Shifted = response;
                // Shifting string using the substring method
                numOfShifts = numOfShifts % Shifted.length();
                Shifted = Shifted.substring(numOfShifts) + Shifted.substring(0, numOfShifts);
            } else if ("right".equals(choice)) {
                // Initialize response to a different variable in order to shift the copy and
                // not the original
                Shifted = response;
                // Shifting string using the substring method
                numOfShifts = numOfShifts % Shifted.length();
                Shifted = Shifted.substring(Shifted.length() - numOfShifts, Shifted.length())
                        + Shifted.substring(0, Shifted.length() - numOfShifts);
            }

            // Outputs
            System.out.println("Original String: " + response + "\nShifted String: " + Shifted);
            System.out.println("Do you want to continue?(Y or Yes)"); // asked user if they to continue using this
            confirmedInput = sc.nextLine();
            confirmedInput = confirmedInput.toLowerCase(); // also accept miswritten capitalized letter such as "yEs" or
                                                           // "yES"

        } while ("yes".equals(confirmedInput) || "y".equals(confirmedInput)); // will continue running if yes; otherwise
                                                                              // will stop
        //sc.close();
    }
}