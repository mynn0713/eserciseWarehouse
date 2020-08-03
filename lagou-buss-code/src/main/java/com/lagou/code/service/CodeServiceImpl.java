package com.lagou.code.service;

import com.lagou.code.feign.EmailFeign;
import com.lagou.code.mapper.CodeMapper;
import com.lagou.code.rpc.SendCodeService;
import com.lagou.code.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CodeServiceImpl implements CodeService{
    //@Autowired
    //EmailFeign emailFeign;
    @Autowired
    CodeMapper codeMapper;
    @Value("${code.timeout}")
    Integer codeTimeout;
    @Override
    public String codeCreate(String email) throws Exception {
        String code = RandomUtils.getRandomStr(6);
        String result = "";//emailFeign.email(email, code);
        if(result==null || !"success".equals(result)){
            return "fail";
        }

        return codeMapper.insertCode(email, code, codeTimeout) > 0 ? "success":"fail";
    }
}
