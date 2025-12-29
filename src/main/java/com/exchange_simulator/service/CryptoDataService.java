package com.exchange_simulator.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CryptoDataService {

    private final MarketDataService marketDataService;

    public CryptoDataService(Exchange exchange) {
        this.marketDataService = exchange.getMarketDataService();
    }

    public Ticker getTicker(String base, String counter) {
        try {
            return marketDataService.getTicker(new CurrencyPair(base, counter));
        } catch (IOException e) {
            throw new RuntimeException("Ticker error", e);
        }
    }
    public OrderBook getOrderBook(String base, String counter) {
        try {
            return marketDataService.getOrderBook(new CurrencyPair(base, counter));
        } catch (IOException e) {
            throw new RuntimeException("OrderBook error", e);
        }
    }
}