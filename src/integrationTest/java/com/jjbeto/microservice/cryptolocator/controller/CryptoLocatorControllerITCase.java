package com.jjbeto.microservice.cryptolocator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CryptoLocatorControllerITCase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findPriceInEuro() throws Exception {
        final String currency = "EUR";
        mockMvc.perform(get("/api/crypto-locator/price/bitcoin/" + currency))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", notNullValue()))
                .andExpect(jsonPath("$.code", is(currency)))
                .andExpect(jsonPath("$.disclaimer", containsString("CoinDesk")));
    }

    @Test
    void findPriceInUsDollar() throws Exception {
        final String currency = "USD";
        mockMvc.perform(get("/api/crypto-locator/price/bitcoin/" + currency))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", notNullValue()))
                .andExpect(jsonPath("$.code", is(currency)))
                .andExpect(jsonPath("$.disclaimer", containsString("CoinDesk")));
    }

    @Test
    void returnEuroAsDefaultIfIpCurrencyNotFound() throws Exception {
        final String currency = "XXX";
        mockMvc.perform(get("/api/crypto-locator/price/bitcoin/" + currency))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", notNullValue()))
                .andExpect(jsonPath("$.code", is("EUR")))
                .andExpect(jsonPath("$.disclaimer", containsString("CoinDesk")));
    }

    @Test
    void returnErrorIfInvalidDataIsGiven() throws Exception {
        final String currency = "BANANA";
        mockMvc.perform(get("/api/crypto-locator/price/bitcoin/" + currency))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
