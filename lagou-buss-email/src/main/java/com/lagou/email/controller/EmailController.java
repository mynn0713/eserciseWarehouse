package com.lagou.email.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @RequestMapping("/email/{email}/{code}")
    public String email(
            @PathVariable(name = "email")String email,
            @PathVariable(name = "code") String code){
        System.out.println("email:"+email+",code:"+code);
        return "success";
    }
}
