package com.lagou.bean;

import com.lagou.annotation.Autowired;
import com.lagou.jdbc.ConnectionManager;
import com.lagou.utils.SearchPackageClassUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BeanFactoryBuilder {
    private static BeanFactoryBuilder beanFactoryBuilder;
    private Map<String,Object> beansContext;
    private BeanFactoryBuilder(){}
    public static BeanFactoryBuilder init(){
        if(beanFactoryBuilder ==null){
            beanFactoryBuilder = new BeanFactoryBuilder();
        }
        return beanFactoryBuilder;
    }

    public void searchBeansToContext(){
        beansContext = new HashMap<>();
        RepositoryBeanFactory repositoryBeanFactory = RepositoryBeanFactory.init();
        ServiceBeanFactory serviceBeanFactory = ServiceBeanFactory.init();
        TransationalBeanFactory transationalBeanFactory = TransationalBeanFactory.init();
        try {
            repositoryBeanFactory.searchRepositoryToBeansContext();
            serviceBeanFactory.searchServiceToBeansContext();
            transationalBeanFactory.searchTransactionToBeansContext();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(repositoryBeanFactory.getRepositoryMap()!=null) {
            beansContext.putAll(repositoryBeanFactory.getRepositoryMap());
        }
        if(serviceBeanFactory.getServiceMap()!=null) {
            beansContext.putAll(serviceBeanFactory.getServiceMap());
        }
        if(transationalBeanFactory.getTransactionalMap()!=null) {
            beansContext.putAll(transationalBeanFactory.getTransactionalMap());
        }

        //现场绑定连接池
        try {
            ConnectionManager connectionManager = ConnectionManager.init();
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
            connectionManager.createConnectionToThreanLocal(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //自动注入属性
        autowiredField();
    }

    public void autowiredField(){
        beansContext.forEach((k,v)-> {
            for(Field f : v.getClass().getDeclaredFields()){
                Autowired autowired = f.getAnnotation(Autowired.class);
                if(autowired==null){
                    return;
                }
                String simpleName = f.getName();
                f.setAccessible(true);
                try {
                    f.set(v,beansContext.get(simpleName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void autowiredFieldForBean(Object bean){
        for(Field f : bean.getClass().getDeclaredFields()){
            Autowired autowired = f.getAnnotation(Autowired.class);
            if(autowired==null){
                return;
            }
            String simpleName = f.getName();
            f.setAccessible(true);
            try {
                f.set(bean,beansContext.get(simpleName));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public BeanFactory createBeanFactory(String basePack){
        try {
            SearchPackageClassUtil.init().searchClass(basePack);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.searchBeansToContext();

        if(beansContext==null){
            System.out.println("容器中没有对象");
        }
        BeanFactory beanfactory = BeanFactory.init();
        beanfactory.setBeansMap(beansContext);
        return beanfactory;
    }

}
