package com.exchange_simulator.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})
public class User extends Base{
    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private final List<Position> positions = new ArrayList<>();

    @Getter
    @Column(nullable = false)
    private String name;

    @Getter
    @Column(nullable = false)
    private String email;

    public User() {}

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User " + name + " with email = " + email;
    }
}
