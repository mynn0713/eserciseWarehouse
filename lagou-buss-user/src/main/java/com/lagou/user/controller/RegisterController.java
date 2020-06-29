package com.lagou.user.controller;

import com.lagou.user.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class RegisterController {
    @Autowired
    RegisterService registerService;


    @RequestMapping("/register/{email}/{password}/{code}")
    public String userRegister(
            @PathVariable(name = "email")String email,
            @PathVariable(name = "password")String password,
            @PathVariable(name = "code") String code,
            HttpServletResponse response){
        System.out.println("email:"+email+",password"+password+",code"+code);
        String token = "email_"+email+":password_"+password;
        response.setHeader("Authorization",token);
        String result = registerService.userRegister(email, password, code, token);
        System.out.println(result);
        return result;
    }

    @RequestMapping("/isRegistered/{email}")
    public String userIsRegistered(@PathVariable(name = "email")String email){
        System.out.println("email:"+email);
        return "welcome";
    }

    @RequestMapping("/login/{email}/{password}")
    public String userLogin(
            @PathVariable(name = "email")String email,
            @PathVariable(name = "password")String password){
        System.out.println("email:"+email+",password"+password);
        return "success";
    }

    @RequestMapping("/info/{token}")
    public String userInfo(@PathVariable(name = "token")String token){
        System.out.println("token:"+token);
        return "welcome";
    }

    @RequestMapping("/badRegister")
    public String badRegister(){
        System.out.println("跳转登录页面");
        return "badRegister";
    }
}
