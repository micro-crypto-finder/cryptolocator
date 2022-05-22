package com.jjbeto.microservice.cryptolocator.controller;

import com.jjbeto.microservice.cryptolocator.dto.PriceDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.jjbeto.microservice.cryptolocator.controller.CryptoLocatorController.VALID_ISO_CODE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CryptoLocatorControllerTest {

    @Autowired
    CryptoLocatorController controller;

    String dollar = "USD";

    String euro = "EUR";

    @Test
    void findPriceInEuro() {
        final PriceDataDto dto = controller.getCryptoAvailable(euro);

        assertEquals(euro, dto.getCode());
        assertNotNull(dto.getValue());
        assertThat(dto.getDisclaimer(), containsString("CoinDesk"));
    }

    @Test
    void findPriceInUsDollar() {
        final PriceDataDto dto = controller.getCryptoAvailable(dollar);

        assertEquals(dollar, dto.getCode());
        assertNotNull(dto.getValue());
        assertThat(dto.getDisclaimer(), containsString("CoinDesk"));
    }

    @Test
    void returnEuroAsDefaultIfIpCurrencyNotFound() {
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
