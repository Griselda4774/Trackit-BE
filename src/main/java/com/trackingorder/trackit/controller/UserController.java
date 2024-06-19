package com.trackingorder.trackit.controller;

import com.trackingorder.trackit.dto.AccountDTO;
import com.trackingorder.trackit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trackit/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/accounts/lazada/{username}")
    public ResponseEntity<String> updateLazadaAccount(
            @PathVariable String username,
            @RequestBody AccountDTO accountDTO
    ) {
        String result = userService.updateLazadaAccount(username, accountDTO);
        if (result.equals("Update Lazada account successfully")) {
            return new ResponseEntity<String>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/accounts/shopee/{username}")
    public ResponseEntity<String> updateShopeeAccount(
            @PathVariable String username,
            @RequestBody AccountDTO accountDTO
    ) {
        String result = userService.updateShopeeAccount(username, accountDTO);
        if (result.equals("Update Shopee account successfully")) {
            return new ResponseEntity<String>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
        }
    }
}
