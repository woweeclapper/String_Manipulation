package com.string_manipulator.service;

import com.string_manipulator.util.ShiftedString;
import static com.string_manipulator.util.ReverseString.reverse;

//handle inputs, errors, and calling the functions

import java.util.logging.Level;
import java.util.logging.Logger;

public class StringService {

    private static final Logger logger = Logger.getLogger(StringService.class.getName());

    static {
        logger.log(Level.INFO, "StringService initialized with logging level: {0}", logger.getLevel());
    }

    public String reverseString(String stringToReverse) {

        logger.log(Level.INFO, "Entering reverseString with input: {0}", stringToReverse);
        // these validation is to ensure the string must have at least 1 character through null, empty, and whitespace check
        validateString(stringToReverse);

        //input sanitation
        String sanitizedInput = sanitizeStringInput(stringToReverse);
        String result = reverse(sanitizedInput);
        logger.log(Level.INFO, "Exiting reverseString with result: {0}", result);
        return result;
    }
    /**************************************************************************/

    public String shiftString(String stringToShift, int shifts, String direction) {
        long startTime = System.currentTimeMillis();
        logger.log(Level.INFO, "Starting a timed shift operation: string = {0}, shift = {1}, direction = {2}",
                new Object[] {stringToShift, shifts, direction});
        validateString(stringToShift);

        if (shifts < 0) {
            throw new IllegalArgumentException("Shifts cannot be negative");
        }

        String normalizedDirection = validateAndNormalizeDirection(direction);

        if (shifts == 0) {
            return sanitizeStringInput(stringToShift);
        }

        String sanitizedInput = sanitizeStringInput(stringToShift);
        String result = processShift(sanitizedInput ,shifts, normalizedDirection);

        long endTime = System.currentTimeMillis();
        logger.log(Level.INFO, "Shift operation completed in {0}ms", (endTime - startTime));
        //return the shifted string while lowercasing the direction after validation for the next part
        return result;

    }

    private String validateAndNormalizeDirection(String direction) {
        logger.log(Level.INFO, "Validating direction: {0}", direction);

        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        logger.log(Level.INFO, "Normalizing direction: {0}", direction);
        String normalized = direction.trim().toLowerCase();

        return switch (normalized) {
            case "left", "l" -> {
                logger.info("Direction normalized to left");
                yield "left";
            }
            case "right", "r" -> {
                logger.info("Direction normalized to right");
                yield "right";
            }
            default -> {
                logger.warning("Direction must be 'left'/'l' or 'right'/'r'");
                throw new IllegalArgumentException("Direction must be 'left'/'l' or 'right'/'r'");
            }
        };
    }

    private String processShift(String input, int shifts, String direction) {
        try {
            // Normalize shifts to prevent unnecessary operations
            int normalizedShifts = shifts % input.length();
            logger.log(Level.INFO, "Normalized shifts from {0} to {1}",
                    new Object[] {shifts, normalizedShifts});
            return ShiftedString.shifting(input, normalizedShifts, direction);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to shift string: input = {0}, shifts = {1}, direction = {2}",
                    new Object[] {input, shifts, direction});
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

        logger.info("Validating string");


        if (input == null) {
            logger.warning("Validation failed:String cannot by null");
            throw new IllegalArgumentException("String cannot by null");
        }
        if (input.isEmpty()) {
            logger.warning("Validation failed: String must contain at least 1 character");
            throw new IllegalArgumentException("String must contain at least 1 character");
        }
        if (input.trim().isEmpty()) {
            logger.warning("Validation failed: String cannot contain only whitespace");
            throw new IllegalArgumentException("String cannot contain only whitespace");
        }

    }

}







