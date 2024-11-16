package com.yang.securitydemoservice.controller;

import com.yang.common.exception.LocalException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/resource/{id}")
    public String getResource(@PathVariable("id") int id) {
        if (id <= 0) {
            throw new LocalException("ID must be positive.");
        }
        return "Resource with ID: " + id;
    }

    @GetMapping("/exception")
    public String triggerException() {
        throw new RuntimeException("Unexpected error occurred.");
    }
}
