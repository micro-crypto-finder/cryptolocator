package com.jjbeto.microservice.cryptolocator.service;

import com.jjbeto.microservice.cryptolocator.client.CoinDeskClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CoinDeskServiceITCase {

    @Autowired
    CoinDeskService coinDeskService;

    @Value("${feign.client.coindesk.fallback.currency}")
    String fallbackCurrency;

    @Mock
    CoinDeskClient mockedCoinDeskClient; // test unavailability

    @Test
    void findPriceInEuro() {
        final String currency = "EUR";
        var dto = coinDeskService.getLastPrice(currency);
        assertEquals(currency, dto.getCode());
    }

    @Test
    void findPriceInUsDollar() {
        final String currency = "USD";
        var dto = coinDeskService.getLastPrice(currency);
        assertEquals(currency, dto.getCode());
    }

    @Test
    void returnEuroAsDefaultIfIpCurrencyNotFound() {
        final String currency = "XXX";
        var dto = coinDeskService.getLastPrice(currency);
        assertEquals("EUR", dto.getCode());
    }

    @Test
    void returnFallbackIfInvalidDataIsGiven() {
        final String currency = "abc!";
        var dto = coinDeskService.getLastPrice(currency);
        assertEquals("EUR", dto.getCode());
    }

}
