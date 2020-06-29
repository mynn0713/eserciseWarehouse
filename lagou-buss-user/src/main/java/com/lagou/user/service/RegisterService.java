package com.lagou.user.service;

public interface RegisterService {
    /**
     * 用户注册
     * @param email
     * @param password
     * @param code
     * @return
     */
    String userRegister(String email, String password, String code);
}
