package com.kkey.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("consumer")
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        String url = "http://localhost:6666/hello/" + name;
        String result = restTemplate.getForObject(url, String.class);
        return result + "  ==>> one consumer";
    }

}
