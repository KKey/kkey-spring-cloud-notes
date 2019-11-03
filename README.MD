# 基本指引

## 修改记录
### 初始化项目

## spring cloud简介和组件介绍

### spring cloud项目搭建
	spring initializr:https://start.spring.io/

### boot和cloud的关系

### 集成eureka
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


### 集成ribbon
	负载均衡策略扩展
### 集成feign

### 集成hystrix
	容错：熔断、降级
	监控：dashboard、turbine

### 集成zuul
	添加验证过滤器：登陆认证、日志、异常处理
	灰度、版本

### 集成config
	eureka动态扩容
	动态配置

### 集成sleuth

### 集成stream

### 集成security

### 集成nacos/consul

### docker/k8s







