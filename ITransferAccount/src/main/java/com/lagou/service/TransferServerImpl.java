package com.lagou.service;

import com.lagou.annotation.Autowired;
import com.lagou.annotation.Service;
import com.lagou.annotation.Transactional;
import com.lagou.bean.BeanFactory;
import com.lagou.dao.ITrancferDao;
import com.lagou.jdbc.ConnectionManager;
import com.lagou.pojo.User;
import com.lagou.proxy.TransactionProxyFactory;

import java.util.Properties;

@Service
@Transactional
public class TransferServerImpl implements ITransferServer {

    @Autowired
    private ITrancferDao tracsferDaoImpl;
    @Override
    public void transfer(User userOut, User userIn, Double money) throws Exception {
        tracsferDaoImpl.modifyAccount(userOut.getAccountCode(), -money);
        //double a = 1/0;
        tracsferDaoImpl.modifyAccount(userIn.getAccountCode(), money);
    }
}
