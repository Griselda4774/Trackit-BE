package com.trackingorder.trackit.repository;

import com.trackingorder.trackit.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
