package com.exchange_simulator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="spot_positions")
@AllArgsConstructor
@NoArgsConstructor
public class SpotPosition extends Base{
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Column(nullable = false)
    private String token;

    @Getter
    @Setter
    @Column(nullable = false, name = "quantity", precision = 19, scale = 8)
    private BigDecimal quantity;

    @Getter
    @Column(nullable = false)
    private BigDecimal avgBuyPrice;

    @Getter
    @Setter
    @Column(nullable = false)
    private Instant timestamp;

    @Override
    public String toString() {
        return quantity + " of " + token;
    }
}
