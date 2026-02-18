package com.exchange_simulator.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends Base{
    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private final List<SpotPosition> positions = new ArrayList<>();

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String username;

    @Getter
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(nullable = false)
    private String role;

    @Getter
    @Setter
    @Column(nullable = false)
    private BigDecimal funds;

    @UpdateTimestamp
    @Column(nullable = false)
    @Getter
    private Instant updatedAt;

    @Getter
    @Setter
    @Column(nullable = false)
    private Boolean isActive;

    @Override
    public String toString() {
        return "User " + username + " with email = " + email;
    }
}
