package com.exchange_simulator.service;

import com.exchange_simulator.dto.position.PositionBuyRequestDto;
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
    private final CryptoDataService cryptoDataService;

    @Transactional
    public Position buy(PositionBuyRequestDto dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("user not found"));
        var ticker = cryptoDataService.getTicker(dto.getToken(),"USDT");
        Position newPosition = new Position(dto.getToken(), dto.getQuantity(), ticker.getLast(), user);
        return positionRepository.save(newPosition);
    }
    public List<PositionResponseDto> getPortfolio(Long userId) {
        return positionRepository.findAllByUserId(userId).stream().map(PositionService::getDto).toList();
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
