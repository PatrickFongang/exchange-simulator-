package com.exchange_simulator.controller;

import com.exchange_simulator.dto.position.PositionBuyRequestDto;
import com.exchange_simulator.dto.position.PositionResponseDto;
import com.exchange_simulator.entity.Position;
import com.exchange_simulator.service.CryptoDataService;
import com.exchange_simulator.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/users-positions")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;

    @GetMapping("/{userId}")
    public List<PositionResponseDto> getPortfolio(@PathVariable Long userId)
    {
        return positionService.getPortfolio(userId);
    }
    @PostMapping("/{userId}/buy")
    public PositionResponseDto buy(
            @PathVariable Long userId,
            @RequestBody PositionBuyRequestDto positionBuyRequestDto
            ) {
        positionBuyRequestDto.setId(userId);
        var position = positionService.buy(positionBuyRequestDto);
        return PositionService.getDto(position);
    }
}
