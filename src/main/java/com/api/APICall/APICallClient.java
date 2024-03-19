package com.api.APICall;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class APICallClient {
    @Bean
    private RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @CircuitBreaker(name = "APICall", fallbackMethod = "fallbackSendDataToThirdParty")
    public void sendData(String content) {
        String thirdPartyApiUrl = "https://97mm2.sse.codesandbox.io/teachers/";
        restTemplate().postForObject(thirdPartyApiUrl, content, String.class);
    }

    public void fallbackSendDataToThirdParty(String data, Throwable throwable) {
        // If the third-party API is down, log the error or handle as needed
        throw new RuntimeException("Third-party API is down");
    }
}
