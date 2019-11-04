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
![b2f9085ce9008193112cd3635f51d580.png](en-resource://database/1806:1)

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
    feign配置
    自定义配置
    http://c.biancheng.net/view/5362.html


### five：集成hystrix
* 开关配置
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
















