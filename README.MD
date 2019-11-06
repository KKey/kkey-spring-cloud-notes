# springCloud基本指引

### base：spring cloud简介和组件介绍
######################################

*************************************
## 笔记记录
### one：spring cloud项目搭建
    spring initializr:https://start.spring.io/

### two：集成eureka
* 集成eureka、安装spring-assistant插件
* 单节点，无需进行注册服务和拉取服务，因为自身可以做eureka client
* Network level connection to peer localhost; retrying after delay Connect to localhost:8761 timed out
    https://www.jianshu.com/p/788745f7dc7d
* 服务动态发现，通过discoverClient、loadBalancerClient，然后借助restTemplate。
* 集成actuactor，直接添加pom依赖则可，然后发现返回结果{}，发现是没有配置端点信息
```
management:
  endpoints:
    web:
      exposure:
        include: health,info,beans
  endpoint:
    health:
      show-details: always
```
* 添加身份认证
* 扩容

### three：集成ribbon
com.netflix.client.IClientConfigAware.AbstractLoadBalancerRule的子类
```
AvailabilityFilteringRule
BestAvailableRule
ClientConfigEnabledRoundRobinRule
PredicateBasedRule
RandomRule
ResponseTimeWeightedRule
RetryRule
RoundRobinRule
WeightedResponseTimeRule
ZoneAvoidanceRule
```
![b2f9085ce9008193112cd3635f51d580.png](en-resource://database/2932:1)

* 配置负载均衡策略
```
# three-provider 是service name 名称，配置策略
three-provider:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
```
    
* 代码配置负载均衡策略
使用@RibbonClient注解，指定service的负载均衡策略
// name是服务提供者名，configuration是配置的负载均衡策略类
```java
//注入rule bean
public class MyIRule {
    @Bean
    public IRule rule() {
        return new RandomRule();
    }
}

@RibbonClient(name="three-provider:", configuration = MyIRule.class)
@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate genRestTemplate() {
        return new RestTemplate();
    }
}
```

### four：集成feign
* feign配置，例如要使用feign的注解，而不用SpringMVC的注解；
    模块：four-consumer-feign
    
1. 定义本地Configuration，自定义名称，例如：FeignLocalConfig.java，
      注入要修改的配置，例如Contract，和Logger.Level
2. 在FeignClient添加configuration配置，例如：
  @FeignClient(value = ProviderFeignService.SERVICE_ID,        configuration = FeignLocalConfig.class)
3. 将SpringMVC的注解换成Feign的注解；
  `@GetMapping("/hello/{name}")==>>@RequestLine("GET /hello/{name}")
  @PathVariable("name") String name==>>@Param("name") String name`

* 自定义配置
1. 自定义拦截器；实现RequestInterceptor则可，然后再configuration中注入bean。
    例如代码中的com.kkey.consumer.feign.interceptor.FeignLocalInterceptor

* 用烹制文件配置指定，而非编写代码方式
```
#处理使用代码编码方式修改基本配置，还可以通过配置文件方式
feign:
  client:
    config:
      four-provider: # service name
        connectTimeout: 5000 
        readTimeout: 5000
        loggerLevel: FULL
        requestInterceptors[0]:  interceptor类路径
        encoder: encoder类路径
        decoder: decoder类路径
        contract: 类路径
```
    

### five：集成hystrix
* 开关配置，将feign集成的hystrix开关打开
```
feign:
  hystrix:
    enabled: true
```
* 容错：熔断、降级 TODO 
* 线程隔离和信号量隔离 TODO 
* 监控：dashboard
  1. 确保有actuactor和dashBoard两个依赖;
  2. 启动一下注解：
    @EnableDiscoveryClient
    @EnableHystrix
    @EnableHystrixDashboard
  3. 访问http://localhost:7777/hystrix，可以看到dashBoard界面；
  4. 开放actuactor访问端点；
```
management:
   endpoints:
    web:
      exposure:
        include: health,info,hystrix.stream
```
   5. 此时访问端点，/actuator/hystrix.stream只有ping为空显示；
   6. 添加接口采集点注解；@HystrixCommand
   7. 再次访问端点/actuator/hystrix.stream，此时不仅有空ping，还有data；
   8. 在dashBoard界面，添加url，例如：`http://localhost:7777/actuator/hystrix.stream`，并设置数据同步周期后，点击Monitor Stream后可以看到图形界面；


* 监控：turbine

### six：集成zuul
* 集成和路由：
    1. 单独新起一个模块：six-zuul用作网关转发，添加zuul和eureka依赖
    2. 添加配置：
    ```
    server:
      port: 8888

    eureka:
      client:
        service-url:
          defaultZone: http://localhost:5555/eureka/
    spring:
      application:
        name: zuul-gateway
    zuul:
      routes:
        six:
          path: /six/**
          serviceId: six-consumer
    ```
        转发规则：
        源监听端口8888，如果http://localhost:8888/后面带的/six/的uri则进行转发给six-consumer服务，因为依赖了eureka，所以服务信息从eureka取。
        例如http://localhost:8888/six/consumer/hello/kkey
        直接转：http://localhost:7777/consumer/hello/kkey

    ***

* 添加验证过滤器：登陆认证、日志、异常处理
    pre/route/post/error：直接集成ZuulFilter添加相应逻辑则可；

### seven：集成config
    eureka动态扩容
    动态配置

### eight：集成stream

### nine：集成sleuth

### ten：集成security

### eleven：集成nacos/consul

### twelve：docker/k8s
















