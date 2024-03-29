package com.kkey.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProviderOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOneApplication.class, args);
    }

}
