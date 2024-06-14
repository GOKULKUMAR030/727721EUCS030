package com.afford.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.afford.exam.service.HttpCalculatorService;
import com.afford.exam.service.HttpCalculatorService.Response;

public class HttpCalculatorController {
    
    @Autowired
    private HttpCalculatorService numberService;

    @GetMapping("/{numberId}")
    public ResponseEntity<?> getNumbers(@PathVariable String numberId) {
        Response response = numberService.fetchAndCalculate(numberId);
        return ResponseEntity.ok(response);
    }
}
