package com.exchange_simulator.service;

import com.exchange_simulator.dto.position.PositionResponseDto;
import com.exchange_simulator.entity.Position;
import com.exchange_simulator.entity.User;
import com.exchange_simulator.repository.PositionRepository;
import com.exchange_simulator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    @Transactional
    public Position buy(Long userId, String token, BigDecimal quantity, BigDecimal buyPrice) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        Position newPosition = new Position(token, quantity, buyPrice, user);
        positionRepository.save(newPosition);
        return newPosition;
    }
    public List<Position> getPortfolio(Long userId) {
        return positionRepository.findAllByUserId(userId);
    }

    public static PositionResponseDto getDto(Position position){
        return new PositionResponseDto(
                position.getId(),
                position.getUpdatedAt(),
                position.getCreatedAt(),
                position.getToken(),
                position.getQuantity(),
                position.getBuyPrice(),
                position.getClosedAt());
    }
}
