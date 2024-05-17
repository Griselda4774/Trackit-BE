package com.trackingorder.trackit.controller;

import com.trackingorder.trackit.dto.AccountDTO;
import com.trackingorder.trackit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trackit/api/product")
public class ProductController {

    @Autowired
    private OrderService orderService;

}
