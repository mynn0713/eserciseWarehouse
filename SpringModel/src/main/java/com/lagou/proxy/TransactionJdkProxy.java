package com.lagou.proxy;

import com.lagou.bean.BeanFactoryBuilder;
import com.lagou.jdbc.ConnectionManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

public class TransactionJdkProxy {
    private static TransactionJdkProxy transactionJdkProxy;
    private TransactionJdkProxy(){
    }
    public static TransactionJdkProxy init (){
        if(transactionJdkProxy ==null){
            transactionJdkProxy = new TransactionJdkProxy();
        }
        return transactionJdkProxy;
    }
    public static Object getServerProxyObject(Object target){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object resultObect = null;
                ConnectionManager connectionManager = ConnectionManager.init();
                try {
                    BeanFactoryBuilder.init().autowiredFieldForBean(target);
                    resultObect = method.invoke(target,args);
                    connectionManager.commitTransaction();
                }catch (Exception e){
                    //e.printStackTrace();
                    connectionManager.rollbackTransaction();
                    throw new Exception(e);
                }finally{
                    connectionManager.closeConnection();
                }
                return resultObect;
            }
        });
    }
}
