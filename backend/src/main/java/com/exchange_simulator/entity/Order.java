package com.exchange_simulator.entity;

import com.exchange_simulator.enums.OrderType;
import com.exchange_simulator.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends Base{
    @Getter
    @Column(nullable = false)
    private String token;

    @Getter
    @Column(nullable = false, name = "quantity", precision = 19, scale = 8)
    private BigDecimal quantity;

    @Getter
    @Column(nullable = false)
    private BigDecimal tokenPrice;

    @Getter
    @Column(nullable = false, name = "orderValue", precision = 19, scale = 8)
    private BigDecimal orderValue;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType orderType;

    @Getter
    @Setter
    @Column
    private Instant closedAt;

    @Override
    public String toString() {
        return  "Order: " + transactionType + " of " + quantity + " " + token + " " + " closed at " + closedAt;
    }
}
