package com.jjbeto.microservice.cryptolocator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinBaseResponseBpiDto {

    private String code;

    private String description;

    @JsonProperty("rate_float")
    private String rateFloat;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(String rateFloat) {
        this.rateFloat = rateFloat;
    }

}
