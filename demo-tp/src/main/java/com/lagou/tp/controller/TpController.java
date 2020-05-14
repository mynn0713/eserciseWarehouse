package com.lagou.tp.controller;

import com.lagou.tp.service.TpService;
import com.lagou.tp.util.MonitorWaterLineCalculator;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : maoying
 * @Date : 2020/5/13 11:33
 */
@RestController
@RequestMapping("/say")
public class TpController {
    @RequestMapping(value = "/hello",method = {RequestMethod.POST,RequestMethod.GET})
    public void sayHello(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo.xml");
        context.start();
        TpService eatService = (TpService)context.getBean("tpService");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 1000, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

        AtomicInteger index = new AtomicInteger();
        //System.out.println(eatService.methodA());
        for (int j = 0 ; j < 10000 ;j++) {
            System.out.println("开始执行2000次调用,"+index);
            for (int i = 0; i < 2000; i++) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(eatService.methodA());
                    }
                });

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(eatService.methodB());
                    }
                });

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(eatService.methodC());
                    }
                });
            }
            System.out.println("结束执行2000次调用,"+ index);
            index.getAndIncrement();
        }
        System.out.println("测试结束");

    }
}
