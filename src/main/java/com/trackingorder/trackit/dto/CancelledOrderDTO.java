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
public class CancelledOrderDTO implements BaseOrderDTO {
    private String status;
    private Date createdDate;
    private List<OrderDetailDTO> orderDetailList;
    private String paymentMethod;
    private Date cancelDate;
    private String requestBy;
    private String reason;
}
