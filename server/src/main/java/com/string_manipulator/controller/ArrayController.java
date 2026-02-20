package com.string_manipulator.controller;

import com.string_manipulator.dto.array.SeparationRequest;
import com.string_manipulator.dto.array.SortRequest;
import com.string_manipulator.dto.array.SumRequest;
import com.string_manipulator.dto.array.SumResponse;
import com.string_manipulator.dto.array.separation_responses.DoubleSepResponse;
import com.string_manipulator.dto.array.separation_responses.IntSepResponses;
import com.string_manipulator.dto.array.sort_responses.DoubleSortResponse;
import com.string_manipulator.dto.array.sort_responses.IntSortResponse;
import com.string_manipulator.service.ArrayService;
import com.string_manipulator.service.SeparationResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/array")
public class ArrayController {

    private final ArrayService arrayService;

    @Autowired
    public ArrayController(ArrayService arrayService) {
        this.arrayService = arrayService;
    }

    @PostMapping(
            value = "/sum",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public SumResponse sum(@Valid @RequestBody SumRequest request) {
        List<Number> values = request.numbersList();
        boolean allIntegers = values.stream().allMatch(Integer.class::isInstance);

        if (allIntegers) {
            int[] arr = values.stream().mapToInt(Number::intValue).toArray();
            return new SumResponse(arrayService.sumArray(arr));
        } else {
            double[] arr = values.stream().mapToDouble(Number::doubleValue).toArray();
            return new SumResponse(arrayService.sumArray(arr));
        }

    }

    @PostMapping(
            value = "/sort",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object sort(@Valid @RequestBody SortRequest request) {

        List<Number> values = request.numbersList();

        boolean allIntegers = values.stream()
                .allMatch(Integer.class::isInstance);

        if (allIntegers) {
            int[] arr = values.stream().mapToInt(Number::intValue).toArray();
            int[] sorted = arrayService.sortArray(arr, request.orderType());

            return new IntSortResponse(
                    Arrays.stream(sorted).boxed().toList(),
                    request.orderType());
        }

        double[] arr = values.stream().mapToDouble(Number::doubleValue).toArray();
        double[] sorted = arrayService.sortArray(arr, request.orderType());

        return new DoubleSortResponse(
                Arrays.stream(sorted).boxed().toList(),
                request.orderType());
    }

    @PostMapping(
            value = "/separate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object separate(@Valid @RequestBody SeparationRequest request) {

        List<Number> values = request.numberList();

        boolean allIntegers = values.stream()
                .allMatch(Integer.class::isInstance);

        if (allIntegers) {
            // Convert to int[]
            int[] arr = values.stream()
                    .mapToInt(Number::intValue)
                    .toArray();
            //call the method first
            SeparationResult<Integer> result = arrayService.separateArray(arr, request.separationType());

            // Convert arrays back to List<Integer>
            List<Integer> first;
            List<Integer> second;
            if (result.getSeparationType() == SeparationResult.SeparationType.PARITY) {
                first = result.getEven();
                second = result.getOdd();
            } else {
                first = result.getPositive();
                second = result.getNegative();
            }

            return new IntSepResponses(first, second, result.getSeparationType().getValue());
        }

        // Otherwise treat as double[]
        double[] arr = values.stream()
                .mapToDouble(Number::doubleValue)
                .toArray();

        SeparationResult<Double> result = arrayService.separateArray(arr, request.separationType());

        List<Double> first;
        List<Double> second;
        if (result.getSeparationType() == SeparationResult.SeparationType.PARITY) {
            first = result.getEven();
            second = result.getOdd();
        } else {
            first = result.getPositive();
            second = result.getNegative();
        }

        return new DoubleSepResponse(first, second, result.getSeparationType().getValue());
    }
    
}
