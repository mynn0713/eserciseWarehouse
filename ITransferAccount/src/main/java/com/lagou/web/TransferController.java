package com.lagou.web;

import com.lagou.annotation.Autowired;
import com.lagou.annotation.Service;
import com.lagou.pojo.User;
import com.lagou.proxy.TransactionJdkProxy;
import com.lagou.service.ITransferServer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.lang.reflect.Field;

@WebServlet("/transfer")
@Service
public class TransferController extends HttpServlet {

    @Autowired
    private ITransferServer transferServerImpl;

    public String transfer(User userOut, User userIn, Double money){
        try {
            transferServerImpl.transfer(userOut,userIn,money);
            return "转账成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "转账失败";
        }
    }
}
