package com.exchange_simulator.service;

import com.exchange_simulator.dto.position.PositionRequestDto;
import com.exchange_simulator.dto.position.PositionResponseDto;
import com.exchange_simulator.dto.position.PositionSellResponseDto;
import com.exchange_simulator.entity.Position;
import com.exchange_simulator.entity.User;
import com.exchange_simulator.exceptionHandler.exceptions.InsufficientFundsException;
import com.exchange_simulator.exceptionHandler.exceptions.NotEnoughResourcesException;
import com.exchange_simulator.exceptionHandler.exceptions.PositionNotFoundException;
import com.exchange_simulator.exceptionHandler.exceptions.UserNotFoundException;
import com.exchange_simulator.repository.PositionRepository;
import com.exchange_simulator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;
    private final CryptoDataService cryptoDataService;

    @Transactional
    public Position buy(PositionRequestDto dto) {
        User user = findUserById(dto.getUserId());
        var price = cryptoDataService.getPrice(dto.getToken());
        var priceToPay = dto.getQuantity().multiply(price);

        if(user.getFunds().compareTo(priceToPay) < 0)
            throw new InsufficientFundsException(user.getFunds(), price);
        user.setFunds(user.getFunds().subtract(priceToPay));

        return handlePosition(dto.getToken(), dto.getQuantity(), price, user);
    }
    @Transactional
    public Position sell(PositionRequestDto dto){
        var user = findUserById(dto.getUserId());
        var position = findPositionByToken(positionRepository.findAllByUserId(user.getId()), dto.getToken())
                .orElseThrow(() -> new PositionNotFoundException(user, dto.getToken()));

        if(position.getQuantity().compareTo(dto.getQuantity()) < 0)
            throw new NotEnoughResourcesException(dto.getQuantity(), position.getQuantity());

        if(position.getQuantity().compareTo(dto.getQuantity()) == 0)
            positionRepository.closePosition(user.getId(), position.getId(), Instant.now());

        positionRepository.upadateQuantityByUser(user.getId(), position.getQuantity().subtract(dto.getQuantity()), position.getId());
        user.setFunds(user.getFunds().add(dto.getQuantity().multiply(cryptoDataService.getPrice(dto.getToken()))));
        return position;
    }
    public List<PositionResponseDto> getPortfolio(Long userId) {
        return positionRepository.findAllByUserId(userId)
                .stream()
                .map(PositionService::getDto)
                .toList();
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

    public PositionSellResponseDto getSellDto(Position position, BigDecimal quantity){
        var price = cryptoDataService.getPrice(position.getToken());
        return new PositionSellResponseDto(
                position.getToken(),
                quantity,
                quantity.multiply(price),
                position.getBuyPrice(),
                price
        );
    }

    private Position handlePosition(String token, BigDecimal quantity, BigDecimal price, User user) {
        Optional<Position> position = findPositionByToken(positionRepository.findAllByUserId(user.getId()), token);

        position.ifPresent(pos -> {
                    positionRepository.upadateQuantityByUser(user.getId(), pos.getQuantity().add(quantity), pos.getId());
                    positionRepository.upadateBuyPriceByUser(user.getId(), price, token);
                }
        );
        return position.orElseGet(() -> positionRepository.save(new Position(token, quantity, price, user)));
    }

    private Optional<Position> findPositionByToken(List<Position> positions, String token) {
        return positions.stream().filter(p -> p.getToken().equals(token)).findFirst();
    }
    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
