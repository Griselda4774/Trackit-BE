package com.trackingorder.trackit.repository;

import com.trackingorder.trackit.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByEmail(String username);
    Optional<AccountEntity> findById(Long id);
}
