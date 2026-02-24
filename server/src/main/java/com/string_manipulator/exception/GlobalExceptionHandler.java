package com.string_manipulator.exception;

import com.string_manipulator.dto.error.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex, WebRequest request) {

        // Extract request context information
        String requestUri = request.getDescription(false).replace("uri=", "");
        String userAgent = request.getHeader("User-Agent");


        logger.warn("Request validation failed - URI: {}, User-Agent: {}, Errors: {}",
                requestUri, userAgent, ex.getMessage());


        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Request validation failed",
                details
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJson(
            HttpMessageNotReadableException ex, WebRequest request) {

        String requestUri = request.getDescription(false).replace("uri=", "");

        logger.warn("Malformed JSON request - URI: {}, Error: {}",
                requestUri, ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Malformed JSON",
                "The request body contains invalid JSON",
                List.of("Invalid JSON format")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleServiceValidation(
            IllegalArgumentException ex, WebRequest request) {

        String requestUri = request.getDescription(false)
                .replace("uri=", "");


        logger.warn("Service validation error - URI: {}, Error: {}",
                requestUri, ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Input",
                "Service validation failed",
                List.of(ex.getMessage())
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleServiceState(
            IllegalStateException ex, WebRequest request) {

        String requestUri = request.getDescription(false)
                .replace("uri=", "");

        logger.warn("Service state error - URI: {}, Error: {}",
                requestUri, ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid State",
                "Service state failed",
                List.of(ex.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedErrors(
            Exception ex, WebRequest request) {

        String requestUri = request.getDescription(false).replace("uri=", "");
        String userAgent = request.getHeader("User-Agent");

        logger.error("Unexpected error - URI: {}, User-Agent: {}, Error: {}",
                requestUri, userAgent, ex.getMessage(), ex);


        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred",
                List.of("Please try again later")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
