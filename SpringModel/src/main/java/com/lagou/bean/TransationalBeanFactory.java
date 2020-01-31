package com.lagou.bean;

import com.lagou.annotation.Transactional;
import com.lagou.proxy.TransactionJdkProxy;
import com.lagou.proxy.TransactionProxyFactory;
import com.lagou.utils.SearchPackageClassUtil;
import com.lagou.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransationalBeanFactory {
    private static TransationalBeanFactory transationalBeanFactory;

    private TransationalBeanFactory() {
    }

    public static TransationalBeanFactory init(){
        if(transationalBeanFactory==null){
            transationalBeanFactory=new TransationalBeanFactory();
        }
        return transationalBeanFactory;
    }

    private Map<String,Object> transactionalMap = new HashMap<>();

    public Map<String, Object> getTransactionalMap() {
        return transactionalMap;
    }

    public void searchTransactionToBeansContext(){
        TransactionJdkProxy transactionProxy = TransactionProxyFactory.init().getTransactionProxy();
        List<Class> classes = SearchPackageClassUtil.init().getClasses();
        for(Class cls : classes){
            Transactional transactional = (Transactional) cls.getAnnotation(Transactional.class);
            if(transactional!=null){
                Object tracsactionProxyObject = null;
                try {
                    tracsactionProxyObject = transactionProxy.getServerProxyObject(cls.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                transactionalMap.put(StringUtil.toLowerCaseFirstOne(cls.getSimpleName()),cls.getInterfaces()[0].cast(tracsactionProxyObject));
            }
        }
    }
}
