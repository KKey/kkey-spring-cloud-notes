package com.kkey.consumer.feign.controller;

import com.kkey.consumer.feign.interfaces.ProviderFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("consumer")
public class ConsumerFourController {

    @Resource
    private ProviderFeignService providerFeignService;

    /**
     * NOTE four：借助feign的注解
     */
    @GetMapping("/hello/{name}")
    public String sayHelloRibbonFeign(@PathVariable("name") String name) {
        return providerFeignService.providerSayHelloFeign(name) + "  ==>> five consumer";
    }
}
