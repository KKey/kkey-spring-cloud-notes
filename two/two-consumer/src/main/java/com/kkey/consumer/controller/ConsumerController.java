package com.kkey.consumer.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("consumer")
public class ConsumerController {

    private static final String SERVICE_ID = "two-provider";
    private LoadBalancerClient loadBalancerClient;
    private RestTemplate restTemplate;
    private DiscoveryClient discoveryClient;

    public ConsumerController(LoadBalancerClient loadBalancerClient, RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.loadBalancerClient = loadBalancerClient;
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        ServiceInstance serviceInstance = loadBalancerClient.choose(SERVICE_ID);
        String url = serviceInstance.getUri() + "/hello/" + name;
        String result = restTemplate.getForObject(url, String.class);
        return result + "  ==>> two consumer";
    }

    @GetMapping("/hello2/{name}")
    public String sayHello2(@PathVariable("name") String name) {
        List<ServiceInstance> instanceList = discoveryClient.getInstances(SERVICE_ID);
        ServiceInstance choseInstanceInfo = null;
        if (instanceList != null && instanceList.size() != 0) {
            //TODO 策略
            choseInstanceInfo = instanceList.get(0);
        } else {
            return "注册中心服务列表为空";
        }
        String url = choseInstanceInfo.getUri() + "/hello/" + name;
        String result = restTemplate.getForObject(url, String.class);
        return result + "  ==>> two consumer";
    }

}
