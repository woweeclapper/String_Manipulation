package com.string_manipulator.controller;

import com.string_manipulator.dto.string.ReverseRequest;
import com.string_manipulator.dto.string.ReverseResponse;
import com.string_manipulator.dto.string.ShiftRequest;
import com.string_manipulator.dto.string.ShiftResponse;
import com.string_manipulator.service.StringService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/reverse")
    public ReverseResponse reverse(@RequestBody ReverseRequest request) {
        String reversed = stringService.reverseString(request.text());
        return new ReverseResponse(reversed);
    }

    @PostMapping("/shift")
    public ShiftResponse shift(@RequestBody ShiftRequest request){
        String shifted = stringService.shiftString(request.text(), request.numOfShifts(),  request.direction());
        return new ShiftResponse(shifted, request.numOfShifts(), request.direction());
    }
}






