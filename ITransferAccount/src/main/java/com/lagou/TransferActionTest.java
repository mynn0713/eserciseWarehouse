package com.lagou;

import com.lagou.bean.BeanFactory;
import com.lagou.bean.BeanFactoryBuilder;
import com.lagou.pojo.User;
import com.lagou.web.TransferController;

public class TransferActionTest {

    public static void main(String[] args) {
        BeanFactoryBuilder.init().createBeanFactory("com.lagou");
        TransferController transferController = (TransferController) BeanFactory.init().getBeanObjectByBeanName("transferController");
        User userOut = new User();
        userOut.setAccountCode("123321");
        userOut.setUsername("张三");
        User userIn = new User();
        userIn.setAccountCode("321123");
        userIn.setUsername("李四");
        Double money = 100d;
        System.out.println(transferController.transfer(userOut,userIn,money));
    }
}
