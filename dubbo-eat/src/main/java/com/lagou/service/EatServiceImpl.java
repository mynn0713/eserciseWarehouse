package com.lagou.service;

import com.lagou.api.service.EatService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class EatServiceImpl implements EatService {
    @Override
    public String doEat() {

        return "student begin to eat!!";
    }
}
