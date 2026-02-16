package com.string_manipulator.controller;

import com.string_manipulator.service.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/strings")
public class StringController {


    private final StringService stringService;

    @Autowired
    public StringController(StringService stringService) {
        this.stringService = stringService;
    }

    @PostMapping("/reverse")
    public ResponseEntity<StringResponse> reverseString(@RequestBody StringRequest request) {
        // TODO: implement reverseString calling
        return 0;
    }

    @PostMapping("/shift")
    public ResponseEntity<StringResponse> shiftString(@RequestBody StringRequest request){
        //TODO: implement shiftString calling
    }
}






