package com.jjbeto.microservice.cryptolocator.service;

import com.jjbeto.microservice.cryptolocator.client.CoinDeskClient;
import com.jjbeto.microservice.cryptolocator.dto.CoinDeskResponseBpiDto;
import com.jjbeto.microservice.cryptolocator.dto.CoinDeskResponseDto;
import com.jjbeto.microservice.cryptolocator.dto.PriceDataDto;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CoinDeskService {

    public static final String CACHE_COIN_PRICE = "CryptoApiService.getLastPrice";

    public static final String CURRENCY_FORMAT = "%s.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(CoinDeskService.class);

    private final CoinDeskClient coinDeskClient;

    private final String fallbackCurrency;

    public CoinDeskService(
            final CoinDeskClient coinDeskClient,
            @Value("${feign.client.coindesk.fallback.currency}") final String fallbackCurrency
    ) {
        this.coinDeskClient = coinDeskClient;
        this.fallbackCurrency = fallbackCurrency;
    }

    @Cacheable(CACHE_COIN_PRICE)
    public PriceDataDto getLastPrice(final String currency) {
        CoinDeskResponseDto responseDto;
        String resultCurrency = currency;
        try {
            responseDto = coinDeskClient.getCurrentPrice(CURRENCY_FORMAT.formatted(currency));
            LOGGER.info("Price for currency '{}' found", currency);
        } catch (FeignException e) {
            LOGGER.error("Error processing currency '%s': %s".formatted(currency, e.getMessage()), e);
            if (fallbackCurrency.equals(currency)) {
                return new PriceDataDto();
            }
            LOGGER.warn("Price for currency '{}' not found, currency '{}' returned (fallback)", currency, fallbackCurrency);
            try {
                responseDto = coinDeskClient.getCurrentPrice(CURRENCY_FORMAT.formatted(fallbackCurrency));
            } catch (FeignException eFallback) {
                LOGGER.error("Error processing fallback currency '%s': %s".formatted(fallbackCurrency, e.getMessage()), e);
                return new PriceDataDto();
            }
            resultCurrency = fallbackCurrency;
        }

        final CoinDeskResponseBpiDto bpi = responseDto.getBpi().getOrDefault(resultCurrency, new CoinDeskResponseBpiDto());
        final PriceDataDto priceDataDto = new PriceDataDto(resultCurrency, bpi.getRateFloat(), responseDto.getDisclaimer());
        LOGGER.info("Price for currency '{}' found, price: '{}'", resultCurrency, priceDataDto.getValue());
        return priceDataDto;
    }

}
