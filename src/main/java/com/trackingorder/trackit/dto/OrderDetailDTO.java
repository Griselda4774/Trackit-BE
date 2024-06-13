package com.trackingorder.trackit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO{
    private List<ProductDTO> productList;
    private double totalPrice;
}
