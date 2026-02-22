package com.exchange_simulator.service;

import com.exchange_simulator.entity.BlacklistedToken;
import com.exchange_simulator.repository.BlacklistedTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlacklistedTokenService {
    private final BlacklistedTokenRepository tokenRepository;

    public void blacklistToken(String token, Instant expiresAt) {
        BlacklistedToken blacklistedToken = new BlacklistedToken(token, expiresAt);
        tokenRepository.save(blacklistedToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenRepository.existsByToken(token);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanExpiredTokens() {
        log.info("Deleting expired JWT tokens...");
        tokenRepository.deleteByExpiresAtBefore(Instant.now());
        log.info("Finished deleting expired tokens");
    }
}
