package com.trackingorder.trackit.service;

import com.trackingorder.trackit.dto.AccountDTO;

public interface UserService {
    public String updateLazadaAccount(String username, AccountDTO accountDTO);
    public String updateShopeeAccount(String username, AccountDTO accountDTO);
}
