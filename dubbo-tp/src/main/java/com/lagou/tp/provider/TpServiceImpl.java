package com.lagou.tp.provider;

import com.lagou.tp.service.TpService;

import java.util.Random;

/**
 * @Author : maoying
 * @Date : 2020/5/13 11:16
 */
public class TpServiceImpl implements TpService {
    @Override
    public String methodA() {
        int sleep = this.randomInt();
        return "methodA sleep "+sleep+"ms .";
    }

    @Override
    public String methodB() {
        int sleep = this.randomInt();
        return "methodB sleep "+sleep+"ms .";
    }

    @Override
    public String methodC() {
        int sleep = this.randomInt();
        return "methodC sleep "+sleep+"ms .";
    }

    private int randomInt(){
        Random random = new Random();
        int sleep = random.nextInt(100);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sleep;
    }
}
