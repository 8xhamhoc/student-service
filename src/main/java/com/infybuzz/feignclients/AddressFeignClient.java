package com.infybuzz.feignclients;

import com.infybuzz.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address-feign-client", url = "${feign.client.address}", path = "/api/address")
public interface AddressFeignClient {

    @GetMapping("/getById/{id}")
    AddressResponse getById(@PathVariable("id") long id);
}
