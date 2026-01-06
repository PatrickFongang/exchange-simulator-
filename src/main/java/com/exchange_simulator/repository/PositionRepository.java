package com.exchange_simulator.repository;

import com.exchange_simulator.entity.Position;
import com.exchange_simulator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Integer> {

    @Query("select p from Position p where p.closedAt is null and p.user.id = :userId")
    List<Position> findAllByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("update Position p set p.quantity = :quantity where p.user.id = :userId and p.id = :posId")
    void upadateQuantityByUser (Long userId, BigDecimal quantity, Long posId);

    @Transactional
    @Modifying
    @Query("update Position p set p.buyPrice = :buyPrice where p.user.id = :userId and p.token = :token")
    void upadateBuyPriceByUser (Long userId, BigDecimal buyPrice, String token);

    @Transactional
    @Modifying
    @Query("update Position p set p.closedAt = :timestamp where p.user.id = :userId and p.id = :positionId")
    void closePosition (Long userId, Long positionId, Instant timestamp);
}
