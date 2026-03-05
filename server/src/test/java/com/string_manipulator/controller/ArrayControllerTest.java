package com.string_manipulator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.string_manipulator.dto.array.SeparationRequest;
import com.string_manipulator.dto.array.SortRequest;
import com.string_manipulator.dto.array.SumRequest;
import com.string_manipulator.service.ArrayService;
import com.string_manipulator.service.SeparationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArrayController.class)
@DisplayName("ArrayController Tests")
class ArrayControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ArrayService arrayService;

    private SumRequest validSumRequest;
    private SortRequest validSortRequest;
    private SeparationRequest validSeparationRequest;

    @BeforeEach
    void setUp() {
        validSumRequest = new SumRequest(Arrays.asList(1, 2, 3));
        validSortRequest = new SortRequest(Arrays.asList(3, 1, 2), "ascending");
        validSeparationRequest = new SeparationRequest(Arrays.asList(1, -2, 3, -4), "parity");
    }

    @Nested
    @DisplayName("1. Happy-Path Behavior Tests")
    class HappyPathTests {

        @Test
        @DisplayName("POST /api/array/sum - Integer array should return 200 and correct response")
        void sumIntegerArray_ShouldReturn200AndCorrectResponse() throws Exception {
            // Arrange
            when(arrayService.sumArray(any(int[].class))).thenReturn(6);

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSumRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sum").value(6.0));

            verify(arrayService, times(1)).sumArray(any(int[].class));
        }

        @Test
        @DisplayName("POST /api/array/sum - Double array should return 200 and correct response")
        void sumDoubleArray_ShouldReturn200AndCorrectResponse() throws Exception {
            // Arrange
            SumRequest doubleRequest = new SumRequest(Arrays.asList(1.5, 2.5, 3.0));
            when(arrayService.sumArray(any(double[].class))).thenReturn(7.0);

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(doubleRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sum").value(7.0));

            verify(arrayService, times(1)).sumArray(any(double[].class));
        }

        @Test
        @DisplayName("POST /api/array/sort - Integer array ascending should return 200 and correct response")
        void sortIntegerArrayAscending_ShouldReturn200AndCorrectResponse() throws Exception {
            // Arrange
            when(arrayService.sortArray(any(int[].class), anyString())).thenReturn(new int[]{1, 2, 3});

            // Act & Assert
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSortRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", containsInAnyOrder(1, 2, 3)))
                    .andExpect(jsonPath("$.orderType").value("ascending"));

            verify(arrayService, times(1)).sortArray(any(int[].class), eq("ascending"));
        }

        @Test
        @DisplayName("POST /api/array/sort - Double array descending should return 200 and correct response")
        void sortDoubleArrayDescending_ShouldReturn200AndCorrectResponse() throws Exception {
            // Arrange
            SortRequest doubleSortRequest = new SortRequest(Arrays.asList(1.5, 2.5, 3.0), "descending");
            when(arrayService.sortArray(any(double[].class), anyString())).thenReturn(new double[]{3.0, 2.5, 1.5});

            // Act & Assert
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(doubleSortRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", containsInAnyOrder(3.0, 2.5, 1.5)))
                    .andExpect(jsonPath("$.orderType").value("descending"));

            verify(arrayService, times(1)).sortArray(any(double[].class), eq("descending"));
        }

        @Test
        @DisplayName("POST /api/array/separate - Integer array parity should return 200 and correct response")
        void separateIntegerArrayParity_ShouldReturn200AndCorrectResponse() throws Exception {
            // Arrange
            SeparationResult<Integer> result = new SeparationResult<>(
                    new ArrayList<>(Arrays.asList(2, 4)),
                    new ArrayList<>(Arrays.asList(1, 3)),
                    SeparationResult.SeparationType.PARITY);
            when(arrayService.separateArray(any(int[].class), anyString())).thenReturn(result);

            // Act & Assert
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSeparationRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.firstGroup", containsInAnyOrder(2, 4)))
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup", containsInAnyOrder(1, 3)))
                    .andExpect(jsonPath("$.separationType").value("parity"));

            verify(arrayService, times(1)).separateArray(any(int[].class), eq("parity"));
        }

        @Test
        @DisplayName("POST /api/array/separate - Double array sign should return 200 and correct response")
        void separateDoubleArraySign_ShouldReturn200AndCorrectResponse() throws Exception {
            // Arrange
            SeparationRequest doubleSeparationRequest = new SeparationRequest(Arrays.asList(1.5, -2.5, 3.0, -4.7), "sign");
            SeparationResult<Double> result = new SeparationResult<>(
                    new ArrayList<>(Arrays.asList(1.5, 3.0)),
                    new ArrayList<>(Arrays.asList(-2.5, -4.7)),
                    SeparationResult.SeparationType.SIGN);
            when(arrayService.separateArray(any(double[].class), anyString())).thenReturn(result);

            // Act & Assert
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(doubleSeparationRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.firstGroup", containsInAnyOrder(1.5, 3.0)))
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup", containsInAnyOrder(-2.5, -4.7)))
                    .andExpect(jsonPath("$.separationType").value("sign"));

            verify(arrayService, times(1)).separateArray(any(double[].class), eq("sign"));
        }
    }

    @Nested
    @DisplayName("2. DTO Validation Failure Tests (400 Bad Request)")
    class ValidationFailureTests {

        @Test
        @DisplayName("POST /api/array/sum - Missing numbers field should return 400")
        void sumMissingNumbersField_ShouldReturn400() throws Exception {
            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"))
                    .andExpect(jsonPath("$.message").exists())
                    .andExpect(jsonPath("$.details").isArray())
                    .andExpect(jsonPath("$.details", hasItem(containsString("numbersList"))));

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("POST /api/array/sum - Empty array should return 400")
        void sumEmptyArray_ShouldReturn400() throws Exception {
            // Arrange
            SumRequest emptyRequest = new SumRequest(Collections.emptyList());

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(emptyRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.details", hasItem(containsString("empty"))));

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("POST /api/array/sum - Null array should return 400")
        void sumNullArray_ShouldReturn400() throws Exception {
            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"numbersList\": null}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.details", hasItem(containsString("null"))));

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("POST /api/array/sum - Array with null element should return 400")
        void sumArrayWithNullElement_ShouldReturn400() throws Exception {
            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"numbersList\": [1, null, 3]}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.details", hasItem(containsString("Number cannot be null"))));

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("POST /api/array/sort - Missing order type should return 400")
        void sortMissingOrderType_ShouldReturn400() throws Exception {
            // Act & Assert
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"numbersList\": [1, 2, 3]}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.details", hasItem(containsString("orderType"))));

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("POST /api/array/sort - Invalid order type should return 400")
        void sortInvalidOrderType_ShouldReturn400() throws Exception {
            // Arrange
            SortRequest invalidRequest = new SortRequest(Arrays.asList(1, 2, 3), "invalid");

            // Act & Assert
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.details", hasItem(containsString("Order type must represent"))));

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("POST /api/array/separate - Invalid separation type should return 400")
        void separateInvalidSeparationType_ShouldReturn400() throws Exception {
            // Arrange
            SeparationRequest invalidRequest = new SeparationRequest(Arrays.asList(1, 2, 3), "invalid");

            // Act & Assert
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.details", hasItem(containsString("Separation type must represent"))));

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("POST /api/array/sum - Array exceeding max size should return 400")
        void sumArrayExceedingMaxSize_ShouldReturn400() throws Exception {
            // Arrange - Create array with 1001 elements (exceeds max of 1000)
            Number[] largeArray = new Number[1001];
            Arrays.fill(largeArray, 1);
            SumRequest largeRequest = new SumRequest(Arrays.asList(largeArray));

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(largeRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.details", hasItem(containsString("exceed 1000 elements"))));

            verifyNoInteractions(arrayService);
        }
    }

    @Nested
    @DisplayName("3. JSON Type Mismatch Error Tests (400 Bad Request)")
    class JsonTypeMismatchTests {

        @Test
        @DisplayName("POST /api/array/sum - Numbers field as string should return 400")
        void sumNumbersAsString_ShouldReturn400() throws Exception {
            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"numbersList\": \"not-an-array\"}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("POST /api/array/sum - Mixed types in array should return 400")
        void sumMixedTypesInArray_ShouldReturn400() throws Exception {
            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"numbersList\": [1, \"two\", 3]}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("POST /api/array/sum - Boolean values in array should return 400")
        void sumBooleanValuesInArray_ShouldReturn400() throws Exception {
            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"numbersList\": [true, false]}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("POST /api/array/sort - Invalid JSON structure should return 400")
        void sortInvalidJsonStructure_ShouldReturn400() throws Exception {
            // Act & Assert
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"numbersList\": [1, 2, 3], \"orderType\":}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

            verifyNoInteractions(arrayService);
        }
    }

    @Nested
    @DisplayName("4. Service Exception Tests")
    class ServiceExceptionTests {

        @Test
        @DisplayName("POST /api/array/sum - IllegalArgumentException should return 400")
        void sumIllegalArgumentException_ShouldReturn400() throws Exception {
            // Arrange
            when(arrayService.sumArray(any(int[].class)))
                    .thenThrow(new IllegalArgumentException("Array cannot be empty"));

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSumRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.message").value("Service validation failed"));

            verify(arrayService, times(1)).sumArray(any(int[].class));
        }

        @Test
        @DisplayName("POST /api/array/sort - IllegalStateException should return 400")
        void sortIllegalStateException_ShouldReturn400() throws Exception {
            // Arrange
            when(arrayService.sortArray(any(int[].class), anyString()))
                    .thenThrow(new IllegalStateException("Sorting failed due to internal error"));

            // Act & Assert
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSortRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.message").value("Service state failed"));

            verify(arrayService, times(1)).sortArray(any(int[].class), anyString());
        }

        @Test
        @DisplayName("POST /api/array/separate - Unexpected exception should return 500")
        void separateUnexpectedException_ShouldReturn500() throws Exception {
            // Arrange
            when(arrayService.separateArray(any(int[].class), anyString()))
                    .thenThrow(new RuntimeException("Unexpected error"));

            // Act & Assert
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSeparationRequest)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").value(500));

            verify(arrayService, times(1)).separateArray(any(int[].class), anyString());
        }
    }

    @Nested
    @DisplayName("5. JSON Contract Tests")
    class JsonContractTests {

        @Test
        @DisplayName("POST /api/array/sum - Response should have correct JSON structure")
        void sumResponseJsonContract_ShouldBeCorrect() throws Exception {
            // Arrange
            when(arrayService.sumArray(any(int[].class))).thenReturn(6);

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSumRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isMap())
                    .andExpect(jsonPath("$.sum").isNumber())
                    .andExpect(jsonPath("$.sum").value(6.0))
                    .andExpect(jsonPath("$.sum").exists())
                    .andExpect(jsonPath("$.sum").isNumber())
                    .andExpect(jsonPath("$.*", hasSize(1))); // Only one field: sum
        }

        @Test
        @DisplayName("POST /api/array/sort - Integer response should have correct JSON structure")
        void sortIntegerResponseJsonContract_ShouldBeCorrect() throws Exception {
            // Arrange
            when(arrayService.sortArray(any(int[].class), anyString())).thenReturn(new int[]{1, 2, 3});

            // Act & Assert
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSortRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isMap())
                    .andExpect(jsonPath("$.sorted").isArray())
                    .andExpect(jsonPath("$.sorted", hasSize(3)))
                    .andExpect(jsonPath("$.orderType").isString())
                    .andExpect(jsonPath("$.*", hasSize(2))); // Exactly two fields: sorted, orderType
        }

        @Test
        @DisplayName("POST /api/array/separate - Response should have correct JSON structure")
        void separateResponseJsonContract_ShouldBeCorrect() throws Exception {
            // Arrange
            SeparationResult<Integer> result = new SeparationResult<>(
                    new ArrayList<>(Arrays.asList(2, 4)),
                    new ArrayList<>(Arrays.asList(1, 3)),
                    SeparationResult.SeparationType.PARITY);
            when(arrayService.separateArray(any(int[].class), anyString())).thenReturn(result);

            // Act & Assert
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSeparationRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isMap())
                    .andExpect(jsonPath("$.firstGroup").isArray())
                    .andExpect(jsonPath("$.secondGroup").isArray())
                    .andExpect(jsonPath("$.separationType").isString())
                    .andExpect(jsonPath("$.*", hasSize(3))); // Exactly three fields: firstGroup, secondGroup, separationType
        }
    }

    @Nested
    @DisplayName("6. Method Not Allowed Tests (405)")
    class MethodNotAllowedTests {

        @ParameterizedTest
        @CsvSource({
                "GET, /api/array/sum",
                "PUT, /api/array/sum",
                "DELETE, /api/array/sum"
        })
        @DisplayName("HTTP methods not allowed on /api/array/sum should return 405")
        void disallowedMethodsOnSum_ShouldReturn405(String method, String endpoint) throws Exception {
            // Arrange
            RequestBuilder requestBuilder = switch (method) {
                case "GET" -> get(endpoint);
                case "PUT" -> put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSumRequest));
                case "DELETE" -> delete(endpoint);
                default -> throw new IllegalArgumentException("Unsupported method: " + method);
            };

            // Act & Assert
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isMethodNotAllowed());

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("GET /api/array/sort should return 405")
        void sortGetRequest_ShouldReturn405() throws Exception {
            // Act & Assert
            mockMvc.perform(get("/api/array/sort"))
                    .andExpect(status().isMethodNotAllowed());

            verifyNoInteractions(arrayService);
        }

        @Test
        @DisplayName("GET /api/array/separate should return 405")
        void separateGetRequest_ShouldReturn405() throws Exception {
            // Act & Assert
            mockMvc.perform(get("/api/array/separate"))
                    .andExpect(status().isMethodNotAllowed());

            verifyNoInteractions(arrayService);
        }
    }

    @Nested
    @DisplayName("7. Routing Tests")
    class RoutingTests {

        @Test
        @DisplayName("POST /api/array/sum should route to correct method")
        void sumRouting_ShouldWork() throws Exception {
            // Arrange
            when(arrayService.sumArray(any(int[].class))).thenReturn(6);

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSumRequest)))
                    .andExpect(status().isOk());

            verify(arrayService, times(1)).sumArray(any(int[].class));
        }

        @Test
        @DisplayName("POST /api/array/sort should route to correct method")
        void sortRouting_ShouldWork() throws Exception {
            // Arrange
            when(arrayService.sortArray(any(int[].class), anyString())).thenReturn(new int[]{1, 2, 3});

            // Act & Assert
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSortRequest)))
                    .andExpect(status().isOk());

            verify(arrayService, times(1)).sortArray(any(int[].class), anyString());
        }

        @Test
        @DisplayName("POST /api/array/separate should route to correct method")
        void separateRouting_ShouldWork() throws Exception {
            // Arrange
            SeparationResult<Integer> result = new SeparationResult<>(
                    new ArrayList<>(Arrays.asList(2, 4)),
                    new ArrayList<>(Arrays.asList(1, 3)),
                    SeparationResult.SeparationType.PARITY);
            when(arrayService.separateArray(any(int[].class), anyString())).thenReturn(result);

            // Act & Assert
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validSeparationRequest)))
                    .andExpect(status().isOk());

            verify(arrayService, times(1)).separateArray(any(int[].class), anyString());
        }
    }

    @Nested
    @DisplayName("8. Edge-Case Input Tests")
    class EdgeCaseInputTests {

        @Test
        @DisplayName("POST /api/array/sum - Very large array should return 200")
        void sumVeryLargeArray_ShouldReturn200() throws Exception {
            // Arrange - Create array with 1000 elements (max allowed)
            Number[] largeArray = new Number[1000];
            Arrays.fill(largeArray, 1);
            SumRequest largeRequest = new SumRequest(Arrays.asList(largeArray));
            when(arrayService.sumArray(any(int[].class))).thenReturn(1000);

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(largeRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sum").value(1000.0));

            verify(arrayService, times(1)).sumArray(any(int[].class));
        }

        @Test
        @DisplayName("POST /api/array/sum - Array with negative numbers should return 200")
        void sumNegativeNumbers_ShouldReturn200() throws Exception {
            // Arrange
            SumRequest negativeRequest = new SumRequest(Arrays.asList(-1, -2, -3));
            when(arrayService.sumArray(any(int[].class))).thenReturn(-6);

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(negativeRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sum").value(-6.0));

            verify(arrayService, times(1)).sumArray(any(int[].class));
        }

        @Test
        @DisplayName("POST /api/array/sum - Array with decimals should return 200")
        void sumDecimals_ShouldReturn200() throws Exception {
            // Arrange
            SumRequest decimalRequest = new SumRequest(Arrays.asList(1.5, 2.7, 3.3));
            when(arrayService.sumArray(any(double[].class))).thenReturn(7.5);

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(decimalRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sum").value(7.5));

            verify(arrayService, times(1)).sumArray(any(double[].class));
        }

        @Test
        @DisplayName("POST /api/array/sum - Array with repeated values should return 200")
        void sumRepeatedValues_ShouldReturn200() throws Exception {
            // Arrange
            SumRequest repeatedRequest = new SumRequest(Arrays.asList(5, 5, 5, 5));
            when(arrayService.sumArray(any(int[].class))).thenReturn(20);

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(repeatedRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sum").value(20.0));

            verify(arrayService, times(1)).sumArray(any(int[].class));
        }

        @Test
        @DisplayName("POST /api/array/sum - Array with extreme values should return 200")
        void sumExtremeValues_ShouldReturn200() throws Exception {
            // Arrange
            SumRequest extremeRequest = new SumRequest(Arrays.asList(Integer.MAX_VALUE, Integer.MIN_VALUE));
            when(arrayService.sumArray(any(int[].class))).thenReturn(Math.toIntExact(-1L)); // MAX_VALUE + MIN_VALUE = -1 due to overflow

            // Act & Assert
            mockMvc.perform(post("/api/array/sum")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extremeRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sum").value(-1.0));

            verify(arrayService, times(1)).sumArray(any(int[].class));
        }

        @Test
        @DisplayName("POST /api/array/sort - Single element array should return 200")
        void sortSingleElement_ShouldReturn200() throws Exception {
            // Arrange
            SortRequest singleRequest = new SortRequest(Arrays.asList(42), "ascending");
            when(arrayService.sortArray(any(int[].class), anyString())).thenReturn(new int[]{42});

            // Act & Assert
            mockMvc.perform(post("/api/array/sort")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(singleRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.sorted", hasSize(1)))
                    .andExpect(jsonPath("$.sorted[0]").value(42));

            verify(arrayService, times(1)).sortArray(any(int[].class), anyString());
        }

        @Test
        @DisplayName("POST /api/array/separate - Single element array should return 200")
        void separateSingleElement_ShouldReturn200() throws Exception {
            // Arrange
            SeparationRequest singleRequest = new SeparationRequest(Arrays.asList(1), "parity");
            SeparationResult<Integer> result = new SeparationResult<>(
                    new ArrayList<>(Arrays.asList(1)),
                    new ArrayList<>(Arrays.asList()),
                    SeparationResult.SeparationType.PARITY);
            when(arrayService.separateArray(any(int[].class), anyString())).thenReturn(result);

            // Act & Assert
            mockMvc.perform(post("/api/array/separate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(singleRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstGroup", hasSize(1)))
                    .andExpect(jsonPath("$.firstGroup[0]").value(1))
                    .andExpect(jsonPath("$.secondGroup", hasSize(0)));

            verify(arrayService, times(1)).separateArray(any(int[].class), anyString());
        }
    }
}