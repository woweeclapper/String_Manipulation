package com.string_manipulator.controller;

import com.string_manipulator.dto.string.ReverseRequest;
import com.string_manipulator.dto.string.ReverseResponse;
import com.string_manipulator.dto.string.ShiftRequest;
import com.string_manipulator.dto.string.ShiftResponse;
import com.string_manipulator.service.StringService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/string")
public class StringController {

    private final StringService stringService;

    @Autowired
    public StringController(StringService stringService) {
        this.stringService = stringService;
    }

    @PostMapping(
            value = "/reverse",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReverseResponse reverse(@Valid @RequestBody ReverseRequest request) {
        String reversed = stringService.reverseString(request.text());
        return new ReverseResponse(reversed);
    }

    @PostMapping(
            value = "/shift",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ShiftResponse shift(@Valid @RequestBody ShiftRequest request) {
        String shifted = stringService.shiftString(
                request.text(),
                request.numOfShifts(),
                request.direction()
        );
        return new ShiftResponse(shifted, request.numOfShifts(), request.direction());
    }
}






