package com.lagou.service;

import com.lagou.pojo.User;

public interface ITransferServer {

    public void transfer(User userOut, User userIn, Double money) throws Exception;
}
