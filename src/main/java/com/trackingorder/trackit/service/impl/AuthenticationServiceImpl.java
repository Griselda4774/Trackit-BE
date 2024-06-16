package com.trackingorder.trackit.service.impl;

import com.trackingorder.trackit.dto.UserDTO;
import com.trackingorder.trackit.entity.UserEntity;
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
    public String login(String username, String password) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) { // Verify hashed password
            return "Login successfully!";
        }
        return "Error occur while login";
    }
}
