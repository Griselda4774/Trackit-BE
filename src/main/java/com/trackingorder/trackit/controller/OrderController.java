package com.trackingorder.trackit.controller;

import com.trackingorder.trackit.dto.AccountDTO;
import com.trackingorder.trackit.dto.MessageDTO;
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

    @PostMapping("/shopee")
    public ResponseEntity<MessageDTO> getOrderShopee(@RequestBody AccountDTO accountDTO) {
        MessageDTO messageDTO = orderService.getOrderShopee(accountDTO);
        return new ResponseEntity<MessageDTO>(messageDTO, HttpStatus.OK);
    }

    @PostMapping("/lazada")
    public ResponseEntity<String> getOrderLazada(@RequestBody AccountDTO accountDTO) {
        String testString = orderService.getOrderLazada(accountDTO);
        return new ResponseEntity<String>(testString, HttpStatus.OK);
    }
}
