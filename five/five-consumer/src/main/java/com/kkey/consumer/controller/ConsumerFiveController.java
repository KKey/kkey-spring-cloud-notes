package com.kkey.consumer.controller;

import com.kkey.consumer.interfaces.ProviderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("consumer")
public class ConsumerFiveController {

    @Resource
    private ProviderService providerService;

    /**
     * NOTE five：借助feign集成Hystrix
     */
    @GetMapping("/hello/{name}")
    public String sayHelloRibbon(@PathVariable("name") String name) {
        return providerService.providerSayHello(name) + "  ==>> five consumer";
    }

}
