package com.lagou.user.service;

import com.lagou.user.mapper.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService{
    @Autowired
    RegisterMapper registerMapper;
    @Override
    public String userRegister(String email, String password, String code,String token) {
        if(registerMapper.selectCodeInExpiretTime(email,code)<=0){
            return "codeTimeOut";
        }
        return registerMapper.insertUser(email,token)>0?"success":"fail";
    }
}
