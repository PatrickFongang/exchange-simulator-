package com.exchange_simulator.controller;

import com.exchange_simulator.dto.position.PositionResponseDto;
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
    public List<Position> getPortfolio(@PathVariable Long userId)
    {
        return positionService.getPortfolio(userId);
    }
    @PostMapping("/{userId}/buy")
    public PositionResponseDto buy(
            @PathVariable Long userId,
            @RequestParam String token,
            @RequestParam BigDecimal quantity,
            @RequestParam BigDecimal price
    ) {
        var position = positionService.buy(userId, token, quantity, price);
        return PositionService.getDto(position);
    }
}
