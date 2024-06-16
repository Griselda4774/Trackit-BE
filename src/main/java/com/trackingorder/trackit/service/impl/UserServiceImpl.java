package com.trackingorder.trackit.service.impl;

import com.trackingorder.trackit.dto.AccountDTO;
import com.trackingorder.trackit.entity.AccountEntity;
import com.trackingorder.trackit.entity.UserEntity;
import com.trackingorder.trackit.repository.AccountRepository;
import com.trackingorder.trackit.repository.UserRepository;
import com.trackingorder.trackit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public String updateLazadaAccount(String username, AccountDTO accountDTO) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return "User not found";
        }

        UserEntity user = userOptional.get();
        Optional<AccountEntity> accountOptional = Optional.empty();

        if (user.getLazadaAccountId() != null) {
            accountOptional = accountRepository.findById(user.getLazadaAccountId());
        }

        if (!accountOptional.isPresent()) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setEmail(accountDTO.getEmail());
            accountEntity.setUsername(accountDTO.getUsername());
            accountEntity.setPassword(accountDTO.getPassword());
            accountRepository.save(accountEntity);
            user.setLazadaAccountId(accountEntity.getId());
            userRepository.save(user); // Save user to update LazadaAccountId
        } else {
            AccountEntity account = accountOptional.get();
            account.setEmail(accountDTO.getEmail());
            account.setUsername(accountDTO.getUsername());
            account.setPassword(accountDTO.getPassword());
            accountRepository.save(account);
        }

        return "Update Lazada account successfully";
    }

    @Override
    public String updateShopeeAccount(String username, AccountDTO accountDTO) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return "User not found";
        }

        UserEntity user = userOptional.get();
        Optional<AccountEntity> accountOptional = Optional.empty();

        if (user.getShopeeAccountId() != null) {
            accountOptional = accountRepository.findById(user.getShopeeAccountId());
        }

        if (!accountOptional.isPresent()) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setEmail(accountDTO.getEmail());
            accountEntity.setUsername(accountDTO.getUsername());
            accountEntity.setPassword(accountDTO.getPassword());
            accountRepository.save(accountEntity);
            user.setShopeeAccountId(accountEntity.getId());
            userRepository.save(user); // Save user to update ShopeeAccountId
        } else {
            AccountEntity account = accountOptional.get();
            account.setEmail(accountDTO.getEmail());
            account.setUsername(accountDTO.getUsername());
            account.setPassword(accountDTO.getPassword());
            accountRepository.save(account);
        }

        return "Update Shopee account successfully";
    }
}