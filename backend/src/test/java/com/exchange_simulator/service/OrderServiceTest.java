package com.exchange_simulator.service;

import com.exchange_simulator.dto.order.OrderRequestDto;
import com.exchange_simulator.entity.User;
import com.exchange_simulator.exceptionHandler.exceptions.exchange.BadQuantityException;
import com.exchange_simulator.exceptionHandler.exceptions.exchange.InsufficientFundsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock private UserService userService;
    @Mock private CryptoDataService cryptoDataService;

    @InjectMocks
    private OrderService orderService;


    @Test
    void validateQuantity_shouldThrowException_whenQuantityIsZeroOrLess() {
        assertThrows(BadQuantityException.class,
                () -> orderService.validateQuantity(BigDecimal.ZERO));

        assertThrows(BadQuantityException.class,
                () -> orderService.validateQuantity(BigDecimal.valueOf(-1)));
    }

    @Test
    void validateQuantity_shouldPass_whenQuantityIsPositive() {
        assertDoesNotThrow(() -> orderService.validateQuantity(BigDecimal.valueOf(1.5)));
    }


    @Test
    void toPay_shouldCalculateCorrectPrice_whenFundsAreSufficient() {
        User user = new User();
        user.setFunds(BigDecimal.valueOf(1000));

        BigDecimal quantity = BigDecimal.valueOf(2);
        BigDecimal tokenPrice = BigDecimal.valueOf(150);

        BigDecimal result = orderService.toPay(user, quantity, tokenPrice);

        assertEquals(BigDecimal.valueOf(300), result);
    }

    @Test
    void toPay_shouldThrowException_whenFundsAreInsufficient() {
        User user = new User();
        user.setFunds(BigDecimal.valueOf(100));

        BigDecimal quantity = BigDecimal.valueOf(2);
        BigDecimal tokenPrice = BigDecimal.valueOf(150);

        assertThrows(InsufficientFundsException.class,
                () -> orderService.toPay(user, quantity, tokenPrice));
    }


    @Test
    void prepareToBuy_shouldReturnFinalization_forMarketOrder() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setFunds(BigDecimal.valueOf(1000));

        OrderRequestDto dto = new OrderRequestDto("BTC",null, BigDecimal.valueOf(0.5));

        when(userService.findUserByIdWithLock(userId)).thenReturn(mockUser);
        when(cryptoDataService.getPrice("BTC")).thenReturn(BigDecimal.valueOf(1000));

        OrderService.OrderFinalization result = orderService.prepareToBuy(dto, userId);

        assertNotNull(result);
        assertEquals(mockUser, result.user());
        assertEquals(BigDecimal.valueOf(1000), result.tokenPrice());

        assertEquals(0, BigDecimal.valueOf(500.0).compareTo(result.orderValue()));
    }

    @Test
    void prepareToBuy_shouldReturnFinalization_forLimitOrder() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setFunds(BigDecimal.valueOf(1000));

        OrderRequestDto dto = new OrderRequestDto("BTC", BigDecimal.valueOf(900), BigDecimal.valueOf(0.5));

        when(userService.findUserByIdWithLock(userId)).thenReturn(mockUser);

        OrderService.OrderFinalization result = orderService.prepareToBuy(dto, userId);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(900), result.tokenPrice());
        assertEquals(0, BigDecimal.valueOf(450.0).compareTo(result.orderValue()));
    }
}