package com.trackingorder.trackit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String username;
    private String password;
    private AccountDTO shopeeAccount;
    private AccountDTO lazadaAccount;
}