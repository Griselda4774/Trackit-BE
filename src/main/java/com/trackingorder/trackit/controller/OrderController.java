package com.trackingorder.trackit.controller;

import com.trackingorder.trackit.dto.AccountDTO;
import com.trackingorder.trackit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trackit/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/tiki")
    public ResponseEntity<String> getOrderTiki(@RequestBody AccountDTO accountDTO) {
        String testString = orderService.getOrderTiki(accountDTO);
        return new ResponseEntity<String>(testString, HttpStatus.OK);
    }

    @GetMapping("/shopee")
    public ResponseEntity<String> getOrderShopee() {
        String testString = orderService.getOrderShopee();
        return new ResponseEntity<String>(testString, HttpStatus.OK);
    }

    @GetMapping("/lazada")
    public ResponseEntity<String> getOrderLazada() {
        String testString = orderService.getOrderLazada();
        return new ResponseEntity<String>(testString, HttpStatus.OK);
    }
}
