package com.lagou.code.controller;

import com.lagou.code.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    CodeService codeService;
    //生成验证码发送至邮箱
    @RequestMapping("/create/{email}")
    public String codeCreate(
            @PathVariable(name = "email")String email){
        System.out.println("email:"+email);
        String result = "";
        try {
            result = codeService.codeCreate(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/validate/{email}/{code}")
    public String codeValidate(
            @PathVariable(name = "email")String email,@PathVariable(name = "code")String code){
        System.out.println("email:"+email+",code="+code);
        return "welcome";
    }
}
