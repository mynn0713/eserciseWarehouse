package com.lagou.tp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author : maoying
 * @Date : 2020/5/13 11:27
 */
@SpringBootApplication
public class DubboTpApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo.xml");
        context.start();
        SpringApplication.run(DubboTpApplication.class, args);
    }
}
