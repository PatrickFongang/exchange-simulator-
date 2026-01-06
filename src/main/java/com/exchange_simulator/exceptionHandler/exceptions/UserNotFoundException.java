package com.exchange_simulator.exceptionHandler.exceptions;

public class UserNotFoundException extends ExchangeException {
    public UserNotFoundException(Long userId) {
        super("User with id: " + userId + " not found");
    }
}
