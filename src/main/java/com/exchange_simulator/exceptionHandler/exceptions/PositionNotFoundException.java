package com.exchange_simulator.exceptionHandler.exceptions;

import com.exchange_simulator.entity.User;

public class PositionNotFoundException extends ExchangeException {
    public PositionNotFoundException(User user, String token) {
        super(user.toString() + " does not have " + token + " token");
    }
}
