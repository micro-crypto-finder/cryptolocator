package com.jjbeto.microservice.cryptolocator.controller;

import com.jjbeto.microservice.cryptolocator.dto.PriceDataDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/crypto-locator")
@Validated
public class CryptoLocatorController {

    // generic/simple way to try to eliminate noise from invalid calls (minimally accepts only codes with 3 capital letters)
    public static final String VALID_ISO_CODE = "^[A-Z]{3}$";

    public CryptoLocatorController() {
    }

    @GetMapping("/price/bitcoin/{currency}")
    public PriceDataDto getCryptoAvailable(@PathVariable @Pattern(regexp = VALID_ISO_CODE, message = "Currency is not valid") String currency) {
        PriceDataDto priceDataDto = new PriceDataDto();
        priceDataDto.setValue("28454.7015");
        priceDataDto.setCode(currency);
        priceDataDto.setDisclaimer("This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org");
        return priceDataDto;
    }

}
