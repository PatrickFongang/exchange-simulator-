package com.exchange_simulator.service;

import com.exchange_simulator.dto.order.OrderRequestDto;
import com.exchange_simulator.entity.Order;
import com.exchange_simulator.entity.User;
import com.exchange_simulator.enums.OrderType;
import com.exchange_simulator.enums.TransactionType;
import com.exchange_simulator.repository.OrderRepository;
import com.exchange_simulator.repository.SpotPositionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MarketOrderServiceTest {
    @Mock
    UserService userService;
    @Mock
    CryptoDataService cryptoDataService;
    @Mock
    SpotPositionService spotPositionService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    SpotPositionRepository spotPositionRepository;

    @InjectMocks
    private MarketOrderService marketOrderService;

    @Test
    void buy_shouldSubtractFundsFromUser(){
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setFunds(BigDecimal.valueOf(1000));

        OrderRequestDto orderDto = new OrderRequestDto("BTC", null, BigDecimal.valueOf(0.5));
        when(userService.findUserByIdWithLock(userId)).thenReturn(mockUser);
        when(cryptoDataService.getPrice("BTC")).thenReturn(BigDecimal.valueOf(1000));

        marketOrderService.buy(orderDto, userId);

        assertEquals(BigDecimal.valueOf(500.0), mockUser.getFunds());
    }
    @Test
    void buy_shouldReturnOrderEntity(){
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setFunds(BigDecimal.valueOf(1000));

        OrderRequestDto orderDto = new OrderRequestDto("BTC", null, BigDecimal.valueOf(0.5));
        when(userService.findUserByIdWithLock(userId)).thenReturn(mockUser);
        when(cryptoDataService.getPrice("BTC")).thenReturn(BigDecimal.valueOf(1000));
        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());

        Order order = marketOrderService.buy(orderDto, userId);

        verify(spotPositionService).handleBuy(order);
        verify(orderRepository).save(any(Order.class));
        assertThat(order)
                .usingRecursiveComparison()
                .ignoringFields("closedAt")
                .isEqualTo(new Order("btc", BigDecimal.valueOf(0.5), BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(500.0),mockUser, TransactionType.BUY, OrderType.MARKET, null));
    }
   @Test
    void sell_shouldAddFundsToUser(){
       Long userId = 1L;
       User mockUser = new User();
       mockUser.setFunds(BigDecimal.ZERO);

       OrderRequestDto orderDto = new OrderRequestDto("BTC", null, BigDecimal.ONE);
       when(userService.findUserByIdWithLock(userId)).thenReturn(mockUser);
       when(cryptoDataService.getPrice("BTC")).thenReturn(BigDecimal.valueOf(1000));

       marketOrderService.sell(orderDto, userId);
       assertEquals(BigDecimal.valueOf(1000), mockUser.getFunds());
   }
    @Test
    void sell_shouldReturnOrderEntity(){
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setFunds(BigDecimal.ZERO);

        OrderRequestDto orderDto = new OrderRequestDto("BTC", null, BigDecimal.ONE);
        when(userService.findUserByIdWithLock(userId)).thenReturn(mockUser);
        when(cryptoDataService.getPrice("BTC")).thenReturn(BigDecimal.valueOf(1000));
        when(orderRepository.save(any(Order.class))).then(returnsFirstArg());

        Order order = marketOrderService.sell(orderDto, userId);

        verify(spotPositionService).handleSell(order);
        verify(orderRepository).save(any(Order.class));
        assertThat(order)
                .usingRecursiveComparison()
                .ignoringFields("closedAt")
                .isEqualTo(new Order("btc", BigDecimal.ONE, BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(1000),mockUser, TransactionType.SELL, OrderType.MARKET, null));
    }
}
