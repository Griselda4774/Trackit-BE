package com.trackingorder.trackit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private String providedBy;
    private String imageUrl;
    private double price;
    private String productOption;
    private int quantity;
}
