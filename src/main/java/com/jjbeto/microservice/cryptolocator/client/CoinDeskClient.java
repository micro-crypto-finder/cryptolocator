package com.jjbeto.microservice.cryptolocator.client;

import com.jjbeto.microservice.cryptolocator.dto.CoinDeskResponseDto;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "${feign.client.coindesk.name}", url = "${feign.client.coindesk.url}")
public interface CoinDeskClient {

    @RequestMapping(method = GET, path = "/v1/bpi/currentprice/{currency}")
    CoinDeskResponseDto getCurrentPrice(@PathVariable(value = "currency") String currency) throws FeignException;

}
