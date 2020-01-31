package com.lagou.bean;

import com.lagou.annotation.Repository;
import com.lagou.utils.SearchPackageClassUtil;
import com.lagou.utils.StringUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryBeanFactory {
    private static RepositoryBeanFactory repositoryBeanFactory;
    private RepositoryBeanFactory() {
    }
    public static RepositoryBeanFactory init(){
        if(repositoryBeanFactory==null){
            repositoryBeanFactory = new RepositoryBeanFactory();
        }
        return repositoryBeanFactory;
    }
    private Map<String,Object> repositoryMap = new HashMap<>();

    public Map<String, Object> getRepositoryMap() {
        return repositoryMap;
    }

    public void searchRepositoryToBeansContext() throws Exception{
        List<Class> classes = SearchPackageClassUtil.init().getClasses();
        for(Class cls : classes){
            Annotation repository = cls.getAnnotation(Repository.class);
            if(repository!=null){
                repositoryMap.put(StringUtil.toLowerCaseFirstOne(cls.getSimpleName()),cls.newInstance());
            }
        }
    }

}
