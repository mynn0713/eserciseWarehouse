package com.lagou.edu.controller;

import com.lagou.annotation.MyController;
import com.lagou.annotation.MyRequestMapping;
import com.lagou.annotation.MyRequestParam;
import com.lagou.annotation.Security;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MyRequestMapping("/login")
@MyController
public class LoginController {
    @Security("admin")
    @MyRequestMapping("/admin")
    public String adminLogin(HttpServletRequest request, HttpServletResponse response, String name, @MyRequestParam("userName")String userName, @MyRequestParam("passWord")String passWord){

        try{
            System.out.println("111");
        }catch (Exception e){
            return "fail";
        }
        try {
            response.getWriter().write("登陆成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
