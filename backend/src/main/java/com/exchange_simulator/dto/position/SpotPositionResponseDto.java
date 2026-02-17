package com.exchange_simulator.dto.position;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public record SpotPositionResponseDto(
        Long positionId,
        String token,
        BigDecimal quantity,
        BigDecimal avgBuyPrice,
        BigDecimal positionValue,
        Instant timestamp
) implements Serializable
{}