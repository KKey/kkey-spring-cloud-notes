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
* 容错：熔断、降级
* 线程隔离和信号量隔离
* 监控：dashboard、turbine

### six：集成zuul
    添加验证过滤器：登陆认证、日志、异常处理
    灰度、版本
    管理端点

### seven：集成config
    eureka动态扩容
    动态配置

### eight：集成stream

### nine：集成sleuth

### ten：集成security

### eleven：集成nacos/consul

### twelve：docker/k8s
















