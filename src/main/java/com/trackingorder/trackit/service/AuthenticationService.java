package com.trackingorder.trackit.service;

import com.trackingorder.trackit.dto.UserDTO;

public interface AuthenticationService {
    public String register(UserDTO userDTO);
    public String login(String username, String password);
}
