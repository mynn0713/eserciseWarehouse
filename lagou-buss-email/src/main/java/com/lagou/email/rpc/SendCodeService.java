package com.lagou.email.rpc;

import com.lagou.api.service.CodeService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class SendCodeService {

    @Reference
    CodeService codeService;

    public String getSendCodeByUserId(String userId) {

        return codeService.sendCode(userId);

    }
}
