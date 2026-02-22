package com.exchange_simulator.repository;

import com.exchange_simulator.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
    boolean existsByToken(String token);
    @Transactional
    void deleteByExpiresAtBefore(Instant now);
}
