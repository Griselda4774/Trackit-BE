package com.trackingorder.trackit.service;

import com.trackingorder.trackit.dto.AccountDTO;
import com.trackingorder.trackit.dto.MessageDTO;

public interface OrderService {

    //Test
    public String getOrderTiki(AccountDTO accountDTO);
    public MessageDTO getOrderShopee(AccountDTO accountDTO);
    public String getOrderLazada(AccountDTO accountDTO);
}
