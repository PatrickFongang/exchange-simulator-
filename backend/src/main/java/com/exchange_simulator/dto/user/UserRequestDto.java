package com.exchange_simulator.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequestDto (
    @NotBlank(message = "username field can't be empty")
    String username,

    @NotBlank(message = "email field can't be empty")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "email address is incorrect"
    )
    String email,

    String password,

    String role
)
{}
