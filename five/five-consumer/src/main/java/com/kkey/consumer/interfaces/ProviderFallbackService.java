package com.kkey.consumer.interfaces;

import org.springframework.stereotype.Component;

@Component
public class ProviderFallbackService implements ProviderService{

    @Override
    public String providerSayHello(String name) {
        return "不好意思，后台服务出现问题，我给fall back了。";
    }
}
