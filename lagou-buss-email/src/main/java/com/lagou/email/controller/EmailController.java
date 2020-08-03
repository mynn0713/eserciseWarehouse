package com.lagou.email.controller;

import com.lagou.email.rpc.SendCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    SendCodeService sendCodeService;

    @RequestMapping("/email/{email}/{code}")
    public String email(
            @PathVariable(name = "email")String email,
            @PathVariable(name = "code") String code){
        System.out.println("email:"+email+",code:"+code);
        return "success";
    }

    @RequestMapping("/getSendCode/{userId}")
    public String email(
            @PathVariable(name = "userId")String userId){
        System.out.println("userId:"+userId);

        String code = sendCodeService.getSendCodeByUserId(userId);

        System.out.println("show return code "+code);

        return "success";
    }
}
