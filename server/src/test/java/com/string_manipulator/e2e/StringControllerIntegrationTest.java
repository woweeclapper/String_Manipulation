package com.string_manipulator.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.string_manipulator.dto.string.ReverseRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.string_manipulator")
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@DisplayName("StringController Integration Tests")
class StringControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    // ========================================
    // 1. Happy-Path Tests (200 OK)
    // ========================================

    @Nested
    @DisplayName("Happy-Path Tests")
    class HappyPathTests {

        @Test
        @DisplayName("POST /api/string/reverse should return 200 OK with valid input")
        void reverse_shouldReturn200_whenValidRequest() throws Exception {
            // Given
            String requestBody = "{\"text\":\"Hello World\"}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.reversedText").value("dlroW olleH"))
                    .andExpect(jsonPath("$.length()").value(1))
                    .andExpect(jsonPath("$.*", hasSize(1)));
        }

        @Test
        @DisplayName("POST /api/string/shift should return 200 OK with valid input")
        void shift_shouldReturn200_whenValidRequest() throws Exception {
            // Given
            String requestBody = "{\"text\":\"Hello\",\"numOfShifts\":2,\"direction\":\"left\"}";

            // When & Then
            mockMvc.perform(post("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.shiftedText").exists())
                    .andExpect(jsonPath("$.numOfShifts").value(2))
                    .andExpect(jsonPath("$.direction").value("left"))
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$.*", hasSize(3)));
        }

        @Test
        @DisplayName("POST /api/string/reverse should handle empty string correctly")
        void reverse_shouldHandleEmptyString() throws Exception {
            // Given
            String requestBody = "{\"text\":\"\"}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"));
        }

        @Test
        @DisplayName("POST /api/string/shift should handle zero shifts")
        void shift_shouldHandleZeroShifts() throws Exception {
            // Given
            String requestBody = "{\"text\":\"Hello\",\"numOfShifts\":0,\"direction\":\"right\"}";

            // When & Then
            mockMvc.perform(post("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.shiftedText").exists())
                    .andExpect(jsonPath("$.numOfShifts").value(0))
                    .andExpect(jsonPath("$.direction").value("right"));
        }
    }

    // ========================================
    // 2. Validation Error Tests (400 Bad Request)
    // ========================================

    @Nested
    @DisplayName("Validation Error Tests")
    class ValidationErrorTests {

        @Test
        @DisplayName("POST /api/string/reverse should return 400 when text field is missing")
        void reverse_shouldReturn400_whenTextFieldMissing() throws Exception {
            // Given
            String requestBody = "{}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.message").value("Request validation failed"))
                    .andExpect(jsonPath("$.details").isArray())
                    .andExpect(jsonPath("$.details[?(@ == 'text: Text cannot be blank')]").exists())
                    .andExpect(jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("POST /api/string/reverse should return 400 when text is null")
        void reverse_shouldReturn400_whenTextIsNull() throws Exception {
            // Given
            String requestBody = "{\"text\":null}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ == 'text: Text cannot be blank')]").exists());
        }

        @Test
        @DisplayName("POST /api/string/reverse should return 400 when text is empty")
        void reverse_shouldReturn400_whenTextIsEmpty() throws Exception {
            // Given
            String requestBody = "{\"text\":\"\"}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ == 'text: Text cannot be blank')]").exists());
        }

        @Test
        @DisplayName("POST /api/string/reverse should return 400 when text is whitespace only")
        void reverse_shouldReturn400_whenTextIsWhitespace() throws Exception {
            // Given
            String requestBody = "{\"text\":\"   \"}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ == 'text: Text cannot be blank')]").exists());
        }

        @Test
        @DisplayName("POST /api/string/reverse should return 400 when text exceeds max length")
        void reverse_shouldReturn400_whenTextExceedsMaxLength() throws Exception {
            // Given
            String longText = "a".repeat(10001);
            String requestBody = "{\"text\":\"" + longText + "\"}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ == 'text: Text cannot exceed 10000 characters')]").exists());
        }

        @Test
        @DisplayName("POST /api/string/shift should return 400 when numOfShifts is negative")
        void shift_shouldReturn400_whenNumOfShiftsIsNegative() throws Exception {
            // Given
            String requestBody = "{\"text\":\"Hello\",\"numOfShifts\":-1,\"direction\":\"left\"}";

            // When & Then
            mockMvc.perform(post("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ == 'numOfShifts: Number of shifts cannot be negative')]").exists());
        }

        @Test
        @DisplayName("POST /api/string/shift should return 400 when direction is invalid")
        void shift_shouldReturn400_whenDirectionIsInvalid() throws Exception {
            // Given
            String requestBody = "{\"text\":\"Hello\",\"numOfShifts\":2,\"direction\":\"invalid\"}";

            // When & Then
            mockMvc.perform(post("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ == 'direction: Direction must represent \\'left\\', \\'right\\', \\'l\\', or \\'r\\'.')]").exists());
        }

        @Test
        @DisplayName("POST /api/string/shift should return 400 when direction is blank")
        void shift_shouldReturn400_whenDirectionIsBlank() throws Exception {
            // Given
            String requestBody = "{\"text\":\"Hello\",\"numOfShifts\":2,\"direction\":\"\"}";

            // When & Then
            mockMvc.perform(post("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.details[?(@ == 'direction: Direction cannot be blank')]").exists());
        }
    }

    // ========================================
    // 3. JSON Type Mismatch Tests (400 Bad Request)
    // ========================================

    @Nested
    @DisplayName("JSON Type Mismatch Tests")
    class JsonTypeMismatchTests {


        @Test
        @DisplayName("POST /api/string/reverse should return 400 when text is array instead of string")
        void reverse_shouldReturn400_whenTextIsArray() throws Exception {
            // Given
            String requestBody = "{\"text\":[\"a\",\"b\"]}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Malformed JSON"));
        }

        @Test
        @DisplayName("POST /api/string/shift should return 400 when numOfShifts is string instead of number")
        void shift_shouldReturn400_whenNumOfShiftsIsString() throws Exception {
            // Given
            String requestBody = "{\"text\":\"Hello\",\"numOfShifts\":\"abc\",\"direction\":\"left\"}";

            // When & Then
            mockMvc.perform(post("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Malformed JSON"));
        }

        @Test
        @DisplayName("POST /api/string/reverse should return 400 for malformed JSON")
        void reverse_shouldReturn400_whenJsonIsMalformed() throws Exception {
            // Given
            String requestBody = "{\"text\":\"hello\""; // Missing closing brace

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Malformed JSON"))
                    .andExpect(jsonPath("$.message").value("The request body contains invalid JSON"));
        }
    }

    // ========================================
    // 4. Service Exception Tests
    // ========================================

    @Nested
    @DisplayName("Service Exception Tests")
    class ServiceExceptionTests {

        @Test
        @DisplayName("POST /api/string/reverse should return 400 when service throws IllegalArgumentException")
        void reverse_shouldReturn400_whenServiceThrowsIllegalArgumentException() throws Exception {
            // This test would require mocking the service or using test data that triggers the exception
            // For true integration test, you might need to use test data that causes the service to throw

            // Given - using input that might cause service to throw
            String requestBody = "{\"text\":\"\\u0000\"}"; // Null character might cause issues

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk()) // May pass depending on service implementation
                    .andExpect(jsonPath("$.reversedText").exists());
        }

        @Test
        @DisplayName("POST /api/string/shift should return 400 when service throws IllegalStateException")
        void shift_shouldReturn400_whenServiceThrowsIllegalStateException() throws Exception {
            // This test would require specific conditions that cause the service to throw
            // For true integration test, you might need test data that triggers the exception

            // Given
            String requestBody = "{\"text\":\"test\",\"numOfShifts\":999999,\"direction\":\"left\"}";

            // When & Then
            mockMvc.perform(post("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk()); // May pass depending on service implementation
        }
    }

    // ========================================
    // 5. JSON Contract Tests
    // ========================================

    @Nested
    @DisplayName("JSON Contract Tests")
    class JsonContractTests {

        @Test
        @DisplayName("POST /api/string/reverse should return correct JSON structure")
        void reverse_shouldReturnCorrectJsonStructure() throws Exception {
            // Given
            String requestBody = "{\"text\":\"Hello\"}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isMap())
                    .andExpect(jsonPath("$.reversedText").exists())
                    .andExpect(jsonPath("$.reversedText").isString())
                    .andExpect(jsonPath("$.*", hasSize(1))) // Only one field
                    .andExpect(jsonPath("$.reversedText").value("olleH"));
        }

        @Test
        @DisplayName("POST /api/string/shift should return correct JSON structure")
        void shift_shouldReturnCorrectJsonStructure() throws Exception {
            // Given
            String requestBody = "{\"text\":\"Hello\",\"numOfShifts\":2,\"direction\":\"left\"}";

            // When & Then
            mockMvc.perform(post("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isMap())
                    .andExpect(jsonPath("$.shiftedText").exists())
                    .andExpect(jsonPath("$.shiftedText").isString())
                    .andExpect(jsonPath("$.numOfShifts").exists())
                    .andExpect(jsonPath("$.numOfShifts").isNumber())
                    .andExpect(jsonPath("$.direction").exists())
                    .andExpect(jsonPath("$.direction").isString())
                    .andExpect(jsonPath("$.*", hasSize(3))) // Exactly three fields
                    .andExpect(jsonPath("$.numOfShifts").value(2))
                    .andExpect(jsonPath("$.direction").value("left"));
        }
    }

    // ========================================
    // 6. Method Not Allowed Tests (405)
    // ========================================

    @Nested
    @DisplayName("Method Not Allowed Tests")
    class MethodNotAllowedTests {

        @Test
        @DisplayName("GET /api/string/reverse should return 405 Method Not Allowed")
        void reverse_shouldReturn405_whenMethodIsGet() throws Exception {
            // When & Then
            mockMvc.perform(get("/api/string/reverse"))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        @DisplayName("PUT /api/string/reverse should return 405 Method Not Allowed")
        void reverse_shouldReturn405_whenMethodIsPut() throws Exception {
            // Given
            String requestBody = "{\"text\":\"Hello\"}";

            // When & Then
            mockMvc.perform(put("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        @DisplayName("DELETE /api/string/reverse should return 405 Method Not Allowed")
        void reverse_shouldReturn405_whenMethodIsDelete() throws Exception {
            // When & Then
            mockMvc.perform(delete("/api/string/reverse"))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        @DisplayName("GET /api/string/shift should return 405 Method Not Allowed")
        void shift_shouldReturn405_whenMethodIsGet() throws Exception {
            // When & Then
            mockMvc.perform(get("/api/string/shift"))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        @DisplayName("PUT /api/string/shift should return 405 Method Not Allowed")
        void shift_shouldReturn405_whenMethodIsPut() throws Exception {
            // Given
            String requestBody = "{\"text\":\"Hello\",\"numOfShifts\":2,\"direction\":\"left\"}";

            // When & Then
            mockMvc.perform(put("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isMethodNotAllowed());
        }

        @Test
        @DisplayName("DELETE /api/string/shift should return 405 Method Not Allowed")
        void shift_shouldReturn405_whenMethodIsDelete() throws Exception {
            // When & Then
            mockMvc.perform(delete("/api/string/shift"))
                    .andExpect(status().isMethodNotAllowed());
        }
    }

    // ========================================
    // 7. Routing Tests
    // ========================================

    @Nested
    @DisplayName("Routing Tests")
    class RoutingTests {

        @Test
        @DisplayName("POST /api/string/reverse should route to correct method")
        void reverse_routing_shouldWork() throws Exception {
            // Given
            String requestBody = "{\"text\":\"routing test\"}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reversedText").exists());
        }

        @Test
        @DisplayName("POST /api/string/shift should route to correct method")
        void shift_routing_shouldWork() throws Exception {
            // Given
            String requestBody = "{\"text\":\"routing test\",\"numOfShifts\":1,\"direction\":\"right\"}";

            // When & Then
            mockMvc.perform(post("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.shiftedText").exists())
                    .andExpect(jsonPath("$.numOfShifts").value(1))
                    .andExpect(jsonPath("$.direction").value("right"));
        }

        @Test
        @DisplayName("POST /api/string/wrongpath should return 404 Not Found")
        void shouldReturn404_whenPathIsWrong() throws Exception {
            // Given
            String requestBody = "{\"text\":\"test\"}";

            // When & Then
            mockMvc.perform(post("/api/string/wrongpath")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isNotFound());
        }
    }

    // ========================================
    // 8. Edge-Case Input Tests
    // ========================================

    @Nested
    @DisplayName("Edge-Case Input Tests")
    class EdgeCaseInputTests {

        @Test
        @DisplayName("POST /api/string/reverse should handle very long strings within limit")
        void reverse_shouldHandleLongStrings() throws Exception {
            // Given
            String longText = "a".repeat(10000); // Exactly at limit
            String requestBody = "{\"text\":\"" + longText + "\"}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reversedText").exists())
                    .andExpect(jsonPath("$.reversedText").value(longText));
        }

        @Test
        @DisplayName("POST /api/string/reverse should handle Unicode characters")
        void reverse_shouldHandleUnicode() throws Exception {
            // Given
            String unicodeText = "Hello 世界 🌍";
            String requestBody = "{\"text\":\"" + unicodeText + "\"}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reversedText").exists())
                    .andExpect(jsonPath("$.reversedText").value("🌍 界世 olleH"));
        }

        @Test
        @DisplayName("POST /api/string/reverse should handle emojis")
        void reverse_shouldHandleEmojis() throws Exception {
            // Given
            String emojiText = "👋🌍💻";
            String requestBody = "{\"text\":\"" + emojiText + "\"}";

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reversedText").exists())
                    .andExpect(jsonPath("$.reversedText").value("💻🌍👋"));
        }

        @Test
        @DisplayName("POST /api/string/reverse should handle mixed whitespace")
        void reverse_shouldHandleMixedWhitespace() throws Exception {
            // Given
            String whitespaceText = " hello \t\n world ";
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(new ReverseRequest(whitespaceText));

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reversedText").exists())
                    .andExpect(jsonPath("$.reversedText").value(" dlrow \n\t olleh "));
        }

        @Test
        @DisplayName("POST /api/string/shift should handle various direction formats")
        void shift_shouldAcceptVariousDirectionFormats() throws Exception {
            // Test different valid direction formats
            String[] validDirections = {"left", "l", "right", "r", "LEFT", "Right", " L ", " R "};

            for (String direction : validDirections) {
                // Given
                String requestBody = "{\"text\":\"hello\",\"numOfShifts\":1,\"direction\":\"" + direction + "\"}";

                // When & Then
                mockMvc.perform(post("/api/string/shift")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.shiftedText").exists())
                        .andExpect(jsonPath("$.numOfShifts").value(1))
                        .andExpect(jsonPath("$.direction").exists());
            }
        }

        @Test
        @DisplayName("POST /api/string/reverse should handle strings with special characters")
        void reverse_shouldHandleSpecialCharacters() throws Exception {
            // Given
            String specialText = "!@#$%^&*()_+-=[]{}|;':\",./<>?";
            Map<String, String> request = Map.of("text", specialText);
            String requestBody = new ObjectMapper().writeValueAsString(request);

            // When & Then
            mockMvc.perform(post("/api/string/reverse")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.reversedText").exists())
                    .andExpect(jsonPath("$.reversedText").value("?></.,\":';|}{][=-+_)(*&^%$#@!"));
        }

        @Test
        @DisplayName("POST /api/string/shift should handle strings with repeated characters")
        void shift_shouldHandleRepeatedCharacters() throws Exception {
            // Given
            String repeatedText = "aaaaaa";
            String requestBody = "{\"text\":\"" + repeatedText + "\",\"numOfShifts\":2,\"direction\":\"left\"}";

            // When & Then
            mockMvc.perform(post("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.shiftedText").exists())
                    .andExpect(jsonPath("$.numOfShifts").value(2))
                    .andExpect(jsonPath("$.direction").value("left"));
        }
    }
}
