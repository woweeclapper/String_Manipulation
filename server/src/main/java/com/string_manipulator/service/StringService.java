package com.string_manipulator.service;

import com.string_manipulator.util.ShiftedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.string_manipulator.util.ReverseString.reverse;


@Service
public class StringService {

    private static final Logger logger = LoggerFactory.getLogger(StringService.class);

    public String reverseString(String stringToReverse) {

        logger.info("Entering reverseString with input: {}", stringToReverse);

        //input sanitation
        String sanitizedInput = sanitizeStringInput(stringToReverse);
        String result = reverse(sanitizedInput);
        logger.info("Exiting reverseString with result: {}", result);
        return result;
    }

    /**************************************************************************/

    public String shiftString(String stringToShift, int shifts, String direction) {
        long startTime = System.currentTimeMillis();
        logger.info("Starting a timed shift operation: string = {}, shift = {}, direction = {}",
                stringToShift, shifts, direction);

        String normalizedDirection = normalizingDirection(direction);

        if (shifts == 0) {
            return sanitizeStringInput(stringToShift);
        }

        String sanitizedInput = sanitizeStringInput(stringToShift);
        String result = processShift(sanitizedInput, shifts, normalizedDirection);

        long endTime = System.currentTimeMillis();
        logger.info("Shift operation completed in {}ms with result {}",
                (endTime - startTime), result);
        //return the shifted string while lowercasing the direction after validation for the next part
        return result;

    }

    private String normalizingDirection(String direction) {

        logger.info("Normalizing direction: {}", direction);
        String normalized = direction.trim();              // remove outer whitespace
        normalized = normalized.replaceAll("\\s+", "");    // remove internal whitespace
        normalized = normalized.toLowerCase();             // normalize case

        return switch (normalized) {
            case "left", "l" -> {
                logger.info("Normalized direction to left");
                yield "left";
            }
            case "right", "r" -> {
                logger.info("Normalized direction to right");
                yield "right";
            }
            default -> {//theoretically unreachable (dead code), but just in case
                logger.warn("Direction must be 'left'/'l' or 'right'/'r'");
                throw new IllegalArgumentException("Direction must be 'left'/'l' or 'right'/'r'");
            }
        };
    }

    private String processShift(String input, int shifts, String direction) {
        try {
            // Normalize numOfShifts to prevent unnecessary operations
            int normalizedShifts = shifts % input.length();
            logger.info("Normalized numOfShifts from {} to {}", shifts, normalizedShifts);
            return ShiftedString.shifting(input, normalizedShifts, direction);
        } catch (Exception e) {
            logger.warn("Failed to shift string: input = {}, numOfShifts = {}, direction = {}",
                    input, shifts, direction);
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

}







