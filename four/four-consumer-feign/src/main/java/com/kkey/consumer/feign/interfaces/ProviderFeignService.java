package com.kkey.consumer.feign.interfaces;

import com.kkey.consumer.feign.config.FeignLocalConfig;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 不再借用springMVC的注解，直接使用feign的注解，因此新增了FeignLocalConfig配置类
 */
@FeignClient(value = ProviderFeignService.SERVICE_ID,
        configuration = FeignLocalConfig.class)
public interface ProviderFeignService {

    String SERVICE_ID = "four-provider";

    @RequestLine("GET /hello/{name}")
    String providerSayHelloFeign(@Param("name") String name);

}
