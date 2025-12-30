package com.exchange_simulator.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="positions")
public class Position extends Base{
    public Position() {}

    public Position(String token, BigDecimal quantity, BigDecimal buyPrice, User user){
        this.token = token;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.user = user;
    }

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Column(nullable = false)
    private String token;

    @Getter
    @Column(nullable = false)
    private BigDecimal quantity;

    @Getter
    @Column(nullable = false)
    private BigDecimal buyPrice;

    @Getter
    private Instant closedAt;
}
