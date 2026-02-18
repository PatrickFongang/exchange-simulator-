package com.exchange_simulator.dto.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public record UserResponseDto(
        Long id,
        Instant updatedAt,
        Instant createdAt,
        String username,
        String email,
        BigDecimal funds,
        Boolean isActive) implements Serializable
{}