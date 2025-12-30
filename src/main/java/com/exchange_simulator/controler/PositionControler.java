package com.exchange_simulator.controler;

import com.exchange_simulator.entity.Position;
import com.exchange_simulator.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/users-positions")
@RequiredArgsConstructor
public class PositionControler {
    private final PositionService positionService;

    @GetMapping("/{userId}")
    public List<Position> getPortfolio(@PathVariable Long userId)
    {
        return positionService.getPortfolio(userId);
    }
    @PostMapping("/{userId}/buy")
    public Position buy(
            @PathVariable Long userId,
            @RequestParam String token,
            @RequestParam BigDecimal quantity,
            @RequestParam BigDecimal price
    ) {
        return positionService.buy(userId, token, quantity, price);
    }
}
