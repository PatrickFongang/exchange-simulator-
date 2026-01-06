package com.exchange_simulator.dto.position;

import java.math.BigDecimal;

public record PositionBuyResponseDto (
    String token,
    BigDecimal quantity,
    BigDecimal buyPrice
)
{}
