package com.exchange_simulator.dto.position;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.exchange_simulator.entity.Position}
 */
@Value
public class PositionResponseDto implements Serializable {
    Long id;
    Instant updatedAt;
    Instant createdAt;
    String token;
    BigDecimal quantity;
    BigDecimal buyPrice;
    Instant closedAt;
}