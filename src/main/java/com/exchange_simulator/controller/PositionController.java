package com.exchange_simulator.controller;

import com.exchange_simulator.dto.position.PositionRequestDto;
import com.exchange_simulator.dto.position.PositionBuyResponseDto;
import com.exchange_simulator.dto.position.PositionResponseDto;
import com.exchange_simulator.dto.position.PositionSellResponseDto;
import com.exchange_simulator.entity.Position;
import com.exchange_simulator.service.PositionService;
import lombok.RequiredArgsConstructor;
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
    public PositionBuyResponseDto buy(
            @PathVariable Long userId,
            @RequestBody PositionRequestDto positionRequestDto
            ) {
        positionRequestDto.setUserId(userId);
        var position = positionService.buy(positionRequestDto);
        return new PositionBuyResponseDto(position.getToken(), positionRequestDto.getQuantity(), position.getBuyPrice());
    }
    @PostMapping("/{userId}/sell")
    public PositionSellResponseDto sell(
            @PathVariable Long userId,
            @RequestBody PositionRequestDto positionRequestDto
    ){
        positionRequestDto.setUserId(userId);
        var position = positionService.sell(positionRequestDto);
        return positionService.getSellDto(position, positionRequestDto.getQuantity());
    }
}
