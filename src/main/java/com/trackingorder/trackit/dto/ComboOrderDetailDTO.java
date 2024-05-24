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
public class ComboOrderDetailDTO implements BaseOrderDetailDTO {
    private List<OrderDetailDTO> orderDetailList;
    private double totalPrice;
}
