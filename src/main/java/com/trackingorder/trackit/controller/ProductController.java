package com.trackingorder.trackit.controller;

import com.trackingorder.trackit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/test")
    public ResponseEntity<String> getTestApi() {
        String testString = productService.getTest();
        return new ResponseEntity<String>(testString, HttpStatus.OK);
    }
}
