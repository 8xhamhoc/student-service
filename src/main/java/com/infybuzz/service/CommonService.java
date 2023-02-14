package com.infybuzz.service;

import com.infybuzz.feignclients.NewAddressFeignClient;
import com.infybuzz.response.AddressResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    Logger logger = LoggerFactory.getLogger(CommonService.class);

    int count = 1;
    @Autowired
    NewAddressFeignClient newAddressFeignClient;

    @CircuitBreaker(name = "addressService", fallbackMethod = "fallbackGetAddressId")
    public AddressResponse getAddressById(long addressId) {
        logger.info("count: {}", count);
        count++;
        return newAddressFeignClient.findById(addressId);
    }

    public AddressResponse fallbackGetAddressId(long addressId, Throwable throwable) {
        logger.error("Error", throwable);
        return new AddressResponse();
    }
}
