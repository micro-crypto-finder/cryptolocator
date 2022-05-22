package com.jjbeto.microservice.cryptolocator.dto;

import java.util.Map;

public class CoinDeskResponseDto {

    private String disclaimer;

    private Map<String, CoinDeskResponseBpiDto> bpi;

    public CoinDeskResponseDto() {
    }

    public CoinDeskResponseDto(String disclaimer, Map<String, CoinDeskResponseBpiDto> bpi) {
        this.disclaimer = disclaimer;
        this.bpi = bpi;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Map<String, CoinDeskResponseBpiDto> getBpi() {
        return bpi;
    }

    public void setBpi(Map<String, CoinDeskResponseBpiDto> bpi) {
        this.bpi = bpi;
    }

}
