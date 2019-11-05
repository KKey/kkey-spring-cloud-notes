package com.kkey.provider.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderTwoController {

    @GetMapping("/hello/{name}")
    @HystrixCommand
    public String sayHello(@PathVariable("name") String name) {
        return name + ", hello world! ==>> five provider two";
    }

}
