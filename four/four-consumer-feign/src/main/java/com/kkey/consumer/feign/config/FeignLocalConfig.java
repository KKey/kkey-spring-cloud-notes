package com.kkey.consumer.feign.config;


import com.kkey.consumer.feign.interceptor.FeignLocalInterceptor;
import feign.Contract;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignLocalConfig {

    @Bean
    public Contract genContract() {
        return new Contract.Default();
    }

    @Bean
    public Logger.Level genLoggerLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor genInterceptor(){
        return new FeignLocalInterceptor();
    }
}
