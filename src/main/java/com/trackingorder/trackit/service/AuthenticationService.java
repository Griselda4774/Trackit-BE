package com.trackingorder.trackit.service;

import com.trackingorder.trackit.dto.MessageDTO;
import com.trackingorder.trackit.dto.UserDTO;

public interface AuthenticationService {
    public String register(UserDTO userDTO);
    public MessageDTO login(String username, String password);
}
