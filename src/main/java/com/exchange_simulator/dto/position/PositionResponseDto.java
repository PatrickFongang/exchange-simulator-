package com.exchange_simulator.dto.position;

import com.exchange_simulator.entity.Position;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link Position}
 */
public record PositionResponseDto(
        Long id,
        Instant updatedAt,
        Instant createdAt,
        String token,
        BigDecimal quantity,
        BigDecimal buyPrice,
        Instant closedAt) implements Serializable
{}