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
public class OrderDTO {
    private String status;
    private String createdDate;
    private List<OrderDetailDTO> orderDetailDTOList;
    private double coin;
    private UserAddressDTO userAddressDTO;
    private ShippingMethodDTO shippingMethodDTO;
    private PaymentMethodDTO paymentMethodDTO;
}
