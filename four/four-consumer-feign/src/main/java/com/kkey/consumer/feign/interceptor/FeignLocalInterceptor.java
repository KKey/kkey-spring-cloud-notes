package com.kkey.consumer.feign.interceptor;


import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignLocalInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        System.out.println("***************   do something    ***************");
        System.out.println(template.path());
        System.out.println("******************************");

    }
}
