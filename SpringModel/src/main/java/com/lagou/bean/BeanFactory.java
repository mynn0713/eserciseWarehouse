package com.lagou.bean;

import java.util.Map;

public class BeanFactory {
    private static BeanFactory beanFactory ;
    private BeanFactory(){

    }
    public static BeanFactory init(){
        if(beanFactory==null){
            beanFactory = new BeanFactory();
        }
        return beanFactory;
    }

    private Map<String,Object> beansMap;

    public void setBeansMap(Map<String, Object> beansMap) {
        this.beansMap = beansMap;
    }

    public Object getBeanObjectByBeanName(String beanName){
        Object bean = beansMap.get(beanName);
        if(bean == null){
            System.out.println("容器中未找到"+beanName+"对象");
        }
        return bean;
    }
}
