package com.infybuzz.feignclients;

import com.infybuzz.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "api-gateway")
public interface NewAddressFeignClient {

    @GetMapping("/address-service/api/address/getById/{id}")
    AddressResponse findById(@PathVariable("id") long id);

}
