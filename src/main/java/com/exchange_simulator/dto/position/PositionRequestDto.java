package com.exchange_simulator.dto.position;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PositionRequestDto {
    private Long userId;
    private String token;
    private BigDecimal quantity;
}
