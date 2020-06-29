package com.lagou.user.controller;

import com.lagou.user.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class RegisterController {
    @Autowired
    RegisterService registerService;


    @RequestMapping("/register/{email}/{password}/{code}")
    public String userRegister(
            @PathVariable(name = "email")String email,
            @PathVariable(name = "password")String password,
            @PathVariable(name = "code") String code){
        System.out.println("email:"+email+",password"+password+",code"+code);
        String result = registerService.userRegister(email, password, code);
        System.out.println(result);
        return "redirect:http://192.168.145.128/static/welcome.html";
    }

    @RequestMapping("/isRegistered/{email}")
    public String userIsRegistered(@PathVariable(name = "email")String email){
        System.out.println("email:"+email);
        return "welcome";
    }

    @CrossOrigin(origins = "web.test.com")
    @RequestMapping("/login/{email}/{password}")
    public String userLogin(
            @PathVariable(name = "email")String email,
            @PathVariable(name = "password")String password){
        System.out.println("email:"+email+",password"+password);
        return "redirect:http://192.168.145.128/static/welcome.html";
    }

    @RequestMapping("/info/{token}")
    public String userInfo(@PathVariable(name = "token")String token){
        System.out.println("token:"+token);
        return "welcome";
    }
}
