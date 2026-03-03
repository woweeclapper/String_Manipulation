package com.string_manipulator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.string_manipulator.dto.string.ReverseRequest;
import com.string_manipulator.dto.string.ReverseResponse;
import com.string_manipulator.dto.string.ShiftRequest;
import com.string_manipulator.dto.string.ShiftResponse;
import com.string_manipulator.service.StringService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StringController.class)
@DisplayName("StringController Tests")
class StringControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StringService stringService;

    // ========================================
    // 1. Happy-Path Tests
    // ========================================

    private static Stream<Arguments> malformedJsonRequestProvider() {
        return Stream.of(
                Arguments.of("/api/string/reverse", "{\"text\":[]}", "text is array"),
                Arguments.of("/api/string/reverse", "{\"text\":{}}", "text is object"),
                Arguments.of("/api/string/shift", "{\"text\":\"hello\",\"numOfShifts\":\"two\",\"direction\":\"left\"}", "numOfShifts is not integer")
        );
    }

    @Test
    @DisplayName("POST /api/string/reverse should return 200 OK with valid input")
    void reverse_shouldReturn200_whenValidRequest() throws Exception {

        //Given
        ReverseRequest request = new ReverseRequest("hello");
        ReverseResponse expectedResponse = new ReverseResponse("olleh");

        when(stringService.reverseString("hello")).thenReturn("olleh");

        //When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reversedText").value("olleh"))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        //Verify service called exactly once with correct request
        verify(stringService, times(1)).reverseString("hello");
        verifyNoMoreInteractions(stringService);
    }

    // ========================================
    // 2. DTO Validation Tests
    // ========================================

    @Test
    @DisplayName("POST /api/string/shift should return 200 OK with valid input")
    void shift_shouldReturn200_whenValidRequest() throws Exception {
        // Given
        ShiftRequest request = new ShiftRequest("hello", 2, "left");
        ShiftResponse expectedResponse = new ShiftResponse("llohe", 2, "left");

        when(stringService.shiftString("hello", 2, "left")).thenReturn("llohe");

        // When & Then
        mockMvc.perform(post("/api/string/shift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.shiftedText").value("llohe"))
                .andExpect(jsonPath("$.numOfShifts").value(2))
                .andExpect(jsonPath("$.direction").value("left"))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));// Only expected fields

        // Verify service called exactly once with correct parameters
        verify(stringService, times(1)).shiftString("hello", 2, "left");
        verifyNoMoreInteractions(stringService);
    }

    @Test
    @DisplayName("POST /api/string/reverse should return 400 when text is blank")
    void reverse_shouldReturn400_whenTextIsBlank() throws Exception {
        // Given
        ReverseRequest request = new ReverseRequest("");

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details[?(@ == 'text: Text cannot be blank')]").exists())
                .andExpect(jsonPath("$.timestamp").exists());

        // Verify service not called
        verifyNoInteractions(stringService);
    }

    @Test
    @DisplayName("POST /api/string/reverse should return 400 when text is whitespace only")
    void reverse_shouldReturn400_whenTextIsWhitespace() throws Exception {
        // Given
        ReverseRequest request = new ReverseRequest("   ");

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.details[?(@ == 'text: Text cannot be blank')]").exists());

        verifyNoInteractions(stringService);
    }

    @Test
    @DisplayName("POST /api/string/reverse should return 400 when text field is missing")
    void reverse_shouldReturn400_whenTextFieldMissing() throws Exception {
        // Given
        String json = "{}";

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.details[?(@ == 'text: Text cannot be blank')]").exists());

        verifyNoInteractions(stringService);
    }

    @Test
    @DisplayName("POST /api/string/reverse should return 400 when text is null")
    void reverse_shouldReturn400_whenTextIsNull() throws Exception {
        // Given
        String json = "{\"text\":null}";

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.details[?(@ == 'text: Text cannot be blank')]").exists());

        verifyNoInteractions(stringService);
    }

    @Test
    @DisplayName("POST /api/string/reverse should return 400 when text exceeds max length")
    void reverse_shouldReturn400_whenTextExceedsMaxLength() throws Exception {
        // Given
        String longText = "a".repeat(10001);
        ReverseRequest request = new ReverseRequest(longText);

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.details[?(@ == 'text: Text cannot exceed 10000 characters')]").exists());

        verifyNoInteractions(stringService);
    }

    @Test
    @DisplayName("POST /api/string/shift should return 400 when numOfShifts is negative")
    void shift_shouldReturn400_whenNumOfShiftsIsNegative() throws Exception {
        // Given
        ShiftRequest request = new ShiftRequest("hello", -1, "left");

        // When & Then
        mockMvc.perform(post("/api/string/shift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.details[?(@ == 'numOfShifts: Number of shifts cannot be negative')]").exists());

        verifyNoInteractions(stringService);
    }

    // ========================================
    // 3. JSON Type Mismatch Tests
    // ========================================

    @Test
    @DisplayName("POST /api/string/shift should return 400 when direction is invalid")
    void shift_shouldReturn400_whenDirectionIsInvalid() throws Exception {
        // Given
        ShiftRequest request = new ShiftRequest("hello", 2, "invalid");

        // When & Then
        mockMvc.perform(post("/api/string/shift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.details[?(@ == 'direction: Direction must represent \\'left\\', \\'right\\', \\'l\\', or \\'r\\'.')]").exists());

        verifyNoInteractions(stringService);
    }

    @Test
    @DisplayName("POST /api/string/reverse should accept numeric JSON values as strings")
    void reverse_shouldAcceptNumericValues() throws Exception {
        String json = "{\"text\":123}";

        when(stringService.reverseString("123")).thenReturn("321");

        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reversedText").value("321"));

        verify(stringService).reverseString("123");
    }

    @ParameterizedTest
    @MethodSource("malformedJsonRequestProvider")
    @DisplayName("Should return 400 for malformed JSON requests")
    void shouldReturn400_whenJsonIsMalformed(String endpoint, String json) throws Exception {
        // When & Then
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Malformed JSON"));

        verifyNoInteractions(stringService);
    }

    @Test
    @DisplayName("POST /api/string/reverse should return 400 for malformed JSON")
    void reverse_shouldReturn400_whenJsonIsMalformed() throws Exception {
        // Given
        String malformedJson = "{\"text\":\"hello\""; // Missing closing brace

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Malformed JSON"))
                .andExpect(jsonPath("$.message").value("The request body contains invalid JSON"));

        verifyNoInteractions(stringService);
    }

    // ========================================
    // 4. Service Exception Mapping Tests
    // ========================================

    @Test
    @DisplayName("POST /api/string/reverse should return 400 when service throws IllegalArgumentException")
    void reverse_shouldReturn400_whenServiceThrowsIllegalArgumentException() throws Exception {
        // Given
        ReverseRequest request = new ReverseRequest("hello");
        when(stringService.reverseString("hello"))
                .thenThrow(new IllegalArgumentException("String cannot be null"));

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Invalid Input"))
                .andExpect(jsonPath("$.message").value("Service validation failed"))
                .andExpect(jsonPath("$.details[0]").value("String cannot be null"));

        verify(stringService, times(1)).reverseString("hello");
    }

    @Test
    @DisplayName("POST /api/string/shift should return 400 when service throws IllegalArgumentException")
    void shift_shouldReturn400_whenServiceThrowsIllegalArgumentException() throws Exception {
        // Given
        ShiftRequest request = new ShiftRequest("hello", 2, "left");
        when(stringService.shiftString("hello", 2, "left"))
                .thenThrow(new IllegalArgumentException("Invalid direction"));

        // When & Then
        mockMvc.perform(post("/api/string/shift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Invalid Input"))
                .andExpect(jsonPath("$.message").value("Service validation failed"))
                .andExpect(jsonPath("$.details[0]").value("Invalid direction"));

        verify(stringService, times(1)).shiftString("hello", 2, "left");
    }

    @Test
    @DisplayName("POST /api/string/reverse should return 400 when service throws IllegalStateException")
    void reverse_shouldReturn400_whenServiceThrowsIllegalStateException() throws Exception {
        // Given
        ReverseRequest request = new ReverseRequest("hello");
        when(stringService.reverseString("hello"))
                .thenThrow(new IllegalStateException("Service in invalid state"));

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Invalid State"))
                .andExpect(jsonPath("$.message").value("Service state failed"))
                .andExpect(jsonPath("$.details[0]").value("Service in invalid state"));

        verify(stringService, times(1)).reverseString("hello");
    }

    // ========================================
    // 5. JSON Contract Tests
    // ========================================

    @Test
    @DisplayName("POST /api/string/reverse should return correct JSON structure")
    void reverse_shouldReturnCorrectJsonStructure() throws Exception {
        // Given
        ReverseRequest request = new ReverseRequest("hello");
        when(stringService.reverseString("hello")).thenReturn("olleh");

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1)) // Only one field
                .andExpect(jsonPath("$.reversedText").exists())
                .andExpect(jsonPath("$.reversedText").isString())
                .andExpect(jsonPath("$.reversedText").value("olleh"));

        verify(stringService, times(1)).reverseString("hello");
    }

    @Test
    @DisplayName("POST /api/string/shift should return correct JSON structure")
    void shift_shouldReturnCorrectJsonStructure() throws Exception {
        // Given
        ShiftRequest request = new ShiftRequest("hello", 2, "left");
        when(stringService.shiftString("hello", 2, "left")).thenReturn("llohe");

        // When & Then
        mockMvc.perform(post("/api/string/shift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3)) // Exactly three fields
                .andExpect(jsonPath("$.shiftedText").exists())
                .andExpect(jsonPath("$.shiftedText").isString())
                .andExpect(jsonPath("$.shiftedText").value("llohe"))
                .andExpect(jsonPath("$.numOfShifts").exists())
                .andExpect(jsonPath("$.numOfShifts").isNumber())
                .andExpect(jsonPath("$.numOfShifts").value(2))
                .andExpect(jsonPath("$.direction").exists())
                .andExpect(jsonPath("$.direction").isString())
                .andExpect(jsonPath("$.direction").value("left"));

        verify(stringService, times(1)).shiftString("hello", 2, "left");
    }

    // ========================================
    // 6. Routing Tests
    // ========================================

    @Test
    @DisplayName("POST /api/string/reverse should work with correct routing")
    void reverse_shouldWork_withCorrectRouting() throws Exception {
        // Given
        ReverseRequest request = new ReverseRequest("test");
        when(stringService.reverseString("test")).thenReturn("tset");

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(stringService, times(1)).reverseString("test");
    }

    @Test
    @DisplayName("POST /api/string/shift should work with correct routing")
    void shift_shouldWork_withCorrectRouting() throws Exception {
        // Given
        ShiftRequest request = new ShiftRequest("test", 1, "left");
        when(stringService.shiftString("test", 1, "left")).thenReturn("test");

        // When & Then
        mockMvc.perform(post("/api/string/shift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(stringService, times(1)).shiftString("test", 1, "left");
    }


    @Test
    @DisplayName("GET /api/string/reverse should return 405 Method Not Allowed")
    void reverse_shouldReturn405_whenMethodIsGet() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/string/reverse"))
                .andExpect(status().isMethodNotAllowed());

        verifyNoInteractions(stringService);
    }

    @Test
    @DisplayName("GET /api/string/shift should return 405 Method Not Allowed")
    void shift_shouldReturn405_whenMethodIsGet() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/string/shift"))
                .andExpect(status().isMethodNotAllowed());

        verifyNoInteractions(stringService);
    }

    @Test
    @DisplayName("POST /api/string/wrongpath should return 404 Not Found")
    void shouldReturn404_whenPathIsWrong() throws Exception {
        // Given
        ReverseRequest request = new ReverseRequest("test");

        // When & Then
        mockMvc.perform(post("/api/string/wrongpath")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verifyNoInteractions(stringService);
    }

    // ========================================
    // 7. Edge-Case Input Tests
    // ========================================

    @Test
    @DisplayName("POST /api/string/reverse should handle very long strings within limit")
    void reverse_shouldHandleLongStrings() throws Exception {
        // Given
        String longText = "a".repeat(10000); // Exactly at limit
        ReverseRequest request = new ReverseRequest(longText);
        String reversed = new StringBuilder(longText).reverse().toString();
        when(stringService.reverseString(longText)).thenReturn(reversed);

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reversedText").value(reversed));

        verify(stringService, times(1)).reverseString(longText);
    }

    @Test
    @DisplayName("POST /api/string/reverse should handle Unicode characters")
    void reverse_shouldHandleUnicode() throws Exception {
        // Given
        String unicodeText = "Hello 世界 🌍";
        ReverseRequest request = new ReverseRequest(unicodeText);
        when(stringService.reverseString(unicodeText)).thenReturn("🌍 世界 olleH");

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reversedText").value("🌍 世界 olleH"));

        verify(stringService, times(1)).reverseString(unicodeText);
    }

    @Test
    @DisplayName("POST /api/string/reverse should handle emojis")
    void reverse_shouldHandleEmojis() throws Exception {
        // Given
        String emojiText = "👋🌍💻";
        ReverseRequest request = new ReverseRequest(emojiText);
        when(stringService.reverseString(emojiText)).thenReturn("💻🌍👋");

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reversedText").value("💻🌍👋"));

        verify(stringService, times(1)).reverseString(emojiText);
    }

    @Test
    @DisplayName("POST /api/string/reverse should handle mixed whitespace")
    void reverse_shouldHandleMixedWhitespace() throws Exception {
        // Given
        String whitespaceText = " hello \t\n world ";
        ReverseRequest request = new ReverseRequest(whitespaceText);
        when(stringService.reverseString(whitespaceText)).thenReturn(" dlrow \n\t olleh ");

        // When & Then
        mockMvc.perform(post("/api/string/reverse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reversedText").value(" dlrow \n\t olleh "));

        verify(stringService, times(1)).reverseString(whitespaceText);
    }

    @Test
    @DisplayName("POST /api/string/shift should handle zero shifts")
    void shift_shouldHandleZeroShifts() throws Exception {
        // Given
        ShiftRequest request = new ShiftRequest("hello", 0, "left");
        when(stringService.shiftString("hello", 0, "left")).thenReturn("hello");

        // When & Then
        mockMvc.perform(post("/api/string/shift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shiftedText").value("hello"))
                .andExpect(jsonPath("$.numOfShifts").value(0))
                .andExpect(jsonPath("$.direction").value("left"));

        verify(stringService, times(1)).shiftString("hello", 0, "left");
    }

    @Test
    @DisplayName("POST /api/string/shift should accept various direction formats")
    void shift_shouldAcceptVariousDirectionFormats() throws Exception {
        // Test different valid direction formats
        String[] validDirections = {"left", "l", "right", "r", "LEFT", "Right", " L ", " R "};

        for (String direction : validDirections) {
            // Given
            ShiftRequest request = new ShiftRequest("hello", 1, direction);
            when(stringService.shiftString(anyString(), anyInt(), anyString())).thenReturn("elloh");

            // When & Then
            mockMvc.perform(post("/api/string/shift")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.shiftedText").value("elloh"));

            // Reset mock for next iteration
            reset(stringService);
        }
    }
}