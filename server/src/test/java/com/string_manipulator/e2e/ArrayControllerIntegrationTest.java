package com.string_manipulator.e2e;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.string_manipulator")
@DisplayName("ArrayController Integration Tests")
class ArrayControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    // ========================================
    // 1. Happy-Path Tests (200 OK)
    // ========================================

    @Nested
    @DisplayName("Happy-Path Tests")
    class HappyPathTests {

        @Test
        @DisplayName("POST /api/array/sum should return 200 OK with integer array [1,2,3]")
        void sum_shouldReturn200_whenIntegerArray() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sum").value(6.0))
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$.*", hasSize(1)));
        }

        @Test
        @DisplayName("POST /api/array/sum should return 200 OK with mixed numbers [-1, 5, 10]")
        void sum_shouldReturn200_whenMixedNumbers() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[-1,5,10]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sum").value(14.0))
                    .andExpect(jsonPath("$.length()").value(1));
        }

        @Test
        @DisplayName("POST /api/array/sum should return 200 OK with single element [0]")
        void sum_shouldReturn200_whenSingleElement() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[0]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sum").value(0.0))
                    .andExpect(jsonPath("$.length()").value(1));
        }

        @Test
        @DisplayName("POST /api/array/sort should return 200 OK with ascending mode")
        void sort_shouldReturn200_whenAscendingMode() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[3,1,2],\"orderType\":\"ascending\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", containsInAnyOrder(1, 2, 3)))
                    .andExpect(jsonPath("$.orderType").value("ascending"))
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$.*", hasSize(2)));
        }

        @Test
        @DisplayName("POST /api/array/sort should return 200 OK with descending mode")
        void sort_shouldReturn200_whenDescendingMode() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[3,1,2],\"orderType\":\"d\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", containsInAnyOrder(1, 2, 3)))
                    .andExpect(jsonPath("$.orderType").value("d"))
                    .andExpect(jsonPath("$.length()").value(2));
        }

        @Test
        @DisplayName("POST /api/array/separate should return 200 OK with parity separation")
        void separate_shouldReturn200_whenParitySeparation() throws Exception {
            // Given
            String requestBody = "{\"numberList\":[1,2,3,4],\"separationType\":\"parity\"}";

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.firstGroup", containsInAnyOrder(2, 4)))
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup", containsInAnyOrder(1, 3)))
                    .andExpect(jsonPath("$.separationType").value("parity"))
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$.*", hasSize(3)));
        }

        @Test
        @DisplayName("POST /api/array/separate should return 200 OK with sign separation")
        void separate_shouldReturn200_whenSignSeparation() throws Exception {
            // Given
            String requestBody = "{\"numberList\":[1,-2,3,-4],\"separationType\":\"sign\"}";

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.firstGroup", containsInAnyOrder(1, 3)))
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup", containsInAnyOrder(-2, -4)))
                    .andExpect(jsonPath("$.separationType").value("sign"))
                    .andExpect(jsonPath("$.length()").value(3));
        }
    }

    // ========================================
    // 2. Validation Error Tests (400 Bad Request)
    // ========================================

    @Nested
    @DisplayName("Validation Error Tests")
    class ValidationErrorTests {

        @Test
        @DisplayName("POST /api/array/sum should return 400 when numbers field is missing")
        void sum_shouldReturn400_whenNumbersFieldMissing() throws Exception {
            // Given
            String requestBody = "{}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.message").value("Request validation failed"))
                    .andExpect(jsonPath("$.details").isArray())
                    .andExpect(jsonPath("$.details[?(@ contains 'numbersList')]").exists())
                    .andExpect(jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("POST /api/array/sum should return 400 when numbers is empty array")
        void sum_shouldReturn400_whenNumbersIsEmpty() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ contains 'empty')]").exists());
        }

        @Test
        @DisplayName("POST /api/array/sum should return 400 when numbers is null")
        void sum_shouldReturn400_whenNumbersIsNull() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":null}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ contains 'null')]").exists());
        }

        @Test
        @DisplayName("POST /api/array/sort should return 400 when orderType is missing")
        void sort_shouldReturn400_whenOrderTypeMissing() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3]}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ contains 'orderType')]").exists());
        }

        @Test
        @DisplayName("POST /api/array/sort should return 400 when orderType is invalid")
        void sort_shouldReturn400_whenOrderTypeIsInvalid() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3],\"orderType\":\"invalid\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ contains 'Order type must represent')]").exists());
        }

        @Test
        @DisplayName("POST /api/array/sort should return 400 when orderType is whitespace")
        void sort_shouldReturn400_whenOrderTypeIsWhitespace() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3],\"orderType\":\"   \"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ contains 'Order type cannot be blank')]").exists());
        }

        @Test
        @DisplayName("POST /api/array/separate should return 400 when separationType is invalid")
        void separate_shouldReturn400_whenSeparationTypeIsInvalid() throws Exception {
            // Given
            String requestBody = "{\"numberList\":[1,2,3],\"separationType\":\"invalid\"}";

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ contains 'Separation type must represent')]").exists());
        }
    }

    // ========================================
    // 3. Strict Typing Tests (400 Bad Request)
    // ========================================

    @Nested
    @DisplayName("Strict Typing Tests")
    class StrictTypingTests {

        @Test
        @DisplayName("POST /api/array/sum should return 400 when numbers is string instead of array")
        void sum_shouldReturn400_whenNumbersIsString() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":\"123\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Malformed JSON"));
        }

        @Test
        @DisplayName("POST /api/array/sum should return 400 when numbers is number instead of array")
        void sum_shouldReturn400_whenNumbersIsNumber() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":123}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Malformed JSON"));
        }

        @Test
        @DisplayName("POST /api/array/sum should return 400 when numbers has mixed types")
        void sum_shouldReturn400_whenNumbersHasMixedTypes() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,\"a\",3]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Malformed JSON"));
        }

        @Test
        @DisplayName("POST /api/array/sort should return 400 when orderType is number instead of string")
        void sort_shouldReturn400_whenOrderTypeIsNumber() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3],\"orderType\":123}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"));
        }

        @Test
        @DisplayName("POST /api/array/sort should return 400 when orderType is boolean instead of string")
        void sort_shouldReturn400_whenOrderTypeIsBoolean() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3],\"orderType\":true}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"));
        }
    }

    // ========================================
    // 4. Malformed JSON Tests (400 Bad Request)
    // ========================================

    @Nested
    @DisplayName("Malformed JSON Tests")
    class MalformedJsonTests {

        @Test
        @DisplayName("POST /api/array/sum should return 400 for missing closing brace")
        void sum_shouldReturn400_whenJsonHasMissingClosingBrace() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3]"; // Missing closing brace

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Malformed JSON"))
                    .andExpect(jsonPath("$.message").value("The request body contains invalid JSON"));
        }

        @Test
        @DisplayName("POST /api/array/sort should return 400 for trailing comma")
        void sort_shouldReturn400_whenJsonHasTrailingComma() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3],\"orderType\":\"asc\",}"; // Trailing comma

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Malformed JSON"));
        }

        @Test
        @DisplayName("POST /api/array/separate should return 400 for invalid token")
        void separate_shouldReturn400_whenJsonHasInvalidToken() throws Exception {
            // Given
            String requestBody = "{\"numberList\":[1,2,3],\"separationType\":invalid}"; // Missing quotes

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Malformed JSON"));
        }
    }

    // ========================================
    // 5. Service Exception Tests
    // ========================================

    @Nested
    @DisplayName("Service Exception Tests")
    class ServiceExceptionTests {

        @Test
        @DisplayName("POST /api/array/sum should handle extremely large numbers")
        void sum_shouldHandleLargeNumbers() throws Exception {
            // Given - using very large numbers that might cause overflow
            String requestBody = "{\"numbersList\":[999999999,999999999,999999999]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk()) // May pass or cause service exception
                    .andExpect(jsonPath("$.sum").exists());
        }

        @Test
        @DisplayName("POST /api/array/sort should handle invalid mode that might cause service exception")
        void sort_shouldHandleInvalidMode() throws Exception {
            // Given - using edge case that might cause service to throw
            String requestBody = "{\"numbersList\":[1,2,3],\"orderType\":\"des\"}"; // Full word instead of short form

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest()) // Validation should catch this first
                    .andExpect(jsonPath("$.error").value("Validation Error"));
        }

        @Test
        @DisplayName("POST /api/array/separate should handle edge case separation")
        void separate_shouldHandleEdgeCaseSeparation() throws Exception {
            // Given - using edge case that might stress the service
            String requestBody = "{\"numberList\":[0,-0,0.0,-0.0],\"separationType\":\"sign\"}";

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk()) // Should handle edge cases gracefully
                    .andExpect(jsonPath("$.firstGroup").exists())
                    .andExpect(jsonPath("$.secondGroup").exists())
                    .andExpect(jsonPath("$.separationType").value("sign"));
        }
    }

    // ========================================
    // 6. JSON Contract Tests
    // ========================================

    @Nested
    @DisplayName("JSON Contract Tests")
    class JsonContractTests {

        @Test
        @DisplayName("POST /api/array/sum should return correct JSON structure")
        void sum_shouldReturnCorrectJsonStructure() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isMap())
                    .andExpect(jsonPath("$.sum").exists())
                    .andExpect(jsonPath("$.sum").isNumber())
                    .andExpect(jsonPath("$.*", hasSize(1))) // Only one field: sum
                    .andExpect(jsonPath("$.sum").value(6.0));
        }

        @Test
        @DisplayName("POST /api/array/sort should return correct JSON structure for integers")
        void sort_shouldReturnCorrectJsonStructure_forIntegers() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[3,1,2],\"orderType\":\"ascending\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isMap())
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", hasSize(3)))
                    .andExpect(jsonPath("$.orderType").isString())
                    .andExpect(jsonPath("$.*", hasSize(2))) // Exactly two fields: sorted, orderType
                    .andExpect(jsonPath("$.orderType").value("ascending"));
        }

        @Test
        @DisplayName("POST /api/array/separate should return correct JSON structure")
        void separate_shouldReturnCorrectJsonStructure() throws Exception {
            // Given
            String requestBody = "{\"numberList\":[1,2,3,4],\"separationType\":\"parity\"}";

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isMap())
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.separationType").isString())
                    .andExpect(jsonPath("$.*", hasSize(3))) // Exactly three fields
                    .andExpect(jsonPath("$.separationType").value("parity"));
        }
    }

    // ========================================
    // 7. Method Not Allowed Tests (405)
    // ========================================

    @Nested
    @DisplayName("Method Not Allowed Tests")
    class MethodNotAllowedTests {

        @Test
        @DisplayName("GET /api/array/sum should return 405 Method Not Allowed")
        void sum_shouldReturn405_whenMethodIsGet() throws Exception {
            // When & Then
            mockMvc.perform(get("/api/array/sum"))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        @DisplayName("PUT /api/array/sort should return 405 Method Not Allowed")
        void sort_shouldReturn405_whenMethodIsPut() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3],\"orderType\":\"asc\"}";

            // When & Then
            mockMvc.perform(put("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        @DisplayName("DELETE /api/array/separate should return 405 Method Not Allowed")
        void separate_shouldReturn405_whenMethodIsDelete() throws Exception {
            // When & Then
            mockMvc.perform(delete("/api/array/separate"))
                    .andExpect(status().isMethodNotAllowed());
        }
    }

    // ========================================
    // 8. Routing Tests
    // ========================================

    @Nested
    @DisplayName("Routing Tests")
    class RoutingTests {

        @Test
        @DisplayName("POST /api/array/sum should route to correct method")
        void sum_routing_shouldWork() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sum").exists());
        }

        @Test
        @DisplayName("POST /api/array/sort should route to correct method")
        void sort_routing_shouldWork() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3],\"orderType\":\"ascending\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sorted").exists())
                    .andExpect(jsonPath("$.orderType").exists());
        }

        @Test
        @DisplayName("POST /api/array/separate should route to correct method")
        void separate_routing_shouldWork() throws Exception {
            // Given
            String requestBody = "{\"numberList\":[1,2,3],\"separationType\":\"parity\"}";

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstGroup").exists())
                    .andExpect(jsonPath("$.secondGroup").exists())
                    .andExpect(jsonPath("$.separationType").exists());
        }

        @Test
        @DisplayName("POST /api/array/wrongpath should return 404 Not Found")
        void shouldReturn404_whenPathIsWrong() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2,3]}";

            // When & Then
            mockMvc.perform(post("/api/array/wrongpath")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isNotFound());
        }
    }

    // ========================================
    // 9. Edge-Case Input Tests
    // ========================================

    @Nested
    @DisplayName("Edge-Case Input Tests")
    class EdgeCaseInputTests {

        @Test
        @DisplayName("POST /api/array/sum should handle very large arrays")
        void sum_shouldHandleLargeArrays() throws Exception {
            // Given - create array with 1000 elements (max allowed)
            StringBuilder jsonBuilder = new StringBuilder("{\"numbersList\":[");
            for (int i = 0; i < 1000; i++) {
                if (i > 0) jsonBuilder.append(",");
                jsonBuilder.append(i);
            }
            jsonBuilder.append("]}");
            String requestBody = jsonBuilder.toString();

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sum").exists())
                    .andExpect(jsonPath("$.sum").isNumber());
        }

        @Test
        @DisplayName("POST /api/array/sum should handle arrays with negative numbers")
        void sum_shouldHandleNegativeNumbers() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[-5,-10,-3]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sum").value(-18.0));
        }

        @Test
        @DisplayName("POST /api/array/sort should handle arrays with duplicates")
        void sort_shouldHandleDuplicates() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[3,1,2,1,3,2],\"orderType\":\"ascending\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", hasSize(6)))
                    .andExpect(jsonPath("$.sorted").value(contains(1, 1, 2, 2, 3, 3)))
                    .andExpect(jsonPath("$.orderType").value("ascending"));
        }


        @Test
        @DisplayName("POST /api/array/sort should handle arrays with only one element")
        void sort_shouldHandleSingleElement() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[42],\"orderType\":\"descending\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", hasSize(1)))
                    .andExpect(jsonPath("$.sorted[0]").value(42))
                    .andExpect(jsonPath("$.orderType").value("descending"));
        }

        @Test
        @DisplayName("POST /api/array/separate should handle arrays with only even numbers")
        void separate_shouldHandleOnlyEvenNumbers() throws Exception {
            // Given
            String requestBody = "{\"numberList\":[2,4,6,8],\"separationType\":\"parity\"}";

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.firstGroup", containsInAnyOrder(2, 4, 6, 8)))
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup", hasSize(0))) // No odd numbers
                    .andExpect(jsonPath("$.separationType").value("parity"));
        }

        @Test
        @DisplayName("POST /api/array/separate should handle arrays with only odd numbers")
        void separate_shouldHandleOnlyOddNumbers() throws Exception {
            // Given
            String requestBody = "{\"numberList\":[1,3,5,7],\"separationType\":\"parity\"}";

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.firstGroup", hasSize(0))) // No even numbers
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup", containsInAnyOrder(1, 3, 5, 7)))
                    .andExpect(jsonPath("$.separationType").value("parity"));
        }

        @Test
        @DisplayName("POST /api/array/sum should handle mixed integer and double arrays")
        void sum_shouldHandleMixedNumberTypes() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,2.5,3,4.5]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sum").value(11.0));
        }

        @Test
        @DisplayName("POST /api/array/sort should handle double arrays")
        void sort_shouldHandleDoubleArrays() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[3.5,1.2,2.8],\"orderType\":\"d\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", hasSize(3)))
                    .andExpect(jsonPath("$.orderType").value("d"));
        }

        @Test
        @DisplayName("POST /api/array/sort should handle single element descending")
        void sort_shouldHandleSingleElementDescending() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[42],\"orderType\":\"descending\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", hasSize(1)))
                    .andExpect(jsonPath("$.sorted[0]").value(42))
                    .andExpect(jsonPath("$.orderType").value("descending"));
        }

        @Test
        @DisplayName("POST /api/array/sum should handle arrays with only even numbers")
        void sum_shouldHandleOnlyEvenNumbers() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[2,4,6,8]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sum").value(20.0))
                    .andExpect(jsonPath("$.length()").value(1));
        }

        @Test
        @DisplayName("POST /api/array/sort should handle arrays with only even numbers")
        void sort_shouldHandleOnlyEvenNumbers() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[8,4,6,2],\"orderType\":\"ascending\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", hasSize(4)))
                    .andExpect(jsonPath("$.sorted", contains(2, 4, 6, 8)))
                    .andExpect(jsonPath("$.orderType").value("ascending"));
        }

        @Test
        @DisplayName("POST /api/array/separate should handle arrays with all even numbers")
        void separate_shouldHandleAllEvenNumbers() throws Exception {
            // Given
            String requestBody = "{\"numberList\":[2,4,6,8],\"separationType\":\"parity\"}";

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.firstGroup", hasSize(4)))
                    .andExpect(jsonPath("$.firstGroup", containsInAnyOrder(2, 4, 6, 8)))
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup", hasSize(0))) // Empty odd group
                    .andExpect(jsonPath("$.separationType").value("parity"));
        }

        @Test
        @DisplayName("POST /api/array/sum should handle arrays with only odd numbers")
        void sum_shouldHandleOnlyOddNumbers() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[1,3,5,7]}";

            // When & Then
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sum").value(16.0))
                    .andExpect(jsonPath("$.length()").value(1));
        }

        @Test
        @DisplayName("POST /api/array/separate should handle arrays with all odd numbers")
        void separate_shouldHandleAllOddNumbers() throws Exception {
            // Given
            String requestBody = "{\"numberList\":[1,3,5,7],\"separationType\":\"parity\"}";

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.firstGroup", hasSize(0))) // Empty even group
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup", hasSize(4)))
                    .andExpect(jsonPath("$.secondGroup", containsInAnyOrder(1, 3, 5, 7)))
                    .andExpect(jsonPath("$.separationType").value("parity"));
        }

        @Test
        @DisplayName("POST /api/array/sort should handle arrays with all negative numbers")
        void sort_shouldHandleAllNegativeNumbers() throws Exception {
            // Given
            String requestBody = "{\"numbersList\":[-4,-1,-3,-2],\"orderType\":\"ascending\"}";

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", hasSize(4)))
                    .andExpect(jsonPath("$.sorted", contains(-4, -3, -2, -1)))
                    .andExpect(jsonPath("$.orderType").value("ascending"));
        }

        @Test
        @DisplayName("POST /api/array/separate should handle arrays with all negative numbers")
        void separate_shouldHandleAllNegativeNumbers() throws Exception {
            // Given
            String requestBody = "{\"numberList\":[-1,-2,-3,-4],\"separationType\":\"sign\"}";

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.firstGroup", hasSize(0))) // Empty positive group
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup", hasSize(4)))
                    .andExpect(jsonPath("$.secondGroup", containsInAnyOrder(-1, -2, -3, -4)))
                    .andExpect(jsonPath("$.separationType").value("sign"));
        }

        @Test
        @DisplayName("POST /api/array/sort should handle very large arrays")
        void sort_shouldHandleLargeArrays() throws Exception {
            // Given - create array with 1000 elements in reverse order for sorting test
            StringBuilder jsonBuilder = new StringBuilder("{\"numbersList\":[");
            for (int i = 0; i < 1000; i++) {
                if (i > 0) jsonBuilder.append(",");
                jsonBuilder.append(1000 - i); // Reverse order: 1000, 999, 998, ...
            }
            jsonBuilder.append("],\"orderType\":\"ascending\"}");
            String requestBody = jsonBuilder.toString();

            // When & Then
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", hasSize(1000)))
                    .andExpect(jsonPath("$.orderType").value("ascending"));
        }

        @Test
        @DisplayName("POST /api/array/separate should handle very large arrays")
        void separate_shouldHandleLargeArrays() throws Exception {
            // Given - create array with 1000 sequential elements
            StringBuilder jsonBuilder = new StringBuilder("{\"numberList\":[");
            for (int i = 0; i < 1000; i++) {
                if (i > 0) jsonBuilder.append(",");
                jsonBuilder.append(i);
            }
            jsonBuilder.append("],\"separationType\":\"parity\"}");
            String requestBody = jsonBuilder.toString();

            // When & Then
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.firstGroup", hasSize(500))) // 500 even numbers (0, 2, 4, ...)
                    .andExpect(jsonPath("$.secondGroup", hasSize(500))) // 500 odd numbers (1, 3, 5, ...)
                    .andExpect(jsonPath("$.separationType").value("parity"));
        }
    }
}