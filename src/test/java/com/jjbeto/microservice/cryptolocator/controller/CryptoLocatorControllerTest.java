package com.jjbeto.microservice.cryptolocator.controller;

import com.jjbeto.microservice.cryptolocator.client.CoinDeskClient;
import com.jjbeto.microservice.cryptolocator.dto.CoinDeskResponseBpiDto;
import com.jjbeto.microservice.cryptolocator.dto.CoinDeskResponseDto;
import com.jjbeto.microservice.cryptolocator.dto.PriceDataDto;
import com.jjbeto.microservice.cryptolocator.service.CoinDeskService;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;

import static com.jjbeto.microservice.cryptolocator.controller.CryptoLocatorController.VALID_ISO_CODE;
import static com.jjbeto.microservice.cryptolocator.service.CoinDeskService.CURRENCY_FORMAT;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CryptoLocatorControllerTest {

    @Autowired
    CryptoLocatorController controller;

    @MockBean
    CoinDeskClient coinDeskClient;

    @InjectMocks
    CoinDeskService service;

    String dollar = "USD";

    String euro = "EUR";

    @Test
    void findPriceInEuro() {
        var responseDto = new CoinDeskResponseDto("CoinDesk", Map.of(euro, new CoinDeskResponseBpiDto(euro, "1")));
        when(coinDeskClient.getCurrentPrice(CURRENCY_FORMAT.formatted(euro))).thenReturn(responseDto);

        final PriceDataDto dto = controller.getCryptoAvailable(euro);

        assertEquals(euro, dto.getCode());
        assertNotNull(dto.getValue());
        assertThat(dto.getDisclaimer(), containsString("CoinDesk"));
    }

    @Test
    void findPriceInUsDollar() {
        var responseDto = new CoinDeskResponseDto("CoinDesk", Map.of(dollar, new CoinDeskResponseBpiDto(dollar, "2")));
        when(coinDeskClient.getCurrentPrice(CURRENCY_FORMAT.formatted(dollar))).thenReturn(responseDto);

        final PriceDataDto dto = controller.getCryptoAvailable(dollar);

        assertEquals(dollar, dto.getCode());
        assertNotNull(dto.getValue());
        assertThat(dto.getDisclaimer(), containsString("CoinDesk"));
    }

    @Test
    void returnEuroAsDefaultIfIpCurrencyNotFound() {
        var responseDto = new CoinDeskResponseDto("CoinDesk", Map.of(euro, new CoinDeskResponseBpiDto(euro, "1")));
        when(coinDeskClient.getCurrentPrice(CURRENCY_FORMAT.formatted("XXX"))).thenThrow(FeignException.class);
        when(coinDeskClient.getCurrentPrice(CURRENCY_FORMAT.formatted(euro))).thenReturn(responseDto);

        final PriceDataDto dto = controller.getCryptoAvailable("XXX");

        assertEquals(euro, dto.getCode());
        assertNotNull(dto.getValue());
        assertThat(dto.getDisclaimer(), containsString("CoinDesk"));
    }

    @Test
    void validListOfIpv4() {
        for (String each : List.of("USD", "EUR", "ABC", "XXX")) {
            assertTrue(each.matches(VALID_ISO_CODE), "Currency '%s' is invalid".formatted(each));
        }
    }

    @Test
    void invalidListOfIpv4() {
        for (String each : List.of("123", "BANANA", "usd", "eur", "bitcoin")) {
            assertFalse(each.matches(VALID_ISO_CODE), "Currency '%s' is valid".formatted(each));
        }
    }

}
