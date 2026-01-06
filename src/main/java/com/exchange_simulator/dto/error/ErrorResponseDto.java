package com.exchange_simulator.dto.error;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;


public record ErrorResponseDto(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path)
{}
