package com.lagou.proxy;

import com.lagou.jdbc.ConnectionManager;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Properties;

public class TransactionCglibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object resultObect = null;
        ConnectionManager connectionManager = ConnectionManager.init();
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
        connectionManager.createConnectionToThreanLocal(properties);
        try {
            //resultObect = method.invoke(target,args);
            resultObect = methodProxy.invokeSuper(o,objects);
            connectionManager.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
            connectionManager.rollbackTransaction();
            throw new Exception(e);
        }finally{
            connectionManager.closeConnection();
        }
        return resultObect;
    }
}
