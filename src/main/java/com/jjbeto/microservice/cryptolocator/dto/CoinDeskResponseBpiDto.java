package com.jjbeto.microservice.cryptolocator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinDeskResponseBpiDto {

    private String code;

    @JsonProperty("rate_float")
    private String rateFloat;

    public CoinDeskResponseBpiDto() {
    }

    public CoinDeskResponseBpiDto(String code, String rateFloat) {
        this.code = code;
        this.rateFloat = rateFloat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(String rateFloat) {
        this.rateFloat = rateFloat;
    }

}
