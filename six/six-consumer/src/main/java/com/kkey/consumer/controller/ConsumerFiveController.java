package com.kkey.consumer.controller;

import com.kkey.consumer.interfaces.ProviderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("consumer")
public class ConsumersixController {

    @Resource
    private ProviderService providerService;

    /**
     * NOTE six：借助feign集成Hystrix
     */
    @GetMapping("/hello/{name}")
    @HystrixCommand
    public String sayHelloRibbon(@PathVariable("name") String name) {
        return providerService.providerSayHello(name) + "  ==>> six consumer";
    }

}
