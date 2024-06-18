package com.trackingorder.trackit.service.impl;

import com.trackingorder.trackit.dto.AccountDTO;
import com.trackingorder.trackit.dto.MessageDTO;
import com.trackingorder.trackit.dto.UserDTO;
import com.trackingorder.trackit.entity.AccountEntity;
import com.trackingorder.trackit.entity.UserEntity;
import com.trackingorder.trackit.repository.AccountRepository;
import com.trackingorder.trackit.repository.UserRepository;
import com.trackingorder.trackit.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            return "Username already exists";
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Hash password
        userEntity.setName(userDTO.getName());

        userRepository.save(userEntity);
        return "Register successfully!";
    }

    @Override
    public MessageDTO login(String username, String password) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        UserEntity userEntity = userOpt.get();
        AccountDTO lazadaAccountDTO;
        AccountDTO shopeeAccountDTO;
        if(userEntity.getLazadaAccountId() != null) {
            Optional<AccountEntity> lazadaAccountOpt = accountRepository.findById(userEntity.getLazadaAccountId());
            lazadaAccountDTO = new AccountDTO(lazadaAccountOpt.get().getEmail(), lazadaAccountOpt.get().getUsername(), lazadaAccountOpt.get().getPassword());
        } else {
            lazadaAccountDTO = new AccountDTO();
        }

        if (userEntity.getShopeeAccountId() != null) {
            Optional<AccountEntity> shopeeAccountOpt = accountRepository.findById(userEntity.getShopeeAccountId());
            shopeeAccountDTO = new AccountDTO(shopeeAccountOpt.get().getEmail(), shopeeAccountOpt.get().getUsername(), shopeeAccountOpt.get().getPassword());
        } else {
            shopeeAccountDTO = new AccountDTO();
        }


        MessageDTO messageDTO = new MessageDTO();
        UserDTO userDTO = new UserDTO(userEntity.getName(), userEntity.getUsername(), userEntity.getPassword(), shopeeAccountDTO, lazadaAccountDTO);

        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) { // Verify hashed password
            messageDTO.setData(userDTO);
            messageDTO.setMessage("Login successfully!");
        } else {
            messageDTO.setMessage("Username or password is incorrect");
        }
        return messageDTO;
    }
}
