package com.exchange_simulator.dto.position;

import com.exchange_simulator.exceptionHandler.exceptions.PositionNotFoundException;

import java.math.BigDecimal;

public record PositionSellResponseDto(
        String token,
        BigDecimal quantity,
        BigDecimal valueInUsdt,
        BigDecimal buyPrice,
        BigDecimal sellPrice
)
{}
