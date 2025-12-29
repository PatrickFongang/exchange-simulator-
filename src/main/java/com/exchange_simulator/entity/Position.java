package com.exchange_simulator.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity
@Table(name="positions")
public class Position extends Base{
    public Position() {}

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Column(nullable = false)
    private String token;

    @Getter
    @Column(nullable = false)
    private Double quantity;

    @Getter
    @Column(nullable = false)
    private Double buyPrice;

    @Getter
    private Instant closedAt;
}
