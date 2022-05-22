package com.jjbeto.microservice.cryptolocator.dto;

import java.util.HashMap;

public class CoinDeskResponseDto {

    private String disclaimer;

    private HashMap<String, CoinBaseResponseBpiDto> bpi;

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public HashMap<String, CoinBaseResponseBpiDto> getBpi() {
        return bpi;
    }

    public void setBpi(HashMap<String, CoinBaseResponseBpiDto> bpi) {
        this.bpi = bpi;
    }

}
