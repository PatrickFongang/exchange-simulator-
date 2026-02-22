package com.exchange_simulator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "blacklisted_tokens")
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistedToken extends Base {
    @Getter
    @Setter
    @Column(unique = true, nullable = false, length = 512)
    private String token;

    @Getter
    @Setter
    @Column(nullable = false)
    private Instant expiresAt;
}
