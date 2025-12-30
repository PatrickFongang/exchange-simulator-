package com.exchange_simulator.repository;

import com.exchange_simulator.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Integer> {
    List<Position> findAllByUserId(Long userId);
}
