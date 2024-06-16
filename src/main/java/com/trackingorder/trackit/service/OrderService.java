package com.trackingorder.trackit.service;

import com.trackingorder.trackit.dto.AccountDTO;
import com.trackingorder.trackit.dto.MessageDTO;

public interface OrderService {

    public String getOrderTiki(AccountDTO accountDTO);
    public MessageDTO getOrderShopee(AccountDTO accountDTO);
    public MessageDTO getOrderLazada(AccountDTO accountDTO);
}
