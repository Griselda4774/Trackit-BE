package com.trackingorder.trackit.service.impl;

import com.trackingorder.trackit.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public String getTest() {
        return "This is a test";
    }
}
