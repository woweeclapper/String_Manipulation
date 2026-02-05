package com.string_manipulator.controller;

import com.string_manipulator.service.ArrayService;
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
        // TODO: implement reverseString
        return 0;
    }
    @PostMapping("/shift")
    public ResponseEntity<StringResponse> shiftString(@RequestBody StringRequest request) {
        // TODO: implement shiftString
        return 0;
    }

    @RestController
    @RequestMapping("/api/arrays")
    public class ArrayController {
        private final ArrayService arrayService;
        @Autowired
        public ArrayController(ArrayService arrayService) {
            this.arrayService = arrayService;
        }
        @PostMapping("/sum")
        public ResponseEntity<ArrayResponse> sumArray(@RequestBody ArrayRequest request) {
            // TODO: implement sumArray
            return 0;
        }
        @PostMapping("/sort")
        public ResponseEntity<ArrayResponse> sortArray(@RequestBody ArrayRequest request) {
            // TODO: implement sortArray
            return 0;
        }

    }


}

