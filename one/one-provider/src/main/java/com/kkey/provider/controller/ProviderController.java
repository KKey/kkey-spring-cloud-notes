package com.kkey.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return name + ", hello world! ==>> one provider";
    }

}
