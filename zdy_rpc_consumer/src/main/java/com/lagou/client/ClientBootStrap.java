package com.lagou.client;

import com.lagou.service.UserService;

import java.util.Date;

public class ClientBootStrap {
    public static  final String providerName="UserService#sayHello#";

    public static void main(String[] args) throws InterruptedException {
        RpcConsumer rpcConsumer = new RpcConsumer();
        UserService userService = (UserService) rpcConsumer.createProxy(UserService.class);
        //Thread.sleep(20000);
        while (true){
            System.out.println("调用方法前");
            String hello = userService.sayHello("say hello!!");
            System.out.println(new Date() + ",成功接收返回值："+hello);
            Thread.sleep(2000);
        }
    }
}
