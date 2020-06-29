package com.lagou.eureka.lagousyseureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableEurekaClient
public class LagouSysEureka8762Application {

    public static void main(String[] args) {
        SpringApplication.run(LagouSysEureka8762Application.class, args);
    }

}
