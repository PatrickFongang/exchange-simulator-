package com.exchange_simulator.dto.order;

import java.math.BigDecimal;

public record OrderRequestDto (
        String token,
        BigDecimal limit,
        BigDecimal quantity
)
{}
