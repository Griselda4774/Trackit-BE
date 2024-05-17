package com.trackingorder.trackit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShippingMethodDTO {
    private String shippingType;
    private String arrivalTime;
    private String shippedBy;
    private double price;
    private double discount;
}
