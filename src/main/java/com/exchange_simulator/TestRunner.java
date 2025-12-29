package com.exchange_simulator;

import com.exchange_simulator.service.CryptoDataService;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestRunner implements CommandLineRunner {

    private final CryptoDataService cryptoDataService;

    public TestRunner(CryptoDataService cryptoDataService) {
        this.cryptoDataService = cryptoDataService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- DANE PUBLICZNE Z BINANCE ---");

        // 1. Pobierz Ticker
        Ticker ticker = cryptoDataService.getTicker("BTC", "USDT");
        System.out.println("Para: " + ticker.getCurrencyPair());
        System.out.println("Cena ostatnia: " + ticker.getLast());
        System.out.println("Wolumen 24h: " + ticker.getVolume());

        System.out.println("\n--- ORDER BOOK (TOP 3) ---");

        // 2. Pobierz Order Book
        OrderBook orderBook = cryptoDataService.getOrderBook("BTC", "USDT");

        List<LimitOrder> asks = orderBook.getAsks(); // Sprzedający (chcą drogo)
        List<LimitOrder> bids = orderBook.getBids(); // Kupujący (chcą tanio)

        System.out.println("SPRZEDAJĄCY (Asks):");
        asks.stream().limit(3).forEach(o ->
                System.out.println("Cena: " + o.getLimitPrice() + " | Ilość: " + o.getOriginalAmount()));

        System.out.println("KUPUJĄCY (Bids):");
        bids.stream().limit(3).forEach(o ->
                System.out.println("Cena: " + o.getLimitPrice() + " | Ilość: " + o.getOriginalAmount()));

        System.out.println("--------------------------------");
    }
}