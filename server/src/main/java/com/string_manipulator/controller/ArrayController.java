package com.string_manipulator.controller;

import com.string_manipulator.service.ArrayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
