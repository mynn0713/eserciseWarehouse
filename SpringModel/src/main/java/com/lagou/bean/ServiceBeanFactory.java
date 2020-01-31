package com.lagou.bean;

import com.lagou.annotation.Service;
import com.lagou.utils.SearchPackageClassUtil;
import com.lagou.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceBeanFactory {
    private static ServiceBeanFactory serviceBeanFactory;

    private ServiceBeanFactory() {
    }
    public static ServiceBeanFactory init(){
        if(serviceBeanFactory==null){
            serviceBeanFactory = new ServiceBeanFactory();
        }
        return serviceBeanFactory;
    }

    private Map<String,Object> serviceMap = new HashMap<>();

    public Map<String, Object> getServiceMap() {
        return serviceMap;
    }

    public void searchServiceToBeansContext() throws Exception{
        List<Class> classes = SearchPackageClassUtil.init().getClasses();
        for(Class cls : classes){
            Service service = (Service) cls.getAnnotation(Service.class);
            if(service!=null){
                serviceMap.put(StringUtil.toLowerCaseFirstOne(cls.getSimpleName()),cls.newInstance());
            }
        }
    }
}
