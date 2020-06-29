package com.lagou.code;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.lagou.code.mapper")//mapper接口包路径
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients
public class LagouBussCodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(LagouBussCodeApplication.class, args);
    }

}
