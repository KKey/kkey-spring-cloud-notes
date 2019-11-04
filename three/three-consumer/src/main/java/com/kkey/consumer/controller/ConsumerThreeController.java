package com.kkey.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("consumer")
public class ConsumerThreeController {
    private static final String SERVICE_ID = "three-provider";
    private RestTemplate restTemplate;

    public ConsumerThreeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * NOTE three：借助ribbon 自动将SERVICE_ID转换成host:port
     * NOTE 同时在restTemplate添加loadBalance
     */
    @GetMapping("/hello/{name}")
    public String sayHelloRibbon(@PathVariable("name") String name) {
        String url = "http://" + SERVICE_ID + "/hello/" + name;
        String result = restTemplate.getForObject(url, String.class);
        return result + "  ==>> three consumer";
    }

}
