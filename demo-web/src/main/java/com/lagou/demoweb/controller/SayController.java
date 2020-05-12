package com.lagou.demoweb.controller;

import com.lagou.api.service.EatService;
import com.lagou.api.service.StudyService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@RestController
@RequestMapping("/say")
public class SayController {
    @RequestMapping(value = "/hello",method = {RequestMethod.POST,RequestMethod.GET})
    public String sayHello(@RequestParam("name")String name, HttpServletRequest request){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo.xml");
        context.start();

        System.out.println("address:"+request.getHeader("Host"));
        String ipAdrress = this.getIpAdrress(request);
        System.out.println("ip:"+ipAdrress);

        EatService eatService = (EatService)context.getBean("eatService");
        StudyService studyService = (StudyService)context.getBean("studyService");


        RpcContext.getContext().setAttachment("requestIp",ipAdrress);
        String eat = eatService.doEat();
        System.out.println(eat);


        RpcContext.getContext().setAttachment("requestIp",ipAdrress);
        String study = studyService.doStudy();
        System.out.println(study);
        String talk = name+", say HELLO !!";
        System.out.println(talk);
        return talk;
    }


    /**
     * 获取request的客户端IP地址
     *
     * @param request
     * @return
     */
    private String getIpAdrress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
