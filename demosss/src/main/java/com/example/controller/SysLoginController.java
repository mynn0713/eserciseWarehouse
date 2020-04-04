package com.example.controller;

import com.example.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/sys")
public class SysLoginController {
    @RequestMapping(value = "/login" ,method = {RequestMethod.POST,RequestMethod.GET})
    public String login(User user){
        if("admin".equals(user.getUserName())){
            if("admin".equals(user.getPassWord())){
                return "forward:/resume/table";
            }
        }
        return "";
    }
}
