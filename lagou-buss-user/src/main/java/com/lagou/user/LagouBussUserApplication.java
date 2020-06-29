package com.lagou.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.lagou.user.mapper")//mapper接口包路径
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
public class LagouBussUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(LagouBussUserApplication.class, args);
    }

}
