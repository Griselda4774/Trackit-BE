package com.trackingorder.trackit.controller;

import com.trackingorder.trackit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trackit/api/product")
public class ProductController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    public ResponseEntity<String> getTestApi() {
        String testString = orderService.getOrderTiki();
        return new ResponseEntity<String>(testString, HttpStatus.OK);
    }
}
