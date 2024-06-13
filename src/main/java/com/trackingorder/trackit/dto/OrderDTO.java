package com.trackingorder.trackit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements BaseOrderDTO {
    private String status;
    private List<StatusDetailDTO> statusDetailList;
    private Date createdDate;
    private List<OrderDetailDTO> orderDetailList;
    private double coin;
    private UserAddressDTO userAddress;
    private ShippingMethodDTO shippingMethod;
    private String paymentMethod;
    private double totalCost;
    private double shipCost;
    private double shipDiscount;
    private double shopDiscount;
    private double voucherDiscount;
    private double finalCost;
}
