package com.string_manipulator.service;

import com.string_manipulator.util.ShiftedString;
import static com.string_manipulator.util.ReverseString.reverse;

//handle inputs, errors, and calling the functions

import java.util.logging.Logger;

public class StringService {

    Logger logger = Logger.getLogger(getClass().getName());

    public String reverseString(String stringToReverse) {

        // these validation is to ensure the string must have at least 1 character through null, empty, and whitespace check
        validateString(stringToReverse);

        //input sanitation
        String sanitizedInput = sanitizeStringInput(stringToReverse);
        return reverse(sanitizedInput);
    }

    /**************************************************************************/

    public String shiftString(String stringToShift, int shifts, String direction) {

        validateString(stringToShift);

        if (shifts < 0) {
            throw new IllegalArgumentException("Shifts cannot be negative");
        }

        String normalizedDirection = validateAndNormalizeDirection(direction);

        if (shifts == 0) {
            return sanitizeStringInput(stringToShift);
        }

        String sanitizedInput = sanitizeStringInput(stringToShift);

        //return the shifted string while lowercasing the direction after validation for the next part
        return processShift(sanitizedInput, shifts, normalizedDirection);

    }

    private String validateAndNormalizeDirection(String direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        String normalized = direction.trim().toLowerCase();

        return switch (normalized) {
            case "left", "l" -> "left";
            case "right", "r" -> "right";
            default -> throw new IllegalArgumentException("Direction must be 'left'/'l' or 'right'/'r'");
        };
    }

    private String processShift(String input, int shifts, String direction) {
        try {
            // Normalize shifts to prevent unnecessary operations
            int normalizedShifts = shifts % input.length();
            return ShiftedString.shifting(input, normalizedShifts, direction);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to shift string: " + e.getMessage(), e);
        }
    }

    /**************************************************************************/

//Reusable Method

    // Input sanitization method
    private String sanitizeStringInput(String input) {
        // Remove control characters except common ones (tab, newline, carriage return)
        String sanitized = input.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

        if (!sanitized.equals(input)) {
            logger.info("Warning: Control characters removed from input");
        }

            return sanitized;
    }

    //validating string method
    private void validateString(String input) {

        final int MAX_LENGTH = 1000;
        if (input == null) {
            throw new IllegalArgumentException("String cannot by null");
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException("String must contain at least 1 character");
        }
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("String cannot contain only whitespace");
        }
        if (input.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("String exceeds maximum length of " + MAX_LENGTH + " characters. "
                    + "Provided length: " + input.length());
        }
    }


}







