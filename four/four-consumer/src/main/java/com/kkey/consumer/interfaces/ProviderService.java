package com.kkey.consumer.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = ProviderService.SERVICE_ID)
public interface ProviderService {

    String SERVICE_ID = "four-provider";

    @GetMapping("hello/{name}")
    String providerSayHello(@PathVariable("name") String name);

}
