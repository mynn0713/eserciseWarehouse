package com.lagou.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class RedirectController {
    @RequestMapping("/loginPage")
    public String userInfo(){
        System.out.println("跳转登录页面");
        return "redirect:http://192.168.145.128/static/login.html";
    }
}
