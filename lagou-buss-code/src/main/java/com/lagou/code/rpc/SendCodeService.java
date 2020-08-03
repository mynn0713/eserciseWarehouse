package com.lagou.code.rpc;

import com.lagou.api.service.CodeService;
import com.lagou.code.utils.RandomUtils;
import org.apache.dubbo.config.annotation.Service;

@Service
public class SendCodeService implements CodeService {
    @Override
    public String sendCode(String userId) {
        System.out.println("send userId is "+userId);
        String code = userId +"_"+ RandomUtils.getRandomStr(6);
        return code;
    }
}
