package com.exchange_simulator.dto.position;

import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@Data
public class PositionBuyRequestDto {
    private Long id;
    private String token;
    private BigDecimal quantity;
}
