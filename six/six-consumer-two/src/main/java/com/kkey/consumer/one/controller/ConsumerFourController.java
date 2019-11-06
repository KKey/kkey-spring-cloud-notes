//package com.kkey.consumer.one.controller;
//
//import com.kkey.consumer.one.interfaces.ProviderService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RestController
//@RequestMapping("consumer")
//public class ConsumersixController {
//
//    @Resource
//    private ProviderService providerService;
//
//    /**
//     * NOTE six：借助feign
//     */
//    @GetMapping("/hello/{name}")
//    public String sayHelloRibbon(@PathVariable("name") String name) {
//        return providerService.providerSayHello(name) + "  ==>> four consumer";
//    }
//
//}
